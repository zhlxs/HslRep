<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.sjtjopts = {
		columns : [ 
		{
			display : '主键',
			name : 'id',
			primarykey : true,
			isHide : true
		}, 
		{
			display : '类型',
			name : 'lx',
			width : 200
		}, {
			display : '申请机',
			name : 'sqjmc',
			width : 200
		}, {
			display : '日志内容',
			name : 'context',
			width : 500
		} ,{
			display : '记录时间',
			name : 'jlsj',
			width : 200
		} ],
		checkbox:false,
		url : '${ctx}/wordController/LogJson',
		dateFormat : 'yyyy-MM-dd hh:mm:ss',
		height : '99%'
	};
	/*rwpTemp.sqxxAjaxSelectColumns = [ {
		domID : "sqjbh",
		isCombobox : true,
		defaultOption : {
			Text : "---请选择申请机---",
			Value : ""
		},
		selectVal : "${sqxx.sqjbh }",
		url : "${ctx}/dicController/listSQJ"
	} ];*/
	$(function() {
		rwpTemp.sjtjopts.onAfterShowData = function() {
			rwpListLayout.gridRowclick('sjtjgrid');
		};
		$("#searform").rwpUIForm({
			inputWidth : 150,
			isSearch : true
		}); //应用查询form表单            
		$("#seachbtn").click(function() {
			rwpListLayout.searchGrid('searform');
		});
		$("#seachreset").click(function() {
			$('#searform')[0].reset();
		});
		$("#loggrid").rwpUIDatagrid(rwpTemp.sjtjopts);
		$('input[name=recordTimeSearch]').rwpUIDateinput();
		$('input[name=recordTimeSearch2]').rwpUIDateinput();
		
		/*$.rwpUI.AjaxSelect({
			columns : rwpTemp.sqxxAjaxSelectColumns,
			defaultval : '',
			selectBoxHeight : 200
		});*/
	});
</script>
<div class="searchBar">
	<form id="searform">
		<div>
			<!-- <dl style="width:256px;">
				<dt class="labelWidth" style="width:75px;">申请机：</dt>
				<dd class="inputWidth">
					<select id="sqjbh" name="sqjbh" style="display:none;"
						query-options="{ 'fieldName':'sqjbh', 'whereField':'like', 'fieldType':'String' }"></select>
				</dd>
			</dl> -->
			<dl style="width:256px;">
				<dt class="labelWidth" style="width:75px;">日志类型：</dt>
				<dd class="inputWidth">
					<select id="logType" name="logType" style="display:none;"
						query-options="{ 'fieldName':'log.lx', 'whereField':'like', 'fieldType':'String' }">
						<option value="">--请选择日志类型--</option>
						<option value="1">运行日志</option>
						<option value="2">异常日志</option>
						</select>
				</dd>
			</dl>
			<dl style="width: 511px;">
				<dt class="labelWidth" style="width: 75px;">记录时间：</dt>
				<dd class="inputWidth">
					<input name="recordTimeSearch" type="text"
						id="recordTimeBeginTimeSearch"
						query-options="{ 'fieldName': 'jlsj', 'whereField':'>=', 'fieldType':'DateTime' }" />
				</dd>
				<dt class="labelWidth">至</dt>
				<dd class="inputWidth">
					<input name="recordTimeSearch2" type="text"
						id="recordTimeEndTimeSearch2"
						query-options="{ 'fieldName': 'jlsj', 'whereField':'<=', 'fieldType':'DateTime' }" />
				</dd>
			</dl>
		</div>
		<dl style="width:144px;">
			<dd class="inputWidth">
				<a href="javascript:void(0);" id="seachbtn" class="rwpButton">查找</a>
			</dd>
			<dd class="inputWidth">
				<a href="javascript:void(0);" id="seachreset" class="rwpButton">重置</a>
			</dd>
		</dl>
	</form>
</div>
<div class="tablepanel">
	<div id="loggrid"></div>
</div>