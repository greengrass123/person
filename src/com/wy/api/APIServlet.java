package com.wy.api;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.OperationData;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import allClasses.Ad;
import allClasses.AdType;
import allClasses.Pic;
import allClasses.UnitType;

 

public class APIServlet  extends HttpServlet{
	
	String info;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 info= request.getParameter("info");
	     if(info.equals("type"))//返回粘贴栏的类型
	     {
	    	 this.getAdType(request,response);
	     }
		 if(info.equals("pastelist"))
		 {
			 this.getPasteList(request,response);
		 }
		 if(info.equals("ad"))
		 {
			 this.getAD(request,response);
		 }
	     

    }
	public void getAdType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String unitname;
		String pastename;
		String requestcode;
		int count;
		response.setContentType("text/xml;charset=utf-8");
		request.setCharacterEncoding("utf-8");
	    unitname= request.getParameter("unit");
	    System.out.println("unitname:"+unitname);

	    unitname=URLDecoder.decode(unitname, "utf-8");
	    //unitname=new String(unitname.getBytes("iso-8859-1"),"UTF-8");
	    pastename= request.getParameter("paste");
	    pastename=URLDecoder.decode(unitname, "utf-8");
	   // pastename=new String(pastename.getBytes("iso-8859-1"),"UTF-8");
 	    System.out.println("pastename---->"+pastename);
	    
	    requestcode= request.getParameter("requestcode");
	    OperationData od=new OperationData();
		if(!od.is_paste_special(pastename))
		{
			//非专栏
			ArrayList<String> adTypeList=new ArrayList();
			ArrayList<AdType> adTypeListTem=new ArrayList();
			adTypeListTem=(ArrayList<AdType>) od.query_adType();
			System.out.println("adTypeListTep="+adTypeListTem.get(0).getAdTypeName() );
			adTypeList.add("所有广告");
			for(int i=0;i<adTypeListTem.size();i++)
			{
				 
				adTypeList.add(adTypeListTem.get(i).getAdTypeName());
			}
			System.out.println("unitname="+unitname);
			count=adTypeList.size();
			//request.setAttribute("videos", videos);
			System.out.println("adTypeList="+adTypeList.get(0));
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
			//非专栏
			/*
			System.out.println("专栏的类别.....");
			ArrayList<String> adTypeList=new ArrayList();
			ArrayList<AdType> adTypeListTem=new ArrayList();
			adTypeListTem=(ArrayList<AdType>) od.query_adType();
			System.out.println("adTypeListTep="+adTypeListTem.get(0).getAdTypeName() );
			for(int i=0;i<adTypeListTem.size();i++)
			{
				//adTypeList.set(i, adTypeListTem.get(i).getTypeName());
				adTypeList.add(adTypeListTem.get(i).getAdTypeName());
			}
			String[]spcType=od.specialPaste_adType(pastename);///2014-3-21此处有问题
			for(int i=0;i<spcType.length;i++)
			{
			   adTypeList.add(spcType[i]);
			}
			System.out.println("unitname="+unitname);
			count=adTypeList.size();
			//request.setAttribute("videos", videos);
			System.out.println("adTypeList="+adTypeList.get(0) );
	        request.setAttribute("adTypeList", adTypeList );
	        request.setAttribute("unitname", unitname );
	        request.setAttribute("pastename", pastename );
	        request.setAttribute("requestcode", requestcode );
	        request.setAttribute("count", count );
	        System.out.println("pastename="+pastename);
	        request.getRequestDispatcher("/typeXML.jsp").forward(request, response);
	         
	        System.out.println("getType.........");
	        */
			 
		}
		
		
		
	}
	public void getPasteList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//String requestcode=request.getParameter("requestcode");
		OperationData od=new OperationData();
		
		List<UnitType> typeNameList=od.publicPasteType();
		int  typecount=typeNameList.size();
		 
		
		 Document docement=DocumentHelper.createDocument();  
		    //设置XML文档的元素  
	     Element rootElement=docement.addElement("xml_api_reply");  
 
		 Element classElement=rootElement.addElement("class"); 
		 classElement.addAttribute("count",""+typecount);  
		 for (int i=0;i<typecount;i++)
		 {
		    Element classnameElement=classElement.addElement("class_name");  
		    classnameElement.addAttribute("data",typeNameList.get(i).getUnitTypeName()); 
		    String[]unitList=od.query_unitName(typeNameList.get(i).getUnitTypeName());
	        int unitcount=unitList.length;
		    classnameElement.addAttribute("count",""+unitcount);  
		  
		    for (int j=0;j<unitcount;j++) 
		    {
			   String []postStr=od.query_pasteName(unitList[j]);
			   
			   int postcount=postStr.length;
			   System.out.println(" changdu is "+postcount);
			   Element unitElement=classnameElement.addElement("unit");
			   unitElement.addAttribute("data",""+ unitList[j]);
			   unitElement.addAttribute("count",""+postcount); 
			   for (int k=0;k<postcount;k++)
			    {
				 Element postElement=unitElement.addElement("post");    
				 postElement.addAttribute("data",postStr[k]); 
				 postElement.addAttribute("isSpecial", ""+od.is_paste_special(postStr[k])); 
				 postElement.addAttribute("post_id",""+od.query_pasteId(postStr[k])); //给出粘贴栏ID
			    }
			 
			
		   }
		 }  
		 
		    try {  
		        /** 格式化输出,类型IE浏览一样 */  
		        OutputFormat format = OutputFormat.createPrettyPrint();  
		        /** 指定XML编码 */  
		        format.setEncoding("UTF_8");  
		        File f=new File("/pasteListXML.xml");
		       // if(f.exists())
		        XMLWriter writer=new XMLWriter(new FileWriter(f),format);   
		        writer.write(docement);  
		        writer.close();  
		        System.out.println("------->>>11");
	    
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    }  
		String xmlStr=docement.asXML();
		response.setContentType("text/xml;charset=UTF-8");
		response.getOutputStream().write(xmlStr.getBytes("UTF-8"));
	 
	  //	request.getRequestDispatcher("/pasteListXML.xml").forward(request, response);
	  	System.out.println("getPostList.........");
		
		
	}
	public  void getAD(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		//返回广告
		String unitname;
		String pastename;
		String type;
		String requestcode;
		int  index = 0;
		int  requestcount = 0;
		int  count=0;
		response.setContentType("text/xml;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
	    unitname= request.getParameter("unit");
	    unitname=URLDecoder.decode(unitname, "utf-8");
	 //  unitname=new String(unitname.getBytes("iso-8859-1"),"UTF-8"); 
	    pastename= request.getParameter("paste");
	    pastename=URLDecoder.decode(pastename, "utf-8");
	    //pastename=new String(pastename.getBytes("iso-8859-1"),"UTF-8");
	     
	     ////////////
	    requestcode= request.getParameter("requestcode");
	    type= request.getParameter("type");
	    System.out.println("---->type:"+type);
	   // type=new String(type.getBytes("iso-8859-1"),"UTF-8");
	    if(type != null){
	     type=URLDecoder.decode(type, "utf-8");
	    }
	    System.out.println("unitname="+unitname);
	    System.out.println("pastename="+pastename);
	    System.out.println("type="+type);
	    if(request.getParameter("index")!=null){
	     index= Integer.parseInt(request.getParameter("index"));
	    }
	    if(request.getParameter("requestcount")!=null){
	      requestcount=Integer.parseInt(request.getParameter("requestcount"));
	    }
	    System.out.println("requestcount"+requestcount);
	   System.out.println("index"+index);
	   OperationData od=new OperationData();
	   List<Ad> adList = new ArrayList<Ad>();
	   if(pastename !=null&&type!=null)
	   adList = (List<Ad>)od.queryByAdType_paste(type,pastename);//返回该索引下的所有广告
	   System.out.println("adList.Size()="+adList.size());
	   List<Ad>newAdList=new ArrayList();
	   //index 表示从第index个数据开始,如第0个数
	   //requestcount 表示取requestcount个数
	   //count 表示返回的数目
	   if(index>=adList.size())
	   {  
		  /// index=0;//如果index越界了就从0开始
		     count=0;//如果index越界了，置count 为0
		  
	   }
	   else  
	   {
	       if(index+requestcount-1>=adList.size())//越界检测
	       {
		      count=adList.size()-index;
	        }else
	       {
		     count=requestcount;
	       }
	       for(int i=index;i<index+count;i++)
	       {
		     newAdList.add(adList.get(i));   	
	       }
	   }   
	   Document docement=DocumentHelper.createDocument();  
	    //设置XML文档的元素  
       Element rootElement=docement.addElement("xml_api_reply");  
       Element adElement=rootElement.addElement("ad");  
       adElement.addAttribute("unit", unitname);
       adElement.addAttribute("paste", pastename);
       adElement.addAttribute("type", type);
       adElement.addAttribute("count", ""+count);
       adElement.addAttribute("index", ""+index);
       adElement.addAttribute("requestcount", ""+requestcount);
       adElement.addAttribute("requestcode", requestcode);
	   
       for (int i=0;i<newAdList.size();i++)
       {
    	   
    	   Element sub_adElement=adElement.addElement("sub_ad"); 
    	   sub_adElement.addAttribute("type", ""+od.query_adTypeByadId(newAdList.get(i).getAdId()));
    	   sub_adElement.addAttribute("time",newAdList.get(i).getUpLoadTime());
    	   sub_adElement.addAttribute("id",""+newAdList.get(i).getAdId());
    	   sub_adElement.addAttribute("money",""+newAdList.get(i).getMoney());
    	   sub_adElement.addAttribute("sortid",""+newAdList.get(i).getSortValue());
    	// List<pic>picList=od.query_all_pic(newAdList.get(i).getAdFirstPic());//根据首图查询所有的图片
    	   List<Pic>picList=od.query_pic_byId(newAdList.get(i).getAdId());//根据广告Id查询所有的图片 
    	   //Element picElement=sub_adElement.addElement("pic");
    	   Element colElement=sub_adElement.addElement("collect");
    	   colElement.addAttribute("count",""+picList.size());
    	   for (int j=0;j<picList.size();j++)
    	   {
    		  
    		  // Element picElement=sub_adElement.addElement("pic"); 
    		  // picElement.addAttribute("count",""+picList.size());
    		   Element picElement=colElement.addElement("pic");
    		   String  srcPath=picList.get(j).getPicAddr();
    		   String  thumbPath=srcPath.replace("savefile", "saveSmall");//缩略图路径
    		   String  middlePath=srcPath.replace("savefile", "saveCompress");//缩略图路径
    		   String basePath=request.getSession().getServletContext().getRealPath("/").replace("/", "\\");
    		   File fthumb=new File(basePath+thumbPath.replace("/", "\\"));
    		   File fmiddle=new File(basePath+middlePath.replace("/", "\\"));
    		   if(!fthumb.exists())
    		   { 
    			  thumbPath=srcPath;   
    		   }
    		   if(!fmiddle.exists())
    		   {
    			   middlePath=srcPath;   
    		   }
    		   Element thumbElement =picElement.addElement("thumb"); //缩略图标签
    		   thumbElement.addAttribute("data",thumbPath); 
			   thumbElement.addAttribute("width",""+300);
			   thumbElement.addAttribute("height",""+(int)((picList.get(j).getHeight())*300/picList.get(j).getWidth()));
    		   Element middleElement =picElement.addElement("middle"); //缩略图标签
    		   middleElement.addAttribute("data",middlePath); 
    		   middleElement.addAttribute("width",""+600);
			   middleElement.addAttribute("height",""+(int)((picList.get(j).getHeight())*600/picList.get(j).getWidth()));
    		   
    		   Element srcElement =picElement.addElement("src"); //原图
    		   srcElement.addAttribute("data",srcPath); 
    		   srcElement.addAttribute("width",""+picList.get(j).getWidth());
    		   srcElement.addAttribute("height",""+picList.get(j).getHeight());
    	
    	   }
    	   /****************************首图pic************************************/
    	   Element first_picElement=sub_adElement.addElement("first_pic");//首图
    	   Pic  firstpic=picList.get(0);
    	   String  first_picPath=picList.get(0).getPicAddr();//首图链接
    	   String  first_pic_srcPath=first_picPath;
		   String  first_pic_thumbPath=first_pic_srcPath.replace("savefile", "saveSmall");//缩略图路径
		   String  first_pic_middlePath=first_pic_srcPath.replace("savefile", "saveCompress");//缩略图路径
		   String basePath=request.getSession().getServletContext().getRealPath("/").replace("/", "\\");
		   File fthumb=new File(basePath+first_pic_thumbPath.replace("/", "\\"));
		   File fmiddle=new File(basePath+first_pic_middlePath.replace("/", "\\"));
		   if(!fthumb.exists())
		   {
			   first_pic_thumbPath=first_picPath;   
		   }
		   if(!fmiddle.exists())
		   {
			   first_pic_middlePath=first_picPath;   
		   }
		   Element first_pic_thumbElement =first_picElement.addElement("thumb"); //缩略图标签
		   first_pic_thumbElement.addAttribute("data",first_pic_thumbPath); 
		   first_pic_thumbElement.addAttribute("width",""+300);
		   first_pic_thumbElement.addAttribute("height",""+(int)((firstpic.getHeight())*300/firstpic.getWidth()));
		   Element first_pic_middleElement =first_picElement.addElement("middle"); //缩略图标签
		   first_pic_middleElement.addAttribute("data",first_pic_middlePath); 
		   first_pic_middleElement.addAttribute("width",""+600);
		   first_pic_middleElement.addAttribute("height",""+(int)((firstpic.getHeight())*600/firstpic.getWidth()));
		   
		   Element first_pic_srcElement =first_picElement.addElement("src"); //原图
		   first_pic_srcElement.addAttribute("data",first_pic_srcPath); 
		  first_pic_srcElement.addAttribute("width",""+firstpic.getWidth());
	      first_pic_srcElement.addAttribute("height",""+firstpic.getHeight());
    	   
       }
       
       try {  
	        /** 格式化输出,类型IE浏览一样 */  
	        OutputFormat format = OutputFormat.createPrettyPrint();  
	        /** 指定XML编码 */  
	        format.setEncoding("UTF_8");  
	        File f=new File("/adListXML.xml");
	        if(f.exists()){ System.out.println("adListXML.xml Exits");}
	        else { System.out.println("adListXML.xml  NOT Exits");}
	        XMLWriter writer=new XMLWriter(new FileWriter(f),format);   
	        writer.write(docement);  
	        writer.close();  
	     //   System.out.println("------->>>22");
	       // return returnValue=true;     
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	
	    try{
	    request.setAttribute("requestcode", requestcode);
	    //System.out.println("------->>>222");
		//return f;
	    String xmlStr=docement.asXML();
		response.setContentType("text/xml;charset=UTF-8");
		response.getOutputStream().write(xmlStr.getBytes("UTF-8"));
	    }catch(Exception e){
	    	System.out.println("there is a exception!");
	    }
	    System.out.println("getAdList.........");
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	
}