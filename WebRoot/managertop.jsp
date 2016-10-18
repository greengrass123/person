<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>发布信息</title>

<style >
<!--
.STYLE1 {
	font-size: 24px;
	font-style: italic;
	color: #006600;
}
.STYLE2 {font-size: 12px}
.STYLE3 {font-size: 14px}
.STYLE4 {font-size: 10px}
.STYLE6 {color: #FFFFFF; font-weight: bold; font-size: 14px;}
.STYLE7 {
	font-size: 20px;
	color: #FF8040;
	font-family: Georgia, "Times New Roman", Times, serif;
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
.STYLE8 {font-size: 12px; color: #FF0000; }
.STYLE9 {font-size: 16px}
-->
</style>
</head>
<body>
<table width="991" border="0" align="center" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
   <tr bgcolor="#AEDD81">
  
     <td width="50"  rowspan="2"></td>
     <td width="120"  rowspan="2"><div align="center"><a href="adminManager.jsp">审核信息管理</a></div></td>    
     <td  width="120"  rowspan="2"><div align="center"><a href="AdminManagerLogical?info=showUser" class="STYLE3">注册用户管理</a></div></td>
     <td width="80" rowspan="2"><div align="center"><a href="AdminManagerLogical?info=pasteShow" class="STYLE3">粘贴栏管理</a></div></td>
     <td width="80" rowspan="2"><div align="center"><a href="AdminManagerLogical?info=unitShow" class="STYLE3">单位管理</a></div></td>       
     <td width="80"  rowspan="2"><div align="center"><a href="AdminManagerLogical?info=typeShow" class="STYLE3">类别管理</a></div></td> 
     <td width="120" rowspan="2"><div align="center"><a href="AdminManagerLogical?info=pasteTypeShow" class="STYLE3">单位所属类别管理</a></div></td>       
     <c:if  test="${!empty sessionScope.adminInfo}">
     <td width="150" rowspan="2"><div align="center" class="STYLE4">管理员${sessionScope.adminInfo.name }欢迎你</div></td>
     <td width="100"  rowspan="2"><div align="center" class="STYLE3"><a href="AdminManagerLogical?info=adminloginout" class="a2">退出</a></div></td>
     </c:if>
     <td width="50"  rowspan="2"></td>

   <tr>
     <td height="14" bgcolor="#AEDD81">&nbsp;</td>
     <td bgcolor="#AEDD81"></td>
     <td bgcolor="#AEDD81"></td>
     <td bgcolor="#AEDD81">&nbsp;</td>
          <td bgcolor="#AEDD81">&nbsp;</td>
          <td bgcolor="#AEDD81">&nbsp;</td>
          <td bgcolor="#AEDD81">&nbsp;</td>
          <td bgcolor="#AEDD81">&nbsp;</td>
          <td valign="top" bgcolor="#AEDD81" class="STYLE3"><a href="#"></a></td>
   </tr>
    <tr>
     <td height="26" colspan="3"></td>
     <td width="66"></td>
     <td width="28"></td>
     <td></td>
     <td></td>
     <td></td>
     <td></td>
     <td></td>
     <td></td>
     <td></td>
     <td></td>
     <td></td>
     <td colspan="3"></td>
     <td width="57"></td>
     <td width="11"></td>
     <td width="4"></td>
     <td></td>
     <td></td>
   </tr>
    
   </table>
   <table width="1000" align="center">
   <tr>
    <td height="104"  width="600" colspan="2" valign="middle"  align="center" class="STYLE1"><div align="center"><img src="images/logo02.png" width="232" height="49" align="bottom" /><span class="STYLE7"> 后台管理</span></div></td>
  </tr>
   </table>
 </body>
</html>