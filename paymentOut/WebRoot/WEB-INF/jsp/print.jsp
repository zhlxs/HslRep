<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<%@include file="base1.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>打印信息</title>
    
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
  <object id="kj" classid="clsid:ED49EA20-9C82-4880-9C61-189CE592F760" style="display: none;"></object>
  <input type="hidden" value="${bh} " id="bh"/>
  <input type="hidden" value="${ctx }" id="path"/>
    
   <script type="text/javascript">
  	$(function(){
  		var bh = $("#bh").val();
  		var path = $("#path").val();
  		//var kj = $("#kj");
  		$.ajax({
  			type:"post",
            url: path+"/print/printSqjInfo?bh="+bh,
            dataType:"json",
            success: function(data){
            	//var kj = $("#kj");
            	var str = JSON.stringify(data);
				var json = str.replace(/\"/g, "'");
		        		try {
		        			kj.printInit(window, "callback", "JxPrint.frx",json,"","1","123456");
		        		} catch (err) {
				
		        			alert("请使用IE浏览器（IE6以上）！");
		        		}
            },
            error:function(){
            	alert("获取打印信息失败");
            }
  		});
  		function callback(value) {
			alert(value);
		}
  	});
  </script> 
  </body>
</html>
