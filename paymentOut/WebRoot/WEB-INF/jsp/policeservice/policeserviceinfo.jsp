<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<!-- <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script> -->
<script type="text/javascript">
	$(function() {
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true
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
			var url = '${ctx}/policeServiceController/policeServiceDialog';
			return rwpPluginHelper.selectContact('选择上一级所属业务', url, 'serCode',
					'serName', 'parentCode', 'parentServiceName', null,
					rwpTemp.f_setparentdept);
		};

		$("#parentServiceName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectparentdept
		});
	});
</script>
<style>
.rwpCombobox {
	width: 210px;
}
</style>
<form action="${ctx}/policeServiceController/savePoliceService"
	id="ajaxform" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>业务信息
		</legend>
		<%-- <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
            data-val-required="用户ID 字段是必需的。" id="ID" name="id" type="hidden"
            value="${core_User.id}" /> --%>
		<input type="hidden" value="${policeClassService.serCode}"
			name="serCode">
		<div class="formpanel">
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="serName">业务名称</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="名称不能为空" id="serName" name="serName" type="text"
						value="${policeClassService.serName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="serName"
						data-valmsg-replace="true"></span></li>
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
				<c:if test="${policelist!=null }">
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="pcName">警种名称</label></li>
						<li class="editor-field">
							<select data-val="true" data-val-required="" id="pcName"
							name="pcCode">
								<c:forEach items="${policelist }" var="police">
									<option value="${police.pcCode }"
										<c:if test="${police.pcCode==policeClassService.pcCode}">selected</c:if>>${police.pcName}</option>
								</c:forEach>
						</select>
						</li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="pcName"
							data-valmsg-replace="true"></span></li>
					</ul>
				</c:if>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="businessType">业务类型</label></li>
					<li class="editor-field"><select id="businessType" name="businessType">
						<option <c:if test="${policeClassService.businessType == 1}">selected=selected</c:if> value="1">机动车业务</option>
						<option <c:if test="${policeClassService.businessType == 2}">selected=selected</c:if> value="2">驾驶证业务</option>
						<option <c:if test="${policeClassService.businessType == 4}">selected=selected</c:if> value="4">违法业务</option>
					</select></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="businessType"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth" style="display: none;">
					<li class="editor-label"><label for="price">单价</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="单价不能超过10字符" data-val-length-max="10" id="price"
						name="price" type="text" value="${policeClassService.price}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="price"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>
