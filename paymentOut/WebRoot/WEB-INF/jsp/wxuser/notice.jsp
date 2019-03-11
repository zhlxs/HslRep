<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <title>网上车管所</title>
    <link rel="stylesheet" href="${ctx}/css/css/reset.css">
    <link rel="stylesheet" href="${ctx}/css/css/applynotice.css">
    <script src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
    <style>
        p {
            margin: 0;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div class="ui-mobile-viewport ui-overlay-c" style="padding-top: 1rem">
    <div class="s_count1" onclick="back()" >
        <span style="color: #fff;margin-left: 3px">点此返回</span>
    </div>
    <div class="bsznddiv">
        <div class="bsnrs">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td width="14" height="49" class="g_tl">&nbsp;</td>
                    <td align="center" class="g_t" style="background-color: #108EE9;border-radius: 5px 5px 0 0;">
                        <img src="${ctx}/img/bsyj_x1.jpg" height="49" style="transform: scale(0.8)">
                    </td>
                    <td width="14" class="g_tr">&nbsp;</td>
                </tr>
                <tr>
                    <td class="g_l">&nbsp;</td>
                    <td class="g_m"
                        style="background-color: #108EE9;border-radius: 0 0 5px 5px;padding: 5px;color: white;">
                        <span style="text-shadow:0 0 0 white;">
                            ${notice.mattersneedAttendtion}
                        </span>
                    </td>
                    <td class="g_r">&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="bsnrs" style="margin-top: -20px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td width="14" height="49" class="b_tl">&nbsp;</td>
                    <td align="center" class="b_t" style="background-color: #E29D39;border-radius: 5px 5px 0 0;">
                        <img src="${ctx}/img/bsyj_x2.jpg" height="49" style="transform: scale(0.8)">
                    </td>
                    <td width="14" class="b_tr">&nbsp;</td>
                </tr>
                <tr>
                    <td class="b_l">&nbsp;</td>
                    <td class="b_m"
                        style="background-color: #E29D39;border-radius: 0 0 5px 5px;padding: 5px;color: white;">
                        <span id="sqtj" style="text-shadow:0 0 0 white;">
                            
								${not empty businessconfig.sqtj?businessconfig.sqtj:'等待后台配置'}
                            
                        </span>
                    </td>
                    <td class="b_r">&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="bsnrs" style="margin-top: -20px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td width="14" height="49" class="g_tl">&nbsp;</td>
                    <td align="center" class="g_t" style="background-color: #108EEA;border-radius: 5px 5px 0 0;">
                        <img src="${ctx}/img/bsyj_x3.jpg" height="49" style="transform: scale(0.8)">
                    </td>
                    <td width="14" class="g_tr">&nbsp;</td>
                </tr>
                <tr>
                    <td class="g_l">&nbsp;</td>
                    <td class="g_m"
                        style="background-color: #108EEA;border-radius: 0 0 5px 5px;padding: 5px;color: white;">
                        <span id="sqcl" style="text-shadow:0 0 0 white;">${not empty businessconfig.sqcl?businessconfig.sqcl:'等待后台配置'}</span>
                    </td>
                    <td class="g_r">&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="bsnrs" style="margin-top: -20px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td width="14" height="49" class="b_tl">&nbsp;</td>
                    <td align="center" class="b_t" style="background-color: #E29D39;border-radius: 5px 5px 0 0;">
                        <img src="${ctx}/img/bsyj_x4.jpg" height="49" style="transform: scale(0.8)">
                    </td>
                    <td width="14" class="b_tr">&nbsp;</td>
                </tr>
                <tr>
                    <td class="b_l">&nbsp;</td>
                    <td class="b_m"
                        style="background-color: #E29D39;border-radius: 0 0 5px 5px;padding: 5px;color: white;">
                                <span style="text-shadow:0 0 0 white;">
                                    <p>${notice.businessNotice}</p>
                                </span>
                    </td>
                    <td class="b_r">&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="bsnrs" style="margin-top: -20px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td width="14" height="49" class="g_tl">&nbsp;</td>
                    <td align="center" class="g_t" style="background-color: #108EEA;border-radius: 5px 5px 0 0;">
                        <img src="${ctx}/img/bsyj_x5.jpg" height="49" style="transform: scale(0.8)">
                    </td>
                    <td width="14" class="g_tr">&nbsp;</td>
                </tr>
                <tr>
                    <td class="g_l">&nbsp;</td>
                    <td class="g_m"
                        style="background-color: #108EEA;border-radius: 0 0 5px 5px;padding: 5px;color: white;">
                                <span style="text-shadow:0 0 0 white;">
                                    <span size="2">5个工作日内作出受理或不受理的决定，受理后12个工作日内办结。</span>
                                </span>
                    </td>
                    <td class="g_r">&nbsp;</td>
                </tr>
                <tr>
                    <td height="20" class="g_bl">&nbsp;</td>
                    <td class="g_b">&nbsp;</td>
                    <td class="g_br">&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
	$.hideLoading();
    //var noticeId = localStorage.getItem("dataId");
    //console.log(noticeId);
   /*  var path = $("#path").val();
    $.ajax({
        url: path + "/applyKnow?serCode=${serCode}",
        type: "post",
        dataType: "json",
        success: function (result) {
            console.log(result);
            $("#sqtj").append("<font>" + result.sqtj + "</font>");
            $("#sqcl").append("<font>" + result.sqcl + "</font>");
        }
    }); */
    function back() {
        var url = '${ctx}/wx/businessAction/toApplybusiness?serCode=${serCode}&businessType=${businessType}';
        getHtml(url);
    }
</script>
</html>