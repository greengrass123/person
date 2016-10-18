<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%> 
<%@ page language="java" import="java.util.*"%>  
<jsp:directive.page import="allClasses.*" /> 
<jsp:directive.page import="jdbc.*" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	List<AdType> adTypes=new SearchAboutPost().adTypes();//所有的广告类别
	List<UnitType> unitTypes=new SearchAboutPost().unitTypes();//所有的单位类别
%>
<%
	 
	int adTypeId =0;//获取session存放的id	
	System.out.print("adTypeId:"+ adTypeId);
	String adTypeName = "";
	int postId =0;
	//给上传粘贴栏与上传类别赋予默认值，由于本页面只能上传到公共粘贴栏，如果是专栏则不会给上传赋予默认值
	if (null!=session.getAttribute("post")&&((Post)session.getAttribute("post")).getUserId()<0){
		Post post=(Post)session.getAttribute("post");
		adTypeId =(Integer)session.getAttribute("adTypeId");
		if (adTypeId == 0) {//如果adTypeId等于0，则直接改为1
			adTypeId = 1;
		}
		AdType adType = (AdType) new SearchAboutPost()
				.adTypeOfId(adTypeId);//根据id获取广告类别
		adTypeName = adType.getAdTypeName();				
		postId =post.getPostId();
	}
	System.out.print("adTypeName:" + adTypeName);
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
			$("#selectedPostName").val(""); 
			$("#selectedPostId").val("");
			$("#adTypeContainer li").click(function(event) {// 给adTypeContainer中元素添加点击事件
				var clicked = $(this);
				var adTypeId = clicked.attr('value');// 获取value值，记录的为adTypeId
				var adTypeName= clicked.text(); 
				// alert("adTypeId:"+adTypeId+" "+linkContent);
				$('#adTypeContainer li').removeClass('Selected');// 在给当前对象加样式之前，先将已有selected样式去掉
				$(this).addClass('Selected');// 给当前对象添加selected样式
				$('#selectedAdTypeId').val(adTypeId);// 在隐藏域中记录选种广告类别Id
				$('#selectedAdTypeName').val(adTypeName);
			});
			$("#unitTypeContainer li").click(
					function(event) {
						var clicked = $(this);
						var unitTypeId = clicked.attr('value');
						$('#unitTypeContainer li').removeClass('Selected');
						$(this).addClass('Selected');
						$("#unitContainer").html("");// 改变类别之后首先清除单位、粘贴栏选择框中信息
						$("#postContainer").html("");
						$.post(// 发送异步传输请求，获取对应类别的单位
						"PostLogical", {
							functionName : "unitsWithPublicPost",
							unitTypeId : unitTypeId
						}, function(returnedData, status) {// 返回的是单位
							if (returnedData.length <= 0) {// 如果返回的没有任何包含非专栏的单位，则在文本框显示当前类别没有公共粘贴栏
								$("#unitContainer").text("当前类别没有公共粘贴栏");
							} else { // 如果有则显示
								for ( var i = 0; i < returnedData.length; i++) {
									var obj = returnedData[i];// 获取obj中的每个对象
									var unitId = obj.unitId;
									var unitName = obj.unitName;
									//var onclick="showPosts()";//点击单位后显示该单位的粘贴栏内容
									// alert(unitName);
									// 点击单位后显示该单位的粘贴栏内容
									var html = "<li  value='" + unitId + "'>"
											+ unitName + "</li>";
									var $li=$(html);
									$li.click(function (event) {//点击单位时会显示出对应粘贴栏
										//alert("执行");
										//alert(unitId+":"+unitName);
										var clicked = $(this);
										var html=clicked.html();
										//alert(html);
									  	var unitId = clicked.attr('value');
									  	//alert(unitId);
									 	$('#unitContainer li').removeClass('Selected');
									    $(clicked).addClass('Selected'); 
										$("#postContainer").html(""); //清空原来显示的内容
										$.post("PostLogical", {
											functionName : "publicPostsOfUnit",
											unitId : unitId
										}, function(returnedData, status) {//返回的是粘贴栏
											if (returnedData.length <= 0) {//如果返回的没有任何包含非专栏的单位，则在文本框显示当前类别没有公共粘贴栏
												$("#textOfPosts").val("当前类别没有公共粘贴栏");
											} else {
												for ( var i = 0; i < returnedData.length; i++) {
													var obj = returnedData[i];//获取obj中的每个对象
													var postId = obj.postId;
													var postName = obj.postName;
													//点击类别后将隐藏域内容设置为PostId
													var html = "<li value='"+postId+"' onclick='"+onclick+"'>"+postName+"</li>"; 
													var $li=$(html);
													$li.click(function(event){//为当前对象添加点击事件
														  	var clicked = $(this);
														  	//var html=clicked.html();
														  	//alert(html);
															var postId = clicked.attr('value')+'_';	//用下划线连接多个选中的粘贴栏
															var postName=clicked.text()+"、";
															var selectedPostId=$('#selectedPostId').val();
															var selectedPostName=$('#selectedPostName').val();
															//alert(selectedPostId);
											//点击某个粘贴栏时，看当前对象是否有该class，有则减去，无则添加	，同时处理隐藏栏中postId		
															if($(this).hasClass('Selected')){
																$(this).removeClass('Selected');
																selectedPostId=selectedPostId.replace(postId,'');
																selectedPostName=selectedPostName.replace(postName,'');
															}	
															else{
																$(this).addClass('Selected');
																if(-1==selectedPostId.indexOf(postId)){//如果之前没有选中则添加
																	selectedPostId=selectedPostId+postId;
																	selectedPostName=selectedPostName+postName;
																}
																
															}												
															//$('#postContainer li').removeClass('Selected');
															
														 	$('#selectedPostId').val(selectedPostId);//在隐藏域中记录选种粘贴栏Id
															$('#selectedPostName').val(selectedPostName);
													});
													$("#postContainer").append($li);
												}
											}
										});
									});	
									$("#unitContainer").append($li);
								}
							}
						});
					});
			
		});

	</script>
