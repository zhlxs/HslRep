<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base1.jsp"%>
<html>
<head>
	<title>${sysBaseInfo.sysName}</title>
	<!--[if lte IE 6]>
	<script src="${ctx}/js/png.js" type="text/javascript"></script>
	<script type="text/javascript">
		try {
			document.execCommand("BackgroundImageCache", false, true);
		} catch(e) {}
		DD_belatedPNG.fix('div,p,h1,h2,h3,h4,ul,li,img,a,a:hover,span,em,b,i');
	</script>
	<![endif]-->
	<script type="text/javascript">
        // 		var rwpMenuData = ${menuList };
        var rwpMenuData = JSON.parse('${menuList }');
        //var topMenuIcons = ['work', 'manage', 'user', 'config', 'system'];
        var rwpPluginHelper = $.rwpUI.pluginHelper, rwpListLayout = rwpPluginHelper.listLayout, rwpMenuHelper = rwpPluginHelper.menuHelper, _rwpSplit = $.rwpUI.split = $.rwpUI.split
            || ($.rwpUI.Combobox ? $.rwpUI.Combobox.defaults.split : ','), rwpTemp = {};

        rwpMenuHelper.showParentTreeMenu = function(menuItem) {
            var parent = menuItem.parent().parent();
            if (parent.hasClass('rwpSimpleTreeChildren')) { //树型菜单的子结点
                var parentMenu = $('a.rwpSimpleTreeItem', parent.prev('li')); //获取直接父结点
                if (!$('span', parentMenu).hasClass('open'))
                    parentMenu.click(); //没有展开时触发父结点的点击事件
                rwpMenuHelper.showParentTreeMenu(parentMenu); //递归展开父菜单
            }
        };

        rwpMenuHelper.showParentAccordionPanel = function(menuItem) { //展开所属面板抽屉
            var $panelHeader = menuItem.parents('.rwpAccordionBody').prev(
                '.rwpAccordionHeader');
            if ($panelHeader.length > 0
                && !$panelHeader.hasClass('rwpAccordionHeaderSel')) {
                $panelHeader.click(); //触发面板抽屉标题的点击事件
            }
        };

        rwpMenuHelper.tabMenuClick = function(tabMenu) {
            if (!tabMenu.hasClass("current")) {
                var i = tabMenu.index();
                tabMenu.siblings().removeClass("current");
                tabMenu.addClass("current"); //添加选中
                var thisMenuAccord = $('.rwpLayoutLeft .rwpAccordion').eq(i);
                thisMenuAccord.siblings().hide();
                thisMenuAccord.show(); //显示对应的子菜单面板
                var thismenu = $(".rwpSimpleTree a.selected", thisMenuAccord);
                if (thismenu.length != 1) {
                    thismenu = $(".rwpSimpleTree a", thisMenuAccord).not(
                        '.rwpSimpleTreeItem').first();
                }
                if (thismenu.length == 1) {
                    if (thismenu.parent().parent()
                            .hasClass('rwpSimpleTreeChildren')) {
                        //树菜单的子结点自动展开其所有父结点
                        rwpMenuHelper.showParentTreeMenu(thismenu);
                    }
                    thismenu.get(0).click(); //触发选中菜单的点击
                }
            }
        };

        rwpMenuHelper.pageHeaderRender = function(menuText) {
            menuText = menuText || '';
            return '<i class="i"></i>' + menuText;
        };

        rwpMenuHelper.beforePageRender = function($menuLink, isReback) {
            $.isFunction(rwpTemp.destroyUe) && rwpTemp.destroyUe();
            rwpTemp.interval && clearInterval(rwpTemp.interval); //清除菜单页面定时器
            rwpTemp = {};
            rwpMenuHelper.isRebackPage = isReback; //标记页面是否返回
        };

        rwpMenuHelper.afterPageRender = function() {
            rwpPluginHelper.validateParse($('.pageContent')); //表单验证解析
            rwpMenuHelper.isRebackPage = false; //清除页面返回标志
        };

        rwpMenuHelper.treeMenuClick = function(treeMenu) { //左侧树型菜单点击
            var menuData = treeMenu.data, $menuLink = treeMenu.$target;
            rwpMenuHelper.windowMenuLayout.loadMenu($menuLink, menuData.url,
                menuData.text);
            rwpListLayout.lastSearchParam = {};
        };

        rwpMenuHelper.loadMenuInsidePage = function(pageUrl) { //加载菜单内部页面覆盖右侧显示内容
            rwpMenuHelper.windowMenuLayout.loadPage(pageUrl, false);
        };

        rwpMenuHelper.beforeRebackPage = function(lastPageItem) { //返回上一个页面时确保左侧树展开菜单项
            if (lastPageItem.$menu) {
                rwpMenuHelper.showParentAccordionPanel(lastPageItem.$menu);
                //树菜单的子结点自动展开其所有父结点
                rwpMenuHelper.showParentTreeMenu(lastPageItem.$menu);
            }
        };

        rwpMenuHelper.afterRebackPage = function() { //返回上一个页面时自动赋值queryinfo及页码
            var rwpLastListSearchParam = rwpListLayout.lastSearchParam, gridManager, formManager, lastSearchQueryinfo, $selectTabItem;
            if (rwpLastListSearchParam) {
                gridManager = rwpListLayout.getCurrentGrid();
                gridManager && gridManager.refreshData(rwpLastListSearchParam); //根据原queryinfo及页号刷新表格
                formManager = rwpListLayout.getSearchForm(); //获取查询表单
                if (formManager && rwpLastListSearchParam.params) {
                    $.each(rwpLastListSearchParam.params, function(i, item) {
                        if (item.name == 'queryinfo') {
                            lastSearchQueryinfo = JSON.parse(item.value);
                        }
                    });
                    if (lastSearchQueryinfo) { //上次查询传递了queryinfo
                        formManager.setSearchValues(lastSearchQueryinfo);
                    }
                }
                if (rwpLastListSearchParam.tabName) { //设置标签页选中
                    $selectTabItem = $('.pageContent .rwpTabNavList .rwpTabNavItem[name='
                        + rwpLastListSearchParam.tabName + ']');
                    if ($selectTabItem && $selectTabItem.length == 1) {
                        $selectTabItem.addClass('rwpTabCurrent').siblings()
                            .removeClass('rwpTabCurrent');
                    }
                }
            }
        };

        rwpMenuHelper.rebackLastPage = function() {
            rwpMenuHelper.windowMenuLayout.rebackLastPage();
        };

        rwpMenuHelper.showMenu = function(menudata) {
            var tabMenus = []; //导航菜单
            var childMenuAccord = []; //子菜单面板
            if (menudata && menudata.length > 0) {
                $('.gIMenu ul').empty(); //清空导航菜单
                $('.rwpLayoutLeft').empty(); //清空左侧内容
                $(menudata)
                    .each(
                        function(i, item) {
                            var tabMenu = $('<li><a href="javascript:void(0);" class="'
                                + item.iconCssClass
                                + '"><i class="i">&nbsp;</i>'
                                + item.menuName + '</a></li>');
                            tabMenu.click(function(e) {
                                rwpMenuHelper.tabMenuClick($(this));
                                e.stopPropagation(); //阻止a标签点击事件的冒泡
                                e.preventDefault();
                            });
                            $('.gIMenu ul').append(tabMenu);
                            var childMenu = $('<div class="rwpAccordion" style="display:none;"><div title="' + item.menuName + '功能" state="select"><ul class="rwpSimpleTree"></ul></div></div>');
                            $('.rwpLayoutLeft').append(childMenu);
                            childMenu.rwpUIAccordion();
                            $('.rwpSimpleTree', childMenu)
                                .rwpUISimpleTree(
                                    {
                                        dataAttrs : {
                                            text : 'menuName',
                                            url : 'menuUrl',
                                            icon : 'iconCssClass'
                                        },
                                        data : item.children,
                                        onSelect : rwpMenuHelper.treeMenuClick
                                    });

                        });
            }
            $('.gIMenu ul a:first').click(); //自动点击第一个导航菜单
        };

        var setTimeString = function() {
            var s = document.getElementById('localdatetime');
            s.innerHTML = '今天是：' + rwpPluginHelper.clientTimeString();
        };

        function toggleDropMenuState(element) {
            if (element.is(':hidden')) {
                element.show();
            } else {
                element.hide();
            }
        }

        function isShowContent(sideObj, contentObj) {//切换内容显示状态
            var _side = $(sideObj), _content = $(contentObj);
            if (_content.is(':hidden')) {
                _content.show();
                _side.html("隐藏<i class='i'>&nbsp;</i>");
            } else {
                _content.hide();
                _side.html("展开<i class='i'>&nbsp;</i>");
            }
            _side.toggleClass('open');
        }

        $(function() {
            $('#layout').rwpUILayout();
            $('#pageLayout').rwpUILayout();
            rwpMenuHelper.windowMenuLayout = $.rwpUI.menuLayout({
                pageHeaderRender : rwpMenuHelper.pageHeaderRender,
                onBeforePageRender : rwpMenuHelper.beforePageRender,
                onAfterPageRender : rwpMenuHelper.afterPageRender,
                onBeforeRebackPage : rwpMenuHelper.beforeRebackPage,
                onAfterRebackPage : rwpMenuHelper.afterRebackPage
            });
            rwpMenuHelper.showMenu(rwpMenuData); //显示菜单
            window.rwpLayoutWindow = $('#layout').data('rwpUILayout'); //记录窗口的顶级Layout
            window.layoutHeight = function() {
                return window.rwpLayoutWindow.layoutHeight;
            }; //记录窗口顶级Layout的高度
            window.layoutWidth = function() {
                return window.rwpLayoutWindow.layoutWidth;
            }; //记录窗口顶级Layout的宽度


            $("#mpwd").click(function(event) { //修改密码点击事件
                var url = $(this).attr('href');

                $.rwpUI.open({
                    title : '修改密码',
                    url : url,
                    ajaxCallback : rwpPluginHelper.validateParseDialog,
                    buttons : [ {
                        text : '确定',
                        onclick : rwpPluginHelper.dialogFormImportOK
                    }, {
                        text : '取消',
                        onclick : rwpPluginHelper.dialogImportCancel
                    } ]
                });
                event.stopPropagation();
                event.preventDefault();
            });



            /*切换下连菜单显示状态*/
            $('.uDropMenu .dropMenuTitle').click(function() {
                var dropObj = $(this).next('.dropList');
                toggleDropMenuState(dropObj);
            });

            $('.uDropMenu .dropList .list').click(function() {
                var dropObj = $(this).parents('.dropList');
                toggleDropMenuState(dropObj);
            });




        });
	</script>
	<style type="text/css">
		.dark{
			visibility:hidden
		}
		#ysl{

			margin-left: 25px;
			vertical-align: middle;
		}
		#naba{
			/* position: relative; */
			position: absolute;
			top:17px;
		}
		.uTop{
			height:41px;
			line-height: 41px;
		}
		.ysl{
			float: left;
		}

	</style>
