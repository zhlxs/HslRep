/**
* @license                                     
* switch单选框、复选框列表控件
*
* Since: 2013/2/5
*/
; (function ($, window, document, undefined) {
    
    var pluginName = 'rwpUISwitch';   //控件名称

    var rwpSwitch = function (element, options) {
        var lowerTagName = element.tagName.toLowerCase();   //获取元素tag名称

        if (lowerTagName != 'input') {   //不支持input元素
            this.element = element;
            this.$element = $(element);

            var opt = $.extend({}, $.rwpUI.Switch.defaults, options);  //合并已赋值参数
            this.options = opt;

            this._init();  //初始化
            $.data(this.element, pluginName, this);   //保存到元素上
        }
    };

    rwpSwitch.prototype = {
        _init: function () {
            var opt = this.options;

            if (!this.$element.hasClass('rwpSwitch')) this.$element.addClass('rwpSwitch');            

            this._setData();  //通过data生成input元素和label标签              

            this._getChildInputs()
                ._setInputLabels()
                .setHeight(opt.labelheight);   
        },
        _setData: function () {    //加载data
            var opt = this.options,
                $element = this.$element,
                data = opt.data,
                dataAttrs = opt.dataAttrs;
            
            if (data && data.length) {
                var inputHtmlArr = this._getHtmlByData(data);
                if (inputHtmlArr.length > 0) {
                    $element.append(inputHtmlArr.join(''));  //通过html字符串添加dom元素
                }
            }

            return this;
        },
        _getHtmlByData: function (newData) {   //根据data生成html
            var innerHtmlArr = [],
                dataAttrs = this.options.dataAttrs;

            $.each(newData, function (i, item) {
                var inputID = item[dataAttrs.id],
                    inputName = item[dataAttrs.name],
                    inputType = item[dataAttrs.type],
                    labelText = item[dataAttrs.text],
                    checked = item[dataAttrs.ischeck] ? true : false,
                    disabled = item[dataAttrs.isdisable] ? true : false;

                if ((inputType == 'radio' || inputType == 'checkbox')
                    && inputID && labelText && !(inputType == 'radio' && !inputName)) {
                    innerHtmlArr.push('<input type ="' + inputType + '" id="' + inputID + '"'
                        + (inputName ? (' name="' + inputName + '"') : '')
                        + (checked ? ' checked' : '') + (disabled ? ' disabled' : '') + '/>');  //添加input元素
                    innerHtml.push('<label for="' + inputID + '">' + labelText + '</label>');   //添加对应label元素
                }
            });
            return innerHtml;
        },        
        _getChildInputs: function () {   //获取下属input元素（包括radio和checkbox）
            var $switch = this.$element,
                inputDom = { radios: [], checkboxs: [] };  //radio和checkbox的集合               
                
            $('input:radio, input:checkbox', $switch).each(function (i, input) {
                input = $(input);
                var inputName = input.attr('name'),
                    inputID = input.attr('id'),
                    inputType = input.attr('type');
                if (!(inputType == 'radio' && !inputName) && inputID) {   //必须有id属性,radio必须有name属性
                    var inputLabel = $('label[for=' + inputID + ']', $switch);
                    if (inputLabel.length > 0) {   //必须存在关联label元素
                        if (inputType == 'radio') {
                            if (!inputDom.radios[inputName]) {  //radio按name分组
                                inputDom.radios[inputName] = [];
                            }
                            inputDom.radios[inputName].push({ input: input, label: inputLabel });
                        }
                        else
                            inputDom.checkboxs.push({ input: input, label: inputLabel });
                    }
                }
            });               
            
            this.inputDom = inputDom;
            return this;
        },        
        _setInputLabels: function () {   //设置input元素关联label标签的位置、样式及绑定事件
            var inputDom = this.inputDom, opt = this.options, $switch = this.$element;

            //设置label点击事件
            var setLabelClickEvent = function ($input, $label) {
                var inputType = $input.attr('type');
                inputType == 'checkbox' && $label.click(function (e) {
                    e.preventDefault();
                    if (!$input.attr('disabled')) {
                        $(this).toggleClass('active');
                        $input.click(); //解决IE6、7、8在input隐藏的情况下不同步更新input选中状态
                        opt.onAfterSwitch && typeof (opt.onAfterSwitch) == 'function' && opt.onAfterSwitch($input.val());
                    }
                });
                inputType == 'radio' && $label.click(function (e) {
                    e.preventDefault();
                    if (!$input.attr('disabled')) {
                        $.each(inputDom.radios[$input.attr('name')], function (j, item) {
                            item.label.removeClass('active');  //移除同组所有label标签选中
                        });
                        $(this).addClass('active');  //当前label设置选中
                        $input.click();  //解决IE6、7、8在input隐藏的情况下不同步更新input选中状态
                        opt.onAfterSwitch && typeof (opt.onAfterSwitch) == 'function' && opt.onAfterSwitch($input.val());
                    }
                });
            };
            var setInputLabels = function (inputlabel, index, inputArray) {  //调整input列表label元素位置及样式
                if (inputlabel && inputlabel.input && inputlabel.label
                    && $.isNumeric(index) && $.isArray(inputArray)) {
                    index = parseInt(index);

                    var $input = inputlabel.input,
                        $label = inputlabel.label;                                              

                    $input.hide();   //隐藏input元素
                    $label.addClass('rwpSwitchItem');                    

                    if (index === 0) $label.addClass("first");  //最左边元素                              
                    if (index > 0) {
                        inputArray[index - 1].label.after($label);  //label位置在dom中顺序排列
                    }
                    $label[$input.prop('checked') ? 'addClass' : 'removeClass']('active');  //设置选中元素                    
                    if (index === inputArray.length - 1)
                        $label.addClass("last");  //最右边元素
                    setLabelClickEvent($input, $label);  //设置点击事件                    
                }
            };
            
            this._setElements(setInputLabels);
            return this;
        },
        setDisabled: function (value) {
            value = value ? true : false;
            
            this._setInputDisabled(value);
            this.$element[value ? 'addClass' : 'removeClass']("rwpSwitchDisabled");            
            
            return this;
        },        
        _setInputDisabled: function (value) {  //设置input元素的禁用
            var setInputDisabled = function (inputlabel) {
                if (inputlabel && inputlabel.input && typeof (value) == 'boolean') {
                    inputlabel.input.attr('disabled', value);   //设置input元素禁用状态
                }
            };            

            this._setElements(setInputDisabled);
            return this;
        },
        setHeight: function (value) {
            if ($.isNumeric(value) && value > 18) {
                this._setLabelHeight(value);
            }

            return this;
        },        
        _setLabelHeight: function (value) {   //设置label元素的高度
            var setLabelHeight = function (inputlabel) {
                if (inputlabel && inputlabel.label && $.isNumeric(value)) {
                    inputlabel.label.height(parseFloat(value));   //设置label元素高度 
                }
            };

            this._setElements(setLabelHeight);
            return this;
        },
        _setElements: function (domFunction) {    //设置radio、checkbox及关联label
            var inputDom = this.inputDom;

            var setInputDom = function (callback) {
                var setInputArray = function (inputArray) {
                    if ($.isArray(inputArray)) {
                        for (var group in inputArray) {
                            var inputGroup = inputArray[group];
                            if ($.isArray(inputGroup))  //radio列表进行递归
                                setInputArray(inputGroup);
                            else if (callback && typeof (callback) == 'function')
                                callback.apply(window, [inputGroup, group, inputArray]);   //group为index索引号
                        }
                    }
                };

                for (var key in inputDom) {
                    setInputArray(inputDom[key]);
                }
            };

            setInputDom(domFunction);   //执行函数调用

            return this;
        }
    };

    $.rwpUI.Switch = function (element, options) {
        (new rwpSwitch(element, options));
    };

    $.rwpUI.Switch.dataAttrs = {   //读取数据属性
        id: 'id',
        type: 'type',
        name: 'name',
        text: 'text',
        ischeck: 'ischeck',
        isdisable: 'isdisable'
    };

    $.rwpUI.Switch.defaults = {    //默认参数
        labelheight: 'auto',　　//显示高度
        data: null,             //数据源
        dataAttrs: $.rwpUI.Switch.dataAttrs,   //数据源属性
        onAfterSwitch: null     //switch选中后响应事件
    };    

    $.rwpUI.Switch.fn = rwpSwitch;  //指向rwpSwitch

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Switch(element, options);
        });
    };

})(jQuery, window, document);