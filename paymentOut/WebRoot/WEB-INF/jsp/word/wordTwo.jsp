<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	
</script>
<html>

<body>
	<div style="overflow: auto; height:100%;">
		<p>
		<h3>1.打开控制面板--卸载程序--打开或关闭Windows功能--Internet信息服务(勾选所选组件)</h3>
		</p>
		<p>
			<img src="images/Internet.png"></img>
		</p>
		<p>
			<img src="images/windows1.png">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
				src="images/windows2.png"></img>
		</p>
		</br>
		<p>
		<h3>2.安装和配置JDK</h3>
		<button onclick="window.location.href('${ctx}/Down/jdk-7x86.rar')">下载32位
			JDK</button>
		&nbsp;&nbsp;
		<button onclick="window.location.href('${ctx}/Down/jdk-7x64.rar')">下载64位
			JDK</button>
		</p>
		<h3>步骤如下:</h3>
		<h4>1.安装JDK 选择安装目录 安装过程中会出现两次 安装提示 。第一次是安装 jdk ，第二次是安装 jre
			。建议两个都安装在同一个java文件夹中的不同文件夹中。</h4>
		<h4>（不能都安装在java文件夹的根目录下，jdk和jre安装在同一文件夹会出错） 如下图所示</h4>
		<p>
			<img src="images/JDK1.png"></img>
		</p>
		</br>
		<h4>2：安装jdk 随意选择目录 只需把默认安装目录 \java 之前的目录修改即可;安装jre→更改→ \java
			之前目录和安装 jdk 目录相同即可</h4>
		<h4>注：若无安装目录要求，可全默认设置。无需做任何修改，两次均直接点下一步。</h4>
		<p>
			<img src="images/JDK2.png"></img>
		</p>
		</br>
		<h4>3.安装完JDK后配置环境变量 计算机→属性→高级系统设置→高级→环境变量</h4>
		<p>
			<img src="images/JDK3.png"></img>
		</p>
		</br>
		<h4>系统变量→新建 JAVA_HOME 变量 。变量值填写jdk的安装目录（本人是 E:\Java\jdk1.7.0)</h4>
		<img src="images/javahomejdk.png"></img>
		<p>&nbsp;</p>
		<h4>在系统变量中→找到 "Path"变量→编辑</h4>
		<h4>在变量值最后输入 %JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;</h4>
		<h4>（注意原来Path的变量值末尾有没有;号，如果没有，先输入；号再输入上面的代码）</h4>
		<p>
			<img src="images/Java_Home.png"></img>
		</p>
		</br>
		<h4>系统变量→新建 CLASSPATH 变量</h4>
		<h4>变量值填写   .;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;（注意最前面有一点）</h4>
		<h4>系统变量配置完毕</h4>
		<p>
			<img src="images/CLASSPATH.png"></img>
		</p>
		</br>
		<h4>检验是否配置成功 运行cmd(或"Windows标徽键+R") 输入 java -version （java 和 -version 之间有空格）</h4>
		<h4>若如图所示 显示版本信息 则说明安装和配置成功。</h4>
		<p>
			<img src="images/JAVAC.png"></img>
		</p>
		</br>
		<p>
		<h3>3.下载免安装版Tomcat</h3>
		<button onclick="window.location.href('${ctx}/Down/tomcat-7 x86.zip')">下载32位Tomcat</button>
		&nbsp;&nbsp;
		<button onclick="window.location.href('${ctx}/Down/tomcat-7.x64.rar')">下载64位Tomcat</button>
		</p>
		<p><h4>（1）将ZZSQJ文件夹复制到Tomcat目录下webapps文件夹 &nbsp;&nbsp;
		<button onclick="window.location.href('${ctx}/Down/ZZSQJ.rar')">下载ZZSQJ</button>
		</h4></p>
		<p><h4>（2）在Tomcat--bin目录下-- 按住Shift 右击鼠标，选择在此处打开命令窗口--输入 service.bat install</h4>
		</p><p><img src="images/cmd.png"></img><img src="images/start.png"></img></p></br>
		<p><h4>（3）打开windows系统服务，找到Tomcat(可能比较难找，但一般就是服务名中含有Tomcat),设置自动模式，并开启服务</h4>
		</p></br><p><img src="images/tomcatup.png"></img></p></br>
		<h3>
			4.控制面板--系统和安全--管理工具--Internet 信息服务(IIS)管理器--右击网站——添加网站
			<button
				onclick="window.location.href('${ctx}/Down/jiekou.rar')">下载
				jiekou.rar</button>
		</h3>
		<p>
			<img src="images/jiekou.png"></img>
		</p>
		</br>
		<p>
		<h3>5.安装Oracle</h3>
		</p>
		<p>（1）导入表机构和内容</p>
		<p>（2）设置刚刚复制的ZZSQJ文件夹下的\WEB-INF\classes\jdbc.properties文件，右击编辑，设置申请机的数据库用户名，密码</p>
		<p>jdbc.driver=oracle.jdbc.driver.OracleDriver</p>
		<p>jdbc.url=jdbc:oracle:thin:@127.0.0.1（ip,本机为127.0.0.1）:1521:orcl</p>
		<p>jdbc.username=ZZSQJ（用户名）</p>
		<p>jdbc.pwd=123（密码）</p>

	</div>
</body>
</html>