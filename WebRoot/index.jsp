<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html;utf-8" language="java" import="java.sql.*"%> 
<%@ page language="java" import="java.util.*"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:directive.page import="jdbc.*" />
 <jsp:directive.page import="allClasses.*" />

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%	 //获取所有单位类别
    List<UnitType> unitTypes=new SearchAboutPost().unitTypes();		
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<jsp:include page="top.jsp" flush="true" />
  <head>
    <base href="<%=basePath%>">
   
  <title> index of Post</title>
<link rel="stylesheet" href="css/homepage.css" type="text/css"/>
<script type="text/javascript" src="js/JQ.js"></script>
<script type="text/javascript" src="js/lrtk.js"></script>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="js/jquery.touchSlider.js"></script>
<link href="css/common.css" rel="stylesheet"/>
<script type="text/javascript">
$(document).ready(function () {
	$(".main_visual").hover(function(){
		$("#btn_prev,#btn_next").fadeIn()
		},function(){
		$("#btn_prev,#btn_next").fadeOut()
		})
	$dragBln = false;
	$(".main_image").touchSlider({
		flexible : true,
		speed : 300,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".flicking_con a"),
		counter : function (e) {
			$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
		}
	});
	$(".main_image").bind("mousedown", function() {
		$dragBln = false;
	})
	$(".main_image").bind("dragstart", function() {
		$dragBln = true;
	})
	$(".main_image a").click(function() {
		if($dragBln) {
			return false;
		}
	})
	timer = setInterval(function() { $("#btn_next").click();}, 3000);
	$(".main_visual").hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(function() { $("#btn_next").click();}, 3000);
	})
	$(".main_image").bind("touchstart", function() {
		clearInterval(timer);
	}).bind("touchend", function() {
		timer = setInterval(function() { $("#btn_next").click();}, 3000);
	})
});
</script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function checkText(){//验证是否输入搜索字段，如果没有则不提交请求
			var text=$("#searchText").val().trim();
			if(text==""||text==null){
				alert("您尚未输入任何搜索字段，请输入");
				return false;
			} 	 
		}
		//location.reload(); 
	</script>
	
  </head>

<body>
	<center>
	 <!--

    
	-->


 <!-- 焦点图 -->

     <div class="main_visual">
                <div class="flicking_con">
                	<div class="flicking_inner">
                    <a href=""></a>
                    <a href=""></a>
                    <a href=""></a>
					<!--
                    <a href=""></a>
                    <a href=""></a>
					-->
               	 </div>
            </div>
			<div class="main_image">
				<ul>					
					<li><span class="img_1"></span></li>
					<li><span class="img_2"></span></li>
					<li><span class="img_3"></span></li>
					<!--
					<li><span class="img_4"></span></li>
					<li><span class="img_5"></span></li>
					-->
				</ul>
				<a href="javascript:;" id="btn_prev"></a>
				<a href="javascript:;" id="btn_next"></a>
			</div>
			</div>
<div style="text-align:center;clear:both">

</div>
 <!-- 焦点图 -->
 

    <div class="homepage_search">
	
	<div class="logo">
	<img src="images/logo07.png" width="150" height="150">
	<img src="images/logo06.png" width="150" height="150">
	</div>

    <div class="search">

		 <!-- 搜索 -->
			<form action="PostLogical?functionName=searchPosts" method="post" onsubmit='return checkText();'>
				<input type="text" id="searchText" name="searchText" class="sinput" placeholder="请输入搜索内容">
				<input type="submit" value="搜索" class="sbtn">
			</form>
		</div>
		
		<div class="post"><!-- 显示所有单位类别 -->
		  <font size="3"> 
		    <c:forEach var="item" items="<%=unitTypes%>">
		    	<a href="PostLogical?functionName=unitsOfType&unitTypeId=${item.unitTypeId}&unitTypeName=${item.unitTypeName}"
				target="_blank">${item.unitTypeName}</a> &nbsp;	&nbsp;&nbsp;	     
		    </c:forEach>
		   
   	       </font>
		</div>
		</div>
		<div class="background"></div>
	</center>
	<jsp:include page="buttom.jsp" flush="true" />
</body>

</html>
