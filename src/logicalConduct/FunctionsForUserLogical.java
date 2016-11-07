package logicalConduct;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jdbc.SearchAboutPost;
import jdbc.UserOperation;
import tool.CheckUserInformation;
import tool.GetCurrentTime;
import tool.GetSortValue;
import allClasses.Ad;
import allClasses.Post;
import allClasses.PrivateAd;
import allClasses.PrivateAdType;
import allClasses.Unit;
import allClasses.User;
import configurations.Configuration;

public class FunctionsForUserLogical {
	private UserOperation jdbc= new UserOperation();//操作与用户相关的数据库
	private SearchAboutPost searchAboutPost=new SearchAboutPost();//操作与粘贴栏相关的数据库
	//注册，需要参数userName、password、repassword、email
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");  
		response.setCharacterEncoding("utf-8");  
		PrintWriter out=response.getWriter();
		/*用error来表示错误信息，-1为必填信息中出现错误，0为全部正确，1为邮箱错误、将邮箱设置为null,2为手机号码错误、将手机号码设置为null,
	       3为手机跟邮箱均要设置为null*/
		int error=new CheckUserInformation().check(request,response);//检查输入的信息
		if(error==-1){//必填信息有错误，则直接跳转回重新注册页面
			System.out.println("输入注册信息不正确");
			response.sendRedirect("register.jsp");
		}
		String userName=request.getParameter("userName");
		userName=new String(userName.getBytes("iso-8859-1"),"utf-8");
		String password=request.getParameter("password");
		String email;
		String phone;
		int userTypeId;
		if(error==1){//邮箱错
			email=null;
			phone=request.getParameter("phone");
		}
		else if(error==2){
			phone=null;
			email=request.getParameter("email");
		}
		else if(error==3){
			phone=null;
			email=null;
		}
		else{//如果所有信息都正确			
			email=request.getParameter("email");
			phone=request.getParameter("phone");
		}
		//userTypeId默认为0，缴费后注册
		if(request.getParameter("userTypeId")==null){
			userTypeId=0;
		}
		else{
			userTypeId=Integer.parseInt(request.getParameter("userTypeId"));
		}
		
