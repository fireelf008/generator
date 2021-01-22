$(document).ready(function() {
    //初始化时间控件
    layui.laydate.render({
        elem: '#birthday',
        trigger: 'click'
    });
    layui.laydate.render({
        elem: '#workDate',
        type: 'year',
        trigger: 'click'
    });
    layui.laydate.render({
        elem: '#entryDate',
        trigger: 'click'
    });

    //自定义校验规则
    layui.form.verify({
        pwdRequired: function(value){
            var pwd = $("#password").val();
            var pwd2 = $("#password2").val();
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
        $.post('/sys/user/add', $("#dataForm").serialize(), function(result) {
            if (200 == result.code) {
                close();
                parent.layui.table.reload('dataTable');
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