function newcredit() {
    //iframe窗
    layer.open({
        type: 2,
        title: '授信申请',
        shadeClose: false,
        shade: [0.8, '#393D49'],
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '90%'],
        content: 'credit/add'
    });
}

function edit(data) {
    creditID = data;
    //iframe窗
    layer.open({
        type: 2,
        title: '资料补交',
        shadeClose: false,
        shade: [0.8, '#393D49'],
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '90%'],
        content: 'credit/edit'
    });
}

function showCredit(data) {
    creditID = data;
    //iframe窗
    layer.open({
        type: 2,
        title: ' ',
        shadeClose: false,
        shade: [0.8, '#393D49'],
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '90%'],
        content: 'credit/showCredit'
    });
}

$(function () {
    init();
});

function init() {
    var options = {
        url: "credit/listByBillId",
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
                field: 'id',
                title: '授信编号',
                align: 'center',
                sortable: true
            },
            {
                field: 'appCreditAmount',
                title: '申请授信额度（元）',
                align: 'center',
                formatter: function (value, row, index) {
                    return outputmoney(value);
                }
            },
            {
                field: 'appCreditPeriod',
                title: '申请授信期限（月）',
                align: 'center'
            },
            {
                field: 'custManagerName',
                title: '客户经理',
                align: 'center'
            },
            {
                field: 'creditStatus',
                title: '申请授信状态',
                align: 'center',
                formatter: function (value, row, index) {
                    var start;
                    if (value == 0) {
                        start = "<b>审核中</b>";
                    } else if (value == 1) {
                        start = "<b style='color: red'>驳回</b>";
                    } else if (value == 2) {
                        start = "<b style='color: darkgrey'>拒绝</b>";
                    } else if (value == 4) {
                        start = "<b style='color: darkgrey'>过期</b>";
                    } else {
                        start = "<b style='color: #00f000'>审批通过</b>";
                    }
                    return start;
                }
            },
            {
                field: 'createTime',
                title: '申请日期',
                align: 'center',
                formatter: function (value, row, index) {
                    return value.substr(0, 10);
                }
            },
            {
                field: 'creditStatus',
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.creditStatus == 1) {
                        return '<button class="btn btn-sm btn-default" type="button"  onclick="edit(\'' + row.billId + '\')" style="font-size: 0.87rem">资料补交</button>';
                    } else if (row.creditStatus == 0) {
                        return '<button class="btn btn-sm btn-default" type="button" onclick="showCredit(\'' + row.billId + '\')" style="font-size: 0.87rem">&nbsp;&nbsp;&nbsp;查&nbsp;看&nbsp;&nbsp;&nbsp;</button>';
                    } else if (row.creditStatus == 4) {
                        return '';
                    } else if (row.creditStatus == 2) {
                        return '';
                    } else {
                        return '<button class="btn btn-sm btn-default" type="button" onclick="location.href = \'withdraw\'" style="font-size: 0.87rem">&nbsp;&nbsp;&nbsp;提&nbsp;款&nbsp;&nbsp;&nbsp;</button>';
                    }
                }
            }]
    };
    $.table.init(options);
}

function outputmoney(number) {
    if (number == 0) {
        return "0.00";
    }
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