package com.wy.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.AdType;
import allClasses.PrivateAdType;

import jdbc.OperationData;
import jdbc.SearchAboutPost;

 

public class TypeServlet extends  HttpServlet{

	public String info=null;
	
	public void doGet(HttpServletRequest request ,HttpServletResponse response )
	throws ServletException ,IOException
	{
		System.out.println("TypeServlet......."); 
		info=request.getParameter("info");
		  if(info.equals("type"))
		  {
			  this.getType(request,response);
		  }
}
	public void getType(HttpServletRequest  request,HttpServletResponse response)
			throws ServletException, IOException
	{
		String unitname;
		String pastename;
		String requestcode;
		int count;
		response.setContentType("text/xml;charset=utf-8");
		request.setCharacterEncoding("utf-8");
	    unitname= request.getParameter("unit");
	    unitname=new String(unitname.getBytes("iso-8859-1"),"UTF-8");
	    pastename= request.getParameter("paste");
	   pastename=new String(pastename.getBytes("iso-8859-1"),"UTF-8");
	    System.out.println("pastename---->"+pastename);
	    
	    requestcode= request.getParameter("requestcode");
	    //count= request.getParameter("count");
	    OperationData od=new OperationData();
	    ArrayList<String> adTypeList=new ArrayList();
		ArrayList<AdType> adTypeListTem=new ArrayList();
		adTypeList.add("所有广告");
		if(!od.is_paste_special(pastename))
		//if(unitname!=null)	
		{
			//非专栏
			
			adTypeListTem=(ArrayList<AdType>) od.query_adType();
			System.out.println("adTypeListTep="+adTypeListTem.get(0).getAdTypeName() );
			for(int i=0;i<adTypeListTem.size();i++)
			{
				//adTypeList.set(i, adTypeListTem.get(i).getTypeName());
				adTypeList.add(adTypeListTem.get(i).getAdTypeName());
			}
			System.out.println("unitname="+unitname);
			count=adTypeList.size();
			System.out.println("adTypeList="+adTypeList.get(0) );
	        request.setAttribute("adTypeList", adTypeList );
	        request.setAttribute("unitname", unitname );
	        request.setAttribute("pastename", pastename );
	        request.setAttribute("requestcode", requestcode );
	        request.setAttribute("count", count );
	        System.out.println("pastename="+pastename);
	        request.getRequestDispatcher("/typeXML.jsp").forward(request, response);
	         
	        System.out.println("getType.........");
						
		}else
		{
			//专栏
		
			 
			System.out.println("专栏的类别.....");
			ArrayList<String> adTypeList1=new ArrayList();
			ArrayList<AdType> adTypeListTem1=new ArrayList();
			adTypeListTem1=(ArrayList<AdType>) od.query_adType();
			System.out.println("adTypeListTep="+adTypeListTem1.get(0).getAdTypeName()  );
			for(int i=0;i<adTypeListTem1.size();i++)
			{
				//adTypeList.set(i, adTypeListTem.get(i).getTypeName());
				adTypeList1.add(adTypeListTem1.get(i).getAdTypeName());
			}
			SearchAboutPost sap=new SearchAboutPost();
			int postId=od.query_pasteId(pastename);
			System.out.println("postId="+postId);
			List<PrivateAdType> spcType=sap.privateAdTypes(postId);
			
			//String[]sp=spcType.toString();
			//String[]spcType=od.specialPaste_adType(pastename);
			for(int i=0;i<spcType.size();i++)
			{
			   adTypeList1.add(spcType.get(i).getAdTypeName());
			}
			System.out.println("unitname="+unitname);
			count=adTypeList1.size();
			//request.setAttribute("videos", videos);
			System.out.println("adTypeList="+adTypeList1.get(0) );
	        request.setAttribute("adTypeList", adTypeList1 );
	        request.setAttribute("unitname", unitname );
	        request.setAttribute("pastename", pastename );
	        request.setAttribute("requestcode", requestcode );
	        request.setAttribute("count", count );
	        System.out.println("pastename="+pastename);
	        request.getRequestDispatcher("/typeXML.jsp").forward(request, response);
	         
	        System.out.println("getType.........");
	        
			 
		}
		
		
		
		
		
	}
public void doPost(HttpServletRequest request  ,HttpServletResponse response)
  throws ServletException,IOException
{
	 this.doGet(request, response);
}
	
	
	
	
	
	
	
	
	
	
	
	
}
