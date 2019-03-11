<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script src="${ctx}/js/jquery-form.js"></script>
<style>
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
	height: -1%;
}
</style>
<script type="text/javascript">
	rwpTemp.f_selectuserdeptok = function(node, value, text) {
		value = value || 0; //没选择部门时赋值为0
		$('#deptID').val(value);
		$('#deptName').val(text);
	};

	rwpTemp.f_selectuserdept = function() {
		var url = '${ctx}/deptController/deptDialog';
		return rwpPluginHelper.selectContact('选择所属部门', url, 'ID', 'deptName',
				'deptID', '', null, rwpTemp.f_selectuserdeptok);
	};
	$(function() {
		if ($("#id").val() != null && $("#id").val() != "") {
			$("#img_info").attr(
					'src',
					'${ctx}/accountManagerController/getIcon?id='
							+ $("#id").val() + '&rnd' + Math.random());
		}
		$("#deptName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectuserdept
		});
		$("#ajaxform")
				.rwpUIForm(
						{
							isAjaxSubmit : true,
							inputWidth : 'auto',
							ajaxSubmitAttrs : {
								beforeSubmit : function() {
									var option = {
										url : '${ctx}/accountManagerController/saveAccount',
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
													.loadMenuInsidePage('${ctx}/accountManagerController/accountList');
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
</script>
<form action="" id="ajaxform" method="post"
	enctype="multipart/form-data">
	<fieldset>
		<legend>
			<i class="i"></i>新增账号信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字" id="id"
				name="id" type="hidden" value="${account.id}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="file1">用户照片</label></li>
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
					<li class="editor-label"><label for="deptID">所属部门</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-number="字段所属部门必须是一个数字" data-val-regex="请选择所属部门"
						data-val-regex-pattern="^[1-9]\d*|0$"
						data-val-required="所属部门字段是必需的。" id="deptID" name="deptid"
						type="hidden" value="${account.deptid}" /></li>
					<li class="editor-field"><input id="deptName" name="deptName"
						type="text" value="${account.dept.deptName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="deptID"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="account">账号</label></li>
					<li class="editor-field"><input data-val="true"
						style="width: 150px;" data-val-length="账号不能超过50字符"
						data-val-length-max="50" data-val-required="账号不能为空" id="account"
						name="account" type="text" value="${account.account}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="account"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="password">密码</label></li>
					<li class="editor-field"><input data-val="true"
						style="width: 150px;" maxlength="32" data-val-length="密码不能超过50字符"
						data-val-length-max="50" data-val-required="密码不能为空" id="password"
						name="password" type="password" value="${account.password}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="password"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="name">姓名</label></li>
					<li class="editor-field"><input data-val="true"
						style="width: 150px;" data-val-length="姓名不能超过50字符"
						data-val-length-max="50" data-val-required="姓名不能为空" id="name"
						name="name" type="text" value="${account.name}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="name"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="jynumber">警员编号</label></li>
					<li class="editor-field"><input data-val="true"
						style="width: 150px;" maxlength="32" data-val-length="警员编号不能超过50字符"
						data-val-length-max="50" data-val-required="警员编号不能为空" id="jynumber"
						name="jynumber" type="text" value="${account.jynumber}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="jynumber"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<%-- <ul class="DialabelWidth">
					<li class="editor-label"><label for="ableacceptcount">单人单日可取号数</label></li>
					<li class="editor-field"><input data-val="true"
						class="ableacceptcount" style="width: 120px;"
						data-val-length="取号数不能超过2字符" data-val-length-max="50"
						data-val-required="取号数不能为空" id="ableacceptcount"
						name="ableacceptcount" type="text"
						value="${address.ableacceptcount}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="ableacceptcount"
						data-valmsg-replace="true"></span></li>
				</ul> --%>
				<!-- <script type="text/javascript">
					$('.ableacceptcount').rwpUISpinner({
						type : 'int',
						minValue : 0,
						maxValue : 99,
						defaultValueText : ''
					});
				</script> -->
			</div>
			<%-- <div class="clear">
				<ul class="">
					<li class="editor-label"><label for="address">地址</label></li>
					<li class="editor-field"><textarea data-val="true"
							style="width: 440px;" data-val-length="地址不能超过200字符"
							data-val-length-max="200" data-val-required="地址不能为空" id="address"
							name="address" type="text">${address.address}</textarea></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="address"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div> --%>
		</div>
		</div>
	</fieldset>
</form>