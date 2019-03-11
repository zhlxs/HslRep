<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<!-- <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script> -->
<script type="text/javascript">
    $(function () {
        $("#ajaxform").rwpUIForm({
            isAjaxSubmit: true
        });

        rwpTemp.f_setparentdept = function(node, value, text) {
            $("#parentCode").val(value);
            $("#parentServiceName").val(text);
            var isHideDeptCode = (node && $.isArray(node) && node[0].ID > 0 && !node[0].deptCode);
            if (isHideDeptCode) {
                $('#divDeptCode').hide();
            } else {
                $('#divDeptCode').show();
            }
        };

        rwpTemp.f_selectparentdept = function() {
            var url = '${ctx}/deptServiceController/policeServiceDialog';
            return rwpPluginHelper.selectContact('选择上一级所属业务', url, 'serCode', 'serName',
                'parentCode', 'parentServiceName', null, rwpTemp.f_setparentdept);
        };

        $("#parentServiceName").rwpUICombobox({
            onBeforeOpen : rwpTemp.f_selectparentdept
        });
    });
</script>
<style>
    .rwpCombobox{
        width: 210px;
    }

</style>
<form action="${ctx}/policeServiceController/savePoliceService" id="ajaxform"
      method="post">
      
    <fieldset>
        <legend>
            <i class="i"></i>业务信息
        </legend>
        <%-- <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
            data-val-required="用户ID 字段是必需的。" id="ID" name="id" type="hidden"
            value="${core_User.id}" /> --%>
        <input type="hidden" value="${uiCode}" name="uiCode"/>
        <div class="formpanel">
            <div class="clear">
            	<ul class="DialabelWidth">
                    <li class="editor-label"><label for="writeBackUrl">回写地址</label>
                    </li>
                    <li class="editor-field"><input data-val="true"

                                                    data-val-required="名称不能为空" id="writeBackUrl" name="writeBackUrl"
                                                    type="text" />
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="serName"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
            	<ul class="DialabelWidth">
                    <li class="editor-label"><label for="parentCode">所属业务</label>
                    </li>
                    <li style="display: none;"><input data-val="true"
                                                      data-val-number="字段 所属部门 必须是一个数字。" data-val-regex="请选择所属业务"
                                                      data-val-regex-pattern="^[1-9]\d*|0$"
                                                      data-val-required="所属业务 字段是必需的。" id="parentCode" name="parentCode"
                                                      type="hidden"
                                                      value="${not empty policeClassService.parentCode?policeClassService.parentCode:0}" />
                    </li>
                    <li class="editor-field"><input id="parentServiceName"
                                                    name="parentServiceName" type="text"
                                                    value="${not empty policeClassService.parent.serName?policeClassService.parent.serName:'请选择业务'}" /></li>

                </ul>
                
                
            </div>
            <div class="clear">
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="payCode">支付编码</label>
                    </li>
                    <li class="editor-field"><input data-val="true"
                                                    data-val-length="单价不能超过10字符" data-val-length-max="10"
                                                     id="payCode" name="payCode"
                                                    type="text" />
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="payCode"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
                
                <ul class="DialabelWidth">
                    <li class="editor-label"><label for="orderDescribe">商品描述</label>
                    </li>
                    <li class="editor-field"><%-- <input data-val="true"
                                                    data-val-length="单价不能超过10字符" data-val-length-max="10"
                                                     id="price" name="price"
                                                    type="text" value="${policeClassService.price}"/> --%>
                                             <textarea rows="10" cols="20" id="orderDescribe" name="orderDescribe"></textarea>
                    </li>
                    <li class="editor-validation"><span
                            class="field-validation-valid" data-valmsg-for="price"
                            data-valmsg-replace="true"></span>
                    </li>
                </ul>
            </div>
        </div>
    </fieldset>
</form>
