toastr.options = {
    closeButton: false,
    debug: false,
    progressBar: true,
    positionClass: "toast-top-center",
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "2000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut"
};
var layerLoadingIndex = 0;
var upload = "";
var element = "";
var laydate = "";
var layuiForm = "";
var laydateFlag = 0;

var address = "";
//初始化文件上传框
$(document).ready(function () {
    // initFileinput1("businessLicensePath", "bus1", "system/file/uploadBus", "拖放上传营业执照");
    // initFileinput2("idcardFrontPath", "bus2", "system/file/upload", "拖放上传身份证国徽面");
    // initFileinput3("idcardBackPath", "bus3", "system/file/uploadIDCard", "拖放上传身份证照片面");
    validate();
    findCustomerManager();
    layui.use(['upload', 'element', 'laydate', 'form'], function () {
        var $ = layui.jquery;
        upload = layui.upload;
        element = layui.element;
        laydate = layui.laydate;
        layuiForm = layui.form;
        layui.form.render;
        laydate.render({
            elem: '#enterpriseTime' //指定元素
        });
        laydate.render({
            elem: '#validDateStart' //指定元素
        });
        laydate.render({
            elem: '#validDateEnd' //指定元素
        });
        //营业执照上传
        var businessLicense = upload.render({
            elem: '#businessLicenseUpload',
            url: 'system/file/uploadBus',
            exts: 'jpg|jpeg|png',
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('businessLicensePath', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    removeFileBusinessLicense();
                    $("#businessLicensePath").val(file.name);
                    $('#businessLicenseView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg1"><img style="margin: 15px;" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>');
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.ENTERPRISE_ID != undefined && data.ENTERPRISE_ID != "" && data.url != "") {
                    onwer = data.ENTERPRISE_OWNER;
                    $("#companyname").val(data.ENTERPRISE_NAME_CH);
                    $("#code").val(data.ENTERPRISE_ID);
                    $("#corprType").val(data.ENTERPRISE_TYPE);
                    $("#enterpriseCapital").val(data.ENTERPRISE_CAPITAL);
                    $("#enterpriseTime").val(data.ENTERPRISE_TIME);
                    $("#validDateStart").val(data.ENTERPRISE_VALID_DATE_START);
                    if (data.ENTERPRISE_VALID_DATE_START == "" || data.ENTERPRISE_VALID_DATE_START == null) {
                        $("#validDateStart").val(data.ENTERPRISE_TIME);
                    }
                    $("#corprAddres").val(data.ENTERPRISE_REGISTER_ADDRESS);
                    address = data.ENTERPRISE_REGISTER_ADDRESS;
                    $("#validDateEnd").val(data.ENTERPRISE_VALID_DATE_END);
                    $("#bus1").val(data.url);
                    if (data.ENTERPRISE_REGISTER_ADDRESS == "") {
                        $("#corprAddres").val(data.ENTERPRISE_REGISTER_ADDRESS_FORMAT_ADDRESS);
                        if (data.ENTERPRISE_REGISTER_ADDRESS_FORMAT_ADDRESS == "") {
                            $("#corprAddres").val(data.ENTERPRISE_REGISTER_ADDRESS_DISTRICT);
                        }
                    }
                    if (data.ENTERPRISE_VALID_DATE_END == "永久") {
                        if (!$("#toggle-date").prop("checked")) {
                            $("#toggle-date").click();
                            layui.form.render();
                        }
                    }
                } else if (data.url != "") {
                    $("#bus1").val(data.url);
                    toastr.error("上传成功，但识别失败，请填写信息");
                    //removeFileBusinessLicense();
                } else {
                    toastr.error("上传失败");
                }
            },
            error: function () {
                //异常回调
                layer.alert("上传失败", {icon: 2});
                layer.close(layerLoadingIndex);
                removeFileBusinessLicense();
            }
        });

        //上传身份证国徽面
        var idcardFront = upload.render({
            elem: '#idcardFrontUpload',
            url: 'system/file/upload',
            accept: 'file',
            // url: 'system/file/uploadIDCard',
            exts: 'jpg|jpeg|png|gif',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('progressBar', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    removeFileTwo();
                    $("#idcardFrontPath").val(file.name);
                    $('#idcardFrontView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg2"><img style="margin: 15px;" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                });
            },
            done: function (data, index, upload) {

                layer.close(layerLoadingIndex);
                //上传完毕
                // if (data.success) {
                //     $("#bus2").val(data.path);
                // }else{
                //     toastr.error("无法识别该身份证信息");
                // }
                //上传完毕
                layer.close(layerLoadingIndex);
                $("#bus2").val(data.path);
            },
            error: function () {
                //异常回调
                layer.alert("上传失败", {icon: 2});
                layer.close(layerLoadingIndex);
                removeFileTwo();
            }
        });

        //身份证照片面
        var idcardBack = upload.render({
            elem: '#idcardBackUpload',
            url: 'system/file/uploadIDCard',
            exts: 'jpg|jpeg|png|gif',
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('idcardBackPath', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    removeFileThree();
                    $("#idcardBackPath").val(file.name);
                    $('#idcardBackView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg3"><img style="margin: 15px;" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                });
            },
            done: function (data, index, upload) {
                //上传完毕
                layer.close(layerLoadingIndex);
                if (data.url != null) {
                    $("#bus3").val(data.url);
                }
                if (data.success) {
                    //if (data.name == owner)) {
                    $("#nationality").val(data.nationality);
                    $("#sex").val(data.sex);
                    $("#birth").val(data.birth);
                    $("#address").val(data.address);
                    $("#request_id").val(data.request_id);
                    $("#legalPersonName").val(data.name);
                    $("#legalPersonIdno").val(data.num);
                } else {
                    toastr.error("上传成功，但是识别失败，请填写信息");
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2}, function () {
                    removeFileThree();
                });
            }
        });
    });
});

