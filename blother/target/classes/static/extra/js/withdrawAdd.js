var element = "";
var upload = "";
$(function () {
    layui.use(['upload', 'element'], function () {
        var $ = layui.jquery;
        upload = layui.upload;
        element = layui.element;
        //提款申请书
        var withdrawApp = upload.render({
            elem: '#withdrawAppPathUpload',
            url: '/system/file/uploadOther',
            exts: 'doc|docx|wps|pdf|jpg|jpeg|png|gif|bmp|zip|rar|7z',
            multiple: true,//是否可以多选
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('withdrawAppPath', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    if ($("#withdrawAppPath").val() == "") {
                        $("#withdrawAppPath").val(file.name);
                    } else {
                        $("#withdrawAppPath").val($("#withdrawAppPath").val() + "," + file.name);
                    }
                    if (isImg(MySubstr(file.name))) {
                        $('#withdrawAppPathView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg1"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                    } else {
                        $('#withdrawAppPathView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg1">' + file.name + '</div>')
                    }
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    if ($("#file1").val() == "") {
                        $("#file1").val(data.path + ":" + data.size + ":" + data.name);
                    } else {
                        $("#file1").val($("#file1").val() + "," + data.path + ":" + data.size + ":" + data.name);
                    }
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeWithdrawAppPath();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeWithdrawAppPath();
            }
        });
        //其他资料
        var fundamentalState = upload.render({
            elem: '#additionFilePathUpload',
            url: '/system/file/uploadOther',
            exts: 'doc|docx|wps|pdf|jpg|jpeg|png|gif|bmp|zip|rar|7z',
            multiple: true,//是否可以多选
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('otherForm', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    if ($("#additionFilePath").val() == "") {
                        $("#additionFilePath").val(file.name);
                    } else {
                        $("#additionFilePath").val($("#additionFilePath").val() + "," + file.name);
                    }
                    if (isImg(MySubstr(file.name))) {
                        $('#additionFilePathView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg2"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                    } else {
                        $('#additionFilePathView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg2">' + file.name + '</div>')
                    }
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    if ($("#file2").val() == "") {
                        $("#file2").val(data.path + ":" + data.size + ":" + data.name);
                    } else {
                        $("#file2").val($("#file2").val() + "," + data.path + ":" + data.size + ":" + data.name);
                    }
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeAdditionFilePath();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeAdditionFilePath();
            }
        });
    });
    findBankCord();
    $("#amount").val(formatMoney(parent.currentAmount,2));
    $("#amountUpDiv").css("visibility","visible");
    $("#amountUp").text("大写: " + convertCurrency(parent.currentAmount));
    $("#maxAppCreditAmount").text(outputmoney(parent.currentAmount));
    $("#commit").click(function () {
        validate();
    });
    $("#creditBillId").val(parent.creditBillId);
    $("#sellerCorprName").val(sessionStorage.getItem("name"));

    $("#search-btn").focus(function () {
        var value = this.value;
        searchBuyer(value);
    });

    $("#search-btn").click(function () {
        var value = $("#search").val();
        searchBuyer(value);
    });
    $("#search-btn").blur(function () {
        var value = this.value;
        searchBuyer(value);
    });

    $("#search-btn").keyup(function () {
        var value = this.value;
        searchBuyer(value);
    });
    searchBuyer('');

    $("#doAdd").click(function () {
        $("#info1").val($("#bankCard").val());
        $("#info2").val($("#personName").val());
        $("#info3").val($("#bankName").val());
        addBankCard();
    });
});

function searchBuyer(value) {
    $.ajax({
        url: "/system/buyer/list",
        type: "POST",
        success: function (data) {
            $("#buyerCorprName")[0].innerHTML = '';
            $("#buyerCorprName").append('<option>请选择</option>');
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].buyerName.indexOf(value) >= 0) {
                    $("#buyerCorprName").append('<option id = ' + data.rows[i].buyerId + '>' + data.rows[i].buyerName + '</option>');
                }
            }
        },
        error: function () {
            toastr.error("error");
        }
    });
}

/**
 * 显示添加汇款账号框
 */
function showAddBankCard() {
    $("#bankCard").val("");
    $("#personName").val("");
    $("#bankName").val("");
    layer.open({
        type: 1,
        title: '',
        maxmin: false,
        shadeClose: false, //点击遮罩关闭层
        area: ['60%', '66%'],
        content: $("#addWindow")
    });
}


