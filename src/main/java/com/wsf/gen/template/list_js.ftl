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
        url:'/${config.modularName}/${config.varEntityName}/findList',
        response: {
            statusCode: 200
        },
        cols: [[
            <#list config.fieldColumnMap?keys as key>
            {field:'${config.fieldColumnMap[key].name}', title: '${config.fieldColumnMap[key].comment}', sort: true, hide: false},
            </#list>
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
        content: '/${config.modularName}/${config.varEntityName}/toAdd',
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
        content: '/${config.modularName}/${config.varEntityName}/toEdit/' + obj.data.id,
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
        $.get("/${config.modularName}/${config.varEntityName}/del/" + obj.data.id, function(result) {
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
            <#list config.fieldColumnMap?keys as key>
            ${config.fieldColumnMap[key].name}: $("#search_${config.fieldColumnMap[key].name}").val()<#if key_has_next>,</#if>
            </#list>
        }
    });
}