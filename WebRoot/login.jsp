<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>register success</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/login.css">
	<script type="text/javascript" src="js/jquery-1.4.4.js" ></script>
	<script type="text/javascipt">
		function check(){
			alert($("#inputName").val().trim());
			if($("#inputName").val().trim()==null||$("#inputName").val().trim().equals("")||$("#inputName").val().trim()==""){//如果用户名对话框为空则显示提示信息
				alert("请输入用户名");
				return false;
			}
			else if($("#inputPassWord").val().trim()==null||$("#inputPassWord").val().trim().equals('')){//如果密码对话框为空则显示提示信息
				alert("请输入密码");
				return false;
			}
			return true;
		}
	</script>
  </head>
  
  <body>
       <div align="left" style="margin-left:60px; margin-top:30px">
			<img src="logo/logo02.png" width="80" height="80">
			&nbsp;&nbsp;&nbsp;&nbsp; <font color="#FAA07D" size="+4">用户登录	</font>		
		</div>

		<hr width="90%" />
		<br />
		<br />
		
       <form  action='UserLogical?functionName=login'  id='userInfo' onsubmit='return check();' method='post'>
       <div class='login'>
       <table align="center" class='login_table'>
				    <tr><td><font_set>用户名</font_set></td><td><input type='text' id='inputName' name='userName'></td></tr>
				    <tr height="10px"></tr>
				   <tr><td><font_set>密码</font_set></td><td><input type='password' id='inputPassWord' name='password'> </td></tr>
				     <tr><td></td></tr>
				    <tr><td><input type='submit' value='登录' class='button'> </td></tr>
				   
	  </table> 
	  <br/><br/>
	   
	   </div>
	 </form>
	 
  </body>
</html>
