$(document).ready(function () {
    // jquery
    $(function () {

        $(".nav_bar a").click(function () {
            console.log("111")
            $(this).addClass("active").siblings().removeClass("active");
        })
        $(".list_nav a").click(function () {
           if($(this).index()==1){
        	   vu.$data.saleTypeContinue=2;	
           }
           if($(this).index()==2){
        	   vu.$data.saleTypeContinue=3;
           }
           if($(this).index()==0){
        	   vu.$data.saleTypeContinue="";	
           }
           $(this).addClass("active").siblings().removeClass("active");
        })

    })
    // vue
   var vu= new Vue({
        el: '#app',
        data: {
            modelTitle: "操作密码",
            modelDes: "*输入后即会对用户全额退款，并取消占用",
            
            btnType: 1,
            password: "",
            saleUUID:'',
            modtype:1,
            saleTypeContinue:"",
            roomRemark: "",
            phoneNum: "",
            username: "",
            startTime: "",
            endTime: "",
            nestCode:"",
            
            form:{},
            isRenderLaypage:false,
            laypage:{},
            currPage: 1,
    		limitPage: 10,
    		count: 0,
    		list: []

        },
        watch: {
        	saleTypeContinue(curVal, oldVal){
        		let that = this;
        		if(curVal !=oldVal){
        			that.isRenderLaypage = false;
        			that.loadData();
        		}
        	}
        },
        mounted: function () {
            let that = this;
            that.default();
            console.log(that.startTime)
        },
        updated: function() { 
        	let that = this;
        	layui.use(['form', 'laydate'], function () {
        		layui.form.render();
        	})
    	},
        methods: {
        	loadData: function() {
    			let that = this;
    			let url = phosts + '/SaleInfo/saleAjaxList';
    			
    			let saleStatus = $("#saleStatus").val();
    			let selRoomI = $("#selRoomId").val();
    			let selSort = $("#selSort").val();
    			let saleStatusApply= $("#saleStatusApply").val();
    			if (saleStatusApply !='') {
    				saleStatus = saleStatusApply;
				}
    			let data = {
    				saleTypeContinue:that.saleTypeContinue,
    				saleStatus: saleStatus,
    				userRealName: that.username,
    				nestCode:that.nestCode,
                    userPhone: that.phoneNum,
                    nestUuid: selRoomI,
                    saleTimeStart: that.startTime,
                    saleTimeEnd: that.endTime,
                    orderBy: selSort,
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
            actionBtn: function (e,saleUUID) {
                let that = this;
                that.btnType = parseInt(e);
                that.saleUUID = saleUUID;
                that.password="123456";
                if (that.password != "") {
                	that.modtype = that.btnType;
                }
                switch (that.modtype) {
                	case 2:
                		if (that.password != "") {
                			that.orderDetail(that.saleUUID);
                        } else {
                        	$('#myModal').modal();
						}
	                    break;
                    case 3:
                        that.modelDes = "您是否确认取消该订单，并为该订单全额退款。";
                        that.modelTitle = "退款";
                        $('#myModal').modal();
                        break;
                    case 4:
                        that.modelDes = "结束订单后，状态更改为已完成，房间占用会被放开，订单金额不会退款。";
                        that.modelTitle = "结束订单";
                        $('#myModal').modal();
                        break;
                    default:
                    	$('#myModal').modal();
                    	break;
                }
                
            },
            surePassword: function () {
                let that = this;
                console.log(that.btnType+'--------------------'+that.saleUUID);
                if (that.password == "") {
                    layer.msg("密码不能为空！", { time: 1 * 1000});
                    return;
                }
                let url = phosts;
                let data={};
                switch (that.btnType) {
                    case 2:
                    	that.orderDetail(that.saleUUID);
                        break;
                    case 3:
                    	// 退款
                    	url += '/SaleInfo/remove?saleUuid='+that.saleUUID;
                    	$.post(url, data, function(res, textStatus, jqXHR) {
            				if (res.success) {
            					console.log(res);
            					 that.loadData();
        					}
            				layer.msg(res.message, { time: 1 * 1000});
                    	}, 'JSON');
                        
                        break;
                    case 4:
                    	// 结束订单
                    	url += '/SaleInfo/saleShutDown?saleUuid='+that.saleUUID;
                    	$.post(url, data, function(res, textStatus, jqXHR) {
            				if (res.success) {
            					console.log(res);
            					 that.loadData();
        					}
            				layer.msg(res.message, { time: 1 * 1000});
                    	}, 'JSON');
                        break;
                }
                that.closeModel();
            },
            closeModel: function () {
                let that = this;
                $('#myModal').modal('hide');
                that.btnType = 1;
                that.modelTitle = "操作密码";
                // that.password = "";
                that.saleUUID='';
                that.modelDes = "*输入后即会对用户全额退款，并取消占用";
            },
            suerSearch() {
                let that = this;
                let saleStatus = $("#saleStatus").val();
                let selRoomI = $("#selRoomId").val();
                let selSort = $("#selSort").val();
                let saleStatusApply= $("#saleStatusApply").val();
                that.form.verify();
                if (saleStatus == "" /* && selBackOrderStatus == "" */ && that.username == "" &&
                    that.phoneNum == "" && selRoomI == "" && that.startTime == "" && that
                    .endTime == "" && selSort == "" && that.roomRemark == "" && saleStatusApply=='') {
                    layer.msg("搜索条件不能为空哦！", {
                        time: 1 * 1000
                    })
                    return
                }
                that.isRenderLaypage=false;
                that.loadData();
            },
            orderDetail: function (id) {
                let that = this;
                window.location.href= phosts+'/SaleInfo/orderdetail?saleUuid='+id;

            },
            default: function () {
                let that = this;

                // layui
                layui.use(['form', 'laydate'], function () { // 此段代码必不可少
                    that.form = layui.form;
                    
                    that.form.on('select(saleStatus)', function(data) {
                    	console.log(data);
                    	$("#saleStatusApply").val('');
    				})
    				that.form.on('select(saleStatusApply)', function(data) {
    					console.log(data);
                    	$("#saleStatus").val('');
    				})
    				that.form.render();
                    var laydate = layui.laydate;
                    laydate.render({
                        elem: '#selDate_input',
                        range: '-',
                        format: 'yyyy/MM/dd',
                        eventElem: '.calebder1',
                        trigger: 'click',
                        change: function (value, date) { // 监听日期被切换
                            console.log(value)
                            var startTime = value.split('-')[0];
                            var endTime = value.split('-')[1];
                            that.startTime = startTime;
                            that.endTime = endTime;
                        }
                    });
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
	        method5:function(){
	        	let that = this;
    			let url = phosts + '/SaleInfo/export';
    			
    			let saleStatus = $("#saleStatus").val();
    			let selRoomI = $("#selRoomId").val();
    			let selSort = $("#selSort").val();
    			let saleStatusApply= $("#saleStatusApply").val();
    			if (saleStatusApply !='') {
    				saleStatus = saleStatusApply;
				}
    			url +='?saleTypeContinue='+that.saleTypeContinue+'&';
    			url +='saleStatus='+saleStatus+'&';
    			url +='userRealName='+that.username+'&';
    			url +='nestCode='+that.nestCode+'&';
    			url +='userPhone='+that.phoneNum+'&';
    			url +='nestUuid='+selRoomI+'&';
    			url +='saleTimeStart='+that.startTime+'&';
    			url +='saleTimeEnd='+that.endTime+'&';
    			url +='orderBy='+selSort+'&';
    			url +='roomRemark='+that.roomRemark;
    			
    			var a = document.createElement('a');
    		    var file_name = "订单数据报表";
    		    if( that.startTime != ''){
    		    	 file_name +=that.startTime+'--';
    		    }
    		    if( that.endTime != ''){
    		    	 file_name +=that.endTime;
    		    }
    		    file_name += ".xls";
    		    a.href = url;
    		    a.download = file_name;
    		    a.click();
    		    a.remove();
	        }

        }
    })
});