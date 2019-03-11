<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- saved from url=(0046)http://demos.sucaihuo.com/modals/32/3251/demo/ -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>本页面正在维护中</title>
<meta name="author" content="">
<meta name="copyright" content="Copyright sucaihuo.com">
<style>
html{*overflow:auto;_overflow-x:hidden;-webkit-text-size-adjust:none;height:100%;
background:#80CBF3}
body{margin:0; height:100%;
font-family:"Microsoft YaHei",tahoma,arial,simsun;}
p,h1,h2,h3,h4{ margin-top:0; margin-bottom:0;}
.main{ width:850px; margin:0 auto;}
.container{ padding-left:300px; padding-top:180px;}
h1{ font-size:42px;}
h2{ font-size:22px; font-weight:100; margin-top:2px; margin-bottom:2px;}
h3{ font-size:26px; color:#2F2D2E;}
.Countdown{ padding-top: 30px; font-size:30px; font-family: tahoma,arial,simsun; color: #4A4A4A;}
.Countdown span{ display:inline-block;*display:inline;*zoom:1; width:auto;line-height:55px;text-align:center;margin-left:2px;}
.Countdown .bd{ width:16px; background-image:none;}
:root .Countdown span{}
</style>
  
</head>

<body>
<div class="main">
	<div class="container">
    	<div class="content">
            <h1>本系统需要在IE6以上版本使用才能达到最佳效果！</h1>
            <h2>在IE6及以下可能会出现加载错误</h2>
            <h3>请更换浏览器（推荐使用谷歌浏览器或者IE8及以上！）~</h3>
        </div>
        <div class="Countdown" id="time">
            <span id="m">9</span><span class="bd">:</span><span id="s">28</span>
        </div>
    </div>
</div>

<div style="padding:20px 0;margin-top:30px;">

<div style="margin-bottom:30px;text-align:center;"><h3>create by :晓风</h3></div>
</div>

<script>
var m = 9;//传个分钟数到这里
var s = 60;
function showtime(){
	document.getElementById('m').innerHTML = m;
	document.getElementById('s').innerHTML = s;
	s = s-1;
	if(s==0){
		m = m -1;
		s = 60;
	}
	if(m==0){
		window.location='http://www.sucaihuo.com';//倒计时结束跳转到素材火
	}
}
clearInterval(settime); 
var settime = setInterval(function(){
	showtime();
},1000);
</script>

</body></html>