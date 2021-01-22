$(document).ready(function() {
    var setting = {
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        data: {
            key: {
                name: 'resName',
                url: 'xurl'
            },
            simpleData: {
                enable: true,
                idKey: 'id',
                pIdKey: 'pid',
                rootPId: 0
            },
        }
    };

    $.get('/sys/res/findAll?id=' + $("#id").val(), function(result) {
        var tree = $.fn.zTree.init($("#tree"), setting, result.data);
        tree.expandAll(true);
    });
});

/**
 * 关闭当前窗口
 */
function close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

/**
 * 获取选中的节点并给父页面赋值
 */
function getSelectNode() {
    var tree = $.fn.zTree.getZTreeObj("tree");
    var node = tree.getSelectedNodes();
    if (0 != node.length) {
        window.parent.$("#pid").val(node[0].id);
        window.parent.$("#pcode").val(node[0].resCode);
        window.parent.$("#pname").val(node[0].resName);

        close();
    }
}