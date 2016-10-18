<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*"%> 
<jsp:directive.page import="allClasses.*" />
<jsp:directive.page import="jdbc.*" />
<jsp:directive.page import="java.util.*" /> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	int postId=Integer.parseInt(request.getParameter("postId"));//获取postId
	List<PrivateAdType> adTypes=(List<PrivateAdType>)new SearchAboutPost().privateAdTypes(postId);//获取单位类别 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'editPrivatePostAdType.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type='text/javascript'>
		function addAdType(postId){
			var adType=prompt("请填入你要添加的类别","");
			adType=adType.trim();
			if(adType==''||adType==null){
				alert('您尚未增加任何类别');
			}
			else{
				document.getElementById('addAdType').href='UserLogical?functionName=addAdType&postId='+postId+'&adTypeName='+adType;		
			}
		}
		function deleteAdType(postId,adTypeId){
			var isDelete=confirm("注意：当您删除某个广告类别时，会删除该类别中所有的广告");
			if(isDelete){			
				document.getElementById('deleteAdType').href='UserLogical?functionName=deleteAdType&postId='+postId+'&adTypeId='+adTypeId;		
			 
			} 
		
		}
		//href='UserLogical?functionName=deleteAdType&adTypeId=${adType.adTypeId}&postId=${param.postId}'
	</script>
  </head>
  
  <body>
  	<p>注意：当您删除某个广告类别时，会删除该类别中所有的广告<br/><br/><br/>
  	 <a id='addAdType' onclick="addAdType('${param.postId}')">增加广告类别</a> 
     <c:set var='adTypes' value='<%=adTypes%>'></c:set>     
     <c:forEach items='${adTypes}' var='adType'>
     	<p>${adType.adTypeName}&nbsp;&nbsp;&nbsp;&nbsp;
     	<a href='UserLogical?functionName=deleteAdType&adTypeId=${adType.adTypeId}&postId=${param.postId}'  id='deleteAdType'  >删除</a>
     </c:forEach>
  </body>
</html>
