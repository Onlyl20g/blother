// var prefix = ctx + "/system/supplier";
var prefix = "/system/supplier";
var laydate = "";
var laydateFlag = 0;
var element = "";
/**
 * 数据初始话
 */
$(function () {
    //初始话图片src
    $("#businessLicenseFilepathIdImg")[0].src = '/system/file/down?url=' + $("#businessLicenseFilepathId").val() + '&name=3df341ds4&type=jpg';
    $("#coedIdImg")[0].src = '/system/file/down?url=' + $("#coedId").val() + '&name=45fda42df&type=jpg';
    $("#idback")[0].src = '/system/file/down?url=' + $("#idcardBackFilepathId").val() + '&name=45fsbackdf&type=jpg';
    // $("#businessLicenseFilepathIdshow")[0].src = '/system/file/down?url='+$("#businessLicenseFilepathId").val();
    if ($("#status").val() == 0) { //判断账号是否已激活
        $("#open").hide();
        $("#save").hide();
        $("#stop").show();
        $(":input").attr("readonly", "readonly");
        $("#sex").attr("disabled", "disabled");
        $("#controlDiv").hide();
    } else {
        dateMuen();
    }
    if ($("#operationPeriod").val() == "永久") {
        $("#toggle-date").click();
    }
});

function dateMuen() {
    layui.use(['laydate', 'element'], function () {
        laydate = layui.laydate;
        element = layui.element;
        laydate.render({
            elem: '#establishDate' //指定元素
        });
        laydate.render({
            elem: '#enterpriseTime' //指定元素
        });
        laydate.render({
            elem: '#enterpriseValidDateStart' //指定元素
        });
        laydate.render({
            elem: '#operationPeriod' //指定元素
        });
        laydate.render({
            elem: '#birth' //指定元素
        });
    });
}

function dateHide() {
    $("#establishDate").show();
    $("#enterpriseTime").show();
    $("#enterpriseValidDateStart").show();
    $("#operationPeriod").show();
    $("#birth").show();
    $("#establishDateRd").hide();
    $("#enterpriseTimeRd").hide();
    $("#enterpriseValidDateStartRd").hide();
    $("#operationPeriodRd").hide();
    $("#birthRd").hide();
    dateMuen();
}

function dateShow() {
    $("#establishDate").hide();
    $("#enterpriseTime").hide();
    $("#enterpriseValidDateStart").hide();
    $("#operationPeriod").hide();
    $("#birth").hide();
    $("#establishDateRd").show();
    $("#enterpriseTimeRd").show();
    $("#enterpriseValidDateStartRd").show();
    $("#operationPeriodRd").show();
    $("#birthRd").show();
}

function submitStatus() {
    $.modal.confirm("确认要停用用户吗？", function () {
        $.ajax({
            url: "/system/supplier/changeStatus",
            type: "POST",
            dataType: "JSON",
            data: {
                "id": $("#id").val(),
                "status": 1
            },
            success: function (r) {
                // $("#open").show();
                // $("#save").show();
                // $("#stop").hide();
                // $(":input").removeAttr("readonly");
                // $("#controlDiv").show();
                // dateHide();
                // layer.alert('停用成功', {icon: 1});
                // closeItem();
                showInfo("停用成功");
            }
        })
    })
}


/**
 * 确认启用
 */
function submitHandler(code) {
    $.modal.confirm("确认要启用用户吗？", function () {
        $.ajax({
            url: "/system/supplier/edit",
            type: "POST",
            dataType: "JSON",
            data: {
                "id": $("#id").val(),
                "status": 0,
                "businessLicenseCode": $("#businessLicenseCode").val(),
                "salt": $("#salt").val()
            },
            success: function (r) {
                if ("success" == r.code) {
                    showInfo("用户：<b>" + code + "</b> 的密码<br/>已重设为：<b>" + r.data + "</b>")
                } else {
                    $.modal.alertError("错误：" + r.data);
                }
            }
        })
    });
}

/**
 * 确认保存
 */
