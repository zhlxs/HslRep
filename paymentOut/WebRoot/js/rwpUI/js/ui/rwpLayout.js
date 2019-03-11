/**
* @license                                     
* layout布局控件
*
* Since: 2013/1/29
*/
; (function ($, window, document, undefined) {
    if (!$.rwpUI) {
        $.rwpUI = {};
    };

    var pluginName = 'rwpUILayout';   //控件名称

    var rwpLayout = function (element, options) {
        this.element = element;
        this.$element = $(element);

        var opt = $.extend({}, $.rwpUI.Layout.defaults, options);  //合并已赋值参数
        this.options = opt;

        this._init();  //初始化
        $.data(this.element, pluginName, this);   //保存到元素上        
    };

    rwpLayout.prototype = {
        _init: function () {
            var opt = this.options, that = this;
            if (!this.$element.hasClass('rwpLayout')) this.$element.addClass('rwpLayout');
            this._getRegions();  //获取所有Div区域                        
            this._resize();    //刷新大小

            $(window).resize(function () {
                that._resize()._resize();
            });
        },
        _getRegions: function () {
            var $layout = this.$element;
            var regions = { 'top': 'Top', 'left': 'Left', 'center': 'Center', 'right': 'Right', 'bottom': 'Bottom' };
            var regionDom = {};
            $("> div", $layout).each(function (i, item) {
                var $item = $(item);
                var childopts = $(this).attr("data-options");   //获取div元素data-options属性
                if (childopts) {
                    childopts = childopts.replace(/\'/g, "\"");
                    childopts = $.parseJSON(childopts);  //解析json
                }
                childopts = childopts || {};

                var regionOpts = $.rwpUI.Layout.regionOptions;
                for (var j = 0; j < regionOpts.length; j++) {
                    if (!childopts[regionOpts[j]])
                        childopts[regionOpts[j]] = $item.attr(regionOpts[j]);  //通过attr获取区域option
                }

                if (childopts.region && regions[childopts.region]) {  //该Div元素属于某区域
                    childopts.upperRegion = regions[childopts.region]
                    $item.addClass('rwpLayout' + childopts.upperRegion);  //添加类名                    
                    regionDom['Layout' + childopts.upperRegion] = { '$div': $item, 'regionData': childopts };  //添加到区域Dom列表中
                }
            });
            this.regionDom = regionDom;
            return this;
        },
        _initStyle: function () {
            var $layout = this.$element;

            this.layoutHeightDeduct = $layout.outerHeight(true) - $layout.height();
            this.layoutWidthDeduct = $layout.outerWidth(true) - $layout.width();

            return this;
        },
        _getRegionWidthHeight: function () {
            var regionDom = this.regionDom, opt = this.options;
            this.topOuterHeight = this.bottomOuterHeight = this.leftOuterWidth = this.rightOuterWidth
                = this.centerHeightDeduct = this.leftHeightDeduct = this.rightHeightDeduct
                = this.centerWidthDeduct = this.topWidthDeduct = this.bottomWidthDeduct = 0;   //全部初始为0
            this.topMarginLeft = this.leftMarginLeft = this.centerMarginLeft = this.rightMarginLeft = this.bottomMarginLeft = 0;
            this.topMarginTop = this.leftMarginTop = this.centerMarginTop = this.rightMarginTop = this.bottomMarginTop = 0;
            for (var k in regionDom) {
                var regionDomObj = regionDom[k],
                    $regionDiv = regionDomObj.$div,  //Div元素
                    regionData = regionDomObj.regionData,  //区域数据
                    regionName = regionData.region;  //区域名称
                if (regionName == 'top' || regionName == 'bottom') {
                    regionData.height && $regionDiv.height(regionData.height);  //设置高度
                    this[regionName + 'OuterHeight'] = $regionDiv.outerHeight(true);   //获取最后实际高度(包括上下边距margin)
                    this[regionName + 'WidthDeduct'] = $regionDiv.outerWidth(true) - $regionDiv.width();   //元素padding、margin及border的宽度
                    this[regionName + 'MarginLeft'] = parseInt($regionDiv.css('marginLeft'));
                    this[regionName + 'MarginTop'] = parseInt($regionDiv.css('marginTop'));
                    //$regionDiv.css((regionName == 'top' ? { 'left': this[regionName + 'MarginLeft'], 'top': this[regionName + 'MarginTop'] } : { 'left': this[regionName + 'MarginLeft'] }));
                }
                else if (regionName == 'left' || regionName == 'right') {
                    $regionDiv.width(regionData.width || opt[regionName + 'Width']); //设定宽度
                    this[regionName + 'OuterWidth'] = $regionDiv.outerWidth(true);   //获取最后实际宽度(包括左右边距margin)
                    this[regionName + 'HeightDeduct'] = $regionDiv.outerHeight(true) - $regionDiv.height();   //元素padding、margin及border的高度
                    this[regionName + 'MarginLeft'] = parseInt($regionDiv.css('marginLeft'));
                    this[regionName + 'MarginTop'] = parseInt($regionDiv.css('marginTop'));
                    //if (regionName == 'left') $regionDiv.css({ 'left': this[regionName + 'MarginLeft'] });
                }
                else if (regionName == 'center') {
                    this.centerMarginLeft = parseInt($regionDiv.css('marginLeft'));  //center元素的margin左间隔
                    this.centerMarginTop = parseInt($regionDiv.css('marginTop'));  //center元素的margin上间隔
                    this.centerHeightDeduct = $regionDiv.outerHeight(true) - $regionDiv.height();   //元素padding、margin及border的高度
                    this.centerWidthDeduct = $regionDiv.outerWidth(true) - $regionDiv.width();   //元素padding、margin及border的宽度
                }
            }
            return this;
        },
        _getParentHeight: function () {
            var windowHeight = $(window).height();   //获取窗体高度
            var parentHeight = null, $layout = this.$element, $layoutparent = this.$layoutparent;
            if (!$layoutparent) {
                $layoutparent = this.$layoutparent = $layout.parent();
            }
            if ($layoutparent[0].tagName.toLowerCase() == "body") {
                parentHeight = windowHeight;
                parentHeight -= ($layout.offset().top - parseInt($layout.css('marginTop')));
                parentHeight -= parseInt($layoutparent.css('paddingBottom'));  //减去父元素的padding下间隔
                parentHeight -= parseInt($layoutparent.css('marginBottom'));  //减去父元素的margin下间隔
            }
            else {
                parentHeight = $layoutparent.height();  //获取父元素高度
            }
            return parentHeight;   //返回父元素高度
        },
        _getParentWidth: function () {
            var windowWidth = $(window).width();   //获取窗体宽度
            var parentWidth = null, $layout = this.$element, $layoutparent = this.$layoutparent;
            if (!$layoutparent) {
                $layoutparent = this.$layoutparent = $layout.parent();
            }
            if ($layoutparent[0].tagName.toLowerCase() == "body") {
                parentWidth = windowWidth;
                parentWidth -= ($layout.offset().left - parseInt($layout.css('marginLeft')));
                parentWidth -= parseInt($layoutparent.css('paddingRight'));  //减去父元素的padding右间隔
                parentWidth -= parseInt($layoutparent.css('marginRight'));  //减去父元素的margin右间隔
            }
            else {
                parentWidth = $layoutparent.width();  //获取父元素宽度
            }
            return parentWidth;   //返回父元素宽度
        },
        _getLayoutWidthHeight: function () {
            var h = 0, opt = this.options, $layout = this.$element, w = 0;
            var layoutParentHeight = this._getParentHeight();    //获取父容器高度
            var layoutParentWidth = this._getParentWidth();   //获取父容器宽度
            if (typeof (opt.height) == "string" && opt.height.indexOf('%') > 0) {
                h = layoutParentHeight * parseFloat(opt.height) * 0.01;  //乘以百分比
            }
            else {
                if (opt.height === +opt.height)  //高度为数字
                    h = opt.height;
                else
                    h = layoutParentHeight - this.layoutHeightDeduct;  //父容器高度扣减layout元素padding、margin及border的高度                        
            }
            h = h < opt.minHeight ? opt.minHeight : h;  //不能小于最小高度
            this.layoutHeight = h;
            $layout.height(h);  //设置layout高度

            w = layoutParentWidth - this.layoutWidthDeduct;  //父容器宽度扣减layout元素padding、margin及border的宽度
            if (w < opt.minWidth) {   //不能小于最小宽度
                w = opt.minWidth;
                $layout.width(w);  //设置layout宽度
            }
            this.layoutWidth = w;
            return this;
        },
        _resize: function () {
            this._initStyle()._getRegionWidthHeight()._getLayoutWidthHeight()._getCenterWidthHeight();
            var regionDom = this.regionDom;
            if (regionDom['LayoutCenter']) {
                //设置中间 宽高度
                regionDom['LayoutCenter'].$div.height(this.centerHeight)
                    .width(this.centerWidth).css({ 'left': this.leftOuterWidth, 'top': this.topOuterHeight });
            }
            if (regionDom['LayoutLeft']) {      //左边框高度 与 中间高度一致
                regionDom['LayoutLeft'].$div.height(this.centerOuterHeight - this.leftHeightDeduct).css({ 'top': this.topOuterHeight });
            }
            if (regionDom['LayoutRight']) {  //右边框高度 与 中间高度一致
                regionDom['LayoutRight'].$div.height(this.centerOuterHeight - this.rightHeightDeduct)
                    .css({ 'left': (this.leftOuterWidth + this.centerOuterWidth), 'top': this.topOuterHeight });  //设置右边框 left和top值                
            }
            if (regionDom['LayoutBottom']) {  //设置底部框 top值 与实际宽度
                regionDom['LayoutBottom'].$div.width(this.actualWidth - this.bottomWidthDeduct).css({ 'top': (this.topOuterHeight + this.centerOuterHeight) });
            }
            if (regionDom['LayoutTop']) {
                regionDom['LayoutTop'].$div.width(this.actualWidth - this.topWidthDeduct);   //设置实际宽度
            }
            return this;
        },
        _getCenterWidthHeight: function () {
            //获取中间宽高度  //小于0时出现滚动条                    
            this.centerHeight = Math.max(this.layoutHeight, (this.topOuterHeight + this.bottomOuterHeight + this.centerHeightDeduct))
                - (this.topOuterHeight + this.bottomOuterHeight + this.centerHeightDeduct);
            this.centerWidth = Math.max(this.layoutWidth, (this.leftOuterWidth + this.rightOuterWidth + this.centerWidthDeduct))
                - (this.leftOuterWidth + this.rightOuterWidth + this.centerWidthDeduct);
            this.centerOuterHeight = this.centerHeight + this.centerHeightDeduct;   //获取中间外部高度
            this.centerOuterWidth = this.centerWidth + this.centerWidthDeduct;   //获取中间外部宽度
            //获取实际宽高
            this.actualHeight = this.topOuterHeight + this.bottomOuterHeight + this.centerOuterHeight;
            this.actualWidth = this.leftOuterWidth + this.rightOuterWidth + this.centerOuterWidth;
            return this;
        }
    };

    $.rwpUI.Layout = function (element, options) {
        (new rwpLayout(element, options));
    };

    $.rwpUI.Layout.regionOptions = ['region', 'height', 'width'];

    $.rwpUI.Layout.defaults = {  //默认参数
        height: 'auto',
        minWidth: 0,
        minHeight: 0,
        leftWidth: 200,    //左边框宽度
        rightWidth: 0   //右边框宽度
    };

    $.rwpUI.Layout.fn = rwpLayout;  //指向rwpLayout    

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Layout(element, options);
        });
    };

})(jQuery, window, document);




