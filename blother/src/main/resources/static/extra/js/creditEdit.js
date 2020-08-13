var upload = "";
var element = "";
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
                    $('#businessAppFormView').append('<div style="border: 2px; border-color: #000;display: inline" class="addImg1"><div style="margin: 15px;max-height:200px" width="160px">"' + file.name + '" </div><span onclick="this.parentNode.remove()" style=""></span></div>')
                });
            },
            done: function (data, index, upload) {
                layer.close(layerLoadingIndex);
                //上传完毕
                if (data.path != "") {
                    $("#file1").val(data.path+":"+data.size+":"+data.name);
                } else {
                    layer.alert("上传失败", {icon: 2});
                    removeFundamentalStateForm();
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
                        $("#file2").val(data.path+":"+data.size+":"+data.name);
                    } else {
                        $("#file2").val($("#file2").val() + "," + data.path+":"+data.size+":"+data.name);
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
                        $("#file3").val(data.path+":"+data.size+":"+data.name);
                    } else {
                        $("#file3").val($("#file3").val() + "," + data.path+":"+data.size+":"+data.name);
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
                        $("#file4").val(data.path+":"+data.size+":"+data.name);
                    } else {
                        $("#file4").val($("#file4").val() + "," + data.path+":"+data.size+":"+data.name);
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
                        $("#file5").val(data.path+":"+data.size+":"+data.name);
                    } else {
                        $("#file5").val($("#file5").val() + "," + data.path+":"+data.size+":"+data.name);
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
                        $("#file6").val(data.path+":"+data.size+":"+data.name);
                    } else {
                        $("#file6").val($("#file6").val() + "," + data.path+":"+data.size+":"+data.name);
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
                        $("#file7").val(data.path+":"+data.size+":"+data.name);
                    } else {
                        $("#file7").val($("#file7").val() + "," + data.path+":"+data.size+":"+data.name);
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
    // initFileinput("businessAppForm", "file1", "/system/file/upload", "拖放上传业务申请书");
    // initFileinput("fundamentalStateForm", "file2", "/system/file/upload", "拖放上传企业基本情况介绍");
    // initFileinput("bylawForm", "file3", "/system/file/upload", "拖放上传公司章程或合伙、联营协议复印件");
    // initFileinput("corporationForm", "file4", "/system/file/upload", "拖放上传身份证复印件、履历");
    // initFileinput("financePrincipalForm", "file5", "/system/file/upload", "拖放上传身份证复印件、履历");
    // initFileinput("actualControllerForm", "file6", "/system/file/upload", "拖放上传结婚证、本人及配偶身份证复印件、本人履历");
    // initFileinput("otherForm", "file7", "/system/file/upload", "拖放上传其他补充资料");
    $("#commit").click(function () {
        commit();
    });
    loadData();
});

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
        if ($("#" + tag).val() == "") {
            $("#" + tag).val(data.response.path);
        } else {
            $("#" + tag).val($("#" + tag).val() + "," + data.response.path);
        }
    }).on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    }).on("fileclear", function (event, data, msg) {//删除、移除文件回调
        $("#" + tag).val("");
    }).on('filesuccessremove', function (event, id) {
    });
}

