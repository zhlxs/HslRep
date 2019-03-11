<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.parentId = parseInt("${id}"); 
	$(function() {
		$("#businessConfiggrid").rwpUIDatagrid(
				{
					columns : [ {
						display : '主键',
						name : 'id',
						isHide : true,
						primarykey : true
					}, {
						display : '流程名称',
						name : 'businessname',
						width : 250
					} ],
					usePager : false,
					url : '${ctx}/businessconfigController/businessParentDialogJson?isall='
							+ 1,
					/* tree : {
						columnName : 'businessName'
					}, */
					isSelected : rwpPluginHelper.gridIsSelectedFun('id',
							_rwpSplit+ rwpTemp.parentId + _rwpSplit),//没有parentid
					height : '79%',
					checkbox : false
				});
	});
</script>

<div id="businessConfiggrid"></div>
