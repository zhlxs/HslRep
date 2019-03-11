/**
* @license                                     
* 菜单页 for RWP
*
* Since: 2014/05/04
*/
; (function ($, window, document, undefined) {

    var rwpPluginHelper = $.rwpUI.pluginHelper = $.rwpUI.pluginHelper || {},       
        rwpMenuHelper = rwpPluginHelper.menuHelper = {};

    rwpPluginHelper.clientTimeString = function () {
        var currentDate = new Date();
        return currentDate.toLocaleDateString() + ' ' + ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'][currentDate.getDay()] + ' ' + currentDate.toLocaleTimeString();
    };

    var rwpMenuLayout = function(options) {
        var opt = $.extend({}, rwpMenuLayout.defaults, options); 
        return new rwpMenuLayout.prototype._init(opt);
    };

    rwpMenuLayout.fn = rwpMenuLayout.prototype = {
        _init: function (opt) {            
            this.options = opt;           
            this.pageHistory = [];
            this._domHtml();
            return this;
        },
        _domHtml: function() {
            var dom = {}, opt = this.options,
                $pageHeader = null, $pageContent = null;
            if (opt.pageHeaderClass) {
                $pageHeader = $('.' + opt.pageHeaderClass);
                if ($pageHeader && $pageHeader.length > 0) {
                    dom.pageHeader = $pageHeader;
                }                
            }
            if (opt.pageContentClass) {
                $pageContent = $('.' + opt.pageContentClass);
                if ($pageContent && $pageContent.length > 0) {
                    dom.pageContent = $pageContent;
                }
            }
            this.dom = dom;
            return this;
        },
        loadMenu: function($menuLink, menuUrl, menuText) {
            var opt = this.options, that = this, menuUrl, menuText;            
            if ($menuLink && $menuLink instanceof jQuery && $menuLink.length == 1) {                
                if ($menuLink[0].tagName == 'A') {
                    menuUrl = menuUrl || $menuLink.attr('href');                    
                }
                menuText = menuText || $menuLink.text();                
                return that.loadPage(menuUrl, false, $menuLink, menuText);
            }
            return this;
        },
        loadPage: function(pageUrl, isReback, $menuLink, menuText) {
            var opt = this.options, that = this, ajaxOpts = {};
            if (pageUrl) {
                if ($.isFunction(opt.onBeforeLoading) && opt.onBeforeLoading(pageUrl) == false) {
                    return false;
                }                                             
                $.rwpUI.getHtml(pageUrl,
                    function() {
                        that._showLoadingTip();
                    },
                    function (data) {
                    	if(data.indexOf("<html class=\"userlogin\">")!=-1){
                        	$.rwpUI.error("会话超时，请重新登录！","",false,function(){
                            	window.location.reload();
                        	});
                        	return;
                        };
                        that._renderPage(data, isReback, $menuLink, menuText);
                        that._savePageHistory(pageUrl, $menuLink, menuText);               
                    },
                    null,
                    function() {
                        that._closeLoadingTip($menuLink);                    
                    });
            }
            return this;
        },        
        _showLoadingTip: function() {
            var opt = this.options;
            if (!this.loadingTip) {
                this.loadingTip = $.rwpUI.waitting(opt.waittingMessage);
            }
            return this;
        },
        _closeLoadingTip: function($menuLink) {
            var opt = this.options;
            if (this.loadingTip) {
                this.loadingTip.close();
                delete this.loadingTip;
            }
            $.rwpUI.funcApply(opt.onAfterLoading)();
            return this;
        },
        _renderPage: function(pageHtml, isReback, $menuLink, menuText) {
            var opt = this.options;
            $.rwpUI.funcApply(opt.onBeforePageRender)($menuLink, isReback);
            $menuLink && this._renderPageHeader(menuText);
            this._renderPageContent(pageHtml);
            $.rwpUI.funcApply(opt.onAfterPageRender)($menuLink);
            isReback && $.rwpUI.funcApply(opt.onAfterRebackPage)();            
            return this;
        },
        _renderPageHeader: function(menuText) {
            var opt = this.options, dom = this.dom,
                $pageHeader = dom.pageHeader,
                headerHtml = menuText;
            if ($pageHeader) {
                if ($.isFunction(opt.pageHeaderRender)) {
                    headerHtml = opt.pageHeaderRender(menuText);
                }
                $pageHeader.html(headerHtml);
            }
            return this;
        },
        _renderPageContent: function(pageHtml) {
            var opt = this.options, dom = this.dom,
                $pageContent = dom.pageContent,
                contentHtml = pageHtml;
            if ($pageContent) {
                if ($.isFunction(opt.pageContentRender)) {
                    contentHtml = opt.pageContentRender(pageHtml);
                }
                contentHtml = contentHtml || '';
                $pageContent.empty();
                $pageContent.html(contentHtml);
            }
            return this;
        },
        _savePageHistory: function(pageUrl, $menuLink, menuText) {
            var opt = this.options, pageHistory = this.pageHistory, pageHistoryItem = {};
            if (pageUrl) {
                pageHistoryItem.url = pageUrl;
                if ($menuLink) {
                    pageHistoryItem.$menu = $menuLink;
                    pageHistoryItem.menuText = menuText;
                }
            }
            pageHistory.push(pageHistoryItem);
            if (pageHistory.length > opt.pageHistoryCount) {    //只保存最近100条历史页面
                pageHistory.shift();   //移除首个元素
            }
            return this;
        },
        rebackLastPage: function() {
            var opt = this.options, pageHistory = this.pageHistory, lastPageItem = {};
            if (pageHistory.length >= 2) {
                lastPageItem = pageHistory.splice(pageHistory.length - 2, 1)[0],  //取出倒数第二个历史页面
                $.rwpUI.funcApply(opt.onBeforeRebackPage)(lastPageItem);                
                this.loadPage(lastPageItem.url, true, lastPageItem.$menu, lastPageItem.menuText);                   
            }
            return this;
        },
        getCurrentPage: function() {
            var pageHistory = this.pageHistory;
            if (pageHistory.length > 0) {
                return pageHistory[pageHistory.length - 1];
            }
        }
    };
    //new init(opt)时会创建原型为init.prototype的对象, 并在该对象上执行init方法
    rwpMenuLayout.fn._init.prototype = rwpMenuLayout.fn;  //指定init方法的原型    

    rwpMenuLayout.defaults = {
        pageHeaderClass: 'pageHeader',   //页面头部Div类名
        pageContentClass: 'pageContent',    //页面内容Div类名             
        pageHistoryCount: 2,     //默认只保存最近2个显示的页面地址
        waittingMessage: '页面加载中,请稍候...',   //页面加载提示文本
        onBeforeLoading: null,    //页面加载前事件       
        onAfterLoading: null,     //页面加载后事件
        onBeforePageRender: null,  //页面渲染前事件
        onAfterPageRender: null,   //页面渲染后事件
        pageHeaderRender: null,   //页面头部展示
        pageContentRender: null,   //页面内容展示
        onBeforeRebackPage: null,  //页面返回前事件
        onAfterRebackPage: null    //页面返回后事件
    };

    $.rwpUI.menuLayout = rwpMenuLayout;

    $.rwpUI.Datagrid.defaults.checkInitLoadData = function () {   //设置默认表格 判断初始化加载数据方法
        return !rwpMenuHelper.isRebackPage;
    };

})(jQuery, window, document);