<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>缺陷管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
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

			initSelfProjectList();
			console.info("shaonian");

//			$(function() {
//
//			});

			
		});

		/**
		 * 获取项目列表
		 */
		function initSelfProjectList() {
			$('#project').empty();
			$.ajax({
				type : "POST",
				url : "${ctx}/bug/bugProject/selfJson",
				success : function(data) {
					$.each(data, function(i, it) {
						console.info(it.name);
						$("<option value='" + it.id + "' >"
								+ it.name +"</option>").click(function() {
							initVersion(it.id);
						}).appendTo($('#project'));
					});
				}
			});
		}
		/**
		 * 获取版本列表
		 */
		function initVersion(projectId) {
			$('#version').empty();
			$.ajax({
				type : "POST",
				url :  "${ctx}/bug/bugProject/findProjectVersionJson?projectId=" + projectId,
				success : function(data) {
					$.each(data, function(i, it) {
						$("<option value='" + it.id + "' >"
								+ it.version + ":"+it.build+"</option><br>").appendTo($('#version'));
					});
				}
			});
		}
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="bug" action="${ctx}/bug/bug/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden id="flag" path="act.flag"/>
			<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>

					<td class="width-15 active"><label class="pull-right">项目主键：</label></td>
					<td class="width-35">
						<%--<sys:treeselect id="bugProject" name="bugProject.id" value="${bug.bugProject.id}" labelName="" labelValue="${bug.bugProject.name}"--%>
										<%--title="部门" url="/sys/office/treeData?type=2" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>--%>

							<%--<form:select path="bugProject" class="form-control required">--%>
								<%--<form:option value="" label=""/>--%>
								<%--<form:options items="" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
							<%--</form:select>--%>

							<select name="bugProject.id"  id='project' class="form-control required"><option>---项目---</option></select>
					</td>
					<td class="width-15 active"><label class="pull-right">项目版本主键：</label></td>
					<td class="width-35">
						<%--<sys:treeselect id="bugVersion" name="bugVersion.id" value="${bug.bugVersion.id}" labelName="" labelValue="${bug.bugVersion.version}-${bug.bugVersion.build}"--%>
							<%--title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>--%>
							<%--<form:select path="bugVersion" class="form-control required">--%>
								<%--<form:option value="" label=""/>--%>
								<%--<form:options items="${fns:getProjectVersionList('bug_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
							<%--</form:select>--%>

							<select name="bugVersion.id" id='version' class="form-control required"><option>---版本---</option></select>

					</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>缺陷类型（0：BUG;1:改进；2：任务；3：需求）：</label></td>
					<td class="width-35">
						<form:select path="bugType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('bug_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>缺陷状态（0：新建；1：进行中；2：重开；3：已解决；4：暂缓；5：不解决；6：已关闭）：</label></td>
					<td class="width-35">
						<form:select path="bugStatus" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('poster_task')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>缺陷优先级（0：低；1：普通；2：高；3：紧急）：</label></td>
					<td class="width-35">
						<form:select path="bugLevel" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('bug_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">简介：</label></td>
					<td class="width-35">
						<form:input path="summary" htmlEscape="false" maxlength="255" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">内容详情：</label></td>
					<td class="width-35">
						<%--<input type="hidden" id="content" name="content">--%>
						<input type="hidden" id="content" name="content">
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">缺陷文件：</label></td>
					<td class="width-35">
						<form:hidden id="file" path="file" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="file" type="files" uploadPath="/bug/bug" selectMultiple="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">缺陷图片：</label></td>
					<td class="width-35">
						<form:hidden id="image" path="image" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="image" type="files" uploadPath="/bug/bug" selectMultiple="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">测试主管意见：</label></td>
					<td class="width-35">
						<form:textarea path="testerLeadText" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">开发主管意见：</label></td>
					<td class="width-35">
						<form:textarea path="developerLeadText" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目经理意见：</label></td>
					<td class="width-35">
						<form:textarea path="projectManagerText" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
			<div class="form-actions">
				<shiro:hasPermission name="bug:bug:edit">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" onclick="$('#flag').val('NEW')"/>&nbsp;
					<c:if test="${not empty bug.id}">
						<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="丢弃" onclick="$('#flag').val('RENEW')"/>&nbsp;
					</c:if>
				</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
			<c:if test="${not empty bug.id}">
				<act:histoicFlow procInsId="${testAudit.act.procInsId}" />
			</c:if>


	</form:form>
</body>

</html>