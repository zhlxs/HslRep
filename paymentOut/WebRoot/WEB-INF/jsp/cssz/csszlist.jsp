<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.rolebaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			dia : {
				title : '参数'
			},
			url : '${ctx}/csController/addCS'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '参数'
			},
			url : '${ctx}/csController/editCS'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/csController/deleteCS'
		}, {
			text : '默认设置',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			confirm : true,
			postajax : true,
			url : '${ctx}/csController/savelistCS'
		} ]
	};
	rwpTemp.roleopts = {
		columns : [ {
			display : '主键',
			name : 'csm',
			isHide : true,
			primarykey : true
		}, {
			display : '单位代码',
			name : 'dwdm',
			isHide : true
		}, {
			display : '参数名称',
			name : 'ms',
			width : 300
		}, {
			display : '参数值',
			name : 'csz',
			width : 300
		} ],
		<c:set var="salary" scope="session" value="${user.isSys}"/>
        <c:if test="${salary ==true}">
        toolbar : rwpTemp.rolebaritems,
		checkbox : true,
		</c:if>
		<c:if test="${salary !=true}">
		checkbox : false,
		</c:if>
		url : '${ctx}/csController/CSJson',
		height : '99%'
	};
	rwpTemp.sqjdwAjaxSelectColumns = [ {
		domID : "dwdmSearch",
		isCombobox : true,
		defaultOption : {
			Text : "---请选择单位---",
			Value : ""
		},
		selectVal : "core",
		url : "${ctx}/dicController/listUnitFromSQJ"
	} ];
	$(function() {
		rwpTemp.roleopts.onAfterShowData = function() {
			rwpListLayout.gridRowclick('rolegrid');
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
		$("#rolegrid").rwpUIDatagrid(rwpTemp.roleopts);
		$.rwpUI.AjaxSelect({
			columns : rwpTemp.sqjdwAjaxSelectColumns,
			defaultval : '',
			selectBoxHeight : 200
		});
	});
</script>
<div class="searchBar">
	<form id="searform">
		<dl style="width:256px;">
			<dt class="labelWidth" style="width:75px;">单位：</dt>
			<dd class="inputWidth">
				<select id="dwdmSearch" name="dwdmSearch" style="display:none;"
					query-options="{ 'fieldName':'dwdm', 'whereField':'like', 'fieldType':'String' }"></select>
			</dd>
		</dl>
		<dl style="width:256px;">
			<dt class="labelWidth" style="width:75px;">参数名称：</dt>
			<dd class="inputWidth">
				<input name="dwdmSearch" type="text" id="dwdmSearch2"
					query-options="{ 'fieldName': 'ms', 'whereField':'like', 'fieldType':'String' }" />
			</dd>
		</dl>
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
	<div id="rolegrid"></div>
</div>