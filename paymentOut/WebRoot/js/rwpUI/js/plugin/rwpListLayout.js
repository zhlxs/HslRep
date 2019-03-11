/**
* @license                                     
* 列表页 for 牛发发
* flyofsky@163.com
*
* Since: 2012/08/09
*/
; (function ($, window, document, undefined) {    

    var rwpPluginHelper = $.rwpUI.pluginHelper = $.rwpUI.pluginHelper || {},
        rwpListLayout = rwpPluginHelper.listLayout = {};

    rwpListLayout.lastSearchParam = {};

    if ($.validator && $.rwpUI.DateTime) {
        //重写jquery validate 日期验证，原方法new Date(value).toString()在ie6-8下会返回NaN导致验证失败
        $.validator.addMethod('date', function (value, element) {
            return this.optional(element) || $.rwpUI.DateTime.parseDate(value);
        });
    }

    rwpPluginHelper.validateParse = function (domElement) {    //对dom元素中包含的Form表单 进行客户端验证解析
        $.validator && $.validator.unobtrusive && $.validator.unobtrusive.parse
        && typeof ($.validator.unobtrusive.parse) == 'function' && $.validator.unobtrusive.parse(domElement);
    };

    rwpPluginHelper.validateParseDialog = function (dialog) {  //对Dialog中包含的Form表单 进行客户端验证解析
        dialog && dialog.dom && rwpPluginHelper.validateParse(dialog.dom.DialogContent);
    };

    rwpPluginHelper.getCurrentDialogForm = function (dialog) {  //获取当前弹窗中的表单
        var dialogManager = dialog,
            dialogFrame, formManager;

        if (!(dialogManager && !dialogManager.closed)) {  //必须满足dialog未关闭
            dialogManager = $.rwpUI.Dialog.focus;
        }
        if (dialogManager) {
            dialogFrame = dialogManager.dom.DialogFrame;  //获取的是iframe的window对象

            if (dialogFrame)
                formManager = $('form:visible', dialogFrame.document).eq(0).data('rwpUIForm');
            else
                formManager = $('form:visible', dialogManager.dom.DialogContent).eq(0).data('rwpUIForm');
        }

        return formManager;
    };

    rwpPluginHelper.dialogFormImportOK = function (item, dialog, successCallback) {  //当前弹窗中的表单 点击确定响应函数
        var formManager = rwpPluginHelper.getCurrentDialogForm(dialog);   //当前弹窗中的表单

        var submitCallback = function (submitData) {
            if (submitData && submitData.validState && !submitData.submitState) {
                var submitResult = submitData.submitResult;
                if (submitResult) {
                    if (submitResult.stateType == 0) {
                        dialog.close();
                        successCallback && typeof (successCallback) == 'function' && successCallback();  //提交成功 回调函数
                    }
                    if (submitResult.stateMsg) {
                        $.rwpUI.alert(submitResult.stateMsg, '操作提示', submitResult.stateType == 0 ? 'success' : 'error', false);
                    }
                }
            }
        };
        formManager && formManager.ajaxSubmit(submitCallback);   //执行表单ajax提交
    };

    rwpPluginHelper.dialogImportCancel = function (item, dialog) {  //当前弹窗 点击取消响应函数       
        dialog.close();
    };    
    
    //表格按钮点击事件
    rwpListLayout.gridToolButtonClick = (function () {
        var btn = {
            //按钮点击
            itemclick: function (item) {
                //获取表格选中行主键
                var rowkeys = [],
                    gridManager = rwpListLayout.getCurrentGrid();
                if (item.isGetRowKey === undefined || item.isGetRowKey === true || item.isGetRowKey === 'true' && gridManager)
                    rowkeys = gridManager.getCheckedRowKeys();  //表格行操作链接不需要获取表格选中行
                //判断最小选中值
                if (item.min && $.isNumeric(item.min)) {
                    if (rowkeys.length < item.min) {
                        $.rwpUI.warn('请最少选择' + item.min + '项要操作的数据！', false);
                        return false;
                    }
                }
                //判断最大选中值
                if (item.max && $.isNumeric(item.max)) {
                    if (rowkeys.length > item.max) {
                        $.rwpUI.warn('最多只能选择' + item.max + '项要操作的数据！', false);
                        return false;
                    }
                }
                btn.getUrl(rowkeys, item.url);
                if (item.confirm) {
                    $.rwpUI.confirm('是否' + item.text + '?', false,
                            function (yes) {
                                if (yes) {
                                    btn.btnstyle(item);
                                }
                            });
                }
                else {
                    btn.btnstyle(item);
                }
            },
            //获取Url
            getUrl: function (rowkeys, url) {
                var _url = '';
                if (url) {
                    _url = url + (url.indexOf("?") >= 0 ? '&' : '?');
                }
                if (rowkeys && rowkeys.length > 0) {
                    if (rowkeys.length == 1) {
                        _url += "&id=" + rowkeys[0];
                    }
                    else {
                        _url += "&" + $.param({ id: rowkeys }, true);
                    }
                }
                btn.url = _url;
            },
            //按钮触发方式
            btnstyle: function (item) {
                if (item.dia) {
                    btn._dialog(item); //弹窗
                }
                else if (item.postajax) {
                    btn.ajaxsubmit(item); //ajax提交
                }                
                else if (item.link) {
                    var linkFun = $.isFunction(item.link) ? item.link : rwpPluginHelper.menuHelper.loadMenuInsidePage;
                    linkFun(btn.url);
                }
            },            
            //弹窗
            _dialog: function (item) {
                var ajaxCallback = item.dia.ajaxCallback;
                var btnImportOK = item.dia.importOK;

                if (!$.isFunction(btnImportOK)) {
                    btnImportOK = btn.importOK(item.dia);
                }

                if (!(ajaxCallback && typeof (ajaxCallback) == 'function')) {
                    if (item.dia.isFrame !== true)  //非自定义回调函数 并且不是通过iframe加载时 回调默认表单验证解析
                        ajaxCallback = rwpPluginHelper.validateParseDialog;
                }

                $.rwpUI.open({
                    title: item.text + (item.dia.title || ''),
                    width: item.dia.width,
                    height: item.dia.height,
                    url: btn.url,
                    ajaxCallback: ajaxCallback,
                    isFrame: item.dia.isFrame,  //是否通过iframe加载
                    allowClose: item.dia.allowClose,
                    buttons: [
                    { text: '确定', onclick: btnImportOK },
                    { text: '取消', onclick: rwpPluginHelper.dialogImportCancel }
                    ]
                });
            },
            //确定事件
            importOK: function (diaopts) {
                var successCallback = btn.reloadGrid;
                if ($.isFunction(diaopts.submitOk)) {
                    successCallback = diaopts.submitOk;
                }
                return function (item, dialog) {
                    rwpPluginHelper.dialogFormImportOK(null, dialog, successCallback);
                };
            },            
            //ajax提交
            ajaxsubmit: function (item) {
                if (!btn.ajaxs[item.url]) {
                    btn.ajaxs[item.url] = true;
                    var gridmanager = rwpListLayout.getCurrentGrid();  //当前可见表格
                    var tipmanager = $.rwpUI.waitting('数据提交中,请稍候...');
                    //请求服务器
                    $.ajax({
                        type: 'POST',
                        url: btn.url + (btn.url.indexOf("?") >= 0 ? '&' : '?') + 'rnd' + Math.random(),
                        async: false,
                        dataType: 'json',
                        success: function (data) {
                            if (data && data.stateType == 0) {
                                var submitOkObj = $.rwpUI.getPropertyByPath(item.submitOk);
                                if ($.isFunction(submitOkObj)) {
                                    submitOkObj();
                                }
                                else {
                                    gridmanager && gridmanager._loadData();
                                }
                            }
                            if (data && data.stateMsg) {
                                $.rwpUI.alert(data.stateMsg, '操作提示', data.stateType == 0 ? 'success' : 'error', false)
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert(errorThrown);
                        }
                    });
                    tipmanager && tipmanager.close();
                    btn.ajaxs[item.url] = false;
                }
                else {
                    alert("提交中");
                }
            },
            reloadGrid: function () {  //刷新表格
                var gridManager = rwpListLayout.getCurrentGrid();  //当前可见表格
                gridManager && gridManager._loadData();
            }
        };
        btn.ajaxs = [];
        return btn;
    })();    

    //表格查询
    rwpListLayout.searchGrid = function (formID) {
        var sear = [], formManager,
            gridManager = rwpListLayout.getCurrentGrid(),            
            $form = $("#" + formID);        
        
        if ($form && $form.length > 0) {
            var formManager = $form.data('rwpUIForm');
            if (formManager && formManager.queryInfo) {   //启用了查询表单                
                sear.push({ name: "queryinfo", value: JSON.stringify(formManager.getSearchValues()) });
            }
            else
                sear = $form.serializeArray();
            rwpListLayout.lastSearchParam = { params: sear, newPage: 1 };    //保存最后一次查询参数             
            gridManager && gridManager.refreshData(rwpListLayout.lastSearchParam);
        }
    };
    
    rwpListLayout.getSearchForm = function(parentDom) {   //获取当前可见查询表单管理对象
        var formManager, topDialog = $.rwpUI.Dialog.focus,
            searchDom = parentDom, $searchDialog;

        if (!searchDom) {
            if (topDialog)  //优先取 最顶级弹窗对象
            {
                $searchDialog = topDialog.dom.Dialog;
                if ($('form:visible', $searchDialog).length > 0) {
                    searchDom = $searchDialog;  //顶级弹窗存在表单
                }
            }
            if (!searchDom)
                searchDom = document;  //没有弹窗或顶级弹窗不存在表单时
        }

        $('form:visible', searchDom).each(function (i, item) {
            formManager = $(item).data('rwpUIForm');
            if (formManager && formManager.queryInfo)
                return false;  //结束循环
        });

        if (formManager && formManager.queryInfo) {
            return formManager;  //只返回查询表单
        }
    };    

    rwpListLayout.getCurrentGrid = function (parentDom) {  //获取当前可见表格管理对象
        var gridManager, topDialog = $.rwpUI.Dialog.focus,
            searchDom = parentDom, $searchDialog;

        if (!searchDom) {
            if (topDialog)  //优先取 最顶级弹窗对象
            {
                $searchDialog = topDialog.dom.Dialog;
                if ($('.rwpGridTable:visible', $searchDialog).length > 0) {
                    searchDom = $searchDialog;  //顶级弹窗存在表格
                }
            }
            if (!searchDom)
                searchDom = document;  //没有弹窗或顶级弹窗不存在表格时
        }

        $('.rwpGridTable:visible', searchDom).each(function (i, item) {
            gridManager = $(item).data('rwpUIDatagrid');
            if (gridManager)
                return false;  //结束循环
        });

        return gridManager;
    };

    //表格行链接点击
    rwpListLayout.gridRowclick = function (gridID, $tdLinks) {
        var p = {
            importOK: null,    //确定按钮
            importCancel: null //取消按钮
        };
        var c = {
            //初始化
            init: function () {
                c.gridLinks.on("click", function (e) {
                    e.stopPropagation();
                    var opts = null;
                    try {
                        opts = eval('(' + c.getOpts(this) + ')');
                    }
                    catch (ex) {
                    }
                    if (opts) {
                        var _opts = $.extend({}, p, opts || {});
                        _opts._this = $(this);
                        _opts.isdefaultevent !== true && e.preventDefault();  
                        if (_opts.isconfirm === true) {
                            $.rwpUI.confirm('是否' + _opts.confirmText + '?', false,
                                function (yes) {
                                    if (yes) {
                                        c.click(_opts);
                                    }
                                });
                        }
                        else
                            c.click(_opts);
                    }                    
                });
            },
            //获取参数
            getOpts: function (_this) {
                var opts = $(_this).attr("rwpOpts");
                return opts;
            },
            //点击事件
            click: function (copts) {
                var beforeClickObj = $.rwpUI.getPropertyByPath(copts.beforeClick);
                $.isFunction(beforeClickObj) && beforeClickObj(copts._this);
                if (copts.isdialog === true) {
                    c.diglog(copts);
                }
                else if (copts.isajax === true) {
                    rwpListLayout.gridToolButtonClick.itemclick({
                        isGetRowKey: false,
                        postajax: true,
                        url: copts._this.attr('href'),                        
                        submitOk: copts.submitOk
                    });
                }
                else if (copts.ispage === true) {
                    rwpPluginHelper.menuHelper.loadMenuInsidePage(copts._this.attr('href'));
                }
            },
            //弹出窗口
            diglog: function (dopts) {
                dopts.url = dopts._this.attr("href");
                dopts.title = dopts._this.attr("title");
                dopts.buttons = [];                
                if (dopts.importOK) {   //传递参数不是function时提供默认确定事件                    
                    dopts.buttons.push({ text: '确定', onclick: c.importOK(dopts) });
                }
                if (dopts.importCancel) {   //传递参数不是function时提供默认取消事件
                    dopts.buttons.push({ text: '取消', onclick: c.importCancel(dopts) });
                }
                if (dopts.buttons.length == 0) {
                    dopts.buttons = null;
                }
                dopts.ajaxCallback = $.rwpUI.getPropertyByPath(dopts.ajaxCallback); //获取对应的回调函数
                $.rwpUI.open({
                    title: dopts.title,
                    width: dopts.width,
                    height: dopts.height,
                    url: dopts.url,
                    ajaxCallback: dopts.ajaxCallback,
                    allowClose: dopts.allowClose,
                    buttons: dopts.buttons
                });
            },
            //确定事件
            importOK: function (dopts) {
                var successCallback = c.reloadGrid,
                    importOKObj = $.rwpUI.getPropertyByPath(dopts.importOK),
                    submitOkObj = $.rwpUI.getPropertyByPath(dopts.submitOk);
                if ($.isFunction(importOKObj)) {
                    return importOKObj;
                }
                if ($.isFunction(submitOkObj)) {
                    successCallback = submitOkObj;
                }
                return function (item, dialog) {
                    rwpPluginHelper.dialogFormImportOK(item, dialog, successCallback);
                };
            },
            //取消事件
            importCancel: function (dopts) {
                var importCancelObj = $.rwpUI.getPropertyByPath(dopts.importCancel);
                if ($.isFunction(importCancelObj)) {
                    return importCancelObj;
                }
                return rwpPluginHelper.dialogImportCancel;
            },
            reloadGrid: function () {  //刷新表格
                c.gridManager = c.gridManager || c.grid.data('rwpUIDatagrid') || c.gridBody.parents('.rwpGridTable').eq(0).data('rwpUIDatagrid');
                c.gridManager && c.gridManager._loadData();
            }
        };
        c.grid = $('#' + gridID);
        if (c.grid && c.grid.length > 0) {
            c.gridBody = $('.rwpGridTableBody', c.grid);  //得到表格主体
            if (c.gridBody && c.gridBody.length > 0) {
                if ($tdLinks && $tdLinks instanceof jQuery && $tdLinks.length > 0) {
                    c.gridLinks = $tdLinks;
                }
                else {
                    c.gridLinks = $('a', c.gridBody);  //获取表格中的a链接
                }
                c.init();
            }            
        }
    };

    //获取所有子节点
    $.rwpUI.getAllChildNode = function (data, arr, checkfun) {
        if (data.ID && data.ID > 0) {
            !(checkfun && typeof (checkfun) == 'function' && !checkfun.call(data, data)) && arr.push(data.ID);
            if (data.children) {
                $.each(data.children, function (i, item) {
                    arr.concat($.rwpUI.getAllChildNode(item, arr, checkfun))
                });
            }
        }
        return arr;
    };

    $.rwpUI.Datagrid.defaults.onChangePage = function(opt) {   //设置默认表格切换分页事件
        rwpListLayout.lastSearchParam = { params: opt.params, newPage: opt.newPage };
    };

})(jQuery, window, document);