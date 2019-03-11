/**
* @license                                     
* Combobox下拉框控件 扩展插件
*
* Since: 2013/2/8
*/
; (function ($) {    

    if ($.rwpUI.Combobox) {
        var rwpCombobox = $.rwpUI.Combobox.fn;            

        if ($.rwpUI.Tree) {
            //扩展默认参数
            $.extend($.rwpUI.Combobox.defaults, {
                tree: null,            //下拉框以树的形式显示
                treeLeafOnly: true,     //是否只选择叶子结点
                onAfterTreeSelect: null   //树结点选中事件
            }); 
             
            rwpCombobox.prototype._bulidContent = (function () {  //重写 生成下拉框函数
                var _old_buildContent = rwpCombobox.prototype._bulidContent;  //保留原 生成下拉框 函数
                return function () {
                    var opt = this.options;

                    if (opt.tree)
                        this._setTree();
                    else {
                        _old_buildContent.apply(this);  //调用原 生成下拉框 函数
                    }

                    return this;
                };
            })();

            rwpCombobox.prototype._setTree = function () {   //树型展示下拉框
                var opt = this.options, optTree = opt.tree, that = this,
                    selectBoxDom = this.selectBoxDom,
                    $selectBox = selectBoxDom.comboSelectBox,
                    $selectTree = $("<ul></ul>");   //创建tree顶级元素

                this._clearContent();
                selectBoxDom.comboSelectTable.hide(); //隐藏表格                

                if (optTree.checkbox) {   //多选时响应事件
                    optTree.onCheck = function () {
                        var treeManager = that.selectTreeManager,
                            nodes = treeManager.getChecked(), value = [], text = [];
                        $.each(nodes, function (i, node) {
                            if (opt.treeLeafOnly && treeManager.hasChildren(node.data)) return;
                            value.push(node.data[opt.valueField]);
                            text.push(node.data[opt.textField]);
                        });
                        that._changeValue(value.join(opt.split), text.join(opt.split));
                    };
                }
                else {    //单选时响应事件
                    optTree.onSelect = function (node) {
                        var treeManager = that.selectTreeManager,
                            value = node.data[opt.valueField], text = node.data[opt.textField];

                        if (opt.treeLeafOnly && treeManager.hasChildren(node.data)) {
                            that._changeValue('', '');  //清空值
                            return;
                        }
                        that._changeValue(value, text);
                        $selectBox.hide();
                        $.rwpUI.funcApply(opt.onAfterTreeSelect)(node.data);
                    };
                    optTree.onCancelSelect = function (node) {
                        that._changeValue('', '');   //清空值
                    };
                }
                if (optTree.url) {
                    optTree.onAfterShowData = function () { that._initValue(); };  //加载完后初始化选中值
                }
                $("div:first", $selectBox).append($selectTree);
                $selectTree.rwpUITree(optTree);  //应用rwpUITree控件
                that.selectTreeManager = $selectTree.data('rwpUITree');  //获取rwpUITree控件管理对象
                !optTree.url && that._initValue();  //初始化选中值

                $selectBox.hide();

                selectBoxDom.comboSelectTree = $selectTree;   //保存树型Dom元素的引用

                return this;
            };
            
            rwpCombobox.prototype._setValue = (function () {   //重写 设置选中值函数
                var _old_setValue = rwpCombobox.prototype._setValue;  //保留原 设置选中值 函数
                return function (value) {   //重写 设置选中值函数
                    var opt = this.options;

                    if (opt.tree)
                        this._setValueByTree(value);
                    else
                        _old_setValue.apply(this, [value]);  //调用原 设置选中值 函数

                    return this;
                };
            })();

            rwpCombobox.prototype._setValueByTree = function (value) {   //设置 树型下拉框 选中值
                var opt = this.options,
                    optTree = opt.tree,
                    isCheckbox = optTree.checkbox,
                    treeManager = this.selectTreeManager;

                if (value != null && value != undefined && treeManager) {
                    var text = '', valuelist = value.toString().split(opt.split), len = valuelist.length;

                    if (isCheckbox) treeManager.clearCheck();  //清除所有勾选
                    else treeManager.clearSelect();

                    $.each(valuelist, function (i, item) {
                        treeManager.selectNode(item.toString());
                        if (isCheckbox)  //多选
                            text += treeManager.getTextByID(item);
                        else
                            text = treeManager.getTextByID(item);  //单选
                        if (isCheckbox && i < len - 1) text += opt.split;  //多选时添加分隔符
                    });
                    this._changeValue(value, text);

                    if (isCheckbox)
                        optTree.onCheck();   //多选时 重新循环所有选中值 因为选中父结点导致选中所有子结点
                }

                return this;
            };
        }

        if ($.rwpUI.Datagrid) {
            //扩展默认参数
            $.extend($.rwpUI.Combobox.defaults, {
                grid: null          //下拉框以表格的形式显示
            });
            
            rwpCombobox.prototype._bulidContent = (function () {  //重写 生成下拉框函数
                var _old_buildContent = rwpCombobox.prototype._bulidContent;  //保留原 生成下拉框 函数
                return function () {
                    var opt = this.options;

                    if (opt.grid)
                        this._setGrid();
                    else {
                        _old_buildContent.apply(this);  //调用原 生成下拉框 函数
                    }

                    return this;
                };
            })();

            rwpCombobox.prototype._setGrid = function () {   //多列表格展示下拉框
                var opt = this.options, optGrid = opt.grid, that = this,
                    selectBoxDom = this.selectBoxDom,
                    $selectBox = selectBoxDom.comboSelectBox,
                    $selectGrid = $('<div></div>');   //创建表格顶级元素

                this._clearContent();
                selectBoxDom.comboSelectTable.hide(); //隐藏表格                
                
                if (optGrid.checkbox) {
                    optGrid.onCheckRow = function () {
                        var rowsdata = that.selectGridManager.getCheckedRows(), value = [], text = [];
                        $.each(rowsdata, function (i, rowdata) {
                            value.push(rowdata[opt.valueField]);
                            text.push(rowdata[opt.textField]);
                        });
                        that._changeValue(value.join(opt.split), text.join(opt.split));
                    };
                }
                else {
                    optGrid.onSelectRow = function (rowdata) {
                        var value = rowdata[opt.valueField], text = rowdata[opt.textField];
                        that._changeValue(value, text);
                    };
                    optGrid.onCancelSelectRow = function (rowdata) {
                        that._changeValue('', '');   //清空值
                    }
                }

                $("div:first", $selectBox).append($selectGrid);
                $selectGrid.rwpUIGrid(optGrid);
                that.selectGridManager = $selectGrid.data('rwpUIGrid');  //获取rwpUIGrid控件管理对象

                $selectBox.hide();

                selectBoxDom.comboSelectGrid = $selectGrid;   //保存多列表格Dom元素的引用

                return this;
            };
            
            rwpCombobox.prototype._setValue = (function () {
                var _old_setValue = rwpCombobox.prototype._setValue;  //保留原 设置选中值 函数
                return function (value) {   //重写 设置选中值函数
                    var opt = this.options;

                    if (!opt.grid)   //多列表格 不支持设置值                   
                        _old_setValue.apply(this, [value]);  //调用原 设置选中值 函数

                    return this;
                };
            })();
        }
    }
})(jQuery);