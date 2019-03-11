<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>

<script type="text/javascript">
	rwpTemp.f_selectuserrole = function() {
		var url = '${ctx}/roleController/roleDialog?actiontype=selectrole&roleids='
				+ $("#roleIDs").val() + '&rnd=' + Math.random();
		return rwpPluginHelper.selectContact('选择所属角色', url, 'ID', 'roleName',
				'roleIDs', 'roleNames');
	};

	rwpTemp.f_selectuserdeptok = function(node, value, text) {
		value = value || 0; //没选择部门时赋值为0
		$('#deptID').val(value);
		$('#deptName').val(text);
	};

	rwpTemp.f_selectuserdept = function() {
		var url = '${ctx}/deptController/deptDialog?';
		return rwpPluginHelper.selectContact('选择所属部门', url, 'ID', 'deptName',
				'deptID', '', null, rwpTemp.f_selectuserdeptok);
	};

	$(function() {
		$("#password").val('');
		$("#roleNames").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectuserrole
		});
		$("#deptName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectuserdept
		});
		if ("1" == 1)
			$('#ulIsSys').show(); //系统级管理员 可设置用户的系统级权限        
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true,
			ajaxSubmitAttrs : {
				isDialog : true,
				submitOk : rwpMenuHelper.rebackLastPage
			}
		});

		$("#back").click(function() {
			rwpMenuHelper.rebackLastPage();
		});
		$("#gosub").click(function() {
			$("#gosub").submit();
		});
	});
</script>

