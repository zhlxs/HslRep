/**
* @license                                     
* Tree树控件
*
* Since: 2013/1/31
*/
; (function ($, window, document, undefined) {

    var pluginName = 'rwpUITree';   //控件名称

    var rwpTree = function (element, options) {
        this.element = element;
        this.$element = $(element);

        var opt = $.extend(true, {}, $.rwpUI.Tree.defaults, options);  //合并已赋值参数(进行递归合并)
        this.options = opt;

        this._init();  //初始化
        $.data(this.element, pluginName, this);   //保存到元素上        
    };

    rwpTree.prototype = {
        _init: function () {
            var opt = this.options;
            if (!this.$element.hasClass('rwpTree')) this.$element.addClass('rwpTree');

            this._setloading();   //设置加载中效果

            this.clear();  //清空
            this._applyTree();   //根据原始html子元素生成树形结构html

            if (opt.data) this._append(null, opt.data);  //添加树型数据
            if (opt.url) this.loadData(null, opt.url, opt.param);

            this._setTreeEvent();  //设置响应事件
        },
        _setloading: function () {
            var that = this;
            this.$loading = $('<div class="rwpTreeLoading"></div>');
            this.$element.after(this.$loading);   //加载中div，默认隐藏
            this.loadingFunctions = {  //默认加载效果
                showLoading: function () {
                    that.$loading.show();
                },
                hideLoading: function () {
                    that.$loading.hide();
                }
            };

            return this;
        },
        //加载url数据
        loadData: function (node, url, param, callbacks) {
            var $loading = this.$loading,
                ajaxtype = param ? "post" : "get",
                that = this;
            callbacks = $.extend({}, that.loadingFunctions, callbacks || {});
            param = param || [];
            $.rwpUI.Ajax({
                type: ajaxtype,
                url: url,
                data: param,
                dataType: 'json',
                beforeSend: function () {
                    callbacks.showLoading();
                },
                success: function (data) {
                    if (!data) return;
                    that._append(node, data);
                    $.isFunction(callbacks.successFun) && callbacks.successFun();
                },
                complete: function () {
                    callbacks.hideLoading();
                    $.isFunction(callbacks.completeFun) && callbacks.completeFun();
                }
            });
            return this;
        },
        clear: function () {    //清空
            var opt = this.options, that = this, $tree = this.$element;

            that.nodeExpandCallbacks = [];
            that.data = [];
            that.maxOutlineLevel = 1;
            that.treedataindex = 0;  //初始数据索引

            $tree.html('');

            return this;
        },
        _append: function (parentNode, newData) {
            var that = this, afterShow = this.options.onAfterShowData,
                treehtmlarr, $parenttreeitem, outlineLevel = 1, $toAppendUl = this.$element;

            parentNode = that.getNodeDom(parentNode);  //获取父结点   
            if (newData && newData.length) {
                this._addTreeDataIndexToData(newData);  //设置数据的索引
                this._appendData(parentNode, newData);
                if (parentNode) {
                    $parenttreeitem = $(parentNode);
                    outlineLevel = parseInt($parenttreeitem.attr("outlinelevel")) + 1; //获取子结点层级
                    $childrenUl = $("> ul.rwpTreeChildren", $parenttreeitem);
                    if ($childrenUl.length < 1) {
                        this._upgrade(parentNode);  //升级为父结点
                        $parenttreeitem.append("<ul class='rwpTreeChildren'></ul>");
                        $toAppendUl = $("> ul.rwpTreeChildren", $parenttreeitem).eq(0);
                    }
                }
                treehtmlarr = that._getTreeHTMLByData(newData, outlineLevel, true);
                treehtmlarr[treehtmlarr.length - 1] = treehtmlarr[0] = "";
                this._updateLastItem($("> li:last", $toAppendUl)[0], false);   //原父结点 最后直接子结点 更改为中间元素
                $toAppendUl.append(treehtmlarr.join(''));

                $('a', $toAppendUl).click(function (e) {
                    e.preventDefault();
                });
            }

            afterShow && typeof (afterShow) == 'function' && afterShow.apply(window, [parentNode, newData]); //数据加载完响应事件
            return this;
        },
        //更新最后一个元素
        _updateLastItem: function (treeNode, isLast) {
            var $treeItem = $(treeNode),
                outlineLevel, prevCount = 0, afterCount = 0, treeClass = '';
            if ($treeItem.length > 0) {
                outlineLevel = parseInt($treeItem.attr("outlinelevel"));
                prevCount = $treeItem.prevAll('li').length;
                afterCount = $treeItem.nextAll('li').length;
                if (isLast == false) {
                    if (outlineLevel == 1) treeClass = 'rwpTreeRoot';
                    else if (afterCount < 1) treeClass = 'rwpTreeLast';
                    else if (prevCount < 1) treeClass = 'rwpTreeFirst';  //重新获取其树型类名
                    $treeItem.removeClass('rwpTreeRoot rwpTreeFirst rwpTreeLast').addClass(treeClass);
                    $('>span.rwpTreeExpandOpen, >span.rwpTreeExpandClose', $treeItem)
                        .removeClass('rwpTreeRootExpand rwpTreeFirstExpand rwpTreeLastExpand')
                        .addClass(treeClass ? (treeClass + 'Expand') : ''); //父结点展开按钮
                    $('>span.rwpTreeSwitch', $treeItem).removeClass('rwpTreeRootNote rwpTreeFirstNote rwpTreeLastNote')
                        .addClass(treeClass ? (treeClass + 'Note') : '');  //子结点虚线
                }
                else if (isLast == true) {
                    $treeItem.removeClass('rwpTreeRoot rwpTreeFirst').addClass('rwpTreeLast');
                    $('>span.rwpTreeExpandOpen, >span.rwpTreeExpandClose', $treeItem)
                        .removeClass('rwpTreeRootExpand rwpTreeFirstExpand').addClass('rwpTreeLastExpand'); //父结点展开按钮
                    $('>span.rwpTreeSwitch', $treeItem).removeClass('rwpTreeRootNote rwpTreeFirstNote').addClass('rwpTreeLastNote'); //子结点虚线
                }
            }
            return this;
        },
        _upgrade: function (treeNode, isCollapse) {   //升级为父节点
            var $treeItem = $(treeNode), outlineLevel, afterCount = 0,
                treeClass = '', childNodeClass = this._getChildNodeClassName(), parentNodeClass = this._getParentNodeClassName(true);
            if ($treeItem.length > 0) {
                outlineLevel = parseInt($treeItem.attr("outlinelevel"));
                afterCount = $treeItem.nextAll('li').length;
                if (outlineLevel == 1) treeClass = 'rwpTreeRoot';
                else if (afterCount < 1) treeClass = 'rwpTreeLast';  //重新获取其树型类名    
                //虚线 转化为 展开图标                    
                $(">span.rwpTreeSwitch", $treeItem).removeClass("rwpTreeSwitch rwpTreeRootNote rwpTreeNote rwpTreeLastNote")
                    .addClass("rwpTreeBox " + (isCollapse ? 'rwpTreeExpandClose ' : 'rwpTreeExpandOpen ') + (treeClass ? treeClass + 'Expand' : ''));  //添加 展开/收缩 图标         
                //叶子 转化为 文件夹展开图标
                $(">span." + childNodeClass, $treeItem).removeClass(childNodeClass).addClass(parentNodeClass);
            }

            return this;
        },
        //设置data 索引
        _addTreeDataIndexToData: function (data) {
            var that = this;
            $.each(data, function (i, item) {
                if (item.treedataindex !== undefined) return;
                item.treedataindex = that.treedataindex++;
                if (item.children && $.isArray(item.children)) {
                    that._addTreeDataIndexToData(item.children);
                }
            });
            return this;
        },
        //添加项到this.data
        _appendData: function (treeNode, data) {
            var toAppendData = this.data, that = this,
                dataAttrs = this.options.dataAttrs,
                treedataindex, treeNodeData;
            that._fillDataAttrs(data);  //填充属性
            if (treeNode) {
                treedataindex = parseInt($(treeNode).attr("treedataindex"));
                treeNodeData = that._getDataNodeByTreeDataIndex(null, treedataindex); //获取结点数据
            }
            if (treeNodeData) {
                if (!(treeNodeData.children && $.isArray(treeNodeData.children))) {
                    treeNodeData.children = [];
                }
                toAppendData = treeNodeData.children;
            }
            $.each(data, function (i, item) {
                toAppendData.push(item);
            });
            return this;
        },
        //填充数据属性
        _fillDataAttrs: function (data) {
            var that = this,
                dataAttrs = this.options.dataAttrs;
            $.each(data, function (i, item) {
                for (var key in dataAttrs) {
                    item[key] = item[dataAttrs[key]];
                }
                if (item.children && $.isArray(item.children))
                    that._fillDataAttrs(item.children);
            });
            return this;
        },
        getNodeDom: function (nodeParam) {
            var $tree = this.$element, that = this;
            if (nodeParam != null && nodeParam != undefined) {
                if (typeof (nodeParam) == "string" || typeof (nodeParam) == "number") {
                    return $("li[treedataindex=" + nodeParam + "]", $tree).get(0);
                }
                else if (typeof (nodeParam) == "object" && 'treedataindex' in nodeParam) {  //nodedata
                    return that.getNodeDom(nodeParam['treedataindex']);
                }
            }
            return nodeParam;
        },
        //根据数据索引获取数据
        _getDataNodeByTreeDataIndex: function (searchData, treedataindex) {
            var that = this, data = searchData || this.data;
            for (var i = 0, len = data.length; i < len; i++) {
                if (data[i].treedataindex === treedataindex)
                    return data[i];
                if (data[i].children && $.isArray(data[i].children)) {
                    var targetData = that._getDataNodeByTreeDataIndex(data[i].children, treedataindex);
                    if (targetData) return targetData;
                }
            }
            return null;
        },
        hasChildren: function (treenodedata) {
            var opt = this.options;
            if (opt.isLeaf && typeof (opt.isLeaf) == 'function')
                return opt.isLeaf(treenodedata);
            return treenodedata.children ? true : false;
        },
        _isExpand: function (treedata) {   //判断节点是否展开状态
            var opt = this.options, isExpand = treedata.isexpand;
            if (isExpand === undefined || isExpand === null) return true;
            if (typeof (isExpand) == "boolean") return isExpand;
            if (typeof (isExpand) == "string") return !(isExpand == 'false');
            return true;
        },
        //获取叶子节点图标类名
        _getChildNodeClassName: function () {
            var childIcon = this.options.childIcon;
            return 'rwpTreeIcon' + childIcon.substring(0, 1).toUpperCase() + childIcon.substring(1);
        },
        //获取父节点图标类名
        _getParentNodeClassName: function (isOpen) {
            var opt = this.options,
                upperParentIcon = opt.parentIcon.substring(0, 1).toUpperCase() + opt.parentIcon.substring(1),
                nodeclassname = 'rwpTreeIcon' + upperParentIcon;
            if (isOpen) nodeclassname += 'Open';
            return nodeclassname;
        },
        //递归设置父节点的状态
        _setParentCheckboxStatus: function (treeitem) {
            var that = this, $parentitem = treeitem.parent(),
            //当前同级别或低级别的节点是否都选中了
                isCheckedComplete = $(".rwpTreeCheckboxUnchecked", $parentitem).length == 0,
            //当前同级别或低级别的节点是否都没有选中
                isCheckedNull = $(".rwpTreeCheckboxChecked", $parentitem).length == 0;
            if (isCheckedComplete) {
                $parentitem.prevAll("span.rwpTreeCheckbox")
                                    .removeClass("rwpTreeCheckboxUnchecked rwpTreeCheckboxIncomplete")
                                    .addClass("rwpTreeCheckboxChecked");   //父结点设置选中
            }
            else if (isCheckedNull) {
                $parentitem.prevAll("span.rwpTreeCheckbox")
                                    .removeClass("rwpTreeCheckboxChecked rwpTreeCheckboxIncomplete")
                                    .addClass("rwpTreeCheckboxUnchecked");  //父结点设置未选中
            }
            else {
                $parentitem.prevAll("span.rwpTreeCheckbox")
                                    .removeClass("rwpTreeCheckboxUnchecked rwpTreeCheckboxChecked")
                                    .addClass("rwpTreeCheckboxIncomplete");  //父结点设置部分选中
            }
            if ($parentitem.parent("li").length > 0)
                that._setParentCheckboxStatus($parentitem.parent("li"));  //递归父结点
            return this;
        },
        //根据简洁的html获取data
        _getDataByTreeHTML: function (parentDom) {
            var data = [], that = this, opt = this.options,
                treeDom = parentDom || this.$element,
                dataAttrs = opt.dataAttrs;
            $("> li", treeDom).each(function (i, item) {    //循环li元素
                var dataindex = data.length, $item = $(item);
                data[dataindex] = { treedataindex: that.treedataindex++ };
                for (var key in dataAttrs) {   //填充数据的属性
                    var attrName = dataAttrs[key], attrValue;
                    if (attrName === 'text')
                        attrValue = $("> span,> a", $item).html();
                    else
                        attrValue = $item.attr(attrValue);
                    data[dataindex][key] = attrValue;
                }
                if ($("> ul", $item).length > 0) {   //获取子结点
                    data[dataindex].children = that._getDataByTreeHTML($("> ul", $item));
                }
            });
            return data;
        },
        //根据data生成最终完整的tree html
        _getTreeHTMLByData: function (data, outlineLevel, isExpand, isLast) {
            outlineLevel = outlineLevel || 1;
            if (this.maxOutlineLevel < outlineLevel) {
                this.maxOutlineLevel = outlineLevel;   //赋值当前输出层级
            }
            var treehtmlarr = [], opt = this.options, that = this;
            if (!isExpand) treehtmlarr.push('<ul class="rwpTreeChildren' + (isLast ? '"' : ' rwpTreeLine"') + ' style="display:none">');
            else treehtmlarr.push('<ul class="rwpTreeChildren' + (isLast ? '"' : ' rwpTreeLine"') + '>');   //每层最后一个结点 不显示坚线
            for (var i = 0, len = data.length; i < len; i++) {
                var treedata = data[i],
                    isFirst = i == 0,
                    isLastCurrent = i == len - 1,
                    delay = false,
                    isExpandCurrent = true,
                    treeClass = "rwpTree";
                if (that._getDelay && typeof (that._getDelay) == 'function') {
                    delay = that._getDelay(treedata, outlineLevel);
                }
                isExpandCurrent = delay ? false : that._isExpand(treedata, outlineLevel);
                treehtmlarr.push('<li');
                treehtmlarr.push(' treedataindex="' + treedata.treedataindex + '"');  //写入数据索引值
                if (isExpandCurrent)
                    treehtmlarr.push(' isexpand="true"');  //写入是否展开
                treehtmlarr.push(' outlinelevel="' + outlineLevel + '"');    //写入输出层级
                if (outlineLevel == 1) {
                    treeClass = treeClass + "Root";  //第一层级元素
                    treehtmlarr.push(' class=' + treeClass + '');
                }
                else if (isLastCurrent) {
                    treeClass = treeClass + "Last";
                    treehtmlarr.push(' class=' + treeClass + '');
                }
                else if (isFirst) {
                    treehtmlarr.push(' class=' + treeClass + 'First');  //其它层级第一个元素
                }
                treehtmlarr.push('>');
                if (that.hasChildren(treedata)) {
                    if (isExpandCurrent) treehtmlarr.push('<span class="rwpTreeBox rwpTreeIcon rwpTreeExpandOpen '
                        + (treeClass != 'rwpTree' ? treeClass + 'Expand' : '') + '"></span>');   //显示展开图标
                    else treehtmlarr.push('<span class="rwpTreeBox rwpTreeIcon rwpTreeExpandClose '
                        + (treeClass != 'rwpTree' ? treeClass + 'Expand' : '') + '"></span>');   //显示关闭图标                            
                }
                else {   //子结点虚线
                    treehtmlarr.push('<span class="rwpTreeSwitch rwpTreeIcon ' + treeClass + 'Note"></span>');
                }
                if (opt.checkbox) {
                    if (treedata.ischecked)
                        treehtmlarr.push('<span class="rwpTreeBox rwpTreeIcon rwpTreeCheckbox rwpTreeCheckboxChecked"></span>');  //选中复选框
                    else
                        treehtmlarr.push('<span class="rwpTreeBox rwpTreeIcon rwpTreeCheckbox rwpTreeCheckboxUnchecked"></span>');  //未选中复选框
                }
                treehtmlarr.push('<a href="#" title="' + treedata.text + '"');   //添加a标签
                if (treedata.url)
                    treehtmlarr.push(' href = "' + treedata.url + '"');  //写入a标签的href属性
                treehtmlarr.push('>');
                if (that.hasChildren(treedata)) {
                    treehtmlarr.push('<span class="rwpTreeIcon ' + that._getParentNodeClassName(isExpandCurrent) + '"></span>');  //显示文件夹图标                    
                }
                else {
                    treehtmlarr.push('<span class="rwpTreeIcon ' + that._getChildNodeClassName() + '"></span>');  //显示叶子结点图标
                }
                treehtmlarr.push(treedata.text);   //写入结点文本字符串
                treehtmlarr.push('</a>');
                if (that.hasChildren(treedata)) {   //获取子结点的html字符串
                    if (delay) {
                        if (delay === true) {
                            delay = {};
                            delay.contentCallbackFun = (function (level, isLastCur) {
                                return function (treedata) {
                                    return that._getTreeHTMLByData(treedata.children, level, true, isLastCur).join('');
                                };
                            })(outlineLevel + 1, isLastCurrent);  //设置子结点html字符串回调                           
                        }
                        that._addDelayCallback(treedata, delay);
                    }
                    else {
                        treehtmlarr.push(that._getTreeHTMLByData(treedata.children, outlineLevel + 1, isExpandCurrent, isLastCurrent).join(''));
                    }
                }
            }
            treehtmlarr.push("</ul>");
            return treehtmlarr;
        },
        //生成树
        _applyTree: function () {
            this.data = this._getDataByTreeHTML();
            var treehtmlarr = this._getTreeHTMLByData(this.data, 1, true);
            treehtmlarr[treehtmlarr.length - 1] = treehtmlarr[0] = "";   //清除首尾包裹的div
            this.$element.html('');  //先清空内容
            this.$element.append(treehtmlarr.join(''));
            return this;
        },
        //设置动作
        _setTreeEvent: function () {
            var that = this, $tree = this.$element, opt = this.options,
                parentExpandNodeClassName = that._getParentNodeClassName(true),
                parentCloseNodeClassName = that._getParentNodeClassName();
            $tree.click(function (e) {
                var obj = (e.target || e.srcElement), $obj = $(obj), $treeitem = null;
                if ((obj.tagName.toLowerCase() == "span" && $obj.hasClass("rwpTreeBox")) || obj.tagName.toLowerCase() == "a") {
                    $treeitem = $obj.parent();   //a标签 或者 展开/收缩按钮
                }
                else if (obj.tagName.toLowerCase() == "span") {
                    $treeitem = $obj.parent().parent();   //a标签中的图标
                }
                else if (obj.tagName.toLowerCase() == "li") {
                    $treeitem = $obj;
                }
                if (!$treeitem) return;
                var treedataindex = parseInt($treeitem.attr("treedataindex")),
                    treenodedata = that._getDataNodeByTreeDataIndex(null, treedataindex),
                    clickOnTreeItemBtn = $obj.hasClass("rwpTreeExpandOpen") || $obj.hasClass("rwpTreeExpandClose");
                if (!$obj.hasClass("rwpTreeCheckbox") && !clickOnTreeItemBtn) {
                    //非 点击复选框 和 点击展开/收缩按钮
                    var $link = $("> a", $treeitem);   //获取a标签
                    if ($link.hasClass("rwpTreeSelected")) {
                        $link.removeClass("rwpTreeSelected");  //移除选中样式
                        opt.onCancleSelect && typeof (opt.onCancleSelect) == 'function' && opt.onCancleSelect({ data: treenodedata });  //调用单击取消选中事件
                    }
                    else {
                        $("a", $tree).removeClass("rwpTreeSelected");
                        $link.addClass("rwpTreeSelected");  //添加选中样式
                        opt.onSelect && typeof (opt.onSelect) == 'function' && opt.onSelect({ data: treenodedata });  //调用单击选中事件
                    }
                }
                //点击checkbox
                if ($obj.hasClass("rwpTreeCheckbox")) {
                    //状态：未选中 或 未完全选中
                    if ($obj.hasClass("rwpTreeCheckboxUnchecked") || $obj.hasClass("rwpTreeCheckboxIncomplete")) {
                        $obj.removeClass("rwpTreeCheckboxIncomplete rwpTreeCheckboxUnchecked").addClass("rwpTreeCheckboxChecked");
                        opt.checkCascade && $("ul.rwpTreeChildren .rwpTreeCheckbox", $treeitem)
                                .removeClass("rwpTreeCheckboxIncomplete rwpTreeCheckboxUnchecked")
                                .addClass("rwpTreeCheckboxChecked");   //级联勾选时 子结点全部选中                                
                    }
                        //状态：选中
                    else if ($obj.hasClass("rwpTreeCheckboxChecked")) {
                        $obj.removeClass("rwpTreeCheckboxChecked").addClass("rwpTreeCheckboxUnchecked");
                        opt.checkCascade && $("ul.rwpTreeChildren .rwpTreeCheckbox", $treeitem)
                                .removeClass("rwpTreeCheckboxIncomplete rwpTreeCheckboxChecked")
                                .addClass("rwpTreeCheckboxUnchecked");   //级联勾选时 子结点全部未选中                                
                    }
                    opt.checkCascade && that._setParentCheckboxStatus($treeitem);  //级联勾选时 设置父结点的勾选状态
                    opt.onCheck && typeof (opt.onCheck) == 'function' && opt.onCheck();  //调用checkbox勾选事件
                }
                    //状态：已经张开
                else if ($obj.hasClass("rwpTreeExpandOpen")) {
                    $obj.removeClass("rwpTreeExpandOpen").addClass("rwpTreeExpandClose");   //切换收缩样式
                    $("> ul.rwpTreeChildren", $treeitem).hide();
                    $("> a > span." + parentExpandNodeClassName, $treeitem)
                            .removeClass(parentExpandNodeClassName)
                            .addClass(parentCloseNodeClassName);    //切换文件夹图标为收缩样式                        
                }
                    //状态：没有张开
                else if ($obj.hasClass("rwpTreeExpandClose")) {
                    //调用结点展开回调函数
                    that.nodeExpandCallbacks[treedataindex] && that.nodeExpandCallbacks[treedataindex].apply(window, [$treeitem[0]]);
                    $obj.removeClass("rwpTreeExpandClose").addClass("rwpTreeExpandOpen");  //切换展开样式
                    $("> ul.rwpTreeChildren", $treeitem).show();
                    $("> a > span." + parentCloseNodeClassName, $treeitem)
                            .removeClass(parentCloseNodeClassName)
                            .addClass(parentExpandNodeClassName);   //切换文件夹图标为展开样式
                }
            });
            return this;
        },
        //清除选中
        clearSelect: function () {
            $("a", this.$element).removeClass("rwpTreeSelected");
            return this;
        },
        //清除勾选
        clearCheck: function () {
            if (!this.options.checkbox) return;
            $("span.rwpTreeCheckbox", this.$element).removeClass("rwpTreeCheckboxIncomplete rwpTreeCheckboxChecked")
                .addClass("rwpTreeCheckboxUnchecked");
            return this;
        },
        //获取选中节点
        getSelected: function () {
            var node = {}, $target;
            node.target = ($target = $("a.rwpTreeSelected", this.$element).parent("li"))[0];
            if (node.target) {
                var treedataindex = parseInt($target.attr("treedataindex"));
                node.data = this._getDataNodeByTreeDataIndex(null, treedataindex);
                return node;
            }
            return null;
        },
        //获取checked节点
        getChecked: function () {
            var that = this, opt = this.options, $tree = this.$element;
            if (!opt.checkbox) return null;
            var nodes = [];
            $("span.rwpTreeCheckboxChecked", $tree).parent("li").each(function (i, item) {
                var treedataindex = parseInt($(item).attr("treedataindex"));
                nodes.push({ target: item, data: that._getDataNodeByTreeDataIndex(null, treedataindex) });
            });
            return nodes;
        },
        //关闭所有
        collapseAll: function () {
            $("span.rwpTreeExpandOpen", this.$element).click();
            return this;
        },
        //展开所有
        expandAll: function () {
            $("span.rwpTreeExpandClose", this.$element).click();
            return this;
        },
        getTextByID: function (id) {
            var data = this.getDataByID(id);
            if (!data) return null;
            return data.text;
        },
        getDataByID: function (id) {
            var that = this, data = null;
            $("li", this.$element).each(function (i, item) {
                var $treeitem = $(item),
                    treedataindex = parseInt($treeitem.attr("treedataindex")),
                    treenodedata = that._getDataNodeByTreeDataIndex(null, treedataindex);
                if (treenodedata.id.toString() == id.toString()) {
                    data = treenodedata;
                    return false;  //退出循环
                }
            });
            return data;
        },
        //选择节点(参数：条件函数、Dom节点或ID值)
        selectNode: function (selectNodeParm) {
            var clause = null, that = this, opt = this.options;
            if (typeof (selectNodeParm) == "function") {
                clause = selectNodeParm;
            }
            else if (typeof (selectNodeParm) == "object") {
                var $treeitem = $(selectNodeParm);
                if (opt.checkbox) {
                    var $checkboxitem = $('> span.rwpTreeCheckbox', $treeitem);
                    if (!$checkboxitem.hasClass('rwpTreeCheckboxChecked'))
                        $checkboxitem.trigger('click');    //没有选中时才触发点击事件
                }
                else {
                    var $linkitem = $('> a', $treeitem);
                    if (!$linkitem.hasClass('rwpTreeSelected'))
                        $linkitem.trigger('click');   //没有选中时才触发点击事件
                }
                return this;  //支持链式操作
            }
            else {   //传递ID值
                clause = function (data) {
                    if (!data.id) return false;
                    return data.id.toString() == selectNodeParm.toString();
                };
            }
            $("li", this.$element).each(function (i, item) {
                var $treeitem = $(item),
                    treedataindex = parseInt($treeitem.attr("treedataindex")),
                    treenodedata = that._getDataNodeByTreeDataIndex(null, treedataindex);
                if (clause(treenodedata, treedataindex)) {   //循环结点， 根据函数返回值判断是否选中
                    that.selectNode(item);
                }
            });
            return this;
        },
        setData: function (data) {   //设置静态数据
            if (data) {
                this.clear();
                this._append(null, data);
            }
        },
        setUrl: function (url, params) {   //设置url
            if (url) {
                this.clear();
                this.loadData(null, url, param);
            }
        }
    };

    $.rwpUI.Tree = function (element, options) {
        (new rwpTree(element, options));
    };

    $.rwpUI.Tree.dataAttrs = {   //读取数据属性
        id: 'id',
        text: 'html',
        url: 'url',
        icon: 'icon',
        isexpand: 'isexpand',
        ischecked: 'ischecked'
    };

    $.rwpUI.Tree.defaults = {  //默认参数
        dataAttrs: $.rwpUI.Tree.dataAttrs,
        checkbox: false,        //是否显示复选框 
        parentIcon: 'folder',    //文件夹名称
        childIcon: 'leaf',       //叶结点名称
        data: null,              //初始化数据
        url: null,                //加载json数据url路径
        param: null,             //url参数
        onSelect: null,           //单选时选中响应事件
        onCancleSelect: null,   //单选时取消选中响应事件
        onCheck: null,         //多选时响应事件
        checkCascade: true,     //是否级联勾选
        onAfterShowData: null   //数据展示完后响应事件
    };

    $.rwpUI.Tree.fn = rwpTree;  //指向rwpTree    

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Tree(element, options);
        });
    };

})(jQuery, window, document);