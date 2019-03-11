<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.businessconfigstrbaritems = {
		items : [
				{
					text : '添加',
					click : rwpListLayout.gridToolButtonClick.itemclick,
					icon : 'add',
					min : 0,
					max : 0.5,
					dia : {
						title : '图片'
					},
					url : '${ctx}/mpcConfigController/addMacPhoto?winId=${winId}'
				},
				{
					text : '修改',
					click : rwpListLayout.gridToolButtonClick.itemclick,
					icon : 'edit',
					min : 1,
					max : 1,
					dia : {
						title : '图片'
					},
					url : '${ctx}/mpcConfigController/editMacPhoto'
				}, {
					text : '删除',
					click : rwpListLayout.gridToolButtonClick.itemclick,
					icon : 'delete',
					min : 1,
					max : 1,
					confirm : true,
					postajax : true,
					url : '${ctx}/mpcConfigController/deleteById'
				}, {
					text : '返回上一页',
					click : rwpListLayout.gridToolButtonClick.itemclick,
					icon : 'add',
					min : 0,
					max : 10000,
					link : true,
					url : '${ctx}/macWindowsController/macList'
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
									},
									{
										display : '图片名称',
										width : 180,
										render : function(data) {
											if (data.macPhoto != null) {
												return data.macPhoto.photoname;
											}
										}
									},
									{
										display : '系统级别',
										name : 'relationType',
										width : 80,
										render : function(data) {
											if (data.relationType == 1) {
												return "系统";
											} else if (data.relationType == 2) {
												return "部门";
											} else if (data.relationType == 3) {
												return "窗口";
											}
										}
									},
									{
										display : '操作',
										width : 100,
										render : function(row) {
											var html = '';
											html = '<a href="${ctx}/macPhotoController/viewPhoto?id='
													+ row.photoId
													+ '" target ="_blank">查看样图</a>';
											return html;
										}
									}],
							pageSize : 10,
							toolbar : rwpTemp.businessconfigstrbaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							url : '${ctx}/mpcConfigController/photoListOfMacJson?winId=${winId}',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout
										.gridRowclick('businessconfigstrgrid')
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