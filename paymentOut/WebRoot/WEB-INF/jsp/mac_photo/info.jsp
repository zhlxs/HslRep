<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<style>
#documenttype li {
	float: left; /* 往左浮动 */
}
</style>
<script type="text/javascript">
	rwpTemp.f_selectphotoName = function() {
		var url = '${ctx}/macPhotoController/sampleDialog?type=3' + '&rnd='
				+ Math.random();
		return rwpPluginHelper
				.selectContact('选择样图', url, 'id', 'photoname', 'samplepathid',
						'bconfigstrcname', null, rwpTemp.f_setphotoName);
	};
	rwpTemp.f_setphotoName = function(node, value, text) {
		$("#sampleid").val(value);
		$("#bconfigstrcname").val(text);
	};
	$(function() {
		//样图名称
		$("#bconfigstrcname").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectphotoName
		});
		$("#ismustfill").rwpUIToggle();
		$('#manageTypeSwitch').rwpUISwitch();
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true
		});
	});
</script>
<form action="${ctx}/mpcConfigController/saveMacPhoto" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>图片信息
		</legend>
		<input id="id" name="id" type="hidden" value="${maConfig.id}" /> <input
			id="relationId" name="relationId" type="hidden" value="${winId}" />
		<div class="formpanel">
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="sampleid">样图</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-required="请选择图片名称" id="sampleid" name="photoId"
						type="hidden" value="${maConfig.photoId}" /></li>
					<li class="editor-field"><input id="bconfigstrcname"
						name="photoname" type="text"
						value="${maConfig.macPhoto.photoname}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="sampleid"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="ckbh">所属窗口</label></li>
					<li class="editor-field"><input data-val="true"
						readonly="readonly" type="text" value="${macWindows.ckbh}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="ckbh"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>