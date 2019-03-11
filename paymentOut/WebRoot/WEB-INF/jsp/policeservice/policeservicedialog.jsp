<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<script type="text/javascript">
    // rwpTemp.parentid = parseInt("${parentid}");
    //  rwpTemp.isall = parseInt("${isall}");
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
    $(function () {
        $("#deptdialoggrid").rwpUIDatagrid(
            {
                columns:
                    [
                        {display: '业务编号', name: 'serCode', primarykey: true, isHide: true},
                        {display: '业务名称', name: 'serName', width: 400}
                    ],
                pageSize: 1,
                url: rwpTemp.policeservicelistiniturl,
                tree : {columnName : 'serName', delay:rwpTemp.getDeptGridDelay},
                isSelected :rwpPluginHelper.gridIsSelectedFun('serCode',
                    _rwpSplit + rwpTemp.parentid + _rwpSplit),
                height:'79%',
                checkbox:false
    })
        ;
    });
</script>

<div id="deptdialoggrid"></div>