</head>
<body>
<!-- 如果不是专栏用户则用第一个上传页面 -->
	<c:if test='${null==sessionScope.user||sessionScope.user.userType<=0}'>
		<%
			response.sendRedirect("login.jsp");
		 %>
	</c:if>
<!-- 如果是专栏用户，可以选择多个粘贴栏 -->	
	<c:if test='${sessionScope.user.userType>0}'>
		<div class="step">
			<!-- 显示第几步 -->
			<div class="current">第一步：选择上传广告的分类</div>
			<div class="not_current">第二步：选择上传的图片</div>
			<div class="not_current"><br><br>第三步：完成上传</div>
		</div>

		<div id="CategoryTitle">
			<!-- 各个不同类别头部显示 -->
			<ul id="TitleContent">
				<li>广告类别</li>
				<li>单位类别</li>
				<li>单位名称</li>
				<li>粘贴栏名称</li>
			</ul>
		</div>

		<div id="CategorySelector">
			<!-- 各个不同类别用于显示对应信息的背景框 -->
			<ul id="adTypeContainer" class="Blank">
				<c:forEach items="<%=adTypes%>" var='adType'>
					<!-- 显示各种广告类别，点击之后显示颜色，并且记录选中值 -->
					<li value='${adType.adTypeId}'>${adType.adTypeName}</li>
				</c:forEach>
			</ul>
			<ul id="unitTypeContainer" class="Blank">
				<!-- 显示各种单位类别，点击之后显示颜色，并且调出对应类别的所有单位 -->
				<c:forEach items="<%=unitTypes%>" var='unitType'>
					<li value='${unitType.unitTypeId}'>${unitType.unitTypeName}</li>
				</c:forEach>
			</ul>
			<ul id="unitContainer" class="Blank">
			</ul>
			<ul id="postContainer" class="Blank">
			</ul>
		</div> 
		
		
			<form action='upLoad2.jsp' onsubmit='return check()' method='post'>
				<!-- 点击广告后向upLoad2发送adTypeId和postId,发送前先确定是否选中-->
				<div class='info'>
				<ul><li>
				<input type='text' style='display:none' id='selectedAdTypeId'
					name='adTypeId' value='<%=adTypeId%>'>
				<!--用隐藏域显示选中的广告类别id -->
				</li>
				<li>
				
				<input type='text' style='display:none' id='selectedPostId'
					name='postId' value='<%=postId%>'>
					</li>
				<li>
				上传广告到：<input type="text" style='width:300'  id='selectedPostName' name='postName' readonly='true' value=''>	<!--显示选中的粘贴栏名称 -->		
				</li>
				<li>
				所选类别：<input type="text" style='width:80'  id='selectedAdTypeName' name='adTypeName' readonly='true' value='<%=adTypeName%>'>	<!--显示选中的粘贴栏名称 -->
				</li>								
			   <li>
		        <div class="but">
				<p>
					<input type="submit" value="下一步" class="button" />
				</p>
				</div>
				</li>
				</ul>
				</div>
			</form>
		</div>
		
		
	</c:if>
</body>
</html>