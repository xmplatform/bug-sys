/**
 * Created by always on 16/5/22.
 */

// var config={
//     selectId:"getNextTaskGroup",
//     showLabel:"showLabel",
//     showId:"showId",
//     postData:{
//         projectId:"${bug.bugProject.id}",
//         proInstId:"${procInsId}",
//         status:""
//     }
// }
function getNextTask(config){


    $("#"+config.selectId).change(function () {


        var $show=$('#'+config.showId);
        $show.empty();
        var status = $(this).val();

        if(status==undefined||$.trim(status)==''){
            return;
        }

        var url=config.url||bugCtx+"/bug/bug/task/next";
        config.postData.status=status;
        $.post(url,config.postData,function (json) {

            console.info(json);
            if(json.success) {


                console.info(json.data);


                $.each(json.data, function(i, user) {
                    $("<option value='" + user.loginName + "' >" + user.name +"</option>").appendTo($show);
                });
            }else{
                $("<option value='close' >" +json.msg+"</option>").appendTo($show);
            }
        },"json")
    });
}

function getStartEventNextTask(config){


    $("#"+config.selectId).change(function () {

        var projectId=$("#"+config.projectSelect).val();
        var $show=$('#'+config.showId);
        $show.empty();
        if(projectId==undefined||$.trim(projectId)==''){
            $(this).val("");
			toastr.info('提示', '请选择项目');
            return ;
		}

        config.postData.projectId=projectId;


        var status = $(this).val();

        if(status==undefined||$.trim(status)==''){
            return;
        }

        var url=config.url||bugCtx+"/bug/bug/task/next";
        config.postData.status=status;




        $.post(url,config.postData,function (json) {

            console.info(json);
            if(json.success) {


                console.info(json.data);


                $.each(json.data, function(i, user) {
                    $("<option value='" + user.loginName + "' >" + user.name +"</option>").appendTo($show);
                });
            }else{
                $("<option value='close' >" +json.msg+"</option>").appendTo($show);
            }
        },"json")
    });
}