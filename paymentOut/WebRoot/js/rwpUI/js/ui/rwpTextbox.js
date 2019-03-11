/**
* @license                                     
* 文本框控件
*
* Since: 2013/2/5
*/
; (function ($, window, document, undefined) {    

    var pluginName = 'rwpUITextbox';   //控件名称

    var rwpTextbox = function (element, options) {
        this.element = element;
        this.$element = $(element);
        var opt = $.extend({}, $.rwpUI.Textbox.defaults, options);  //合并已赋值参数
        this.options = opt;

        this._init();  //初始化
        $.data(this.element, pluginName, this);   //保存到元素上
    };

    rwpTextbox.prototype = {
        _init: function () {
            var opt = this.options, $element = this.$element;
            if (!$element.hasClass('rwpInputText')) $element.addClass('rwpInputText');            
            if ($element.attr('disabled') || $element.attr('readonly'))  //优先取input中属性 
                opt.disabled = true;

            this.setDisabled(opt.disabled)
                .setWidth(opt.width)
                .setHeight(opt.height);

            this._setEvent();
        },        
        setDisabled: function (value) {
            this.options.disabled = value ? true : false;
            var $element = this.$element;
            if (value) {
                $element.attr("readonly", "readonly");
                $element.addClass('rwpTextDisabled');
            }
            else {
                $element.removeAttr("readonly");
                $element.removeClass('rwpTextDisabled');
            }
            return this;
        },
        setWidth: function (value) {
            if (value === +value && value > 20) {
                this.$element.css({ width: value });
            }
            return this;
        },
        setHeight: function (value) {
            if (value === +value && value > 20) {
                var $element = this.$element;
                $element.height(value);
                $element.css("line-height", value + "px");  //使input内文本垂直居中
            }
            return this;
        },
        _setEvent: function () {
            var element = this.element, $element = this.$element, opt = this.options;
            $element.focus(function () {
                $element.addClass("rwpTextFocus");                
                opt.onFocus && typeof (opt.onFocus) == 'function' && opt.onFocus(element);                
            }).blur(function () {
                $element.removeClass('rwpTextFocus');                
                opt.onBlur && typeof (opt.onBlur) == 'function' && opt.onBlur(element);
            }).hover(function () {
                $element.addClass('rwpTextOver')
            }, function () {
                $element.removeClass('rwpTextOver');
            });
            return this;
        }
    };

    $.rwpUI.Textbox = function (element, options) {
        (new rwpTextbox(element, options));
    };

    $.rwpUI.Textbox.defaults = {    //默认参数
        disabled: false,      //是否禁用
        height: 'auto',
        width: 'auto',        
        onFocus: null,  //聚焦响应事件
        onBlur: null    //失去焦点响应事件
    };

    $.rwpUI.Textbox.fn = rwpTextbox;  //指向rwpTextbox

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Textbox(element, options);
        });
    };

})(jQuery, window, document);