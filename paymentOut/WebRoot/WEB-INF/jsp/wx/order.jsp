<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>在线预约</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/j-select.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.less">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reveal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/style.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/popups.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery-nicescroll.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery-jSelect.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery.reveal.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/My97DatePicker/WdatePicker.js"></script>

    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/wx/css/public.css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/wx/css/mobileSelect.css">

    <script src="${pageContext.request.contextPath }/wx/js/mobileSelect.js" type="text/javascript"></script>
    <script>
        setSize();
        addEventListener('resize',setSize);
        function setSize() {
            document.documentElement.style.fontSize = document.documentElement.clientWidth/750*100+'px';
        }
    </script>
    <style>
        body {
            background: #F8F8F8;
        }
        #yes_btn{
            color: #1AAD19;
        }
        .header {
            margin-top: 1.0em;
            /*padding: 10px 15px;*/
            line-height: 1.41176471;
            font-size: 20px;
            overflow: hidden;
            /*position: relative;*/
            text-indent: 15px;
        }
        .weui-mask_transparent {
            background: #000;
            opacity: 0.5;
        }
        .form {
            width: 192px;
            height: 36px;
            float: right;
            margin-right: 4px;
        }
        .current_select {
            border-radius: 5px;
        }
        .show-list{
            width:80%;
            margin: 0 auto;
        }
        .show-list li{
            height: 1rem;
            font-size: .3rem;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-bottom: 1px solid #dcdcdc;
        }
        .content{
            text-align: center;
        }
    </style>
</head>
<body>
    <div id="loadingToast" style="display: block;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast" style="top: 225px;">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">加载中请稍候...</p>
        </div>
    </div>
<input type="hidden" value="${pageContext.request.contextPath }" id="path"/>
<form action="${pageContext.request.contextPath }/appointmeAction/saveAppointmeInfo" id="appointemeForm" method="post">
    <input type="hidden" id="openid" value="${sessionScope.openid}" name="openid">
    <article class="r-m301">
        <div class="header">
            预约信息
        </div>
        <div class="weui-cells" style="overflow: visible">
            <a class="weui-cell weui-cell_access" href="javascript:void(0);">
                <div class="weui-cell__bd" style="padding: 5px 3px;">
                    <span>办证网点</span>
                </div>
                <div class="weui-cell__hd">
                    <div id="trigger5" style="color: #0055aa;font-size: 14px;">选择网点</div>
                    <input type="hidden" id="deptid" value="" name="deptid">
                </div>
            </a>
            <c:if test="${deptid==273}">
			 <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">办理业务种类</label>
                </div>
                <div class="weui-cell__bd">
                    <div class="form">
                        <select id="service" class="" name="service">
                            <option value="0">请选择办理业务种类</option>
                            <option value="1">户政业务</option>
                            <option value="2">出入境业务</option>
                        </select>
                    </div>
                </div>
            </div>
            </c:if>
            <!--<a class="weui-cell weui-cell_access" href="javascript:void(0);">-->
            <div class="weui-cell">
                <div class="weui-cell__bd" style="padding: 5px 3px;">
                    <span>办理日期</span>
                </div>
                <div class="weui-cell__hd" style="width: 196px;">
                    <div>
                        <!--<input id="txtEndDate" style="font-size: 16px;width:170px;padding:7px 10px;border:1px solid #ccc;" />-->
                        <input id="txtEndDate" class="Wdate findAppCount" type="text"
                               onClick="WdatePicker({minDate:'%y-%M-{%d+1}',disabledDays:[0,6]})"
                               style="font-size: 16px;width:199px;padding:7px 10px;border:1px solid #ccc;height: 30px;box-shadow: 1px 1px 2px #ccc;border-radius: 5px;"
                               name="appointmenttime" value="">
                    </div>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">预约时间段</label>
                </div>
                <div class="weui-cell__bd">
                    <div class="form">
                        <select id="time" class="findAppCount select" name="timeQuantum">
                            <option value="请选择办理时间段">请选择办理时间段</option>
                            <option value="1">09:00-10:00</option>
                            <option value="2">10:00-11:00</option>
                            <option value="3">11:00-12:00</option>
                            <option value="4">14:00-15:00</option>
                            <option value="5">15:00-16:00</option>
                            <option value="6">16:00-17:00</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="weui-cell__bd">
                <div class="weui-cell__hd" id="countTips" style="text-align: center;color: red;"></div>
            </div>
            <!--</a>-->
        </div>
        <div class="header">
            个人信息
        </div>
        <div class="weui-cells" style="overflow: visible">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">常用联系人</label>
                </div>
                <div class="weui-cell__bd">
                    <div class="form">
                        <select id="frequent" class="select">
                            <option value="选择常用联系人">选择常用联系人</option>
                            <c:forEach items="${frequentContacts}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">身份证号码</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="useridcard" type="text" placeholder="请输入身份证号码" value="" name="cardnumber">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">姓名：</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="name" type="text" placeholder="请输入姓名" value="" name="name">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">手机号码</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="telnumber" type="text" placeholder="请输入手机号码" value="" name="phone">
                </div>
            </div>
        </div>
    </article>
</form>
<div class="weui-btn-area" style="margin: 4.176471em 15px 0.3em;">
    <a data-reveal-id="myModal" data-animation="fade" class="weui-btn weui-btn_primary" href="javascript:void(0);" id="showTooltips">申请预约</a>
</div>
<%--<div id="myModel" class="reveal-modal" style="opacity: 0.9;">--%>
    <%--<h1>是否将信息添加至常用联系人？</h1>--%>
    <%--<p class="weui-btn-area">--%>
        <%--<a href="javascript:;" class="weui-btn weui-btn_primary frquence" id="yes">确认</a>--%>
        <%--<a href="javascript:;" class="weui-btn weui-btn_default frquence" id="no">取消</a>--%>
    <%--</p>--%>
    <%--<a class="close-reveal-modal">&#215;</a>--%>
