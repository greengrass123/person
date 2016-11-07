<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%> 
<%@ page language="java" import="java.util.*"%>  
<jsp:directive.page import="allClasses.Pic" /> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List pics=(List)request.getAttribute("pics");
System.out.println("pics:"+pics.size());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>show pics of ad</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <link rel="stylesheet" type="text/css" href="css/picsOfAd/jquery.ad-gallery.css">
  <link rel="stylesheet" type="text/css" href="css/picsOfAd/post_images.css">
   <link rel="stylesheet" type="text/css" href="css/picsOfAd/littlePics.css">
  <script type="text/javascript" src="js/picsOfAd/jquery.min.js"></script>
  <script type="text/javascript" src="js/picsOfAd/jquery.ad-gallery.js"></script>
  <script type="text/javascript" src="js/picsOfAd/zzsc.js"></script>
  </head>
  
 <body>
 <c:if test="${pics.size()>0}"><!-- 有图片则部署展示样式 -->
 <center>
    <div id="gallery" class="ad-gallery">
      <div class="ad-image-wrapper">
      </div>
      <div class="ad-nav">
        <div class="ad-thumbs">
          <ul class="ad-thumb-list">
            <c:forEach var="pic" items="${pics}">  
       		  <li> 
       		    <a href="${pic.picAddr}">
                 <img src="${pic.picAddr}" class="littlePic">
                </a> 
              </li> 
            </c:forEach>   
          </ul>
        </div>
      </div>
    </div>

  </center>
</c:if>
<c:if  test="${pics.size()<=0}"><!-- 用户可能从地址栏直接输入而非按步骤操作，会导致对应广告没有图片 -->
        <table align="center"><tr align="center" > 您所选择的广告为空</tr></table>        
</c:if>
</body>
</html>
