<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
    import="java.sql.*"%>
<%@ page language="java" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<jsp:directive.page import="jdbc.*" />
<jsp:directive.page import="allClasses.*" />
<jsp:directive.page import="configurations.*" />
<%
    String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    List<Ad> ads=(List<Ad>)request.getAttribute("ads");
 Configuration configuration=new Configuration();
 int picWidth=200;//  获取限定的每张图片高度与宽度
 int picHeight=200;
 int picNumOfEveryLoading=configuration.PICNumOFEVERYLOADING;//每次加载图片张数
 int picsInOneRow=5;//每行显示图片张数
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<base href="<%=basePath%>">
<title>body of the post</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<link rel="stylesheet" href="css/post.css" type="text/css" />
<script src="js/post/post_jquery.min.js"></script>
<script src="js/post/post_jquery-ui.min.js"></script>
<script src="js/post/imgbubbles.js"></script>
<!-- rounded corners for IE -->
<script src="js/post/DD_roundies_0.0.2a-min.js"></script>
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="js/showAds.js"></script>
<link rel="stylesheet" type="text/css" href="css/bodyOfPost.css">
 
 

<script type="text/javascript">
    
    function returnTop() {//将滚动条滚到顶部，从而整个页面也回到最顶部
        $(document).scrollTop(0);
    }
    DD_roundies.addRule("#nav", "5px");
    DD_roundies.addRule("#nav li", "5px");
    $(document).ready(function() {   
        var $adTypeId = $("#adTypeId").val();//获取当前单位id 
        var $againadTypeId= $adTypeId+'again';
        //alert($againadTypeId);
        $("#"+$adTypeId+"").css('background-color','#FFAA8C');
        $("#"+$adTypeId+"").css('color','#F3F3F3');
        $("#"+$adTypeId+"").css('border-radius','5px');
        $("#"+$againadTypeId+"").css('background-color','#FFAA8C');
        $("#"+$againadTypeId+"").css('color','#F3F3F3');
        $("#"+$againadTypeId+"").css('border-radius','5px');
        //alert($("#"+$adTypeId+"").text());
        var obj = $("#user");//判断是否有Id为user的控件，如果有则表示已经登陆
        if (obj.length > 0) {
            var postId = $("#postId").val();//获取postId
            //alert(postId);
            var link = 'UserLogical?functionName=attention&postId=' + postId;
            var html = "<li><a href='"+link+"'>关注本栏</a></li>";
            $("#top").append(html);
        }
        $nav_li = $("#nav li");
        $nav_li_a = $("#nav li a");
        var animSpeed = 450; //fade speed
        var hoverTextColor = "#fff"; //text color on mouse over
        var hoverBackgroundColor = "#FFAA8C"; //background color on mouse over
        var textColor = $nav_li_a.css("color");
        var backgroundColor = $nav_li.css("background-color");
        //text color animation
        $nav_li_a.hover(function() {
            var $this = $(this);
            $this.stop().animate({
                color : hoverTextColor
            }, animSpeed);
        }, function() {
            var $this = $(this);
            $this.stop().animate({
                color : textColor
            }, animSpeed);
        });
        //background color animation
        $nav_li.hover(function() {
            var $this = $(this);
            $this.stop().animate({
                backgroundColor : hoverBackgroundColor
            }, animSpeed);
        }, function() {
            var $this = $(this);
            $this.stop().animate({
                backgroundColor : backgroundColor
            }, animSpeed);
        });
    });

</script>

</head>

