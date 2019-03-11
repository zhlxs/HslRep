<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link href="${ctx}/js/style/css/core.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpButton.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpDialog.css"
	rel="stylesheet" type="text/css">

<link rel="stylesheet" href="${ctx }/js/style/css/jquery.multiselect.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/style/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/style/css/prettify.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/style/css/style.css" type="text/css" />

	
<script src="${ctx }/js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx }/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="${ctx }/js/prettify.js"></script>
<script src="${ctx}/js/rwpUI/js/core/rwpInit.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpButton.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpDialog.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpDialog.plugin.js" type="text/javascript"></script>




<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpReset.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpLayout.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpToggle.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpCombobox.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpDatagrid.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpDateinput.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpSwitch.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpTree.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpSpinner.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpForm.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpTab.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpIconSelect.css"
	rel="stylesheet" type="text/css">


<script src="${ctx}/js/rwpUI/js/ui/rwpDateinput.js"type="text/javascript"></script>


<script src="${ctx}/js/json2.js" type="text/javascript"></script>
<style type="text/css" adt="123"></style>
<script src="${ctx}/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.validate.unobtrusive.min.js"
	type="text/javascript"></script>
<script src="${ctx}/js/pinyin.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpLayout.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpToggle.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpCombobox.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpDatagrid.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpSwitch.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpSpinner.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpTextbox.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpTree.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpForm.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpTab.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpLayout.plugin.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpCombobox.plugin.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpDatagrid.plugin.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpForm.plugin.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpTree.plugin.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpIconSelect.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/plugin/rwpAjaxSelect.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/plugin/rwpListLayout.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/plugin/rwpMenuLayout.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/plugin/rwpSelectContact.js"
	type="text/javascript"></script>

