//根据滚动条所滚动的高度来决定每次加载图片的个数 
 window.onscroll = function(){	 
    //document.body是DOM中Document对象里的body节点,document.documentElement是文档对象根节点(html)的引用,有些标准中不支持document.body.scrollTop 
    var currentScrollTop = document.documentElement.scrollTop || document.body.scrollTop;
   //alert(currentScrollTop);
   // alert($("#nav").offset().top);
	if (currentScrollTop > $("#nav").offset().top) {//$("#nav").offset().top元素离顶部的距离
		//alert("a");
		//navFixed.style.display = 'block';
		  $("#navFixed").css("display","inline");     
	} else {
		//alert("b");
		//navFixed.style.display = 'none';
		$("#navFixed").css("display","none");
	}
    var windowHeight=$(window).height();//当前屏幕高度
    var ifShowReturnTop=(currentScrollTop/windowHeight)>=1/3?true:false;//滚动条是否到屏幕1/3位置
    if(ifShowReturnTop) {//如果滚动条到屏幕1/3位置则出现返回顶部的链接
       $("#returnTop").css("display","inline");      
    } else {
        $("#returnTop").css("display","none");
    }
    
    //如果isAnyMorePic记录的值为false,则表示数据库已经没有图片可供加载，无需发送请求   	   
    var isAnyMorePics=$("#isAnyMorePics").val();
    //alert(isAnyMorePics);
    var recordScrollTop=$("#recordScrollTop").val();//记录上一次滚动条所在位置			
    var picNumOfEveryLoading=$("#picNumOfEveryLoading").val();//每次加载图片个数
	var move=(currentScrollTop-recordScrollTop)/windowHeight;//这次相对上一次的移动值相对屏幕高度的比例
	//alert("move"+move);
	if (move>=1/3?true:false){
		if (isAnyMorePics=='true'){//如果移动超过相对屏幕高度的1/3，则发送请求下载picNumOfEveryLoading*(move*3)张图片[move/(1/3)=move*3]
			$("#recordScrollTop").val(currentScrollTop);//超过1/3进行相应处理后，将原有的记录值改为现在的滚动条高度
			$("#isAnyMorePics").val("false"); //在处理完这次之前不会接受下一个请求，以防止短时间内移动多次，而数据冲突
			var count = parseInt(move*3);//看移动的距离是1张图片的几倍，决定下载几个picNumOfEveryLoading张图片
			//alert("count:"+count);
			var begin = $(":image").size();//计算出当前图片个数，从第begin个图片加载   
			//alert("begin:"+begin);
			var number = count * picNumOfEveryLoading;//加载number个图片
			//alert(count+"-"+begin+"-"+end);		 
			$.post("UserLogical", {
				functionName : "adsOfPrivatePost",
				postId : $('#postId').val(),
				adTypeId : $('#adTypeId').val(),
				begin : begin,
				number : number
			}, function(returnedData, status) {
				//处理本次数据，添加所获取的图片
				if(returnedData.length>0){//如果返回的数据长度大于0，则显示图片					 
					showAds(returnedData);					
				}
				//alert("returnedData.length:"+returnedData.length);
				//如果本次返回的广告数，等于一次加载的量，则表示数据库有对应广告，将isAnyMorePics的值改为true，下次可以继续加载
				if(returnedData.length==picNumOfEveryLoading){
					//alert(returnedData.length+"<"+picNumOfEveryLoading);
					$("#isAnyMorePics").val("true");
					//alert($("#isAnyMorePics").val());
				}				
			});	
		
		}		
	}   
	 
};