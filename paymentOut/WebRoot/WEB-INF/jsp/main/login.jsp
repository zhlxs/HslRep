<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>
<body>
	<sf:form id="ajaxform" action="${ctx }/mainController/checkUserLogin"
		method="post" modelAttribute="core_User">
		<sf:input path="userName" />
		<sf:errors path="userName" />
		<input type="submit" value="Submit" />
	</sf:form>
</body>
</html>
