<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%--<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>网上车管所</title>
    <link rel="stylesheet" href="${ctx}/css/css/reset.css">
    <link rel="stylesheet" href="${ctx}/css/css/order.css">
    <!--<link rel="stylesheet" href="css/j-select.css">-->
    <%-- <link rel="stylesheet" href="${ctx}/css/css/weui.min.css">
    <link rel="stylesheet" href="${ctx}/css/css/jquery-weui.css"> --%>
    <!--<link rel="stylesheet" href="css/reveal.css">-->
    <!--<link rel="stylesheet" href="css/style.css">-->
    <script src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <!--<script src="js/jquery-nicescroll.js"></script>-->
    <!--<script src="js/My97DatePicker/WdatePicker.js" ></script>-->
    <script src="${ctx}/js/js/swiper.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
    <style>
        .weui-cells_checkbox .weui-icon-checked:before {
            content: "\EA01";
            color: #E29D36;
            font-size: 23px;
            display: block;
        }
        .weui-cells_checkbox .weui-check:checked+.weui-icon-checked:before {
            content: "\EA06";
            color: #E29D36;
        }
    </style>
</head>
<body>
<article class="r-m301" style="background: #FEFCED;">
    <!--<div class="header">-->
    <!--申请预约-->
    <!--</div>-->

    <!--头部-->
    <div class="wrap">
        <div class="wrap_left">
            <img src="${ctx}/img/tips.png" alt="" style="transform: scale(0.8);vertical-align: middle">
            <span style="font-size: 18px;vertical-align: middle">温馨提示</span>
        </div>
        <div class="wrap_right" onclick="jumpNotice()">
            <img src="${ctx}/img/question.png" alt="" style="left: 5px;">
            <img src="${ctx}/img/notice.png" alt="" style="right: 8px;">
        </div>
        <ul class="wrap_fix" onclick="getHtml('${ctx}/wxUserAction/toIndex')">
            <li style="height: inherit">
                <img src="${ctx}/img/aback.png" alt="" style="transform: scale(0.5);margin-left: 10px;margin-top: -2px">
            </li>
            <li>
                <span style="color: #fff;vertical-align: -webkit-baseline-middle;margin-right: 15px">返回</span>
            </li>
        </ul>
        <p class="text">以下填写内容必须真实有效，如有虚假、瞒报，由本人自行承担一切法律后果；对提供不实数据的申报人，平台将锁定用户，不再提供服务</p>
    </div>
    <!--头部end-->

    <!--办理信息-->
    <div class="weui-cells" style="overflow: visible;margin-top: 0">
        <div class="weui-cell" style="color: #919191;font-size: 14px">
            <span style="padding: 5px 3px;">办理信息</span>
        </div>

        <a class="weui-cell weui-cell_access" href="javascript:void(0);">
            <div class="weui-cell" style="padding: 5px 3px;">
                <span>办证网点</span>
            </div>
            <div class="weui-cell__hd" style="margin-left: 20px">
                <img src="${ctx}/img/gps.png" alt="" style="width:20px;display: inline-block">
                <!-- <span style="">南昌车管所</span> -->
            </div>
            <div class="weui-cell__bd">
                <input style="text-align: left;position: relative;top: 0" class="weui-input" id="mobile" type="text" value="" readonly="readonly" placeholder="请选择办理网点">
            </div>
        </a>

        <!--<a class="weui-cell weui-cell_access" href="javascript:void(0);">-->
        <div class="weui-cell">
            <div class="weui-cell__hd" style="padding: 5px 3px;">
                <span>办理日期</span>
            </div>
            <div class="weui-cell__bd" style="margin-left: 20px">
                <div>
                    <input class="weui-input" type="text" id="my-input" data-toggle="date" placeholder="请选择办理日期" onchange="judgeWeek()"/>
                    <!--<input id="txtEndDate" class="Wdate" type="text" onClick="WdatePicker({minDate:'%y-%M-{%d+1}',disabledDays:[0,6]})" style="font-size: 16px;width:170px;padding:7px 10px;border:1px solid #ccc;">-->
                </div>
            </div>
        </div>

        <!--</a>-->
    </div>
    <!--办理信息end-->

    <!--所需材料-->
    <div class="weui-cells" style="overflow: visible;margin-top: 0">
        <div class="weui-cell" style="color: #919191;font-size: 14px">
            <span style="padding: 5px 3px;">选择办理业务</span>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd" style="padding: 5px 3px;">
                <span>预约业务</span>
            </div>
            <div class="weui-cell__bd" style="margin-left: 20px">
                <input id="business" type="text" class="weui-input" placeholder="请选择需要预约的业务" readonly="readonly">
            </div>
        </div>
    </div>
    <!--所需材料end-->

    <!--办理时间段-->
    <div class="weui-cells" style="overflow: visible;margin-top: 0">

        <div class="weui-cell" style="color: #919191;font-size: 14px">
            <span style="padding: 5px 3px;">办理时间段</span>
        </div>

        <div class="weui-cells weui-cells_checkbox" style="margin-top: 0">
            <!-- <label class="weui-cell weui-check__label" for="x11">
                <div class="weui-cell__hd">
                    <input type="radio" class="weui-check" name="radio1" id="x11" onclick="addProperty(this)">
                    <span class="weui-icon-checked"></span>
                </div>
                <div class="weui-cell__bd">
                    <p>09:00-10:00</p>
                </div>
                <div class="weui-cell__ft">
                    <p class="number">
                        可预约人数<span>4</span>人
                    </p>
                </div>
            </label>
            <label class="weui-cell weui-check__label" for="x12">
                <div class="weui-cell__hd">
                    <input type="radio" name="radio1" class="weui-check" id="x12" onclick="addProperty(this)">
                    <span class="weui-icon-checked"></span>
                </div>
                <div class="weui-cell__bd">
                    <p>10:00-11:00</p>
                </div>
                <div class="weui-cell__ft">
                    <p class="number">
                        可预约人数<span>5</span>人
                    </p>
                </div>
            </label>
            <label class="weui-cell weui-check__label" for="x13">
                <div class="weui-cell__hd">
                    <input type="radio" name="radio1" class="weui-check" id="x13" onclick="addProperty(this)">
                    <span class="weui-icon-checked"></span>
                </div>
                <div class="weui-cell__bd">
                    <p>11:00-12:00</p>
                </div>
                <div class="weui-cell__ft">
                    <p class="number">
                        可预约人数<span>6</span>人
                    </p>
                </div>
            </label> -->
        </div>
    </div>
    <!--办理时间段end-->

    <!--办理人信息-->
    <div class="weui-cells" style="overflow: visible;margin-top: 0">
        <div class="weui-cell" style="color: #919191;font-size: 14px">
            <span style="padding: 5px 3px;">办理人信息</span>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">姓名</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="username" type="text" placeholder="请输入姓名" value="">
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">身份证号码</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="useridcard" type="text" placeholder="请输入身份证号码" value="">
            </div>
        </div>
    </div>
    <!--办理人信息end-->
