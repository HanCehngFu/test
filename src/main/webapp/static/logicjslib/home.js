$(document).ready(function () {
    allFun(echarts)

    function allFun(ec) {
        order_charts(ec);
        ageRatio_charts(ec);
        genderRatio_charts(ec);
        drag_charts(ec);
    }




    function order_charts(ec) {
        // 下单时间段柱状图
        var order_charts = ec.init(document.getElementById('order_charts'));
        var option2 = {
            color: ['#7C50F2'],
            title: {
                text: '订单下单时间段'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter(params) {
                    const item = params[0];
                    console.log(item)
                    return `${item.axisValue+"下单: "+item.data} `;
                },
            },
            
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            
            xAxis: [{
                type: 'category',
                data: ['08:00  -  11:00', '11:00  -  14:00', '14:00  -  17:00',
                    '17:00  -  20:00',
                    '20:00  -  23:00', '23:00  -  次日8点'
                ],
                axisTick: {
                    alignWithLabel: true
                },
                axisTick: { //坐标轴刻度相关设置
                    width: 5 //是否显示坐标轴刻度。
                },
                axisLine: {
                    lineStyle: {
                        color: "#BEBEBE "
                    }
                },
                axisLabel: { //坐标轴刻度标签的相关设置 
                    textStyle: {
                        color: '#000',
                    }
                },
                axisLabel: {
                    interval: 0,
                    formatter: function (value) {
                        var ret = " ";
                        var maxLength = 5;
                        var valLength = value.length;
                        var rowN = Math.ceil(valLength / maxLength);
                        if (rowN > 1) {
                            for (var i = 0; i < rowN; i++) {
                                var temp = " ";
                                var start = i * maxLength;
                                var end = start + maxLength;
                                temp = value.substring(start, end) + "\n ";
                                ret += temp;
                            }
                            return ret;
                        } else {
                            return value;
                        }
                    }
                }

            }],
            yAxis: [{
                // show: false,
                type: 'value',
                splitLine: { //坐标轴在 grid 区域中的分隔线。
                    lineStyle: {
                        color: "#f5f5f5"
                    }
                },
                // 改变样式
                axisLine: {
                    lineStyle: {
                        color: '#fff',
                    }
                },
                axisTick: { //坐标轴刻度相关设置
                    show: false, //是否显示坐标轴刻度。
                },
                axisLabel: { //坐标轴刻度标签的相关设置 
                    textStyle: {
                        color: '#000',
                    }
                },
            }],
            series: [{
                name: '直接访问',
                type: 'bar',
                barWidth: '60%',
                data : [],
				//data : [10, 52, 200, 334, 390, 330]
            }]
        };
		var url='/statistics/quantumTime';
		var postData={
			
		};
		$.post(url,postData,function (res) {
			if (res.code == '200') {
				option2.series[0].data = res.entity;
				order_charts.setOption(option2, true);
			} 
		},'JSON'); 
        //order_charts.setOption(option2, true);
    }

    function ageRatio_charts(ec) {
        // 年龄占比段柱状图
        var ageRatio_charts = ec.init(document.getElementById('ageRatio_charts'));
        var option3 = {
            color: ['#1890FF'],
            title: {
                text: '年龄占比'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter(params) {
                    const item = params[0];
                    return `
                ${item.axisValue+"下单: "+item.data}
               `;
                },
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [{
                type: 'category',
                data: [],
                axisTick: {
                    alignWithLabel: true
                },
                axisTick: { //坐标轴刻度相关设置
                    width: 5 //是否显示坐标轴刻度。
                },
                axisLine: {
                    lineStyle: {
                        color: "#BEBEBE "
                    }
                },
                axisLabel: { //坐标轴刻度标签的相关设置 
                    textStyle: {
                        color: '#000',

                    },
                    interval: 0,
                    rotate: 40
                },
            }],
            yAxis: [{
                // show: false,
                type: 'value',
                splitLine: { //坐标轴在 grid 区域中的分隔线。
                    lineStyle: {
                        color: "#f5f5f5 "
                    }
                },
                // 改变样式
                axisLine: {
                    lineStyle: {
                        color: '#fff',
                    }
                },
                axisTick: { //坐标轴刻度相关设置
                    show: false, //是否显示坐标轴刻度。
                },
                axisLabel: { //坐标轴刻度标签的相关设置 
                    show: false
                },
            }],
            series: [{
                name: '直接访问',
                type: 'bar',
                barWidth: '60%',
				data: []
               // data: [52, 139, 120, 100, 50]
            }]
        };
		var url='/statistics/Age';
		var postData={
			
		};
		$.post(url,postData,function (res) {
			if (res.code == '200') {
				var data=[];
				var data2=[];
				for (var i = 0; i < 3; i++) {
					var tmp = res.entity[i]
					data[i]= tmp.value;
					data2[i]=tmp.name+'岁';
				}
				option3.xAxis[0].data=data2;
				option3.series[0].data = data;
				ageRatio_charts.setOption(option3, true);
			} 
		},'JSON'); 
    }

    function genderRatio_charts(ec) {
        // 男女占比饼状图
        var genderRatio_charts = ec.init(document.getElementById('genderRatio_charts'));
        var option4 = {
            color: ['#FF4369', '#7C50F2', '#F5F5F5'],
            title: {
                text: '男女占比',
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b} : {c} ({d}%) "
            },
            legend: {
                orient: 'vertical',
                // top: 'middle',
                bottom: 10,
                left: '10',
                data: ['女', '男', '未知']
            },
            series: [{
                type: 'pie',
                radius: '70%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data: [],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };
		var url='/statistics/sex';
		var postData={
			
		};
		$.post(url,postData,function (res) {
			if (res.code == '200') {
				option4.series[0].data = res.entity
				genderRatio_charts.setOption(option4, true);
			} 
		},'JSON'); 

    }

    function drag_charts(ec) {
        var darg_charts = ec.init(document.getElementById('darg_charts'));
        var base = new Date();
        var oneDay = 24 * 3600 * 1000;
        var date = [];
        var data = [];
        var option5 = {
            color: ['#4FC63D'/* , '#1890FF' */],
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                top: '0%',
                right: '4%',
                data: ['支付订单数'/* ,'访问人数' */]
            },
            toolbox: {
                feature: {
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: date,
                axisTick: { //坐标轴刻度相关设置
                    width: 5 //是否显示坐标轴刻度。
                },
                axisLine: {
                    lineStyle: {
                        color: "#BEBEBE "
                    }
                },
               /* axisLabel: { //坐标轴刻度标签的相关设置 
                    textStyle: {
                        color: '#000',
                    }
                }, */
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%'],
                splitLine: { //坐标轴在 grid 区域中的分隔线。
                    lineStyle: {
                        color: "#f5f5f5 "
                    }
                },
                // 改变样式
                axisLine: {
                    lineStyle: {
                        color: '#fff',
                    }
                },
                axisTick: { //坐标轴刻度相关设置
                    show: false, //是否显示坐标轴刻度。
                },
                axisLabel: { //坐标轴刻度标签的相关设置 
                    textStyle: {
                        color: '#000',
                    }
                },
            },
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 10
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],

            series: [{
                    name: '支付订单数',
                    type: 'line',
                    smooth: true,
                    symbol: 'none',
                    sampling: 'average',
                    itemStyle: {
                        // color: 'rgb(255, 70, 131)',
                        normal: {
                            lineStyle: {
                                color: "#4FC63D "
                            }
                        }
                    },
                    data: data
                }/* ,
                {
                    name: '访问人数',
                    type: 'line',
                    stack: '总量',
                    itemStyle: {
                        normal: {
                            lineStyle: {
                                color: "#1890FF "
                            }
                        }
                    },
                    data: data
                } */
            ]
        };
		var url='/statistics/orderPayCount';
		var postData={
			
		};
		$.post(url,postData,function (res) {
			if (res.code==200) {
				option5.xAxis.data = res.entity.days;
				option5.series[0].data = res.entity.payCounts;
				darg_charts.setOption(option5, true);
			} 
		},'JSON'); 
    }
});
new Vue({
    el: '#app',
    data: {
        massageData: 1,


    },
    mounted: function () {
        let that = this;
        



    },
    updated: function () {

    },
    methods: {
    	jumpUrl:function(e) {
    	    if (e == 3) {
    	        window.location.href = "/SaleInfo/n_orderManage"
    	    } else if (e == 5) {
    	        window.location.href = "/nestlotpwd/passwordManage?currentTab=1"
    	    }
    	   window.parent.vm.changeTab(e);
    	}



    }




})