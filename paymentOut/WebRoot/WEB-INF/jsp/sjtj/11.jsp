<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript" charset="utf-8"
        src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${ctx}/ueditor/ueditor.all.min.js"></script>

<link rel="stylesheet" href="${ctx}/mulitselect/jquery.multiselect.css">
<link rel="stylesheet" href="${ctx}/mulitselect/jquery-ui.css">
<link rel="stylesheet" href="${ctx}/mulitselect/prettify.css">
<link rel="stylesheet" href="${ctx}/mulitselect/style.css">
<script type="text/javascript" src="${ctx}/mulitselect/jquery-ui.js"></script>
<script type="text/javascript"
        src="${ctx}/mulitselect/jquery.multiselect.js"></script>
<script type="text/javascript" src="${ctx}/mulitselect/prettify.js"></script>

<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
        src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- <style>
.edui-popup-content.edui-default {
height: auto !important;}
</style> -->
<script type="text/javascript">
    $(function() {
        $("#ajaxform").rwpUIForm({
            inputWidth: 'auto',
            isAjaxSubmit : true,
            ajaxSubmitAttrs : {
                isDialog : true,
                submitOk : rwpMenuHelper.rebackLastPage
            }
        });
        $("#back").click(function() {
            rwpMenuHelper.rebackLastPage();
        });
        $("#gosub").click(function() {
            $("#ajaxform").submit();
        });
        /* $("#next").click(function(){
            if($(".rwpTabCurrent").next().length != 0){
            var nextName=$(".rwpTabCurrent").next().attr("id");
            var $next=$(".rwpTabCurrent").next();
            if(nextName=="_yj"){
                $("#next").hide();
            }
            $(".rwpTab .rwpTabNavList .rwpTabNavItem").removeClass(
            "rwpTabCurrent");
            $next.addClass("rwpTabCurrent");
            $(".parmdiv").hide();
            $("." + nextName).show();
            }
        }); */
        $(".parmdiv").hide();
        $("._xt").show();
        $(".rwpTab .rwpTabNavList .rwpTabNavItem").bind(
            "click",
            function() {
                $(".rwpTab .rwpTabNavList .rwpTabNavItem").removeClass(
                    "rwpTabCurrent");
                $(this).addClass("rwpTabCurrent");
                var divname = $(this).attr("id");
                $(".parmdiv").hide();
                $("." + divname).show();
            });
    });
</script>

<style type="text/css">
    .parmBut {
        font-size: 10px;
        margin-left: 10px;
        margin-bottom: 5px;
        line-height: 20px;
        height: 30px;
    }
    .rwpSpinner{
        width: auto;
    }
    #sjhm ~.rwpCombobox {
        display: none;
    }

    #sbj_hb ~.rwpCombobox {
        display: none;
    }

    .cur {
        box-shadow: 0px 4px 10px black;
    }



</style>