//初始化文件上传框方法
function initFileinput(id, tag, url, msg) {
    $("#" + id).fileinput({
        'theme': 'explorer-fas',
        'uploadUrl': url,
        contentType: false,
        dropZoneTitle: msg,
        autoReplace: true,//自动替换之前选的
        overwriteInitial: false,
        initialPreviewAsData: false,
        allowedFileExtensions: ['doc', 'docx', 'wps', 'image', 'jpg', 'jpeg', 'png', 'gif', 'txt', 'pdf', 'xlsx', 'xls'],
        layoutTemplates: {
            actionDelete: '',    // 预览区域的删除按钮
            actionUpload: '', // 预览区域的上传按钮
            actions: '' // 预览区域的所有按钮
        }
    }).on("fileuploaded", function (event, data, previewId, index) {
        //多文件的url以,分割
        if ($("#" + tag).val() == "") {
            $("#" + tag).val(data.response.path);
        } else {
            $("#" + tag).val($("#" + tag).val() + "," + data.response.path);
        }
    }).on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    }).on('filesuccessremove', function (event, id) {

    });
}

function initFileinputDoc(id, tag, url, msg) {
    $("#" + id).fileinput({
        'theme': 'explorer-fas',
        'uploadUrl': url,
        contentType: false,
        dropZoneTitle: msg,
        autoReplace: true,//自动替换之前选的
        overwriteInitial: false,
        initialPreviewAsData: false,
        allowedFileExtensions: ['doc', 'docx', 'wps'],
        layoutTemplates: {
            actionDelete: '',    // 预览区域的删除按钮
            actionUpload: '', // 预览区域的上传按钮
            actions: '' // 预览区域的所有按钮
        }
    }).on("fileuploaded", function (event, data, previewId, index) {
        //多文件的url以,分割
        if ($("#" + tag).val() == "") {
            $("#" + tag).val(data.response.path);
        } else {
            $("#" + tag).val($("#" + tag).val() + "," + data.response.path);
        }
    }).on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    }).on('filesuccessremove', function (event, id) {

    });
}

//提交表单方法
function commit() {
    mask();
    var amount = reverseMoney($("#amount").val());
    var period = $("#period ").val();
    var sellerCorprName = $("#sellerCorprName").val();
    var buyerCorprName = $("#buyerCorprName").val();
    var creditBillId = $("#creditBillId").val();
    var withdrawAppPath = $("#file1").val();
    var additionFilePath = $("#file2").val();
    var bankId = $("#bankId").val();
    var data = "{\"amount\":\"" + amount
        + "\",\"period\":\"" + period
        + "\",\"sellerCorprName\":\"" + sellerCorprName
        + "\",\"buyerCorprName\":\"" + buyerCorprName
        + "\",\"creditBillId\":\"" + creditBillId
        + "\",\"withdrawAppPath\":\"" + withdrawAppPath
        + "\",\"additionFilePath\":\"" + additionFilePath
        + "\",\"bankId\":\"" + bankId
        + "\"}";
    $.ajax({
        url: "/withdraw/save",
        type: "POST",
        data: data,
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.insert > 0) {
                toastr.success("提交成功");
                setTimeout(function () {
                    layer.closeAll();
                    parent.location.reload();
                }, 3000);
            } else {
                layer.closeAll();
                toastr.error("提交申请失败");
            }
        },
        error: function () {
            layer.closeAll();
            toastr.error("提交申请失败");
        }
    });
}

//验证表单
function validate() {
    var icon = "";//"<i class='fa fa-times-circle' style='color: red;'></i> ";
    $("#add_Form").validate({
        rules: {
            amount: "required",
            period: "required",
            sellerCorprName: "required",
            withdrawAppPath: "required"
        },
        messages: {
            amount: icon + "请输入提款金额(元)",
            period: icon + "请输入提款期限(月)",
            sellerCorprName: icon + "请输入卖方企业名称",
            withdrawAppPath: icon + "请上传业务申请书"
        },
        submitHandler: function (form) {
            if ($("#file1").val() == "") {
                toastr.error("您还未上传申请书!");
            } else {
                if ($("#buyerCorprName").val() == "请选择") {
                    toastr.error("请选择买方企业");
                    $("#buyerCorprName").focus();
                    return;
                }
                commit();
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList.length > 0) {
                toastr.error(errorList[0].message);
            }
        },
        onfocusout: false
    });
}

function valiamount(node) {
    node.value = formatMoney(node.value,2);
    if (reverseMoney($("#amount").val()) > parseFloat(parent.currentAmount)) {
        $("#amount").val(formatMoney(parent.currentAmount,2));
    }
}

//手机号验证
function isMobile(s) {
    var patrn = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
    if (patrn.exec(s))
        return true;
    return false;
}

function outputmoney(number) {
    //number = number.replace(/\,/g, "");
    if (isNaN(number) || number == "") return "";
    number = Math.round(number * 100) / 100;
    if (number < 0)
        return '-' + outputdollars(Math.floor(Math.abs(number) - 0) + '') + outputcents(Math.abs(number) - 0);
    else
        return outputdollars(Math.floor(number - 0) + '') + outputcents(number - 0);
}