/**
* @license                                     
* Accordion导航面板控件
*
* Since: 2013/1/31
*/
; (function ($, window, document, undefined) {
    if (!$.rwpUI) {
        $.rwpUI = {};
    };

    var pluginName = 'rwpUIAccordion';   //控件名称

    var rwpAccordion = function (element, options) {
        this.element = element;
        this.$element = $(element);

        var opt = $.extend({}, $.rwpUI.Accordion.defaults, options);  //合并已赋值参数
        this.options = opt;

        this._init();  //初始化
        $.data(this.element, pluginName, this);   //保存到元素上        
    };

    rwpAccordion.prototype = {
        _init: function () {
            var opt = this.options;
            if (!this.$element.hasClass('rwpAccordion')) this.$element.addClass('rwpAccordion');
            this._getPanels();  //获取所有Div面板
            this._addEvent();  //绑定标题事件

            this._initHeight();   //初始化高度
        },
        _getPanels: function () {
            var $accordion = this.$element, panelDom = [],
                panelHeightDeduct = headerOuterHeight = selectedIndex = 0;
            if ($("> div[state=select]", $accordion).length > 0)
                selectedIndex = $("> div", $accordion).index($("> div[state=select]:first", $accordion));
            $("> div", $accordion).each(function (i, item) {
                var $item = $(item);
                var childopts = $(this).attr("data-options");   //获取div元素data-options属性
                if (childopts) {
                    childopts = childopts.replace(/\'/g, "\"");
                    childopts = $.parseJSON(childopts);  //解析json
                }
                if (!childopts) {
                    childopts = {};
                    childopts.title = $item.attr('title');  //获取title
                }
                var panelObj = {};
                panelObj.header = $('<div class="rwpAccordionHeader"><span class="headBox"><i class="rwpAccordionHeaderIcon"></i><cite class="rwpAccordionHeaderTitle"></cite><cite class="rwpDropIcon"></cite></span></div>');
                panelObj.headerTitle = $(".rwpAccordionHeaderTitle", panelObj.header);
                if (i == selectedIndex) {
                    panelObj.header.addClass("rwpAccordionHeaderSel");  //显示展开图标
                    $item.show();
                }
                else {
                    $item.hide();
                }
                if (childopts.title) {
                    panelObj.headerTitle.html(childopts.title);   //设置标题div的内容
                    $item.removeAttr('title');
                }

                $item.wrap('<div class="rwpAccordionPanel"></div>');   //包装在panel中
                panelObj.panel = $item.parent();
                $item.before(panelObj.header);      //在之前插入头部div
                $item.addClass("rwpAccordionBody");    //添加面板body样式
                panelObj.body = $item;
                panelObj.bodyHeightDeduct = $item.outerHeight(true) - $item.height();
                panelDom.push(panelObj);  //添加到面板Dom列表中

                panelHeightDeduct += panelObj.panel.outerHeight(true) - panelObj.panel.height();
                headerOuterHeight += panelObj.header.outerHeight(true);  //累加标题高度
            });
            this.selectedIndex = 0;   //记录选中的索引项
            this.headerOuterHeight = headerOuterHeight;
            this.panelHeightDeduct = panelHeightDeduct;  //panel边距总和
            this.panelDom = panelDom;
            return this;
        },
        _addEvent: function () {
            var panelDom = this.panelDom;
            var hideOtherPanel = function (index) {   //隐藏其它panel
                for (var j = 0; j < panelDom.length; j++) {
                    if (j != index) {
                        var otherPanelObj = panelDom[j];
                        otherPanelObj.header.removeClass("rwpAccordionHeaderSel");
                        otherPanelObj.body.hide();
                    }
                };
            };

            for (var i = 0; i < panelDom.length; i++) {
                panelDom[i].header.click((function (index) {
                    return function () {
                        var panelObj = panelDom[index],   //保存当时的索引
                            header = panelObj.header;
                        if (header.hasClass("rwpAccordionHeaderSel")) {
                            header.removeClass("rwpAccordionHeaderSel");  //显示关闭图标 
                            panelObj.body.hide();   //隐藏内容                        
                        }
                        else {
                            header.addClass("rwpAccordionHeaderSel");  //显示展开图标                           
                            panelObj.body.show();   //显示内容                        
                            hideOtherPanel(index);  //其它panel 显示关闭图标
                        }
                    }
                })(i));
            }
            return this;
        },
        _initHeight: function () {
            var opt = this.options, that = this;
            if (opt.height && opt.height === +opt.height) {
                this.setHeight(parseInt(opt.height));   //设置高度值
            }
            else if ((opt.height && typeof (opt.height) == 'string' && opt.height.indexOf('%') > 0) || opt.height == 'auto') {
                this._resize();    //设置百分比或auto时均响应缩放
                $(window).resize(function () {
                    that._resize()._resize();
                });
            }
            return this;
        },
        _getParentHeight: function () {
            var windowHeight = $(window).height();   //获取窗体高度
            var parentHeight = null, $accordion = this.$element, $accordionparent = this.$accordionparent;
            if (!$accordionparent) {
                $accordionparent = this.$accordionparent = $accordion.parent();
            }
            if ($accordionparent[0].tagName.toLowerCase() == "body") {
                parentHeight = windowHeight;
                parentHeight -= ($accordion.offset().top - parseInt($accordion.css('marginTop')));
                parentHeight -= parseInt($accordionparent.css('paddingBottom'));  //减去父元素的padding下间隔
                parentHeight -= parseInt($accordionparent.css('marginBottom'));  //减去父元素的margin下间隔
            }
            else {
                parentHeight = $accordionparent.height();  //获取父元素高度
            }
            return parentHeight;   //返回父元素高度
        },
        _resize: function () {
            var opt = this.options, $accordion = this.$element;
            if (opt.height && opt.height === +opt.height) return this;   //高度参数为数字，直接返回
            this.accordionHeightDeduct = $accordion.outerHeight(true) - $accordion.height();
            var accordionParentHeight = this._getParentHeight();    //获取父容器高度
            if (opt.height && typeof (opt.height) == 'string' && opt.height.indexOf('%') > 0) {
                this.setHeight(accordionParentHeight * parseFloat(opt.height) * 0.01);  //乘以百分比
            }
            else if (opt.height == 'auto') {
                this.setHeight(accordionParentHeight - this.accordionHeightDeduct);
            }
            return this;
        },
        //设置高度
        setHeight: function (height) {
            if (height === +height && height > 0) {
                var $accordion = this.$element, panelDom = this.panelDom;
                this.accordionHeight = height;
                $accordion.height(this.accordionHeight);

                this.bodyOuterHeight = this.accordionHeight - this.headerOuterHeight - this.panelHeightDeduct;
                //设置所有body内容的高度
                for (var i = 0; i < panelDom.length; i++) {
                    panelDom[i].body.height(this.bodyOuterHeight - panelDom[i].bodyHeightDeduct);
                }
            }
            return this;
        }
    };

    $.rwpUI.Accordion = function (element, options) {
        (new rwpAccordion(element, options));
    };

    $.rwpUI.Accordion.defaults = {  //默认参数
        height: 'auto'
    };

    $.rwpUI.Accordion.fn = rwpAccordion;  //指向rwpAccordion    

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Accordion(element, options);
        });
    };

})(jQuery, window, document);





