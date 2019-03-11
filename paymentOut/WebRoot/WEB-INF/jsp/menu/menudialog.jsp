<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.parentid = parseInt("${parentid}");
	rwpTemp.isall = parseInt("${isall}");
	$(function() {
		$("#menudialoggrid").rwpUIDatagrid(
				{
					columns : [ {
						display : '主键',
						name : 'ID',
						isHide : true,
						primarykey : true
					}, {
						display : '菜单名称',
						name : 'menuName',
						width : 250
					} ],
					pageSize : 5,
					url : '${ctx}/menuController/menuDialogJson?isall='
							+ rwpTemp.isall,
					tree : {
						columnName : 'menuName'
					},
					isSelected : rwpPluginHelper.gridIsSelectedFun('ID',
							_rwpSplit + rwpTemp.parentid + _rwpSplit),
					height : '79%',
					checkbox : false
				});
	});
</script>

<div id="menudialoggrid"></div>
