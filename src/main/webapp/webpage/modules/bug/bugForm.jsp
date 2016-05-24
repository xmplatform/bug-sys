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
 <div class="ibox-content">
		<form:form id="inputForm" modelAttribute="bug" action="${ctx}/bug/bug/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden id="flag" path="act.flag"/>
			<sys:message content="${message}"/>
			
		<%--<table class="">--%>
			<%--<tr>--%>
				<%--<td>项目</td>--%>
				<%--<td><select name="bugVersion.id"  id="version" class="required" /></td>--%>
				<%--<td>版本</td>--%>
				<%--<td><select name="bugProject.id"  id="project" class="required" /></td>--%>
			<%--</tr>--%>
		<%--</table>--%>
			
			
		<!-- <div class="row">
		<label class="col-sm-2 control-label">版本：</label>
			<div class="col-sm-4">
				<select name="bugVersion.id"  id="version" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			<label class="col-sm-2 control-label" >项目：</label>
			<div class="col-sm-4">
				<select name="bugProject.id"  id="project" class="required" /><font color="red">*</font> 
			</div>
			
			
		</div> -->

		<div class="form-group">
			<label class="col-sm-2 control-label" >项目：</label>
			<div class="col-sm-4">
				<select name="bugProject.id"  id="project" class=" form-control required" /><font color="red">*</font>
			</div>

		</div>

		<div class="form-group">

			<label class="col-sm-2 control-label">版本：</label>
			<div class="col-sm-4">
				<select name="bugVersion.id"  id="version" class=" form-control required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">缺陷类型：</label>
			<div class="col-sm-10">
				<form:select path="bugType" class=" form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
			
		
		<div class="form-group">
			<label class="col-sm-2 control-label">缺陷状态：</label>
			<div class="col-sm-10">
				<form:select path="bugStatus" class="form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('poster_task')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">缺陷优先级：</label>
			<div class="col-sm-10">
				<form:select path="bugLevel" class=" form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		
		<div class="form-group">
			<label class="col-sm-2 control-label">名称：</label>
			<div class="col-sm-10">
				<form:input path="name" htmlEscape="false" maxlength="64" class=" form-control input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
	
		
		
		

		<div class="form-group">
			<label class="col-sm-2 control-label">简介：</label>
			<div class="col-sm-10">
				<form:textarea path="summary" htmlEscape="false" rows="4" maxlength="255" class=" form-control input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label">内容详情:</label>
			<div class="col-sm-10">
				<form:textarea id="description" htmlEscape="true" path="description" rows="4" maxlength="200" class=" form-control input-xxlarge"/>
				<sys:ckeditor replace="description" uploadPath="/bug/bug" />
			</div>
		</div>
		
	
		<div class="form-group">
			<label class="col-sm-2 control-label">图片:</label>
			<div class="col-sm-10">
                <input type="hidden" id="image" name="image" value="${image}" />
				<sys:ckfinder input="image" type="thumb" uploadPath="/bug/bug" selectMultiple="true"/>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label">附件:</label>
			<div class="col-sm-10">
                <input type="hidden" id="file" name="file" value="${file}" />
				<sys:ckfinder input="file" type="thumb" uploadPath="/bug/bug" selectMultiple="true"/>
			</div>
		</div>
		
		
		<div class="form-group">
			<label class="col-sm-2 control-label">备注信息：</label>
			<div class="col-sm-10">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class=" form-control input-xxlarge "/>
			</div>
		</div>
	
	  <div class="hr-line-dashed"></div>
	  
		<div class="form-group">
			<div class="col-sm-4 col-sm-offset-2">
				<shiro:hasPermission name="bug:bug:edit">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" onclick="$('#flag').val('NEW')"/>&nbsp;
					<c:if test="${not empty bug.id}">
						<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="丢弃" onclick="$('#flag').val('RENEW')"/>&nbsp;
					</c:if>
				</shiro:hasPermission>
				<input id="btnCancel" class="btn btn-white" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</div>
		<c:if test="${not empty bug.id}">
			<act:histoicFlow procInsId="${bug.act.procInsId}"/>
		</c:if>
		
		
		
	
		


	</form:form>
	
	</div>
</body>

</html>