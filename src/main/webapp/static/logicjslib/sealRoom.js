$(document).ready(function () {
    $(function () {



    });
})


new Vue({
    el: '#app',
    data: {
        sealAllRoom: false,
        width: 0,
        useType: 1, //1可预订2封房
        timeArray: [],
        disabled: true,
        monthNum: 1,
        scrollLeft: 0,
        moveTrNum: 7,
        trWidth: 145,
        righWidth: 0,
        leftWidth: 0,
        clientWidth: 0,
        moveWidth: 0,
        moveLeft: false,
        moveRight: true,
        startTime: "",
        endTime: "",
        textareaValue: "",
		
       
        selRoomList: [],
        usedType: "",
        roomRemark:'',
        nestName:'',
		selRoom: "",
		ntData:{},
		orderDetail:{},
		nestCode:"",
        form:{},
        isRenderLaypage:false,
        laypage:{},
        currPage: 1,
		limitPage: 7,
		count: 0,
		list: []

    },
    mounted: function () {
        let that = this;
        var nowTime = new Date();
        var year = nowTime.getFullYear();
        var month = nowTime.getMonth();
        var currMonth = parseInt(month) + 1
        var curDay = year + "/" + that.add0(currMonth) + "/" + that.add0(nowTime.getDate());
        if (getUrlParam("monthNum")) {
            that.monthNum = getUrlParam("monthNum");
        }
		console.log(that.monthNum);
        var monthNum = that.monthNum;
        for (var i = 1; i <= monthNum; i++) {
            year = month + 1 > 12 ? year + 1 : year;
            month = month + 1 > 12 ? 1 : month + 1;
            // 每个月的数据
            time = that.dataInit(year, month);
        }
        // console.log(that.timeArray)
        that.timeArray[0].isToday = that.timeArray[0].isToday + " (今天)";
        that.initSelectBox('.right_table', that.showModal);
        that.default();
       // that.usedFun();

        $('#myModal').on('hidden.bs.modal', function () {
            that.closeModel();

        })

    },
    updated: function () {
		let that = this;
        layui.use(['form', 'laydate'], function () {
            var form = layui.form;
            form.render();
            var laydate = layui.laydate;
            var ins1 = laydate.render({
                elem: '#selDate_input',
                range: '-',
                format: 'yyyy/MM/dd',
                	eventElem: '.calebder',
                	trigger: 'click',
                min: new Date().valueOf(),
                max: new Date(that.timeArray[that.timeArray.length - 1].strTime).valueOf(),
                done: function (value, date, endDate) {
                    if (value != "") {
                        var startTime = value.split('-')[0];
                        var endTime = value.split('-')[1];
                        if (new Date(startTime).getTime() > new Date(endTime).getTime()) {
                            setTimeout(function () {
                                $("#selDate_input").val("");
                            }, 200);
                            layer.msg("结束时间不能小于开始时间!");
                            startTime="";
                            endTime="";
                        }
                        that.startTime = startTime;
                        that.endTime = endTime;
                        var roomList = that.list;
                        var selRoomList = that.selRoomList;
                        var i, j, k;
                        console.log(selRoomList)
                        for (k = 0; k < selRoomList.length; k++) {
                            if (roomList.length > 0) {
                                for (i = 0; i < roomList.length; i++) {
                                    if (selRoomList[k].roomId == roomList[i].nestInfo.nestUuid) {
                                        if (roomList[i].nestTimeInfos.length > 0) {
                                            for (j = 0; j < roomList[i].nestTimeInfos.length; j++) {
                                                var itemTime = roomList[i].nestTimeInfos[j];
                                                var startNum = parseInt(itemTime.timeStart.replace(/-/g, ""));
                                                var endNum = parseInt(itemTime.timeEnd.replace(/-/g, ""));
                                                if (startTime.replace(/\//g, "") <= startNum && endTime.replace(/\//g, "") >= startNum || startTime.replace(/\//g, "") <= endNum && endTime.replace(/\//g, "") >= endNum) {
                                                    that.startTime = "";
                                                    that.endTime = "";
                                                    setTimeout(function () {
                                                        $("#selDate_input").val("");
                                                    }, 200);
                                                    layer.msg("此时间段有房间被占用不能选择封房!");
                                                    return;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }


                    }else{
                        that.startTime = "";
                        that.endTime = "";
                        setTimeout(function () {
                            $("#selDate_input").val("");
                        }, 200);
                    }


                },
                testClear: function () { //此处是初始化开始时间的限制
                    console.log(1112667)
                }
            });
        })
    },
    methods: {
		suerSearch:function () {
			let that = this;
			that.isRenderLaypage=false;
			that.loadData();
		},
    	loadData: function() {
			let that = this;
			let url = phosts + '/nest/ajaxCloseHouseList';
			let data = {
			    nestUuid: that.selRoom,
			    roomRemark: that.roomRemark,
			    nestName: that.nestName,
				pageNum: that.currPage,
				pageSize: that.limitPage,
			};
			$.post(url, data, function(res, textStatus, jqXHR) {
				layer.msg(res.message, {time: 1 * 1000});
				if (res.success) {
					if (res.entity.closeHouseList != null) {						
						that.list = res.entity.closeHouseList;
						that.usedFun();
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
			}, 'JSON');
		},
        filterPrice: function (index, date) {
            let that = this;
            var price = 0;
            var priceList = that.list[index].nestPriceList;
			for (var j = 0; j < priceList.length; j++) {
			    var npDate = priceList[j].npDate.replace(/-/g, "");
			    if (date == npDate) {
			        price = priceList[j].npNowdayPrice;
			    }
			}
            return price;
        },
        getDataArr: function () {
            let that = this;
            var startDate = new Date();
            var endDate = new Date();
            endDate.setDate(startDate.getDate() + 30);
            var dataArr = [];
            var weeks = ['日', '一', '二', '三', '四', '五', '六'];
            while ((endDate.getTime() - startDate.getTime()) >= 0) {
                var obj = {};
                var year = startDate.getFullYear()
                var month = (startDate.getMonth() + 1).toString().length == 1 ? "0" + (startDate
                        .getMonth() + 1)
                    .toString() : (startDate.getMonth() + 1);
                var day = startDate.getDate().toString().length == 1 ? "0" + startDate
                    .getDate() :
                    startDate.getDate();
                var week = weeks[startDate.getDay()];
                if (month == 12 && parseInt(day > 1)) {
                    year = parseInt(year) + 1
                }
                obj.strDate = month + "月" + day + '日';
                obj.rormatDate = year + "-" + month + "-" + day;
                dataArr.push(obj);
                startDate.setDate(startDate.getDate() + 1);
            }
            dataArr[0].strDate = dataArr[0].strDate + "(今天)";
            // return dataArr;
            that.dataArr = dataArr;

        },
        moveLeft: function () {
            let that = this;
            if (that.moveLeft <= that.width && that.moveLeft > 160) {

            }
        },
        getWidth: function () {
            let that = this;
            that.righWidth = $(".right_table").width();
            that.leftWidth = $(".tabel_content-left").width()
            that.clientWidth = $(".tabel_layout").width() - $(".tabel_content-left").width();
            that.scrollLeft = $(".tabel_content-right").scrollLeft();
            that.moveWidth = that.moveTrNum * that.trWidth;
            console.log(that.righWidth, that.leftWidth, that.clientWidth)

        },
        default: function () {
            let that = this;
            layui.use(['form', 'laydate'], function () { //此段代码必不可少
                that.form = layui.form;
                that.form.render();
                that.form.on('radio(useType)', function (data) {
                    that.useType = data.value;
                });
                that.form.on('select(selRoom)', function (data) {
                    that.selRoom = data.value;
                });
                that.laypage = layui.laypage;
                that.loadData();
            });
             
        },
        // 日历初始化
        dataInit(setYear, setMonth) {
            let that = this;
            var now = setYear ? new Date(setYear, setMonth) : new Date();
            var year = setYear || now.getFullYear();
            var month = setMonth || now.getMonth() + 1;
            var obj = {};
            var dateArr = [];
            var arrLen = 0;
            // var startWeek = new Date(year + '-' + that.add0(month) + '-' + that.add0(currDay)).getDay();
            var dayNums = new Date(year, that.add0(month), 0).getDate();
            var num = 0;
            arrLen = dayNums * 1;
            var startDay = 1;
            if (year == new Date().getFullYear()) {
                if (month == new Date().getMonth() + 1) {
                    startDay = new Date().getDate();
                }
            } else {
                if (month == new Date().getMonth() + 1) {
                    arrLen = new Date().getDate();
                }
            }
            for (var i = startDay; i <= arrLen; i++) {
                num = i;
                obj = {
                    month: month,
                    isToday: that.add0(month) + '月' + that.add0(num) + "日",
                    dateNum: num,
                    timeNum: String(year) + String(that.add0(month)) + that.add0(num),
                    strTime: year + "/" + that.add0(month) + "/" + that.add0(num)
                }
                dateArr[i] = obj;
                that.timeArray.push(obj);
            };

            // return dateArr;
        },
        add0(m) {
            return m < 10 ? '0' + m : m
        },
        clikLeftArrow: function () {
            let that = this;
            if (that.moveLeft) {
                that.getWidth();
                if (that.scrollLeft >= that.moveWidth) {
                    that.scrollLeft = that.scrollLeft - that.moveWidth;
                    console.log(that.scrollLeft)
                    $(".tabel_content-right").stop(true, false).animate({
                        scrollLeft: that.scrollLeft
                    }, {
                        specialEasing: {
                            duration: 2000,
                            scrollLeft: 'easeOutQuart',
                        }
                    });
                    // // $(".tabel_content-right").scrollLeft(that.moveWidth);
                } else {
                    $(".tabel_content-right").scrollLeft(0);
                    that.scrollLeft = 0;
                    that.moveLeft = false;
                    that.moveRight = true;
                }

            };
        },
        clickRightArrow: function () {
            let that = this;
            if (that.moveRight) {
                that.getWidth();
                if (that.scrollLeft <= (that.righWidth - that.clientWidth)) {
                    that.scrollLeft = that.scrollLeft + that.moveWidth;
                    console.log(that.scrollLeft)
                    $(".tabel_content-right").stop(true, false).animate({
                        scrollLeft: that.scrollLeft
                    }, {
                        specialEasing: {
                            duration: 2000,
                            scrollLeft: 'easeOutQuint',
                        }
                    });
                    // $(".tabel_content-right").scrollLeft(that.scrollLeft);
                    that.moveLeft = true;
                } else {
                    $(".tabel_content-right").scrollLeft(0);
                    that.scrollLeft = 0;
                    that.moveLeft = true;
                    that.moveRight = false;
                }
            };
        },
        //拖动选择
        initSelectBox: function (selector, selectCallback) {
            let that = this;
            if (that.monthNum == 1) {
                return;
            }

            function clearBubble(e) {
                if (e.stopPropagation) {
                    e.stopPropagation();
                } else {
                    e.cancelBubble = true;
                }

                if (e.preventDefault) {
                    e.preventDefault();
                } else {
                    e.returnValue = false;
                }
            }
            var $container = $(selector);
            //  框选事件
            var hasMove = false;
            $container.on('mousedown', function (eventDown) {
                    //  设置选择的标识
                    var isSelect = true;
                    //  创建选框节点
                    var $selectBoxDashed = $('<div class="select-box-dashed"></div>');
                    $('body').append($selectBoxDashed);
                    //  设置选框的初始位置
                    var offsetTop = Math.abs($(".tabel_content-right").offset().top);
                    var offsetLeft = Math.abs($(".tabel_content-right").offset().left);
                    var startX = eventDown.x || eventDown.clientX;
                    var startY = eventDown.y || eventDown.clientY;
                    $selectBoxDashed.css({
                        left: startX,
                        top: startY
                    });

                    //  根据鼠标移动，设置选框宽高
                    var _x = null;
                    var _y = null;
                    //  清除事件冒泡、捕获
                    // clearBubble(eventDown);
                    //  监听鼠标移动事件
                    $(selector).on('mousemove', function (eventMove) {
                        hasMove = true;
                        //  设置选框可见
                        $selectBoxDashed.css('display', 'block');
                        //  根据鼠标移动，设置选框的位置、宽高
                        _x = eventMove.x || eventMove.clientX;
                        _y = eventMove.y || eventMove.clientY;
                        //  暂存选框的位置及宽高，用于将 select-item 选中
                        var _left = Math.min(_x, startX);
                        var _top = Math.min(_y, startY);
                        var _width = Math.abs(_x - startX);
                        var _height = Math.abs(_y - startY);
                        $selectBoxDashed.css({
                            left: _left,
                            top: _top,
                            width: _width,
                            height: _height
                        });
                        var scrollLeft = $(".tabel_content-right").scrollLeft();
                        var scrollTop = $(".tabel_content-right").scrollTop();
                        var selTop = _top - offsetTop + scrollTop;
                        var selLeft = _left - offsetLeft + scrollLeft;
                        // console.log(_left+_width)
                        // console.log(selLeft, selTop)
                        // console.log("x虚线宽高" + selLeft, selTop, _width, _height)
                        //  遍历容器中的选项，进行选中操作
                        $(selector).find('.td').each(function () {
                            var $item = $(this);
                            var itemX_pos = $item.prop('offsetWidth') + $item.prop('offsetLeft');
                            var itemY_pos = $item.prop('offsetHeight') + $item.prop('offsetTop');
                            // console.log("offsetWidth:" + $item.prop('offsetWidth'), "offsetLeft" + $item.prop('offsetLeft'))
                            // console.log("offsetHeight:" + $item.prop('offsetHeight'), "offsetTop" + $item.prop('offsetTop'))
                            //  判断 select-item 是否与选框有交集，添加选中的效果（ temp-selected ，在事件 mouseup 之后将 temp-selected 替换为 selected）
                            var condition1 = itemX_pos > selLeft;
                            var condition2 = itemY_pos > selTop;
                            var condition3 = $item.prop('offsetLeft') < (selLeft + _width);
                            var condition4 = $item.prop('offsetTop') < (selTop + _height);

                            // console.log(condition1, condition2, condition3, condition4)
                            if (condition1 && condition2 && condition3 && condition4) {
                                $item.addClass('temp-selected');
                            } else {
                                $item.removeClass('temp-selected');
                            }
                        });
                        //  清除事件冒泡、捕获
                        clearBubble(eventMove);
                    });
                    $(document).on('mouseup', function () {
                        $(selector).off('mousemove');
                        $(selector)
                            .find('.temp-selected')
                            .removeClass('temp-selected')
                            .addClass('selected');
                        $selectBoxDashed.remove();
                        var flag = true;
                        $('.selected').each(function () {
                            if ($(this).data("usedtype") != 3) {
                                flag = false;
                            }
                        })
                        if (hasMove) {
                            // console.log("移动后鼠标松开事件");
                            if (flag) {
                                if ($(selector).find('.selected').length > 0) {
                                    if (selectCallback) {
                                        selectCallback(selector);
                                    }
                                }
                            } else {
                                $('.selected').each(function () {
                                    $(this).removeClass('selected');
                                })
                                layer.msg("有房间被占用无法选择!", {
                                    time: 1000
                                });
                            }
                        } else {
                            // console.log("没有移动鼠标松开事件,模拟click");
                        }
                        hasMove = false;


                    });
                })

                //  点选切换选中事件
                .on('click', '.td', function () {
                    console.log(222)
                    if ($(this).data("usedtype") != 3) {
                        layer.msg("有房间被占用无法选择!", {
                            time: 1000
                        });
                        return;
                    }
                    if ($(this).hasClass('selected')) {
                        $(this).removeClass('selected');
                        // $('#myModal').modal('hide');
                        if ($(selector).find('.selected').length > 0) {
                            if (selectCallback) {
                                selectCallback(selector);
                            }
                        } else {
                            that.selRoomList = [];
                        }
                    } else {
                        $(this).addClass('selected');
                        if ($(selector).find('.selected').length > 0) {
                            if (selectCallback) {
                                selectCallback(selector);
                            }
                        }
                    }
                })
        },
        showModal: function (selector) {
            let that = this;
            $('#myModal').modal();
            var selRoomList = [];
            var totalPrice = 0;
            var roomId = undefined;
            var timeList = [];
            $(selector).find('.selected').each(function () {
                var item = $(this);
                var obj = {};  
				console.log(item.data("roomid"));
                if (item.data("roomid") == roomId) {
                    var ind = selRoomList.length - 1;
                    selRoomList[ind].totalPrice = selRoomList[ind].totalPrice + item.data("price");
                    totalPrice += item.data("price");
                    timeList.push(item.data("time"))
                    selRoomList[ind].timeList = timeList;
                    selRoomList[ind].totalDay = timeList.length;
                    var startT = timeList[0].replace("/", "").replace("/", "");
                    var endT = timeList[timeList.length - 1].replace("/", "").replace("/", "");
                    if (startT < endT) {
                        selRoomList[ind].startTime = timeList[0];
                        selRoomList[ind].endTime = timeList[timeList.length - 1];
                    } else {
                        selRoomList[ind].startTime = timeList[timeList.length - 1];
                        selRoomList[ind].endTime = timeList[0];
                    }
                } else {
                    timeList = [];
                    roomId = item.data("roomid");
                    totalPrice = item.data("price");
                    timeList.push(item.data("time"))
                    obj.totalPrice = totalPrice;
                    obj.roomId = roomId;
                    obj.timeList = timeList;
                    obj.totalDay = timeList.length;
                    obj.startTime = item.data("time");
                    obj.endTime = item.data("time");
                    selRoomList.push(obj)
                }
            })
            that.selRoomList = selRoomList;
            that.startTime = selRoomList[0].startTime;
            that.endTime = selRoomList[0].endTime;
            $("#selDate_input").val(selRoomList[0].startTime+" - "+selRoomList[0].endTime);
        },
        // 关闭弹窗
        closeModel: function () {
            console.log('关闭')
            let that = this;
			that.useType=3;
            // $('#myModal').modal('hide');
            $(".right_table").find('.td').each(function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    that.selRoomList = [];
                }
            });
			that.ntData = {};
			that.orderDetail = {};
        },
        submitData: function () {
            let that = this;
			if ( that.selRoomList.length<1) {
				 layer.msg("请用鼠标拖拽选择时间列表!", {time: 1000});
				return;
			} 
            if (that.startTime == "") {
                layer.msg("请选择时间!", {time: 1000});
                return;
            }
			if (that.timeEnd == "") {
				 layer.msg("请选择时间!", {time: 1000});
				 return;
			}
			if (that.textareaValue == "") {
				 layer.msg("请输入封房备注!", {time: 1000});
				 return;
			}
			let url= phosts + '/nest/closeHouse';
			let data = {
				nestInfos:that.selRoomList,
				timeStart:that.startTime,
				timeEnd:that.endTime,
				remark:that.textareaValue,
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
						that.loadData();
			    	} else {
			    		layer.msg(res.message, { icon: 1,time: 1000});
					}
			    },
			    error:function(){
			    	
			    }
			});   
            $('#myModal').modal('hide');
        },
        usedFun: function () {
            let that = this;
			$(".usedBox")[0].innerHTML='';
			console.log(that.list.length);
            var timeArray = that.timeArray;
            if (that.list.length > 0) {
                var i, j, k;
                for (i = 0; i < that.list.length; i++) {
					var nestTimeInfos = that.list[i].nestTimeInfos;
					
                    if (nestTimeInfos.length > 0) {
                        for (j = 0; j < nestTimeInfos.length; j++) {
                            var itemTime = nestTimeInfos[j];
							console.log(itemTime);
                            var top = (i + 1) * 54 + 8;
                            if (itemTime.timeEnd.split(" ")[0].replace(/-/g, "") > timeArray[timeArray.length - 1].timeNum && itemTime.timeStart.split(" ")[0].replace(/-/g, "") < timeArray[timeArray.length - 1].timeNum) {
                                itemTime.timeEnd = timeArray[timeArray.length - 1].timeStart.split(" ")[0].replace(/\//g, "-");
                            }

                            if (itemTime.timeStart.split(" ")[0].replace(/-/g, "") < timeArray[0].timeNum && itemTime.timeEnd.split(" ")[0].replace(/-/g, "") > timeArray[0].timeNum) {
                                itemTime.timeStart = timeArray[0].strTime.replace(/\//g, "-");
                            }
                            var totalDay = GetDateDiff(itemTime.timeStart, itemTime.timeEnd);
                            var width = Math.ceil(totalDay) * 145;
                            var left = 0;
							console.log(itemTime.timeStart)
                            for (k = 0; k < timeArray.length; k++) {
                                if (itemTime.timeStart.split(" ")[0].replace(/-/g, "") == timeArray[k].timeNum) {
                                    left = k * 145 + 290;
                                }
                            }
                            if (itemTime.ntStatus == '1') {
								var text='';
								var classCss='usedFlag';
								if(itemTime.saleUuid!=null){
									text = '订单UUID是:'+itemTime.saleUuid;
								} else{
									classCss ='sealFlag';
									text ='后台手动封房';
								}
								text +='-'+itemTime.remark;
                                var div = "<div class='used "+classCss+"' style='top:" + top + "px;left:" + left + "px;width:" + width + "px' data-usedtype='" + itemTime.ntStatus + "' data-starttime='" + itemTime.timeStart + "' data-endtime='" + itemTime.timeEnd +"' data-index='"+i+"' data-ntindex='"+j+"' >" + text + "</div>"
                            } 
                            $(".usedBox").append(div);
                        }
                    }

                }
            }
            $(".usedBox").on("click", ".used", function () {
                if (that.monthNum == 1) {
                    return;
                }
                var _this = $(this);
                var usedType = _this.data("usedtype");
				var dataIndex =_this.data("index");
				var ntIndex = _this.data("ntindex");
				that.ntData = that.list[dataIndex].nestTimeInfos[ntIndex];
				if( that.ntData != undefined && that.ntData.saleUuid != null){
					let url = phosts + '/SaleInfo/queryById';
					let data = {
					    saleUuid: that.ntData.saleUuid,
					};
					$.post(url, data, function(res, textStatus, jqXHR) {
						layer.msg(res.message, {time: 1 * 1000});
						if (res.success) {
							that.usedType = usedType;
							that.orderDetail= res.entity;
							$('#myModal').modal();
						}
					}, 'JSON');
				} else {
					let url= phosts + '/nest/openHouse';
					let data = {
						ntUuid:that.ntData.ntUuid,
						nestUuid:that.ntData.nestUuid
					};
					//询问框
					layer.confirm('你确定要解封吗？', {
					  btn: ['确定解封','取消解封操作'] //按钮
					}, function(){
					  $.ajax({
					      contentType: 'application/json',
					      type:'POST',
					      url:url,
					      dataType:'JSON',
					      async:true,
					      data:JSON.stringify(data),
					      success: function (res) {
					      	if(res.success){
					      		layer.msg('解封成功!', { icon: 1,time: 1000});
								setTimeout(function () {
									that.loadData();
									//window.location.reload();
								},1000);
					      	} else {
					      		layer.msg(res.message, { icon: 1,time: 1000});
					  		}
					      },
					      error:function(){
					      	
					      }
					  });
					}, function(){
					  layer.msg('取消解封!', {time: 1000});
					});
				}
            });
        },
        addUsedFlag: function (index, date) {
            let that = this;
            var usedType = 3; //1占用 封房 2正常；
            var roomList = that.list[index].nestTimeInfos;
			var timeNumber = parseInt(date);
			if (roomList.length > 0) {
			    for (j = 0; j < roomList.length; j++) {
			        var itemTime = roomList[j];
			        var startTime = parseInt(itemTime.timeStart.replace(/-/g, "").replace(" 00:00:00",""));
			        var endTime = parseInt(itemTime.timeEnd.replace(/-/g, "").replace(" 00:00:00",""));
			        if (timeNumber >= startTime && timeNumber <= endTime) {
			            usedType = itemTime.ntStatus;
			        }
			    }
			}
            return usedType;
        },
        sealRoomDetail: function () {
            let that = this;
            parent.location.href = "/nest/closeHouseList?monthNum=12&v=" + new Date().getTime();
        },
		orderStatus:function(status){
			switch (status){
				case 1:
					return "待付";
				case 2:
					return "待入住";
				case 3:
					return "待入住";
				case 4:
					return "已完成";
				case 5:
					return "已申请退款";
				case 6:
					return "已审核";
				case 7:
					return "已退款";
				case 8:
					return "待退款（订房重复)";
			}
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
    }
});