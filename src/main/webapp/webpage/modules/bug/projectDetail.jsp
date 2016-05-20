<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		     WinMove();
		});
	</script>
</head>
<body class="gray-bg">

             <div class="wrapper wrapper-content">
                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <div class="ibox float-e-margins">
                            <div class="ibox-content">
                                    <div>
                                        <%--<span class="pull-right text-right">--%>
                                        <%--<small>Average value of sales in the past month in: <strong>United states</strong></small>--%>
                                            <%--<br/>--%>
                                            <%--All sales: 162,862--%>
                                        <%--</span>--%>
                                        <%--<h1 class="m-b-xs">$ 50,992()</h1>--%>
                                        <h3 class="font-bold no-margins">
                                            最近7天内的 Bug
                                        </h3>
                                        <small>Bug 统计.</small>
                                    </div>

                                <div>
                                    <canvas id="lineChart"></canvas>
                                    <%--<canvas id="canvas"></canvas>--%>
                                </div>

                                <div class="m-t-md">
                                    <small class="pull-right">
                                        <i class="fa fa-clock-o"> </i>
                                        Update today
                                    </small>
                                   <%--<small>--%>
                                       <%--<strong>Analysis of sales:</strong> The value has been changed over time, and last month reached a level over $50,000.--%>
                                   <%--</small>--%>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>



                 <!--我的任务-->
                 <div class="row">




                     <div class="col-lg-4 col-md-4">
                         <div class="ibox float-e-margins">
                             <div class="ibox-title">
                                 <span class="label label-primary pull-right">Today</span>
                                 <h5>指派给你的</h5>
                             </div>
                             <div class="ibox-content">
                                 <h1 class="no-margins">5</h1>
                                 <div class="stat-percent font-bold text-navy">20% <i class="fa fa-level-up"></i></div>
                                 <small>指派给你的 bug </small>
                             </div>
                         </div>
                     </div>



                     <div class="col-lg-4 col-md-4">
                         <div class="ibox float-e-margins">
                             <div class="ibox-title">
                                 <span class="label label-info pull-right">Monthly</span>
                                 <h5>你提交的</h5>
                             </div>
                             <div class="ibox-content">
                                 <h1 class="no-margins">60 420,600</h1>
                                 <div class="stat-percent font-bold text-info">40% <i class="fa fa-level-up"></i></div>
                                 <small>New orders</small>
                             </div>
                         </div>
                     </div>
                     <div class="col-lg-4 col-md-4">
                         <div class="ibox float-e-margins">
                             <div class="ibox-title">
                                 <span class="label label-warning pull-right">Annual</span>
                                 <h5>你追踪的</h5>
                             </div>
                             <div class="ibox-content">
                                 <h1 class="no-margins">$ 120 430,800</h1>
                                 <div class="stat-percent font-bold text-warning">16% <i class="fa fa-level-up"></i></div>
                                 <small>New orders</small>
                             </div>
                         </div>
                     </div>
                 </div>

                 <!--应用状态,应用信息-->
                 <div class="row">

                     <div class="col-lg-6 col-md-6 ">
                         <div class="ibox float-e-margins">
                             <div class="ibox float-e-margins">
                                 <div class="ibox-title">
                                     <h5>应用状态</h5>

                                     <div ibox-tools></div>
                                 </div>
                                 <div class="ibox-content">
                                     <div>
                                         <canvas id="projectStatusChart" height="140"></canvas>
                                     </div>
                                 </div>
                             </div>
                         </div>
                     </div>


                     <div class="col-lg-6 col-md-6 ">
                         <div class="ibox float-e-margins">
                             <div class="ibox float-e-margins">
                                 <div class="ibox-title">
                                     <h5>应用信息</h5>

                                     <div ibox-tools></div>
                                 </div>
                                 <div class="ibox-content">
                                     <div class="row">
                                         <div class="col-lg-12">
                                             <div class="m-b-md">
                                                 <%--<a href="#" class="btn btn-white btn-xs pull-right">Edit project</a>--%>
                                                 <h2>${bugProject.name}</h2>
                                             </div>
                                             <dl class="dl-horizontal">
                                                 <dt>创建者:</dt> <dd><span class="label label-primary">${bugProject.createBy.name}</span></dd>
                                             </dl>
                                         </div>
                                     </div>
                                     <div class="row">
                                         <div class="col-lg-12" id="cluster_info">
                                             <dl class="dl-horizontal" >


                                                 <dt>创建时间:</dt> <dd><fmt:formatDate value="${bugProject.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
                                                 <dt>修改时间:</dt><dd><fmt:formatDate value="${bugProject.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
                                                 <dt>项目组成员:</dt>
                                                 <dd class="project-people" id="project-people">

                                                     <c:forEach items="${bugProject.userList}" var="user">
                                                         <a href=""><img alt="${user.name}" class="img-circle" src="${user.photo}"></a>
                                                     </c:forEach>
                                                 </dd>
                                             </dl>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                         </div>
                     </div>

                 </div>


           
    </div>
<!-- Mainly scripts -->
    <script src="${ctxStatic}/js/jquery-2.1.1.js"></script>
    <script src="${ctxStatic}/js/bootstrap.min.js"></script>
    <script src="${ctxStatic}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctxStatic}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Flot -->
    <script src="${ctxStatic}/js/plugins/flot/jquery.flot.js"></script>
    <script src="${ctxStatic}/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="${ctxStatic}/js/plugins/flot/jquery.flot.spline.js"></script>
    <script src="${ctxStatic}/js/plugins/flot/jquery.flot.resize.js"></script>
    <script src="${ctxStatic}/js/plugins/flot/jquery.flot.pie.js"></script>
    <script src="${ctxStatic}/js/plugins/flot/jquery.flot.symbol.js"></script>
    <script src="${ctxStatic}/js/plugins/flot/curvedLines.js"></script>

    <!-- Peity -->
    <script src="${ctxStatic}/js/plugins/peity/jquery.peity.min.js"></script>
    <script src="${ctxStatic}/js/demo/peity-demo.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctxStatic}/common/inspinia.js"></script>
    <script src="${ctxStatic}/js/plugins/pace/pace.min.js"></script>

    <!-- jQuery UI -->
    <script src="${ctxStatic}/js/plugins/jquery-ui/jquery-ui.min.js"></script>

    <!-- Jvectormap -->
    <script src="${ctxStatic}/js/plugins/jvectormap/jquery-jvectormap-2.0.2.min.js"></script>
    <script src="${ctxStatic}/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>

    <!-- Sparkline -->
    <script src="${ctxStatic}/js/plugins/sparkline/jquery.sparkline.min.js"></script>

    <!-- Sparkline demo data  -->
    <script src="${ctxStatic}/js/demo/sparkline-demo.js"></script>

    <!-- ChartJS-->
    <script src="${ctxStatic}/js/plugins/chartJs/Chart.js"></script>

    <script>
        $(document).ready(function() {



            // 初始化 图表 projectDetail.js
            initProjectData2("${ctx}","${bugProject.id}");
            initProjectTotalByDay("${bugProject.id}",7);

            loadProjectUserImage();



        });

    </script>

<script type="application/javascript" src="${ctxStatic}/modules/bug/projectDetail.js"></script>
<script type="application/javascript" src="${ctxStatic}/modules/bug/projectTotal.js"></script>

</body>
</html>