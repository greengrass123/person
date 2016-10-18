<%@ page  pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理</title>
<style type="text/css">
<!--
.STYLE1 {
	font-family: "宋体";
	font-size: 20px;

}
.STYLE3 {
	font-size: 36px;
	color: #FF8040;
	font-family: Georgia, "Times New Roman", Times, serif;
}
.STYLE4 {font-family: "宋体"; font-size: 24px; }
.STYLE5 {font-size: 24px}
.STYLE6 {font-size: 18px; }

.STYLE7 {
	font-family: "宋体";
	font-size: 20px;
	color:red;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
-->
</style>
</head>

<body>
<p><br></p>
<form action="AdminManagerLogical?info=adminlogin" method="post" >
<table width="450" height="266" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="104" colspan="2" valign="middle" class="STYLE1"><div align="left"><img src="images/logo02.png" width="232" height="49" align="bottom" /><span class="STYLE3"> --后台管理</span></div></td>
  </tr>
  <tr>
    <td width="153" height="76" class="STYLE1"><div align="right" class="STYLE6">管理员：</div></td>
    <td width="297">
      <div align="left">
        <input name="name" type="text" class="STYLE1" size="20" />
      </div></td>
  </tr>
  <tr>
    <td class="STYLE1"><div align="right" class="STYLE5"><span class="STYLE6">密码</span>：</div></td>
    <td>
      <div align="left">
        <input name="password" type="password" class="STYLE1" size="20" />
      </div></td>
  </tr>
  <tr>
    <td height="65" colspan="2"><div align="center"><input type="submit" value="登录"></div></td>
  </tr>
</table>
</form>
<p class="STYLE7" align="center">${requestScope.infomation}</p>
</body>
</html>