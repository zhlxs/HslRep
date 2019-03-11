<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">    
    rwpTemp.parentid = 0;
    rwpTemp.deptid = 0;
    rwpTemp.isall = 1;    
    rwpTemp.check = 0;
    rwpTemp.deptfor = "";    

    rwpTemp.getDeptDialogGridDelay = function (deptNodeData, level) {
        if (deptNodeData.isdelay) {
            var params = [];
            params.push({ name: 'isall', value: 1 });
            params.push({ name: 'islazy', value: 1 });
            params.push({ name: 'isgrid', value: 1 });
            params.push({ name: 'parentid', value: deptNodeData.ID });
            return { url: '${ctx}/userController/deptTreeJson', params: params };
        }
         return false;
    };

    rwpTemp.deptdialogopts = {
        columns: [
        { display: '主键', name: 'ID', isHide: true, primarykey: true },
        { display: '部门名称', name: 'deptName', width: 200 }
        ],
        pageSize: 1,
        url: '${ctx}/userController/deptDialogJson?isall=' + rwpTemp.isall + '&parentid=' + rwpTemp.parentid,
        tree: { columnName: 'deptName', delay: rwpTemp.getDeptDialogGridDelay },
        isSelected: rwpPluginHelper.gridIsSelectedFun('ID', _rwpSplit + (rwpTemp.check == 1 ? rwpTemp.deptfor : rwpTemp.deptid) + _rwpSplit),
        height: '79%',
        checkbox: true
    };

    $(function () {
        if (rwpTemp.check == 1) {
            rwpTemp.deptdialogopts.checkbox = true;   //添加复选框
            rwpTemp.deptdialogopts.usePager = false;  //不分页
            rwpTemp.deptdialogopts.url += '&check=1';
        }
        $("#deptdialoggrid").rwpUIDatagrid(rwpTemp.deptdialogopts);                
    });
</script>

<div id="deptdialoggrid"></div>
