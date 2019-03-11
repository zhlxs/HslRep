<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<body>
<div class="fAutoPage">
    <fieldset>
        <legend>
            <i class="i"></i>订单详细
        </legend>
        <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
               data-val-required="用户ID 字段是必需的。" id="ID" name="id" type="hidden"
               value="${core_User.id}"/>
        <div class="formpanel">
            <div class="clear">
                <%--<ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">业务名称</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.policeClassService.serName}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>--%>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">民警姓名</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.user.fullName}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">民警手机</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.user.phone}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>

                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">机器码</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.machinecore}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">IP</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.machineip}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">支付方</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text"
                                                    value='<c:if test="${payOrders.paytype==1}">支付宝</c:if><c:if test="${payOrders.paytype==2}">微信</c:if><c:if test="${payOrders.paytype==3}">微信和支付宝</c:if>'
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">支付状态</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text"
                                                    value='<c:if test="${payOrders.paystatus==0}">等待生成二维码</c:if><c:if test="${payOrders.paystatus==1}">待支付</c:if><c:if test="${payOrders.paystatus==2}">已支付</c:if><c:if test="${payOrders.paystatus==3}">失败</c:if><c:if test="${payOrders.paystatus==4}">超时</c:if><c:if test="${payOrders.paystatus==5}">取消</c:if>'
                                                    readonly="readonly"/>


                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">支付金额</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.paymoney}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">下单时间</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.creattime}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">实际支付方式</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text"
                                                    value='<c:if test="${payOrders.relpaytype==1}">支付宝</c:if><c:if test="${payOrders.relpaytype==2}">微信</c:if>'
                                                    readonly="readonly"/>


                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">第三方支付编号</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.ordernumber}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="userName">支付时间</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="userName" name="userName"
                                                    type="text" value="${payOrders.paytime}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="userName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>

            </div>

        </div>


</fieldset>
<fieldset>
    <legend>
        <i class="i"></i>包含业务
    </legend>
    <div class="formpanel">
        <c:forEach items="${payOrders.orderDetails}" var="orderDetail">
            <div class="clear">
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="serName">业务名称</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="serName" name="serName"
                                                    type="text" value="${orderDetail.policeClassService.serName}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="serName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="price">业务单价</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="price" name="price"
                                                    type="text" value="${orderDetail.policeClassService.price}元"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="price"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="workprocessnumber">业务流水号</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="workprocessnumber"
                                                    name="workprocessnumber"
                                                    type="text" value="${orderDetail.workprocessnumber}"
                                                    readonly="readonly"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="workprocessnumber"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>


            </div>
        </c:forEach>
    </div>

</fieldset>
<div class="clear" style="margin-left: 420px;">
<%--
    <div class="fPptions" style="float: left; margin-top: 4px;" id="gosub">
        <a href="javascript:;" title="确定" class="btnLightBlue btn back"
           style="width: 80px; height: 32px;font-weight: bolder;" id="rego"><i class="i"></i>确定</a>
    </div>--%>
    <div class="fPptions" style="float: left; margin-top: 4px;" id="back">
        <a href="javascript:;" title="返回" class="btnLightBlue btn back"
           style="width: 80px; height: 32px;font-weight: bolder;" id="reback"><i class="i"></i>返回</a>
    </div>
</div>

</div>
</body>
<script>
    $(function () {
        $("#back").click(function () {
            rwpMenuHelper.rebackLastPage();
        });
    });
</script>
</html>
