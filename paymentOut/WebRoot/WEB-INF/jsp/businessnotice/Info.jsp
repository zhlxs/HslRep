<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script src="${ctx}/js/jquery-form.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/ueditor.all.min.js"></script>
<style>
.edui-popup-content.edui-default {
	height: auto !important;
}

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
	margin-top: -3%;
}
</style>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript">
	$(function() {
		$("#ajaxform").rwpUIForm({
			inputWidth : 'auto',
			isAjaxSubmit : true,
			ajaxSubmitAttrs : {}
		});
	});
</script>
<form action="${ctx}/dicBusinessnoticeController/saveBusinessnotice" id="ajaxform" method="post"
	enctype="multipart/form-data">
	<fieldset>
		<legend>
			<i class="i"></i>新增办事流程信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字"
				data-val-required="ID不能为空" id="id" name="id" type="hidden"
				value="${businessnotice.id}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="name">名称(中文)</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="名称不能超过100字符" data-val-length-max="100"
						data-val-required="名称不能为空" id="name"
						name="name" type="text"
						value="${businessnotice.name}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="name"
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
						href="javascript:void(0);"><i class="i i1"></i>经办流程</a></span> <span
						class="rwpTabNavItem" name="pass" id="_sqcl"><a
						href="javascript:void(0);"><i class="i i2"></i>办事依据</a></span>
				</div>
			</div>
			<div class="clear parmdiv _sqtj content">
				<ul class="DialabelWidth" style="width:auto;">
					<li class="editor-label"><label for="sqtj">经办流程</label></li>
					<li class="editor-field"><textarea cols="20" data-val="true"
							data-val-length="经办流程不能超过1300字符" data-val-length-max="1300"
							id="sqtj" name="businessNotice" rows="2" data-val-required="经办流程不能为空"
							style=" width: 700px; height: 100px;"">${businessnotice.businessNotice}</textarea></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="sqtj"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear parmdiv _sqcl content">
				<ul class="DialabelWidth" style="width:auto;">
					<li class="editor-label"><label for="sqcl">办事依据</label></li>
					<li class="editor-field"><textarea cols="20" data-val="true"
							data-val-length="办事依据不能超过1300字符" data-val-length-max="1300"
							id="sqcl" name="mattersneedAttendtion" rows="2" data-val-required="办事依据不能为空"
							style=" width: 700px; height: 100px;"">${businessnotice.mattersneedAttendtion}</textarea></li>
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
</form>