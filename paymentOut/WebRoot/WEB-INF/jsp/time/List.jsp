<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.noticebaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			dia : {
				title : '预约时间模板'
			},
			url : '${ctx}/timeModelController/addTimeModel',
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '预约时间模板'
			},
			url : '${ctx}/timeModelController/editTimeModel'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/timeModelController/delete'
		} ]
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
										display : '模板名称',
										name : 'modelName',
										width : 270
									},
									{
										display : '创建时间',
										name : 'createtime',
										width : 180
									},
									{
										display : '操作',
										width : 80,
										render : function(rowdata) {
											var html = '';
											html += '<a class="grid_row_tool" href="${ctx}/timeModelController/timeModelDetail?modelId='
													+ rowdata.id
													+ '" rwpOpts="{ isdialog: true }">详情</a>';
											return html;
										}
									}],
							pageSize : 20,
							toolbar : rwpTemp.noticebaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							url : '${ctx}/timeModelController/modelListJson',
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
		<dl style="width:275px;">
			<dt class="labelWidth" style="width:75px;">模板名称：</dt>
			<dd class="inputWidth">
				<input name="deptNameSearch" type="text" id="deptNameSearch"
					query-options="{ 'fieldName': 'modelName', 'whereField':'like', 'fieldType':'String' }" />
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