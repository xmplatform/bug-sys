<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>审批管理</title>
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
		<li class="active"><a href="${ctx}/oa/testAudit/form/?procInsId=${testAudit.procInsId}">审批详情</a></li>
	</ul>
	<form:form class="form-horizontal">
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
					<td colspan="5">
							${bug.bugLevel}
					</td>

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
			</table>
		</fieldset>
		<act:histoicFlow procInsId="${bug.act.procInsId}" />
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
