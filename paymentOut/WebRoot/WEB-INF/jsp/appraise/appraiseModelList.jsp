<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>

<script type="text/javascript">
    rwpTemp.apprModelbaritems = {
        items: [{
            text: '添加',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'add',
            dia: {
                title: '模板'
            },
            link : true,
            url: '${ctx}/appraiseContrller/addApprModel'
        }, {
            text: '修改',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'edit',
            min: 1,
            max: 1,
            link : true,
            dia: {
                title: '模板'
            },
            url: '${ctx}/appraiseContrller/editApprModel'
        },
            {
            text: '删除',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'delete',
            min: 1,
            max: 1,
            confirm: true,
            postajax: true,
            url: '${ctx}/appraiseContrller/deleteApprModel'
        }
        ]
    };

    rwpTemp.apprModellistiniturl = '${ctx}/appraiseContrller/apprModeleJson?1=1'; //初始url

    rwpTemp.apprModellistopts = {
        columns: [
            {
                display: '主键',
                name: 'id',
                isHide: true,
                primarykey: true
            },
            {
                display: '模板名称',
                name: 'modelname',
                width: 180
            },
            {
                display: '详细',
                render: function (rowdata) {
                    var html = '<a href="${ctx}/appraiseContrller/modelDetails?id=' + rowdata.id + '" rwpOpts="{ ispage: true }">查看详情'
                        + '</a>';
                    return html;
                },
                width: 180
            }
        ],
        pageSize: 20,
        toolbar: rwpTemp.apprModelbaritems,
        url: rwpTemp.apprModellistiniturl,
        height: '99%',
        checkbox: true,
        dateFormat: 'yyyy-MM-dd hh:mm:ss',
        onAfterShowData: function () {
            rwpListLayout.gridRowclick('apprModelgrid');
        }
    };
    //获取所有拼接了所以参数的URL
    function getJurl() {
        var jurl = rwpTemp.apprModellistiniturl;
        //  var deptids = getTreeParams().deptids;
        var modelName = $("#modelName").val();
        // if (deptids.length != 0) {
        //     jurl += '&deptids=' + deptids;
        // }
        if (modelName != null && modelName != '') {
            jurl += '&modelName=' + modelName;
        }
        return jurl;
    }

    $(function () {
        $('#apprModelSearchLayout').rwpUILayout();
        $('#apprModelListLayout').rwpUILayout();
        $("#apprModelgrid").rwpUIDatagrid(rwpTemp.apprModellistopts);
        //应用查询form表单
        $("#searform").rwpUIForm({
            isSearch: true
        });
        $("#seachbtn").click(function () {
            console.log(getJurl());
            var gridManager = rwpListLayout.getCurrentGrid();
            gridManager.refreshData({
                url: getJurl()
            });
        });
        $("#seachreset").click(function () {
            $('#searform')[0].reset();
        });
    });
</script>

<div id="apprModelSearchLayout">
    <div class="searchBar" data-options="{ 'region':'top' }">
        <form id="searform">
            <dl style="width: 274px;">
                <dt class="labelWidth" style="width: 63px;">模板名称：</dt>
                <dd class="inputWidth">
                    <input name="modelName" type="text" id="modelName"/>
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
        <div id="apprModelListLayout" style="margin: 10px 10px 0 10px;">
            <div region="center">
                <div id="apprModelgrid" style="margin-right: 0px; margin-left: 5px;"></div>
            </div>
        </div>
    </div>
</div>
