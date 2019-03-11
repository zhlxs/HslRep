var ocx = {
	// 身份证阅读器
	idcard : {
		"classid" : "clsid:951DABB1-127B-4753-98B3-E0FB4DE6250E",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "3000px"
			}
		}
	},
	// 指纹验证
	zw_yz : {
		"classid" : "clsid:951DABB1-127B-4753-98B3-E0FB4DE6250E",
		"width" : "840",
		"height" : "560",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "300px",
				"left" : "200px"
			}
		}
	},
	// 指纹采集
	zw_cj : {
		"classid" : "clsid:951DABB1-127B-4753-98B3-E0FB4DE6250E",
		"width" : "800",
		"height" : "600",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "220px",
				"left" : "120px",
				"font-size" : "15px"
			}
		}
	},
	// 键盘
	keyboard_tel : {
		"classid" : "clsid:FAE56A61-0129-4E69-99A0-9222B25F8B66",
		"width" : "300px",
		"height" : "45px",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "400px",
				"left" : "640px",
			}
		}
	},
	keyboard_sjr : {
		"parent" : {
			clazz : [ "mn-all" ]
		},
		"classid" : "clsid:FAE56A61-0129-4E69-99A0-9222B25F8B66",
		"width" : "300px",
		"height" : "45px",
		"div" : {
			clazz : [ "mn-normal", "mn-first" ]
		}
	},
	keyboard_sjrdh : {
		"parent" : {
			clazz : [ "mn-all" ]
		},
		"classid" : "clsid:FAE56A61-0129-4E69-99A0-9222B25F8B66",
		"width" : "300px",
		"height" : "45px",
		"div" : {
			clazz : [ "mn-normal", "mn-seccond" ]
		}
	},
	keyboard_yzbm : {
		"parent" : {
			clazz : [ "mn-all" ]
		},
		"classid" : "clsid:FAE56A61-0129-4E69-99A0-9222B25F8B66",
		"width" : "300px",
		"height" : "45px",
		"div" : {
			"position" : "absolute",
			"top" : "515px",
			"left" : "640px",
			clazz : [ "mn-normal", "mn-third" ]
		}
	},
	keyboard_4x : {
		"classid" : "clsid:FAE56A61-0129-4E69-99A0-9222B25F8B66",
		"width" : "300px",
		"height" : "45px",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "765px",
				"left" : "670px",
			}
		}
	},
	keyboard_user : {
		"classid" : "clsid:FAE56A61-0129-4E69-99A0-9222B25F8B66",
		"width" : "300px",
		"height" : "45px",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "345px",
				"left" : "535px",
			}
		}
	},
	keyboard_pwd : {
		"classid" : "clsid:FAE56A61-0129-4E69-99A0-9222B25F8B66",
		"width" : "300px",
		"height" : "45px",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "480px",
				"left" : "535px",
			}
		}
	},
	// 支付，打印
	work : {
		"classid" : "clsid:D2977554-1BB6-4B3B-9AB4-63523101DB81",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "3000px"
			}
		}
	},
	
	//单一打印
	workone : {
		"classid" : "clsid:ED49EA20-9C82-4880-9C61-189CE592F760",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "3000px"
			}
		}
	},
	
	// 单一键盘
	onebox : {
		"classid" : "clsid:FAE56A61-0129-4E69-99A0-9222B25F8B66",
		"width" : "300px",
		"height" : "45px",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "400px",
				"left" : "640px",
			}
		}
	},
	
	//1021-单一键盘
	
	oneB : {
		"classid" : "clsid:64E84146-1E08-459E-9E2B-0849827EFE1D",
		"width" : "0",
		"height" : "0",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "400px",
				"left" : "640px",
			}
		}
	},
	
	
	// 摄像头
	camera : {
		"classid" : "clsid:E54D1800-F6EF-4A0B-B4D3-B7BD05B696A3",
		"width" : "498",
		"height" : "314",
		"div" : {
			"css" : {
				"position" : "absolute",
				// "top" : "300px",
				// "left" : "525px",
				"left" : "60%",
				"top" : "50%",
				"transform" : " translate(-50%, -50%)"

			}
		}

	},
	// 人像比对
	face_match : {
		"classid" : "clsid:21E18998-4092-4836-90F8-FD4676E39EE4",
		"div" : {
			"css" : {
				"position" : "absolute",
				"top" : "3000px"
			}
		}
	},
	showObject : function(id) {
		// 确保只有一个ocx对象
		ocx.removeObject(id);
		var object = ocx[id];
		if (object) {
			var _div = document.createElement("div");
			_div.id = "div_" + id;
			var div = object.div;
			if (div) {
				if (div.css) {
					$(_div).css(div.css);
				}
				if (div.clazz) {
					$.each(div.clazz, function(i, o) {
						$(_div).addClass(o);
					})
				}
			}
			var _obj = document.createElement("object");
			_obj.id = id;
			var $_obj = $(_obj);
			$.each(object, function(key, value) {
				if (key != "div" && key != "parent") {
					$_obj.attr(key, value);
				}
			})
			_div.appendChild(_obj);
			var body = document.getElementsByTagName("body")[0];
			var parent = object.parent;
			if (parent) {
				var div_parent = document.createElement("div");
				var flag = true;
				if (parent.clazz) {
					$.each(parent.clazz, function(i, o) {
						if ($("." + o).length > 0) {
							flag = false;
							div_parent = $("." + o)[0];
						} else {
							$(div_parent).addClass(o);
						}
					})
				}
				div_parent.appendChild(_div);
				if (flag) {
					body.appendChild(div_parent);
				}
			} else {
				body.appendChild(_div);
			}
			return _obj;
		}
	},
	removeObject : function(id) {
		var $_div = $("#div_" + id);
		if ($_div.length > 0) {
			$_div.hide();
			var $_obj = $("#" + id);
			if ($_obj.length > 0) {
				try {
					if (id == "idcard") {
						$_obj[0].closeReadIDCard();
					}
					if (id.indexOf("keyboard") != -1
							|| id.indexOf("work") != -1
							|| id.indexOf("camera") != -1||id.indexOf("workone")!=-1) {
						$_obj[0].closeControl();
					}
				} catch (e) {
					// alert(e.message + "关闭控件异常");
				}
			}
			$_div.remove();
		}
	},
	removeAllObject : function() {
		var ocx_obj = ocx;
		$.each(ocx_obj, function(name, obj) {
			if (!$.isFunction(obj)) {
				ocx.removeObject(name);
			}
		})
	},
	removeOtherObject : function() {
		var ocx_obj = ocx;
		$.each(ocx_obj, function(name, obj) {
			if (!$.isFunction(obj)) {
				if (name.indexOf("keyboard") != -1) {
					keyboardController.hide(name);
				} else {
					ocx.removeObject(name);
				}
			}
		})
	}
}

