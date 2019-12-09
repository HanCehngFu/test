$(document).ready(function () {
    // jquery
    $(function () {


    })
    // vue 
    new Vue({
        el: '#app',
        data: {
            
            btnType: 1,
            password: "",
            saleUUID:'',
            modtype:1,
            saleTypeContinue:1,
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
    		list: [],
    		order:"",
    		saleUuid:"",
    		saleInfo:{},

        },
        mounted: function () {
            let that = this;
            if(getUrlParam("saleUuid")){
            	that.saleUuid = getUrlParam("saleUuid");
            }
            //that.getOrderDetailData();
 
            
        },
        methods: {
          closeDetail:function(){
              let that = this;
              parent.layer.close();
              window.history.back(-1);
          }
        }

    })
})