//当点击移除时，进行清除操作
function removeFileBusinessLicense() {
    $('.addImg1').remove();
    $("#bus1").val("");
    $("#biss1").val("");
    $("#companyname").val("");
    $("#code").val("");
    $("#corprType").val("");
    $("#corprAddres").val("");
    $("#enterpriseCapital").val("");
    $("#enterpriseTime").val("");
    $("#validDateStart").val("");
    $("#validDateEnd").val("");
    $("#businessLicensePath").val("");
    if ($("#toggle-date").prop("checked")) {
        $("#toggle-date").click();
        layui.form.render();
        //$(".layui-unselect").attr("class","layui-unselect layui-form-switch");
    }
    element.progress('businessLicensePath', '0%');
}


function removeFileTwo() {
    $('.addImg2').remove();
    $("#bus2").val("");
    $("#idcardFrontPath").val("");
    element.progress('progressBar', '0%');
}

function removeFileThree() {
    $('.addImg3').remove();
    $("#bus3").val("");
    $('#legalPersonName').val("");
    $('#legalPersonIdno').val("");
    $("#idcardBackPath").val("");
    element.progress('idcardBackPath', '0%');
}

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

//打开用户协议窗口
function showUserProtocol() {
    layui.use(['layer', 'form'], function () {
        layer.open({
            type: 2,
            title: '用户协议',
            shadeClose: true,
            shade: [0.8, '#393D49'],
            maxmin: false, //开启最大化最小化按钮
            area: ['80%', '90%'],
            content: 'system/supplier/userProtocol',
        });
    });
}

function findCustomerManager() {
    $.ajax({
        url: "system/supplier/findCustomerManager",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function (r) {
            if (r.data.length > 0) {
                var value = "<option></option>";
                for (var i = 0; i < r.data.length; i++) {
                    value += "<option value='" + r.data[i].billId + "'>" + r.data[i].userName + "</option>";
                }
                $("#customerManagerName").html(value);
            }
        }
    });
}

var onwer = "";

//初始化文件上传框方法
function initFileinput1(id, tag, url, msg) {
    $("#" + id).fileinput({
        'theme': 'explorer-fas',
        'uploadUrl': url,
        dropZoneTitle: msg,
        autoReplace: true,//自动替换之前选的
        overwriteInitial: false,
        initialPreviewAsData: false,
        layoutTemplates: {
            actionDelete: '',    // 预览区域的删除按钮
            actionUpload: '', // 预览区域的上传按钮
            actions: '' // 预览区域的所有按钮
        },
        //allowedFileExtensions: ['jpg','png','bmp'],//允许上传的文件后缀名,不能和allowedFileTypes一起用
        allowedFileTypes: ['image']//允许上传的文件类型
        // initialPreview: [//初始化时加载
        //     "/img/profile.jpg"
        // ],
    }).on("fileuploaded", function (event, data, previewId, index) {
        onwer = data.response.ENTERPRISE_OWNER;
        $("#companyname").val(data.response.ENTERPRISE_NAME_CH);
        $("#code").val(data.response.ENTERPRISE_ID);
        $("#corprType").val(data.response.ENTERPRISE_TYPE);
        $("#corprAddres").val(data.response.ENTERPRISE_REGISTER_ADDRESS);
        $("#bus1").val(data.response.url);
    }).on("fileclear", function (event, data, msg) {
        //当删除时清空对应的输入框
        $("#companyname").val("");
        $("#code").val("");
        $("#corprType").val("");
        $("#corprAddres").val("");
        $("#" + tag).val("");
    }).on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    });
}