//身份证阅读器
var IdCardReader = {
	// 回调函数
	callback : function(value) {
		try {
			var dr = JSON.parse(value);
			if (dr.stateType == 0) {
				var idAvailEndDate = dr.stateValue.IDAvailEndDate;
				var checkResult = IdCardReader.checkDate(idAvailEndDate);
				if (checkResult == "") {
					var idCard = dr.stateValue.IDNumber;
					$.post("sfz/saveidcard", {
						"sqr_sfz" : idCard}, function(data) {
						if (data.stateType == 0) {
							$.Post("next", {}, function(data) {
								$(".main").html(data);
							});
						} else {
							$.warnPopup("身份证获取失败,请重新获取");
						}
					});
				} else {
					$.warnPopup("您的身份证暂不支持到期换领");
				}
			} else {
				// alert(dr.stateMsg + "_这是失败消息");// 读卡错误
				// 在读卡错误的时候重新读卡
				// IdCardReader.restart();
				$.warnPopup("读卡失败,请重新读卡");
			}
		} catch (e) {
			$.warnPopup("身份证扫描失败");
		}
	},
	// 启动身份证阅读
	start : function() {
		try {
			var obj = ocx.showObject("idcard");
			obj.idCardControlInit(window, "IdCardReader.callback");
			obj.readIDCard();
		} catch (err) {
			$.warnPopup("身份证阅读器启动失败");
			IdCardReader.stop();
		}
	},
	// 结束身份证阅读
	stop : function() {
		ocx.removeObject("idcard");
	},
	// 再次启动
	restart : function() {
		var obj = document.getElementById("idcard");
		if (obj) {
			obj.readIDCard();
		} else {
			IdCardReader.start();
		}
	},
	checkDate : function(date) {
		// 有效期到期时间
		var endDate = new Date(date.substring(0, 4),
				(date.substring(4, 6) - 1), date.substring(6, 8));
		// 可办理时间
		var passDate = new Date(endDate.valueOf() - ini.init("date") * 24 * 60
				* 60 * 1000);
		// 当前时间
		var nowDate = new Date();
		var days = (passDate.getTime() - nowDate.getTime()) / 24 / 60 / 60
				/ 1000;
		if (days >= 0) {
			var passStr = (passDate.getFullYear()) + "年"
					+ (passDate.getMonth() + 1) + "月" + (passDate.getDate())
					+ "日";
			return passStr;
		} else {
			return "";
		}
	}
}

