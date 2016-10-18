<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="allClasses.*"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

List list=(List)request.getAttribute("list");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<style type="text/css">
<!--
.p {
	font-size: 24px;
	font-style: italic;
	color: #006600;
}
-->
</STYLE>

<script type="text/javascript">
	function change(id,name){
		var newType=null;		
		newType=prompt("请输入你要修改的用户等级",name);
		while(newType==null ||newType==" "||newType==""){
			alert('输入的类别名不能为空');
     		newType=prompt("请输入你要修改的用户等级",name);     		
		}
		var cn=document.getElementById(id);
		cn.href="AdminManagerLogical?info=changeUser&userType="+newType+"&userId="+id;  
	}

	
</script>
  </head>
  
  <body>
  <jsp:include page="managertop.jsp" flush="true" />
  <p align="center" class="p">注册用户管理</p>  
   <p align="center">
      <a href="register.jsp" id="add" >注册新用户</a>
     </p>
   <table>
    <tr align="center" height="20">    
   		<td width="150" align="center">用户Id</td>   
   		<td width="150" align="center">用户名</td> 
   		<td width="150" align="center">用户密码</td>     
    	<td width="150" align="center">用户等级</td>     	
    	<td width="150" align="center">邮件</td>  
    	<td width="150" align="center">联系电话</td>  
  		<td width="100" align="center">操作  </td>
  		<td></td>
    </tr>
    <c:if test="${list!=null }">   
    <% 
   		for(int j=0;j<list.size();j++){
   			User t=(User)list.get(j); 
    %>
    <tr>
  	 		<td width="150" align="center"><%=t.getUserId()%></td>  	 		
   	 		<td width="150" align="center"><%=t.getUserName()%></td>   
   	 		<td width="150" align="center"><%=t.getPassword()%></td>  
   	 		<td width="150" align="center"><%=t.getUserType()%></td> 
   	 		<td width="150" align="center"><%=t.getEmail()%></td>   
   	 		<td width="150" align="center"><%=t.getPhone()%></td>  
  			<td>
  				<table width="100" >  
  				<tr align="center">	 		
    				<td width="50" align="center"><a href=" " onclick="change('<%=t.getUserId()%>','<%=t.getUserType() %>')"  id="<%=t.getUserId()%>" >修改</a></td>
    				  		
     				<td width="50" align="center"><a href="AdminManagerLogical?info=delUser&userId=<%=t.getUserId() %>">删除 </a></td>
    			</tr>
    			</table>
    		</td>
   	</tr>    
     <%
   }
    %>
  </c:if>
  </table>     
     
     
    
     
  </body>
</html>
