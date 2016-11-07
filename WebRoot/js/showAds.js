
function showAds(returnedData){//根据返回的图片信息（json对象）在图片框显示
	var count = 0;
	$("#ads").append("<tr>");
	for ( var i = 0; i < returnedData.length; i++) {
		var ad = returnedData[i];
		var adId = ad.adId;
		var firstPicAddr = ad.firstPicAddr;
		// alert("adId:"+adId);
		// alert("firstPicAddr:"+firstPicAddr);
		var link = "PostLogical?functionName=picsOfAd&adId=" + adId;
		var html = "<td><a href='" + link
				+ "' target='_blank'><input type='image' alt='查看' src='"
				+ firstPicAddr + "' id='" + adId + "' class='img' style='width:250;height:250'/></a></td>";
		// alert(html);
		$("#ads tr:last").append(html);
		count++;
		if (count % 5 == 0) {// 到了五个则换行
			$("#ads").append("</tr><tr> ");
		}
	}
	 
}