</article>
<div class="weui-btn-area">
    <a style="background: #118EEA;" data-reveal-id="myModal" data-animation="fade" class="weui-btn weui-btn_primary" href="javascript:void(0);"
       id="showTooltips">申请预约</a>
</div>
<!--<div id="myModel" class="reveal-modal" style="opacity: 0.9;">-->
<!--<h1>当前时间段预约人数为：<span style="color: red;font-weight: bold;">8</span>人</h1>-->
<!--<h1>是否继续预约？</h1>-->
<!--<p class="weui-btn-area">-->
<!--<a href="javascript:;" class="weui-btn weui-btn_primary">确认</a>-->
<!--<a href="javascript:;" class="weui-btn weui-btn_default">取消</a>-->
<!--</p>-->
<!--<a class="close-reveal-modal">&#215;</a>-->
<!--</div>-->
    <input id="dID" type="hidden" value="${departmentId}">
    <input id="dName" type="hidden" value="${departmentName}">
</body>
<script type="text/javascript">
    <%
    RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
    %>
    window.addEventListener("popstate", function (e) {
        <%--alert("我监听到了浏览器的返回按钮事件啦");--%>
        // 根据自己的需求实现自己的功能
        getHtml('${ctx}/wxUserAction/toIndex');
    });
    var datalist = new Array();
    var department = new Array();
    var depid = new Array();
    var departmentId;
    var depID = "${deptid}";
    var depname = "${deptname}";
    if(depID != undefined && depname != ""){
        $("#mobile").val(depname);
        departmentId = depID;
    }
    var $name = "<%=weUserinfo.getUsername()%>";
    var $card = "<%=weUserinfo.getIdCardNumber()%>";
    var $phone = "<%=weUserinfo.getPhoneNumber()%>";
    var isReg = false;
    //前往办理须知页面
    function jumpNotice(){
    	 getHtml('${ctx}/wxUserAction/businessnotice?serCode=${serCode}&businessType=${businessType}');
    }
    //判断是否注册
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
    }else{
        $("#username").val($name);
        $("#useridcard").val($card);
    }
    //ajax加载部门
    $.ajax({
        url:'${ctx}/deptAddressAction/getDeptAddress',
        type:'post',
        dataType:'json',
        success:function(data){
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    department[i] = data[i].deptname;
                    depid[i] = data[i].deptid;
                    //console.log(data[i].deptName);
                    var info = { "title": department[i], "value": depid[i]};
                    datalist.push(info);
                }
                //console.log(datalist[0].title);
                $("#mobile").select({
                    title: "请选择办理网点",
                    textAlign: 'center',
                    multi: false,
                    items: datalist,
                    beforeClose:function(){
                        //console.log($("#mobile").attr("data-values"));
                        departmentId = $("#mobile").attr("data-values");
                    }
                })
            }
        }
    });
    //ajax加载业务
    var businessList = new Array();
    var businessName = new Array();
    var busId = new Array();
    var serCode;
    var businessId;
    $.ajax({
        url:'${ctx}/wx/businessAction/businessDirListJson',
        type:'post',
        dataType:'json',
        success:function(data){
        	//console.log(data);
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    //字段名待定
                    businessName[i] = data[i].serName;
                    busId[i] = data[i].businessType+"/"+data[i].serCode;
                    var info = { "title": businessName[i], "value":busId[i] };
                    businessList.push(info);
                }
                //console.log(datalist[0].title);
                $("#business").select({
                    title: "请选择办理业务",
                    textAlign: 'center',
                    multi: false,
                    items: businessList,
                    beforeClose:function(){
                        //console.log($("#business").attr("data-values"));
                        var arr = new Array();
                        arr = $("#business").attr("data-values").split("/");
                        businessId = arr[0];
                        serCode = arr[1];
                        //console.log(businessId);
                       // function setBusiness(){
                        	//ajax方式加载预约时间段信息
                        	console.log(businessId);
                        if(businessId == 2){
                            $.alert("抱歉！根据相关规定暂不提供预约代办功能，请前往办事大厅进行办理")
                        }else{
                            var value = $("#my-input").val().trim();
                            var ajaxData = 'deptid=' + departmentId + '&businessType=' + businessId;
                            ajaxData += '&day=' + value;
                            $.ajax({
                                type : 'POST',
                                url : '${ctx}/phone/wxAppointmeController/appointmeCount',
                                data: ajaxData,
                                dataType:'json',
                                success:function (result) {
                                    $(".weui-cells_checkbox").empty();
                                    $.each(result.stateValue,function(index,obj){
                                        //console.log(obj);
                                        var id = obj['id'];
                                        $(".weui-cells_checkbox").append("<label class='weui-cell weui-check__label' for="+ id +">" +
                                            "<div class='weui-cell__hd'>"+ "<input value="+ id +" type='radio' class='weui-check' name='timeInter' id="+ id +" onclick='addProperty(this)'>" +
                                            "<span class='weui-icon-checked'>" + "</span>" + "</div>" +
                                            "<div class='weui-cell__bd'>" + "<p>" + obj['time'] + "</p>" + "</div>" +
                                            "<div class='weui-cell__ft'>" + "<p class='number'>" + '可预约人数' + "<span>" + obj['orderCount'] + "</span>" + '人' + "</p>" + "</div>" +
                                            "</label>");
                                    });
                                }
                            });
                            //return false;
                        }
                        //}
                    }
                })
            }
        }
    });
    //ajax方式加载样图数据
    <%--$.ajax({--%>
        <%--type:'post',--%>
        <%--url:'${ctx}/wx/businessAction/businessconfigTypes?serCode=${serCode}',--%>
        <%--dataType:'json',--%>
        <%--success:function (result) {--%>
            <%--//选项卡--%>
            <%--$.each(result,function (index,obj) {--%>
                <%--//console.log(obj);--%>
                <%--$.each(obj.stuffs, function (index, child) {--%>
                    <%--//console.log(child);--%>
                    <%--var str = "${ctx}/" + child.photoSample['photosamplepath'];--%>
                    <%--$(".weui-cells:eq(1)").append("<div class='weui-cell'>" + "<div class='weui-cell__hd'>" +--%>
                        <%--"<label class='weui-label'>" + child['bconfigstrname'] + "</label>" + "</div>" +--%>
                        <%--"<div class='weui-cell__bd'>" + "<img class='example' src='"+str+"'>" + "</div>" +--%>
                        <%--"<div class='weui-cell__ft'>" + "<span onclick='getphotostr(this)' class='check'>" + "点击查看</span>" + "</div>" +--%>
                        <%--"</div>");--%>
                <%--});--%>
            <%--});--%>
        <%--}--%>
    <%--});--%>
    //获取选中时间段的值
    var istime = false;
    var timequantum;
    var timeInter;
    var peopleNum;
    function addProperty(obj){
        $(obj).attr("hsl","yes");
        $(obj).parents().parents().siblings().children(".weui-cell__hd").find("input").removeAttr("hsl");
        if($(obj).attr("hsl") == "yes"){
            timequantum = $(obj).val();
            timeInter = $(obj).parent().next().find("p").text();
            peopleNum = $(obj).parent().next().next().find("p").find("span").text();
            istime = true;
        }
    }
    var myDate = new Date();
    $("#my-input").calendar({
        minDate:myDate.toLocaleDateString()
    });
    function judgeWeek() {
        var value = $("#my-input").val().trim();
        if (value == "") {
            return false;
        } else {
            var day = new Date(value).getDay(),text = "";
            switch (day) {
                case 0:
                    text = "7";
                    break;
                case 1:
                    text = "1";
                    break;
                case 2:
                    text = "2";
                    break;
                case 3:
                    text = "3";
                    break;
                case 4:
                    text = "4";
                    break;
                case 5:
                    text = "5";
                    break;
                case 6:
                    text = "6";
                    break;
            }
            week = text;
            // 判断日期是不是周末
            if (week == "7" || week == "6"){
                $.alert({
                    title:'提示',
                    text:'您选择的时间是周末，周末为休息时间，不办理业务',
                    onOK:function () {
                        $("#my-input").click();
                    }
                });
            }
    }
}
    <%--function getphotostr(obj) {--%>
        <%--var pb1 = $.photoBrowser({--%>
            <%--items: [$(obj).parent().prev().children("img").attr("src")],--%>
            <%--onSlideChange: function(index) {--%>

            <%--},--%>
            <%--onOpen: function() {--%>

            <%--},--%>
            <%--onClose: function() {--%>

            <%--}--%>
        <%--});--%>
        <%--pb1.open();--%>
    <%--}--%>
    /* var istime = false;
     var timequantum;
     var timeInter;
     var peopleNum;
     function addProperty(obj){
     $(obj).attr("hsl","yes");
     $(obj).parents().parents().siblings().children(".weui-cell__hd").find("input").removeAttr("hsl");
     if($(obj).attr("hsl") == "yes"){
     timequantum = $(obj).val();
     timeInter = $(obj).parent().next().find("p").text();
     peopleNum = $(obj).parent().next().next().find("p").find("span").text();
     istime = true;
     }
     } */
    function getParams(url) {
        var theRequest = new Object();
        if (!url)
            url = location.href;
        if (url.indexOf("?") !== -1) {
            var str = url.substr(url.indexOf("?") + 1) + "&";
            var strs = str.split("&");
            for (var i = 0; i < strs.length - 1; i++) {
                var key = strs[i].substring(0, strs[i].indexOf("="));
                var val = strs[i].substring(strs[i].indexOf("=") + 1);
                theRequest[key] = val;
            }
        }
        return theRequest;
    }
    var params = getParams();
    //获取地址栏上的userName
    var depId = params.depId;
    //部门ID
    var present = 8;
    var week;
    function isNull(object) {
        if (object == null || typeof object == "undefined") {
            return true;
        }
        return false;
    }
    function isPoneAvailable(str) {
        var myreg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
        if (!myreg.test(str)) {
            return false;
        } else {
            return true;
        }
    }
    function isCardNo(card) {
        // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (reg.test(card) === false) {
            return false;
        } else {
            return true;
        }
    }
    $(".close-reveal-modal").click(function () {
        $("#myModel").css("visibility", "hidden");
    });
    $(".weui-btn_default").click(function () {
        $("#myModel").css("visibility", "hidden");
    });
    $("#showTooltips").click(function () {
        if(isReg){
            $.confirm({
                title:'提示',
                text:'您尚未注册，无法进行预约，点击确定前往注册',
                onOK:function () {
                    //跳转到注册页面
                    getHtml('${ctx}/wxUserAction/goRegister');
                    //window.location.href = "${ctx}/wxUserAction/goRegister";
                },
                onCancel:function(){
                    return false;
                }
            });
        }else{
            if($("#mobile").val() == ""){
                $.alert({
                    title: '提示',
                    text: '请选择网点',
                    onOK: function () {
                        //点击确认
                    }
                });
            }else if ($("#my-input").val() == "") {
                $.alert({
                    title: '提示',
                    text: '请选择办理日期',
                    onOK: function () {
                        //点击确认
                    }
                });
            } else if (istime == false && $("#my-input").val() != "") {
                $.alert({
                    title: '提示',
                    text: '请选择办理的时间段',
                    onOK: function () {
                        //点击确认
                    }
                });
            } else if ($("#username").val() == "") {
                $.alert({
                    title: '提示',
                    text: '请填写姓名',
                    onOK: function () {
                        //点击确认
                    }
                });
            } else if ($("#useridcard").val() == "") {
                $.alert({
                    title: '提示',
                    text: '请填写身份证号码',
                    onOK: function () {
                        //点击确认
                    }
                });
            } else {
                /* if (isPoneAvailable($("#username").val()) == false) {
                 alert("请填写正确的手机号码");
                 return false;
                 } else  */
                if (isCardNo($("#useridcard").val()) == false) {
                    $.alert({
                        title: '提示',
                        text: '请填写正确的身份证号码',
                        onOK: function () {
                            //点击确认
                        }
                    });
                    return false;
                } else {
                    $.confirm({
                        title:'提示',
                        text:'当前时间段预约人数为' + peopleNum + '人,是否继续预约？',
                        onOK:function () {
                            $.showLoading();
                            var ajaxData = 'cardNumber=' + $("#useridcard").val();
                            ajaxData += '&name=' + $name;
                            ajaxData += '&deptid=' + departmentId;
                            ajaxData += '&appointmenttime=' + $("#my-input").val();
                            ajaxData += '&phone=' + $phone;
                            ajaxData += '&timequantum=' + timequantum;
                            ajaxData += '&ser_code=' + serCode;
                            ajaxData += '&businessType=' + businessId;
                            $.ajax({
                                type:'POST',
                                url:'${ctx}/phone/wxAppointmeController/saveAppointmeInfo',
                                data:ajaxData,
                                dataType:'json',
                                success: function (data) {
                                    $.hideLoading();
                                    var deptId;
                                    var day = $("#my-input").val();
                                    var time = timeInter;
                                    getHtml('${ctx}/wxUserAction/successView?deptId=' + departmentId +'&day=' + day + '&time=' + time);
                                    //window.location.href = '${ctx}/wxUserAction/successView?deptId=' + departmentId +'&day=' + day + '&time=' + time;
                                }
                            });
                        },
                        onCancel:function(){
                            return false;
                        }
                    });
                }
            }
        }
    });
</script>
</html>