<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.paramopts = {
		columns : [{
			display : '主键',
			name : 'id',
			isHide : true,
			primarykey : true
		},
		{
			display : '标题',
			name : 'title',
			width : 120
		},
		{
			display : '更新时间',
			name : 'updatetime',
			width : 180
		}],
		checkbox : true,
		url : '${ctx}/macConfigController/listDialogJson?modelId=${modelId}',
		height : '99%'
	};
	rwpTemp.actiontype = "${actiontype}";
	rwpTemp.roleids = _rwpSplit + "${parentId}" + _rwpSplit;
	$(function() {
		if (rwpTemp.actiontype == "selectparent") {
			rwpTemp.paramopts.toolbar = null;
			rwpTemp.paramopts.isSelected = rwpPluginHelper.gridIsSelectedFun(
					'id', rwpTemp.roleids);
			;
			rwpTemp.paramopts.url = '${ctx}/macConfigController/listDialogJson?modelId=${modelId}';
		} else if (rwpTemp.actiontype == "foruser") {
			rwpTemp.paramopts.toolbar = null;
			rwpTemp.paramopts.checkbox = false;
			rwpTemp.paramopts.url = '${ctx}/questionController/questionListJson'
					+ "?actiontype=" + rwpTemp.actiontype + "&roleids="
					+ rwpTemp.roleids;
		} else {
			rwpTemp.paramopts.columns
					.push({
						display : '详细',
						width : 60,
						render : function(rowdata) {
							var html = '<a title="'
									+ rowdata.roleName
									+ '所属权限" href="${ctx}/authController/authDialog?actiontype=forrole&roleid='
									+ rowdata.ID
									+ '" rwpOpts="{ isdialog: true }">详细</a>';
							return html;
						}
					});
			rwpTemp.paramopts.onAfterShowData = function() {
				rwpListLayout.gridRowclick('paramgrid');
			};
			$("#searform").rwpUIForm({
				inputWidth : 'auto',
				isSearch : true
			}); //应用查询form表单            
			$("#seachbtn").click(function() {
				rwpListLayout.searchGrid('searform');
			});
			$("#seachreset").click(function() {
				$('#searform')[0].reset();
			});
		}
		$("#paramgrid").rwpUIDatagrid(rwpTemp.paramopts);
	});
</script>
<c:if test="${actiontype==null||actiontype=='' }">
	<div class="searchBar">
		<form id="searform">
			<dl style="width:256px;">
				<dt class="labelWidth" style="width:75px;">角色名称：</dt>
				<dd class="inputWidth">
					<input name="roleNameSearch" type="text" id="roleNameSearch"
						query-options="{ 'fieldName': 'roleName', 'whereField':'like', 'fieldType':'String' }" />
				</dd>
			</dl>
			<dl style="width:144px;">
				<dd class="inputWidth">
					<a href="javascript:void(0);" id="seachbtn" class="rwpButton">查找</a>
				</dd>
				<dd class="inputWidth">
					<a href="javascript:void(0);" id="seachreset" class="rwpButton">重置</a>
				</dd>
			</dl>
		</form>
	</div>
</c:if>
<div class="tablepanel">
	<div id="paramgrid"></div>
</div>