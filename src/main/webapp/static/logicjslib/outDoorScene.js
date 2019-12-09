$(document).ready(function () {
    // jquery
    $(function () {

    })

})

//vue
var vm = new Vue({
    el: '#app',
    data: {
        modalType: 1, //1添加2编辑
        roomName: "",
        url_list:[],
        url_list2:[],
        lotUuid:'',
        lotUuid2:'',
        imgFileurl:'',

        upload:{},
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
			let url = phosts + '/nestLot/ajaxNestLotImgs';
			let data = {
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
			});
		},
        deleteBtn: function (i) {
            let that = this;
            layer.confirm('是否要删除信息!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
            	console.log(i);
               	let lontInfo = that.list[i];
               	let url = phosts + '/nestLot/deleteNestLotImgs';
    			let data = {
    				lotUuid:lontInfo.lotUuid,
    				doAction:'all'
    			};
    			$.post(url, data, function(res, textStatus, jqXHR) {
    				if(res.success){
    					that.list[i].imgFiles=[];
    	                layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
    	                layer.msg("删除成功!", {icon: 1,time: 1000});
    				}else {						
    					layer.msg(res.message, {time: 1 * 1000});
					}
    			}); 	
            });
        },
        default: function () {
            let that = this;
            layui.use(['jquery', 'form', "upload"], function () { 
            	//此段代码必不可少
            	that.form = layui.form;
            	that.upload = layui.upload;
                var $ = layui.$;
                var url_list = that.url_list;
                
                that.form.on('select(selRoom)', function (data) {
                	console.log(data.value);
                    that.lotUuid2 = data.value;
                });
                
                that.form.render();
                // 分页
                that.laypage = layui.laypage;
               
                //多图片上传
                that.upload.render({
                    elem: '#upLoad1',
                    url: '/nestLot/upload',
                    multiple: true,
                    before: function (obj) {
                    	var flag = true
                        obj.preview(function (index, file, result) {
                            //that.url_list.push(result);
                            if(flag){
                            	setTimeout(function(){
                            		that.initPic1();
                            	},200)
                            	flag=false;
                            }
                        });
                    },
                    //上传完毕
                    done: function (res) {
                    	if(res.success){
                    		that.url_list2.push(res.entity[0]);
                    		console.log(that.url_list2);
                    		let url = phosts + '/nestLot/addlotpicfile';
                    		let lotUuid3='';
                    		if(that.modalType==1){
                    			lotUuid3=that.lotUuid2;
                    		}else{
                    			lotUuid3=that.lotUuid;
                    		}
                			let data = {
                				usedid:lotUuid3,
                				useType:'4',
                				filemark:'-',
                				imgurls:res.entity,
                			};
                			
                			$.ajax({
                                contentType: 'application/json',
                                type:'POST',
                                url:url,
                                dataType:'JSON',
                                async:true,
                                data:JSON.stringify(data),
                                success: function (res) {
                                	if(res.success){
                                		
                                		layer.msg('添加成功!', { icon: 1,time: 1000});
                                	} else {
                                		layer.msg(res.message, { icon: 1,time: 1000});
                					}
                                },
                                error:function(){
                                	
                                }
                            });       		
                    	} else {
                    		layer.msg(res.message, {time: 1 * 1000});
						}
                    }
                });
                $(".item_container").on("click", ".close", function () {
                    var ind = $(this).data("index");
                    var thisEelment = $(this);
                    let url = phosts + '/nestLot/deleteNestLotImgs';
        			let data = {
        				lotUuid:that.lotUuid,
        				doAction:that.url_list2[ind],
        			};
        			$.post(url, data, function(res, textStatus, jqXHR) {
        				if(res.success){
        					that.url_list.splice(ind, 1);
        					that.url_list2.splice(ind, 1);
        					thisEelment.parent().remove();
        					that.initPic1();
        					that.loadData();
        				} else {
        					console.log("删除失败")
        					layer.msg(res.message, {time: 1 * 1000});
    					}
        			}); 
                    
                    
                });
            });
            that.loadData();
        },
        initPic1: function () {
            let that = this;
            var html = "";
            var look_html = "";
            var url_list = that.url_list2;
            for (var i = 0; i < url_list.length; i++) {
                html += '<div class="item">';
                html += '<img class = "drag_img" img_index="' + i + '" src="' + url_list[i] + '" width="" height="">';
                html += '<span  class="rmPicture close iconfont icon-guanbi2" data-index="' + i +'"></span>';
                html += '</div>';
                look_html += '<li><img  img_index="' + i + '"  src="' + url_list[i] + '"></li>';
            }
            $(".item_container").html(html);
            $("#dowebok").html(look_html);
            initDragHtml(2);
            var viewer = new Viewer(document.getElementById('dowebok'), {url: 'src'});
        },
        addFun: function () {
            let that = this;
            that.modalType = 1;
            $('.layui-form-select').show();
            $('#myModal').modal();
        },
        editFun: function (i) {
            let that = this;
            that.modalType = 2;      
            $('.layui-form-select').hide();
            that.url_list=that.list[i].imgFiles;
            that.url_list2=that.list[i].imgFiles;
            that.initPic1();
            //that.url_list.concat(that.list[i].imgFiles);
            that.roomName = that.list[i].lotName;
            that.lotUuid = that.list[i].lotUuid;
            $('#myModal').modal();
        },
        sureFun:function(){
            let that = this;
            if(that.modalType==1){
                if(that.roomName == ""){
                    layer.msg("请输入小区名称!", {time: 1000});
                    return;
                }
                if(that.url_list.length == 0){
                    layer.msg("请添加外景图!", {time: 1000});
                    return;
                }
            }
       
            that.closeModel();
            that.loadData();
        },
        // 关闭弹窗
        closeModel: function () {
            let that = this;
            that.url_list = [];
            that.url_list2 = [];
            that.roomName="";
            that.lotUuid='';
            that.initPic1();
            $('#myModal').modal('hide');

        },





    }




})