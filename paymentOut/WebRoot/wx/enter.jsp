<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>在线预约</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/transit.css">
</head>
<body>
<div>
    <img src="${pageContext.request.contextPath }/wx/img/banner.jpg" alt="" style="width: 100%;">
</div>
<div style="background: url('${pageContext.request.contextPath }/wx/img/background.jpg');background-size: 100% auto">
    <!--<div class="page_hd" style="text-align: center;font: 6vw SimHei;color: #01307d;">-->
    <!--<div>线上居住证业务预审核、办证须知</div>-->
    <!--</div>-->
    <!--<div class="text" style="margin-top: -15px;">-->
    <!--1、本平台为南昌市红谷滩新区的一般居住证业务办理提供线上预审核服务，微信直接申请，材料拍照上传，公安机关预审核、审批通过后，再一次性携带原材料到窗口办结；缺、漏、错材料直接通过微信交互；对情形特别复杂的，请直接到派出所户籍窗口咨询办理；对其他地区办证不构成建议和参考；-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--2、点击“在线申请”进入六大类居住证业务选择界面后，请选择需要申请的业务类别；-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--3、申请人按模板引导样式完善材料并如实填写本人信息资料，保证真实、有效、无误，申请人也可以点击业务办理界面右上角“办理须知”获取更多帮助；-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--4、请按模板引导的步骤依次上传申报材料的清晰照片，再提交办事申请，后台工作人员将对提交的电子材料进行审查，并以短信方式告知申请人审查结果（审查通过、或提示补充相关材料），申请人也可以自主通过“我的事项”模块进行审查结果查询。-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--5、审查通过后，申请人持电子材料原件到办事窗口进行同一性认定，只要身份、材料真实无误，即可当场受理，由派出所审批的业务可当场领取证照，实现“先批后办、一趟办结”。-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--6、申请人或当事人使用、伪造、变造申请材料的，所办理的居住证相关业务无效，并承担相应的法律责任。-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--7、注意：（1）在我市就业、创业或投资并符合以下条件之一的流动人口，在居住地派出所办理居住登记后，即可申领居住证：-->
    <!--</div>-->
    <!--<p class="text">（一）具有国家承认大专及以上学历；</p>-->
    <!--<p class="text">提供材料：学历证书。</p>-->
    <!--<p class="text">（二）具有中级及以上专业技术职称；</p>-->
    <!--<p class="text">提供材料：专业技术等级证书。</p>-->
    <!--<p class="text">（三）持有高级工及以上国家职业资格证书；</p>-->
    <!--<p class="text">提供材料：职业资格证书。 </p>-->
    <!--<p class="text">（四）工业园区组织人事部门认定的企业急需人才；</p>-->
    <!--<p class="text">提供材料：有关组织人事部门出具的认定文件。</p>-->
    <!--<p class="text">（五）县级以上组织部门或人力资源社会保障部门认定的企业急需人才；</p>-->
    <!--<p class="text">提供材料：有关组织人事部门出具的认定文件。</p>-->
    <!--<p class="text">（六）个人在当地投资30万元以上；</p>-->
    <!--<p class="text">提供材料：工商营业执照。</p>-->
    <!--<div class="text">-->
    <!--（2）符合以上第（一）至（五）项条件的，还要提供以下材料：-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--就业的，提供与用人单位签订的半年以上期限的劳动（聘用）合同或企业为其缴纳的社会保险凭证；-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--创业的，提供工商营业执照（合伙企业同时提供合伙协议）或事业单位法人证书（或社会组织登记证书）-->
    <!--</div>-->
    <!--<div class="text">-->
    <!--创业的，提供工商营业执照（合伙企业同时提供合伙协议）或事业单位法人证书（或社会组织登记证书）-->
    <!--</div>-->


    <div style="height: 150px"></div>
    <div class="page__bd page__bd_spacing" style="margin-top: 50px;">
        <%--<a href="transit.html" class="weui-btn weui-btn_primary art">预约申请</a>--%>
        <a href="wx/transit.jsp" class="weui-btn weui-btn_primary art">预约申请</a>

        <a href="${pageContext.request.contextPath }/appointmeAction/queryAppointmeInfo"
           class="weui-btn weui-btn_primary art">预约查询与取消</a>

        <!--<a href="javascript:;" class="weui-btn weui-btn_primary art">个人中心</a>-->
    </div>
    <div style="height:20px;"></div>
</div>
<div class="weui-footer" style="position: fixed;bottom: 30%;left: 21%;">
    <p style="color: #736666;font-size: 35px;height: 30px;text-align: center;">
        版权所有©红谷滩公安局<br>
        技术支持 &nbsp;&nbsp;江西科泰华软件有限公司
    </p>
</div>
</body>
</html>
