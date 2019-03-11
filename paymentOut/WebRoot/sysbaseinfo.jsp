<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'TestUserLogin.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	
  
 
  <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
  <script type="text/javascript" src="js/jquery.validate.min.js"></script>
  <script type="text/javascript" src="js/jquery.validate.unobtrusive.min.js"></script>
  <script type="text/javascript" src="js/json2.js"></script>
  <script type="text/javascript" src="js/pinyin.js"></script>
  <script type="text/javascript" src="js/png.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/core/rwpInit.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/plugin/rwpAjaxSelect.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/plugin/rwpMenuLayout.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/plugin/rwpSelectContact.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpButton.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpCombobox.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpCombobox.plugin.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpDatagrid.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpDatagrid.plugin.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpDateinput.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpDialog.js"></script> 
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpDialog.plugin.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpForm.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpForm.plugin.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpIconSelect.js"></script> 
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpLayout.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpSpinner.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpSwitch.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpTab.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpTextbox.js"></script>
  <script type="text/javascript" src="js/rwpUI/js/ui/rwpToggle.js"></script>
<script type="text/javascript" src="js/rwpUI/js/ui/rwpTree.js"></script>
<script type="text/javascript" src="js/rwpUI/js/ui/rwpTree.plugin.js"></script>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpButton.css" type="text/css"></link>
<link rel="stylesheet" href="js/style/css/core.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpCombobox.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpDatagrid.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpDateinput.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpDialog.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpDrag.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpForm.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpIconSelect.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpLayout.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpReset.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpSpinner.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpSwitch.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpTab.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpToggle.css" type="text/css"></link>
<link rel="stylesheet" href="js/rwpUI/skins/lightblue/css/rwpTree.css" type="text/css"></link>
	<script type="text/javascript">    
	    $(function () {
	        $("#provinceName").rwpUICombobox({
	            valueField: 'ID',
	            textField: 'cityName',
	            valueFieldDomID: 'provinceID',
	            url: '@Url.Action("citytreejson", "city", new { @area = "Dic" })?parentid=0&citytype=0&islazy=1'
	        });
	
	   	     $("#ajaxform-sysbaseinfo").rwpUIForm({ isAjaxSubmit: true, ajaxSubmitAttrs: { isDialog: true} });
	    });    
	</script>
</head>
   <body>
    <fieldset>
        <legend><i class="i"></i></legend>
        <div class="formpanel">        
            <ul class="clear">
                <li class="editor-label"></li>
                <li class="editor-field"></li>
                <li class="editor-validation"></li>
            </ul>
            <ul class="clear">
                <li class="editor-label"></li>
                <li style="float: left;"></li>
                <li class="editor-validation"></li>
            </ul>
        </div>
    </fieldset>
    <div class="editor-submit">
        <input type="submit" value="确定" class="inputbtn cancel" />
    </div>

  </body>
</html>