<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${title}-登录</title>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body class="login_page">
	<div class="wrapper wrapper-content" id="app">
		<div class="login_layout">
			<div class="login_content">
				
				<div class="layui-form">
					<div class="layui-form-item">
						<input id="userLoginId" type="text" name="" autofocus="autofocus" autocomplete="off" class="layui-input" placeholder="输入账号">
					</div>
					<div class="layui-form-item">
						<input id="userPwd" type="password" name="" autocomplete="off" class="layui-input" placeholder="输入密码">
					</div>
					<button id="toLogin" class="login_btn">登录</button>
					<p id="error" class="errow_mas" hidden="">密码错误！</p>
					<p class="forget_btn" data-toggle="modal" data-target="#myModal">忘记密码</p>
				</div>
			</div>
		</div>
		<div class="modal fade coupon_modal" tabindex="-1" role="dialog" id="myModal">
			<div class="modal-dialog modal-sm" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<!-- <h4 class="modal-title">忘记密码</h4> -->
					</div>
					<div class="modal-body " style="margin-bottom: 50px;">
						<p style="margin-bottom: 10px;">
							请联系客服 <i style='color: #7C50F2'>15220098981</i>
						</p>
						</p>
						<p>核实身份后即可重置密码</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn_default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn_primary sure_btn" data-dismiss="modal">确定</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/views/common/foot.jsp"%>
<script type="text/javascript">
$(function() {
	$("#toLogin").click(function() {
		var userLoginId = $("#userLoginId").val();
		var userPwd = $("#userPwd").val();

		if (userLoginId == null || userLoginId == '') {
			alert("请输入账号");
			return;
		}
		if (userPwd == null || userPwd == '') {
			alert("请输入密码");
			return;
		}
		var that = $(this);
		$(this).attr("disabled", true)
		var params = {};
		params.userLoginId = userLoginId;
		params.userPwd = userPwd;
		params.rememberMe = true;
		$.ajax({
			type : "POST",
			url : "${ctx }/ajaxLogin",
			data : params,
			dataType : "json",
			success : function(res) {
				if (res.code == 200) {
					window.location.reload();
				} else {
					alert(res.message);
					$('#error').show();
					that.attr("disabled", false)
				}
			},
		});
	});
});
</script>

</html>