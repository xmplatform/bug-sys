/**
 * Created by always on 16/5/7.
 */

var chartUtils={

    /*Makes the AJAX calll (synchronous) to load a Student Data*/
    loadData : function(url){
        
        var formattedListArray =[];
        
        

        $.ajax({

            async: false,

            url: url,

            dataType:"json",

            success: function(json) {

                console.log(studentJsonData);

                $.each(json,function(index,obj){

                    formattedListArray.push([obj.mathematicsMark,obj.computerMark,obj.historyMark,obj.litratureMark,obj.geographyMark]);
                    
                });
            }
        });
        return formattedListArray;
    },
    /*Crate the custom Object with the data*/
    createChartData : function(jsonData){

        console.log(jsonData);


        return {

            labels : ["", "Computers", "History","Literature", "Geography"],

            datasets : [
                {
                    fillColor : "rgba(255,0,0,0.3)",

                    strokeColor : "rgba(0,255,0,1)",

                    pointColor : "rgba(0,0,255,1)",

                    pointStrokeColor : "rgba(0,0,255,1)",

                    /*As Ajax response data is a multidimensional array, we have 'student' data in 0th position*/
                    data : jsonData[0]     }
            ]   };
    },
    /*Renders the Chart on a canvas and returns the reference to chart*/
    renderRadarChart:function(radarChartData){

        var context2D = document.getElementById("canvas").getContext("2d"),

            myRadar = new Chart(context2D).Radar(radarChartData,{

                scaleShowLabels : false,

                pointLabelFontSize : 10
            });


        return myRadar;
    },
    
    /*Initalization Student render chart*/
    initRadarChart : function(chartId){

        var data = chartUtils.loadData(url);

        chartData = chartUtils.createChartData(data);

        radarChartObj = chartUtils.renderRadarChart(chartData);

    }
};

