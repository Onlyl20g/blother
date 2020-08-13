var layerLoadingIndex = 0;
var element = "";
var upload = "";
$(function () {
    layui.use(['upload', 'element'], function () {
        var $ = layui.jquery;
        upload = layui.upload;
        element = layui.element;
        //上传业务申请书
        var businessLicense = upload.render({
            elem: '#businessAppFormUpload',
            url: '/system/file/uploadOther',
            exts: 'doc|docx|wps|zip|rar|7z',
            multiple: false,//是否可以多选
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('businessAppForm', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    removeBusinessAppForm();
                    $("#businessAppForm").val(file.name);
                    $('#businessAppFormView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg1">' + file.name + '</div>');
                    // $('#businessAppFormView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg1"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img "><span onclick="this.parentNode.remove()" style=""></span></div>')
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    $("#file1").val(data.path + ":" + data.size + ":" + data.name);
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeBusinessAppForm();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeBusinessAppForm();
            }
        });
        //企业基本情况介绍
        var fundamentalState = upload.render({
            elem: '#fundamentalStateFormUpload',
            url: '/system/file/uploadOther',
            exts: 'doc|docx|wps|pdf|jpg|jpeg|png|gif|bmp|zip|rar|7z',
            multiple: true,//是否可以多选
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('fundamentalStateForm', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    if ($("#fundamentalStateForm").val() == "") {
                        $("#fundamentalStateForm").val(file.name);
                    } else {
                        $("#fundamentalStateForm").val($("#fundamentalStateForm").val() + "," + file.name);
                    }
                    if (isImg(MySubstr(file.name))) {
                        $('#fundamentalStateView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg2"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                    } else {
                        $('#fundamentalStateView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg2">' + file.name + '</div>')
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
                    removeFundamentalStateForm();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeFundamentalStateForm();
            }
        });
        //公司章程或合伙、联营协议复印件
        var bylaw = upload.render({
            elem: '#bylawFormUpload',
            url: '/system/file/uploadOther',
            exts: 'doc|docx|wps|pdf|jpg|jpeg|png|gif|bmp|zip|rar|7z',
            multiple: true,//是否可以多选
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('bylawForm', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    if ($("#bylawForm").val() == "") {
                        $("#bylawForm").val(file.name);
                    } else {
                        $("#bylawForm").val($("#bylawForm").val() + "," + file.name);
                    }
                    if (isImg(MySubstr(file.name))) {
                        $('#bylawView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg3"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                    } else {
                        $('#bylawView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg3">' + file.name + '</div>')
                    }
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    if ($("#file3").val() == "") {
                        $("#file3").val(data.path + ":" + data.size + ":" + data.name);
                    } else {
                        $("#file3").val($("#file3").val() + "," + data.path + ":" + data.size + ":" + data.name);
                    }
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeBylawForm();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeBylawForm();
            }
        });
        //法定代表人：身份证复印件、履历
        var corporation = upload.render({
            elem: '#corporationFormUpload',
            url: '/system/file/uploadOther',
            exts: 'doc|docx|wps|pdf|jpg|jpeg|png|gif|bmp|zip|rar|7z',
            multiple: true,//是否可以多选
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('corporationForm', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    if ($("#corporationForm").val() == "") {
                        $("#corporationForm").val(file.name);
                    } else {
                        $("#corporationForm").val($("#corporationForm").val() + "," + file.name);
                    }
                    if (isImg(MySubstr(file.name))) {
                        $('#corporationView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg4"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                    } else {
                        $('#corporationView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg4">' + file.name + '</div>')
                    }
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    if ($("#file4").val() == "") {
                        $("#file4").val(data.path + ":" + data.size + ":" + data.name);
                    } else {
                        $("#file4").val($("#file4").val() + "," + data.path + ":" + data.size + ":" + data.name);
                    }
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeCorporationForm();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeCorporationForm();
            }
        });
        //财务负责人：身份证复印件、履历
        var financePrincipal = upload.render({
            elem: '#financePrincipalFormUpload',
            url: '/system/file/uploadOther',
            exts: 'doc|docx|wps|pdf|jpg|jpeg|png|gif|bmp|zip|rar|7z',
            multiple: true,//是否可以多选
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('financePrincipalForm', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    if ($("#financePrincipalForm").val() == "") {
                        $("#financePrincipalForm").val(file.name);
                    } else {
                        $("#financePrincipalForm").val($("#financePrincipalForm").val() + "," + file.name);
                    }
                    if (isImg(MySubstr(file.name))) {
                        $('#financePrincipalView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg5"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                    } else {
                        $('#financePrincipalView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg5">' + file.name + '</div>')
                    }
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    if ($("#file5").val() == "") {
                        $("#file5").val(data.path + ":" + data.size + ":" + data.name);
                    } else {
                        $("#file5").val($("#file5").val() + "," + data.path + ":" + data.size + ":" + data.name);
                    }
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeFinancePrincipalForm();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeFinancePrincipalForm();
            }
        });
        //实际控制人（结婚证、本人及配偶身份证复印件、本人履历）
        var actualController = upload.render({
            elem: '#actualControllerFormUpload',
            url: '/system/file/uploadOther',
            exts: 'doc|docx|wps|pdf|jpg|jpeg|png|gif|bmp|zip|rar|7z',
            multiple: true,//是否可以多选
            accept: 'file',
            xhr: xhrOnProgress,
            progress: function (value) {//上传进度回调 value进度值
                element.progress('actualControllerForm', value + '%');//设置页面进度条
            },
            before: function (obj) {
                layerLoadingIndex = layer.load(2, {time: 30 * 1000}); //遮罩层，time是超时时间
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    if ($("#actualControllerForm").val() == "") {
                        $("#actualControllerForm").val(file.name);
                    } else {
                        $("#actualControllerForm").val($("#actualControllerForm").val() + "," + file.name);
                    }
                    if (isImg(MySubstr(file.name))) {
                        $('#actualControllerView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg6"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                    } else {
                        $('#actualControllerView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg6">' + file.name + '</div>')
                    }
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    if ($("#file6").val() == "") {
                        $("#file6").val(data.path + ":" + data.size + ":" + data.name);
                    } else {
                        $("#file6").val($("#file6").val() + "," + data.path + ":" + data.size + ":" + data.name);
                    }
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeActualControllerForm();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeActualControllerForm();
            }
        });
        //其他补充资料
        var other = upload.render({
            elem: '#otherFormUpload',
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
                    if ($("#otherForm").val() == "") {
                        $("#otherForm").val(file.name);
                    } else {
                        $("#otherForm").val($("#otherForm").val() + "," + file.name);
                    }
                    if (isImg(MySubstr(file.name))) {
                        $('#otherView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg7"><img style="margin: 15px;max-height:200px" width="160px" src="' + result + '" alt="' + file.name + '" class="layui-upload-img " onclick="lookimg(this.src)"><span onclick="this.parentNode.remove()" style=""></span></div>')
                    } else {
                        $('#otherView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg7">' + file.name + '</div>')
                    }
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    if ($("#file7").val() == "") {
                        $("#file7").val(data.path + ":" + data.size + ":" + data.name);
                    } else {
                        $("#file7").val($("#file7").val() + "," + data.path + ":" + data.size + ":" + data.name);
                    }
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeOtherForm();
                }
            },
            error: function () {
                //异常回调
                layer.close(layerLoadingIndex);
                layer.alert("上传失败", {icon: 2});
                removeOtherForm();
            }
        });


    });
    //initFileinputOne("businessAppForm", "file1", "/system/file/upload", "拖放上传业务申请书");
    // initFileinput("fundamentalStateForm", "file2", "/system/file/upload", "拖放上传企业基本情况介绍");
    // initFileinput("bylawForm", "file3", "/system/file/upload", "拖放上传公司章程或合伙、联营协议复印件");
    // initFileinput("corporationForm", "file4", "/system/file/upload", "拖放上传身份证复印件、履历");
    // initFileinput("financePrincipalForm", "file5", "/system/file/upload", "拖放上传身份证复印件、履历");
    // initFileinput("actualControllerForm", "file6", "/system/file/upload", "拖放上传结婚证、本人及配偶身份证复印件、本人履历");
    // initFileinput("otherForm", "file7", "/system/file/upload", "拖放上传其他补充资料");
    $("#saveForm").click(function () {
        valiFormdate();
    });
    $("#addBuy").click(function () {
        if (addbuy()) {
            $("#buyth1").val("");
            $("#buyth2").val("");
            $("#buyth3").val("");
        }
    });
    //$("#radios")[0].children[0].click(); //默认选中一个
    $("#sellerCorporationName").val(sessionStorage.getItem("name"));

    //格式化申请金额
    $("#appCreditAmount").blur(function () {
        if (checkAppCreditAmount(this)) {
            getAppCreditAmountUp();
        } else {
            $("#appCreditAmountUpDiv").css("visibility","hidden");
            $("#appCreditAmountUp").val("");
        }
        //$(this).val(outputmoney($(this).val()));
    });
    findCustomerManager();
});

function getAppCreditAmountUp() {
    if ($("#appCreditAmount").val().length > 0) {
        $("#appCreditAmountUpDiv").css("visibility","visible");
        $("#appCreditAmountUp").text("大写: " + convertCurrency($("#appCreditAmount").val() * 10000));
    } else {
        $("#appCreditAmountUpDiv").css("visibility","hidden");
        $("#appCreditAmountUp").val("");
    }
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
function removeBusinessAppForm() {
    $('.addImg1').remove();
    $("#file1").val("");
    $("#businessAppForm").val("");
    element.progress('businessAppForm', '0%');
}

function removeFundamentalStateForm() {
    $('.addImg2').remove();
    $("#file2").val("");
    $("#fundamentalStateForm").val("");
    element.progress('fundamentalStateForm', '0%');
}

function removeBylawForm() {
    $('.addImg3').remove();
    $("#file3").val("");
    $("#bylawForm").val("");
    element.progress('bylawForm', '0%');
}

function removeCorporationForm() {
    $('.addImg4').remove();
    $("#file4").val("");
    $("#corporationForm").val("");
    element.progress('corporationForm', '0%');
}

function removeFinancePrincipalForm() {
    $('.addImg5').remove();
    $("#file5").val("");
    $("#financePrincipalForm").val("");
    element.progress('financePrincipalForm', '0%');
}

function removeActualControllerForm() {
    $('.addImg6').remove();
    $("#file6").val("");
    $("#actualControllerForm").val("");
    element.progress('actualControllerForm', '0%');
}

function removeOtherForm() {
    $('.addImg7').remove();
    $("#file7").val("");
    $("#otherForm").val("");
    element.progress('otherForm', '0%');
}

//清空上传框 end
//增加一行买方企业
function addbuy() {
    if ($("#buyth1").val() == "") {
        toastr.error("请输入企业名称");
        return false;
    }
    // if ($("#buyth2").val() == "") {
    //     toastr.error("请输入联系人名称");
    //     return false;
    // }
    if ($("#buyth3").val() != "") {
        if (!isMobile($("#buyth3").val())) {
            toastr.error("请输入正确的联系人电话");
            return false;
        }
    }
    var newPhone = $("#buyth3").val();
    var newCompany = $("#buyth1").val();
    var flag1 = 0;
    if (newPhone != "") {
        $(".phone").each(function () {
            if (newPhone == this.innerText) {
                flag1 = 10086;
                return false;
            }
        });
        if (flag1 == 10086) {
            toastr.error("已有相同的联系人电话");
            return false;
        }
    }
    if (newCompany != "") {
        $(".comp").each(function () {
            if (newCompany == this.innerText) {
                flag1 = 1;
                return false;
            }
        });
    }
    if (flag1 == 1) {
        toastr.error("已有相同的联系人");
        return false;
    }
    $("#buyer").show();
    var code = "<tr>";
    code += "<td class='comp' style='vertical-align: middle'>" + $("#buyth1").val() + "</td>";
    code += "<td class='per' style='vertical-align: middle'>" + $("#buyth2").val() + "</td>";
    code += "<td class='phone' style='vertical-align: middle'>" + $("#buyth3").val() + "</td>";
    code += "<td><button type='button' onclick='this.parentNode.parentNode.remove();' class='btn btn-primary'>删除</button></td>";
    code += "</tr>";
    $("#buyBody").append(code);
    return true;
}

//初始化文件上传框方法
function initFileinput(id, tag, url, msg) {
    $("#" + id).fileinput({
        'theme': 'explorer-fas',
        'uploadUrl': url,
        contentType: false,
        dropZoneTitle: msg,
        overwriteInitial: false,
        initialPreviewAsData: false,
        showUpload: false,
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
    }).on("fileclear", function (event, data, msg) {//删除、移除文件回调
        $("#" + tag).val("");
    }).on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    });
}

//初始化文件上传框方法只能传一个
function initFileinputOne(id, tag, url, msg) {
    $("#" + id).fileinput({
        'uploadUrl': url,
        dropZoneTitle: msg,
        showUpload: false,
        showCaption: true,
        browseClass: "btn btn-primary btn-lg",
        allowedFileExtensions: ['doc', 'docx', 'wps'],
        msgInvalidFileExtension: "允许上传的文件类型有：doc，docx，wps",
        layoutTemplates: {
            actionDelete: '',    // 预览区域的删除按钮
            actionUpload: '', // 预览区域的上传按钮
            actions: ''
        },
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i><i class='glyphicon glyphicon-king'></i>",
        maxFileCount: 1 //每次上传允许的最大文件数。如果设置为0，则表示允许的文件数是无限制的。默认为0
        // initialPreview: [//初始化时加载
        //     "/img/profile.jpg"
        // ],
    }).on("fileuploaded", function (event, data, previewId, index) {
        $("#" + tag).val(data.response.path);
    }).on("fileclear", function (event, data, msg) {//删除、移除文件回调
        $("#" + tag).val("");
    }).on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    });
}

//提交表单方法
function commit() {
    if ($("#appCreditAmount").val() == 0) {
        toastr.error("不能申请0元的授信");
        return false;
    }
    layerLoadingIndex = layer.load(1, {
        shade: [0.1, '#fff']
    });
    mask();
    var appCreditAmount = $("#appCreditAmount").val() * 10000;//以前用的元为单位，现在是万元
    var appCreditLife = $("#appCreditLife").val();
    var sellerCorporationName = $("#sellerCorporationName").val();
    var sellerPerson = $("#sellerPerson").val();
    var customerManagerId = $("#customerManagerName").val();
    var customerManagerName = $("#customerManagerName")[0].options[$("#customerManagerName")[0].selectedIndex].text;
    var sellerPersonTel = $("#sellerPersonTel").val();
    var factorType = $("#factorType").val();
    var businessAppForm = $("#file1").val();
    var fundamentalStateForm = $("#file2").val();
    var bylawForm = $("#file3").val();
    var corporationForm = $("#file4").val();
    var financePrincipalForm = $("#file5").val();
    var actualControllerForm = $("#file6").val();
    var otherForm = $("#file7").val();
    var businessLicenseCode = sessionStorage.getItem("username");
    var corprName = sessionStorage.getItem("name");
    var guaranteeMode = "";//$("input[name='guaranteeMode']:checked").val();
    $("input:checkbox:checked").each(function () {
        guaranteeMode += this.value + ",";
    });
    guaranteeMode = guaranteeMode.substring(0, guaranteeMode.length - 1);
    var data = "{\"appCreditAmount\":\"" + appCreditAmount
        + "\",\"appCreditLife\":\"" + appCreditLife
        + "\",\"sellerCorporationName\":\"" + sellerCorporationName
        + "\",\"sellerPerson\":\"" + sellerPerson
        + "\",\"sellerPersonTel\":\"" + sellerPersonTel
        + "\",\"factorType\":\"" + factorType
        + "\",\"businessAppForm\":\"" + businessAppForm
        + "\",\"fundamentalStateForm\":\"" + fundamentalStateForm
        + "\",\"bylawForm\":\"" + bylawForm
        + "\",\"corporationForm\":\"" + corporationForm
        + "\",\"financePrincipalForm\":\"" + financePrincipalForm
        + "\",\"actualControllerForm\":\"" + actualControllerForm
        + "\",\"otherForm\":\"" + otherForm
        + "\",\"guaranteeMode\":\"" + guaranteeMode
        + "\",\"businessLicenseCode\":\"" + businessLicenseCode
        // + "\",\"billId\":\"" + billId
        + "\",\"corprName\":\"" + corprName
        + "\",\"customerManagerId\":\"" + customerManagerId
        + "\",\"customerManagerName\":\"" + customerManagerName
        + "\"}";
    $.ajax({
        url: "/credit/save",
        type: "POST",
        data: data,
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.code == 500) {
                toastr.error("提交申请失败");
                layer.closeAll();
            } else {
                if (data.status = 0) {
                    toastr.error("提交申请失败");
                    layer.closeAll();
                } else {
                    saveCredits(data);
                }
            }
            // alert(data.status);
        },
        error: function () {
            toastr.error("提交申请失败");
            layer.closeAll();
        }
    });
}

