/**
* @license                                     
* dialog弹出框控件
*
* Since: 2013/2/8
*/
; (function ($, window, document, undefined) {   
    
    var _count = 0;   //窗口dialog计数

    var rwpDialog = function (options) {                
        var opt = $.extend({}, rwpDialog.defaults, options);
        opt.id = opt.id || $.rwpUI.guid('rwpDialog_');
        var dialogApi = rwpDialog.list[opt.id];

        if (dialogApi) {  //已存在Dialog对象          
            dialogApi.zIndex();
            return dialogApi;
        };
        
        //更新当前window最大的zIndex
        if (_count < 1)
            rwpDialog.topzIndex = parseInt(opt.zIndex);
        else
            rwpDialog.topzIndex = Math.max(parseInt(opt.zIndex), rwpDialog.topzIndex);

        _count++;
        return rwpDialog.list[opt.id] = new rwpDialog.prototype._init(opt);
    };

    rwpDialog.fn = rwpDialog.prototype = {
        _init: function (opt) {
            this.options = opt;
            this.dom = this._innerHtml();  //添加dialog对应dom元素到body

            this.setTitle(opt.title)
                .setHeight(opt.height)
                .setWidth(opt.width)
                .setContent(opt.content);

            if (!opt.content) this.setUrl(opt.url);
            if (!opt.allowClose) this.dom.DialogClose.hide();   //隐藏关闭按钮
            this.button.apply(this, opt.buttons || []);

            this._setImage();  //设置内容区域图片            
            this._setPosition();  //设置位置

            this.zIndex();
            opt.modal && this.lock();  //设置锁屏

            this._addEvent();
            this[opt.visible ? 'visible' : 'hidden']();

            return this;
        },        
        setWidth: function (value) {
            if (this.dom.Dialog && value === +value && value >= 20) {
                this.dom.DialogBody.width(value - 20);  //左右预留10px的间距
                this.dom.DialogContent.width(value - 20);  //与body宽度一致
            }
            return this;           
        },
        setHeight: function (value) {
            if (this.dom.Dialog && value === +value) {                
                this.dom.DialogContent.height(value);
            }
            return this;
        },
        setTitle: function (value) {
            if (this.dom.Dialog && value) {
                this.dom.DialogTitle.html(value);
            }
            return this;
        },
        //设置内容
        setContent: function (content) {
            var dialogContent = this.dom.DialogContent
            if (typeof (content) === 'string')
                dialogContent.html(content);
            else if (content) {     //dom元素，html字符串 或 jQuery对象
                dialogContent.prepend(content);
                $(content).show();
            }
            this._setPosition();
            return this;
        },
        setUrl: function (url) {
            var self = this, opt = self.options, $dialogContent = self.dom.DialogContent;
            if (url && this.dom.Dialog) {                
                if (opt.isFrame === true && $.isFunction(self.loadFrame))  //设置通过iframe 并且加载了插件集
                    this.loadFrame(url);  //通过iframe加载url
                else {                    
                    $.rwpUI.getHtml(url, function () {
                            $dialogContent.html('<div class="rwpLoading"></div>');
                        },
                        function (data) {
                        	if(data.indexOf("<html class=\"userlogin\">")!=-1){
                            	$.rwpUI.error("会话超时，请重新登录！","",false,function(){
                                	window.location.reload();
                            	});
                            	return;
                            };
                            $dialogContent.html(data);                                                   
                            self._setPosition();
                            $.rwpUI.funcApply(opt.ajaxCallback)(self);
                        },
                        null,
                        function(jqXHR, textStatus) {                            
                            $('.rwpLoading', $dialogContent).remove();   //移除loading元素
                        });                    
                }
            }
            return this;
        },
        button: function () {
            var ags = [].slice.call(arguments);
            var isButtonPlugin = $.rwpUI.Button;
            var buttonInner = this.dom.DialogButtonsInner;
            var dialog = this;
            $.each(ags, function (i, item) {
                var btn = $('<a class="rwpDialogButton" href="javascript:void(0);">' + item.text + '</a>');
                buttonInner.prepend(btn);
                var btnClick = item.onclick && typeof (item.onclick) == 'function'
                    ? function () {  item.onclick(item, dialog, i); } : null;  //按钮点击事件
                if (isButtonPlugin) {   //已加载Button控件
                    btn.rwpUIButton({ width: item.width, click: btnClick });
                }
                else {                    
                    item.width && btn.width(item.width);
                    btnClick && btn.click(function(e) { e.preventDefault(); btnClick();});  //阻止默认操作解决IE6无法页面跳转的Bug
                }                
            });
            if ($('a.rwpDialogButton', buttonInner).length < 1)
                this.dom.DialogButtons.hide();  //隐藏底部按钮区域
            return this;
        },
        /** 置顶对话框 */
        zIndex: function () {
            var dom = this.dom,                
                index = rwpDialog.topzIndex ++;

            // 设置叠加高度
            dom.Dialog.css('zIndex', index);
            this._zIndex = index;  //保存最后的zIndex值

            this._lockMask && this._lockMask.css('zIndex', index - 1);

            // 设置为最高层           
            rwpDialog.focus = this;

            return this;
        },
        /** 设置屏锁 */
        lock: function () {
            if (this._isLock) {
                return this;
            };

            var div = $("<div class='rwpDialogMask' style='display: block;'></div>"),
                index = rwpDialog.topzIndex - 1;  //设置为dialog的zIndex

            this.zIndex();  //dialog的zIndex提升1
            div.css({ zIndex: index});  //设置zIndex值

            $(document.body).append(div);

            this._lockMask = div;
            this._isLock = true;

            return this;
        },
        /** 解开屏锁 */
        unlock: function () {
            if (!this._isLock) {
                return this;
            };

            this._lockMask.unbind();
            this._lockMask.hide();
            this._lockMask.remove();

            this._isLock = false;

            return this;
        },
        /** 显示对话框 */
        visible: function () {            
            this.dom.Dialog.css('visibility', 'visible');

            this._isLock && this._lockMask.show();  //显示锁屏
           
            return this;
        },
        /** 隐藏对话框 */
        hidden: function () {
            this.dom.Dialog.css('visibility', 'hidden');

            this._isLock && this._lockMask.hide();  //关闭锁屏
                        
            return this;
        },
        /** 关闭对话框 */
        close: function () {
            if (this.closed) {
                return this;
            };

            if (this.options.isHidden && this.dom.Dialog.css('visibility') != 'hidden') {
                return this.hidden();   //只隐藏对话框
            };

            var opt = this.options,
                dom = this.dom,
                dialog = dom.Dialog,
                list = rwpDialog.list;
            
            $.rwpUI.funcApply(opt.onBeforeClose)(dialog);  //关闭前触发事件
            this.unlock();
            this._removeEvent();   //解除事件绑定
            delete list[this.options.id];  //从对话框列表中移除
            _count--;

            dialog.remove();
            this.closed = true;
            
            if (rwpDialog.focus === this) {
                rwpDialog.focus = null;  //取消为置顶
                if (_count < 1)
                    rwpDialog.topzIndex = rwpDialog.initzIndex;  //窗口中没有dialog
                else {
                    rwpDialog.topzIndex = null;
                    for (var id in list) {   //刷新最大的zIndex
                        if (rwpDialog.topzIndex == null) {
                            rwpDialog.topzIndex = parseInt(list[id]._zIndex);  //赋值首个dialog的zIndex
                            rwpDialog.focus = list[id];
                        }
                        else {
                            var dialogzIndex = parseInt(list[id]._zIndex);
                            if (dialogzIndex > rwpDialog.topzIndex) {  //获取最大的zIndex
                                rwpDialog.topzIndex = dialogzIndex;
                                rwpDialog.focus = list[id];
                            }
                        }
                    };
                    rwpDialog.topzIndex++;  //自动累加1
                }                    
            };

            return this;
        },
        //位置初始化
        _setPosition: function () {
            if (this.dom.Dialog) {
                var window = this.dom.Window, document = this.dom.Document,
                    dialog = this.dom.Dialog,
                    ww = window.width(), wh = window.height(),
                    dl = document.scrollLeft(), dt = document.scrollTop(),
                    ow = dialog[0].offsetWidth, oh = dialog[0].offsetHeight;

                var left = (ww - ow) / 2 + dl;
                left = Math.max(parseFloat(left), dl);

                var top = (wh - oh) * 382 / 1000 + dt; // 黄金比例
                top = Math.max(parseFloat(top), dt);

                dialog.css({ left: left, top: top });  //设置left和top
            }
            return this;
        },
        //设置图片
        _setImage: function () {
            var opt = this.options,
                dom = this.dom;
            if (opt.url || opt.type == "none") opt.type = null;
            if (opt.type && this.dom && this.dom.Dialog && this.dom.DialogImage) {                
                if (opt.type == 'success' || opt.type == 'done' || opt.type == 'ok') {
                    dom.DialogImage.addClass("rwpDialogImageDone").show();
                    dom.DialogContent.css({ paddingLeft: 64, paddingBottom: 30, paddingTop: 20 });
                }
                else if (opt.type == 'error') {
                    dom.DialogImage.addClass("rwpDialogImageError").show();
                    dom.DialogContent.css({ paddingLeft: 64, paddingBottom: 30, paddingTop: 20 });
                }
                else if (opt.type == 'warn') {
                    dom.DialogImage.addClass("rwpDialogImageWarn").show();
                    dom.DialogContent.css({ paddingLeft: 64, paddingBottom: 30, paddingTop: 20 });
                }
                else if (opt.type == 'question') {
                    dom.DialogImage.addClass("rwpDialogImageQuestion").show();
                    dom.DialogContent.css({ paddingLeft: 64, paddingBottom: 40, paddingTop: 20 });
                }
            }
            else {
                dom.DialogImage.remove();
                delete dom.DialogImage;
            }
        },
        //添加元素到body
        _innerHtml: function () {
            var dom = {};
            var dialog = $(rwpDialog._template);
            $(document.body).append(dialog);  //添加到body
            dom.Dialog = dialog;
            
            //遍历dialog的dom元素集合
            dom.Dialog.find('*').each(function (i, item) {
                name = item.className.split('rwp')[1];
                if (name) {
                    dom[name] = $(item);
                };
            });

            dom.Window = $(window);
            dom.Document = $(document);
            return dom;
        },
        //事件监听
        _addEvent: function () {
            var that = this,
                dom = this.dom;

            // 监听点击
            dom.Dialog
            .bind('click', function (event) {
                var target = event.target;

                // IE BUG
                if (target.disabled) {
                    return false;
                };

                if (target === dom.DialogClose[0]) {  //点击关闭按钮
                    that.close();
                    return false;
                }
            });
            //.bind('mousedown', function () {
            //    that.zIndex();
            //});
        },
        // 卸载事件代理
        _removeEvent: function () {
            this.dom.Dialog.unbind();
        },
        //按下回车
        _enter: function () {
            var isClose;
            var opt = this.options;
            if (opt.closeWhenEnter) {
                isClose = opt.closeWhenEnter;
            }
            else if (opt.type == "warn" || opt.type == "error" || opt.type == "success") {
                isClose = true;
            }
            if (isClose) {
                this.close();
            }
        }
    };
    //new init(opt)时会创建原型为init.prototype的对象, 并在该对象上执行init方法
    rwpDialog.fn._init.prototype = rwpDialog.fn;  //指定init方法的原型

    rwpDialog.list = {};

    /**
    * 根据 ID 获取某对话框 API
    * @param    {String}    对话框 ID
    * @return   {Object}    对话框 API (实例)
    */
    rwpDialog.get = function (id) {
        return id === undefined ? rwpDialog.list : rwpDialog.list[id];
    };

    /** 最顶层的对话框API */
    rwpDialog.focus = null;

    // 浏览器窗口改变后重置对话框位置
    $(window).bind('resize', function () {
        var dialogs = rwpDialog.list;
        for (var id in dialogs) {
            dialogs[id]._setPosition();
        };
    });

    // 全局快捷键
    $(document).bind('keydown', function (event) {
        var topDialog = rwpDialog.focus,
            key = event.keyCode;
        if (topDialog && key == 13) {
            topDialog._enter();   //按下回车
        }
    });

    // 锁屏时自动聚焦
    function focusin(event) {
        var topDialog = rwpDialog.focus;
        if (topDialog && topDialog.dom.Dialog.css('visibility') == 'visible'  //排除隐藏的Dialog
            && topDialog._isLock && !topDialog.dom.Dialog[0].contains(event.target)) {
            event.stopPropagation();
            topDialog.dom.Dialog[0].focus();
        }
    }

    if ($.fn.live) {
        $('body').live('focus', focusin);
    } else if (document.addEventListener) {
        document.addEventListener('focus', focusin, true);
    } else {
        $(document).bind('focusin', focusin);
    }

    rwpDialog.topzIndex = rwpDialog.initzIndex = 1988;   //初始zIndex

    rwpDialog.defaults = {        
        type: 'none',   //类型 warn、success、error、question
        buttons: null,   //按钮集合        
        width: 'auto',   //宽度
        height: 'auto',   //高度
        url: null,      //目标页url，默认以ajax获取html的方式载入
        ajaxCallback: null,    //ajax加载目标页html回调
        modal: true,    //是否模态对话框
        title: '提示',  //标题
        content: null,    //内容
        visible: true,   // 初始化后是否显示对话框
        allowClose: true,   //允许关闭
        isHidden: false,     //关闭对话框时是否只是隐藏，还是销毁对话框
        zIndex: rwpDialog.topzIndex,   //对话框叠加高度值(重要：此值不能超过浏览器最大限制)
        closeWhenEnter: false,  //按下回车时关闭
        onBeforeClose: null    //关闭窗口前触发事件
    };    

    rwpDialog.defaultStrings = {
        titleMessage: '提示',   //提示文本标题
        ok: '确定',
        yes: '是',
        no: '否',
        cancel: '取消',
        waittingMessage: '正在等待中,请稍候...'
    };

    rwpDialog._template = '<div class="rwpDialog">'
                             + '<table class="rwpDialogTable" cellpadding="0" cellspacing="0" border="0">'
                                 + '<tbody>'
                                     + '<tr>'
                                        + '<td class="rwpDialogHeader">'
                                           + '<div class="rwpDialogIcon"></div>'
                                           + '<div class="rwpDialogTitle"></div>'
                                           + '<div class="rwpDialogWinbtns"><div class="rwpDialogClose"></div></div>'
                                        + '</td>'
                                     + '</tr>'
                                     + '<tr>'
                                        + '<td class="rwpDialogBody">'
                                           + '<div class="rwpDialogImage"></div>'
                                           + '<div class="rwpDialogContent"></div>'
                                        + '</td>'
                                     + '</tr>'
                                     + '<tr>'
                                        + '<td class="rwpDialogButtons">'
                                           + '<div class="rwpDialogButtonsInner"></div>'
                                        + '</td>'
                                     + '</tr>'
                                + '</tbody>'
                             + '</table>'
                         + '</div>';

    $.rwpUI.Dialog = rwpDialog;

})(jQuery, window, document);
