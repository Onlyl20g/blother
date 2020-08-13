$(function () {

    validateKickout();
    validateRule();
    if (localStorage.getItem("username") != null && localStorage.getItem("username") != "") {
        $(".uname")[0].value = localStorage.getItem("username");
    }

    if (sessionStorage.getItem('loginName') != null && sessionStorage.getItem("username") != "") {
        isFirstLond(sessionStorage.getItem('loginName'));
    }

});

function changePwd() {
    var msg = "操作成功";
    var loginName = $("#loginName").val();
    var passWord = $("#password").val();
    var result = checkIntensity(passWord);
    if (result >= 2) {
        $.ajax({
            url: "/system/user/resetPwdByLoginName",
            type: "POST",
            data: {"loginName": loginName, "password": passWord},
            dataType: "json",
            success: function (r) {
                if (r.msg == msg) {
                    $.modal.msg("修改成功");
                    setTimeout(function () {
                        // window.location.reload()
                        sessionStorage.setItem('loginName', loginName);
                        sessionStorage.clear();
                        location.href = '/system/login';
                    }, 2000);
                } else {
                    $.modal.msg("修改失败")
                }
            }
        })
    } else {
        $.modal.msg("密码强度低、请确认是否包含数字、字母等")
    }
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

$.validator.setDefaults({
    submitHandler: function () {
        login();
    }
});

function login() {
    var username = $.common.trim($('input[name="username"]').val());
    var password = $.common.trim($('input[name="password"]').val());
    var validateCode = $('input[name="validateCode"]').val();
    var rememberMe = $('input[name="rememberme"]').is(':checked');

    $.modal.loading($('#btnSubmit').data('loading'));
    $.ajax({
        type: 'post',
        url: '/system/login',
        data: {
            'username': username,
            'password': password,
            'validateCode': validateCode,
            'rememberMe': rememberMe
        },
        success: function (r) {
            if (r.code == 0) {
                localStorage.setItem("username", username);//记录下次登陆名
                sessionStorage.setItem('loginName', username);
                isFirstLond(username);
                // $.modal.closeLoading();
                // sessionStorage.setItem('loginName', username);
                // location.href = '/system';
            } else {
                $.modal.closeLoading();
                $('.imgcode').click();
                $('.code').val('');
                $.modal.msg(r.msg);
            }
        }
    });
}


function isFirstLond(username) {
    $.ajax({
        url: "/system/user/findUser?loginName=" + username,
        type: "POST",
        dataType: "json",
        processData: false,
        cache: false,
        success: function (data) {
            /*if (data.data == null) {
                $.modal.msg("用户不存在/密码错误");
                return;
            }*/
            $.modal.closeLoading();
            if (username != data.data.updateBy) {
                $("#loginName").val(username);
                layer.open({
                    type: 1,
                    title: '初次登陆-重设密码',
                    maxmin: true,
                    shadeClose: false, //点击遮罩关闭层
                    area: ['40%', '46%'],
                    closeBtn: 0,
                    content: $("#change_pwd")
                });
            } else {
                // sessionStorage.setItem('loginName', username);
                location.href = '/system';
            }
        }
    })
}

function validateRule() {
    var icon = '<i class="fa fa-times-circle"></i> ';
    $('#signupForm').validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + '请输入您的用户名',
            },
            password: {
                required: icon + '请输入您的密码',
            }
        }
    });

    $("#form-user-resetPwd").validate({
        rules: {
            password: {
                required: true,
                minlength: 8
            },
            confirm_password: {
                required: true,
                minlength: 8,
                equalTo: "#password"
            }
        },
        messages: {
            password: {
                required: icon + "请输入您的密码",
                minlength: icon + "密码必须8个字符以上且由数字+字母或特殊字符组成"
            },
            confirm_password: {
                required: icon + "请再次输入密码",
                minlength: icon + "密码必须8个字符以上",
                equalTo: icon + "两次输入的密码不一致"
            }
        },
        submitHandler: function () {  //通过之后回调
            changePwd();
        }
    });
}

function validateKickout() {
    if (getParam('kickout') == 1) {
        layer.alert('<font color="red">您已在别处登录，请您修改密码或重新登录</font>', {
                icon: 0,
                title: '系统提示'
            },
            function (index) {
                //关闭弹窗
                layer.close(index);
                if (top != self) {
                    top.location = self.location;
                } else {
                    var url = location.search;
                    if (url) {
                        var oldUrl = window.location.href;
                        var newUrl = oldUrl.substring(0, oldUrl.indexOf('?'));
                        self.location = newUrl;
                    }
                }
            });
    }
}

function getParam(paramName) {
    var reg = new RegExp('(^|&)' + paramName + '=([^&]*)(&|$)');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}