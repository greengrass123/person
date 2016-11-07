<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html;utf-8" language="java"
	import="java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<base href="<%=basePath%>">

<title>My JSP 'register.jsp' starting page</title>
<script type="text/javascript" src="js/register.js"></script>
<link rel="stylesheet" href="css/register.css" type="text/css"/> 

</head>

<body>
<!-- 用户没登陆时不能修改信息  -->
  
	<c:if test='${null==sessionScope.user}'>
		<script type='text/javascript'>
			alert('您尚未登录，请登录后再修改个人信息');
			window.navigate("login.jsp");		
		</script>
	</c:if>
	<c:if test='${true}'><!-- null!=sessionScope.user-->
		<div align="left" style="margin-left:60px; margin-top:30px">
			<img src="logo/logo02.png" width="80" height="80">
			&nbsp;&nbsp;&nbsp;&nbsp; <font color="#FAA07D" size="+4">修改信息</font>	
			 ${sessionScope.user.userName} 
		</div>

		<hr width="90%" />
		<br />
		<br />
		<div class="reg_bg" align="center">
		 
		  	<star>*</star><font_set>为必填信息</font_set>
			<form action="UserLogical?functionName=alterUserInformation" method="post" onsubmit="return check()">
				<table style="width:'50%'">
					<tr>
						<td width="35%" align="right"><font_set>用户名</font_set> <star>*</star>
						</td>
						<td width="20%"><input type="text" name="userName"
							id="userName" onBlur="check_user_name()" /></td>
						<td width="20%" align="left"><div id="info1"
								style="color:#FF0000; display:inline;"></div>
						</td>
					</tr>
					<tr height="10"></tr>
					<tr>
						<td align="right"><font_set>密码 </font_set><star>*</star>
						</td>
						<td><input type="password" name="password"
							id="password" onBlur="check_pw()"
							onKeyUp=pwStrength(this.value) onBlur=pwStrength(this.value) />
						</td>
						<td><div id="info2" style="color:#FF0000; display:inline;"></div>
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td width="20%" colspan="2">
							<table class="passwordDegree">
								<tr align="center" bgcolor="#f5f5f5">
									<td width="45px" id="strength_L"><font_set>弱</font_set></td>
									<td width="45px" id="strength_M"><font_set>中</font_set></td>
									<td width="45px" id="strength_H"><font_set>强</font_set></td>
								</tr>
							</table>
							</td>

					</tr>
					<tr height="10"></tr>
					<tr>
						<td align="right"><font_set>确认密码 </font_set><star>*</star>
						</td>
						<td><input type="password" id="repassword" name="repassword"
							onBlur="checkpwagain()" />
						</td>
						<td><div id="info3" style="color:#FF0000; display:inline;"></div>
						</td>
					</tr>
					<tr height="10"></tr>

					<tr>
						<td align="right"><font_set>电子邮件</font_set></td>
						<td><input type="text" name="email"
							id="email" onblur="check_mail()" />
						</td>
						<td><div id="info7" style="color:#FF0000; display:inline;"></div>
						</td>
					</tr>
					<tr height="10"></tr>
					<tr>
						<td align="right"><font_set>手机号码</font_set></td>
						<td><input type="text" name="phone"
							id="phone" onblur="check_num()" />
						</td>
						<td><div id="info6" style="color:#FF0000; display:inline;"></div>
						</td>
					</tr>
				</table>
				<br />
				<br /> <br />
				<div class="sub">

					<input type="submit" value="确认修改" class="button" onClick="return check()"/>
					<input type="reset" value="重置" class="button"/>
					<!--
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="返回" style="width:80px; height:30px;" onClick="location.href='javascript:history.go(-1);'"/> 
				-->
				</div>
			</form>
		</div>
	</c:if>


</body>
</html>