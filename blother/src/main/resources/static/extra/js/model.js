/**
 * 通用js方法封装处理
 * Copyright (c) 2019
 */
(function ($) {
    $.extend({
        // 弹出层封装处理
        modal: {
            // 显示图标
            icon: function (type) {
                var icon = "";
                if (type == modal_status.WARNING) {
                    icon = 0;
                } else if (type == modal_status.SUCCESS) {
                    icon = 1;
                } else if (type == modal_status.FAIL) {
                    icon = 2;
                } else {
                    icon = 3;
                }
                return icon;
            },
            // 消息提示
            msg: function (content, type) {
                if (type != undefined) {
                    layer.msg(content, {icon: $.modal.icon(type), time: 1000, shift: 5});
                } else {
                    layer.msg(content);
                }
            },
            // 错误消息
            msgError: function (content) {
                $.modal.msg(content, modal_status.FAIL);
            },
            // 成功消息
            msgSuccess: function (content) {
                $.modal.msg(content, modal_status.SUCCESS);
            },
            // 警告消息
            msgWarning: function (content) {
                $.modal.msg(content, modal_status.WARNING);
            },
            // 弹出提示
            alert: function (content, type) {
                layer.alert(content, {
                    icon: $.modal.icon(type),
                    title: "系统提示",
                    btn: ['确认'],
                    btnclass: ['btn btn-primary'],
                });
            },
            // 消息提示并刷新父窗体
            msgReload: function (msg, type) {
                layer.msg(msg, {
                        icon: $.modal.icon(type),
                        time: 500,
                        shade: [0.1, '#8F8F8F']
                    },
                    function () {
                        $.modal.reload();
                    });
            },
            // 错误提示
            alertError: function (content) {
                $.modal.alert(content, modal_status.FAIL);
            },
            // 成功提示
            alertSuccess: function (content) {
                $.modal.alert(content, modal_status.SUCCESS);
            },
            // 警告提示
            alertWarning: function (content) {
                $.modal.alert(content, modal_status.WARNING);
            },
            // 关闭窗体
            close: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            // 关闭全部窗体
            closeAll: function () {
                layer.closeAll();
            },
            // 确认窗体
            confirm: function (content, callBack) {
                layer.confirm(content, {
                    icon: 3,
                    title: "系统提示",
                    btn: ['确认', '取消']
                }, function (index) {
                    layer.close(index);
                    callBack(true);
                });
            },
            // 弹出层指定宽度
            open: function (title, url, width, height, callback) {
                //如果是移动端，就使用自适应大小弹窗
                if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50);
                }
                if ($.common.isEmpty(callback)) {
                    callback = function (index, layero) {
                        var iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler(index, layero);
                    }
                }
                layer.open({
                    type: 2,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url,
                    btn: ['关闭'],
                    // 弹层外区域关闭
                    shadeClose: true,
                    yes: '',
                    cancel: function (index) {
                        return true;
                    }
                });
            },
            // 弹出层指定参数选项
            openOptions: function (options) {
                var _url = $.common.isEmpty(options.url) ? "/404.html" : options.url;
                var _title = $.common.isEmpty(options.title) ? "系统窗口" : options.title;
                var _width = $.common.isEmpty(options.width) ? "800" : options.width;
                var _height = $.common.isEmpty(options.height) ? ($(window).height() - 50) : options.height;
                var _btn = ['<i class="fa fa-check" style="display: none"></i>', '<i class="fa fa-close"></i> 关闭'];
                if ($.common.isEmpty(options.yes)) {
                    options.yes = function (index, layero) {
                        options.callBack(index, layero);
                    }
                }
                layer.open({
                    type: 2,
                    maxmin: true,
                    shade: 0.3,
                    title: _title,
                    fix: false,
                    area: [_width + 'px', _height + 'px'],
                    content: _url,
                    shadeClose: true,
                    skin: options.skin,
                    btn: $.common.isEmpty(options.btn) ? _btn : options.btn,
                    yes: options.yes,
                    cancel: function () {
                        return true;
                    }
                });
            },
            // 弹出层全屏
            openFull: function (title, url, width, height) {
                //如果是移动端，就使用自适应大小弹窗
                if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50);
                }
                var index = layer.open({
                    type: 2,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url,
                    btn: ['确定', '关闭'],
                    // 弹层外区域关闭
                    shadeClose: true,
                    yes: function (index, layero) {
                        var iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler(index, layero);
                    },
                    cancel: function (index) {
                        return true;
                    }
                });
                layer.full(index);
            },
            // 选卡页方式打开
            openTab: function (title, url) {
                createMenuItem(url, title);
            },
            // 关闭选项卡
            closeTab: function () {
                closeItem();
            },
            // 禁用按钮
            disable: function () {
                var doc = window.top == window.parent ? window.document : window.parent.document;
                $("a[class*=layui-layer-btn]", doc).addClass("layer-disabled");
            },
            // 启用按钮
            enable: function () {
                var doc = window.top == window.parent ? window.document : window.parent.document;
                $("a[class*=layui-layer-btn]", doc).removeClass("layer-disabled");
            },
            // 打开遮罩层
            loading: function (message) {
                $.blockUI({message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>'});
            },
            // 关闭遮罩层
            closeLoading: function () {
                setTimeout(function () {
                    $.unblockUI();
                }, 50);
            },
            // 重新加载
            reload: function () {
                parent.location.reload();
            }
        },
        // 操作封装处理
        operate: {
            // 提交数据
            submit: function (url, type, dataType, data, callback) {
                var config = {
                    url: url,
                    type: type,
                    dataType: dataType,
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                    },
                    success: function (result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.ajaxSuccess(result);
                    }
                };
                $.ajax(config)
            },
            // post请求传输
            post: function (url, data, callback) {
                $.operate.submit(url, "post", "json", data, callback);
            },
            // get请求传输
            get: function (url, callback) {
                $.operate.submit(url, "get", "json", "", callback);
            },
            // 详细信息
            detail: function (id, width, height) {
                var _url = $.operate.detailUrl(id);
                var options = {
                    title: $.table._option.modalName + "详细",
                    width: width,
                    height: height,
                    url: $.operate.detailUrl(id),
                    skin: 'layui-layer-gray',
                    btn: ['关闭'],
                    yes: function (index, layero) {
                        layer.close(index);
                    }
                };
                $.modal.openOptions(options);
            },
            // 详细访问地址
            detailUrl: function (id) {
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = $.table._option.detailUrl.replace("{id}", id);
                } else {
                    var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
                    if (id.length == 0) {
                        $.modal.alertWarning("请至少选择一条记录");
                        return;
                    }
                    url = $.table._option.detailUrl.replace("{id}", id);
                }
                return url;
            },
            // 删除信息
            remove: function (id) {
                $.modal.confirm("确定删除该条" + $.table._option.modalName + "信息吗？", function () {
                    var url = $.common.isEmpty(id) ? $.table._option.removeUrl : $.table._option.removeUrl.replace("{id}", id);
                    if ($.table._option.type == table_type.bootstrapTreeTable) {
                        $.operate.get(url);
                    } else {
                        var data = {"ids": id};
                        $.operate.submit(url, "post", "json", data);
                    }
                });

            },
            // 批量删除信息
            removeAll: function () {
                var rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
                if (rows.length == 0) {
                    $.modal.alertWarning("请至少选择一条记录");
                    return;
                }
                $.modal.confirm("确认要删除选中的" + rows.length + "条数据吗?", function () {
                    var url = $.table._option.removeUrl;
                    var data = {"ids": rows.join()};
                    $.operate.submit(url, "post", "json", data);
                });
            },
            // 清空信息
            clean: function () {
                $.modal.confirm("确定清空所有" + $.table._option.modalName + "吗？", function () {
                    var url = $.table._option.cleanUrl;
                    $.operate.submit(url, "post", "json", "");
                });
            },
            // 添加信息
            add: function (id) {
                $.modal.open("添加" + $.table._option.modalName, $.operate.addUrl(id));
            },
            // 添加信息，以tab页展现
            addTab: function (id) {
                $.modal.openTab("添加" + $.table._option.modalName, $.operate.addUrl(id));
            },
            // 添加信息 全屏
            addFull: function (id) {
                var url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.modal.openFull("添加" + $.table._option.modalName, url);
            },
            // 添加访问地址
            addUrl: function (id) {
                var url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                return url;
            },
            // 修改信息
            edit: function (id) {
                if ($.common.isEmpty(id) && $.table._option.type == table_type.bootstrapTreeTable) {
                    var row = $.bttTable.bootstrapTreeTable('getSelections')[0];
                    if ($.common.isEmpty(row)) {
                        $.modal.alertWarning("请至少选择一条记录");
                        return;
                    }
                    var url = $.table._option.updateUrl.replace("{id}", row[$.table._option.uniqueId]);
                    $.modal.open("修改" + $.table._option.modalName, url);
                } else {
                    $.modal.open("修改" + $.table._option.modalName, $.operate.editUrl(id));
                }
            },
            // 修改信息，以tab页展现
            editTab: function (id) {
                $.modal.openTab("修改" + $.table._option.modalName, $.operate.editUrl(id));
            },
            // 修改信息 全屏
            editFull: function (id) {
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = $.table._option.updateUrl.replace("{id}", id);
                } else {
                    var row = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
                    url = $.table._option.updateUrl.replace("{id}", row);
                }
                $.modal.openFull("修改" + $.table._option.modalName, url);
            },
            // 修改访问地址
            editUrl: function (id) {
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = $.table._option.updateUrl.replace("{id}", id);
                } else {
                    var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
                    if (id.length == 0) {
                        $.modal.alertWarning("请至少选择一条记录");
                        return;
                    }
                    url = $.table._option.updateUrl.replace("{id}", id);
                }
                return url;
            },
            // 保存信息 刷新表格
            save: function (url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                        $.modal.disable();
                    },
                    success: function (result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.successCallback(result);
                    }
                };
                $.ajax(config)
            },
            // 保存信息 弹出提示框
            saveModal: function (url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                    },
                    success: function (result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        if (result.code == web_status.SUCCESS) {
                            $.modal.alertSuccess(result.msg)
                        } else if (result.code == web_status.WARNING) {
                            $.modal.alertWarning(result.msg)
                        } else {
                            $.modal.alertError(result.msg);
                        }
                        $.modal.closeLoading();
                    }
                };
                $.ajax(config)
            },
            // 保存选项卡信息
            saveTab: function (url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
                    },
                    success: function (result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.successTabCallback(result);
                    }
                };
                $.ajax(config)
            },
            // 保存结果弹出msg刷新table表格
            ajaxSuccess: function (result) {
                if (result.code == web_status.SUCCESS && $.table._option.type == table_type.bootstrapTable) {
                    $.modal.msgSuccess(result.msg);
                    $.table.refresh();
                } else if (result.code == web_status.SUCCESS && $.table._option.type == table_type.bootstrapTreeTable) {
                    $.modal.msgSuccess(result.msg);
                    $.treeTable.refresh();
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            },
            // 成功结果提示msg（父窗体全局更新）
            saveSuccess: function (result) {
                if (result.code == web_status.SUCCESS) {
                    $.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            },
            // 成功回调执行事件（父窗体静默更新）
            successCallback: function (result) {
                if (result.code == web_status.SUCCESS) {
                    var parent = window.parent;
                    if (parent.$.table._option.type == table_type.bootstrapTable) {
                        $.modal.close();
                        parent.$.modal.msgSuccess(result.msg);
                        parent.$.table.refresh();
                    } else if (parent.$.table._option.type == table_type.bootstrapTreeTable) {
                        $.modal.close();
                        parent.$.modal.msgSuccess(result.msg);
                        parent.$.treeTable.refresh();
                    } else {
                        $.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
                    }
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
                $.modal.enable();
            },
            // 选项卡成功回调执行事件（父窗体静默更新）
            successTabCallback: function (result) {
                if (result.code == web_status.SUCCESS) {
                    var topWindow = $(window.parent.document);
                    var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-panel');
                    var $contentWindow = $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow)[0].contentWindow;
                    $.modal.close();
                    $contentWindow.$.modal.msgSuccess(result.msg);
                    $contentWindow.$(".layui-layer-padding").removeAttr("style");
                    if ($contentWindow.$.table._option.type == table_type.bootstrapTable) {
                        $contentWindow.$.table.refresh();
                    } else if ($contentWindow.$.table._option.type == table_type.bootstrapTreeTable) {
                        $contentWindow.$.treeTable.refresh();
                    }
                    $.modal.closeTab();
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            }
        },
        // 通用方法封装处理
        common: {
            // 判断字符串是否为空
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "") {
                    return true;
                }
                return false;
            },
            // 判断一个字符串是否为非空串
            isNotEmpty: function (value) {
                return !$.common.isEmpty(value);
            },
            // 空对象转字符串
            nullToStr: function (value) {
                if ($.common.isEmpty(value)) {
                    return "-";
                }
                return value;
            },
            // 是否显示数据 为空默认为显示
            visible: function (value) {
                if ($.common.isEmpty(value) || value == true) {
                    return true;
                }
                return false;
            },
            // 空格截取
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 比较两个字符串（大小写敏感）
            equals: function (str, that) {
                return str == that;
            },
            // 比较两个字符串（大小写不敏感）
            equalsIgnoreCase: function (str, that) {
                return String(str).toUpperCase() === String(that).toUpperCase();
            },
            // 将字符串按指定字符分割
            split: function (str, sep, maxLen) {
                if ($.common.isEmpty(str)) {
                    return null;
                }
                var value = String(str).split(sep);
                return maxLen ? value.slice(0, maxLen - 1) : value;
            },
            // 字符串格式化(%s )
            sprintf: function (str) {
                var args = arguments, flag = true, i = 1;
                str = str.replace(/%s/g, function () {
                    var arg = args[i++];
                    if (typeof arg === 'undefined') {
                        flag = false;
                        return '';
                    }
                    return arg;
                });
                return flag ? str : '';
            },
            // 指定随机数返回
            random: function (min, max) {
                return Math.floor((Math.random() * max) + min);
            },
            // 判断字符串是否是以start开头
            startWith: function (value, start) {
                var reg = new RegExp("^" + start);
                return reg.test(value)
            },
            // 判断字符串是否是以end结尾
            endWith: function (value, end) {
                var reg = new RegExp(end + "$");
                return reg.test(value)
            },
            // 数组去重
            uniqueFn: function (array) {
                var result = [];
                var hashObj = {};
                for (var i = 0; i < array.length; i++) {
                    if (!hashObj[array[i]]) {
                        hashObj[array[i]] = true;
                        result.push(array[i]);
                    }
                }
                return result;
            },
            // 数组中的所有元素放入一个字符串
            join: function (array, separator) {
                if ($.common.isEmpty(array)) {
                    return null;
                }
                return array.join(separator);
            },
            // 获取form下所有的字段并转换为json对象
            formToJSON: function (formId) {
                var json = {};
                $.each($("#" + formId).serializeArray(), function (i, field) {
                    json[field.name] = field.value;
                });
                return json;
            }
        }
    });
})(jQuery);

/** 表格类型 */
table_type = {
    bootstrapTable: 0,
    bootstrapTreeTable: 1
};

/** 消息状态码 */
web_status = {
    SUCCESS: 0,
    FAIL: 500,
    WARNING: 301
};

/** 弹窗状态码 */
modal_status = {
    SUCCESS: "success",
    FAIL: "error",
    WARNING: "warning"
};