$(document).ready(function() {
	$(function() {



	})
})
// vue

new Vue({
	el: '#app',
	data: {
		username: "",
		phoneNum: "",
		selCouponType: 1,
		selCouponDate: "",
		selCoupon: 10,
		fullMony: "",
		subMoney: "",
		userUuid: "",
		couponRentType: 0,

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
		isRenderLaypage:false,
        laypage:{},
		currPage: 1,
		limitPage: 10,
		count: 0,
		form: {},
		list: []
	},
	mounted: function() {
		let that = this;
		that.default();

	},
	updated: function() {
		layui.form.render();
	},

	methods: {
		suerSearch() {
			let that = this;
			if (that.username == "" && that.phoneNum == "") {
				layer.msg("搜索条件不能为空哦！", {
					time: 1 * 1000
				})
				return
			}
			that.loadData();
		},
		deleteBtn: function(e) {
			let that = this;
			layer.confirm('是否要删除信息!', {
				btn: ['确定', '取消']
			}, function(index, layero) {
				layer.closeAll('dialog'); // 加入这个信息点击确定 会关闭这个消息框
				layer.msg("删除成功!", {
					icon: 1,
					time: 1000
				});
			});

		},
		giveCoupon: function() {
			let that = this;
			$('#myModal').modal();
		},
		closeModel: function() {
			console.log(123)
			let that = this;
			$('#myModal').modal('hide');
			that.selCouponType = "";
			that.selCouponDate = "";
			that.fullMony = "";
			that.subMoney = "";
			$("#selCouponType").val("");
			$("#selCouponDate").val("");
			$("#selCoupon").val("");
		},
		sureCouponType: function() {
			let that = this;
			if (that.userUuid.replace(',,', '').replace(',,,', '')=='') {
				layer.msg("请勾选用户!", {
					time: 1000
				});
				that.closeModel();
				return
			}
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
			if (that.selCouponType != 2) {
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
			} 
			if (that.selCouponType == 2 && (that.selCoupon == 10)) {
				layer.msg("请选择折扣力度!", {
					time: 1000
				});
				return
			}
			if (that.couponRentType == 0) {
				layer.msg("请选择使用范围!", {
					time: 1000
				});
				return
			}
			switch (that.selCouponDate) {
				case '15天':
					that.selCouponDate = '15';
					break;
				case '1个月':
					that.selCouponDate = '30';
					break;
				case '2个月':
					that.selCouponDate = '60';
					break;
				case '3个月':
					that.selCouponDate = '90';
					break;
				default:
					that.selCouponDate = '15';
					break;
			}
			that.sendCoupon();
		},
		sendCoupon: function() {
			let that = this;
			let url = phosts + '/couponUser/someMessage';
			let couponValue = 0;
			if (that.selCouponType == 2) {
				couponValue = that.selCoupon;
			} else {
				couponValue = that.subMoney;
			}
			let data = {
				couponType: that.selCouponType,
				userUuid: that.userUuid.replace(',,', '').replace(',,,', ''),
				couponSum: that.fullMony,
				couponValue: couponValue,
				couponRentType: that.couponRentType,
				abateDay:that.selCouponDate,
			};
			$.post(url, data, function(data, textStatus, jqXHR) {
				that.closeModel();
				layer.msg(data.message, {
					time: 1000
				});
			}, 'JSON');
		},
		loadData: function() {
			let that = this;
			var url = phosts + '/couponUser/couponSomeAjaxList';
			var data = {
				userRealName: that.username,
				userPhone: that.phoneNum,
				pageNum: that.currPage,
				pageSize: that.limitPage,
			};
			$.post(url, data, function(res, textStatus, jqXHR) {
				if (res.entity.list != null) {						
					that.list = res.entity.list;
				}
				that.count = res.entity.total;
				that.limitPage = res.entity.pageSize;
				if(!that.isRenderLaypage){
					that.laypage.render({
						elem: 'test1',
						count: that.count,
						limit: that.limitPage,
						layout: ['prev', 'page', 'next', 'limit', 'refresh', 'skip'],
						jump: function(obj) {
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
		default: function() {
			let that = this;
			layui.use('form', function() {
				var form = layui.form;
				var $ = layui.$;
				// 监听selecte
				form.on('select(selCouponType)', function(data) {
					that.selCouponType = data.value;
				})
				form.on('select(selCoupon)', function(data) {
					that.selCoupon = data.value;
				})
				form.on('select(selCouponDate)', function(data) {
					that.selCouponDate = data.value;
				})
				form.on('select(couponRentType)', function(data) {
					that.couponRentType = data.value;
				})
				that.form = form;
				// 分页
				that.laypage = layui.laypage;
				// 全选
				form.on('checkbox(c_all)', function(data) {
					var a = data.elem.checked;
					let checkboxList = $(".nameId");
					if (a == true) {
						checkboxList.prop("checked", true);
						checkboxList.each(function(i, e) {
							if (i == 0) {
								that.userUuid += e.value;
							} else {
								that.userUuid += ',' + e.value;
							}
						});
						form.render('checkbox');
					} else {
						checkboxList.prop("checked", false);
						that.userUuid = '';
						form.render('checkbox');
					}
					console.log(that.userUuid);
				})
				// //有一个未选中全选取消选中
				form.on('checkbox(c_one)', function(data) {
					var item = $(".nameId");
					for (var i = 0; i < item.length; i++) {
						if (item[i].checked == false) {
							$("#c_all").prop("checked", false);
							if (that.userUuid.indexOf(data.value) > -1) {
								that.userUuid.replace(data.value, '');
							}
							form.render('checkbox');
							break;
						} else {
							if (that.userUuid.indexOf(data.value) < 0) {
								if (that.userUuid.length > 0) {
									that.userUuid += ',' + data.value;
								} else {
									that.userUuid += data.value;
								}

							}
						}
					}
					// 如果都勾选了 勾上全选
					var all = item.length;
					for (var i = 0; i < item.length; i++) {
						if (item[i].checked == true) {
							all--;
						}
					}
					if (all == 0) {
						$("#c_all").prop("checked", true);
						form.render('checkbox');
					}
					console.log(that.userUuid);
				});
				that.loadData();
			})
		},
	}
})
