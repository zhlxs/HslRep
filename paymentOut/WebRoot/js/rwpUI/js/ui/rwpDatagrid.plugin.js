/**
* @license                                     
* DataGrid表格控件 扩展插件
*
* Since: 2013/2/8
*/
; (function ($) {         

    if ($.rwpUI.Datagrid) {
        var rwpDatagrid = $.rwpUI.Datagrid.fn;

        //扩展默认参数
        $.extend($.rwpUI.Datagrid.defaults, {
            tree: null,                  //treeGrid模式
            autoCheckChildren: true,     //是否自动选中子节点
            includeParent: true          //获取选中行记录时是否包括父结点
        });

        rwpDatagrid.prototype._extendOption = function () {  //扩充option
            var opt = this.options;

            if (opt.tree) {  //启用树模式           
                opt.tree.childrenName = opt.tree.childrenName || "children";
                opt.tree.isParent = opt.tree.isParent || function (rowData) {
                    if (opt.tree.childrenName in rowData && rowData[opt.tree.childrenName])
                        return true;
                    return false;
                };
                opt.tree.isExpand = (function() {
                    var optIsExpand = opt.tree.isExpand;
                    return function(rowData, level) {
                        var isExpand;                        
                        if (rowData.isexpand != null && rowData.isexpand != undefined) {
                            isExpand = rowData.isexpand;
                        }
                        else {
                            isExpand = optIsExpand;
                        }           
                        if (isExpand == null) return true; //默认展开
                        if (typeof (isExpand) == "function") isExpand = isExpand.apply(window, [rowData, level]);
                        if (typeof (isExpand) == "boolean") return isExpand;
                        if (typeof (isExpand) == "string") return !(isExpand == 'false');
                        if (typeof (isExpand) == "number") return isExpand > level;   //展开终止层级
                        
                        return true;
                    };
                })();
                opt.tree.getDelay = function(rowData, level) {
                    var delay;
                    if (rowData.delay != null && rowData.delay != undefined) {
                        delay = rowData.delay;
                    }
                    else {
                        delay = opt.tree.delay;
                    }            
                    if (delay == null) return false; 
                    if (typeof (delay) == "function") delay = delay.apply(window, [rowData, level]);
                    if (typeof (delay) == "boolean") return delay;
                    if (typeof (delay) == "string") return { url: delay };
                    if (typeof (delay) == "number") delay = [delay];
                    if ($.isArray(delay)) return $.inArray(level, delay) != -1;
                    if (typeof (delay) == "object" && delay.url) return delay;
                    return false;
                };                
                this.rowExpandCallbacks = {};  //行记录展开回调函数集合
            }

            return this;
        };

        rwpDatagrid.prototype._appendGridRowData = function (newData, gridRow) {  //获取表格行数据
            var opt = this.options, appendData = newData, startRowLevel = 1, parentID = null,
                targetData = this.gridRowData || [], targetRecords = this.gridRowRecords || {},
                rowID = this.gridRowID || 1, startRowIndex = rowIndex = targetData.length,  //当前表格行数
                optTree = opt.tree, childrenName = optTree ? opt.tree.childrenName : null, appendDataArr = [],  //本次新添加数据行
                that= this, parentRowID, parentRowData, rowData, updateStartIndex, indexIncrease = 0;  //初始索引递增量为0

            var dataLoad = function (data, level, pid) {    //递归加载子结点数据                                
                if (!$.isArray(data) || data.length < 1) return;
                if ($.isNumeric(pid)) {                    
                    pid = 'row' + parseInt(pid);
                }
                else {
                    pid = -1;
                }
                for (var i = 0, len = data.length; i < len; i++) {
                    rowData = data[i];
                    rowData._treeLevel = level;
                    rowData._rowIndex = rowIndex++;
                    rowData._rowID = 'row' + rowID++;
                    rowData._pID = pid;   //记录父结点ID
                    rowData._delay = opt.tree ? opt.tree.getDelay(rowData, rowData._treeLevel) : false;  //获取结点延迟加载状态                                        
                    appendDataArr.push(rowData);  
                    targetRecords[rowData._rowID] = rowData;   //赋值给字典集合
                    if (optTree && $.isArray(rowData[childrenName]) && !rowData._delay) {   //排除延迟加载的数据
                        dataLoad(rowData[opt.tree.childrenName], level + 1, rowID - 1);
                    }
                }
            }

            if (appendData) {
                appendData = appendData[opt.root];
            }
            if (optTree && gridRow) {
                parentRowID = $(gridRow).attr("rowid");
                parentRowData = targetRecords[parentRowID] ; //获取父记录数据
            }
            if (parentRowData) {
                startRowLevel = parentRowData._treeLevel + 1;   //父元素层级加1
                parentID = parentRowData._rowID.substring(3);  //得到父元素ID
                startRowIndex = rowIndex = parentRowData._rowIndex + 1;   //父元素索引号加1即为 初始行索引号
                if (!(parentRowData[childrenName] && $.isArray(parentRowData[childrenName]))) {
                    parentRowData[childrenName] = [];
                }
                if (!(appendData && $.isArray(appendData) && appendData.length > 0)) {
                    appendData = parentRowData[childrenName];    //本地延迟加载
                }
                else {
                    parentRowData[childrenName] = appendData;   //远程延迟加载
                }
            }            

            dataLoad(appendData, startRowLevel, parentID);
            if (appendDataArr.length > 0) {
                targetData = targetData.slice(0, startRowIndex).concat(appendDataArr, targetData.slice(startRowIndex)); //将 新添加的行记录 组合成新的表格行数组
                this.gridRowData = targetData;   //行记录数组
                this.gridRowRecords = targetRecords; //以行记录ID为键值的字典集合
                this.gridRowID = rowID;  //保存最后的ID
                if (parentRowData) {
                    indexIncrease = rowIndex - startRowIndex;
                    updateStartIndex = startRowIndex + appendDataArr.length;  //获取起始更新的行索引
                    that._updateRowDataIndex(updateStartIndex, indexIncrease);   //更新后续数据行的行索引值
                }                
            }  

            return appendDataArr;
        };

        rwpDatagrid.prototype._updateRowDataIndex = function(startIndex, indexIncrease) {    //更新行记录的索引
            var opt = this.options, gridRowData = this.gridRowData, rowLength = gridRowData.length;

            if ($.isNumeric(startIndex) && $.isNumeric(indexIncrease)) {
                startIndex = parseInt(startIndex);                
                indexIncrease = parseInt(indexIncrease);

                //校验起始和终止索引号
                if (startIndex >= 0 && startIndex < rowLength && indexIncrease > 0) {
                    for(var i = startIndex; i < rowLength; i++) {
                        gridRowData[i]._rowIndex += indexIncrease;  //行索引增量
                    }
                }
            }
            
            return this;            
        };

        rwpDatagrid.prototype._isGridTrHide = function (rowData) {  //判断表格行是否隐藏           
            var opt = this.options, rowRecords = this.gridRowRecords, parentRow;

            if (opt.tree) {    //树型表格时　判断是否有一个父结点不展开时 就设置隐藏                
                parentRow = rowRecords[rowData._pID];
                             
                while (parentRow) {
                    if (!parentRow._delay) {    
                        if (!opt.tree.isExpand(parentRow, parentRow._treeLevel)) return true;
                        parentRow = rowRecords[parentRow._pID];
                    }
                    else {
                        break;  //父节点延迟加载时 所有上级节点均显示
                    }
                }
            }

            return false;
        };        

        rwpDatagrid.prototype._extendCellHtml = function (header, oldContent, rowData) {  //树型层级显示           
            var content = "", opt = this.options, that = this, isParent, isExpand;

            if (opt.tree && opt.tree.columnName && opt.tree.columnName == header.columnName) {
                if (rowData._treeLevel > 1)
                    content += '<span class="rwpGridTreeSpace" style="width:' + (rowData._treeLevel - 1) * 18 + 'px;"/>';

                isParent = opt.tree.isParent(rowData);
                isExpand = rowData._delay ? false : opt.tree.isExpand(rowData, rowData._treeLevel);                
                if (isParent && isExpand)
                    content += '<span class="rwpTreeBox rwpTreeIcon rwpTreeExpandOpen"/>';
                else if (isParent)
                    content += '<span class="rwpTreeBox rwpTreeIcon rwpTreeExpandClose"/>';
                else
                    content += '<span class="rwpGridTreeSpace"/>';
                content += '<span class="rwpGridTreeContent">' + oldContent + '</span>';
            }
            else
                content = oldContent;

            return content;
        };

        rwpDatagrid.prototype._extendClickEvent = function ($toBindTrs) {  //扩充点击事件
            var $bodyTable = this.innerDom.gridBodyTable,
                gridRowData = this.gridRowData, that = this;

            $('span.rwpTreeIcon', $toBindTrs).click(function (e) {
                e.stopPropagation();                
                var $treeIcon = $(this), $rowTr = $treeIcon.parents('tr:first'),                    
                    rowID = $rowTr.attr('rowid'),  //得到行ID
                    $girdTrs = $('tr', $bodyTable),                    
                    childRowDatas = that._getChildrenRowDatas(rowID, false);  //获取直接子记录
                if ($treeIcon.hasClass('rwpTreeExpandOpen')) {   //点击后收缩
                    $treeIcon.removeClass('rwpTreeExpandOpen').addClass('rwpTreeExpandClose');
                    $.each(childRowDatas, function (i, item) {                        
                        $girdTrs.eq(item._rowIndex).hide();
                    });                    
                }
                else {   //点击后展开
                    that.rowExpandCallbacks[rowID] && that.rowExpandCallbacks[rowID].apply(window, [$rowTr[0]]);
                    $treeIcon.removeClass('rwpTreeExpandClose').addClass('rwpTreeExpandOpen');
                    $.each(childRowDatas, function (i, item) {
                        $girdTrs.eq(item._rowIndex).show();
                    });
                }
            });

            return this;
        };
        
        rwpDatagrid.prototype._getChildrenRowDatas = function (rowID, isCascade) {   //获取树型表格中所有子记录(排除延迟加载的)
            var opt = this.options, childRows = [], gridRowRecords = this.gridRowRecords, rowData,
                $bodyTable = this.innerDom.gridBodyTable, $girdTrs = $("tr", $bodyTable),
                optTree = opt.tree, childrenName = optTree ? opt.tree.childrenName : null;
            var loadChildren = function (data) {
                if (optTree && opt.tree.isParent(data) && !data._delay && $.isArray(data[childrenName])) {
                    //判断是否为父结点, 排除延迟加载
                    for (var i = 0, len = data[childrenName].length; i < len; i++) {
                        var childData = data[childrenName][i],
                            $childTr = $girdTrs.eq(childData._rowIndex);
                        childRows.push(childData);                        
                        if (isCascade !== false || $('span.rwpTreeExpandOpen', $childTr).length > 0)
                            loadChildren(childData);  //子记录展开时 也要递归得到下级子记录 切换显示状态
                    }
                }
            };
                                
            rowData = gridRowRecords[rowID];
            rowData && loadChildren(rowData);                
            
            return childRows;
        };

        rwpDatagrid.prototype._extendGridRowClick = function (rowID, isFocus) {  //扩充表格行点击事件
            var opt = this.options, that = this, $bodyTable = this.innerDom.gridBodyTable,
                $girdTrs = $("tr", $bodyTable), childRowDatas = [], selectIndex = -1, $childTr;

            if (opt.checkbox && opt.autoCheckChildren) {
                childRowDatas = that._getChildrenRowDatas(rowID);
            }

            $.each(childRowDatas, function (i, childData) {   //设置选中子记录                
                selectIndex = that._getIndexInCurrentSelected(childData._rowID);
                $childTr = $girdTrs.eq(childData._rowIndex);

                isFocus && selectIndex !== -1 && that.currentSelectedRows.splice(selectIndex, 1);  //当前页选中集合中移除该行
                !isFocus && selectIndex === -1 && that.currentSelectedRows.push(childData);   //当前页选中集合中添加该行
                
                $childTr[isFocus ?  'removeClass' : 'addClass']("rwpGridTableTrFocus");  //设置行记录是否选中
                $("input:checkbox", $childTr).prop("checked", !isFocus);   //关联子记录checkbox状态                
            });            

            opt.checkbox && opt.autoCheckChildren && that._setParentCheckStatus(rowID);   //设置父记录的选中状态

            return this;
        };
                
        rwpDatagrid.prototype._setParentCheckStatus = function (rowID) {   //递归设置父记录的状态
            var that = this, $bodyTable = this.innerDom.gridBodyTable, $girdTrs = $("tr", $bodyTable),
                childRows = [], checkCount = 0, selectIndex = -1, rowData = that.gridRowRecords[rowID], parentRow, $parentTr;

            if (rowData && rowData._pID !== -1) {
                parentRow = that.gridRowRecords[rowData._pID];
            }
            if (parentRow) {
                childRows = that._getChildrenRowDatas(parentRow._rowID);  //获取所有子记录

                $.each(childRows, function (i, item) {   //循环获取选中的子记录数                    
                    $girdTrs.eq(item._rowIndex).hasClass('rwpGridTableTrFocus') && checkCount++;
                });                    
                if (checkCount > 0 && checkCount === childRows.length) {
                    that.select(parentRow._rowID);   //选中父记录
                }
                else {
                    $parentTr = $girdTrs.eq(parentRow._rowIndex);                    
                    $parentTr.removeClass('rwpGridTableTrFocus');
                    $("input:checkbox", $parentTr).prop("checked", false);  //取消checkbox选中
                    selectIndex = that._getIndexInCurrentSelected(parentRow._rowID);
                    if (selectIndex !== -1)
                        that.currentSelectedRows.splice(selectIndex, 1);   //当前页选中集合中移除父记录                    
                }
                if (parentRow._pID !== -1)  //递归设置父记录的父记录
                    that._setParentCheckStatus(parentRow._rowID);
            }

            return this;
        };
        
        rwpDatagrid.prototype.getCheckedRows = function (isKey) {  //获取选中行集合(无复选框也适用)
            var rows = [], currentSelectedRows = this.currentSelectedRows, opt = this.options;
            if (currentSelectedRows && currentSelectedRows.length > 0) {
                $.each(currentSelectedRows, function (i, rowData) {
                    if (opt.tree && opt.tree.isParent(rowData) && !opt.includeParent) return;  //排除树表格中的父记录
                    rows.push(isKey === true ? rowData._keyValue : rowData);
                });
            }
            return rows;   //返回所有选中行
        };                

        rwpDatagrid.prototype._addDelayCallback = function (rowData) {  //添加延迟加载回调
            var opt = this.options, that = this, rowID, rowExpandCallbacks = this.rowExpandCallbacks;
            if (rowData && rowData._delay) {
                rowID = rowData._rowID;
                if (rowData._delay.url) {    //远程延迟加载
                    rowExpandCallbacks[rowID] = function(rowTr) {
                        that._loadData(rowTr, rowData._delay.url, rowData._delay.params, {
                            showLoading: function () { 
                                $("span.rwpTreeExpandClose:first", rowTr).addClass("rwpTreeExpandLoading");
                            },
                            hideLoading: function () {
                                $("span.rwpTreeExpandLoading:first", rowTr).removeClass("rwpTreeExpandLoading");
                            },
                            afterShowData: function() {
                                rowData._delay = false; //延迟加载结束
                            }
                        });
                        delete rowExpandCallbacks[rowID];   //删除属性                                                
                    };
                }
                else {
                    rowExpandCallbacks[rowID] = function (rowTr) {
                        that._loadData(rowTr);                           
                        delete rowExpandCallbacks[rowID];   //删除属性
                        rowData._delay = false;   //延迟加载结束                        
                    };
                }
            }
        };
    }
})(jQuery);