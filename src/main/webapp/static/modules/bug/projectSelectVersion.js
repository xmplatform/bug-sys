/**
 * Created by always on 16/5/23.
 */
function loadProjectVersion(config){

    $("#"+config.selectId).change(function () {
        var projectId = $(this).val();

        if(projectId==undefined||$.trim(projectId)==''){
            return;
        }
        var postData={
            projectId:projectId
        }

        var url=bugCtx+"/bug/bugProject/loadProjectVersion";
        $.post(url,postData,function (json) {

            console.info(json);
            if(json.success) {

                var $show=$('#'+config.showId);
                $show.empty();
                console.info(json.data);
                var data=json.data;
                $.each(data, function(i, v) {
                    $("<option value='" + v.id + "' >"
                        + v.version + ":"+v.build+"</option><br>").appendTo($show);
                });


            }
        },"json")
    });
}
