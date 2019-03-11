<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.businessconfigstrbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			dia : {
				title : '申请材料'
			},
			url : '${ctx}/businessconfigStuController/addBusinessStuff?applytypeid=${id}'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '申请材料'
			},
			url : '${ctx}/businessconfigStuController/editBusinessStuff?applytypeid=${id}'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/businessconfigStuController/delete'
		},{
			text : '返回上一页',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 10000,
			link : true,
			url : '${ctx}/businessConfigTypeController/typeList?serCode=${serCode}'
		} ]
	};
	$(function() {
		$("#businessconfigstrgrid")
				.rwpUIDatagrid(
						{
							columns : [
									{
										display : '主键',
										name : 'id',
										isHide : true,
										primarykey : true
									} ,
									{
										display : '所属业务类型',
										//name : 'applytypeid',
										width : 220,
										render : function(data){
											if(data.businessconfigType != null){
												return data.businessconfigType.applyTypeName;
											}
										}
									},
									{
										display : '申请材料名称',
										name : 'bconfigstrname',
										width : 240
									} ,
									{
										display : '排序码',
										name : 'ordercode',
										width : 80
									} ,
									{
										display : '材料类型',
										name : 'showtype',
										width : 70,
										render : function(row) {
											var html = '';
											if (!isNaN(row.showtype)) {
												if (row.showtype == 0)
													{html = '图片';}
												else if (row.showtype == 1)
													{html = '附件';}
												else  
													{html = '图片、附件 ';}
											}
											return html;
										}
									},
									{
										display : '是否有效',
										name : 'ismustfill',
										width : 80,
										render : function(row) {
											var html = '';
											if (!isNaN(row.ismustfill)) {
												html = row.ismustfill == 1 ? '<span class="fClr3">有效</span>'
														: '<span class="fClr4">失效</span>';
											}
											return html;
										}  
									},
									{
										display : '操作',
										width : 120,
										render : function(rowdata) {
											var html = '<span class="rwpDgIcon">';
											if (rowdata.ismustfill)
												html += '<a href="${ctx}/businessconfigStuController/showBusinessconfigstr?ismustfill=false&id='
														+ rowdata.id
														+ '" rwpOpts="{ isajax: true }" class="eye2 icon">&nbsp;</a>';
											else
												html += '<a href="${ctx}/businessconfigStuController/showBusinessconfigstr?ismustfill=true&id='
														+ rowdata.id
														+ '" rwpOpts="{ isajax: true }" class="eye1 icon">&nbsp;</a>';
										 	html += '<a href="${ctx}/businessconfigStuController/moveBusinessconfigstr?movetype=1&id='
													+ rowdata.id
													+ '" rwpOpts="{ isajax: true }"class="up icon">&nbsp;</a>';
											html += '<a href="${ctx}/businessconfigStuController/moveBusinessconfigstr?movetype=2&id='
													+ rowdata.id
													+ '" rwpOpts="{ isajax: true }" class="down icon">&nbsp;</a>';
											return html;
										}
									} ,{
										display : '管理',
										width : 100,
										render : function(rowdata) {
											var html = '';
											html += '<a class="grid_row_tool" href="${ctx}/photoSampleController/sampleListofType?applytypeid=${id}&serCode=${serCode}&id='
													+ rowdata.id
													+ '" rwpOpts="{ ispage: true }">样图</a>';
													//+'&nbsp;&nbsp;<a class="grid_row_tool" href="${ctx}/documentController/documentList?applytypeid=${id}&businessconfigid=${businessconfigid}&id='
													//+ rowdata.id
													//+ '" rwpOpts="{ ispage: true }">附件</a>';
											return html;
										}
									}],
							pageSize : 10,
							toolbar : rwpTemp.businessconfigstrbaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							url : '${ctx}/businessconfigStuController/businessStuffJson?id=${id}',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('businessconfigstrgrid')
							}
						});
	});
	$(function() {
		$(".pageHeader").html("<i class='i'></i>申请材料配置");
		$("#businessconfigstrform").rwpUIForm({
			inputWidth : 150,
			isSearch : true
		}); //应用查询form表单      	
		$("#seachbtn").click(function() {
			rwpListLayout.searchGrid('businessconfigstrform');
		});
		$("#seachreset").click(function() {
			$('#businessconfigstrform')[0].reset();
		});
	/* 	$("#sqjgrid").rwpUIDatagrid(rwpTemp.sqjopts); */
	});
</script>
<div class="tablepanel">
	<div id="businessconfigstrgrid"></div>
</div>