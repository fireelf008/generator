<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/js/commons/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/commons/base.css" media="all">
    <link rel="stylesheet" href="/css/mng/${config.modularName}/${config.varEntityName}_list.css" media="all">
</head>
<body>
<form class="layui-form layui-form-pane">
    <div class="layui-form-item">
        <#list config.fieldColumnMap?keys as key>
        <div class="layui-inline">
            <label class="layui-form-label">${config.fieldColumnMap[key].comment}</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="search_${config.fieldColumnMap[key].name}" name="search_${config.fieldColumnMap[key].name}" autocomplete="off">
            </div>
        </div>
        </#list>

        <div class="layui-inline">
            <button type="button" class="layui-btn" id="searchBtn">查询</button>
            <button type="reset" class="layui-btn" id="resetBtn">重置</button>
            <button type="button" class="layui-btn" id="addBtn">新增</button>
        </div>
    </div>
</form>

<table class="layui-hide" id="dataTable" lay-filter="dataTable"></table>

<script type="text/html" id="rightToolbar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

</body>
</html>
<script charset="utf-8" src="/js/commons/layui/layui.all.js" ></script>
<script charset="utf-8" src="/js/commons/jquery.js"></script>
<script src="/js/mng/${config.modularName}/${config.varEntityName}_list.js"></script>