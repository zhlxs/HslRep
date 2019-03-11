<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<!-- <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script> -->
<script type="text/javascript">
    $(function () {
        $("#ajaxform").rwpUIForm({
            isAjaxSubmit: true
        });
    });
</script>

<form action="${ctx}/updateController/saveUpdate" id="ajaxform"
      method="post">
    <fieldset>
        <legend>
            <i class="i"></i>版本信息
        </legend>
        <%-- <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
            data-val-required="用户ID 字段是必需的。" id="ID" name="id" type="hidden"
            value="${core_User.id}" /> --%>
        <input type="hidden" value="${update.id}" name="id">
        <div class="formpanel">
            <div class="clear">
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="uploadname">客户端版本名称</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-required="名称不能为空" id="uploadname" name="uploadname"
                                                    type="text" value="${update.uploadname}"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="uploadname"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
            </div>
        </div>
    </fieldset>
</form>
