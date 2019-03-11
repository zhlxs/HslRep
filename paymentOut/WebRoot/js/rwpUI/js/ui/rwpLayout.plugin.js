/**
* @license                                     
* Layout布局控件 扩展插件
*
* Since: 2013/2/8
*/
; (function ($) {    

    if ($.rwpUI.Layout) {
        $.rwpUI.Layout.regionOptions.push('title');  //添加标题

        var rwpLayout = $.rwpUI.Layout.fn;

        rwpLayout.prototype._getRegions = (function () {  //重写 获取区域函数
            var _old_getRegions = rwpLayout.prototype._getRegions;  //保留原 获取区域函数
            return function () {
                _old_getRegions.apply(this);  //调用原 生成下拉框 函数                
                this._setTitle();   //设置每块区域的标题

                return this;
            };
        })();

        rwpLayout.prototype._setTitle = function () {
            var regionDom = this.regionDom,
                regionDomObj, $regionDiv, regionData;

            for (var k in regionDom) {
                regionDomObj = regionDom[k],
                $regionDiv = regionDomObj.$div,  //Div元素
                regionData = regionDomObj.regionData;  //区域数据

                if (regionData.title !== undefined && regionData.title !== null) {
                    $regionDiv.wrapInner('<div class="rwpLayoutContent"></div>');
                    $regionDiv.prepend('<div class="rwpLayoutHead">' + regionData.title + '</div>');
                    regionDomObj.$divHead = $('.rwpLayoutHead', $regionDiv);
                    regionDomObj.$divContent = $('.rwpLayoutContent', $regionDiv);
                }
            }

            return this;
        };

        rwpLayout.prototype._resize = (function () {  //重写 窗体缩放函数
            var _old_resize = rwpLayout.prototype._resize;  //保留原 窗体缩放函数
            return function () {
                _old_resize.apply(this);  //调用原 窗体缩放 函数                
                this._setContentHeight();   //设置每块区域的内容的高度

                return this;
            };
        })();

        rwpLayout.prototype._setContentHeight = function () {
            var regionDom = this.regionDom,
                regionDomObj, $regionDiv, $regionHead, $regionContent, regionData;

            for (var k in regionDom) {
                regionDomObj = regionDom[k],
                $regionDiv = regionDomObj.$div,  //Div元素
                regionData = regionDomObj.regionData;  //区域数据

                if (regionData.title !== undefined && regionData.title !== null) {
                    $regionHead = regionDomObj.$divHead;
                    $regionContent = regionDomObj.$divContent;
                    //设置内容的高度
                    $regionContent.height($regionDiv.height() - $regionHead.outerHeight() - 1);
                }
            }

            return this;
        };
    }

})(jQuery);