<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<link rel="stylesheet" href="${ctx}/mulitselect/jquery.multiselect.css">
<link rel="stylesheet" href="${ctx}/mulitselect/jquery-ui.css">
<link rel="stylesheet" href="${ctx}/mulitselect/prettify.css">
<%--<link rel="stylesheet" href="${ctx}/mulitselect/style.css">--%>
<script type="text/javascript" src="${ctx}/mulitselect/jquery-ui.js"></script>
<script type="text/javascript"
	src="${ctx}/mulitselect/jquery.multiselect.js"></script>
<script type="text/javascript" src="${ctx}/mulitselect/prettify.js"></script>
<script type="text/javascript">
	rwpTemp.userservicelistiniturl = '${ctx}/userServiceController/userBizInfoJson?uiCode=${uiCode}';//初始化Json
	rwpTemp.getDeptGridDelay = function(rowData, level) {
		if (rowData.isdelay) {
			var params = [];
			params.push({
				name : 'parentCode',
				value : rowData.serCode
			});
			params.push({
				name : 'uiCode',
				value : $("#uiCode").val()
			});
			return {
				url : rwpTemp.treeUrl,
				params : params
			};
		}
		rwpTemp.bindGridRowLink = function(newAppendRowData) { //绑定表格行链接事件
			var gridManager = $('#policeservicegrid').data('rwpUIDatagrid'), $bodyTable = gridManager.innerDom.gridBodyTable, startIndex, endIndex, $newAppendTrs, $newAppendLinks;
			if ($.isArray(newAppendRowData) && newAppendRowData.length > 0) {
				startIndex = newAppendRowData[0]._rowIndex;
				endIndex = newAppendRowData[newAppendRowData.length - 1]._rowIndex;
				$newAppendTrs = $('tr', $bodyTable).slice(startIndex,
						endIndex + 1);
				$newAppendLinks = $('a', $newAppendTrs);
				rwpListLayout.gridRowclick('macBusinessgrid', $newAppendLinks);
			}
		};
		return false;
	};
	$(function() {
		$(".pageHeader").html("<i class='i'></i>叫号机业务配置");
		$("#macBusinessgrid")
				.rwpUIDatagrid(
						{
							columns : [
									{
										display : '主键',
										name : 'serCode',
										isHide : true,
										primarykey : true
									},
									{
										display : '业务名称',
										name : 'serName',
										width : 320
									},
									{
										display : '状态',
										width : 120,
										render :
										function(rowdata) {
											if (rowdata.isopen) {
												var html = '<span class="fClr2">开通</span>';
												return html;
											} else {
												var html = '<span class="fClr1">未开通</span>';

												return html;
											}

											return html;
										}
									},
									{
										display : '操作',
										width : 120,
										render : function(rowdata) {
											var html = '';
											html += '<a href="${ctx}/userServiceController/editUserService?uiCode=${uiCode}&type=add&serCode='
													+ rowdata.serCode
													+ '" rwpOpts="{ isajax: true }">开通</a> | ';
											html += '<a href="${ctx}/userServiceController/editUserService?uiCode=${uiCode}&type=delete&serCode='
													+ rowdata.serCode
													+ '" rwpOpts="{ isajax: true }"><span class="fClr4">关闭</span></a>';
											return html;
										}
									} ],
							pageSize : 1,
							checkbox : false,
							url : rwpTemp.userservicelistiniturl,
							height : '99%',
							tree : {
								columnName : 'serName',
								delay : rwpTemp.getDeptGridDelay
							},
							onAfterShowData : function() {
								rwpListLayout.gridRowclick('macBusinessgrid');
							}
						});
		$(".back").click(function() {
			rwpMenuHelper.rebackLastPage();
		});
		$("#config").hide();
		$("#configtab").click(function() {
			$("#biztab").removeClass("rwpTabCurrent");
			$(this).addClass("rwpTabCurrent");
			$("#macBusinessgrid").hide();
			$("#config").show();

		});
		$("#biztab").click(function() {
			$("#configtab").removeClass("rwpTabCurrent");
			$(this).addClass("rwpTabCurrent");
			$("#config").hide();
			$("#macBusinessgrid").show();
		});
	});
	$(function() {
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true,
			ajaxSubmitAttrs : {
				isDialog : true
			}
		});
	});
	$("#gosub").click(function() {
		$("#ajaxform").submit();
	});
