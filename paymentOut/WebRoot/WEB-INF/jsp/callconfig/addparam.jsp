<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script src="${ctx}/js/jquery-form.js"></script>
<%-- <script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/ueditor.all.min.js"></script> --%>
<style>
/* .edui-popup-content.edui-default { */
/* 	height: auto !important; */
/* } */
.upbtn {
	/*a标签的样式*/
	padding: 3px 10px;
	height: 20px;
	line-height: 20px;
	position: relative;
	border: 3px solid #6FB3E0;
	text-decoration: none;
	color: white;
	background-color: #6FB3E0;
	margin-left: 3%;
}

.up {
	/*file的样式*/
	position: absolute;
	overflow: hidden;
	right: 0;
	top: 0;
	opacity: 0;
	filter: alpha(opacity = 0);
}

.rwpDialog {
	height: auto;
}
</style>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript">
	rwpTemp.f_selectparentok = function(node, value, text) {
		value = value || 0; //没选择部门时赋值为0
		$('#parentId').val(value);
		$('#parentName').val(text);
	};

	rwpTemp.f_selectparent = function() {
		var url = '${ctx}/macConfigController/listDialog?actiontype=selectparent&parentId='
				+ $("#parentId").val() + '&modelId=${modelId}';
		return rwpPluginHelper.selectContact('选择父级菜单', url, 'id', 'title',
				'parentId', '', null, rwpTemp.f_selectparentok);
	};
	//提交按钮方法
	function gosub() {
		var option = {
			url : '${ctx}/macConfigController/saveParam',
			type : 'POST',
			// async: false,
			dataType : 'json',
			success : function(result) {
				$(".rwpDialogMask").hide();
				$(".rwpDialog").remove();
				$.rwpUI.alert(result.stateMsg, '操作提示',
						result.stateType == 0 ? 'success' : 'error', false);
				//rwpPluginHelper.menuHelper
				//.loadMenuInsidePage('${ctx}/policeServiceController/policeserviceList');
			},
			error : function(data) {
				$.rwpUI.warn("保存失败,请联系管理员！");
			}
		};
		$("#ajaxform").ajaxSubmit(option);
	}
	//返回按钮方法
	function closedia() {
		$(".rwpDialogMask").hide();
		$(".rwpDialog").remove();
	}
	$(function() {
		$("#parentName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectparent
		});
		$("#ajaxform1").rwpUIForm(
				{
					inputWidth : 'auto',
					isAjaxSubmit : true,
					ajaxSubmitAttrs : {
						beforeSubmit : function() {
							var option = {
								url : '${ctx}/macConfigController/saveParam',
								type : 'POST',
								// async: false,
								dataType : 'json',
								success : function(result) {
									$(".rwpDialogMask").hide();
									$(".rwpDialog").remove();
									$.rwpUI.alert(result.stateMsg, '操作提示',
											result.stateType == 0 ? 'success'
													: 'error', false);
									rwpPluginHelper.menuHelper
									.loadMenuInsidePage('${ctx}/macConfigController/addConfigModel?modelId=${modelId}');
								},
								error : function(data) {
									$.rwpUI.warn("保存失败,请联系管理员！");
								}
							};
							$("#ajaxform1").ajaxSubmit(option);
							return false;
						}
					}
				});
		if ($("#id").val() != null && $("#id").val() != "") {
			$("#img_info").attr(
					'src',
					'${ctx}/macConfigController/getIcon?id='
							+ $("#id").val() + '&rnd' + Math.random());
		}
	});
	//图标预加载效果
	$(".up")
			.change(
					function() {
						var $this = $(this);
						if (this.files && this.files[0]) {
							var reader = new FileReader();
							reader.onload = function(evt) {
								$this.parent().parent().prev().find("img")
										.attr("src", evt.target.result);
							};
							reader.readAsDataURL(this.files[0]);
						} else {//兼容性
							var filterAlpha = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
							this.select();
							if (navigator.userAgent.indexOf('MSIE 9.0')
									|| navigator.userAgent.indexOf('MSIE 10.0')) {
								//不是嵌套在iframe中直接执行file.blur
								if (window.location.href === window.parent.location.href) {
									this.blur();
								} else {
									//被套在iframe中的时候需要给页面上其他的DIV或者BUTTON执行focus
									window.parent.document.body.focus();
								}
							}
							var src = document.selection.createRange().text;
							var tempImage = $(this).parent().parent().prev()
									.find("img");
							tempImage.attr('src', src);
							//tempImage.show();
						}
					});
	//返回按钮事件
	// 	$("#back").click(function() {
	// 		$(".rwpDialogMask").hide();
	// 		$(".rwpDialog").remove();
	// 	});
	//表单提交事件
	// 	$("#gosub").click(function() {
	// 		$("#ajaxform").ajaxSubmit({
	// 			success : function(data) {
	// 				if (data.stateType == 0) {
	// 					$.rwpUI.success(data.stateMsg, false);
	// 				} else {
	// 					$.rwpUI.error(data.stateMsg, false);
	// 				}
	// 				if ("undefined" == data) {
	// 					$.rwpUI.warn('未知错误', false);
	// 				}
	// 				rwpMenuHelper.rebackLastPage();
	// 				setTimeout(function(){
	// 					$(".rwpDialogMask").hide();
	// 					$(".rwpDialog").remove();
	// 				},1000);
	// 			},
	// 			error : function() {
	// 				$.rwpUI.warn('请求错误', false);
	// 			}
	// 		});
	// 	});
