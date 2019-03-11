<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.machinebaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			min : 0,
			max : 0.5,
			dia : {
				title : '窗口'
			},
			url : '${ctx}/macWindowsController/addtion'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '窗口'
			},
			url : '${ctx}/macWindowsController/editMac'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/macWindowsController/delete'
		} ]
	};
	$(function() {
		$("#macgrid").rwpUIDatagrid({
			columns : [ {
				display : '主键',
				name : 'id',
				isHide : true,
				primarykey : true
			}, {
				display : '窗口编号',
				name : 'ckbh',
				width : 80,
				render : function(data){
					var html = '';
					html += '<span style="font-weight:bold;color:blue;">';
					html += data.ckbh;
					html += '</span>';
					return html;
				}
			}, {
				display : '所属部门',
				name : 'deptid',
				width : 160,
				render : function(data) {
					if (data.dept != null) {
						return data.dept.deptName;
					}
				}
			}, 
			{
				display : '设备机器码',
				name : 'devicenumber',
				width : 220
			},
			{
				display : '窗口计算机IP',
				name : 'deviceip',
				width : 120
			} ,{
				display : '操作',
				width : 100,
				render : function(rowdata) {
					var html = '';
					html += '<a class="btnPlum newbtn" style="color:#fff;padding: 2px 5px;" href="${ctx}/mpcConfigController/photoListOfMac?deptId='
							+ rowdata.deptid + '&winId=' + rowdata.id
							+ '" rwpOpts="{ ispage: true }">宣传图管理</a>';
					return html;
				}
			}],
			toolbar : rwpTemp.machinebaritems,
			usePager : true,
			checkbox : true,
			autoCheckChildren : false,
			url : '${ctx}/macWindowsController/macListJson',
			height : '99%',
			onAfterShowData : function() {
				rwpListLayout.gridRowclick('macgrid');
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
			var deviceNumber = $("#deviceNumber").val();
			var ckbh = $("#ckbh").val();
			var jurl = '${ctx}/macWindowsController/macListJson?1=1';
			if (deptids.length != 0) {
				jurl += '&deptids=' + deptids;
			}
			if (deviceNumber != null && deviceNumber != '') {
				jurl += '&deviceNumber=' + deviceNumber;
			}
			if (ckbh != null && ckbh != '') {
				jurl += '&ckbh=' + ckbh;
			}
			$("#macgrid").data('rwpUIDatagrid')
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
				<dt class="labelWidth" style="width:70px;">机器码：</dt>
				<dd class="inputWidth">
					<input name="deviceNumber" type="text" id="deviceNumber"
						query-options="{ 'fieldName': 'm.deviceNumber', 'whereField':'like', 'fieldType':'String' }" />
				</dd>
			</dl>
			<dl style="width:270px;">
				<dt class="labelWidth" style="width:70px;">窗口编号：</dt>
				<dd class="inputWidth">
					<input name="ckbh" type="text" id="ckbh"
						query-options="{ 'fieldName': 'm.ckbh', 'whereField':'like', 'fieldType':'String' }" />
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
				<div id="macgrid" style="width: 100%; height: 100%; overflow: auto;"></div>
			</div>
		</div>
	</div>
</div>