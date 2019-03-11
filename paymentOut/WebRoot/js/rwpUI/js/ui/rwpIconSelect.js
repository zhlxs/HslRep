/**
* @license                                     
* 图标选择
* 
* Since: 2013/07/22
*/
; (function ($, window, document, undefined) {    

    if ($.rwpUI.Dialog) {   //必须已加载弹窗控件        

        var pluginName = 'rwpUIIconSelect';   //控件名称

        var rwpIconSelect = function (element, options) {
            var lowerTagName = element.tagName.toLowerCase();   //获取元素tag名称

            if (lowerTagName === 'div') {   //只支持div
                this.element = element;
                this.$element = $(element);

                var opt = $.extend({}, $.rwpUI.IconSelect.defaults, options);  //合并已赋值参数
                this.options = opt;

                this._init();  //初始化
                $.data(this.element, pluginName, this);   //保存到元素上
            }
        };

        rwpIconSelect.prototype = {
            _init: function () {
                var opt = this.options;
                if (!this.$element.hasClass('rwpIconSelect')) this.$element.addClass('rwpIconSelect');
                this._innerHtml()
                    ._initIconDialog();                
            },
            _innerHtml: function () {
                var innerHtml = '', opt = this.options, that = this,
                    $element = this.$element, innerDom = {}, inputClassValue;

                $element.empty();  //清空div中元素

                innerHtml += '<i style="width:' + opt.iconWidth + 'px; height:' + opt.iconHeight + 'px;" />';  //添加图标显示Div
                innerHtml += '<a class="rwpButton">' + opt.selectButtonText + '</a>';

                that.domID = $element.attr('id') || $.rwpUI.guid('rwpIconSelect_');  //得到Div的ID                
                if (opt.classFieldDomID) {
                    inputClassValue = $("#" + opt.classFieldDomID + ":input");
                    if (inputClassValue.length == 0) inputClassValue = $('<input type="hidden"/>');  //没有找到就创建
                    inputClassValue.attr({ id: opt.classFieldDomID, name: opt.classFieldDomID });  //设置ID和名称 用于在form中提交
                }
                else {                    
                    inputClassValue = $('<input type="hidden"/>');
                    inputClassValue.attr({ id: that.domID + '_val', name: that.domID + '_val' });
                }

                $element.append(innerHtml, inputClassValue);   //添加元素
                
                innerDom.iconShow = $('i', $element);
                innerDom.selectButton = $('a.rwpButton', $element);
                innerDom.inputClass = inputClassValue;

                this.innerDom = innerDom;
                return this;
            },            
            _initIconDialog: function () {  //初始化图标选择Dialog
                var opt = this.options, that = this;

                that.$iconList = $('<ul class="rwpIconList"></ul>');
                that.iconDialog = $.rwpUI.Dialog({ title: opt.selectButtonText, visible: false, content: that.$iconList, isHidden: true });
                that._getImageSize();

                return this;
            },            
            _getImageSize: function () {    //获取图片实际大小
                var newImg = new Image(), opt = this.options,
                    width, height; that = this;
                if (opt.imgSrc) {
                    newImg.onload = function () {
                        that.imgWidth = newImg.width;
                        that.imgHeight = newImg.height;                          
                        that._initIconList();   
                    }
                    newImg.src = opt.imgSrc;
                }

                return this;
            },
            _initIconList: function () {   //初始化图标列表
                var opt = this.options, that = this, imgWidth = that.imgWidth, imgHeight = that.imgHeight,
                    iconListHtml = '', columnNum = 0, rowNum = 0, 
                    iconNum = opt.iconClassArr.length, backgroundPositionX = backgroundPositionY = 0,

                columnNum = Math.floor(imgWidth / parseInt(opt.iconWidth));  //得到列数
                if (columnNum > 0) {
                    rowNum = Math.ceil(iconNum / columnNum);  //得到行数
                    if (rowNum > 0) {
                        //只显示背景图片大小, 累加边框像素
                        that.iconDialog.dom.DialogContent.css({ 'width': imgWidth + columnNum * 2, 'height': imgHeight + rowNum * 2 });
                        for (var i = 0; i < rowNum; i += 1) {
                            for (var j = 0; j < columnNum && j < iconNum - i * columnNum; j += 1) {
                                backgroundPositionX = -(j * opt.iconWidth);
                                backgroundPositionY = -(i * opt.iconHeight);
                                iconListHtml += '<li iconclass="' + opt.iconClassArr[i * columnNum + j]
                                    + '" style="background:url(' + opt.imgSrc + ') no-repeat ' + backgroundPositionX + 'px ' + backgroundPositionY + 'px;'
                                    + ' width:' + opt.iconWidth + 'px; ' + 'height:' + opt.iconHeight + 'px;"/></li>';
                            }  //循环添加显示图标
                        }
                        that.$iconList.html(iconListHtml);   
                    }
                }
                that._setEvent();  //绑定事件
                that._setValue(that.innerDom.inputClass.val());  //同步初始值

                return this;
            },
            _setEvent: function () {   //绑定事件
                var innerDom = this.innerDom, opt = this.options, that = this,
                    $selectButton = innerDom.selectButton;

                $selectButton.click(function () {   //切换Dialog显示状态
                    if (that.iconDialog.dom.Dialog.css("visibility") == 'hidden') {
                        that.iconDialog._setPosition().visible();  //重新调整位置后显示
                    }
                    else {
                        that.iconDialog.hidden();
                    }
                });

                $('li', that.$iconList).click(function (e) {
                    var $iconLi = $(this);

                    $iconLi.addClass('rwpIconSelectedItem').siblings().removeClass('rwpIconSelectedItem');
                    that.innerDom.inputClass.val($iconLi.attr('iconclass'));  //设置图标类名
                    that.innerDom.iconShow.css({ 'background-image': 'url(' + opt.imgSrc + ')', 'background-position': $iconLi.css('background-position') });  //复制背景设置                    
                    that.iconDialog.hidden();  //隐藏图标选择窗体
                });

                return this;
            },
            _setValue: function (value) {   //设置值
                var $iconList = this.$iconList;

                if (value !== null && value !== undefined) {                    
                    $('li[iconclass=' + value + ']', $iconList).trigger('click');
                }

                return this;
            }
        };

        $.rwpUI.IconSelect = function (element, options) {
            (new rwpIconSelect(element, options));
        };

        $.rwpUI.IconSelect.defaults = {    //默认参数        
            iconWidth: 40,     //图标宽度
            iconHeight: 40,    //图标高度            
            iconClassArr: [],  //图标类名数组
            imgSrc: null,     //图标所属图片地址
            selectButtonText: '选择图标',    //选择图标按钮的显示文本
            classFieldDomID: null    //类名 值字段对应的Dom元素ID
        };

        $.rwpUI.IconSelect.fn = rwpIconSelect;  //指向rwpIconSelect

        $.fn[pluginName] = function (options) {
            if (this.length == 0) return;
            this.each(function () {
                var element = this;
                if ($.data(element, pluginName)) return;
                $.rwpUI.IconSelect(element, options);
            });
        };
    }

})(jQuery, window, document);