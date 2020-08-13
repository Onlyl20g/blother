$(function () {
    loadData();
});


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
            $("#factorType").val(data.factorType);
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
}

function loadFileList(tag, fileId) {
    $.post('/credit/selectFile',
        {fileId: fileId},
        function (result) {
            for (var i = 0; i < result.rows.length; i++) {
                var code = "<tr class='col-sm-12'>";
                var suffix = result.rows[i].formName.substr(result.rows[i].formName.indexOf(".") + 1);
                if (suffix === "jpg" || suffix === "png" || suffix === "gif" || suffix === "bmp") {
                    code += "<td name='forName'><img style='width:56px;height:56px;' onclick='openInfo(this.src)' src=\"" + cusstr(location.href, "/", 3) + "/system/file/loadImg?url=" + result.rows[i].urlBillid + "\" alt=''>" + result.rows[i].formName + "</td>";
                } else {
                    code += "<td name='forName'><a href='/system/file/down?url=" + result.rows[i].urlBillid + "&name=" + result.rows[i].formName.split(',')[0] + "'>" + result.rows[i].formName + "</a></td>";
                }
                code += "<td name='createTime' style='text-align: right'>" + result.rows[i].createTime + "</td>";
                code += "</tr>";
                if (result.rows[i].urlBillid == null && result.rows[i].formName === "file") {
                    //code = "查无数据";
                }
                $("#" + tag).append(code);
            }
        });
}

function cusstr(str, findStr, num) {
    var idx = str.indexOf(findStr);
    var count = 1;
    while (idx >= 0 && count < num) {
        idx = str.indexOf(findStr, idx + 1);
        count++;
    }
    if (idx < 0) {
        return '';
    }
    return str.substring(0, idx);
}

function openInfo(src) {
    $("#imgTag").attr("src", src);
    layer.open({
        type: 1,
        title: ' ',
        shadeClose: true,
        shade: [0.8, '#393D49'],
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '80%'],
        content: $("#imgInfo")
    });

}

//遮罩层
function mask() {
    var index = layer.load(1, {
        shade: [0.2, '#ddd']
    });
}