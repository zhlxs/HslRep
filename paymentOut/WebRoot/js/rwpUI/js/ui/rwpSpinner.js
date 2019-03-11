/**
* @license                                     
* Spinner微调器控件
*
* Since: 2013/2/5
*/
; (function ($, window, document, undefined) {

        var pluginName = 'rwpUISpinner';   //控件名称

        var rwpSpinner = function (element, options) {
                this.element = element;
                this.$element = $(element);

                var opt = $.extend({}, $.rwpUI.Spinner.defaults, options);  //合并已赋值参数
                this.options = opt;

                this._init();  //初始化
                $.data(this.element, pluginName, this);   //保存到元素上
        };

        rwpSpinner.prototype = {
                _init: function () {
                        var opt = this.options, $element = this.$element;

                        this._domHtml();
                        if ($element.attr('disabled') || $element.attr('readonly'))  //优先取input中属性 
                                opt.disabled = true;

                        this.setDisable(opt.disabled)
                            .setWidth(opt.width)
                            .setHeight(opt.height);

                        this._validStep()
                            ._initValue()   //值初始化
                            ._bindEvent();  //绑定事件
                },
                _domHtml: function () {    //dom操作
                        var element = this.element, $element = this.$element, dom = {};
                        if (element.tagName.toLowerCase() == "input" && element.type && element.type == "text") {
                                dom.inputText = $element;
                        }
                        else {
                                dom.inputText = $('<input type="text"/>');
                                dom.inputText.appendTo(element);
                        }
                        if (!dom.inputText.hasClass('rwpInputText')) dom.inputText.addClass('rwpInputText')
                        dom.spinner = dom.inputText.wrap('<div class="rwpSpinner"></div>').parent();
                        dom.spinnerButton = $('<span class="rwpSpinnerButton"><span class="rwpSpinnerUp"></span><span class="rwpSpinnerDown"></span></span>');
                        dom.spinner.append(dom.spinnerButton);
                        dom.spinnerButtonUp = $('.rwpSpinnerUp', dom.spinnerButton);
                        dom.spinnerButtonDown = $('.rwpSpinnerDown', dom.spinnerButton);

                        this.dom = dom;
                        return this;
                },
                setDisable: function (value) {
                        this.options.disabled = value ? true : false;
                        this.dom.spinner[value ? 'addClass' : 'removeClass']('rwpSpinnerDisabled');
                        return this;
                },
                setWidth: function (value) {
                        if (value === +value && value > 30) {
                                var dom = this.dom;
                                dom.spinner.css({ width: value + dom.inputText.outerWidth() - dom.inputText.width() });  //加上文本框的padding
                                dom.inputText.css({ width: value - 30 });
                        }
                        return this;
                },
                setHeight: function (value) {
                        if (value === +value && value > 10) {
                                var dom = this.dom;
                                dom.spinner.height(value);
                                dom.inputText.height(value);
                                dom.inputText.css("line-height", value + "px");  //使input内文本垂直居中
                                dom.spinnerButton.height(value);
                        }
                        return this;
                },
                _validStep: function () {  //更改递增量
                        var opt = this.options;

                        if (opt.type == 'int' && parseFloat(opt.step) < 1)
                                opt.step = 1;
                        else if (opt.type == 'time' && parseFloat(opt.step) < 1)
                                opt.step = 1;

                        return this;
                },
                _initValue: function () {
                    var opt = this.options,
                        $inputText = this.dom.inputText,
                        inputValue = $inputText.val();

                    if (!this._isVerify(inputValue)) {
                        this.value = this._getDefaultValue();
                        $inputText.val(opt.defaultValueText || this.value);
                    }
                    else
                        this.value = inputValue;

                    return this;
                },
                _isVerify: function (str) {   //判断值是否正确
                        var opt = this.options, value;

                        if (opt.type == 'float') {
                                if (!this._isFloat(str)) return false;
                                value = parseFloat(str);
                                if ($.isNumeric(opt.minValue) && opt.minValue > value) return false;
                                if ($.isNumeric(opt.maxValue) && opt.maxValue < value) return false;
                                return true;
                        } else if (opt.type == 'int') {
                                if (!this._isInt(str)) return false;
                                value = parseInt(str);
                                if ($.isNumeric(opt.minValue) && opt.minValue > value) return false;
                                if ($.isNumeric(opt.maxValue) && opt.maxValue < value) return false;
                                return true;
                        } else if (opt.type == 'time') {
                                return this._isTime(str);
                        }
                        return false;
                },
                _isInt: function (str) {   //判断是否为整数              
                        var strP = /^-?\d+$/;
                        if (!strP.test(str)) return false;
                        if (parseFloat(str) != str) return false;
                        return true;
                },
                _isFloat: function (str) {    //判断是否为小数            
                        var strP = /^-?\d+(\.\d+)?$/;
                        if (!strP.test(str)) return false;
                        if (parseFloat(str) != str) return false;
                        return true;
                },
                _isTime: function (str) {    //判断是否为时间            
                        var a = str.match(/^(\d{1,2}):(\d{1,2})$/);
                        if (a == null) return false;
                        if (a[1] > 23 || a[2] > 59) return false;
                        return true;
                },
                _getDefaultValue: function () {   //获取默认值
                        var opt = this.options;

                        if (opt.type == 'float' || opt.type == 'int') {
                            return 0;
                        }
                        else if (opt.type == 'time') {
                            return "00:00";
                        }
                },
                _bindEvent: function () {
                        var that = this, opt = this.options, $inputText = this.dom.inputText;

                        $inputText.change(function () {
                                var value = $inputText.val();
                                that.value = that._getVerifyValue(value);
                                $inputText.val(that.value);
                                if (opt.onChangeValue && typeof (opt.onChangeValue) == 'function')
                                        opt.onChangeValue(that.value);    //触发值改变事件
                        });

                        this._setUpDownEvent();  //绑定递增递减事件

                        return this;
                },
                _getVerifyValue: function (value) {   //获取正确的值             
                        var opt = this.options, newvalue = null;
                        if (opt.type == 'float') {
                                newvalue = this._round(value, opt.decimalplace);
                        } else if (opt.type == 'int') {
                                newvalue = parseInt(value);
                        } else if (opt.type == 'time') {
                                newvalue = value;
                        }
                        if (!this._isVerify(newvalue)) {
                                return this.value;
                        } else {
                                return newvalue;
                        }
                },
                _round: function (v, e) {   //获取保留小数位值             
                        var t = 1;
                        for (; e > 0; t *= 10, e--);
                        for (; e < 0; t /= 10, e++);
                        return Math.round(v * t) / t;
                },
                _setUpDownEvent: function () {   //设置递增递减事件
                        var opt = this.options, that = this,
                            $spinnerButtonUp = this.dom.spinnerButtonUp,
                            $spinnerButtonDown = this.dom.spinnerButtonDown;

                        var clearInterval = function () {
                                that._clearInterval();
                        },
                        mouseDownEvent = function (callback) {
                                return function () {
                                        if (!opt.disabled && callback && typeof (callback) == 'function') {
                                                that.interval = setInterval(function () {
                                                        callback.apply(that);
                                                }, opt.interval);
                                        }
                                };
                        };

                        $spinnerButtonUp.mousedown(mouseDownEvent(that._uping))
                            .mouseup(clearInterval)
                            .mouseleave(clearInterval);

                        $spinnerButtonDown.mousedown(mouseDownEvent(that._downing))
                            .mouseup(clearInterval)
                            .mouseleave(clearInterval);

                        return this;
                },
                _clearInterval: function () {  //清除定时器
                        var opt = this.options,
                            intervalID = this.interval,
                            $inputText = this.dom.inputText;

                        setTimeout(function () {
                                intervalID && clearInterval(intervalID);
                                //$inputText.trigger("change");
                        }, opt.interval);

                        return this;
                },
                _uping: function () {  //递增
                        var opt = this.options;

                        if (opt.type == 'float' || opt.type == 'int') {
                                this._addValue(opt.step);
                        } else if (opt.type == 'time') {
                                this._addTime(opt.step);
                        }

                        return this;
                },
                _downing: function () {  //递减
                        var opt = this.options;

                        if (opt.type == 'float' || opt.type == 'int') {
                                this._addValue(-1 * opt.step);
                        } else if (opt.type == 'time') {
                                this._addTime(-1 * opt.step);
                        }

                        return this;
                },
                _addValue: function (num) {   //值增减
                        var $inputText = this.dom.inputText,
                            value = $inputText.val();

                        if (!$.isNumeric(value)) {
                            value = this.value;
                        }
                        value = parseFloat(value) + num;
                        if (this._isOverValue(value)) return;

                        $inputText.val(value);
                        $inputText.trigger("change");

                        return this;
                },
                _addTime: function (minute) {   //时间增减             
                        var $inputText = this.dom.inputText,
                            value = $inputText.val(),
                            a = value.match(/^(\d{1,2}):(\d{1,2})$/),
                            addhour = Math.floor((parseInt(a[2]) + minute) / 60),  //获取增加的小时数
                            newminute = parseInt(a[2]) + minute - addhour * 60;

                        if (newminute < 10) newminute = "0" + newminute;
                        newhour = parseInt(a[1]) + addhour;  //得到新的小时数
                        value = newhour + ":" + newminute;
                        if (this._isOverValue(value)) return;

                        $inputText.val(value);
                        $inputText.trigger("change");

                        return this;
                },
                _isOverValue: function (value) {    //是否超出最小最大值限制
                        var opt = this.options;

                        if ($.isNumeric(opt.minValue) && opt.minValue > value) return true;
                        if ($.isNumeric(opt.maxValue) && opt.maxValue < value) return true;

                        return false;
                },
                setValue: function (value) {
                        this.dom.inputText.val(value);

                        return this;
                }
        };

        $.rwpUI.Spinner = function (element, options) {
                (new rwpSpinner(element, options));
        };

        $.rwpUI.Spinner.defaults = {    //默认参数
                type: 'float',     //类型 float:浮点数 int:整数 time:时间        
                decimalplace: 2,   //小数位 type=float时起作用
                step: 0.1,         //每次增减量
                interval: 100,      //间隔，毫秒
                onChangeValue: false,    //改变值事件
                minValue: null,        //最小值
                maxValue: null,         //最大值
                disabled: false,
                height: 'auto',
                width: 'auto',
                defaultValueText: null   //默认值显示文本
        };

        $.rwpUI.Spinner.fn = rwpSpinner;  //指向rwpSpinner

        $.fn[pluginName] = function (options) {
                if (this.length == 0) return;
                this.each(function () {
                        var element = this;
                        if ($.data(element, pluginName)) return;
                        $.rwpUI.Spinner(element, options);
                });
        };

})(jQuery, window, document);