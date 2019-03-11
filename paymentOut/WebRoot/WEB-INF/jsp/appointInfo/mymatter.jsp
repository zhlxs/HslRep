<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>南昌车管所</title>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${ctx}/css/css/reset.css">
    <link rel="stylesheet" href="${ctx}/css/css/search.css">
    <link rel="stylesheet" href="${ctx}/css/css/style.css">
    <%--<link rel="stylesheet" href="${ctx}/css/css/weui.min.css">--%>
    <%--<link rel="stylesheet" href="${ctx}/css/css/jquery-weui.css">--%>
    <style>
        .weui-dialog__bd{
            line-height: 40px;
        }
        .weui-tabbar__item.weui-bar__item_on .weui-tabbar__icon, .weui-tabbar__item.weui-bar__item_on .weui-tabbar__icon>i, .weui-tabbar__item.weui-bar__item_on .weui-tabbar__label {
    		color: #00a0e9;
		}
    </style>

</head>

<body>


<div>
    <div style="margin-top: 1em;margin-left: 15px">
        <span style="color: #8D8D8D;">预约信息</span>
    </div>
</div>

<div id="bw">
    <!-- <div id="info" class="weui-cells weui-cells_form" style="display: block;margin-top: 1em">

        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="userName">张凯</span>
            </div>

        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">办理网点：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="place">南昌车管所</span>
            </div>
            <div class="weui-cell__bd" id="cancel">
            <span style="color: #fe4a49;font-size: 18px;float: right">取消预约</span>
            <img src="img/cancel.png" alt="" style="margin-right:3px;width: 22px;height: 22px;float:right;">
            </div>

        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约日期：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="date">2018-07-02</span>
            </div>
            <div class="weui-cell__bd" id="cancel1">
            <span style="color: #fe4a49;font-size: 18px;float: right">评价投诉</span>
            <img src="img/cancel.png" alt="" style="margin-right:3px;width: 22px;height: 22px;float:right;">
            </div>


        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约业务：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="business">机动车注册登记</span>
            </div>
            <div class="weui-cell__bd" id="cancel2">
            <span style="color: #fe4a49;font-size: 18px;float: right">意见调查</span>
            <img src="img/cancel.png" alt="" style="margin-right:3px;width: 22px;height: 22px;float:right;">
            </div>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约时段：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="time">09:00-10:00</span>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">是否签到：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="isArrive">是</span>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">办理进度：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="schedule" style="color: #22AB3B">已办结</span>
            </div>
        </div>

    </div>

    <div class="weui-flex" style="margin-top: 15px">
        <div id="judge" class="button weui-flex__item">评论投诉</div>
        <div id="advice" class="button weui-flex__item">意见调查</div>
        <div id="cancel" class="button weui-flex__item">取消预约</div>
    </div> -->
</div>

