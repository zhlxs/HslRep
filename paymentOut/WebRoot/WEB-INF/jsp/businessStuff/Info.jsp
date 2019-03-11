<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<style>
#documenttype li {
	float: left; /* 往左浮动 */
}
</style>
<script type="text/javascript">
	rwpTemp.f_selectphotoName = function() {
		var url = '${ctx}/photoSampleController/sampleDialog?id='
				+ $("#sampleid").val() + '&isall=1' + '&rnd'
				+ Math.random();
		return rwpPluginHelper.selectContact('选择样图', url, 'id',
				'bconfigstrcname', 'samplepathid', 'bconfigstrcname', null,
				rwpTemp.f_setphotoName);
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
<form action="${ctx}/businessconfigStuController/saveBusinessStuff"
	id="ajaxform" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>新增申请材料信息
		</legend>
		<input data-val="true" data-val-number="字段材料ID必须是一个数字"
			data-val-required="材料ID字段是必需的" id="id" name="id" type="hidden"
			value="${weBusinessconfigstr.id }" /> <input data-val="true"
			data-val-number="字段材料ID必须是一个数字" data-val-required="材料ID字段是必需的"
			id="ordercode" name="ordercode" type="hidden"
			value="${weBusinessconfigstr.ordercode}" /> <input data-val="true"
			data-val-number="字段ID必须是一个数字" data-val-required="ID不能为空"
			id="applytypeid" name="applytypeid" type="hidden"
			value="${weApplytype.id}" />
		<div class="formpanel">
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="bconfigstrname">申请材料名称(中文)</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="材料名称不能超过100字符" data-val-length-max="100"
						data-val-required="材料名称不能为空" id="bconfigstrname"
						name="bconfigstrname" type="text"
						value="${weBusinessconfigstr.bconfigstrname}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="bconfigstrname"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="applyTypeName">业务类型名称</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="业务流程不能超过100字符" data-val-length-max="100"
						data-val-required="业务流程不能为空" id="applyTypeName"
						name="applyTypeName" type="text" readonly
						value="${weApplytype.applyTypeName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="applyTypeName"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="sampleid">样图</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-required="请选业务材料样图名" id="sampleid"
						name="sampleid" type="hidden"
						value="${weBusinessconfigstr.sampleid}" /></li>
					<li class="editor-field"><input id="bconfigstrcname"
						name=bconfigstrcname type="text"
						value="${weBusinessconfigstr.photoSample.bconfigstrcname}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="sampleid"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="datatype">上传数据类型</label></li>
					<li class="editor-field"><select id="datatype" name="datatype">
							<c:if test="${empty weBusinessconfigstr.datatype }">
								<!-- <option value="">请选择</option> -->
								<!-- <option value="0">文本</option> -->
								<!-- <option value="1">数字</option> -->
								<option value="2">图片</option>
								<!-- <option value="3">时间</option> -->
							</c:if>
							<c:if test="${!empty weBusinessconfigstr.datatype }">
								<c:if test="${weBusinessconfigstr.datatype==0}">
									<option value="${weBusinessconfigstr.datatype}">文本</option>
									<option value="1">数字</option>
									<option value="2">图片</option>
									<option value="3">时间</option>
								</c:if>
								<c:if test="${weBusinessconfigstr.datatype==1 }">
									<option value="${weBusinessconfigstr.datatype}">数字</option>
									<option value="0">文本</option>
									<option value="2">图片</option>
									<option value="3">时间</option>
								</c:if>
								<c:if test="${weBusinessconfigstr.datatype==2 }">
									<option value="${weBusinessconfigstr.datatype}">图片</option>
									<!-- <option value="0">文本</option> -->
									<!-- <option value="1">数字</option> -->
									<!-- <option value="3">时间</option> -->
								</c:if>
								<c:if test="${weBusinessconfigstr.datatype==3}">
									<option value="${weBusinessconfigstr.datatype}">时间</option>
									<option value="0">文本</option>
									<option value="1">数字</option>
									<option value="2">图片</option>
								</c:if>
							</c:if>
					</select></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="datatype"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<div
					style="float:left;width:260px;margin-left:0px;margin-bottom:10px;">
					<div class="tbCol c1">材料类型</div>
					<!--//itemCol结束-->
					<div class="tbCol">
						<div id="manageTypeSwitch">
							<input id="showtype-detoxification" data-val="true"
								data-val-number="字段展示状态必须是一个数字" data-val-required="展示状态不得为空"
								checked="checked" name="showtype" value="0"
								style="display: none;" type="radio"> <label
								class="rwpSwitchItem first" for="showtype-detoxification">样图</label>
							<!-- <label class="rwpSwitchItem active" for="showtype-recovery">附件</label> -->
							<!-- <label class="rwpSwitchItem last" for="showtype-registeredperson">两者都展示</label> -->
							<input id="showtype-recovery"
								<c:if test="${weBusinessconfigstr.showtype==1}">checked="checked"</c:if>
								name="showtype" value="1" style="display: none;" type="radio">
							<input id="showtype-registeredperson"
								<c:if test="${weBusinessconfigstr.showtype==2}">checked="checked"</c:if>
								name="showtype" value="2" style="display: none;" type="radio">
						</div>
					</div>
					<!--//tbCol结束-->
				</div>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="ismustfill">是否启用</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="是否启用字段是必需的。" id="ismustfill" name="ismustfill"
						<c:if test="${weBusinessconfigstr.ismustfill}">
							checked=checked
						</c:if>
						type="checkbox" value="true" /><input name="ismustfill"
						type="hidden" value="false" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="ismustfill"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>