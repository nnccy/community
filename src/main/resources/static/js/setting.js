$(function(){
    $("#uploadForm").submit(upload);
});

function upload() {
    $.ajax({ //.post是简化方式，现在不是简化
        url: "http://upload-z2.qiniup.com",
        method: "post",
        processData: false,  //上传文件不要把文件转化为字符串
        contentType: false,  //不要设置上传类型
        data: new FormData($("#uploadForm")[0]),  //得到一个dom
        success: function(data) {   //响应信息
            if(data && data.code == 0) {
                // 更新头像访问路径
                $.post(
                    CONTEXT_PATH + "/user/header/url",
                    {"fileName":$("input[name='key']").val()},
                    function(data) {
                        data = $.parseJSON(data);
                        if(data.code == 0) {
                            window.location.reload();
                        } else {
                            alert(data.msg);
                        }
                    }
                );
            } else {
                alert("上传失败!");
            }
        }
    });
    return false;
}