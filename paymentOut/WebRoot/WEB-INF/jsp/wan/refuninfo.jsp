<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<style>
    table {
        *border-collapse: collapse; /* IE7 and lower */
        border-spacing: 0;
        /*width: 960px;*/
        font-family: "Microsoft YaHei";
    }

    .bordered {
        width: 960px;
        border: solid #ccc 1px;
        -moz-border-radius: 6px;
        -webkit-border-radius: 6px;
        border-radius: 6px;
        -webkit-box-shadow: 0 1px 1px #ccc;
        -moz-box-shadow: 0 1px 1px #ccc;
        box-shadow: 0 1px 1px #ccc;
    }

    .bordered tr:hover {
        background: #fbf8e9;
        -o-transition: all 0.1s ease-in-out;
        -webkit-transition: all 0.1s ease-in-out;
        -moz-transition: all 0.1s ease-in-out;
        -ms-transition: all 0.1s ease-in-out;
        transition: all 0.1s ease-in-out;
    }

    .bordered td, .bordered th {
        border-left: 1px solid #ccc;
        border-top: 1px solid #ccc;
        padding: 5px;
        text-align: left;
    }

    .bordered th {
        background-color: #dce9f9;
        background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
        background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
        background-image: -moz-linear-gradient(top, #ebf3fc, #dce9f9);
        background-image: -ms-linear-gradient(top, #ebf3fc, #dce9f9);
        background-image: -o-linear-gradient(top, #ebf3fc, #dce9f9);
        background-image: linear-gradient(top, #ebf3fc, #dce9f9);
        -webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .8) inset;
        -moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .8) inset;
        box-shadow: 0 1px 0 rgba(255, 255, 255, .8) inset;
        border-top: none;
        text-shadow: 0 1px 0 rgba(255, 255, 255, .5);
        padding: 10px;
    }

    .bordered td:first-child, .bordered th:first-child {
        border-left: none;
    }

    .bordered th:first-child {
        -moz-border-radius: 6px 0 0 0;
        -webkit-border-radius: 6px 0 0 0;
        border-radius: 6px 0 0 0;
    }

    .bordered th:last-child {
        -moz-border-radius: 0 6px 0 0;
        -webkit-border-radius: 0 6px 0 0;
        border-radius: 0 6px 0 0;
    }

    .bordered th:only-child {
        -moz-border-radius: 6px 6px 0 0;
        -webkit-border-radius: 6px 6px 0 0;
        border-radius: 6px 6px 0 0;
    }

    .bordered tr:last-child td:first-child {
        -moz-border-radius: 0 0 0 6px;
        -webkit-border-radius: 0 0 0 6px;
        border-radius: 0 0 0 6px;
    }

    .bordered tr:last-child td:last-child {
        -moz-border-radius: 0 0 6px 0;
        -webkit-border-radius: 0 0 6px 0;
        border-radius: 0 0 6px 0;
    }

    .showDefault {
        background: url("${ctx}/images/select3.png") no-repeat center center;
        /*background-size: 60px 30px;*/
    }

    .upbtn {
        /*a标签的样式*/
        padding: 4px 10px;
        height: 20px;
        line-height: 20px;
        position: relative;
        border: 3px solid #6FB3E0;
        text-decoration: none;
        color: white;
        background-color: #6FB3E0;
    }

    .up {
        /*file的样式*/
        position: absolute;
        overflow: hidden;
        right: 0;
        top: 0;
        opacity: 0;
        filter: alpha(opacity=0);
    }

    .first {
        width: 100px;
        height: 35px;
    }

    .second {
        width: 200px;
        height: 35px;
    }

</style>
<script type="text/javascript">

    var services = $("#services").val();
    console.log(services);
    var types = JSON.parse(services);
    // var types = {
    //     items: [{
    //         name: "身份证业务",
    //         value: "1",
    //         child: [{
    //             name: "补领",
    //             value: "2"
    //         }, {
    //             name: "申领",
    //             value: "3"
    //         }
    //
    //         ]
    //     },
    //         {
    //             name: "临时证业务",
    //             value: "4",
    //             child: [{
    //                 name: "临时证",
    //                 value: "5"
    //             }
    //
    //             ]
    //         }
    //     ]
    // };

    function change1(obj) {
        console.log($(obj).find('option:selected').val());
        var workcode = $(obj).find('option:selected').val();

        var chid = new Array();
        for (var i = 0; i < types.length; i++) {
            if (workcode == types[i].serCode) {
                chid = types[i].children;
            }
        }
        $(obj).next(".second").empty();
        for (var i = 0; i < chid.length; i++) {
            if (i == 0) {
            	$(obj).next(".second").append('<option  value=" "  selected="selected">' + '请选择' + '</option>');
                $(obj).next(".second").append('<option  value="' + chid[i].serCode + '">' + chid[i].serName + '</option>');
            } else {
                $(obj).next(".second").append('<option  value="' + chid[i].serCode + '">' + chid[i].serName + '</option>');
            }
        }
    }

    function change2(obj) {
        console.log($(obj).find('option:selected').val());
        var workcode = $(obj).find('option:selected').val();

        var money;
        for (var i = 0; i < types.length; i++) {
            var chid = new Array();
            chid = types[i].children;
            for (var j = 0; j < chid.length; j++) {
                if (chid[j].serCode == workcode) {
                    money = chid[j].price;
                }
            }
        }
        $(obj).parent("td").next().find("input").val(money);
        var $second = $(".money");
        var total = 0;
        $second.each(function () {
            var thismoney = Number($(this).val());
            total += thismoney;
        })
        $("#total").text(total);
        $("#totalinpt").val(total);
    }

    $(function () {
        $("#ajaxform").rwpUIForm({
            isAjaxSubmit: true,
            inputWidth: 300,
            ajaxSubmitAttrs: {
                isDialog: true,
                submitOk: function () {
                    $("#uploadPicWindow").hide();
                    $(".rwpDialog").remove();
                    $("#uploadPicWindow").hide();
                    $(".rwpDialogMask").hide();
                    $("#seachbtn").click();
                }
            }
        });


        //业务增加
        $("#addWeAppra").click(function () {

            var werowCount = $('.bordered tr').length;

            var html = '<tr class="werowCount"><td style="text-align: center;width: 40px">' + werowCount + '</td>' +
                '<td style="text-align: center;width: 100px">';
            html += '<select class="first" onChange="change1(this);"> ';
            html += '<option  value="">请选择</option>'
            for (var i = 0; i < types.length; i++) {
                html += '<option  value="' + types[i].serCode + '">' + types[i].serName + '</option>';
            }
            // '  湖北<option>广东</option>' +
            html += '</select>';
            html += '<select class="second" onChange="change2(this);" style="margin-left: 4px" name="workcode">';
            html += '<option  value="">请选择</option>'
            html += '</select>';
            html += '</td>' +
                '<td style="text-align: center;"><input type="text" class="money" name="refundMoney" style="width: 40px;height:35px;border: none;text-align: center;font-weight: bolder" placeholder="" readonly="readonly"  value="0"/></td>' +
                '<td style="text-align: center;"><input type="text" class="worknumber" name="worknumber" style="width: 300px;height:35px;border: none;text-align: center"  placeholder="请输入受理号"/></td>' +
                '<td style="text-align: center;"><span class="rwpDgIcon"><input name="weid" type="hidden" value="0"><a href="#" rwpOpts="{ isajax: true }" class="del icon">&nbsp;</a></span></td>';
            html += '</tr>';
            $(".bordered").append(html);
        });
        //业务移除
        $('.bordered').on("click", ".del", function () {
            var ths = $(this);
            ths.parent().parent().parent().remove();
        });
        $("#back").click(function () {
            $(".rwpDialog").remove();
            $(".rwpDialogMask").hide();
        });
        $("#gosub").click(function () {
            var flag = true;
            $("#uploadPicWindow").show();
            var $second = $(".second");
            $second.each(function () {
                var thismoney =$(this).val();
                if (thismoney == ' ') {
                    $.rwpUI.alert("请选择业务类型！", '提示', 'warn', false);
                    flag = false;
                }
            })
            var $worknumber = $(".worknumber");
            $worknumber.each(function () {
                var worknumber = $(this).val();
                if (worknumber == '') {
                    $.rwpUI.alert("请填写受理号！", '提示', 'warn', false);
                    flag = false;
                }
            })
            if (flag) {
                $("#ajaxform").submit();
            }
        });
        $("#allornot").click(function () {
            $("#refundinfo").toggle();
        });
    });
</script>
<div class="rwpMidLoading" style="display: none" id="insertBlock">数据加载中...</div>
<div class="clear">
    <form action="${ctx}/refund/saveApply" id="ajaxform"
          method="post">
        <input type="hidden" value='${services}' id="services">
        <fieldset>
            <legend>
                <i class="i"></i>退款信息
            </legend>
            <div class="formpanel">
                <div class="clear">
                    <ul class="DialabelWidth" style="width: 320px;">
                        <li class="editor-label"><label for="ordernumbertest">订单编号</label>
                        </li>
                        <li class="editor-field">
                            <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
                                   data-val-required="用户ID 字段是必需的。" id="ordernumbertest" name="ordernumbertest" type="text" disabled
                                   value="${ordernumber}"/>
                            <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
                                   data-val-required="用户ID 字段是必需的。" id="ordernumber" name="ordernumber" type="hidden"
                                   value="${ordernumber}"/>
                        </li>
                        <li class="editor-label"><label for="ordernumbertest">支付金额</label>
                        </li>
                        <li class="editor-field">
                            <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
                                   data-val-required="用户ID 字段是必需的。" id="paymoney" type="text" disabled
                                   value="${paymoney}"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="ordernumbertest"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                </div>
                <div class="clear">
                    <ul class="DialabelWidth" style="width: 320px;">
                        <li class="editor-label"><label for="refundreson">退款原因</label>
                        </li>
                        <li class="editor-field">
                            <%--<textarea id="refundreson" name="refundreson" cols="30" rows="10" style="width: 300px;"></textarea>--%>
                            <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
                                   data-val-required="用户ID 字段是必需的。" id="refundreson" name="refundreson" type="text"
                                   placeholder="填写退款原因" value=""/>
                        </li>

                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="refundreson"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>

                </div>
                <div class="clear">
                    <ul id="ulIsSys" class="DialabelWidth" style="display: block;">
                        <li class="editor-label"><label for="isAll">是否退全部</label>
                        </li>
                        <li class="editor-field" id="allornot"><input data-val="true"
                                                        data-val-required="是否系统级 字段是必需的。" id="isAll" name="isAll"

                                                        checked=checked

                                                        type="checkbox" value="true"/><input name="isAll" type="hidden"
                                                                                             value="false"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="isAll"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                </div>
            </div>
        </fieldset>
        <fieldset style="display: none" id="refundinfo">
            <legend>
                <i class="i"></i>退款业务
            </legend>
            <div class="formpanel">
                <div class="clear">
                    <a href="javascript:void(0);" class="rwpButton Add" id="addWeAppra"><span
                            class="rwpButtonIcon"></span><span
                            class="rwpButtonText">添加业务</span></a>
                    <table class="bordered">
                        <thead>
                        <tr>
                            <th style="text-align: center;color: black">#</th>
                            <th style="width:310px;text-align: center;color: black">业务类型</th>
                            <th style="text-align: center;color: black">金额</th>
                            <th style="color: black;text-align: center;">受理号</th>
                            <th style="color: black;text-align: center;">操作</th>
                        </tr>
                        </thead>

                    </table>
                    <p style="font-weight: bolder;font-size: 14px;color: #478FCA">总金额：<span id="total"
                                                                                            style="font-weight: bolder;font-size: 14px;color: red">0</span>元
                    </p>
                    <input type="hidden" id="totalinpt" value="" name="total">
                </div>

            </div>
        </fieldset>
        <div class="clear">
            <div class="clear" style="margin: 0 auto; width: 180px;">
                <div class="fPptions" style="float: left; margin-top: 4px;" id="gosub">
                    <a href="javascript:;" title="确定" class="btnLightBlue btn back"
                       style="width: 50px; height: 32px;" id="rego"><i class="i"></i>确定</a>
                </div>
                <div class="fPptions" style="float: left; margin-top: 4px;" id="back">
                    <a href="javascript:;" title="返回" class="btnLightBlue btn back"
                       style="width: 50px; height: 32px;" id="reback"><i class="i"></i>返回</a>
                </div>
            </div>
        </div>
    </form>
</div>