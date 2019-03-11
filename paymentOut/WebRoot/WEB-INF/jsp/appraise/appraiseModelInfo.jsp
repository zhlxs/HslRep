<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<script type="text/javascript">
    $(function () {
        $("#ajaxform").rwpUIForm({
            isAjaxSubmit: true,
            inputWidth: 'auto'
        });
    });
</script>
<form action="${ctx}/appraiseContrller/saveAppraModel" id="ajaxform"
      method="post">
    <fieldset>
        <legend>
            <i class="i"></i>评价模板信息
        </legend>
        <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
               data-val-required="用户ID 字段是必需的。" id="id" name="id" type="hidden"
               value="${model.id}"/>
        <div class="formpanel">
            <div class="clear">
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="modelname">模板名称</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                    data-val-required="用户名不能为空" id="modelname" name="modelname"
                                                    type="text" value="${model.modelname}"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="modelname"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>

            </div>
        </div>
    </fieldset>

    <!-- <div class="clear" style="margin-left: 420px;">

        <div class="fPptions" style="float: left; margin-top: 4px;" id="gosub">
            <a href="javascript:;" title="确定" class="btnLightBlue btn back"
               style="width: 50px; height: 32px;" id="rego"><i class="i"></i>确定</a>
        </div>
        <div class="fPptions" style="float: left; margin-top: 4px;" id="back">
            <a href="javascript:;" title="返回" class="btnLightBlue btn back"
               style="width: 50px; height: 32px;" id="reback"><i class="i"></i>返回</a>
        </div>
    </div> -->
</form>