<div class="fAutoPage">
    <form action="${ctx}/jxSqjController/JxaddCsszInfo1" id="ajaxform"
          method="post">
        <fieldset>

            <div class="rwpTab">
                <div class="rwpTabNavList">
					<span class="rwpTabNavItem rwpTabCurrent" name="wait" id="_xt"><a
                            href="javascript:void(0);"><i class="i i1"></i>系统参数</a></span> <span
                        class="rwpTabNavItem" name="pass" id="_yw"><a
                        href="javascript:void(0);"><i class="i i2"></i>业务参数</a></span> <span
                        class="rwpTabNavItem" name="pass" id="_bl"><a
                        href="javascript:void(0);"><i class="i i2"></i>办理须知</a></span>  <span
                        class="rwpTabNavItem" name="pass" id="_dx"><a
                        href="javascript:void(0);"><i class="i i2"></i>短信参数</a></span><span
                        class="rwpTabNavItem" name="pass" id="_rk"><a
                        href="javascript:void(0);"><i class="i i2"></i>人口参数</a></span> <span
                        class="rwpTabNavItem" name="all" id="_zw"><a
                        href="javascript:void(0);"><i class="i i2"></i>指纹参数</a></span> <span
                        class="rwpTabNavItem" name="all" id="_rx"><a
                        href="javascript:void(0);"><i class="i i2"></i>人像对比</a></span> <span
                        class="rwpTabNavItem" name="all" id="_zp"><a
                        href="javascript:void(0);"><i class="i i2"></i>照片参数</a></span> <span
                        class="rwpTabNavItem" name="all" id="_yj"><a
                        href="javascript:void(0);"><i class="i i2"></i>硬件参数</a></span>
                </div>
                <!--rwpTabNavList结束-->
            </div>

            <legend>
                <i class="i"></i>
            </legend>
            <div class="rwpTabContent">
                <div class="formpanel">

                    <div class="clear">
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="jqm">当前机器码</label></li>
                                <li class="editor-field"><input id="jqm" name="jqm"
                                                                type="text" value="${sqjxx.JQM }" readonly="readonly" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="jqmc">申请机名称</label></li>
                                <li class="editor-field"><input id="jqmc" name="jqmc"
                                                                type="text" value="${sqjxx.JQMC }" readonly="readonly" /></li>
                            </ul>
                            <input type="hidden" name="bh" value="${params.bh}">
                        </div>
                    </div>
                    <!--这里是系统参数  -->
                    <div class="clear _xt parmdiv">
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_bt">系统标题</label></li>
                                <li class="editor-field"><input id="xt_bt" name="xt_bt"
                                                                type="text" value="${params.xt_bt }" /></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_lk">系统落款</label></li>
                                <li class="editor-field"><input id="xt_lk" name="xt_lk"
                                                                type="text" value="${params.xt_lk }" /></li>
                            </ul>
                        </div>


                    </div>


                    <!--业务参数  -->
                    <div class="clear _yw parmdiv">
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label">系统受理范围
                                </li>
                                <li class="editor-field"><select name="xt_slfw">
                                    <option value="1"
                                            <c:if test="${params.xt_slfw=='1'}">selected</c:if>>本派出所</option>
                                    <option value="2"
                                            <c:if test="${params.xt_slfw=='2'}">selected</c:if>>本区/县</option>
                                    <option value="3"
                                            <c:if test="${params.xt_slfw=='3'}">selected</c:if>>全市</option>
                                    <option value="4"
                                            <c:if test="${params.xt_slfw=='4'}">selected</c:if>>全省</option>
                                    <option value="5"
                                            <c:if test="${params.xt_slfw=='5'}">selected</c:if>>本派出所+全市居住本派出所</option>
                                    <option value="6"
                                            <c:if test="${params.xt_slfw=='6'}">selected</c:if>>本区/县+全市级居住本派出所</option>
                                    <option value="7"
                                            <c:if test="${params.xt_slfw=='7'}">selected</c:if>>本派出所+全市居住本区/县</option>
                                    <option value="8"
                                            <c:if test="${params.xt_slfw=='8'}">selected</c:if>>本区/县+全市居住本区/县</option>
                                </select></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_dqsj">身份证到期时间限制</label></li>
                                <li class="editor-field"><input id="xt_dqsj" name="xt_dqsj"
                                                                type="text" value="${params.xt_dqsj }" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zfdm">支付代码</label></li>
                                <li class="editor-field"><input id="zfdm" name="zfdm"
                                                                type="text" value="${params.zfdm }" /></li>
                            </ul>


                            <script type="text/javascript">
                                $(function() {
                                    //下面的代码是用来 引用手机限制的下拉选框
                                    $(document)
                                        .ready(
                                            function() {
                                                //目前所有号码的开头
                                                // 移动 134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 198
                                                // 联通 130 131 132 145 146 155 156 166 171 175 176 185 186
                                                // 电信 133 149 153 173 174 177 180 181 189 199
                                                var allPhone = [ "134",
                                                    "135", "136",
                                                    "137", "138",
                                                    "139", "147",
                                                    "148", "150",
                                                    "151", "152",
                                                    "157", "158",
                                                    "159", "172",
                                                    "183", "184",
                                                    "187", "188",
                                                    "198", "130",
                                                    "131", "132",
                                                    "145", "146",
                                                    "155", "156",
                                                    "166", "171",
                                                    "175", "176",
                                                    "185", "186",
                                                    "133", "149",
                                                    "153", "173",
                                                    "174", "177",
                                                    "180", "181",
                                                    "189", "199", "170" ];
                                                //从数据库取出的限制号码
                                                var checkSjhm = $(
                                                    "#checkSjhm").val();
                                                var selePhone = checkSjhm
                                                    .split(",");
                                                //var selePhone = ["136","189","187","188"];
                                                if (null != allPhone
                                                    && allPhone.length > 0) {
                                                    $("#sjhm").empty();
                                                    var sjhm = $("#sjhm");

                                                    for (var i = 0; i < allPhone.length; i++) {
                                                        // 添加选项
                                                        sjhm
                                                            .append("<option value='" + allPhone[i] + "'>"
                                                                + allPhone[i]
                                                                + "</option>");
                                                    }

                                                    if (null != selePhone
                                                        && selePhone.length > 0) {
                                                        for (var j = 0; j < selePhone.length; j++) {
                                                            // 设置选中项
                                                            $(
                                                                "#sjhm  option[value='"
                                                                + selePhone[j]
                                                                + "'] ")
                                                                .attr(
                                                                    "selected",
                                                                    true);
                                                        }
                                                    }
                                                }

                                                //	$('input[name=xt_dqsj]').rwpUIDateinput();
                                                $("#sjhm")
                                                    .multiselect(
                                                        {
                                                            noneSelectedText : "==请选择==",
                                                            checkAllText : "全选",
                                                            uncheckAllText : '全不选',
                                                            selectedList : 4
                                                        });
                                            });
                                });
                            </script>
                            <dl class="DialabelWidth">
                                <dt class="editor-label">
                                    <label for="sjhm">手机号码限制号码段</label>
                                </dt>
                                <dd class="editor-field">
                                    <input type="hidden" value="${params.sjhm }" id="checkSjhm" />
                                    <select id="sjhm" multiple="multiple" style="display:none;"
                                            title="Basic example" multiple="multiple" name="sjhm" size="5">

                                    </select>
                                </dd>
                            </dl>
                        </div>
                        <div class="clear">


                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="jg_dsbl">丢失补领业务价格</label></li>
                                <li class="editor-field"><input id="jg_dsbl" name="jg_dsbl"
                                                                type="number" value="${params.jg_dsbl }"  /></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="jg_dqhl">到期换领业务价格</label></li>
                                <li class="editor-field"><input id="jg_dqhl" name="jg_dqhl"
                                                                type="number" value="${params.jg_dqhl}" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="jg_shhl">损坏换领业务价格</label></li>
                                <li class="editor-field"><input id="jg_shhl" name="jg_shhl"
                                                                type="number" value="${params.jg_shhl}" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="jg_xmbg">项目变更业务价格</label></li>
                                <li class="editor-field"><input id="jg_xmbg" name="jg_xmbg"
                                                                type="number" value="${params.jg_xmbg}" /></li>
                            </ul>
                        </div>

                        <script type="text/javascript">
                            $(function() {
                                /*
                                下面的代码是用来控制 开通业务时 对应的受理时间不能填
                                 */
                                var dsblflag = $("#xt_dsbl").prev().children(
                                    ".rwpToggleThumb").html() == "否" ? true
                                    : false;
                                var dqhlflag = $("#xt_dqhl").prev().children(
                                    ".rwpToggleThumb").html() == "否" ? true
                                    : false;
                                var shhlflag = $("#xt_shhl").prev().children(
                                    ".rwpToggleThumb").html() == "否" ? true
                                    : false;
                                var xmbgflag = $("#xt_xmbg").prev().children(
                                    ".rwpToggleThumb").html() == "否" ? true
                                    : false;
                                if (dsblflag) {
                                    $(".dsbl_slsj").attr("readonly", "readonly");
                                    $(".dsbl_slsj").css("background", "#eee");
                                }
                                if (dqhlflag) {
                                    $(".dqhl_slsj").attr("readonly", "readonly");
                                    $(".dqhl_slsj").css("background", "#eee");
                                }
                                if (shhlflag) {
                                    $(".shhl_slsj").attr("readonly", "readonly");
                                    $(".shhl_slsj").css("background", "#eee");
                                }
                                if (xmbgflag) {
                                    $(".xmbg_slsj").attr("readonly", "readonly");
                                    $(".xmbg_slsj").css("background", "#eee");
                                }
                                $("#xt_dsbl").prev().click(
                                    function() {
                                        dsblflag = $("#xt_dsbl").prev()
                                            .children(".rwpToggleThumb")
                                            .html() == "否" ? true : false;
                                        if (dsblflag) {
                                            $(".dsbl_slsj").attr("readonly",
                                                "readonly");
                                            $(".dsbl_slsj").css("background",
                                                "#eee");
                                        } else {
                                            $(".dsbl_slsj").removeAttr(
                                                "readonly");
                                            $(".dsbl_slsj").css("background",
                                                "#fff");
                                        }
                                    });
                                $("#xt_dqhl").prev().click(
                                    function() {
                                        dsblflag = $("#xt_dqhl").prev()
                                            .children(".rwpToggleThumb")
                                            .html() == "否" ? true : false;
                                        if (dsblflag) {
                                            $(".dqhl_slsj").attr("readonly",
                                                "readonly");
                                            $(".dqhl_slsj").css("background",
                                                "#eee");
                                        } else {
                                            $(".dqhl_slsj").removeAttr(
                                                "readonly");
                                            $(".dqhl_slsj").css("background",
                                                "#fff");
                                        }
                                    });
                                $("#xt_shhl").prev().click(
                                    function() {
                                        dsblflag = $("#xt_shhl").prev()
                                            .children(".rwpToggleThumb")
                                            .html() == "否" ? true : false;
                                        if (dsblflag) {
                                            $(".shhl_slsj").attr("readonly",
                                                "readonly");
                                            $(".shhl_slsj").css("background",
                                                "#eee");
                                        } else {
                                            $(".shhl_slsj").removeAttr(
                                                "readonly");
                                            $(".shhl_slsj").css("background",
                                                "#fff");
                                        }
                                    });
                                $("#xt_xmbg").prev().click(
                                    function() {
                                        dsblflag = $("#xt_xmbg").prev()
                                            .children(".rwpToggleThumb")
                                            .html() == "否" ? true : false;
                                        if (dsblflag) {
                                            $(".xmbg_slsj").attr("readonly",
                                                "readonly");
                                            $(".xmbg_slsj").css("background",
                                                "#eee");
                                        } else {
                                            $(".xmbg_slsj").removeAttr(
                                                "readonly");
                                            $(".xmbg_slsj").css("background",
                                                "#fff");
                                        }
                                    });

                                //验证受理时间的输入格式
                                /* $(".slsjcheck")
                                        .blur(
                                                function() {
                                                    var $this = $(this);
                                                    var reg = /^[0-9]{4}\,[0-9]{4}\|[0-9]{4}\,[0-9]{4}/;
                                                    var slsj = $this.val();

                                                    if (reg.test(slsj)) {
                                                        $this.parents("li").prev()
                                                                .html("");
                                                    } else {
                                                        $this
                                                                .parents("li")
                                                                .prev()
                                                                .html(
                                                                        "受理时间的格式应该为：如0800,1200|1400,1800（早上8点到中午12点，下午2点到晚上6点）");
                                                    }
                                                }); */

                                $('.dsbl_slsj').rwpUISpinner({ type: 'int', minValue: 0, maxValue: 60, defaultValueText: '' });
                                $('.dqhl_slsj').rwpUISpinner({ type: 'int', minValue: 0, maxValue: 60, defaultValueText:  ''});
                                $('.shhl_slsj').rwpUISpinner({ type: 'int', minValue: 0, maxValue: 60, defaultValueText: '' });
                                $('.xmbg_slsj').rwpUISpinner({ type: 'int', minValue: 0, maxValue: 60, defaultValueText: '' });
                            });


                        </script>

                        <div class="clear">

                            <ul class="DialabelWidth" style="width: 850px;">
                                <li class="editor-label">丢失补领业务受理时间(24小时制)</li>
                                <li style="color: red;"></li>
                                <li class="editor-field">
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">上午:</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="dsbl_slsj"
                                                   name="dsblmhb" type="text" value="${timeHelper.dsblmhb}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="dsbl_slsj"
                                                                        name="dsblmmb" type="text" value="${timeHelper.dsblmmb}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分---</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="dsbl_slsj"
                                                   name="dsblmhe" type="text" value="${timeHelper.dsblmhe}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="dsbl_slsj"
                                                                        name="dsblmme" type="text" value="${timeHelper.dsblmme}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分,</li>
                                    </ul>
                                </li>
                                <li class="editor-field">
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">下午:</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="dsbl_slsj"
                                                   name="dsblahb" type="text" value="${timeHelper.dsblahb}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="dsbl_slsj"
                                                                        name="dsblamb" type="text" value="${timeHelper.dsblamb}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分---</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="dsbl_slsj"
                                                   name="dsblahe" type="text" value="${timeHelper.dsblahe}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="dsbl_slsj"
                                                                        name="dsblame" type="text" value="${timeHelper.dsblame}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分</li>
                                    </ul>
                                </li>

                            </ul>

                            <ul class="DialabelWidth" style="width: 850px;">
                                <li class="editor-label">到期换领业务受理时间(24小时制)</li>
                                <li style="color: red;"></li>
                                <li class="editor-field">
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">上午:</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="dqhl_slsj"
                                                   name="dqhlmhb" type="text" value="${timeHelper.dqhlmhb}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="dqhl_slsj"
                                                                        name="dqhlmmb" type="text" value="${timeHelper.dqhlmmb}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分---</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="dqhl_slsj"
                                                   name="dqhlmhe" type="text" value="${timeHelper.dqhlmhe}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="dqhl_slsj"
                                                                        name="dqhlmme" type="text" value="${timeHelper.dqhlmme}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分,</li>
                                    </ul>
                                </li>
                                <li class="editor-field">
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">下午:</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="dqhl_slsj"
                                                   name="dqhlahb" type="text" value="${timeHelper.dqhlahb}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="dqhl_slsj"
                                                                        name="dqhlamb" type="text" value="${timeHelper.dqhlamb}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分---</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="dqhl_slsj"
                                                   name="dqhlahe" type="text" value="${timeHelper.dqhlahe}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="dqhl_slsj"
                                                                        name="dqhlame" type="text" value="${timeHelper.dqhlame}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分</li>
                                    </ul>
                                </li>

                            </ul>

                            <ul class="DialabelWidth" style="width: 850px;">
                                <li class="editor-label">损坏换领业务受理时间(24小时制)</li>
                                <li style="color: red;"></li>
                                <li class="editor-field">
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">上午:</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="shhl_slsj"
                                                   name="shhlmhb" type="text" value="${timeHelper.shhlmhb}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="shhl_slsj"
                                                                        name="shhlmmb" type="text" value="${timeHelper.shhlmmb}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分---</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="shhl_slsj"
                                                   name="shhlmhe" type="text" value="${timeHelper.shhlmhe}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="shhl_slsj"
                                                                        name="shhlmme" type="text" value="${timeHelper.shhlmme}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分,</li>
                                    </ul>
                                </li>
                                <li class="editor-field">
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">下午:</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="shhl_slsj"
                                                   name="shhlahb" type="text" value="${timeHelper.shhlahb}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="shhl_slsj"
                                                                        name="shhlamb" type="text" value="${timeHelper.shhlamb}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分---</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="shhl_slsj"
                                                   name="shhlahe" type="text" value="${timeHelper.shhlahe}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="shhl_slsj"
                                                                        name="shhlame" type="text" value="${timeHelper.shhlame}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分</li>
                                    </ul>
                                </li>

                            </ul>


                            <ul class="DialabelWidth" style="width: 850px;">
                                <li class="editor-label">项目变更业务受理时间(24小时制)</li>
                                <li style="color: red;"></li>
                                <li class="editor-field">
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">上午:</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="xmbg_slsj"
                                                   name="xmbgmhb" type="text" value="${timeHelper.xmbgmhb}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="xmbg_slsj"
                                                                        name="xmbgmmb" type="text" value="${timeHelper.xmbgmmb}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分---</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="xmbg_slsj"
                                                   name="xmbgmhe" type="text" value="${timeHelper.xmbgmhe}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="xmbg_slsj"
                                                                        name="xmbgmme" type="text" value="${timeHelper.xmbgmme}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分,</li>
                                    </ul>
                                </li>
                                <li class="editor-field">
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">下午:</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="xmbg_slsj"
                                                   name="xmbgahb" type="text" value="${timeHelper.xmbgahb}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="xmbg_slsj"
                                                                        name="xmbgamb" type="text" value="${timeHelper.xmbgamb}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分---</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">
                                            <input class="xmbg_slsj"
                                                   name="xmbgahe" type="text" value="${timeHelper.xmbgahe}"
                                                   style="width: 30px; " />
                                        </li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">时</li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field"><input class="xmbg_slsj"
                                                                        name="xmbgame" type="text" value="${timeHelper.xmbgame}"
                                                                        style="width: 30px; " /></li>
                                    </ul>
                                    <ul class="DialabelWidth" style="width: auto">
                                        <li class="editor-field">分</li>
                                    </ul>
                                </li>

                            </ul>
                        </div>
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="dy_lzsj">回执单中领证时间</label></li>
                                <li class="editor-field"><input id="dy_lzsj" name="dy_lzsj"
                                                                type="text" value="${params.dy_lzsj}" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zp_lsh_cd">照片回执单上的业务流水号长度</label></li>
                                <li class="editor-field"><input id="zp_lsh_cd" name="zp_lsh_cd"
                                                                type="text" value="${params.zp_lsh_cd}" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_dsbl">是否开通丢失补领业务</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_dsbl" name="xt_dsbl"
                                        <c:if test="${params.xt_dsbl=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_dsbl" type="hidden"
                                                                                                   value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_dsbl"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_dqhl">是否开通到期换领</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_dqhl" name="xt_dqhl"
                                        <c:if test="${params.xt_dqhl=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_dqhl" type="hidden"
                                                                                                   value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_dqhl"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_shhl">是否开通损坏换领</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_shhl" name="xt_shhl"
                                        <c:if test="${params.xt_shhl=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_shhl" type="hidden"
                                                                                                   value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_shhl"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_xmbg">是否开通项目变更换领</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_xmbg" name="xt_xmbg"
                                        <c:if test="${params.xt_xmbg=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_xmbg" type="hidden"
                                                                                                   value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_xmbg"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_slsj_zdtj">受理数据是否自动提交</label>
                                </li>
                                <li class="editor-field"><input data-val="0"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_slsj_zdtj"
                                                                name="xt_slsj_zdtj"
                                        <c:if test="${params.xt_slsj_zdtj=='1'  }">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_slsj_zdtj"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_slsj_zdtj"
                                        data-valmsg-replace="0"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_zf_zfb">是否开通支付宝支付</label>
                                </li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_zf_zfb" name="xt_zf_zfb"
                                        <c:if test="${params.xt_zf_zfb=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_zf_zfb"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_zf_zfb"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_zf_wx">是否开通微信支付</label>
                                </li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_zf_wx" name="xt_zf_wx"
                                        <c:if test="${params.xt_zf_wx=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_zf_wx"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_zf_wx"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_zf_sbj">是否开通收币机支付</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_zf_sbj" name="xt_zf_sbj"
                                        <c:if test="${params.xt_zf_sbj=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_zf_sbj"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_zf_sbj"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_zf_ylk">是否开通银联卡支付</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_zf_ylk" name="xt_zf_ylk"
                                        <c:if test="${params.xt_zf_ylk=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_zf_ylk"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_zf_ylk"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_yj">是否开通邮寄</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="xt_yj" name="xt_yj"
                                        <c:if test="${params.xt_yj=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="xt_yj" type="hidden"
                                                                                                   value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="xt_yj"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <!--  问题点-->
                            <!-- last问题点 -->
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="jk_sflx">监控是否开通实时录像</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="jk_sflx" name="jk_sflx"
                                        <c:if test="${params.jk_sflx=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="jk_sflx" type="hidden"
                                                                                                   value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="jk_sflx"
                                        data-valmsg-replace="true"></span></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="pzj_dyzp">拍照机是否打印照片</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="pzj_dyzp" name="pzj_dyzp"
                                        <c:if test="${params.pzj_dyzp=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="pzj_dyzp"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="pzj_dyzp"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="sfzhsr_tsy">身份证号码输入是否开启提示音</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="sfzhsr_tsy" name="sfzhsr_tsy"
                                        <c:if test="${params.sfzhsr_tsy=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="sfzhsr_tsy"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="sfzhsr_tsy"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                        </div>

                    </div>

                    <!--办理须知  -->
                    <div class="clear _bl parmdiv">
                        <div class="clear">
                            丢失补领办理须知内容
                            <textarea data-val="true" id="xz_dsbl" name="xz_dsbl" rows="2"  class="xznr" style=" width: 800px; height: 320px;">${params.xz_dsbl}</textarea>
                        </div>
                        <div class="clear">
                            到期换领办理须知内容<textarea data-val="true"
                                                data-val-length="注意事项不能超过800字符" data-val-length-max="800"
                                                id="xz_dqhl" name="xz_dqhl" rows="2"
                                                style=" width: 800px; height: 320px;">${params.xz_dqhl}</textarea>
                        </div>
                        <div class="clear">
                            损坏换领办理须知内容
                            <textarea data-val="true"
                                      data-val-length="注意事项不能超过800字符" data-val-length-max="800"
                                      id="xz_shhl" name="xz_shhl" rows="2"
                                      style=" width: 800px; height: 320px;">${params.xz_shhl}</textarea>
                        </div>
                        <div class="clear">
                            项目变更办理须知内容
                            <textarea data-val="true"
                                      data-val-length="注意事项不能超过800字符" data-val-length-max="800"
                                      id="xz_xmbg" name="xz_xmbg" rows="2"
                                      style=" width: 800px; height: 320px;">${params.xz_xmbg}</textarea>
                        </div>


                        <div class="clear">
                            回执单提示内容和温馨提示
                            <div class="clear">
						<textarea data-val="true"
                                  id="hzd_tsnr" name="hzd_tsnr" rows="2"
                                  style=" width: 800px; height: 320px;">${params.hzd_tsnr}</textarea></div>
                        </div>


                    </div>
                    <div class="clear _dx parmdiv">
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="dxyh">短信平台账号</label></li>
                                <li class="editor-field"><input id="dxyh" name="dxyh"
                                                                type="text" value="${params.dxyh}" /></li>
                            </ul><ul class="DialabelWidth">
                            <li class="editor-label"><label for="dxmm">短信平台密码</label></li>
                            <li class="editor-field"><input id="dxmm" name="dxmm"
                                                            type="text" value="${params.dxmm}" /></li>
                        </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="dxtx">是否开通短信提醒</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="dxtx" name="dxtx"
                                        <c:if test="${params.dxtx=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="dxtx"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="dxtx"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                        </div>
                        <div class="clear">
                            短信模板
                        </div>
                        <div class="clear">
						<textarea data-val="true"
                                  id="dxmb" name="dxmb" rows="2"
                                  style=" width: 800px; height: 320px;">${params.dxmb}</textarea></div>

                    </div>
                    <script type="text/javascript">
                        var ue = UE.getEditor('xz_dsbl', {
                            toolbars : [ [ 'undo', 'fontfamily', 'fontsize',
                                'bold', 'italic', 'forecolor', //字体颜色
                                'backcolor', //背景色
                                'superscript', //上标
                                'subscript', //下标
                                'touppercase', //字母大写
                                'tolowercase', //字母小写
                                'justifyleft', //居左对齐
                                'justifyright', //居右对齐
                                'justifycenter', //居中对齐
                                'justifyjustify' ] //两端对齐
                            ],
                            initialFrameWidth : 800,//设置编辑器宽度
                            initialFrameHeight : 320//设置编辑器高度

                            //autoHeightEnabled :true
                        });
                        var ue = UE.getEditor('xz_dqhl', {
                            toolbars : [ [ 'undo', 'fontfamily', 'fontsize',
                                'bold', 'italic', 'forecolor', //字体颜色
                                'backcolor', //背景色
                                'superscript', //上标
                                'subscript', //下标
                                'touppercase', //字母大写
                                'tolowercase', //字母小写
                                'justifyleft', //居左对齐
                                'justifyright', //居右对齐
                                'justifycenter', //居中对齐
                                'justifyjustify' ] //两端对齐
                            ],

                            initialFrameWidth : 800,//设置编辑器宽度
                            initialFrameHeight : 320//设置编辑器高度
                        });
                        var ue = UE.getEditor('xz_shhl', {
                            toolbars : [ [ 'undo', 'fontfamily', 'fontsize',
                                'bold', 'italic', 'forecolor', //字体颜色
                                'backcolor', //背景色
                                'superscript', //上标
                                'subscript', //下标
                                'touppercase', //字母大写
                                'tolowercase', //字母小写
                                'justifyleft', //居左对齐
                                'justifyright', //居右对齐
                                'justifycenter', //居中对齐
                                'justifyjustify' ] //两端对齐
                            ],
                            initialFrameWidth : 800,//设置编辑器宽度
                            initialFrameHeight : 320//设置编辑器高度
                        });
                        var ue = UE.getEditor('xz_xmbg', {
                            toolbars : [ [ 'undo', 'fontfamily', 'fontsize',
                                'bold', 'italic', 'forecolor', //字体颜色
                                'backcolor', //背景色
                                'superscript', //上标
                                'subscript', //下标
                                'touppercase', //字母大写
                                'tolowercase', //字母小写
                                'justifyleft', //居左对齐
                                'justifyright', //居右对齐
                                'justifycenter', //居中对齐
                                'justifyjustify' ] //两端对齐
                            ],

                            initialFrameWidth : 800,//设置编辑器宽度
                            initialFrameHeight : 320//设置编辑器高度
                        });
                        /* var ue = UE.getEditor('hzd_tsnr', {
                            toolbars : [ [ 'undo', 'fontfamily', 'fontsize',
                                    'bold', 'italic', 'forecolor', //字体颜色
                                    'backcolor', //背景色
                                    'superscript', //上标
                                    'subscript', //下标
                                    'touppercase', //字母大写
                                    'tolowercase', //字母小写
                                    'justifyleft', //居左对齐
                                    'justifyright', //居右对齐
                                    'justifycenter', //居中对齐
                                    'justifyjustify' ] //两端对齐
                            ],
                            initialFrameWidth : null,//设置编辑器宽度
                            initialFrameHeight : null,//设置编辑器高度
                            autoHeightEnabled : true
                        }); */
                    </script>

                    <!--人像对比  -->
                    <div class="clear _rk parmdiv">
                        <div class="clear">
                            <ul class="DialabelWidth">

                                <li class="editor-label"><label for="rk_dz">人口接口地址</label></li>
                                <li class="editor-field"><input id="rk_dz" name="rk_dz"
                                                                type="text" value='${params.rk_dz }' /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="rk_sq">人口接口LIC</label>
                                </li>
                                <li class="editor-field"><input id="rk_sq" name="rk_sq"
                                                                type="text" value='${params.rk_sq }' /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="rk_xtyh">人口接口系统用户</label>
                                </li>
                                <li class="editor-field"><input id="rk_xtyh" name="rk_xtyh"
                                                                type="text" value="${params.rk_xtyh }" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zxsb_zch">照相设备注册号</label></li>
                                <li class="editor-field"><input id="zxsb_zch" name="zxsb_zch"
                                                                type="text" value="${params.zxsb_zch }"  /></li>
                            </ul>
                        </div>
                    </div>

                    <!-- 指纹参数 -->
                    <div class="clear _zw parmdiv">
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_dz">指纹接口地址</label></li>
                                <li class="editor-field"><input id="zw_dz" name="zw_dz"
                                                                type="text" value="${params.zw_dz }" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_hyzl">指纹核验质量（1-100）</label>
                                </li>
                                <li class="editor-field"><input id="zw_hyzl" name="zw_hyzl"
                                                                type="text" value="${params.zw_hyzl  }" maxlength="3" /></li>
                            </ul>


                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_hyxsd">指纹核验相似度（0.01-1）</label></li>
                                <li class="editor-field"><input id="zw_hyxsd"
                                                                name="zw_hyxsd" type="text" value="${params.zw_hyxsd }"
                                                                maxlength="4" /></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_hycs">一枚指纹核验失败次数</label></li>
                                <li class="editor-field"><input id="zw_hycs" name="zw_hycs"
                                                                type="text" value="${params.zw_hycs}" maxlength="5" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_cjzl">指纹采集质量（1-100）</label>
                                </li>
                                <li class="editor-field"><input id="zw_cjzl" name="zw_cjzl"
                                                                type="text" value="${params.zw_cjzl}" maxlength="3" /></li>
                            </ul>


                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_cjxsd">指纹采集相似度（0.01-1）</label></li>
                                <li class="editor-field"><input id="zw_cjxsd"
                                                                name="zw_cjxsd" type="text" value="${params.zw_cjxsd }"
                                                                maxlength="4" /></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_cjcs">一枚指纹采集失败次数</label></li>
                                <li class="editor-field"><input id="zw_cjcs" name="zw_cjcs"
                                                                type="text" value="${params.zw_cjcs}" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label">开通双指纹或单指纹核验
                                </li>
                                <li class="editor-field"><select name="zw_hyszs">
                                    <option value="1"
                                            <c:if test="${params.zw_hyszs=='1'}">selected</c:if>>单手核验</option>
                                    <option value="2"
                                            <c:if test="${params.zw_hyszs=='2'}">selected</c:if>>双手核验</option>
                                </select></li>
                            </ul>
                        </div>
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_zscjq">是否启用左手指纹采集器</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="zw_zscjq" name="zw_zscjq"
                                        <c:if test="${params.zw_zscjq=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="zw_zscjq"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="zw_zscjq"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zw_yscjq">是否启用右手指纹采集器</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="zw_yscjq" name="zw_yscjq"
                                        <c:if test="${params.zw_yscjq=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="zw_yscjq"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="zw_yscjq"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="rk_wzwsl">是否开通无指纹受理业务</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="rk_wzwsl" name="rk_wzwsl"
                                        <c:if test="${params.rk_wzwsl=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="rk_wzwsl"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="rk_wzwsl"
                                        data-valmsg-replace="true"></span></li>
                            </ul>

                        </div>

                    </div>
                    <!--人像对比  -->
                    <div class="clear _rx parmdiv">
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="rxbd_zjz_fz">制证人像阀值</label></li>
                                <li class="editor-field"><input id="rxbd_zjz_fz"
                                                                name="rxbd_zjz_fz" type="text" value="${params.rxbd_zjz_fz }" />
                                </li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="rxbd_xcz_fz">现场人像阀值</label></li>
                                <li class="editor-field"><input id="rxbd_xcz_fz"
                                                                name="rxbd_xcz_fz" type="text" value="${params.rxbd_xcz_fz }" />
                                </li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="rxbd_zjz">制证人像比对开通</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="rxbd_zjz" name="rxbd_zjz"
                                        <c:if test="${params.rxbd_zjz=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="rxbd_zjz"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="rxbd_zjz"
                                        data-valmsg-replace="true"></span></li>
                            </ul>


                            <!-- 标记  就是你  -->
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="rxbd_xcz">现场人像比对开通</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="rxbd_xcz" name="rxbd_xcz"
                                        <c:if test="${params.rxbd_xcz==1}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /> <input name="rxbd_xcz"
                                                                                                    type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="rxbd_xcz"
                                        data-valmsg-replace="true"></span></li>
                            </ul>
                            <!-- 标记  就是你  -->

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="rxbd_fz_xs">人像比对阈值是否显示</label></li>
                                <li class="editor-field"><input data-val="true"
                                                                data-val-required="启用状态 字段是必需的。" id="rxbd_fz_xs"
                                                                name="rxbd_fz_xs"
                                        <c:if test="${params.rxbd_fz_xs=='1'}">
                                            checked=checked
                                        </c:if>
                                                                type="checkbox" value="1" /><input name="rxbd_fz_xs"
                                                                                                   type="hidden" value="0" /></li>
                                <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="zw_yscjq"
                                        data-valmsg-replace="true"></span></li>
                            </ul>



                        </div>
                    </div>
                    <!-- 照片参数 -->
                    <div class="clear _zp parmdiv">
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zp_hqfs">制证照片获取方式</label>
                                </li>
                                <li class="editor-field"><select name="zp_hqfs" id="zp_hqfs">
                                    <option value="1"
                                            <c:if test="${params.zp_hqfs=='1'}">selected</c:if>>接口</option>
                                    <option value="2"
                                            <c:if test="${params.zp_hqfs=='2'}">selected</c:if>>物理地址</option>
                                    <option value="3"
                                            <c:if test="${params.zp_hqfs=='3'}">selected</c:if>>ftp</option>
                                </select></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label">制证照片获取关键字
                                </li>
                                <li class="editor-field"><select name="zp_hqzj">
                                    <option value="1"
                                            <c:if test="${params.zp_hqzj=='1'}">selected</c:if>>身份证号</option>
                                    <option value="2"
                                            <c:if test="${params.zp_hqzj=='2'}">selected</c:if>>流水号</option>
                                </select></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="zp_hqdz">制证照片获取地址</label>
                                </li>
                                <li class="editor-field"><input id="zp_hqdz" name="zp_hqdz"
                                                                type="text" value="${params.zp_hqdz }" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="pzj_dymb">拍照机所使用的打印模板</label></li>
                                <li class="editor-field"><input id="pzj_dymb"
                                                                name="pzj_dymb" type="text" value="${params.pzj_dymb }"
                                                                maxlength="4" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="pzj_dyjmc">拍照机所使用的打印机名称</label></li>
                                <li class="editor-field"><input id="pzj_dyjmc"
                                                                name="pzj_dyjmc" type="text" value="${params.pzj_dyjmc }"
                                                                maxlength="4" /></li>
                            </ul>

                            <!--跳转  -->
                        </div>
                    </div>

                    <!--硬件参数  -->
                    <div class="clear _yj parmdiv">
                        <div class="clear">

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="ydq_dkh">阅读器端口号</label></li>
                                <li class="editor-field"><input id="ydq_dkh" name="ydq_dkh"
                                                                type="text" value="${params.ydq_dkh }" maxlength="4" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="sbj_dkh">收币机端口号</label></li>
                                <li class="editor-field"><input id="sbj_dkh" name="sbj_dkh"
                                                                type="text" value="${params.sbj_dkh }" maxlength="4" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label">指纹采集器品牌
                                </li>
                                <li class="editor-field"><select name="zwcjq">
                                    <option value="1"
                                            <c:if test="${params.zwcjq=='1'}">selected</c:if>>科泰华</option>
                                    <option value="2"
                                            <c:if test="${params.zwcjq=='2'}">selected</c:if>>鸿达</option>
                                </select></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label">>键盘输入法
                                </li>
                                <li class="editor-field"><select name="jpsr">
                                    <option value="1"
                                            <c:if test="${params.jpsr=='1'}">selected</c:if>>科泰华</option>
                                    <option value="2"
                                            <c:if test="${params.jpsr=='2'}">selected</c:if>>myTouch</option>
                                </select></li>
                            </ul>
                            <script type="text/javascript">
                                $(function() {

                                    var allHb = [ "100", "50", "20", "10", "5",
                                        "2", "1" ];
                                    //从数据库取出的限制号码
                                    var checkHb = $("#checkHb").val();

                                    var selectHb = checkHb.split(",");
                                    //var selePhone = ["136","189","187","188"];
                                    if (null != allHb && allHb.length > 0) {
                                        $("#sbj_hb").empty();
                                        var hb = $("#sbj_hb");

                                        for (var i = 0; i < allHb.length; i++) {
                                            // 添加选项
                                            hb
                                                .append("<option value='" + allHb[i] + "'>"
                                                    + allHb[i]
                                                    + "元</option>");
                                        }

                                        if (null != selectHb && selectHb.length > 0) {
                                            for (var j = 0; j < selectHb.length; j++) {
                                                // 设置选中项
                                                $(
                                                    "#sbj_hb  option[value='"
                                                    + selectHb[j]
                                                    + "'] ").attr(
                                                    "selected", true);
                                            }
                                        }
                                    }
                                    $("#sbj_hb").multiselect({
                                        noneSelectedText : "==请选择==",
                                        checkAllText : "全选",
                                        uncheckAllText : '全不选',
                                        selectedList : 4
                                    });

                                });
                            </script>
                            <dl class="DialabelWidth">
                                <dt class="editor-label">
                                    <label for="sbj_hb">收币机货币</label>
                                </dt>
                                <dd class="editor-field">
                                    <input type="hidden" id="checkHb" value="${params.sbj_hb }" />
                                    <select id="sbj_hb" multiple="multiple" style="display:none;"
                                            title="Basic example" multiple="multiple" name="sbj_hb"
                                            size="5">

                                    </select>
                                </dd>
                            </dl>
                        </div>
                        <div class="clear">
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="sbj_zdje">收币机能识别最大金额</label></li>
                                <li class="editor-field"><input id="sbj_zdje"
                                                                name="sbj_zdje" type="text" value="${params.sbj_zdje }"
                                                                maxlength="6" /></li>
                            </ul>



                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="sxt_qj">全景摄像头索引号</label>
                                </li>
                                <li class="editor-field"><input id="sxt_qj" name="sxt_qj"
                                                                type="text" value="<c:if test="${params.sxt_qj!=-1 }">${params.sxt_qj }</c:if>" maxlength="4" /></li>




                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="sxt_qz">前置摄像头索引号</label>
                                </li>
                                <li class="editor-field"><input id="sxt_qz" name="sxt_qz"
                                                                type="text" value="<c:if test="${params.sxt_qz!=-1 }">${params.sxt_qz }</c:if>" maxlength="4" /></li>
                            </ul>
                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="sxt_zw1">左手指纹采集器摄像头索引号</label>
                                </li>
                                <li class="editor-field"><input id="sxt_zw1" name="sxt_zw1"
                                                                type="text" value="<c:if test="${params.sxt_zw1!=-1 }">${params.sxt_zw1 }</c:if>" maxlength="4" /></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="sxt_zw2">右手指纹采集器摄像头索引号</label>
                                </li>
                                <li class="editor-field"><input id="sxt_zw2" name="sxt_zw2"
                                                                type="text" value="<c:if test="${params.sxt_zw2!=-1 }">${params.sxt_zw2 }</c:if>" maxlength="4" /></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="xt_dymb">打印模板名称</label></li>
                                <li class="editor-field"><input id="xt_dymb" name="xt_dymb"
                                                                type="text" value="${params.xt_dymb }" /></li>
                            </ul>

                            <ul class="DialabelWidth">
                                <li class="editor-label"><label for="dyj_mc">打印机名称</label></li>
                                <li class="editor-field"><input id="dyj_mc" name="dyj_mc"
                                                                type="text" value="${params.dyj_mc }" /></li>
                            </ul>


                        </div>
                        <!--开关  -->
                        <div class="clear"></div>
                    </div>


                </div>
            </div>
        </fieldset>
        <div class="clear" style="margin-left: 420px;">

            <div class="fPptions" style="float: left; margin-top: 4px;" id="gosub">
                <a href="javascript:;" title="确定" class="btnLightBlue btn back"
                   style="width: 80px; height: 32px;font-weight: bolder;" id="rego"><i class="i"></i>确定</a>
            </div>
            <div class="fPptions" style="float: left; margin-top: 4px;" id="back">
                <a href="javascript:;" title="返回" class="btnLightBlue btn back"
                   style="width: 80px; height: 32px;font-weight: bolder;" id="reback"><i class="i"></i>返回</a>
            </div>
        </div>
    </form>
</div>
<style>
    .fAutoPage *{
        position: static !important;
    }
</style>
