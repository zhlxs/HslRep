<%--
  Created by IntelliJ IDEA.
  User: lwj
  Date: 2018/7/31
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>手机三级联动选择js代码 - 站长素材</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="wap-font-scale" content="no">
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1,user-scalable=no">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/wx/css/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/wx/css/mobileSelect.css">

    <script src="${pageContext.request.contextPath }/wx/js/mobileSelect.js" type="text/javascript"></script>
    <script src='${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js'></script>
</head>
<body>

<div class="contain">
    <div class="fixWidth">
        <div class="nav">
            <h1>mobileSelect Demo</h1>
        </div>
        <div class="demo">

            <div id="trigger5">车型选择-级联</div>
        </div>
    </div>

</div>

<script type="text/javascript">
    $.post('http://localhost:8090/paymentOut/deptAddressAction/wx/testdept?deptid=543',{},function(data){

        var UplinkData =data;
//----------------------------------------------------------

//只有trigger 和 wheels 是必要参数  其他都是选填参数

        var mobileSelect5 = new MobileSelect({
            trigger: '#trigger5',
            title: '车型选择',
            wheels: [
                {data : UplinkData}
            ],
            transitionEnd:function(indexArr, data){
                console.log(data);
            },
            callback:function(indexArr, data){
                console.log(data);
            }
        });

    },'json')



</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
    <p>适用浏览器：360、FireFox、Chrome、Opera、傲游、搜狗、世界之窗. 不支持Safari、IE8及以下浏览器。</p>
    <p>来源：<a href="http://sc.chinaz.com/" target="_blank">站长素材</a></p>
</div>
</body>
</html>