function initFileinput2(id, tag, url, msg) {
    $("#" + id).fileinput({
        'theme': 'explorer-fas',
        'uploadUrl': url,
        dropZoneTitle: msg,
        autoReplace: true,//自动替换之前选的
        overwriteInitial: false,
        allowedFileTypes: ['image'],
        layoutTemplates: {
            actionDelete: '',    // 预览区域的删除按钮
            actionUpload: '', // 预览区域的上传按钮
            actions: '' // 预览区域的所有按钮
        },
        initialPreviewAsData: false
    }).on("fileuploaded", function (event, data, previewId, index) {
        $("#bus2").val(data.response.url);
        url = data.response.path;
        $("#" + tag).val(data.response.path);
    }).on("fileclear", function (event, data, msg) {
        $("#" + tag).val("");
    }).on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    });
}

function initFileinput3(id, tag, url, msg) {
    $("#" + id).fileinput({
        'theme': 'explorer-fas',
        'uploadUrl': url,
        dropZoneTitle: msg,
        autoReplace: true,//自动替换之前选的
        overwriteInitial: false,
        allowedFileTypes: ['image'],
        layoutTemplates: {
            actionDelete: '',    // 预览区域的删除按钮
            actionUpload: '', // 预览区域的上传按钮
            actions: '' // 预览区域的所有按钮
        },
        initialPreviewAsData: false
    }).on("fileclear", function (event, data, msg) {//删除、移除文件回调
        $("#" + tag).val("");
    }).on("fileuploaded", function (event, data, previewId, index) {
        if (data.response.success) {
            //if (data.response.name == owner)) {
            $("#bus3").val(data.response.url);
            $("#nationality").val(data.response.nationality);
            $("#sex").val(data.response.sex);
            $("#birth").val(data.response.birth);
            $("#address").val(data.response.address);
            $("#request_id").val(data.response.request_id);
            $("#legalPersonName").val(data.response.name);
            $("#legalPersonIdno").val(data.response.num);
        } else {
            toastr.error("无法识别该身份证");
        }
    }).on('filesuccessremove', function (event, id) {
        //删除时清空下面的表单
        $("#legalPersonName").val("");
        $("#legalPersonIdno").val("");
    }).on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    });
}

