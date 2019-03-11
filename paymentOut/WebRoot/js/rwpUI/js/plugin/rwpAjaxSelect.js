/**
* @license                                     
* 多级联动下拉框 for 牛发发
* flyofsky@163.com
*
* Since: 2012/08/09
*/
; (function ($, window, document, undefined) {

    var rwpAjaxSelect = function (opts) {        
        this.options = opts;

        return this._init();  //初始化
    };

    rwpAjaxSelect.prototype = {
        _init: function () {
            this._getDom()
                ._selectInitBind();

            return this;
        },
        _getDom: function () {   //获取dom元素
            var that = this, opt = this.options,
                columns = opt.columns, len = columns.length,
                column, $select, selectColumns = [];

            for (var i = 0; i < len; i++) {
                column = columns[i];
                if (column) {
                    selectColumns[i] = column;
                    $select = $("#" + column.domID);
                    if ($select.length > 0)
                        selectColumns[i].$dom = $select;  //保存select元素                    
                }
            }

            this.selectColumns = selectColumns;   //保存select集合

            return this;
        },
        _selectInitBind: function () {   //select下拉框加载数据并绑定change事件         
            var opt = this.options, that = this,
                selectColumns = that.selectColumns,
                selectColumn, beforeVal;

            for (var i = 0, len = selectColumns.length; i < len; i++) {
                selectColumn = selectColumns[i];
                if (selectColumn.$dom) {
                    if (i === 0) {
                        beforeVal = opt.defaultval;
                    }
                    else {  //前一个select的选中值
                        beforeVal = selectColumns[i - 1].selectVal;
                    }
                    that._ajaxLoadData(i, beforeVal);  //填充对应下拉框                    
                    selectColumn.$dom.change((function (index) {
                        return function () {
                            var val = $(this).val();
                            that._selectBind(index + 1, val);  //重新绑定下一个select
                        };
                    })(i));
                }
            }

            return this;
        },
        //动态获取数据
        _ajaxLoadData: function (index, val) {
            index = parseInt(index);
            var that = this,
                selectColumn = that.selectColumns[index];

            if (selectColumn && selectColumn.$dom) {  //存在dom元素
                var url = selectColumn.url.replace(/(^\s*)|(\s*$)/g, "");
                if (selectColumn.queryParamName && typeof (selectColumn.queryParamName) == "string"
                    && val && (($.type(val) == "number") || ($.type(val) == "string"))) {
                    if (url.indexOf('?') < 0) {
                        url += '?';
                    }
                    else {
                        url += '&';
                    }
                    url += selectColumn.queryParamName + '=' + val;  //添加查询条件
                }
                $.rwpUI.getJson(url, null, 
                    function (data) {
                        that._selectFill(index, $.extend({}, data));
                    }, null, null, true);  //发送同步ajax请求                
            }

            return this;
        },
        //填充数据
        _selectFill: function (index, data) {
            index = parseInt(index);
            var selectColumn = this.selectColumns[index], $select,
                opt = this.options, dataRow, selectOption;

            if (selectColumn && selectColumn.$dom) {
                $select = selectColumn.$dom;
                $select.empty();  //清空元素的内容
                if ($.isArray(selectColumn.defaultOption)) {
                    for (var i = 0, len = selectColumn.defaultOption.length; i < len; i++) {   //循环添加默认的option
                        dataRow = selectColumn.defaultOption[i];
                        $select.append($('<option value="' + dataRow[opt.valueFieldName] + '">' + dataRow[opt.textFieldName] + '</option>'));
                    }
                }
                else {
                    $select.append($('<option value="' + selectColumn.defaultOption[opt.valueFieldName] + '">' + selectColumn.defaultOption[opt.textFieldName] + '</option>'));
                }
                for (var key in data) {
                    dataRow = data[key];
                    selectOption = $('<option value="' + dataRow[opt.valueFieldName] + '">' + dataRow[opt.textFieldName] + '</option>');

                    if (selectColumn.selectVal == dataRow[opt.valueFieldName]) {
                        selectOption[0].selected = true;    //设置选中option
                    }
                    $select.append(selectOption);
                }
                if (selectColumn.isCombobox === true) {
                    var manager = $select.data('rwpUICombobox');
                    if (manager) {
                        manager.setWidth(selectColumn.width);
                        manager.refreshContent({ selectBoxHeight: opt.selectBoxHeight });  //重新生成下拉框
                    }
                    else
                        $.rwpUI.Combobox && $select.rwpUICombobox({
                             width: selectColumn.width, 
                             valueFieldDomID: selectColumn.domID,
                             disabled: selectColumn.disabled, 
                             selectBoxHeight: opt.selectBoxHeight });  //应用下拉框控件
                }
            }

            return this;
        },
        //重新绑定数据
        _selectBind: function (index, val) {
            index = parseInt(index);
            var len = this.selectColumns.length, selectColumn;

            this._ajaxLoadData(index, val);

            for (var i = index + 1; i < len; i++) {  //之后的下拉框全部清空                  
                selectColumn = this.selectColumns[i];
                if (selectColumn && selectColumn.$dom) {
                    this._selectFill(i, {});
                }
            }

            return this;
        }
    };

    $.rwpUI.AjaxSelect = function (options) {
        var opt = $.extend({}, $.rwpUI.AjaxSelect.defaults, options || {});
        if (opt.columns && opt.columns.length > 0)
            return new rwpAjaxSelect(opt);
    };

    $.rwpUI.AjaxSelect.defaults = {   //默认参数
        defaultval: 0, 　//起始默认值
        columns: [],    　//级联下拉框数组
        valueFieldName: 'Value',   //值字段名称
        textFieldName: 'Text',       //文本字段名称
        selectBoxHeight: 'auto'   //下拉框高度
    };

})(jQuery, window, document);