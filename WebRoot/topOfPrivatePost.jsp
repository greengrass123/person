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
			$("#user").mouseout(//当鼠标移走之后，隐藏用户信息下拉框
				function(){
					$("#userRoot").css("display","none");
				
				}		
			);	 
			$("#edit").mouseover(//当鼠标放在用户名上之后，显示下拉框用户有关信息
				function(){						 		 
					$("#edits").css("display","block");
					 
				}		
			);	
			$("#edit").mouseout(//当鼠标移走之后，隐藏用户信息下拉框
				function(){
					$("#edits").css("display","none");
				
				}		
			);
		}); 
	
	</script>
  </head>
  
  <body >
  <div class="nav">
    <ul  id="top">
        <li class="cur"><a href="index.jsp" target="_blank">首页</a></li>          
        <!--用session里面的user属性，记录当前用户信息。 如果为空则表示没登录，显示登录一栏，如果登陆了就显示某某用户，
                     并在鼠标移动到该用户名下时显示：退出、修改信息、我的关注三个条目,有专栏的用户同时显示我的专栏-->
		<c:if test="${sessionScope.user!=null}">
			<li id='user'>${user.userName}
				<div id='userRoot' style='display:none'>
					<c:if test="${sessionScope.user.userType>0}">
						<p><a href='UserLogical?functionName=privatePost&adTypeId=0' target='_blank'>我的专栏</a></p>
					</c:if>
					<p><a href='UserLogical?functionName=myAttentions' target='_blank'>我的关注</a></p>
					<p><a href='UserLogical?functionName=myAds' target='_blank'>已发布广告</a></p>
					<p><a href='alterUserInformation.jsp'>修改信息</a></p>	
					<p><a href='UserLogical?functionName=exit'>退出</a></p>
				</div>	
			</li>
		</c:if>  		 
		<li><a href="privateUpLoad.jsp">上传广告</a></li><!-- 用户上传广告 -->	 
		<li id='edit'>编辑专栏
			<div id='edits' style='display:none'>
					<!-- 如果有专栏，即userType>0，则显示我的专栏选项 --> 
					
				<p><a href='editOrderOfAds.jsp?postId=${post.postId}' target='_blank'>调整专栏广告顺序</a></p>
				<p><a href='editPrivateAdType.jsp?postId=${post.postId}'  target='_blank'>修改专栏类别</a></p>
				<p><a href='upLoadToPrivatePost.jsp?postId=${post.postId}'target='_blank'>上传广告到专栏</a></p>				
				<p><a href='deletePrivateAd.jsp?postId=${post.postId}' target='_blank'>删除专栏广告</a></p>	 	
			</div>			
		</li>     	
    </ul> 
    <div class="curBg"></div>
    <div class="cls"></div>
  </div>
  
  	
  </body>
</html>
