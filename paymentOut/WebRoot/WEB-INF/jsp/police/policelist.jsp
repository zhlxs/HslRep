<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>

<script type="text/javascript">
	rwpTemp.policebaritems = {
		items : [ {
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			dia : {
				title : '警种'
			},
			link:true,
			url : '${ctx}/policeController/addPolice'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '警种'
			},
			link:true,
			url : '${ctx}/policeController/editPolice'
		}, {
			text : '删除',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'delete',
			min : 1,
			max : 1,
			confirm : true,
			postajax : true,
			url : '${ctx}/policeController/deletePolice'
		} ]
	};
	rwpTemp.policelistiniturl = '${ctx}/policeController/PoliceJson'; //初始化Json
	$(function(){
		$("#policegrid")
		.rwpUIDatagrid({
			columns : [ 
						{
							display : '警种编号',
							name : 'pcCode',
							width : 150,
							primarykey:true
						},
						{
							display : '警种姓名',
							name : 'pcName',
							width : 150
						}],
						pageSize : 20,
						toolbar : rwpTemp.policebaritems,
						url : rwpTemp.policelistiniturl,
						height : '99%',
						checkbox : true,
						dateFormat : 'yyyy-MM-dd hh:mm:ss',
						onAfterShowData : function() {
							rwpListLayout.gridRowclick('policegrid');
						}
		});
	});

	/* rwpTemp.userlistopts = {
		columns : [ 
				{
					display : '警种编号',
					name : 'pcCode',
					width : 150,
					primarykey:true
				},
				{
					display : '警种姓名',
					name : 'pcName',
					width : 150
				}],
		pageSize : 20,
		toolbar : rwpTemp.policebaritems,
		url : rwpTemp.userlistiniturl,
		height : '99%',
		checkbox : true,
		dateFormat : 'yyyy-MM-dd hh:mm:ss',
		onAfterShowData : function() {
			rwpListLayout.gridRowclick('policegrid');
		}
	}; */

	 //rwpTemp.userByDeptlistiniturl = '${ctx}/userController/userBydeptId';  //初始部门ID查找用户url

	/* rwpTemp.searchDeptUser = function(node) { //按部门查用户        
		var queryUrl = rwpTemp.userByDeptlistiniturl, gridManager = $(
				"#policegrid") // 把初始URL获得的数据放到 DIV $("#usergrid")
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
	}; */
	/* rwpTemp.getDeptTreeDelay = function(deptNodeData, level) {
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
				url : '${ctx}/deptController/deptTreeJson',
				params : params
			};
		}
		return false;
	}; */
	/* rwpTemp.staticSearch = function() {
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
	}, */
	/* $(function() {
		$('#userSearchLayout').rwpUILayout();
		$('#userListLayout').rwpUILayout();
		$("#depttree").rwpUITree({
			checkbox : false,
			dataAttrs : {
				id : 'ID',
				text : 'deptName'
			},
			url : '${ctx}/deptController/deptTreeJson?parentid=0&isall=1',
			onSelect : rwpTemp.searchDeptUser,
			delay : rwpTemp.getDeptTreeDelay
		});
	}); */
	$(function() {
		//$("#policegrid").rwpUIDatagrid(rwpTemp.userlistopts);
		$("#searform").rwpUIForm({
			inputWidth : 150,
			isSearch : true
		}); 
		//应用查询form表单        
		$("#seachbtn").click(function() {
			var name=$("#pcNameSearch").val();
			var jurl ='${ctx}/policeController/getPolice?1=1';
			if(name!=null && name!=''){
				jurl+='&pcName='+name;
			}
			 $("#policegrid").data('rwpUIDatagrid').refreshData({
				url : encodeURI(jurl),
				newPage : 1
			});
		});
		$("#seachreset").click(function() {
			$('#searform')[0].reset();
		});
	});
</script>

<!-- <div id="userSearchLayout"> -->
	<div class="searchBar">
		<form id="searform">
			<dl>
				<dt class="labelWidth" style="width: 75px;">警种名称：</dt>
				<dd class="inputWidth">
					<input name="pcNameSearch" type="text" id="pcNameSearch"
						query-options="{ 'fieldName':'pcName', 'whereField':'like', 'fieldType':'String' }" />
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
	<div id="policegrid"></div>
	</div>
