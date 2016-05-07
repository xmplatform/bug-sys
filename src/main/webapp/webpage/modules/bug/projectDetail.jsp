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

  <%--<div class="row">--%>
  		 <%--<div class="sidebard-panel col-lg-4 col-md-3">--%>
                <%--<div>--%>
                    <%--<h4>Messages <span class="badge badge-info pull-right">16</span></h4>--%>
                    <%--<div class="feed-element">--%>
                        <%--<a href="#" class="pull-left">--%>
                            <%--<img alt="image" class="img-circle" src="img/a1.jpg">--%>
                        <%--</a>--%>
                        <%--<div class="media-body">--%>
                            <%--There are many variations of passages of Lorem Ipsum available.--%>
                            <%--<br>--%>
                            <%--<small class="text-muted">Today 4:21 pm</small>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="feed-element">--%>
                        <%--<a href="#" class="pull-left">--%>
                            <%--<img alt="image" class="img-circle" src="img/a2.jpg">--%>
                        <%--</a>--%>
                        <%--<div class="media-body">--%>
                            <%--TIt is a long established fact that.--%>
                            <%--<br>--%>
                            <%--<small class="text-muted">Yesterday 2:45 pm</small>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="feed-element">--%>
                        <%--<a href="#" class="pull-left">--%>
                            <%--<img alt="image" class="img-circle" src="img/a3.jpg">--%>
                        <%--</a>--%>
                        <%--<div class="media-body">--%>
                            <%--Many desktop publishing packages.--%>
                            <%--<br>--%>
                            <%--<small class="text-muted">Yesterday 1:10 pm</small>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="feed-element">--%>
                        <%--<a href="#" class="pull-left">--%>
                            <%--<img alt="image" class="img-circle" src="img/a4.jpg">--%>
                        <%--</a>--%>
                        <%--<div class="media-body">--%>
                            <%--The generated Lorem Ipsum is therefore always free.--%>
                            <%--<br>--%>
                            <%--<small class="text-muted">Monday 8:37 pm</small>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="m-t-md">--%>
                    <%--<h4>Statistics</h4>--%>
                    <%--<p>--%>
                        <%--Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt.--%>
                    <%--</p>--%>
                    <%--<div class="row m-t-sm">--%>
                        <%--<div class="col-md-6">--%>
                            <%--<span class="bar">5,3,9,6,5,9,7,3,5,2</span>--%>
                            <%--<h5><strong>169</strong> Posts</h5>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-6">--%>
                            <%--<span class="line">5,3,9,6,5,9,7,3,5,2</span>--%>
                            <%--<h5><strong>28</strong> Orders</h5>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="m-t-md">--%>
                    <%--<h4>Discussion</h4>--%>
                    <%--<div>--%>
                        <%--<ul class="list-group">--%>
                            <%--<li class="list-group-item">--%>
                                <%--<span class="badge badge-primary">16</span>--%>
                                <%--General topic--%>
                            <%--</li>--%>
                            <%--<li class="list-group-item ">--%>
                                <%--<span class="badge badge-info">12</span>--%>
                                <%--The generated Lorem--%>
                            <%--</li>--%>
                            <%--<li class="list-group-item">--%>
                                <%--<span class="badge badge-warning">7</span>--%>
                                <%--There are many variations--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%----%>
            <%----%>
             <div class="wrapper wrapper-content">
                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <div class="ibox float-e-margins">
                            <div class="ibox-content">
                                    <div>
                                        <span class="pull-right text-right">
                                        <small>Average value of sales in the past month in: <strong>United states</strong></small>
                                            <br/>
                                            All sales: 162,862
                                        </span>
                                        <h1 class="m-b-xs">$ 50,992()</h1>
                                        <h3 class="font-bold no-margins">
                                            Half-year revenue margin(7天内的bug)
                                        </h3>
                                        <small>Sales marketing.</small>
                                    </div>

                                <div>
                                    <canvas id="lineChart" height="70"></canvas>
                                </div>

                                <div class="m-t-md">
                                    <small class="pull-right">
                                        <i class="fa fa-clock-o"> </i>
                                        Update on 16.07.2015
                                    </small>
                                   <small>
                                       <strong>Analysis of sales:</strong> The value has been changed over time, and last month reached a level over $50,000.
                                   </small>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">

                    <div class="col-lg-4 col-md-4">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <span class="label label-info pull-right">Monthly</span>
                                <h5>Orders</h5>
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
                                <h5>Income</h5>
                            </div>
                            <div class="ibox-content">
                                <h1 class="no-margins">$ 120 430,800</h1>
                                <div class="stat-percent font-bold text-warning">16% <i class="fa fa-level-up"></i></div>
                                <small>New orders</small>
                            </div>
                        </div>
                    </div>




                </div>
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
                             <div class="ibox-title">
                                 <h5>我的任务</h5>

                                 <div ibox-tools></div>
                             </div>
                             <div class="ibox-content">
                                 <div>
                                     <%--<canvas id="projectStatusChart" height="140"></canvas>--%>
                                 </div>
                             </div>
                         </div>
                     </div>

                 </div>

                <div class="row">
                <div class="col-lg-6 col-md-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>New data for the report</h5>
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
                        <div class="ibox-content ibox-heading">
                        <h3>Stock price up
                            <div class="stat-percent text-navy">34% <i class="fa fa-level-up"></i></div>
                        </h3>
                        <small><i class="fa fa-stack-exchange"></i> New economic data from the previous quarter.</small>
                    </div>
                        <div class="ibox-content">
                            <div>

                                <div class="pull-right text-right">

                                    <span class="bar_dashboard">5,3,9,6,5,9,7,3,5,2,4,7,3,2,7,9,6,4,5,7,3,2,1,0,9,5,6,8,3,2,1</span>
                                    <br/>
                                    <small class="font-bold">$ 20 054.43</small>
                                </div>
                                <h4>NYS report new data!
                                    <br/>
                                    <small class="m-r"><a href="graph_flot.html"> Check the stock price! </a> </small>
                                </h4>
                            </div>
                        </div>
                    </div>
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Read below comments and tweets</h5>
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
                        <div class="ibox-content no-padding">
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <p><a class="text-info" href="#">@Alan Marry</a> I belive that. Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                                    <small class="block text-muted"><i class="fa fa-clock-o"></i> 1 minuts ago</small>
                                </li>
                                <li class="list-group-item">
                                    <p><a class="text-info" href="#">@Stock Man</a> Check this stock chart. This price is crazy! </p>
                                    <small class="block text-muted"><i class="fa fa-clock-o"></i> 2 hours ago</small>
                                </li>
                                <li class="list-group-item">
                                    <p><a class="text-info" href="#">@Kevin Smith</a> Lorem ipsum unknown printer took a galley </p>
                                    <small class="block text-muted"><i class="fa fa-clock-o"></i> 2 minuts ago</small>
                                </li>
                                <li class="list-group-item ">
                                    <p><a class="text-info" href="#">@Jonathan Febrick</a> The standard chunk of Lorem Ipsum</p>
                                    <small class="block text-muted"><i class="fa fa-clock-o"></i> 1 hour ago</small>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="col-lg-6 col-md-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Timeline</h5>
                            <span class="label label-primary">Meeting today</span>
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

                            <div class="timeline-item">
                                <div class="row">
                                    <div class="col-xs-3 date">
                                        <i class="fa fa-briefcase"></i>
                                        6:00 am
                                        <br/>
                                        <small class="text-navy">2 hour ago</small>
                                    </div>
                                    <div class="col-xs-7 content no-top-border">
                                        <p class="m-b-xs"><strong>Meeting</strong></p>

                                        <p>Conference on the sales results for the previous year. Monica please examine sales trends in marketing and products.</p>

                                    </div>
                                </div>
                            </div>
                            <div class="timeline-item">
                                <div class="row">
                                    <div class="col-xs-3 date">
                                        <i class="fa fa-file-text"></i>
                                        7:00 am
                                        <br/>
                                        <small class="text-navy">3 hour ago</small>
                                    </div>
                                    <div class="col-xs-7 content">
                                        <p class="m-b-xs"><strong>Send documents to Mike</strong></p>
                                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since.</p>
                                    </div>
                                </div>
                            </div>
                            <div class="timeline-item">
                                <div class="row">
                                    <div class="col-xs-3 date">
                                        <i class="fa fa-coffee"></i>
                                        8:00 am
                                        <br/>
                                    </div>
                                    <div class="col-xs-7 content">
                                        <p class="m-b-xs"><strong>Coffee Break</strong></p>
                                        <p>
                                            Go to shop and find some products.
                                            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's.
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="timeline-item">
                                <div class="row">
                                    <div class="col-xs-3 date">
                                        <i class="fa fa-phone"></i>
                                        11:00 am
                                        <br/>
                                        <small class="text-navy">21 hour ago</small>
                                    </div>
                                    <div class="col-xs-7 content">
                                        <p class="m-b-xs"><strong>Phone with Jeronimo</strong></p>
                                        <p>
                                            Lorem Ipsum has been the industry's standard dummy text ever since.
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <%--</div>--%>
            
            
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
    <script src="${ctxStatic}/js/inspinia.js"></script>
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



            <%--var data={--%>
                <%--"projectId":${bugProject.id}--%>
            <%--}--%>



            <%--$.post("${ctx}/bug/bugProject/projectStatus",data,function (json) {--%>
//                if(json.isSuccess){
//
//                   new Chart(ctx).Doughnut(json.data, projectStatusOpt);
//                }
//
//                console.info(json.msg);

            <%--},"json");--%>

            // 初始化 图表 projectDetail.js
           initProjectData2("${ctx}","${bugProject.id}");







            var lineData = {
                labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [
                    {
                        label: "RENEW",
                        fillColor: "rgba(220,220,220,0.5)",
                        strokeColor: "rgba(220,220,220,1)",
                        pointColor: "rgba(220,220,220,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(220,220,220,1)",
                        data: [65, 59, 80, 81, 56, 55, 40]
                    },
                    {
                        label: "New",
                        fillColor: "rgba(26,179,148,0.5)",
                        strokeColor: "rgba(26,179,148,0.7)",
                        pointColor: "rgba(26,179,148,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(26,179,148,1)",
                        data: [28, 48, 40, 19, 86, 27, 90]
                    }
                ]
            };


            var statusData = {
                labels:[${labels}],
                datasets: [
                    {
                        label: "RENEW",
                        fillColor: "rgba(220,220,220,0.5)",
                        strokeColor: "rgba(220,220,220,1)",
                        pointColor: "rgba(220,220,220,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(220,220,220,1)",
                        data: [65, 59, 80, 81, 56, 55, 40]
                    },
                    {
                        label: "New",
                        fillColor: "rgba(26,179,148,0.5)",
                        strokeColor: "rgba(26,179,148,0.7)",
                        pointColor: "rgba(26,179,148,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(26,179,148,1)",
                        data: [28, 48, 40, 19, 86, 27, 90]
                    }
                ]
            };

            var lineOptions = {
                scaleShowGridLines: true,
                scaleGridLineColor: "rgba(0,0,0,.05)",
                scaleGridLineWidth: 1,
                bezierCurve: true,
                bezierCurveTension: 0.4,
                pointDot: true,
                pointDotRadius: 4,
                pointDotStrokeWidth: 1,
                pointHitDetectionRadius: 20,
                datasetStroke: true,
                datasetStrokeWidth: 2,
                datasetFill: true,
                responsive: true,
            };


            var ctx = document.getElementById("lineChart").getContext("2d");
            var myNewChart = new Chart(ctx).Line(lineData, lineOptions);



        });

    </script>

<script type="application/javascript" src="${ctxStatic}/modules/bug/projectDetail.js"></script>
</body>
</html>