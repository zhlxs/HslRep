/**
* @license                                     
* toggle复选框控件
*
* Since: 2013/2/5
*/
; (function ($, window, document, undefined) {   

    var pluginName = 'rwpUIToggle';   //控件名称

    var rwpToggle = function (element, options) {
        var lowerTagName = element.tagName.toLowerCase();   //获取元素tag名称

        if (lowerTagName == 'input' && element.type == 'checkbox') {   //只支持checkbox
            this.element = element;
            this.$element = $(element);

            var opt = $.extend({}, $.rwpUI.Toggle.defaults, options);  //合并已赋值参数
            this.options = opt;

            this._init();  //初始化
            $.data(this.element, pluginName, this);   //保存到元素上
        }
    };

    rwpToggle.prototype = {
        _init: function () {
            var opt = this.options;
            
            this._domHtml()._initStyle();

            if (this.$element.attr('disabled'))  //优先取checkbox中属性 
                opt.disabled = true;

            this.setDisable(opt.disabled)
                .setWidth(opt.width)
                .setHeight(opt.height);

            this._initValue()
                ._addClickEvent();
        },
        _domHtml: function () {
            var $element = this.$element,
                dom = {}, wrapTemplate = $.rwpUI.Toggle._template;

            $element.hide();   //隐藏checkbox
            dom.toggle = $element.wrap('<div class="rwpToggle"></div>').parent();  //包裹checkbox
            dom.toggleWrap = $(wrapTemplate);
            dom.toggle.prepend(dom.toggleWrap);
            dom.toggleOn = $('.rwpToggleOn:first', dom.toggleWrap);
            dom.toggleOff = $('.rwpToggleOff:first', dom.toggleWrap);
            dom.toggleThumb = $('.rwpToggleThumb:first', dom.toggleWrap);

            this.dom = dom;
            return this;
        },        
        setDisable: function (value) {
            var $element = this.$element,
                dom = this.dom, opt = this.options;

            opt.disabled = value ? true : false;
            if (value) {
                $element.attr('disabled', true);
                dom.toggle.addClass("rwpToggleDisabled");
            }
            else {
                $element.attr('disabled', false);
                dom.toggle.removeClass("rwpToggleDisabled");
            }

            return this;
        },
        _initStyle: function() {
            var dom = this.dom, $toggleThumb = dom.toggleThumb,
                $toggleOn = dom.toggleOn, $toggleOff = dom.toggleOff;
            
            this.thumbHeightDeduct = $toggleThumb.outerHeight(true) - $toggleThumb.height();
            this.onHeightDeduct = $toggleOn.outerHeight(true) - $toggleOn.height();
            this.offHeightDeduct = $toggleOff.outerHeight(true) - $toggleOff.height();

            this.onOffOuterWidth = Math.max($toggleOn.outerWidth(true), $toggleOff.outerWidth(true));  //开关span宽度一致
            this.onWidthDeduct = $toggleOn.outerWidth(true) - $toggleOn.width();
            this.offWidthDeduct = $toggleOff.outerWidth(true) - $toggleOff.width();

            $toggleOn.width(this.onOffOuterWidth - this.onWidthDeduct);
            $toggleOff.width(this.onOffOuterWidth - this.offWidthDeduct);  //初始化开关span的宽度

            return this;
        },
        setWidth: function (value) {
            var dom = this.dom, autualWidth = 0,
                thumbOuterWidth = dom.toggleThumb.outerWidth(true);  //获取中间留白的宽度

            if ($.isNumeric(value)) {  //判断宽度是否设置为数字
                autualWidth = value;
            }
            else if (value == 'auto') {  //取开关span元素宽度最大值                    
                autualWidth = thumbOuterWidth + this.onOffOuterWidth;
            }
            if (autualWidth < this.onOffOuterWidth + 30) {
                autualWidth = this.onOffOuterWidth + 30;  //最小宽度
            }            
            this.toggleWidth = autualWidth;    //得到显示区域的宽度             

            dom.toggle.width(this.toggleWidth);
            dom.toggleWrap.width((this.onOffOuterWidth) * 2 + thumbOuterWidth);  //设置包裹的宽度

            return this;
        },
        setHeight: function (value) {
            var dom = this.dom, autualHeight = 0,
                $toggleThumb = dom.toggleThumb, $toggleOn = dom.toggleOn, $toggleOff = dom.toggleOff;

            if ($.isNumeric(value)) {  //判断高度是否设置为数字
                autualHeight = value;
            }
            else if (value == 'auto') {  //取三个span元素高度最大值
                autualHeight = Math.max($toggleThumb.outerHeight(true), $toggleOn.outerHeight(true), $toggleOff.outerHeight(true));
            }
            autualHeight = autualHeight > 20 ? autualHeight : 20;  //最小高度

            this.toggleHeight = autualHeight;
            $toggleThumb.height(autualHeight - this.thumbHeightDeduct);   //span元素高度保持一致
            $toggleOn.height(autualHeight- this.onHeightDeduct);
            $toggleOff.height(autualHeight- this.offHeightDeduct);

            return this;
        },
        _initValue: function () {  //初始控件显示（判断checkbox是否选中）
            var that = this, isCheck = this.$element.prop('checked'),
                $toggleWrap = this.dom.toggleWrap, $toggle = this.dom.toggle,
                $toggleThumb = this.dom.toggleThumb, opt = this.options;

            if (!isCheck) {  //checkbox没有选中
                $toggleWrap.css('margin-left', - that.onOffOuterWidth);  //设置margin-left显示为关闭
                $toggleThumb.text(opt.offtext);
            }                
            else {
                $toggle.addClass('rwpToggleActive');  //添加样式表明开启
                $toggleThumb.text(opt.ontext);
            }

            return this;
        },
        _setValue: function (value) {
            var that = this, opt = this.options, marginleftwidth = 0,
                $toggleWrap = this.dom.toggleWrap, $toggle = this.dom.toggle,
                $toggleThumb = this.dom.toggleThumb;

            this.value = value ? true : false;
            this.$element.prop('checked', that.value);            
            if (this.value) {
                $toggle.addClass('rwpToggleActive');
                $toggleThumb.text(opt.ontext);
            }
            else {
                marginleftwidth = - that.onOffOuterWidth;
                $toggle.removeClass('rwpToggleActive');
                $toggleThumb.text(opt.offtext);
            }
            $toggleWrap.animate({ marginLeft: marginleftwidth }, opt.duration);
            this.$element.trigger('change');  //触发checkbox的change事件

            return this;
        },
        _addClickEvent: function () {   //添加点击事件
            var that = this, opt = this.options,
                checkboxID = this.$element.attr('id'),
                $toggleWrap = this.dom.toggleWrap,
                $toggle = this.dom.toggle;

            var clickEvent = function () {
                if (opt.disabled) return false;   //阻止默认的点击操作
                else that._setValue(!$toggle.hasClass("rwpToggleActive"));
            };

            $toggleWrap.click(clickEvent);    //外层包裹 点击事件

            if (checkboxID) {     //label标签的点击事件
                $("label[for=" + checkboxID + "]").click(clickEvent);
            }

            return this;
        }
    };

    $.rwpUI.Toggle = function (element, options) {
        (new rwpToggle(element, options));
    };

    $.rwpUI.Toggle.defaults = {    //默认参数
        disabled: false,      //是否禁用
        ontext: '是',        //开启显示文本
        offtext: '否',        //关闭显示文本
        width: 'auto',        //显示宽度
        height: 'auto',　　　　//显示高度
        duration: 250          //动画时长毫秒数
    };

    $.rwpUI.Toggle._template = '<div class="rwpToggleWrapper">'
                                    + '<span class="rwpToggleLabel rwpToggleOn"></span>'
                                    + '<span class="rwpToggleThumb"></span>'
                                    + '<span class="rwpToggleLabel rwpToggleOff"></span>'
                                + '</div>';

    $.rwpUI.Toggle.fn = rwpToggle;  //指向rwpToggle    

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Toggle(element, options);
        });
    };

})(jQuery, window, document);