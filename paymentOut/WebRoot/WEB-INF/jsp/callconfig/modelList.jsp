<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
 rwpTemp.callconfigbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			link : true,
			url : '${ctx}/macConfigController/addConfigModel'
		},
		/* {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			link : true, 
			url : '${ctx}/macConfigController/editMachine'
		}, */ 
		{
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/macConfigController/delete'
		}]
	}; 
	$(function() {
		$("#modelgrid")
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
										display : '操作',
										width : 80,
										render : function(rowdata) {
											var html = '';
											if (rowdata.isStart)
												html += '<a href="${ctx}/macConfigController/setModelStart?isstart=0&id='
														+ rowdata.id
														+ '" rwpOpts="{ isajax: true }"><span class="fClr4">停用</span></a>';
											else
												html += '<a href="${ctx}/macConfigController/setModelStart?isstart=1&id='
														+ rowdata.id
														+ '" rwpOpts="{ isajax: true }">启用</a>';
											return html;
										}
									},
									{
										display : '状态',
										width : 70,
										render : function(row) {
											var html = row.isStart == 1 ? '<span class="fClr3">启用</span>'
													: '<span class="fClr4">停用</span>';
											return html;
										}
									},
									{
										display : '模板名称',
										name : 'modelName',
										width : 220
									},
									{
										display : '创建时间',
										name : 'createtime',
										width : 180
									}],
							toolbar : rwpTemp.callconfigbaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							url : '${ctx}/macConfigController/configModelListJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('modelgrid');
							}
						});

	});
	$(function() {
		//$(".pageHeader").html("<i class='i'></i>样图管理");
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
<div class="searchBar">
	<form id="deptform">
		<dl style="width:270px;">
			<dt class="labelWidth" style="width:70px;">模板名称：</dt>
			<dd class="inputWidth">
				<input name="modelName" type="text" id="modelName"
					query-options="{ 'fieldName': 'modelname', 'whereField':'like', 'fieldType':'String' }" />
			</dd>
		</dl>
		<dl style="width: 144px;">
			<dd class="inputWidth">
				<a id="seachbtn" class="rwpButton">查找</a>
			</dd>
			<dd class="inputWidth">
				<a id="seachreset" class="rwpButton">重置</a>
			</dd>
		</dl>
	</form>
</div>
<div class="tablepanel">
	<div id="modelgrid"></div>
</div>