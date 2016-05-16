<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>


		<div class="ibox-title">
			<h5>流转环节</h5>
			<span class="label label-primary">Bug Trace</span>
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

		<div class="ibox-content inspinia-timeline">
			<c:forEach items="${histoicFlowList}" var="act">
				<div class="timeline-item">
					<div class="row">
						<div class="col-xs-3 date">
							<i class="fa fa-briefcase"></i>
							<fmt:formatDate value="${act.histIns.startTime}" type="both"/>
							<br/>
							<small class="text-navy">
									${act.durationTime}
							</small>
						</div>
						<div class="col-xs-7 content no-top-border">
							<p class="m-b-xs"><strong>${act.histIns.activityName}「${act.assigneeName}」</strong></p>

							<p>${act.comment}</p>

						</div>
					</div>
				</div>

			</c:forEach>
		</div>
	</div>

<%--<table class="table table-striped table-bordered table-condensed">--%>
	<%--<tr><th>执行环节</th><th>执行人</th><th>开始时间</th><th>结束时间</th><th>提交意见</th><th>任务历时</th></tr>--%>
	<%--<c:forEach items="${histoicFlowList}" var="act">--%>
		<%--<tr>--%>
			<%--<td>${act.histIns.activityName}</td>--%>
			<%--<td>${act.assigneeName}</td>--%>
			<%--<td><fmt:formatDate value="${act.histIns.startTime}" type="both"/></td>--%>
			<%--<td><fmt:formatDate value="${act.histIns.endTime}" type="both"/></td>--%>
			<%--<td style="word-wrap:break-word;word-break:break-all;">${act.comment}</td>--%>
			<%--<td>${act.durationTime}</td>--%>
		<%--</tr>--%>
	<%--</c:forEach>--%>
<%--</table>--%>



