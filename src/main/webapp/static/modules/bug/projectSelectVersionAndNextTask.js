/**
 * Created by always on 16/5/23.
 */
function loadProjectVersionAndNextTask(config){

    $("#"+config.selectId).change(function () {
        var $version=$('#'+config.verionList);
        var $group=$('#'+config.nextTaskGroup);
        $version.empty();
        $group.empty();

        var projectId = $(this).val();

        if(projectId==undefined||$.trim(projectId)==''){
            return;
        }
        var postData={
            projectId:projectId
        }

        var url=bugCtx+"/bug/bugProject/loadProjectVersionAndNextTask";
        $.post(url,postData,function (json) {

            console.info(json);
            if(json.success) {


                console.info(json.data);
                var data=json.data;
                var versionList=data[0];
                var groupList=data[1];
                $.each(versionList, function(i, v) {
                    $("<option value='" + v.id + "' >"
                        + v.version + ":"+v.build+"</option><br>").appendTo($version);
                });

                $.each(groupList, function(i, user) {
                    $("<option value='" + user.loginName+ "' >"
                        + user.name + "</option><br>").appendTo($group);
                });


            }
        },"json")
    });
}
