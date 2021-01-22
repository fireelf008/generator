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
        url:'/test/test/findList',
        response: {
            statusCode: 200
        },
        cols: [[
            {field:'id', title: 'id', sort: true, hide: false},
            {field:'username', title: '用户名', sort: true, hide: false},
            {field:'password', title: '密码', sort: true, hide: false},
            {field:'salt', title: '密码盐', sort: true, hide: false},
            {field:'realName', title: '真实姓名', sort: true, hide: false},
            {field:'birthday', title: '生日', sort: true, hide: false},
            {field:'sex', title: '性别，0男，1女', sort: true, hide: false},
            {field:'regTime', title: '注册时间', sort: true, hide: false},
            {field:'regIp', title: '注册ip', sort: true, hide: false},
            {field:'phone', title: '电话', sort: true, hide: false},
            {field:'lastTime', title: '最后登录时间', sort: true, hide: false},
            {field:'lastIp', title: '最后登录ip', sort: true, hide: false},
            {field:'admin', title: '是否是超级管理员，0不是1是', sort: true, hide: false},
            {field:'remark', title: '备注', sort: true, hide: false},
            {field:'createName', title: '创建人', sort: true, hide: false},
            {field:'createTime', title: '创建时间', sort: true, hide: false},
            {field:'updateName', title: '修改人', sort: true, hide: false},
            {field:'updateTime', title: '修改时间', sort: true, hide: false},
            {field:'enable', title: '数据是否可用，0不可用，1可用', sort: true, hide: false},
            {field:'version', title: '版本号', sort: true, hide: false},
            {fixed: 'right', width: 190, align: 'center', unresize: true, toolbar: '#rightToolbar'}
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
        area: ['695px', '400px'],
        btn: ['保存', '关闭'],
        content: '/test/test/toAdd',
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
        area: ['695px', '400px'],
        btn: ['保存', '关闭'],
        content: '/test/test/toEdit/' + obj.data.id,
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
        $.get("/test/test/del/" + obj.data.id, function(result) {
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
        where: {
            id: $("#search_id").val(),
            username: $("#search_username").val(),
            password: $("#search_password").val(),
            salt: $("#search_salt").val(),
            realName: $("#search_realName").val(),
            birthday: $("#search_birthday").val(),
            sex: $("#search_sex").val(),
            regTime: $("#search_regTime").val(),
            regIp: $("#search_regIp").val(),
            phone: $("#search_phone").val(),
            lastTime: $("#search_lastTime").val(),
            lastIp: $("#search_lastIp").val(),
            admin: $("#search_admin").val(),
            remark: $("#search_remark").val(),
            createName: $("#search_createName").val(),
            createTime: $("#search_createTime").val(),
            updateName: $("#search_updateName").val(),
            updateTime: $("#search_updateTime").val(),
            enable: $("#search_enable").val(),
            version: $("#search_version").val()
        }
    });
}