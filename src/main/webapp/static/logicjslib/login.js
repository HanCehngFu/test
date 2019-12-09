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
					that.attr("disabled", false)
				}
			},
		});
	});
});