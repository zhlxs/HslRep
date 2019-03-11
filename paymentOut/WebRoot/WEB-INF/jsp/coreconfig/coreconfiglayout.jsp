<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#sysconfigtab").rwpUITab();
	});
</script>

<div id="sysconfigtab"
	style="width: 99.2%; margin: 0 auto; margin-top: 2px;">
	<ul>
		<li tabsrc="${ctx }/configController/sysBaseInfo">基本信息</li>
	</ul>
</div>