<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
 rwpTemp.addressbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			dia : {
				title : '网点'
			}, 
			url : '${ctx}/deptaddressController/addDeptaddress'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '网点'
			}, 
			url : '${ctx}/deptaddressController/editDeptaddress'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/deptaddressController/delete'
		}]
	}; 
	$(function() {
		$("#addressgrid")
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
										display : '网点地址',
										name : 'address',
										width : 250
									},
									{
										display : '经度',
										name : 'longitude',
										width : 140
									},
									{
										display : '纬度',
										name : 'latitude',
										width : 140
									},
									{
										display : '所属部门',
										name : 'deptId',
										width : 140,
										render : function(data){
											if(data.dept != null){
												return data.dept.deptName;
											}else{
												return "未知";
											}
										}
									},
									{
										display : '评价模板',
										name : 'modelId',
										width : 90,
										render : function(data){
											if(data.model != null){
												var html = '';
												html += '<a style="color:#F37A09;font-size:12px;font-weight:bold;" class="" href="${ctx}/deptaddressController/setModel?id='
														+ data.id
														+ '" rwpOpts="{ isdialog: true }">' + data.model.modelname + '</a>';
												return html;
											}else{
												var html = '';
												html += '<a style="font-size:12px;" class="grid_row_tool" href="${ctx}/deptaddressController/setModel?id='
														+ data.id
														+ '" rwpOpts="{ isdialog: true }">点击配置</a>';
												return html;
											}
										}
									},
									{
										display : '时间模板',
										name : 'tmodelId',
										width : 90,
										render : function(data){
											if(data.tModel != null){
												var html = '';
												html += '<a style="color:#44C426;font-size:12px;font-weight:bold;" class="" href="${ctx}/deptaddressController/setTimeModel?id='
														+ data.id
														+ '" rwpOpts="{ isdialog: true }">' + data.tModel.modelName + '</a>';
												return html;
											}else{
												var html = '';
												html += '<a style="font-size:12px;" class="grid_row_tool" href="${ctx}/deptaddressController/setTimeModel?id='
														+ data.id
														+ '" rwpOpts="{ isdialog: true }">点击配置</a>';
												return html;
											}
										}
									},
									{
										display : '创建时间',
										name : 'createtime',
										width : 180
									}],
							toolbar : rwpTemp.addressbaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							url : '${ctx}/deptaddressController/addressListJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('addressgrid');
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
			<dt class="labelWidth" style="width:75px;">部门名称：</dt>
			<dd class="inputWidth">
				<input name="deptname" type="text" id="deptname"
					query-options="{ 'fieldName': 'cd.deptname', 'whereField':'like', 'fieldType':'String' }" />
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
	<div id="addressgrid"></div>
</div>