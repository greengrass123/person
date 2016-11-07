<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%> 
<%@ page language="java" import="java.util.*"%>  
<jsp:directive.page import="allClasses.*" /> 
<jsp:directive.page import="jdbc.*" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/upload.css" type="text/css">
	<title>upLoad ads</title>
	<script type='text/javascript'>
		function goBack(){
			history.go(-2);
		}
		
	</script>
</head>
<body>

<div class="step">
<div class="not_current">
第一步：选择上传广告的分类
</div>
<div class="not_current">
第二步：选择上传的图片
</div>
<div class="current">
第三步：完成上传
</div>
</div>

<div class="but">
恭喜您上传成功！
<p><p><p><p>
<!-- 此处返回的应该是进来的地方，history.go(-2) -->
<input type='button' value="  继续上传"" class="button" onclick="goBack()">
<input type="button" value="返回首页" class="button" onclick="window.open('index.jsp','_self')">  
</div>

</body>
</html>