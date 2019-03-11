/**
* @license                                     
* Button按钮控件
*
* Since: 2013/2/5
*/
; (function ($, window, document, undefined) {    

    var pluginName = 'rwpUIButton';   //控件名称

    var rwpButton = function (element, options) {
        this.element = element;
        this.$element = $(element);

        var opt = $.extend({}, $.rwpUI.Button.defaults, options);  //合并已赋值参数
        this.options = opt;

        this._init();  //初始化
        $.data(this.element, pluginName, this);   //保存到元素上
    };

    rwpButton.prototype = {
        _init: function () {
            var opt = this.options;
            if (!this.$element.hasClass('rwpButton')) this.$element.addClass('rwpButton');
            this._innerHtml();

            this.setDisable(opt.disabled)
                .setWidth(opt.width)
                .setHeight(opt.height)
                .bindClick();            
        },
        _innerHtml: function () {            
            if (this.element.tagName != 'INPUT') {
                var innerHtml = '', opt = this.options;
                if (opt.icon && typeof (opt.icon) == 'string') {
                    this.hasIcon = true;
                    this.$element.addClass(opt.icon);
                    innerHtml += '<span class="rwpButtonIcon"></span>';  //添加图标
                }
                innerHtml += '<span class="rwpButtonText">' + this.$element.html() + '</span>';  //按钮文本
                this.$element.html(innerHtml);  //重新设置innerHtml
                var innerDom = {};
                innerDom.ButtonText = $('span.rwpButtonText', this.$element);
                if (this.hasIcon) innerDom.ButtonIcon = $('span.rwpButtonIcon', this.$element);
                this.innerDom = innerDom;
            }
            this.isLinkDom = this.element.tagName == 'A';
            return this;
        },
        setDisable: function (value) {
            this.options.disabled = value ? true : false;            
            this.$element[value ? 'addClass' : 'removeClass']('rwpButtonDisabled');
            return this;
        },
        setWidth: function (value) {
            if (value === +value) {
                var innerDom = this.innerDom;
                if (innerDom) {
                    value = innerDom.ButtonIcon ? value - 20 : value;
                    if (value > 0)
                        innerDom.ButtonText.width(value);
                }
                else {
                    this.$element.width(value);  //input按钮直接设置宽度
                }
            }
            return this;
        },
        setHeight: function (value) {
            if (value === +value && value > 25) {
                var innerDom = this.innerDom;
                if (innerDom) {
                    innerDom.ButtonText.height(value);
                    innerDom.ButtonText.css({ 'line-height': value + 'px' });
                    innerDom.ButtonIcon && innerDom.ButtonIcon.height(value);
                }
                else {
                    this.$element.height(value);  //input按钮直接设置高度
                }
            }
            return this;
        },
        bindClick: function() {
            var opt = this.options, isLinkDom = this.isLinkDom;

            this.$element.click(function (e) {
                (isLinkDom || opt.disabled) && e.preventDefault();  //a链接时阻止默认事件
                !opt.disabled && $.isFunction(opt.click) && opt.click();                            
            });
            return this;
        }
    };

    $.rwpUI.Button = function (element, options) {
        (new rwpButton(element, options));
    };

    $.rwpUI.Button.defaults = {    //默认参数
        disabled: false,     //是否禁用
        icon: null,          //显示图标
        width: 'auto',
        height: 'auto',
        click: null     //单击响应
    };    

    $.rwpUI.Button.fn = rwpButton;  //指向rwpButton

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;            
            $.rwpUI.Button(element, options);
        });
    };

})(jQuery, window, document);