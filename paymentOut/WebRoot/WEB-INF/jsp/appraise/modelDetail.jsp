<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<style>
    table {
        *border-collapse: collapse; /* IE7 and lower */
        border-spacing: 0;
        width: 100%;
        font-family: "Microsoft YaHei";
    }

    .bordered {
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

</style>
<script type="text/javascript">
    $(function () {
        $("#back").click(function () {
            rwpMenuHelper.rebackLastPage();
        });
        var $Isautoappraise = $("#isautoVal").val();
        var $isWarn = $("#isWarn").val();
        var $isWeWarn = $("#isWeWarn").val();
        if ($Isautoappraise == 'true') {
            $(".default_").show();
            $("#outtime").show();
        }
        if ($isWarn == 'true') {
            $(".warn_").show();
        }
        if ($isWeWarn == 'true') {
            $(".Wewarn_").show();
        }
        $("#apprform").rwpUIForm({
            isAjaxSubmit: true,
            inputWidth: 'auto'
        });
    });
</script>
<div class="fAutoPage">
    <form action="${ctx}/appraiseContrller/saveAppraDetail" id="apprform"
          method="post" enctype="multipart/form-data">
        <fieldset>
            <legend>
                <i class="i"></i>评价模板信息
            </legend>
            <input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
                   data-val-required="用户ID 字段是必需的。" id="modelId" name="modelId" type="hidden"
                   value="${model.id}"/>
            <div class="formpanel">
                <div class="clear">
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="modelname">模板名称</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="用户名不能超过20字符" data-val-length-max="20"
                                                        data-val-required="用户名不能为空" id="modelname" name="modelname"
                                                        type="text" value="${model.modelname}" readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="modelname"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <ul class="DialabelWidth" style="display: none;" id="outtime">
                        <li class="editor-label"><label for="outtime">评价超时时间</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="评价超时时间不能超过5字符" data-val-length-max="5"
                                                        data-val-required="请填写超时时间" id="outtime" name="outtime"
                                                        type="text" value="${model.outtime}" readonly="readonly"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="outtime"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <%--超时自动评价选择按钮--%>
                    <input type="hidden" value="${model.isautoappraise}" id="isautoVal">
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="isautoappraise">超时自动评价</label></li>
                        <li class="editor-field spe"><input data-val="true"
                                                            data-val-required="超时自动评价字段是必需的" id="isautoappraise"
                                                            name="isautoappraise"
                                <c:if test="${model.isautoappraise}">
                                    checked=checked
                                </c:if>type="checkbox" value="true" readonly="readonly" disabled="disabled"/><input name="isautoappraise" type="hidden" value="false" readonly="readonly" disabled="disabled"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="isautoappraise"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                </div>
                <div class="clear">
                    <%--是否启用短信预警--%>
                    <input type="hidden" value="${model.haswarn}" id="isWarn">
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="iswarning">窗口短信预警</label></li>
                        <li class="editor-field hasWarn"><input data-val="true"
                                                                data-val-required="短信预警字段是必需的" id="iswarning"
                                                                name="iswarning"
                                <c:if test="${model.haswarn}">
                                    checked=checked
                                </c:if>type="checkbox" value="true" readonly="readonly" disabled="disabled"/><input name="iswarning" type="hidden" value="false" readonly="readonly" disabled="disabled">
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="iswarning"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                    <input type="hidden" value="${model.wewarn}" id="isWeWarn">
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="isWewarning">微信短信预警</label></li>
                        <li class="editor-field hasWeWarn"><input data-val="true"
                                                                  data-val-required="短信预警字段是必需的" id="isWewarning"
                                                                  name="isWewarning"
                                <c:if test="${model.wewarn}">
                                    checked=checked
                                </c:if>type="checkbox" value="true" readonly="readonly" disabled="disabled"/><input name="isWewarning" type="hidden" value="false" readonly="readonly" disabled="disabled">
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="isWewarning"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                </div>
            </div>
        </fieldset>
        <fieldset>
            <legend>
                <i class="i"></i>窗口节点信息
            </legend>
            <div class="formpanel">
                <div class="clear" style="width: 600px">
                   <%-- <a href="javascript:void(0);" class="rwpButton Add" id="addAppra"><span class="rwpButtonIcon"></span><span
                            class="rwpButtonText">添加节点</span></a>--%>
                    <table class="bordered">
                        <thead>
                        <tr>
                            <th style="width: 60px;text-align: center;color: black">#</th>
                            <th style="width: 140px;color: black;text-align: center;">节点名称</th>
                            <th style="width: 80px;color: black;text-align: center;">分值</th>
                            <th style="width: 80px;color: black;text-align: center;">图标预览</th>
                            <th style="width: 80px;color: black;text-align: center;display: none;" class="default_">设为默认</th>
                            <th style="width: 80px;color: black;text-align: center;display: none;" class="warn_">设为预警</th>
                        </tr>
                        </thead>
                        <c:forEach items="${details}" var="detail" varStatus="stau">
                            <tr>
                                <td style="text-align: center;font-family: 'Microsoft YaHei';color: black">${stau.index+1} </td>
                                <td style="text-align: center;font-family: 'Microsoft YaHei';color: black">${detail.appraisename}</td>
                                <td style="text-align: center;font-family: 'Microsoft YaHei';color: black">${detail.appraisevalue}</td>
                                <td style="text-align: center;"><img src="${ctx}/appraiseContrller/showImg?id=${detail.id}"
                                         style="width: 45px;height: 45px;"/></td>
                                <td class="default_" style="display: none;"><div style="width: 60px;height: 30px;margin-left: 20px;"
                                    class="img_select <c:if test="${detail.isdefault==true}">showDefault</c:if>"></div>
                                </td>
                                <td class="warn_" style="display: none;width: 100px">
                                    <div style="width: 60px;height: 30px;margin-left: 20px;"
                                         class="warn <c:if test="${detail.iswarning==true}">showDefault</c:if>"></div>
                                    <input type="hidden" class="iswarn" name="iswarn" value="${detail.iswarning}">
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </fieldset>
        <%--微信节点信息--%>
        <%--微信节点管理--%>
        <style>
            .webordered {
                width: 650px;
                border: solid #ccc 1px;
                -moz-border-radius: 6px;
                -webkit-border-radius: 6px;
                border-radius: 6px;
                -webkit-box-shadow: 0 1px 1px #ccc;
                -moz-box-shadow: 0 1px 1px #ccc;
                box-shadow: 0 1px 1px #ccc;
            }

            .webordered tr:hover {
                background: #fbf8e9;
                -o-transition: all 0.1s ease-in-out;
                -webkit-transition: all 0.1s ease-in-out;
                -moz-transition: all 0.1s ease-in-out;
                -ms-transition: all 0.1s ease-in-out;
                transition: all 0.1s ease-in-out;
            }

            .webordered td, .webordered th {
                border-left: 1px solid #ccc;
                border-top: 1px solid #ccc;
                padding: 5px;
                text-align: left;
            }

            .webordered th {
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

            .webordered td:first-child, .webordered th:first-child {
                border-left: none;
            }

            .webordered th:first-child {
                -moz-border-radius: 6px 0 0 0;
                -webkit-border-radius: 6px 0 0 0;
                border-radius: 6px 0 0 0;
            }

            .webordered th:last-child {
                -moz-border-radius: 0 6px 0 0;
                -webkit-border-radius: 0 6px 0 0;
                border-radius: 0 6px 0 0;
            }

            .webordered th:only-child {
                -moz-border-radius: 6px 6px 0 0;
                -webkit-border-radius: 6px 6px 0 0;
                border-radius: 6px 6px 0 0;
            }

            .webordered tr:last-child td:first-child {
                -moz-border-radius: 0 0 0 6px;
                -webkit-border-radius: 0 0 0 6px;
                border-radius: 0 0 0 6px;
            }

            .webordered tr:last-child td:last-child {
                -moz-border-radius: 0 0 6px 0;
                -webkit-border-radius: 0 0 6px 0;
                border-radius: 0 0 6px 0;
            }
        </style>
        <fieldset>
            <legend>
                <i class="i"></i>微信节点管理
            </legend>
            <div class="formpanel">
                <div class="clear" style="width: 600px">
                    <table class="webordered">
                        <thead>
                        <tr>
                            <th style="width: 40px;text-align: center;color: black">#</th>
                            <th style="width: 100px;text-align: center;color: black">服务评价</th>
                            <th style="width: 80px;text-align: center;color: black">星级</th>
                            <th id="_Wewarn" class="Wewarn_" style="width: 60px;color: black;text-align: center;display: none;">设为预警</th>
                        </tr>
                        </thead>
                        <c:forEach items="${list}" var="detail" varStatus="stau">
                            <tr>
                                <td style="text-align: center;width: 40px;">${stau.index+1}</td>
                                <td style="text-align: center;width: 80px;">${detail.appraisename}</td>
                                <td style="width: 80px;text-align: center;">${detail.appraisevalue}</td>
                                <td class="Wewarn_" style="display: none;text-align: center;width: 60px;">
                                    <div style="width: 60px;height: 30px;margin-left: 80px;"
                                         class="Wewarn <c:if test="${detail.iswewarning==true}">showDefault</c:if>"></div>
                                    <input type="hidden" class="isWewarn" name="isWewarn" value="${detail.iswewarning}">
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </fieldset>
        <div class="clear" style="margin-left: 20px;">
            <div class="fPptions" style="float: left; margin-top: 4px;" id="back">
                <a href="javascript:;" title="返回" class="btnLightBlue btn back"
                   style="width: 50px; height: 32px;" id="reback"><i class="i"></i>返回</a>
            </div>
        </div>
    </form>
</div>