</head>
<body>
<div id="layout">
	<div region="top">
		<div class="gHd">
			<div class="uTop">

				<ul class="uTip">

					<li class="menuList time"><i class="i">&nbsp;</i><cite
							id="localdatetime"></cite></li>
					<li class="tipList uDropMenu uDropMenuSmall dropMenuUser"><a
							href="javascript:void(0);" class="dropMenuTitle"><i
							class="i1">&nbsp;</i><em>${user.fullName}&nbsp;欢迎您！</em><i
							class="i2">&nbsp;</i> </a>
						<div class="dropList">
							<i class="listToolBar"></i> <a class="list firstlist editPwd"
														   href="${ctx }/layoutController/changePwd?rwpOpts='%7Bisdialog:true,width:800%7D%E2%80%98"
														   title="修改密码" id="mpwd"><strong><i class="i">&nbsp;</i>修改密码</strong>
						</a>

							<p class="line"></p>
							<a class="list ext" href="${ctx}/logout" title="退出"><strong><i
									class="i">&nbsp;</i>退出</strong> </a>
						</div> <!--dropList结束-->
					</li>
				</ul>
			</div>
			<!--uTtop结束-->

			<div class="uCenter">
				<div class="Logo">
					<span class="first t">&nbsp;</span> <span class="txt t">${sysBaseInfo.sysName}</span>
					<span class="end t">&nbsp;</span>
				</div>
				<!--Logo结束 -->
				<div class="gIMenu">
					<ul></ul>
				</div>
				<!--gIMenu结束 -->
				<script type="text/javascript">
                    setTimeString();
                    setInterval(setTimeString, 1000);
				</script>
			</div>
			<!--uCenter结束-->
		</div>
		<!-- gHd结束 -->
	</div>
	<!-- top结束 -->
	<div data-options="{ 'region':'left','width':'200'}"></div>
	<div data-options="{ 'region':'center'}">
		<div id="pageLayout">
			<div class="pageHeader" data-options="{ 'region':'top' }"></div>
			<div class="pageContent" data-options="{ 'region':'center' }"></div>
		</div>
	</div>
	<!-- center结束 -->
</div>
<!-- layout结束 -->
</body>
</html>