<body bgcolor="#F3F3F3">
<div class='info'>
<span>${unitName} -> ${post.postName}</span>
<span>今日访问量：<em>${post.visitorsOfToday}</em></span>  
<span>总访问量:<em>${post.allVisitors}</em></span>
</div>
    <!--单位名、粘贴栏名 -->
    <input type="hidden" id="recordScrollTop" value="0">
    <!-- 记录上一次滚动条高度的隐藏域 -->
    <input type="hidden" id="picNumOfEveryLoading"
        value="<%=picNumOfEveryLoading%>">
    <!--picNumOfEveryLoading为每次加载图片个数的隐藏域 -->
    <input type="hidden" id="postId" value="${post.postId}">
    <!-- 记录postId的隐藏域 -->
    <input type="hidden" id="adTypeId" value="${adTypeId}">
    <!-- 记录当前类别adTypeId的隐藏域 -->
    <c:set var="picsInOneRow" value="<%=picsInOneRow%>"></c:set>
    <!--picsInOneRow为每行显示图片张数-->
    <c:set var="picNumOfEveryLoading" value="<%=picNumOfEveryLoading%>"></c:set>
    <!--picNumOfEveryLoading为每次加载图片个数 -->
    <input type="hidden" id="isAnyMorePics"
        value="${fn:length(ads)<picNumOfEveryLoading? false:true }">
    <!-- 记录该类别是否还有图片可供加载 -->

    <!--将当前Post、adTypeId存放在session，在上传时可调取-->
    <c:set var='post' value='${post}' scope='session'></c:set>
    <!-- 如果传入的ID是0则将其改为1 -->
    <c:set var='adTypeId' value='${adTypeId}' scope='session'></c:set>  
    <table align="center" cellpadding="0" width="95%" id="adType">
        <tr>
            <div id="nav">
                <ul id='adType'>
                    <!-- 进入自己的专栏与浏览其他粘贴栏都是共用一个body，因此在点击类别的链接时应先判断是在哪一种情况-->
                  
                    <c:if test="${param.functionName eq 'enterPost'}">
                        <!-- 标示不是专栏 -->
                        <c:set var='privatePost' value='false' scope='session'></c:set>
                        <li>                            
                          <a href="PostLogical?functionName=enterPost&postId=${post.postId}&adTypeId=0" id=0>所有广告</a>
                        </li>
                    </c:if>
                    <c:if test="${param.functionName eq 'privatePost'}">
                        <!-- 标示是专栏 -->
                        <li id="all"><a href="UserLogical?functionName=privatePost&adTypeId=0" id=0>所有广告</a></li>
                    </c:if> 
                    <c:if test="${fn:length(adTypes)>0}">
                        <!--如果有其他广告类别则显示fn:length(adTypes)>0-->
                        <c:forEach var="item" items="${adTypes}" varStatus="adType">
                            <c:set var="adTypeId" value="${item.adTypeId}"></c:set>
                            <li>
                                <c:if test="${param.functionName eq 'enterPost'}">
                                    <a href="PostLogical?functionName=enterPost&postId=${post.postId}&adTypeId=${adTypeId}" id='${item.adTypeId}'>${item.adTypeName}</a>
                                </c:if> 
                                <c:if test="${param.functionName eq 'privatePost'}">
                                    <a href="UserLogical?functionName=privatePost&adTypeId=${adTypeId}" id='${item.adTypeId}'>${item.adTypeName}</a>
                                </c:if>
                            </li>
                        </c:forEach>
                    </c:if>
                     
                </ul>
                <div class="clear"></div>
            </div>
            <div class="nav-wrapper-fixed" id="navFixed" style="display:none;">
                <div id="nav">
                    <ul>
                        <!-- 进入自己的专栏与浏览其他粘贴栏都是共用一个body，因此在点击类别的链接时应先判断是在哪一种情况-->
                        <c:if test="${param.functionName eq 'enterPost'}">
                            <li id="all"><a href="PostLogical?functionName=enterPost&postId=${post.postId}&adTypeId=0" onclick='' id='0again'>所有广告</a>
                            </li>
                        </c:if>
                        <c:if test="${param.functionName eq 'privatePost'}">
                            <li id="all"><a href="UserLogical?functionName=privatePost&adTypeId=0" id='0again'>所有广告</a>
                            </li>
                        </c:if>
                        <c:if test="${fn:length(adTypes)>0}">
                            <!--如果有其他广告类别则显示-->
                            <c:forEach var="item" items="${adTypes}" varStatus="adType">
                                <c:set var="adTypeId" value="${item.adTypeId}"></c:set>
                                <li><c:if test="${param.functionName eq 'enterPost'}">
                                        <a  href="PostLogical?functionName=enterPost&postId=${post.postId}&adTypeId=${adTypeId}" id='${adTypeId}again'>${item.adTypeName}</a>
                                    </c:if> <c:if test="${param.functionName eq  'privatePost'}">
                                        <a  href="UserLogical?functionName=privatePost&adTypeId=${adTypeId}" id='${adTypeId}again'>${item.adTypeName}</a>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>

                    <div id="clearId" class="clear"></div>
                </div>
            </div>
        </tr>
        </table>
        <div id="ads" class="ads">
        
         
            <c:if test="${fn:length(ads)>0}">
            
                
                    
                <c:forEach var="ad" items="${ads}" varStatus="status">
                     
                     <div class="ad_cell">
                      <a href="PostLogical?functionName=picsOfAd&adId=${ad['adId']}&postId=${post.postId}" target="_blank">
                        <img  class="img" alt="点击查看" src="${ad['firstPicAddr']}"
                        id="${ad['adId']}"/></a>                     
                    </div>
                      
                     
                   
                </c:forEach>
                <div style="clear:both"></div>                       
            </c:if>
            
            <c:if test="${fn:length(ads)<=0 }">
                <div class="adpost_none"><img src="images/adpost_none.jpg"></div>            
            </c:if>

    </div>
    <div id="returnTop" class="returnTop" onclick="returnTop() ">返回顶部</div>
    <!-- 返回顶部的小链接 -->
   <!--  <div style="height:1000"></div> -->
    

</body>
</html>
