<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
 rwpTemp.deptbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			url : '${ctx}/photoSampleController/addSample',
			link: true
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			link: true,
			url : '${ctx}/photoSampleController/editSample'
		}/* , {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/photoSampleController/deleteSample'
		} *//* ,{
			text : '返回上一页',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 10000,
			link : true,
			url : '${ctx}/businessconfigstrController/businessconfigstrlist?id=${applytypeid}&businessconfigid=${businessconfigid}'
		} */ ]
	}; 

	$(function() {
		$("#deptgrid")
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
										display : '样图名称（中）',
										name : 'bconfigstrcname',
										width : 180
									},
									{
										display : '上传时间',
										name : 'createtime',
										width : 160
									},
									{
										display : '有效状态',
										name : 'isvalid',
										width : 60,
										render : function(row) {
											var html = '';
											if (!isNaN(row.isvalid)) {
												html = row.isvalid == 1 ? '<span class="fClr2">有效</span>'
														: '<span class="fClr4">失效</span>';
											}
											return html;
										}
									}, 
									{
										display : '操作',
										width : 60,
										render : function(rowdata) {
											var html = '<span class="rwpDgIcon">';
											if (rowdata.id > 0) {
												if (rowdata.isvalid)
													html += '<a href="${ctx}/photoSampleController/showSample?isvalid=0&id='
															+ rowdata.id
															+ '" rwpOpts="{ isajax: true }" >失效</a>';
												else
													html += '<a href="${ctx}/photoSampleController/showSample?isvalid=1&id='
															+ rowdata.id
															+ '" rwpOpts="{ isajax: true }">生效</a>';
											}
											return html;
										}
									},{
										display : '样图描述',
										name : 'describe',
										width : 300
									},{
										display : '查看样图',
										name : 'photosamplepath',
										width : 100,
										render : function(row) {
											var html = '';
											html = '<a href="${ctx}/photoSampleController/viewPhoto?id='
											+row.id
											+'" target ="_blank">查看样图</a>';
											return html;
										}
									} ],
							toolbar : rwpTemp.deptbaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							url : '${ctx}/photoSampleController/sampleJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('deptgrid');
							}
						});

	});
	
	$(function() {
		$(".pageHeader").html("<i class='i'></i>微信-样图管理");
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
		/* 	$("#sqjgrid").rwpUIDatagrid(rwpTemp.sqjopts); */
	});
</script>
<div class="searchBar">
	<form id="deptform">
		<dl style="width:286px;">
			<dt class="labelWidth" style="width:100px;">样图中文名称：</dt>
			<dd class="inputWidth">
				<input name="deptNameSearch" type="text" id="deptNameSearch"
					query-options="{ 'fieldName': 'bconfigstrcname', 'whereField':'like', 'fieldType':'String' }" />
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
	<div id="deptgrid"></div>
</div>