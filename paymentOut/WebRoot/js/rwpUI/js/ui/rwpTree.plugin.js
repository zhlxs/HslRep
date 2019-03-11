/**
* @license                                     
* Tree树控件 扩展插件
*
* Since: 2013/2/8
*/
; (function ($, window, document, undefined) {        

    if ($.rwpUI.Tree) {
        var rwpTree = $.rwpUI.Tree.fn;        

        //扩展默认参数
        $.extend($.rwpUI.Tree.defaults, {
            /*
            是否展开 
                1,可以是true/false 
                2,也可以是数字(层次)N 代表第1层到第N层都是展开的，其他收缩
                3,或者是判断函数 函数参数e(data,level) 返回true/false

            优先级没有节点数据的isexpand属性高,并没有delay属性高
            */
            isExpand: null,
            /*
            是否延迟加载 
                1,可以是true/false 
                2,也可以是数字(层次)N 代表第N层延迟加载 
                3,或者是字符串(Url) 加载数据的远程地址
                4,如果是数组,代表这些层都延迟加载,如[1,2]代表第1、2层延迟加载
                5,再是函数(运行时动态获取延迟加载参数) 函数参数e(data,level),返回true/false或者{url:...,parms:...}

            优先级没有节点数据的delay属性高
            */
            delay: null                        
        });

        //获取节点的延迟加载状态,返回true/false (本地模式) 或者是object({url :'...',parms:null})(远程模式)
        rwpTree.prototype._getDelay = function (treedata, level) {
            var opt = this.options, delay;
            if (treedata.delay != null && treedata.delay != undefined) {
                delay = treedata.delay;
            }
            else {
                delay = opt.delay;
            }            
            if (delay == null) return false;
            if (typeof (delay) == "function") delay = delay.apply(window, [treedata, level]);
            if (typeof (delay) == "boolean") return delay;
            if (typeof (delay) == "string") return { url: delay };
            if (typeof (delay) == "number") delay = [delay];
            if ($.isArray(delay)) return $.inArray(level, delay) != -1;
            if (typeof (delay) == "object" && delay.url) return delay;
            return false;            
        };

        rwpTree.prototype._isExpand = function (treedata, level) {   //判断节点是否展开状态
            var opt = this.options, isExpand;
            if (treedata.isexpand != null && treedata.isexpand != undefined) {
                isExpand = treedata.isexpand;
            }
            else {
                isExpand = opt.isExpand;
            }           
            if (isExpand == null) return true;  //树形默认折叠方式  true 展开; false折叠
            if (typeof (isExpand) == "function") isExpand = isExpand.apply(window, [treedata, level]);
            if (typeof (isExpand) == "boolean") return isExpand;
            if (typeof (isExpand) == "string") return !(isExpand == 'false');
            if (typeof (isExpand) == "number") return isExpand > level;   //展开终止层级
            return true;
        };
        
        rwpTree.prototype._addDelayCallback = function (treedata, delay) {   //添加延迟加载回调
            var opt = this.options, that = this, treedataindex, nodeExpandCallbacks = this.nodeExpandCallbacks;            
            if (treedata && delay) {
                treedataindex = treedata.treedataindex;
                if (delay.url) {
                    nodeExpandCallbacks[treedataindex] = function(dom) {
                        that.loadData(dom, delay.url, delay.params, {
                            showLoading: function ()
                            { 
                                $("span.rwpTreeExpandClose:first", dom).addClass("rwpTreeExpandLoading");
                            },
                            hideLoading: function ()
                            {
                                $("span.rwpTreeExpandLoading:first", dom).removeClass("rwpTreeExpandLoading");
                            }
                        });
                        delete nodeExpandCallbacks[treedataindex];   //删除属性
                    };                    
                }
                else if (!delay.url && delay.contentCallbackFun && typeof(delay.contentCallbackFun) == 'function') {
                    nodeExpandCallbacks[treedataindex] = function (dom) {
                        var content = delay.contentCallbackFun.apply(that, [treedata]);
                        $(dom).append(content);                            
                        delete nodeExpandCallbacks[treedataindex];   //删除属性
                    };                   
                }
            }
        };        
    }
})(jQuery, window, document);