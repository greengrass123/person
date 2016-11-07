<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<script type="text/javascript" src="js/scrollLoading.js"></script>

</head>

<body>
<jsp:include page="top.jsp" flush="true" />
<jsp:include page="bodyOfPost.jsp" flush="true" />
</body>
</html> 


