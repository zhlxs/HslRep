
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.getDeptGridDelay = function (rowData, level) {

        if (rowData.isdelay) {

            var params = [];
            params.push({name: 'parentCode', value: rowData.serCode});
            return {url: rwpTemp.treeUrl, params: params};
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
    rwpTemp.policeservicelistiniturl = '${ctx}/policeServiceController/policeServiceJson';//初始化Json
    rwpTemp.treeUrl = '${ctx}/policeServiceController/policeServiceTreeJson';//初始化Json
    rwpTemp.userServiceopts = {
        columns : [

            {
                display : '主键',
                name : 'serCode',
                isHide : true,
                primarykey : true
            },
            {
                display : '业务名称',

                name : 'serName',
                width : 200
            }
        ],
        //toolbar : rwpTemp.applbaritems,
        //usePager : false,
        checkbox : true,
        autoCheckChildren : true,
        dateFormat : 'yyyy-MM-dd hh:mm:ss',
        onAfterShowData : function() {
            rwpListLayout.gridRowclick('userServicesgrid');
        },
        pageSize: 1,
        url: rwpTemp.policeservicelistiniturl,
        tree : {columnName : 'serName', delay:rwpTemp.getDeptGridDelay},
        isSelected :rwpPluginHelper.gridIsSelectedFun('serCode',
        _rwpSplit + rwpTemp.parentid + _rwpSplit),
        height:'99%',
    };
 
    $(function() {
        $("#userServicesgrid").rwpUIDatagrid(rwpTemp.userServiceopts);
        $("#ajaxform").rwpUIForm({
            inputWidth: 'auto',
            isAjaxSubmit : true,
            ajaxSubmitAttrs : {
                isDialog : true,
                submitOk : rwpPluginHelper.dialogImportCancel
            }
        });

   });

</script>
<style>
    .rwpToggle{
        display: none;
    }
</style>
<body>
    <form  id="ajaxform" action="${ctx}/deptServiceController/saveDeptService" method="post">
        <input type="hidden" value="${uiCode}" name="uiCode"/>
        <div id="userServicesgrid" style="margin-right: 0px; margin-left: 5px;"></div>
    </form>
</body>