</script>
<style>
#chargeMode ~ .rwpCombobox {
	display: none;
}

#apprType ~ .rwpCombobox {
	display: none;
}

.ui-multiselect {
	width: 190px;
}
</style>
<div class="fAutoPage">
	<div class="tablepanel">
		<div class="rwpTab">
			<div class="rwpTabNavList">
				<span class="rwpTabNavItem rwpTabCurrent" name="wait" id="biztab"><a
					href="javascript:void(0);"><i class="i i1"></i>业务开通</a></span> <span
					class="rwpTabNavItem" name="pass" id="configtab"><a
					href="javascript:void(0);"><i class="i i2"></i>业务参数</a></span>
			</div>
		</div>
	</div>
	<div class="tablepanel">
		<div id="macBusinessgrid"></div>
	</div>
	<div class="tablepanel">
		<div id="config">
			<form action="${ctx}/userServiceController/saveConfig" id="ajaxform"
				method="post">
				<fieldset>
					<legend>
						<i class="i"></i>参数信息
					</legend>
					<div class="formpanel">
						<div class="clear">
							<input type="hidden" name="usCode" value="${config.usCode}">
							<input type="hidden" name="uiCode" value="${uiCode}">
							<script type="text/javascript">
								$(function() {
									var checktype = $("#checkType").val();
									$("#chargeMode").append(
											"<option value='1'>支付宝</option>");
									$("#chargeMode").append(
											"<option value='2'>微信</option>");
									if (checktype == 3) {
										// 设置选中项
										$("#chargeMode  option[value='1']")
												.attr("selected", true);
										$("#chargeMode  option[value='2']")
												.attr("selected", true);
									} else if (checktype == 2) {
										$("#chargeMode  option[value='2']")
												.attr("selected", true);
									} else if (checktype == 1) {
										$("#chargeMode  option[value='1']")
												.attr("selected", true);
									}
									$("#chargeMode").multiselect({
										noneSelectedText : "==请选择支付方式==",
										checkAllText : "全选",
										uncheckAllText : '全不选',
										selectedList : 4,
										minWidth : 190
									});
								});
							</script>
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="chargeMode">支付方式</label>
								</li>
								<li class="editor-field"><input type="hidden"
									id="checkType" value="${config.chargeMode }" /> <select
									id="chargeMode" title="Basic example" multiple="multiple"
									name="chargeMode" size="5">
								</select></li>
								<li class="editor-validation"><span
									class="field-validation-valid" data-valmsg-for="chargeMode"
									data-valmsg-replace="true"></span></li>
							</ul>
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="payAccount">选择支付账号</label>
								</li>
								<li class="editor-field"><select data-val="true"
									data-val-required="" id="payAccount" name="payAccount">
										<option value="1" selected>江西科泰华</option>
										<c:forEach items="${businessInfos}" var="item">
											<option value="${item.id}"
												<c:if test="${item.id==config.payAccount}">selected</c:if>>${item.accountname}
											</option>
										</c:forEach>
								</select></li>
								<li class="editor-validation"><span
									class="field-validation-valid" data-valmsg-for="payAccount"
									data-valmsg-replace="true"></span></li>
							</ul>
						</div>
						<div class="clear">
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="orderDescribe">商品描述</label>
								</li>
								<li class="editor-field"><input id="orderDescribe"
									name="orderDescribe" type="text"
									value="${config.orderDescribe}" /></li>
								<!-- <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="orderDescribe"
                                        data-valmsg-replace="true"></span>
                                </li> -->
							</ul>
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="systitle">APP标题</label>
								</li>
								<li class="editor-field"><input id="systitle"
									name="systitle" type="text" value="${config.systitle}" /></li>
								<!-- <li class="editor-validation"><span
                                        class="field-validation-valid" data-valmsg-for="orderDescribe"
                                        data-valmsg-replace="true"></span>
                                </li> -->
							</ul>

						</div>


					</div>
				</fieldset>
				<fieldset>
					<legend>
						<i class="i"></i>评价信息
					</legend>

					<div class="formpanel">
						<div class="clear">
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="isAppr">是否开启评价</label></li>
								<li class="editor-field" id="apprSpe"><input
									data-val="true" data-val-required="启用状态 字段是必需的。" id="isAppr"
									name="isAppr"
									<c:if test="${config.isAppr==1}">
                                            checked=checked
                                        </c:if>
									type="checkbox" value="1" /><input name="isAppr" type="hidden"
									value="2" /> <input type="hidden" id="apprHelp"
									value="${config.isAppr}"></li>
								<li class="editor-validation"><span
									class="field-validation-valid" data-valmsg-for="isAppr"
									data-valmsg-replace="true"></span></li>
							</ul>
							<ul class="DialabelWidth choserAppr" style="display:none;">
								<li class="editor-label"><label for="apprType">选择评价方式</label>
								</li>
								<li class="editor-field"><input type="hidden"
									id="checkAppr" value="${config.apprType}" /> <select
									id="apprType" title="Basic example" multiple="multiple"
									name="apprType" size="5">
								</select></li>
							</ul>
							<ul class="DialabelWidth choserAppr" style="display:none;">
								<li class="editor-label"><label for="apprSeq">评价顺序</label>
								</li>
								<li class="editor-field"><select data-val="true"
									data-val-required="" id="apprSeq" name="apprSeq">

										<option value="1"
											<c:if test="${config.apprSeq==1}">selected</c:if>>先支付后评价
										</option>
										<option value="2"
											<c:if test="${config.apprSeq==2}">selected</c:if>>先评价后支付
										</option>

								</select></li>

							</ul>
						</div>
						<div class="clear choserAppr" id="tableAppr" style="display: none">
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="appraiseModelId">选择评价模板</label>
								</li>
								<li class="editor-field"><select data-val="true"
									data-val-required="" id="appraiseModelId"
									name="appraiseModelId">
										<c:forEach items="${apprList}" var="item">
											<option value="${item.id}"
												<c:if test="${item.id==config.appraiseModelId}">selected</c:if>>${item.modelname}
											</option>
										</c:forEach>
								</select></li>
								<%--  <li class="editor-field">
                                      <a href="${ctx}/appraiseContrller/apprDetail" id="editApprModel"
                                         rwpopts="{ isdialog: true }" style="margin-left: 10px;color: #00a0e9">自定义模板</a>
                                  </li>--%>

							</ul>
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="isTakePhoto">是否抓拍图片</label>
								</li>
								<li class="editor-field"><input data-val="true"
									data-val-required="是否系统级 字段是必需的。" id="isTakePhoto"
									name="isTakePhoto" type="checkbox" value="1"
									<c:if test="${config.isTakePhoto==1}">
                                            checked="checked"
                                        </c:if> /><input
									name="isTakePhoto" type="hidden" value="2" /></li>
								<li class="editor-validation"><span
									class="field-validation-valid" data-valmsg-for="isTakePhoto"
									data-valmsg-replace="true"></span></li>
							</ul>
						</div>
						<script>
							$(function() {
								var checkTableAppr = function() {
									// var strConditions = $("#apprType").multiselect("MyValues");
									// var types = strConditions.toString().split(",");
									// if (types.length = 1) {
									//     if (types[0] == 1) {
									//         $('#tableAppr').show();
									//     } else {
									//         $('#tableAppr').hide();
									//     }
									// } else if (types.length == 2) {
									//     if (types[0] == 1) {
									//         $('#tableAppr').show();
									//     }
									// } else {
									//     $('#tableAppr').hide();
									// }
								};
								$("#editApprModel")
										.click(
												function(event) { //修改密码点击事件
													var url = $(this).attr(
															'href');

													$.rwpUI
															.open({
																title : '自定义模板',
																url : url,
																ajaxCallback : rwpPluginHelper.validateParseDialog,
																buttons : [
																		{
																			text : '确定',
																			onclick : rwpPluginHelper.dialogFormImportOK
																		},
																		{
																			text : '取消',
																			onclick : rwpPluginHelper.dialogImportCancel
																		} ]
															});
													event.stopPropagation();
													event.preventDefault();
												});
								if ($("#apprHelp").val() == 1) {
									$(".choserAppr").show();
								}
								$("#apprSpe").click(function() {
									$(".choserAppr").toggle();
									// if ($(".choserAppr").is(":hidden")) {
									//     $("#tableAppr").hide();
									// } else {
									//     checkTableAppr()
									// }
								});
								var checkAppr = $("#checkAppr").val();
								$("#apprType").append(
										"<option value='1'>窗口评价</option>");
								$("#apprType").append(
										"<option value='2'>微信评价</option>");
								if (checkAppr == 3) {
									// 设置选中项
									$("#apprType  option[value='1']").attr(
											"selected", true);
									$("#apprType  option[value='2']").attr(
											"selected", true);
									//如果开了评价 且选择了窗口评价 则显示模板和是否抓拍
									if ($("#apprHelp").val() == 1) {
										$('#tableAppr').show();
									}
								} else if (checkAppr == 2) {
									$("#apprType  option[value='2']").attr(
											"selected", true);
								} else if (checkAppr == 1) {
									$("#apprType  option[value='1']").attr(
											"selected", true);
									//如果开了评价 且选择了窗口评价 则显示模板和是否抓拍
									if ($("#apprHelp").val() == 1) {
										$('#tableAppr').show();
									}
								}
								$("#apprType").multiselect({
									noneSelectedText : "==请选择评价方式==",
									checkAllText : "全选",
									uncheckAllText : '全不选',
									selectedList : 4,
									minWidth : 190
								});
								$("#apprType").change(function() {
									checkTableAppr();
								});
								var firval = $("#firingval").val();
								if (firval == 2) {
									$("#congimg").show();
								}
								$("#isfiring").click(function() {
									$("#congimg").toggle();
								});
								$("#editImg")
										.click(
												function(event) { //修改密码点击事件
													var url = $(this).attr(
															'href');

													$.rwpUI
															.open({
																title : '宣传图片配置',
																url : url,
																ajaxCallback : rwpPluginHelper.validateParseDialog,
																buttons : [
																		{
																			text : '确定',
																			onclick : rwpPluginHelper.dialogFormImportOK
																		},
																		{
																			text : '取消',
																			onclick : rwpPluginHelper.dialogImportCancel
																		} ]
															});
													event.stopPropagation();
													event.preventDefault();
												});
							});
						</script>
					</div>
				</fieldset>


				<fieldset>
					<legend>
						<i class="i"></i>宣传信息
					</legend>
					<div class="formpanel">
						<div class="clear">
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="firing">是否开启宣传</label>
								</li>
								<li class="editor-field" id="isfiring"><input
									data-val="true" data-val-required="是否系统级 字段是必需的。" id="firing"
									name="firing" type="checkbox" value="2"
									<c:if test="${config.firing==2}">
                                            checked="checked"
                                        </c:if> /><input
									name="firing" type="hidden" value="1" /> <input type="hidden"
									id="firingval" value="${config.firing}"></li>
								<li class="editor-field" id="congimg" style="display: none">
									<a
									href="${ctx}/photeAndVideoController/configPhotes?deptid=${uiCode}"
									id="editImg" rwpopts="{ isdialog: true }"
									style="margin-left: 10px;color: #00a0e9">点击配置宣传图片</a>
								</li>
							</ul>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<legend>
						<i class="i"></i>预约叫号信息
					</legend>
					<div class="formpanel">
						<div class="clear">
							<ul class="DialabelWidth">
								<li class="editor-label"><label for="isappointme">是否开启短信预警</label>
								</li>
								<li class="editor-field"><input data-val="true"
									data-val-required="是否系统级 字段是必需的。" id="isappointme"
									name="isappointme" type="checkbox" value="1"
									<c:if test="${config.isappointme==1}">
                                            checked="checked"
                                        </c:if> /><input
									name="isappointme" type="hidden" value="2" /></li>
								<li class="editor-validation"><span
									class="field-validation-valid" data-valmsg-for="isappointme"
									data-valmsg-replace="true"></span></li>
							</ul>
						</div>
					</div>
				</fieldset>
				<div class="clear" style="margin-left: 20px;">
					<div class="fPptions" style="float: left; margin-top: 4px;"
						id="gosub">
						<a href="javascript:;" title="确定" class="btnLightBlue btn back"
							style="width: 80px; height: 32px;font-weight: bolder;" id="rego"><i
							class="i"></i>确定</a>
					</div>
					<div class="fPptions reback" style="float: left; margin-top: 4px;"
						id="back">
						<a href="javascript:;" title="返回" class="btnLightBlue btn back"
							style="width: 80px; height: 32px;font-weight: bolder;"><i
							class="i"></i>返回</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>