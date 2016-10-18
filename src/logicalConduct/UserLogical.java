package logicalConduct;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserLogical
 */ 
//用户相关操作
public class UserLogical extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String functionName=request.getParameter("functionName");
		 FunctionsForUserLogical functions=new FunctionsForUserLogical();
		 System.out.println("进入UserLogical,要执行的方法为："+functionName);
		 if("register".equals(functionName)){//注册
			 functions.register(request,response);
		 }
		 else if("login".equals(functionName)){//登录
			 functions.login(request,response);
		 }
		 else if("exit".equals(functionName)){// 退出
			 functions.exit(request,response);
		 }
		 else if("myAds".equals(functionName)){//管理用户已发布的广告
			 functions.myAds(request,response);
		 }
		 else if("deleteMyAds".equals(functionName)){//删除用户已发布的广告
			 functions.deleteMyAds(request,response);
		 }	
		 else if("deletePrivateAds".equals(functionName)){//删除专栏广告
			 functions.deletePrivateAds(request,response);
		 }	
		 else if("alterUserInformation".equals(functionName)){//修改用户信息
			 functions.alterUserInformation(request,response);
		 }
		 else if("attention".equals(functionName)){//关注指定粘贴栏
			 functions.attention(request,response);
		 }
		 else if("myAttentions".equals(functionName)){//获取用户所关注的所有粘贴栏
			 functions.myAttentions(request,response);
		 }
		 else if("privatePost".equals(functionName)){//用户进入自己的专栏
			 functions.privatePost(request,response);
		 }		
		 else if("deleteMyAttention".equals(functionName)){//用户删除自己关注的粘贴栏
			 functions. deleteMyAttention(request,response);
		 }
		 else if("deleteAdType".equals(functionName)){//增加广告类别
			 functions.deleteAdType(request,response);
		 }
		 
		 else if("addAdType".equals(functionName)){//删除广告类别
			 functions.addAdType(request,response);
		 }		 
		 else if("saveOrderOfAds".equals(functionName)){//存储更换之后的专栏广告顺序
			 functions.saveOrderOfAds(request,response);
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
