<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*"%> 
<jsp:directive.page import="allClasses.*" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>top of Post</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		#user {
			position: relative;
			z-index: 10000;
		}
	</style>
	<script type="text/javascript" src="js/jquery-1.4.4.js" ></script>
	<link rel="stylesheet" href="css/index.css" type="text/css"/>
	<script type="text/javascript" src="js/top/JQ.js"></script>
	<script type="text/javascript" src="js/top/lrtk.js"></script>
	<script type="text/javascript">
		$(function(){
		
			$("#user").mouseover(//当鼠标放在用户名上之后，显示下拉框用户有关信息
				function(){						 		 
					$("#userRoot").css("display","block");				 
				}		
			);	
			$("#user").mouseleave(//当鼠标移走之后，隐藏用户信息下拉框
				function(){
					$("#userRoot").css("display","none");	
					console.log('mouseleave div');		
					return false;
				}		
			);
			$("#userRoot").mouseover(//当鼠标放在用户名上之后，显示下拉框用户有关信息
				function(){						 		 
					console.log('mouseover div');				 
				}		
			); 
			 
		}); 	
	</script>
  </head>
  
  <body>
  <div class="nav">
    <ul  id="top">
        <li class="cur"><a href="index.jsp">首页</a></li>          
        <!--用session里面的user属性，记录当前用户信息。 如果为空则表示没登录，显示登录一栏，如果登陆了就显示某某用户，
                     并在鼠标移动到该用户名下时显示：退出、修改信息、我的关注三个条目,有专栏的用户同时显示我的专栏-->
		<c:if test="${sessionScope.user!=null}">
			<li id='user'>${user.userName}
				<div id='userRoot' style='display:none;'>
					<!-- 如果有专栏，即userType>0，则显示我的专栏选项 -->
					<c:if test="${sessionScope.user.userType>0}">
						<p><a href='UserLogical?functionName=privatePost&adTypeId=0' >我的专栏</a></p>
					</c:if>
					<p><a href='UserLogical?functionName=myAttentions' >我的关注</a></p>
					<p><a href='UserLogical?functionName=myAds' >已发布广告</a></p>
					<p><a href='alterUserInformation.jsp'>修改信息</a></p>	
					<p><a href='UserLogical?functionName=exit'>退出</a></p>					
				</div>	
			</li>
		</c:if>    
		<c:if test="${sessionScope.user==null}"><!-- 没有登陆则上传跳转到未登录用户的上传页面 -->
			<li id='login'><a href='login.jsp' >登录</a></li>	<!-- 未登录用户显示登陆 -->		  
			<li><a href="register.jsp" >注册</a></li><!-- 未登录用户显示注册 -->			
		</c:if>
		<c:if test='${null==sessionScope.user||sessionScope.user.userType<=0 }'><!-- 非专栏用户上传广告 -->
			<li><a href="upLoad1.jsp" >上传广告</a></li> 
		</c:if>
		<c:if test='${sessionScope.user.userType>0 }'><!-- 专栏用户上传广告 -->
			<li><a href="privateUpLoad.jsp" >上传广告</a></li> 
		</c:if>	    	
    </ul> 
    <div class="curBg"></div>
    <div class="cls"></div>
  </div>
  
  	
  </body>
</html>
