<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>bug 审核管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/testAudit/">审批列表</a></li>
		<li class="active"><a href="#"><shiro:hasPermission name="oa:testAudit:edit">${testAudit.act.taskName}</shiro:hasPermission><shiro:lacksPermission name="oa:testAudit:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="bug" action="${ctx}/bug/bug/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<fieldset>
			<legend>${bug.act.taskName}</legend>
			<table class="table-form">
				<tr>
					<td class="tit">项目名称</td><td>${bug.bugProject.name}</td>
					<td class="tit">部门</td><td>${bug.bugVersion.version}:${bug.bugVersion.build}</td>
					<td class="tit">缺陷类型</td><td>${bug.bugType}</td>
				</tr>
				<tr>
					<td class="tit">缺陷状态</td>
					<td colspan="5">${bug.bugStatus}</td>
				</tr>
				<tr>
					<td class="tit">缺陷优先级${bug.bugLevel}</td>
					<form:select path="bugStatus" class="form-control required" >
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('bug_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</tr>
				<tr>
					<td class="tit">名称</td>
					<td colspan="5">${bug.name}</td>
				</tr>
				<tr>
					<td class="tit">简介</td>
					<td colspan="5">${bug.summary}</td>
				</tr>
				<tr>
					<td class="tit">详情</td>
					<td colspan="5">${bug.description}</td>
				</tr>
				<tr>
					<td class="tit">备注</td>
					<td colspan="5">${bug.remarks}</td>
				</tr>

				<tr>
					<td class="tit">缺陷文件</td>
					<td colspan="5">${bug.file}</td>
				</tr>

				<tr>
					<td class="tit">缺陷图片</td>
					<td colspan="5">${bug.image}</td>
				</tr>

				<tr>
					<td class="tit">项目经理意见</td>
					<td colspan="5">
						${bug.projectManagerText}
					</td>
				</tr>
				<tr>
					<td class="tit">开发主管意见</td>
					<td colspan="5">
						${bug.developerLeadText}
					</td>
				</tr>

				<tr>
					<td class="tit">您的看法</td>
					<td colspan="5">
						<form:select path="bugStatus" class="form-control required" >
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('${bug.act.taskDefKey}')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="tit">原因</td>
					<td colspan="5">
						<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
					</td>
				</tr>
			</table>
		</fieldset>
		<div class="form-actions">
			<shiro:hasPermission name="bug:bug:edit">
				<%--<c:if test="${bug.act.taskDefKey eq 'testerTask'}">--%>
					<%--<input id="btnSubmit" class="btn btn-primary" type="submit" value="兑 现" onclick="$('#flag').val('yes')"/>&nbsp;--%>
				<%--</c:if>--%>
				<%--<c:if test="${testAudit.act.taskDefKey ne 'apply_end'}">--%>
					<%--<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;--%>
					<%--<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;--%>


				<input id="btnSubmit" class="btn btn-primary" type="submit"  value="确认"/>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:histoicFlow procInsId="${testAudit.act.procInsId}"/>
	</form:form>
</body>
</html>