function loadData() {
    //设置无法修改checkbox
    var doCheck = $("input:checkbox");
    doCheck.each(function () {
        $(this).click(function () {
            return false;
        });
    });
    //加载数据
    $.post('/credit/selectByBillId',
        {billId: parent.creditID},
        function (result) {
            var data = result.rows[0];
            $("#appCreditAmount").val(data.appCreditAmount);
            $("#appCreditLife").val(data.appCreditPeriod);
            $("#sellerCorporationName").val(data.sellerCorprName);
            $("#sellerPerson").val(data.sellerContactName);
            $("#sellerPersonTel").val(data.sellerContactTel);
            if (data.factorType == "10010F100000000GSBRS") {
                $("#factorType").val("有追索权");
            } else if (data.factorType == "10010F100000000GSBRT") {
                $("#factorType").val("无追索权");
            }
            if (data.businessAppPath != "" && data.businessAppPath != null) {
                loadFileList("businessAppFormList", data.businessAppPath);
                $("#businessAppFormBtn").show();
            }
            if (data.fundamentalStatePath != "" && data.fundamentalStatePath != null) {
                loadFileList("fundamentalStateFormList", data.fundamentalStatePath);
                $("#fundamentalStateFormBtn").show();
            }
            if (data.bylawPath != "" && data.bylawPath != null) {
                loadFileList("bylawFormList", data.bylawPath);
                $("#bylawFormBtn").show();
            }
            if (data.corprPath != "" && data.corprPath != null) {
                loadFileList("corporationFormList", data.corprPath);
                $("#corporationFormBtn").show();
            }
            if (data.financePrincipalPath != "" && data.financePrincipalPath != null) {
                loadFileList("financePrincipalFormList", data.financePrincipalPath);
                $("#financePrincipalFormBtn").show();
            }
            if (data.actualControllerPath != "" && data.actualControllerPath != null) {
                loadFileList("actualControllerFormList", data.actualControllerPath);
                $("#actualControllerFormBtn").show();
            }
            if (data.additionFilePath != "" && data.additionFilePath != null) {
                loadFileList("otherFormList", data.additionFilePath);
                $("#otherFormBtn").show();
            }

            var temp = new Array();
            temp = data.guaranteeMode.split(",");
            for (var i = 0; i < temp.length; i++) {
                doCheck.each(function () {
                    if (temp[i] == this.value) {
                        $(this).prop("checked", true);
                    }
                });
            }
        });
    //加载买方数据
    $.post('/system/creditBuyerInfo/selectBuy',
        {billId: parent.creditID},
        function (result) {
            for (var i = 0; i < result.rows.length; i++) {
                var code = "<tr>";
                code += "<td name='comp'>" + result.rows[i].buyerCorprName + "</td>";
                if (result.rows[i].buyerContactName != null && result.rows[i].buyerContactName != "") {
                    code += "<td name='per'>" + result.rows[i].buyerContactName + "</td>";
                } else {
                    code += "<td name='per'></td>";
                }
                if (result.rows[i].buyerContactTel != null && result.rows[i].buyerContactTel != "") {
                    code += "<td name='phone'>" + result.rows[i].buyerContactTel + "</td>";
                } else {
                    code += "<td name='per'></td>";
                }
                // code += "<td name='phone'>" + result.rows[i].buyerContactTel + "</td>";
                code += "</tr>";
                $("#buyBody").append(code);
            }
        });
}

function loadFileList(tag, fileId) {
    $.post('/credit/selectFile',
        {fileId: fileId},
        function (result) {
            for (var i = 0; i < result.rows.length; i++) {
                var code = "<tr>";
                code += "<td name='forName'><a href='/system/file/down?url=" + result.rows[i].url + "&name=" + result.rows[i].formName.split(',')[0] + "'>" + result.rows[i].formName + "</a></td>";
                code += "<td name='createTime' style='text-align: right'>" + result.rows[i].createTime + "</td>";
                code += "</tr>";
                $("#" + tag).append(code);
            }
        });
}

//提交表单方法
function commit() {
    layerLoadingIndex = layer.load(1, {
        shade: [0.1, '#fff']
    });
    mask();
    var billId = parent.creditID;
    var businessAppForm = $("#file1").val();
    var fundamentalStateForm = $("#file2").val();
    var bylawForm = $("#file3").val();
    var corporationForm = $("#file4").val();
    var financePrincipalForm = $("#file5").val();
    var actualControllerForm = $("#file6").val();
    var otherForm = $("#file7").val();
    var guaranteeMode = "";
    $("input:checkbox:checked").each(function () {
        guaranteeMode += this.value + ",";
    });
    guaranteeMode = guaranteeMode.substring(0, guaranteeMode.length - 1);
    var data = "{"
        + "\"businessAppForm\":\"" + businessAppForm
        + "\",\"fundamentalStateForm\":\"" + fundamentalStateForm
        + "\",\"bylawForm\":\"" + bylawForm
        + "\",\"corporationForm\":\"" + corporationForm
        + "\",\"financePrincipalForm\":\"" + financePrincipalForm
        + "\",\"actualControllerForm\":\"" + actualControllerForm
        + "\",\"otherForm\":\"" + otherForm
        + "\",\"billId\":\"" + billId
        + "\"}";
    $.ajax({
        url: "/credit/saveEdit",
        type: "POST",
        data: data,
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            toastr.success("提交申请成功!!");
            setTimeout(function () {
                parent.location.reload();
                layer.closeAll();
            }, 3000);
        },
        error: function () {
            toastr.error("提交申请失败!!");
            layer.closeAll();
        }
    });
}

//遮罩层
function mask() {
    var index = layer.load(1, {
        shade: [0.2, '#ddd']
    });
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