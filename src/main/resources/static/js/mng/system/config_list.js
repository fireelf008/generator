var table = layui.table;
var laydate = layui.laydate;

laydate.render({
    elem: '#endDate'
    ,format: 'yyyy-MM-dd'
});
laydate.render({
    elem: '#search_create_time'
    ,format: 'yyyy-MM-dd'
});
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
        url:'/sys/config/findList',
        response: {
            statusCode: 200
        },
        cols: [[
            {field:'id', title: 'ID', sort: true, hide: true},
            {field:'confName', title: '参数名称', sort: true},
            {field:'confValue', title: '参数值', sort: true},
            {field:'createName', title: '设置者', sort: true},
            {field:'createTime', title: '设置时间', sort: true},
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
        area: ['330px', '330px'],
        btn: ['保存', '关闭'],
        content: '/sys/config/toAdd',
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
        area: ['330px', '260px'],
        btn: ['保存', '关闭'],
        content: '/sys/config/toEdit/' + obj.data.id,
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
        $.get("/sys/config/del/" + obj.data.id, function(result) {
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
        where: {configName: $("#search_config_name").val(), createTime: $("#search_create_time").val()}
    });
}