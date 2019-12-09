$(document).ready(function () {

    // jquery
    $(function () {



    })

})
// vue 

new Vue({
    el: '#app',
    data: {
        goodList: [{
                goodId: 1,
                goodName: "薯片",
                goodStatus: 1,
                goodNum:5,
                goodTotal: 23

            },
            {
                goodId: 2,
                goodName: "可乐",
                goodStatus: 2,
                goodNum:5,
                goodTotal: 220
            }
        ],
        checkgoods: [{
                goodId: 3,
                goodName: "方便面",
                goodStatus: 1,
                goodTotal: 300,
                goodNum:1,
                goodPrice: 5
            },
            {
                goodId: 4,
                goodName: "饼干",
                goodStatus: 1,
                goodTotal: 100,
                goodNum:1,
                goodPrice: 6
              

            },
            {
                goodId: 5,
                goodName: "果汁",
                goodStatus: 1,
                goodTotal: 50,
                goodNum:1,
                goodPrice: 10
            }
        ],
        checkedgoods: [],
        modalType: "", //1添加 2编辑
        selectGoosName: "",
        selectGoosToatal: "",
        setGoodNum:"",
        selGoodId:""


    },
    mounted: function () {
        let that = this;
        that.default();

    },

    updated: function () {
        this.$nextTick(function () {
            layui.use(['form'], function () {
                layui.form.render("checkbox");
            });
        });

    },
    watch: {
        setGoodNum(newInputs) {
            if(newInputs>this.selectGoosToatal){
                layer.msg("编辑数量不能大于总量!", {
                    time: 1000
                });
                newInputs ="";
            }
            this.setGoodNum = newInputs;
        }
    },
    
    methods: {
        addGoods: function () {
            let that = this;
            that.modalType = 1;
            $('#myModal').modal();
        },
        closeModel: function () {
            let that = this;
            $('#myModal').modal('hide');
            that.checkedgoods = "";
            $(".checkboxs").each(function () {
                $(this).prop('checked', false);
            });
            that.setGoodNum="";
        },

        default: function () {
            let that = this;
            layui.use('form', function () { //此段代码必不可少
                var form = layui.form;
                form.render();
                var checkedgoods = [];
                form.on('checkbox(checkedgoods)', function (data) {
                    var checkgoods = that.checkgoods;
                    if (this.checked) {
                        checkgoods.map(function (item) {
                            if (item.goodId == data.value) {
                                checkedgoods.push(item)
                            }
                        })
                    } else {
                        checkgoods.map(function (item, index) {
                            if (item.goodId == data.value) {
                                checkedgoods.splice(index, 1)
                            }
                        })
                    }
                    that.checkedgoods = checkedgoods;
                });
                // 分页
                var laypage = layui.laypage;
                laypage.render({
                    elem: 'test1',
                    count: 70,
                    layout: ['prev', 'page', 'next', 'limit', 'refresh', 'skip'],
                    jump: function (obj) {
                        console.log(obj)
                    }
                });

            })

        },
        sureChecked: function () {
            let that = this;
            if (that.checkedgoods.length == 0) {
                layer.msg("未添加商品!", {
                    time: 1000
                });
                return
            }
            that.goodList = that.goodList.concat(that.checkedgoods);
            that.closeModel();
        },
        editFun: function (id, name,total) {
            let that = this;
            that.modalType = 2;
            that.selectGoosName = name;
            that.selectGoosToatal=total;
            that.selGoodId = id;
            $('#myModal').modal();
        },
        sureEdit:function(){
            let that = this;
            if(that.setGoodNum == ""){
                layer.msg("未编辑商品数量!", {
                    time: 1000
                });
                return
            }
          var goodList = that.goodList;
          goodList.map(function(item){
              if(item.goodId == that.selGoodId){
                  item.goodNum = that.setGoodNum;
              }
          })
          that.goodList = goodList;
          that.closeModel();
        }

    }
})