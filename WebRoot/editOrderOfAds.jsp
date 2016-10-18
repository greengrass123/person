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
	List<PrivateAd> ads=(List<PrivateAd>)new SearchAboutPost().adsOfPrivatePost(postId);//获取专栏中所有广告
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
<script type="text/javascript">
	function saveOrder(){
		var images=document.getElementsByName("image");
		var adIds='';//记录现有的adId顺序
		for(var i=0;i<images.length;i++){			
			//alert("images.value:"+images[i].value);
			adIds=adIds+images[i].value+"_";
		}
		//alert(adId);
		$('#save').attr('href','UserLogical?functionName=saveOrderOfAds&adId='+adIds);
	}
</script>
</head>
<body>
<a href='' id='save'><input type='button' value='保存' onclick='saveOrder()'></a>
 <section>
	<ul class="gbin1-list">
		<c:forEach items='<%=ads%>' var='ad'>
			<li><input type='image'  src="${ad.firstPicAddr}"  value='${ad.adId}' name='image'/></li>
		</c:forEach>		
	</ul>
	
	<div style="clear:both" id="msg"></div>
 </section>
 
<script>
//提示广告已经改变位置
    $('.gbin1-list').sortable().bind('sortupdate', function() {
		$('#msg').html('position changed').fadeIn(200).delay(1000).fadeOut(200);
	});
</script>
	
</body>
</html>
