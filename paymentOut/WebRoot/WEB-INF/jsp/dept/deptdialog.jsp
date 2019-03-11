<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
   // rwpTemp.parentid = parseInt("${parentid}");
   // rwpTemp.isall = parseInt("${isall}");
   rwpTemp.treeUrl = '${ctx}/deptController/deptTreeJson';//初始化Json
    rwpTemp.getDeptGridDelay = function (rowData, level) {
        if (rowData.isdelay) {
            var params = [];
            params.push({ name: 'parentid', value: rowData.ID });
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
                rwpListLayout.gridRowclick('deptgrid', $newAppendLinks);
            }
        };
        return false;
    };
    $(function() {
        $("#deptdialoggrid").rwpUIDatagrid(
            {
                columns : [ {
                    display : '主键',
                    name : 'ID',
                    isHide : true,
                    primarykey : true
                }, {
                    display : '部门名称',
                    name : 'deptName',
                    width : 200
                } ],
                pageSize : 5,
                url : '${ctx}/deptController/deptJson',
                tree : {
                    columnName : 'deptName',
                    delay: rwpTemp.getDeptGridDelay
                },
                isSelected : rwpPluginHelper.gridIsSelectedFun('ID',
                    _rwpSplit + rwpTemp.parentid + _rwpSplit),
                height : '79%',
                checkbox : false
            });
    });
</script>
<div id="deptdialoggrid"></div>