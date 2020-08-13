// /*点击验证图片刷新*/
// $(document).ready(function () {
//     $('.imgcode').click(function () {
//         var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
//         $(".imgcode").attr("src", url);
//     });
//     control1login();
// });

$(function () {
    if (top != self) {
        if (top.toUrl != "") {
            window.top.location.href = top.toUrl;
        } else {
            window.top.location.href = "/logout";
        }
    }
    /**
     * 修改密码验证
     */
    // $("#confir-btn").click(function () {
    //     checkForm();
    // });
    if (sessionStorage.getItem("username") != null) {
        if (sessionStorage.getItem("username").length > 0) {
            $("#pwdModel").show();
        }
    }

    // $("#pwdModel").show();
    control1login();

    /*验证表单*/
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#login-form").validate({
        rules: {
            password: {
                required: true
            },
            username: {
                required: true
            },
        },
        messages: {
            password: {
                required: icon + "请输入您的密码"
            },
            username: {
                required: icon + "请输入您的用户名"
            },
        },
        onsubmit: true,// 是否在提交时验证
        onfocusout: false,// 是否在获取焦点时验证
        onkeyup: false,// 是否在敲击键盘时验证
        submitHandler: function (form) {  //通过之后回调
            loginUser();
        },
        invalidHandler: function (form, validator) {  //不通过回调
            return false;
        }
    });
    /**
     * 退出登陆
     */
    $("#logout").click(function () {
        $.modal.confirm("确认要退出吗？", function () {
            sessionStorage.clear();//清除浏览器缓存
            document.getElementById("logoutservice").click();//清除服务器缓存
        })
    });
});

/**
 * 验证密码
 * @returns
 */
function checkForm() {
    if ($("#oldPas").val() == "") {
        toastr.error("您还未输入旧密码");
        return;
    }
    if ($("#newPas").val() == "") {
        toastr.error("您还未输入密码");
        return;
    }
    if ($("#newPas").val().length < 8) {
        toastr.error("您输的密码位数小于8");
        return;
    }
    if ($("#newPas").val().length > 32) {
        toastr.error("您输的密码位数大于32");
        return;
    }
    if ($("#confir-pas").val() == "") {
        toastr.error("您还未输入确认密码");
        return;
    }
    if ($("#confir-pas").val() != $("#newPas").val()) {
        toastr.error("两次输入密码不符");
        return;
    }
    if ($("#oldPas").val() == $("#newPas").val()) {
        toastr.error("新密码与旧密码相同");
        return;
    }
    if (checkIntensity($("#newPas").val()) < 2) {
        toastr.error("密码强度低、请确认是否包含数字、字母等");
        return;
    }
    updatePW();
}

/**
 * 修改密码
 */
function updatePW() {
    var pass = $("#newPas").val();
    // var username = sessionStorage.getItem("username");
    var oldpassword = $("#oldPas").val();
    $.ajax({
        url: "updateSupPW",
        type: "post",
        dataType: "json",
        data: {password: pass, oldPassword: oldpassword},
        success: function (r) {
            if (r == 1) {
                toastr.success("修改成功");
                // sessionStorage.clear();
              /*  setTimeout(function () {
                    document.getElementById("logoutservice").click();//清除服务器缓存
                }, 2000);*/
                $('#modify-form').modal('hide');
                $('.modal-backdrop').remove();
            } else if (r == 0) {
                toastr.error("原密码错误");
            }
        }
    });
}

/**
 *  判断用户是否登录
 */
function control1login() {
    $.ajax({
        url: '/system/user/getUserInfo',
        type: 'post',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.code != 1) {
                sessionStorage.setItem("username", data.code);
                sessionStorage.setItem("loginName", data.code);
                sessionStorage.setItem("bil", data.bil);//用户名称存储本地
                sessionStorage.setItem("name", data.companyName);
                sessionStorage.setItem("personName", data.legalPersonName);
                sessionStorage.setItem("manger", data.customerManagerName);
            }
        }
    });
    var username = sessionStorage.getItem("username");
    if (username != null && username != "") {
        $.ajax({
            url: 'isSuplierLogin',
            type: 'post',
            dataType: 'json',
            success: function (r) {
                if (r == 1) {
                    $("#bus-hand").show();
                    findUser();
                    $("#loginForm").hide();
                    isFirstLogin();
                } else {
                    $("#username").val(localStorage.getItem("loginName"));
                    $("#userForm").hide();
                    $("#loginForm").show();
                    $("#bus-hand").hide();
                }
            }
        })
        /*   if (username != null) {
         $("#bus-hand").show();
         findUser(username);
         $("#loginForm").hide();
         isFirstLogin();
         } */
    } else {
        $("#username").val(localStorage.getItem("loginName"));
        $("#userForm").hide();
        $("#loginForm").show();
        $("#bus-hand").hide();
    }
}

