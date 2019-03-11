<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.degreeeduid = parseInt("${id}");
	$(function() {
		$("#sampleConfiggrid")
				.rwpUIDatagrid(
						{
							columns : [ {
								display : '主键',
								name : 'id',
								isHide : true,
								primarykey : true
							}, {
								display : '样图名称',
								name : 'photoname',
								width : 350
							} ],
							pageSize : 5,
							url : '${ctx}/macPhotoController/sampleDialogJson?type=${type}',
							tree : {
								columnName : 'bconfigstrcname'
							},
							isSelected : rwpPluginHelper.gridIsSelectedFun(
									'id', _rwpSplit + rwpTemp.businessConfigid
											+ _rwpSplit),//没有parentid
							height : '79%',
							checkbox : false
						});
	});
	$(function() {
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
		<dl style="width:260px;">
			<dt class="labelWidth" style="width:70px;">中文名称：</dt>
			<dd class="inputWidth">
				<input name="deptNameSearch" type="text" id="deptNameSearch"
					query-options="{ 'fieldName': 'photoname', 'whereField':'like', 'fieldType':'String' }" />
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
<div id="sampleConfiggrid"></div>
