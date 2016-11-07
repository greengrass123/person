<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html;utf-8" language="java"
	import="java.sql.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<base href="<%=basePath%>">

<title>注册页面</title>
<script type="text/javascript" src="js/register.js"></script>
<!-- <link rel="stylesheet" href="css/register.css" type="text/css"/>  -->
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>

<body style="background:url(css/images/back_register.jpg) top center no-repeat; background-size:100% 100%;opacity: 0.8;overflow:-Scroll;overflow-y:hidden;">
        <div class="container">
	        
            <section>               
                <div id="container_demo">
               <!--  <img alt="" src="css/images/2.jpg" style="width:340px;height:300px;top:200px;right:-200px;float:left;position:relative"> -->
                    <div id="wrapper_register">
		                     
                        <div id="register" class="animate form">
                            <div align="left" style="margin-left:60px; margin-top:10px">
                                 <img src="logo/logo02.png" width="80" height="80">
                                &nbsp;&nbsp;&nbsp;&nbsp; <font color="#FAA07D" size="+4">用户注册   </font>     
                            </div><br>
                            <form  action="UserLogical?functionName=register" method="post" onsubmit="return check()" autocomplete="on"> 
                                <p> 
                                    <label for="userName" class="uname" data-icon="u">用户名（必填）</label>
                                    <input id="userName" name="userName" onBlur="check_user_name()" required="required" type="text" placeholder="username" />
                                    <span id="info1" style="color:#FF0000; display:inline;float:right"></span>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p">密码（必填）</label>
                                    <input id="password" name="password" onBlur="check_pw()" onKeyUp=pwStrength(this.value) onBlur=pwStrength(this.value) required="required" type="password" placeholder="......"/>
                                    <span id="info2" style="color:#FF0000; display:inline;float:right"></span>
                                </p>
                                <p>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span width="45px" id="strength_L" style="background-color:#E8E8E8">&nbsp;&nbsp;&nbsp;&nbsp;<font_set>弱</font_set>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                    <span width="45px" id="strength_M" style="background-color:#E8E8E8">&nbsp;&nbsp;&nbsp;&nbsp;<font_set>中</font_set>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                    <span width="45px" id="strength_H" style="background-color:#E8E8E8">&nbsp;&nbsp;&nbsp;&nbsp;<font_set>强</font_set>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                </p>
                                <p> 
                                    <label for="repassword" class="youpasswd" data-icon="p">确认密码（必填） </label>
                                    <input id="repassword" name="repassword" onBlur="checkpwagain()" required="required" type="password" placeholder="......"/>
                                    <span id="info3" style="color:#FF0000; display:inline;float:right"></span>
                                </p>
                                <p> 
                                    <label for="email" class="youmail" data-icon="e" > 邮箱</label>
                                    <input id="email" name="email" onblur="check_mail()" required="required" type="email" placeholder="mysupermail@mail.com"/> 
                                    <span id="info7" style="color:#FF0000; display:inline;float:right"></span>
                                </p>
                                <p> 
                                    <label class="icon-phone">手机号</label>
                                    <input  id="phone" name="phone" onblur="check_num()" required="required" type="tel" placeholder="..........."/> 
                                    <span id="info6" style="color:#FF0000; display:inline;float:right"></span>
                                </p>
                                <p class="signin button" style="margin-top:0px;"> 
                                    <input type="submit" value="注册" onClick="return check()"/> 
                                </p>
                                <p class="change_link">  
                                    已有账号?
                                    <a href="http://localhost:8080/post/login.jsp" class="to_register"> 立即登录 </a>
                                </p>
                            </form>
                        </div>
                    </div>
                </div>  
            </section>
        </div>


</body>
</html>