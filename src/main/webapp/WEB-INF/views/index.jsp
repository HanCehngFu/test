<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${title}-后台管理</title>
<meta charset="utf-8">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 8]>
<meta http-equiv="refresh" content="0;ie.html" />
<![endif]-->
<link rel="shortcut icon" href="favicon.ico">
<link href="${ctx}/static/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
<link href="${ctx}/static/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/static/css/animate.min.css" rel="stylesheet">
<link href="${ctx}/static/css/style.min.css?v=4.0.0" rel="stylesheet">
<script src="${ctx}/static/js/babel/browser.js"></script>
<script src="${ctx}/static/js/babel/browser-polyfill.js"></script>
<script type="text/javascript" src="${ctx}/static/js/vue/vue.min.js"></script>
<style>
  .nav li .iconfont {
            font-size: 20px;
        }
        
        .active_color {
            color: #FF4369;
        }
        
        .nav>li>a:focus,
        .nav>li>a:hover {
            text-decoration: none;
            background-color: unset;
        }
        
        .navbar-default .nav>li>a:focus,
        .navbar-default .nav>li>a:hover {
            background: unset;
            color: #fff;
        }
        
        .nav>li>a>img {
            width: 16px;
            height: 16px;
            margin-right: 8px;
        }
        
        .li_active {
            background: rgba(255, 67, 105, 0.05);
            position: relative;
        }
        
        .li_active::before {
            content: '';
            top: 0;
            bottom: 0;
            position: absolute;
            width: 4px;
            background-color: #FF4369;
            left: 0;
        }
        
        .body.mini-navbar .nav-header {
            background-color: unset;
        }
</style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<%@ include file="/WEB-INF/views/common/left.jsp"%>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row J_mainContent" id="content-main">
				<c:choose>
				   <c:when test="${nestCount >0 }">
					<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="/home" frameborder="0" data-id="n_home.html" seamless></iframe>
				   </c:when>
				   <c:otherwise>
				   	<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="/nest/index" frameborder="0" data-id="n_home.html" seamless></iframe>
				   </c:otherwise>
				</c:choose>
			</div>
		</div>
		<!--右侧部分结束-->
	</div>
	<script src="${ctx}/static/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctx}/static/js/bootstrap.min.js?v=3.3.5"></script>
	<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${ctx}/static/js/plugins/layer/layer.min.js"></script>
	<script src="${ctx}/static/js/hplus.min.js?v=4.0.0"></script>
	<script src="${ctx}/static/js/contabs.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    <script>
        var isFirstUpDate = false; //是否第一次上传房间
        function changeTab(data) {
            window.vm._data.currentIndex = data;
        }
        $(document).ready(function() {
            // jquery
            $(function() {
                if (isFirstUpDate) {
                    window.vm._data.currentIndex = 5;
                    $(".J_iframe").attr("src", "./page/firstUpData/step_one/step_one.html")
                }
                $(".sure_btn").click(function() {
                    $('#myModal').modal("hide");
                })
                $(".logOut").click(function() {
                    layer.confirm('是否退出登录!', {
                        btn: ['确定', '取消']
                    }, function(index, layero) {
                        window.location.href = "page/login/login.html"
                        layer.closeAll('dialog'); //加入这个信息点击确定 会关闭这个消息框
                    });
                })
                $(".menuLi a").click(function() {
                    if (isFirstUpDate) {
                        $('#myModal').modal();
                        return
                    }
                    window.vm._data.currentIndex = $(this).parent().data("index");

                });
                	var changeMob = function(){
                	if ($(document).width() < 1000) {
                	$("body").addClass("mini-navbar")
                	}else{
                	$("body").removeClass("mini-navbar") 
                	}
                	}
                	changeMob();
                	$(window).resize(function () {
                	changeMob();
                	});


               

            })

        })

        // //vue
        var vm = new Vue({
            el: '#wrapper',
            data: {
                currentIndex: 1,
            },
            mounted: function() {
                let that = this;
            },

            methods: {
            	changeTab:function(e){
            		let that = this;
            		that.currentIndex = e;
            	}

            }




        })
    </script>
</body>
</html>
