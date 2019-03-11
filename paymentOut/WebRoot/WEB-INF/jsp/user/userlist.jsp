<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>

<script type="text/javascript">
	rwpTemp.userbaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',

			link:true,
			url : '${ctx}/userController/addUser'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,

			link:true,
			url : '${ctx}/userController/editUser'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/userController/deleteUser'
		}
        ]
	};

	rwpTemp.userlistiniturl = '${ctx}/userController/userJson'; //初始url

	rwpTemp.userlistopts = {
		columns : [
				{
					display : '主键',
					name : 'ID',
					isHide : true,
					primarykey : true
				},{
					display : '状态',
					width : 70,
					render : function(row) {
						var html = row.isStart == 1 ? '<span class="fClr3">启用</span>'
								: '<span class="fClr4">停用</span>';
						return html;
					}
				},
				{
					display : '操作',
					width : 80,
					render : function(rowdata) {
						var html = '';
						if (rowdata.isStart)
							html += '<a href="${ctx}/userController/setUserStart?isstart=0&id='
									+ rowdata.ID
									+ '" rwpOpts="{ isajax: true }"><span class="fClr4">停用</span></a>';
						else
							html += '<a href="${ctx}/userController/setUserStart?isstart=1&id='
									+ rowdata.ID
									+ '" rwpOpts="{ isajax: true }">启用</a>';
						return html;
					}
				}, 
				{
					display : '用户名',
					name : 'userName',
					width : 150
				},
				{
					display : '民警姓名',
					name : 'fullName',
					width : 130
				},
				{
					display : '所属部门',
					name : 'deptName',
					width : 120
				},
				{
					display : '创建时间',
					name : 'createTime',
					width : 190
				},
				{
					display : '最后登录时间',
					name : 'lastLoginTime',
					width : 190
				},
				{
					display : '最后登录IP',
					name : 'lastLoginIP',
					width : 95
				},
				{
					display : '创建用户',
					name : 'createBy',
					width : 95
				}],
		pageSize : 20,
		toolbar : rwpTemp.userbaritems,
		url : rwpTemp.userlistiniturl,
		height : '99%',
		checkbox : true,
		dateFormat : 'yyyy-MM-dd hh:mm:ss',
		onAfterShowData : function() {
			rwpListLayout.gridRowclick('usergrid');
		}
	};

	rwpTemp.userByDeptlistiniturl = '${ctx}/userController/userBydeptId'; //初始部门ID查找用户url

	rwpTemp.searchDeptUser = function(node) { //按部门查用户        
		var queryUrl = rwpTemp.userByDeptlistiniturl, gridManager = $(
				"#usergrid") // 把初始URL获得的数据放到 DIV $("#usergrid")
		.data('rwpUIDatagrid');
		if (node.data.ID > 0) { //ID必须大于0才筛选
			$("#deptid").val(node.data.ID);
			//queryUrl += '?id=' + node.data.ID; //URL拼接条件传到后台
			//gridManager.refreshData({
			//	url : queryUrl,
			//	newPage : 1
			//});
			rwpListLayout.searchGrid('searform');
		}
	};
	rwpTemp.getDeptTreeDelay = function(deptNodeData, level) {
		if (deptNodeData.isdelay) {
			var params = [];
			params.push({
				name : 'isall',
				value : 1
			});
			params.push({
				name : 'islazy',
				value : 1
			});
			params.push({
				name : 'parentid',
				value : deptNodeData.ID
			});
			return {
				url : '${ctx}/deptController/listTreeJson',
				params : params
			};
		}
		return false;
	};
	rwpTemp.staticSearch = function() {
		var staticparams = rwpTemp.getStaticParams(), gridManager, gridSearchOpts;
		if (staticparams && staticparams.length > 0) {
			gridSearchOpts = {
				params : staticparams
			};
			gridManager = rwpListLayout.getCurrentGrid();
			if (gridManager) {
				gridManager.refreshData(gridSearchOpts);
			}
		}
	}

	$(function() {
		$('#userSearchLayout').rwpUILayout();
		$('#userListLayout').rwpUILayout();
		$("#depttree").rwpUITree({
			checkbox : false,
			dataAttrs : {
				id : 'ID',
				text : 'deptName'
			},
			url : '${ctx}/deptController/listTreeJson?parentid=0',
			onSelect : rwpTemp.searchDeptUser,
			delay : rwpTemp.getDeptTreeDelay
		});
	});
	$(function() {
		$("#usergrid").rwpUIDatagrid(rwpTemp.userlistopts);

		$("#searform").rwpUIForm({
			isSearch : true
		}); //应用查询form表单        
		$("#seachbtn").click(function() {
			/*  rwpTemp.staticSearch(); */			
			//$(".rwpTreeSelected").removeClass("rwpTreeSelected");
			//$("#usergrid").data('rwpUIDatagrid').refreshData({
			//	url : rwpTemp.userlistiniturl,
			//	newPage : 1
			//});
			rwpListLayout.searchGrid('searform');					
		});
		$("#seachreset").click(function() {
			$('#searform')[0].reset();
		});
	});
</script>

<div id="userSearchLayout">
	<div class="searchBar" data-options="{ 'region':'top' }">
		<form id="searform">
			<dl style="display: none;">
				<dt class="labelWidth" style="width: 63px;">deptid：</dt>
				<dd class="inputWidth">
					<input name="deptid" type="text" id="deptid"
						query-options="{ 'fieldName':'deptid', 'whereField':'in', 'fieldType':'Long' }" />
				</dd>
			</dl>
			<dl style="width: 274px;">
				<dt class="labelWidth" style="width: 63px;">姓名：</dt>
				<dd class="inputWidth">
					<input name="fullNameSearch" type="text" id="fullNameSearch"
						query-options="{ 'fieldName':'fullName', 'whereField':'like', 'fieldType':'String' }" />
				</dd>
			</dl>
			<dl style="width: 274px;">
				<dt class="labelWidth" style="width: 63px;">用户名：</dt>
				<dd class="inputWidth">
					<input name="userNameSearch" type="text" id="userNameSearch"
						query-options="{ 'fieldName':'userName', 'whereField':'like', 'fieldType':'String' }" />
				</dd>
			</dl>
			<dl style="width: 274px;">
				<dt class="labelWidth" style="width: 63px;">电话：</dt>
				<dd class="inputWidth">
					<input name="phoneSearch" type="text" id="phoneSearch"
						query-options="{ 'fieldName':'phone', 'whereField':'like', 'fieldType':'String' }" />
				</dd>
			</dl>
			<dl style="width: 274px;">
				<dt class="labelWidth" style="width: 63px;">状态：</dt>
				<dd class="inputWidth">
					<select id="isStartSearch" name="isStartSearch"
						query-options="{ 'fieldName':'isStart', 'whereField':'like', 'fieldType':'String' }">
						<option value="">所有状态</option>
						<option value="1">启用</option>
						<option value="0">停用</option>
					</select>
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
	<div data-options="{ 'region':'center' }">
		<div id="userListLayout" style="margin: 10px 10px 0 10px;">
			<div data-options="{'region':'left','width':'225'}" title="组织机构">
				<div id="depttreewarp"
					style="width: 100%; height: 100%; overflow: auto;">
					<ul id="depttree"></ul>
				</div>
			</div>
			<div region="center">
				<div id="usergrid" style="margin-right: 0px; margin-left: 5px;"></div>
			</div>
		</div>
	</div>
</div>
