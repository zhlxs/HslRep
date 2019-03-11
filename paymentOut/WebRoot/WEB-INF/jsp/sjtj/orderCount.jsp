<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<script type="text/javascript">
    //订单信息
    rwpTemp.orderDetailsopts = {
        columns: [
            {
                display: '订单业务名称',
                name: 'bizName',
                width: 180
            },
            {
                display: '订单数量',
                name: 'bizCount',
                width: 80


            },
            {
                display: '单价/元',
                name: 'price',
                width: 80


            },
            {
                display: '合计/元',
                name: 'total',
                width: 180
            }

        ],

        checkbox: false,
        url: '${ctx}/orderController/orderCountJson',
        dateFormat: 'yyyy-MM-dd hh:mm:ss',
        height: '99%',
        autoCheckChildren: false,
        onAfterShowData: function () {
            rwpListLayout.gridRowclick('ordergrid');
        }
    };
    $('input[name=recordTimeSearch]').rwpUIDateinput();

    var getdeptids = function () {
        var treeManager = $('#typetree').data('rwpUITree'),
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
        return deptids;
    };


    $(function () {
       /* $('#newsSearchLayout').rwpUILayout();
        $('#newsListLayout').rwpUILayout();*/
    /*    $("#typetree")
            .rwpUITree(
                {
                    checkbox: true,
                    dataAttrs: {
                        id: 'ID',
                        text: 'deptName'
                    },
                    url: '${ctx}/deptController/deptTreeJson',
                    onCheck: rwpTemp.searchtypenews
                });*/
    });

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
        $("#ordergrid").rwpUIDatagrid(rwpTemp.orderDetailsopts);
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
        /* 	$("#recordTimeBeginTimeSearch").val(date);
            $("#recordTimeEndTimeSearch").val(date); */

        $("#searform").rwpUIForm({
            inputWidth: 150,
            isSearch: true
        });
        //应用查询form表单
        $("#seachbtn").click(function () {
            var paystatus = $("#paystatus").val();
            var workprocessnumber = $("#workprocessnumber").val();
            var begintime = $("#recordTimeBeginTimeSearch").val();
            var endtime = $("#recordTimeEndTimeSearch").val();
            var jurl = '${ctx}/orderController/orderCountJson?1=1';
            if (paystatus != null && paystatus != '') {
                jurl += '&paystatus=' + paystatus;
            }

            if (begintime != null && begintime != '') {
                jurl += '&begintime=' + begintime;
            }
            if (endtime != null && endtime != '') {
                jurl += '&endtime=' + endtime;
            }
            $("#ordergrid").data('rwpUIDatagrid').refreshData({
                url: jurl
            });
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
    	var paystatus = $("#paystatus").val();
		var workprocessnumber = $("#workprocessnumber").val();
		var begintime = $("#recordTimeBeginTimeSearch").val();
		var endtime = $("#recordTimeEndTimeSearch").val();
		var jurl = '${ctx}/orderController/sjtjprint?1=1';
		if (paystatus != null && paystatus != '') {
			jurl += '&paystatus=' + paystatus;
		}
		if (workprocessnumber != null && workprocessnumber != '') {
			jurl += '&workprocessnumber=' + workprocessnumber;
		}
		if (begintime != null && begintime != '') {
			jurl += '&begintime=' + begintime;
		}
		if (endtime != null && endtime != '') {
			jurl += '&endtime=' + endtime;
		}
        $.post(jurl, {}, function (data) {
            if (data.stateType == 0) {
                window.location.href = data.stateValue;
            } else {
                $.rwpUI.error("" + "" + "导出失败!");
            }
        });
    });


</script>
<body>
<div id="newsSearchLayout">
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
                <dt class="labelWidth" style="width:75px;">支付状态：</dt>
                <dd class="inputWidth">
                    <select id="paystatus" name="paystatus" >
                            
                        <option value="">全部</option>
                        <option value="0">等待生成二维码</option>
                        <option value="1">待支付</option>
                        <option value="2">已支付</option>
                        <option value="3">失败</option>
                        <option value="4">超时</option>
                        <option value="5">取消</option>
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
    <!-- 部门列表 -->
    <div data-options="{ 'region':'center' }">
        <div id="newsListLayout" style="margin: 10px 10px 0 10px;">
            <div data-options="{'region':'left','width':'210'}" title="部门列表">
                <div id="typetreewarp"
                     style="width: 100%; height: 100%; overflow: auto;">
                    <ul id="typetree"></ul>
                </div>
            </div>

            <div region="center">
                <div id="ordergrid" style="margin-right: 0px; margin-left: 5px;"></div>
            </div>

        </div>
    </div>
</div>
</body>