</script>
<form action="" id="ajaxform1" method="post"
	enctype="multipart/form-data">
	<fieldset style="height: 500px;">
		<legend>
			<i class="i"></i>配置参数信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字。"
				data-val-required="ID不能为空" id="id" name="id" type="hidden" value="${id}" />
			<input name="modelId" value="${modelId}" type="hidden" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="file1">图标</label></li>
					<li><img src="${ctx}/photos/imgHdPic.jpg" width="100px;"
						height="145px;" id="img_info"
						onclick="document.getElementById('icon').click();"></li>
					<li class="editor-field"><a href="javascript:;" class="upbtn">选择图标<input
							style="width: 50px;" class="up" type="file" name="myImage" /></a></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="file1"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="title">标题</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="名称不能超过100字符" data-val-length-max="100"
						data-val-required="名称不能为空" id="title" name="title" type="text"
						value="${paramObject.title}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="title"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="businessType">业务类型</label></li>
					<li class="editor-field"><select id="businessType"
						name="businessType">
							<option <c:if test="${paramObject.businessType == 1}">selected=selected</c:if> value="1">机动车业务</option>
							<option <c:if test="${paramObject.businessType == 2}">selected=selected</c:if> value="2">驾驶证业务</option>
							<option <c:if test="${paramObject.businessType == 4}">selected=selected</c:if> value="4">违法业务</option>
					</select></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="businessType"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="topCoord">TOP坐标</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="TOP坐标不能超过20字符" data-val-length-max="20"
						data-val-required="TOP坐标不能为空" id="topCoord" name="topCoord"
						type="text" value="${paramObject.topCoord}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="topCoord"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="leftCoord">LEFT坐标</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="LEFT坐标不能超过20字符" data-val-length-max="20"
						data-val-required="LEFT坐标不能为空" id="leftCoord" name="leftCoord"
						type="text" value="${paramObject.leftCoord}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="leftCoord"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="parentId">所属父级(无父级可不选)</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-number="所属父级必须是一个数字" data-val-regex="请选择父级"
						data-val-regex-pattern="^[1-9]\d*|0$"
						data-val-required="所属父级字段是必需的" id="parentId" name="parentId"
						type="hidden" value="${paramObject.parentId}" /></li>
					<li class="editor-field"><input id="parentName"
						name="parentName" type="text" value="${paramObject.parentName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="parentId"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth" style="width:auto;">
					<li class="editor-label"><label for="remark">备注</label></li>
					<li class="editor-field"><textarea cols="20" data-val="true"
							data-val-length="备注不能超过50字符" data-val-length-max="50" id="remark"
							name="remark" rows="2" style=" width: 700px; height: 100px;"">${paramObject.remark}</textarea></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="remark"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>
<!-- <div class="clear" style="margin-left: 75%;">
	<div class="fPptions" style="float: left; margin-top: 4px;" id="gosub" onclick="gosub()">
		<a href="javascript:;" title="确定" class="btnLightBlue btn back"
			style="width: 50px; height: 32px;" id="rego"><i class="i"></i>确定</a>
	</div>
	<div class="fPptions" style="float: left; margin-top: 4px;" id="back" onclick="closedia()">
		<a href="javascript:;" title="返回" class="btnLightBlue btn back"
			style="width: 50px; height: 32px;" id="reback"><i class="i"></i>返回</a>
	</div>
</div> -->