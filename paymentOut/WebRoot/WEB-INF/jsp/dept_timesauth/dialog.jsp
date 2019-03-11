<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<style>
.rwpDialog {
	margin-top: -52px;
}
</style>
<script type="text/javascript">
	//日期转为字符串方法
	function dateToString(date) {
		//date = new Date();
		var year = date.getFullYear();
		var month = (date.getMonth() + 1).toString();
		var day = (date.getDate()).toString();
		var hour = (date.getHours()).toString();
		var min = (date.getMinutes()).toString();
		if (month.length == 1) {
			month = "0" + month;
		}
		if (day.length == 1) {
			day = "0" + day;
		}
		if (hour.length == 1) {
			hour = "0" + hour;
		}
		if (min.length == 1) {
			min = "0" + min;
		}
		var dateTime = year + "-" + month + "-" + day + " " + hour + ":" + min;
		return dateTime;
	}
	$(function() {
		//时间格式化
		Date.prototype.Format = function(fmt) { //author: meizz
			var o = {
				"M+" : this.getMonth() + 1, //月份
				"d+" : this.getDate(), //日
				"h+" : this.getHours(), //小时
				"m+" : this.getMinutes(), //分
				"s+" : this.getSeconds(), //秒
				"q+" : Math.floor((this.getMonth() + 3) / 3), //季度
				"S" : this.getMilliseconds()
			};
			if (/(y+)/.test(fmt))
				fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
						.substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(fmt))
					fmt = fmt.replace(RegExp.$1,
							(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
									.substr(("" + o[k]).length)));
			return fmt;
		};
		//开始日期JS渲染
		$('input[name=starttime]').rwpUIDateinput({
			time : true
		//onChangeDate : function() {
		//var timeInter = $("#timeInterval").val();
		//var starttime = $("#starttime").val() + ":00";
		//starttime = starttime.replace(/-/g, "/");
		//var date = new Date(starttime);
		//var t = new Date(date.getTime() + timeInter * 60 * 1000);
		//$("#endtime").val(GetDate(t));
		//}
		});
		//$("#starttime").val('2019-03-11 13:11');
		$("#starttime").val(dateToString(new Date()));
		//失效日期JS渲染
		$('input[name=endtime]').rwpUIDateinput({
			time : true
		});
		$(".rwpDialogTitle").html("<span>业务配置</span>");
		$("#modelName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectmodel
		});
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true,
			inputWidth : 'auto',
			ajaxSubmitAttrs : {
				isDialog : true,
				afterSubmit : afterSubmit
			}
		});
	});
	$("#gosub").click(function() {
		$("#gosub").submit();
	});
	$("#back").click(function() {
		$(".rwpDialogMask").hide();
		$(".rwpDialog").remove();
	});
	function afterSubmit() {
		$(".rwpDialogMask").hide();
		$(".rwpDialog").remove();
		rwpPluginHelper.menuHelper
				.loadMenuInsidePage('${ctx}/deptTimesAuthController/waitAuthList');
	}
</script>
<form action="${ctx}/deptTimesAuthController/saveTimesAuth"
	id="ajaxform" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>取号次数授权配置
		</legend>
		<div class="formpanel">
			<input id="id" name="id" type="hidden" value="${deptTimesAuth.id}" />
			<input id="deptid" name="deptid" type="hidden" value="${deptTimesAuth.deptid}" />
			<input id="idCard" name="idCard" type="hidden" value="${deptTimesAuth.idCard}" />
			<input id="userName" name="userName" type="hidden" value="${deptTimesAuth.userName}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="times">授权次数</label></li>
					<li class="editor-field"><input data-val="true" class="times"
						style="width: 100px;" data-val-length="授权次数不能超过2字符"
						data-val-length-max="2" data-val-required="授权次数不能为空" id="times"
						name="times" type="text"
						value="${not empty deptTimesAuth.times?deptTimesAuth.times:5}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="times"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<script type="text/javascript">
				$('.times').rwpUISpinner({
					type : 'int',
					minValue : 0,
					maxValue : 50,
					defaultValueText : ''
				});
			</script>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="starttime">授权生效时间(默认当前时间)</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="授权生效时间不能为空" id="starttime" name="starttime"
						type="text"
						value="<fmt:formatDate value='${deptTimesAuth.starttime}' type='date' pattern="yyyy-MM-dd HH:mm"/>" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="starttime"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="endtime">授权失效时间</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="授权失效时间不能为空" id="endtime" name="endtime"
						type="text"
						value="<fmt:formatDate value='${deptTimesAuth.endtime}' type='date' pattern="yyyy-MM-dd HH:mm"/>" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="endtime"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
		</div>
	</fieldset>
	<div class="clear" style="margin-left: -1%;">
		<div class="fPptions" style="float: left; margin-top: 4px;" id="gosub">
			<a href="javascript:;" title="确定" class="btnLightBlue btn back"
				style="width: 40px; height: 30px;line-height: 30px;" id="rego"><i
				class="i"></i>确定</a>
		</div>
		<div class="fPptions" style="float: left; margin-top: 4px;" id="back">
			<a href="javascript:;" title="返回" class="btnLightBlue btn back"
				style="width: 40px; height: 30px;line-height: 30px;" id="reback"><i
				class="i"></i>返回</a>
		</div>
	</div>
</form>