<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>

<script type="text/javascript">
    //rwpTemp.userlistiniturl = '${ctx}/userController/userJson'; //初始url
    rwpTemp.mainopts = {
        columns: [{display: '主键', name: 'id', primarykey: true, isHide: true},
            {
                display: '操作', width: 160, link: true, render: function (rowdata) {
                    var html = '';
                    if (rowdata.checkstatus == '0') {
                        html = '<a class="btnGreen newbtn" style="color:#fff;padding:1.5px 4px;"  href="${ctx}/refund/orders?id='
                            + rowdata.id
                            + '" rwpOpts="{ isajax: true }" title="同意">同意</a>  |  ';
                        html += '<a class="btnRed newbtn" style="color:#fff;padding:1.5px 4px;"  href="${ctx}/refund/checkApply?id='
                            + rowdata.id
                            + '" rwpOpts="{ isajax: true }" title="驳回">驳回</a>  |  ';

                    }
                    html += '<a class="btnBlue newbtn" style="color:#fff;padding:1.5px;4px;"   href="${ctx}/refund/refundinfo?id='
                        + rowdata.id
                        + '" rwpOpts="{ ispage: true }">详细</a>';
                    return html;
                }
            },
            {
                display: '退款状态', width: 80, render: function (data) {
                    var html = '';
                    if (data.checkstatus == '0') {
                        html = '<span style="color:#f19249;">待审核</span>';
                    } else if (data.checkstatus == '1') {
                        html = '<span style="color:#f19249;">退款中</span>';
                    } else if (data.checkstatus == '2') {
                        html = '<span style="color:red;">请求驳回</span>';
                    } else if (data.checkstatus == '3') {
                        html = '<span style="color:red;">取消</span>';
                    } else {
                        html = '<span style="color:green;">退款成功</span>';
                    }
                    return html;
                }
            },
            {display: '订单号', name: 'ordernumber', width: 280},
            {display: '退款金额', name: 'paymoney', width: 80},
            {display: '申请时间', name: 'createtime', width: 180},
            {display: '申请人', name: 'applyusername', width: 100},
            {display: '审核时间', name: 'checktime', width: 180},
            {display: '退款原因', name: 'refundreson', width: 300}
        ],
        checkbox: false,
        url: '${ctx}/refund/waitrefundListJson',
        dateFormat: 'yyyy-MM-dd hh:mm:ss',
        height: '99%',
        autoCheckChildren: false,
        onAfterShowData: function () {
            rwpListLayout.gridRowclick('myrefundGrid');
        }
    };

    rwpTemp.userByDeptlistiniturl = '${ctx}/userController/userBydeptId'; //初始部门ID查找用户url

    rwpTemp.searchDeptUser = function (node) { //按部门查用户
        var treeManager = $('#depttree').data('rwpUITree'),
            deptitems = treeManager.getChecked() || [],
            nodeExpandCallbacks = treeManager.nodeExpandCallbacks,  //获取目前需要延迟加载的结点集合
            deptids = [], parentdeptids = [];
        $.each(deptitems, function (i, deptitem) {
            deptids.push(deptitem.data.ID);
        });
        if (deptitems.length == 0) {
            //没有勾选部门时传0
            deptids.push("0");
        }
        $("#deptid").val(deptids.toString());
        rwpListLayout.searchGrid('searform');
    };
    rwpTemp.getDeptTreeDelay = function (deptNodeData, level) {
        if (deptNodeData.isdelay) {
            var params = [];
            params.push({
                name: 'isall',
                value: 1
            });
            params.push({
                name: 'islazy',
                value: 1
            });
            params.push({
                name: 'parentid',
                value: deptNodeData.ID
            });
            return {
                url: '${ctx}/deptController/listTreeJson',
                params: params
            };
        }
        return false;
    };
    rwpTemp.staticSearch = function () {
        var staticparams = rwpTemp.getStaticParams(), gridManager, gridSearchOpts;
        if (staticparams && staticparams.length > 0) {
            gridSearchOpts = {
                params: staticparams
            };
            gridManager = rwpListLayout.getCurrentGrid();
            if (gridManager) {
                gridManager.refreshData(gridSearchOpts);
            }
        }
    };

    $(function () {
        $('input[name=recordTimeSearch]').rwpUIDateinput();
        Date.prototype.Format = function (fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "h+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds()
                //毫秒
            };
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1,
                        (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
                            .substr(("" + o[k]).length)));
                }
            }
            return fmt;
        };
        var date = new Date().Format("yyyy-MM-dd");
        $("#recordTimeBeginTimeSearch").val(date);
        $("#recordTimeEndTimeSearch").val(date);
        $("#searform").rwpUIForm({
            inputWidth: 150,
            isSearch: true
        });
        $('#userSearchLayout').rwpUILayout();
        $('#userListLayout').rwpUILayout();
        $("#depttree").rwpUITree({
            checkbox: true,
            dataAttrs: {
                id: 'ID',
                text: 'deptName'
            },
            url: '${ctx}/deptController/listTreeJson?parentid=0',
            onCheck: rwpTemp.searchDeptUser,
            delay: rwpTemp.getDeptTreeDelay
        });
        $("#myrefundGrid").rwpUIDatagrid(rwpTemp.mainopts);

        $("#seachbtn").click(function () {
            rwpListLayout.searchGrid('searform');
        });
        $("#seachreset").click(function () {
            $('#searform')[0].reset();
        });
    });
