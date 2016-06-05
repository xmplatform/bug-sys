<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>bug 审核管理</title>
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

		});
	</script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
	<div class="row animated fadeInRight">


		<!--bug 信息查看-->
		<%@ include file="/webpage/modules/bug/bug_task_view.jsp"%>
		<!--测试人员任务-->
		<div class="row">

			<div class="ibox float-e-margins">


				<div class="ibox-title">
					<h5>问题单</h5>

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


						<form:form id="inputForm" modelAttribute="bug" action="${ctx}/bug/bug/completeTask" method="post" class="form-horizontal">
							<form:hidden path="id"/>
							<form:hidden path="act.taskId"/>
							<form:hidden path="act.taskName"/>
							<form:hidden path="act.taskDefKey" id="taskDefKey"/>
							<form:hidden path="act.procInsId"/>
							<form:hidden path="act.procDefId"/>
							<c:set var="procInsId" value="${bug.act.procInsId}" />
							<form:hidden id="flag" path="act.flag"/>
							<sys:message content="${message}"/>


							<div class="form-group">
								<label class="col-sm-2 control-label">操作：</label>
								<div class="col-sm-10">
									<form:select path="bugStatus" class="form-control required" id="getNextTaskGroup">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList(bug.act.taskDefKey)}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</div>
							</div>


							<div class="form-group">
								<label class="col-sm-2 control-label">说明：</label>
								<div class="col-sm-10">
									<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
								</div>
							</div>


							<div class="form-group">
								<label class="col-sm-2 control-label">分派给：</label>
								<div class="col-sm-10">
									<select name="assign"  id="nextTaskGroup" class=" form-control required" />
								</div>
							</div>
						</form:form>

					</div><%--ibox-content--%>
				</div><%--row--%>
			</div>
		</div><!--测试人员任务-->
	</div>
</div>


<script type="text/javascript">

	$(function () {
		var config={
			selectId:"getNextTaskGroup",
			showLabel:"showLabel",
			showId:"nextTaskGroup",
			postData:{
				projectId:"${bug.bugProject.id}",
				proInstId:"${procInsId}",
				status:""
			}
		}
		getNextTask(config);
	});
</script>

<script type="text/javascript" src="${ctxStatic}/modules/bug/bugtask.js"></script>
</body>
</html>
