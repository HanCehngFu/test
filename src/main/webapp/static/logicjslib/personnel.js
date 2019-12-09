$(document).ready(function () {

    // jquery
    $(function () {



    })

})
// vue 

new Vue({
    el: '#app',
    data: {
    	
    	storehouseName:"",
    	storehousePhone:"",
    	storehouseStatus:"",
        storehouseUuid:"",
        cleanUuid:"",
    	form:{},
        isRenderLaypage:false,
        laypage:{},
        currPage: 1,
		limitPage: 10,
		count: 0,
		list: [],
		
		form2:{},
        isRenderLaypage2:false,
        laypage2:{},
        currPage2: 1,
		limitPage2: 10,
		count2: 0,
		list2: [],
		lotName:"",
		
		cleanPersonName:"",
		cleanPersonPhone:"",
		nestUuid:"",
		lotUuid:"",
		cleanStatus:"",
		cleanList:[],
 
		roomList:[],
		
        modalType: "", //1新增2编辑
        actionType: "", //1库管2保洁
        checkedRooms: [],
        
        cleanerPhone: "",
        cleanerName: "",
        

        keeperPhone:'',
        keeperName:'',
        htmlType: 0,
    },
    mounted: function () {
        let that = this;
        that.default();
        if (getUrlParam("htmlType") != "") {
            that.htmlType = getUrlParam("htmlType");
        }
    },
    updated: function () {
        this.$nextTick(function () {
            layui.use(['form'], function () {
                layui.form.render();
            });
        });
    },
    methods: {
    	loadData: function() {
    	
			var that = this;
			let url = phosts + '/Storehouse/storeAjaxList';
			let data = {
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
		
		loadData2: function() {
	    	
			let that = this;
			let url = phosts + '/clean/toCleanAjax';
			let data = {
				pageNum: that.currPage2,
				pageSize: that.limitPage2,
			};
			$.post(url, data, function(res, textStatus, jqXHR) {
				if (res.success) {
					if (res.entity != null) {						
						that.list2 = res.entity.list;	
					}
    				that.count2= res.entity.total;
    				that.limitPage2 = res.entity.pageSize;
				}
				if(!that.isRenderLaypage2){
					that.laypage2.render({
                        elem: 'test2',
                        count: that.count2,
                        limit: that.limitPage2,
                        layout: [ 'prev', 'page', 'next', 'limit', 'refresh', 'skip'],
                        jump: function (obj) {
                        	that.currPage2 = obj.curr;
    						that.limitPage2 = obj.limit;
    						if(that.isRenderLaypage2){
    							that.loadData2();
    						}
                        }
                    });
					that.isRenderLaypage2=true;
				}
				layer.msg(res.message, {time: 1 * 1000});
			}, 'JSON');
		},
		
        deleteFun: function (type,id) {
            let that = this;
            that.actionType=type ;//1 库管 2  保洁
            layer.confirm('是否要删除信息!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
                if (that.actionType==1) {  //删除库管
                    let url = phosts + '/Storehouse/remove';
                    let data = {
                        storehouseUuid:id
                    };
                    $.post(url, data, function(res, textStatus, jqXHR) {
                        if(res.success){
                            layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                            layer.msg("删除成功!", {icon: 1,time: 1000});
                            that.isRenderLaypage=false;
                            that.loadData();
                        }else {
                            layer.msg(res.message, {time: 1 * 1000});
                        }
                    });
                } else{
                    //删除保洁
                	let url = phosts + '/clean/deleteCleanInfo';
                    let data = {
                        cleanUuid:id
                    };
                    $.post(url, data, function(res, textStatus, jqXHR) {
                        if(res.success){
                            layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                            layer.msg("删除成功!", {icon: 1,time: 1000});
                            that.isRenderLaypage2=false;
                            that.loadData2();
                        }else {
                            layer.msg(res.message, {time: 1 * 1000});
                        }
                    });

                }

            });
        },

        addFun: function (type, id) {
            let that = this;
            that.modalType = 1;
            that.actionType = type;
            $('#myModal').modal();
        },
        editFun: function (type, id) {
            let that = this;
            console.log(id);
            that.modalType = 2;
            that.actionType = type;
            that.cleanUuid='';
            that.storehouseUuid='';
            $('#myModal').modal();
            if (type == 2) {
            	that.cleanerName=that.list2[id].cleanPersonName;
                that.cleanerPhone=that.list2[id].cleanPersonPhone;
                that.cleanUuid=that.list2[id].cleanUuid;
                that.lotName=that.list2[id].lotName;
            } else {
            	that.keeperName=that.list[id].storehouseName;
            	that.keeperPhone=that.list[id].storehousePhone;
            	that.storehouseUuid =that.list[id].storehouseUuid;
            }
        },
        sureFun: function () {
            let that = this;

            if (that.actionType == 1) { //库管
                if (that.modalType == 1) { // 新增
                    if (that.keeperPhone == "") {
                        layer.msg("请输入库管电话！", {
                            time: 1 * 1000
                        })
                        return
                    }
                    if (that.keeperName == "") {
                        layer.msg("请输入库管姓名！", {
                            time: 1 * 1000
                        })
                        return
                    }
                    //调用新增
                	console.log(222);
                    let url = phosts + "/Storehouse/addOrupdateStorehouse";
                    let data = {
                        storehouseName:that.keeperName,
                        storehousePhone:that.keeperPhone
                    };
                    $.post(url, data, function(res, textStatus, jqXHR) {
                        if(res.success){
                        	that.closeModel();
                            layer.msg("新增成功!", {icon: 1,time: 1000});
                            that.isRenderLaypage=false;
                            that.loadData();
                        }else {
                            layer.msg(res.message, {time: 1 * 1000});
                        }
                    });

                } else {
                    //编辑
                	console.log(111);
                    let url = phosts + "/Storehouse/edit";
                    let data = {
                        storehouseUuid:that.storehouseUuid,
                        storehouseName:that.keeperName,
                        storehousePhone:that.keeperPhone
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

                }

            } else { 
            	//保洁
                if (that.modalType == 1) { // 新增
                    /*if (that.checkedRooms.length == 0) {
                        layer.msg("请选择房间！", {
                            time: 1 * 1000
                        })
                        return
                    }*/
                    if (that.cleanerPhone == "") {
                        layer.msg("请输入保洁电话！", {
                            time: 1 * 1000
                        })
                        return
                    }
                    if (that.cleanerName == "") {
                        layer.msg("请输入保洁姓名！", {
                            time: 1 * 1000
                        })
                        return
                    }
                    if(that.nestUuid==''){
                    	layer.msg("请选择房间！", {
                            time: 1 * 1000
                        })
                        return
                    }
                    let url = phosts + "/clean/addOrUpdate";
                    let data = {
                    	cleanUuid:that.cleanUuid,
                    	nestUuid:that.nestUuid,
                        cleanPersonName:that.cleanerName,
                        cleanPersonPhone:that.cleanerPhone
                    };
                    $.post(url, data, function(res, textStatus, jqXHR) {
                        if(res.success){
                        	that.closeModel();
                            layer.msg("新增成功!", {icon: 1,time: 1000});
                            that.isRenderLaypage2=false;
                            that.loadData2();
                        }else {
                            layer.msg(res.message, {time: 1 * 1000});
                        }
                    });
                    
                    
                } else { 
                	if (that.cleanerPhone == "") {
                        layer.msg("请输入保洁电话！", {
                            time: 1 * 1000
                        })
                        return
                    }
                    if (that.cleanerName == "") {
                        layer.msg("请输入保洁姓名！", {
                            time: 1 * 1000
                        })
                        return
                    }
                	let url = phosts + "/clean/addOrUpdate";
                    let data = {
                    	cleanUuid:that.cleanUuid,
                        cleanPersonName:that.cleanerName,
                        cleanPersonPhone:that.cleanerPhone
                    };
                    $.post(url, data, function(res, textStatus, jqXHR) {
                        if(res.success){
                        	that.closeModel();
                            layer.msg("成功!", {icon: 1,time: 1000});
                            that.isRenderLaypage2=false;
                            that.loadData2();
                        }else {
                            layer.msg(res.message, {time: 1 * 1000});
                        }
                    });

                }
            }
        },
        closeModel: function () {
            let that = this;
            $('#myModal').modal('hide');
            that.checkedRooms = [];
            that.cleanerPhone = "";
            that.cleanerName = "";
            that.keeperName = "";
            that.keeperPhone = "";
            that.modalType = "";
            that.actionType = "";
            that.roomList.map(function (j) {
                j.checked = false
            })
            layui.form.render();

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
                that.loadData2();

            });
        },
    }
})