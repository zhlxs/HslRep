/**
* @license                                     
* 弹窗选择 for 牛发发
* flyofsky@163.com
*
* Since: 2012/08/09
*/
; (function ($, window, document, undefined) {    

    var _split = $.rwpUI.split;

    var pluginHelper = $.rwpUI.pluginHelper = $.rwpUI.pluginHelper || {};

    pluginHelper.gridIsSelectedFun = function (keyName, rowKeys) {  //生成 判断表格行是否选中的函数
        return function (rowData) {
            var keyValue = rowData[keyName];
            if (rowKeys !== undefined && rowKeys !== null &&
                keyValue !== undefined && keyValue !== null
                && rowKeys.indexOf(_split + keyValue + _split) >= 0) {
                return true;
            }
            return false;
        };
    };

    pluginHelper.getDialogGrid = function (dialog) {   //获取弹窗中的表格对象
        var dialogManager = dialog || $.rwpUI.Dialog.focus;  //获取最顶层Dialog
        var dialogFrame, gridManager, parentDom;

        if (dialogManager) {
            dialogFrame = dialogManager.dom.DialogFrame;
            if (dialogFrame)
                parentDom = dialogFrame.document;  //获取iframe窗口的docuemnt
            else
                parentDom = dialogManager.dom.DialogContent;
            
            $('.rwpGridTable:visible', parentDom).each(function (i, item) {
                gridManager = $(item).data('rwpUIDatagrid');
                if (gridManager)
                    return false;  //结束循环
            });
        }          

        return gridManager;
    };

    /**
    * 选择确定
    * @item  	{Object}	参数对象
    * @dialog	{Object}	弹窗对象
    */
    var selectContactOK = function (item, dialog) {
        var node, value = '', text = '',
            dialogGrid = pluginHelper.getDialogGrid(dialog);

        if (item.selectFun && typeof (item.selectFun) == 'function')
            node = item.selectFun.apply(window, [dialog]);
        else {
            if (dialogGrid)
                node = dialogGrid.getCheckedRows();
        }
        if (!node) return;        
        if ($.isArray(node)) {
            for (var i = 0; i < node.length; i++) {
                if (i > 0) {
                    value += _split;
                    text += _split;
                }
                value += node[i][item.valueField];
                text += node[i][item.textField];
            }
        }
        else {
            value = node[item.valueField];
            text = node[item.textField];
        }

        if (item.yesFun) {
            if (typeof item.yesFun == 'function') {
                item.yesFun.call(value, node, value, text);
            }
        }
        else {
            $("#" + item.valueDomID).val(value);
            $("#" + item.textDomID).val(text);
        }
        dialog.close();
    },
    /**
    * 选择取消
    * @item  	{Object}	参数对象
    * @dialog	{Object}	弹窗对象
    */
    selectContactCancel = function (item, dialog) {
        dialog.close();
    };

    /**
    * 表格弹窗选择
    * @title	{String}	窗口标题
    * @url	    {String}	窗口地址    
    * @valuefield	{String}	值字段ID
    * @textfield	{String}	文本字段ID
    * @valuedomid	{String}	值DomID
    * @textdomid	{String}	文本DomID
    * @selectfun	{Function}	获取选中值的函数
    * @yesfun	{Function}	成功函数
    * @width	{Int}	    窗口宽度
    * @height	{Int}	    窗口高度
    * @return	{Boolean}
    */
    pluginHelper.selectContact = function (title, url, valuefield, textfield, valuedomid, textdomid, selectfun, yesfun, width, height) {
        $.rwpUI.open({
            title: title,
            width: width,
            height: height,
            url: url,
            buttons: [
                { text: '确定', valueField: valuefield, textField: textfield, valueDomID: valuedomid, textDomID: textdomid, selectFun: selectfun, yesFun: yesfun, onclick: selectContactOK },
                { text: '取消', onclick: selectContactCancel }
            ]
        });
        return false;
    };

})(jQuery, window, document);