// 核验指纹
var ZhiWenCheck = {
	callback : function(value) {
		try {
			var dr = JSON.parse(value);
			if (dr.stateType == 0 && dr.stateValue != null) {
				$.Post("next", {}, function(data) {
					ZhiWenCheck.stop();
					$(".main").html(data);
				})
			} else {
				// alert(dr.stateMsg + "_这是失败消息");
				$.warnPopup("指纹核验失败");
				// ZhiWenCheck.show();
			}
		} catch (e) {
			ZhiWenCheck.stop();
			$.warnPopup("指纹核验失败");
		}
	},
	start : function(str) {
		try {
			var obj = ocx.showObject("zw_yz");
			obj.fingerConfirmControlInit(str, window, "ZhiWenCheck.callback");
			obj.fingerConfirmStart(ini.init("zwhyxsd"),
					ini.init("right") == 1 ? true : false,
					ini.init("left") == 1 ? true : false, ini.init("zwhyzlz"),
					ini.init("zwhycs"));
		} catch (err) {
			ZhiWenCheck.stop();
		}
	},
	stop : function() {
		ocx.removeObject("zw_yz");
	},
	show : function() {
		$.Post("sfz/getzwinfo", {}, function(data) {
			var result = data;
			if (result.stateType == 0) {
				var value = result.stateValue;
				var arr = value.toString().split(",");
				for ( var i = 0; i < arr.length; i++) {
					var temp = arr[i];
					arr[i] = "\'" + temp + "\'";
				}
				var str = "[" + arr.join(",") + "]";
				ZhiWenCheck.start(str);
			} else {
				$.warnPopup("指纹数据获取失败");
			}
		}, "json")
	}
}

// 指纹采集
var ZhiWenGather = {
	callback : function(value) {
		 //alert(value);// "采集取消"
		try {
			var dr = JSON.parse(value);
			//alert(dr.stateType);
			if (dr.stateType == 0) {
				if (dr.stateValue) {
					var fingerInfos = JSON.stringify(dr.stateValue);
					//alert(fingerInfos);
					$.post("sfz/savezwinfo", {
						"fingerInfos" : fingerInfos
					}, function(data) {
						if (data.stateType == 0) {
							$.Post("next", {}, function(data) {
								ZhiWenGather.stop();
								$(".main").html(data);
							})
						}
					});
				} else {
					$.warnPopup("指纹采集失败");
				}
			} else {
				$.warnPopup("确认取消采集");
			}
		} catch (e) {
			$.warnPopup("指纹采集失败");
		}
	},
	start : function() {
		try {
			var obj = ocx.showObject("zw_cj");
			obj.fingerCollectControlInit(ini.init("right") == 1 ? true : false,
					ini.init("left") == 1 ? true : false, ini.init("zwcjzlz"),
					ini.init("zwcjcs"), ini.init("zwcjxsd"), window,
					"ZhiWenGather.callback");
		} catch (err) {
			$.warnPopup("指纹采集器初始化失败");
		}
	},
	stop : function() {
		ocx.removeObject("zw_cj");
	}
}

/*var PayController = {
	callback : function(data) {
		// alert(data);
		var dr = JSON.parse(data);
		PayController.stop();
		if (dr.stateType == 0) {
			$.post("yw/paysuccess", {}, function(data) {
				if (data.stateType == 0) {
					if (data.stateType == 0) {
						$.post("sfz/submit", {}, function(data) {
							if (data.stateType == 0) {
								PrintController.show();
							} else {
								$.warnPopup(data.stateMsg);
							}
						})
					}
				}
			})
		} else {
			$.alertPopup(dr.stateMsg + ",请联系管理员");
		}
		// PrintController.show();
	},
	start : function() {
		try {
			var obj = ocx.showObject("work");
			$.Post("sfz/sbjpay", {}, function(data) {
				if (data.stateType == 0) {
					var money = data.stateValue.toString();
					var text = "本次申请费用:" + money + "元<br>请准备好纸币,谢谢";
					$.popup(text, [
							{
								name : "开始支付",
								callback : function() {
									obj.CashCodeInit(window,
											"PayController.callback", money);
								}
							}, {
								name : "取消",
								callback : function() {
									$("#tck").remove();
								}
							} ], "blue");
				}
			});
		} catch (e) {
			// alert(e.message);
			$.alertPopup("收币机未连接,请联系管理员,或选择其他支付方式");
			PayController.stop();
		}
	},
	stop : function() {
		ocx.removeObject("work");
	}
}*/

