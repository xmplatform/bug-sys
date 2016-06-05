<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>运行中的执行实例列表--chapter13</title>
    <meta name="decorator" content="default"/>
</head>


<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>参与列表 </h5>
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
                    <form:form id="searchForm" modelAttribute="bug" action="${ctx}/bug/bug/task/join" method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">
                            <span>项目版本主键：</span>
                            <sys:treeselect id="bugVersion" name="bugVersion.id" value="${bug.bugVersion.id}" labelName="" labelValue="${bug.bugVersion.version}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
                            <span>缺陷类型：</span>
                            <form:select path="bugType"  class="form-control m-b">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('bug_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                            <span>缺陷状态：</span>
                            <form:select path="bugStatus"  class="form-control m-b">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('bug_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                            <span>缺陷优先级：</span>
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
                        <%--<shiro:hasPermission name="bug:bug:add">--%>
                        <%--<table:addRow url="${ctx}/bug/bug/form" title="新建问题单"></table:addRow><!-- 增加按钮 -->--%>
                        <%--</shiro:hasPermission>--%>
                        <shiro:hasPermission name="bug:bug:edit">
                            <table:editRow url="${ctx}/bug/bug/form" title="处理问题单" id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <%--<shiro:hasPermission name="bug:bug:del">--%>
                        <%--<table:delRow url="${ctx}/bug/bug/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="bug:bug:import">--%>
                        <%--<table:importExcel url="${ctx}/bug/bug/import"></table:importExcel><!-- 导入按钮 -->--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="bug:bug:export">--%>
                        <%--<table:exportExcel url="${ctx}/bug/bug/export"></table:exportExcel><!-- 导出按钮 -->--%>
                        <%--</shiro:hasPermission>--%>
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
                    <th  class="sort-column bugStatus">问题状态</th>
                    <th  class="sort-column bugLevel">问题优先级</th>

                    <th  class="sort-column ">项目</th>
                    <th  class="sort-column ">版本</th>

                    <%--<th>执行ID</th>--%>
                    <%--<th>流程实例ID</th>--%>

                    <th>所属流程</th>
                    <%--<th>流程定义ID</th>--%>
                    <th>当前节点</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="bug">

                    <c:set var="act" value="${bug.act}"></c:set>

                    <%--<c:set var="task" value="${act.task}" />--%>
                    <%--<c:set var="taskId" value="${task.id}"/>--%>
                    <c:set var="vars" value="${act.vars}" />
                    <c:set var="procDef" value="${act.procDef}" />
                    <c:set var="status" value="${act.status}" />

                    <tr>

                        <td> <input type="checkbox" id="${bug.id}" class="i-checks"></td>
                        <td>
                            <a  href="#" onclick="openDialogView('查看缺陷', '${ctx}/bug/bug/form?id=${bug.id}','800px', '500px')">
                                    ${bug.name}
                            </a>
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
                                ${bug.bugProject.name}
                        </td>
                        <td>
                                ${bug.bugVersion.version}
                        </td>

                        <%--<td>${act.executionId}</td>--%>
                        <%--<td>${act.procInsId}</td>--%>
                        <td>${definitions[act.procDefId].name}</td>
                        <%--<td>${act.procDefId}</td>--%>
                        <td>
                            <c:forEach items="${currentActivityMap[act.executionId]}" var="acid">
                                <c:set var="task" value="${taskMap[acid]}" />
                                <a href="#" onclick="openDialogView('流程跟踪','${ctx }/act/trace/view/${task.executionId}','800px', '500px')">
                                        <%--<a   target="_blank" href="${ctx }/act/trace/view/${task.executionId}">--%>
                                        <%-- 处理[调用活动] --%>
                                    <c:if test="${task.processDefinitionId != act.procDefId}">
                                        <span title='引用了外部流程'>${definitions[task.processDefinitionId].name}</span><i style="margin-left: 0.5em;" class="icon-circle-arrow-right"></i>
                                    </c:if>
                                        ${task.name}
                                </a>
                                <c:if test="${empty task.assignee}">「<span class="text-info">未签收</span>」</c:if>
                                <c:if test="${not empty task.assignee}">
                                    「<span class="text-info">办理中</span><i class="fa fa-user"></i><span class="text-success">${task.assignee}</span>」
                                </c:if>
                            </c:forEach>
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