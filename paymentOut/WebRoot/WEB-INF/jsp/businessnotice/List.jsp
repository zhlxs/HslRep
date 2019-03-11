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
				title : '办事流程'
			}, 
			url : '${ctx}/dicBusinessnoticeController/addBusinessnotice'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '办事流程'
			}, 
			url : '${ctx}/dicBusinessnoticeController/editBusinessnotice'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/dicBusinessnoticeController/delete'
		}]
	}; 
	$(function() {
		$("#noticegrid")
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
										display : '名称',
										name : 'name',
										width : 270
									},
									{
										display : '创建时间',
										name : 'createtime',
										width : 180
									},
									{display : '操作',width : 80,
										render : function(rowdata) {
											var html = '';
											html += '<a class="grid_row_tool" href="${ctx}/dicBusinessnoticeController/businessnoticeDetail?id='
													+ rowdata.id
													+ '" rwpOpts="{ ispage: true }">详情</a>';
											return html;
										}
									}],
							toolbar : rwpTemp.noticebaritems,
							usePager : true,
							checkbox : true,
							autoCheckChildren : false,
							/* tree : {
								columnName : 'deptName'
							}, */
							url : '${ctx}/dicBusinessnoticeController/noticeListJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('noticegrid');
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
			<dt class="labelWidth" style="width:65px;">名称：</dt>
			<dd class="inputWidth">
				<input name="deptNameSearch" type="text" id="deptNameSearch"
					query-options="{ 'fieldName': 'noticename', 'whereField':'like', 'fieldType':'String' }" />
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
	<div id="noticegrid"></div>
</div>