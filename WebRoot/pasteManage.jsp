<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="allClasses.*"/>
<jsp:directive.page import="logicalConduct.*"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

List list=(List)request.getAttribute("list");
AdminLogic  data=new AdminLogic();

int[] userIds=new int[data.userIds().length];
int[] unitIds=new int[data.unitIds().length];
userIds=data.userIds();
unitIds=data.unitIds();
String unit="";//提示信息
String user="";
for(int i=0;i<userIds.length;i++){
	user=user+userIds[i]+":"+data.userName(userIds[i]) +" ";	
}
for(int i=0;i<unitIds.length;i++){
	unit=unit+unitIds[i]+":"+data.unitName(unitIds[i]) +" ";
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>粘贴栏管理</title>
    
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
		var newName=null;
		newName=prompt("请输入粘贴栏名",name);
		while(newName==null || newName==" "){
			alert('输入的粘贴栏名不能为空');
			newName=prompt("请输入粘贴栏名",name);   		
		}		
		var cn=document.getElementById(id);
		cn.href="AdminManagerLogical?info=updatePaste&newName="+newName+"&id="+id;  
	}

	function insert(){
		var unit_name=null;
		var newType=null;
		var paste_name=prompt("请输入粘贴栏名称","");
		while(paste_name==null||paste_name==" "||paste_name==""){
			alert('输入的名字不能为空');
     		paste_name=prompt("请输入粘贴栏名称",name);     		
		}
		var unitName=prompt("请输入所属单位名称","");
		while(unitName==null ||unitName==" "||unitName==""){
			alert('输入的类别名不能为空');
     		unitName=prompt("请输入所属单位名称","");     		
		}
		var userName=prompt("请输入用户名：","");
		while(userName==null ||userName==" "||userName==""){
			alert('输入的类别名不能为空');
     		userName=prompt("请输入用户Id：","");     		
		}
		//alert("333");
		var cn=document.getElementById("add");
		cn.href="AdminManagerLogical?info=insertPaste&unitName="+unitName+"&paste_name="+paste_name+"&userName="+userName;  		
	}
</script>
  </head>
  
  <body>
  <jsp:include page="managertop.jsp" flush="true" />
  <p align="center" class="p">粘贴栏管理</p>
  
     <p align="center"> 
     	<a href=""  onclick="insert()"id="add">添加粘贴栏</a>
     </p>
  <br>
  <br>
   <table  align="center" >
    <tr align="center">
   		<td width="150" align="center">粘贴栏编号</td>     		
  	    <td width="200" align="center">粘贴栏名称</td>	
  	    <td width="150" align="center">单位id</td> 
  	    <td width="150" align="center">用户id</td>	    
    	<td width="150" align="center">开设时间</td>     	
  		<td width="100" align="center">操作  </td>  		
    </tr>
    <c:if test="${list!=null }">   
    <% 
   		for(int j=0;j<list.size();j++){
      		Post p=(Post)list.get(j);
      		int unitId=p.getUnitId();
   			String unitName=data.unitName(unitId);
   			unitName=unitId+":"+unitName;//同时显示类别标号和名称
   			
   			int userId=p.getUserId();
   			String userName=data.userName(userId);
   			userName=userId+":"+userName;//同时显示类别标号和名称
    %>
    <tr>
  	 		<td width="150" align="center"><%=p.getPostId()%></td>    	 		
  			<td width="200" align="center"><%=p.getPostName() %></td>	  			
  			<td width="150" align="center"><%=unitName%></td>
  			<td width="150" align="center"><%=userName%></td>
  			<td width="150" align="center"><%=p.getCreateTime() %></td>			
  			
  			<td>
  				<table width="100" >  
  				<tr align="center">	 		
    				<td width="50" align="center"><a href=" " onclick="change('<%=p.getPostId()%>','<%=p.getPostName() %>')"  id="<%=p.getPostId()%>">修改</a></td>
    				  		
     				<td width="50" align="center"><a href="AdminManagerLogical?info=delPaste&pasteId=<%=p.getPostId()%>">删除 </a></td>
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
