/**
 * Created by always on 16/5/7.
 */

function initProjectData2(baseUrl,id) {

    var values=[];
    var labels=[];
    var backgroundColor=[];
    var hoverBackgroundColor=[];





    $.ajax({

        async: false,

        url: baseUrl+"/bug/bugProject/projectStatus?projectId="+id,

        dataType:"json",

        success: function(json) {

            if(json.success) {


                $.each(json.data,function(index,obj){

                    labels.push(obj.label);
                    values.push(obj.value);
                    backgroundColor.push(obj.color);
                    hoverBackgroundColor.push(obj.color);


                });

            }


        }
    });

    var data = {  // data.labels  data.datasets.data
        labels: labels,
        datasets: [
            {
                data: values,
                backgroundColor: backgroundColor,
                hoverBackgroundColor: hoverBackgroundColor
            }]
    };


    console.info(data);
    var canvas=document.getElementById("projectStatusChart");
    var ctx = canvas.getContext("2d");
    var chart=new Chart(ctx, {
        type: 'doughnut',
        data: data,
        options: {
            responsive: true,
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: '应用状态'
            },
            animation: {
                animateScale: true,
                animateRotate: true
            }
        }
    });

     $("#projectStatusChart").click(
         function (evt) {
             console.info(evt);
             var activePoints = chart.getElementAtEvent(evt);
             // var act=chart.getDatasetAtEvent(evt);
             // //var url = "http://example.com/?label=" + activePoints[0].label + "&value=" + activePoints[0].value;
             // //alert(url);
             // console.info();
             // console.info(act);
             // console.info(activePoints['_model']);
             // console.info(activePoints._model.label);
             var statusPhrase = activePoints[0]._model.label;

             //projectStatus?projectId=${bugProject.id}
             var url=baseUrl+"/bug/bug/statusList?projectId="+id+"&statusPhrase="+statusPhrase;

             //self.location.href=url;

             top.openTab(url,"应用状态「"+statusPhrase+"」", false)

         }
     )

}