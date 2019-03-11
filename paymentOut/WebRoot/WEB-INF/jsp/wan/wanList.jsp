<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<script type="text/javascript">
    //订单信息
    rwpTemp.orderDetailsopts = {
        columns: [{display: '主键', name: 'id', primarykey: true, isHide: true},
            {
                display: '操作', width: 80, link: true, render: function (rowdata) {
                    var html = '<a href="${ctx}/payWanController/wanDetail?id='
                        + rowdata.id
                        + '" rwpOpts="{ ispage: true }">详细</a>';
                    return html;
                }
            },
            {display: '身份证号码', name: 'idNumber', width: 180},
            {display: '设备类型', name: 'machineType', width: 180},
            {display: '支付金额', name: 'payMoney', width: 80},
            {display: '支付方式', name: 'payType', width: 100},
            {display: '收款方', name: 'configName', width: 180},
            {display: '支付状态', name: 'payStatus', width: 80},
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
            rwpListLayout.gridRowclick('wangrid');
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
            if (/(y+)/.test(fmt)){
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));}
            for (var k in o){
                if (new RegExp("(" + k + ")").test(fmt)){
                    fmt = fmt.replace(RegExp.$1,
                        (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
                            .substr(("" + o[k]).length)));}}
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
        $("#wangrid").rwpUIDatagrid(rwpTemp.orderDetailsopts);
        $("#seachbtn").click(function () {
            rwpListLayout.searchGrid('searform');
        });

        $("#seachreset").click(function () {
            $('#searform')[0].reset();
        });
        /* $("#wangrid").rwpUIDatagrid(rwpTemp.orderDetailsopts); */

        $.rwpUI.AjaxSelect({
            columns: rwpTemp.sqxxAjaxSelectColumns,
            defaultval: '',
            selectBoxHeight: 200
        });
       //$("#seachbtn").click();
    });


</script>
<body>
<div id="userSearchLayout">
    <div class="searchBar" data-options="{ 'region':'top' }">
        <form id="searform">
            <dl style="display: none;">
                <dt class="labelWidth" style="width: 63px;">部门隐藏域：</dt>
                <dd class="inputWidth">
                    <input style="width: 79px;" name="deptcode" type="text" id="deptcodeSearch" value="${deptcode}"
                           query-options="{ 'fieldName':'s.deptcode', 'whereField':'=', 'fieldType':'String' }"/>
                </dd>
            </dl>
            <dl style="width: 511px;">
                <dt class="labelWidth" style="width: 75px;">开始日期：</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeBeginTimeSearch"
                           query-options="{ 'fieldName': 's.paytime', 'whereField':'>=', 'fieldType':'DateTime' }"/>
                </dd>
                <dt class="labelWidth">结束日期:</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeEndTimeSearch"
                           query-options="{ 'fieldName': 's.paytime', 'whereField':'<=', 'fieldType':'DateTime' }"/>
                </dd>
            </dl>
            <dl style="width:266px;">
                <dt class="labelWidth" style="width:75px;">支付方式：</dt>
                <dd class="inputWidth">
                    <select id="payType" name="payType"
                            query-options="{ 'fieldName': 's.paytype', 'whereField':'=', 'fieldType':'String' }">
                        <option value="">全部</option>
                        <option value="1">支付宝</option>
                        <option value="2">微信</option>
                    </select>
                </dd>
            </dl>
            <dl style="width:266px;">
                <dt class="labelWidth" style="width:75px;">支付状态：</dt>
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
            <dl style="width:266px;">
                <dt class="labelWidth" style="width:75px;">订单号：</dt>
                <dd class="inputWidth">
                    <input type="text" name="ordernumber" id="ordernumber"
                           query-options="{ 'fieldName': 's.ordernumber', 'whereField':'=', 'fieldType':'String' }">
                </dd>
            </dl>
            <dl style="width:275px;">
                <dd class="inputWidth">
                    <a href="javascript:void(0);" id="seachbtn" class="rwpButton">查找</a>
                </dd>
                <dd class="inputWidth">
                    <a href="javascript:void(0);" id="seachreset" class="rwpButton">重置</a>
                </dd>
                <%--  <dd class="inputWidth">
                      <a href="javascript:void(0);" id="print" class="rwpButton">导出Excel</a>
                  </dd>--%>
            </dl>
        </form>
    </div>


    <div data-options="{ 'region':'center' }">
        <div id="userListLayout" style="margin: 10px 10px 0 10px;">
            <%--  <div data-options="{'region':'left','width':'225'}" title="组织机构">
                  <div id="depttreewarp"
                       style="width: 100%; height: 100%; overflow: auto;">
                      <ul id="depttree"></ul>
                  </div>
              </div>--%>
            <div region="center">
                <div id="wangrid"></div>
            </div>
        </div>
    </div>
</div>


</body>
