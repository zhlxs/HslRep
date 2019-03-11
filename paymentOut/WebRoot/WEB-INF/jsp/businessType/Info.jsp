<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		if ("1" == 1)
			$('#ulIsSys').show(); //系统级管理员 可设置菜单的系统级权限
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true
		});
	});
</script>
<form action="${ctx}/businessConfigTypeController/saveBusinessType"
	id="ajaxform" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>业务类型信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字"
				data-val-required="ID不能为空" id="id" name="id" type="hidden"
				value="${weApplytype.id}" /> <input data-val="true"
				data-val-required="排序码不能为空" id="orderCode" name="orderCode"
				type="hidden" value="${weApplytype.orderCode}" /> <input
				data-val="true" data-val-required="业务编码不能为空" id="serCode"
				name="serCode" type="hidden" value="${serCode}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="serName">所属业务名称</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="业务类型不能超过100字符" data-val-length-max="100"
						data-val-required="业务类型不能为空" id="serName" name="serName"
						type="text" readonly="readonly"
						value="${policeClassService.serName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="serName"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="applyTypeName">业务类型名(中文)</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="业务类型不能超过100字符" data-val-length-max="100"
						data-val-required="业务类型不能为空" id="applyTypeName"
						name="applyTypeName" type="text"
						value="${weApplytype.applyTypeName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="applyTypeName"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="isValid">是否有效</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="是否有效 字段是必需的。" id="isValid" name="isValid"
						<c:if test="${weApplytype.isValid}">
							checked=checked
						</c:if>
						type="checkbox" value="true" /><input name="isValid"
						type="hidden" value="false" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="isValid"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
		</div>
	</fieldset>
</form>