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
			
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="bug" action="${ctx}/bug/bug/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
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
							<form:options items="${fns:getDictList('bug_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
						<form:textarea path="summary" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">内容详情：</label></td>
					<td class="width-35">
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目版本主键：</label></td>
					<td class="width-35">
						<form:input path="bugVersion.id" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目主键：</label></td>
					<td class="width-35">
						<form:input path="bugProject.id" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>