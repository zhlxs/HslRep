<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
﻿<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
  <title>@ViewBag.Title</title>
  <!--[if lte IE 6]>
    <script src="@Url.Content("~/lib/png.js")" type="text/javascript"></script>
    <script type="text/javascript">
        try {
            document.execCommand("BackgroundImageCache", false, true);
        } catch(e) {}
        DD_belatedPNG.fix('div,p,h1,h2,h3,h4,ul,li,img,a,a:hover,span,em,b,i');
    </script>
  <![endif]-->
  @RenderPage("_Header.cshtml")
  @RenderSection("head", false)
</head>
<body>

</body> 
</html>
