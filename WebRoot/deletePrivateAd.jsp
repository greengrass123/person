<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*"%>
<%@ page language="java" import="java.util.*"%>
<jsp:directive.page import="allClasses.*" /> 
<jsp:directive.page import="jdbc.*" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	int postId=Integer.parseInt(request.getParameter("postId"));
	List<PrivateAd> ads=(List<PrivateAd>)new SearchAboutPost().adsOfPrivatePost(postId);
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<base href="<%=basePath%>">
<title>body of the post</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  
<script type="text/javascript">
 
</script>
</head>
<body>   
	<c:set var='ads' value='<%=ads%>'></c:set>
	 <table id="ads" align="center">
      <c:if test="${ads.size()>0}">   
       <tr>  
    	 <c:forEach var="ad" items="${ads}" varStatus="status">
    		<td>
    		  <table id='ad' align='center'>
    		 	<tr align='center'>
    		 		<td> <a href="PostLogical?functionName=picsOfAd&adId=${ad['adId']}" target="_blank"><input type="image" class="img" alt="点击查看"  src="${ad['firstPicAddr']}" id="${ad['adId']}"></a></td>
    		 		<c:set var='adTypeId' value="${ad.adTypeId}"></c:set>
    		 		<!-- 获取postId对应的postName -->
    		 		<%
    		 			int adTypeId=(Integer)pageContext.getAttribute("adTypeId");
    		 			System.out.println("adTypeId:"+adTypeId);
    		 			String adTypeName="";
    		 			if(null!=new UserOperation().privateAdTypeOfId(adTypeId)){
    		 				PrivateAdType privateAdType=(PrivateAdType)new UserOperation().privateAdTypeOfId(adTypeId);
    		 				adTypeName=privateAdType.getAdTypeName();
    		 				System.out.println("adTypeName:"+adTypeName);
    		 			}    		 			
    		 		 %>
    		 		<td style='valign:middle;align:center'>    		 			
	    		 		<p id='upLoadTime'>上传时间：${ad.upLoadTime} 
	    		 		<p id='postName'>广告类别：<%=adTypeName%>
	    		 		<p><a href="UserLogical?functionName=deletePrivateAds&adId=${ad['adId']}">删除</a>    		 			
    		 	    </td>
    		  </table>
    		</td>	    		 	 
    		<c:if test="${status.count%2==0}"><!-- 如果每行达到指定个数则转行-->    			 
    			 <c:out value="</tr><tr>" escapeXml="false"></c:out>
    		</c:if>
    	 </c:forEach>
      </c:if>
      <c:if test="${ads.size()<=0 }">	
      	 <tr align="center"><td> 您尚未发布广告</td></tr>
      </c:if>
    </table>    
 
 
<script>
 
	
</body>
</html>
