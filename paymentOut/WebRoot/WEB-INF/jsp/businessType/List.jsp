<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.menubaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			dia : {
				title : '业务申请类型'
			},
			url : '${ctx}/businessConfigTypeController/addBusinessType?serCode=${serCode}'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '业务申请类型'
			},
			url : '${ctx}/businessConfigTypeController/editBusinessType?serCode=${serCode}'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/businessConfigTypeController/delete'
		},{
			text : '返回上一页',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 10000,
			link : true,
			url : '${ctx}/policeServiceController/policeserviceList'
		} ]
	};
	$(function() {
		$("#applyTypeid")
				.rwpUIDatagrid(
						{
							columns : [
									{
										display : '主键',
										name : 'id',
										isHide : true,
										primarykey : true
									},
									{
										display : '所属业务',
										name : 'configName',
										width : 250
									},
									{
										display : '业务类型名',
										name : 'applyTypeName',
										width : 250
									},
									{
										display : '业务编码',
										name : 'orderCode',
										width : 60
									},
									{
										display : '是否有效',
										name : 'isValid',
										width : 80,
										render : function(row) {
											var html = '';
											if (!isNaN(row.isValid)) {
												html = row.isValid == 1 ? '<span class="fClr3">有效</span>'
														: '<span class="fClr4">失效</span>';
											}
											return html;
										}  
									},
									{
									   display : '修改启用状态',
										width : 100,
										render : function(rowdata) {
											var html = '<span class="rwpDgIcon">';
											if (rowdata.id > 0) {
												if (rowdata.isValid)
													html += '<a href="${ctx}/businessConfigTypeController/isValidType?isValid=false&id='
															+ rowdata.id
															+ '" rwpOpts="{ isajax: true }" class="eye2 icon">&nbsp;</a>';
												else
													html += '<a href="${ctx}/businessConfigTypeController/isValidType?isValid=true&id='
															+ rowdata.id
															+ '" rwpOpts="{ isajax: true }" class="eye1 icon">&nbsp;</a>';
											}
											return html;
										}
									} ,{
										display : '操作',
										width : 100,
										render : function(rowdata) {
											var html = '';
											html += '<a class="grid_row_tool" href="${ctx}/businessconfigStuController/businessSruffList?serCode=${serCode}&applytypeid='
													+ rowdata.id
													+ '" rwpOpts="{ ispage: true }">管理申请材料</a>';
											return html;
										}
									}],
							toolbar : rwpTemp.menubaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false, 
							//tree : {
								//columnName : 'configName'
							//},
							url : '${ctx}/businessConfigTypeController/businessTypeJson?serCode=${serCode}',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('applyTypeid')
							}
						});
	});
	$(function() {
		$(".pageHeader").html("<i class='i'></i>申请类型管理");
		$("#deptform").rwpUIForm({
			inputWidth : 150,
			isSearch : true
		}); 
		//应用查询form表单      	
		$("#seachbtn").click(function() {
			rwpListLayout.searchGrid('deptform');
		});
		$("#seachreset").click(function() {
			$('#deptform')[0].reset();
		});
	});
</script>
<div class="tablepanel">
	<div id="applyTypeid"></div>
</div>
