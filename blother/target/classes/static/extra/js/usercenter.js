$(function () {
    $.ajax({
        url: "/system/user/getUserInfo",
        type: "post",
        dataType: "json",
        success: function (data) {
            $("#companyName").text(data.corprName);
            $("#companyPerson").text(data.legalPersonName);
            $("#businessLicenseCode").text(data.code);
            $("#manger").text(data.customerManagerName);
        }
    });
    layui.use('layer', function () {
        var $ = layui.jquery,
            layer = layui.layer;
        $("#addBankCard").click(function () {
            $("#bankCard").val("");
            $("#bankName").val("");
            $("#personName").val("");
            $("#passwordConfirm1").val("");
            layer.open({
                type: 1,
                title: '',
                shade: [0.1, '#393D49'],
                maxmin: false, //最大化最小化按钮
                area: ['600px', '350px'],
                content: $("#addWindow"),
                shadeClose: false
            });
        });
    });

    $("#doAdd").click(function () {
        $("#info1").val($("#bankCard").val());
        $("#info2").val($("#personName").val());
        $("#info3").val($("#bankName").val());
        addBankCard();
    });

    var options = {
        url: "/system/bankCardInfo/list",
        showSearch: false,
        showRefresh: false,
        showToggle: false,
        showColumns: false,
        pagination: false,
        //height: "450",
        //pageSize: 7,
        pageSize: 99999,
        sidePagination: "server",
        columns: [
            {
                field: 'bankCard',
                title: '收款帐户帐号',
                align: 'center'
            },
            {
                field: 'owner',
                title: '开户名称',
                align: 'center'
            },
            {
                field: 'bank',
                title: '帐户开户行',
                align: 'center'
            },
            {
                field: 'createTime',
                title: '添加时间',
                align: 'center'
            },
            {
                field: 'id',
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    return '<button class="btn btn-sm btn-default" type="button" onclick = "deleteTemp = this.parentNode.parentNode.firstChild.textContent;confirmWindow();" style="font-size: 0.87rem">删除</button>';
                }
            }]
    };
    $.table.init(options);
});

function confirmWindow() {
    layer.open({
        type: 1,
        title: '',
        shade: [0.8, '#393D49'],
        maxmin: false, //最大化最小化按钮
        area: ['500px', '230px'],
        content: $("#confirmWindow"),
        shadeClose: false
    });
}

function validatePassword() {
    if ($("#oldpassword").val() == "" || $("#oldpassword").val() == null) {
        toastr.error("请输入当前密码");
        return;
    }
    if ($("#password").val() == "" || $("#password").val().length < 6) {
        toastr.error("请输入想要修改的密码,至少6位");
        return;
    }
    if ($("#password").val().length > 16) {
        toastr.error("请输入想要修改的密码,最多16位");
        return;
    }
    if ($("#passwordConfirm").val() == "" || $("#passwordConfirm").val() == null) {
        toastr.error("请确认想要修改的密码");
        return;
    }
    if ($("#passwordConfirm").val() != $("#password").val()) {
        toastr.error("两次输入密码不一样");
        return;
    }
    if (isPasswd($("#password").val())) {
        updatePassword();
    } else {
        toastr.error("请检查密码，密码可以是英文字母与数字");
    }
}

/**
 * 修改密码
 */
function updatePassword() {
    var pass = $("#password").val();
    var username = sessionStorage.getItem("username");
    var oldpassword = $("#oldpassword").val();
    $.ajax({
        url: "updateSupPW",
        type: "post",
        dataType: "json",
        auto: true,
        data: {password: pass, oldPassword: oldpassword},
        success: function (result) {
            if (result == 1) {
                toastr.success("修改成功");
                setTimeout(function () {
                    location.reload();
                }, 3000);
                $('#modify-form').modal('hide');
            } else if (result == 0) {
                toastr.error("原密码错误");
            }
        }
    });
}

var deleteTemp = "";

/**
 * 添加收款账户
 */
function addBankCard() {
    if ($("#bankCard").val() == "" || $("#bankCard").val() == null) {
        toastr.error("请输入收款账户");
        $("#bankCard")[0].focus();
        return;
    }
    if ($("#bankCard").val().length < 5) {
        toastr.error("输入的收款帐户帐号太短");
        $("#bankCard")[0].focus();
        return;
    }
    if ($("#personName").val() == "" || $("#personName").val() == null) {
        toastr.error("请输入开户名称");
        $("#personName")[0].focus();
        return;
    }
    if (chinese($("#personName").val())) {
        toastr.error("请输入中文开户名称");
        $("#personName")[0].focus();
        return;
    }
    if ($("#bankName").val() == "" || $("#bankName").val() == null) {
        toastr.error("请输入帐户开户行");
        $("#bankName")[0].focus();
        return;
    }
    if (chinese($("#bankName").val())) {
        toastr.error("请输入中文的帐户开户行");
        $("#bankName")[0].focus();
        return;
    }
    // $("#bankInfo")[0].innerText("账户：" + $("#bankCard").val());
    // $("#bankInfo")[0].innerText("开户行：" + $("#bankName").val());
    // $("#bankInfo")[0].innerText("持卡人：" + $("#personName").val());
    layer.open({
        type: 1,
        title: '',
        shade: [0.1, '#393D49'],
        maxmin: false, //最大化最小化按钮
        area: ['600px', '350px'],
        content: $("#passwordInput"),
        shadeClose: false
    });
}

function sendReport() {
    //if (bank.checked({cardNo: $("#bankCard").val()}).message != "error") {  //银行卡校验
    $.ajax({
        url: "/system/bankCardInfo/add",
        type: "post",
        dataType: "json",
        auto: true,
        data: {
            bankCard: $("#bankCard").val(),
            bank: $("#bankName").val(),
            owner: $("#personName").val(),
            str5: $("#passwordConfirm1").val()
        },
        success: function (result) {
            if (result.code == "0") {
                layer.closeAll();
                toastr.success("添加成功");
                setTimeout(function () {
                    location.reload()
                }, 1500);
                $("#bankCard").val("");
                $("#bankName").val("");
                $("#personName").val("");
                $("#passwordConfirm1").val("");
            } else if (result.code == "500") {
                toastr.error("密码错误");
            } else if (result.code == "301") {
                toastr.error(result.msg);
            }

        }
    });
}

/**
 * 修改收款帐户状态为删除
 */
function deleteBankCard() {
    if ($("#loginPassword").val() != "") {
        $.ajax({
            url: "/system/bankCardInfo/deleteBankCard",
            type: "post",
            dataType: "json",
            auto: true,
            data: {bankCard: deleteTemp},
            success: function (result) {
                layer.closeAll();
                if (result.code == "0") {
                    toastr.success("删除成功");
                    setTimeout(function () {
                        location.reload()
                    }, 1000);
                } else if (result.code == "301") {
                    toastr.error(result.msg);
                }
            }
        });
    } else {
        toastr.error("请输入密码");
    }
}

function isPasswd(s) {
    var patrn = /^(\w){8,18}$/;
    if (!patrn.exec(s)) {
        return false;
    }
    return true;
}

function chinese(string) {
    var pattern = /^[\u4E00-\u9FA5]{1,50}$/;
    if (pattern.test(string)) {　　//用原生JS的test()函数来匹配传入的值，返回布尔值。
        return false;
    } else {
        return true;
    }
}