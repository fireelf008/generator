$(document).ready(function() {
    //自定义校验规则
    layui.form.verify({
        pwdRequired: function(value){
            var pwd = $("#newPwd").val();
            var pwd2 = $("#newPwd2").val();
            if (pwd.length < 6) {
                return "密码应大于6位";
            }
            if (null != pwd && null != pwd2 && "" != pwd && "" != pwd2 && pwd != pwd2) {
                return "两次输入的密码不同";
            }
        },
    });

    //监听表单提交
    layui.form.on('submit(submitBtn)', function(data){
        $.post('/sys/user/updatePwd', $("#dataForm").serialize(), function(result) {
            if (200 == result.code) {
                layer.alert("修改成功", function(index) {
                    close();
                });
            } else {
                layer.alert(result.msg);
            }
        });
        return false;
    });
});

/**
 * 关闭当前窗口
 */
function close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}