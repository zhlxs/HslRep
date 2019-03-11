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
        if ($Isautoappraise=='true') {
            $(".default_").show();
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
                </div>
            </div>
        </fieldset>

        <fieldset>
            <legend>
                <i class="i"></i>节点信息
            </legend>
            <div class="formpanel">
                <div class="clear" style="width: 600px">
                    <table class="bordered">
                        <thead>
                        <tr>
                            <th style="width: 60px;text-align: center;color: black">#</th>
                            <th style="width: 140px;color: black;text-align: center;">节点名称</th>
                            <%--<th style="width: 80px;color: black;text-align: center;">分值</th>--%>
                            <%--<th style="width: 80px;color: black;text-align: center;">图标预览</th>--%>
                            <%--<th style="width: 80px;color: black;text-align: center;display: none;" class="default_">设为默认</th>--%>
                        </tr>
                        </thead>
                        <c:forEach items="${details}" var="detail" varStatus="stau">
                            <tr>
                                <td style="text-align: center;font-family: 'Microsoft YaHei';color: black">${stau.index+1}</td>
                                <td style="text-align: center;font-family: 'Microsoft YaHei';color: black">${detail.appraisename}</td>
                                <%--<td style="text-align: center;font-family: 'Microsoft YaHei';color: black">${detail.appraisevalue}</td>--%>
                                <%--<td style="text-align: center;"><img src="${ctx}/appraiseContrller/showImg?id=${detail.id}" style="width: 45px;height: 45px;"/>--%>
                                <%--</td>--%>
                                <%--<td class="default_" style="display: none;"><div style="width: 60px;height: 30px;margin-left: 20px;"--%>
                                <%--class="img_select <c:if test="${detail.isdefault==true}">showDefault</c:if>"></div>--%>
                                <%--</td>--%>
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