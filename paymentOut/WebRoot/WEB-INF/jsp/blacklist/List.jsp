<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
 rwpTemp.blacklistbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			dia : {
				title : '黑名单'
			}, 
			url : '${ctx}/blackListController/addBlack'
		},{
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/blackListController/deleteBlack'
		}]
	}; 
	$(function() {
		$("#blacklistgrid")
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
										display : '姓名',
										name : 'name',
										width : 120
									},
									{
										display : '身份证',
										name : 'cardnumber',
										width : 180
									},
									{
										display : '创建时间',
										name : 'createtime',
										width : 180
									}],
							toolbar : rwpTemp.blacklistbaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							url : '${ctx}/blackListController/blackListJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('blacklistgrid');
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
			<dt class="labelWidth" style="width:50px;">姓名：</dt>
			<dd class="inputWidth">
				<input name="name" type="text" id="name"
					query-options="{ 'fieldName': 'name', 'whereField':'like', 'fieldType':'String' }" />
			</dd>
		</dl>
		<dl style="width:275px;">
			<dt class="labelWidth" style="width:75px;">身份证号：</dt>
			<dd class="inputWidth">
				<input name="cardnumber" type="text" id="cardnumber"
					query-options="{ 'fieldName': 'cardnumber', 'whereField':'like', 'fieldType':'String' }" />
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
	<div id="blacklistgrid"></div>
</div>