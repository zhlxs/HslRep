<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.modelbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			dia : {
				title : '角色'
			},
			url : '${ctx}/roleController/addRole'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '角色'
			},
			url : '${ctx}/roleController/editRole'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/roleController/deleteRole'
		}]
	};
	rwpTemp.roleopts = {
		columns : [ {
			display : '主键',
			name : 'id',
			isHide : true,
			primarykey : true
		}, {
			display : '模板名称',
			name : 'modelname',
			width : 150
		} ],
		toolbar : rwpTemp.modelbaritems,
		checkbox : true,
		url : '${ctx}/appraiseContrller/apprModeleJson',
		height : '99%'
	};
	rwpTemp.actiontype = "${actiontype}";
	rwpTemp.roleids = _rwpSplit + "${modelId}" + _rwpSplit;
	$(function() {
		if (rwpTemp.actiontype == "selectModel") {
			rwpTemp.roleopts.toolbar = null;
			rwpTemp.roleopts.isSelected = rwpPluginHelper.gridIsSelectedFun(
					'id', rwpTemp.roleids);
			;
			rwpTemp.roleopts.url = '${ctx}/appraiseContrller/apprModeleJson';
		} else if (rwpTemp.actiontype == "foruser") {
			rwpTemp.roleopts.toolbar = null;
			rwpTemp.roleopts.checkbox = false;
			rwpTemp.roleopts.url = '${ctx}/roleController/roleDialogJson'
					+ "?actiontype=" + rwpTemp.actiontype + "&roleids="
					+ rwpTemp.roleids;
		} else {
			rwpTemp.roleopts.columns
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
			rwpTemp.roleopts.onAfterShowData = function() {
				rwpListLayout.gridRowclick('rolegrid');
			};
			$("#searform").rwpUIForm({
				inputWidth : 150,
				isSearch : true
			}); //应用查询form表单            
			$("#seachbtn").click(function() {
				rwpListLayout.searchGrid('searform');
			});
			$("#seachreset").click(function() {
				$('#searform')[0].reset();
			});
		}
		$("#rolegrid").rwpUIDatagrid(rwpTemp.roleopts);
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
	<div id="rolegrid"></div>
</div>