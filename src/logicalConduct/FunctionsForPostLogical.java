package logicalConduct;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.OperationData;
import jdbc.SearchAboutPost;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tool.Check;
import tool.DisposePic;
import tool.GetCurrentTime;
import tool.GetSortValue;
import allClasses.Ad;
import allClasses.AdType;
import allClasses.Pic;
import allClasses.Post;
import allClasses.PrivateAd;
import allClasses.PrivateAdType;
import allClasses.PrivatePic;
import allClasses.Unit;
import allClasses.User;
import allClasses.VisitorLog;

import com.google.gson.Gson;

import configurations.Configuration;

public class FunctionsForPostLogical {
	private  SearchAboutPost searchFromDB=new SearchAboutPost();
	 
	//查找特定单位类别下所有的单位及粘贴栏,需传参数：unitTypeId
	public void unitsOfType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;utf-8");
		response.setHeader("pragma","no-cache");

		//System.out.println("执行:src/logicalConduct/FunctionsForPostLogical/unitsOfType");	 
		if(null==request.getParameter("unitTypeId")){//如果传来的参数信息不全，跳回主页
			System.out.println("您尚未选中任何单位类别，请在主页选择您要查看的单位类别"); 
			response.sendRedirect("index.jsp");
		}
		else{
			int unitTypeId=Integer.parseInt(request.getParameter("unitTypeId"));	
			String unitTypeName=request.getParameter("unitTypeName");
			//unitTypeName=new String(unitTypeName.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println("unitTypeId="+unitTypeId+"unitTypeName="+unitTypeName);
			List<Unit> units=new ArrayList<Unit>();
			units=searchFromDB.unitsOfType(unitTypeId);//获取用户列表
			int unitId=0;
			String unitName="";			
			List<Map<String,Object>> unitsAndPosts=new ArrayList<Map<String,Object>>();
			for(Unit u:units){
				//获取所查询单位的单位Id和单位名称
				Unit unit=u;
				unitId=unit.getUnitId();
				unitName=unit.getUnitName();	 
				String unitInfo=unitId+"_"+unitName;
				Map<String,Object> unitAndPosts=new HashMap<String,Object>();				 
				System.out.println("unitInfo:"+unitInfo);
				//获取该unitId对应的所有粘贴栏
				List<Post> posts=searchFromDB.postsOfUnit(unitId); 
				unitAndPosts.put(unitInfo,posts);	
				unitsAndPosts.add(unitAndPosts);				
			}	
			request.setAttribute("unitTypeName",unitTypeName);
			request.setAttribute("unitsAndPosts",unitsAndPosts);
			request.getRequestDispatcher("unitsAndPostsOfType.jsp").forward(request,response);
		}
		
	}

	//进入某个粘贴栏(包括专栏与非专栏),进入需传参数:postInfo、adTypeId
	public void enterPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	 		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if(null==request.getParameter("postId")||null==request.getParameter("adTypeId")){
			//System.out.println("您尚未选择任何粘贴栏"); 
			response.sendRedirect("index.jsp");
		}
		else{
			//Get the IP of client 
			String visitorip=null;
			if (request.getHeader("x-forwarded-for") == null) {   
				visitorip= request.getRemoteAddr();   
				  }  
			else
			{
				visitorip=request.getHeader("x-forwarded-for");  
	        }
			System.out.println("visitorip===="+visitorip);
			int adTypeId=Integer.parseInt(request.getParameter("adTypeId")); 
			int postId=Integer.parseInt(request.getParameter("postId"));
			searchFromDB.updateVisitors(postId);//进入之前先更新访问人数，加1
			Post post=searchFromDB.postOfId(postId);//获取粘贴栏信息
		    Unit unit=searchFromDB.unitOfId(post.getUnitId());//获取粘贴栏对应单位信息
			System.out.println("unitName="+unit.getUnitName()+"postName="+post.getPostName());	
			
			VisitorLog vl=new VisitorLog();
		    //vl.setVisitorid();
			vl.setVisitorip(visitorip);
			vl.setVisitorpostname(post.getPostName());
			OperationData od=new OperationData();
			System.out.println("addVisitorLog......");
			od.addVisitorLog(vl);
			System.out.println(postId+" ");
			//查找粘贴栏下的所有类别
			if(post.getUserId()<=0){//如果不是专栏
				List<AdType> adTypes=searchFromDB.adTypes();//返回所有普通粘贴栏类别		
				System.out.println("adTypes.size()"+adTypes.size());
				request.setAttribute("adTypes",adTypes);
				List<Ad> ads=new ArrayList<Ad>();
				if(adTypeId==0){//adTypeId==0则返回所有广告
					//根据粘贴栏id返回 广告信息,每次取Configuration中指定的picNumOfEveryLoading
					ads=(List<Ad>)searchFromDB.adsOfPost(postId,0,Configuration.PICNumOFEVERYLOADING);				
				}
				else{//如果adTypeId不为0则返回对应类别的广告
					ads=(List<Ad>)searchFromDB.adsOfPost(postId,adTypeId,0,Configuration.PICNumOFEVERYLOADING);					
				}
				//System.out.println("粘贴栏下广告个数："+ads.size());					
				request.setAttribute("post",post);
				request.setAttribute("unitName",unit.getUnitName());
				request.setAttribute("adTypeId",adTypeId); 			
				request.setAttribute("ads",ads);
				request.getRequestDispatcher("post.jsp").forward(request,response);	
			}
			else{//如果是专栏
				List<PrivateAdType> adTypes=searchFromDB.privateAdTypes(postId);//返回专栏对应广告类别
				request.setAttribute("adTypes",adTypes); 
				List<PrivateAd> ads = new ArrayList<PrivateAd>();
				if (adTypeId == 0) {// adTypeId==0则返回所有广告
					// 根据粘贴栏id返回 广告信息,每次取Configuration中指定的picNumOfEveryLoading
					ads = (List<PrivateAd>) searchFromDB.adsOfPrivatePost(
							postId, 0,
							Configuration.PICNumOFEVERYLOADING);
				} else {// 如果adTypeId不为0则返回对应类别的广告
					ads = (List<PrivateAd>) searchFromDB.adsOfPrivatePost(
							postId, adTypeId, 0,
							Configuration.PICNumOFEVERYLOADING);
				}

				System.out.println("adTypes.size()" + adTypes.size());
				request.setAttribute("adTypes", adTypes);
				request.setAttribute("ads", ads);
				request.setAttribute("unitName", unit.getUnitName());
				request.setAttribute("post", post);
				request.setAttribute("adTypeId", adTypeId);
				request.setAttribute("myPost","false");//标志不是自己的专栏
				request.getRequestDispatcher("privatePost.jsp").forward(request, response);
			}
			
		}		
	}

	//动态加载某个粘贴栏指定类别的指定范围广告，需传参数:postId、adTypeId、begin、number
	public void adsOfPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		 
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
			List<Ad> ads=new ArrayList<Ad>();
			if(adTypeId==0){//adTypeId==0则返回所有广告
			//根据粘贴栏id返回 广告信息,每次取Configuration中指定的picNumOfEveryLoading
				ads=(List<Ad>)searchFromDB.adsOfPost(postId,begin,number);					
			}
			else{//如果adTypeId不为0则返回对应类别的广告
				ads=(List<Ad>)searchFromDB.adsOfPost(postId,adTypeId,begin,number);					
			}
			//System.out.println("返回广告个数："+ads.size());				
 			Gson gson=new Gson(); 			
 			String result=gson.toJson(ads);
 			//System.out.println(result);
 			out.println(result);
 			out.flush();
 			
		}		
	}

	//根据广告id返回广告下所有已审核图片<后台检测时应该是按照一个广告审核，此处先假设是单个图片>,传入参数：adId,postId
	public void picsOfAd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		 	 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if(null==request.getParameter("adId")||null==request.getParameter("postId")){
			System.out.println("您尚未选择任何图片"); 
			response.sendRedirect("index.jsp");
		}
		else{				
			int adId=Integer.parseInt(request.getParameter("adId")); 
			int postId=Integer.parseInt(request.getParameter("postId"));//获取PostId
			Post post=searchFromDB.postOfId(postId);
			List pics=new ArrayList();
			//返回指定ID下所有图片 	
			if(post.getUserId()>0){//如果是专栏
				pics=searchFromDB.picsOfPrivateAd(adId);
				String sql="update privatead set click=click+1 where adId="+ adId;
				searchFromDB.updateClick(sql);
			}
			//System.out.println("ad:"+adId);
			//如果不是专栏	
			else{
				pics=searchFromDB.picsOfAd(adId);
				String sql="update ad set click=click+1 where adId="+ adId;
                searchFromDB.updateClick(sql);
			}
			request.setAttribute("pics",pics);
 			//System.out.println("pics.size():"+pics.size());
 			request.getRequestDispatcher("picsOfAd.jsp").forward(request,response);	
		}	
		
	}
	
	//在上传图片时点击某个类别后显示属于该类别的非空单位（即包含非专栏的单位）,需传入参数：unitTypeId
	public void unitsWithPublicPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");	 
		response.setContentType("application/json;utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		System.out.println("执行:src/logicalConduct/FunctionsForPostLogical/unitsWithPublicPost");
		if (null==request.getParameter("unitTypeId")) {// 如果传来的参数信息不全，跳回主页
			System.out.println("您尚未选中任何单位类别，请在主页选择您要查看的单位类别");
			response.sendRedirect("index.jsp");
		} else {
			int unitTypeId = Integer.parseInt(request.getParameter("unitTypeId"));			
			List<Unit> units = new ArrayList<Unit>();
			units = searchFromDB.unitsWithPublicPost(unitTypeId);// 获取用户列表			
			// 将List转化成json对象传给显示粘贴栏的页面
			Gson gson = new Gson();
			String result = gson.toJson(units);
			System.out.println("result:" + result);
			out.println(result);
			out.flush();
		}

	}
	
	//选择某个单位下的所有非专栏，需传入参数unitId
	public void publicPostsOfUnit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{	
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		System.out.println("执行:src/logicalConduct/FunctionsForPostLogical/publicPostOfUnit");
		if (null == request.getParameter("unitId")) {// 如果传来的参数信息不全，跳回主页
			System.out.println("您尚未选中任何单位，请在主页选择您要查看的单位类别");
			response.sendRedirect("index.jsp");
		} else {
			int unitId = Integer.parseInt(request.getParameter("unitId"));
			List<Post> publicPosts= searchFromDB.publicPostsOfUnit(unitId);// 获取指定单位下所有非专栏		
			Gson gson = new Gson();
			String result = gson.toJson(publicPosts);
			System.out.println("result:" + result);
			out.println(result);
			out.flush();
		}
	}
	
	//选择所有包含指定字段的单位和粘贴栏
	public void searchPosts(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); 
		System.out.println("执行:src/logicalConduct/FunctionsForPostLogical/searchPosts");
		if (null==request.getParameter("searchText")) {// 如果传来的参数信息不全，跳回主页
			System.out.println("您未输入任何搜索字段");
			response.sendRedirect("index.jsp");
		} else {
			String searchText = request.getParameter("searchText");
			//System.out.println("searchText :"+searchText );
			searchText=new String(searchText.getBytes("iso-8859-1"),"utf-8");
			//System.out.println(searchText);
			List<Map<String,List<Post>>> unitsAndPosts=searchFromDB.postsContaintText(searchText);//所有包含searchText字段的单位跟粘贴栏
			request.setAttribute("unitsAndPosts",unitsAndPosts);
			request.setAttribute("searchText",searchText);
			request.getRequestDispatcher("searchedPosts.jsp").forward(request,response);
		}

	}
	
	//进入某个单位,查看该单位下所有非专栏粘贴栏中广告,进入需传参数:unitInfo、adTypeId
	public void enterUnit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{	
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (null==request.getParameter("unitInfo")|| null==request.getParameter("adTypeId")) {
			System.out.println("您尚未选择任何粘贴栏");
			response.sendRedirect("index.jsp");
		} 
		else {
			String unitInfo = request.getParameter("unitInfo");  
			int adTypeId = Integer.parseInt(request.getParameter("adTypeId")); 
			//unitInfo = new String(unitInfo.getBytes("iso-8859-1"), "utf-8");
			System.out.println("unitInfo=" + unitInfo+ "adTypeId=" + adTypeId);
			String[] unitInfos = unitInfo.split("_");
			int unitId = Integer.parseInt(unitInfos[0].trim());
			String unitName = unitInfos[1].trim(); 		 
			System.out.println("unitId:"+unitId+"unitName:"+unitName);
			 
		}
	}
	
	//处理上传事件,获取上传粘贴栏（可能多个）、上传广告类别、广告信息
	public void upLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter(); 
		// 为解析类提供配置信息
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 创建解析类的实例
		ServletFileUpload sfu = new ServletFileUpload(factory);
		long fileMax = 1024 * 1024 * 8; // 设置单个上传文件的最大限度，byte为单位,2M
		long filesMax = 1024 * 1024 * 40;// 设置总上传文件的最大限度，10M
		//sfu.setFileSizeMax(fileMax);
		//sfu.setSizeMax(filesMax);
		List<FileItem> fileItems = new ArrayList<FileItem>();// 接收文件域
	
		String[] postIds=null;// 可能一次上传多个粘贴栏
		int adTypeId=0;
		String remark="";//备注
		boolean isPrivateAd=false;//标示是否为专栏广告
		// 每个表单域中数据会封装到一个对应的FileItem对象上
		try {
			List<FileItem> items = sfu.parseRequest(request);			
			Iterator<FileItem> it = items.iterator();
			System.out.println(items.size());
			// 遍历
			while (it.hasNext()) {
				FileItem item = it.next();
				String fileName = item.getFieldName();// 获取表单名
				if (item.isFormField()) {// 判断是否为表单域					
					String value = item.getString("utf-8");// 获取值
					System.out.println("fileName:" + fileName);
					if ("postId".equals(fileName)) {
						String postId=value;
						System.out.println("postId:"+postId);
						if(postId.endsWith("_")){//当专栏用户选择多个粘贴栏时最后一位为——,应该去掉
							postId=postId.substring(0,postId.length()-1);
						}					
						System.out.println("postId:"+postId);
						postIds=postId.split("_");//获取所有的粘贴栏id
					}
					if ("adTypeId".equals(fileName)) {
						adTypeId = Integer.parseInt(value);
					}
					if ("remark".equals(fileName)) {
						remark = value;
					}
					if ("privatePost".equals(fileName)) {
						isPrivateAd = new Boolean(value);//标志是否为专栏广告
					}

				} else {// 文件域
					if (item.getSize() > fileMax) {// 如果文件过大则返回上传页面，提示过大
						System.out.println("文件过大");
						out.println("<script type='text/javascript'>alert('文件过大')</script>");
						out.println("<script type='text/javascript'>history.go(-1)</script>");
					}
					else{
						String fileType = fileName.substring(fileName.lastIndexOf(".")+1);// 获取文件类型
						System.out.println("fileType:" + fileType);
						if (new Check().checkFileType(fileType) == false) {// 检查文件类型是否满足条件
							System.out.println("文件类型错误");
							out.println("<script type='text/javascript'>alert('the image is too big文件类型错误')</script>");
							//out.println("<script type='text/javascript'>history.go(-1)</script>");
						}
						else{
							if(item.getSize()>0){
								fileItems.add(item);// 将文件域添加到列表
							}
						}							
					}			
				}
			}
			if(fileItems.size()<=0){//没有图片
				System.out.println("您没有上传任何图片");
				out.println("<script type='text/javascript'>history.go(-1)</script>");	
				out.println("<script type='text/javascript'>alert('您没有上传图片,u haven't upLoad any pic')</script>");						
			}
			else{
				if(postIds==null||adTypeId==0){//如果没有选择粘贴栏，或者广告类别，则不存储广告
					System.out.println("您尚未选择任何粘贴栏或者广告类别");
					out.println("<script type='text/javascript'>alert('您尚未选择任何粘贴栏或者广告类别')</script>");
					out.println("<script type='text/javascript'>history.go(-1)</script>");
				}
				//存储广告到对应的粘贴栏、类别
				else{
					saveAds(postIds,adTypeId,remark,isPrivateAd,fileItems,request,response);
				}				
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	} 

	//广告存储
	public void saveAds(String[] postIds,int adTypeId,String remark,boolean isPrivateAd,List<FileItem> fileItems,HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException{ 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter(); 
		SearchAboutPost searchAboutPost=new SearchAboutPost();
		User user=null;
		if(null!=request.getSession().getAttribute("user")){//获取用户信息
			user=(User)request.getSession().getAttribute("user");
		}	
		System.out.println("isPrivateAd:"+request.getParameter("privatePost"));
		if((user==null||user.getUserType()<=0)&&postIds.length>1){//如果不是专栏用户但是选择了多个粘贴栏
			System.out.println("您没有一次选择多个粘贴栏的权利");
			out.println("<script type='text/javascript'>alert('您没有一次选择多个粘贴栏的权利')</script>");
			out.println("<script type='text/javascript'>history.go(-1)</script>");
		}
		else{
			/*
			 * 
			 * 先处理首图，压缩，分为专栏用户与非专栏用户
	         *如果是专栏用户，可以上传到多个粘贴栏，广告加水印表示为专栏用户，checked=1；不是专栏用户，上传到一个粘贴栏，checked=0
	         *然后再将所有图片存到数据库
	         *用privatePost标示用户是不是上传到自己专栏，如果是的话，则将广告存储到专栏广告表
			 * 
			 */
			String compressPath= request.getSession().getServletContext().getRealPath("/firstPics").replace("/", "\\");//首图压缩后存放路径
			String path = request.getSession().getServletContext().getRealPath("/photoes").replace("/", "\\");//图片存储路径
			System.out.println("/photoes所在真实路径path:" + path);	
			System.out.println("/firstPics所在真实路径compressPath:"+compressPath);
			File photoesDir = new File(path);
			File compressDir = new File(compressPath);
			if (!photoesDir.exists()) {//如果不存在此路径则创建
				photoesDir.mkdir();
			}
			if (!compressDir.exists()) {//如果不存在此路径则创建
				compressDir.mkdir();
			}
		    FileItem firstPic=fileItems.get(0);//获取首图
			String firstPicName=firstPic.getName();
			String fileType = firstPicName.substring(firstPicName.lastIndexOf(".")+1);// 获取文件类型
			String firstPicPath=System.currentTimeMillis() + "."+ fileType;//首图存储路径
			//System.out.println("由当前时间毫秒数与后缀确定的firstPicPath:"+firstPicPath);	
			String firstPicCompressPath= System.currentTimeMillis() + "."+ fileType;//首图压缩后的存储路径 
			//System.out.println("由当前时间毫秒数与后缀确定的firstPicCompressPath:"+firstPicCompressPath);
			//存储文件	
			File file=new File(path+"\\"+firstPicPath);	
			//System.out.println("首图存储路径："+path+"\\"+firstPicPath);
			try {
				firstPic.write(file);//将图片输出到对应文件夹下
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("输出首图失败");
				e1.printStackTrace();
			}
			//压缩首图,并且存储在对应路径
			new DisposePic().compress(path+"\\"+firstPicPath,compressPath+"\\"+firstPicCompressPath,250,250,0.8F);
			//给压缩图加上标题
			new DisposePic().createMark(compressPath+"\\"+firstPicCompressPath,compressPath+"\\"+firstPicCompressPath,remark);
			String upLoadTime = new GetCurrentTime().currentTime();// 获取当前时间，作为广告的上传时间
			//将压缩后首图存储在ad表中		
			int money=0;
			long sortValue=new GetSortValue().sortValue(money,upLoadTime);//排序值
			int userId=0;
			int checked=1;//是否审核，专栏用户默认已审核	
			if(user==null){
				userId=-1;
				checked=0;
			}
			else{
				userId=user.getUserId();
				if(user.getUserType()<=0){//普通注册用户
					checked=0;
				}
			}

			int adId;
			String firstPicAddr = "firstPics/" + firstPicPath;
			// firstPicAddr=firstPicAddr.replace("/", "\\");
			// System.out.println("数据库所存首图地址："+firstPicAddr);
			// 广告压缩后的宽高为250
			if (isPrivateAd == true) {// 如果是专栏广告，广告存放在专栏广告表中,广告只会上传到一个粘贴栏-自己专栏 
				adId=searchAboutPost.maxPrivateAdId()+ 1;// 广告id为当前最大id+1
				PrivateAd ad = new PrivateAd(adId, adTypeId, upLoadTime,
						userId,Integer.parseInt(postIds[0]), firstPicAddr, money, sortValue, remark,
						250, 250,1,0);
				searchAboutPost.savePrivateAd(ad);
				System.out.println("存储专栏广告");
			} 
			else {//可能要上传到多个粘贴栏
				adId=searchAboutPost.maxAdId()+ 10;// 广告id为当前最大id+10，加10以防并发时会冲突
				for(int i=0;i<postIds.length;i++){		
					int id=adId+i;//当前广告的id
					Ad ad = new Ad(id, adTypeId, upLoadTime, userId, Integer.parseInt(postIds[i]),
							firstPicAddr, money, sortValue, checked, remark, 250,
							250,1,0);
					searchAboutPost.saveAd(ad);					 
				}				
			}
			System.out.println("存储广告成功");
			for (int j = 0; j < fileItems.size(); j++) {// 存储该广告下所有图片
				String filedir;// 图片路径
				FileItem fileItem = fileItems.get(j);
				if (j == 0) {// 如果是首图，则为已经取出的地址
					filedir = firstPicPath;
				} else {
					String fileName = fileItem.getName();
					fileType = fileName
							.substring(fileName.lastIndexOf(".") + 1);// 获取文件类型
					filedir = System.currentTimeMillis() + "." + fileType;// 文件存储路径
					System.out.println("根据当前毫秒数与类型确定图片后缀：" + filedir);
				}

				file = new File(path + "\\" + filedir);// 存储文件
				// System.out.println("存储图片路径："+path+"\\"+filedir);
				int width = 0;
				int height = 0;
				try {
					if (!file.exists()) {// 如果文件不存在，则存储文件到对应路径
						fileItem.write(file);
					}
					// 读取图片的宽度与高度
					java.io.File img = new java.io.File(path + "\\" + filedir);
					BufferedImage image = null;
					image = ImageIO.read(img);
					width = image.getWidth();
					height = image.getHeight();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String picAddr = "photoes/" + filedir;
				if (isPrivateAd == true) {// 如果是专栏广告，广告下所有图片存放在专栏图片表中
					PrivatePic pic = new PrivatePic(0, picAddr, width, height,
							adId);
					searchAboutPost.savePrivatePic(pic);// 存储图片
					System.out.println("存储专栏广告");
				} else {
					for(int i=0;i<postIds.length;i++){//如果有多个粘贴栏，则在每个粘贴栏下申明一个对应的adId，而图片需要对应每一个广告
						int id=adId+i;//当前广告id
						Pic pic = new Pic(0, picAddr, width, height, checked, id);
						searchAboutPost.savePic(pic);// 存储图片
					}					
				}
			}
			 		
		}	
		response.sendRedirect("upLoad3.jsp");//成功则跳转到成功页面		
		 		
	}
}