<%--</div>--%>
</body>
<script type="text/javascript">
    $(function () {
        var chose ='';
       // var deptname='';
        $.post('${pageContext.request.contextPath }/deptAddressAction/wx/testdept?deptid=${deptid}',{},function(data){

            var UplinkData =data;
//----------------------------------------------------------

//只有trigger 和 wheels 是必要参数  其他都是选填参数

            var mobileSelect5 = new MobileSelect({
                trigger: '#trigger5',
                title: '部门选择',
                wheels: [
                    {data : UplinkData}
                ],
                transitionEnd:function(indexArr, data){
                    //console.log(data[2]);

                },
                callback:function(indexArr, data){
                    console.log(data[2]);
                    var len = data.length;
                    chose=data[len-1].id;
                    deptname=data[len-1].value;
                    $("#deptid").val(chose);
                    //$("#deptname").val(deptname);

                }
            });
            $("#loadingToast").hide();

        },'json');



        $("#loadingToast").css("display","none");
        $("#frequent").change(function () {
            var fid = $("#frequent").find("option:selected").val();
            if (fid != null && fid != '选择常用联系人') {
                $.post("${pageContext.request.contextPath }/wx/frequent/getByid", {"id": fid}, function (data) {
                    console.log(data);
                    $("#telnumber").val(data.phone);
                    $("#useridcard").val(data.cardnumber);
                    $("#name").val(data.name);

                });
            } else {
                $("#telnumber").val("");
                $("#useridcard").val("");
                $("#name").val("");
            }
        });


        $("#dpQS").css("display", "none");
        $(".select").jSelect();
        var week;

        $(".findAppCount").change(function () {
            var flag = true;
            $(".findAppCount").each(function () {
                //alert($(this).val());
                if ($(this).val() == null || $(this).val() == '') {
                    flag = false;
                }
            });

            if (flag) {
                var day = $("#txtEndDate").val();
                var time = $("#time").find("option:selected").val();
                var parm = {
                    "day": day,
                    "time": time,
                    "deptid": $("#deptid").val()
                };
                $.post("${pageContext.request.contextPath }/appointmeAction/getAppointmeCount", parm, function (data) {
                    $("#countTips").text("该时间段已预约" + data.stateValue + "人;  还可预约" + (30 - Number(data.stateValue)) + "人");
                    // $("#count1").text(30 - Number(data.stateValue));
                    // $("#myModel").css("visibility", "visible");
                });
            }
        });

        function isNull(object) {
            if (object == null || typeof object == "undefined") {
                return true;
            }
            return false;
        }

        function isPoneAvailable(str) {
            var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
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

        /*   $(".close-reveal-modal").click(function () {
               $("#myModel").css("visibility", "hidden");
           });
           $(".weui-btn_default").click(function () {
               $("#myModel").css("visibility", "hidden");
           });*/
        $("#showTooltips").click(function () {
            var day = $("#txtEndDate").val();
            var time = $("#time").find("option:selected").val();
            var phone = $("#telnumber").val();
            var cardnumber = $("#useridcard").val();
            var deptid = $("#deptid").val();
            var name = $("#name").val();
            if ($("#txtEndDate").val() == "") {
                alert("请选择办理日期");
            } else if ($("#time").find("option:selected").text() == "请选择办理时间段") {
                alert("请选择办理的时间段");
            } else if (phone == "") {
                alert("请填写电话号码");
            } else if (cardnumber == "") {
                alert("请填写身份证号码");
            } else if (deptid == "") {
                alert("请选择网点");
            } else if (name == "") {
                alert("请填写姓名");
            } else {
                if (isPoneAvailable($("#telnumber").val()) == false) {
                    alert("请填写正确的手机号码");
                    return false;
                } else if (isCardNo($("#useridcard").val()) == false) {
                    alert("请填写正确的身份证号码");
                    return false;
                }
                //填写完信息 去查询常用联系人中是否有这条信息
                //如果没有 则提示是否加入常用联系人
                var parm = {
                    "cardnumber": $("#useridcard").val()
                };

                $.post("${pageContext.request.contextPath }/wx/frequent/isSave", parm, function (data) {
                    console.log(data);
                    if (data.stateValue == 0) {
                        //   $("#appointemeForm").submit();
                        jqalert({
                            title:'提示',
                            content:'是否加入常用联系人？',
                            yesfn:function(){
                                $("#loadingToast").css("display","block");
                                var parm = {
                                    "cardnumber": $("#useridcard").val(),
                                    "phone": $("#telnumber").val(),
                                    "name": $("#name").val(),
                                    "openid": $("#openid").val()
                                };
                                $.post("${pageContext.request.contextPath }/wx/frequent/saveFrquence", parm, function (data) {
                                console.log(data);
                                if (data.stateType == 0) {
                                //   $("#appointemeForm").submit();
                                $("#appointemeForm").submit();
                                } else {
                                    $("#loadingToast").css("display","none");
                                    jqalert({
                                        title:'提示',
                                        content:'保存失败，请检查联系人信息是否正确',
                                        yestext:'确定'
                                    });
                                }
                                // $("#count1").text(30 - Number(data.stateValue));
                                //  $("#myModel").css("visibility", "visible");
                                });
                            },
                            notext:'取消'
                        });

                    } else if (data.stateValue == 1) {
                        $("#loadingToast").css("display","block");
                        $("#appointemeForm").submit();
                    } else {
                        alert("查询联系人出错了");
                    }


                    // $("#count1").text(30 - Number(data.stateValue));
                    //  $("#myModel").css("visibility", "visible");
                });


            }
        });


    })
</script>
</html>
