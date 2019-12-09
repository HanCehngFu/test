$(document).ready(function () {
    // jquery
    $(function () {
    })

})

// vue 
var vm = new Vue({
    el: '#app',
    data: {
    	depositStatus:"",
    	userName:"",
    	userPhone:"",
    	pledgeUuid:"",
    	pledgeTime:"",
    	txTime:"",
    	txStatus:"",
    	txTotal:"",
    	remark:"",
    	pledgeStatus:"",
    	pledgeSum:"",
    	list: [],
    	selStatus:"",
    	pledgeType: [{
    		pledgeStatusName: '已缴纳',
            pledgeStatus: 1
        }, {
        	pledgeStatusName: '未缴纳',
        	pledgeStatus: 2
        }],
    	
    },
    
    mounted: function () {
        let that = this;
        that.default();
    },
    updated: function () {

    },
    methods: {
    	loadData: function() {
        	console.log(1233)
    			var that = this;
    			let url = phosts + '/pledgeInfo/pledgeAjaxList';
    			let data = {
    				//左边java：that.js(属性名：值)
    				status:that.pledgeStatus,
    				userName:that.userName,
    				userPhone:that.userPhone,
    				saleTimeStart:that.startTime,
    				saleTimeEnd:that.endTime,
    				
    				pageNum: that.currPage,
    				pageSize: that.limitPage,
    			};
			$.post(url, data, function(res, textStatus, jqXHR) {
				
				if (res.success) {
					console.log(res.entity.list);
					if (res.entity.list != null) {						
						that.list = res.entity.list;
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
       
		suerSearch() {
            let that = this;
            that.loadData();
        },
        
         default: function () {
            let that = this;
            layui.use(['form', 'laydate'], function () { //此段代码必不可少
            	that.form = layui.form;
            	that.form.on('select(selStatus)', function (data) {
                    that.pledgeStatus = data.value;
                });
            	
            	that.form.render();
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#selDate_input',
                    range: '-',
                    format: 'yyyy/MM/dd',
                    eventElem: '.calebder1',
                    trigger: 'click',
                    change: function(value, date) { //监听日期被切换
                        console.log(value)
                        var startTime = value.split('-')[0];
                        var endTime = value.split('-')[1];
                        that.startTime = startTime;
                        that.endTime = endTime;

                    }
                });
                /*that.form.on('select(selCouponDate)', function (data) {
                    that.selCouponDate = data.value;
                });*/
                that.form.render();
                // 分页
                that.laypage = layui.laypage;
                that.loadData();
            });
        },
        forId: function(index) {
            return "tips" + index
        },
        showTips: function(id, name) {
            layer.tips(name, "#tips" + id, {
                tips: 1,
                time: 1000,
            });
        },

        // 关闭弹窗
       closeModel: function () {
        	console.log(123)
            let that = this;
            $('#myModal').modal('hide');
            that.txTotal = "";
            that.remark = "";
            layui.form.render();
        },
        
        sureFun:function(){

            let that = this;

            //编辑
        	console.log(11111111111);
            let url = phosts + "/pledgeInfo/updatePledgeWithdrawInfo";
            let data = {
                pledgeUuid:that.pledgeUuid,
                remark:that.remark,
                txTotal:that.txTotal
            };
            $.post(url, data, function(res, textStatus, jqXHR) {
                if(res.success){
                	that.closeModel();
                    layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                    layer.msg("编辑成功!", {icon: 1,time: 1000});
                    that.isRenderLaypage=false;
                    that.loadData();
                }else {
                    layer.msg(res.message, {time: 1 * 1000});
                }
            });

        },
        
        
        refundFun: function (id) {
            let that = this;
            console.log(111)
            	window.location.href=phosts+"/pledgeInfo/editDeposit?pledgeUuid="+id+"&v="+new Date().getTime();
            
        },
        
        
     // 点击编辑和取消编辑的方法
        editFun: function (index) {
            let that = this;
            console.log(index);
            console.log(that.list[index].pledgeUuid);
            console.log(that.list[index].userName);
            
            $('#myModal').modal();
            that.pledgeUuid=that.list[index].pledgeUuid;
        	that.txTotal=that.list[index].txTotal;
        	that.remark=that.list[index].remark;
        },
        updatePledgeWithdrawInfo:function(reqdata){
        	let data = {
    			/*npUuid:reqdata.npUuid,
    			npNowhourPrice:reqdata.npNowhourPrice,
    			npNowdayPrice:reqdata.npNowdayPrice,
    			npNownightPrice:reqdata.npNownightPrice,*/
    			pledgeUuid:reqdata.pledgeUuid,
    			txTotal:reqdata.txTotal,
    			remark:reqdata.remark
    			
        	};
        	$.post(phosts + '/pledgeInfo/updatePledgeWithdrawInfo', data, function(res, textStatus, jqXHR) {
        		layer.msg(res.message, {time: 1 * 1000});
        	});
        },
        submitFun: function (index) {
            let that = this;
            var data = that.list;
            that.data = data;
            that.updateprice(that.data[index]);
            console.log(that.data[index]);
        },
        mouseLeave: function () {
            let that = this;
            var data = that.list;
            data.map(function (pledgeInfo) {
            	pledgeInfo.txTotal = false;
            	pledgeInfo.remark = false;
                if (pledgeInfo.txTotal || pledgeInfo.remark ) {
                    that.data = data;
                    return
                }
            });
        },




    }




})