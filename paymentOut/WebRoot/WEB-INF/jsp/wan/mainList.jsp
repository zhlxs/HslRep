<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>

<script type="text/javascript">
    //rwpTemp.userlistiniturl = '${ctx}/userController/userJson'; //初始url
    rwpTemp.mainopts = {
        columns: [{display: '主键', name: 'id', primarykey: true, isHide: true},
            {
                display: '操作', width: 120, link: true, render: function (rowdata) {
                    //var  html = '<a class="btnRed newbtn" style="color:#fff;padding:2px;5px;" href="'+atturl+'" rwpOpts="{ isdialog: true}" title="详细">详细</a>';
                    var html = '';
                    if (rowdata.refundStatus != '0' &&rowdata.refundStatus !='1' &&rowdata.refundStatus !='4') {
                        html += '<a class="btnPurple newbtn" style="color:#fff;padding:1.5px;4px;"  href="${ctx}/refund/refundApply?ordernumber='
                            + rowdata.orderNumber
                            + '" rwpOpts="{ isdialog: true }" title="发起退款" button="{}">发起退款</a>&nbsp;|&nbsp;';
                    }
                    html += '<a class="btnBlue newbtn" style="color:#fff;padding:1.5px;4px;"   href="${ctx}/payWanController/wanDetail?id='
                        + rowdata.id
                        + '" rwpOpts="{ ispage: true }">详细</a>';
                    return html;
                }
            },
            {
                display: '退款状态', width: 80, render: function (data) {
                    var html = '';
                    if (data.refundStatus == '0') {
                        html = '<span style="color:#f19249;">待审核</span>';
                    } else if (data.refundStatus == '1') {
                        html = '<span style="color:#f19249;">退款中</span>';
                    } else if (data.refundStatus == '2') {
                        html = '<span style="color:red;">请求驳回</span>';
                    } else if (data.refundStatus == '3') {
                        html = '<span style="color:red;">取消</span>';
                    } else if (data.refundStatus == '4') {
                        html = '<span style="color:green;">退款成功</span>';
                    } else {
                        html = '<span style="color:blue;">未发起退款</span>';
                    }
                    return html;
                }
            },
            {
                display: '支付状态', width: 80, render: function (data) {
                    var html = '';
                    if (data.payStatus == '待生成') {
                        html = '<span style="color:#f19249;">data.payStatus</span>';
                    } else if (data.payStatus == '待支付') {
                        html = '<span style="color:#f19249;">data.payStatus</span>';
                    } else if (data.payStatus == '成功') {
                        html = '<span style="color:green;">成功</span>';
                    } else {
                        html = '<span style="color:red;">失败</span>';
                    }
                    return html;
                }
            },

            {display: '支付金额', name: 'payMoney', width: 80},
            {display: '支付方式', name: 'payType', width: 100},
            {display: '收款方', name: 'configName', width: 180},

            {display: '支付时间', name: 'payTime', width: 180},
            {display: '订单号', name: 'orderNumber', width: 220},
            {display: '描述', name: 'orderDescribe', width: 180}
        ],
        checkbox: false,
        url: '${ctx}/payWanController/wanListJson',
        dateFormat: 'yyyy-MM-dd hh:mm:ss',
        height: '99%',
        autoCheckChildren: false,
        onAfterShowData: function () {
            rwpListLayout.gridRowclick('mainGrid');
        }
    };

    rwpTemp.userByDeptlistiniturl = '${ctx}/userController/userBydeptId'; //初始部门ID查找用户url

    rwpTemp.searchDeptUser = function (node) { //按部门查用户
        var queryUrl = rwpTemp.userByDeptlistiniturl, gridManager = $(
            "#mainGrid") // 把初始URL获得的数据放到 DIV $("#mainGrid")
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
    }

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
            checkbox: false,
            dataAttrs: {
                id: 'ID',
                text: 'deptName'
            },
            url: '${ctx}/deptController/listTreeJson?parentid=0',
            onSelect: rwpTemp.searchDeptUser,
            delay: rwpTemp.getDeptTreeDelay
        });
        $("#mainGrid").rwpUIDatagrid(rwpTemp.mainopts);

        $("#seachbtn").click(function () {
            /*  rwpTemp.staticSearch(); */
            //$(".rwpTreeSelected").removeClass("rwpTreeSelected");
            //$("#mainGrid").data('rwpUIDatagrid').refreshData({
            //	url : rwpTemp.userlistiniturl,
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
    <div class="searchBar" data-options="{ 'region':'top' }">
        <form id="searform">
            <%--    <dl style="display: none;">
                    <dt class="labelWidth" style="width: 63px;">部门隐藏域：</dt>
                    <dd class="inputWidth">
                        <input style="width: 79px;" name="deptid" type="text" id="deptid" value=""
                               query-options="{ 'fieldName':'d.id', 'whereField':'=', 'fieldType':'Long' }"/>
                    </dd>
                </dl>--%>
            <dl style="display: none;">
                <dt class="labelWidth" style="width: 63px;">部门code隐藏域：</dt>
                <dd class="inputWidth">
                    <input style="width: 79px;" name="deptcode" type="text" id="deptcodeSearch" value="${deptcode}"
                           query-options="{ 'fieldName':'s.deptcode', 'whereField':'=', 'fieldType':'String' }"/>
                </dd>
            </dl>
            <dl style="width: 520px;">
                <dt class="labelWidth" style="width: 75px;">开始日期：</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeBeginTimeSearch"
                           query-options="{ 'fieldName': 's.paytime', 'whereField':'>=', 'fieldType':'DateTime' }"/>
                </dd>
                <dt class="labelWidth" style="width: 75px;">结束日期:</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeEndTimeSearch"
                           query-options="{ 'fieldName': 's.paytime', 'whereField':'<=', 'fieldType':'DateTime' }"/>
                </dd>
            </dl>
            <dl style="width: 274px;">
                <dt class="labelWidth" style="width: 80px;">支付方式：</dt>
                <dd class="inputWidth">
                    <select id="payType" name="payType"
                            query-options="{ 'fieldName': 's.paytype', 'whereField':'=', 'fieldType':'String' }">
                        <option value="">全部</option>
                        <option value="1">支付宝</option>
                        <option value="2">微信</option>
                    </select>
                </dd>
            </dl>

            <dl style="width: 300px;">
                <dt class="labelWidth" style="width: 80px;">支付状态：</dt>
                <dd class="inputWidth">
                    <select id="payStatus" name="payStatus"
                            query-options="{ 'fieldName': 's.paystatus', 'whereField':'=', 'fieldType':'String' }">
                        <option value="">全部</option>
                        <option value="0">待生成</option>
                        <option value="1">待支付</option>
                        <option value="2">已支付</option>
                        <option value="3">失败</option>
                    </select>
                </dd>
            </dl>
            <dl style="width: 274px;">
                <dt class="labelWidth" style="width: 63px;">订单号：</dt>
                <dd class="inputWidth">
                    <input type="text" name="ordernumber" id="ordernumber"
                           query-options="{ 'fieldName': 's.ordernumber', 'whereField':'=', 'fieldType':'String' }">
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
                <div id="mainGrid" style="margin-right: 0px; margin-left: 5px;"></div>
            </div>
        </div>
    </div>
</div>
