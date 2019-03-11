<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<!-- <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script> -->
<script src="${ctx}/js/jquery-form.js"></script>
<script type="text/javascript">
    $(function () {
        $("#ajaxform").rwpUIForm({
            isAjaxSubmit: true,
            ajaxSubmitAttrs: {
                beforeSubmit: function () {
                    var version =  $("#version").val();
                    if (version == ''||version=='undifined') {
                        $.rwpUI.alert('请填写版本号', '操作提示', result.stateType == 0 ? 'success' : 'error', false);
                        return false;
                    }
                    var option = {
                        url: '${ctx}/updateController/saveDll',
                        type: 'POST',
                        // async: false,
                        dataType: 'json',
                        success: function (result) {
                            $(".rwpDialogMask").hide();
                            $(".rwpDialog").remove();
                            $.rwpUI.alert(result.stateMsg, '操作提示', result.stateType == 0 ? 'success' : 'error', false);
                            rwpPluginHelper.menuHelper.loadMenuInsidePage('${ctx}/updateController/dllindex?updateid=${updateid}');
                        },
                        error: function (data) {
                            alert("保存模块失败,请联系管理员！");
                        }
                    };
                    $("#ajaxform").ajaxSubmit(option);
                    return false;
                }
            }
        });
    });
</script>

<form action="" id="ajaxform"
      method="post" enctype="multipart/form-data">
    <fieldset style="width: 400px">
        <legend>
            <i class="i"></i>上传更新文件
        </legend>
        <%-- <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
            data-val-required="用户ID 字段是必需的。" id="ID" name="id" type="hidden"
            value="${core_User.id}" /> --%>
        <input type="hidden" value="${updateid}" name="updateid">
        <div class="formpanel">
            <c:if test="${updateid==2}">
            <div class="clear">
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="version">更新文件版本号</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-required="版本号不能为空" id="version" name="version"
                                                    type="text" value="${dll.version}"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="version"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
            </div>
            </c:if>
            <div class="clear">
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="version">更新路径</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-required="版本号不能为空" id="clientCopyPath" name="clientCopyPath"
                                                    type="text" value="${dll.clientCopyPath==null?'\\':dll.clientCopyPath}"/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="version"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
            </div>
            <div class="clear">
                <ul class="DialabelWidth" style="width: 320px;">
                    <li class="editor-label"><label for="dll">上传文件</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-required="名称不能为空" id="dll" name="file"
                                                    type="file" value=""/>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="dll"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
            </div>
        </div>
    </fieldset>
</form>
