<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	//rwpTemp.businessConfigid = parseInt("${id}");
	//rwpTemp.isall = parseInt("${isall}");
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
					pageSize : 5, 
					url : '${ctx}/businessconfigController/businessconfigDialogJson?',
					tree : {
						columnName : 'businessname'
					},
					isSelected : rwpPluginHelper.gridIsSelectedFun('id',
							_rwpSplit + rwpTemp.parentid + _rwpSplit),//没有parentid
					height : '99%',
					checkbox : false
				});
	});
	$(function() {
		$("#deptform1").rwpUIForm({
		//	inputWidth : 150,
			isSearch : true
		}); //应用查询form表单      	
		$("#seachbtn1").click(function() {
			rwpListLayout.searchGrid('deptform1');
		});
		$("#seachreset1").click(function() {
			$('#deptform1')[0].reset();
		});
		/* 	$("#sqjgrid").rwpUIDatagrid(rwpTemp.sqjopts); */
	});
</script>
<div class="searchBar">
	<form id="deptform1">
		<dl style="width:279px;">
			<dt class="labelWidth" style="width:75px;">业务名：</dt>
			<dd class="inputWidth" >
				<input name="deptNameSearch" type="text" id="deptNameSearch"
					query-options="{ 'fieldName': 'businessName', 'whereField':'like', 'fieldType':'String' }" />
			</dd>
		</dl>
		<!--<dl style="width: 274px;">
			<dt class="labelWidth" style="width: 63px;">状态：</dt>
			<dd class="inputWidth">
				<select id="isStartSearch" name="isStartSearch"
					query-options="{ 'fieldName':'businessType', 'whereField':'like', 'fieldType':'String' }">
					<option value="">所有状态</option>
					<option value="0">户口申报</option>
					<option value="1">户口迁入</option>
					<option value="2">户口迁出</option>
					<option value="3">户口注销</option>
					<option value="4">信息变更</option>
				</select>
			</dd>
		</dl> -->
		<dl style="width: 75px;">
			<dd class="inputWidth">
				<a id="seachbtn1" class="rwpButton">查找</a>
			</dd>
		<!-- 	<dd class="inputWidth">
				<a id="seachreset1" class="rwpButton">重置</a>
			</dd> -->
		</dl>
	</form>
</div>
<div id="businessConfiggrid"></div>
