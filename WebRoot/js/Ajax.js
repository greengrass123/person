function showUnitsOfType() {//选择类别改变时会显示出对应类别的单位
	$("#selectOfUnits").html("");//改变类别之后首先清除单位、粘贴栏选择框中信息
	$("#textOfUnits").val("");
	$("#selectOfPosts").css("display", "none");
	$("#textOfPosts").val("");
	$("#textOfUnits").attr("disabled", ""); //防止由于上一个类别没有单位，导致将文本框设置为不可用
	$.post(//发送异步传输请求，获取对应类别的单位
	"PostLogical", {
		functionName : "unitsWithPublicPost",
		unitTypeId : $('#unitType :selected').val()
	}, function(returnedData, status) {//返回的是单位
		if (returnedData.length <= 0) {//如果返回的没有任何包含非专栏的单位，则在文本框显示当前类别没有公共粘贴栏
			$("#textOfUnits").val("当前类别没有公共粘贴栏");
			$("#textOfUnits").attr("disabled", "disabled");
		} else {
			$("#selectOfUnits").css("display", "block");//如果有单位则显示下拉列表框	
			for ( var i = 0; i < returnedData.length; i++) {
				var obj = returnedData[i];//获取obj中的每个对象
				var unitId = obj.unitId;
				var unitName = obj.unitName;
				var html = "<option id='" + unitId + "' value='" + unitId + "'>"
						+ unitName + "</option>";
				$("#selectOfUnits").append(html);
			}
		}
	});
}

function showPosts() {//所选择的粘贴栏改变时会显示出对应粘贴栏
	var text = $('#selectOfUnits :selected').text();
	//alert("text:"+text+" a");	
	$("#textOfUnits").val(text);//将选择列表中选中的值赋予文本框	 
	$("#textOfPosts").attr("disabled", "");//防止上次没有公共粘贴栏使粘贴栏文本框不可用 	
	$("#textOfPosts").text("");//清空上次显示的文本框内容
	$("#selectOfPosts").html("");//清空原有的粘贴栏
	$.post("PostLogical", {
		functionName : "publicPostsOfUnit",
		unitId : $('#selectOfUnits :selected').val()
	}, function(returnedData, status) {//返回的是粘贴栏
		if (returnedData.length <= 0) {//如果返回的没有任何包含非专栏的单位，则在文本框显示当前类别没有公共粘贴栏
			$("#textOfPosts").val("当前类别没有公共粘贴栏");
			$("#textOfPosts").attr("disabled", "disabled");
		} else {
			$("#selectOfPosts").css("display", "block");//如果有单位则显示下拉列表框	
			for ( var i = 0; i < returnedData.length; i++) {
				var obj = returnedData[i];//获取obj中的每个对象
				var postId = obj.postId;
				var postName = obj.postName;
				var html = "<option id='" + postId + "' value='" + postId
						+ "'>" + postName + "</option>";
				$("#selectOfPosts").append(html);
			}
		}
	});
}