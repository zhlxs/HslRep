<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script src="${ctx}/js/jquery-form.js"></script>
<style>
table {
	*border-collapse: collapse; /* IE7 and lower */
	border-spacing: 0;
	/* width: 1100px; */
	font-family: "Microsoft YaHei";
}

.bordered {
	width: 1000px;
	border: solid #ccc 1px;
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	-webkit-box-shadow: 0 1px 1px #ccc;
	-moz-box-shadow: 0 1px 1px #ccc;
	box-shadow: 0 1px 1px #ccc;
}

.bordered tr:hover {
	background: #fbf8e9;
	-o-transition: all 0.1s ease-in-out;
	-webkit-transition: all 0.1s ease-in-out;
	-moz-transition: all 0.1s ease-in-out;
	-ms-transition: all 0.1s ease-in-out;
	transition: all 0.1s ease-in-out;
}

.bordered td,.bordered th {
	border-left: 1px solid #ccc;
	border-top: 1px solid #ccc;
	padding: 5px;
	text-align: left;
}

.bordered th {
	background-color: #dce9f9;
	background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc),
		to(#dce9f9));
	background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
	background-image: -moz-linear-gradient(top, #ebf3fc, #dce9f9);
	background-image: -ms-linear-gradient(top, #ebf3fc, #dce9f9);
	background-image: -o-linear-gradient(top, #ebf3fc, #dce9f9);
	background-image: linear-gradient(top, #ebf3fc, #dce9f9);
	-webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .8) inset;
	-moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .8) inset;
	box-shadow: 0 1px 0 rgba(255, 255, 255, .8) inset;
	border-top: none;
	text-shadow: 0 1px 0 rgba(255, 255, 255, .5);
	padding: 10px;
}

.bordered td:first-child,.bordered th:first-child {
	border-left: none;
}

.bordered th:first-child {
	-moz-border-radius: 6px 0 0 0;
	-webkit-border-radius: 6px 0 0 0;
	border-radius: 6px 0 0 0;
}

.bordered th:last-child {
	-moz-border-radius: 0 6px 0 0;
	-webkit-border-radius: 0 6px 0 0;
	border-radius: 0 6px 0 0;
}

.bordered th:only-child {
	-moz-border-radius: 6px 6px 0 0;
	-webkit-border-radius: 6px 6px 0 0;
	border-radius: 6px 6px 0 0;
}

.bordered tr:last-child td:first-child {
	-moz-border-radius: 0 0 0 6px;
	-webkit-border-radius: 0 0 0 6px;
	border-radius: 0 0 0 6px;
}

.bordered tr:last-child td:last-child {
	-moz-border-radius: 0 0 6px 0;
	-webkit-border-radius: 0 0 6px 0;
	border-radius: 0 0 6px 0;
}

.showDefault {
	background: url("${ctx}/images/select3.png") no-repeat center center;
	/*background-size: 60px 30px;*/
}

.upbtn {
	/*a标签的样式*/
	padding: 4px 10px;
	height: 20px;
	line-height: 20px;
	position: relative;
	border: 3px solid #6FB3E0;
	text-decoration: none;
	color: white;
	background-color: #6FB3E0;
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
</style>
<script type="text/javascript">
	$("#ajaxform").rwpUIForm({
		inputWidth : 'auto',
		//isAutoPlugin : false,
		isAjaxSubmit : true,
		ajaxSubmitAttrs : {
			isDialog : true,
			submitOk : rwpMenuHelper.rebackLastPage
		}
	});
	function getJurl() {
		var modelId = $("#modelId").val();
		var jurl = '${ctx}/macConfigController/configParamsList?1=1';
		if (modelId != null && modelId != '') {
			jurl += '&modelId=' + modelId;
		}
		return jurl;
	}
	function getHtml(jurl) {
		$(".rwpMidLoading").show();
		$.post(jurl, function(result) {
			//console.log(result);
			$("#datagrid").empty();
			$("#datagrid").html(result);
			$(".rwpMidLoading").hide();
		});
	}
	$("#seachbtn").click(function() {
		var url = getJurl();
		getHtml(url);
	});
	$(function() {
		$("#seachbtn").click();
	});
	$("#gosub").click(function() {
		$("#gosub").submit();
	});
	$("#back").click(function() {
        rwpMenuHelper.rebackLastPage();
    });
</script>
<div class="fAutoPage">
	<form action="${ctx}/macConfigController/saveModel" id="ajaxform"
		method="post" enctype="multipart/form-data">
		<fieldset>
			<legend>
				<i class="i"></i>新增配置模板信息
			</legend>
			<input data-val="true" id="modelId" name="modelId" type="hidden"
				value="${model.id}" />
			<div class="formpanel">
				<div class="clear">
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="modelName">模板名称</label>
						</li>
						<li class="editor-field"><input data-val="true"
							data-val-length="模板名称不能超过20字符" data-val-length-max="20"
							data-val-required="模板名称不能为空" id="modelName" name="modelName"
							type="text" value="${model.modelname}" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="modelName"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="isStart">是否启用</label></li>
						<li class="editor-field"><input data-val="true"
							data-val-required="是否启用字段是必需的" id="isStart" name="isStart"
							<c:if test="${model.isStart == 1}">
                                    checked=checked
                                </c:if>
							type="checkbox" value="1" /><input name="isStart" type="hidden"
							value="0"></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="isStart"
							data-valmsg-replace="1"></span></li>
					</ul>
				</div>
		</fieldset>
		<%--模板参数管理--%>
		<fieldset>
			<legend>
				<i class="i"></i>模板参数管理
			</legend>
			<dl style="width:275px;display: none;">
				<dd class="inputWidth">
					<a href="javascript:void(0);" id="seachbtn" class="rwpButton">查找</a>
				</dd>
				<dd class="inputWidth">
					<a href="javascript:void(0);" id="seachreset" class="rwpButton">重置</a>
				</dd>
			</dl>
			<div data-options="{ 'region':'center' }">
				<div class="rwpMidLoading" style="display: none;">数据加载中...</div>
				<div class="fAutoPage"
					style="width: 100%; height: 100%; overflow: auto;">
					<div id="datagrid" class="datagrid" style="width: auto;"></div>
				</div>
			</div>
		</fieldset>
		<div class="clear" style="margin-left: 20px;">
			<div class="fPptions" style="float: left; margin-top: 4px;"
				id="gosub">
				<a href="javascript:;" title="确定" class="btnLightBlue btn back"
					style="width: 50px; height: 32px;" id="rego"><i class="i"></i>确定</a>
			</div>
			<div class="fPptions" style="float: left; margin-top: 4px;" id="back">
				<a href="javascript:;" title="返回" class="btnLightBlue btn back"
					style="width: 50px; height: 32px;" id="reback"><i class="i"></i>返回</a>
			</div>
		</div>
	</form>
</div>