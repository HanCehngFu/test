$(document).ready(function () {
    // jquery
    $(function () {
    })

})

// vue 
var vm = new Vue({
    el: '#app',
    data: {
    	nestName: "",
    	roomRemark: "",
        selCouponType: "",
        selCouponDate: "",
        fullMony: "",
        subMoney: "",
        // sureBthType: 1, //1,选择优惠券弹窗 2，确定发送
        couponType: [{
            couponTypeName: '金额满减券',
            couponTypeId: 1
        }, {
            couponTypeName: '折扣券',
            couponTypeId: 2
        }, {
            couponTypeName: '时间满减券',
            couponTypeId: 3
        }],
        dateNum: ['15天', '1个月', '2个月', '3个月'],
        
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
    methods: {
    	loadData: function() {
			let that = this;
			let url = phosts + '/nest/ajaxList';
			let data = {
				nestName:that.nestName,
                roomRemark: that.roomRemark,
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
        deleteBtn: function (e) {
            let that = this;
            layer.confirm('是否要删除信息!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
                // $("#tr" + id).remove();
                layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                layer.msg("删除成功!", {
                    icon: 1,
                    time: 1000
                });
            });
        },
        suerSearch() {
            let that = this;
            if (that.nestName == "" && that.roomRemark == "") {
                layer.msg("搜索条件不能为空哦！", {
                    time: 1 * 1000
                })
                return
            }
            that.loadData();
        },
        giveCoupon: function () {
            let that = this;
            $('#myModal').modal();
        },
        sureCouponType: function () {
            let that = this;
            console.log(that.selCouponType, that.selCouponDate)
            if (that.selCouponType == "") {
                layer.msg("请选择优惠券类型!", {
                    time: 1000
                });
                return
            }
            if (that.selCouponDate == "") {
                layer.msg("请选择有效时间!", {
                    time: 1000
                });
                return
            }
            if (that.fullMony == "") {
                layer.msg("请输入满减金额!", {
                    time: 1000
                });
                return
            }
            if (that.subMoney == "") {
                layer.msg("请输入优惠金额!", {
                    time: 1000
                });
                return
            }

        },
        default: function () {
            let that = this;
            layui.use('form', function () { //此段代码必不可少
            	that.form = layui.form;
            	that.form.on('select(selCouponType)', function (data) {
                    that.selCouponType = data.value;
                });
                that.form.on('select(selCouponDate)', function (data) {
                    that.selCouponDate = data.value;
                });
                that.form.render();
                // 分页
                that.laypage = layui.laypage;
                that.loadData();
            });
        },

        // 关闭弹窗
        closeModel: function () {
            console.log(123)
            let that = this;
            $('#myModal').modal('hide');

        },
        editPrice: function (id) {
            let that = this;
            console.log(111)
            	window.location.href=phosts+"/NestPrice/priceManage?nestInfoUUID="+id+"&v="+new Date().getTime();
            
        },
        
        editNestInfo: function (id) {
            let that = this;
            console.log()
            window.location.href=phosts+"/nest/addRoom?nestUuid="+id+"&htmlType=2"+"&v="+new Date().getTime();
        },
        addRoomFun:function(){
            let that = this;
            console.log(111)
            window.location.href=phosts+"/nest/addRoom?&htmlType=0"+"&v="+new Date().getTime();


        }




    }




})