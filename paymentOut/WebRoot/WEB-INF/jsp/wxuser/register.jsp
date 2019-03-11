<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>网上车管所</title>
<link rel="stylesheet" href="${ctx}/css/css/reset.css">
<link rel="stylesheet" href="${ctx}/css/css/register.css">
<link rel="stylesheet" href="${ctx}/css/css/weui.css">
<link rel="stylesheet" href="${ctx}/css/css/jquery-weui.css">
<script src="${ctx}/js/js/jquery-2.1.4.js"></script>
<script src="${ctx}/js/js/jquery-weui.js"></script>
<script>
	    <%RegisterUser weUserinfo = (RegisterUser) request.getSession()
					.getAttribute("userInfo");%>
	    var $id = "<%=weUserinfo.getId()%>";
        function IdentityCodeValid(code) {
            var city = {
                11: "北京",
                12: "天津",
                13: "河北",
                14: "山西",
                15: "内蒙古",
                21: "辽宁",
                22: "吉林",
                23: "黑龙江 ",
                31: "上海",
                32: "江苏",
                33: "浙江",
                34: "安徽",
                35: "福建",
                36: "江西",
                37: "山东",
                41: "河南",
                42: "湖北 ",
                43: "湖南",
                44: "广东",
                45: "广西",
                46: "海南",
                50: "重庆",
                51: "四川",
                52: "贵州",
                53: "云南",
                54: "西藏 ",
                61: "陕西",
                62: "甘肃",
                63: "青海",
                64: "宁夏",
                65: "新疆",
                71: "台湾",
                81: "香港",
                82: "澳门",
                91: "国外 "
            };
            var tip = "";
            var pass = true;
            if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
            	console.log(1);
                tip = "身份证号填写错误";
                pass = false;
            }
            else if (!city[code.substr(0, 2)]) {
            	console.log(2);
                tip = "身份证号填写错误";
                pass = false;
            }
            else {
                //18位身份证需要验证最后一位校验位
                if (code.length == 18) {
                	console.log(3);
                    code = code.split('');
                    //∑(ai×Wi)(mod 11)
                    //加权因子
                    var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                    //校验位
                    var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                    var sum = 0;
                    var ai = 0;
                    var wi = 0;
                    for (var i = 0; i < 17; i++) {
                        ai = code[i];
                        wi = factor[i];
                        sum += ai * wi;
                    }
                    var last = parity[sum % 11];
                    if (parity[sum % 11] != code[17]) {
                    	console.log(4);
                        tip = "身份证号填写错误";
                        pass = false;
                    }
                }
            }
            if (!pass) alert(tip);
            return pass;
        }

        function checkPhone(phone) {
            if (!(/^1[345789]\d{9}$/.test(phone))) {
                //alert("手机号码有误，请重填");
                return false;
            } else {
                return true;
            }
        }
        $(function () {
            //var _telnumber = $("#telnumber").val();
            //var _username = $("#username").val();
            //var _useridcard = $("#useridcard").val();
            //console.log($("#telnumber").val());
           // console.log(checkPhone($("#telnumber").val()));
            $("#showTooltips").click(function () {
                //验证姓名，身份证号，验证码
                if (!$("#username").val()) {
                    $.alert({
                    	  title: '提示',
                    	  text: '请填写姓名',
                    	  onOK: function () {
                    	    //点击确认
                    	  }
                    	});
                    return false;
                }
                if (!IdentityCodeValid($("#useridcard").val())) {
                    $.alert({
                  	  title: '提示',
                  	  text: '请填写有效的身份证号',
                  	  onOK: function () {
                  	    //点击确认
                  	  }
                  	});
                    return false;
                }
                if (checkPhone($("#telNumber").val()) == false){
                    $.alert({
                    	  title: '提示',
                    	  text: '手机号码无效，请核对后重试。',
                    	  onOK: function () {
                    	    //点击确认
                    	  }
                    	});
                    return false;
                }
                //ajax提交
                var ajaxData = '&userid=' + $id
                    + '&name=' + $("#username").val()
                    + '&card=' + $("#useridcard").val()
                    + '&phone=' + $("#telNumber").val();
                $.ajax({
                    type: 'POST',
                    //保存接口
                    url: '${ctx}/wxUserAction/saveUserInfo',
                    data: ajaxData,
                    dataType: 'json',
                    success: function (data) {
                    	$.showLoading();
                        if (data.stateType == 0) {
                                $.alert({
                              	  title: '提示',
                              	  text: ' ' + data.stateMsg + ' ',
                              	  onOK: function () {
                              	    //点击确认
                              	    getHtml('${ctx}/wxUserAction/usercenter');
                              	  }
                              	});
                                $.hideLoading();
                        } else {
                        	$.alert({
                            	  title: '提示',
                            	  text: ' ' + data.stateMsg + ' ',
                            	  onOK: function () {
                            	    //点击确认
                            	  }
                            	});
                        	$.hideLoading();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    	$.hideLoading();
                    	$.alert({
                        	  title: '提示',
                        	  text: '注册失败:程序异常',
                        	  onOK: function () {
                        	    //点击确认
                        	  }
                        	});
                    }
                });
            });
        });
    </script>
</head>
<body>
	<div>
		<div class="hzdb-left" style="margin-top: .5rem">
			<span style="color: #8D8D8D;">注册</span>
		</div>
		<!--<div class="hzdb-right">用户绑定</div>-->
	</div>
	<div>
		<div class="weui-cells weui-cells_form" style="margin-top: .5rem">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">姓名</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="username" type="text"
						placeholder="请输入姓名" value="">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">身份证号码</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="useridcard" type="text"
						placeholder="请输入身份证号码" value="">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">手机号码</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="telNumber" type="text"
						placeholder="请输入手机号码" value="">
				</div>
			</div>
		</div>
		<div class="weui-btn-area">
			<a style="background: #118EEA;: #118EEA"
				class="weui-btn weui-btn_primary" href="javascript:"
				id="showTooltips">确定</a>
		</div>
	</div>
</body>
</html>