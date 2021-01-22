/**
 * 登录
 */
function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    $.post("/sys/user/loginVali", {username: username, pwd: password}, function(result) {
        if (200 == result.code) {
            window.location = "/sys/user/index";
        } else {
            alert(result.msg);
        }
    });
}