//提交注册
function reportForm() {
    if (!$("#customCheckRegister").prop("checked")) {
        toastr.error("您未勾选我同意用户协议!");
    } else {
        layerLoadingIndex = layer.load(1, {
            shade: [0.1, '#fff']
        });
        var businessLicensePath = $("#bus1").val();
        var companyname = $("#companyname").val();
        var code = $("#code").val();
        var corprType = $("#corprType").val();
        //var corprAddres = address != "" ? address : $("#corprAddres").val();
        var corprAddres = $("#corprAddres").val();
        var enterpriseCapital = $("#enterpriseCapital").val();//注册资本
        var enterpriseTime = $("#enterpriseTime").val();//成立日期
        var validDateStart = $("#validDateStart").val();//成立日期
        var validDateEnd = $("#validDateEnd").val();//营业期限
        var customerManagerId = $("#customerManagerName").val();
        var index = $("#customerManagerName")[0].selectedIndex;
        var options = $("#customerManagerName")[0].options;
        var customerManagerName = options[index].text;
        var idcardFrontPath = $("#bus2").val();
        var idcardBackPath = $("#bus3").val();
        var legalPersonName = $("#legalPersonName").val();
        var legalPersonIdno = $("#legalPersonIdno").val();
        var legalPersonPhone = $("#legalPersonPhone").val();
        var data = "{\"businessLicensePath\":\"" + businessLicensePath
            + "\",\"companyname\":\"" + companyname
            + "\",\"code\":\"" + code
            + "\",\"corprType\":\"" + corprType
            + "\",\"corprAddres\":\"" + corprAddres
            + "\",\"enterpriseCapital\":\"" + enterpriseCapital
            + "\",\"enterpriseTime\":\"" + enterpriseTime
            + "\",\"validDateStart\":\"" + validDateStart
            + "\",\"validDateEnd\":\"" + validDateEnd
            + "\",\"customerManagerId\":\"" + customerManagerId
            + "\",\"customerManagerName\":\"" + customerManagerName
            + "\",\"idcardFrontPath\":\"" + idcardFrontPath
            + "\",\"idcardBackPath\":\"" + idcardBackPath
            + "\",\"legalPersonName\":\"" + legalPersonName
            + "\",\"legalPersonIdno\":\"" + legalPersonIdno
            + "\",\"legalPersonPhone\":\"" + legalPersonPhone
            + "\",\"nationality\":\"" + $("#nationality").val()
            + "\",\"sex\":\"" + $("#sex").val()
            + "\",\"birth\":\"" + $("#birth").val()
            + "\",\"address\":\"" + $("#address").val()
            + "\",\"requestId\":\"" + $("#request_id").val()
            + "\"}";
        $.ajax({
            url: "system/supplier/register",
            type: "POST",
            data: data,
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data > 0) {
                    toastr.success("注册成功");
                    setTimeout(function () {
                        location.href = "/";
                        layer.close(layerLoadingIndex);
                    }, 3000);
                } else {
                    layer.close(layerLoadingIndex);
                    toastr.error("该企业已注册");
                }
            },
            error: function () {
                layer.close(layerLoadingIndex);
                toastr.error("注册失败");
            }
        });
    }
}

