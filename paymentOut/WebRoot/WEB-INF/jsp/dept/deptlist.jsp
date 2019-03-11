<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>

<script type="text/javascript">
	rwpTemp.deptbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			dia : {
				title : '部门'
			},
			url : '${ctx}/deptController/addDept'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '部门'
			},
			url : '${ctx}/deptController/editDept'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/deptController/deleteDept'
		} ]
	};
	rwpTemp.deptlistiniturl = '${ctx}/deptController/deptJson';//初始化Json
	rwpTemp.treeUrl = '${ctx}/deptController/deptTreeJson';//初始化Json
	rwpTemp.getDeptGridDelay = function(rowData, level) {
		if (rowData.isdelay) {
			var params = [];
			params.push({
				name : 'parentid',
				value : rowData.ID
			});
			return {
				url : rwpTemp.treeUrl,
				params : params
			};
		}
		rwpTemp.bindGridRowLink = function(newAppendRowData) { //绑定表格行链接事件
			var gridManager = $('#policeservicegrid').data('rwpUIDatagrid'), $bodyTable = gridManager.innerDom.gridBodyTable, startIndex, endIndex, $newAppendTrs, $newAppendLinks;
			if ($.isArray(newAppendRowData) && newAppendRowData.length > 0) {
				startIndex = newAppendRowData[0]._rowIndex;
				endIndex = newAppendRowData[newAppendRowData.length - 1]._rowIndex;
				$newAppendTrs = $('tr', $bodyTable).slice(startIndex,
						endIndex + 1);
				$newAppendLinks = $('a', $newAppendTrs);
				rwpListLayout.gridRowclick('deptgrid', $newAppendLinks);
			}
		};
		return false;
	};
	$(function() {
		$("#deptgrid")
				.rwpUIDatagrid(
						{
							columns : [
									{
										display : '主键',
										name : 'ID',
										isHide : true,
										primarykey : true
									},
									{
										display : '部门名称',
										name : 'deptName',
										width : 240
									},
									{
										display : '分类标示',
										name : 'orderCode',
										width : 200
									},
									{
										display : '部门代码',
										name : 'deptCode',
										width : 150
									},
									{
										display : '配置',
										width : 100,
										render : function(rowdata) {
											var html = '';
											html += '<a class="btnPlum newbtn" style="color:#fff;padding:2px;5px;" href="${ctx}/dpcConfigController/photoListOfMac?deptId='
													+ rowdata.ID
													+ '" rwpOpts="{ ispage: true }" title="宣传图配置">宣传图配置</a>';
											return html;
										}
									},
									{
										display : '状态',
										name : 'isShow',
										width : 60,
										render : function(row) {
											var html = '';
											if (!isNaN(row.isShow)) {
												html = row.isShow == 1 ? '<span class="fClr3">显示</span>'
														: '<span class="fClr4">不显示</span>';
											}
											return html;
										}
									},
									{
										display : '操作',
										width : 80,
										render : function(rowdata) {
											var html = '<span class="rwpDgIcon">';
											if (rowdata.ID > 0) {
												html += '<a href="${ctx}/deptController/showDept?isShow=0&id='
														+ rowdata.ID
														+ '" rwpOpts="{ isajax: true }" class="eye2 icon">&nbsp;</a>';
											} else {
												html += '<a href="${ctx}/deptController/showDept?isShow=1&id='
														+ rowdata.ID
														+ '" rwpOpts="{ isajax: true }" class="eye1 icon">&nbsp;</a>';
											}
											return html;
										}
									} ],
							pageSize : 1,
							toolbar : rwpTemp.deptbaritems,
							checkbox : true,
							autoCheckChildren : false,
							tree : {
								columnName : 'deptName',
								delay : rwpTemp.getDeptGridDelay
							},
							url : rwpTemp.deptlistiniturl,
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
			var name = $("#deptNameSearch").val();
			var jurl = '${ctx}/deptController/deptByname?1=1';
			if (name != null && name != '') {
				jurl += '&deptname=' + name;
			}
			$("#deptgrid").data('rwpUIDatagrid').refreshData({
				url : encodeURI(jurl),
				newPage : 1
			});
		});

		$("#seachreset").click(function() {
			$('#deptform')[0].reset();
		});

		$("#sqjgrid").rwpUIDatagrid(rwpTemp.sqjopts);
	});
</script>

<div class="searchBar">
	<form id="deptform">
		<dl style="width:286px;">
			<dt class="labelWidth" style="width:75px;">部门名称：</dt>
			<dd class="inputWidth">
				<input name="deptNameSearch" type="text" id="deptNameSearch"
					query-options="{ 'fieldName': 'deptName', 'whereField':'like', 'fieldType':'String' }" />
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