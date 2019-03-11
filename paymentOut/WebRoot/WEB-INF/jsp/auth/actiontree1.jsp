@{
    ViewBag.Title = "动作树";
    Layout = null;
    string actions = (string)ViewBag.actions;
}
<!-- <script type="text/javascript">
    rwpTemp.actions = _rwpSplit + "@Html.Raw(actions)" + _rwpSplit;
    @Html.Raw("rwpTemp.actiondata = " + (string)ViewBag.actiondata);    
    rwpTemp.allactionopts = {
        columns: [
                { display: '说明', name: 'display', width: 300 },
                { display: '名称', name: 'controllerAction', width: 300 },
                { display: '类型', name: 'typeOf', width: 60,
                    render: function (row) {
                        var html = row.typeOf == 2 ? '动作' : '<span class="fClr4">控制器</span>';
                        return html;
                    }
                }
            ],        
        usePager: false,
        data: rwpTemp.actiondata,
        tree: { columnName: 'display' },
        autoCheckChildren: true,
        includeParent: false,
        checkbox: true,
        isSelected: rwpPluginHelper.gridIsSelectedFun('controllerAction', rwpTemp.actions),
        height: '99%'
    };
    rwpTemp.forauthactionopts = {
        columns: [
                { display: '说明', name: 'display', width: 150 },
                { display: '控制器', name: 'controName', width: 200 },
                { display: '动作', name: 'actionName', width: 200 }
            ],
        usePager: false,
        checkbox: false,
        data: rwpTemp.actiondata,
        height: '79%'
    };
    $(function () {
        @Html.Raw("rwpTemp.actiongridopts = " + (actions == "forauth" ? "rwpTemp.forauthactionopts" : "rwpTemp.allactionopts"));        
        $("#actiongrid").rwpUIDatagrid(rwpTemp.actiongridopts);
    }); 
</script>

<div id="actiongrid"></div> -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  		${msg}
    	<p>跳转到了test页面</p>
    	<c:forEach items="${list}" var="auth">
    		<c:out value="${auth.authName}"></c:out>
    	</c:forEach>
  </body>
</html>

