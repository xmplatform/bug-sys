<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<div class="row">

    <!--问题单展示-->
    <div class="col-lg-8 col-md-8">
        <div class="ibox float-e-margins">


            <div class="ibox-title">
                <h5>问题单</h5>

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

            <div class="ibox-content">
                <div class="row">

                    <table class="table table-bordered">
                        <tr>
                            <td class="info">项目</td>
                            <td colspan="3">${bug.bugProject.name}</td>
                            <td class="info">版本</td>
                            <td colspan="3">${bug.bugVersion.version}:${bug.bugVersion.build}</td>
                        </tr>
                        <tr>
                            <td class="info">名称</td>
                            <td>${bug.name}</td>
                            <td class="info">平台</td>
                            <td>${fns:getDictLabel(bug.bugPlatform,'bug_platform','')}</td>
                            <td class="info">操作系统</td>
                            <td colspan="3">${fns:getDictLabel(bug.bugSystemAndVersion,'bug_systemandversion','')}</td>
                        </tr>

                        <tr>
                            <td class="info">类型</td>
                            <td>${fns:getDictLabel(bug.bugType,'bug_type','')}</td>
                            <td class="info">优先级</td>
                            <td>${fns:getDictLabel(bug.bugLevel,'bug_level','')}</td>
                            <td class="info">严重性</td>
                            <td>${fns:getDictLabel(bug.bugSerious,'bug_serious','')}</td>
                            <td class="info">频率</td>
                            <td>${fns:getDictLabel(bug.bugFrequency,'bug_frequency','')}</td>
                        </tr>

                        <tr width="30">
                            <td class="info">简介</td>
                            <td colspan="7">
                                ${bug.summary}
                            </td>
                        </tr>


                        <tr >
                            <td class="info" >重现步骤</td>
                            <td colspan="7" id="step2Reproduce">
                                ${bug.step2Reproduce}
                            </td>
                        </tr>

                        <tr>
                            <td class="info">实际行为</td>
                            <td colspan="7" id="behavior">
                                ${bug.behavior}
                            </td>
                        </tr>

                        <tr>
                            <td class="info" >期望结果</td>
                            <td colspan="7" id="expected">
                                ${bug.expected}
                            </td>
                        </tr>

                        <tr>
                            <td class="info" >截图</td>
                            <td colspan="7" id="image">
                            </td>
                        </tr>

                        <tr>
                            <td class="info" >附件</td>
                            <td colspan="7">
                                <form:hidden id="file2" path="bug.file" htmlEscape="false" maxlength="255" class="form-control"/>
                                <sys:ckfinder input="file2" type="files" uploadPath="/bug/file" selectMultiple="true" readonly="true"/>
                            </td>
                        </tr>

                        <c:if test="${not empty bug.solution}">
                        <tr>
                            <td class="info" >解决办法</td>
                            <td colspan="7" id="solutioned">
                                ${bug.solution}
                            </td>
                        </tr>
                        </c:if>
                    </table>

                </div>
            </div>
        </div>

    </div>

    <!--活动流程-->
    <div class="col-lg-4 col-md-4">
        <act:histoicFlow procInsId="${bug.act.procInsId}"/>
    </div>


</div>


<script type="text/javascript">

    $("#behavior").html($("#behavior").text());
    $("#expected").html($("#expected").text());
    $("#step2Reproduce").html($("#step2Reproduce").text());
    $("#solutioned").html($("#solutioned").text());



    var image="${bug.image}";


    var splits = image.split("|");

    $.each(splits, function(i, image) {
        if(image.length>5){
            $("<image src='" +image+ "' />").appendTo($("#image"));
        }

    });




</script>