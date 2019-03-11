<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0"/>
    <!--医首信息自定义css-->
    <link rel="stylesheet" type="text/css" href="css/jiaj.css"/>
    <!--官网demo css-->
    <link rel="stylesheet" type="text/css" href="css/example.css"/>
    <!--weui css-->
    <link rel="stylesheet" href="css/weui.min.css"/>
    <!--jQuery weui-->
    <link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/0.8.0/css/jquery-weui.min.css">
    <!--切换样式-->
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=IDvNBsejl9oqMbPF316iKsXR"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css"/>
    <style type="text/css">
        .weui_navbar_item.weui_bar_item_on {
            color: #2196F3;
            cursor: pointer;
        }

        body, html, #map {
            width: 100%;
            height: 100%;
            overflow: hidden;
            margin: 0;
            font-family: "微软雅黑";
        }

        .wbzt {
            font-weight: bold;
            color: black;
        }

        .spe {
            width: 20px;
            margin-right: 5px;
            margin-top: 20px;
        }
    </style>
    <title>选择一个办事网点</title>
</head>

<body ontouchstart>
<!--页面切换必须要添加js_container-->
<div class="container js_container">
    <div class="page">
        <div class="weui_tab">
            <!--底部菜单-->
            <div class="weui-navbar">
                <a href="#tab1" class="weui_navbar_item weui_bar_item_on">
                    地图
                </a>
                <a href="#tab2" class="weui_navbar_item">
                    列表
                </a>
            </div>
            <!--菜单对应panel-->
            <div class="weui_tab_bd">
                <!--tab1  医生管理面板-->
                <div id="tab1" class="weui_tab_bd_item weui_tab_bd_item_active">
                    <div id="map" style="margin-top: 45px;position: absolute"></div>

                    <!--</fieldset>-->
                    <!--</div>-->
                </div>
                <!--tab2  我的面板-->
                <div id="tab2" class="weui_tab_bd_item" style="margin-top: 45px">
                    <div class="weui-cells" style="margin-top: 0;">
                        <c:forEach items="${deptlist}" var="item">
                            <div class="weui-cell deptdiv" style="padding: 15px;">
                                <input type="hidden" value="${item.deptid}" class="deptid">
                                <input type="hidden" value="${item.deptname}" class="deptname">
                                <input type="hidden" value="${item.latitude}" class="latitude">
                                <input type="hidden" value="${item.longitude}" class="longitude">
                                <input type="hidden" value="${item.address}" class="address">
                                <div class="weui-cell__bd">
                                    <div>
                                        <p>
                                            <span class="wbzt">${item.deptname}</span>
                                            <span style="color: red;font-weight: bold;float: right;">2.3公里</span>
                                        </p>
                                        <p>
                                            <img class="spe" src="${pageContext.request.contextPath }/wx/img/wz.png" alt="">
                                            <span style="color:#6d6d6d;" class="wbzt">地址：</span>
                                            <span style="color:#000000;">${item.address}</span>
                                        </p>
                                        <p>
                                            <img class="spe" src="${pageContext.request.contextPath }/wx/img/kfdh.png" alt="">
                                            <span style="color:#6d6d6d;" class="wbzt">电话：</span>
                                            <span style="color:black">0791-88536171</span>
                                        </p>
                                    </div>
                                </div>
                                <div class="weui-cell__ft"></div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var list3 = new Array();
    $('.deptdiv').each(function () {
        var $this = $(this);
        var longitude = $this.find('.longitude').val();//经度
        var latitude = $this.find('.latitude').val();//纬度
        var address = $this.find('.address').val();//地址
        var deptname = $this.find('.deptname').val();//名称
        var deptid = $this.find('.deptid').val();
        list3.push({
            "title": "<a href='order.html?depId=" + deptid + "'>" + deptname + "</a>",
            "point": longitude + ',' + latitude,
            "address": address
        })
    });
    let markerArr = [
        {
            title: "名称：南昌市公安局红谷滩分局",
            point: "115.8688711175,28.7034238099",
            address: "地址：南昌市红谷滩新区春晖路545号",
            tel: "0791-88536171"
        },
        {
            title: "名称：红谷滩分局红谷滩派出所",
            point: "115.858451,28.698702",
            address: "地址：南昌市青山湖区芳华路266号",
            tel: "0791-88533110"
        },
        {
            title: "名称：红谷滩分局生米派出所",
            point: "115.802579,28.576607",
            address: "地址：江西省南昌市新建区生米镇049县道红谷滩新区生米中学",
            tel: "0791-83508110"
        }

        //{ title: "名称：天河公园", point: "113.372867,23.134274", address: "广东省广州市天河公园", tel: "18500000000" }
    ];
    markerArr = list3;

    function map_init() {
        let map = new BMap.Map("map"); // 创建Map实例
        var point = new BMap.Point(115.802579, 28.576607); //地图中心点，广州市
        map.centerAndZoom(point, 11); // 初始化地图,设置中心点坐标和地图级别。
        map.enableScrollWheelZoom(true); //启用滚轮放大缩小
        map.addControl(new BMap.NavigationControl());
        map.addControl(new BMap.ScaleControl());
        map.setDefaultCursor("crosshair");
        map.addEventListener("click", function (e) {   //点击事件
            if (!e.overlay) {
                var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
                    offset: new BMap.Size(10, 25), // 指定定位位置
                    imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
                });
                var marker = new BMap.Marker(e.point, {icon: myIcon});
                map.removeOverlay(preMarker);
                map.addOverlay(marker);
                preMarker = marker;
            }
        });
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function (r) {
            //console.log(r.point);
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                var mk = new BMap.Marker(r.point);
                map.addOverlay(mk);//标出所在地
                map.panTo(r.point);//地图中心移动
                //alert('您的位置：'+r.point.lng+','+r.point.lat);
                var point = new BMap.Point(r.point.lng, r.point.lat);//用所定位的经纬度查找所在地省市街道等信息
                var gc = new BMap.Geocoder();
                gc.getLocation(point, function (rs) {
                    var addComp = rs.addressComponents;
                    //console.log(rs.address);//地址信息
                    //alert(rs.address);//弹出所在地址

                });
            } else {
                alert('failed' + this.getStatus());
            }
        }, {enableHighAccuracy: true});
        //地图、卫星、混合模式切换
        map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP, BMAP_HYBRID_MAP]}));
        //向地图中添加缩放控件
        let ctrlNav = new window.BMap.NavigationControl({
            anchor: BMAP_ANCHOR_TOP_LEFT,
            type: BMAP_NAVIGATION_CONTROL_LARGE
        });
        map.addControl(ctrlNav);
        //向地图中添加缩略图控件
        let ctrlOve = new window.BMap.OverviewMapControl({
            anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
            isOpen: 1
        });
        map.addControl(ctrlOve);
        //向地图中添加比例尺控件
        let ctrlSca = new window.BMap.ScaleControl({
            anchor: BMAP_ANCHOR_BOTTOM_LEFT
        });
        map.addControl(ctrlSca);

        var point = new Array(); //存放标注点经纬信息的数组
        let marker = new Array(); //存放标注点对象的数组
        let info = new Array(); //存放提示信息窗口对象的数组
        let searchInfoWindow = new Array();//存放检索信息窗口对象的数组
        for (let i = 0; i < markerArr.length; i++) {
            let p0 = markerArr[i].point.split(",")[0]; //
            let p1 = markerArr[i].point.split(",")[1]; //按照原数组的point格式将地图点坐标的经纬度分别提出来
            point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
            marker[i] = new window.BMap.Marker(point[i]); //按照地图点坐标生成标记
            map.addOverlay(marker[i]);
//                    marker[i].setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
            //显示marker的title，marker多的话可以注释掉
            let label = new window.BMap.Label(markerArr[i].title, {offset: new window.BMap.Size(20, -10)});
            marker[i].setLabel(label);
            // 创建信息窗口对象
            info[i] = "<p style=’font-size:12px;lineheight:1.8em;’>" + "</br>地址：" + markerArr[i].address + "</br> 电话：" + markerArr[i].tel + "</br></p>";
            //创建百度样式检索信息窗口对象
            searchInfoWindow[i] = new BMapLib.SearchInfoWindow(map, info[i], {
                title: markerArr[i].title,      //标题
                width: 290,             //宽度
                height: 55,              //高度
//                        panel  : "panel",         //检索结果面板
                enableAutoPan: true,     //自动平移
//                        searchTypes   :[
//                            BMAPLIB_TAB_SEARCH,   //周边检索
//                            BMAPLIB_TAB_TO_HERE,  //到这里去
//                            BMAPLIB_TAB_FROM_HERE //从这里出发
//                        ]
            });
            //添加点击事件
            marker[i].addEventListener("click",
                (function (k) {
                    // js 闭包
                    return function () {
                        //将被点击marker置为中心
                        //map.centerAndZoom(point[k], 18);
                        //在marker上打开检索信息窗口
                        searchInfoWindow[k].open(marker[k]);
                        $(".BMapLib_sendToPhone").css("display", "none");
                        $(".BMapLib_nav").css("display", "none");
                        $(".BMapLib_trans").css("top", "102px");
                        $(".BMapLib_bubble_content").css("height", "65px");
                    }
                })(i)
            );
        }
    }

    //异步调用百度js
    function map_load() {
        let load = document.createElement("script");
        load.src = "http://api.map.baidu.com/api?v=2.0&ak=IDvNBsejl9oqMbPF316iKsXR&callback=map_init";
        document.body.appendChild(load);
    }

    window.onload = map_load;
</script>
<!--页面切换-->
<script>
    $(function () {
        $(".deptdiv").click(function () {
                alert($(this).find(".deptid").val());
                window.location.href = "${pageContext.request.contextPath }/appointmeAction/gotoorder?deptid="
                    + $(this).find(".deptid").val() + "&deptname=" + $(this).find(".deptname").val();
            }
        )
    });
</script>
<script src="https://cdn.bootcss.com/jquery-weui/0.8.2/js/jquery-weui.min.js"></script>
</body>
</html>
