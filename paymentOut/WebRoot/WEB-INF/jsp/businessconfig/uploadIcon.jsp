<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script src="${ctx }/js/ajaxfileupload.js"></script>


<script>

	function submitPic() {
		/*  if(!$("#picForm").form('validate')){  
		     return false;  
		 }   */
		var projectId = $("#id").val();
		var type = "1";//展示图片  
		var f = $("#file").val();
		if (f == null || f == "") {
			$("#picTip").html(
					"<span style='color:Red'>错误提示:上传文件不能为空,请重新选择文件</span>");
			return false;
		} else {
			var extname = f.substring(f.lastIndexOf(".") + 1, f.length);
			extname = extname.toLowerCase();//处理了大小写  
			if (extname != "jpeg" && extname != "jpg" && extname != "gif"
					&& extname != "png") {
				$("#picTip")
						.html(
								"<span style='color:Red'>错误提示:格式不正确,支持的图片格式为：JPEG、GIF、PNG！</span>");
				return false;
			}
		}
		var file = document.getElementById("file").files;
		var size = file[0].size;
		if (size > 2097152) {
			$("#picTip").html(
					"<span style='color:Red'>错误提示:所选择的图片太大，图片大小最多支持2M!</span>");
			return false;
		}
		ajaxFileUploadPic(projectId, type);
	}

	function ajaxFileUploadPic(projectId, type) {
		$.ajaxFileUpload({
			url : '${ctx}/businessconfigController/uploadIcon?id='
					+ $("#id").val(), //用于文件上传的服务器端请求地址  
			secureuri : false, //一般设置为false  
			fileElementId : 'file', //文件上传空间的id属性  <input type="file" id="file" name="file" />  
			type : 'post',
			// dataType : 'json', //返回值类型 一般设置为json  
			success : function(result, status) //服务器成功响应处理函数  
			{

				if (1006 == result) {
					alert("上传成功!");
					rwpMenuHelper.rebackLastPage();
					$("#img_info").attr(
							'src',
							'${ctx}/businessconfigController/getIcon?id='
									+ $("#id").val() + '&rnd' + Math.random());
				} else if (1001 == result) {
					alert("上传失败:文件为空!");
				} else if (1002 == result) {
					alert("上传失败:无效图片文件类型!");
				} else if (1003 == result) {
					alert("上传失败:文件为空!");
				} else if (1004 == result) {
					alert("上传失败:文件大小不能超过2M");
				} else if (1005 == result) {
					alert("上传失败，请检查照片格式大小后重新尝试!");
				}
				$("#picList").datagrid('reload');
				$('#uploadPicWindow').window('close');
				// alert(data.msg);  
			},
			error : function(data, status, e)//服务器响应失败处理函数  
			{
				alert("上传成功!");
				$("#img_info").attr(
						'src',
						'${ctx}/businessconfigController/getIcon?id='
								+ $("#id").val() + '&rnd' + Math.random());
				$("#picList").datagrid('reload');
				$('#uploadPicWindow').window('close');
				// alert(data.msg);  
			}
		});
		return false;
	}
</script>



<style>
.theme-signin li {
	margin-top: 10px;
}
</style>



<div id="uploadPicWindow" class="easyui-window" title="上传图片"
	style="width:300px;height:140px;padding:20px;background:#fafafa;"
	data-options="iconCls:'icon-save',closable:true, collapsible:true,minimizable:true,maximizable:true">
	<form id="picForm" action="" method="post">
		<input data-val="true" data-val-number="字段 ID 必须是一个数字。"
			data-val-required="ID不能为空" id="id" name="id" type="hidden"
			value="${id }" />
		<!--    <div style="margin-bottom:20px">  
                图片名称:  
                <input type="text" name="name" id="picName" class="easyui-textbox" style="width:80%" data-options="required:true,validType:'stringCheck'"/>  
            </div>  
            <br/>   -->
		
		<div style="margin-bottom:20px">
			选择图片: <input type="file" name="file" id="file"
				data-options="prompt:'Choose a file...'" style="width:180px;" />
		</div>
		<div id="picTip"></div>
		<div id="formWindowfooterPic1" style="padding:5px;text-align:center;">
			<a href="#" onclick="submitPic();" class="rwpButton"
				data-options="iconCls:'icon-save'">开始上传</a>
		</div>
	</form>
</div>