function saveHandler() {
    $.modal.confirm("确认修改？", function () {

        if ($("#corprName").val() == null || $("#corprName").val() == "") {
            layer.alert('请填写企业名称', {icon: 2});
            $("#corprName").focus();
            return;
        }
        if ($("#corprType").val() == null || $("#corprType").val() == "") {
            layer.alert('请填写企业类型', {icon: 2});
            $("#corprType").focus();
            return;
        }
        if ($("#registeredCapital").val() == null || $("#registeredCapital").val() == "") {
            layer.alert('请填写企业注册资本', {icon: 2});
            $("#registeredCapital").focus();
            return;
        }
        if ($("#establishDate").val() == null || $("#establishDate").val() == "") {
            layer.alert('请填写成立日期', {icon: 2});
            $("#establishDate").focus();
            return;
        }
        if ($("#corprAddres").val() == null || $("#corprAddres").val() == "") {
            layer.alert('请填写企业通讯地址', {icon: 2});
            $("#corprAddres").focus();
            return;
        }
        if ($("#enterpriseValidDateStart").val() == null || $("#enterpriseValidDateStart").val() == "") {
            layer.alert('请填写企业营业期限开始时间', {icon: 2});
            $("#enterpriseValidDateStart").focus();
            return;
        }
        if ($("#operationPeriod").val() == null || $("#operationPeriod").val() == "") {
            layer.alert('请填写企业营业期限结束时间', {icon: 2});
            $("#operationPeriod").focus();
            return;
        }
        if ($("#legalPersonName").val() == null || $("#legalPersonName").val() == "") {
            layer.alert('请填写法人姓名', {icon: 2});
            $("#legalPersonName").focus();
            return;
        }
        if ($("#legalPersonIdno").val() == null || $("#legalPersonIdno").val() == "") {
            layer.alert('请填写身份证号', {icon: 2});
            $("#legalPersonIdno").focus();
            return;
        }
        if ($("#legalPersonPhone").val() == null || $("#legalPersonPhone").val() == "") {
            layer.alert('请填写手机号', {icon: 2});
            $("#legalPersonPhone").focus();
            return;
        }


        //企业信息
        var enterpriseCode = $("#enterprise-id").val();
        var corprName = $("#corprName").val();
        var corprType = $("#corprType").val();
        var businessLicenseCode = $("#businessLicenseCode").val();
        var enterpriseUniversalCreditIdAgencyCode = $("#enterpriseUniversalCreditIdAgencyCode").val();
        var registeredCapital = $("#registeredCapital").val();
        var establishDate = $("#establishDate").val();
        var corprAddres = $("#corprAddres").val();
        var enterpriseCountryId = $("#enterpriseCountryId").val();
        var enterpriseBankCity = $("#enterpriseBankCity").val();
        var enterpriseBankDistrict = $("#enterpriseBankDistrict").val();
        var enterpriseBankProvince = $("#enterpriseBankProvince").val();
        var enterpriseCity = $("#enterpriseCity").val();
        var enterpriseDistrict = $("#enterpriseDistrict").val();
        var enterpriseId = $("#enterpriseId").val();
        var enterpriseRegisterAddressFormatAddress = $("#enterpriseRegisterAddressFormatAddress").val();
        var enterpriseRegisterAddress = $("#enterpriseRegisterAddress").val();
        var enterpriseRegisterAddressGps = $("#enterpriseRegisterAddressGps").val();
        var enterpriseRegisterAddressProvince = $("#enterpriseRegisterAddressProvince").val();
        var enterpriseTime = $("#enterpriseTime").val();
        var enterpriseTaxpayerRegisterId = $("#enterpriseTaxpayerRegisterId").val();
        var enterpriseValidDateStart = $("#enterpriseValidDateStart").val();
        var operationPeriod = $("#operationPeriod").val();

        // 供应商信息
        var supplierId = $("#supplier-id").val();

        // 法人身份证信息
        var supplierIdcardinfoId = $("#supplierIdcardinfo-id").val();
        var legalPersonName = $("#legalPersonName").val();
        var legalPersonIdno = $("#legalPersonIdno").val();
        var legalPersonPhone = $("#legalPersonPhone").val();
        var address = $("#address").val();
        var sex = $("#sex").val();
        var birth = $("#birth").val();
        var nationality = $("#nationality").val();

        if (!isPoneAvailable(legalPersonPhone)) {
            layer.alert('请检查手机号', {icon: 2}, function () {
                $("#legalPersonPhone").focus();
                layer.closeAll();
            });
            return;
        }
        if (!idCardNoUtil.checkIdCardNo(legalPersonIdno)) {
            layer.alert('请检查身份证号码', {icon: 2}, function () {
                $("#legalPersonIdno").focus();
                layer.closeAll();
            });
            return;
        }
        $.ajax({
            url: "/system/supplier/saveSupplier",
            type: "POST",
            dataType: "JSON",
            data: {
                "id": supplierId, "corprAddres": corprAddres, "registeredCapital": registeredCapital,
                "establishDate": establishDate, "operationPeriod": operationPeriod, "corprType": corprType,
                "corprName": corprName, "legalPersonName": legalPersonName, "legalPersonIdno": legalPersonIdno,
                "legalPersonPhone": legalPersonPhone
            },
            success: function (data) {
                if (data.row > 0) {
                    $.ajax({
                        url: "/system/supplierIdcardinfo/editSupplierCard",
                        type: "POST",
                        dataType: "JSON",
                        data: {
                            "id": supplierIdcardinfoId,
                            "legalPersonName": legalPersonName,
                            "legalPersonIdno": legalPersonIdno,
                            "legalPersonPhone": legalPersonPhone,
                            "address": address,
                            "sex": sex,
                            "birth": birth,
                            "nationality": nationality
                        },
                        success: function (data) {
                            if (data.code == 0) {
                                $.ajax({
                                    url: "/system/enterprise/editEnterprise",
                                    type: "POST",
                                    dataType: "JSON",
                                    data: {
                                        "id": enterpriseCode,
                                        "enterpriseNameCh": corprName,
                                        "enterpriseType": corprType,
                                        "enterpriseId": businessLicenseCode,
                                        "enterpriseUniversalCreditIdAgencyCode": enterpriseUniversalCreditIdAgencyCode,
                                        "enterpriseCapital": registeredCapital,
                                        // "establishDate": establishDate,
                                        "enterpriseRegisterAddress": corprAddres,
                                        "enterpriseCountryId": enterpriseCountryId,
                                        "enterpriseBankCity": enterpriseBankCity,
                                        "enterpriseBankDistrict": enterpriseBankDistrict,
                                        "enterpriseBankProvince": enterpriseBankProvince,
                                        "enterpriseCity": enterpriseCity,
                                        "enterpriseDistrict": enterpriseDistrict,
                                        "enterpriseId": enterpriseId,
                                        "enterpriseRegisterAddressFormatAddress": enterpriseRegisterAddressFormatAddress,
                                        "enterpriseRegisterAddress": enterpriseRegisterAddress,
                                        "enterpriseTime": enterpriseTime,
                                        "enterpriseTaxpayerRegisterId": enterpriseTaxpayerRegisterId,
                                        "enterpriseValidDateStart": enterpriseValidDateStart,
                                        "enterpriseValidDateend": operationPeriod,
                                        "enterpriseRegisterAddressGps": enterpriseRegisterAddressGps,
                                        "enterpriseRegisterAddressProvince": enterpriseRegisterAddressProvince
                                    },
                                    success: function (data) {
                                        if (data.code == 1) {
                                            showInfo("提示：保存成功");
                                        } else {
                                            layer.alert('必填数据不能为空', {icon: 2});
                                        }
                                    }
                                })
                            }
                        }
                    })
                } else {
                    layer.alert('必填数据不能为空', {icon: 2});
                }
            }
        })
    });
}


