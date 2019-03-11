/**
* @license                                     
* form表单
*
* Since: 2013/2/5
*/
; (function ($, window, document, undefined) {    

    var pluginName = 'rwpUIForm';   //控件名称

    var rwpForm = function (element, options) {
        var lowerTagName = element.tagName.toLowerCase();   //获取元素tag名称

        if (lowerTagName == 'form') {   //只支持Form元素
            this.element = element;
            this.$element = $(element);

            var opt = $.extend(true, {}, $.rwpUI.Form.defaults, options);  //合并已赋值参数(进行递归合并)
            this.options = opt;

            this._init();  //初始化
            $.data(this.element, pluginName, this);   //保存到元素上
        }
    };

    rwpForm.prototype = {
        _init: function () {
            var opt = this.options,
                inputOptions = {};

            if (opt.inputWidth) inputOptions.width = opt.inputWidth;
            this.inputOptions = inputOptions;

            this._setElements();

            this._setSubmit();
        },
        _setElements: function () {   //设置表单元素
            var $form = this.$element, that = this,
                opt = this.options,
                supportTags = $.rwpUI.Form._supportTags;
            
            if (typeof opt.isAutoPlugin == 'boolean' && opt.isAutoPlugin === true) {   //是否自动应用控件
                $form.find('*').each(function (i, item) {
                    var $item = $(item),
                        lowerTagName = item.tagName.toLowerCase(),
                        pluginName = '', inputType;

                    if ($item.hasClass('rwpButton')) {   //a标签常用于按钮
                        pluginName = 'Button';
                    }
                    else if ($.inArray(lowerTagName, supportTags) !== -1) {
                        if (lowerTagName === 'select')
                            pluginName = 'Combobox';
                        else if (lowerTagName === 'input') {
                            inputType = $(item).attr('type');
                            switch (inputType) {
                                case 'password':  //密码输入框
                                    pluginName = 'Textbox';
                                    break;
                                case 'checkbox':   //复选框
                                    pluginName = 'Toggle';
                                    break;
                                case 'text':
                                    pluginName = 'Textbox';
                                    break;
                                case 'submit':   //提交
                                    pluginName = 'Button';
                                    break;
                                case 'button':
                                    pluginName = 'Button';
                                    break;
                                case 'reset':   //重置
                                    pluginName = 'Button';
                                    break;
                            }
                        }
                    }

                    that._applyPlugin($item, pluginName);
                });
            }

            return this;
        },
        _applyPlugin: function ($element, pluginName) {    //应用控件
            var inputOptions = this.inputOptions;            

            if ($element instanceof jQuery && pluginName) {
                var isPlugin = $.rwpUI[pluginName],  //判断是否加载对应控件js
                    manager = null;
                if (pluginName === 'Textbox')
                    manager = $element.data('rwpUICombobox') || $element.data('rwpUISpinner') || $element.data('rwpUITextbox');
                else
                    manager = $element.data('rwpUI' + pluginName);

                if (pluginName !== 'Toggle' && pluginName !== 'Button')
                    manager && manager.setWidth(inputOptions.width);  //已应用控件的文本框 重新设置宽度
                //应用控件并设置统一宽度
                !manager && isPlugin && $element['rwpUI' + pluginName]((pluginName == 'Toggle' || pluginName == 'Button') ? {} : inputOptions);
            }

            return this;
        },
        _setSubmit: function () {   //设置表单提交
            var opt = this.options, that = this,
                $form = this.$element;                

            if (typeof opt.isAjaxSubmit == 'boolean' && opt.isAjaxSubmit === true) {
                $form.submit(function (e) {                    
                    e.preventDefault();
                    that.ajaxSubmit();  //使用ajax提交                    
                    return false; //阻止表单提交
                });
            }

            return this;
        },
        ajaxSubmit: function (callback) {
            var that = this, $form = this.$element,
                ajaxOpts = this.options.ajaxSubmitAttrs,
                isDialogWaitting = $.rwpUI.waitting;   //等待窗口插件

            if (ajaxOpts.isValid === false || !(typeof($form.valid) == 'function' && !$form.valid())) //validation状态
            {
                that.formValid = true;   //标识表单客户端验证成功
                var _submitstate = that.ajaxSubmitState; //缓存提交状态
                if (!_submitstate) {
                    if (ajaxOpts.beforeSubmit && typeof (ajaxOpts.beforeSubmit) == 'function') {
                        ajaxOpts.beforeSubmit();                        
                    }
                    else if (isDialogWaitting){
                        that.tipManager = $.rwpUI.waitting('数据提交中,请稍候...');
                    }
                    $form.data("ajaxSubmit", true); //状态 = 提交中
                    that.ajaxUrl = $form.attr('action');
                    that._formAjax();   //执行ajax请求
                }
            }
            else {
                that.formValid = false;  //标识表单客户端验证失败
            }

            callback && typeof (callback) == 'function'   //ajax提交回调
            && callback.apply(window, [{ validState: that.formValid, submitState: that.ajaxSubmitState, submitResult: that.submitResult }])

            return this;
        },        
        _formAjax: function () {  //ajax请求
            var $form = this.$element,
                that = this, ajaxOpts = this.options.ajaxSubmitAttrs;

            //请求服务器
            $.ajax({
                type: 'POST',
                url: that.ajaxUrl + (that.ajaxUrl.indexOf("?") >= 0 ? '&' : '?') + 'rnd' + Math.random(),
                data: $form.serialize(),
                async: false,  //同步提交，阻塞浏览器窗口
                dataType: 'json',
                success: function (data) {
                    if (data) {
                        that.submitResult = data;  //保存提交结果
                        if (ajaxOpts.resultsForm && typeof (ajaxOpts.resultsForm) == 'function') {
                            ajaxOpts.resultsForm.call('formdata', data);
                        }
                        else {
                            that._formResults(data);
                        }
                    }
                    that._submitOver(data);
                    if (ajaxOpts.isDialog) {
                        if (data && data.stateMsg && $.rwpUI.alert) {
                            var yesfun = null;
                            if (data.stateType == 0 && ajaxOpts.submitOk && typeof(ajaxOpts.submitOk) == 'function') {
                                yesfun = ajaxOpts.submitOk;
                            }
                            $.rwpUI.alert(data.stateMsg, '操作提示', data.stateType == 0 ? 'success' : 'error', false, yesfun);
                        }
                    }
                    else if (ajaxOpts.isRedirect) {  //允许跨转页面
                        if (data && data.url) {
                            window.location = data.url;   //执行页面跳转
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                	if(XMLHttpRequest.responseText.indexOf("<html class=\"userlogin\">")!=-1){
                		$.rwpUI.error("会话超时，请重新登录！","",false,function(){
                        	window.location.reload();
                    	});
                    	return;
                    };
                    that._submitOver();
                }
            });

            return this;
        },        
        _submitOver: function (data) {  //表单提交结束
            var $form = this.$element,
                ajaxOpts = this.options.ajaxSubmitAttrs;

            this.ajaxSubmitState = false; //状态 = 未提交
            if (ajaxOpts.afterSubmit && typeof (ajaxOpts.afterSubmit) == 'function') {
                ajaxOpts.afterSubmit.call('aftersubmit', data);
            }
            this.tipManager && this.tipManager.close();  //关闭数据提交等待窗口

            return this;
        },        
        _formResults: function (data) {  //格式化返回信息
            var $form = this.$element;

            if (data && data.stateType == 2) {   //验证失败
                $('.field-validation-error', $form).addClass("field-validation-valid")
                    .removeClass("field-validation-error");   //去除之前验证失败的提示                
                $('.validation-summary-errors', $form).addClass("validation-summary-valid")
                    .removeClass("validation-summary-errors");   //去除之前所有验证失败的提示
                $('.validation-summary-valid > ul', $form).empty();
                var summaryCount = 0;
                $.each(data.validationResults, function (i, item) {
                    $.each(item.MemberNames, function (j, name) {
                        var _vname = name,
                            _vmsg = name,
                            _vinput = $("#" + _vname + ":input", $form);
                        if (_vinput.length == 0) {
                            _vinput = $(":input[name='" + _vname + "']", $form);
                            if (_vinput.length < 1) {
                                if (summaryCount == 0)
                                    $('.validation-summary-valid', $form).addClass('validation-summary-errors')
                                         .removeClass('validation-summary-valid');
                                $('.validation-summary-errors > ul', $form).append('<li>' + item.ErrorMessage + '</li>')
                                summaryCount++;
                            }
                        }
                        _vinput.addClass("input-validation-error");
                        $("span[data-valmsg-for='" + _vmsg + "']", $form)
                                    .html('<span for="' + _vname + '" generated="true">' + item.ErrorMessage + '</span>')
                                    .removeClass("field-validation-valid")
                                    .addClass("field-validation-error");
                    });
                });
            }
            return this;
        }        
    };

    $.rwpUI.Form = function (element, options) {
        (new rwpForm(element, options));
    };

    $.rwpUI.Form.ajaxSubmitAttrs = {
        isValid: true,        //提交前是否进行表单验证
        isDialog: false,      //返回提示信息是否弹窗显示        
        isRedirect: false,    //提交后是否允许跳转
        beforeSubmit: null,   //提交前事件
        afterSubmit: null,    //提交后事件
        resultsForm: null,    //提交返回验证信息显示
        submitOk: null        //提交成功事件
    };

    $.rwpUI.Form.defaults = {    //默认参数
        inputWidth: 180,    //控件宽度
        isAutoPlugin: true,    //是否自动应用控件
        isAjaxSubmit: false,      //是否ajax提交
        ajaxSubmitAttrs: $.rwpUI.Form.ajaxSubmitAttrs  //表单提交参数
    };    

    $.rwpUI.Form._supportTags = [ 'input', 'select' ];

    $.rwpUI.Form.fn = rwpForm;  //指向rwpForm

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Form(element, options);
        });
    };

})(jQuery, window, document);