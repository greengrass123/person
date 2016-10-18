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

/**
 * Servlet Filter implementation class EncodingFilter
 */
 //指定一个处理字符集的过滤器，使rquest和response的编码方式为utf-8
public class EncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		System.out.println("EncodingFilter结束工作");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//处理请求字符集
		 HttpServletRequest req=(HttpServletRequest)request;
		  HttpServletResponse res=(HttpServletResponse)response;
		//  String req_uri=req.getRequestURI();
		 // System.out.println("打印请求网址："+req_uri);
		 // req.setCharacterEncoding("utf-8");      
		 //System.out.println("执行EncodingFilter,request.setCharacterEncoding");
	     //传递给下一个过滤器 
	     chain.doFilter(request,response);  	  
	      //处理响应字符集  
	     //res.setCharacterEncoding("utf-8");  
	     //System.out.println("执行EncodingFilter,response.setCharacterEncoding");
		 
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("EncodingFilter初始化");
	}

}
