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
                                                        type="text" value="${refund.policeClassService.serName}"
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
                                                        type="text" value="${refund.ordernumber}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="orderNumber"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="payMoney">退款金额</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="payMoney" name="payMoney"
                                                        type="text" value="${refund.paymoney}"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="payMoney"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>


                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="status">退款状态</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="status" name="payStatus"
                                                        type="text"
                                                        value='${refund.status}'
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="payStatus"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>

                        <ul class="DialabelWidth">
                            <li class="editor-label"><label for="applyusername">申请人</label>
                            </li>
                            <li class="editor-field"><input data-val="true"
                                                            data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                            data-val-required="用户名不能为空" id="applyusername" name="payStatus"
                                                            type="text"
                                                            value='${refund.applyusername}'
                                                            readonly="readonly"/>
                            </li>
                            <li class="editor-validation"><span
                                    class="field-validation-valid" data-valmsg-for="payStatus"
                                    data-valmsg-replace="true"></span>
                            </li>
                        </ul>

                        <ul class="DialabelWidth">
                            <li class="editor-label"><label for="checkusername">审核人</label>
                            </li>
                            <li class="editor-field"><input data-val="true"
                                                            data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                            data-val-required="用户名不能为空" id="checkusername" name="payStatus"
                                                            type="text"
                                                            value='${refund.checkusername}'
                                                            readonly="readonly"/>
                            </li>
                            <li class="editor-validation"><span
                                    class="field-validation-valid" data-valmsg-for="payStatus"
                                    data-valmsg-replace="true"></span>
                            </li>
                        </ul>
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="payTime">审核时间</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="payTime" name="payTime"
                                                        type="text" value="<fmt:formatDate value="${refund.checktime}" type="both"/>"
                                                        readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="payTime"
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
