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
        var status = $(this).val();

        if(status==undefined||$.trim(status)==''){
            return;
        }

        var url=bugCtx+"/bug/bug/task/next";
        config.postData.status=status;
        $.post(url,config.postData,function (json) {

            console.info(json);
            if(json.success) {

                var $show=$('#'+config.showId);
                $show.empty();
                console.info(json.data);


                $.each(json.data, function(i, user) {
                    // console.info(user.name);
                    // $("<option value='" + user.id + "' >"
                    //     + user.name +"</option>").appendTo($('#showId'));
                   // console.info(group);
                    // $.each(group,function(i,user){
                    //     console.info(user);
                    //     $("<option value='" + user.loginName + "' >" + user.name +"</option>").appendTo($show);
                    // });

                    $("<option value='" + user.loginName + "' >" + user.name +"</option>").appendTo($show);
                });



            }
        },"json")
    });
}