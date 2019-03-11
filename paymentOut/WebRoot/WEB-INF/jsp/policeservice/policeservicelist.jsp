<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>

<script type="text/javascript">
    rwpTemp.policeservicebaritems = {
        items: [{
            text: '添加',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'add',
            dia: {
                title: '业务'
            },
            url: '${ctx}/policeServiceController/addPoliceService'
        }, {
            text: '修改',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'edit',
            min: 1,
            max: 1,
            dia: {
                title: '业务'
            },
            url: '${ctx}/policeServiceController/editPoliceService'
        }, {
            text: '删除',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'delete',
            min: 1,
            max: 1,
            confirm: true,
            postajax: true,
            url: '${ctx}/policeServiceController/deletePoliceService'
        },{
            text: '参数配置',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: '',
            min: 1,
            max: 1,
            dia: {
                title: ''
            },
            url: '${ctx}/businessconfigController/addConfig'
        },]
    };
    rwpTemp.policeservicelistiniturl = '${ctx}/policeServiceController/policeServiceJson';//初始化Json
    rwpTemp.treeUrl = '${ctx}/policeServiceController/policeServiceTreeJson';//初始化Json
    rwpTemp.getDeptGridDelay = function (rowData, level) {
        if (rowData.isdelay) {
            var params = [];
            params.push({ name: 'parentCode', value: rowData.serCode });
            return { url: rwpTemp.treeUrl, params: params };
        }
        rwpTemp.bindGridRowLink = function (newAppendRowData) {    //绑定表格行链接事件
            var gridManager = $('#policeservicegrid').data('rwpUIDatagrid'),
                $bodyTable = gridManager.innerDom.gridBodyTable,
                startIndex, endIndex, $newAppendTrs, $newAppendLinks;
            if ($.isArray(newAppendRowData) && newAppendRowData.length > 0) {
                startIndex = newAppendRowData[0]._rowIndex;
                endIndex = newAppendRowData[newAppendRowData.length - 1]._rowIndex;
                $newAppendTrs = $('tr', $bodyTable).slice(startIndex, endIndex + 1);
                $newAppendLinks = $('a', $newAppendTrs);
                rwpListLayout.gridRowclick('policeservicegrid', $newAppendLinks);
            }
        };
        return false;
    };
    $(function () {
        $("#policeservicegrid").rwpUIDatagrid({
            columns: [
                {display: '业务编号', name: 'serCode', primarykey: true, isHide: true},
                {display: '业务名称', name: 'serName', width: 350},
                //{display: '单价', name: 'price', width: 120},
                {display: '分类标识', name: 'orderCode', width: 200},
                {
                	display : '操作',
                	width : 160,
                	render : function(data){
                		var html = '';
                        html += '<a class="grid_row_tool" href="${ctx}/businessConfigTypeController/typeList?serCode='
                            + data.serCode
                            + '" rwpOpts="{ ispage: true}">管理申请类型</a>';
                        return html;
                	}
                }
            ],
            pageSize: 1,
            toolbar: rwpTemp.policeservicebaritems,
            url: rwpTemp.policeservicelistiniturl,
            height: '99%',
            tree: {columnName: 'serName',delay: rwpTemp.getDeptGridDelay },
            checkbox: true,
            autoCheckChildren : false,
            dateFormat: 'yyyy-MM-dd hh:mm:ss',
            onAfterShowData: function () {
                rwpListLayout.gridRowclick('policeservicegrid');
            }
        });
    });
    $(function () {
        $("#searform").rwpUIForm({
            inputWidth: 150,
            isSearch: true
        });
        //应用查询form表单
        $("#seachbtn").click(function () {
            var sername = $("#serNameSearch").val();
            var pcname = $("#pcNameSearch").val();
            var jurl = '${ctx}/policeServiceController/getPoliceService?1=1';
            if (sername != null && sername != '' || pcname != null && pcname != '') {
                jurl += '&serName=' + sername + '&pcName=' + pcname;
            }
            $("#policeservicegrid").data('rwpUIDatagrid').refreshData({
                url: encodeURI(jurl),
                newPage: 1
            });
        });
        $("#seachreset").click(function () {
            $('#searform')[0].reset();
        });
    });
</script>
<div class="searchBar">
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
</div>
<div class="tablepanel">
    <div id="policeservicegrid"></div>
</div>
