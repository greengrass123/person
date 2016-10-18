<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%> 
<%@ page language="java" import="java.util.*"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<jsp:directive.page import="jdbc.*" />
<jsp:directive.page import="allClasses.*" /> 
<jsp:directive.page import="configurations.*" /> 
<jsp:include page="top.jsp" flush="true" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 
 Configuration configuration=new Configuration();
 int picWidth=200;//每张图片高度与宽度
 int picHeight=200;
 int picNumOfEveryLoading=configuration.PICNumOFEVERYLOADING;//每次加载图片张数
 int picsInOneRow=2;//每行显示图片张数
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 

    <base href="<%=basePath%>">
    <title>body of the post </title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link rel="stylesheet"  type="text/css"  href="css/bodyOfPost.css">

  </head>
  
  <body>  
  
    ${sessionScope.user.userName} 您上传到公共粘贴栏的广告为： 
  <center>
    <c:set var="picsInOneRow" value="<%=picsInOneRow%>"></c:set> <!--picsInOneRow为每行显示图片张数-->
    <br/><br/><br/>
    
    <table id="ads" align="center" width=80%>
      <c:if test="${ads.size()>0}"> 
        <p><a href='UserLogical?functionName=deleteMyAds'>全部删除</a></p>
        <br/> <br/>
       <tr> 
         <c:set var="picsInOneRow" value="${picsInOneRow}"> </c:set><!-- 每行显示的图片个数 -->
    	 <c:forEach var="ad" items="${ads}" varStatus="status">
    		<td>
    		  <table id='ad' align='center'>
    		 	<tr align='center'>
    		 		<td> <a href="PostLogical?functionName=picsOfAd&adId=${ad.adId}&postId=${ad.postId}" target="_blank"><input type="image" class="img" alt="点击查看"  src="${ad['firstPicAddr']}" id="${ad['adId']}"></a></td>
    		 		<c:set var='postId' value="${ad.postId}"></c:set>
    		 		<!-- 获取postId对应的postName -->
    		 		<%
    		 			int postId=(Integer)pageContext.getAttribute("postId");
    		 			Post post=(Post)new SearchAboutPost().postOfId(postId);
    		 			String postName=post.getPostName();
    		 			System.out.println("postName:"+postName);
    		 		 %>
    		 		<td style='valign:middle;align:center'>    		 			
	    		 		<p id='upLoadTime'>上传时间：${ad.upLoadTime} 
						<p id='postName'>广告所在粘贴栏： <a href="PostLogical?functionName=enterPost&adTypeId=0&postId=${postId}" target="_blank"><%=postName%></a>
	    		 		<p><a href="UserLogical?functionName=deleteMyAds&adId=${ad['adId']}">删除</a>    		 			
    		 	    </td>
    		 	    </tr>
    		 	    <tr></tr>
    		  </table>
    		  
    		</td>	
    		    		 	 
    		<c:if test="${status.count%picsInOneRow==0}"><!-- 如果每行达到指定个数则转行-->    			 
    			 <c:out value="</tr><tr>" escapeXml="false"></c:out>
    		</c:if>
    	 </c:forEach>
      </c:if>
      <c:if test="${ads.size()<=0 }">	
      	 <tr align="center"><td> 您尚未发布广告</td></tr>
      </c:if>
    </table>    
     <div id="returnTop" class="returnTop" onclick="returnTop() ">返回顶部</div><!-- 返回顶部的小链接 -->
    <div style="height:1000" ></div>
    </center>
  </body>
</html>
