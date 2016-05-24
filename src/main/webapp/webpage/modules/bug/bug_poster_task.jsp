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
<body class="gray-bg">
<div class="wrapper wrapper-content">
	<div class="row animated fadeInRight">


		<div class="col-lg-8 col-md-8">
			<div class="ibox float-e-margins">


				<div class="ibox-title">
					<h5>重新提交问题单</h5>

					<div class="ibox-tools">
						<a class="collapse-link">
							<i class="fa fa-chevron-up"></i>
						</a>
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<i class="fa fa-wrench"></i>
						</a>
						<ul class="dropdown-menu dropdown-user">
							<li><a href="#">Config option 1</a>
							</li>
							<li><a href="#">Config option 2</a>
							</li>
						</ul>
						<a class="close-link">
							<i class="fa fa-times"></i>
						</a>
					</div>
				</div>

				<div class="ibox-content">
					<div class="row">
			<form:form id="inputForm" modelAttribute="bug" action="${ctx}/bug/bug/saveAudit" method="post" class="form-horizontal">

				<form:hidden path="id"/>
				<form:hidden path="act.taskId"/>
				<form:hidden path="act.taskName"/>
				<form:hidden path="act.taskDefKey" id="taskDefKey"/>
				<form:hidden path="act.procInsId"/>
				<form:hidden path="act.procDefId"/>
				<form:hidden id="flag" path="act.flag"/>
				<sys:message content="${message}"/>

				<div class="form-group">
					<label class="col-sm-2 control-label">项目：</label>
					<div class="col-sm-10">
							${bug.bugProject.name}


					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">版本：</label>
					<div class="col-sm-10">
							${bug.bugVersion.version}:${bug.bugVersion.build}
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">缺陷类型：</label>
					<div class="col-sm-10">
						<form:select path="bugType" class=" form-control input-xlarge required" disabled="true">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('bug_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">缺陷状态：</label>
					<div class="col-sm-10">
						${bug.bugStatusPhrase}
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">缺陷优先级：</label>
					<div class="col-sm-10">
						<form:select path="bugLevel" class=" form-control input-xlarge required" disabled="true">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('bug_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false" maxlength="64" class=" form-control input-xlarge required" disabled="true"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">简介：</label>
					<div class="col-sm-10">
						<form:textarea path="summary" htmlEscape="false" rows="4" maxlength="255" class=" form-control input-xxlarge required" disabled="true"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">内容详情：</label>
					<div class="col-sm-10">
						<form:textarea id="description" htmlEscape="true" path="description" rows="4" maxlength="200" class=" form-control input-xxlarge" disabled="true"/>
						<sys:ckeditor replace="description" uploadPath="/bug/bug" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">图片：</label>
					<div class="col-sm-10">
						<img src="${bug.image}">
						<input type="hidden" id="image" name="image" value="${image}" />
						<sys:ckfinder input="image" type="thumb" uploadPath="/bug/bug" selectMultiple="false"/>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label">附件:</label>
					<div class="col-sm-10">
						<a href="${bug.file}">文件</a>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class=" form-control input-xxlarge " disabled="true"/>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label">您的看法：</label>
					<div class="col-sm-10">
						<form:select path="bugStatus" class="form-control required" >
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList(bug.act.taskDefKey)}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label">看法原因：</label>
					<div class="col-sm-10">
						<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>

				<div class="hr-line-dashed"></div>

				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="bug:bug:edit">
							<input id="btnSubmit" class="btn btn-primary" type="submit"  value="确认"/>
						</shiro:hasPermission>
						<input id="btnCancel" class="btn btn-white" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
					</div>
					</div>
			</div>
		</div>

		<div class="col-lg-4 col-md-4">
			<act:histoicFlow procInsId="${bug.act.procInsId}"/>
		</div>
	</div>
</div>
</body>
</html>
