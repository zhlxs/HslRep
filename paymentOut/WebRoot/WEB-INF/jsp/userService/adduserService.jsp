
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
    rwpTemp.policeservicelistiniturl = '${ctx}/userServiceController/addUserServiceJson?uiCode=${uiCode}';//初始化Json
    rwpTemp.treeUrl = '${ctx}/userServiceController/addUserServiceTreeJson';//初始化Json
    rwpTemp.getDeptGridDelay = function (rowData, level) {

        if (rowData.isdelay) {

            var params = [];
            params.push({ name: 'parentCode', value: rowData.serCode });
            params.push({ name: 'uiCode', value: $("#uiCode").val() });

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
                width : 400
            }
        ],
        //toolbar : rwpTemp.applbaritems,
        pageSize: 1,
        checkbox : true,
        autoCheckChildren : true,
        url :  rwpTemp.policeservicelistiniturl,
        tree: {columnName: 'serName',delay: rwpTemp.getDeptGridDelay },
        dateFormat : 'yyyy-MM-dd hh:mm:ss',
        height : '99%',
        onAfterShowData : function() {
            rwpListLayout.gridRowclick('userServicesgrid');
        }
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
    <form  id="ajaxform" action="${ctx}/userServiceController/saveUserService" method="post">
        <input type="hidden" value="${uiCode}" name="uiCode"/>
        <div id="userServicesgrid" style="margin-right: 0px; margin-left: 5px;"></div>
    </form>
</body>
