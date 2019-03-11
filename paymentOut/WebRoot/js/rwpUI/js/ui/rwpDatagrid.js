/**
* @license                                     
* DataGrid表格控件
*
* Since: 2013/2/4
*/
; (function ($, window, document, undefined) {

    var pluginName = 'rwpUIDatagrid';   //控件名称

    var rwpDatagrid = function (element, options) {
        this.element = element;
        this.$element = $(element);

        var opt = $.extend({}, $.rwpUI.Datagrid.defaults, options);  //合并已赋值参数
        this.options = opt;

        this._init();  //初始化
        $.data(this.element, pluginName, this);   //保存到元素上
    };

    rwpDatagrid.prototype = {
        _init: function () {
            if (this._extendOption && typeof (this._extendOption) == 'function')
                this._extendOption();
            var opt = this.options, that = this;
            if (!this.$element.hasClass('rwpGridTable')) this.$element.addClass('rwpGridTable');
            this._innerHtml()._setloading();

            if (opt.toolbar) this._setToolBar();

            this._setGridHead()
                ._getGridWrapHeight()
                ._setGridWrapWidth();

            this.selectedRows = [];   //初始化所有页面选中集合
            this.gridRowID = 1;  //初始行记录ID            
            
            if (!($.isFunction(opt.checkInitLoadData) && opt.checkInitLoadData() == false)) {
                this._loadData();  //判断是否初始化时加载数据
            }            
            this._resize()
                ._scrollSync();

            $(window).resize(function () {
                that._resize()._resize();
            });
        },
        _innerHtml: function () {
            var $grid = this.$element,
                templateStr = $.rwpUI.Datagrid._template,
                innerDom = {}, opt = this.options;

            $grid.html($(templateStr));

            innerDom.gridHead = $(".rwpGridTableHead", $grid);
            innerDom.gridHeadTable = $("table:first", innerDom.gridHead);
            innerDom.gridHeadTableWrap = innerDom.gridHeadTable.parent();
            innerDom.gridLoading = $(".rwpMidLoading", $grid);
            innerDom.gridBody = $(".rwpGridTableBody", $grid);
            innerDom.gridBodyTable = $("table:first", innerDom.gridBody);

            if (opt.usePager) {  //添加分页
                innerDom.gridPage = $('<div class="rwpGridPage"></div>');
                $grid.append(innerDom.gridPage);
            }

            if (opt.toolbar) {
                innerDom.gridTopToolbar = $('<div class="rwpGridToolbar"></div>');
                innerDom.gridHead.before(innerDom.gridTopToolbar);  //插入在表头之前
                if (opt.isBottomToolBar) {
                    innerDom.gridBottomToolbar = $('<div class="rwpGridToolbar"></div>');
                    innerDom.gridBody.after(innerDom.gridBottomToolbar);   //插入表尾工具栏
                }
            }

            this.innerDom = innerDom;
            return this;
        },
        _setloading: function () {
            var that = this, $gridLoading = this.innerDom.gridLoading;

            $gridLoading.hide();  //先隐藏
            this.loadingFunctions = {  //默认加载效果
                showLoading: function () {
                    $gridLoading.show();
                },
                hideLoading: function () {
                    $gridLoading.hide();
                }
            };

            return this;
        },
        //设置工具栏
        _setToolBar: function () {
            var opt = this.options,
                toolItems = opt.toolbar.items,
                innerDom = this.innerDom,
                $topToolbar = innerDom.gridTopToolbar,
                $bottomToolbar = innerDom.gridBottomToolbar,
                $toolCheck = $($.rwpUI.Datagrid._toolCheckTemplate),
                isButtonPlugin = $.rwpUI.Button;

            var setToolbarBtn = function ($toolBarDom, toolItem) {   //设置工具栏按钮
                if ($toolBarDom && $toolBarDom instanceof jQuery && toolItem) {
                    var $toolbtn = $('<a href="javascript:void(0);"></a>'), btnOpts = {};
                    toolItem.text && $toolbtn.html(toolItem.text);
                    $toolBarDom.append($toolbtn);

                    if (toolItem.icon) btnOpts.icon = "rwpIcon" + toolItem.icon && (toolItem.icon.substring(0, 1).toUpperCase() + toolItem.icon.substring(1));
                    if (toolItem.click && typeof (toolItem.click) == 'function') btnOpts.click = function () { toolItem.click(toolItem); };

                    isButtonPlugin && $toolbtn.rwpUIButton(btnOpts);  //应用rwpButton控件
                    !isButtonPlugin && btnOpts.click && $toolbtn.click(btnOpts.click);  //绑定点击事件
                }
            };

            if (opt.checkbox && opt.isToolBarCheckAll) {   //表格带复选框 并且工具栏添加全选                
                $topToolbar.append($toolCheck);
                if (opt.isBottomToolBar) {
                    var $toolcheckClone = $toolCheck.clone();
                    $bottomToolbar.append($toolcheckClone);
                }
            }

            toolItems && $.each(toolItems, function (i, item) {
                setToolbarBtn($topToolbar, item);   //设置表头工具栏
                opt.isBottomToolBar && setToolbarBtn($bottomToolbar, item);  //设置表尾工具栏
            });

            return this;
        },
        //设置表头
        _setGridHead: function () {
            var $gridHead = this.innerDom.gridHead,
                $headtr = $("tr:first", $gridHead),
                gridTableWidth = 0, headerColumns = [],
                opt = this.options;

            //如果有复选框列 
            if (opt.checkbox) {
                var $checkHeaderCell = $('<th>' + (opt.isToolBarCheckAll ? '' : '<input id="checkall" type="checkbox" />') + '</th>');
                $checkHeaderCell.css({ width: 16 });
                $headtr.append($checkHeaderCell);
                headerColumns.push({
                    width: 16,
                    isCheckbox: true
                });
                gridTableWidth += 17 + 10;  //添加padding左右边距10
            }
            //遍历列
            $.each(opt.columns, function (i, item) {
                var $headerCell = $("<th></th>"),
                    headerText = '', columnWidth = item.width,
                    isLast = i == opt.columns.length - 1; //是否末列
                if (item.name) $headerCell.attr({ columnname: item.name });
                headerText = item.display || '&nbsp;';
                $headerCell.html(headerText);

                if (item.isHide) {//隐藏
                    $headerCell.hide();
                }
                else {
                    if (!columnWidth) {//列没有设置宽度，读取默认宽
                        columnWidth = opt.columnWidth;
                    }
                    if (!isNaN(columnWidth)) {
                        $headerCell.css({ width: columnWidth });
                        if (isLast) {
                            if ('undefined' == typeof (document.body.style.maxHeight) && !$.support.style) {
                                //columnWidth += 1;  //IE6
                            }
                        }
                        else {
                            //columnWidth += 1;
                        }
                        gridTableWidth += columnWidth + 10 + 1;   //添加padding左右边距共10 边框1
                    }
                }

                $headtr.append($headerCell);
                headerColumns.push({
                    width: columnWidth,
                    columnName: item.name,
                    isHide: item.isHide,
                    columnIndex: i,
                    isLast: isLast
                });
            });
            this.gridTableWidth = gridTableWidth;
            this.headerColumns = headerColumns;

            return this;
        },
        //获取外部高度
        _getGridWrapHeight: function () {
            var wrapHeight = 0, opt = this.options, innerDom = this.innerDom, $grid = this.$element;
            if (opt.height && opt.height != 'auto') {//当高度设置成100%时，获取额外高度
                wrapHeight += $grid.offset().top;
                if (innerDom.gridHead)
                    wrapHeight += parseInt(innerDom.gridHead.outerHeight(true));
                if (innerDom.gridPage)
                    wrapHeight += parseInt(innerDom.gridPage.outerHeight(true));
                if (innerDom.gridTopToolbar)
                    wrapHeight += parseInt(innerDom.gridTopToolbar.outerHeight(true));
                if (innerDom.gridBottomToolbar)
                    wrapHeight += parseInt(innerDom.gridBottomToolbar.outerHeight(true));
                wrapHeight += 2;   //增加body边框高度
                wrapHeight += opt.heightDeduct;  //表格下面元素的高度
            }
            this.wrapHeight = wrapHeight;

            return this;
        },
        //设置外部宽度
        _setGridWrapWidth: function () {
            var opt = this.options, innerDom = this.innerDom, gridTableWidth = this.gridTableWidth;
            if (!(typeof (opt.width) == "string" && opt.width.indexOf('%') > 0)) {
                innerDom.gridHeadTableWrap.width(gridTableWidth + 17);  //添加右侧滚动条宽度
                innerDom.gridHeadTable.width(gridTableWidth);   //减去右侧滚动条宽度                
            }
            this._setGridBodyTableWidth();

            return this;
        },
        //设置主体表格宽度
        _setGridBodyTableWidth: function () {
            var opt = this.options, innerDom = this.innerDom, gridTableWidth = this.gridTableWidth;
            if (typeof (opt.width) == "string" && opt.width.indexOf('%') > 0) {
                innerDom.gridBodyTable.width(opt.width);
            }
            else {
                innerDom.gridBodyTable.width(gridTableWidth);
            }

            return this;
        },
        //读取数据
        _loadData: function (rowTr, url, param, callbacks) {
            var that = this, opt = this.options,
                callbacks = $.extend({}, that.loadingFunctions, callbacks || {}),
                ajaxParam = [], appendData;

            if (!rowTr) {   //非延迟加载 获取表格参数
                url = url || opt.url;
                if (!(param && $.isArray(param))) {
                    param = opt.params;
                }
            }
            if (url) {
                if (param && $.isArray(param) && param.length > 0) {
                    ajaxParam = $.rwpUI.objectClone(param);  //复制一个副本
                }
                if (!rowTr) {   //初始远程加载
                    if (!opt.newPage) opt.newPage = 1;
                    if (!opt.sortOrder) opt.sortOrder = "asc";
                    if (opt.usePager) {
                        ajaxParam.push({ name: "page", value: opt.newPage });
                        ajaxParam.push({ name: "pagesize", value: opt.pageSize });
                    }
                    if (opt.sortName) {
                        ajaxParam.push({ name: "sortname", value: opt.sortName });
                        ajaxParam.push({ name: "sortorder", value: opt.sortOrder });
                    }
                }
                $.rwpUI.post(url, ajaxParam,
                    function () {
                        callbacks.showLoading();
                    },
                    function (data) {
                        callbacks.hideLoading();
                        that._showData($.extend({}, data), rowTr);
                        $.rwpUI.funcApply(callbacks.afterShowData)();
                    },
                    null,
                    function () {
                        callbacks.hideLoading();
                    });
            }
            else {
                if (!rowTr) {  //初始本地加载
                    appendData = that._getCurrentData($.extend({}, opt.data));  //获取当前数据                                     
                }
                that._showData(appendData, rowTr);  //本地延迟(appendData为空，rowTr为父元素)， 初始本地加载(appendData为初始数据，rowTr为空)
            }

            return this;
        },
        //获取静态数据
        _getCurrentData: function (source) {
            var data = {}, opt = this.options;
            data[opt.root] = [];
            if (!source || !source[opt.root] || !source[opt.root].length) {
                data[opt.record] = 0;
                return data;
            }
            data[opt.record] = source[opt.root].length;

            if (opt.usePager && !opt.newPage) opt.newPage = 1;
            for (var i = (opt.usePager ? (opt.newPage - 1) * opt.pageSize : 0), maxIndex = (opt.usePager ? Math.min(data[opt.record], opt.newPage * opt.pageSize) : data[opt.record]) ;
                i < maxIndex; i++) {
                data[opt.root].push(source[opt.root][i]);
            }
            return data;
        },
        //显示数据
        _showData: function (data, rowTr) {
            var opt = this.options;

            if (!rowTr) {
                this.currentData = data;   //保留初始展示数据
                this.gridRowID = 1;      //重置表格控件
                this.gridRowData = [];
                this.gridRowRecords = {};
                if (!data || !data[opt.root]) return this;
            }

            this._setGridBody(data, rowTr);

            return this;
        },
        //设置表格内容
        _setGridBody: function (data, rowTr) {
            var that = this, opt = this.options, $gridBody = this.innerDom.gridBody,
                $bodyTable = this.innerDom.gridBodyTable, gridHtmlArr = [];

            if (!rowTr) {
                if (opt.usePager) {
                    //更新分页
                    opt.total = data[opt.record];
                    opt.pageCount = Math.ceil(opt.total / opt.pageSize);
                    that._buildPager();
                }
                if ($('tr', $bodyTable).length > 0) {
                    $bodyTable.remove();   //移除原table
                    $gridBody.append('<table></table>');   //新建table
                    $bodyTable = this.innerDom.gridBodyTable = $("table:first", $gridBody);   //重新赋值引用
                    this._setGridBodyTableWidth();  //重新设置表格主体宽度
                }
                that.currentSelectedRows = [];   //初始化当前页面选中的行
            }

            newAppendRowData = that._appendGridRowData(data, rowTr);  //添加表格行数据
            if (newAppendRowData.length > 0) {
                $.each(newAppendRowData, function (i, rowData) {  //循环数据
                    gridHtmlArr.push(that._getHtmlFromRowData(rowData));
                });
                if (!rowTr) {
                    $bodyTable.append(gridHtmlArr.join(''));    //append在大数量插入时 比html效率高
                }
                else {
                    $(rowTr).after(gridHtmlArr.join(''));   //添加子记录表格行
                }

                that._addClickEvent(newAppendRowData);

                opt.isSelected && typeof (opt.isSelected) == "function" && $.each(newAppendRowData, function (j, item) {
                    opt.isSelected(item) && that.select(item._rowID);   //初始化选中记录
                });
                opt.onAfterShowData && typeof (opt.onAfterShowData) == "function" && opt.onAfterShowData.apply(window, [newAppendRowData]);  //数据展示完后触发事件
            }

            return this;
        },
        //绑定分页
        _buildPager: function () {
            var pagerHtmlArr = [], opt = this.options, currentPage = parseInt(opt.newPage), totalPage = parseInt(opt.pageCount),
                that = this, $gridPage = this.innerDom.gridPage, isButtonPlugin = $.rwpUI.Button;
            pagerHtmlArr.push('<span class="rwpPageCount">&nbsp;&nbsp;共' + opt.total + '条记录&nbsp;&nbsp;</span><div class="rwpGridPaging">');
            if (currentPage > totalPage)
                currentPage = totalPage;   //当前页 不能大于总页数
            if (currentPage > 1) {
                pagerHtmlArr.push('<a class="rwpGridPageLink rwpGridPageFirst" page="1" href="1">首页</a><a class="rwpGridPageLink rwpGridPagePrev" page="' + (currentPage - 1) + '" href="' + (currentPage - 1) + '"><</a>');
            }
            if (currentPage > 4)  //前省略
                pagerHtmlArr.push('<span>...</span>');
            for (var i = currentPage - 3; i <= currentPage + 3 ; i++) {   //显示前后三页链接
                if (i >= 1 && i <= totalPage) {
                    if (i == currentPage)   //高亮显示当前页，但不能点击
                        pagerHtmlArr.push('<span class="rwpGridPageLink rwpGridPageCurrent" page="' + i + '">' + i + '</span>');
                    else
                        pagerHtmlArr.push('<a class="rwpGridPageLink" page="' + i + '" href="' + i + '">' + i + '</a>');
                }
            }
            if (currentPage < totalPage - 3)  //后省略
                pagerHtmlArr.push('<span>...</span>');
            if (currentPage < totalPage) {
                pagerHtmlArr.push('<a class="rwpGridPageLink rwpGridPageNext" page="' + (currentPage + 1) + '" href="' + (currentPage + 1) + '">></a><a class = "rwpGridPageLink rwpGridPageLast" page="' + totalPage + '"  href="' + totalPage + '">末页</a>');
            }
            if (opt.isPageInput) {   //添加分页号码输入框
                pagerHtmlArr.push('<span>&nbsp;&nbsp;转到第&nbsp;</span>');
                pagerHtmlArr.push('<span class="rwpGridInputPage"><input type="text" style="width:30px" value="' + currentPage + '">/' + totalPage + '页</span><a class="rwpGridPageButton">跳转</a><span>&nbsp;&nbsp;每页' + opt.pageSize + '条记录</span>');
            }
            pagerHtmlArr.push('</div>');
            $gridPage.html(pagerHtmlArr.join(''));
            $("a.rwpGridPageLink", $gridPage).unbind('click').click(function (e) {
                e.preventDefault();
                that._changPager($(this).attr("page"));
            });
            if (opt.isPageInput) {  //分页号码输入框 响应事件
                var $pageInput = $(":text", $gridPage),
                    $pageButton = $("a.rwpGridPageButton", $gridPage),
                    btnClick = function () { that._changPager($pageInput.val()); };

                isButtonPlugin && $pageButton.rwpUIButton({ click: btnClick });  //应用rwpUIButton控件
                !isButtonPlugin && $pageButton.click(btnClick);
            }

            return this;
        },
        //改变分页
        _changPager: function (page) {
            var opt = this.options;
            if ($.isNumeric(page)) {
                if (page > 0 && page <= opt.pageCount) {
                    opt.newPage = page;   //设置 新的页号
                    $.rwpUI.funcApply(opt.onChangePage)(opt);  //调用更改分页事件
                    this._loadData();
                }
            }

            return this;
        },
        //获取表格行数据
        _appendGridRowData: function (newData) {
            var opt = this.options, appendData = newData, rowData, appendDataArr = [],  //本次新添加数据行
                targetData = this.gridRowData || [], targetRecords = this.gridRowRecords || {},
                rowID = this.gridRowID || 1, rowIndex = targetData.length;  //当前表格行数

            if (appendData) {
                appendData = appendData[opt.root];
            }
            if ($.isArray(appendData) && appendData.length > 0) {
                for (var i = 0, len = appendData.length; i < len; i++) {
                    rowData = appendData[i];
                    rowData._rowIndex = rowIndex++;
                    rowData._rowID = 'row' + rowID++;
                    appendDataArr.push(rowData);
                    targetRecords[rowData._rowID] = rowData;
                }

                targetData = targetData.concat(appendDataArr); //将 新添加的行记录 组合成新的表格行数组
                this.gridRowData = targetData;
                this.gridRowRecords = targetRecords; //以行记录ID为键值的字典集合
                this.gridRowID = rowID;  //保存最后的ID                
            }

            return appendDataArr;
        },
        //表格行数据转换为html
        _getHtmlFromRowData: function (rowData) {
            var that = this, gridRowHtmlArr = [], headerColumns = this.headerColumns, opt = this.options,
                tdHtmlArr = [], tdCheckBoxHtmlArr = [], checkboxHeader, checkboxValue;

            if (!rowData || !rowData._rowID) return;
            gridRowHtmlArr.push('<tr');
            if (that._isGridTrHide && typeof (that._isGridTrHide) == 'function') {
                if (that._isGridTrHide(rowData))
                    gridRowHtmlArr.push(' style="display:none;"');  //该行隐藏
            }
            if (rowData._rowIndex % 2 == 1) {
                gridRowHtmlArr.push(' class="rwpGridEvenTr"');  //隔行区分显示
            }
            gridRowHtmlArr.push(' rowid="' + rowData._rowID + '" >');

            $.each(headerColumns, function (i, header) { //循环表头                
                var column = opt.columns[header.columnIndex], tdContent = '';
                if (header.isCheckbox) {
                    return;
                }
                tdHtmlArr.push('<td columnindex="' + header.columnIndex + '"');
                if (header.columnName) {
                    tdHtmlArr.push(' columnname="' + header.columnName + '"');
                    tdContent = that._formatTdContent(rowData[header.columnName], column.format);   //格式化表格列显示
                }
                if (column.primarykey) {//主键                    
                    rowData._keyValue = tdContent;  //保存主键值
                }
                if ($.rwpUI.isFunction(column.render)) {//模版列
                    tdContent = column.render(rowData, i, tdContent);
                }
                if (that._extendCellHtml && typeof (that._extendCellHtml) == 'function') {
                    tdContent = that._extendCellHtml(header, tdContent, rowData);   //扩充表格列显示
                }

                tdHtmlArr.push(' style="');
                if (header.isHide) {    //隐藏该列
                    tdHtmlArr.push('display: none;');
                }
                if (header.width && rowData._rowIndex == 0) {    //只设置表格首行 各列的宽度
                    tdHtmlArr.push('width:' + header.width + 'px;');
                }
                tdHtmlArr.push('">' + tdContent + '</td>');
            });
            if (headerColumns && headerColumns.length > 0 && headerColumns[0].isCheckbox) {
                checkboxHeader = headerColumns[0];
                tdCheckBoxHtmlArr.push('<td');
                if (checkboxHeader.width && rowData._rowIndex == 0) {
                    tdCheckBoxHtmlArr.push(' style="width:' + checkboxHeader.width + 'px"');
                }
                checkboxValue = rowData._keyValue;
                if (checkboxValue == undefined || checkboxValue == null) {
                    checkboxValue = '';
                }
                tdCheckBoxHtmlArr.push('><input id="checkid" name="checkid" type="checkbox" value="' + checkboxValue + '" /></td>');
            }
            gridRowHtmlArr.push(tdCheckBoxHtmlArr.join(''));   //复选框列            
            gridRowHtmlArr.push(tdHtmlArr.join(''));   //其它数据列
            gridRowHtmlArr.push('</tr>');

            if (rowData._delay) {  //延迟加载 添加行记录 展开回调 
                $.isFunction(that._addDelayCallback) && that._addDelayCallback(rowData);
            }

            return gridRowHtmlArr.join('');
        },
        //格式化Td内容
        _formatTdContent: function (value, format) {
            var that = this;
            if (typeof value == 'string') {
                var date = value + "";
                if (date.indexOf('/Date(') == 0) {
                    date = eval('new ' + value.replace('/', '', 'g').replace('/', '', 'g'));
                    date = $.rwpUI.formatDate(date, format || that.options.dateFormat);
                }
                return date;
            }
            else {
                return (value === undefined || value === null) ? '' : value;    //行记录中没定义该属性时显示空字符串
            }
        },        
        //增加点击事件
        _addClickEvent: function (appendRowData) {
            var $bodyTable = this.innerDom.gridBodyTable, opt = this.options, that = this,
                $grid = this.$element, $gridHead = this.innerDom.gridHead, $bodyTable = this.innerDom.gridBodyTable,
                $toBindTrs, startIndex = endIndex = 0, isFirstBind, $checkall;

            if ($.isArray(appendRowData) && appendRowData.length > 0) {
                startIndex = appendRowData[0]._rowIndex;
                endIndex = appendRowData[appendRowData.length - 1]._rowIndex;
                $toBindTrs = $('tr', $bodyTable).slice(startIndex, endIndex + 1);  //获取待绑定事件的行记录集合
                isFirstBind = startIndex == 0;

                $toBindTrs.click(function (e) {
                    var $tr = $(this), rowID = $tr.attr('rowid');
                    that._gridRowClick(rowID);
                });
                if (opt.checkbox) {
                    //工具栏全选 或者 表头全选
                    $checkall = opt.isToolBarCheckAll ? $("input[type=checkbox][name=checkall]", $grid) : $("#checkall", $gridHead);
                    if (isFirstBind) {   //首次绑定才设置全选checkbox的change事件
                        $checkall.change(function () {
                            $("input[type=checkbox][name=checkid]", $bodyTable).prop("checked", this.checked);
                            //工具栏checkbox全选时　级联更新另一个全选checkbox
                            opt.isToolBarCheckAll && $("input[type=checkbox][name=checkall]", $grid).not($(this)).prop("checked", this.checked);
                            if (this.checked) {
                                $('tr', $bodyTable).addClass("rwpGridTableTrFocus");   //所有行选中
                                that.currentSelectedRows = [];  //清空当前页面选中集合
                                $.each(that.gridRowData, function (i, item) {
                                    that.currentSelectedRows.push(item);   //将页面所有记录添加到选中集合
                                });
                                opt.onCheckRow && typeof (opt.onCheckRow) == "function" && opt.onCheckRow();
                            }
                            else {
                                $('tr', $bodyTable).removeClass("rwpGridTableTrFocus");
                                that.currentSelectedRows = [];  //清空当前页面选中集合
                            }
                        });
                    }
                    $("input[type=checkbox][name=checkid]", $toBindTrs).change(function () {
                        if (!this.checked) {
                            $checkall.prop("checked", this.checked);    //有一行没选中 则取消全选
                        }
                    });
                }
                $("a", $toBindTrs).click(function (e) {
                    e.stopPropagation();    //阻止a标签点击事件的冒泡                
                });

                if (that._extendClickEvent && typeof (that._extendClickEvent) == 'function')
                    that._extendClickEvent($toBindTrs);   //扩展点击响应事件
            }

            return this;
        },
        //表格行点击事件
        _gridRowClick: function (rowID) {
            var $bodyTable = this.innerDom.gridBodyTable, clickRowData = this.gridRowRecords[rowID],
                that = this, opt = this.options, selectIndex = -1;

            if (clickRowData) {
                var $clickTr = $('tr', $bodyTable).eq(clickRowData._rowIndex),
                    isFocus = $clickTr.hasClass("rwpGridTableTrFocus");

                if (isFocus) {
                    $clickTr.removeClass("rwpGridTableTrFocus");  //取消选中样式
                    selectIndex = that._getIndexInCurrentSelected(rowID);
                    selectIndex !== -1 && that.currentSelectedRows.splice(selectIndex, 1);   //当前页面选中集合中 移除该行

                    !opt.checkbox && opt.onCancleSelectRow && typeof (opt.onCancleSelectRow) == "function" && opt.onCancleSelectRow(clickRowData);
                }
                else {
                    if (!opt.checkbox) {   //没有复选框时　先移除所有选中行，并清空选中集合
                        $("tr", $bodyTable).removeClass("rwpGridTableTrFocus");
                        that.currentSelectedRows = [];
                    }
                    else {
                        selectIndex = that._getIndexInCurrentSelected(rowID);
                    }
                    $clickTr.addClass("rwpGridTableTrFocus");
                    selectIndex === -1 && that.currentSelectedRows.push(clickRowData)  //添加该行 到当前页面选中集合

                    opt.checkbox && opt.onCheckRow && typeof (opt.onCheckRow) == "function" && opt.onCheckRow();
                    !opt.checkbox && opt.onSelectRow && typeof (opt.onSelectRow) == "function" && opt.onSelectRow(clickRowData);
                }
                $("input:checkbox", $clickTr).prop("checked", !isFocus);   //关联点击行checkbox是否选中                

                if (that._extendGridRowClick && typeof (that._extendGridRowClick) == 'function')
                    that._extendGridRowClick(rowID, isFocus);  //扩展表格行点击事件
            }

            return this;
        },
        //得到特定行记录在 当前页选中集合中的索引值
        _getIndexInCurrentSelected: function (rowID) {
            var currentSelectedRows = this.currentSelectedRows, selectIndex = -1;

            $.each(currentSelectedRows, function (i, item) {
                if (item._rowID === rowID) {
                    selectIndex = i;
                    return false; //实现break功能
                }
            });

            return selectIndex;  //-1代表不存在
        },
        //缩放
        _resize: function () {
            this._getGridWrapHeight()
                ._setGridBodyHeight()
                ._setGridBodyWidth();

            return this;
        },
        //设置表格高度
        _setGridBodyHeight: function () {
            var opt = this.options, wrapHeight = this.wrapHeight,
                $gridBody = this.innerDom.gridBody,
                h = $(window).height(), lh;  //窗体顶级Layout宽度

            if (window.layoutHeight && typeof (window.layoutHeight) == 'function') {
                lh = window.layoutHeight();
            }
            if (opt.height && opt.height != 'auto') {
                if (!this.winHeight || this.winHeight != h) {
                    this.winHeight = h;
                    if ($.isNumeric(lh))
                        h = h < lh ? lh : h;  //框架页面出现垂直滚动条时 以Layout布局高度为准
                    if (typeof (opt.height) == "string" && opt.height.indexOf('%') > 0) {
                        if ($.isNumeric(wrapHeight)) {
                            h -= wrapHeight;
                        }
                        h = h * parseFloat(opt.height) * 0.01;
                    }
                    else if (opt.height === +opt.height) {  //判断高度是否设置为数字                            
                        h = parseFloat(opt.height);
                    }
                    $gridBody.height(h);
                }
            }

            return this;
        },
        //设置表格宽度
        _setGridBodyWidth: function () {
            var opt = this.options, innerDom = this.innerDom,
                $gridBody = innerDom.gridBody, $gridBodyTable = innerDom.gridBodyTable,
                $gridHead = innerDom.gridHead, $gridHeadTableWrap = innerDom.gridHeadTableWrap,
                $gridHeadTable = innerDom.gridHeadTable,
                isIE6 = 'undefined' == typeof (document.body.style.maxHeight) && !$.support.style,
                isIE8 = !!window.XDomainRequest && !!document.documentMode;

            if (isIE6) {
                $gridHead.width('100%');
                var gridHeadWidth = $gridHead.width();
                $gridHead.css({ width: gridHeadWidth - 1 });   //IE6 表格头部width 100%后 减去border边框的宽度
                $gridBody.css({ width: gridHeadWidth - 1 });  //IE6 表格宽度与表格头部宽度一致
            }
            if (isIE8) {   //修复IE8 设置table-layout:fix后 每列宽度相同并充满父元素,而不是按照每列设置的宽度进行显示           
                $gridHeadTable[0].style.display = "inline-table";
                $gridBodyTable[0].style.display = "inline-table";
                setTimeout(function () { $gridHeadTable[0].style.display = ""; $gridBodyTable[0].style.display = ""; }, 0);
            }
            if (typeof (opt.width) == "string" && opt.width.indexOf('%') > 0) {
                var $tr = $("tr:first", $gridBodyTable), $ths = $("th", $gridHead);
                if ($tr) {    //存在一行数据                   
                    $("td", $tr).each(function (i, item) {
                        var thWidth = $(item).width();
                        thWidth = i == opt.columns.length - 1 ? thWidth : thWidth - 1;
                        $ths.eq(i).width(thWidth);   //以行中列的宽度 同步 表头列的宽度
                    });
                }
                if (isIE6) {
                    var bodyWidth = $gridBody.width();
                    $gridHeadTableWrap.width(bodyWidth + 15);   //IE6下同步表头整体的宽度
                    $gridHeadTable.width(bodyWidth);
                }
            }

            return this;
        },
        //表体 横向滚动 同步表头
        _scrollSync: function () {
            var $gridBody = this.innerDom.gridBody, gridHead = this.innerDom.gridHead[0];

            $gridBody.scroll(function () {
                var scrollLeft = $(this).scrollLeft();
                if (scrollLeft === undefined) return;
                gridHead.scrollLeft = scrollLeft;
            });

            return this;
        },
        //刷新数据
        refreshData: function (params) {
            var oldUrl = this.options.url;
            if (params && params.data) {
                this.options.data = null;  //清空之前展示数据
            }
            $.extend(true, this.options, params);  //进行递归合并

            if (oldUrl !== this.options.url)
                this.selectedRows = [];   //改变表格url时清空选中集合
            if (this._extendOption && typeof (this._extendOption) == 'function')
                this._extendOption();

            this._loadData();

            return this;
        },
        //获取选中行主键集合
        getCheckedRowKeys: function () {
            return this.getCheckedRows(true);
        },
        //获取选中行集合(无复选框也适用)
        getCheckedRows: function (isKey) {  //是否只获取主键
            var rows = [], currentSelectedRows = this.currentSelectedRows;
            if (currentSelectedRows && currentSelectedRows.length > 0) {
                $.each(currentSelectedRows, function (i, rowData) {
                    rows.push(isKey === true ? rowData._keyValue : rowData);
                });
            }
            return rows;   //返回所有选中行
        },
        select: function (rowID) {
            var gridRowRecords = this.gridRowRecords, opt = this.options, $rowTr,
                $bodyTable = this.innerDom.gridBodyTable, rowData = gridRowRecords[rowID];

            if (rowData) {
                if (!opt.checkbox) {
                    $("tr", $bodyTable).removeClass('rwpGridTableTrFocus');  //没有checkbox时先移除所有行选中
                    this.currentSelectedRows = [];  //清空选中记录
                }
                $rowTr = $('tr', $bodyTable).eq(rowData._rowIndex);
                if (!$rowTr.hasClass('rwpGridTableTrFocus')) {
                    $rowTr.click();  //没有选中时触发点击事件                        
                }
            }

            return this;
        },
        getOptions: function () {
            return this.options;
        }
    };

    $.rwpUI.Datagrid = function (element, options) {
        (new rwpDatagrid(element, options));
    };

    $.rwpUI.Datagrid.defaults = {    //默认参数
        width: 'auto',                         //宽度值
        columnWidth: 120,                     //如果宽度为 auto，而列未设置宽度，则自动设置
        height: '100%',                        //高度值
        data: null,                            //静态数据
        url: false,                            //ajax url
        usePager: true,                        //是否分页
        page: 1,                               //默认当前页
        total: 1,                              //总页面数
        pageSize: 20,                          //每页默认的结果数
        parms: [],                             //提交到服务器的参数
        columns: [],                           //列定义数据源 
        //数据源属性(isHide：'是否隐藏', display: '列显示文本', name: '对应数据源的属性名', primarykey: '是否主键', width: '列宽', render: '渲染函数')
        checkbox: true,                        //是否显示复选框
        dateFormat: 'yyyy-MM-dd',              //默认时间显示格式
        root: 'items',                         //数据源 字段名
        record: 'totalItems',                  //数据源记录数 字段名
        toolbar: null,                         //工具栏
        onSelectRow: null,                     //选择行事件
        onCancleSelectRow: null,               //取消选择行事件        
        onCheckRow: null,                      //勾选行事件
        isSelected: null,                      //是否选中判断函数        
        onAfterShowData: null,                 //数据展示完后响应事件
        isBottomToolBar: false,                //是否表格底部添加工具栏
        isToolBarCheckAll: false,              //checkbox全选是否添加到工具栏
        isPageInput: true,                     //是否添加分页输入框
        isCacheCheckRows: false,               //是否缓存选中的行记录
        onChangePage: null,                    //切换分页事件
        heightDeduct: 0,                       //高度补差，一般用于表格下面还需放置其它元素的情况
        checkInitLoadData: null                //判断是否初始化加载数据
    };

    $.rwpUI.Datagrid._template = '<div class="rwpGridTableHead"><div><table><tr></tr></table></div></div>'
                                 + '<div class="rwpMidLoading">数据加载中...</div>'
                                 + '<div class="rwpGridTableBody"><table></table></div>';

    $.rwpUI.Datagrid._toolCheckTemplate = '<span class="ckbAll"><input name="checkall" type="checkbox" /><cite>全选</cite></span>';

    $.rwpUI.Datagrid.fn = rwpDatagrid;  //指向rwpDatagrid

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Datagrid(element, options);
        });
    };

})(jQuery, window, document);