<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>

<script type="text/javascript">
	rwpTemp.dwopts = {
		columns : [ {
			display : '单位名称',
			name : 'unit',
			width : 200
		}, {
			display : '单位代码',
			name : 'unitid',
			width : 200
		} , {
			display : '别名',
			width : 300,
			render : function(row) {
				
				var html = '<input type="hidden" class="hd" value='+row.otherName+'>';
				html += '<input type="hidden" class="unitid" value='+row.unitid+'>';
				if(row.otherName!=null){
					html += '<input type="text" name="otherName" readonly="readonly" style="background:#eee;" class="otherName" value='+row.otherName+'>';
				}else{
					html += '<input type="text" name="otherName" readonly="readonly" style="background:#eee;" class="otherName" value="">';
					
				}
				 html+='<input type="button" class="edt" value="修改">';
				 html+='<input type="button" class="confirmEdit" value="确定">';
				
					
				return html;
			}
		}],
		checkbox : false,
		url : '${ctx}/dwController/DWJson',
		dateFormat : 'yyyy-MM-dd hh:mm:ss',
		height : '99%'
	};
	$(function(){
		
			//点击修改 输入框打开
			$("body").on("click",".edt",function(){
				var $this=$(this);
				if($this.siblings(".otherName").attr("readonly")!="undefined"){
					$this.siblings(".otherName").removeAttr("readonly");
					$this.siblings(".otherName").css("background","#fff");
					$this.siblings(".otherName").focus();
				}
			});
			
			//点击确定 更新数据，输入框关闭
			$("body").on("click",".confirmEdit",function(){
				var $this=$(this);
				var otherName=$this.siblings(".hd").val();
				if(otherName=="undefined"){
					otherName="";
				}
				var newName = $this.siblings(".otherName").val();
				if(otherName==newName){
					//如果修改的数据更之前的数据相同，则不提交后台，直接关闭输入框
					$this.siblings(".otherName").attr("readonly","readonly");
					$this.siblings(".otherName").css("background","#eee");
				}else{
					var unitid = $this.siblings(".unitid").val();
					$.ajax({
						type:"POST",
						url: "dwController/editOtherName",
						data:{"unitid":unitid,"newName":newName},
						dataType:"json",
						success: function(data){
						   if(data.result=="ok"){
							   //修改成功 输入框关闭
							   $this.siblings(".otherName").attr("readonly","readonly");
							   $this.siblings(".otherName").css("background","#eee");
						   }else{
							   alert("修改失败！");
						   }
						}
					}); 
				}
			});
		}); 
	$(function() {
		rwpTemp.dwopts.onAfterShowData = function() {
			rwpListLayout.gridRowclick('dwgrid');
		}; 
		$("#searform").rwpUIForm({
			inputWidth : 150,
			isSearch : true
		}); //应用查询form表单            
		$("#seachbtn").click(function() {
			rwpListLayout.searchGrid('searform');
		});
		$("#seachreset").click(function() {
			$('#searform')[0].reset();
		});
		$("#dwgrid").rwpUIDatagrid(rwpTemp.dwopts);
		$('input[name=recordTimeSearch]').rwpUIDateinput();
		
	});
</script>

<div class="searchBar">
	<form id="searform">
		<div>
			<dl style="width:256px;">
				<dt class="labelWidth" style="width:75px;">单位名称：</dt>
				<dd class="inputWidth">
					<input name="dwdmSearch" type="text" id="dwdmSearch"
						query-options="{ 'fieldName': 'unit', 'whereField':'like', 'fieldType':'String' }" />
				</dd>
			</dl>
		</div>
		<dl style="width:144px;">
			<dd class="inputWidth">
				<a href="javascript:void(0);" id="seachbtn" class="rwpButton">查找</a>
			</dd>
			<dd class="inputWidth">
				<a href="javascript:void(0);" id="seachreset" class="rwpButton">重置</a>
			</dd>
		</dl>
	</form>
</div>
<div class="tablepanel">
	<div id="dwgrid"></div>
</div>