/**
* @license                                     
* 简单树控件
*
* Since: 2013/1/31
*/
; (function ($, window, document, undefined) {
    if (!$.rwpUI) {
        $.rwpUI = {};
    };

    var pluginName = 'rwpUISimpleTree';   //控件名称

    var rwpSimpleTree = function (element, options) {
        this.element = element;
        this.$element = $(element);

        var opt = $.extend(true, {}, $.rwpUI.SimpleTree.defaults, options);  //合并已赋值参数(进行递归合并)
        this.options = opt;

        this._init();  //初始化
        $.data(this.element, pluginName, this);   //保存到元素上        
    };

    rwpSimpleTree.prototype = {
        _init: function () {
            var opt = this.options;
            if (!this.$element.hasClass('rwpSimpleTree')) this.$element.addClass('rwpSimpleTree');
            this.$loading = $('<div class="rwpTreeLoading"></div>');
            this.$element.after(this.$loading);   //加载div，默认隐藏

            this.maxOutlineLevel = 1;
            this.treedataindex = 0;  //初始数据索引
            this._applyTree();   //根据原始html子元素生成树形结构html

            if (opt.data) this._append(opt.data);  //添加树型数据
            if (opt.url) this.loadData(opt.url);

            this._setTreeEvent();  //设置响应事件
        },
        //加载url数据
        loadData: function (url, param) {
            var $loading = this.$loading,
                ajaxtype = param ? "post" : "get",
                that = this;
            $loading.show();
            param = param || [];
            //请求服务器
            $.ajax({
                type: ajaxtype,
                url: url,
                data: param,
                dataType: 'json',
                success: function (data) {
                    $loading.hide();
                    if (!data) return;
                    that._append(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $loading.hide();
                }
            });
            return this;
        },
        _append: function (newData) {
            if (!newData || !newData.length) return this;
            this._addTreeDataIndexToData(newData);  //设置数据的索引
            this._appendData(newData);
            var treehtmlarr = this._getTreeHTMLByData(newData, 1, true);
            treehtmlarr[treehtmlarr.length - 1] = treehtmlarr[0] = "";
            this.$element.append(treehtmlarr.join(''));
            return this;
        },
        //设置data 索引
        _addTreeDataIndexToData: function (data) {
            var that = this;
            $.each(data, function (i, item) {
                if (item.treedataindex) return;
                item.treedataindex = that.treedataindex++;
                if (item.children) {
                    that._addTreeDataIndexToData(item.children);
                }
            });
            return this;
        },
        //添加项到this.data
        _appendData: function (data) {
            var treeData = this.data, that = this,
                dataAttrs = this.options.dataAttrs;
            that._fillDataAttrs(data);  //填充属性
            $.each(data, function (i, item) {
                treeData[treeData.length] = item;
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
                if (item.children)
                    that._fillDataAttrs(item.children);
            });
            return this;
        },
        //根据数据索引获取数据
        _getDataNodeByTreeDataIndex: function (searchData, treedataindex) {
            var that = this, data = searchData || this.data;
            for (var i = 0, len = data.length; i < len; i++) {
                if (data[i].treedataindex === treedataindex)
                    return data[i];
                if (data[i].children) {
                    var targetData = that._getDataNodeByTreeDataIndex(data[i].children, treedataindex);
                    if (targetData) return targetData;
                }
            }
            return null;
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
        _getTreeHTMLByData: function (data, outlineLevel, isExpand) {
            outlineLevel = outlineLevel || 1;
            if (this.maxOutlineLevel < outlineLevel) {
                this.maxOutlineLevel = outlineLevel;   //赋值当前输出层级
            }
            var treehtmlarr = [], opt = this.options, that = this;
            if (!isExpand) treehtmlarr.push('<ul class="rwpSimpleTreeChildren" style="display:none">');  //不展开时隐藏子结点
            else treehtmlarr.push('<ul class="rwpSimpleTreeChildren">');
            for (var i = 0, len = data.length; i < len; i++) {
                var isExpandCurrent = true;
                if (data[i].isexpand && (data[i].isexpand == false || data[i].isexpand == "false")) {
                    isExpandCurrent = false;    //标识是否展开
                }
                treehtmlarr.push('<li><a');
                if (data[i].children)
                    treehtmlarr.push(' class="rwpSimpleTreeItem"');  //设置父结点样式
                if (data[i].url)
                    treehtmlarr.push(' href = "' + data[i].url + '"');  //写入a标签的href属性
                treehtmlarr.push(' treedataindex="' + data[i].treedataindex + '"');  //写入数据索引值
                treehtmlarr.push(' outlinelevel="' + outlineLevel + '">');  //写入层级
                treehtmlarr.push('<span class="rwpSelIcon"></span><span class="rwpSpace"  ' + (outlineLevel > 1 ? 'style="width:' + outlineLevel * 15 + 'px"' : '') + '><i class="rwpIcon"  ' + (outlineLevel == 1 ? 'style="width:15px"' : '') + '></i></span>');
                if (data[i].children) {
                    treehtmlarr.push('<span ' + (isExpandCurrent ? ' class="rwpDropIcon open"' : 'rwpDrop') + '></span><span class="rwpDrop">' + data[i].text + '</span>');   //父结点文本添加span包裹
                }
                else
                    treehtmlarr.push(data[i].text);   //子结点文本字符串

                treehtmlarr.push('</a></li>');
                if (data[i].children) {   //获取子结点的html字符串                        
                    treehtmlarr.push(that._getTreeHTMLByData(data[i].children, outlineLevel + 1, isExpandCurrent).join(''));
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
            this.$element.html(treehtmlarr.join(''));
            return this;
        },
        //设置动作
        _setTreeEvent: function () {
            var that = this, $tree = this.$element, opt = this.options;
            $('a', $tree).click(function (e) {
                var $obj = $(this);
                if ($obj.hasClass('rwpSimpleTreeItem')) {   //代表点击父结点
                    var $rwpDropIcon = $('> .rwpDropIcon', $obj);   //获取span标签
                    if ($rwpDropIcon.hasClass('open')) {
                        $rwpDropIcon.removeClass('open');  //收缩子结点
                        $obj.parent().next('ul.rwpSimpleTreeChildren').hide();
                    }
                    else {
                        $rwpDropIcon.addClass('open');  //显示子结点
                        $obj.parent().next('ul.rwpSimpleTreeChildren').show();
                    }
                    e.preventDefault();  //父结点阻止默认操作
                }
                else {   //代表点击子结点
                    var treedataindex = parseInt($obj.attr('treedataindex')),
                        treenodedata = that._getDataNodeByTreeDataIndex(null, treedataindex);  //获取选择的数据项
                    if (!$obj.hasClass('selected')) {
                        $('a', $tree).removeClass('selected');  //先移除所有a标签选中样式
                        $obj.addClass('selected');  //添加该a标签选中样式
                        $obj.parents('.rwpSimpleTree').addClass('rwpTreeSel').siblings().removeClass('rwpTreeSel');
                    }
                    opt.onSelect && typeof (opt.onSelect) == 'function' && opt.onSelect({ data: treenodedata, $target: $obj });  //调用单击选中事件
                    treenodedata.isdefaultevent !== true && e.preventDefault();
                }
            });
            return this;
        },
        //清除选中
        clearSelect: function () {
            $("a", this.$element).removeClass("selected");
            return this;
        },
        //获取选中节点
        getSelected: function () {
            var node = {}, $target;
            node.target = ($target = $("a.selected", this.$element))[0];
            if (node.target) {
                var treedataindex = parseInt($target.attr("treedataindex"));
                node.data = this._getDataNodeByTreeDataIndex(null, treedataindex);
                return node;
            }
            return null;
        },
        //关闭所有
        collapseAll: function () {
            $("span.open", this.$element).parent('.rwpSimpleTreeItem').click();
            return this;
        },
        //展开所有
        expandAll: function () {
            $("a.rwpSimpleTreeItem", this.$element).each(function (i, item) {
                var $treeitem = $(item);
                if (!$('> span:first', $treeitem).hasClass('open'))  //父结点没有展开
                    $treeitem.click();
            });
            return this;
        },
        getTextByID: function (id) {
            var data = this.getDataByID(id);
            if (!data) return null;
            return data.text;
        },
        getDataByID: function (id) {
            var that = this, data = null;
            $("a", this.$element).each(function (i, item) {
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
            var clause = null, that = this;
            if (typeof (selectNodeParm) == "function") {
                clause = selectNodeParm;
            }
            else if (typeof (selectNodeParm) == "object") {
                var $treeitem = $(selectNodeParm);
                if (!$treeitem.hasClass('selected')) {
                    $treeitem.trigger('click');   //没有选中时才触发点击事件
                }
                return this;
            }
            else {   //传递ID值
                clause = function (data) {
                    if (!data.id) return false;
                    return data.id.toString() == selectNodeParm.toString();
                };
            }
            $("a", this.$element).each(function (i, item) {
                var $treeitem = $(item),
                    treedataindex = parseInt($treeitem.attr("treedataindex")),
                    treenodedata = that._getDataNodeByTreeDataIndex(null, treedataindex);
                if (clause(treenodedata, treedataindex)) {   //循环结点， 根据函数返回值判断是否选中
                    that.selectNode(item);
                }
            });
            return this;
        }
    };

    $.rwpUI.SimpleTree = function (element, options) {
        (new rwpSimpleTree(element, options));
    };

    $.rwpUI.SimpleTree.dataAttrs = {   //读取数据属性
        id: 'ID',
        text: 'html',
        url: 'url',
        icon: 'icon',
        isexpand: 'expand',
        isdefaultevent: 'defaultevent'
    };

    $.rwpUI.SimpleTree.defaults = {  //默认参数
        dataAttrs: $.rwpUI.SimpleTree.dataAttrs,
        parentIcon: 'folder',    //文件夹名称
        childIcon: 'leaf',       //叶结点名称
        data: null,              //初始化数据
        url: null,                //加载json数据url路径
        onSelect: null           //单选时选中响应事件        
    };

    $.rwpUI.SimpleTree.fn = rwpSimpleTree;  //指向rwpSimpleTree    

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.SimpleTree(element, options);
        });
    };

})(jQuery, window, document);