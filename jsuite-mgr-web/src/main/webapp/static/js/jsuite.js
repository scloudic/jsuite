$(function () {
    $.extend({
        options: {},
        ztreeOptions: {},
        btnTable: {},
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
            },// 判断移动端
            isMobile: function () {
                return navigator.userAgent.match(/(Android|iPhone|SymbianOS|Windows Phone|iPad|iPod)/i);
            },
            // 获取form下所有的字段并转换为json对象
            formToJSON: function (formId) {
                var json = {};
                $.each($("#" + formId).serializeArray(), function (i, field) {
                    if (json[field.name]) {
                        json[field.name] += ("," + field.value);
                    } else {
                        json[field.name] = field.value;
                    }
                });
                return json;
            },
            strToJSON: function (str) {
                var json = $.parseJSON(str);
                return json;
            },
            //如果子集为空删除children字段,ztree
            deleteChildren: function (arr) {
                var childes = arr;
                for (let i = childes.length; i--; i > 0) {
                    if (childes[i].children) {
                        if (childes[i].children.length) {
                            this.deleteChildren(childes[i].children)
                        } else {
                            delete childes[i].children
                        }
                    }
                }
                return arr
            }
        },
        closeCurrPage: function () {
            var parent = $(window.parent.document);
            $('.page-tabs-content', parent).find('.active i').trigger("click");
        },
        form: {
            getData: function (formId) {
                return $("#" + formId).serialize();
            },
            validate: function (formId, rules) {
                return $('#' + formId).validate({
                    rules: rules,
                }).form();
            },
            mobileValidator: function () {
                $.validator.addMethod('mobile', function (value, element) {
                    return this.optional(element) || /^1\d{10}$/.test(value);
                }, '手机号码格式不正确');
            },
            clearForm: function () {
                $("form").each(function () {
                    this.reset();
                });
                $("input").filter(".error").each(function () {
                    $(this).removeClass('error');
                });

                $("label[class='error']").each(function () {
                    $(this).remove();
                });
            },
        },
        postAjax: function (url, data, callback, failCallback) {
            $.ajax({
                url: url,
                type: "post",
                dataType: "json",
                contentType : "application/json",
                data: JSON.stringify(data),
                beforeSend: function (XMLHttpRequest) {
                    XMLHttpRequest.setRequestHeader("ClientSource", "PC");
                },
                success: function (data, textStatus, XMLHttpRequest) {
                    $.unblockUI();
                    let status = data.status;
                    if (status != 200) {
                        $.respError(data.status, data.message);
                        if (failCallback) {
                            failCallback(data.status, data.message);
                        }
                    } else {
                        callback(data.status, data.data, data.message);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.unblockUI();
                    toastr.error(errorThrown);
                    // swal({title: '', text: errorThrown, icon: 'error'});

                }
            });
        },
        fileAjax: function (url, data, callback) {
            $.ajax({
                url: url,
                type: "post",
                dataType: "json",
                contentType: false,
                processData: false,
                data: data,
                beforeSend: function (XMLHttpRequest) {
                    XMLHttpRequest.setRequestHeader("ClientSource", "PC");
                },
                success: function (data, textStatus, XMLHttpRequest) {
                    $.unblockUI();
                    let status = data.status;
                    if (status != 200) {
                        $.respError(data.status, data.message);
                    } else {
                        callback(data.status, data.data, data.message);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.unblockUI();
                    toastr.error(errorThrown);
                    // swal({title: '', text: errorThrown, icon: 'error'});

                }
            });
        },
        respError: function (status, message) {
            $.unblockUI();
            if (status == 4) {
                toastr.error('用户或密码错误');
            } else {
                toastr.error(message);
            }
        },
        getAjax: function (url, callback) {
            $.ajax({
                url: url,
                type: "GET",
                dataType: "json",
                beforeSend: function (XMLHttpRequest) {
                    XMLHttpRequest.setRequestHeader("ClientSource", "PC");
                },
                success: function (data, textStatus, XMLHttpRequest) {
                    $.unblockUI();
                    let status = data.status;
                    if (status != 200) {
                        $.respError(data.status, data.message);
                    } else {
                        callback(data.status, data.data, data.message);
                    }
                },
                error: function (status, message, XMLHttpRequest, errorThrown) {
                    $.unblockUI();
                    toastr.error(errorThrown);
                }
            });
        },
        loading: function (message) {
            if ($.common.isEmpty(message)) {
                message = '加载中....';
            }
            $.blockUI({
                message: '<div id="loading"><img class="img-loading"/>' +
                    '' + message + '</div>', css: {width: "15%"}
            });
        },
        menuChangeIcon: function (obj) {
            let thisObj = $(obj);
            let navIcon = thisObj.find(".nav-icon");
            let clazzValue = navIcon.attr("class");
            if (clazzValue.indexOf("fa-folder-open") != -1) {
                navIcon.removeClass("fa-folder-open").addClass("fa-folder");
            } else {
                navIcon.removeClass("fa-folder").addClass("fa-folder-open");
            }
        },
        modal: {
            dialog: function (title, contentId, width, height, callback) {
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 20);
                }
                layer.open({
                    type: 1,
                    title: title,
                    maxmin: true,
                    shadeClose: false,
                    btn: ['确定', '关闭'],
                    area: [width + 'px', height + 'px'],
                    content: $("#" + contentId),
                    yes: function (index, layero) {
                        var r = callback();
                        if (r) {
                            layer.close(index);
                        }
                    },
                    cancel: function (index) {
                        return true;
                    },
                });
            },
            dialogIframe: function (title, url, width, height, callback) {
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 20);
                }
                layer.open({
                    type: 2,
                    title: title,
                    maxmin: true,
                    shadeClose: false,
                    btn: ['确定', '关闭'],
                    area: [width + 'px', height + 'px'],
                    content: url,
                    yes: function (index, layero) {
                        var r = callback(index, layero);
                        if (r) {
                            layer.close(index);
                        }
                    },
                    cancel: function (index) {
                        return true;
                    },
                });
            },
            confirm: function (title, callback) {
                layer.confirm(title, {icon: 3, title: '提示'}, function (index) {
                    callback();
                    layer.close(index);
                });
            },
        },
        unFullLoad: function () {
            let obj = $("#full-loading");
            if (obj.length > 0) {
                setTimeout(function () {
                    obj.fadeOut(function () {
                        obj.remove();
                    })
                }, 1000);
            }
        },
        formatDate: function (val, format) {
            if (format == undefined) {
                format = 'YYYY-MM-DD HH:mm:ss';
            }
            return moment(val).format(format);
        },
        treeTable: {
            load: function (options) {
                var defaults = {
                    id: 'menuTreeTable',
                    toolbar: "toolbar",
                    height: 0,
                    delUrl: "",
                    menuId: "id",
                    delKey: "id",
                    parentMenuId: "parentId",
                    expandColumn: 0,
                    type: 'GET',
                    url: null,
                    rootIdValue: 1,
                    expandAll: false,
                    expandFirst: false,
                    ajaxParams: {},
                    data: []
                };
                options = $.extend(defaults, options);
                $.options = options;
                $.btnTable = $("#" + options.id).bootstrapTreeTable({
                    id: options.menuId,
                    rootIdValue: options.rootIdValue,
                    parentId: options.parentMenuId,
                    height: options.height,
                    type: 'GET',
                    data: options.data,
                    toolbar: "#" + options.toolbar,
                    expandColumn: options.expandColumn,
                    ajaxParams: options.ajaxParams,
                    columns: options.columns,
                    url: options.url,
                    expandAll: options.expandAll,
                    expandFirst: options.expandFirst,
                });
            },
            search: function (formId) {
                var searchParams = {};
                if (formId != null) {
                    searchParams = $("#" + formId).serialize();
                }
                $.btnTable.bootstrapTreeTable('refresh', searchParams);
            },
            getSelection: function (id) {
                var value = $.btnTable.bootstrapTreeTable('getSelection', id);
                return value;
            },
            del: function (id, callback) {
                var params = {};
                 params[$.options.delKey]=id;
                //var params = $.options.delKey + "=" + id;
                $.modal.confirm('是否确认删除', function () {
                    $.postAjax($.options.delUrl, params, function () {
                        $.treeTable.search();
                        if (callback != null) {
                            callback();
                        }
                    });
                });
            }
        },
        table: {
            load: function (options) {
                var defaults = {
                    formId: null,
                    id: 'table',
                    cache: false,
                    delKey: "",
                    uniqueId: "",
                    delUrl: "",
                    pagination: true,   //是否显示分页（*）
                    sortable: true,     //是否启用排序
                    showColumns: true,  //是否显示所有的列（选择显示的列）
                    showRefresh: true,  //是否显示刷新按钮
                    sidePagination: "server",
                    minimumCountColumns: 2,             //最少允许的列数
                    pageNumber: 1,                                      // 初始化加载第一页，默认第一页
                    columns: [],
                    buttonsClass: 'btn btn-default btn-outline',
                    loadingFontSize: 12,
                    toolbar: 'toolbar',
                    pageSize: 10,
                    params: {},
                    url: "",
                    strictSearch: true,
                    showToggle: true, //是否显示详细视图和列表视图的切换按钮
                    cardView: false,                    //是否显示详细视图
                    detailView: false,                  //是否显示父子表
                    queryParams: $.table.queryParams,
                    responseHandler: $.table.responseHandler,           // 在加载服务器发送来的数据之前处理函数
                };
                options = $.extend(defaults, options);
                $.options = options;
                $.btnTable = $('#' + options.id).bootstrapTable({
                    cache: options.cache,
                    pagination: options.pagination,
                    sortable: options.sortable,
                    showColumns: options.showColumns,
                    showRefresh: options.showRefresh,
                    sidePagination: options.sidePagination,
                    minimumCountColumns: options.minimumCountColumns,
                    strictSearch: options.strictSearch,
                    showToggle: options.showToggle,
                    toolbar: "#" + options.toolbar,
                    cardView: options.cardView,
                    detailView: options.detailView,
                    queryParams: options.queryParams,
                    responseHandler: options.responseHandler,
                    url: options.url,
                    uniqueId: options.uniqueId,
                    loadingFontSize: options.loadingFontSize,
                    columns: options.columns,
                    pageSize: options.pageSize,
                    buttonsClass: options.buttonsClass,
                });
            },
            responseHandler: function (res) {
                if (res.status == 200) {
                    return {rows: res.data.datas, total: res.data.totalRecord};
                } else {
                    $.respError(res.status, res.message);
                    return {rows: [], total: 0};
                }
            },
            queryParams: function (params) {
                var curParams = {};
                if ($.options.pagination) {
                    curParams = {
                        // 传递参数查询参数
                        pageSize: params.limit,
                        pageNum: (params.offset / params.limit) + 1,
                    };
                }
                curParams = $.extend(curParams, params);
                curParams = $.extend(curParams, $.options.params);
                delete curParams["limit"];
                delete curParams["offset"];
                return curParams;
            },
            search: function (formId) {
                var searchParams = {};
                if (formId != null) {
                    searchParams = $.common.formToJSON(formId);
                }
                $.options.params = searchParams;
                $.btnTable.bootstrapTable('refresh', {pageNumber: $.options.pageNumber, pageSize: $.options.pageSize});
            },
            searchByParams: function (params) {
                $.options.params = params;
                $.btnTable.bootstrapTable('refresh', {pageNumber: $.options.pageNumber, pageSize: $.options.pageSize});
            },
            getSelection: function (id) {
                var value = $.btnTable.bootstrapTable('getRowByUniqueId', id);
                return value;
            },
            del: function (id, callback) {
                var params = $.options.delKey + "=" + id;
                $.modal.confirm('是否确认删除', function () {
                    $.postAjax($.options.delUrl, params, function () {
                        $.table.search($.options.formId);
                        if (callback != null) {
                            callback();
                        }
                    });
                });
            }
        },
        tree: {
            initConfig: function (options) {
                var defaults = {
                    showId: "menuTree",
                    isCheckBox: false,
                    idKey: "",
                    pIdKey: "",
                    rootPidId: "1",
                    checkedKey: "checked",
                    childrenKey: "children",
                    isParentKey: "isParent",
                    isHiddenKey: "isHidden",
                    nameKey: "",
                    urlKey: "",
                    callBackClick: null,
                    chkStyle: "checkbox",
                    radioType: "all",
                    async: {}
                };

                options = $.extend(defaults, options);
                $.ztreeOptions = options;
                var setting = {
                        view: {
                            selectedMulti: false
                        },
                        check: {
                            enable: options.isCheckBox,
                            chkStyle: options.chkStyle,
                            radioType: options.radioType,
                        },
                        async: options.async,
                        callback: {
                            onClick: options.callBackClick,
                            onAsyncSuccess: options.onAsyncSuccess,
                        },
                        data: {
                            simpleData: {
                                enable: false,
                                idKey: options.idKey,
                                pIdKey: options.pIdKey,
                                rootPidId: options.rootPidId,
                            },
                            key: {
                                checked: options.checkedKey,
                                name: options.nameKey,
                                url: options.urlKey,
                                children: options.childrenKey,
                                isParent: options.isParentKey,
                                isHidden: options.isHiddenKey,
                            },
                        },
                    }
                ;
                return setting;
            },
            treeDataLoad: function (options, data) {
                var setting = this.initConfig(options);
                var id = $.ztreeOptions.showId;
                $.fn.zTree.init($("#" + id), setting, data);
            },
            treeAsyncLoad: function (options) {
                var setting = this.initConfig(options);
                var id = $.ztreeOptions.showId;
                $.fn.zTree.init($("#" + id), setting);
            },
            asyncRefresh: function (key, value) {
                var zTree = $.fn.zTree.getZTreeObj($.ztreeOptions.showId);
                var node = zTree.getNodeByParam(key, value);
                zTree.reAsyncChildNodes(node, "refresh", false);
            },
            getCheckedNodes: function (checked) {
                var zTree = $.fn.zTree.getZTreeObj($.ztreeOptions.showId);
                var nodes = zTree.getCheckedNodes(checked);
                return nodes;
            },
            expandAll: function () {
                var zTree = $.fn.zTree.getZTreeObj($.ztreeOptions.showId);
                zTree.expandAll(true);
            }
        },
    })
});
