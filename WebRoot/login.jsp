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
    
    <title>登录页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- <link rel="stylesheet" type="text/css" href="css/login.css"> -->
	<link rel="shortcut icon" href="../favicon.ico"> 
    <link rel="stylesheet" type="text/css" href="css/demo.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
   <!--  <link rel="stylesheet" type="text/css" href="css/animate-custom.css" /> -->
	<script type="text/javascript" src="js/jquery-1.4.4.js" ></script>
  </head>
  
  <body style="background:url(css/images/back_login.jpg) top center no-repeat; background-size:cover;overflow-y:auto;opacity: 0.8;">
    <div class="container">
            <section>               
                <div id="container_demo" >
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                    <img alt="" src="css/images/copy1     .jpg" style="width:1100px;height:680px;right:500px;margin: 0px auto;position:relative">
                        <div id="login" class="animate form">
                            <form  action='UserLogical?functionName=login'  id='userInfo' method='post'> 
                                <div align="left" style="margin-left:60px; margin-top:30px">
            <img src="logo/logo02.png" width="80" height="80" style="margin-top:8px">
             <font color="#FAA07D" size="+4">用户登录   </font>     
        </div><br><br><br>
                                <p> 
                                    <label for="username" class="uname" data-icon="u" style="right:169px;">&nbsp;&nbsp;&nbsp;用户名</label>
                                    <input id="username" name="userName" required="required" type="text" placeholder="username"/>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p" style="right:169px;">&nbsp;&nbsp;&nbsp;密     码 </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="......" /> 
                                </p>
                                <br>
                                <p class="login button"> 
                                    <input type="submit" value="登录"/> 
                                </p>
                                <p class="change_link">
                                    还没有账号?
                                    <a href="http://localhost:8080/post/register.jsp" class="to_register">立即注册</a>
                                </p>
                            </form>
                        </div>
                    </div>
                </div>  
            </section>
        </div>	 
  </body>
</html>