</script>
<div id="userSearchLayout">
    <div class="searchBar" data-options="{ 'region':'top' }">
        <form id="searform">
            <dl style="display: none;">
                <dt class="labelWidth" style="width: 63px;">部门隐藏域：</dt>
                <dd class="inputWidth">
                    <input style="width: 79px;" name="deptid" type="text" id="deptid" value="${deptid}"
                           query-options="{ 'fieldName':'d.id', 'whereField':'in', 'fieldType':'String' }"/>
                </dd>
            </dl>
            <%--  <dl style="display: none;">
                  <dt class="labelWidth" style="width: 63px;">部门code隐藏域：</dt>
                  <dd class="inputWidth">
                      <input style="width: 79px;" name="deptcode" type="text" id="deptcodeSearch" value="${deptcode}"
                             query-options="{ 'fieldName':'s.deptcode', 'whereField':'=', 'fieldType':'String' }"/>
                  </dd>
              </dl>--%>
            <dl style="width: 520px;">
                <dt class="labelWidth" style="width: 75px;">开始日期：</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeBeginTimeSearch"
                           query-options="{ 'fieldName': 'ra.createtime', 'whereField':'>=', 'fieldType':'DateTime' }"/>
                </dd>
                <dt class="labelWidth" style="width: 75px;">结束日期:</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeEndTimeSearch"
                           query-options="{ 'fieldName': 'ra.createtime', 'whereField':'<=', 'fieldType':'DateTime' }"/>
                </dd>
            </dl>
            <%--    <dl style="width: 274px;">
                    <dt class="labelWidth" style="width: 63px;">支付方式：</dt>
                    <dd class="inputWidth">
                        <select id="payType" name="payType"
                                query-options="{ 'fieldName': 's.paytype', 'whereField':'=', 'fieldType':'String' }">
                            <option value="">全部</option>
                            <option value="1">支付宝</option>
                            <option value="2">微信</option>
                        </select>
                    </dd>
                </dl>--%>

            <dl style="width: 300px;">
                <dt class="labelWidth" style="width: 80px;">退款状态：</dt>
                <dd class="inputWidth">
                    <select id="payStatus" name="payStatus"
                            query-options="{ 'fieldName': 'ra.checkstatus', 'whereField':'=', 'fieldType':'String' }">
                        <option value="">全部</option>
                        <option value="0">待审核</option>
                        <option value="1">退款中</option>
                        <option value="2">请求驳回</option>
                        <option value="3">退款成功</option>
                    </select>
                </dd>
            </dl>
            <dl style="width: 274px;">
                <dt class="labelWidth" style="width: 63px;">订单号：</dt>
                <dd class="inputWidth">
                    <input type="text" name="ordernumber" id="ordernumber"
                           query-options="{ 'fieldName': 'ra.ordernumber', 'whereField':'=', 'fieldType':'String' }">
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
        <div id="userListLayout" style="margin: 10px 10px 0 10px;">
            <div data-options="{'region':'left','width':'225'}" title="组织机构">
                <div id="depttreewarp"
                     style="width: 100%; height: 100%; overflow: auto;">
                    <ul id="depttree"></ul>
                </div>
            </div>
            <div region="center">
                <div id="myrefundGrid" style="margin-right: 0px; margin-left: 5px;"></div>
            </div>
        </div>
    </div>
</div>
