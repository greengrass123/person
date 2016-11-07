<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="allClasses.*"/>
<jsp:directive.page import="logicalConduct.*"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

List list=(List)request.getAttribute("list");
AdminLogic data=new AdminLogic();
int[] pasteTypes=data.pasteTypes();
String pasteType="";
for(int i=0;i<pasteTypes.length;i++){
	pasteType=pasteType+pasteTypes[i]+":"+data.typeName(pasteTypes[i]) +" ";	
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>单位管理</title>
    
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
	function change(id,name,type,pasteType){
		var newName=null;
		var newType=null;
		newName=prompt("请输入你要修改的名字",name);
		while(newName==null||newName==" "||newName==""){
			alert('输入的名字不能为空');
     		newName=prompt("请输入你要修改的单位名",name);     		
		}
		newType=prompt("请输入你要修改的类别  "+pasteType,type);
		while(newType==null ||newType==" "||newType==""){
			alert('输入的类别名不能为空');
     		newType=prompt("请输入你要修改的类别",type);     		
		}
	/*	//判断输入的类别newType是否出界
		var flag=0;
		for(i=0;i<pasteTypes.length;i++){
			if(newType==pasteTypes[i])//如果类别Id正确则返回true
			{
				flag=1;
				break;
			}				
		}
		alert('flag:'+flag);
		if(flag==0){
			alert('输入的粘贴栏类别id不在数据库范围内');
			//history.go(-1);
		}
		else{
		*/
			var cn=document.getElementById(id);
			cn.href="AdminManagerLogical?info=updateUnit&newName="+newName+"&id="+id+"&newType="+newType;  
	//	}		
		
	}
	
		function insert(pasteType){
		var unit_name=null;
		var newType=null;
		//var paste_type = null;
		var unit_name=prompt("请输入单位名称","");
		while(unit_name==null||unit_name==" "||unit_name==""){
			alert('输入的名字不能为空');
     		unit_name=prompt("请输入单位名称",name);     		
		}
	    //alert("1111");
		var paste_type=prompt("请输入单位类别"+pasteType,"");
		//alert("222");
		while(paste_type==null ||paste_type==" "||paste_type==""){
			alert('输入的类别名不能为空');
     		paste_type=prompt("请输入你要修改的类别"+pasteType,type);     		
		}
		///alert("333");
		var cn=document.getElementById("addUnit");
		cn.href="AdminManagerLogical?info=insertUnit&unit_name="+unit_name+"&paste_type="+paste_type;  		
	}
	
	
	
</script>
  </head>
  
  <body>
  <jsp:include page="managertop.jsp" flush="true" />
  <p align="center" class="p">单位管理</p>
  <p align="center">
      <a href=""  onclick="insert('<%=pasteType%>')"  id="addUnit" >添加新单位</a>
     </p>
  <br>
  <br>
   <table  align="center" >
    <tr align="center">    
   		<td width="150" align="center">单位编号</td>   		
  	    <td width="150" align="center">单位名称</td>  	    
    	<td width="150" align="center">类别</td>     	
  		<td width="200" align="center">操作  </td>
  		<td></td>
    </tr>
    <c:if test="${list!=null }">   
    <% 
   		for(int j=0;j<list.size();j++){
   			Unit u=(Unit)list.get(j); 
   			int typeId=u.getUnitTypeId();
   			String typeName=data.typeName(typeId);
   			typeName=typeId+":"+typeName;//同时显示类别标号和名称
    %>
    <tr>
  	 		<td width="150" align="center"><%=u.getUnitId()%></td>  	 		
   	 		<td width="150" align="center"><%=u.getUnitName()%></td>   			
  			<td width="150" align="center"><%=typeName%></td>
  			<td>
  				<table width="200" >  
  				<tr align="center">	 		
    				<td width="100" align="center"><a href="" onclick="change('<%=u.getUnitId()%>','<%=u.getUnitName() %>','<%=u.getUnitTypeId()%>','<%=pasteType%>')"  id="<%=u.getUnitId()%>" >修改</a></td>
    				  		
     				<td width="100" align="center"><a href="AdminManagerLogical?info=delUnit&unitId=<%=u.getUnitId() %>">删除 </a></td>
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
