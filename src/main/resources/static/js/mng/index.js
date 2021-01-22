$(document).ready(function () {
    getCurrentDate();
    setInterval(function () {
        getCurrentDate();
    }, 1000);
});

/**
 * 获取当前系统时间
 */
function getCurrentDate() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    var week;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + week + " " + fnW(hour) + ":" + fnW(minute) + ":" + fnW(second);
    $("#currentDate").text(time)
}

function fnW(str) {
    var num;
    str >= 10 ? num = str : num = "0" + str;
    return num;
}

function updatePwd() {
    layer.open({
        type: 2,
        title: '修改密码',
        fix: false,
        resize: false,
        area: ['320px', '350px'],
        btn: ['保存', '关闭'],
        content: '/sys/user/toUpdatePwd',
        yes: function(index, layero) {
            $(window.frames[0].document).contents().find("#submitBtn").click();
        }
    });
}

/**
 * 登出
 */
function logout() {
    $.get("/sys/user/logout?time=" + new Date().getTime(), function (result) {
        if (200 == result.code) {
            window.location = "/sys/user/login";
        }
    });
}

/**
 * 打开或切换选项卡
 * @param code
 * @param text
 * @param url
 */
function newTab(code, text, url) {
    var element = layui.element;
    var exist = $("li[lay-id='" + code + "']").length;
    if (0 >= exist) {
        element.tabAdd('maintab', {
            title: text,
            content: '<iframe frameborder="0" width="100%" scrolling="auto" src="' + url + '"></iframe>',
            id: code
        })
    }
    $(".layui-tab-content").find('iframe').each(function () {
        $(this).height($(window).height() - 125);
    });
    element.tabChange('maintab', code);
}