<script src="${ctx}/js/js/jquery-2.1.4.js"></script>
<script src="${ctx}/js/js/fastclick.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
<script src="${ctx}/js/js/jquery-weui.js"></script>
<script>
        $.showLoading();
        <%
		RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
		%>
        var $name = "<%=weUserinfo.getUsername()%>";
        var $openId = "<%=weUserinfo.getWxOpenId()%>";
        var $card = "<%=weUserinfo.getIdCardNumber()%>";
        var $phone = "<%=weUserinfo.getPhoneNumber()%>";
        var isReg = false;
        if($name == 'null'||$card == 'null'){
            isReg = true;
            $.confirm({
                title:'提示',
                text:'您尚未注册，无法进行预约，点击确定前往注册',
                onOK:function () {
                    //跳转到注册页面
                    getHtml('${ctx}/wxUserAction/goRegister');
                },
                onCancel:function(){
                    getHtml('${ctx}/wxUserAction/toIndex');
                }
            });
        }else {
            $.ajax({
                url:'${ctx}/wx/appointInfoAction/appointInfoJson?openId=' + $openId,
                type:'post',
                dataType:'json',
                success:function (result) {
                    if(result.stateValue == null||result.stateValue == ''){
                        $("#bw").append("<p style='margin-top: 30px;color: red;text-align: center;font-weight: bold'>暂无预约信息</p>"+
                            "<div style='height: 30px;'></div>");
                        $.hideLoading();
                    }else{
                        $.each(result.stateValue,function(index,obj){
                            if(obj.status == 1){
                                $("#bw").append("<div id='info' class='weui-cells weui-cells_form' style='display: block;margin-top: 1em'>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：' + "</label>" +  "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='userName'>" + obj['name'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '办理网点：' + "</label>" +"</div>"+ "<div class='weui-cell__bd'>" +
                                    "<span class='weui-input' id='place'>" + obj['deptName'] + "</span>" +
                                    "</div>" +"</div>" + "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约日期：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='date'>" + obj['appTime'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约业务：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='business'>" + obj['serName'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约时段：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='time'>" + obj['timeInter'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '是否签到：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='isArrive'>" + '否' + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '办理进度：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='schedule' style='color: #22AB3B;'>" + '预约成功' + "</span>" + "</div>" + "</div>" +
                                    "</div>" + "<div class='weui-flex' style='margin-top: 15px'>" + "<div id='cancel' class='button weui-flex__item' onclick='cancelAppoint(this)'>" +
                                    "<input type='hidden' value="+ obj['id'] +">" + '取消预约' + "</div>" + "</div>");
                                $.hideLoading();
                            }else if(obj.status == 2){
                                $("#bw").append("<div id='info' class='weui-cells weui-cells_form' style='display: block;margin-top: 1em'>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：' + "</label>" +  "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='userName'>" + obj['name'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '办理网点：' + "</label>" +"</div>"+ "<div class='weui-cell__bd'>" +
                                    "<span class='weui-input' id='place'>" + obj['deptName'] + "</span>" +
                                    "</div>" +"</div>" + "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约日期：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='date'>" + obj['appTime'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约业务：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='business'>" + obj['serName'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约时段：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='time'>" + obj['timeInter'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '是否签到：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='isArrive'>" + '是' + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '办理进度：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='schedule' style='color: #22AB3B;'>" + '已经签到' + "</span>" + "</div>" + "</div>" +
                                    "</div>");
                                $.hideLoading();
                            }else if(obj.status == 3){
                                $("#bw").append("<div id='info' class='weui-cells weui-cells_form' style='display: block;margin-top: 1em'>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：' + "</label>" +  "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='userName'>" + obj['name'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '办理网点：' + "</label>" +"</div>"+ "<div class='weui-cell__bd'>" +
                                    "<span class='weui-input' id='place'>" + obj['deptName'] + "</span>" +
                                    "</div>" +"</div>" + "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约日期：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='date'>" + obj['appTime'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约业务：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='business'>" + obj['serName'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约时段：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='time'>" + obj['timeInter'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '是否签到：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='isArrive'>" + '是' + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '办理进度：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='schedule' style='color: #EC6617;'>" + '已经完结' + "</span>" + "</div>" + "</div>" +
                                    "</div>"+"<div class='weui-flex' style='margin-top: 15px'>" + "<div id='judge' class='button weui-flex__item' onclick='appraiseAppoint(this)'>" +
                                    "<input type='hidden' value="+ obj['id'] +">" + "<input type='hidden' value="+ obj['deptId'] +">" + '评论投诉' + "</div>" + "</div>");
                                $.hideLoading();
                            }else if(obj.status == 4){
                                $("#bw").append("<div id='info' class='weui-cells weui-cells_form' style='display: block;margin-top: 1em'>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：' + "</label>" +  "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='userName'>" + obj['name'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '办理网点：' + "</label>" +"</div>"+ "<div class='weui-cell__bd'>" +
                                    "<span class='weui-input' id='place'>" + obj['deptName'] + "</span>" +
                                    "</div>" +"</div>" + "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约日期：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='date'>" + obj['appTime'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约业务：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='business'>" + obj['serName'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '预约时段：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='time'>" + obj['timeInter'] + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '是否签到：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='isArrive'>" + '是' + "</span>" + "</div>" + "</div>" +
                                    "<div class='weui-cell'>" + "<div class='weui-cell__hd'>" + "<label class='weui-label'>" + '办理进度：' +"</label>" + "</div>" +
                                    "<div class='weui-cell__bd'>" + "<span class='weui-input' id='schedule' style='color: #EC6617;'>" + '已经完结' + "</span>" + "</div>" + "</div>" +
                                    "</div>");
                                $.hideLoading();
                            }
                        });
                    }
                }
            });
            //取消预约的方法
            function cancelAppoint(obj){
                var id = $(obj).find("input:eq(0)").val();
                var ajaxData = 'appointmeId=' + id;
                $.confirm({
                    title:'提示',
                    text:'您确定要取消此次预约吗？',
                    onOK:function () {
                        //$.showLoading();
                        $.ajax({
                            url:'${ctx}/phone/wxAppointmeController/cancelAppointme',
                            type:'post',
                            dataType:'json',
                            data:ajaxData,
                            success:function () {
                                //$.hideLoading();
                                $.toast("操作成功");
                                getHtml('${pageContext.request.contextPath}/wx/appointInfoAction/appointInfoView');
                            }
                        });
                    },
                    onCancel:function () {

                    }
                });
            }
            //评论投诉的方法
            function appraiseAppoint(obj){
                var id = $(obj).find("input").val();
                var deptId = $(obj).find("input:eq(1)").val();
                $.confirm({
                    title:'提示',
                    text:'您是否愿意对我们的服务作出评价？',
                    onOK:function () {
                        $.showLoading();
                        getHtml('${ctx}/wx/appraiseDetailAction/apprList?appointmeId=' + id +'&deptId=' + deptId);
                        $.hideLoading();
                    },
                    onCancel:function () {

                    }
                });
            }
        }
</script>
</body>
</html>
