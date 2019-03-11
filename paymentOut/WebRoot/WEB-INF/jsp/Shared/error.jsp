<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>没有访问权限！</title>
<link href="${ctx}/js/style/css/core.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpButton.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpDialog.css"
	rel="stylesheet" type="text/css" />

<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/core/rwpInit.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpButton.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpDialog.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpDialog.plugin.js"
	type="text/javascript"></script>



<script type="text/javascript">
	var url = '';
	function pageurl() {
		if (url && url.length > 0) {
			window.location.href = url;
		} else {
			$("body").html('没有访问权限！');
		}
	}
	$(function() {
		$.rwpUI.alert('没有访问权限！', '操作提示', 'error', pageurl);
	});
</script>
</head>
<body>
</body>
</html>
