<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
 rwpTemp.machinebaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			dia : {
				title : '叫号机'
			}, 
			url : '${ctx}/callMachineController/addMachine'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '叫号机'
			}, 
			url : '${ctx}/callMachineController/editMachine'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/callMachineController/delete'
		},
		{
			text : '参数模板配置',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : '',
			min : 1,
			max : 1,
			dia : {
				title : ''
			},
			//confirm : true,
			//link : true,
			url : '${ctx}/callMachineController/macsettingView'
		}]
	}; 
	$(function() {
		$("#machinegrid")
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
										display : '设备机器码',
										name : 'deviceNumber',
										width : 220
									},
									{
										display : '所属部门',
										name : 'deptId',
										width : 160,
										render : function(data){
											if(data.dept != null){
												return data.dept.deptName;
											}
										}
									},
									{
										display : '设备IP',
										name : 'machineIp',
										width : 120
									},
									{
										display : '参数模板',
										/* name : 'modelId', */
										width : 140,
										render : function(data){
											if(data.model != null){
												return data.model.modelName;
											}else{
												return "未配置";
											}
										}
									},
									{
										display : '创建时间',
										name : 'createtime',
										width : 180
									}
									/* {
						                display: '业务配置', 
						                width: 120, 
						                render: function (rowdata) {
						                    var html = '<a href="${ctx}/callMachineController/businessConfig?deviceNumber=' + rowdata.deviceNumber
						                    		+ '"  rwpOpts="{ ispage: true }">业务开通' + '</a>';
						                    return html;
						                }
						            } */],
							toolbar : rwpTemp.machinebaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							url : '${ctx}/callMachineController/machineListJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('machinegrid');
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
			<dt class="labelWidth" style="width:70px;">机器码：</dt>
			<dd class="inputWidth">
				<input name="deviceNumber" type="text" id="deviceNumber"
					query-options="{ 'fieldName': 'm.deviceNumber', 'whereField':'like', 'fieldType':'String' }" />
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
	<div id="machinegrid"></div>
</div>