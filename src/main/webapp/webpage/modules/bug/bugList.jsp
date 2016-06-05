<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>缺陷管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>缺陷列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="bug" action="${ctx}/bug/bug/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>缺陷类型：</span>
				<form:select path="bugType"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span>状态：</span>
				<form:select path="bugStatus"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			<span>严重：</span>
				<form:select path="bugSerious"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_serious')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			<span>频率：</span>
				<form:select path="bugFrequency"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bug_frequency')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			<span>优先级：</span>
			<form:select path="bugLevel"  class="form-control m-b">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('bug_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">

			<shiro:hasPermission name="bug:bug:del">
				<table:delRow url="${ctx}/bug/bug/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bug:bug:import">
				<table:importExcel url="${ctx}/bug/bug/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bug:bug:export">
	       		<table:exportExcel url="${ctx}/bug/bug/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">

		<thead>
		<tr>
			<th> <input type="checkbox" class="i-checks"></th>

			<th  class="sort-column name">问题单</th>

			<th  class="sort-column bugType">问题类型</th>
			<th  class="sort-column bugStatus">状态</th>
			<th  class="sort-column bugLevel">优先级</th>
			<th  class="sort-column bugSerious">严重度</th>
			<th  class="sort-column bugFrequency">频率</th>

			<th  class="sort-column ">项目</th>
			<th  class="sort-column ">版本</th>


				<%--<th  class="sort-column " >标题</th>--%>
			<th  class="sort-column ">当前环节</th>
			<th  class="sort-column ">流程名称</th>
			<th  class="sort-column ">流程版本</th>
			<th  class="sort-column ">创建时间</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bug">
			<c:set var="act" value="${bug.act}"></c:set>

			<c:set var="task" value="${act.task}" />
			<c:set var="taskId" value="${task.id}"/>
			<c:set var="vars" value="${act.vars}" />
			<c:set var="procDef" value="${act.procDef}" />
			<c:set var="status" value="${act.status}" />


			<tr>
				<td> <input type="checkbox" id="${bug.id}" class="i-checks"></td>
				<td>

							${bug.name}
				</td>

				<td>
						${fns:getDictLabel(bug.bugType, 'bug_type', '')}
				</td>
				<td>
						${fns:getDictLabel(bug.bugStatus, 'bug_status', '')}
				</td>
				<td>
						${fns:getDictLabel(bug.bugLevel, 'bug_level', '')}
				</td>

				<td>
						${fns:getDictLabel(bug.bugSerious, 'bug_serious', '')}
				</td>
				<td>
						${fns:getDictLabel(bug.bugFrequency, 'bug_frequency', '')}
				</td>

				<td>
						${bug.bugProject.name}
				</td>
				<td>
						${bug.bugVersion.version}
				</td>

					<%--<td>--%>
					<%--<c:if test="${empty task.assignee}">--%>
					<%--<a href="javascript:claim('${task.id}');" title="签收任务">${fns:abbr(not empty act.vars.map.title ? act.vars.map.title : task.id, 60)}</a>--%>
					<%--</c:if>--%>
					<%--<c:if test="${not empty task.assignee}">--%>
					<%--<a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</a>--%>
					<%--</c:if>--%>
					<%--</td>--%>
				<td>
					${task.name}
				</td>

					<%--
            <td>${task.description}</td> --%>

				<td>${procDef.name}</td>
				<td><b title='流程版本号'>V: ${procDef.version}</b></td>
				<td><fmt:formatDate value="${task.createTime}" type="both"/></td>
				<td>




					<c:if test="${not empty task}">
						<shiro:hasPermission name="bug:bug:task">
							<a href="#" onclick="openDialogView('查看缺陷', '${ctx}/bug/bug/task/view/${taskId}?id=${bug.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="bug:bug:task">
							<a href="#" onclick="openDialogView('追踪缺陷', '${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-s"></i> 追踪</a>
						</shiro:hasPermission>
					</c:if>

					<shiro:hasPermission name="bug:bug:del">
						<a href="${ctx}/bug/bug/delete?id=${bug.id}" onclick="return confirmx('确认要删除该缺陷吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>

				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>