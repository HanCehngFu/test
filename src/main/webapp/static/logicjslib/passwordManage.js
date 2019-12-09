$(document).ready(function () {
    // jquery
    $(function () {
    	
    });
});
// vue 

new Vue({
    el: '#app',
    data: {
    	currentTab:0,
        insidePassword: "",
        outPassword: "",
        doorNum: 2,
       

        selRoom: "",
        selStatus: "",
        addSelRoom: "",
        queryRoomTittle: "",
        startTime: "",
        endTime: "",
        nestUuid:'',
        nestCode:'',
        nestLockNum:'',
        
        modalType: 1, //1查看密码2添加密码
        passWordList:[],
        
        form:{},
        laypage:{},
        isRenderLaypage:false,
        currPage: 1,
		limitPage: 10,
		count: 0,
		list: [],
		
		isRenderLaypage1:false,
		currPage1: 1,
		limitPage1: 10,
		count1: 0,
		queryList: []
    },
    mounted: function () {
        let that = this;
        that.default();
        that.loadData();
        if (getUrlParam('currentTab')) {
            that.currentTab = getUrlParam("currentTab")
        }
    },
    watch: {
        insidePassword(curVal, oldVal) {
            if (curVal.length > 6) {
                this.insidePassword = String(curVal).slice(0, 6);
            }
        },
        outPassword(curVal, oldVal) {
            if (curVal.length > 6) {
                this.outPassword = String(curVal).slice(0, 6);
            }
        },

    },
    updated: function () {
        let that = this;
        this.$nextTick(function () {
            that.default();
        });
    },
    methods: {
    	   // nav 切换
        changeTab: function(e) {
            let that = this;
            that.currentTab = e;
            that.isRenderLaypage=false;
            that.loadData();
        },
    	loadData: function() {
			let that = this;
			let url = phosts + '/nestlotpwd/nestInfoList';
			let data = {
				needAddPwd:that.currentTab,
				nestUuid:that.selRoom,
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
    	loadNestPwdInfo: function() {
			let that = this;
			let url = phosts + '/nestlotpwd/nestpwdList';
			let stDate ='';
			let enDate ='';
			
			if(that.startTime != ''){
				stDate = that.startTime.replace(new RegExp('/','g'),"-").replace(' ','')+' 00:00:00';
			}
			if(that.endTime != ''){
				enDate = that.endTime.replace(new RegExp('/','g'),"-")+' 00:00:00';
			}
			let data = {
				nestUuid:that.nestUuid,
				nestCode:that.nestCode,
				startTime:stDate,
				endTime:enDate, 
				usedStatus:that.selStatus,
				lockPasswordIn:that.insidePassword, 
				lockPasswordOut:that.outPassword, 
				pageNum: that.currPage1,
				pageSize: that.limitPage1,
			};
			$.post(url, data, function(res, textStatus, jqXHR) {
				if (res.success) {
					console.log(res.entity.list);
					if (res.entity.list != null) {
						that.queryList = res.entity.list;
					}
    				that.count1 = res.entity.total;
    				that.limitPage1 = res.entity.pageSize;
				}
				if(!that.isRenderLaypage1){
					that.laypage.render({
                        elem: 'test2',
                        count: that.count1,
                        limit: that.limitPage1,
                        layout: [ 'prev', 'page', 'next', 'limit', 'refresh', 'skip'],
                        jump: function (obj) {
                        	that.currPage1 = obj.curr;
    						that.limitPage1 = obj.limit;
    						if(that.isRenderLaypage1){
    							that.loadNestPwdInfo();
    						}
                        }
                    });
					that.isRenderLaypage1=true;
				}
				layer.msg(res.message, {time: 1 * 1000});
			}, 'JSON');
		},
        suerSearch() {
            let that = this;
            if (that.selRoom == "" && that.selStatus == "") {
                layer.msg("搜索条件不能为空哦！", {time: 1 * 1000});
                return;
            }
            that.isRenderLaypage=false;
            that.loadData();
        },
        deleteFun: function (e) {
            let that = this;
            layer.confirm('是否要删除信息!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
                var queryList = that.queryList;
                let pwdID = queryList[e].id;
                let url = phosts + '/nestlotpwd/deletenestpwd?id='+pwdID;
                let data={};
                $.post(url, data, function(res, textStatus, jqXHR) {
                	if(res.success){
                		queryList.splice(e, 1)
                        layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                        layer.msg("删除成功!", { icon: 1,time: 1000});
                        that.queryList = queryList;
                	} else {
                		layer.msg(res.message, { icon: 1,time: 1000});
					}
                });
            });

        },
        queryAllPassword: function (id, title) {
            let that = this;
            that.queryRoomTittle = title;
            that.modalType = 1;
            that.nestUuid = id;
            for (var i = 0; i < that.list.length; i++) {
				var nestinfoj= that.list[i];
				if(nestinfoj.nest_uuid == that.nestUuid){
					that.nestLockNum=nestinfoj.nest_lock_num;
					break;
				}
			}
            that.loadNestPwdInfo();
            $('#myModal').modal();
        },
        addPassWord: function (id) {
            let that = this;
            that.modalType = 2;
            that.nestUuid=id;
            for (var i = 0; i < that.list.length; i++) {
				var nestinfoj= that.list[i];
				if(nestinfoj.nest_uuid == that.nestUuid){
					that.nestLockNum=nestinfoj.nest_lock_num;
					break;
				}
			}
            $('#myModal').modal();
        },
        searchPassword: function () {
            let that = this;
            if (that.insidePassword == "" && that.insidePassword == "" && that.startTime == "" && that.endTime == "") {
                layer.msg("搜索条件不能为空哦！", {time: 1 * 1000});
                return;
            }
            that.isRenderLaypage1=false;
            that.loadNestPwdInfo();
        },
        // 关闭弹窗
        closeModel: function () {
            let that = this;
            $('#myModal').modal('hide');
            that.isRenderLaypage1 = false;
            that.insidePassword = "";
            that.outPassword = "";
            that.startTime = "";
            that.endTime = "";
            that.addSelRoom = "";
            that.nestUuid='';
            that.passWordList = [];
            that.nestLockNum='';
            $("#selEndTime").val("");
            $("#selStartTime").val("");
            $("#selDate").val("");
            $("#addSelRoom").val("");
            layui.form.render();
        },
        default: function () {
            let that = this;
            layui.use(['form', "laydate"], function () { //此段代码必不可少
                that.form = layui.form;
                // 监听selecte
                that.form.on('select(selRoom)', function (data) {
                    that.selRoom = data.value;
                });
                that.form.on('select(selStatus)', function (data) {
                    that.selStatus = data.value;
                });
                 that.form.on('select(addSelRoom)', function (data) {
                    console.log(data);
                    that.addSelRoom = data.value;
                });
                that.form.render();
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#selStartTime',
                    type:'datetime',
                    format: 'yyyy-MM-dd HH:mm:ss',
                    eventElem: '.calebder2',
                    trigger: 'click',
                    done: function (value, date) { //监听日期被切换
                        console.log(value)
                        var v = value;
                        if (that.endTime != "") {
                            console.log(v, that.startTime);
                            var s = new Date(v).getTime();
                            var e = new Date(that.endTime).getTime();
                            if (s > e) {
                                layer.msg('开始时间不能大于结束时间！', {
                                    time: 1 * 1000
                                });
                                $("#selStartTime").val("");
                                v = "";
                            }
                        }
                        that.startTime = v;
                    }
                });
                laydate.render({
                    elem: '#selEndTime',
                    type:'datetime',
                    format: 'yyyy-MM-dd HH:mm:ss',
                    eventElem: '.calebder3',
                    trigger: 'click',
                    done: function (value, date) { //监听日期被切换
                        console.log(value)
                        var v = value;
                        if (that.startTime != "") {
                            console.log(v, that.startTime)
                            var e = new Date(v).getTime();
                            var s = new Date(that.startTime).getTime();
                            if (s > e) {
                                layer.msg('结束时间不能小于开始时间！', {
                                    time: 1 * 1000
                                });
                                v = "";
                                $("#selEndTime").val("");
                            }
                        }
                        that.endTime = v;
                    }
                });
                laydate.render({
                    elem: '#selDate',
                    type:'datetime',
                    range: '-',
                    min: new Date().valueOf(),
                    format: 'yyyy-MM-dd HH:mm:ss',
                    eventElem: '.calebder1',
                    trigger: 'click',
                    change: function (value, date) { //监听日期被切换
                        console.log(value)
                        var startTime = value.split(' - ')[0];
                        var endTime = value.split(' - ')[1];
                        that.startTime = startTime;
                        that.endTime = endTime;
                    }
                });
                // 分页
               that.laypage = layui.laypage;
            });
            
        },
        createInsidePassword: function () {
            let that = this;
            if (that.insidePassword == "" || that.insidePassword.length < 6) {
                layer.msg("请输入6位门内密码！", {time: 1 * 1000});
                return;
            }
            if (that.passWordList.length >= 7) {
                layer.msg("只能预存7个门内密码！", {time: 1 * 1000});
                return;
            }
            that.passWordList.push({inpwd:that.insidePassword,outpwd:''});
            //that.insidePasswordArr.push(that.insidePassword);
            that.insidePassword = "";
        },
        createOutPassword: function () {
            let that = this;
            if (that.outPassword == "" || that.outPassword.length < 6) {
                layer.msg("请输入6位门内密码！", {time: 1 * 1000});
                return;
            }

            for(i=0;i<that.passWordList.length;i++){
            	if (that.passWordList[i].inpwd !=null && that.passWordList[i].outpwd == '') {
            		that.passWordList[i].outpwd = that.outPassword;
            		break;
				}
            }
            that.outPassword = "";
        },
        deleteInsidePassword: function (e) {
            let that = this;
            that.passWordList.remove(e);
        },
        deleteOutPasswor: function (e) {
            let that = this;
            that.passWordList.remove(e);
        },
        //  生成6位随机数
        MathRand: function () {
            var Num = "";
            for (var i = 0; i < 6; i++) {
                Num += Math.floor(Math.random() * 10);
            }
            return Num

        },
        autoCreate: function () {
            let that = this;
            if (that.passWordList.length >= 7) {
                layer.msg("本次不能超过7个预存密码！", {time: 1 * 1000});
                return
            }
            if(that.nestLockNum == "2"){
            	that.passWordList.push({inpwd:that.MathRand(),outpwd:that.MathRand()});
            }else if(that.nestLockNum == "1"){
            	that.passWordList.push({inpwd:that.MathRand()});
            }
        },
        sureFun: function () {
            let that = this;
            if (that.modalType == 1) { //查看密码

            } else { //添加密码
                if (that.startTime == "" && that.endTime == "") {
                    layer.msg("请选择有效开始时间和结束时间！", {
                        time: 1 * 1000
                    })
                    return
                }
                if (that.passWordList.length == 0) {
                    layer.msg("请设置门内密码！", {
                        time: 1 * 1000
                    })
                    return
                }
                let url = phosts + '/nestlotpwd/aadnestpwd';
                let data = {
                	nestUuid : that.nestUuid,
                	startTime:that.startTime,
                	endTime:that.endTime,
                	passWordList:that.passWordList
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
                    		that.isRenderLaypage=false;
                    		that.loadData();
                    	} else {
                    		layer.msg(res.message, { icon: 1,time: 1000});
    					}
                    	that.closeModel();
                    },
                    error:function(){
                    	
                    }
                });
            }
        }

    }

});