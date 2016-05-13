<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>运行中的执行实例列表--chapter13</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        div.datepicker {z-index: 10000;}
    </style>

</head>
<body>

<ul class="nav nav-tabs">
    <li><a href="${ctx}/act/task/todo/">待办任务</a></li>
    <li class="active"><a href="${ctx}/act/task/join/">参与任务</a></li>
    <li><a href="${ctx}/act/task/historic/">已办任务</a></li>
    <li><a href="${ctx}/act/task/process/">新建任务</a></li>
</ul>
    <table width="100%" class="table table-bordered table-hover table-condensed">
        <thead>
        <tr>
            <th>执行ID</th>
            <th>流程实例ID</th>
            <th>所属流程</th>
            <th>流程定义ID</th>
            <th>当前节点</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.result }" var="e">
            <tr>
                <td>${e.id}</td>
                <td>${e.processInstanceId}</td>
                <td>${definitions[e.processDefinitionId].name}</td>
                <td>${e.processDefinitionId}</td>
                <td>
                    <c:forEach items="${currentActivityMap[e.id]}" var="acid">
                        <c:set var="task" value="${taskMap[acid]}" />

                        <a onclick='top.openTab("${ctx }/act/trace/view/${task.executionId}","流程跟踪--${e.processInstanceId}", false)'>
                        <%--<a   target="_blank" href="${ctx }/act/trace/view/${task.executionId}">--%>
                            <%-- 处理[调用活动] --%>
                            <c:if test="${task.processDefinitionId != e.processDefinitionId}">
                                <span title='引用了外部流程'>${definitions[task.processDefinitionId].name}</span><i style="margin-left: 0.5em;" class="icon-circle-arrow-right"></i>
                            </c:if>
                            ${task.name}
                        </a>
                        <c:if test="${empty task.assignee}">（<span class="text-info">未签收</span>）</c:if>
                        <c:if test="${not empty task.assignee}">
                            （<span class="text-info">办理中</span><i class="icon-user"></i><span class="text-success">${task.assignee}</span>）
                        </c:if>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
       </tbody>
    </table>


    <page:pagination page="${page}" paginationSize="${page.pageSize}"/>

    <!-- 流程跟踪对话框 -->
    <div id="traceProcessModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="traceProcessModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="traceProcessModalLabel">新任务</h3>
        </div>
        <div class="modal-body">
            <iframe src="" frameborder="0"></iframe>
        </div>
    </div>
</body>
</html>