<div class="fAutoPage">
	<form action="${ctx}/userController/saveUser" id="ajaxform"
		method="post">
		<fieldset>
			<legend>
				<i class="i"></i>用户信息
			</legend>
			<input data-val="true" data-val-number="字段 用户ID 必须是一个数字。"
				data-val-required="用户ID 字段是必需的。" id="ID" name="id" type="hidden"
				value="${core_User.id}" />
			<div class="formpanel">
				<div class="clear">
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="userName">用户名</label></li>
						<li class="editor-field"><input data-val="true"
							data-val-length="用户名不能超过20字符" data-val-length-max="20"
							data-val-required="用户名不能为空" id="userName" name="userName"
							type="text" value="${core_User.userName}" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="userName"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="passWord">密码</label></li>
						<li class="editor-field"><input data-val="true"
							data-val-length="密码不能超过32字符" data-val-length-max="32"
							data-val-required="密码不能为空" id="password" name="password"
							type="password" value="${core_User.password}" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="password"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="fullName">民警姓名</label></li>
						<li class="editor-field"><input data-val="true"
							data-val-length="姓名不能超过20字符" data-val-length-max="20"
							data-val-required="姓名不能为空" id="fullName" name="fullName"
							type="text" value="${core_User.fullName}" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="fullName"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="phone">联系电话</label></li>
						<li class="editor-field"><input data-val="true"
							data-val-length="联系电话不能超过20字符" data-val-length-max="20"
							id="phone" name="phone" type="text" value="${core_User.phone}" />
						</li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="phone"
							data-valmsg-replace="true"></span></li>
					</ul>
				</div>
				<div class="clear">
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="deptID">所属部门</label></li>
						<li style="display: none;"><input data-val="true"
							data-val-number="字段 所属部门 必须是一个数字。" data-val-regex="请选择所属部门"
							data-val-regex-pattern="^[1-9]\d*|0$"
							data-val-required="所属部门 字段是必需的。" id="deptID" name="deptId"
							type="hidden" value="${core_User.deptId}" /></li>
						<li class="editor-field"><input id="deptName" name="deptName"
							type="text" value="${core_User.dept.deptName}" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="deptID"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="roleIDs">所属角色</label></li>
						<li style="display: none;"><input data-val="true"
							data-val-required="请勾选管理员角色" id="roleIDs" name="roleIDs"
							type="hidden" value="${userhelper.user.roleIDs}" /></li>
						<li class="editor-field"><input id="roleNames"
							name="roleNames" type="text" value="${userhelper.user.roleNames}" />
						</li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="roleIDs"
							data-valmsg-replace="true"></span></li>
					</ul>
					<%--<ul class="DialabelWidth">
					<li class="editor-label"><label for="payCode">支付编码</label>
					</li>
					<li class="editor-field"><input data-val="true"
						id="payCode" name="payCode"
						type="text" value="${core_User.payCode}" />
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="payCode"
						data-valmsg-replace="true"></span>
					</li>
				</ul>
				<ul class="DialabelWidth">
						<li class="editor-label"><label for="payType">支付类型:</label></li>
						<li class="editor-field"><select name="payType" id="payType">
								<option value="1" <c:if test="${core_User.payType=='1'}">selected</c:if>>支付宝</option>
								<option value="2" <c:if test="${core_User.payType=='2'}">selected</c:if>>微信</option>
								<option value="3" <c:if test="${core_User.payType=='3'}">selected</c:if>>两种都开通</option>
						</select>
						</li>
					</ul>--%>
				</div>
				<div class="clear">
					<%--<ul class="DialabelWidth">
					<li class="editor-label"><label for="machineCode">机器码</label>
					</li>
					<li class="editor-field"><input data-val="true"
						id="machineCode"
						name="machineCode" type="text" value="${core_User.machineCode}" />
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="machineCode"
						data-valmsg-replace="true"></span>
					</li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="machineIp">机器IP</label>
					</li>
					<li class="editor-field"><input data-val="true"
						id="machineIp"
						name="machineIp" type="text" value="${core_User.machineIp}" />
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="machineIp"
						data-valmsg-replace="true"></span>
					</li>
				</ul>--%>
					<%--<ul class="DialabelWidth">
					<li class="editor-label"><label for="pcName">所属警种</label>
					</li>
					<li class="editor-field">
						&lt;%&ndash; <input data-val="true"
                            data-val-required="姓名不能为空" id="pcName" name="pcCode"
                            type="text" value="${policelist.pcName}" /> &ndash;%&gt;
						<select data-val="true" data-val-required=""
								id="pcName" name="pcCode" >
							<c:if test="${core_User.pcCode==0}">
								<option value="0" selected>超级管理员</option>
							</c:if>
							<c:if test="${core_User.pcCode!=0}">
								<c:forEach items="${policelist }" var="police">
									<option value="${police.pcCode }" <c:if test="${police.pcCode==core_User.pcCode}">selected</c:if>>${police.pcName}</option>

								</c:forEach>
							</c:if>
						</select>
					</li>
					<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="pcName"
							data-valmsg-replace="true"></span>
					</li>
				</ul>--%>

				</div>
				<div class="clear">
					<%--	<ul class="DialabelWidth">
					<li class="editor-label"><label for="goodsremark">商品描述</label></li>
					<li class="editor-field"><textarea rows="2" cols="50" value="" data-val="true"
													   data-val-required="商品描述必须填写" id="goodsremark" name="goodsremark">${core_User.goodsremark}</textarea></li>
					<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="goodsremark"
							data-valmsg-replace="true"></span>
					</li>
				</ul>--%>
				</div>
				<div class="clear">
					<%--<ul id="ulIsSys" class="DialabelWidth" style="display: none;">
					<li class="editor-label"><label for="isAdmin">是否为部门管理员</label>
					</li>
					<li class="editor-field"><input data-val="true"
													data-val-required="是否系统级 字段是必需的。" id="isAdmin" name="isAdmin"
							<c:if test="${core_User.isAdmin==1||core_User.isAdmin==2}">
								checked=checked
							</c:if>
													type="checkbox" value="1" /><input name="isAdmin" type="hidden"
																						  value="0" />
					</li>
					<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="isSys"
							data-valmsg-replace="true"></span>
					</li>
				</ul>--%>
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="isStart">启用状态</label></li>
						<li class="editor-field"><input data-val="true"
							data-val-required="启用状态 字段是必需的。" id="isStart" name="isStart"
							<c:if test="${core_User.isStart}">
							checked=checked
						</c:if>
							type="checkbox" value="true" /><input name="isStart"
							type="hidden" value="false" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="isStart"
							data-valmsg-replace="true"></span></li>
					</ul>
					<ul id="ulIsSys" class="DialabelWidth" style="display: none;">
						<li class="editor-label"><label for="isSys">是否系统级</label></li>
						<li class="editor-field"><input data-val="true"
							data-val-required="是否系统级 字段是必需的。" id="isSys" name="isSys"
							<c:if test="${core_User.isSys}">
							checked=checked
						</c:if>
							type="checkbox" value="true" /><input name="isSys" type="hidden"
							value="false" /></li>
						<li class="editor-validation"><span
							class="field-validation-valid" data-valmsg-for="isSys"
							data-valmsg-replace="true"></span></li>
					</ul>
				</div>
			</div>
		</fieldset>
		<div class="clear" style="margin-left: 420px;">
			<div class="fPptions" style="float: left; margin-top: 4px;"
				id="gosub">
				<a href="javascript:;" title="确定" class="btnLightBlue btn back"
					style="width: 50px; height: 32px;" id="rego"><i class="i"></i>确定</a>
			</div>
			<div class="fPptions" style="float: left; margin-top: 4px;" id="back">
				<a href="javascript:;" title="返回" class="btnLightBlue btn back"
					style="width: 50px; height: 32px;" id="reback"><i class="i"></i>返回</a>
			</div>
		</div>
	</form>
</div>