function saveOpen() {
    if ($("#corprName").val() == null || $("#corprName").val() == "") {
        layer.alert('请填写企业名称', {icon: 2});
        $("#corprName").focus();
        return;
    }
    if ($("#corprType").val() == null || $("#corprType").val() == "") {
        layer.alert('请填写企业类型', {icon: 2});
        $("#corprType").focus();
        return;
    }
    if ($("#registeredCapital").val() == null || $("#registeredCapital").val() == "") {
        layer.alert('请填写企业注册资本', {icon: 2});
        $("#registeredCapital").focus();
        return;
    }
    if ($("#establishDate").val() == null || $("#establishDate").val() == "") {
        layer.alert('请填写成立日期', {icon: 2});
        $("#establishDate").focus();
        return;
    }
    if ($("#corprAddres").val() == null || $("#corprAddres").val() == "") {
        layer.alert('请填写企业通讯地址', {icon: 2});
        $("#corprAddres").focus();
        return;
    }
    if ($("#enterpriseValidDateStart").val() == null || $("#enterpriseValidDateStart").val() == "") {
        layer.alert('请填写企业营业期限开始时间', {icon: 2});
        $("#enterpriseValidDateStart").focus();
        return;
    }
    if ($("#operationPeriod").val() == null || $("#operationPeriod").val() == "") {
        layer.alert('请填写企业营业期限结束时间', {icon: 2});
        $("#operationPeriod").focus();
        return;
    }
    if ($("#legalPersonName").val() == null || $("#legalPersonName").val() == "") {
        layer.alert('请填写法人姓名', {icon: 2});
        $("#legalPersonName").focus();
        return;
    }
    if ($("#legalPersonIdno").val() == null || $("#legalPersonIdno").val() == "") {
        layer.alert('请填写身份证号', {icon: 2});
        $("#legalPersonIdno").focus();
        return;
    }
    if ($("#legalPersonPhone").val() == null || $("#legalPersonPhone").val() == "") {
        layer.alert('请填写手机号', {icon: 2});
        $("#legalPersonPhone").focus();
        return;
    }
    var enterpriseCode = $("#enterprise-id").val();
    var corprName = $("#corprName").val();
    var corprType = $("#corprType").val();
    var businessLicenseCode = $("#businessLicenseCode").val();
    var enterpriseUniversalCreditIdAgencyCode = $("#enterpriseUniversalCreditIdAgencyCode").val();
    var registeredCapital = $("#registeredCapital").val();
    var establishDate = $("#establishDate").val();
    var corprAddres = $("#corprAddres").val();
    var enterpriseCountryId = $("#enterpriseCountryId").val();
    var enterpriseBankCity = $("#enterpriseBankCity").val();
    var enterpriseBankDistrict = $("#enterpriseBankDistrict").val();
    var enterpriseBankProvince = $("#enterpriseBankProvince").val();
    var enterpriseCity = $("#enterpriseCity").val();
    var enterpriseDistrict = $("#enterpriseDistrict").val();
    var enterpriseId = $("#enterpriseId").val();
    var enterpriseRegisterAddressFormatAddress = $("#enterpriseRegisterAddressFormatAddress").val();
    var enterpriseRegisterAddress = $("#enterpriseRegisterAddress").val();
    var enterpriseRegisterAddressGps = $("#enterpriseRegisterAddressGps").val();
    var enterpriseRegisterAddressProvince = $("#enterpriseRegisterAddressProvince").val();
    var enterpriseTime = $("#enterpriseTime").val();
    var enterpriseTaxpayerRegisterId = $("#enterpriseTaxpayerRegisterId").val();
    var enterpriseValidDateStart = $("#enterpriseValidDateStart").val();
    var operationPeriod = $("#operationPeriod").val();

    // 供应商信息
    var supplierId = $("#supplier-id").val();

    // 法人身份证信息
    var supplierIdcardinfoId = $("#supplierIdcardinfo-id").val();
    var legalPersonName = $("#legalPersonName").val();
    var legalPersonIdno = $("#legalPersonIdno").val();
    var legalPersonPhone = $("#legalPersonPhone").val();
    var address = $("#address").val();
    var sex = $("#sex").val();
    var birth = $("#birth").val();
    var nationality = $("#nationality").val();

    if (!isPoneAvailable(legalPersonPhone)) {
        layer.alert('请检查手机号', {icon: 2}, function () {
            $("#legalPersonPhone").focus();
            layer.closeAll();
        });
        return;
    }
    if (!idCardNoUtil.checkIdCardNo(legalPersonIdno)) {
        layer.alert('请检查身份证号码', {icon: 2}, function () {
            $("#legalPersonIdno").focus();
            layer.closeAll();
        });
        return;
    }

    $.ajax({
        url: "/system/supplier/saveSupplier",
        type: "POST",
        dataType: "JSON",
        data: {
            "id": supplierId, "corprAddres": corprAddres, "registeredCapital": registeredCapital,
            "establishDate": establishDate, "operationPeriod": operationPeriod, "corprType": corprType,
            "corprName": corprName, "legalPersonName": legalPersonName, "legalPersonIdno": legalPersonIdno,
            "legalPersonPhone": legalPersonPhone
        },
        success: function (data) {
            if (data.row > 0) {
                $.ajax({
                    url: "/system/supplierIdcardinfo/edit",
                    type: "POST",
                    dataType: "JSON",
                    data: {
                        "id": supplierIdcardinfoId,
                        "legalPersonName": legalPersonName,
                        "legalPersonIdno": legalPersonIdno,
                        "legalPersonPhone": legalPersonPhone,
                        "address": address,
                        "sex": sex,
                        "birth": birth,
                        "nationality": nationality
                    },
                    success: function (data) {
                        if (data.code == 0) {
                            $.ajax({
                                url: "/system/enterprise/editEnterprise",
                                type: "POST",
                                dataType: "JSON",
                                data: {
                                    "id": enterpriseCode,
                                    "corprName": corprName,
                                    "corprType": corprType,
                                    "businessLicenseCode": businessLicenseCode,
                                    "enterpriseUniversalCreditIdAgencyCode": enterpriseUniversalCreditIdAgencyCode,
                                    "enterpriseCapital": registeredCapital,
                                    "establishDate": establishDate,
                                    "corprAddres": corprAddres,
                                    "enterpriseCountryId": enterpriseCountryId,
                                    "enterpriseBankCity": enterpriseBankCity,
                                    "enterpriseBankDistrict": enterpriseBankDistrict,
                                    "enterpriseBankProvince": enterpriseBankProvince,
                                    "enterpriseCity": enterpriseCity,
                                    "enterpriseDistrict": enterpriseDistrict,
                                    "enterpriseId": enterpriseId,
                                    "enterpriseRegisterAddressFormatAddress": enterpriseRegisterAddressFormatAddress,
                                    "enterpriseRegisterAddress": enterpriseRegisterAddress,
                                    "enterpriseTime": enterpriseTime,
                                    "enterpriseTaxpayerRegisterId": enterpriseTaxpayerRegisterId,
                                    "enterpriseValidDateStart": enterpriseValidDateStart,
                                    "enterpriseValidDateend": operationPeriod,
                                    "enterpriseRegisterAddressGps": enterpriseRegisterAddressGps,
                                    "enterpriseRegisterAddressProvince": enterpriseRegisterAddressProvince
                                },
                                success: function (data) {
                                    if (data.code == 1) {
                                        submitHandler(businessLicenseCode);
                                    } else {
                                        layer.alert('必填数据不能为空', {icon: 2});
                                    }
                                }
                            })
                        }
                    }
                })
            } else {
                layer.alert('必填数据不能为空', {icon: 2});
            }
        }
    })
}

