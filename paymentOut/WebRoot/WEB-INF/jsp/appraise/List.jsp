<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#apprgrid")
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
										name : 'userid',
										width : 140,
										render : function(data){
											if(data.user != null){
												return data.user.username;
											}else{
												return "未知";
											}
										}
									},
									{
										display : '队列编号',
										name : 'dlbh',
										width : 120
									},
									{
										display : '取号顺序号',
										name : 'qusxh_sxh',
										width : 140
									},
									{
										display : '评价结果',
										name : 'ywpjjg',
										width : 140,
										render : function(data){
											if(data.ywpjjg == 1){
												return "满意";
											}else if(data.ywpjjg == 2){
												return "一般";
											}else if(data.ywpjjg == 3){
												return "不满意";
											}else{
												return "异常";
											}
										}
									},
									{
										display : '评价日期',
										name : 'ywpjrqsj_rqsj',
										width : 160
									},
									{
										display : '所属部门',
										name : 'deptid',
										width : 140,
										render : function(data){
											if(data.dept != null){
												return data.dept.deptName;
											}else{
												return "未知";
											}
										}
									}],
							usePager : true,
							checkbox : false,
							autoCheckChildren : false,
							url : '${ctx}/businessApprController/businessApprListJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('apprgrid');
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
			<dt class="labelWidth" style="width:60px;">姓名：</dt>
			<dd class="inputWidth">
				<input name="username" type="text" id="username"
					query-options="{ 'fieldName': 'r.username', 'whereField':'like', 'fieldType':'String' }" />
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
	<div id="apprgrid"></div>
</div>