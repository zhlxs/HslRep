<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true
		});
	});
</script>

<form action="${ctx}/csController/saveCS" id="ajaxform" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>参数信息
		</legend>
		<c:if test="${empty csList }">
			<input id="csm" name="csm" type="hidden" value="${cs.csm }" />
			<input id="csm" name="dwdm" type="hidden" value="${cs.dwdm }" />
		</c:if>
		<div class="formpanel">
			<div class="clear">
				<c:if test="${not empty unitList }">
					<ul class="DialabelWidth">
						<li class="editor-label"><label for="dwdm">单位</label>
						</li>
						<li class="editor-field"><select id="dwdm" name="dwdm">
								<c:forEach var="unit" items="${unitList }">
									<option value="${unit.unitid }">${unit.unit }</option>
								</c:forEach>
						</select>
						</li>
					</ul>
				</c:if>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="csm">参数</label>
					</li>
					<li class="editor-field"><c:if test="${not empty csList }">
							<select id="csm" name="csm">
								<c:forEach var="cs" items="${csList }">
									<option value="${cs.dm }">${cs.mc }</option>
								</c:forEach>
							</select>
						</c:if> <c:if test="${empty csList }">
							<input id="ms" name="ms" type="text" value="${cs.ms }" />
						</c:if></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="csz">参数值</label>
					</li>
					<li class="editor-field"><input id="csz" name="csz"
						type="text" value="${cs.csz }" />
					</li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>