		//userType=0
		User user = new User(userName, password, email,phone,userTypeId);
		System.out.println(userName);
		if (jdbc.register(user)) {
			out.println("<script type='text/javascript'>alert('success')</script>");
			//out.println("<script type='text/javascript'>history.go(-1)</script>");
				// request.getRequestDispatcher("registerSuccess.jsp").forward(request,response);
			response.sendRedirect("login.jsp");
		} else {
			out.println("<script type='text/javascript'>alert('the user have existed,please register a new one')</script>");
			out.println("<script type='text/javascript'>history.go(-1)</script>");
		}	
	}

	//用户登录,登录之后将cookie中存入属性user,传入属性username,password
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");  
		response.setCharacterEncoding("utf-8");  
		PrintWriter out=response.getWriter();
		if(null==request.getParameter("userName")||null==request.getParameter("password")){
			System.out.println("登陆信息不全面");
			out.println("<script type='text/javascript'>history.go(-1)</script>");//保持在原页面
		}
		else{
			String userName=request.getParameter("userName");
			userName=new String(userName.getBytes("iso-8859-1"),"utf-8");
			String password=request.getParameter("password");
			System.out.println(userName+" "+password);
			/* 
			 * 判断用户名密码是否正确,正确则将用户信息存入cookie		
			*/		
			User user=jdbc.user(userName,password);
			if(user==null){//如果没查找到对应的用户，则显示登录失败
			   
				out.println("<script type='text/javascript'>alert('false,check your userName and password')</script>");
				out.println("<script type='text/javascript'>history.go(-1)</script>");
				 
			}
			else {//查找到用户则在session保存用户信息
			   
				request.getSession().setAttribute("user",user);
				// out.println("<script type='text/javascript'>alert('success')</script>");
				 out.println("<script type='text/javascript'>window.opener.location.reload();window.close();</script>");				
				  
				//System.out.println("查找到用户"); 
			}
			
		}
	}
	
	//用户退出，将cookie中属性user设为空
	public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");  
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();		
		if(null==request.getSession().getAttribute("user")){
			System.out.println("尚未登录");
			response.sendRedirect("index.jsp");
			//out.println("<script type='text/javascript'>location.replace(document);</script>");
		}
		else{
			System.out.println("退出");
			request.getSession().removeAttribute("user");
			//out.println("<script type='text/javascript'>alert('success')</script>");
			//out.println("<script type='text/javascript'>window.location.reload();</script>");		
			//会自动刷新前一个页面
			out.println("<script type='text/javascript'>location.replace(document.referrer);</script>");
		}
	}		
	
	//用户已发布的广告
	public void myAds(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if(null==request.getSession().getAttribute("user")){
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		}
		else{
			User user=(User)request.getSession().getAttribute("user");//获取session中存的user信息
			int userId=user.getUserId();//获取UserId
			List<Ad> myAds=jdbc.myAds(userId);
			request.setAttribute("ads",myAds);
			request.getRequestDispatcher("myAds.jsp").forward(request,response);	
		}		
	}
	
	//删除用户在普通粘贴栏已发布的广告,判断用户是否登录，传入的是adId则删除用户发布的该广告，没有传入则删除所有
	public void deleteMyAds(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		System.out.println("执行deleteMyAds");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(null==request.getSession().getAttribute("user")){
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		}
		else{
			User user = (User) request.getSession().getAttribute("user");// 获取session中存的user信息
			if(null==request.getParameter("adId")){//如果没有传入aId，则删除该用户所有广告
				int userId = user.getUserId();// 获取UserId
				jdbc.deleteMyAds(userId);
			}
			else{//如果传入了adId，则删除对应广告
				int adId=Integer.parseInt(request.getParameter("adId"));
				jdbc.deleteAd(adId);
			}
			System.out.println("删除成功");
			//myAds(request,response);//跳转到显示我的广告页面
			//out.println("<script type='text/javascript'>alert('success')</script>");
			out.println("<script type='text/javascript'>location.replace(document.referrer);opener.location.reload();</script>");
		}
		
	}
	
	//删除用户专栏广告,判断用户是否登录，传入:adId,删除用户发布的该广告
	public void deletePrivateAds(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(null==request.getSession().getAttribute("user")){
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		}
		else{			
			if(null==request.getParameter("adId")){//如果没有传入aId
				out.println("<script type='text/javascript'>alert('没有选择要删除的广告');</script>");
				out.println("<script type='text/javascript'>history.go(-1);</script>");
			}
			else{//如果传入了adId，则删除对应广告
				int adId=Integer.parseInt(request.getParameter("adId"));
				jdbc.deletePrivateAd(adId);
			}
			System.out.println("删除成功");
			//myAds(request,response);//跳转到显示我的广告页面
			//out.println("<script type='text/javascript'>alert('success')</script>");
			out.println("<script type='text/javascript'>location.replace(document.referrer);opener.location.reload();</script>");
		}
		
	}
	
	//修改用户信息，需要参数userName、password、repassword、email
	public void alterUserInformation(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(request.getSession().getAttribute("user")==null){//如果没有登陆则不可以修改用户信息
			System.out.println("尚未登陆");
			response.sendRedirect("login.jsp");
		}
		else{
			int error=new CheckUserInformation().check(request,response);//检查输入的信息
			if(error==-1){//必填信息有错误，则直接跳转回修改页面
				System.out.println("输入注册信息不正确");
				response.sendRedirect("alterMyInformation.jsp");
			}
			//原有的用户信息
			User user=(User)request.getSession().getAttribute("user");
			int userId=user.getUserId();
			//新输入的用户信息
			String userName=request.getParameter("userName");
			userName=new String(userName.getBytes("iso-8859-1"),"utf-8");
			String password=request.getParameter("password");
			String email;
			String phone;
			int userTypeId;
			if(error==1){//邮箱错，则不更新邮箱信息
				email=user.getEmail();
				phone=request.getParameter("phone");
			}
			else if(error==2){//手机号码错，则不更新手机号码信息
				phone=user.getPhone();
				email=request.getParameter("email");
			}
			else if(error==3){
				phone=null;
				email=null;
			}
			else{//如果所有信息都正确			
				email=request.getParameter("email");
				phone=request.getParameter("phone");
			}
			//userTypeId更新了则修改，未更新则保持
			if(request.getParameter("userTypeId")==null){
				userTypeId=user.getUserType();
			}
			else{
				userTypeId=Integer.parseInt(request.getParameter("userTypeId"));
			}
			
			//userType=0
			User newUserInfo = new User(userId,userName, password, email,phone,userTypeId);
			System.out.println(userName);
			if (jdbc.alterUserInformation(newUserInfo)) {//修改用户信息
				System.out.println("修改信息成功");
				request.getSession().setAttribute("user",newUserInfo);//更新存在session中的信息
				out.println("<script type='text/javascript'>alert('success')</script>");
				out.println("<script type='text/javascript'>window.close();opener.location.reload();</script>");	
					// request.getRequestDispatcher("registerSuccess.jsp").forward(request,response);
			} else {
				out.println("<script type='text/javascript'>alert('the user have existed,please input a new one')</script>");
				out.println("<script type='text/javascript'>history.go(-1)</script>");
			}	
		}		
	}
	
	//登录用户关注某粘贴栏，需传入参数：postId
	public void attention(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (null == request.getSession().getAttribute("user")) {//未登录用户不能关注
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		} else {
			PrintWriter out=response.getWriter();
			if(null == request.getParameter("postId")){
				System.out.println("没有获取到postId");
				out.println("<script type='text/javascript'>alert('没有指定要关注的粘贴栏')</script>");
				out.println("<script type='text/javascript'>history.go(-1)</script>");
			}
			else{
				int postId=Integer.parseInt(request.getParameter("postId"));//获取要关注的粘贴栏ID
				User user=(User)request.getSession().getAttribute("user");//获取用户信息
				int userId=user.getUserId();
				//关注
				if(jdbc.attention(userId,postId)){//关注成功
					System.out.println("成功关注");
					out.println("<script type='text/javascript'>alert('success')</script>");
					out.println("<script type='text/javascript'>history.go(-1)</script>");
				}
				else{
					out.println("<script type='text/javascript'>alert('false')</script>");
					out.println("<script type='text/javascript'>history.go(-1)</script>");
				}
			} 
		}
	}	
	
	//登录用户关注的所有粘贴栏
	public void myAttentions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (null == request.getSession().getAttribute("user")) {//未登录用户不能显示
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		} else {
			PrintWriter out=response.getWriter(); 
			User user = (User) request.getSession().getAttribute("user");// 获取用户信息
			int userId = user.getUserId();
			// 调取用户所关注的粘贴栏信息
			List<Post> posts=(List<Post>)jdbc.myAttentions(userId);// 关注成功
			request.setAttribute("posts",posts);
			request.getRequestDispatcher("myAttentions.jsp").forward(request,response);		
		 
		}
	}		
	 
	//删除用户的某个关注
	public void deleteMyAttention(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (null == request.getSession().getAttribute("user")) {// 未登录用户不能显示
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		} else {
			PrintWriter out = response.getWriter();
			if(null==request.getParameter("postId")){
				System.out.println("没有传入PostId");
				out.println("<script type='text/javascript'>alert('没有指定要关注的粘贴栏')</script>");
				out.println("<script type='text/javascript'>history.go(-1);</script>");
			}
			else{
				User user = (User) request.getSession().getAttribute("user");// 获取用户信息
				int userId = user.getUserId();
				int postId=Integer.parseInt(request.getParameter("postId"));
				// 调取用户所关注的粘贴栏信息
				if(jdbc.deleteMyAttention(userId,postId)){
					System.out.println("删除成功");
					out.println("<script type='text/javascript'>location.replace(document.referrer);</script>");
				}
				else{
					System.out.println("删除失败");
				}
			}			
		}
	}
	
	//删除指定专栏的广告类别，传入指定postId、adTypeId,会同时删除对应类别的所有广告
	public void deleteAdType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (null == request.getSession().getAttribute("user")) {// 未登录用户不能显示
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		} else {
			PrintWriter out = response.getWriter();
			if (null == request.getParameter("postId")||null == request.getParameter("adTypeId")) {
				System.out.println("没有传入指定参数");
				out.println("<script type='text/javascript'>alert('没有传入指定参数')</script>");
				out.println("<script type='text/javascript'>history.go(-1)</script>");
			} else {
				int adTypeId = Integer.parseInt(request.getParameter("adTypeId")); 
				int postId = Integer.parseInt(request.getParameter("postId"));
				jdbc.deletePrivateAdsOfType(adTypeId);//删除对应类别下所有广告				 
				if (jdbc.deleteAdType(postId,adTypeId)){//删除该广告类别
					System.out.println("删除成功");
					out.println("<script type='text/javascript'>location.replace(document.referrer);opener.location.reload();</script>");
				} else {
					out.println("<script type='text/javascript'>alert('fail')</script>");
					System.out.println("删除失败");
				}
			}
		}	
	}
		
	// 添加指定专栏的广告类别，传入指定postId、adTypeName
	public void addAdType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (null == request.getSession().getAttribute("user")) {// 未登录用户不能显示
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		} else {
			PrintWriter out = response.getWriter();
			if (null == request.getParameter("postId")
					|| null == request.getParameter("adTypeName")) {
				System.out.println("没有传入指定参数");
				out.println("<script type='text/javascript'>alert('没有传入指定参数')</script>");
				out.println("<script type='text/javascript'>history.go(-1)</script>");
			} else {
				String adTypeName = request.getParameter("adTypeName");
				int postId = Integer.parseInt(request.getParameter("postId"));
				System.out.println("postId:"+postId+"adTypeName:"+adTypeName);
				// 调取用户所关注的粘贴栏信息
				if (jdbc.addAdType(postId, adTypeName)) {
					System.out.println("添加成功");
					out.println("<script type='text/javascript'>location.replace(document.referrer);opener.location.reload();</script>");
				} else {
					out.println("<script type='text/javascript'>alert('fail');</script>");
					System.out.println("添加失败");
				}
			}
		}
	}

	//进入专栏
	public void privatePost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (null == request.getSession().getAttribute("user")||null == request.getParameter("adTypeId")) {// 未登录用户不能进入专栏,没有传入广告类别不能进入
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		}
		else {			 
			User user = (User) request.getSession().getAttribute("user");// 获取用户信息
			int userType=user.getUserType();//获取用户类型
			PrintWriter out=response.getWriter();
			
			if(userType<=0){//用户类型小于等于0都是普通用户，没有专栏
				out.println("<script type='text/javascript'>alert('您没有专栏')</script>");
				out.println("<script type='text/javascript'>history.go(-1)</script>");
			}
			else{			
				int userId = user.getUserId();
		
				// 调取用户的专栏，原则上一个用户没有多个,这里暂且做一个用户名、密码对一个专栏处理
				List<Post> posts = (List<Post>) jdbc.privatePosts(userId);				
				if(posts.size()==0){//没有
					out.println("<script type='text/javascript'>alert('you have no private Posts')</script>");
					out.println("<script type='text/javascript'>history.go(-1)</script>");
				} 
				else if(posts.size()==1){//进入专栏
					Post post=posts.get(0);
					int postId=post.getPostId();
					new SearchAboutPost().updateVisitors(postId);//更新访问量
				    Unit unit=searchAboutPost.unitOfId(post.getUnitId());//获取粘贴栏对应单位信息
				    List<PrivateAdType> adTypes=searchAboutPost.privateAdTypes(postId);//返回专栏对应广告类别
				    int adTypeId=Integer.parseInt(request.getParameter("adTypeId"));//获取广告Id
				    List<PrivateAd> ads=new ArrayList<PrivateAd>();
				    if(adTypeId==0){//adTypeId==0则返回所有广告
						//根据粘贴栏id返回 广告信息,每次取Configuration中指定的picNumOfEveryLoading
						ads=(List<PrivateAd>)searchAboutPost.adsOfPrivatePost(postId,0,Configuration.PICNumOFEVERYLOADING);				
					}
					else{//如果adTypeId不为0则返回对应类别的广告
						ads=(List<PrivateAd>)searchAboutPost.adsOfPrivatePost(postId,adTypeId,0,Configuration.PICNumOFEVERYLOADING);					
					}
				     
					System.out.println("adTypes.size()"+adTypes.size());
					request.setAttribute("adTypes",adTypes);
				    request.setAttribute("ads",ads);
				    request.setAttribute("unitName",unit.getUnitName()); 
				    request.setAttribute("post",post); 
				    request.setAttribute("adTypeId",adTypeId);
				    request.setAttribute("myPost","true");//标志是自己的专栏
				    request.getRequestDispatcher("privatePost.jsp").forward(request,response);
				}
				else{//一个人对多个专栏的情况
					out.println("<script type='text/javascript'>alert('一个用户不能有多个专栏')</script>");
					out.println("<script type='text/javascript'>history.go(-1)</script>");
					System.out.println("一个用户不能有多个专栏");
				}
			}
		}
	}
	
	//动态加载某个专栏指定类别的指定范围广告，需传参数:postId、adTypeId、begin、number
	public void adsOfPrivatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;utf-8");
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		PrintWriter out=response.getWriter();		 
		if(null==request.getParameter("postId")||null==request.getParameter("adTypeId")||null==request.getParameter("begin")||null==request.getParameter("number")){
			System.out.println("您尚未选择任何粘贴栏"); 
			response.sendRedirect("index.jsp");
		}
		else{				
			int adTypeId=Integer.parseInt(request.getParameter("adTypeId"));
			int postId=Integer.parseInt(request.getParameter("postId"));
			int begin=Integer.parseInt(request.getParameter("begin"));//从begin开始加载
			int number=Integer.parseInt(request.getParameter("number"));//加载广告个数
			//System.out.println(adTypeId+"-"+postId+"-"+begin+"-"+number);
			//查找粘贴栏下的所有类别
			List<PrivateAd> ads=new ArrayList<PrivateAd>();
			if(adTypeId==0){//adTypeId==0则返回所有广告
			//根据粘贴栏id返回 广告信息,每次取Configuration中指定的picNumOfEveryLoading
				ads=(List<PrivateAd>)searchAboutPost.adsOfPrivatePost(postId,begin,number);					
			}
			else{//如果adTypeId不为0则返回对应类别的广告
				ads=(List<PrivateAd>)searchAboutPost.adsOfPrivatePost(postId,adTypeId,begin,number);					
			}
			//System.out.println("返回广告个数："+ads.size());				
 			Gson gson=new Gson(); 			
 			String result=gson.toJson(ads);
 			//System.out.println(result);
 			out.println(result);
 			out.flush();
 			
		}		
	}
	
	//保存更换后的专栏广告顺序，传入的参数：adId
	public void saveOrderOfAds(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		if (null == request.getSession().getAttribute("user")||null == request.getParameter("adId")) {// 未登录用户不能进入专栏,没有传入adId不能进入
			System.out.println("尚未登录");
			response.sendRedirect("login.jsp");
		}
		else {			 
			//由adId反应当前广告的顺序
			String adId=request.getParameter("adId");
			System.out.println(adId);
			if(adId.endsWith("_")){
				adId=adId.substring(0,adId.length()-1);
			}
			String[] adIds=adId.split("_");
			String upLoadTime = new GetCurrentTime().currentTime();// 获取当前时间，作为新的广告排序值基准时间
			//此处默认都是没有缴费，如果实行缴费机制，得修改生成sortValue的方法，要考虑到缴费有效时间等
			long sortValue=new GetSortValue().sortValue(0,upLoadTime);
			for(int i=adIds.length-1;i>=0;i--){//sortValue从后往前加,每次加一
				int id=Integer.parseInt(adIds[i]);
				System.out.println("adId:"+id);
				searchAboutPost.updateSortValueOfPrivateAds(id,sortValue++);
				System.out.println("sortValue:"+sortValue);
			}
			out.println("<script type='text/javascript'>alert('success')</script>");
			out.println("<script type='text/javascript'>location.replace(document.referrer);opener.location.reload();</script>");
		}
	}
	 
}
