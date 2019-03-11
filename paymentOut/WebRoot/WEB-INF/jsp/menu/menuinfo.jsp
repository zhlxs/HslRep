<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.menuAjaxSelectColumns = [
			{
				domID : "areaValue",
				isCombobox : true,
				defaultOption : {
					Text : "-----请选择区域-----",
					Value : ""
				},
				selectVal : "",
				url : "${ctx}/menuController/areaJson"
			},
			{
				domID : "controlValue",
				isCombobox : true,
				queryParamName : "areaValue",
				defaultOption : {
					Text : "----请选择控制器----",
					Value : ""
				},
				selectVal : "${menuHelper.menu.actionList}".substring(0,
						"${menuHelper.menu.actionList}".indexOf("|")),
				url : "${ctx}/menuController/controlJson"
			},
			{
				domID : "actionValue",
				isCombobox : true,
				queryParamName : "controlValue",
				defaultOption : {
					Text : "-----请选择动作-----",
					Value : ""
				},
				selectVal : "${menuHelper.menu.actionList}"
						.substring("${menuHelper.menu.actionList}".indexOf("|") + 1),
				url : "${ctx}/menuController/actionJson"
			} ];
	rwpTemp.f_selectparentmenu = function() {
		var url = '${ctx}/menuController/menuDialog?parentid='
				+ $("#parentID").val() + '&isall=1' + '&rnd' + Math.random();
		return rwpPluginHelper.selectContact('选择上一级菜单', url, 'ID', 'menuName',
				'parentID', 'parentMenuName');
	}
	rwpTemp.f_selectmenuaction = function() {
		var url = '${ctx}/authController/actionTree?actions='
				+ $("#actionList").val() + '&rnd' + Math.random();
		return rwpPluginHelper.selectContact('选择所属动作', url, 'controllerAction',
				'display', 'actionList', 'actionNames');
	}
	$(function() {
		$("#parentMenuName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectparentmenu
		});
		$("#actionNames").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectmenuaction
		});
		if ("1" == 1)
			$('#ulIsSys').show(); //系统级管理员 可设置菜单的系统级权限

		$('#selectMenuIcon')
				.rwpUIIconSelect(
						{
							iconClassArr : [ 'default', 'work', 'manage',
									'user', 'system', 'config' ],
							imgSrc : "${ctx}/js/rwpUI/skins/lightblue/images/imgMenuIcon2.gif",
							classFieldDomID : 'iconCssClass'
						});

		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true
		});

		$.rwpUI.AjaxSelect({
			columns : rwpTemp.menuAjaxSelectColumns,
			defaultval : '',
			selectBoxHeight : 200
		});
	});
</script>

<form action="${ctx}/menuController/saveMenu" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>菜单信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段 菜单ID 必须是一个数字。"
				data-val-required="菜单ID不能为空" id="ID" name="id" type="hidden"
				value="${menuHelper.menu.id }" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="parentID">所属菜单</label></li>
					<li style=" display: none;"><input data-val="true"
						data-val-number="字段 所属菜单 必须是一个数字。" data-val-regex="请选择所属菜单"
						data-val-regex-pattern="^[1-9]\d*|0$"
						data-val-required="所属菜单 字段是必需的。" id="parentID" name="parentId"
						type="hidden" value="${not empty menuHelper.menu.parentId?menuHelper.menu.parentId:"0"}" />
					</li>
					<li class="editor-field"><input id="parentMenuName"
						name="parentMenuName" type="text"
						value="${not empty menuHelper.menu.parentMenuName?menuHelper.menu.parentMenuName:'菜单'}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="parentID"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="menuName">菜单名称</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="菜单名称不能超过20字符" data-val-length-max="20"
						data-val-required="菜单名称不能为空" id="menuName" name="menuName"
						type="text" value="${menuHelper.menu.menuName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="menuName"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="menuUrl">菜单URL</label></li>
					<li class="editor-field"><select id="areaValue"
						name="areaValue" style="display:none;"></select> <select
						id="controlValue" name="controlValue" style="display:none;"></select>
						<select id="actionValue" name="actionValue" style="display:none;"></select>
						<input id="urlParam" name="urlParam" type="text" value="" /> <label
						for="urlParam" style="margin-left:5px; color: #999;">路径参数</label>
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="menuUrl"
						data-valmsg-replace="true"></span></li>
				</ul>
				<div id="selectMenuIcon" class="labelWidth"></div>
				<input data-val="true" data-val-length="菜单图标不能超过20字符"
					data-val-length-max="20" id="iconCssClass" name="iconCssClass"
					type="hidden" value="${menuHelper.menu.iconCssClass}" />
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="actionList">动作列表</label>
					</li>
					<li style=" display: none;"><input data-val="true"
						data-val-length="动作列表不能超过1000字符" data-val-length-max="1000"
						id="actionList" name="actionList" type="hidden"
						value="${menuHelper.menu.actionList}" /></li>
					<li class="editor-field"><input id="actionNames"
						name="actionNames" type="text"
						value="${menuHelper.menu.actionNames}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="actionList"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="isShow">显示状态</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="显示状态 字段是必需的。" id="isShow" name="isShow"
						<c:if test="${menuHelper.menu.isShow}">
						checked="checked"
						</c:if>
						type="checkbox" value="true" /><input name="isShow" type="hidden"
						value="false" />
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="isShow"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth" id="ulIsSys" style="display:none;">
					<li class="editor-label"><label for="isSys">是否系统级</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="是否系统级 字段是必需的。" id="isSys" name="isSys"
						<c:if test="${menuHelper.menu.isSys}">
						checked="checked"
						</c:if>
						type="checkbox" value="true" /><input name="isSys" type="hidden"
						value="false" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="isSys"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>