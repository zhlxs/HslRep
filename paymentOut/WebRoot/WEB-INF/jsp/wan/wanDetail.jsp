<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<script>

    $("#ajaxform").rwpUIForm({
        isAjaxSubmit: true
    });
</script>
<body>
<form action="${ctx}/appraiseContrller/saveAppraDetail" id="ajaxform"
      method="post">
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
                                                        type="text" value="${payWanInfo.policeClassService.serName}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="userName"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>--%>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="orderNumber">订单号</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="orderNumber" name="orderNumber"
                                                        type="text" value="${payWanInfo.orderNumber}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="orderNumber"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="idNumber">支付身份证号</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="idNumber" name="idNumber"
                                                        type="text" value="${payWanInfo.idNumber}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="idNumber"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <%--    <ul class="DialabelWidth">
                            <li class="editor-label"><label for="machineName">设备名称</label>
                            </li>
                            <li class="editor-field"><input data-val="true"
                                                            data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                            data-val-required="用户名不能为空" id="machineName" name="machineName"
                                                            type="text" value="${payWanInfo.machineName}"
                                                            readonly="readonly"/>
                            </li>
                            <li class="editor-validation"><span
                                    class="field-validation-valid" data-valmsg-for="machineName"
                                    data-valmsg-replace="true"></span>
                            </li>
                        </ul>--%>

                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="machineId">机器码</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="machineId" name="machineId"
                                                        type="text" value="${payWanInfo.machineId}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="machineId"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="machineType">设备类型</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="machineType" name="machineType"
                                                        type="text" value="${payWanInfo.machineType}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="machineType"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <%--   <ul class="DialabelWidth">
                           <li class="editor-label"><label for="deptName">部门名称</label>
                           </li>
                           <li class="editor-field"><input data-val="true"
                                                           data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                           data-val-required="用户名不能为空" id="deptName" name="deptName"
                                                           type="text" value="${payWanInfo.deptName}"
                                                           readonly="readonly"/>
                           </li>
                           <li class="editor-validation"><span
                                   class="field-validation-valid" data-valmsg-for="deptName"
                                   data-valmsg-replace="true"></span>
                           </li>
                       </ul>--%>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="payMoney">支付金额</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="payMoney" name="payMoney"
                                                        type="text" value="${payWanInfo.payMoney}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="payMoney"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="configName">收款方</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="configName" name="configName"
                                                        type="text" value="${payWanInfo.configName}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="configName"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="payMoney">支付类型</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="payType" name="payType"
                                                        type="text" value="${payWanInfo.payType}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="payType"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>

                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="payStatus">支付状态</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="payStatus" name="payStatus"
                                                        type="text"
                                                        value='${payWanInfo.payStatus}'
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="payStatus"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="payTime">支付时间</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="payTime" name="payTime"
                                                        type="text" value="<fmt:formatDate value="${payWanInfo.payTime}" type="both"/>"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="payTime"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="orderDescribe">商品描述</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="orderDescribe" name="orderDescribe"
                                                        type="text" value="${payWanInfo.orderDescribe}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="orderDescribe"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>

                </div>

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
</form>
</body>
<script>
    $(function () {
        $("#back").click(function () {
            rwpMenuHelper.rebackLastPage();
        });
    });
</script>
</html>
