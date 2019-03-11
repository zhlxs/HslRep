/**
* @license
* rwpUI初始化
*
* Since: 2013/11/18
*/
; (function ($, window, document, undefined) {
    if (!Date.now) {
        Date.now = function now() {
            return (new Date).valueOf();
        };
    }

    window.rwpUI = $.rwpUI = {
        version: 'V0.5.0',
        split: ',',
        pluginPrev: 'rwpUI',        
        guid : function(pre){  //创建唯一的uuid
            return (pre || 'rwp_') + 
                (+new Date()) + 
                (Math.random()+ '').slice(-8);
        },
        objectClone: function (obj) {
            var objClone;
            if (obj.constructor == Object) {
                objClone = new obj.constructor();
            } else {
                objClone = new obj.constructor(obj.valueOf());
            }
            for (var key in obj) {
                if (objClone[key] != obj[key]) {
                    if (typeof (obj[key]) == 'function') {
                        objClone[key] = eval('[' + obj[key].toString() + ']')[0];
                    }
                    else if (typeof (obj[key]) == 'object') {
                        objClone[key] = this.objectClone(obj[key]);
                    } else {
                        objClone[key] = obj[key];
                    }
                }
            }
            objClone.toString = obj.toString;
            objClone.valueOf = obj.valueOf;
            return objClone;
        },
        getStrRealLength: function (str) {
            if (str && typeof (str) == "string")
                return str.replace(/[^\x00-\xff]/g, "xx").length;
            else
                return 0;
        }
    };

    $.rwpUI.isFunction = function (obj) {  //针对跨窗口访问函数对象时 IE6-9均会转换为Object类型
        return $.isFunction(obj) || 
            (!!obj && !obj.nodeName 
            && $.inArray($.type(obj), ['string', 'array', 'regexp']) == -1
            && /function/i.test(obj + ''));
    };

    $.rwpUI.funcApply = function(func, scope) {
        return function() {            
            $.isFunction(func) && func.apply(scope || window, [].concat(Array.prototype.slice.call(arguments, 0)));
        };
    };

    $.rwpUI.getPropertyByPath = function (propPathStr) {   //根据对象的属性路径从window中找到该对象     
        if (typeof (propPathStr) == 'string' && propPathStr.length > 0) {
            var propNames = propPathStr.split('.'), propObj = window, i = 0;
            for (; i < propNames.length; i++) {
                propObj = propObj[propNames[i]];
                if (propObj == null || propObj == undefined) {
                    break;
                }
            }
            return propObj;
        }
    };

    //格式化日期
    $.rwpUI.formatDate = function (date, dateForamt) {
        if (date instanceof Date) {
            dateForamt = dateForamt || "yyyy-MM-dd";
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds()
            };
            if (/(y+)/.test(dateForamt)) {
                dateForamt = dateForamt.replace(RegExp.$1, (date.getFullYear() + "")
                                       .substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(dateForamt)) {
                    dateForamt = dateForamt.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                                  : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return dateForamt;
        }
        else {
            return date;
        }
    };

    $.rwpUI.serverHost = function () {
        return location.protocol + "//" + location.host;
    };

    var rwpRegex = {
        rQuery: /\?/,
        rNoContent: /^(?:GET|HEAD)$/
    };
    $.rwpUI.Regex = rwpRegex;
    
    //ajax请求
    var rwpAjax = function(options) {
        var opt = $.extend({}, rwpAjax.defaults, options);
        if (opt.url) {
//            if (!(rwpAjax.list[opt.url] && rwpAjax.list[opt.url].completed === false)) {  //阻止重复请求
                return rwpAjax.list[opt.url] = new rwpAjax.prototype._init(opt);
//            }
        }
    };

    rwpAjax.fn = rwpAjax.prototype = {
        _init: function (opt) {
            this.completed = false;
            this.options = opt;
            this._formatType()._formatUrl();

            this._send();

            return this;
        },
        _formatType: function() {
            var opt = this.options;

            opt.type = opt.type.toUpperCase();  //转换为大写            

            return this;
        },
        _formatUrl: function() {
            var opt = this.options;

            // 判断请求类型，如果是GET或HEAD，hasContent = false
            opt.hasContent = !rwpRegex.rNoContent.test(opt.type);
            if (!opt.hasContent) {
                if (opt.data) {
                    opt.data = $.param(opt.data);  // 将key/value值转换成编码过的URI
                    opt.url += (rwpRegex.rQuery.test(opt.url) ? '&' : '?') + opt.data;
                    delete opt.data;
                }
                if (opt.cache === false) {
                    opt.url += (rwpRegex.rQuery.test(opt.url) ? '&' : '?') + '_=' + Date.now();
                }
            }            

            return this;
        },        
        _beforeSend: function (jqXHR, settings) {
            var opt = this.options;

            $.rwpUI.funcApply(opt.beforeSend, opt.funcApplyScope)(jqXHR, settings, opt);
        },
        _success: function (data, textStatus, jqXHR) {
            var opt = this.options, isDoResult = this.isDoResult(data);

            if (isDoResult && !opt.expectDoResult) {
                this._showDoResult(data);
            }
            else {
                $.rwpUI.funcApply(opt.success, opt.funcApplyScope)(data, textStatus, jqXHR, opt);
            }            
        },
        _error: function (jqXHR, textStatus, errorThrown) {
            var opt = this.options;
            if (opt.error) {
                $.rwpUI.funcApply(opt.error, opt.funcApplyScope)(jqXHR, textStatus, errorThrown, opt);
            }
            else {
                var errData = {};
                errData.stateMsg = '出现程序异常！';
                errData.stateType = 'error';
                this._showDoResult(errData);
            }  
        },
        _complete: function (jqXHR, textStatus) {
            var opt = this.options;

            $.rwpUI.funcApply(opt.complete, opt.funcApplyScope)(jqXHR, textStatus, opt);
            this.completed = true;   //标识ajax请求结束
        },
        _send: function () {
            var opt = this.options, that = this;            

            $.ajax({
                type: opt.type,
                url: opt.url,
                data: opt.data,
                async: opt.async,
                dataType: opt.dataType,
                beforeSend: $.rwpUI.funcApply(that._beforeSend, that),
                success: $.rwpUI.funcApply(that._success, that),
                error: $.rwpUI.funcApply(that._error, that),
                complete: $.rwpUI.funcApply(that._complete, that)
            });
        },
        isDoResult: function(data) {   //判断是否为DoResult
            return (data && data.stateType != undefined);
        },
        _showDoResult: function(data) {
            var that = this;
            
            if (that.isDoResult(data)) {
               
                (function (doResult) {
                    return function () {
                        var isUrl = doResult.url && doResult.url.length > 0,
                            redirectUrlFun = function () {
                                if (isUrl) {
                                    window.location.href = doResult.url;
                                }
                            };
                        if ($.rwpUI.alert) {
                            $.rwpUI.alert(doResult.stateMsg, '操作提示', doResult.stateType == 0 ? 'success' : 'error', redirectUrlFun);
                        }
                        else {
                            if (isUrl) {
                                redirectUrlFun();
                            }
                            else {
                                alert(doResult.stateMsg);
                            }
                        }
                    }
                })(data)();
            }            
        }
    };
    //new init(opt)时会创建原型为init.prototype的对象, 并在该对象上执行init方法
    rwpAjax.fn._init.prototype = rwpAjax.fn;  //指定init方法的原型

    rwpAjax.list = {};
    
    rwpAjax.defaults = {        
        type: 'GET',    //默认为Get       
        url: '',        //目标url
        data: null,     //Ajax请求参数
        dataType: null,   //预期服务器返回的数据类型 html、json
        async: true,    //默认异步请求
        cache: false,   //默认不请求浏览器的缓存
        funcApplyScope: window,   //回调函数的this指针
        beforeSend: null,  //请求前执行函数
        success: null,    //请求成功执行函数
        error: null,      //请求失败执行函数
        complete: null,   //请求结束执行函数
        expectDoResult: false  //是否期望获取DoResult结果
    };

    $.rwpUI.Ajax = rwpAjax;

    //请求页面
    $.rwpUI.getHtml = $.rwpUI.Ajax.Html = function (url, beforeGet, successGet, errorGet, completeGet, sync) {
        var p = {
            url: url,
            beforeSend: beforeGet,
            success: successGet,
            error: errorGet,
            complete: completeGet,
            async: !!!sync
        };
        return $.rwpUI.Ajax(p);
    };

    //请求数据
    $.rwpUI.getJson = $.rwpUI.Ajax.Json = function (url, beforeGet, successGet, errorGet, completeGet, sync) {
        var p = {
            url: url,            
            dataType: 'json',
            beforeSend: beforeGet,
            success: successGet,
            error: errorGet,
            complete: completeGet,
            async: !!!sync
        };
        return $.rwpUI.Ajax(p);
    };

    //POST提交数据
    $.rwpUI.post = $.rwpUI.Ajax.Post = function (url, postData, beforePost, successPost, errorPost, completePost, expectDoResult, sync) {
        var p = {
            type: 'POST',
            url: url,
            data: postData,
            dataType: 'json',
            beforeSend: beforePost,
            success: successPost,
            error: errorPost,
            complete: completePost,
            expectDoResult: !!expectDoResult,
            async: !!!sync
        };
        return $.rwpUI.Ajax(p);
    };

    var rwpLoading = function(options) {
        var opt = $.extend({}, rwpLoading.defaults, options);
        opt.id = opt.id || $.rwpUI.guid('rwpLoading_');
        var loadingApi = rwpLoading.list[opt.id];

        if (loadingApi) {  //已存在Loading对象          
            loadingApi.zIndex();
            return loadingApi;
        };

        return rwpLoading.list[opt.id] = new rwpLoading.prototype._init(opt);
    };

    rwpLoading.fn = rwpLoading.prototype = {
        _init: function (opt) {            
            this.options = opt;           

            return this;
        }
    };
    //new init(opt)时会创建原型为init.prototype的对象, 并在该对象上执行init方法
    rwpLoading.fn._init.prototype = rwpLoading.fn;  //指定init方法的原型
    rwpLoading.list = {};

    rwpLoading.defaults = {                
        modal: true,        //是否锁屏
        waittingMessage: '正在加载中,请稍候...'   //等待提示文本
    };

    $.rwpUI.Loading = rwpLoading;

    //窗体遮罩
    var rwpMask = function(options) {
        var opt = $.extend({}, rwpMask.defaults, options);
        opt.id = opt.id || $.rwpUI.guid('rwpMask_');
        var maskApi = rwpMask.list[opt.id];

        if (maskApi) {  //已存在遮罩对象          
            maskApi.zIndex();
            return maskApi;
        };

        return rwpMask.list[opt.id] = new rwpMask.prototype._init(opt);
    };

    rwpMask.fn = rwpMask.prototype = {
        _init: function (opt) {            
            this.options = opt;           

            return this;
        }
    };

    rwpMask.list = {};

    rwpMask.defaults = {
    };

    $.rwpUI.Mask = rwpMask;

})(jQuery, window, document);