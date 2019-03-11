<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#reback").click(function() {
			rwpMenuHelper.rebackLastPage();
		});
	})
</script>

<div class="fAutoPage">
	<div class="fAutoPage">
		<form id="ajaxform">
			<input data-val="true" data-val-number="字段 日志ID 必须是一个数字。"
				data-val-required="日志ID不能为空" id="ID" name="ID" type="hidden"
				value="${logHelper.log.id} " />
			<fieldset>
				<legend>
					<i class="i"></i>操作信息
				</legend>
				<div class="formpanel">
					<div class="clear">
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="areaName">操作区域</label>
							</li>
							<li class="editor-field"><input style="font-family: 黑体;" value="${logHelper.log.areaName}" readonly="readonly"></li>
						</ul>
					</div>
					<div class="clear">
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="controName">操作控制器</label>
							</li>
							<li class="editor-field"><input style="font-family: 黑体;" value="${logHelper.log.controlName}" readonly="readonly"></li>
						</ul>
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="controDisplay">控制器说明</label>
							</li>
							<li class="editor-field"><input style="font-family: 黑体;" value="${logHelper.log.controlDisplay}" readonly="readonly"></li>
						</ul>
					</div>
					<div class="clear">
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="actionName">操作动作</label>
							</li>
							<li class="editor-field"><input style="font-family: 黑体;" value="${logHelper.log.actionName}" readonly="readonly"></li>
						</ul>
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="actionDisplay">动作说明</label>
							</li>
							<li class="editor-field"><input style="font-family: 黑体;" value="${logHelper.log.actionDisplay}" readonly="readonly"></li>
						</ul>
					</div>
				</div>
			</fieldset>
			<fieldset>
				<legend>
					<i class="i"></i><label for="parameterJson">参数json字符串</label>
				</legend>
				<div class="formpanel">
					<div class="clear">
						<ul class="DialabelWidth" style="width: 100%;">
							<li class="editor-field" style="width: 100%;">${logHelper.log.parameterJson}</li>
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