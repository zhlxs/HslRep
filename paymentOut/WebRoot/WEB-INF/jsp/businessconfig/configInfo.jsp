<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script src="${ctx}/js/jquery-form.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/ueditor.all.min.js"></script>
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
rwpTemp.f_selectnoticeok = function(node, value, text) {
	value = value || 0; //没选择部门时赋值为0
	$('#noticeId').val(value);
	$('#noticename').val(text);
};

rwpTemp.f_selectnotice = function() {
	var url = '${ctx}/dicBusinessnoticeController/noticeDialog?actiontype=selectmodel&modelId=' + $("#noticeId").val();
	return rwpPluginHelper.selectContact('选择模板', url, 'id', 'name',
			'noticeId', '', null, rwpTemp.f_selectnoticeok);
};
	$(function() {
		$("#noticename").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectnotice
		});
		$("#ajaxform")
				.rwpUIForm(
						{
							inputWidth : 'auto',
							isAjaxSubmit : true,
							ajaxSubmitAttrs : {
								beforeSubmit : function() {
									var option = {
										url : '${ctx}/businessconfigController/saveBusinessconfig',
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
													.loadMenuInsidePage('${ctx}/policeServiceController/policeserviceList');
										},
										error : function(data) {
											$.rwpUI.warn("保存失败,请联系管理员！");
										}
									};
									$("#ajaxform").ajaxSubmit(option);
									return false;
								}
							}
						});
		if ($("#id").val() != null && $("#id").val() != "") {
			$("#img_info").attr(
					'src',
					'${ctx}/businessconfigController/getIcon?id='
							+ $("#id").val() + '&rnd' + Math.random());
		}
		if ("1" == 1)
			$('#ulIsSys').show(); //系统级管理员 可设置菜单的系统级权限
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
<form action="" id="ajaxform" method="post"
	enctype="multipart/form-data">
	<fieldset style="height: 500px;">
		<legend>
			<i class="i"></i>业务配置信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字。"
				data-val-required="ID不能为空" id="id" name="id" type="hidden"
				value="${businessConfig.id}" /> <input name="serCode"
				value="${serCode}" type="hidden" />
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
					<li class="editor-label"><label for="noticeId">经办流程、办事依据</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-number="模板主键必须是一个数字" data-val-regex="请选择配置模板"
						data-val-regex-pattern="^[1-9]\d*|0$"
						data-val-required="模板主键字段是必需的" id="noticeId" name="noticeId"
						type="hidden" value="${noticeId}" /></li>
					<li class="editor-field"><input id="noticename" name="noticename"
						type="text" value="${noticename}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="noticeId"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<!-- 选项卡js -->
			<script type="text/javascript">
				$(function() {
					$(".content:gt(0)").hide();
					$(".rwpTab .rwpTabNavList .rwpTabNavItem").bind(
							"click",
							function() {
								$(".rwpTab .rwpTabNavList .rwpTabNavItem")
										.removeClass("rwpTabCurrent");
								$(this).addClass("rwpTabCurrent");
								var divname = $(this).attr("id");
								$(".parmdiv").hide();
								$("." + divname).show();
							});
				});
			</script>
			<!-- 选项卡 -->
			<div class="rwpTab">
				<div class="rwpTabNavList">
					<span class="rwpTabNavItem rwpTabCurrent" name="wait" id="_sqtj"><a
						href="javascript:void(0);"><i class="i i1"></i>申请条件</a></span> <span
						class="rwpTabNavItem" name="pass" id="_sqcl"><a
						href="javascript:void(0);"><i class="i i2"></i>申请材料</a></span>
				</div>
			</div>
			<div class="clear parmdiv _sqtj content">
				<ul class="DialabelWidth" style="width:auto;">
					<li class="editor-label"><label for="sqtj">申请条件</label></li>
					<li class="editor-field"><textarea cols="20" data-val="true"
							data-val-length="申请条件不能超过1300字符" data-val-length-max="1300"
							id="sqtj" name="sqtj" rows="2" data-val-required="申请条件不能为空"
							style=" width: 700px; height: 100px;"">${businessConfig.sqtj}</textarea></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="sqtj"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear parmdiv _sqcl content">
				<ul class="DialabelWidth" style="width:auto;">
					<li class="editor-label"><label for="sqcl">申请材料</label></li>
					<li class="editor-field"><textarea cols="20" data-val="true"
							data-val-length="申请材料不能超过1300字符" data-val-length-max="1300"
							id="sqcl" name="sqcl" rows="2" data-val-required="申请材料不能为空"
							style=" width: 700px; height: 100px;"">${businessConfig.sqcl}</textarea></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="sqcl"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<script type="text/javascript">
				//实例化编辑器
				//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
				var ue = UE.getEditor('sqtj', {
					initialFrameWidth : 680,//设置编辑器宽度
					initialFrameHeight : 100,//设置编辑器高度
					scaleEnabled : true
				});
				var ue = UE.getEditor('sqcl', {
					initialFrameWidth : 680,//设置编辑器宽度
					initialFrameHeight : 100,//设置编辑器高度
					scaleEnabled : true
				});
			</script>
		</div>
	</fieldset>
	<!-- 	<div class="clear" style="margin-left: 75%;"> -->
	<!-- 		<div class="fPptions" style="float: left; margin-top: 4px;" id="gosub"> -->
	<!-- 			<a href="javascript:;" title="确定" class="btnLightBlue btn back" -->
	<!-- 				style="width: 50px; height: 32px;" id="rego"><i class="i"></i>确定</a> -->
	<!-- 		</div> -->
	<!-- 		<div class="fPptions" style="float: left; margin-top: 4px;" id="back"> -->
	<!-- 			<a href="javascript:;" title="返回" class="btnLightBlue btn back" -->
	<!-- 				style="width: 50px; height: 32px;" id="reback"><i class="i"></i>返回</a> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
</form>