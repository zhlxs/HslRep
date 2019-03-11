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
	width: 650px;
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
											$.rwpUI.warn("保存模块失败,请联系管理员！");
										}
									};
									$("#ajaxform").ajaxSubmit(option);
									return false;
								}
							}
						});
		//删除节点
		$('.bordered').on(
				"click",
				".del",
				function() {
					var ths = $(this);
					var rowCount = $(".rowCount").length;
					var self = ths.parent().parent().parent().find("td")
							.first();
					if (Number(self.html()) == rowCount) {
						ths.parent().parent().parent().remove();
					} else {
						var num = Number(self.html());
						ths.parent().parent().parent().remove();
						for (var i = 0; i < rowCount; i++) {
							var Td = $(".bordered tr").eq(i + 1).find("td")
									.first();
							//alert(Td.text());
							var $Int = $(".bordered tr").eq(i + 1).find("td")
									.first().prev();
							//alert($Int.val());
							if (Number(Td.html()) > num) {
								$Int.val(Number(Td.html()) - 1);
								Td.html(Number(Td.html()) - 1);
							}
						}
					}
				});
		//下移表格行单元
		$('.bordered').on(
				"click",
				".down",
				function() {
					var ths = $(this);
					var rowCount = $(".rowCount").length;
					var self = ths.parent().parent().parent().find("td")
							.first();
					var next = ths.parent().parent().parent().next().find("td")
							.first();
					var selfInt = ths.parent().parent().parent().find("td")
							.first().prev();
					var nextInt = ths.parent().parent().parent().next().find(
							"td").first().prev();
					if (Number(self.html()) == rowCount) {
						$.rwpUI.success("" + "" + "已经是最后一张了!");
					} else {
						var objParentTR = ths.parent().parent().parent();
						var nextTR = objParentTR.next();
						selfInt.val(Number(self.html()) + 1);
						nextInt.val(Number(next.html()) - 1);
						self.html(Number(self.html()) + 1);
						next.html(Number(next.html()) - 1);
						nextTR.insertBefore(objParentTR);
					}
				});
		//上移表格行单元
		$('.bordered').on(
				"click",
				".up",
				function() {
					var ths = $(this);
					var rowCount = $(".rowCount").length;
					var self = ths.parent().parent().parent().find("td")
							.first();
					var prev = ths.parent().parent().parent().prev().find("td")
							.first();
					var selfInt = ths.parent().parent().parent().find("td")
							.first().prev();
					var prevInt = ths.parent().parent().parent().prev().find(
							"td").first().prev();
					if (Number(self.html()) == 1) {
						$.rwpUI.success("" + "" + "已经是第一张了!");
					} else {
						selfInt.val(Number(self.html()) - 1);
						prevInt.val(Number(prev.html()) + 1);
						self.html(Number(self.html()) - 1);
						prev.html(Number(prev.html()) + 1);
						var objParentTR = ths.parent().parent().parent();
						var prevTR = objParentTR.prev();
						prevTR.insertAfter(objParentTR);
					}
				});
		$("#addAppra")
				.click(
						function() {
							var rowCount = $('.bordered tr').length;
							if (rowCount > 6) {
								$.rwpUI.success("最多只能添加6个节点!");
								return;
							}
							var html = '<tr class="rowCount"><input type="hidden" name="order" value="' + rowCount + '"/>'
									+ '<td class="sequence" style="text-align: center;width:40px;">'
									+ rowCount
									+ '</td>'
									+ '<td style="text-align: center;width:300px;"><input required="required" name="prehour" maxlength="2" type="number" class="timeInt" />'
									+ ':<input required="required" name="premin" maxlength="2" type="number" class="timeInt" />'
									+ '--<input required="required" name="sufhour" maxlength="2" type="number" class="timeInt" />'
									+ ':<input required="required" name="sufmin" maxlength="2" type="number" class="timeInt" /></td>'
									+ '<td style="text-align: center;"><input name="orderCount" style="width:40px;text-align: center;" /></td>'
									+ '<td style="text-align: center;width:100px;padding:0px 3px 0px 7px;"><span class="rwpDgIcon">'
									+ '<a href="#" rwpOpts="{ isajax: true }" class="del icon">&nbsp;</a>';
							html += '&nbsp;<a href="#" rwpOpts="{ isajax: true }" class="up icon">&nbsp;</a>';
							html += '&nbsp;<a href="#" rwpOpts="{ isajax: true }" class="down icon">&nbsp;</a>';
							html += '</span><input type="hidden" value="" class="imgid"></td></tr>';
							$(".bordered").append(html);
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
							type="text" value="${model.modelName}" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="modelName"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="businessType">业务类型</label>
						</li>
						<li class="editor-field"><select id="businessType"
							name="businessType">
							<option <c:if test="${model.businessType == 1}">selected = selected</c:if> value="1">机动车业务</option>
							<option <c:if test="${model.businessType == 2}">selected = selected</c:if> value="2">驾驶证业务</option>
							<option <c:if test="${model.businessType == 4}">selected = selected</c:if> value="4">违法业务</option>
						</select></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="businessType"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="isValid">是否启用</label></li>
						<li class="editor-field spe"><input data-val="true"
							data-val-required="是否启用字段是必需的" id="isValid" name="isValid"
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
				<i class="i"></i>时间段设置
			</legend>
			<div class="formpanel">
				<div class="clear" style="width: auto;">
					<a href="javascript:void(0);" class="rwpButton Add" id="addAppra"><span
						class="rwpButtonIcon"></span><span class="rwpButtonText">添加节点</span></a>
					<table class="bordered">
						<thead>
							<tr>
								<th style="text-align: center;color: black;width: 40px;">#</th>
								<th style="text-align: center;color: black;width: 300px;">时间段</th>
								<th style="text-align: center;color: black;width: 55px;">可预约人数</th>
								<th style="color: black;text-align: center;width: 40px;">操作</th>
							</tr>
						</thead>
						<c:forEach items="${list}" var="item" varStatus="stau">
							<tr class="rowCount">
								<input type="hidden" name="order" value="${item.order}" />
								<td style="text-align: center;width: 40px;">${stau.index+1}
								</td>
								<td style="text-align: center;width: 300px;"><input
									value="${item.preHour}" required="required" name="prehour"
									maxlength="2" type="number" class="timeInt" />:<input
									value="${item.preMin}" required="required" name="premin"
									maxlength="2" type="number" class="timeInt" />--<input
									value="${item.sufHour}" required="required" name="sufhour"
									maxlength="2" type="number" class="timeInt" />:<input
									value="${item.sufMin}" required="required" name="sufmin"
									maxlength="2" type="number" class="timeInt" /></td>
									<td style="text-align: center;"><input name="orderCount" value="${item.orderCount}" style="width:40px;text-align: center;" /></td>
								<td style="width: 100px;"><span class="rwpDgIcon"><input
										class="imgid" type="hidden" value="${item.id}">&nbsp;<a
										href="#" rwpOpts="{ isajax: true }" class="del icon">&nbsp;</a>
										<a href="#" rwpOpts="{ isajax: true }" class="up icon">&nbsp;</a>
										<a href="#" rwpOpts="{ isajax: true }" class="down icon">&nbsp;</a></span>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</fieldset>
		<span><span style="color: red;">*</span>添加时间段时，请注意格式(如：09:00--10:00)，不要交叉添加时间。</span>
	</form>
</div>