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
 <div class="ibox-content">
		<form:form id="inputForm" modelAttribute="bug" action="${ctx}/bug/bug/start" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden id="flag" path="act.flag"/>
			<sys:message content="${message}"/>
			


		<div class="form-group">
			<label class="col-sm-2 control-label" >项目：</label>
			<div class="col-sm-4">
				<%--<select name="bugProject.id"  id="project" class=" form-control required" /><font color="red">*</font>--%>
					<form:select path="bugProject.id"  class="form-control" id="selectSelfProject">
						<form:option value="" label="请选择项目"/>
						<form:options items="${bugProjectList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
			</div>

		</div>

		<div class="form-group">

			<label class="col-sm-2 control-label">版本：</label>
			<div class="col-sm-4">
				<div class ="row">
					<div class="col-sm-10">
						<select name="bugVersion.id"  id="selectVersion" class=" form-control required">
							<option value="${bug.bugVersion.id}" label="${bug.bugVersion.version}:${bug.bugVersion.build}"></option>
						</select>
					</div>

				</div>


			</div>


		</div>



		<div class="form-group">
			<label class="col-sm-2 control-label">名称：</label>
			<div class="col-sm-10">
				<form:input path="name" htmlEscape="false" maxlength="64" class=" form-control input-xlarge required"/>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">平台：</label>
			<div class="col-sm-10">
				<form:select path="bugPlatform" class=" form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_platform')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>


		<div class="form-group">
			<label class="col-sm-2 control-label">操作系统以及版本：</label>
			<div class="col-sm-10">
				<form:select path="bugSystemAndVersion" class=" form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_systemandversion')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>




		<div class="form-group">
			<label class="col-sm-2 control-label">分类：</label>
			<div class="col-sm-4">
				<form:select path="bugType" class=" form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">优先级：</label>
			<div class="col-sm-10">
				<form:select path="bugLevel" class=" form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">严重性：</label>
			<div class="col-sm-10">
				<form:select path="bugSerious" class=" form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_serious')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">频率：</label>
			<div class="col-sm-10">
				<form:select path="bugFrequency" class=" form-control input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_frequency')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		

		
		
		

		<div class="form-group">
			<label class="col-sm-2 control-label">简介：</label>
			<div class="col-sm-10">
				<form:textarea path="summary" htmlEscape="false" rows="4" maxlength="255" class=" form-control input-xxlarge required"/>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label">重现步骤:</label>
			<div class="col-sm-10">
				<form:textarea id="step2Reproduce" htmlEscape="true" path="step2Reproduce" rows="4" maxlength="200" class=" form-control input-xxlarge"/>
				<sys:ckeditor replace="step2Reproduce" uploadPath="/bug/bug" />
			</div>
		</div>


		<div class="form-group">
			<label class="col-sm-2 control-label">实际行为:</label>
			<div class="col-sm-10">
				<form:textarea id="behavior" htmlEscape="true" path="behavior" rows="4" maxlength="200" class=" form-control input-xxlarge"/>
				<sys:ckeditor replace="behavior" uploadPath="/bug/bug" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">期望结果:</label>
			<div class="col-sm-10">
				<form:textarea id="expected" htmlEscape="true" path="expected" rows="4" maxlength="200" class=" form-control input-xxlarge"/>
				<sys:ckeditor replace="expected" uploadPath="/bug/bug" />
			</div>
		</div>
	
		<div class="form-group">
			<label class="col-sm-2 control-label">截图:</label>
			<div class="col-sm-10">
                <input type="hidden" id="image" name="image" value="${image}" />
				<sys:ckfinder input="image" type="thumb" uploadPath="/bug/image" selectMultiple="true"/>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label">附件:</label>
			<div class="col-sm-10">
                <input type="hidden" id="file" name="file" value="${file}" />
				<sys:ckfinder input="file" type="files" uploadPath="/bug/file" selectMultiple="true"/>
			</div>
		</div>
		
		
		<div class="form-group">
			<label class="col-sm-2 control-label">备注信息：</label>
			<div class="col-sm-10">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class=" form-control input-xxlarge "/>
			</div>
		</div>



		<div class="form-group">
			<label class="col-sm-2 control-label">操作：</label>
			<div class="col-sm-10">
				<form:select path="bugStatus" class="form-control input-xlarge required" id="getNextTaskGroup">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('apply_task')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">分派给：</label>
			<div class="col-sm-10">
				<select name="assign"  id="nextTaskGroup" class=" form-control required" ></select><font color="red">*</font>
			</div>
		</div>


	
	  <div class="hr-line-dashed"></div>
	  
		<div class="form-group">
			<div class="col-sm-4 col-sm-offset-2">
				<shiro:hasPermission name="bug:bug:task">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;
				</shiro:hasPermission>
				<input id="btnCancel" class="btn btn-white" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</div>
		<c:if test="${not empty bug.id}">
			<act:histoicFlow procInsId="${bug.act.procInsId}"/>
		</c:if>
		
		
		
	
		


	</form:form>
	
	</div>

<script type="text/javascript">

	$(function () {

		$("#behavior").html($("#behavior").text());
		$("#expected").html($("#expected").text());
		$("#step2Reproduce").html($("#step2Reproduce").text());



		var configVersion={
			selectId:"selectSelfProject",
			showId:"selectVersion"
		}


		var projectId=$("#"+configVersion.selectId).val();
		var configNextTask={
			projectSelect:"selectSelfProject",
			selectId:"getNextTaskGroup",
			showId:"nextTaskGroup",
			url:"${ctx}/bug/bug/task/startNext",
			postData:{
				projectId:"",
				status:""
			}
		};
		loadProjectVersion(configVersion);

		getStartEventNextTask(configNextTask);

//		if(projectId==undefined||$.trim(projectId)==''){
//			toastr.info('提示', '请选择项目');
//		}else{
//
//		}

	});
</script>
 <script type="text/javascript" src="${ctxStatic}/modules/bug/projectSelectVersion.js"></script>
 <script type="text/javascript" src="${ctxStatic}/modules/bug/bugtask.js"></script>
</body>

</html>