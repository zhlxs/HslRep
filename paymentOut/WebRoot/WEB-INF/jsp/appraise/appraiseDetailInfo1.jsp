<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<script src="${ctx}/js/jquery-form.js"></script>
<%--<script src="${ctx}/js/ajaxfileupload.js"></script>--%>
<%--<script src="${ctx}/js/lrz.bundle.js"></script>--%>
<style>
    table {
        *border-collapse: collapse; /* IE7 and lower */
        border-spacing: 0;
        /* width: 750px; */
        font-family: "Microsoft YaHei";
    }

    .bordered {
        width: 680px;
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

</style>
<script type="text/javascript">
    $(function () {
        $("#apprform").rwpUIForm({
            isAjaxSubmit: true,
            ajaxSubmitAttrs: {
                // isDialog : true
            }
        });
        $('.bordered').on("click", ".del", function () {
            var ths = $(this);
            var $id = ths.prev().val();
            if ($id == null || $id == '') {
                ths.parent().parent().parent().remove();
            } else {
                //异步删除
                var ajaxData = "id=" + $id;
                $.ajax({
                    type: "POST",
                    url: "${ctx}/appraiseContrller/deleteDetail",
                    data: ajaxData,
                    success: function (data) {
                        ths.parent().parent().parent().remove();
                    }
                });
            }
        });
        $("#addAppra").click(function () {
            // var isAuto = $("#isautoappraise").is(':checked');
            var rowCount = $('.bordered tr').length;
            if (rowCount > 6) {
                $.rwpUI.success("最多只能添加6个节点!");
                return;
            }
            var html = '<tr class="rowCount"><td style="text-align: center;width: 40px">' + rowCount + '</td>' +
                '<td style="text-align: center;width: 100px"><input type="text" name="appraisename" style="width: 280px;height:35px;border: none;text-align: center" placeholder="请输入节点名称..."/></td>' +
                // '<td style="text-align: center;width: 80px"><input type="text" name="appraisevalue" style="width: 80px;height:35px;border: none" placeholder="请输入分值"/></td>' +
                // '<td style="text-align: center;width: 80px"><div style=""><a href="javascript:;" class="upbtn">选择文件<input name="did" type="hidden" value="0"><input class="isFile" name="isFile" type="hidden" value="0"><input style="width: 50px;" class="up" type="file" name="myImage" /></a></div></td>' +
                // '<td style="text-align: center;width: 100px" class="showImage">' +
                // '<img src="" style="display: none; width: 40px;height: 40px;"/></td>' +
                '<td style="text-align: center;width: 140px"><span class="rwpDgIcon"><a href="#" rwpOpts="{ isajax: true }" class="del icon">&nbsp;</a></span></td>';
            // if (isAuto) {
            //     html += '<td class="default_" style="text-align: center;width: 100px" >' +
            //         '<div style="width: 60px;height: 30px;margin-left: 20px;" class="img_select"></div>' +
            //         '<input type="hidden" name="isdefault" value="false">' +
            //         '</td>';
            // } else {
            //     html += '<td style="display: none;text-align: center;width: 100px" class="default_">' +
            //         '<div style="width: 60px;height: 30px;margin-left: 20px;" class="img_select"></div>' +
            //         '<input type="hidden" name="isdefault" value="false">' +
            //         '</td>';
            // }
            html += '</tr>';
            $(".bordered").append(html);
        });
        // 上传图标
        $(function () {
            $(".bordered").on("click", ".upload", function () {
                $(this).next().click();
            });
            $(".bordered").on('change', ".up", function () {
                var $this = $(this);
                $this.prev().val('1');
                if (this.files && this.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function (evt) {
                        $this.parent().parent().parent().next().find("img").attr("src", evt.target.result);
                        $this.parent().parent().parent().next().find("img").show();
                    };
                    reader.readAsDataURL(this.files[0]);
                } else {
                    var filterAlpha = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
                    this.select();
                    if (navigator.userAgent.indexOf('MSIE 9.0') || navigator.userAgent.indexOf('MSIE 10.0')) {
                        if (window.location.href === window.parent.location.href) { //不是嵌套在iframe中直接执行file.blur
                            this.blur();
                        } else {
                            //被套在iframe中的时候需要给页面上其他的DIV或者BUTTON执行focus
                            window.parent.document.body.focus();
                        }
                    }
                    var src = document.selection.createRange().text;
                    var tempImage = $(this).parent().parent().parent().next().find("img");
                    tempImage.attr('src', src);
                    tempImage.show();
                }
            });
            var $Isautoappraise = $("#isautoVal").val();
            if ($Isautoappraise == 'true') {
                $(".default_").show();
            }
            $(".spe").click(function () {
                $(".default_").toggle();
            });
            $(".bordered").on("click", ".default_", function () {
                var $this = $(this);
                var bool = $this.find('div').first().hasClass("showDefault");
                if (bool) {
                    $this.find('div').removeClass("showDefault");
                    $this.find('input').first().val('false');
                } else {
                    $(".img_select").removeClass("showDefault");
                    $this.find('div').addClass("showDefault");
                    $(".isdefault").val('false');
                    $this.find('input').first().val('true');
                }
            });
        });
    });
</script>
<div class="fAutoPage">
    <form action="${ctx}/appraiseContrller/saveAppraDetail1" id="apprform"
          method="post" enctype="multipart/form-data">
        <fieldset>
            <legend>
                <i class="i"></i>评价模板信息
            </legend>
            <input data-val="true" data-val-number="字段用户ID必须是一个数字。"
                   data-val-required="用户ID字段是必需的。" id="modelId" name="modelId" type="hidden"
                   value="${model.id}"/>
            <div class="formpanel">
                <div class="clear">
                    <ul class="DialabelWidth">
                        <li class="editor-label"><label for="modelname">模板名称</label>
                        </li>
                        <li class="editor-field"><input data-val="true"
                                                        data-val-length="模板名称不能超过20字符" data-val-length-max="20"
                                                        data-val-required="模板名称不能为空" id="modelname" name="modelname"
                                                        type="text" value="${model.modelname}"/>
                        </li>
                        <li class="editor-validation"><span
                                class="field-validation-valid" data-valmsg-for="modelname"
                                data-valmsg-replace="true"></span>
                        </li>
                    </ul>
                </div>
            </div>
        </fieldset>
        <%--节点管理--%>
        <fieldset>
            <legend>
                <i class="i"></i>节点管理
            </legend>
            <div class="formpanel">
                <div class="clear" style="width: 600px">
                    <a href="javascript:void(0);" class="rwpButton Add" id="addAppra"><span
                            class="rwpButtonIcon"></span><span
                            class="rwpButtonText">添加节点</span></a>
                    <table class="bordered">
                        <thead>
                        <tr>
                            <th style="width: 40px;text-align: center;color: black">#</th>
                            <th style="width: 100px;text-align: center;color: black">服务评价</th>
                            <%--<th style="width: 80px;text-align: center;color: black">分值</th>--%>
                            <%--<th style="width: 100px;color: black;text-align: center;">上传图标</th>--%>
                            <%--<th style="color: black;text-align: center;width: 100px">图标预览</th>--%>
                            <th style="width: 140px;color: black;text-align: center;">操作</th>
                            <%--<th id="_default" class="default_" style="width: 100px;color: black;text-align: center;display: none;">设为默认</th>--%>
                        </tr>
                        </thead>
                        <c:forEach items="${details}" var="detail" varStatus="stau">
                            <tr>
                                <td style="text-align: center;width: 40px;">${stau.index+1}
                                </td>
                                <td style="text-align: center"><input type="text" style="width: 100px;height:35px;border: none;text-align: center" name="appraisename"
                                           placeholder="请输入节点名称..." value="${detail.appraisename}"/>
                                </td>
                                <%--<td   style="width: 80px;"><input type="text" style="width: 80px;height:35px;border: none" name="appraisevalue"--%>
                                           <%--placeholder="请输入分值" value="${detail.appraisevalue}"/>--%>
                                <%--</td>--%>
                                <%--<td style="width: 80px;text-align: center;"><div style=""><a href="javascript:;" class="upbtn">选择文件--%>
                                    <%--<input name="did" type="hidden" value="${detail.id}">--%>
                                    <%--<input class="isFile" name="isFile" type="hidden" value="0">--%>
                                    <%--<input style="width: 50px;" class="up" type="file" name="myImage" /></a></div>--%>
                                <%--</td>--%>
                                <%--<td style="text-align: center;width: 100px;" class="showImage">--%>
                                    <%--<img src="${ctx}/appraiseContrller/showImg?id=${detail.id}" style="width: 45px;height: 45px;"/>--%>
                                <%--</td>--%>
                                <td style="width: 140px;"><span class="rwpDgIcon"><input type="hidden" value="${detail.id}"><a
                                        href="#" rwpOpts="{ isajax: true }" class="del icon">&nbsp;</a></span>
                                </td>
                                <%--<td class="default_" style="display: none;width: 100px">--%>
                                    <%--<div style="width: 60px;height: 30px;margin-left: 20px;"--%>
                                         <%--class="img_select <c:if test="${detail.isdefault==true}">showDefault</c:if>"></div>--%>
                                    <%--<input type="hidden" class="isdefault" name="isdefault" value="${detail.isdefault}">--%>
                                <%--</td>--%>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </fieldset>
    </form>
</div>