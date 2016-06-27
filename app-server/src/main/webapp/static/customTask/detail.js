function sendMessage() {
	var content = $("#message").val();
	if (content.length < 1) {
		alert("回复内容不能为空!!");
		return;
	}else if(content.length>1500){
		alert("回复内容过长!!");
		return;
	}
	var message = {
		"customtaskId" : taskId,
		"salesmanId" : salesmanId,
		"content" : content,
		"roletype" : 1
	};
	$.ajax({
		url : '/v1/customTask/message',
		type : 'post',
		dataType : "json",
		data : JSON.stringify(message),
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(data) {
			alert("已成功回复");
			location.reload();
		},
		error : function() {
			alert("系统异常，请稍后重试或联系管理员！");
		}
	});
}