/**
 * 显示启用时回显的密码
 * @param content
 */
function showInfo(content) {
    layer.confirm(content, {
        icon: 1,
        title: "提示",
        btn: ['关闭']
    }, function (index) {
        layer.close(index);
        var topWindow = $(window.parent.document);
        var target = $('.RuoYi_iframe[data-id="system/supplier"]', topWindow);
        var url = target.attr('src');
        target.attr('src', url).ready();
        refreshItem();
    });
}

/**
 * 关闭按钮后关闭页签
 */
function closeEdit() {
    closeItem();
    $.operate.saveTab(
        prefix + "/edieClose", {}
    );
}

/**
 * 下载营业执照照片
 */
$("#btn_down").click(function () {
    window.open('/system/file/down?url=' + $("#businessLicenseFilepathId").val() + '&type=jpg');
});
/**
 * 显示图片弹框
 */
/*$("#businessLicenseFilepathIdImg").click(function(){
 $('#show_img').modal('show');
 })*/
/**
 * 展现图
 */
$("#businessLicenseFilepathIdImg").click(function () {
    sessionStorage.setItem('src', '/system/file/down?url=' + $("#businessLicenseFilepathId").val() + '&name=3df341ds4&type=jpg');
    var url = prefix + '/show/';
    var width = $(window).width() * 0.6;
    var height = $(window).height();
    $.modal.open("营业执照", url, width, height);
});