//保存授信申请
function saveCredits(data) {
    var billId = data.id;
    var trList = $("#buyBody").children("tr");
    //var cridits = "{\"data\":";
    var cridits = "[";
    for (var i = 0; i < trList.length; i++) {
        cridits += "{";
        var tdArr = trList.eq(i).find("td");
        var buyerCorprName = tdArr.eq(0).text();//买方企业名称
        var buyerContactName = tdArr.eq(1).text();//买方联系人
        var buyerContactTel = tdArr.eq(2).text();//买方联系人电话
        cridits += "\"buyerCorprName\":\"" + buyerCorprName + "\",";
        cridits += "\"buyerContactName\":\"" + buyerContactName + "\",";
        cridits += "\"buyerContactTel\":\"" + buyerContactTel + "\",";
        cridits += "\"billId\":\"" + billId + "\"";
        cridits += "}";
        //	cridits += "}]";
        if (i < trList.length - 1) {
            cridits += ",";
        }
    }
    cridits += "]";
    $.ajax({
        url: "/credit/saveCredits",
        type: "POST",
        data: cridits,
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.msg == "suc") {
                toastr.success("提交申请成功");
                setTimeout(function () {
                    parent.location.reload();
                    layer.closeAll();
                }, 3000);
            } else {
                toastr.error("提交申请失败");
                layer.closeAll();
            }
        },
        error: function () {
            toastr.error("提交申请失败");
            layer.closeAll();
        }
    });
}

