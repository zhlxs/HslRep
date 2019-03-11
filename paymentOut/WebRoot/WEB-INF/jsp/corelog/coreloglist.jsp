<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
    //日期插件
    Date.prototype.Format = function(fmt) { //author: meizz
        var o = {
            "M+" : this.getMonth() + 1, //月份
            "d+" : this.getDate(), //日
            "h+" : this.getHours(), //小时
            "m+" : this.getMinutes(), //分
            "s+" : this.getSeconds(), //秒
            "q+" : Math.floor((this.getMonth() + 3) / 3), //季度
            "S" : this.getMilliseconds()
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        for ( var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1,
                    (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
                        .substr(("" + o[k]).length)));
        return fmt;
    };
    //默认显示当天时间段
    $(function() {
        var date = new Date().Format("yyyy-MM-dd");
        $("#recordTimeBeginTimeSearch").val(date);
        $("#recordTimeEndTimeSearch").val(date);
    });
    $(function() {
        $("#coreloggrid")
            .rwpUIDatagrid(
                {
                    columns : [
                        {
                            display : '操作日志',
                            width : 120,
                            render : function(rowdata) {
                                var html = '';
                                html += '<a class="grid_row_tool" href="${ctx}/logController/logDetial?id='
                                    + rowdata.ID
                                    + '" rwpOpts="{ ispage: true }">详情</a>';
                                return html;
                            }
                        }, {
                            display : '操作日志ID',
                            name : 'ID',
                            isHide : true,
                            primarykey : true
                        }, {
                            display : '操作人ID',
                            name : 'userID',
                            isHide : true
                        }, {
                            display : '操作人',
                            name : 'fullName',
                            width : 120
                        }, {
                            display : '部门',
                            /* name : 'deptName', */
                            width : 120,
                            render : function(data){
                            	if(data.userID == 1){
                            		return "超级管理员";
                            	}else{
                            		return data.user.dept.deptName;
                            	}
                            }
                        }, {
                            display : '操作动作',
                            name : 'actionName',
                            width : 120
                        }, {
                            display : 'IP',
                            name : 'recordIP',
                            width : 140
                        }, {
                            display : '操作时间',
                            name : 'recordTime',
                            width : 180
                        } ],
                    toolbar : rwpTemp.corelogbaritems,
                    pageSize : 10,
                    autoCheckChildren : false,
                    checkbox : false,
                    dateFormat : 'yyyy-MM-dd hh:mm:ss',
                    tree : {
                        columnName : 'id'
                    },
                    url : '${ctx}/logController/logJson',
                    height : '99%',
                    onAfterShowData : function() {
                        rwpListLayout.gridRowclick('coreloggrid')
                    }
                });
        $('input[name=recordTimeSearch]').rwpUIDateinput();

        $("#searform").rwpUIForm({
            isSearch : true
        }); //应用查询form表单
        $("#seachbtn").click(function() {
            rwpListLayout.searchGrid('searform');
        });
        $("#seachreset").click(function() {
            $('#searform')[0].reset();
        });
    });
</script>

<div class="searchBar">
    <form id="searform">
        <dl style="width: 286px;">
            <dt class="labelWidth" style="width: 75px;">部门：</dt>
            <dd class="inputWidth">
                <input name="deptNameSearch" type="text" id="deptNameSearch"
                       query-options="{ 'fieldName': 'd.deptName', 'whereField':'like', 'fieldType':'String' }" />
            </dd>
        </dl>
        <dl style="width: 286px;">
            <dt class="labelWidth" style="width: 75px;">操作人：</dt>
            <dd class="inputWidth">
                <input name="fullNameSearch" type="text" id="fullNameSearch"
                       query-options="{ 'fieldName': 'u.fullName', 'whereField':'like', 'fieldType':'String' }" />
            </dd>
        </dl>
        <dl style="width: 286px;">
            <dt class="labelWidth" style="width: 75px;">操作动作：</dt>
            <dd class="inputWidth">
                <input name="actionNameSearch" type="text" id="actionNameSearch"
                       query-options="{ 'fieldName': 'l.actionName', 'whereField':'like', 'fieldType':'String' }" />
            </dd>
        </dl>
        <div>
            <dl style="width: 511px;">
                <dt class="labelWidth" style="width: 75px;">时间：</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeBeginTimeSearch"
                           query-options="{ 'fieldName': 'l.recordTime', 'whereField':'>=', 'fieldType':'DateTime' }" />
                </dd>
                <dt class="labelWidth">至</dt>
                <dd class="inputWidth">
                    <input name="recordTimeSearch" type="text"
                           id="recordTimeEndTimeSearch"
                           query-options="{ 'fieldName': 'l.recordTime', 'whereField':'<=', 'fieldType':'DateTime' }" />
                </dd>
            </dl>
        </div>
        <dl style="width: 144px;">
            <dd class="inputWidth">
                <a id="seachbtn" class="rwpButton">查找</a>
            </dd>
            <dd class="inputWidth">
                <a id="seachreset" class="rwpButton">重置</a>
            </dd>
        </dl>
    </form>
</div>
<div class="tablepanel">
    <div id="coreloggrid"></div>
</div>