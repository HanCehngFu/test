$(document).ready(function () {


    // jquery
    $(function () {





    })

})
// vue

new Vue({
    el: '#app',
    data: {
        url_list1: [],
        url_list2: [],
        nestUuid:'',
		lotUuid:'',
        fangJianJingtu:[],
        keTingtu:[],
        roomNum: "",
        userPhone:"",
        onLine: '',
        disabled: true,
        roomName:"",
        doorNum:[1,2],
        selDoorNum:"",
        selArea: "",
        landmarkName:"",
        address: "",
        coord: "",
        selStyle: "",
        pledgeFee:"",
        roomNote: "",
        nestCode:"",
       /* outDoorPic: [],
        selOutPic: "",*/
        bedNum: 1,
        tipShow: false,
        checkedTese: [],
        checkedNeishe: [],
        dayPrice: "",
        weekPrice: "",
        leaseWay: "",
        textareaValue: "",
        
        isAlldes: true,
        htmlType:'1',
        id:"",
    },
    mounted: function () {
        let that = this;
        that.default();
        if(getUrlParam("htmlType")){
            that.htmlType = getUrlParam("htmlType");
        }

        that.id = getUrlParam("id")
        console.log(that.htmlType)
        that.editInitDate();
    },
    watch: {
        textareaValue(curVal, oldVal) {
            if (curVal.length > 80) {
                this.textareaValue = String(curVal).slice(0, 80);
            }
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
    	editInitDate:function(){
    		let that = this;
    		if(that.htmlType == '2'){
    			console.log(roomInfo);
    			that.roomName= roomInfo.nestName;
    			that.userPhone=roomInfo.nestHostPhone;
    			that.address=roomInfo.nestLocationSpecific;
    			that.selDoorNum=roomInfo.nestLockNum;
    			that.areaName=roomInfo.areaName;
    			that.landmarkName=roomInfo.landmarkName;
    			that.coord=roomInfo.nestCoordinateB+","+roomInfo.nestCoordinateA,
    			that.doorNum=roomInfo.nestLockNum,
    			that.styleName=roomInfo.styleName;
    			that.pledgeFee=roomInfo.pledgeFee;
    			that.roomNote=roomInfo.roomRemark;
    			that.bedNum=roomInfo.nestBedNum;
    			that.textareaValue=roomInfo.nestDescription;
				that.onLine = roomInfo.nestStatus;
				that.selStyle = roomInfo.styleUuid;
				that.selArea = nestLotInfo.areaUuid;
				that.nestUuid = roomInfo.nestUuid;
				that.lotUuid = roomInfo.lotUuid;
				that.nestCode = roomInfo.nestCode;
				console.log(that.htmlType,that.onLine)
			
				for (var i = 0; i < roomInfo.elementInfos.length; i++) {
					that.checkedTese.push(roomInfo.elementInfos[i].elementUuid);
				}
				for (var i = 0; i < nestInInfos.length; i++) {
					that.checkedNeishe.push(nestInInfos[i].niUuid);
				}
				for (var i = 0; i < fileInfos.length; i++) {
					that.fangJianJingtu.push(fileInfos[i].fileUrl),
					that.url_list1.push(fileInfos[i].fileUrl);
				}
				that.initPic1(that.url_list1);
				for (var i = 0; i < fileInfos2.length; i++) {
					that.keTingtu.push(fileInfos2[i].fileUrl);
					that.url_list2.push(fileInfos2[i].fileUrl);
				}
				that.initPic2(that.url_list2);
				
				/* setTimeout(function() {
				var select = 'dd[lay-value=' + that.selStyle + ']';
				$('#selStyle').siblings("div.layui-form-select").find('dl').find(select).click();
				}, 200); */
	
//		
    		}
    	
    	},
        default: function () {
            let that = this;
            layui.use(['jquery', 'form', "upload"], function () { // 此段代码必不可少
                var form = layui.form;
                var upload = layui.upload;
                var $ = layui.$;
                var url_list1 = that.url_list1;
                var url_list2 = that.url_list2;
                form.on('radio(onLine)', function (data) {
                	if(data.value == 'true'){
                		that.onLine='1';
                	}else{
                		that.onLine='2';
                	}
                    console.log(that.onLine);
                });
                form.on('radio(leaseWay)', function (data) {
                    that.leaseWay = data.value;
                });
                var checkedNeishe = [];
                form.on('checkbox(checkedNeishe)', function (data) {
                    if (this.checked) {
                        checkedNeishe.push(data.value)
                    } else {
                        checkedNeishe.remove(data.value)
                    }
                    that.checkedNeishe = checkedNeishe;
                    console.log(that.checkedNeishe);
                });
                form.on('checkbox(checkedTese)', function (data) {
					var flag = false;
                    if (this.checked) {
						flag = false;
						for (var i = 0; i < that.checkedTese.length; i++) {
							if (that.checkedTese[i] == data.value) {
								flag = true;
								break;
							} 
						}
						if (!flag) {
							that.checkedTese.push(data.value);
						}
                    } else {
						flag = false;
						for (var i = 0; i < that.checkedTese.length; i++) {
							if (that.checkedTese[i] == data.value) {
								flag = true;
								break;
							} 
						}
						if (flag) {
							 that.checkedTese.remove(data.value);
						}
                    }
                    console.log(that.checkedTese);
                });
                form.on('checkbox(isAlldes)', function (data) {
                    that.isAlldes = this.checked;
                });
                form.on('select(selArea)', function (data) {
                    that.selArea = data.value;
                })
                form.on('select(selStyle)', function (data) {
                    that.selStyle = data.value;
                })
                /*form.on('select(selOutPic)', function (data) {
                    that.selOutPic = data.value;
                })*/
                form.on('select(selDoorNum)', function (data) {
                    that.selDoorNum = data.value;
                })
                form.render();
//              //全选
//                var checkedNeisheArr = [];
//                form.on('checkbox(c_all)', function (data) {
//                var a = data.elem.checked;
//                if (a == true) { 
//                checkedArr= [];
//                $(".nameId").prop("checked", true);
//                that.checkedTese.map(function (item) {
//                checkedTeseArr.push(item.elementUuid);
//                });
//                form.render('checkbox');
//                } else {
//                $(".nameId").prop("checked", false);
//                checkedTeseArr = [];
//                form.render('checkbox');
//                }
//                that.checkedTeseArr = checkedTeseArr;
//                })
//                // //有一个未选中全选取消选中
//                form.on('checkbox(c_one)', function (data) {
//                var item = $(".nameId");
//                for (var i = 0; i < item.length; i++) {
//                if (item[i].checked == false) {
//                $("#c_all").prop("checked", false);
//                form.render('checkbox');
//                break;
//                }
//                }
//                //如果都勾选了 勾上全选
//                var all = item.length;
//                for (var i = 0; i < item.length; i++) {
//                if (item[i].checked == true) {
//                all--;
//                }
//                }
//                if (all == 0) {
//                $("#c_all").prop("checked", true);
//                form.render('checkbox');
//                }
//                if (this.checked) {
//                checkedTeseArr.push(data.value)
//                } else {
//                checkedTeseArr.map(function (item) {
//                if (item == data.value) {
//               checkedTeseArr.remove(data.value)
//                }
//                })
//                }
//                that.checkedTeseArr = checkedTeseArr;
//                });
//                
                
                
                
                
                
                // 多图片上传
                //that.initPic1(that.url_list1)
                var aa = upload.render({
                    elem: '#upLoad1',
                    url: '/nestLot/upload',
                    multiple: true,
                    before: function (obj) {
                    	var flag=true;
                        obj.preview(function (index, file, result) {
                            url_list1.push(result)
                            that.url_list1 = url_list1
                            console.log(that.url_list1)
                            if(flag){
                            	setTimeout(function(){
                            		that.initPic1(that.url_list1);
                            	},200)
                            	flag=false;
                            }
                        });
                    },
                    // 上传完毕
                    done: function (res) {
                    	console.log(res.entity);
                    	that.fangJianJingtu.push(res.entity[0]);
                    	//that.fangJianJingtu=that.distinct(that.fangJianJingtu,res.entity);
                    	//that.addNestImg(res,'3');
                    }
                });
                var bb = upload.render({
                    elem: '#upLoad2',
                    url: '/nestLot/upload',
                    multiple: true,
                    before: function (obj) {
                        // 预读本地文件示例，不支持ie8
                        obj.preview(function (index, file, result) {
                            url_list2.push(result)
                            that.url_list2 = url_list2
                            console.log(that.url_list2)
                            that.initPic2(that.url_list2);
                        });
                    },
                    // 上传完毕
                    done: function (res) {
                    	that.keTingtu.push(res.entity[0]);
                    	 //that.keTingtu = that.distinct(that.keTingtu,res.entity);
                    	// that.addNestImg(res,'13');
                    }
                })
                $(".item_container2").on("click", ".close", function () {
                    var ind = $(this).data("index");
                    for (var i = 0; i < that.url_list2.length; i++) {
						if(ind == that.url_list2[i]){
							that.url_list2.splice(i, 1);
							break;
						}
					}
                    for (var i = 0; i < that.keTingtu.length; i++) {
						if(ind == that.keTingtu[i]){
							that.keTingtu.splice(i, 1);
							break;
						}
					}
                    $(this).parent().remove();
                    initDragHtml2();
                });
                $(".item_container").on("click", ".close", function () {
                    var ind = $(this).data("index");
                    console.log(ind);
                    for (var i = 0; i < that.url_list1.length; i++) {
						if(ind == that.url_list1[i]){
							that.url_list1.splice(i, 1);
							break;
						}
					}
                    for (var i = 0; i < that.fangJianJingtu.length; i++) {
						if(ind == that.fangJianJingtu[i]){
							that.fangJianJingtu.splice(i, 1);
							break;
						}
					}
                    $(this).parent().remove();
                    initDragHtml(1);
                });
            })
        },
		initPic1:function (url_list) {
		    var html = "";
		    var look_html = "";
		    for (var i = 0; i < url_list.length; i++) {
		        html += '<div class="item">';
		        html += '<img class = "drag_img" img_index="' + i + '" src="' + url_list[i] + '" width="" height="">';
		        html += '<span  class="rmPicture close iconfont icon-guanbi2" data-index=' + url_list[i] + '></span>';
		        html += '</div>';
		        look_html += '<li><img  img_index="' + i + '"  src="' + url_list[i] + '"></li>';
		    }
		    $(".item_container").html(html);
		    $("#dowebok").html(look_html);
		    initDragHtml(1);
		    var viewer = new Viewer(document.getElementById('dowebok'), {
		        url: 'src'
		    });
		},
		initPic2:function(url_list) {
		    var html = "";
		    var look_html = "";
		    for (var i = 0; i < url_list.length; i++) {
		        html += '<div class="item">';
		        html += '<img class = "drag_img" img_index="' + i + '" src="' + url_list[i] + '" width="" height="">';
		        html += '<span  class="rmPicture close iconfont icon-guanbi2" data-index=' + url_list[i] + '></span>';
		        html += '</div>';
		        look_html += '<li><img  img_index="' + i + '"  src="' + url_list[i] + '"></li>';
		    }
		    $(".item_container2").html(html);
		    $("#dowebok2").html(look_html);
		    initDragHtml2();
		    var viewer = new Viewer(document.getElementById('dowebok2'), {
		        url: 'src'
		    });
		},
        validate:function(){
        	let that = this;
            let nestCoordinate = that.coord.split(',');
            let msg = '';
            if(that.roomName == ''){
                msg += '请输入房间名称'+"</br>";
            }
            if(that.roomName.length<5 || that.roomName.length>45){
                msg += '房间标题在5至45个字符之间'+"</br>";
            }
            if(nestCoordinate[1] == ''){
            	msg += '请输入经纬度'+"</br>";
                    
            }
            if(nestCoordinate[0] == ''){
            	msg += '请输入经纬度'+"</br>";
                    
            }
            if(that.selStyle.length<1){
            	msg += '请选择房间风格'+"</br>";
                    
            }
            if(that.userPhone == ''){
            	msg += '请输入房东手机号码'+"</br>";
                    
            }
            if(that.address == ''){
            	msg += '请输入地址'+"</br>";
                    
            }
            if(that.selArea.length<1){
            	msg += '请选择区域'+"</br>";
                    
            }
            if(that.landmarkName == ''){
            	msg += '请输入商圈名字'+"</br>";
                    
            }
            if(that.textareaValue == ''){
            	msg += '请输入房间描述信息'+"</br>";
                    
            }
            /*if(that.checkedNeishe.length<1){
            	msg += '请选择房间内设'+"</br>";
            }*/
            if(that.checkedTese == ''){
            	msg += '请选择房间特色'+"</br>";
                    
            }
            if(that.fangJianJingtu.length<3){
            	msg += '房间图片上传不得少于3张'+"</br>";
                    
            }
            if(that.keTingtu.length == ''){
            	msg += '公共区域请传一张图片'+"</br>";
                    
            }
            if(that.bedNum < 1){
            	msg += '请输入房间床数'+"</br>";
                    
            }
            if(that.selDoorNum == ''){
            	msg += '请输入门锁数'+"</br>";
                    
            }
            return msg;
        },
        addNestinfo:function(){
        	let that = this;
        	let msg = that.validate();
        	if(that.validate() != '' || msg.trim() != ''){
        		layer.tips(msg, '#addButton',{
        			  tips: [1, '#3595CC'],
        			  time: 4000
        		});
        		return;
        	}
           	let url = phosts + '/nest/addOrUpdateNest';
           	let nestCoordinate = that.coord.split(',');
			let data = {
					nestUuid:that.nestUuid,
					lotUuid:that.lotUuid,
					nestName:that.roomName,
					nestCoordinateA:nestCoordinate[1],
					nestCoordinateB:nestCoordinate[0],
					nestHostPhone:that.userPhone,
					nestLocation:that.address,
					nestLocationSpecific:that.address,
					indistinctPosition:that.address,
					areaUuid:that.selArea,
					landmarkName:that.landmarkName,
					styleUuid:that.selStyle,
					nestDescription:that.textareaValue,
					elementUuid:that.checkedTese,
					niUuid:that.checkedNeishe,
					carousel:that.fangJianJingtu,
					common:that.keTingtu,
					dayXian:that.dayPrice,
					weekDayXian:that.weekPrice,
					roomRemark:that.roomNote,
					nestBedNum:that.bedNum,
					nestLockNum:that.selDoorNum,
					nestStatus:that.onLine,
					nestMinCount:'1',
					nestMaxCount:'2',
					pledgeFee:that.pledgeFee,

					leaseWay:that.leaseWay,
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
                		setTimeout(function() {
                			if(nestCount >=1 ){
                				window.parent.location.href='/nest/index';
                			}else {
                				console.log("-----------------------------------top.location.reload()------------------------------------------>");
                				top.location.reload();
							}
                			
						},1001);
                	} else {
                		layer.msg(res.message, { icon: 1,time: 1000});
					}
                },
                error:function(){
                	
                }
            }); 
        },
        distinct:function (a, b) {
        	let barr = [];
            let arr = a.concat(barr.push(b));
            let result = [];
            for (let i of arr) {
                !result.includes(i) && result.push(i);
            }
            return result;
        },
        addFun: function () {
            let that = this;
            that.bedNum += 1;
        },
        subFun: function () {
            let that = this;
            if (that.bedNum > 0) {
                that.bedNum -= 1;
            }
        },
        showTips: function () {
            let that = this;
            that.tipShow = !that.tipShow;
        },
        nextPage:function(){
            let that = this;
             window.document.location.href="../../../firstUpData/step_three/step_three.html?htmlType=1&v="+ new Date().getTime();
        }

    }




})