/**
* @license                                     
* dialog弹出框控件 扩展插件
*
* Since: 2013/2/8
*/
; (function ($) {    

    // 拖拽支持
    var rwpDragEvent = function () {
        var that = this,
            proxy = function (name) {
                var fn = that[name];
                that[name] = function () {
                    return fn.apply(that, arguments);
                };
            };        
        proxy('start');
        proxy('over');
        proxy('end');
    };

    rwpDragEvent.prototype = {
        // 开始拖拽
        // onstart: function () {},
        start: function (event) {
            $(document)
            .bind('mousemove', this.over)
            .bind('mouseup', this.end);
            
            this.x = event.clientX;
            this.y = event.clientY;
            this.onstart(event.clientX, event.clientY);

            return false;
        },    
        // 正在拖拽
        // onover: function () {},
        over: function (event) {		
            this.onover(
                event.clientX - this.x,
                event.clientY - this.y
            );
        
            return false;
        },    
        // 结束拖拽
        // onend: function () {},
        end: function (event) {
            $(document)
            .unbind('mousemove', this.over)
            .unbind('mouseup', this.end);
        
            this.onend(event.clientX, event.clientY);
            return false;
        }    
    };    
    $.rwpUI.DragEvent = function () {
        return new rwpDragEvent;  //返回新建的拖放事件
    }

    if ($.rwpUI.Dialog) {

        $.rwpUI.open = $.rwpUI.Dialog.open = function (p) {
            return $.rwpUI.Dialog(p);
        };

        $.rwpUI.tip = $.rwpUI.Dialog.tip = function (options) {
            options = $.extend({
                width: 240,
                modal: false,
                height: 100,
                type: 'none'
            }, options || {});

            return $.rwpUI.open(options);
        };

        $.rwpUI.alert = $.rwpUI.Dialog.alert = function (content, title, type, allowClose, callback) {
            content = content || "";
            if (typeof (title) == "function") {   //没有传递title、type和allowClose
                callback = title;
            }
            else if (typeof (type) == "function") {    //没有传递type和allowClose
                callback = type;
            }
            else if (typeof (allowClose) == "function") {  //没有传递allowClose
                callback = allowClose;
            }
            if (typeof (title) == "boolean") {   //没有传递title和type
                allowClose = title;
            }
            else if (typeof (type) == "boolean") {   //没有传递type
                allowClose = type;
            }
            var btnclick = function (item, Dialog, index) {
                Dialog.close();
                if (callback)
                    callback(item, Dialog, index);                
            };
            p = {
                content: content,
                buttons: [{ text: $.rwpUI.Dialog.defaultStrings.ok, onclick: btnclick }]
            };
            if (typeof (title) == "string" && title != "") p.title = title;
            if (typeof (type) == "string" && type != "") p.type = type;
            p.allowClose = allowClose;
            return $.rwpUI.Dialog(p);
        };
        //确认窗口
        $.rwpUI.confirm = $.rwpUI.Dialog.confirm = function (content, title, allowClose, callback) {
            content = content || "";
            if (typeof (title) == "function") {    //没有传递title和allowClose
                callback = title;
            }
            else if (typeof (allowClose) == "function") {
                callback = allowClose;
            }
            if (typeof (title) == "boolean") {   //没有传递title
                allowClose = title;
            }
            var btnclick = function (item, Dialog) {
                Dialog.close();
                if (callback) {
                    callback(item.type == 'yes');  //判断按钮的type属性
                }
            };
            p = {
                type: 'question',
                content: content,
                buttons: [{ text: $.rwpUI.Dialog.defaultStrings.yes, onclick: btnclick, type: 'yes' }, { text: $.rwpUI.Dialog.defaultStrings.no, onclick: btnclick, type: 'no' }]
            };
            if (typeof (title) == "string" && title != "") p.title = title;
            p.allowClose = allowClose;
            return $.rwpUI.Dialog(p);
        };

        $.rwpUI.question = $.rwpUI.Dialog.question = function (content, title, allowClose, callback) {
            content = content || "";
            if (typeof (title) == "function") {  //没有传递title和allowClose
                callback = title;
            }
            else if (typeof (allowClose) == "function") {
                callback = allowClose;
            }
            if (typeof (title) == "boolean") {   //没有传递title
                allowClose = title;
            }
            var btnclick = function (item, Dialog) {
                Dialog.close();
                if (callback) {
                    callback(item.type);
                }
            };
            p = {
                type: 'question',
                content: content,
                buttons: [{ text: $.rwpUI.Dialog.defaultStrings.yes, onclick: btnclick, type: 'yes' }, { text: $.rwpUI.Dialog.defaultStrings.no, onclick: btnclick, type: 'no' }, { text: $.rwpUI.Dialog.defaultStrings.cancel, onclick: btnclick, type: 'cancel' }]
            };
            if (typeof (title) == "string" && title != "") p.title = title;
            p.allowClose = allowClose;
            return $.rwpUI.Dialog(p);
        };
        //等待窗口
        $.rwpUI.waitting = $.rwpUI.Dialog.waitting = function (title) {
            title = title || $.rwpUI.Dialog.defaultStrings.waittingMessage;
            return $.rwpUI.Dialog.open({ type: 'none', content: title, allowClose: false });
        };

        $.rwpUI.success = $.rwpUI.Dialog.success = function (content, title, allowClose, onBtnClick) {
            return $.rwpUI.alert(content, title, 'success', allowClose, onBtnClick);
        };
        $.rwpUI.error = $.rwpUI.Dialog.error = function (content, title, allowClose, onBtnClick) {
            return $.rwpUI.alert(content, title, 'error', allowClose, onBtnClick);
        };
        $.rwpUI.warn = $.rwpUI.Dialog.warn = function (content, title, allowClose, onBtnClick) {
            return $.rwpUI.alert(content, title, 'warn', allowClose, onBtnClick);
        };
        //输入框窗口
        $.rwpUI.prompt = $.rwpUI.Dialog.prompt = function (title, labeltext, value, multi, callback) {
            title = title || "";
            var label = $('<label class="rwpDialogLabel"></label>'), input;
            if (typeof (multi) == "function") {   //没有传递multi
                callback = multi;
            }
            if (typeof (value) == "function") {   //没有传递value和multi
                callback = value;
            }
            else if (typeof (value) == "boolean") {   //没有传递value
                multi = value;
            }
            if (typeof (labeltext) == "function") {   //没有传递labeltext、value和multi
                callback = labeltext;
            }
            else if (typeof (labeltext) == "boolean") {  //没有传递labeltext和value
                multi = labeltext;
            }
            var content = $('<div class="rwpDialogContentInner"></div>');  //外层div
            if (typeof (labeltext) == "string" || typeof (labeltext) == "int") {
                label.text(labeltext);    //设置label文本
                label.appendTo(content);
            }
            if (typeof (multi) == "boolean" && multi) {   //多行文本
                input = $('<textarea class="rwpDialogTextarea"></textarea>');
            }
            else {
                input = $('<input class="rwpDialogInput"></input>');
            }
            if (typeof (value) == "string" || typeof (value) == "int") {
                input.val(value);    //设置初始文本            
            }
            input.appendTo(content);
            var btnclick = function (item, Dialog, index) {
                Dialog.close();
                if (callback) {
                    callback(item.type == 'ok', input.val());
                }
            }
            p = {
                title: title,
                content: content,
                width: 320,
                buttons: [{ text: $.rwpUI.Dialog.defaultStrings.ok, onclick: btnclick, type: 'ok' }, { text: $.rwpUI.Dialog.defaultStrings.cancel, onclick: btnclick, type: 'cancel' }]
            };
            return $.rwpUI.Dialog(p);
        };

        $.rwpUI.Dialog.defaults.isDrag = true;  //添加默认参数支持拖动

        var $window = $(window),
            $document = $(document),
            html = document.documentElement,
            isIE6 = !('minWidth' in html.style),
            isLosecapture = !isIE6 && 'onlosecapture' in html,
            isSetCapture = 'setCapture' in html,
            stopdragstart = function () {
                return false
            };

        var dialogDragInit = function (event) {

            var dialogDragEvent = $.rwpUI.DragEvent(),
                topDialog = $.rwpUI.Dialog.focus,
                $dialogDom = topDialog.dom.Dialog,
                dialogDom = $dialogDom[0],
                $dialogTitle = topDialog.dom.DialogTitle,
                dialogTitle = $dialogTitle[0];
            
            var minX = $document.scrollLeft(),
                minY = $document.scrollTop(),
                maxX = $window.width() - dialogDom.offsetWidth + minX,
                maxY = $window.height() - dialogDom.offsetHeight + minY;

            var startLeft, startTop;

            // 对话框准备拖动
            dialogDragEvent.onstart = function (x, y) {
                
                startLeft = dialogDom.offsetLeft;
                startTop = dialogDom.offsetTop;

                $document.bind('dblclick', dialogDragEvent.end)
                .bind('dragstart', stopdragstart);  //阻止继续开始拖动

                if (isLosecapture) {
                    $dialogTitle.bind('losecapture', dialogDragEvent.end)
                } else {
                    $window.bind('blur', dialogDragEvent.end)
                };

                isSetCapture && dialogTitle.setCapture();
               
                dialogDom.focus();
            };
            // 对话框拖动进行中
            dialogDragEvent.onover = function (x, y) {

                var left = Math.max(minX, Math.min(maxX, x + startLeft)),
                    top = Math.max(minY, Math.min(maxY, y + startTop));

                $dialogDom.css({ left: left, top: top });  //设置left和top                
            };
            // 对话框拖动结束
            dialogDragEvent.onend = function (x, y) {

                $document.unbind('dblclick', dialogDragEvent.end)
                .unbind('dragstart', stopdragstart);

                if (isLosecapture) {
                    $dialogTitle.unbind('losecapture', dialogDragEvent.end);
                } else {
                    $window.unbind('blur', dialogDragEvent.end)
                };

                isSetCapture && dialogTitle.releaseCapture();
            };
            //开始拖动
            dialogDragEvent.start(event);
        };

        // 代理 mousedown 事件触发对话框拖动
        $(document).bind('mousedown', function (event) {
            var topDialog = $.rwpUI.Dialog.focus;
            if (!topDialog) return;

            var target = event.target,
                opt = topDialog.options,
                dom = topDialog.dom;

            if (opt.isDrag !== false && target === dom.DialogHeader[0]) {  //必须拖动头部
                dialogDragInit(event);

                // 防止firefox与chrome滚屏
                return false;
            };
        });

        $.rwpUI.Dialog.defaults.isFrame = false;  //添加默认参数 是否加载iframe

        //加载iframe
        $.rwpUI.Dialog.prototype.loadFrame = function (url) {
            var dom = this.dom;
            var dialogFrame = $('<iframe frameborder="0" style="width:100%; height:100%;"></iframe>');
            var frameName = 'Open' + this.options.id;
            dialogFrame.attr('name', frameName);  //设置iframe的name 
            dom.DialogContent.empty();   //清空content
            dom.DialogContent.append(dialogFrame);
            dom.DialogContent.css('padding', '0px');
            setTimeout(function () {
                dialogFrame.attr('src', url);  //设置url
                dom.DialogFrame = window.frames[frameName];  //dom元素中增加DialogFrame
            }, 0);
        };
    }
})(jQuery);