<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String searchText=request.getParameter("searchText");
System.out.println("页面内searchText："+searchText);
searchText=new String(searchText.getBytes("iso-8859-1"),"utf-8");
%>
<jsp:include page="top.jsp" flush="true" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>posts contain searchText</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link rel="stylesheet" type="text/css" href="css/showPosts.css">
  </head>
  
  <body>

	搜索字段为：<%=searchText %> 搜索结果为：
	<!-- 如果有包含搜索字段的单位或粘贴栏则循环 -->
	<c:if test="${unitsAndPosts.size()>0}">
		<c:forEach var="Map" items="${unitsAndPosts}" >
		<c:forEach items="${Map}" var="entry" varStatus="status">
			<!-- 显示单位名 -->		
			<div class='unit_post'>	 	 
			<table align="center" class="post_title">
				<tr align="left">
				  <c:set var='unitId' value="${entry.key.split('_')[0]}"></c:set>
				  <c:set var='unitName' value="${entry.key.split('_')[1]}"></c:set>
					<td>${unitName}</td>
				</tr>
			</table>
			<hr />	
			<table align="center" class="post_name">
				<!-- 如果该单位下有粘贴栏，则循环遍历 -->
				<tr>
				<c:if test="${entry.value.size()>0}">
					<div class='post'>
					<!-- <td><a href="PostLogical?functionName=enterUnit&adTypeId=0&unitInfo=${entry.key}">所有</a>
						</td>  -->	
						<c:forEach var='post' items='${entry.value}'>
							<a href="PostLogical?functionName=enterPost&adTypeId=0&postId=${post.postId}" target="_blank">${post.postName}</a>
						</c:forEach>
						</div>
					
				</c:if>
				</tr>
				<tr>
				<!-- 如果单位下没有粘贴栏则输出提示字段 -->
			   	<c:if test="${entry.value.size()<=0}">
			    	<div class="post_info"><a>该单位下没有粘贴栏</a></div>	    	 
			    </c:if>
			    </tr>
			</table>
			</div>
		</c:forEach>
		</c:forEach>
	</c:if>
	<c:if test="${unitsAndPosts.size()<=0}">
		<div class="post_info"><a>没有包含该字段的单位或粘贴栏</a></div>
	</c:if>
</body>
</html>
