<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<script type="text/javascript">
    //订单信息
    rwpTemp.orderDetailsopts = {
        columns: [
            {
                display: '主键',
                name: 'id',
                primarykey: true,
                isHide: true
            },
            {
                display: '操作',
                width: 80,
                link: true,
                render: function (rowdata) {
                    var html = '<a href="${ctx}/orderController/orderDetails?id='
                        + rowdata.id
                        + '" rwpOpts="{ ispage: true }">详细</a>';
                    return html;
                }
            },
            {
                display: '用户',
                render: function (rowdata) {
                    return  rowdata.user.fullName;
                },
                width: 180
            },

            {
                display: '下单时间',
                name:'creattime',
                width: 180
            },
            {
                display: '支付状态',
                render: function (rowdata) {
                    // 支付状态【0：等待生成二维码 1： 待支付，2：已支付 3 失败  4  超时  5 取消】
                    if (rowdata.paystatus == 0) {
                        return "等待生成二维码";
                    } else if (rowdata.paystatus == 1) {
                        return "待支付";
                    } else if (rowdata.paystatus == 2) {
                        return "已支付";
                    } else if (rowdata.paystatus == 3) {
                        return "失败";
                    } else if (rowdata.paystatus == 4) {
                        return "超时";
                    } else {
                        return "取消";
                    }

                },
                width: 60
            },
            {
                display: '支付金额',
                render: function (rowdata) {

                    return rowdata.paymoney;
                },
                width: 60
            },
            {
                display: '支付方式',
                render: function (rowdata) {
                    if (rowdata.relpaytype == 1) {
                        return "支付宝";
                    }else if(rowdata.relpaytype == 2){
                        return "微信";
                    }else {
                         return  "";
                    }

                },
                width: 60
            }
            , {
                display: '支付时间',
                render: function (rowdata) {

                    return rowdata.paytime;
                },
                width: 180
            }
        ],

        checkbox: false,
        url: '${ctx}/orderController/orderListJson',
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
    rwpTemp.searchtypenews = function (node) {
        var deptids = getdeptids();
        $("#ordergrid").data('rwpUIDatagrid').refreshData({
            url: '${ctx}/sqxxController/SQXXJson?deptids=' + deptids
        });
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
       rwpTemp.orderDetailsopts.onAfterShowData= function () {
            rwpListLayout.gridRowclick('ordergrid');
        };

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
            var jurl = '${ctx}/orderController/orderListJson?1=1';
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
             $("#ordergrid").data('rwpUIDatagrid').refreshData({
					url : jurl
				}); 
        });
        $("#seachreset").click(function () {
            $('#searform')[0].reset();
        });
        /* $("#ordergrid").rwpUIDatagrid(rwpTemp.orderDetailsopts); */

		$("#ordergrid").rwpUIDatagrid(rwpTemp.orderDetailsopts);
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
            var jurl = '${ctx}/orderController/printModel?1=1';
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
    <div class="searchBar">
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
                <dt class="labelWidth" style="width:75px;">业务流水号：</dt>
                <dd class="inputWidth">
                	<input name="workprocessnumber" type="text" id="workprocessnumber">
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
<div class="tablepanel">
		<div id="ordergrid"></div>
	</div>
</body>
