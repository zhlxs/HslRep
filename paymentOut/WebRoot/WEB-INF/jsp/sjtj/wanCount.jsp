<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<script type="text/javascript">
    //订单信息
    rwpTemp.jsonurl = '${ctx}/payWanController/wanCountJson?1=1';
    rwpTemp.wanDetailsopts = {
        columns: [
            {
                display: '设备类型',
                name: 'machinetype',
                width: 200
            },
            {
                display: '订单数量',
                name: 'count',
                width: 120
            },

            {
                display: '合计/元',
                name: 'total',
                width: 200
            },
            {
                display: '收款方',
                name: 'name',
                width: 300
            }

        ],
        checkbox: false,
        url: rwpTemp.jsonurl,
        dateFormat: 'yyyy-MM-dd hh:mm:ss',
        height: '99%',
        autoCheckChildren: false,
        onAfterShowData: function () {
            rwpListLayout.gridRowclick('ordergrid');
        }
    };

    $('input[name=recordTimeSearch]').rwpUIDateinput();

    // var getdeptids = function () {
    //     var treeManager = $('#typetree').data('rwpUITree'),
    //         deptitems = treeManager.getChecked() || [],
    //         nodeExpandCallbacks = treeManager.nodeExpandCallbacks,  //获取目前需要延迟加载的结点集合
    //         deptids = [], parentdeptids = [];
    //     $.each(deptitems, function (i, deptitem) {
    //         deptids.push(deptitem.data.ID);
    //     });
    //     if (deptitems.length == 0) {
    //         //没有勾选部门时传0
    //         deptids.push("0");
    //     }
    //     return deptids;
    // };
    // var getTreeParams = function(){
    //     var treeManager = $('#depttree').data('rwpUITree'),
    //         deptitems = treeManager.getChecked() || [],
    //         nodeExpandCallbacks = treeManager.nodeExpandCallbacks,  //获取目前需要延迟加载的结点集合
    //         deptids = [],macIds=[], parentdeptids = [];
    //     $.each(deptitems, function (i, deptitem) {
    //         if(deptitem.data.ID!=null&&deptitem.data.ID!='') {
    //             if (deptitem.data.parentID!=null&&deptitem.data.parentID!=''){
    //                 parentdeptids.push(deptitem.data.parentID);
    //             }
    //             deptids.push(deptitem.data.ID);
    //         }
    //         if(deptitem.data.macId!=null&&deptitem.data.macId!=''){
    //             macIds.push(deptitem.data.macId);
    //         }
    //     });
    //     var parms = {"deptids":deptids,"macIds":macIds,"parentdeptids":parentdeptids};
    //     return parms;
    // };
    function getJurl() {
        var machinetype = $("#machinetype").val();
        var configname = $("#configname").val();
        var payType = $("#payType").val();
        var begintime = $("#recordTimeBeginTimeSearch").val();
        var endtime = $("#recordTimeEndTimeSearch").val();
        //var ordernumber=$("#ordernumber").val();
        var jurl = '${ctx}/payWanController/wanCountJson?1=1';
        if (machinetype != null && machinetype != '') {
             jurl += '&machinetype=' + machinetype;
         }
        if (configname != null && configname != '') {
            jurl += '&configname=' + configname;
        }
        if (payType != null && payType != '') {
            jurl += '&payType=' + payType;
        }
        if (begintime != null && begintime != '') {
            jurl += '&begintime=' + begintime;
        }
        if (endtime != null && endtime != '') {
            jurl += '&endtime=' + endtime;
        }
        /*  if (configName != null && configName != '') {
              jurl += '&configName=' + configName;
          }
          if (ordernumber != null && ordernumber != '') {
              jurl += '&ordernumber=' + ordernumber;
          }*/
        return jurl;
    }

    $(function () {
        $("#_configgrid").rwpUIDatagrid(rwpTemp.wanDetailsopts);
        $('#userSearchLayout').rwpUILayout();
        $('#userListLayout').rwpUILayout();
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
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1,
                        (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
                            .substr(("" + o[k]).length)));
            return fmt;
        };
        var date = new Date().Format("yyyy-MM-dd");
        $("#recordTimeBeginTimeSearch").val(date);
        $("#recordTimeEndTimeSearch").val(date);
        <%--//点击加载部门树事件--%>
        <%--rwpTemp.getDeptTreeDelay = function (deptNodeData, level) {--%>
        <%--if (deptNodeData.isdelay) {--%>
        <%--var params = [];--%>
        <%--params.push({--%>
        <%--name: 'parentid',--%>
        <%--value: deptNodeData.ID--%>
        <%--});--%>
        <%--return {--%>
        <%--url: '${ctx}/payWanController/listTreeJson',--%>
        <%--params: params--%>
        <%--};--%>
        <%--}--%>
        <%--return false;--%>
        <%--};--%>
        $("#seachbtn").click(function () {
            var url = getJurl();
            $("#_configgrid").data('rwpUIDatagrid').refreshData({
                url: url
            });
        });

        /*    $("#depttree").rwpUITree({
                checkbox: true,
                dataAttrs: {
                    id: 'ID',
                    text: 'deptName'
                },
                url: '

        ${ctx}/payWanController/listTreeJson?parentid=0',
            onCheck: rwpTemp.searchWans,
            delay: rwpTemp.getDeptTreeDelay
        });*/
        $("#searform").rwpUIForm({
            inputWidth: 150,
            isSearch: true
        });

        $("#seachreset").click(function () {
            $('#searform')[0].reset();
        });
        //  $("#ordergrid").rwpUIDatagrid(rwpTemp.sqxxopts);

        $.rwpUI.AjaxSelect({
            columns: rwpTemp.sqxxAjaxSelectColumns,
            defaultval: '',
            selectBoxHeight: 200
        });
    });
    $("#print").click(function () {
        var parentdeptids = getTreeParams().parentdeptids;
        var deptids = getTreeParams().deptids;
        var macIds = getTreeParams().macIds;
        //var payStatus = $("#payStatus").val();
        var payType = $("#payType").val();
        var begintime = $("#recordTimeBeginTimeSearch").val();
        var endtime = $("#recordTimeEndTimeSearch").val();
        // var configName=$("#configName").val();
        //var ordernumber=$("#ordernumber").val();
        var jurl = '${ctx}/payWanController/printWanDataCountModel?1=1&type=' + thisdivname;
        /* if (payStatus != null && payStatus != '') {
             jurl += '&payStatus=' + payStatus;
         }*/
        if (payType != null && payType != '') {
            jurl += '&payType=' + payType;
        }
        if (begintime != null && begintime != '') {
            jurl += '&begintime=' + begintime;
        }
        if (endtime != null && endtime != '') {
            jurl += '&endtime=' + endtime;
        }
        if (deptids.length != 0) {
            jurl += '&deptids=' + deptids;
        }
        if (macIds.length != 0) {
            jurl += '&macIds=' + macIds;
        }
        if (parentdeptids.length != 0) {
            jurl += '&parentdeptids=' + parentdeptids;
        }
        $.post(jurl, {}, function (data) {
            if (data.stateType == 0) {
                window.location.href = data.stateValue;
            } else {
                $.rwpUI.error("" + "" + "导出失败!");
            }
        });
    });
    /*   $(function () {
           $(".rwpTab .rwpTabNavList .rwpTabNavItem").bind(
               "click",
               function () {
                   $(".rwpTab .rwpTabNavList .rwpTabNavItem").removeClass(
                       "rwpTabCurrent");
                   $(this).addClass("rwpTabCurrent");
                   var divname = $(this).attr("id");
                   $(".parmdiv").hide();
                   $("." + divname).show();
                   thisdivname = divname;
                   var url = getJurl();
                   var thiselem = "_" + thisdivname;
                   var $div = $("#" + thiselem);
                   rwpTemp.wanDetailsopts.url = url;
                   $div.rwpUIDatagrid(rwpTemp.wanDetailsopts);
                   // rwpTemp.jsonurl= '${ctx}/payWanController/wanCountJson?1=1';
                // console.log( rwpTemp.jsonurl);
            });
    })*/


