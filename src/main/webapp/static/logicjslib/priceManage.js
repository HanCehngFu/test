$(document).ready(function () {
    // jquery
    $(function () {



    })

})
/*var data = [{
    hourPrice: '10',
    neightPrice: '20',
    dayPrice: '40',
}, {
    hourPrice: '10',
    neightPrice: '20',
    dayPrice: '90',
}, {
    hourPrice: '35',
    neightPrice: '40',
    dayPrice: '70',
}];
data.map(function (item) {
    item.hourShow = false;
    item.neighShow = false;
    item.dayShow = false;
    item.hourVal = item.hourPrice
    item.neighVal = item.neightPrice
    item.dayVal = item.dayPrice
});*/
// vue 
var vm = new Vue({
    el: '#app',
    data: {
 
        weekPrice: "",
        dayPrice: "",
        holidayPrices: "",
        
        npNowOrdinaryhourPrice:'',
        npNowOrdinarydayPrice:'',
        npNowOrdinarynightPrice:'',
        npNowResthourPrice:'',
        npNowRestdayPrice:'',
        npNowRestnightPrice:'',
        npNowHolidayhourPrice:'',
        npNowHolidaydayPrice:'',
        npNowHolidaynightPrice:'',
        
        
        
        roomId: "",
        roomName: "",
        startTime:'',
        endTime: '',
        
        form:{},
        isRenderLaypage:false,
        laypage:{},
        currPage: 1,
		limitPage: 10,
		count: 0,
		list: []

    },
    mounted: function () {
        let that = this;
        that.default();
    },
    updated: function () {

    },
    watch: {
    	endTime(curVal, oldVal){
    		let that = this;
    		if(curVal !=oldVal){
    			that.loadData();
    		}
    	}
    },
    methods: {
    	loadData: function() {
			let that = this;
			console.log("房间ID：" + that.roomId);
			let url = phosts + '/NestPrice/priceAjaxList';
			let stDate ='';
			let enDate ='';
			
			if(that.startTime != ''){
				stDate = that.startTime.replace(new RegExp('/','g'),"-").replace(' ','')+' 00:00:00';
			}
			if(that.endTime != ''){
				enDate = that.endTime.replace(new RegExp('/','g'),"-")+' 00:00:00';
			}
			let data = {
				startTime :stDate,
				endTime : enDate,
				nestUuid : that.roomId,
				pageNum: that.currPage,
				pageSize: that.limitPage,
			};
			$.post(url, data, function(res, textStatus, jqXHR) {
				
				if (res.success) {
					console.log(res.entity.list);
					if (res.entity.list != null) {
						let data = res.entity.list;
						data.map(function (item) {
						    item.hourShow = false;
						    item.neighShow = false;
						    item.dayShow = false;
						});
						that.list = data;
					}
    				that.count = res.entity.total;
    				that.limitPage = res.entity.pageSize;
				}
				if(!that.isRenderLaypage){
					that.laypage.render({
                        elem: 'test1',
                        count: that.count,
                        limit: that.limitPage,
                        layout: [ 'prev', 'page', 'next', 'limit', 'refresh', 'skip'],
                        jump: function (obj) {
                        	that.currPage = obj.curr;
    						that.limitPage = obj.limit;
    						if(that.isRenderLaypage){
    							that.loadData();
    						}
                        }
                    });
					that.isRenderLaypage=true;
				}
				layer.msg(res.message, {time: 1 * 1000});
			}, 'JSON');
		},
		// 点击编辑和取消编辑的方法
        editFun: function (index, type) {
            let that = this;
            var data = that.list;
            data.map(function (item) {
                item.hourShow = false;
                item.neighShow = false;
                item.dayShow = false;
                if (item.hourShow || item.neighShow || item.dayShow) {
                    that.data = data;
                    return
                }
            })
            switch (type) {
                case 1:
                    data[index].hourShow = true
                    break;
                case 2:
                    data[index].neighShow = true
                    break;
                case 3:
                    data[index].dayShow = true
                    break;
            }
            that.data = data;

        },
        updateprice:function(reqdata){
        	let data = {
    			npUuid:reqdata.npUuid,
    			npNowhourPrice:reqdata.npNowhourPrice,
    			npNowdayPrice:reqdata.npNowdayPrice,
    			npNownightPrice:reqdata.npNownightPrice
        	};
        	$.post(phosts + '/NestPrice/updateNestPrice', data, function(res, textStatus, jqXHR) {
        		layer.msg(res.message, {time: 1 * 1000});
        	});
        },
        submitFun: function (index, type) {
            let that = this;
            var data = that.list;
            switch (type) {
                case 1:
                    //data[index].npNowhourPrice = data[index].npNowhourPrice;
                	
                    data[index].hourShow = false
                    break;
                case 2:
                    //data[index].npNightPrice = data[index].npNightPrice;
                	
                    data[index].neighShow = false
                    break;
                case 3:
                    //data[index].npNowdayPrice = data[index].npNowdayPrice;
                	
                    data[index].dayShow = false
                    break;
            }
            that.data = data;
            that.updateprice(that.data[index]);
            console.log(that.data[index]);
        },
        mouseLeave: function () {
            let that = this;
            var data = that.list;
            data.map(function (item) {
                item.hourShow = false;
                item.neighShow = false;
                item.dayShow = false;
                if (item.hourShow || item.neighShow || item.dayShow) {
                    that.data = data;
                    return
                }
            });
        },
        // 批量改价
        editAllPrice() {
            let that = this;
            that.npNowOrdinaryhourPrice='';
            that.npNowOrdinarydayPrice='';
            that.npNowOrdinarynightPrice='';
            that.npNowResthourPrice='';
            that.npNowRestdayPrice='';
            that.npNowRestnightPrice='';
            that.npNowHolidayhourPrice='';
            that.npNowHolidaydayPrice='';
            that.npNowHolidaynightPrice='';
            $('#myModal').modal();
        },
        editAllPriceAction:function(){
        	let that = this;
        	let url = phosts + '/NestPrice/editAllPrice';
        	let data = {
        		nestUuid:that.roomId,
        		
        		//npNowOrdinaryhourPrice:that.npNowOrdinaryhourPrice,
        		npNowOrdinarydayPrice:that.npNowOrdinarydayPrice,
        		//npNowOrdinarynightPrice:that.npNowOrdinarynightPrice,
        		
        		//npNowResthourPrice:that.npNowResthourPrice,
        		npNowRestdayPrice:that.npNowRestdayPrice,
        		//npNowRestnightPrice:that.npNowRestnightPrice,
        		
        		//npNowHolidayhourPrice:that.npNowHolidayhourPrice,
        		npNowHolidaydayPrice:that.npNowHolidaydayPrice,
        		//npNowHolidaynightPrice:that.npNowHolidaynightPrice,
        	};
        	$.post(url, data, function(res, textStatus, jqXHR) {
        		layer.msg(res.message, {time: 1 * 1000});
        		if(res.success){
        			that.loadData();
        			that.closeModel();
        		}
        	});
        },
        sureEditFun: function () {
        	let that = this;
        	let reg = /^\d+(\.\d{1,1})?$/;
        	/*if (!reg.test(that.npNowOrdinaryhourPrice)) {
                layer.msg("请输入平日时租价格!", { time: 1000});
                return;
        	}*/
	        if (!reg.test(that.npNowOrdinarydayPrice)) {
	                layer.msg("请输入平日日租价格!", { time: 1000});
	                return;
	        }
	        /*if (!reg.test(that.npNowOrdinarynightPrice)) {
	                layer.msg("请输入平日夜租价格!", { time: 1000});
	                return;
	        }
	        
	        if (!reg.test(that.npNowResthourPrice)) {
	                layer.msg("请输入周末时租价格!", { time: 1000});
	                return;
	        }*/
	        if (!reg.test(that.npNowRestdayPrice)) {
	                layer.msg("请输入周末日租价格!", { time: 1000});
	                return;
	        }
	        /*if (!reg.test(that.npNowRestnightPrice)) {
	                layer.msg("请输入周末夜租价格!", { time: 1000});
	                return;
	        }
	        
	        if (!reg.test(that.npNowHolidayhourPrice)) {
	                layer.msg("请输入节假日时租价格!", { time: 1000});
	                return;
	        }*/
	        if (!reg.test(that.npNowHolidaydayPrice)) {
	                layer.msg("请输入节假日 日租价格!", { time: 1000});
	                return;
	        }
	        /*if (!reg.test(that.npNowHolidaynightPrice)) {
	                layer.msg("请输入节假日夜租价格!", { time: 1000});
	                return;
	        }*/
            that.editAllPriceAction();
        },

        default: function () {
            let that = this;
            layui.use(['form', 'laydate'], function () { //此段代码必不可少
                that.form = layui.form;
                that.form.render();
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#selDate_input',
                    range: '-',
                    format: 'yyyy/MM/dd',
                    eventElem: '.calebder1',
                    trigger: 'click',
                    change: function (value, date) { //监听日期被切换
                        console.log(value)
                        var startTime = value.split('-')[0];
                        var endTime = value.split('-')[1];
                        that.startTime = startTime;
                        that.endTime = endTime;

                    }
                });

                // 分页
                that.laypage = layui.laypage;
            });
            that.roomId = $('#roomId').val();
            that.roomName=$('#roomName').val();
            that.loadData();

        },
        //获取URL中参数
        getUrlParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg); //匹配目标参数
            if (r != null) return unescape(r[2]); //返回参数值
            return null;
        },
        // 关闭弹窗
        closeModel: function () {
            console.log(123)
            let that = this;
            $('#myModal').modal('hide');
            that.weekPrice = "";
            that.dayPrice = "";
            that.holidayPrices = "";

        },
        showHoliday:function(){
        	let that = this;
        	var img = "<img src='/static/img/holidayImg.png' style='height:auto;width:100%'>"
        	layer.open({
        		  type: 1,
        		  area:["500px","300px"],
        		  shade:0,
        		  maxmin:false,
        		  anim:1,
        		  content: img,
        		});
        }






    }




})