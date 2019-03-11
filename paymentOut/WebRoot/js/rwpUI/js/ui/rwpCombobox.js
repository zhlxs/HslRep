/**
* @license                                     
* Combobox下拉框控件
*
* Since: 2013/2/7
*/
; (function ($, window, document, undefined) {    

    var _count = 0;  //窗口combobox计数        

    var pluginName = 'rwpUICombobox';   //控件名称

    var rwpCombobox = function (element, options) {
        var lowerTagName = element.tagName.toLowerCase();   //获取元素tag名称

        if (lowerTagName == 'input' || lowerTagName == 'select') {   //只支持input或select
            this.element = element;
            this.$element = $(element);

            var opt = $.extend({}, $.rwpUI.Combobox.defaults, options);  //合并已赋值参数
            this.options = opt;

            this._init();  //初始化
            _count++;

            $.data(this.element, pluginName, this);   //保存到元素上
        }
    };

    rwpCombobox.prototype = {
        _init: function () {
            var opt = this.options;

            this.data = opt.data;
            this._domHtml();  //生成dom元素

            this.setDisabled(opt.disabled)
                .setWidth(opt.width)
                .setHeight(opt.height);

            this._setComboClick();  //绑定控件点击事件
            
            this._bulidContent()    //生成下拉框内容
                ._setSelectBoxEvent()   //绑定下拉框鼠标移入移出事件
                ._setSelectBoxWidth()
                ._setSelectBoxHeight();

        },
        _domHtml: function () {
            var element = this.element, $element = this.$element, opt = this.options, that = this,
                selectBoxTemplate = $.rwpUI.Combobox._selectBoxTemplate, zIndex,
                comboboxDom = {}, selectBoxDom = {}, inputValue = null, isNewInputValue = false;  //保存值的input

            if (element.tagName.toLowerCase() == "input") {
                comboboxDom.inputText = $element;
                $element.prop('readonly', true);   //设置只读
                that.textFieldDomID = $element.attr('id') || $.rwpUI.guid('rwpCombobox_');  //得到inputText的ID
            }
            else if (element.tagName.toLowerCase() == "select") {
                $element.hide();
                that.isSelect = true;   //标识为操作select元素 生成下拉框
                opt.isMultiSelect = false;
                that.textFieldDomID = ($element.attr('id') || $.rwpUI.guid('rwpCombobox_')) + '_txt';  //得到inputText的ID
                comboboxDom.inputText = $('<input type="text" readonly="readonly"/>');
                comboboxDom.inputText.attr("id", that.textFieldDomID).insertAfter($element);  //插入到select元素之后
            } else {
                //不支持其他类型
                return this;
            }
            comboboxDom.inputText.addClass('rwpInputText');

            if (opt.valueFieldDomID) {
                inputValue = $("#" + opt.valueFieldDomID + ":input");
                if (inputValue.length == 0) {
                    isNewInputValue = true;
                    inputValue = $('<input type="hidden"/>');  //没有找到就创建
                    inputValue.attr({ id: opt.valueFieldDomID, name: opt.valueFieldDomID });  //设置ID和名称 用于在form中提交
                }                
            }
            else {
                isNewInputValue = true;
                inputValue = $('<input type="hidden"/>');
                inputValue.attr({ id: that.textFieldDomID + '_val', name: that.textFieldDomID + '_val' });
            }
            comboboxDom.inputValue = inputValue;

            comboboxDom.comboButton = $('<a href="javascript:void(0);" class="rwpComboboxButton"><span class="rwpComboboxDown"></span></a>');
            comboboxDom.comboButtonDown = $('.rwpComboboxDown', comboboxDom.comboButton);

            //下拉框
            selectBoxDom.comboSelectBox = $(selectBoxTemplate);
            if (opt.zindex === +opt.zindex)
                selectBoxDom.comboSelectBox.css('z-index', opt.zindex);
            else {
                if ($.rwpUI.Dialog && $.rwpUI.Dialog.topzIndex) 
                    zIndex = $.rwpUI.Dialog.topzIndex++;   //关联dialog控件的zIndex                
                else
                    zIndex = $.rwpUI.Combobox.initzIndex + _count;
                selectBoxDom.comboSelectBox.css('z-index', zIndex);  //设置下拉框的z-index
            }
            if (opt.id && typeof (opt.id) == 'string')
                selectBoxDom.comboSelectBox.attr('id', opt.id);  //下拉框设置ID
            selectBoxDom.comboSelectTable = $("table:first", selectBoxDom.comboSelectBox);

            //外层包裹
            comboboxDom.comboWrap = comboboxDom.inputText.wrap('<div class="rwpCombobox"></div>').parent();
            comboboxDom.comboWrap.append(comboboxDom.comboButton);
            if (isNewInputValue) {  //控件中创建的inputValue需添加到comboWrap元素下
                comboboxDom.comboWrap.append(comboboxDom.inputValue);
            }

            selectBoxDom.comboSelectBox.appendTo('body');  //下拉框附加到body最后

            this.comboboxDom = comboboxDom;
            this.selectBoxDom = selectBoxDom;

            return this;
        },
        _setComboClick: function () {   //设置下拉按钮与文本框的点击事件
            var opt = this.options, that = this,
                $comboButton = this.comboboxDom.comboButton,
                $inputText = this.comboboxDom.inputText;

            var clickBind = function () {
                if (opt.disabled) return;
                if (opt.onBeforeOpen && typeof (opt.onBeforeOpen) == 'function' && opt.onBeforeOpen() == false) return false;
                that._toggleSelectBox();
            };
            $comboButton.click(clickBind);
            $inputText.click(clickBind);

            return this;
        },
        _toggleSelectBox: function () {   //切换下拉框的显示状态
            var $selectBox = this.selectBoxDom.comboSelectBox,
                isShow = $selectBox.is(":visible"), opt = this.options;

            if (isShow) {
                $selectBox.hide();
            }
            else {
                var $comboWrap = this.comboboxDom.comboWrap,
                    offset = $comboWrap.offset(),                    
                    top = offset.top + $comboWrap.outerHeight(); //控件底部top                
                                      
                $selectBox.css({ "left": offset.left + "px", "top": top + "px" }).show();

                if ($('tr.rwpComboboxItemSelected', $selectBox).length > 0) {
                    var selectOffSet = ($selectBox.scrollTop() + $('tr.rwpComboboxItemSelected', $selectBox).offset().top - $selectBox.offset().top);
                    $selectBox.animate({ scrollTop: selectOffSet });
                }
            }

            return this;
        },
        _bulidContent: function () {   //生成下拉框内容
            var opt = this.options,
                $selectBoxTable = this.selectBoxDom.comboSelectTable;

            if (this.isSelect) {
                this._setSelect();
            }
            else if (this.data) {
                this._setData();
            }            
            else if (opt.url) {                
                this._setUrl();
            }

            $selectBoxTable.siblings().remove();  //移除所有同级元素
            $selectBoxTable.show();   //显示下拉框表格

            return this;
        },
        _setSelectBoxEvent: function () {
            var that = this,
                $selectBox = this.selectBoxDom.comboSelectBox;
            
            $selectBox.hover(null, function (e) {
                if ($selectBox.is(":visible")) {
                    that._toggleSelectBox();
                }
            });

            return this;
        },
        //清除下拉框内容
        _clearContent: function () {
            this.selectBoxDom.comboSelectTable.html('');

            return this;
        },        
        _setSelect: function () {   //根据option生成下拉框内容
            var select = this.element, $select = this.$element, opt = this.options,
                $selectBox = this.selectBoxDom.comboSelectBox,
                $selectBoxTable = this.selectBoxDom.comboSelectTable,
                $inputText = this.comboboxDom.inputText,
                $inputValue = this.comboboxDom.inputValue,
                $selectTr, newIndex = 0, $selectTd, selectValue, selectText;

            this._clearContent();
            $('option', $select).each(function (i, item) {
                var $option = $(item),                                     
                    $tr = $('<tr index="' + i + '"><td value="' + $option.val() + '">' + $option.html() + '</td></tr>');
                $selectBoxTable.append($tr);
            });

            $selectTr = $('tr:eq(' + select.selectedIndex + ')', $selectBoxTable);   //获取选中的Tr
            
            if ($selectTr.hasClass('rwpComboboxItemSelected')) {   //判断该项是否已选中
                $selectBox.hide();
            }
            else {
                newIndex = parseInt($selectTr.attr('index'));  //获取新的索引

                $('.rwpComboboxItemSelected', $selectBox).removeClass('rwpComboboxItemSelected');
                $selectTr.addClass('rwpComboboxItemSelected');
                if (select.selectedIndex !== newIndex && select.onchange) {   //针对IE支持
                    select.selectedIndex = newIndex;
                    select.onchange();   //触发onchange事件
                }                 
                select.selectedIndex = newIndex;
                $select.trigger("change");
                $selectBox.hide();

                $selectTd = $('td:first', $selectTr);
                selectValue = $selectTd.attr("value");  //选中值
                selectText = $selectTd.html();  //选中文本
                this._changeValue(selectValue, selectText);
            }
            this._addSingleClickEvent();

            return this;
        },
        _addSingleClickEvent: function () {   //添加下拉框单选点击响应事件
            var that = this, $selectBox = this.selectBoxDom.comboSelectBox;

            $('tr', $selectBox).click(function () {
                var $clickTr = $(this),
                    $clickTd = $('td:first', $clickTr),
                    clickValue = $clickTd.attr('value'),
                    clickIndex = parseInt($clickTr.attr('index')),
                    clickText = $clickTd.html();

                if ($clickTr.hasClass('rwpComboboxItemSelected')) {
                    $selectBox.hide();   //隐藏下拉框
                    return;
                }                
                $('.rwpComboboxItemSelected', $selectBox).removeClass('rwpComboboxItemSelected');
                $clickTr.addClass('rwpComboboxItemSelected');
                if (that.isSelect) {   //判断是否为Select元素
                    if (that.element.selectedIndex !== clickIndex) {
                        that.element.selectedIndex = clickIndex;
                        that.$element.trigger("change");
                    }
                }
                $selectBox.hide(); //隐藏下拉框
                that._changeValue(clickValue, clickText);
            });

            return this;
        },
        _changeValue: function (newValue, newText) {   //设置值到 文本框和隐藏域
            var opt = this.options,
                $inputText = this.comboboxDom.inputText,
                $inputValue = this.comboboxDom.inputValue;

            $inputValue.val(newValue);
            if (opt.render) {
                $inputText.val(opt.render(newValue, newText));
            }
            else {
                $inputText.val(newText);
            }
            this.selectedValue = newValue;
            this.selectedText = newText;
            $inputText.trigger("change");

            return this;
        },
        _setData: function () {   //通过data生成下拉框内容
            var opt = this.options, data = this.data, value, text,
                $selectTable = this.selectBoxDom.comboSelectTable,
                $selectBox = this.selectBoxDom.comboSelectBox;

            this._clearContent();

            if (!data || !data.length) return;
            for (var i = 0, len = data.length; i < len; i++) {
                value = data[i][opt.valueField];
                text = data[i][opt.textField];
                if (opt.isMultiSelect)
                    $selectTable.append('<tr index="' + i + '"><td class="rwpComboboxCheckboxWrap" value="' + value + '" text="' + text + '" ><input type="checkbox" /></td><td value="' + value + '" >' + text + '</td></tr>');
                else
                    $selectTable.append('<tr index="' + i + '"><td value="' + value + '" >' + text + '</td></tr>');

            }
            
            this._initValue();  //选择项初始化

            if (!opt.isMultiSelect) this._addSingleClickEvent();  //单选时tr click事件
            else this._addMultiClickEvent(); //多选时tr click事件

            $selectBox.hide();

            return this;
        },
        _addMultiClickEvent: function () {   //添加下拉框多选点击响应事件
            var that = this, $selectTable = this.selectBoxDom.comboSelectTable;

            $('tr', $selectTable).click(function () {  
                var $clickTr = $(this),
                    selected = $clickTr.hasClass('rwpComboboxItemSelected');

                $clickTr[selected ? 'removeClass' : 'addClass']('rwpComboboxItemSelected');  //设置选中样式
                $('input:checkbox', $clickTr).prop("checked", !selected);
                that._checkboxUpdateValue();
            });

            return this;
        },
        _checkboxUpdateValue: function () {  //更新选中的值(复选框)
            var $selectTable = this.selectBoxDom.comboSelectTable,
                opt = this.options, valueStr = '', textStr = '';

            $("input:checked", $selectTable).each(function () {
                var parentTD = $(this).parent();
                if (!parentTD) return;
                valueStr += parentTD.attr('value') + opt.split;
                textStr += parentTD.attr('text') + opt.split;
            });
            if (valueStr.length > 0) valueStr = valueStr.substr(0, valueStr.length - 1);  //去除最后一个分隔符号
            if (textStr.length > 0) textStr = textStr.substr(0, textStr.length - 1);
            this._changeValue(valueStr, textStr);

            return this;
        },       
        _initValue: function () {   //设置 初始选项值
            var opt = this.options,
                value = opt.initValue,
                $inputValue = this.comboboxDom.inputValue;

            if (value == null)
                value = $inputValue.val();  //没传初始值时 尝试获取 input的值
            if (value) {
                this._setValue(value);
            }

            return this;
        },        
        _setValue: function (value) {   //设置选中值
            var opt = this.options,
                $selectTable = this.selectBoxDom.comboSelectTable,
                text = this._getTextByValue(value), $selectTr, selectIndex;

            if (value != null && value != undefined) {
                this._changeValue(value, text);

                if (!opt.isMultiSelect) {
                    $selectTr = $('td[value=' + value + ']', $selectTable).parent('tr');
                    $selectTr.addClass('rwpComboboxItemSelected');   //改变tr的选中样式
                    $('td[value!=' + value + ']', $selectTable).parent('tr').removeClass('rwpComboboxItemSelected');

                    if (this.isSelect && $selectTr.length == 1) {   //判断是否为Select元素
                        selectIndex = parseInt($selectTr.attr('index'));
                        if (this.element.selectedIndex !== selectIndex) {
                            this.element.selectedIndex = selectIndex;
                            this.$element.trigger("change");
                        }
                    }
                }
                else {
                    var targetdata = value.toString().split(opt.split);
                    $('tr', $selectTable).removeClass('rwpComboboxItemSelected');    //移除所有行的选中样式
                    $('input:checkbox', $selectTable).each(function () {  //所有checkbox设置为未选中                    
                        $(this).prop('checked', false);
                    });
                    for (var i = 0, len = targetdata.length; i < len; i++) {    //循环设置选中的行
                        $('td[value=' + targetdata[i] + ']', $selectTable).each(function () {
                            var $selectTd = $(this);
                            $selectTd.parent('tr').addClass('rwpComboboxItemSelected');
                            $('input:checkbox', $selectTd).prop('checked', true);
                        });
                    }
                }
            }

            return this;
        },        
        _getTextByValue: function (value) {  //查找Text,适用多选和单选
            var opt = this.options, that = this,
                $selectTable = this.selectBoxDom.comboSelectTable,
                texts = '';  //输出Text文本
            var contain = function (checkvalue) {    //判断值是否包含在 选中值列表中
                var targetdata = value.toString().split(opt.split);
                for (var i = 0, len = targetdata.length; i < len; i++) {
                    if (targetdata[i] == checkvalue) return true;
                }
                return false;
            };
            
            if (value != null && value != undefined) {
                if (that.isSelect) {    //select下拉框
                    var $selectTd = $('td[value=' + value + ']', $selectTable);
                    if ($selectTd && $selectTd.length > 0)
                        texts = $selectTd.html();  //td的html内容即为文本值
                }
                else {
                    $.each(that.data, function (i, item) {
                        var val = item[opt.valueField],
                            txt = item[opt.textField];
                        if (contain(val)) {
                            texts += txt + opt.split;
                        }
                    });
                    if (texts.length > 0) texts = texts.substr(0, texts.length - 1);  //除去 最后的分隔符
                }
            }
            return texts;
        },
        _setUrl: function () {  //通过url加载数据 生成下拉框
            var opt = this.options, that = this;

            $.rwpUI.getJson(opt.url, null,
                function (data) {
                    that.data = data;
                    that._setData();
                });            

            return this;
        },
        _setSelectBoxWidth: function () {   //设置下拉框宽度
            var opt = this.options,
                $selectBox = this.selectBoxDom.comboSelectBox,
                $comboWrap = this.comboboxDom.comboWrap;

            if ($.isNumeric(opt.selectBoxWidth))    //宽度为数字
                $selectBox.width(opt.selectBoxWidth);            
            else
                $selectBox.css('width', $comboWrap.css('width'));   //默认下拉框与控件宽度一致

            return this;
        },
        _setSelectBoxHeight: function () {  //设置下拉框高度
            var opt = this.options,
                $selectBox = this.selectBoxDom.comboSelectBox;                

            if ($.isNumeric(opt.selectBoxHeight))    //高度为数字
                $selectBox.height(opt.selectBoxHeight);            

            return this;
        },
        setDisabled: function (value) {
            this.options.disabled = value ? true : false;
            this.comboboxDom.comboWrap[value ? 'addClass' : 'removeClass']('rwpComboboxDisabled');

            return this;
        },
        setWidth: function (value) {
            if (value === +value && value > 30) {
                var opt = this.options,
                    $comboWrap = this.comboboxDom.comboWrap,
                    $inputText = this.comboboxDom.inputText,
                    $selectBox = this.selectBoxDom.comboSelectBox;
                opt.width = value;
                $comboWrap.css({ width: value + $inputText.outerWidth() - $inputText.width() });  //加上文本框的padding
                $inputText.css({ width: value - 30 });  //减去右侧下拉按钮的宽度
                opt.selectBoxWidth == 'auto' && $selectBox.css({ width: $comboWrap.width() });  //自适应时 同步selectbox的宽度
            }

            return this;
        },
        setHeight: function (value) {
            if (value === +value && value > 10) {
                var opt = this.options,
                    $comboWrap = this.comboboxDom.comboWrap,
                    $inputText = this.comboboxDom.inputText,
                    $comboButton = this.comboboxDom.comboButton;
                opt.height = value;
                $comboWrap.height(value);
                $inputText.height(value);
                $inputText.css("line-height", value + "px");  //使input内文本垂直居中
                $comboButton.height(value);
            }

            return this;
        },        
        selectValue: function (value) {  //设置选中值            
            this._setValue(value);

            return this;
        },        
        refreshValue: function () {   //根据inputValue值 刷新下拉框显示
            var $inputValue = this.comboboxDom.inputValue,
                value = $inputValue.val();

            if (this.isSelect) {
                value = this.$element.val();  //优先取select元素的值
            }
            return this.selectValue(value);
        },        
        refreshContent: function (params) {   //刷新数据
            $.extend(this.options, params);
            this._bulidContent()
                ._setSelectBoxWidth()
                ._setSelectBoxHeight();

            return this;
        }
    };

    $.rwpUI.Combobox = function (element, options) {
        (new rwpCombobox(element, options));
    };

    $.rwpUI.Combobox.defaults = {    //默认参数
        valueField: 'id',     //值字段名称
        textField: 'text',    //文本字段名称    
        disabled: false,
        height: 'auto',
        width: 'auto',
        data: null,           //静态数据源
        url: null,            //url数据源
        isMultiSelect: false,   //是否多选 多选时默认提供复选框        
        valueFieldDomID: null,    //值字段对应的Dom元素ID
        onBeforeOpen: null,      //打开下拉框前事件，可以通过return false来阻止继续操作
        selectBoxWidth: 'auto',   //下拉框宽度
        selectBoxHeight: 200,  //下拉框高度
        render: null,            //文本框显示html函数
        split: ',',               //多选时文本间隔符号
        initValue: null,          //初始值 不适用于select标签        
        zindex: null,            //z-index值
        id: null                 //下拉框ID        
    };

    $.rwpUI.Combobox._selectBoxTemplate = '<div class="rwpComboboxSelect">'
                                            + '<div class="rwpComboboxSelectInner">'
                                               + '<table cellpadding="0" cellspacing="0" border="0" class="rwpComboboxSelectTable"></table>'
                                            + '</div>'
                                         + '</div>';

    $.rwpUI.Combobox.initzIndex = 1988;   //初始zIndex

    $.rwpUI.Combobox.fn = rwpCombobox;  //指向rwpCombobox

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Combobox(element, options);
        });
    };

})(jQuery, window, document);