</script>
<body>
<div id="userSearchLayout">
    <div class="searchBar" data-options="{ 'region':'top' }">
        <form id="searform">

            <dl style="width: 511px;">
                <dt class="labelWidth" style="width: 75px;">开始日期：</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeBeginTimeSearch"
                           query-options="{ 'fieldName': 'slsj', 'whereField':'>=', 'fieldType':'DateTime' }"/>
                </dd>
                <dt class="labelWidth">结束日期:</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeEndTimeSearch"
                           query-options="{ 'fieldName': 'slsj', 'whereField':'<=', 'fieldType':'DateTime' }"/>
                </dd>
            </dl>
            <dl style="width:266px;">
                <dt class="labelWidth" style="width:75px;">收款方：</dt>
                <dd class="inputWidth">
                    <input name="configname" type="text" id="configname">
                </dd>
            </dl>
            <dl style="width:266px;">
                <dt class="labelWidth" style="width:75px;">设备类型：</dt>
                <%--//设备类型 0自助拍照机，1自助领证机，2自助申请机 3 签注机 5 交警拍照机 6 警银通--%>
                <dd class="inputWidth">
                    <select id="machinetype" name="machinetype">
                        <option value="">全部</option>
                        <option value="0">自助拍照机</option>
                        <option value="1">自助领证机</option>
                        <option value="2">自助申请机</option>
                        <option value="3">签注机</option>
                        <option value="5">交警拍照机</option>
                        <option value="6">警银通</option>
                    </select>
                </dd>
            </dl>
            <dl style="width:266px;">
                <dt class="labelWidth" style="width:75px;">支付方式：</dt>
                <dd class="inputWidth">
                    <select id="payType" name="payType">
                        <option value="">全部</option>
                        <option value="0">未知</option>
                        <option value="1">支付宝</option>
                        <option value="2">微信</option>
                    </select>
                </dd>
            </dl>

            <dl style="width:275px;">
                <dd class="inputWidth">
                    <a href="javascript:void(0);" id="seachbtn" class="rwpButton">查找</a>
                </dd>
                <dd class="inputWidth">
                    <a href="javascript:void(0);" id="seachreset" class="rwpButton">重置</a>
                </dd>
                <dd class="inputWidth">
                    <a href="javascript:void(0);" id="print" class="rwpButton">导出Excel</a>
                </dd>
            </dl>
        </form>
    </div>
    <div data-options="{ 'region':'center' }">
        <div id="userListLayout" style="margin: 10px 10px 0 10px;">
        <%--    <div data-options="{'region':'left','width':'225'}" title="组织机构">
                <div id="depttreewarp"
                     style="width: 100%; height: 100%; overflow: auto;">
                    <ul id="depttree"></ul>
                </div>
            </div>--%>
            <div region="center">
              <%--  <div class="rwpTab">
                    <div class="rwpTabNavList">
                        <span class="rwpTabNavItem rwpTabCurrent" name="wait" id="configgrid"><a href="javascript:void(0);"><i
                                class="i i1"></i>收款方</a></span>
                        <span class="rwpTabNavItem" name="pass" id="macgrid"><a href="javascript:void(0);"><i
                                class="i i2"></i>设备</a></span>
                    </div>
                </div>--%>
                <div id="_macgrid" class="macgrid parmdiv"></div>
                <div id="_configgrid" class="configgrid parmdiv"></div>
            </div>
            <%--  <div region="center">
                  <div id="_configgrid"></div>
              </div>--%>
        </div>
    </div>
</div>
</body>
