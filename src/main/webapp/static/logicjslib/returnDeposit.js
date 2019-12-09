$(document).ready(function() {
    // jquery
    $(function() {

        })
        // vue 
    new Vue({
        el: '#app',
        data: {
           /* depositStatus: ["已缴纳", "未缴纳"],
            selStatus: "",
            startTime: "",
            endTime: "",
            userName: "",
            userPhone: "",
            list: [],*/
            

        	//depositStatus:"",
        	userName:"",
        	userPhone:"",
        	pledgeUuid:"",
        	txTime:"",
        	//pledgeStatus:"",
        	//pledgeSum:"",
        	txStatus:"",
        	list: [],
        	selStatus:"",
        	txType: [{
        		txStatusName: '申请退押金',
                txStatus: 1
            }, {
            	pledgeStatusName: '客服已审核',
            	txStatus: 2
            }, {
            	pledgeStatusName: '主管已审核',
            	txStatus: 3
            }, {
            	pledgeStatusName: '退款完成',
            	txStatus: 4
            }
            
            
            ],
        	
        




        },
        mounted: function() {
            let that = this;
            that.default();
            console.log("111111111")
        },
        methods: {
        	
        	loadData: function() {
            	console.log(1233)
        			var that = this;
        			let url = phosts + '/PledgeWithdrawInfo/pledgeAjaxList';
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
                if (that.selStatus == "" && that.userName == "" && that.userName == "" && that.startTime == "" && that.endTime == "") {
                    layer.msg("搜索条件不能为空哦！", {
                        time: 1 * 1000
                    })
                    return
                }
            },
            default: function() {
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
            auditFun: function(id) {
                let that = this;
                console.log(11111);
                console.log(id);
                window.location.href=phosts+"/PledgeWithdrawInfo/review?pledgeUuid="+id;
                layer.confirm('确定审核此退款!', {
                    btn: ['确定', '取消']
                }, function(index, layero) {

                    layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                    layer.msg("审核成功!", {
                        icon: 1,
                        time: 1000
                    });

                });

            },
            refundFun: function(id) {
                let that = this;
                layer.confirm('确定退款!', {
                    btn: ['确定', '取消']
                }, function(index, layero) {

                    layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                    layer.msg("退款成功!", {
                        icon: 1,
                        time: 1000
                    });

                });
            }





        }




    })
})