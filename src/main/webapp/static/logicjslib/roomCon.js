$(document).ready(function () {
    // jquery
    $(function () {



    })

})
// vue 

var vm = new Vue({
    el: '#app',
    data: {
        roomId: "",
        roomNum: "",
        curNum: "",
        maxNum: "",
        minNum: "",
        editID: "",
        editName: "",
        modalType: 1, //1新增2编辑
        checkedArr:[],
        consumeName:"",
        consumeNum:"",
        status:"",
        everytimeReduce:"",
        nestUuid:"",
        
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
        var url = window.location.href;
        that.roomId = getUrlParam('nestUuid');
        
        console.log("商品ID：" + that.roomId);
        console.log("列表：" + that.list);
        console.log("consumeNum：" + that.consumeNum);
        that.default();

    },
    updated: function () {
        layui.use(['form'], function () { 
            layui.form.render("checkbox");
        })

    },
    methods: {
    	
    	loadData: function() {
    		let that = this;
    		console.log("-------------走到这里来了吗--------------------");
    		
			let url = phosts + '/Storehouse/nestConsumeInfoAjaxList';
			let data = {
				/*consumeName:that.consumeName,
				consumeNum:that.consumeNum,
				status:that.status,
				everytimeReduce:that.everytimeReduce,*/
				
				nestUuid: that.roomId,
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
		
        //编辑易耗品
        editFun(id, name) {
            let that = this;
            $('#myModal').modal();
            that.editID = id;
            that.editName = name;
            that.modalType=2;
        },
        addFun:function(){
            let that = this;
            $('#myModal').modal();
            that.modalType=1;
            console.log(111)
        },
        sureEditFun: function () {
            let that = this;
            if(that.modalType == 1){
                if(that.checkedArr.length==0){
                    layer.msg("请选择易耗品名称!", {
                        time: 1000
                    });
                    return
                }
            }
            if (that.curNum == "") {
                layer.msg("请输入当前数量!", {
                    time: 1000
                });
                return
            }
            if (that.maxNum == "") {
                layer.msg("请输入最大数量!", {
                    time: 1000
                });
                return
            }
            if (that.minNum == "") {
                layer.msg("请输入最小数量!", {
                    time: 1000
                });
                return
            }
            
        },

        default: function () {
            let that = this;
            layui.use(['form'], function () { //此段代码必不可少
                that.form = layui.form;
                var checkedArr = [];
                that.form.on('checkbox(checkboxs)', function (data) {
                    if (this.checked) {
                        that.checkList.map(function (item) {
                            if (item.id == data.value) {
                                checkedArr.push(item)
                            }
                        })
                    } else {
                        that.checkList.map(function (item, index) {
                            if (item.id == data.value) {
                                checkedArr.splice(index, 1)
                            }
                        })
                    }
                    that.checkedArr = checkedArr;
                });
                that.form.render();
                // 分页
                that.laypage = layui.laypage;
                 
                that.loadData();
                 
                 
                 
                 
            });
        },
  
        // 关闭弹窗
        closeModel: function () {
            let that = this;
            $('#myModal').modal('hide');
            that.curNum = "";
            that.maxNum = "";
            that.minNum = "";
            $(".checkboxs").each(function () {
                $(this).prop('checked', false);
            });
            that.checkedArr=[];
        },





    }




})