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
			url : '${ctx}/deptTimesConfigController/addAccount'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '叫号机'
			},
			url : '${ctx}/deptTimesConfigController/editAccount'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/deptTimesConfigController/delete'
		} ]
	};
	$(function() {
		$("#accountgrid")
				.rwpUIDatagrid(
						{
							columns : [
									{
										display : '主键',
										name : 'deptid',
										isHide : true,
										primarykey : true
									},
									{
										display : '部门名称',
										name : 'deptname',
										width : 180
									},
									{
										display : '时间间隔(天)',
										name : 'timeinter',
										width : 100
									},
									{
										display : '限制次数',
										name : 'times',
										width : 80
									},
									{
										display : '规则描述',
										width : 200,
										render : function(data) {
											var html = '';
											html += '<span>';
											html += '每人' + data.timeinter
													+ '天内可以取号' + data.times
													+ '次';
											html += '</span>';
											return html;
										}
									},
									{
										display : '是否默认配置',
										width : 100,
										render : function(data) {
											if (data.isdefault == 0) {
												return "是";
											} else {
												return "否";
											}
										}
									},
									{
										display : '操作',
										width : 80,
										render : function(data) {
											var html = '';
											html += '<a class="grid_row_tool" href="${ctx}/deptTimesConfigController/timesConfig?deptid='
													+ data.deptid
													+ '&deptname='
													+ data.deptname
													+ '" rwpOpts="{ isdialog: true }">点击配置</a>';
											return html;
										}
									} ],
							//toolbar : rwpTemp.accountbaritems,
							usePager : true,
							checkbox : false,
							autoCheckChildren : false,
							url : '${ctx}/deptTimesConfigController/timesConfigListJson',
							height : '99%',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('accountgrid');
							}
						});

	});
	var getTreeParams = function() {
		var treeManager = $('#depttree').data('rwpUITree'), deptitems = treeManager
				.getChecked()
				|| [], nodeExpandCallbacks = treeManager.nodeExpandCallbacks, //获取目前需要延迟加载的结点集合
		deptids = [], macIds = [], parentdeptids = [];
		$.each(deptitems, function(i, deptitem) {//遍历被选中的部门数组
			if (deptitem.data.ID != null && deptitem.data.ID != '') {
				if (deptitem.data.isdelay) {
					var flag = true;
					for (var i = 0; i < parentdeptids.length; i++) {
						if (parentdeptids[i] == deptitem.data.parentID) {
							flag = false;
						}
					}
					if (flag) {
						parentdeptids.push(deptitem.data.ID);
					}
				} else {
					deptids.push(deptitem.data.ID);
				}
			}
		});
		var parms = {
			"deptids" : deptids,
			"macIds" : macIds,
			"parentdeptids" : parentdeptids
		};
		return parms;
	};
	//组织机构js
	$(function() {
		$('#newsSearchLayout').rwpUILayout();
		$('#newsListLayout').rwpUILayout();
		//点击加载部门树事件
		rwpTemp.getDeptTreeDelay = function(deptNodeData, level) {
			if (deptNodeData.isdelay) {
				var params = [];
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
		//部门树点击事件//按部门查用户
		rwpTemp.searchWans = function(node) {
			$("#seachbtn").click();
		};
		$("#depttree").rwpUITree({
			checkbox : true,
			dataAttrs : {
				id : 'ID',
				text : 'deptName',
				ischecked : false
			},
			url : '${ctx}/deptController/listTreeJson?parentid=0',
			onCheck : rwpTemp.searchWans,
			delay : rwpTemp.getDeptTreeDelay
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
			//rwpListLayout.searchGrid('deptform');
			var deptids = getTreeParams().deptids;
			var deptname = $("#deptname").val();
			var jurl = '${ctx}/deptTimesConfigController/timesConfigListJson?1=1';
			if (deptids.length != 0) {
				jurl += '&deptids=' + deptids;
			}
			if (deptname != null && deptname != '') {
				jurl += '&deptname=' + deptname;
			}
			$("#accountgrid").data('rwpUIDatagrid')
			.refreshData({
				url : encodeURI(jurl),
				newPage : 1
			});
		});
		$("#seachreset").click(function() {
			$('#deptform')[0].reset();
		});
	});
</script>
<div id="newsSearchLayout">
	<div class="searchBar" data-options="{ 'region':'top' }">
		<form id="deptform">
			<dl style="width:270px;">
				<dt class="labelWidth" style="width:70px;">部门名称：</dt>
				<dd class="inputWidth">
					<input name="deptname" type="text" id="deptname"
						query-options="{ 'fieldName': 'cd.deptname', 'whereField':'like', 'fieldType':'String' }" />
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
		<div id="newsListLayout" style="margin: 0px 0px 0 0px;">
			<div data-options="{'region':'left','width':'235'}" title="组织机构">
				<div id="depttreewarp"
					style="width: 100%; height: 100%; overflow: auto;">
					<ul id="depttree"></ul>
				</div>
			</div>
			<div region="center">
				<div id="accountgrid"
					style="width: 100%; height: 100%; overflow: auto;"></div>
			</div>
		</div>
	</div>
</div>