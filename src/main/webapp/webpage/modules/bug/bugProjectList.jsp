<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
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
		<h5>项目列表 </h5>
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
	<form:form id="searchForm" modelAttribute="bugProject" action="${ctx}/bug/bugProject/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="bug:bugProject:add">
				<table:addRow url="${ctx}/bug/bugProject/form" title="项目"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bug:bugProject:edit">
			    <table:editRow url="${ctx}/bug/bugProject/form" title="项目" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bug:bugProject:del">
				<table:delRow url="${ctx}/bug/bugProject/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bug:bugProject:import">
				<table:importExcel url="${ctx}/bug/bugProject/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bug:bugProject:export">
	       		<table:exportExcel url="${ctx}/bug/bugProject/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">项目名称</th>
				<th  class="sort-column name">项目LOGO</th>
				<th  class="sort-column summary">项目简介</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bugProject">

			<tr>
				<td> <input type="checkbox" id="${bugProject.id}" class="i-checks"></td>
				<td>


					<a  href="#" onclick="openDialogView('查看项目', '${ctx}/bug/bugProject/form?id=${bugProject.id}','800px', '500px')">

						<%--<c:if test="${not empty bugProject.processKey}">--%>
							<%----%>
						<%--</c:if>--%>

						<c:if test="${empty bugProject.processKey}">
							<span  class=" label ">${bugProject.name}</span>
						</c:if>

							<c:if test="${!empty bugProject.processKey}">
								<span  class=" label label-primary">${bugProject.name}</span>
							</c:if>


					</a>

				</td>
				<td>
					<img src="${bugProject.logo}" width="50" height="50">
				</td>
				<td>
					${bugProject.summary}
				</td>
				<td>
					<shiro:hasPermission name="bug:bugProject:view">
						<a href="#" onclick="openDialogView('查看项目', '${ctx}/bug/bugProject/form?id=${bugProject.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="bug:bugProject:edit">
    					<a href="#" onclick="openDialog('修改项目', '${ctx}/bug/bugProject/form?id=${bugProject.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="bug:bugProject:del">
						<a href="${ctx}/bug/bugProject/delete?id=${bugProject.id}" onclick="return confirmx('确认要删除该项目吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>

					<shiro:hasPermission name="sys:role:assign">
						<a href="#" onclick="openDialogView('分配用户', '${ctx}/bug/bugProject/assign?id=${bugProject.id}','800px', '600px')"  class="btn  btn-warning btn-xs" ><i class="glyphicon glyphicon-plus"></i> 分配用户</a>
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