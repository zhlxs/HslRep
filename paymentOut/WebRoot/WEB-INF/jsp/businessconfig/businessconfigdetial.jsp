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
			<input data-val="true" data-val-number="字段 日志ID 必须是一个数字。"
				data-val-required="日志ID不能为空" id="id" name="id" type="hidden"
				value="${weBusinessconfig.id}" />
			<fieldset>
				<legend>
					<i class="i"></i>流程信息
				</legend>
				<div class="formpanel">
					<div class="clear">
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="businessname">业务流程名称</label></li>
							<li class="editor-field">${weBusinessconfig.businessname}</li>
						</ul>
					</div>
					<div class="clear">
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="businessCode">业务流程编码</label></li>
							<li class="editor-field">${weBusinessconfig.businesscode }</li>
						</ul>
						<ul class="DialabelWidth">
							<li class="editor-label"><label for="parentId">流程所属类型</label></li>
							<li class="editor-field">
							<c:if test="${empty businessconfigHelper.businessConfig.parentName}">
							${weBusinessconfig.businessname}</c:if>
							<c:if test="${!empty businessconfigHelper.businessConfig.parentName}">
							${businessconfigHelper.businessConfig.parentName}
								</c:if>
							</li>
						</ul> 
					</div>
					<div class="clear" style="width:500px;">
						<ul class="DialabelWidth"  style="width:650px;">
				<li class="editor-label"><label for="copyrightInfo">办理须知</label></li>
				<li class="editor-field">${weBusinessconfig.businessnotice}</li>
				</div>
				<div class="clear">
						<ul class="DialabelWidth" style="width:650px;">
							<li class="editor-label"><label for="mattersneedattention">办理依据</label>
						<li class="editor-field">
						${weBusinessconfig.mattersneedattention}</li>	
							
							<%-- <li class="editor-field">
							<textarea cols="20" data-val="true"
							data-val-length="注意事项不能超过800字符" data-val-length-max="800"
							id="mattersneedattention" name="mattersneedattention" rows="2"
							data-val-required="注意事项不能为空"
							style=" width: 550px; height: 100px;">
${wE_BusinessConfig.mattersneedattention}</textarea></li> --%>
							<%-- ${wE_BusinessConfig.mattersneedattention} 
							//文本域的默认值
		document.getElementById("businessnotice").value="${wE_BusinessConfig.businessnotice}";
		//文本域的默认值
		document.getElementById("mattersneedattention").value="${wE_BusinessConfig.mattersneedattention}";--%>
					</ul>
				</div>
				</div>
			</fieldset>
			<%-- <fieldset>
				<legend>
					<i class="i"></i><label for="parameterJson">参数json字符串</label>
				</legend>
				<div class="formpanel">
					<div class="clear">
						<ul class="DialabelWidth" style="width: 100%;">
							<li class="editor-field" style="width: 100%;">${log.parameterJson}</li>
						</ul>
					</div>
				</div>
			</fieldset> --%>
			<div class="fPptions" style="text-align: center;">
				<a href="javascript:void(0);" title="返回"
					class="btnLightBlue btn back" style="width: 50px; height: 28px;"
					id="reback"><i class="i"></i>返回</a>
			</div>
		</form>
	</div>
</div>