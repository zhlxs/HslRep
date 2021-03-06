/**
 * @description 基于jQuery的select下拉框插件
 * @author Arvin
 * @date 2016年8月11日18:40:56
 * @version 1.0
 */
(function($) {
	
	var defautl_options = {
		
	};
	
	$.fn.extend({
		"jSelect":function(options) {
			options = $.extend(defautl_options, options);
			$(this).hide();
			$(this).each(function() {
				// 获取当前元素的ID
				var id = $(this).attr("id");
				var $jSelect = createSelect(id);
				$jSelect.width($(this).width() + 20);
				$(this).after($jSelect);
				
				// 显示当前下拉框中的值
				$("#"+ id +"_current_select .current_select_text").html($(this).val());
				
				// 遍历下拉框，将每个option中的值添加到列表中
				$(this).find("option").each(function() {
					$("#"+ id +"_select ul").append("<li>"+ $(this).text() +"</li>");
				});
				$("#"+ id +"_select ul li:eq(0)").addClass("current_item");
				
				// 显示或隐藏下拉列表
				$("#"+ id +"_current_select").click(function() {
					toggle(id);
				});
				
				initListItemClickEvent(id);
				initCloseEvent(id);
				initKeyEvent(id);
				
				// 初始化滚动条
				try {
					initSelectScrollBar(id);
				} catch(e) {
					console.log(e);
				}
			});
		}
	});

	
	
	/**
	 * 控制下拉列表隐藏或显示
	 */
	function toggle(id) {
		$("#"+ id +"_select ul").is($(":hidden")) ? showSelect(id) : hideSelect(id);
	}
	
	function showSelect(id) {
		$("#"+ id +"_select ul").show().scrollTop(0);
	}
	function hideSelect(id) {
		$("#"+ id +"_select ul").find("li:eq(0)").addClass("current_item").siblings().removeClass("current_item");
		$("#"+ id +"_select ul").hide();
		index = 0;
	}
	
	/**
	 * 绑定列表项点击事件
	 * @param {Object} id
	 */
	function initListItemClickEvent(id) {
		$("#"+ id +"_select ul li").click(function() {
			$("#"+ id).find("option").eq($(this).index()).attr("selected", "selected").siblings("option").removeAttr("selected");
			$("#"+ id +"_current_select .current_select_text").html($(this).html());
			hideSelect(id);
			$("#"+ id).change();
		});
	}
	
	/**
	 * 关闭下拉列表
	 * @param {Object} id
	 */
	function initCloseEvent(id) {
		$(document).click(function(e) {
			var $target = $(e.target || e.srcElement);
			if (
				!$target.is($("#" + id + "_select ul,#" + id + "_current_select")) && 
				!$target.is($("#" + id + "_current_select").find("*"))
			) {
				hideSelect(id); // 点击页面其他地方，隐藏下拉列表
			}
		});
		
		$("#" + id + "_current_select").blur(function() {
			setTimeout(function() {
				hideSelect(id);
			}, 200);
		});
	}
	
	/**
	 * 改变滚动条样式
	 * @param {Object} id
	 */
	function initSelectScrollBar(id) {
		$("#"+ id +"_select ul").niceScroll({
    		styler: "fb",
			cursorcolor: "#999",
			cursorwidth: '6',
			cursorborderradius: '10px',
			background: 'transparent',
			spacebarenabled: false,
			cursorborder: '0',
			zindex: '1000'
        });
	}
	
	var index = 0; // 当前下拉列表中当前选中项的索引
	
	/**
	 * 初始化键盘事件
	 * @param {Object} id
	 */
	function initKeyEvent(id) {
		$("#"+ id +"_select").keydown(function(event) {
			//alert(event.keyCode);
			switch (event.keyCode) {
				case 13: // 回车键
				case 32: // 空格键
					$("#"+ id).find("option").eq($(this).find("ul li.current_item").index()).attr("selected", "selected").siblings("option").removeAttr("selected");
					$("#"+ id +"_current_select .current_select_text").html($(this).find("ul li.current_item").html());
					hideSelect(id);
					$("#"+ id).change();
					break;
				case 27: // ESC
					hideSelect(id);
					break;
				case 38: // 方向键上键
					index--;
					if (index < 0) {
						index = 0;
					}
					if (index < $(this).find("ul li").length - 10) {
						$(this).find("ul").scrollTop($(this).find("ul").scrollTop() - 30);
					}
					break;
				case 40: // 方向下键
					if ($(this).find("ul").is($(":hidden"))) {
						showSelect(id); // 显示下拉列表
					} else {
						index++;
						if (index >= $(this).find("ul li").length) {
							index = $(this).find("ul li").length - 1;
						}
					}
					if (index > 9) {
						$(this).find("ul").scrollTop($(this).find("ul").scrollTop() + 30);
					}
					break;
			}
			console.log("index:"+index);
			$(this).find("ul li").eq(index).addClass("current_item").siblings().removeClass("current_item");
		});
		
	}

	/**
	 * 创建下拉框 html
	 */
	function createSelect(srcId) {
		var selectHtml = 
			"<div id=\"" + srcId + "_select\" class='j_select'>"+
			"	<div id=\"" + srcId + "_current_select\" class=\"current_select\" tabindex=\"0\">"+
			"		<span class=\"current_select_text "+ srcId +"_current_select_text\"></span>"+
			"		<span class=\"select_icon "+ srcId +"_select_icon\">"+
			"			<svg width=\"13\" height=\"13\" viewBox=\"0 0 200 200\">"+
			"				<g class=\"transform-group\">"+
			"					<g transform=\"scale(0.1953125, 0.1953125)\">"+
			"						<path d=\"M818.393225 300.939003c12.824073-14.09502 34.658358-15.126512 48.752354-2.303462 14.09502 12.843516 15.126512 34.678824 2.302439 48.752354l-332.676845 364.835266c-12.844539 14.114462-34.659381 15.127536-48.753377 2.302439-0.815575-0.733711-1.588171-1.486864-2.302439-2.302439l-0.080841-0.078795-0.13917-0.13917L153.018046 347.388918c-12.824073-14.074553-11.791557-35.909861 2.302439-48.752354 14.09502-12.824073 35.930327-11.792581 48.753377 2.303462l307.168891 336.845795 307.149449-336.845795L818.393225 300.939003 818.393225 300.939003z\" fill=\"#272636\"></path>"+
			"					</g>"+
			"				</g>"+
			"			</svg>"+
			"		</span>"+
			"	</div>"+
			"	<ul tabindex=\"1\"></ul>"+
			"</div>";
		return $(selectHtml);
	}
	
})(jQuery);
