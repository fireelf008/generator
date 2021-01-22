$(document).ready(function() {
    var setting = {
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        check: {
            enable: true
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

    $.get('/sys/role/findAll?id=' + $("#id").val() + "&time=" + new Date().getTime(), function(result) {
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
    var node = tree.getCheckedNodes(true);
    var resIds = "";
    for (i in node) {
        resIds += node[i].id + "_"
    }

    $.post('/sys/role/res', {roleId: $("#id").val(), resIds: resIds}, function(result) {
        if (200 == result.code) {
            close();
        } else {
            layer.alert("保存失败");
        }
    });
}