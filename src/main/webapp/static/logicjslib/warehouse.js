$(document).ready(function () {
    // jquery
    $(function () {



    })

})

// vue 
var vm = new Vue({
    el: '#app',
    data: {
        yhName:"",
        unit:"",
        addNum:"",
        residueNum:"",
        selId:"",
        selToatal:"",
        list: [{
            id: "1",
            name:"毛巾",
            unit:"个",
            total: 100,
            residue:20,
        },{
            id: "2",
            name:"牙刷",
            unit:"个",
            total: 100,
            residue:10,
        }]

    },
    mounted: function () {
        let that = this;
        that.default();
    },
    watch: {
        addNum(newInputs) {
            if(newInputs>this.selToatal){
                layer.msg("编辑数量不能大于总量!", {
                    time: 1000
                });
                newInputs ="";
            }
            this.addNum = newInputs;
        }
    },
    methods: {
        suerSearch() {
            let that = this;
            if (that.yhName == "" ) {
                layer.msg("搜索条件不能为空哦！", {
                    time: 1 * 1000
                })
                return
            }
        },
        default: function () {
            let that = this;
            layui.use('form', function () {
                var form = layui.form;
                form.render();
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
        addFun:function(id,num){
            let that = this;
            that.selId = id;
            that.selToatal = num;
            $('#myModal').modal();
        },
        closeModel: function () {
            let that = this;
            $('#myModal').modal('hide');
            that.unit="";
            that.addNum="";
            that.residueNum="";
        },
        deleteFun: function (e) {
            let that = this;
            layer.confirm('是否要删除信息!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
                var list = that.list;
                for(var i = 0; i<list.length;i++){
                    if(list[i].id == e){
                        list.splice(i,1)
                    }
                }
                that.list = list;
                layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                layer.msg("删除成功!", {
                    icon: 1,
                    time: 1000
                });
            });
        },
        sureAdd:function(){
            let that = this;
            if (that.unit == "") {
                layer.msg("请输入易耗品单位!", {
                    time: 1000
                });
                return
            }
            if (that.addNum == "") {
                layer.msg("请输入补充数量!", {
                    time: 1000
                });
                return
            }
            if (that.residueNum == "") {
                layer.msg("请输入剩余数量!", {
                    time: 1000
                });
                return
            }

        }





    }




})