//验证表单
function valiFormdate() {
    if ($("#appCreditAmount").val() == null || $("#appCreditAmount").val().length < 1) {
        toastr.error("请输入申请额度");
        $("#appCreditAmount")[0].focus();
        return false;
    }
    if ($("#customerManagerName").val() == null || $("#customerManagerName").val().length < 1) {
        toastr.error("请选择客户经理");
        $("#customerManagerName")[0].focus();
        return false;
    }
    if ($("#appCreditLife").val() == null || $("#appCreditLife").val().length < 1) {
        toastr.error("请输入申请额度期限");
        $("#appCreditLife")[0].focus();
        return false;
    }
    if ($("#sellerCorporationName").val() == null || $("#sellerCorporationName").val().length < 1) {
        toastr.error("请输入卖方企业名称");
        $("#sellerCorporationName")[0].focus();
        return false;
    }
    if ($("#sellerPerson").val() == null || $("#sellerPerson").val().length < 1) {
        toastr.error("请输入卖方联系人");
        $("#sellerPerson")[0].focus();
        return false;
    }
    if ($("#sellerPersonTel").val() == null || $("#sellerPersonTel").val().length < 1) {
        toastr.error("请输入卖方联系人电话");
        $("#sellerPersonTel")[0].focus();
        return false;
    }
    if ($(".comp").length == 0) {
        toastr.error("请添加买方联系人");
        $("#buyth2")[0].focus();
        return false;
    }
    if (!checkGuaranteeModeChecked()) {
        toastr.error("请至少选择一种担保方式");
        $("#guaranteeModes")[0].focus();
        return false;
    }
    if ($("#file1").val() == null || $("#file1").val().length < 1) {
        toastr.error("请上传业务申请书");
        $("#businessAppForm")[0].focus();
        return false;
    }
    if ($("#file2").val() == null || $("#file2").val().length < 1) {
        toastr.error("请上传企业基本情况介绍");
        $("#fundamentalStateForm")[0].focus();
        return false;
    }
    if ($("#file3").val() == null || $("#file3").val().length < 1) {
        toastr.error("请上传公司章程或合伙、联营协议复印件");
        $("#bylawForm")[0].focus();
        return false;
    }
    if ($("#file4").val() == null || $("#file4").val().length < 1) {
        toastr.error("请上传法定代表人：身份证复印件、履历");
        $("#corporationForm")[0].focus();
        return false;
    }
    if ($("#file5").val() == null || $("#file5").val().length < 1) {
        toastr.error("请上传财务负责人：身份证复印件、履历");
        $("#financePrincipalForm")[0].focus();
        return false;
    }
    if ($("#file6").val() == null || $("#file6").val().length < 1) {
        toastr.error("请上传实际控制人(结婚证、本人及配偶身份证复印件、本人履历)");
        $("#actualControllerForm")[0].focus();
        return false;
    }
    commit();
}

//手机号验证
function isMobile(s) {
    var patrn = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
    return !!patrn.exec(s);
}

//验证是否勾选至少一个担保方式
function checkGuaranteeModeChecked() {
    return $("input:checkbox:checked").size() > 0 ? true : false;
}

//遮罩层
function mask() {
    var index = layer.load(1, {
        shade: [0.2, '#ddd']
    });
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

function unMoney(str) {
    if (str.indexOf(".00") != -1) {
        str = str.substr(0, str.length - 3);
        return str.replace(new RegExp(",", "gm"), "");
    } else {
        return str;
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

function findCustomerManager() {
    $.ajax({
        url: "/system/supplier/findCustomerManager",
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