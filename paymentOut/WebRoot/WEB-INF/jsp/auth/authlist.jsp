<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.actiontype = "${actiontype}";
	rwpTemp.authbaritems = rwpTemp.actiontype == "" ? {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			dia : {
				title : '权限'
			},
			url : '${ctx}/authController/addAuth'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '权限'
			},
			url : '${ctx}/authController/editAuth'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/authController/deleteAuth'
		} ]
	} : {};
	rwpTemp.authopts = {
		columns : [ {
			display : '主键',
			name : 'ID',
			isHide : true,
			primarykey : true
		}, {
			display : '权限名称',
			name : 'authName',
			width : 180
		} ],
		toolbar : rwpTemp.authbaritems,
		checkbox : true,
		pageSize : 50,
		url : '${ctx}/authController/authJson',
		height : '99%'
	};
	rwpTemp.authids = _rwpSplit + "${authids}" + _rwpSplit;
	$(function() {
		if (rwpTemp.actiontype == "selectauth") {
			rwpTemp.authopts.toolbar = null;
			rwpTemp.authopts.isSelected = rwpPluginHelper.gridIsSelectedFun(
					'ID', rwpTemp.authids);
			rwpTemp.authopts.height = '49%';
			rwpTemp.authopts.url = '${ctx}/authController/authDialogJson';
		} else if (rwpTemp.actiontype == "forrole") {
			rwpTemp.authopts.toolbar = null;
			rwpTemp.authopts.checkbox = false;
			rwpTemp.authopts.height = '49%';
			rwpTemp.authopts.url = '${ctx}/authController/authDialogJson'
					+ "?actiontype=" + rwpTemp.actiontype + "&authids="
					+ rwpTemp.authids;
		} else {
			rwpTemp.authopts.columns
					.push({
						display : '详细',
						width : 60,
						render : function(rowdata) {
							var html = '<a title="'
									+ rowdata.authName
									+ '所属动作" href="${ctx}/authController/actionTree?actions=forauth&id='
									+ rowdata.ID
									+ '" rwpOpts="{ isdialog: true }">详细</a>';
							return html;
						}
					});
			rwpTemp.authopts.onAfterShowData = function() {
				rwpListLayout.gridRowclick('authgrid');
			};
			$("#searform").rwpUIForm({
				isSearch : true
			}); //应用查询form表单            
			$("#seachbtn").click(function() {
				rwpListLayout.searchGrid('searform');
			});
			$("#seachreset").click(function() {
				$('#searform')[0].reset();
			});

		}
		$("#authgrid").rwpUIDatagrid(rwpTemp.authopts);
	});
</script>
<c:if test="${actiontype==null||actiontype=='' }">
	<form id="searform">
		<div class="searchBar">
			<dl style="width: 286px;">
				<dt class="labelWidth" style="width: 75px;">权限名称：</dt>
				<dd class="inputWidth">
					<input name="authNameSearch" type="text" id="authNameSearch"
						query-options="{ 'fieldName': 'authName', 'whereField':'like', 'fieldType':'String' }" />
				</dd>
			</dl>
			<dl style="width: 144px;">
				<dd class="inputWidth">
					<a href="javascript:void(0);" id="seachbtn" class="rwpButton">查找</a>
				</dd>
				<dd class="inputWidth">
					<a href="javascript:void(0);" id="seachreset" class="rwpButton">重置</a>
				</dd>
			</dl>
		</div>
	</form>
</c:if>
<div class="tablepanel">
	<div id="authgrid"></div>
</div>