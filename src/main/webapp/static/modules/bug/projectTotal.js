/**
 * Created by always on 16/5/7.
 */


var randomScalingFactor = function() {
    return Math.round(Math.random() * 100);
    //return 0;
};
var randomColorFactor = function() {
    return Math.round(Math.random() * 255);
};
var randomColor = function(opacity) {
    return 'rgba(' + randomColorFactor() + ',' + randomColorFactor() + ',' + randomColorFactor() + ',' + (opacity || '.3') + ')';
};

function initProjectTotalByDay(id,day) {



    var config = {
        type: 'line',
        data: {
            labels: [],
            datasets:[]
        },
        options: {
            responsive: true,
            title:{
                display:true,
                text:'Chart.js Line Chart'
            },
            tooltips: {
                mode: 'label',
            },
            hover: {
                mode: 'dataset'
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        show: true,
                        labelString: 'Month'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        show: true,
                        labelString: 'Value'
                    },
                    ticks: {
                        suggestedMin: 0,
                        suggestedMax: 10,
                    }
                }]
            }
        }
    };


    $.ajax({
        async: false,
        url: bugCtx+"/bug/bugProject/totalProjectByDay?projectId="+id+"&day="+day,
        dataType:"json",
        success: function(json) {
            if(json.success) {
                var map=json.data;
                config.data.labels=map.dateStr;
                $.each(map.bugList,function(index,bug){

                    var newDataset = {
                        label: bug.label,
                        borderColor: bug.color,
                        backgroundColor:'#FFFFFF',
                        pointBorderWidth: 1,
                        data: bug.values,
                    };
                    config.data.datasets.push(newDataset);

                });
            }
        }
    });
    var ctx = document.getElementById("lineChart").getContext("2d");
    new Chart(ctx, config);


    // var data = {  // data.labels  data.datasets.data
    //     labels: labels,
    //     datasets: [
    //         {
    //             data: values,
    //             backgroundColor: backgroundColor,
    //             hoverBackgroundColor: hoverBackgroundColor
    //         }]
    // };


    // console.info(data);
    // var canvas=document.getElementById("lineChart");
    // var ctx = canvas.getContext("2d");
    // var chart=new Chart(ctx, {
    //     type: 'line',
    //     data: data,
    //     options: {
    //         responsive: true,
    //         legend: {
    //             position: 'top',
    //         },
    //         title: {
    //             display: true,
    //             text: '应用状态'
    //         },
    //         animation: {
    //             animateScale: true,
    //             animateRotate: true
    //         }
    //     }
    // });


}