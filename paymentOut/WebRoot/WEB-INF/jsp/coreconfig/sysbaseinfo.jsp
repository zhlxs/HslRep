<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#provinceName").rwpUICombobox({
			valueField : 'ID',
			textField : 'cityName',
			valueFieldDomID : 'provinceID',
			url : '?parentid=0&citytype=0&islazy=1'
		});

		$("#ajaxform-sysbaseinfo").rwpUIForm({
			isAjaxSubmit : true,
			inputWidth : 'auto',
			ajaxSubmitAttrs : {
				isDialog : true
			}
		});
	});
	function reloadPage(){
		setTimeout(function(){window.location.reload();},1500);
	}
</script>
<form action="${ctx }/configController/saveSysBaseInfo"
	id="ajaxform-sysbaseinfo" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>系统参数
		</legend>
		<div class="formpanel">
			<ul class="clear">
				<li class="editor-label"><label for="sysName">系统名称</label></li>
				<li class="editor-field"><input data-val="true" style="width: 240px;"
					data-val-length="系统名称不能超过50字符" data-val-length-max="50"
					data-val-required="系统名称不能为空" id="sysName" name="sysName"
					type="text" value="${sysBaseInfo.sysName}" /></li>
				<li class="editor-validation"><span
					class="field-validation-valid" data-valmsg-for="sysName"
					data-valmsg-replace="true"></span></li>
			</ul>
			<ul class="clear">
				<li class="editor-label"><label for="copyrightInfo">版权信息</label>
				</li>
				<li style="float: left;"><textarea cols="20" data-val="true"
						data-val-length="版权信息不能超过500字符" data-val-length-max="500"
						data-val-required="版权信息不能为空" id="copyrightInfo"
						name="copyrightInfo" rows="2"
						style=" width: 320px; height: 100px; margin-left:3px;">${sysBaseInfo.copyrightInfo }</textarea>
				</li>
				<li class="editor-validation"><span
					class="field-validation-valid" data-valmsg-for="copyrightInfo"
					data-valmsg-replace="true"></span></li>
			</ul>
		</div>
	</fieldset>
	<div class="editor-submit">
		<input type="submit" value="确定" class="inputbtn cancel" onclick="reloadPage()" />
	</div>
</form>