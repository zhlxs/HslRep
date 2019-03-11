<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>个人中心</title>
    <link rel="stylesheet" href="${ctx}/css/css/reset.css">
    <link rel="stylesheet" href="${ctx}/css/css/weui.css">
    <link rel="stylesheet" href="${ctx}/css/css/jquery-weui.css">
    <script src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <script src="${ctx}/js/js/swiper.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
    <style>
        body{
            background: #F8F8F8;
        }
        .pinch-zoom, .pinch-zoom img {
            width: 100%;
            -webkit-user-drag: none;
        }
        .weui-cell__hd>img{
            width: 20px;
            height: auto;
            margin-right:5px;
            display:block;
        }
    </style>
</head>
<body>

        <article class="r-m301">
            <div class="weui-cells">
                <a class="weui-cell weui-cell_access" href="javascript:void(0);" onclick="getHtml('${pageContext.request.contextPath}/wx/appointInfoAction/appointInfoView')">
                    <div class="weui-cell__hd"><img src="${ctx}/img/wdzp.png" alt=""></div>
                    <div class="weui-cell__bd" style="padding: 5px 3px;">
                        <span>我的事项</span>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>

            </div>
            <div class="weui-cells" style="margin-top: 0">
                <a class="weui-cell weui-cell_access" href="javascript:void(0);" onclick="isReg()">
                    <div class="weui-cell__hd"><img src="${ctx}/img/grxx.png" alt=""></div>
                    <div class="weui-cell__bd" style="padding: 5px 3px;">
                        <span>个人信息</span>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>

            </div>
            <div class="weui-cells">
                <a class="weui-cell weui-cell_access" href="tel:88536171">
                    <div class="weui-cell__hd"><img src="${ctx}/img/kfdh.png" alt="" style="width:20px;"></div>
                    <div class="weui-cell__bd" style="padding: 5px 3px;">
                        <span>咨询电话&nbsp;&nbsp;&nbsp;&nbsp;0791-3723695</span>
                    </div>
                    <div class="weui-cell__ft"></div>
                    <!--<img src="img/look.png" alt="" style="transform: scale(0.3)">-->
                </a>
                <a class="weui-cell weui-cell_access" href="tel:18172801110">
                    <div class="weui-cell__hd"><img src="${ctx}/img/zxsj.png" alt="" style="transform: scale(0.6)"></div>
                    <div class="weui-cell__bd" style="padding: 5px 3px;">
                        <span>咨询手机 18172801110</span>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>
            </div>
            <div class="weui-cells" style="margin-top: 0">
                <div class="weui-cell">
                    <div class="weui-cell__hd"><img src="${ctx}/img/kfsj.png" alt=""></div>
                    <div class="weui-cell__bd" style="padding: 5px 3px;">
                        <span>服务时间 9:00-17:00</span>
                    </div>
                    <div class="weui-cell__ft"></div>
                </div>
            </div>
        </article>
</body>
<script>
<%
RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
%>
function isReg(){
	var $name = "<%=weUserinfo.getUsername()%>";
	var $card = "<%=weUserinfo.getIdCardNumber()%>";
	if($name == 'null'||$card == 'null'){
		$.confirm({
		    title:'提示',
		    text:'您尚未注册，无法查看信息，点击确定前往注册',
		    onOK:function () {
		        //跳转到注册页面
		    	//window.location.href = "${ctx}/wxUserAction/goRegister";
		    	getHtml('${ctx}/wxUserAction/goRegister');
		    },
		    onCancel:function(){
		    	return false;
		    }
		});
	}else{
		getHtml('${ctx}/wxUserAction/userInfo');
	}
}
</script>
</html>