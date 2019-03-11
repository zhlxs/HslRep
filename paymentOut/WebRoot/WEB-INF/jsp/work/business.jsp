<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<!--<meta name="apple-mobile-web-app-capable" content="yes">-->
<meta http-equiv="Cache-Control" content="no-cache">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>网上车管所</title>
<link rel="stylesheet" href="${ctx}/css/css/reset.css">
<link rel="stylesheet" href="${ctx}/css/css/applybusiness.css">
<link rel="stylesheet" href="${ctx}/css/css/weui.css">
<link rel="stylesheet" href="${ctx}/css/css/jquery-weui.css">
<script src="${ctx}/js/js/jquery-2.1.4.js"></script>
<script src="${ctx}/js/js/jquery-weui.js"></script>
</head>
<body>
	<div class="hzdb" id="sqcx">
		<div class="hzdb-left" style="width: 40px;">
			<a href="javascript:;" onclick="backindex()" style="color: white;">
				<img src="${ctx}/img/aback.png">
			</a>
		</div>
		<div
			style="float: right;width: 76px;margin-top: -2px;margin-right:10px;">
			<button class="blbt">
				<a href="javascript:;"
					onclick="getHtml('/wxhz/view/work/applynotice.html')">办理须知</a>
			</button>
		</div>
	</div>

	<div class="weui-cells" style="width: 100%;margin-top:0;">
		<div class="weui-cell weui-cell_switch">
			<div class="weui-cell__bd">
				<span style="color:#999999;">温馨提示：所有数据请如实填写,带"<img
					src="${ctx}/img/redhd.png" alt=""
					style="width:8px;margin-right:5px;">"为必填项。
				</span><br> <span style="color:#e60012;">以下申报内容必须真实有效，如有虚假、瞒报，由本人自行承担一切法律后果；该平台为正式办公平台，对提供不实数据（含照片信息）的申报人，平台将锁定用户，不再提供服务。</span>
			</div>
			<div class="weui-cell__ft"></div>
		</div>
	</div>

	<form id="form1" method="post"
		action="http://localhost:8080/wxhz/weixinPageController/saveData"
		enctype="multipart/form-data">
		<!--办证人信息-->
		<div class="weui-cells">
			<div class="weui-cell weui-cell_switch"
				style="background-color: #2185df;font-size: 18px;color: white;font-weight: 900;letter-spacing: 2px;">
				<div class="weui-cell__bd wbzt" style="padding: 8px 0;">
					<span style="margin-left: -10px;">&nbsp;填表信息&nbsp;</span>
				</div>
				<div class="weui-cell__ft"></div>
			</div>
		</div>
		<article class="colF">
			<div class='iptbox idCodeBox r-idcodebox r-idcodeDiv bort0'>
				<label class='username'
					style="background: url(${pageContext.request.contextPath}/img/redhd.png) no-repeat 55% 50%;"></label>
				<input id='idName' name='idName' value='${weUserinfo.username}'
					class='r-photonum r-pleaseFillNum' placeholder='申请姓名'
					maxlength='20'>
			</div>
			<div class='idCodeBox iptbox r-idNumDiv bor0'>
				<label class='password'
					style="background: url(${pageContext.request.contextPath}/img/redhd.png) no-repeat 55% 50%;"></label>
				<input type='number' id='phone' value='${weUserinfo.phoneNumber}'
					name='phone'
					onkeypress='return event.keyCode&gt;=48&amp;&amp;event.keyCode&lt;=57'
					class='r-validcode r-pleaseFillNum' placeholder='申请人手机号'
					maxlength='11'>
			</div>
		</article>

		<div class="weui-cells">
			<div class="weui-cell weui-cell_switch" id="sbcl"
				style="background-color: #2185df;font-size: 18px;color: white;font-weight: 900;letter-spacing: 2px;">
				<div class="weui-cell__bd wbzt" style="padding: 8px 0;">
					<span style="margin-left: -10px;">&nbsp;申报材料<span
						style="color:#efe552;"></span>&nbsp;
					</span>
				</div>
				<div class="weui-cell__ft"></div>
			</div>
			<div id="allnum" style="border-top: 1px solid #D9D9D9;"></div>
		</div>

		<div class="weui-gallery" id="gallery"
			style="z-index:9999;height: 90%;">
			<span class="weui-gallery__img" id="galleryImg"></span>
			<div class="weui-gallery__opr">
				<a href="javascript:" class="weui-gallery__del"> <i
					class="weui-icon-delete weui-icon_gallery-delete"></i>
				</a>
			</div>
		</div>
		<!--隐藏域部分-->
		<input type="hidden" name="hide_acceptancedeptid"
			id="hide_acceptancedeptid" value="44">
		<!--受理单位-->
		<!--<input type="hidden" name="hide_acceptancedeptid" id="hide_businessconfigid" value="2">&lt;!&ndash;业务流程&ndash;&gt;-->
		<input type="hidden" name="hide_applytypeid" id="hide_applytypeid"
			value="1">
		<!--业务申请类型-->
		<input type="hidden" name="hide_applyrecordid" id="hide_applyrecordid"
			value="0">
		<!--申请业务记录-->
		<!--<input type="hidden" name="hide_businesscode" id="hide_businesscode" value="0000100001">&lt;!&ndash;业务流程编码&ndash;&gt;-->
		<!--隐藏域部分-->
		<div style="width:100%">
			<div
				style="float:left;width:50%;margin-bottom: 20px;margin-left: 25%;">
				<a href="javascript:void(0);" class="weui-btn weui-btn_ls"
					style="margin-top: 30px;width: 80%;" onclick="putin('tj')">开始预约</a>
			</div>
		</div>
	</form>
	<script>
	<%
		RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
	%>
	var $username = "<%=weUserinfo.getUsername()%>";
	var $phone = "<%=weUserinfo.getPhoneNumber()%>";
	$("#idName").val($username);
	$("#phone").val($phone);
	var isregister = false;
	if ($("#idName").val() && $("#phone").val()) {
        isregister = true;
    }
	console.log(isregister);
    //var dataId = localStorage.getItem("dataId");
    //console.log(dataId);
    localStorage.clear();
    var sblxid;
    var businessconfigstrid;
    $.ajax({
        type:'post',
        url:'${ctx}/wx/businessAction/businessconfigTypes?serCode='+${serCode},
        //+id,
        dataType:'json',
        success:function (result) {
            //console.log(result);
            $('#form1').append("<input type='hidden' name='hide_acceptancedeptid' id='hide_businessconfigid' value='"+result[0].businessconfigid+"'>");
            //选项卡
            $.each(result,function (index,value) {
                    console.log(value);
                //标题
//                    $('#sqcx').append("<div class='hzdb-right' style='width: 55%;font-size: 16px;'>"+value.bconfigstrcname+"</div>");
                $('#form1').append("<input type='hidden' name='hide_businesscode' id='hide_businesscode' value='"+value.orderCode+"'>");
                $('.weui-cells_radio').append("<label class='weui-cell weui-check__label' for='label_"+index+"'>"+
                    "<div class='weui-cell__bd wbzt'>"+
                    "<p>"+value.bconfigstrname+"</p>"+
                    "</div>"+
                    "<div class='weui-cell__ft applytypeclass'>"+
                    "<input type='radio' class='weui-check' bcname='"+value.bconfigstrname+"' value='"+value.serCode+"' name='radio1' id='label_"+index+"'>"+
                    "<span class='weui-icon-checked'></span>"+
                    "</div>"+
                    "</label>");
                $('#label_0').attr('checked','checked');
//                    console.log(value.weBusinessconfigstrs);
//                    console.log(value);
                $("#label_"+index).click(function () {
                    var val= value.weBusinessconfigstrs;
//                       console.log(val.bconfigstrname);
                    $("#allnum").empty();
                    $.each(val,function (index,value) {
//                           console.log(value.wePhotosample.photosamplepath);
                        sblxid = value.applytypeid;
//                           console.log(value);
                        businessconfigstrid = value.id;
                        var str = "http://localhost:8080/wxhz/"+value.wePhotosample.photosamplepath;
                        var ctr = value.bconfigstrname.replace("《","");
                        var ctr1 = ctr.replace("》","");
                        $("#allnum").append("<div id='"+index+"' style='display: block;'>"+
                            "<div class='weui-cell weui-cell_switch'>"+
                            "<div class='weui-cell__hd wbzt'>"+
                            "<img id='spe_"+index+"' src='../img/redhd.png' alt='' style='width:8px;margin-right:5px;'>"+
                            "</div>"+
                            "<div class='weui-cell__bd' style='padding: 8px 2px;'>"+value.bconfigstrname+"</div>"+
                            "<div class='weui-cell__ft'></div>"+
                            "</div>"+
                            "<div class='weui-cell'>"+
                            "<div class='weui-cell__hd wbzt'>"+
                            "<img onclick='getphotostr(this)' src='"+str+"' alt='' style='width:50px;margin-right:5px;display:block;'>"+
                            "</div>"+
                            "<div class='weui-cell__bd'>"+
                            "<p>"+ctr1+"<span style='color:red'></span></p>"+
                            "</div>"+
                            "<div class='weui-cell__ft'></div>"+
                            "</div>"+
                            "<div class='weui-cell'>"+
                            "<div class='weui-cell__bd wbzt'>"+
                            "<div class='weui-uploader'>"+
                            "<div class='weui-uploader__bd'>"+
                            "<ul class='weui-uploader__files' id='uploaderFiles_"+(index+1)+"'></ul>"+
                            "<div class='weui-uploader__input-box'>"+
                            "<input id='uploaderInput_0' bconfigstrcname='"+value.bconfigstrname+"' mustfill='"+value.ismustfill+"' titlename='"+value.bconfigstrname+"' samplepathid='"+index+"' rereqid='"+(index+1)+"' onchange='imgshow(this)' class='weui-uploader__input' type='file' accept='image/*' multiple  capture='camera' value='材料上传'>"+
                            "</div>"+
                            "</div>"+
                            "</div>"+
                            "</div>"+
                            "</div>"+
                            "</div>");
                        if(value.ismustfill == false){
                            $('#spe_'+index).css({'display':'none'});
                        }
                    });
                });
            });
            //绑定click时间后，在最后主动调取一次click方法可以让页面默认展示选项内容
            $("#label_0").click();
        }
    });


    //================办理须知====================
    //办理须知弹出层
    $(".blbt").click(function () {
        //$("#blxzdialog").show();
        getHtml('/view/work/applynotice?businessconfigid=');
    });

    //点击查看样图
    function getphotostr(obj) {
        if (_photo_close_div_offset == 0) {
            _photo_close_div_offset = $("#photo_demo_close").offset().top;
        }
        _photo_close_div_margintop = -(_photo_close_div_offset - $(window).height() * 0.1 + 1);
        var src = obj.id;
        $("#tpstr").attr("src", src);
        $("#tpdialog img").load(function () {
            $("#photo_demo_close").css('margin-top', _photo_close_div_margintop);
            var margintoph = (($(".zp-dialog").height() - $(".r-close").height()) - $("#tpstr").height()) / 2;
            if (margintoph < $("#photo_demo_close").height()) { margintoph = $("#photo_demo_close").height(); }
            $("#tpstr").css('margin-top', margintoph);
        });
        $("#tpdialog").show();
        $("#photo_demo_close").show();
    }
    //关闭弹窗
    $(".r-closeImg").click(function () {
        $("#sblxDialog").hide();
        $("#iosDialog").hide();
    });
    //==============提交事件=================
    function putin(savetype) {
        if (!isregister) {
            var text = "您尚未绑定个人身份证，无法办理业务！";
            doubleDialog(text, "临时浏览", "马上绑定");
            $("#doubleOK").click(function () {
                getHtml('/wxhz/view/index/register?businessconfigID=2');
            });
            $("#doubleNO").click(function () {
                pushHistory();
            });
            pushHistory();
            var bool = false;
            setTimeout(function () {
                bool = true;
            }, 1500);
            window.addEventListener("popstate", function (e) {
                if (bool) {
                    //alert("我监听到了浏览器的返回按钮事件啦");//根据自己的需求实现自己的功能

                    backindex();
                }
            }, false);
            return false;
        } else {
            var isstop = false;
            if (isstop) {
                $('#loadingToast').fadeOut(100);
                return false;
            } else {
                var ajaxData = 'status=' + savetype + '&applyrecordid=' + $("#hide_applyrecordid").val();
                $.ajax({
                    type: 'POST',
                    //submitdata和savepicture...
                    url: 'http://localhost:8080/wxhz/weixinPageController/applySubmit?rnd=' + Math.random(),
                    data: ajaxData,
                    dataType: 'json',
                    success: function (data) {
                        $('#loadingToast').fadeOut(100);
                        if (data.stateType == 0) {
                            showMessage(data.stateMsg, 0, '/wxhz/view/matter/mymatter.html')
                        } else {
                            showMessage(data.stateMsg);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $('#loadingToast').fadeOut(100);
                        showMessage('材料提交错误。');
                    }
                });
            }
        }
    }
    function backindex() {
        var text = "确定要退出业务申请页面吗？";
        doubleDialog(text);
        $("#doubleOK").click(function () {
            getHtml('/wxhz/view/index/index.html');
        });
        $("#doubleNO").click(function () {
            pushHistory();
        });
    }

    function pushHistory() {
        var state = {
            title: "网上车管所",
            url: "/wxhz/view/index/index.html"
        };
        window.history.pushState(state, "", "#");
    }

    $(function () {
        pushHistory();
        var bool = false;
        setTimeout(function () {
            bool = true;
        }, 1500);
        window.addEventListener("popstate", function (e) {
            //alert(bool);
            if (bool) {
                //alert("我监听到了浏览器的返回按钮事件啦");//根据自己的需求实现自己的功能
                //getHtml('/wxhzweb/index/index');
                backindex();
            }

        }, false);
        if (!isregister) {
            var text = "您尚未绑定个人身份证，无法办理业务！";
            doubleDialog(text, "临时浏览", "马上绑定");
            $("#doubleOK").click(function () {
                getHtml('/wxhz/view/index/register?businessconfigID=2');
            });
            $("#doubleNO").click(function () {
            });

        } else {
            //查找未完成的流程，是否要继续
            //传入id值
            //@Model.ID
//                getsaveapply('2');


        }
        //设置办理类型
//        $("#" + $(".weui-cells .weui-cells_radio input[type='radio']:checked").val()).show();
//        //$("#sblx1").html($(".weui-cells .weui-cells_radio input[type='radio']:checked").attr("bcname"));
//        $("#hide_applytypeid").val($(".weui-cells .weui-cells_radio input[type='radio']:checked").val());
//        //切换申报类型点击事件
//        $(".applytypeclass").click(function () {
//            $('#allnum').children().each(function () {
//                $(this).hide();
//            });
//            $("#" + $(this).find("input[type='radio']:checked").val()).show();
//            $("#hide_applytypeid").val($(this).find("input[type='radio']:checked").val());
//        });
    });
</script>
</body>
</html>