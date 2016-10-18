package logicalConduct;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
 
//处理关于粘贴栏的各种逻辑
public class PostLogical extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String functionName=request.getParameter("functionName");
		FunctionsForPostLogical functions=new FunctionsForPostLogical();
		System.out.println("进入src/logicalConduct/PostLogical,要执行的方法为："+functionName);		 
		if("unitsOfType".equals(functionName)){//点击某个类别后显示属于该类别的单位和粘贴栏
			functions.unitsOfType(request,response);			
		}
		else if("enterPost".equals(functionName)){//点击进入某个粘贴栏
			functions.enterPost(request,response);
			
		}
		else if("adsOfPost".equals(functionName)){//动态加载某个粘贴栏指定类别的指定范围广告
			functions.adsOfPost(request,response);			
		}
		
		else if("picsOfAd".equals(functionName)){//点击首图后返回广告中所有图片
			functions.picsOfAd(request,response);			
		}
		//在上传图片时点击某个类别后显示属于该类别的非空单位（即包含非专栏的单位）
		else if("unitsWithPublicPost".equals(functionName)){
			functions.unitsWithPublicPost(request,response);			
		}
		//在上传图片时点击某个单位后显示所有非专栏
		else if("publicPostsOfUnit".equals(functionName)){ 
			functions.publicPostsOfUnit(request,response);		
		}
		//根据查找字段寻找包含此字段的所有粘贴栏
		else if("searchPosts".equals(functionName)){ 
			functions.searchPosts(request,response);
		}
		//根据查找字段寻找包含此字段的所有粘贴栏
		else if("enterUnit".equals(functionName)){ 
			functions.enterUnit(request,response);
		}	
		//上传图片
		else if("upLoad".equals(functionName)){ 
			functions.upLoad(request,response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	}

}
