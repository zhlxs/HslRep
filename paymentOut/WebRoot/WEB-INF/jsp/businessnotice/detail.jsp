<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#reback").click(function() {
			rwpMenuHelper.rebackLastPage();
		});
	});
</script>
<div class="fAutoPage">
	<div class="fAutoPage">
		<form id="ajaxform">
			<input data-val="true" data-val-number="字段日志ID必须是一个数字。"
				data-val-required="日志ID不能为空" id="id" name="id" type="hidden"
				value="${businessnotice.id}" />
			<fieldset>
				<legend>
					<i class="i"></i>办事流程信息
				</legend>
				<div class="formpanel">
					<div class="clear" style="width:500px;">
						<ul class="DialabelWidth" style="width:650px;">
							<li class="editor-label"><label for="businessNotice">经办流程</label></li>
							<li class="editor-field">${businessnotice.businessNotice}</li>
					</div>
					<div class="clear">
						<ul class="DialabelWidth" style="width:650px;">
							<li class="editor-label"><label for="mattersneedAttendtion">办理依据</label>
							<li class="editor-field">
								${businessnotice.mattersneedAttendtion}</li>
						</ul>
					</div>
				</div>
			</fieldset>
			<div class="fPptions" style="text-align: center;">
				<a href="javascript:void(0);" title="返回"
					class="btnLightBlue btn back" style="width: 50px; height: 28px;"
					id="reback"><i class="i"></i>返回</a>
			</div>
		</form>
	</div>
</div>