var table = layui.table;

$(document).ready(function() {

    //初始化数据列表
    load();

    //初始化右边工具栏
    initRightToolbar();
});

function load() {
    table.render({
        id: 'dataTable',
        elem: '#dataTable',
        method: 'post',
        url:'/sys/res/findList',
        response: {
            statusCode: 200
        },
        cols: [[
            {field:'id', title: 'ID', sort: true, hide: true},
            {field:'resName', title: '名称', sort: true},
            {field:'resCode', title: 'CODE', sort: true},
            {field:'url', title: 'URL', sort: true},
            {field:'sort', title: '排序', sort: true},
            {field:'typeStr', title: '类型', sort: true},
            {field:'pname', title: '父级权限', sort: true},
            {field:'createName', title: '创建人', sort: true},
            {field:'createTime', title: '创建时间', sort: true},
            {fixed: 'right', width: 120, align: 'center', unresize: true, toolbar: '#rightToolbar'}
        ]],
        page: true
    });

    $("#searchBtn").click(search);
    $("#addBtn").click(add);
}

function initRightToolbar() {
    table.on('tool(dataTable)', function(obj){
        if(obj.event === 'edit') {
            edit(obj);
        } else if(obj.event === 'del') {
            del(obj);
        }
    });
}

/**
 * 新增按钮
 */
function add() {
    layer.open({
        type: 2,
        title: '新增',
        fix: false,
        resize: false,
        area: ['680px', '400px'],
        btn: ['保存', '关闭'],
        content: '/sys/res/toAdd',
        yes: function(index, layero) {
            $(window.frames[0].document).contents().find("#submitBtn").click();
        }
    });
}

/**
 * 编辑按钮
 * @param obj
 */
function edit(obj) {
    layer.open({
        type: 2,
        title: '编辑',
        fix: false,
        resize: false,
        area: ['680px', '400px'],
        btn: ['保存', '关闭'],
        content: '/sys/res/toEdit/' + obj.data.id,
        yes: function(index, layero) {
            $(window.frames[0].document).contents().find("#submitBtn").click();
        }
    });
}

/**
 * 删除按钮
 * @param obj
 */
function del(obj) {
    layer.confirm('确定删除数据吗？', function(index){
        $.get("/sys/res/del/" + obj.data.id, function(result) {
            if (200 == result.code) {
                layer.close(index);
                table.reload('dataTable');
            } else {
                layer.alert("删除失败");
            }
        });
    });
}

/**
 * 查询按钮
 */
function search() {
    table.reload('dataTable', {
        page: {
            curr: 1
        },
        where: {resName: $("#search_res_name").val(), type: $("#search_res_type").val()}
    });
}