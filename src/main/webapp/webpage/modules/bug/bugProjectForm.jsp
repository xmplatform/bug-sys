<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="bugProject" action="${ctx}/bug/bugProject/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>项目名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="64" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目简介：</label></td>
					<td class="width-35">
						<form:textarea path="summary" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>项目LOGO：</label></td>
					<td class="width-35">
						<form:hidden id="logo" path="logo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<sys:ckfinder input="logo" type="images" uploadPath="/project/logo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
					</td>
					<td class="width-15 active"><label class="pull-right">指定流程：</label></td>
					<td class="width-35">
						<form:select path="processKey"  class="form-control">
							<form:option value="" label="请选择流程"/>
							<form:options items="${processList}" itemLabel="name" itemValue="key" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
		
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">项目版本表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#bugVersionList', bugVersionRowIdx, bugVersionTpl);bugVersionRowIdx = bugVersionRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>版本,限10个字符</th>
						<th>build,限10个字符</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="bugVersionList">
				</tbody>
			</table>
			<script type="text/template" id="bugVersionTpl">//<!--
				<tr id="bugVersionList{{idx}}">
					<td class="hide">
						<input id="bugVersionList{{idx}}_id" name="bugVersionList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="bugVersionList{{idx}}_delFlag" name="bugVersionList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="bugVersionList{{idx}}_version" name="bugVersionList[{{idx}}].version" type="text" value="{{row.version}}" maxlength="11" class="form-control required"/>
					</td>
					
					
					<td>
						<input id="bugVersionList{{idx}}_build" name="bugVersionList[{{idx}}].build" type="text" value="{{row.build}}" maxlength="11" class="form-control required"/>
					</td>
					
					
					<td>
						<textarea id="bugVersionList{{idx}}_remarks" name="bugVersionList[{{idx}}].remarks" rows="4" maxlength="255" class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#bugVersionList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var bugVersionRowIdx = 0, bugVersionTpl = $("#bugVersionTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(bugProject.bugVersionList)};
					for (var i=0; i<data.length; i++){
						addRow('#bugVersionList', bugVersionRowIdx, bugVersionTpl, data[i]);
						bugVersionRowIdx = bugVersionRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>