<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.configparamsbaritems = {
		items : [{
			text : '添加',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'add',
			dia : {
				title : '参数节点'
			},
			url : '${ctx}/macConfigController/addParam?modelId=${modelId}'
		}, {
			text : '修改',
			click : rwpListLayout.gridToolButtonClick.itemclick,
			icon : 'edit',
			min : 1,
			max : 1,
			dia : {
				title : '参数节点'
			},
			url : '${ctx}/macConfigController/alterModelParams?modelId=${modelId}'
		}]
	};
	rwpTemp.paramslistiniturl = '${ctx}/macConfigController/configParamsJson?modelId=${modelId}';//初始化Json
	rwpTemp.getTypeGridDelay = function (rowData, level) {
        if(rowData.parentId == 0){
            rowData.isexpand = false;
        }
        rwpTemp.bindGridRowLink = function (newAppendRowData) {//绑定表格行链接事件
            var gridManager = $('#paramsgrid').data('rwpUIDatagrid'),
                $bodyTable = gridManager.innerDom.gridBodyTable,
                startIndex, endIndex, $newAppendTrs, $newAppendLinks;
            if ($.isArray(newAppendRowData) && newAppendRowData.length > 0) {
                startIndex = newAppendRowData[0]._rowIndex;
                endIndex = newAppendRowData[newAppendRowData.length - 1]._rowIndex;
                $newAppendTrs = $('tr', $bodyTable).slice(startIndex, endIndex + 1);
                $newAppendLinks = $('a', $newAppendTrs);
                rwpListLayout.gridRowclick('paramsgrid', $newAppendLinks);
            }
        };
        return false;
    };
	$(function() {
		$("#paramsgrid")
				.rwpUIDatagrid(
						{
							columns : [
									{
										display : '主键',
										name : 'id',
										primarykey : true,
										isHide : true
									},
									{
										display : '标题',
										name : 'title',
										width : 140
									},
									{
										display : '图标',
										name : '图标',
										width : 100
									},
									{
										display : 'TOP坐标',
										name : 'topCoord',
										width : 100
									},
									{
										display : 'LEFT坐标',
										name : 'leftCoord',
										width : 100
									},
									{
										display : '排序码',
										name : 'orderCode',
										width : 100
									},
									{
										display : '更新时间',
										name : 'updatetime',
										width : 160
									},
									{
										display : '操作',
										width : 80,
										render : function(data) {
											var html = '';
											html += '<a class="grid_row_tool" href="${ctx}/businessConfigTypeController/typeList?serCode='
													+ data.serCode
													+ '" rwpOpts="{ ispage: true}">删除</a>';
											return html;
										}
									}],
							pageSize : 20,
							toolbar : rwpTemp.configparamsbaritems,
							url : rwpTemp.paramslistiniturl,
							height : '99%',
							tree : {
								columnName : 'title',
								delay : rwpTemp.getTypeGridDelay
							},
							checkbox : true,
							autoCheckChildren : false,
							dateFormat : 'yyyy-MM-dd hh:mm:ss',
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('paramsgrid');
							}
						});
	});
	$(function() {
		$("#searform").rwpUIForm({
			inputWidth : 150,
			isSearch : true
		});
		//应用查询form表单
		$("#seachbtn")
				.click(
						function() {
							var sername = $("#serNameSearch").val();
							var pcname = $("#pcNameSearch").val();
							var jurl = '${ctx}/macConfigController/getPoliceService?1=1';
							if (sername != null && sername != ''
									|| pcname != null && pcname != '') {
								jurl += '&serName=' + sername + '&pcName='
										+ pcname;
							}
							$("#paramsgrid").data('rwpUIDatagrid').refreshData(
									{
										url : encodeURI(jurl),
										newPage : 1
									});
						});
		$("#seachreset").click(function() {
			$('#searform')[0].reset();
		});
	});
</script>
<!-- <div class="searchBar">
    <form id="searform">
        <dl>
            <dt class="labelWidth" style="width: 75px;">业务名称：</dt>
            <dd class="inputWidth">
                <input name="serNameSearch" type="text" id="serNameSearch"
                       query-options="{ 'fieldName':'serName', 'whereField':'like', 'fieldType':'String' }"/>
            </dd>
        </dl>
        <dl style="width: 274px;">
            <dt class="labelWidth" style="width: 75px;">警种名称：</dt>
            <dd class="inputWidth">
                <input name="pcNameSearch" type="text" id="pcNameSearch"
                       query-options="{ 'fieldName':'pcName', 'whereField':'like', 'fieldType':'String' }"/>
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
</div> -->
<div class="tablepanel">
	<div id="paramsgrid"></div>
</div>