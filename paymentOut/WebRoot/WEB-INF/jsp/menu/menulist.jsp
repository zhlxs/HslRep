<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.menubaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			dia : {
				title : '菜单'
			},
			url : '${ctx}/menuController/addMenu'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '菜单'
			},
			url : '${ctx}/menuController/editMenu'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/menuController/deleteMenu'
		} ]
	};
	$(function() {
		$("#menugrid")
				.rwpUIDatagrid(
						{
							columns : [
									{
										display : '主键',
										name : 'ID',
										isHide : true,
										primarykey : true
									},
									{
										display : '菜单名称',
										name : 'menuName',
										width : 250
									},
									{
										display : '分类标示',
										name : 'orderCode',
										width : 250
									},
									{
										display : '菜单路径',
										name : 'menuUrl',
										width : 250
									},
									{
										display : '状态',
										name : 'isShow',
										width : 60,
										render : function(row) {
											var html = '';
											if (!isNaN(row.isShow)) {
												html = row.isShow == 1 ? '<span class="fClr3">显示</span>'
														: '<span class="fClr4">不显示</span>';
											}
											return html;
										}
									},
									{
										display : '操作',
										width : 120,
										render : function(rowdata) {
											var html = '<span class="rwpDgIcon">';
											if (rowdata.ID > 0) {
												if (rowdata.isShow)
													html += '<a href="${ctx}/menuController/showMenu?isshow=0&id='
															+ rowdata.ID
															+ '" rwpOpts="{ isajax: true }" class="eye2 icon">&nbsp;</a>';
												else
													html += '<a href="${ctx}/menuController/showMenu?isshow=1&id='
															+ rowdata.ID
															+ '" rwpOpts="{ isajax: true }" class="eye1 icon">&nbsp;</a>';
												/* html += '<a href="${ctx}/menuController/moveMenu?movetype=1&id='
														+ rowdata.ID
														+ '" rwpOpts="{ isajax: true }"class="up icon">&nbsp;</a>';
												html += '<a href="${ctx}/menuController/moveMenu?movetype=2&id='
														+ rowdata.ID
														+ '" rwpOpts="{ isajax: true }" class="down icon">&nbsp;</a></span>'; */
											}
											return html;
										}
									} ],
							toolbar : rwpTemp.menubaritems,
							usePager : false,
							checkbox : true,
							autoCheckChildren : false,
							tree : {
								columnName : 'menuName'
							},
							url : '${ctx}/menuController/menuJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('menugrid')
							}
						});
	});
</script>

<div class="tablepanel">
	<div id="menugrid"></div>
</div>
