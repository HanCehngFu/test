$(document).ready(function () {
    // jquery
    $(function () {
    })

})

// vue 
var vm = new Vue({
    el: '#app',
    data: {
        roomNum: "",
        roomName: "",
        list: [],

    },
    mounted: function () {
        let that = this;
        that.default();
    },

    methods: {
    	suerSearch:function () {
			let that = this;
			that.isRenderLaypage=false;
			that.loadData();
		},
		
		openPage:function (id) {
			let that = this;
	        console.log(id)
	        window.location.href=phosts+"/Storehouse/nestConsumeManage?nestUuid="+id;
		},
    	
    	loadData: function() {
    		var that = this;
			let url = phosts + '/nest/ajaxList';
			let data = {
				nestCode:that.roomNum,
				nestName:that.roomName,
				pageNum: that.currPage,
				pageSize: that.limitPage,
			};
			$.post(url, data, function(res, textStatus, jqXHR) {
				if (res.success) {
					if (res.entity != null) {						
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
		
		
        
        showTips: function (id, name) {
            layer.tips(name, "#tips" + id, {
                tips: 1
            });
        },
        forId: function (index) {
            return "tips" + index
        },
        default: function () {
            let that = this;
            layui.use('form', function () { //此段代码必不可少
            	that.form = layui.form;
                that.form.render();
                that.form2 = layui.form;
                that.form2.on('select(nestLotfilter)', function (data) {
                    //that.lotUuid = data.value;
                	that.nestUuid = data.value;
                    console.log(that.nestUuid);
                });
                that.form2.render();
                // 分页
                that.laypage = layui.laypage;
                that.laypage2 = layui.laypage;
                that.loadData();

            });
        },
    
    }




})