//验证表单
function validate() {
    var icon = "";//"<i class='fa fa-times-circle' style='color: red;'></i> ";
    $("#registerForm").validate({
        rules: {
            bus1: "required",
            companyname: "required",
            code: "required",
            corprType: "required",
            corprAddres: "required",
            customerManagerName: "required",
            bus2: "required",
            bus3: "required",
            legalPersonName: "required",
            legalPersonIdno: "required",
            legalPersonPhone: "required"
        },
        messages: {
            bus1: icon + "请上传营业执照",
            companyname: icon + "请输入企业名称",
            code: icon + "请输入统一社会信用代码",
            corprType: icon + "请输入企业类型",
            corprAddres: icon + "请输入企业地址",
            customerManagerName: icon + "请输入客户经理姓名",
            bus2: icon + "请上传法定代表人身份证国徽面",
            bus3: icon + "请上传法定代表人身份证人像面",
            legalPersonName: icon + "请输入法定代表人姓名",
            legalPersonIdno: icon + "请输入法定代表人身份证",
            legalPersonPhone: icon + "请输入法定代表人手机号"
        },
        submitHandler: function (form) {
            if ($("#enterpriseCapital").val() == '') {
                toastr.error("请输入注册资本");
                $("#enterpriseCapital")[0].focus();
            } else if ($("#enterpriseTime").val() == '') {
                toastr.error("请输入成立日期");
                $("#enterpriseTime")[0].focus();
                // } else if ($("#validDateEnd").val() == '') {
                //     toastr.error("请输入营业期限");
                //     $("#validDateEnd")[0].focus();
            } else if ($("#validDateStart").val() == '') {
                toastr.error("请输入开始营业日期");
                $("#validDateStart").focus();
            } else if ($("#bus2").val() == '') {
                toastr.error("请上传法定代表人身份证国徽面");
            } else if ($("#bus1").val() == '') {
                toastr.error("请上传营业执照");
            } else if ($("#bus3").val() == '') {
                toastr.error("请上传法定代表人身份证人像面");
            } else {
                if (checkCode()) {
                    if (isPoneAvailable($("#legalPersonPhone").val())) {
                        if (idCardNoUtil.checkIdCardNo($("#legalPersonIdno").val())) {
                            reportForm();
                        } else {
                            $("#legalPersonPhone").focus();
                            toastr.error("请检查身份证号码");
                        }
                    } else {
                        $("#legalPersonPhone").focus();
                        toastr.error("请输入正确的手机号");
                    }
                }
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

//测试统一社会信用代码
function checkCode() {
    var Code = $("#code").val();
    var patrn = /^[0-9A-Z]+$/;
    //18位校验及大写校验
    if ((Code.length !== 18) || (patrn.test(Code) === false)) {
        toastr.error("不是有效的社会信用代码！");
        return false;
    } else {
        var Ancode;//统一社会信用代码的每一个值
        var Ancodevalue;//统一社会信用代码每一个值的权重
        var total = 0;
        var weightedfactors = [1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28];//加权因子
        var str = '0123456789ABCDEFGHJKLMNPQRTUWXY';
        //不用I、O、S、V、Z
        for (var i = 0; i < Code.length - 1; i++) {
            Ancode = Code.substring(i, i + 1);
            Ancodevalue = str.indexOf(Ancode);
            total = total + Ancodevalue * weightedfactors[i];
        }
        var logiccheckcode = 31 - total % 31;
        if (logiccheckcode === 31) {
            logiccheckcode = 0;
        }
        var Str = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,T,U,W,X,Y";
        var Array_Str = Str.split(',');
        logiccheckcode = Array_Str[logiccheckcode];
        var checkcode = Code.substring(17, 18);
        if (logiccheckcode !== checkcode) {
            toastr.error("不是有效的社会信用代码！");
            return false;
        }
        return true;
    }
}

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

function loadImg() {
    var imgArr = $(".addImg");
    imgArr.each(function (i) {
        var img = $(this)[0];
        if (img.name != "") {
            return true;
        }
        var className = "." + img.dataset.class;
        var image = new Image();
        var realWidth = 0;//储存图片实际宽度
        var realHeight = 0;//储存图片实际高度
        var frameWidth;//获取图片div的宽;
        var frameHeight;//获取图片div的高
        image.src = img.src;
        image.name = className;

        //加载成功的处理
        image.onload = function () {
            frameWidth = $(image.name)[0].offsetWidth;
            frameHeight = $(image.name)[0].offsetHeight;
            realWidth = image.width;//获取图片实际宽度
            realHeight = image.height;//获取图片实际高度
            //让img的宽高相当于图片实际宽高的等比缩放，然后再偏移

            img.className = 'img_sm';

            if (realWidth > realHeight) {
                img.width = (frameWidth / realHeight) * realWidth;//等比缩放宽度
                img.height = frameHeight;//跟div高度一致
                img.style.left = '-' + ((frameHeight / realHeight) * realWidth - frameHeight) / 2 + 'px';//设置图片相对自己位置偏移为img标签的宽度-高度的一半
            } else if (realWidth < realHeight) {
                img.width = frameHeight;//跟div高度一致
                img.height = (frameWidth / realWidth) * realHeight;//等比缩放高度
                img.style.top = '-' + ((frameWidth / realWidth) * realHeight - frameWidth) / 2 + 'px';//设置图片相对自己位置偏移为img标签的高度-宽度的一半
            } else {
                img.width = frameWidth;
                img.height = frameHeight;
            }
        };
        //图片加载失败的处理
        img.onerror = function () {
            img.src = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492076382452&di=04ebd6c4688b2ffbd8ae18e685234704&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D0c96dc86da33c895a62b907fe4235fc6%2F0823dd54564e9258d2bb2dff9f82d158ccbf4e17.jpg";
            img.width = 100;
            img.height = 100;
        }
    })
}

function forevery() {
    $("#validDateEnd").remove();
    //$("#controlDiv").empty();
    if (laydateFlag++ % 2 == 1) {
        $("#dateTimeTag").append("<input type=\"text\" style=\"width: 100%;float: right;\" class=\"form-control\" id=\"validDateEnd\" name=\"validDateEnd\" placeholder=\"截止日期\">");
        laydate.render({
            elem: '#validDateEnd' //指定元素
        });
        //$("#controlDiv").append("<a class=\"btn btn-primary\" style=\"float: right\" href=\"javascript:forevery(this);\">永久</a>");
    } else {
        $("#dateTimeTag").append("<input type=\"text\" style=\"width: 100%;float: right;\" class=\"form-control\" id=\"validDateEnd\" name=\"validDateEnd\" placeholder=\"截止日期\" disabled>");
        $("#validDateEnd").val("永久");
        //$("#controlDiv").append("<a class=\"btn btn-primary\" style=\"float: right\" href=\"javascript:forevery(this);\">选择</a>");
    }
}