<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>
<head>
<script type="text/javascript" src="js/scrollLoadingOfPrivatePost.js"></script>
</head>

<body>
<!-- 如果是其他用户访问应该导入Top，如果是访问自己的专栏则导入 topOfPrivatePost-->
<c:if test='${myPost==true}'>
	<jsp:include page="topOfPrivatePost.jsp" flush="true" />
</c:if>
<c:if test='${myPost==false}'>
	<jsp:include page="top.jsp" flush="true" />
</c:if>
<jsp:include page="bodyOfPost.jsp" flush="true" />
</body>
</html> 


