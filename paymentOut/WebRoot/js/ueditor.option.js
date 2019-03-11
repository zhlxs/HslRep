var getSimpleUeditorOption = function (height, width, initContent) {
    var simpleueoptions = {
        toolbars: [
           ['undo', 'redo', '|',
               'bold', 'italic', 'underline', 'strikethrough', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|',
               'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
               'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
               'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
               'directionalityltr', 'directionalityrtl', 'indent', '|',
               'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
               'link', 'unlink', '|', 'insertimage', 'attachment', '|', 'horizontal', 'date', 'time', 'spechars', '|',
               'print', 'preview', 'searchreplace']
        ]
    };
    if (height && height === +height)
        simpleueoptions.initialFrameHeight = height;
    if (width && width === +width)
        simpleueoptions.initialFrameWidth = width;
    if (typeof (initContent) == 'string')
        simpleueoptions.initialContent = initContent;
    return simpleueoptions;
};

var getMiniUeditorOption = function (height, width, initContent) {
    var miniueoptions = {
        toolbars: [
           ['bold', 'italic', 'underline', 'strikethrough', '|',
               'fontfamily', 'fontsize', 'forecolor', 'backcolor', 'removeformat', 'formatmatch']
        ]
    };
    if (height && height === +height)
        miniueoptions.initialFrameHeight = height;
    if (width && width === +width)
        miniueoptions.initialFrameWidth = width;
    if (typeof (initContent) == 'string')
        miniueoptions.initialContent = initContent;
    return miniueoptions;
};