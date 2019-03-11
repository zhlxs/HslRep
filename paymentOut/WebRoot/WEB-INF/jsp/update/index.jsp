<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>

<script type="text/javascript">
   /* rwpTemp.updatebaritems = {
        items: [{
            text: '添加',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'add',
            dia: {
                title: '升级程序'
            },
            url: '${ctx}/updateController/addUpdate'
        }, {
            text: '修改',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'edit',
            min: 1,
            max: 1,
            dia: {
                title: '升级程序'
            },
            url: '${ctx}/updateController/editUpdate'
        }, {
            text: '删除',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'delete',
            min: 1,
            max: 1,
            confirm: true,
            postajax: true,
            url: '${ctx}/updateController/deleteUpdate'
        }
        ]
    };*/
    rwpTemp.updatelistiniturl = '${ctx}/updateController/indexJson'; //初始url

    rwpTemp.userlistopts = {
        columns: [
            {
                display: '主键',
                name: 'id',
                isHide: true,
                primarykey: true
            },
            {
                display: '升级程序名称',
                name: 'uploadname',
                width: 220
            },
            {
                display: '信息',
                width: 80,
                render: function (rowdata) {
                    var html = '';
                    html += '<a href="${ctx}/updateController/dllindex?updateid='
                        + rowdata.id
                        + '" rwpOpts="{ ispage: true }"  class="btnBlue newbtn" style="color:#fff;padding:1.5px;4px;" >详细</a>';
                    return html;
                }
            }
        ],
        pageSize: 20,
        //toolbar: rwpTemp.updatebaritems,
        url: rwpTemp.updatelistiniturl,
        height: '99%',
        checkbox: false,
        dateFormat: 'yyyy-MM-dd hh:mm:ss',
        onAfterShowData: function () {
            rwpListLayout.gridRowclick('indexGrid');
        }
    };

    rwpTemp.userByDeptlistiniturl = '${ctx}/userController/userBydeptId'; //初始部门ID查找用户url

    rwpTemp.searchDeptUser = function (node) { //按部门查用户
        var queryUrl = rwpTemp.userByDeptlistiniturl, gridManager = $(
            "#indexGrid") // 把初始URL获得的数据放到 DIV $("#indexGrid")
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
    };


    $(function () {
        $('#userSearchLayout').rwpUILayout();
        $('#userListLayout').rwpUILayout();

    });
    $(function () {
        $("#indexGrid").rwpUIDatagrid(rwpTemp.userlistopts);

        $("#searform").rwpUIForm({
            isSearch: true
        }); //应用查询form表单
        $("#seachbtn").click(function () {
            /*  rwpTemp.staticSearch(); */
            //$(".rwpTreeSelected").removeClass("rwpTreeSelected");
            //$("#indexGrid").data('rwpUIDatagrid').refreshData({
            //	url : rwpTemp.updatelistiniturl,
            //	newPage : 1
            //});
            rwpListLayout.searchGrid('searform');
        });
        $("#seachreset").click(function () {
            $('#searform')[0].reset();
        });
    });
</script>
<div id="userSearchLayout">
    <%-- <div class="searchBar" data-options="{ 'region':'top' }">
         <form id="searform">
             <dl style="width: 274px;">
                 <dt class="labelWidth" style="width: 63px;">姓名：</dt>
                 <dd class="inputWidth">
                     <input name="fullNameSearch" type="text" id="fullNameSearch"
                            query-options="{ 'fieldName':'fullName', 'whereField':'like', 'fieldType':'String' }"/>
                 </dd>
             </dl>
             <dl style="width: 274px;">
                 <dt class="labelWidth" style="width: 63px;">用户名：</dt>
                 <dd class="inputWidth">
                     <input name="userNameSearch" type="text" id="userNameSearch"
                            query-options="{ 'fieldName':'userName', 'whereField':'like', 'fieldType':'String' }"/>
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
     </div>--%>
    <div data-options="{ 'region':'center' }">
        <div id="userListLayout" style="margin: 10px 10px 0 10px;">
            <%--	<div data-options="{'region':'left','width':'225'}" title="组织机构">
                    <div id="depttreewarp"
                        style="width: 100%; height: 100%; overflow: auto;">
                        <ul id="depttree"></ul>
                    </div>
                </div>--%>
            <div region="center">
                <div id="indexGrid" style="margin-right: 0px; margin-left: 5px;"></div>
            </div>
        </div>
    </div>
</div>
