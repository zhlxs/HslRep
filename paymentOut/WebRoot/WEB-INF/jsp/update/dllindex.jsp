<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>

<script type="text/javascript">
    rwpTemp.dllbaritems = {
        items: [{
            text: '添加',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'add',
            dia: {
                title: '升级程序'
            },
            url: '${ctx}/updateController/addDll?updateid=${updateid}'
        }, {
            text: '删除',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'delete',
            min: 1,
            max: 1,
            confirm: true,
            postajax: true,
            url: '${ctx}/updateController/deleteDll'
        }, {
            text: '返回上一页',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: '',
            link: true,
            url: '${ctx}/updateController/index'
        }
        ]
    };
    rwpTemp.dlllistiniturl = '${ctx}/updateController/dllindexJson?updateid=${updateid}'; //初始url

    rwpTemp.userlistopts = {
        columns: [
            {
                display: '主键',
                name: 'id',
                isHide: true,
                primarykey: true
            },
            {
                display: '更新文件名称',
                name: 'dllname',
                width: 320
            }, {
                display: '下载地址',
                name: 'downloadpath',
                width: 400
            } ,{
                display: '更新路径',
                name: 'clientcopypath',
                width: 80
            }
            , {
                display: '版本号',
                name: 'version',
                width: 80
            },
            {
                display: '状态',
                width: 70,
                render: function (row) {
                    var html = row.isstart == 1 ? '<span class="fClr3">启用</span>'
                        : '<span class="fClr4">停用</span>';
                    return html;
                }
            },
            {
                display: '操作',
                width: 80,
                render: function (rowdata) {
                    var html = '';
                    if (rowdata.isstart)
                        html += '<a href="${ctx}/updateController/changeDll?isstart=0&id='
                            + rowdata.id
                            + '" rwpOpts="{ isajax: true }" class="btnRed newbtn" style="color:#fff;padding:1.5px;4px;" ><span>停用</span></a>';
                    else
                        html += '<a href="${ctx}/updateController/changeDll?isstart=1&id='
                            + rowdata.id
                            + '" rwpOpts="{ isajax: true }" class="btnGreen newbtn" style="color:#fff;padding:1.5px;4px;">启用</a>';
                    return html;
                }
            }
        ],
        pageSize: 20,
        toolbar: rwpTemp.dllbaritems,
        url: rwpTemp.dlllistiniturl,
        height: '99%',
        checkbox: true,
        dateFormat: 'yyyy-MM-dd hh:mm:ss',
        onAfterShowData: function () {
            rwpListLayout.gridRowclick('dllGrid');
        }
    };


    $(function () {
        $('#userSearchLayout').rwpUILayout();
        $('#userListLayout').rwpUILayout();

    });
    $(function () {
        $("#dllGrid").rwpUIDatagrid(rwpTemp.userlistopts);
        $("#searform").rwpUIForm({
            isSearch: true
        }); //应用查询form表单
        $("#seachbtn").click(function () {
            /*  rwpTemp.staticSearch(); */
            //$(".rwpTreeSelected").removeClass("rwpTreeSelected");
            //$("#dllGrid").data('rwpUIDatagrid').refreshData({
            //	url : rwpTemp.dlllistiniturl,
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
                <div id="dllGrid" style="margin-right: 0px; margin-left: 5px;"></div>
            </div>
        </div>
    </div>
</div>
