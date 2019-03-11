<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.actions = _rwpSplit + "${actions}" + _rwpSplit;
	rwpTemp.actiondata = JSON.parse('${actionJson }')
	rwpTemp.allactionopts = {
		columns : [
				{
					display : '说明',
					name : 'display',
					width : 300
				},
				{
					display : '名称',
					name : 'controllerAction',
					width : 300
				},
				{
					display : '类型',
					name : 'typeOf',
					width : 60,
					render : function(row) {
						var html = row.typeOf == 2 ? '动作'
								: '<span class="fClr4">控制器</span>';
						return html;
					}
				} ],
		usePager : false,
		data : rwpTemp.actiondata,
		tree : {
			columnName : 'display'
		},
		autoCheckChildren : true,
		includeParent : false,
		checkbox : true,
		isSelected : rwpPluginHelper.gridIsSelectedFun('controllerAction',
				rwpTemp.actions),
		height : '99%'
	};
	rwpTemp.forauthactionopts = {
		columns : [ {
			display : '说明',
			name : 'display',
			width : 150
		}, {
			display : '控制器',
			name : 'controName',
			width : 200
		}, {
			display : '动作',
			name : 'actionName',
			width : 200
		} ],
		usePager : false,
		checkbox : false,
		data : rwpTemp.actiondata,
		height : '79%'
	};
	$(function() {
		rwpTemp.actiongridopts = ("${actions}" == "forauth" ? rwpTemp.forauthactionopts
				: rwpTemp.allactionopts);
		$("#actiongrid").rwpUIDatagrid(rwpTemp.actiongridopts);
	});
</script>

<div id="actiongrid"></div>