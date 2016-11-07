<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%> 
<%@ page language="java" import="java.util.*"%>  
<jsp:directive.page import="allClasses.*" /> 
<jsp:directive.page import="jdbc.*" />
<jsp:include page="top.jsp" flush="true" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	int postId=Integer.parseInt(request.getParameter("postId"));//获取postId
	List<PrivateAdType> adTypes=(List<PrivateAdType>)new SearchAboutPost().privateAdTypes(postId);//获取单位类别 
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
	<script type="text/javascript" src="js/upLoad.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.4.js" ></script>
	<title>upLoad ads</title>
	<script type="text/javascript"  >
		$(document).ready(
		function() {
			$("#adTypeContainer li").click(function(event) {// 给adTypeContainer中元素添加点击事件
				var clicked = $(this);
				var adTypeId = clicked.attr('value');// 获取value值，记录的为adTypeId
				var adTypeName = clicked.text();// 获取value值，记录的为adTypeId
				// alert("adTypeId:"+adTypeId+" "+linkContent);
				$('#adTypeContainer li').removeClass('Selected');// 在给当前对象加样式之前，先将已有selected样式去掉
				$(this).addClass('Selected');// 给当前对象添加selected样式
				$('#selectedAdTypeId').val(adTypeId);// 在隐藏域中记录选种广告类别Id
				$('#selectedAdTypeName').val(adTypeName);
			});			
		});

	</script>
</head>
<body>

<div class="step"><!-- 显示第几步 -->
<div class="current">
第一步：选择上传广告的分类
</div>
<div class="not_current">
第二步：选择上传的图片
</div>
<div class="not_current">
第三步：完成上传
</div>
</div>

<div id="CategoryTitle"><!-- 各个不同类别头部显示 -->
	<ul id="TitleContent">
	    <li>广告类别</li> 	
	</ul>
</div>

<div id="CategorySelector"><!-- 各个不同类别用于显示对应信息的背景框 -->
    <ul id="adTypeContainer" class="Blank">
    	<c:forEach items="<%=adTypes%>" var='adType'><!-- 显示各种广告类别，点击之后显示颜色，并且记录选中值 -->
    	   <li value='${adType.adTypeId}' >${adType.adTypeName}</li> 
    	</c:forEach>
	</ul>	
</div>

<div class="but">
<form action='upLoad2.jsp'  onsubmit='return check()' method='post'><!-- 点击广告后向upLoad2发送adTypeId和postId,发送前先确定是否选中-->
<input type='text' style='display:none' id='selectedAdTypeId' name='adTypeId' value=''><!--用隐藏域显示选中的广告类别id -->
<input type='text' style='display:none' id='selectedPostId' name='postId' value='${param.postId}'><!--用隐藏域存储本粘贴栏id -->
上传广告到：<input type='text' readonly='true' id='selectedPostName' name='postName' value='您的专栏'>
所选类别为：<input type='text' readonly='true' id='selectedAdTypeName' name='adTypeName' value=''>
<p>
<input type="submit" value="下一步" class="button" /> </p>
</form>
</div> 
</body>
</html>