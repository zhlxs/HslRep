<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script src="${ctx}/js/jquery-form.js"></script>
<style>
table {
	*border-collapse: collapse; /* IE7 and lower */
	border-spacing: 0;
	/* width: 700px; */
	font-family: "Microsoft YaHei";
}

.bordered {
	width: 620px;
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

.bordered td,.bordered th {
	border-left: 1px solid #ccc;
	border-top: 1px solid #ccc;
	padding: 5px;
	text-align: left;
}

.bordered th {
	background-color: #dce9f9;
	background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc),
		to(#dce9f9));
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
	padding: 8px;
}

.bordered td:first-child,.bordered th:first-child {
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

.timeInt {
	width: 50px;
	height: auto;
	text-align: center;
}

.rwpDialog {
	margin-top: -1%;
}
</style>
<script type="text/javascript">
	$(function() {
		$(function() {
			$("#reback").click(function() {
				$(".rwpDialogMask").hide();
				$(".rwpDialog").remove();
			});
		});
		$("#ajaxform")
				.rwpUIForm(
						{
							isAjaxSubmit : true,
							ajaxSubmitAttrs : {
								beforeSubmit : function() {
									var option = {
										url : '${ctx}/timeModelController/savetimeModel',
										type : 'POST',
										// async: false,
										dataType : 'json',
										success : function(result) {
											$(".rwpDialogMask").hide();
											$(".rwpDialog").remove();
											$.rwpUI
													.alert(
															result.stateMsg,
															'操作提示',
															result.stateType == 0 ? 'success'
																	: 'error',
															false);
											rwpPluginHelper.menuHelper
													.loadMenuInsidePage('${ctx}/timeModelController/modelList');
										},
										error : function(data) {
											alert("保存模块失败,请联系管理员！");
										}
									};
									$("#ajaxform").ajaxSubmit(option);
									return false;
								}
							}
						});
		$('.bordered').on("click", ".del", function() {
			var ths = $(this);
			var $id = ths.prev().val();
			if ($id == null || $id == '') {
				ths.parent().parent().parent().remove();
			} else {
				//异步删除
				var ajaxData = "id=" + $id;
				$.ajax({
					type : "POST",
					url : "${ctx}/appraiseContrller/deleteDetail",
					data : ajaxData,
					success : function(data) {
						ths.parent().parent().parent().remove();
					}
				});
			}
		});
		$("#addAppra")
				.click(
						function() {
							var isAuto = $("#isautoappraise").is(':checked');
							var isWarn = $("#iswarning").is(':checked');
							var rowCount = $('.bordered tr').length;
							if (rowCount > 6) {
								$.rwpUI.success("最多只能添加6个节点!");
								return;
							}
							var html = '<tr class="rowCount"><input type="hidden" name="order" value="' + rowCount + '"/><td style="text-align: center;">'
									+ rowCount
									+ '</td>'
									+ '<td style="text-align: center;"><input required="required" name="prehour" maxlength="2" type="number" class="timeInt" />:<input required="required" name="premin" maxlength="2" type="number" class="timeInt" />--<input required="required" name="sufhour" maxlength="2" type="number" class="timeInt" />:<input required="required" name="sufmin" maxlength="2" type="number" class="timeInt" /></td>'
									+ '<td style="text-align: center;"><span class="rwpDgIcon"><input type="hidden" value=""><a href="#" rwpOpts="{ isajax: true }" class="del icon">&nbsp;</a></span></td>';
							html += '</tr>';
							$(".bordered").append(html);
						});
		// 上传图标
		$(function() {
			$(".bordered").on("click", ".upload", function() {
				$(this).next().click();
			});
			$(".bordered")
					.on(
							'change',
							".up",
							function() {
								var $this = $(this);
								$this.prev().val('1');
								if (this.files && this.files[0]) {
									var reader = new FileReader();
									reader.onload = function(evt) {
										$this.parent().parent().parent().next()
												.find("img").attr("src",
														evt.target.result);
										$this.parent().parent().parent().next()
												.find("img").show();
									};
									reader.readAsDataURL(this.files[0]);
								} else {
									var filterAlpha = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
									this.select();
									if (navigator.userAgent.indexOf('MSIE 9.0')
											|| navigator.userAgent
													.indexOf('MSIE 10.0')) {
										//不是嵌套在iframe中直接执行file.blur
										if (window.location.href === window.parent.location.href) {
											this.blur();
										} else {
											//被套在iframe中的时候需要给页面上其他的DIV或者BUTTON执行focus
											window.parent.document.body.focus();
										}
									}
									var src = document.selection.createRange().text;
									var tempImage = $(this).parent().parent()
											.parent().next().find("img");
									tempImage.attr('src', src);
									tempImage.show();
								}
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
			$(".hasWarn").click(function() {
				$(".warn_").toggle();
			});
			$(".spe").click(function() {
				$(".default_").toggle();
				$("#outtime").toggle();
			});
			$(".bordered").on("click", ".default_", function() {
				var $this = $(this);
				var bool = $this.find('div').first().hasClass("showDefault");
				if (bool) {
					$this.find('div').removeClass("showDefault");
					$this.find('input').first().val('false');
				} else {
					$(".img_select").removeClass("showDefault");
					$(".isdefault").val('false');
					$this.find('div').addClass("showDefault");
					$this.find('input').first().val('true');
				}
			});
			$(".bordered").on("click", ".warn_", function() {
				var $this = $(this);
				var bool = $this.find('div').first().hasClass("showDefault");
				if (bool) {
					$this.find('div').removeClass("showDefault");
					$this.find('input').first().val('false');
				} else {
					$(".warn").removeClass("showDefault");
					$(".iswarn").val('false');
					$this.find('div').addClass("showDefault");
					$this.find('input').first().val('true');
				}
			});
		});
	});
</script>
<div class="fAutoPage">
	<form action="" id="ajaxform" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>
				<i class="i"></i>预约时间段模板
			</legend>
			<input data-val="true" data-val-number="字段用户ID必须是一个数字"
				data-val-required="用户ID字段是必需的。" id="modelId" name="modelId"
				type="hidden" value="${model.id}" />
			<div class="formpanel">
				<div class="clear">
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="modelName">模板名称</label>
						</li>
						<li class="editor-field"><input data-val="true"
							data-val-length="模板名称不能超过20字符" data-val-length-max="20"
							data-val-required="模板名称不能为空" id="modelName" name="modelName"
							readonly="readonly" type="text" value="${model.modelName}" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="modelName"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="businessType">业务类型</label>
						</li>
						<li class="editor-field"><select id="businessType" disabled="disabled"
							name="businessType">
								<option
									<c:if test="${model.businessType == 1}">selected = selected</c:if>
									value="1">机动车业务</option>
								<option
									<c:if test="${model.businessType == 2}">selected = selected</c:if>
									value="2">驾驶证业务</option>
								<option
									<c:if test="${model.businessType == 4}">selected = selected</c:if>
									value="4">违法业务</option>
						</select></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="businessType"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="isValid">是否启用</label></li>
						<li class="editor-field spe"><input data-val="true"
							data-val-required="是否启用字段是必需的" disabled="disabled"
							<c:if test="${model.isValid == 1}">
                                    checked=checked
                                </c:if>
							type="checkbox" value="1" /><input name="isValid" type="hidden"
							value="0"></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="isautoappraise"
							data-valmsg-replace="true"></span></li>
					</ul>
				</div>
		</fieldset>
		<%--时间段节点管理--%>
		<fieldset>
			<legend>
				<i class="i"></i>时间段信息
			</legend>
			<div class="formpanel">
				<div class="clear" style="width: auto;">
					<table class="bordered">
						<thead>
							<tr>
								<th style="text-align: center;color: black;width: 40px;">#</th>
								<th style="text-align: center;color: black;width: 300px;">时间段</th>
								<th style="text-align: center;color: black;width: 55px;">可预约人数</th>
							</tr>
						</thead>
						<c:forEach items="${list}" var="detail" varStatus="stau">
							<tr>
								<td style="text-align: center;width: 40px;">${stau.index+1}
								</td>
								<td style="text-align: center;">${detail.dicTime.time}</td>
								<td style="text-align: center;">${detail.orderCount}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</fieldset>
		<div class="fPptions" style="text-align: center;float: left;">
			<a href="javascript:void(0);" title="返回"
				class="btnLightBlue btn back" style="width: 50px; height: 30px;"
				id="reback"><i class="i"></i>返回</a>
		</div>
	</form>
</div>