//格式化金额
function outputdollars(number) {
    if (number.length <= 3)
        return (number == '' ? '0' : number);
    else {
        var mod = number.length % 3;
        var output = (mod == 0 ? '' : (number.substring(0, mod)));
        for (i = 0; i < Math.floor(number.length / 3); i++) {
            if ((mod == 0) && (i == 0))
                output += number.substring(mod + 3 * i, mod + 3 * i + 3);
            else
                output += ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
        }
        return (output);
    }
}

function outputcents(amount) {
    amount = Math.round(((amount) - Math.floor(amount)) * 100);
    return (amount < 10 ? '.0' + amount : '.' + amount);
}

//遮罩层
function mask() {
    var index = layer.load(1, {
        shade: [0.2, '#ddd']
    });
}

/**
 * 查询收款账户
 */
function findBankCord() {
    $.ajax({
        url: "/system/bankCardInfo/findBankList",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            var value = "";
            if (data.data.length == 0) {
                return;
            }
            for (var i = 0; i < data.data.length; i++) {
                value += '<option value=' + data.data[i].id + '>' + data.data[i].bankCard + ' ' + data.data[i].owner + ' ' + data.data[i].bank + '</option>';
            }
            /* for (val of data.data) {
             value += '<option value=' + val.id + '>' + val.bankCard + ' ' + val.owner + ' ' + val.bank + '</option>';
             //                    value += `<option value="${val.id}">${val.bankCard}</option>`;
             }*/
            $("#bankId")[0].innerHTML = value;
        }
    });
}


function chinese(string) {
    var pattern = /^[\u4E00-\u9FA5]{1,50}$/;
    if (pattern.test(string)) {　　//用原生JS的test()函数来匹配传入的值，返回布尔值。
        return false;
    } else {
        return true;
    }
}

/**
 * 添加银行卡
 */
function addBankCard() {
    if ($("#bankCard").val() == "" || $("#bankCard").val() == null) {
        toastr.error("请输入收款帐户帐号");
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
        toastr.error("请输入帐户开户行中文名字");
        $("#bankName")[0].focus();
        return;
    }
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
                findBankCord();
            } else if (result.code == "500") {
                toastr.error("密码错误");
            } else if (result.code == "301") {
                toastr.error(result.msg);
            }
        }
    });
}

//点击图片查看
function lookimg(str) {
    var newwin = layer.open({
        type: 1,
        title: ' ',
        shadeClose: true,
        shade: [0.8, '#393D49'],
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '90%'],
        content: $("#showImgDiv")
    });
    $("#showImg").attr("src", str);
}

function MySubstr(str) {
    var index = str.lastIndexOf(".");
    if (index == -1) {
        return false;
    }
    str = str.substring(index + 1, str.length);
    return str;
}

function isImg(str) {
    if (str == 'jpg' || str == 'png' || str == 'jpeg' || str == 'gif' || str == 'bmp') {
        return true;
    } else {
        return false;
    }
}

//清空上传框 start
function removeWithdrawAppPath() {
    $('.addImg1').remove();
    $("#file1").val("");
    $("#withdrawAppPath").val("");
    element.progress('withdrawAppPath', '0%');
}

function removeAdditionFilePath() {
    $('.addImg2').remove();
    $("#file2").val("");
    $("#additionFilePath").val("");
    element.progress('otherForm', '0%');
}

//end

//创建监听函数
var xhrOnProgress = function (fun) {
    xhrOnProgress.onprogress = fun; //绑定监听
    //使用闭包实现监听绑
    return function () {
        //通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
        var xhr = $.ajaxSettings.xhr();
        //判断监听函数是否为函数
        if (typeof xhrOnProgress.onprogress !== 'function')
            return xhr;
        //如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
        if (xhrOnProgress.onprogress && xhr.upload) {
            xhr.upload.onprogress = xhrOnProgress.onprogress;
        }
        return xhr;
    }
};

function amountUp() {
    if (checkMoney($("#amount").val())) {
        valiamount($("#amount")[0]);
        if ($("#amount").val().length > 0) {
            $("#amountUpDiv").css("visibility","visible");
            $("#amountUp").text("大写: " + convertCurrency(reverseMoney($("#amount").val())));
        } else {
            $("#amountUpDiv").css("visibility","hidden");
            $("#amountUp").val("");
        }
    } else {
        $("#amountUpDiv").css("visibility","hidden");
        $("#amountUp").val("");
        $("#amount").val(formatMoney(parent.currentAmount,2));
    }
}