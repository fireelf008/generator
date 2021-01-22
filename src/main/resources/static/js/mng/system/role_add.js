$(document).ready(function() {

    //监听表单提交
    layui.form.on('submit(submitBtn)', function(data){
        $.post('/sys/role/add', $("#dataForm").serialize(), function(result) {
            if (200 == result.code) {
                close();
                parent.layui.table.reload('dataTable');
            } else {
                layer.alert("保存失败");
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