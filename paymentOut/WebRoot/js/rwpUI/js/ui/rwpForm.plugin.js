/**
* @license                                     
* Form表单控件 扩展插件
*
* Since: 2013/2/8
*/
; (function ($) {

    if ($.rwpUI.Form) {
        var rwpForm = $.rwpUI.Form.fn;

        $.rwpUI.Form.searchAttrs = {
            selectFields: '*',     //查询字段
            whereType: 'where',    //默认为where条件
            andOr: 'and',           //默认为and        
            groupString: 'group'     //分组字符串
        };

        //扩展默认参数
        $.extend($.rwpUI.Form.defaults, {
            isSearch: false,             //查询表单
            searchAttrs: $.rwpUI.Form.searchAttrs   //查询表单参数            
        });

        rwpForm.prototype._initQueryinfo = function () {  //扩充option
            var opt = this.options, that = this,
                $form = this.$element,
                searchOpts = opt.searchAttrs,
                queryInfo = {},   //所有查询条件
                searchInfo = {};   //执行查询条件

            if (typeof opt.isSearch == 'boolean' && opt.isSearch === true) {
                if (searchOpts.selectFields != '*' && $.isArray(searchOpts.selectFields)) {
                    queryInfo.selectFields = searchOpts.selectFields;  //赋值查询字段
                    searchinfo.selectFields = searchOpts.selectFields;
                };
                var inputs = $('input[type=text], select', $form);
                if (inputs.length > 0) {
                    queryInfo.whereInfos = [];
                    var groupArray = [];
                    inputs.each(function (i, input) {
                        var $input = $(input),
                            queryOpt = $input.attr('query-options');
                        if (queryOpt) {
                            queryOpt = queryOpt.replace(/\'/g, "\"");  //单引号转为双引号
                            queryOpt = $.parseJSON(queryOpt);
                            queryOpt.input = $input;
                            queryOpt = that._getFullQuery(queryOpt);
                            if (queryOpt.whereType && queryOpt.whereType == searchOpts.groupString) {
                                var groupName = queryOpt.groupName;
                                if (!groupName) {
                                    alert("分组名未定义");
                                    return;   //continue 跳过本次循环
                                }
                                if (!groupArray[groupName]) {
                                    groupArray[groupName] = [];
                                }                                
                                groupArray[groupName].push(queryOpt);
                            }
                            else {
                                queryInfo.whereInfos.push(queryOpt);
                            }
                        }
                    });                    
                    $.each(groupArray, function (key, group) {    //处理分组条件
                        if (group.length > 0) { //分组条件数大于0                            
                            var i = 0;
                            $.each(group, function (j, item) {
                                if (item.andOr == searchOpts.andOr)
                                    i += 1;
                            });
                            if (i === 0 || i === group.length)  //全为and组合条件 或 全为or组合条件
                                queryInfo.whereInfos.push(
                                    {
                                        whereType: searchOpts.groupString,
                                        andOr: (i === group.length ? 'and' : 'or'),
                                        childWhere: group
                                    });
                        }
                    });                    
                }
                this.queryInfo = queryInfo;
                this.searchInfo = searchInfo;
            }           
            
            return this;
        };
        
        rwpForm.prototype._init = (function () {    //重写init方法
            var _old_init = rwpForm.prototype._init;  //保留原初始化方法
            return function () {
                _old_init.apply(this);  //调用原初始化方法
                this._initQueryinfo();
            };
        })();
        
        rwpForm.prototype._getFullQuery = function (queryOpt) {  //获取每个input元素的完整查询定义
            var searchOpts = this.options.searchAttrs;

            if (!queryOpt.whereField) {
                alert("字段运算符未定义");
                return null;
            }
            if (!queryOpt.fieldType) {
                alert("字段类型未定义");
                return null;
            }
            if (!queryOpt.whereType || queryOpt.whereType == searchOpts.groupString)  //分组条件时也要更改为where
                queryOpt.whereType = searchOpts.whereType;

            queryOpt.andOr = queryOpt.andOr || searchOpts.andOr;   //默认为and            
            queryOpt.fieldName = queryOpt.fieldName || queryOpt.input.attr('name');  //获取input元素的name属性

            if (!queryOpt.fieldName) {
                alert("字段名称未定义");
                return null;
            }
            return queryOpt;
        };

        rwpForm.prototype.getSearchValues = function () {   //获取所有查询值
            if (this.queryInfo && this.searchInfo) {
                this.searchInfo.whereInfos = this._getQueryValues();  //每次查询时循环获取查询条件值
                return this.searchInfo;
            }
            return {};   //返回空对象
        };

        rwpForm.prototype._getQueryValues = function () {   //获取查询条件集合的查询值
            var queryValues = [], that = this,
                searchOpts = this.options.searchAttrs,
                queryInfo = this.queryInfo;

            if (queryInfo && queryInfo.whereInfos && queryInfo.whereInfos.length > 0) {
                $.each(queryInfo.whereInfos, function (i, item) {
                    var clone_whereInfo = $.rwpUI.objectClone(item);  //复制whereinfo
                    if (clone_whereInfo.whereType == searchOpts.groupString) {
                        var childQueryValues = that._getQueryValues(clone_whereInfo.childWhere);  //递归获取子查询的查询值
                        if (childQueryValues.length > 0) {
                            clone_whereInfo.childWhere = childQueryValues;  //设置子查询的查询值
                            queryValues.push(clone_whereInfo);
                        }
                    }
                    else {
                        var inputValue = clone_whereInfo.input.val();
                        if (inputValue) {
                            clone_whereInfo.queryValue = inputValue;
                            delete clone_whereInfo.input;  //删除对象的input属性
                            queryValues.push(clone_whereInfo);
                        }
                    }
                });
            }
            return queryValues;
        };
        
        rwpForm.prototype.setSearchValues = function (queryInfo) {  //设置查询条件值
            if (queryInfo && queryInfo.whereInfos && queryInfo.whereInfos.length > 0)
                this._setQueryValues(queryInfo.whereInfos);

            return this;
        };

        
        rwpForm.prototype._setQueryValues = function (whereInfos) {  //设置查询条件值
            var that = this,
                searchOpts = this.options.searchAttrs,
                queryInfo = this.queryInfo;

            if (whereInfos && whereInfos.length > 0
                && queryInfo && queryInfo.whereInfos && queryInfo.whereInfos.length > 0) {
                $.each(whereInfos, function (i, searchItem) {
                    if (searchItem.whereType == searchOpts.groupString) {
                        that._setQueryValues(searchItem.childWhere);
                    }
                    else {
                        var matchWhere = that._getMatchWhereInfo(searchItem, queryInfo.whereInfos);
                        if (matchWhere && matchWhere.input) {
                            matchWhere.input.val(searchItem.queryValue);  //匹配结果 设置查询值
                            var comboManager = matchWhere.input.data('rwpUICombobox');
                            comboManager && comboManager.refreshValue();  //刷新下拉框选中值
                        }
                    }
                });
            }
        };
        
        rwpForm.prototype._getMatchWhereInfo = function (whereInfo, whereInfos) {  //单个查询条件与查询条件集合进行匹配, 获取匹配的记录
            var that = this, sameWhere, childSameWhere,
                searchOpts = this.options.searchAttrs;

            if (whereInfo && whereInfos && whereInfos.length > 0) {
                $.each(whereInfos, function (i, item) {
                    if (item.whereType == searchOpts.groupString) {
                        childSameWhere = that._getMatchWhereInfo(whereInfo, item.childWhere)  //递归判断子查询是否匹配
                        if (childSameWhere) {
                            sameWhere = childSameWhere;
                            return false;   //子查询匹配 结束循环
                        }
                    }
                    else {
                        if (whereInfo.fieldName == item.fieldName
                            && whereInfo.whereField == item.whereField
                            && whereInfo.fieldType == item.fieldType) {
                            sameWhere = item;
                            return false;     //一对一匹配 结束循环
                        }
                    }
                });
                return sameWhere;
            }
            else
                return;
        };
    }
})(jQuery);