$("#coedIdImg").click(function () {
    sessionStorage.setItem('src', '/system/file/down?url=' + $("#coedId").val() + '&name=45fda42df&type=jpg');
    var url = prefix + '/show/';
    var width = $(window).width() * 0.6;
    var height = $(window).height();
    $.modal.open("法定代表人身份证人像面", url, width, height);
});

$("#idback").click(function () {
    sessionStorage.setItem('src', '/system/file/down?url=' + $("#idcardBackFilepathId").val() + '&name=45fsbackdf&type=jpg');
    var url = prefix + '/show/';
    var width = $(window).width() * 0.6;
    var height = $(window).height();
    $.modal.open("法定代表人身份证国徽面", url, width, height);
});

function forevery() {
    $("#operationPeriod").remove();
    //$("#controlDiv").empty();
    if (laydateFlag++ % 2 == 1) {
        $("#operationPeriodDiv").append("<input type=\"text\" class=\"form-control style_border\" id=\"operationPeriod\" name=\"operationPeriod\">");
        layui.laydate.render({
            elem: '#operationPeriod' //指定元素
        });
    } else {
        $("#operationPeriodDiv").append("<input type=\"text\" class=\"form-control style_border\" id=\"operationPeriod\" name=\"operationPeriod\" disabled>");
        $("#operationPeriod").val("永久");
    }
}