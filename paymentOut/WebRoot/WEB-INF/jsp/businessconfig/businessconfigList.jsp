<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<style type="text/css">
.matt{
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
 p{
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>
<script type="text/javascript">
	rwpTemp.deptbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			dia : {
				title : '业务流程'
			},
			url : '${ctx}/businessconfigController/addBusinessconfig'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '业务流程'
			},
			url : '${ctx}/businessconfigController/editBusinessconfig'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/businessconfigController/deleteBusinessconfig'
		} ]
	};
	//初始化Json
	rwpTemp.businessconfiglistiniturl = '${ctx}/businessconfigController/businessconfigJson';
	rwpTemp.treeUrl = '${ctx}/businessconfigController/businessTreeJson';
	rwpTemp.getBusinessGridDelay = function(rowData,level) {
		if (rowData.isdelay) {//延时加载
			var params = [];
			params.push({name : 'parentid',value : rowData.id});
			return {url :rwpTemp.treeUrl,params :params};
		}
		rwpTemp.bindGridRowLink = function(newAppendRowData) {//绑定点击“+”获取子集事件
			var gridManager = $('#deptgrid').data('rwpUIDatagrid'),
			$bodyTable = gridManager.innerDom.gridBodyTable,
			startIndex,
			endIndex,
			$newAppendTrs,
			$newAppendLinks;
			if($.isArray(newAppendRowData) && newAppendRowData.length > 0){
				startIndex = newAppendRowData[0]._rowIndex;
				endIndex = newAppendRowData[newAppendRowData.length - 1]._rowIndex;
				$newAppendTrs = $('tr',$bodyTable).slice(startIndex,endIndex + 1);
				$newAppendLinks = $('a',$newAppendTrs);
				rwpListLayout.gridRowclick('deptgrid',$newAppendLinks);
				//////////////////好陌生的表单元素用法/////////////////////
			};
		};
	};
	$(function() {
		$("#deptgrid")
				.rwpUIDatagrid(
						{columns : [
									{display : '主键',name : 'id',isHide : true,primarykey : true},
									{display : '业务名称',name : 'businessname',width : 260},
									{display : '业务编码',name : 'businesscode',width : 200},
									{display : '创建时间',name : 'createtime',width : 180},/* ,
									{display : '办理须知',name : 'businessnotice',width : 300,
										render : function(row) {
											var html = '';
											html = '<div class="matt" style="width:280px;">'
											+row.businessnotice
											+'</div>';
											return html;
										}
									},
									{display : '办事依据',name : 'mattersneedattention',width : 350,
										render : function(row) {
											var html = '';
											html = '<div class="matt" style="width:330px;">'
											+row.mattersneedattention
											+'</div>';
											return html;
										}
									},
									{display : '办理类型',name : 'businessType',width : 80,
										render : function(row) {
											var html = '';
											if (!isNaN(row.businessType)) {
												if (row.businessType == 0) {
													html = '户口申报';
												} else if (row.businessType == 1) {
													html = '户口迁入';
												} else if (row.businessType == 2) {
													html = '户口迁出';
												} else if (row.businessType == 3) {
													html = '户口注销';
												} else if (row.businessType == 4) {
													html = '信息变更';
												}
											}
											return html;
										}
									} */
									{display : '有效状态',name : 'isvalid',width : 60,
										render : function(row) {
											var html = '';
											if (!isNaN(row.isvalid)) {
												html = row.isvalid == 1 ? '<span class="fClr3">有效</span>'
														: '<span class="fClr4">失效</span>';
											}
											return html;
										}
									},
									{display : '操作',width : 60,
										render : function(rowdata) {
											var html = '<span class="rwpDgIcon">';
											if (rowdata.id > 0) {
												if (rowdata.isvalid)
													html += '<a href="${ctx}/businessconfigController/showBusinessconfig?isvalid=0&id='
															+ rowdata.id
															+ '" rwpOpts="{ isajax: true }" class="eye2 icon">&nbsp;</a>';
												else
													html += '<a href="${ctx}/businessconfigController/showBusinessconfig?isvalid=1&id='
															+ rowdata.id
															+ '" rwpOpts="{ isajax: true }" class="eye1 icon">&nbsp;</a>';
											}
											return html;
										}
									} ,{display : '操作',width : 140,
										render : function(rowdata) {
											var html = '';
											html += '<a class="grid_row_tool" href="${ctx}/businessconfigController/businessconfigDetial?id='
													+ rowdata.id
													+ '" rwpOpts="{ ispage: true }">详情</a>'
													+'&nbsp;&nbsp;<a class="grid_row_tool" href="${ctx}/applyTypeController/applyTypeList?id='
													+ rowdata.id
													+ '" rwpOpts="{ ispage: true }">管理申请类型</a>';
											return html;
										}
									}],
							toolbar : rwpTemp.deptbaritems,
							//usePager : false,
							pageSize : 1,
							checkbox : true,
							autoCheckChildren : false,
							tree : {columnName : 'businessname',delay:rwpTemp.getBusinessGridDelay},
							url : rwpTemp.businessconfiglistiniturl,//初始化Json
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('deptgrid');
							}
						});

	});
	$(function() {
		$("#deptform").rwpUIForm({
			inputWidth : 150,
			isSearch : true
		}); 
		//应用查询form表单      	
		$("#seachbtn").click(function() {
			var name=$("#busiNameSearch").val();
			var jurl ='${ctx}/businessconfigController/getBusiness?1=1';
			if(name!=null && name!=''){
				jurl += '&businessname='+name;
			};
			 $("#deptgrid").data('rwpUIDatagrid').refreshData({
				url : encodeURI(jurl),
				newPage : 1
			});	
		});
		$("#seachreset").click(function() {
			$('#deptform')[0].reset();
		});
	});
</script>

<div class="searchBar">
	<form id="deptform">
		<dl style="width:286px;">
			<dt class="labelWidth" style="width:75px;">业务名称：</dt>
			<dd class="inputWidth">
				<input name="busiNameSearch" type="text" id="busiNameSearch"
					query-options="{ 'fieldName': 'businessname', 'whereField':'like', 'fieldType':'String' }" />
			</dd>
		</dl>
		<!-- <dl style="width: 274px;">
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