var PrintController = {	
	start : function(json,zffs, idCard) {
		try {		
			var obj = ocx.showObject("work");
			if(zffs=="4")
			{
		obj.printInit(window,"PrintController.callback","ApplyPayInfo.frx", json, ini.init("dyjmc"), "0", idCard);
			}
			else if(zffs=="2")
			{
		obj.printInit(window, "PrintController.callback","ApplyPayInfo_WeChat.frx", json, ini.init("dyjmc"), "0", idCard);
			}
		} catch (err) {
			alert(err);
			PrintController.stop();
			$.warnPopup("打印机未连接,请联系管理员<br>连接打印机后可补打申请缴费表");
		}
	},
	stop : function() {
		ocx.removeObject("work");
	},
	show : function() {
		$.post("sfz/printform", {}, function(data) {
			var result = data;
			if (result.stateType == 0) {
				var xvalue = result.stateValue;
				var str = JSON.stringify(xvalue);
				var json = str.replace(/\"/g, "'");
				var zffs=result.stateMsg;  //支付方式
				PrintController.start(json,zffs, xvalue.jfsfhm);
			} else {
				$.countdownStop();
				$.warnPopup("获取打印数据失败,请联系管理员");
			}
		});
	},
		callback : function(value) {
		var jsons = JSON.parse(value);
		if (jsons.stateType == 0) {
			PrintController.stop();
			$(".ewm").hide();
		//	flowMenuUrl.next();
		//	$.okPopup("您的身份证申领已完成<br>请拿好缴费凭条<br>请刷二维码进行缴费");
		} else {
			$.warnPopup("未成功打印缴费凭条,请联系管理员<br>"+value);
		}
	}
}


var keyboardController = {
	start : function(id) {
		try {
			var obj = ocx.showObject(id);
			if (id == "keyboard_pwd") {
				obj.keyboardAllControlsInit("1");
			} else {
				obj.keyboardAllControlsInit("0");
			}
			return obj;
		} catch (e) {
			ocx.removeAllObject();
		}
	},
	text : function(id) {
		var obj = window.document.getElementById(id);
		if (obj) {
			return obj.GetInputString();
		}
	},
	show : function(id) {
		var obj = $("#" + id);
		if (obj.length > 0) {
			$("#div_" + id).show();
			return obj[0];
		} else {
			keyboardController.start(id);
		}
	},
	hide : function(id) {
		$("#div_" + id).hide();
	}
}
//单个键盘控制
var keyboardControllerOne = {
		start : function(id) {
			try {
				var obj = ocx.showObject(id);
					obj.keyboardAllControlsInit("1400","375");
				//return obj;
			} catch (e) {
				alert(e);
				ocx.removeAllObject();
			}
		},
		/*text : function(id) {
			var obj = window.document.getElementById(id);
			if (obj) {
				return obj.GetInputString();
			}
		},*/
		show : function(id) {
			var obj = $("#" + id);
			if (obj.length > 0) {
				$("#div_" + id).show();
				return obj[0];
			} else {
				keyboardControllerOne.start(id);
			}
		},
		hide : function(id) {
			$("#div_" + id).hide();
		}
	}

//拍照控制 
var cameraController = {
	callback : function(data) {
			$("#restPZ").css("display","");
		$.Post("sfz/savezpinfo", {
			"zp" : data
		}, function(data) {
			if (data.stateType == 0) {
				// $.Post("next", {}, function(data) {
				// $(".main").html(data);
				// cameraController.stop();
				// })
				$.playMusic("#startRxbd");
				if (ini.init("rxbd") == 1) {
					$.Post("sfz/checkZZ_XC", {}, function(data) {
						if (data.stateType == 0) {
							var photos = data.stateValue;
							var result = faceMatchController
									.checkZZZP_XCZP(photos[0], photos[1]);
							if (result) {
								$.playMusic("#rxbdSuccessToNext");
								//cameraController.stop();
								next=true;
								//flowMenuUrl.next();
							} else {
								$.playMusic("#rxbdFailToRestTackPhotos");
								$.alertPopup("人像比对失败,请重新拍照!");
								next=false;
							}
						}
					})
				} else {
					next=true;
				}
				
			
				$("#photoyy").hide();
			}
		})
	},
	// 开始拍照
	start : function() {
		try {
			var obj = window.document.getElementById("camera");
			if (obj) {
				// obj.StartCamera();
				obj.VideoCamera_Photos(window, "cameraController.callback");
			}
		} catch (e) {
			$.alertPopup("拍照机开启失败,请联系管理员");
			cameraController.stop();
		}
	},
	stop : function() {
		ocx.removeObject("camera");
	},
	// 关闭拍照(失效)
	close : function() {
		var obj = window.document.getElementById("camera");
		if (obj) {
			obj.StopCamera();
		}
	},
	// 拍照(失效)
	Take : function() {
		var obj = window.document.getElementById("camera");
		if (obj) {
			obj.Takepictures();
			return obj.Photo();
		}
	},
	// 重启(失效)
	reboot : function() {
		var obj = window.document.getElementById("camera");
		if (obj) {
			obj.ReStartCamera();
		}
	},
	// 显示
	show : function() {
		try {
			var obj = ocx.showObject("camera");
			return obj;
		} catch (e) {
			cameraController.stop();
		}
	}
}

var localPhotoController = {
	show : function() {
		try {
			var obj = ocx.showObject("work");
			return obj;
		} catch (e) {
			ocx.removeAllObject();
		}
	},
	image : function() {
		var obj = window.document.getElementById("work");
		if (obj) {
			try {
				var image = obj.GetBase64FromImage();
				return image;
			} catch (e) {
				alert(e);
			}
		}
	}
}
// 人像比对
var faceMatchController = {
	callback : function(data) {
		alert(data);
	},
	start : function(pic1Base64, pic2Base64) {
		try {
			var obj = ocx.showObject("face_match");
			if (obj) {
				obj.FaceMatch(pic1Base64, pic2Base64);
			}
		} catch (e) {
			$.alertPopup("人像比对失败,请重试!");
			faceMatchController.stop();
		}
	},
	stop : function() {
		ocx.removeObject("face_match");
	},
	
	//制证照片
	checkZZZP_SFZZP : function(zzzp, sfzzp) {
		try {
			var obj = ocx.showObject("face_match");
			if (obj) {
				var zzzp_sfzzp = obj.FaceMatch(zzzp, sfzzp);
				if (zzzp_sfzzp >= ini.init("zz_sfz")) {
					
					$.Post("sfz/zzxsz", {
						"zzxsz" : zzzp_sfzzp
					}, function(data) {

					})
					return true;
				} else {
					return false;
				}
			}
		} catch (e) {
			$.alertPopup("人像比对失败,请重试!");
			faceMatchController.stop();
		}
	},


//现场照片
	checkZZZP_XCZP : function(zzzp, xczp) {
		try {
			var obj = ocx.showObject("face_match");
			if (obj) {
				var zzzp_xczp = obj.FaceMatch(zzzp, xczp);
				if (zzzp_xczp >= ini.init("zz_xc")) {
					$.Post("sfz/sfzxsz", {
						"sfzxsz" : zzzp_xczp
					}, function(data) {

					})
					return true;
				} else {
					$.playMusic("#rxbdFailToRestTackPhotos");
					$.alertPopup("人像比对失败,请您重新拍照!");
					return false;
				}
			}
		} catch (e) {
			$.alertPopup("人像比对失败,请您重新拍照");
			$.playMusic("#rxbdFailToRestTackPhotos");
			$("#restPZ").removeAttr("style");
			$("#pzok").css("display","none");
			faceMatchController.stop();
		}
	}
}
// 佛山打印支持从这里开始

	//FsPrint 补打缴费单
var FsPrint = {	
		start : function(json,zffs, idCard) {
			try {		
				var obj = ocx.showObject("work");
				if(zffs=="4")
				{
			obj.printInit(window,"FsPrint.callback","ApplyPayInfo.frx", json, "HP LaserJet 400 M401 PCL 6", "0", idCard);
				}
				else if(zffs=="2")
				{
			obj.printInit(window, "FsPrint.callback","ApplyPayInfo_WeChat.frx", json, "HP LaserJet 400 M401 PCL 6", "0", idCard);
				}
			} catch (err) {
				alert(err);
				FsPrint.stop();
				
			}
		},
		stop : function() {
			ocx.removeObject("work");
		},
		show : function(BH) {
			$.post("fsywController/MackPrintJson", {"BH":BH}, function(data) {
				var result = data;
				if (result.stateType == 0) {
					var xvalue = result.stateValue;
					var str = JSON.stringify(xvalue);
					var json = str.replace(/\"/g, "'");
					var zffs=result.stateMsg;  //支付方式
					FsPrint.start(json,zffs, xvalue.jfsfhm);
				} else {
					
					
				}
			});
		},
			callback : function(value) {
			var jsons = JSON.parse(value);
			if (jsons.stateType == 0) {
				PrintController.stop();
				$(".ewm").hide();
			
			} else {
				alert("未成功打印缴费凭条,请联系管理员<br>"+value);
			}
		}
	}	
	
//批量打印
var prints = {
		 starts:function(isState,str) {
					if (document.all.kjs.object == null) {
						$.rwpUI.warn("控件不存在，您还不能使用此功能！");
						return;
					}
					var conn="";
					if(isState=="info"&&str!=""){
					conn="where slh="+"{"+str+"}";
					}
					else if(isState=="printAll"&&str==""){
						conn="where zfzt={1}";
					}else if(isState=="printIsPay"&&str==""){
						conn="";
					}
					else if(isState=="printNoPay"&&str==""){
						conn="where zfzt={0}";
					}
					//现在走这一段
					else if(isState=="printSel"&&str==""){
						var show=0;
						var startDate=$("#recordTimeBeginTimeSearch").val();
						var endDate=$("#recordTimeEndTimeSearch").val();
						var dateStartSub=startDate.substring(0,4)+"年"+startDate.substring(5,7)+"月"+startDate.substring(8,10)+"日";
						var dateEndSub=endDate.substring(0,4)+"年"+endDate.substring(5,7)+"月"+endDate.substring(8,10)+"日";

						var xms=$("#xm").val();
						var sfz=$("#gmsfhm").val();
						var slh=$("#slh").val();
						var objs=$("#searchObject").val();
						var ojs=objs.substring(0,4);//取状态
						var oj=objs.substring(5,6);//取尾数
						
					if(xms==""&&sfz==""&&startDate==""&&endDate==""){
						$.rwpUI.warn("无打印结果");
						return;
					}
					conn="where xm like {%"+xms+"%} and sfzh like {%"+sfz+"%} and slh like {%"+slh+"%}";
					if(startDate!=null&&startDate!=""&&endDate!=null&&endDate!=""){
						conn="where xm like {%"+xms+"%} and sfzh like {%"+sfz+"%}"+
						" and slh like {%"+slh+"%} and sqsj >="+"{"+dateStartSub+"}"+" and sqsj <="+"{"+dateEndSub+"}";
						if(ojs=="sfdy"){
							conn="where xm like {%"+xms+"%} and sfzh like {%"+sfz+"%}"+
							" and slh like {%"+slh+"%} and sqsj >="+"{"+dateStartSub+"}"+" and sqsj <="+"{"+dateEndSub+"}"+
							"and sfdy= "+"{"+oj+"}";
							
						}
					}
					if(objs=="zfzt=0"){
						$.rwpUI.warn("无打印结果");
						return;
					}
					
					//支持对象
					

					
					
					}else if(isState=="printDate"&&str==""){
						var startDate=$("#recordTimeBeginTimeSearch").val();
						var endDate=$("#recordTimeEndTimeSearch").val();
						conn="where sqsj >="+"{"+dateStartSub+"}"+" and sqsj <="+"{"+dateEndSub+"}";
						}
					if(isState=="OnlyPrint"&&str==""){
						show=1;
						conn=" where slh={"+$("#SLH").val()+"}";
					}
					if(conn!=""&&conn!=null){
						
					var start = document.getElementById("kjs");
					try {
					start.PrintAll(
'select sxh, xm, sldw, xb, mz, csrq,lzsj, csrq_y, csrq_m, csrq_d, hjdz, sfzh, zp, zwyzw, zwezw, zwcjjg, yxqx, qfjg, slyy, sqsj, slh, cbr, lxfs, zpm,zpdz from v_sqdjb_fs '+conn,'ApplyInfo.frx', 'HP LaserJet 400 M401 PCL 6',show);
					} catch (err) {
						alert(err.message);
					}	
					}else{
						$.rwpUI.warn("无查询结果");
					}
		 }		 
};


