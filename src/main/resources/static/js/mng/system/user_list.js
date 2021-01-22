var table = layui.table;

$(document).ready(function() {

    //初始化数据列表
    load();

    //初始化右边工具栏
    initRightToolbar();

    //监听指定开关
    layui.form.on('switch(freezeSwitch)', function(data){
        var id = data.elem.attributes['dataid'].nodeValue;
        var enable = this.checked ? 0 : 1;
        $.post("/sys/user/freeze", {id: id, enable: enable}, function(result) {
            if (200 == result.code) {
                layer.close(index);
                table.reload('dataTable');
            } else {
                layer.alert("删除失败");
            }
        });
    });
});

function load() {
    table.render({
        id: 'dataTable',
        elem: '#dataTable',
        method: 'post',
        url:'/sys/user/findList',
        response: {
            statusCode: 200
        },
        cols: [[
            {field:'id', title: 'ID', sort: true, hide: true},
            {field:'username', title: '用户名', sort: true},
            {field:'realName', title: '真实姓名', sort: true},
            {field:'birthday', title: '生日', sort: true},
            {field:'sexStr', title: '性别', sort: true},
            {field:'phone', title: '电话', sort: true},
            {field:'createName', title: '创建人', sort: true},
            {field:'createTime', title: '创建时间', sort: true},
            {field:'enable', width: 120, align: 'center', unresize: true, title:'冻结/解冻', templet: '#freezeTpl',},
            {fixed:'right', width: 120, align: 'center', unresize: true, toolbar: '#rightToolbar'}
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
        area: ['680px', '500px'],
        btn: ['保存', '关闭'],
        content: '/sys/user/toAdd',
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
        area: ['680px', '500px'],
        btn: ['保存', '关闭'],
        content: '/sys/user/toEdit/' + obj.data.id,
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
        $.get("/sys/user/del/" + obj.data.id, function(result) {
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
            username: $("#search_user_name").val(),
            realName: $("#search_user_real_name").val(),
            sex: $("#search_user_sex").val(),
            phone: $("#search_user_phone").val(),
            personnelType: $("#search_personnel_type").val(),
            certType: $("#search_cert_type").val(),
            certNo: $("#search_user_cert_no").val(),
            dept: $("#search_user_dept").val()
        }
    });
}