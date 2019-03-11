<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/wx/css/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/wx/css/mobileSelect.css">

    <script src="${pageContext.request.contextPath }/wx/js/mobileSelect.js" type="text/javascript"></script>
    <!--医首信息自定义css-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/wx/css/jiaj.css"/>
    <!--官网demo css-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/wx/css/example.css"/>
    <!--weui css-->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.min.css"/>
    <!--jQuery weui-->
    <link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/0.8.0/css/jquery-weui.min.css">
    <!--切换样式-->
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <style type="text/css">
        body {
            background: #F8F8F8;
        }

        #yes_btn {
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

        .show-list {
            width: 80%;
            margin: 0 auto;
        }

        .show-list li {
            height: 1rem;
            font-size: .3rem;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-bottom: 1px solid #dcdcdc;
        }
        .demo{
            display: block;
            width: 100%;
            height: 40px;
            border-radius: 5px;
            text-align: center;
            line-height: 40px;
            color: #157302;
        }

    </style>
    <title>选择一个办事网点</title>
</head>

<body>

<!--页面切换必须要添加js_container-->

    <div id="loadingToast" style="display: block;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast" style="top: 225px;">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">加载中请稍候...</p>
        </div>
    </div>
    <input type="hidden" value="${pageContext.request.contextPath }" id="path"/>
    <form action="" id="appointemeForm" method="post">
        <input type="hidden" id="openid" value="${sessionScope.openid}" name="openid">
        <article class="r-m301">
            <div class="header">
                选择办事网点
            </div>
            <div class="weui-cells" style="overflow: visible">
                <div class="contain" style="min-height: 50px;">
                    <div class="fixWidth">
                        <div class="demo">
                            <div id="trigger5">点我选择网点</div>
                        </div>
                    </div>
                </div>
            </div>
        </article>
    </form>
    <div class="weui-btn-area" style="margin: 4.176471em 15px 0.3em;">
        <a data-reveal-id="myModal" data-animation="fade" class="weui-btn weui-btn_primary" href="javascript:void(0);" id="showTooltips">立即取号</a>
    </div>
</body>
<script>
    $(function () {
        var chose ='';
        var deptname='';
        $.post('${pageContext.request.contextPath }/deptAddressAction/wx/testdept?deptid=543',{},function(data){

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
                    chose=data[2].id;
                    deptname=data[2].value;

                }
            });
            $("#loadingToast").hide();

        },'json');
        $("#showTooltips").click(function () {
            //var name = $("#name").val();
            if (chose == "") {
                alert("请选择网点");
            } else {
                console.log( "${pageContext.request.contextPath }/appointmeAction/gotoorder?deptid="
                    + chose + "&deptname=" + deptname);
                window.location.href = "${pageContext.request.contextPath }/appointmeAction/gotoorder?deptid="
                    + chose + "&deptname=" + deptname;
            }
        });

    });
   // console.log($(".BMapLib_SearchInfoWindow"));
</script>
</html>
