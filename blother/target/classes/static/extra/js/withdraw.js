var creditBillId;
var creditamount;
var currentAmount;

function newadd() {
//        var number = currentAmount.replace(/,/g,"");
    if (currentAmount > 0) {
        layer.open({
            type: 2,
            title: '提款申请',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            maxmin: true, //开启最大化最小化按钮
            area: ['80%', '90%'],
            content: 'withdraw/add'
        });
    } else {
        toastr.error("该笔授信可用额度不足");
    }
}

function newcord(id) {
    //iframe窗
    layer.open({
        type: 2,
        title: '提款申请',
        shadeClose: false,
        shade: [0.8, '#393D49'],
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '90%'],
        content: '/withdraw/drawrecord?id=' + id
    });
}

$(function () {
    $.ajax({
        url: "/credit/listByBillId",
        type: "POST"
    });

    var options = {
        url: "/credit/selectSuccessByBillId",
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
                align: 'center'
            },
            {
                field: 'creditAmount',
                title: '授信额度',
                align: 'center',
                formatter: function (value, row, index) {
                    return outputmoney(value);
                }
            },
            {
                field: 'currentAmount',
                title: '剩余额度',
                align: 'center',
                formatter: function (value, row, index) {
                    return outputmoney(value);
                }
            },
            {
                field: 'creditPeriod',
                title: '授信期限（月）',
                align: 'center'
            },
            {
                field: 'custManagerName',
                title: '客户经理',
                align: 'center'
            },
            {
                field: 'approveTime',
                title: '授信时间',
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
                    return '<button class="btn btn-sm btn-danger" type="button" onclick = "hand(this);newadd();" style="font-size: 0.87rem">提款申请</button><button class="btn btn-sm btn-default" type="button" onclick = "newcord(\'' + row.id + '\');hand(this);" style="font-size: 0.87rem">提款记录</button>';
                }
            }]
    };
    $.table.init(options);
});

function hand(node) {
    creditBillId = node.parentNode.parentNode.childNodes[0].innerHTML;
    creditamount = node.parentNode.parentNode.childNodes[1].innerHTML;
    currentAmount = parseInt(node.parentNode.parentNode.childNodes[2].innerHTML.replace(/,/g, ""));
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
