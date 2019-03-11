<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>

<script type="text/javascript">
    rwpTemp.userServicebaritems = {
        items: [{
            text: '添加',
            click: rwpListLayout.gridToolButtonClick.itemclick,
            icon: 'add',
            dia : {
                title : '新业务'
            },
            link: true,
            url: '${ctx}/deptServiceController/addDeptService?uiCode=${uiCode}'
        }
        ]
    };


    $(function () {
        $("#userBizInfogrid")
            .rwpUIDatagrid(
                {
                    columns: [
                        {
                            display: '主键',
                            name: 'usCode',
                            isHide: true,
                            primarykey: true
                        },
                        {
                            display: '业务名称',
                            name: 'serName',
                            width: 200
                        },

                        {
                            display: '支付方式',
                            render: function (rowdata) {

                                if (rowdata.chargeMode == 1) {
                                    return "微信";
                                } else if (rowdata.chargeMode == 2) {
                                    return "支付宝";
                                } else {
                                    return "微信和支付宝";
                                }

                            }

                        },

                        {
                            display: '操作',
                            width: 120,
                            render: function (rowdata) {

                                //删除
                                var html = '<span class="rwpDgIcon">';
                                html += '<a href="${ctx}/deptServiceController/deleteDeptService?usCode='
                                    + rowdata.usCode
                                    + '" rwpOpts="{  isajax: true }" class="del icon">&nbsp;</a>';

                                return html;
                            }
                        }],
                    toolbar: rwpTemp.userServicebaritems,
                    usePager: false,
                    checkbox: false,
                    url: '${ctx}/deptServiceController/deptBizInfoJson?uiCode=${uiCode}',
                    height: '99%',
                    onAfterShowData : function() {
                         rwpListLayout.gridRowclick('userBizInfogrid');
                     }
                });
        $("#back").click(function () {
            rwpMenuHelper.rebackLastPage();
        });
    });

</script>
<div class="fPptions" style="float: left; margin-top: 4px;" id="back">
    <a href="javascript:;" title="返回" class="btnLightBlue btn back"
       style="width: 80px; height: 32px;font-weight: bolder;" id="reback"><i class="i"></i>返回</a>
</div>
<div class="fAutoPage">
<div class="fAutoPage">

    <div class="tablepanel">
        <div id="userBizInfogrid"></div>
    </div>

</div>

