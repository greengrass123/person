<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*"%>
<%@ page language="java" import="java.util.*"%>
<jsp:directive.page import="allClasses.*" />
<jsp:directive.page import="jdbc.*" />
<jsp:directive.page import="tool.GetCurrentTime" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="css/Testupload.css" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<title>upLoad ads</title>
<script type="text/javascript">
$(function() {
	var path,
	FileReader = window.FileReader; 
	count=0;//记录图片张数
	$("#addButton").click(function(){
		//alert("add");
		var $tr=$("<tr></tr>");
		var $tdFile=$("<td align='center'></td>");
		var $file=$("<input type='file'>");//增加file
		$tdFile.append($file);
		var $dele=$("<td align='center'><input type='button' value='删除' ></td>");//增加删除按钮
		var $img=$("<input type='image' style='height:100;width:100;display:hidden'>");//样式	
 		$file.change(function() {//图片的路径改变时则在显示框显示图片
			var file=$file.val().toLowerCase();//取到的并不是图片在本机上的保存路径，但是可以通过这个格式判断是不是图片
			var end1=file.substring(file.length-4);
			var end2=file.substring(file.length-3);
			if(end1!='jpeg'&end2!='jpg'&end2!='bmp'&end2!='gif'){//不符合格式则提示并清除
				alert("只允许上传格式为jpg，jpeg，bmp，gif的文件");	
				$file.val('');			
			}
			else{
				if (window.File && window.FileReader && window.FileList && window.Blob) {
					var reader = new FileReader(),
					file = this.files[0]; 				
					reader.onload = function(e) {				
						var td=$("<td style='width:200'></td>");//声明一个<td>
						var src= e.target.result;//获取的图片路径 
						console.log(src);	 	
						//$img=$("<input name='image' type='image' src='"+src+"' style='"+style+"'>");//图片   
						$img.attr('src',src);	
						$img.style('display','block');			
					};
					reader.readAsDataURL(file);
				}
				else {
					path = $(this).val();
					if (/"\w\W"/.test(path)) {
						path = path.slice(1,-1);
					}
					alert(path);
					clip.attr("src",path);
					alert(clip[0].src);
				}
			}
		});
		$dele.click(function(){//删除按钮
			$tr.remove();
		}); 	
		 $("#ImgList").append($tr);
		  $tr.append($img).append($tdFile).append($dele);
	});	
});
function deleteAll(){//删除所有图片
	$("#ImgList").html("");
}
function check(){//上传前先检查上传图片个数，0~10
	//alert($(":image").size());
	if($(":image").size()>10){
		alert("一个广告中最多十张图片");
		return false;
	}
	if($(":image").size()<=0){
		alert("您尚未上传任何广告");
		return false;
	}
	return true;
}
</script>

</head>
<body>
	<!-- 将关于图片上传的信息都用form提交给服务器 -->
	<div align='center'>
		<input tabIndex=3 id='addButton' type='button' size=3 value='添加'>
		<input tabIndex=3 type='button' size=3 name=pic value='清除'
			onclick='deleteAll()'>
	</div>
	<!-- 图片展示区域 -->
	<form action="PostLogical?functionName=upLoad" method='post'
		onsubmit='return check();'>
		<div>
			<fieldset style="height:500;width:800">
				<legend>图片展示</legend>
				<table id="ImgList" align='left'>

				</table>
			</fieldset>
		</div>
		<!-- 获取用户上传时间，传递给服务器 -->
		<div class="but">
			<input type="submit" value="完成" class="button">
		</div>
	</form>
</body>
</html>