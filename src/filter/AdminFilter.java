package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminFilter implements Filter{
	 public AdminFilter() {
	        // TODO Auto-generated constructor stub
	    }

		/**
		 * @see Filter#destroy()
		 */
		public void destroy() {
			System.out.println("AdminFilter结束工作");
		}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 
		  HttpServletRequest req=(HttpServletRequest)request;
		  HttpServletResponse res=(HttpServletResponse)response;
		  System.out.println("name=:"+request.getParameter("name")+"  password="+request.getParameter("password"));
		  
		  if(!req.getParameter("info").equals("adminlogin")){//如果不是登录页面，则都需判断是否用户名密码正确，否则不能登入
			  //如果没有登录名或密码则返回登录界面
			  System.out.println("t1");
			  System.out.println("----2name=:"+req.getSession().getAttribute("name")+"  password="+req.getSession().getAttribute("password"));
			 if(null==req.getSession().getAttribute("adminInfo")){
				 System.out.println("session没有管理员信息");
				  res.sendRedirect("adminLogin.jsp");
			 }
		  } 
		 System.out.println("t3");
	     chain.doFilter(request,response);  
	     
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("AdminFilter开始工作");
	}
}
