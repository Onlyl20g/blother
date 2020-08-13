$(function () {
    var options = {
        url: "/withdraw/select/" + $("#billId").val(),
        showSearch: false,
        showRefresh: false,
        showToggle: false,
        showColumns: false,
        pagination: false,
        //height: "800",
        sidePagination: "server",
        columns: [
            {
                field: 'id',
                title: '提款编号',
                align: 'center'
            },
            {
                field: 'creditBillId',
                title: '授信编号',
                align: 'center',
                formatter: function (value, row, index) {
                    return $("#id").val();
                }
            },
            {
                field: 'amount',
                title: '提款金额',
                align: 'center',
                formatter: function (value, row, index) {
                    return outputmoney(value);
                }
            },
            {
                field: 'period',
                title: '提款期限',
                align: 'center'
            },
            {
                field: 'status',
                title: '状态',
                align: 'center',
                formatter: function (value, row, index) {
                    var start;
                    if (value == "auditing") {
                        start = "审核中";
                    } else if (value == "rejection") {
                        start = "<font color='red'>拒绝</font>";
                    } else if (value == "audited") {
                        start = "<font color='#00f000'>通过</font>";
                    }
                    return start;
                }
            },
            {
                field: 'creditStatus',
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    return '<button class="btn btn-sm btn-primary" type="button" onclick = "details(\'' + row.id + '\');" style="font-size: 0.87rem">查看详情</button>';
                }
                // <button class="btn btn-sm btn-primary" type="button" onclick = "details(\'' + row.id + '\');" style="font-size: 0.87rem">查看详情</button>
            }
        ]
    };
    $.table.init(options);
});

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

function details(id) {
    layer.open({
        type: 2,
        title: ' ',
        //            shadeClose: true,
        shade: [0.8, '#393D49'],
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '90%'],
        content: '/withdraw/repaydetails?id=' + id,
        shadeClose: false,
        btn: ["关闭"],
        yes: function (index) {
            layer.close(index);
        }
    });
}
