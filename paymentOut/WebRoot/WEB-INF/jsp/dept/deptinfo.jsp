<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>

<script type="text/javascript">
	rwpTemp.f_setparentdept = function(node, value, text) {
		$("#parentID").val(value);
		$("#parentDeptName").val(text);
		var isHideDeptCode = (node && $.isArray(node) && node[0].ID > 0 && !node[0].deptCode);
		if (isHideDeptCode) {
			$('#divDeptCode').hide();
		} else {
			$('#divDeptCode').show();
		}
	};

	rwpTemp.f_selectparentdept = function() {
		var url = '${ctx}/deptController/deptDialog';
		return rwpPluginHelper.selectContact('选择上一级部门', url, 'ID', 'deptName',
				'parentID', 'parentDeptName', null, rwpTemp.f_setparentdept);
	};
	$(function() {
		$("#parentDeptName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectparentdept
		});
		if (!($('#deptCode').val()) && $('#ID').val() > 0) {
			$('#divDeptCode').hide(); //编辑时 如之前部门代码
		}
		if ("1" == 1) {
			$('#ulIsSys').show(); //系统级管理员 可设置部门的系统级权限
		}
		$("#isShow").rwpUIToggle();
		$("#isSys").rwpUIToggle();

		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true
		});
	});
</script>
<form action="${ctx}/deptController/saveDept" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>部门信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段 部门ID 必须是一个数字。"
				data-val-required="部门ID不能为空" id="id" name="id" type="hidden"
				value="${depthelper.dept.id}" /> <input data-val="true"
				data-val-length="排序码不能超过1000字符" data-val-length-max="1000"
				id="orderCode" name="orderCode" type="hidden"
				value="${depthelper.dept.orderCode}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="parentID">所属部门</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-number="字段 所属部门 必须是一个数字。" data-val-regex="请选择所属部门"
						data-val-regex-pattern="^[1-9]\d*|0$"
						data-val-required="所属部门 字段是必需的。" id="parentID" name="parentId"
						type="hidden"
						value="${not empty depthelper.dept.parentId?depthelper.dept.parentId:'0'}" />
					</li>
					<li class="editor-field"><input id="parentDeptName"
						name="parentDeptName" type="text"
						value="${not empty depthelper.dept.parentDeptName?depthelper.dept.parentDeptName:'部门'}" />
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="parentID"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="deptName">部门名称</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="部门名称不能超过20字符" data-val-length-max="20"
						data-val-required="部门名称不能为空" id="deptName" name="deptName"
						type="text" value="${depthelper.dept.deptName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="deptName"
						data-valmsg-replace="true"></span></li>
				</ul>

			</div>
			<div class="clear" id="divDeptCode">
				<%--<ul class="DialabelWidth">
					<li class="editor-label"><label for="deptCode">部门别名</label>
					</li>
					<li class="editor-field"><input id="otherName" name="otherName"
													type="text" value="${depthelper.dept.otherName}" />
					</li>
					<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="deptCode"
							data-valmsg-replace="true"></span>
					</li>
				</ul>--%>
				<%-- <ul class="DialabelWidth">
					<li class="editor-label"><label for="deptCode">部门编号</label>
					</li>
					<li class="editor-field"><input data-val="true"
						data-val-length="部门编号不能超过20字符" data-val-length-max="20"
						data-val-required="部门编号不能为空" id="deptCode" name="deptCode"
						type="text" value="${depthelper.dept.deptCode}" />
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="deptCode"
						data-valmsg-replace="true"></span>
					</li>
				</ul> --%>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="deptCode">部门编号</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="单位不能超过12字符" data-val-length-max="12"
						data-val-required="单位不能为空" id="deptCode" name="deptCode"
						type="text" value="${core_Dept.deptCode}" maxlength="12" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="deptCode"
						data-valmsg-replace="true"></span></li>
				</ul>

			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="isShow">显示状态</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="显示状态 字段是必需的。" id="isShow" name="isShow"
						type="checkbox" value="true"
						<c:if test="${core_Dept.isShow}">
								checked="checked"
							</c:if> /><input
						name="isShow" type="hidden" value="false" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="isShow"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth" id="ulIsSys" style="display:none;">
					<li class="editor-label"><label for="isSys">是否系统级</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="是否系统级 字段是必需的。" id="isSys" name="isSys"
						type="checkbox" value="true"
						<c:if test="${core_Dept.isSys}">
								checked="checked"
							</c:if> /><input
						name="isSys" type="hidden" value="false" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="isSys"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth" style="width:auto;">
					<li class="editor-label"><label for="remark">部门说明</label></li>
					<li class="editor-field"><textarea cols="20" data-val="true"
							data-val-length="部门说明不能超过200字符" data-val-length-max="200"
							id="remark" name="remark" rows="2"
							style=" width: 450px; height: 100px;" value="${core_Dept.remark}">
</textarea></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="remark"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>