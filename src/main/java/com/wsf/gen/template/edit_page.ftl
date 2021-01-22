<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<head>
    <meta charset="utf-8">
    <title>编辑</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/js/commons/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/commons/base.css" media="all">
    <link rel="stylesheet" href="/css/mng/${config.modularName}/${config.varEntityName}_edit.css" media="all">
</head>
<body>
<form id="dataForm" name="dataForm" lay-filter="dataForm" class="layui-form layui-form-pane" style="padding: 15px">
    <div class="layui-form-item">
        <#list config.fieldColumnMap?keys as key>
        <div class="layui-inline">
            <label class="layui-form-label">${config.fieldColumnMap[key].comment}</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="${config.fieldColumnMap[key].name}" name="${config.fieldColumnMap[key].name}" autocomplete="off" th:value="${r"${"}${config.varEntityName}.${config.fieldColumnMap[key].name}${r"}"}">
            </div>
        </div>
        </#list>
    </div>
    <button type="button" class="layui-btn" lay-filter="submitBtn" style="display:none;" lay-submit="" id="submitBtn" name="submitBtn">保存</button>
</form>
</body>
</html>
<script charset="utf-8" src="/js/commons/layui/layui.all.js" ></script>
<script charset="utf-8" src="/js/commons/jquery.js"></script>
<script src="/js/mng/${config.modularName}/${config.varEntityName}_edit.js"></script>