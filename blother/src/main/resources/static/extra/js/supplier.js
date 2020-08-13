/**
 * 初始化
 */

var roleId;

$(function () {
    var panehHidden = false;
    if ($(this).width() < 769) {
        panehHidden = true;
    }
    $('body').layout({initClosed: panehHidden, west__size: 185});
    // if()
    findUserRoles();
});

function findUserRoles() {
    $.ajax({
        url: "/system/user/findUserRole",
        type: "post",
        dataType: "json",
        success: function (result) {
            roleId = result.id;
            // sessionStorage.setItem("role",r.user.roles.roleId)
            queryUserList();
        }
    })
    ;
}

/**
 * 表格设置
 */
function queryUserList() {
    var options = {
        url: prefix + "/listInfo",
        createUrl: prefix + "/add",
        updateUrl: prefix + "/edit/{id}",
        removeUrl: prefix + "/remove",
        exportUrl: prefix + "/export",
        importUrl: prefix + "/importData",
        importTemplateUrl: prefix + "/importTemplate",
        sortName: "createTime",
        sortOrder: "desc",
        modalName: "用户",
        columns: [{
            checkbox: true
        },
            {
                field: 'corprName',
                title: '公司名称'
            },
            {
                field: 'businessLicenseCode',
                title: '营业执照编号'
            },
            {
                field: 'legalPersonName',
                title: '法人姓名'
            },
            {
                field: 'legalPersonPhone',
                title: '手机号码'
            },
            {
                field: 'status',
                title: '用户状态',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return '<span>已启用</span>';
                    } else {
                        return '<span style="color: red;">待审核</span>';
                    }
                }
            },
            {
                field: 'customerManagerName',
                title: '客户经理'
            },
            {
                field: 'createTime',
                title: '创建时间'
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var actions = [];
                    if (roleId == 1) {
                        actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a> ');
                    }
                    actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="javascript:void(0)" onclick="$.operate.editTab(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                    if (row.status == '0') {
                        // actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="javascript:void(0)" onclick="enable(\'' + row.id + '\',\'' + row.businessLicenseCode + '\')"><i class="fa fa-edit"></i>启用</a>');
                        actions.push('<a type="button" class="btn btn-info btn-xs" onclick="resetPwd(\'' + row.id + ',' + row.businessLicenseCode + '\')">重置</a>');
                    }
                    return actions.join('');
                }
            }]
    };
    $.table.init(options);
}

/* 用户管理-重置密码 */
function resetPwd(data) {
    var id = data.split(",")[0];
    var code = data.split(",")[1];
    $.modal.confirm("确认重置用户密码？", function () {
        $.ajax({
            url: "/system/supplier/resetPwdInfo?id=" + id,
            type: "post",
            dataType: "json",
            success: function (r) {
                if (r.code != 500) {
                    if ("success" == r.flag) {
                        $.modal.alertSuccess("用户：<b>" + code + "</b> 的密码<br/>已重设为：<b>" + r.data + "</b>");
                    } else {
                        $.modal.alertError("错误：" + r.data);
                    }
                } else {
                    layer.msg("系统错误，请联系管理人员!");
                }
            }
        })
    });
}