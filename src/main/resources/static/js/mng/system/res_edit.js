$(document).ready(function() {
    $("#cancelBtn").click(close);
    $("#pname").click(openTree);

    //监听表单提交
    layui.form.on('submit(submitBtn)', function(data){
        $.post('/sys/res/edit', $("#dataForm").serialize(), function(result) {
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

function openTree() {
    layer.open({
        type: 2,
        title: '选择父级权限',
        fix: false,
        resize: false,
        area: ['300px', '290px'],
        btn: ['确定', '关闭'],
        content: '/sys/res/toTree?id=' + $("#id").val(),
        yes: function(index, layero) {
            window.frames[0].getSelectNode();
        }
    });
}