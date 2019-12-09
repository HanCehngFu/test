$(document).ready(function () {

    // jquery
    $(function () {})

})
// vue 

new Vue({
    el: '#app',
    data: {
		form:{},
		isRenderLaypage:false,
		laypage:{},
		currPage: 1,
		limitPage: 15,
		count: 0,
		list: []
    },
    mounted: function () {
        let that = this;
        that.default();
    },

    updated: function () {
        let that = this;
        this.$nextTick(function () {
        });
    },
    methods: {
		loadData: function() {
			let that = this;
			let url = phosts + '/nest/exceedCloseHouseList';
			let data = {
					nestUuid:'',
					timeStart:'',
					timeEnd:'',
					remark:'',
					ntStatus:'2',
					pageNum: that.currPage,
					pageSize: that.limitPage,
			};
			$.post(url, data, function(res, textStatus, jqXHR) {
				if (res.success) {
					//console.log(res.entity.list);
					if (res.entity.list != null) {						
						that.list = res.entity.list;
						console.log(that.list);
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
        deleteFun: function (e) {
            let that = this;
            layer.confirm('是否要取消占用!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
                layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                layer.msg("删除成功!", {
                    icon: 1,
                    time: 1000
                });
            });

        },
        default: function () {
            let that = this;
            layui.use(['form', "laydate"], function () { 
                that.laypage = layui.laypage;
				that.loadData();
            });


        },


    }




})