<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script src="${ctx}/js/form.js"
	integrity="sha384-tIwI8+qJdZBtYYCKwRkjxBGQVZS3gGozr3CtI+5JF/oL1JmPEHzCEnIKbDbLTCer"
	crossorigin="anonymous"></script>
<style type="text/css">
#preview {
	width: 100px;
	overflow: hidden;
}

#img_info {
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
}
</style>
<!--上传完图片立即显示效果js  -->
<script>
	function previewImage(file) {
		var MAXWIDTH = 100;
		var MAXHEIGHT = 145;
		var div = document.getElementById('preview');
		if (file.files && file.files[0]) {
			div.innerHTML = '<img id=img_info>';
			var img = document.getElementById('img_info');
			img.onload = function() {
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
						img.offsetWidth, img.offsetHeight);
				img.width = rect.width;
				img.height = rect.height;
				img.style.marginLeft = rect.left + 'px';
				img.style.marginTop = rect.top + 'px';
			};
			var reader = new FileReader();
			reader.onload = function(evt) {
				img.src = evt.target.result;
			};
			reader.readAsDataURL(file.files[0]);
		} else { //兼容IE
			var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
			file.select();
			var src = document.selection.createRange().text;
			div.innerHTML = '<img id=img_info>';
			var img = document.getElementById('img_info');
			img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width
					+ ',' + rect.height);
			div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
		}
	}

	function clacImgZoomParam(maxWidth, maxHeight, width, height) {
		var param = {
			top : 0,
			left : 0,
			width : width,
			height : height
		};
		if (width > maxWidth || height > maxHeight) {
			rateWidth = width / maxWidth;
			rateHeight = height / maxHeight;
			if (rateWidth > rateHeight) {
				param.width = maxWidth;
				param.height = Math.round(height / rateWidth);
			} else {
				param.width = Math.round(width / rateHeight);
				param.height = maxHeight;
			}
		}
		param.left = Math.round((maxWidth - param.width) / 2);
		param.top = Math.round((maxHeight - param.height) / 2);
		return param;
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#reback").click(function() {
			rwpMenuHelper.rebackLastPage();
		});
		if ("" != "${photoSample.id}") {
			$("#img_info").attr(
					'src',
					'${ctx}/photoSampleController/getphoto?id='
							+ $("#id").val() + '&rnd' + Math.random());
		}
	});

	$("#submitBtn").on(
			"click",
			function() {
				if (document.getElementById("describe").value == ""
						|| document.getElementById("describe").value == null) {
					document.getElementById("describe").value = document
							.getElementById("bconfigstrcname").value;
				}
				$("#ajaxform").ajaxSubmit(
						{
							success : function(data) {
								if (data.stateType == 0) {
									$.rwpUI.success(data.stateMsg, false);
								} else {
									$.rwpUI.error(data.stateMsg, false);
								}
								if ("undefined" == data) {
									$.rwpUI.warn('未知错误', false);
								}
								$("#img_info").attr(
										'src',
										'${ctx}/photoSampleController/getphoto?id='
												+ $("#id").val() + '&rnd'
												+ Math.random());
								rwpMenuHelper.rebackLastPage();
							},
							error : function() {
								$.rwpUI.warn('请求错误', false);
							}
						});
				//}
			});
	//文本域的默认值
	document.getElementById("describe").value = "${photoSample.describe}";
</script>
<div class="fAutoPage">
	<div class="fAutoPage">
		<form action="${ctx}/photoSampleController/saveSample" id="ajaxform"
			enctype="multipart/form-data" method="post">
			<fieldset>
				<legend>
					<i class="i"></i>新增样图信息
				</legend>
				<div class="formpanel">
					<input data-val="true" data-val-number="字段 ID 必须是一个数字。"
						data-val-required="ID不能为空" id="id" name="id" type="hidden"
						value="${photoSample.id}" />
					<div class="clear">
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="picfile">样图</label></li>
							<li id="preview"><img src="${ctx}/photos/imgHdPic.jpg"
								width="100px;" height="145px;" id="img_info"
								onclick="document.getElementById('photoSample').click();">
							</li>
							<li class="editor-field">上传样图：</li>
							<li><input type="file" name="picfile" id="picfile"
								style="width: 200px;" onchange="previewImage(this)" /></li>
							<li class="editor-validation"><span
								class="field-validation-valid" data-valmsg-for="file1"
								data-valmsg-replace="true"></span></li>
						</ul>
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="bconfigstrcname">数据中文名称</label></li>
							<li class="editor-field"><input data-val="true"
								data-val-length="不能超过100字符" data-val-length-max="100"
								data-val-required="不能为空" id="bconfigstrcname"
								name="bconfigstrcname" type="text"
								value="${photoSample.bconfigstrcname }" /></li>
							<li class="editor-validation"><span
								class="field-validation-valid" data-valmsg-for="bconfigstrcname"
								data-valmsg-replace="true"></span></li>
							<li class="editor-field"><input name="isvalid" type="hidden"
								value="true" /></li>
							<li class="editor-validation"><span
								class="field-validation-valid" data-valmsg-for="isvalid"
								data-valmsg-replace="true"></span></li>
						</ul>
						<div class="clear">
							<ul class="DialabelWidth" style="width:auto;margin-left: -18px;">
								<li class="editor-label"><label for="describe">样图描述</label>
								</li>
								<li class="editor-field"><textarea cols="20"
										data-val="true" data-val-length="样图描述不能超过200字符"
										data-val-length-max="200" id="describe" name="describe"
										rows="2" data-val-required="样图描述不能为空"
										style=" width: 750px; height: 60px;"">${photoSample.describe}</textarea></li>
								<li class="editor-validation"><span
									class="field-validation-valid" data-valmsg-for="describe"
									data-valmsg-replace="true"></span></li>
							</ul>
						</div>
					</div>
				</div>
			</fieldset>
			<div class="fPptions" style="text-align: center;margin-left: -250px;">
				<input type="button" id="submitBtn" value="提交"
					style="height: 30px;width: 80px;line-height: 25px;"
					class="btnLightBlue btn" /> <a href="javascript:void(0);"
					title="返回" class="btnLightBlue btn back"
					style="width: 65px; height: 30px;" id="reback"><i class="i"></i>返回</a>
			</div>
		</form>
	</div>