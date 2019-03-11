/**
* @license                                     
* 选项卡控件
*
* Since: 2013/2/5
*/
; (function ($, window, document, undefined) {   

    var pluginName = 'rwpUITab';   //控件名称

    var rwpTab = function (element, options) {
        this.element = element;
        this.$element = $(element);

        var opt = $.extend(true, {}, $.rwpUI.Tab.defaults, options);  //合并已赋值参数
        this.options = opt;

        this._init();  //初始化
        $.data(this.element, pluginName, this);   //保存到元素上
    };

    rwpTab.prototype = {
        _init: function () {
            var opt = this.options, $element = this.$element;
            if (!$element.hasClass('rwpTab')) $element.addClass('rwpTab');

            this._domHtml();            

            this._initMenu()
                ._addClickEvent();

            this._initHeight();   //初始化高度
            this.selectTabItem(this.selectedTabId);  //选中指定标签
        },
        _domHtml: function () {   //dom操作
            var $element = this.$element, dom = {},
                $tabMenuList, $tabContentList,
                template = $.rwpUI.Tab._template;

            if ($('> ul', $element).length < 1) {
                $element.html(template);
                $tabMenuList = $('> ul:first', $element);
                $tabContentList = $('> div:first', $element);                
            }
            else {
                $tabMenuList = $('> ul:first', $element);
                $tabMenuList.addClass('rwpTabNavList');
                if ($('> div', $element).length < 1) {
                    $tabContentList = $('<div class="rwpTabContent"><div class="rwpBigLoading"></div></div>');
                    $tabMenuList.after($tabContentList);                    
                }
                else {
                    $tabContentList = $('> div:first', $element);
                    $tabContentList.addClass('rwpTabContent');
                    $('> div.rwpLoading', $tabContentList).remove();   //移除直接元素下的loading                    
                    $tabContentList.prepend('<div class="rwpBigLoading"></div>');  //将loading插入作为第一个子元素
                }
                $element.children().not('.rwpTabNavList, .rwpTabContent').remove();  //移除其它子元素
            }
            $tabContentLoading = $('> .rwpBigLoading:first', $tabContentList);
            dom.rwpTabMenu = $tabMenuList;
            dom.rwpTabContent = $tabContentList;
            dom.rwpTabContentLoading = $tabContentLoading;
            this.dom = dom;

            return this;
        },
        _initMenu: function () {   //初始化导航栏
            var opt = this.options, dom = this.dom, that = this, tabList = {}, $tabMenuList = dom.rwpTabMenu,
                $tabMenuContent = dom.rwpTabContent, $contentLoading = this.dom.rwpTabContentLoading,
                tabIndex, tabId, tabSrc, tabTitle, contentHeightDeduct, $menuItem, $menuContent;

            $('> li', $tabMenuList).each(function (i, menu) {
                tabIndex = i;                                
                $menuItem = $(menu);
                tabId = $menuItem.attr(opt.idField);  //获取标签ID
                tabSrc = $menuItem.attr(opt.urlField);  //获取标签url
                tabTitle = $menuItem.text();
                if (!tabId) {
                    tabId = $.rwpUI.guid('rwpTab_');  //生成tabId
                    $menuItem.attr(opt.idField, tabId);
                }
                $menuItem.addClass('rwpTabNavItem');  //添加样式                               
                if ($('> div', $tabMenuContent).eq(tabIndex + 1).length < 1) {
                    $menuContent = $('<div class="rwpTabContentItem"><div>').appendTo($tabMenuContent);
                }
                else {
                    $menuContent = $('> div', $tabMenuContent).eq(tabIndex + 1);
                    $menuContent.addClass('rwpTabContentItem');
                }
                if (tabIndex == 0 || $menuItem.attr('selected'))  //默认选中第一个标签
                    that.selectedTabId = tabId;   //记录选中标签ID
                contentHeightDeduct = $menuContent.outerHeight(true) - $menuContent.height();  //内容区域高度间距
                tabList[tabId] = {
                    $menu: $menuItem, $content: $menuContent, 
                    tabId: tabId, url: tabSrc, title: tabTitle,
                    tabIndex: tabIndex, contentHeightDeduct: contentHeightDeduct
                };                               
            });            
            this.dom.tabList = tabList;
            this.menuOuterHeight = $tabMenuList.outerHeight(true);  //获取导航条的外部高度
            this.contentLoadingHeightDeduct = $contentLoading.outerHeight(true) - $contentLoading.height();  //loading区域高度间距

            return this;
        },
        _getMenuContent: function (tabId) {   //获取tab标签内容
            var tabList = this.dom.tabList, opt = this.options,
                tabItem, $menuContent, menuUrl, $contentLoading = this.dom.rwpTabContentLoading;

            if (tabId) {
                tabItem = tabList[tabId];
            }
            if (tabItem) {                                    
                tabItem.url = tabItem.$menu.attr(opt.urlField) || tabItem.url;  //重新获取url
                if (tabItem.url) {
                    $menuContent = tabItem.$content;                    

                    $.rwpUI.getHtml(tabItem.url, function () {
                            $menuContent.hide();
                            $contentLoading.show();
                        },
                        function (data) {                            
                            $menuContent.html(data);
                        },
                        function (XMLHttpRequest, textStatus, errorThrown) {                            
                            if (textStatus == "parsererror") {
                                alert(errorThrown);
                            }
                        },
                        function(jqXHR, textStatus) {                            
                            $contentLoading.hide();
                            $menuContent.show();
                            $.isFunction(opt.onAfterSelectTabItem) && opt.onAfterSelectTabItem(tabItem);
                        });                    
                }
                else {   //不需要加载url,触发选中标签事件
                    $.isFunction(opt.onAfterSelectTabItem) && opt.onAfterSelectTabItem(tabItem);
                }
            }

            return this;
        },
        _addClickEvent: function () {
            var $tabMenuList = this.dom.rwpTabMenu, that = this, opt = this.options;

            $('> li.rwpTabNavItem', $tabMenuList).click(function (e) {                
                that.selectTabItem($(this).attr(opt.idField));                                 
            });

            return this;
        },
        selectTabItem: function (tabId) {  //选择标签
            var opt = this.options, that = this, 
                tabList = this.dom.tabList, tabItem; 

            if (tabId) {
                tabItem = tabList[tabId];
            }
            if (tabItem) {                
                if ($.isFunction(opt.onBeforeSelectTabItem) && opt.onBeforeSelectTabItem(tabItem) == false) {
                    return false;
                }
                that.selectedTabId = tabItem.tabId;  //记录选中的选项卡ID                    

                tabItem.$menu.addClass('rwpTabCurrent').siblings().removeClass('rwpTabCurrent');  //选项卡设置选中
                tabItem.$content.show().siblings().hide();  //显示选项卡内容
                that._getMenuContent(tabId);   //获取选项卡内容
            }

            return this;
        },
        _initHeight: function () {
            var opt = this.options, that = this, $tab = this.$element;
            if ($.isNumeric(opt.height)) {
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
            var windowHeight = $(window).height(),   //获取窗体高度
                parentHeight, $tab = this.$element, 
                $tabparent = this.$tabparent;
            if (!$tabparent) {
                $tabparent = this.$tabparent = $tab.parent();
            }
            if ($tabparent[0].tagName.toLowerCase() == "body") {
                parentHeight = windowHeight;
                parentHeight -= $tab.offset().top;
                parentHeight -= parseInt($tabparent.css('paddingBottom'));  //减去父元素的padding下间隔
                parentHeight -= parseInt($tabparent.css('marginBottom'));  //减去父元素的margin下间隔
            }
            else {
                parentHeight = $tabparent.height();  //获取父元素高度
            }            
            return parentHeight;   //返回父元素高度
        },               
        _resize: function () {
            var opt = this.options, $tab = this.$element;
            if ($.isNumeric(opt.height)) return false;   //高度参数为数字，直接返回
            this.tabHeightDeduct = $tab.outerHeight(true) - $tab.height();
            var tabParentHeight = this._getParentHeight();    //获取父容器高度            
            if (opt.height && typeof (opt.height) == 'string' && opt.height.indexOf('%') > 0) {                
                this.setHeight(tabParentHeight * parseFloat(opt.height) * 0.01);  //乘以百分比
            }
            else if (opt.height == 'auto') {                
                this.setHeight(tabParentHeight - this.tabHeightDeduct);
            }

            return this;
        },
        //设置高度
        setHeight: function (height) {
            if ($.isNumeric(height)) {
                var $tab = this.$element, tabList = this.dom.tabList,
                    $contentLoading = this.dom.rwpTabContentLoading;
                this.tabHeight = height;               
                $tab.height(this.tabHeight);
                
                this.contentOuterHeight = this.tabHeight - this.menuOuterHeight;
                //设置所有content内容的高度, 减去高度边距
                for (var tabId in tabList) {  
                    tabList[tabId].$content.height(this.contentOuterHeight - tabList[tabId].contentHeightDeduct);
                }
                $contentLoading.height(this.contentOuterHeight - this.contentLoadingHeightDeduct);  //设置loading的高度                
            }
            return this;
        },
        //添加标签
        addTabItem: function(tabItemOpt) {
            var opt = this.options, that = this, dom = this.dom, tabList = dom.tabList,
                $tabMenuList = dom.rwpTabMenu, $tabContentList = dom.rwpTabContent,
                itemAttrs = $.rwpUI.Tab.itemAttrs, tabItemOpt = tabItemOpt || {},
                $menuItem, $menuContent;
            
            for (var key in itemAttrs) {  //填充标签项属性
                tabItemOpt[key] = tabItemOpt[itemAttrs[key]];
            }
            if (that.isTabItemExist(tabItemOpt))  //判断已经存在 则直接选中对应标签
            {
                that.selectTabItem(tabItemOpt.tabId);  //使用传出的tabid
                return this;
            }
            if ($.isFunction(opt.onBeforeAddTabItem) && opt.onBeforeAddTabItem(tabItemOpt) == false) {
                return false;
            }
            if (!tabItemOpt.tabId) {
                tabItemOpt.tabId = $.rwpUI.guid('rwpTab_');  //生成tabId
            }
            tabItemOpt.title = tabItemOpt.title || tabItemOpt.tabId;   //文本为空时显示ID
            tabItemOpt.canClose = !!tabItemOpt.canClose;  //转换成bool类型
            tabItemOpt.tabIndex = $('li', $tabMenuList).length;  //获取当前标签的索引
            $menuItem = $('<li class="rwpTabNavItem"></li>').appendTo($tabMenuList);
            $menuContent = $('<div class="rwpTabContentItem"></div>').appendTo($tabContentList);
            $menuItem.attr(opt.idField, tabItemOpt.tabId);
            $menuItem.text(tabItemOpt.title);   //设置标题
            if (tabItemOpt.url) {   //url加载
                $menuItem.attr(opt.urlField, tabItemOpt.url);  //设置url属性                                
            }
            else if (tabItemOpt.content)
            {
                $menuContent.html(content);
            }            
            tabItemOpt.$menu = $menuItem;   //填充属性，保持一致性
            tabItemOpt.$content = $menuContent;
            tabItemOpt.contentHeightDeduct = $menuContent.outerHeight(true) - $menuContent.height();  //内容区域高度间距
            if ($.isNumeric(this.contentOuterHeight)) {  //设置内容区域 高度
                $menuContent.height(this.contentOuterHeight - tabItemOpt.contentHeightDeduct);
            }
            tabList[tabItemOpt.tabId] = tabItemOpt;  //赋值到选项卡列表

            that.selectTabItem(tabItemOpt.tabId);
            $menuItem.click(function (e) {   //绑定事件             
                that.selectTabItem($(this).attr(opt.idField));                                 
            });

            $.isFunction(opt.onAfterAddTabItem) && opt.onAfterAddTabItem(tabItemOpt);  //调用添加标签后事件

            return this;
        },
        isTabItemExist: function(tabItemOpt) {  //判断是否存在相同id或url的标签
            var opt = this.options, tabList = this.dom.tabList, tabItem;
            
            tabItemOpt = tabItemOpt || {};
            if (tabItemOpt.tabId) {   //先通过标签ID查找
                tabItem = tabList[tabItemOpt.tabId];
            }
            if (!tabItem && tabItemOpt.url) {
                for(var tabId in tabList) {   //查找相同url的标签
                    if (tabList[tabId].url == tabItemOpt.url) {
                        tabItem = tabList[tabId];
                        tabItemOpt.tabId = tabId;  //将标签ID传出
                        break;
                    }
                }
            }            
            return !!tabItem;
        },
        //移除标签
        removeTabItem: function(tabId) {
            var opt = this.options, tabList = this.dom.tabList, tabItem,
                $tabMenuList = this.dom.rwpTabMenu, newSelectTabIndex, newSelectTabId;

            if (tabId) {
                tabItem = tabList[tabId];
            }
            if (tabItem) {
                if ($.isFunction(opt.onBeforeRemoveTabItem) && opt.onBeforeRemoveTabItem(tabItem) == false) {
                    return false;
                }
                if (this.selectedTabId == tabId) {   //当前选中标签
                    newSelectTabIndex = tabItem.tabIndex - 1;
                    newSelectTabIndex = newSelectTabIndex < 0 ? 0 : newSelectTabIndex;
                    newSelectTabId = $('li', $tabMenuList).eq(newSelectTabIndex).attr(opt.idField);
                    this.selectTabItem(newSelectTabId);   //选中新的标签
                }
                this._updateTabIndex(tabItem.tabIndex + 1, 1);  //更新标签的索引
                tabItem.$menu.remove();
                tabItem.$content.remove();
                delete tabList[tabId];                
                $.isFunction(opt.onAfterRemoveTabItem) && opt.onAfterRemoveTabItem(tabItem)
            }

            return this;
        },
        _updateTabIndex: function(startIndex, indexDown) {   //更新标签索引
            var tabList = this.dom.tabList, tabItem;

            if ($.isNumeric(startIndex) && $.isNumeric(indexDown)) {
                startIndex = parseInt(startIndex);                
                indexDown = parseInt(indexDown);

                if (startIndex - indexDown > -1) {   //保证索引号不小于0
                    for(var tabId in tabList) {
                        tabItem = tabList[tabId];
                        if (tabItem.tabIndex >= startIndex) {
                            tabItem.tabIndex -= indexDown;   //递减索引号
                        }
                    }
                }
            }

            return this;
        }
    };

    $.rwpUI.Tab = function (element, options) {
        (new rwpTab(element, options));
    };
    
    $.rwpUI.Tab.itemAttrs = {   //标签项属性
        tabId: 'id',
        title: 'text',
        url: 'url',
        content: 'content',        
        canClose: 'canClose'
    }      

    $.rwpUI.Tab.defaults = {    //默认参数
        itemAttrs: $.rwpUI.Tab.itemAttrs, 
        height: 'auto',
        idField: 'tabid',   //id字段
        urlField: 'tabsrc',            //url字段        
        onBeforeSelectTabItem: null,   //选中标签前事件
        onAfterSelectTabItem: null,    //选中标签后事件
        onBeforeAddTabItem: null,   //添加标签前事件
        onAfterAddTabItem: null,   //添加标签后事件
        onBeforeRemoveTabItem: null,   //移除标签前事件
        onAfterRemoveTabItem: null   //移除标签后事件       
    };

    $.rwpUI.Tab._template = '<ul class="rwpTabNavList"></ul><div class="rwpTabContent"><div class="rwpBigLoading"></div></div>';    

    $.rwpUI.Tab.fn = rwpTab;  //指向rwpTab

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Tab(element, options);
        });
    };

})(jQuery, window, document);