/**
 * 登录用户
 */
var userUnable = "用户已封禁，请联系管理员";
var userUnableReplace = "账号未激活,请联系相关客户经理";
var userError = "用户不存在/密码错误";
var userErrorReplace = "账号或密码有误,请检查后重试";

function loginUser() {
    var param = $("#login-form").serialize();
    //param = param + "&rememberMe=true";
    $.ajax({
        url: "/login",
        type: "post",
        dataType: "json",
        data: param,
        success: function (r) {
            if (r.code == 0) {
                $("#userForm").show();
                var username = $("#username").val();
                var password = $("#password").val();
                sessionStorage.setItem('username', username);
                localStorage.setItem("loginName", username);
                control1login();
                $.ajax({
                    url: "checkLoginNameFirst",
                    type: "post",
                    dataType: "json",
                    data: {"loginName": username},
                    success: function (r) {
                        if (r == 0) {
                            $('#modify-form').modal('show');
                            $("#pwdModel").show();
                        }
                    }
                });
            } else {
                if (r.msg == userUnable) {
                    r.msg = userUnableReplace;
                } else if (r.msg == userError) {
                    r.msg = userErrorReplace;
                }
                $.modal.closeLoading();
                $('.imgcode').click();
                $(".code").val("");
                $.modal.msg(r.msg);
            }
            $("#pwdModel").show();
        }
    });
}

/**
 * 查询用户id
 */

/*function findUserId(username) {
 $.ajax({
 url: "findUserId",
 type: "post",
 dataType: "json",
 data: {"loginName": username},
 success: function (r) {
 sessionStorage.setItem("userid", r);//用户名称存储本地
 }
 });
 }*/

/**
 * 查询用户信息
 * @param username
 */
function findUser() {
    $.ajax({
        url: "findUser",
        type: "post",
        dataType: "json",
        // data: {"loginName": username},
        success: function (r) {
            sessionStorage.setItem("bil", r.supplier.billId);//用户名称存储本地
            sessionStorage.setItem("name", r.supplier.corprName);
            sessionStorage.setItem("personName", r.supplier.legalPersonName);
            sessionStorage.setItem("manger", r.supplier.customerManagerName);
            $("#loginName").html("<b style='color: #444d5a;'>" + r.supplier.businessLicenseCode) + "</b>";
            $("#corprName").html("<b class='ctr_row' style='color: #444d5a;font-size: 16px;'>" + r.supplier.corprName) + "</b>";
            $("#userForm").removeClass("hidden-bus").addClass("show-bus");
        }
    });
}

/**
 * 检测密码强度
 */
function checkIntensity(pwd) {
    var m = 0;
    var Modes = 0;
    for (i = 0; i < pwd.length; i++) {
        var charType = 0;
        var t = pwd.charCodeAt(i); //得出Unicode 编码编码，值越大，密码就越复杂
        if (t >= 48 && t <= 57) {
            charType = 1;
        } else if (t >= 65 && t <= 90) {
            charType = 2;
        } else if (t >= 97 && t <= 122) {
            charType = 4;
        } else {
            charType = 4;
        }
        Modes |= charType;
    }
    for (i = 0; i < 4; i++) {
        if (Modes & 1) m++;
        Modes >>>= 1;
    }
    if (pwd.length <= 4) {
        m = 1;
    }
    return m;
}

/**
 * 验证第一次登录
 */
function isFirstLogin() {
    $.ajax({
        url: "checkLoginNameFirst",
        type: "post",
        dataType: "json",
        data: {},
        success: function (result) {
            if (result == 0) {
                $('#textPos').hide();
                $('#modify-form').modal('show');
            }
        }
    });
}