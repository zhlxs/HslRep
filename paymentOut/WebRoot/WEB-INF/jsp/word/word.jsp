<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	
<html>

<body >
<div style="overflow: auto; height:100%;">
<p><strong>1.将“加密狗”插入本机USB</strong></p>
<P><strong>2.下载"AutoMachine",复制文件夹“AutoMachine”到C盘根目录下</strong>
<button onclick="window.location.href('${ctx}/Down/AutoMachine.zip')">下载AutoMachine</button></p>
<p><strong>3.首先确定系统是否为 Windows7 Service Pack 1 版本   右击“计算机”选择“属性”如下图查看！！不是则请下载 Sp1补丁</strong>   <button  onclick="window.location.href('${ctx}/Down/windows6.1-KB976932-X86.zip')">下载Sp1补丁</button>
</p>
<p><img src="images/Checksp1.png"></img></p>
<p><strong>4.检查IE版本是否为IE11？</strong> 
<button onclick="window.location.href('${ctx}/Down/IE11forWin7for32.zip')">下载IE32位</button>
<button onclick="window.location.href('${ctx}/Down/IE11forWin7for64.zip')">下载IE64位</button>
</p><br>
</p>
<p><strong>5.安装完成IE11后,请根据操作系统位数导入“自助申请机”注册表(请右击以管理员身份运行)</strong>
<button onclick="window.location.href('${ctx}/Down/32Regedit.zip')">下载32位注册表</button>
<button onclick="window.location.href('${ctx}/Down/64Regedit.zip')">下载64位注册表</button>
</p>
<p><img src="images/Q20160416194908.png"></img></p>
<p><strong>6.检查系统是否安装.NET4.0环境，检查方法：<a style="color:red;" href="javascript:alert(navigator.userAgent);" >请使用IE11浏览器,点击本行红色字体查看</a> 弹出以下内容则为系统已经安装!否则请下载</strong>
<button onclick="window.location.href('${ctx}/Down/Net4.zip')">下载NET4.0</button></p>
<p><img src="images/checknet.png"></img></p><br>
<p><strong>7.在后台注册申请机,进入“工作台”首先点击“单位查询”查出所在地区的"单位代码"并记录，然后进入“申请机注册“ 注册本机</strong></p><br>
<p><img src="images/Q20160416192654.png"></img><img src="images/checkregedit.png"></img></p><br>
<p><strong>8.申请机参数配置,目前申请机参数值为16个，必须都配置</strong></p><br>
<p><img src="images/canshu16.png"></img></p>
<p><strong>9.复制链接 http://10.136.4.18:9080/ZZSQJ_HJZ/index 进入IE11浏览器运行</strong></p>
<p><strong>10.进行IE11 Internet 选项设置</strong></p>
<p><img src="images/IE1.png"></img></p>
<p><img src="images/IE2.png"></img></p><br>
<p><strong>11.IE自定义级别设置</strong></p><br>
<p><img src="images/IE3.png"></img></p>
<p><img src="images/IE4.png"></img>
<img src="images/IE5.PNG"></img></p><br>
<p><strong>12.进行“控件注册”三个控件(键盘_打印、拍照、身份证)需要注册，方法如下   <button onclick="window.location.href('${ctx}/Down/ClicentOrAction.zip')">下载客户端和控件</button></p></p>
<p style="color: red;">(请注意！在注册之后就不能移动文件夹!如果移动,要先右击并以管理员身份运行"UnInstall.bat",方可移动文件夹并重新注册)</strong>
<p><img src="images/activeplus.png"></img></p><br>
<p><strong>13.运行客户端</strong></p>
<p><img src="images/startexe.png"></img></p>
<p>&nbsp;</p>
<p><br/>
  </div>
</body>
</html>