<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script src="${ctx}/js/jquery-form.js"></script>
<style>
/* .rwpDialog { */
/* 	margin-top: -120px; */
/* } */
.rwpDgIcon .add {
	background-position: -150px -34px;
}

table {
	/* 	*border-collapse: collapse; /* IE7 and lower */ */
	/* 	border-spacing: 0; */
	/* 	font-family: "Microsoft YaHei"; */
	
}

.bordered {
	width: 600px;
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
	padding: 10px;
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

.person-msg .person-img a:hover:after {
	content: "上传照片";
	width: 120px;
	height: 120px;
	line-height: 120px;
	background: #000;
	position: absolute;
	left: 0;
	top: 0;
	font-family: MicrosoftYaHei;
	font-size: 20px;
	color: #FFFFFF;
	z-index: 9999;
	letter-spacing: 10px;
	text-shadow: 0 1px 1px rgba(0, 0, 0, 0.50);
}

:root .person-msg .person-img a:hover:after {
	filter: none;
	background-color: rgba(0, 0, 0, 0.5);
}

.up-img-cover {
	position: relative;
}

.file {
	filter: alpha(opacity : 0);
	opacity: 0;
	width: 15px
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

.upFile {
	/*file的样式*/
	position: absolute;
	overflow: hidden;
	right: 0;
	top: 0;
	opacity: 0;
	filter: alpha(opacity = 0);
}
</style>
<script type="text/javascript">
	$("#back").click(function() {
		rwpMenuHelper.rebackLastPage();
	});
	$("#gosub").click(function() {
		$("#imgfrom").submit();
	});
	$(function() {
		$("#imgfrom")
				.rwpUIForm(
						{
							isAjaxSubmit : true,
							ajaxSubmitAttrs : {
								beforeSubmit : function() {
									//alert($(".sequence").length);
									if ($(".sequence").length == 0) {
										//$(".rwpDialogMask").hide();
										//$(".rwpDialog").remove();
										$.rwpUI
												.warn(
														"没有新增图片!",
														"提示",
														true,
														function(yes) {
															if (yes) {
																rwpPluginHelper.menuHelper
																		.loadMenuInsidePage('${ctx}/deptController/deptList');
															}
														});
										return false;
									} else {
										var flag = true;
										$(".upFile")
												.each(
														function() {
															if ($(this).val() == null
																	|| $(this)
																			.val() == '') {
																flag = false;
															}
														});
										if (flag == false) {
											$.rwpUI.alert("请上传图片!", '操作提示',
													'error', false);
											return false;
										}
									}
									var option = {
										url : '${ctx}/photeAndVideoController/savePvConfig',
										type : 'POST',
										dataType : 'json',
										//headers: {"ClientCallMode": "ajax"}, //添加请求头部
										success : function(result) {
											// alert(data);
											//   var result = JSON.parse(data);
											$(".rwpDialogMask").hide();
											$(".rwpDialog").remove();
											rwpMenuHelper.rebackLastPage();
											$.rwpUI
													.alert(
															result.stateMsg,
															'操作提示',
															result.stateType == 0 ? 'success'
																	: 'error',
															false);
										},
										error : function(XMLHttpRequest,
												textStatus, errorThrown) {
											alert(XMLHttpRequest.status);
											alert(XMLHttpRequest.readyState);
											alert(textStatus);
										}
									};
									$("#imgfrom").ajaxSubmit(option);
									return false;
								}
							}
						});
		$('.bordered')
				.on(
						"click",
						".del",
						function() {
							var ths = $(this);
							var id = ths.siblings(".imgid").val();
							var jurl = "${ctx}/photeAndVideoController/deleteImg";
							if (id != null && id != '') {
								$
										.post(
												jurl,
												{
													'id' : id
												},
												function(data) {
													if (data.stateType == 0) {
														var sibs = ths.parent()
																.parent()
																.parent()
																.nextAll();
														for (var i = 0; i < sibs.length; i++) {
															var td = $(sibs[i])
																	.find("td")
																	.first();
															var old = td.text();
															td.text(old - 1);
														}
														ths.parent().parent()
																.parent()
																.remove();
													} else {
														$.rwpUI.error("" + ""
																+ "删除失败!");
													}
												});
							} else {
								var nextTr = ths.parent().parent().parent()
										.next();
								var nextId = nextTr.find("td").last().find(
										".imgid").val();
								if (nextId != null && nextId != '') {
									var jurl1 = "${ctx}/photeAndVideoController/moveImg";
									$
											.post(
													jurl1,
													{
														'id' : nextId,
														'type' : 31
													},
													function(data) {
														if (data.stateType == 0) {
															var sibs = ths
																	.parent()
																	.parent()
																	.parent()
																	.nextAll();
															for (var i = 0; i < sibs.length; i++) {
																var td = $(
																		sibs[i])
																		.find(
																				"td")
																		.first();
																if (td
																		.children().length > 0) {
																	var old = td
																			.find(
																					".sequence")
																			.val();
																	td
																			.find(
																					".sequence")
																			.val(
																					old - 1);
																} else {
																	var old = td
																			.text();
																	td
																			.text(old - 1);
																}
															}
															ths.parent()
																	.parent()
																	.parent()
																	.remove();
														} else {
															$.rwpUI.error(""
																	+ ""
																	+ "删除失败!");
														}
													});
								} else {
									ths.parent().parent().parent().remove();
								}
							}
						});
		$('.bordered').on(
				"click",
				".down",
				function() {
					var ths = $(this);
					var id = ths.siblings(".imgid").val();
					var jurl = "${ctx}/photeAndVideoController/moveImg";
					if (id != null) {
						var self = ths.parent().parent().parent().find("td")
								.first();
						var next = ths.parent().parent().parent().next().find(
								"td").first();
						if (Number(self.html()) == 6) {
							$.rwpUI.success("" + "" + "已经是最后一张了!");
						} else {
							$.post(jurl, {
								'id' : id,
								'type' : 2
							}, function(data) {
								if (data.stateType == 0) {
									self.html(Number(self.html()) + 1);
									next.html(Number(next.html()) - 1);
									var objParentTR = ths.parent().parent()
											.parent();
									var nextTR = objParentTR.next();
									nextTR.insertBefore(objParentTR);
								} else {
									$.rwpUI.error("" + "" + "移动失败!");
								}
							});
						}
					} else {
						//新加的图片
						var nextTr = ths.parent().parent().parent().next();
						var nextId = nextTr.find("td").last().find(".imgid")
								.val();
						//后面是原有的图片
						var self = ths.parent().parent().parent().find("td")
								.first().find(".sequence");
						if (Number(self.val()) == 6) {
							$.rwpUI.success("" + "" + "已经是最后一张了!");
						} else {
							if (nextId != null && nextId != '') {
								$.post(jurl, {
									'id' : nextId,
									'type' : 21
								}, function(data) {
									if (data.stateType == 0) {
										var next = ths.parent().parent()
												.parent().next().find("td")
												.first();
										self.val(Number(self.val()) + 1);
										next.html(Number(next.html()) - 1);
										var objParentTR = ths.parent().parent()
												.parent();
										var nextTR = objParentTR.next();
										nextTR.insertBefore(objParentTR);
									} else {
										$.rwpUI.error("" + "" + "移动失败!");
									}
								});
							} else {
								//前面是新加的图片
								var next = ths.parent().parent().parent()
										.next().find("td").first().find(
												".sequence");
								self.val(Number(self.val()) + 1);
								next.val(Number(next.val()) - 1);
								var objParentTR = ths.parent().parent()
										.parent();
								var nextTR = objParentTR.next();
								nextTR.insertBefore(objParentTR);
							}
						}
					}
				});
		$('.bordered').on(
				"click",
				".up",
				function() {
					var ths = $(this);
					var id = ths.siblings(".imgid").val();
					var jurl = "${ctx}/photeAndVideoController/moveImg";
					if (id != null) {
						var self = ths.parent().parent().parent().find("td")
								.first();
						var prev = ths.parent().parent().parent().prev().find(
								"td").first();
						if (Number(self.html()) == 1) {
							$.rwpUI.success("" + "" + "已经是第一张了!");
						} else {
							//已有的图片
							$.post(jurl, {
								'id' : id,
								'type' : 1
							}, function(data) {
								if (data.stateType == 0) {
									self.html(Number(self.html()) - 1);
									prev.html(Number(prev.html()) + 1);
									var objParentTR = ths.parent().parent()
											.parent();
									var prevTR = objParentTR.prev();
									prevTR.insertAfter(objParentTR);
								} else {
									$.rwpUI.error("" + "" + "移动失败!");
								}
							});
						}
					} else {
						//新加的图片
						var self = ths.parent().parent().parent().find("td")
								.first().find(".sequence");
						if (Number(self.val()) == 1) {
							$.rwpUI.success("" + "" + "已经是第一张了!");
						} else {
							var prevTr = ths.parent().parent().parent().prev();
							var preId = prevTr.find("td").last().find(".imgid")
									.val();
							//前面是原有的图片
							if (preId != null && preId != '') {
								$.post(jurl, {
									'id' : preId,
									'type' : 11
								}, function(data) {
									if (data.stateType == 0) {
										var prev = ths.parent().parent()
												.parent().prev().find("td")
												.first();
										self.val(Number(self.val()) - 1);
										prev.html(Number(prev.html()) + 1);
										var objParentTR = ths.parent().parent()
												.parent();
										var prevTR = objParentTR.prev();
										prevTR.insertAfter(objParentTR);
									} else {
										$.rwpUI.error("" + "" + "移动失败!");
									}
								});
							} else {
								//前面是新加的图片
								var prev = ths.parent().parent().parent()
										.prev().find("td").first().find(
												".sequence");
								self.val(Number(self.val()) - 1);
								prev.val(Number(prev.val()) + 1);
								var objParentTR = ths.parent().parent()
										.parent();
								var prevTR = objParentTR.prev();
								prevTR.insertAfter(objParentTR);
							}
						}
					}
				});
		// $('.bordered').on('click', '.upbtn', function () {
		//     $(this).siblings(".upFile").click();
		// });
		$('.bordered')
				.on(
						'change',
						'.upFile',
						function() {
							var $this = $(this);
							if (this.files && this.files[0]) {
								var reader = new FileReader();
								reader.onload = function(evt) {
									$this.parent().parent().prev().attr("src",
											evt.target.result);
									$this.parent().parent().prev().show();
									$this.parent().parent().hide();
								};
								reader.readAsDataURL(this.files[0]);
							} else {
								//var filterAlpha = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
								this.select();
								if (navigator.userAgent.indexOf('MSIE 9.0')
										|| navigator.userAgent
												.indexOf('MSIE 10.0')) {
									if (window.location.href === window.parent.location.href) { //不是嵌套在iframe中直接执行file.blur
										this.blur();
									} else {
										//被套在iframe中的时候需要给页面上其他的DIV或者BUTTON执行focus
										//$(file).parent().trigger('focus');
										window.parent.document.body.focus();
									}
								}
								var src = document.selection.createRange().text;
								var tempImage = $(this).parent().parent()
										.prev();
								tempImage.attr('src', src);
								$this.parent().parent().hide();
								tempImage.show();
							}
						});
		$("#addAppra")
				.click(
						function() {
							var rowCount = $('.bordered tr').length;
							if (rowCount > 16) {
								$.rwpUI.success("" + "" + "最多只能有16张图片!");
							} else {
								var html = '<tr class="imgtr"><td style="text-align: center;width: 60px"><input type="text" class="sequence" name="sequence" style="width: 60px;height:35px;border: none;text-align: center"  value="' + rowCount + '"/></td>';
								html += '<td style="text-align: center;">';
								html += '<img src="" alt="" class="imgshow" style="width: auto;height: auto;max-height: 120px;display: none;">';
								html += '<div style=""><a href="javascript:;" class="upbtn">选择文件 <input class="upFile" type="file" name="imgfile" /> </a></div>';
								html += '</td>';
								html += '<td style="width: 100px;"><span class="rwpDgIcon"><a href="#" rwpOpts="{ isajax: true }" class="del icon">&nbsp;</a>';
								html += '<a href="#" rwpOpts="{ isajax: true }" class="up icon">&nbsp;</a>';
								//下移
								html += '<a href="#" rwpOpts="{ isajax: true }" class="down icon">&nbsp;</a>';
								html += '</span><input type="hidden" value="" class="imgid"></td></tr>';
								$(".bordered").append(html);
							}
						});
	});
</script>
<div class="fAutoPage">
	<form action="" id="imgfrom" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>
				<i class="i"></i>图片管理
			</legend>
			<div class="formpanel">
				<input type="hidden" name="deptid" value="${deptid}">
				<div class="clear">
					<a href="javascript:void(0);" class="rwpButton Add" id="addAppra"><span
						class="rwpButtonIcon"></span><span class="rwpButtonText">添加图片</span></a>
					<table class="bordered">
						<thead>
							<tr>
								<th style="text-align: center;color: black;width: 60px">序号</th>
								<th style="color: black;text-align: center;">预览</th>
								<%--  <th style="width: 80px;color: black">顺序号</th>--%>
								<th style="color: black;text-align: center;width: 100px">操作</th>
							</tr>
						</thead>
						<c:forEach items="${photosAndVideos}" var="pv" varStatus="stau">
							<tr>
								<td style="text-align: center;width: 60px">${pv.sequence}</td>
								<td style="text-align: center"><c:if
										test="${pv.isvideoorphoto==1}">
										<img src="${ctx}/photeAndVideoController/congImg?id=${pv.id}"
											width="120px;" height="120px">
									</c:if> <c:if test="${pv.isvideoorphoto==2}">
										<a href="${pv.localURL}" target="_blank">视频预览</a>
									</c:if></td>
								<td style="text-align: center;width: 100px"><span
									class="rwpDgIcon"> <a href="#"
										rwpOpts="{ isajax: true }" class="del icon">&nbsp;</a> <a
										href="#" rwpOpts="{ isajax: true }" class="up icon">&nbsp;</a>
										<a href="#" rwpOpts="{ isajax: true }" class="down icon">&nbsp;</a>
										<input type="hidden" value="${pv.id}" class="imgid"></span></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</fieldset>
	</form>
	<div class="clear" style="margin-left: 20px;">
		<div class="fPptions" style="float: left; margin-top: 4px;" id="gosub">
			<a href="javascript:;" title="确定" class="btnLightBlue btn back"
				style="width: 50px; height: 32px;" id="rego"><i class="i"></i>确定</a>
		</div>
		<div class="fPptions" style="float: left; margin-top: 4px;" id="back">
			<a href="javascript:;" title="取消" class="btnLightBlue btn back"
				style="width: 50px; height: 32px;" id="reback"><i class="i"></i>取消</a>
		</div>
	</div>
</div>