$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");   //发布框隐藏


	// 发送ajax之前，将csrf设置到请求消息头中
	// var token = $("meta[name='_csrf']").attr("content");
	// var header = $("meta[name='_csrf_header']").attr("content");
	// $(document).ajaxSend(function (e,xhr,options) {
	// 	xhr.setRequestHeader(header,token);
	// });
	//发型异步请求
	//获取标题和内容
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	//发型异步请求
	$.post(
		CONTEXT_PATH + "/discuss/add",
		{"title":title,"content":content},
		function (data) {
			data = $.parseJSON(data);
			//在提示框中显示返回消息
			$("#hintBody").text(data.msg);
			$("#hintModal").modal("show");		//提示框出现
			setTimeout(function(){
				$("#hintModal").modal("hide");	//过两秒提示框隐藏
				// 刷新页面
				if(data.code == 0){
					window.location.reload();
				}
			}, 2000);
		}
	);
}