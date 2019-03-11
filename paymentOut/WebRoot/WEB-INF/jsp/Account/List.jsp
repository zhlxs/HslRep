<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.accountbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			dia : {
				title : '账号'
			},
			url : '${ctx}/accountManagerController/addAccount'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '叫号机'
			},
			url : '${ctx}/accountManagerController/editAccount'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/accountManagerController/delete'
		} ]
	};
	$(function() {
		$("#accountgrid").rwpUIDatagrid({
			columns : [ {
				display : '主键',
				name : 'id',
				isHide : true,
				primarykey : true
			}, {
				display : '用户姓名',
				name : 'name',
				width : 120
			}, {
				display : '账号',
				name : 'account',
				width : 220
			}, {
				display : '警员编号',
				name : 'jynumber',
				width : 120
			}, {
				display : '所属部门',
				name : 'deptid',
				width : 160,
				render : function(data) {
					if (data.dept != null) {
						return data.dept.deptName;
					}
				}
			}, {
				display : '创建时间',
				name : 'createtime',
				width : 180
			} ],
			toolbar : rwpTemp.accountbaritems,
			usePager : true,
			checkbox : true,
			autoCheckChildren : false,
			url : '${ctx}/accountManagerController/accountListJson',
			height : '99%',
			onAfterShowData : function() {
				rwpListLayout.gridRowclick('accountgrid');
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
			<dt class="labelWidth" style="width:60px;">姓名：</dt>
			<dd class="inputWidth">
				<input name="name" type="text" id="name"
					query-options="{ 'fieldName': 'm.name', 'whereField':'like', 'fieldType':'String' }" />
			</dd>
		</dl>
		<dl style="width:270px;">
			<dt class="labelWidth" style="width:60px;">账号：</dt>
			<dd class="inputWidth">
				<input name="account" type="text" id="account"
					query-options="{ 'fieldName': 'm.account', 'whereField':'like', 'fieldType':'String' }" />
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
	<div id="accountgrid"></div>
</div>