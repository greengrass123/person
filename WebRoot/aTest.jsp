<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*"%>
<%@ page language="java" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
 
<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="js/editPrivatePost/jquery.sortable.js"></script> 
<link rel="stylesheet" type="text/css" href="css/editPrivatePost.css">

</head>

<body>

 <section>
	<ul class="gbin1-list">
		<li><img src="images/01.jpg"/></li>
		<li><img src="images/02.jpg"/></li>
		<li><img src="images/03.jpg"/></li>
		<li><img src="images/04.jpg"/></li>
		<li><img src="images/05.jpg"/></li>
		<li><img src="images/06.jpg"/></li>
		<li><img src="images/07.jpg"/></li>
		<li><img src="images/08.jpg"/></li>
		
	</ul>
	
	<div style="clear:both" id="msg"></div>
 </section>
 
 
<script>
    $('.gbin1-list').sortable().bind('sortupdate', function() {
		$('#msg').html('position changed').fadeIn(200).delay(1000).fadeOut(200);
	});
</script>
	
</body>
</html>
