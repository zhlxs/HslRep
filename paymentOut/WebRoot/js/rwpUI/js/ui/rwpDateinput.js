/**
 * @license                                     
 * 日期控件 for 牛发发
 * 
 * http://www.nffcms.com/
 *
 * 采用字符串拼接，DOM在IE6下，总是卡，万恶的IE6
 * Since: 2011/01/25
 */
; (function ($, window, document, undefined) {   

    var _count = 0;        

    var pluginName = 'rwpUIDateinput';   //控件名称

    var rwpDateinput = function (element, options) {
        this.element = element;
        this.$element = $(element);

        var opt = $.extend({}, $.rwpUI.Dateinput.defaults, options);  //合并已赋值参数
        opt.id = opt.id || $.rwpUI.guid('rwpDateinput_');
        this.options = opt;

        this._init();  //初始化
        _count++;

        $.data(this.element, pluginName, this);   //保存到元素上
    };

    rwpDateinput.prototype = {
        _init: function () {
            var opt = this.options;

            this._domHtml();

            this._addClickEvent();
        },
        _domHtml: function () {
            var template = $.rwpUI.Dateinput._template,
                dateRoot = $(template),
                opt = this.options, dom = {};

            dateRoot.hide();
            $(document.body).append(dateRoot);  //添加到body
            dateRoot.attr({ id: opt.id });   //设置ID
            dom.DateRoot = dateRoot;

            //遍历dateSelect的dom元素集合
            dom.DateRoot.find('*').each(function (i, item) {
                name = item.className.split('rwp')[1];
                if (name) {
                    dom[name] = $(item);
                };
            });

            //时间应用微调器
            dom.DateHour.rwpUISpinner({ type: 'int', minValue: 0, maxValue: 23 });
            dom.DateMinute.rwpUISpinner({ type: 'int', minValue: 0, maxValue: 59 });

            this.dom = dom;

            return this;
        },
        _addClickEvent: function () {    //添加点击事件
            var $element = this.$element, that = this, opt = this.options,
                $dateRoot = this.dom.DateRoot,
                $dateClean = this.dom.DateClean,
                $dateToday = this.dom.DateToday,
                $dateOk = this.dom.DateOk;

            $element.bind('focus click', function () { that._show.apply(that) }).keydown(function (e) {
                return e.preventDefault();
            });

            $('a', $dateRoot).click(function (e) {
                e.preventDefault();  //阻止a链接的默认操作
            });

            $(document).bind("click", function (e) {
                var el = e.target;
                if (!$(el).parents("#" + opt.id).length && el != $element[0]) {
                    if (!el.id || el.id.indexOf(opt.id) < 0) {
                        that._hide();
                    }
                }
            });
            
            $dateClean.click(function (e) {   //清除数据
                that._setValue.apply(that);
            });
            
            $dateToday.click(function (e) {  //设置为今天
                that._setValue.apply(that, [new Date()]);
            });

            $dateOk.click(function (e) {  //确定
                var date = that.getSelectDate();  //获取选择的日期及时间                
                that._setValue.apply(that, [rwpDateTime.parseDate(date)]);
            });

            return this;
        },        
        _show: function () {  //显示
            var $element = this.$element,
                $dateRoot = this.dom.DateRoot,
                objdate = rwpDateTime.parseDate($element.val()) || new Date();

            if (!this.isOpen) {
                this._setMonth(objdate)
                    ._setLocation();   //重新定位

                $dateRoot.show();
                this.isOpen = true;  //标识为打开状态
            }

            return this;
        },
        _setLocation: function () {
            var offset = this.$element.offset(),  //获取对象位置
                outerHeight = this.$element.outerHeight(),   //获取对象尺寸包括边框
                $dateRoot = this.dom.DateRoot;
            
            $dateRoot.css({ top: offset.top + outerHeight, left: offset.left });

            return this;
        },
        _hide: function () {   //隐藏
            var $dateRoot = this.dom.DateRoot;

            if (this.isOpen) {
                $dateRoot.hide();
                this.isOpen = false;
            }

            return this;
        },        
        _setValue: function (date) {  //赋值
            var opt = this.options, $element = this.$element, dateStr = '';

            if (date) {
                if (opt.time)
                    dateStr = rwpDateTime.format(date, 'yyyy-MM-dd HH:mm');
                else
                    dateStr = rwpDateTime.format(date, 'yyyy-MM-dd');
            }

            $element.val(dateStr);            
            this._hide();

            $.rwpUI.funcApply(opt.onChangeDate)(date);   //触发更改日期事件

            return this;
        },        
        _setMonth: function (date) {  //展示月份
            var that = this, dom = this.dom, opt = this.options,
                focusdate = rwpDateTime.parseDate(this.$element.val()) || date;
                currentdate = new Date();

            var year = date.getFullYear(), //获取完整的年份
                month = date.getMonth(), //获取当前月份(0-11,0代表1月)
                day = date.getDate(), //获取当前日(1-31) 
                hour = date.getHours(), //获取当前小时数(0-23)
                minute = date.getMinutes(),  //获取当前分钟数(0-59)
                week = new Date(year, month, 1).getDay(); //获取星期X(0-6,0代表星期天)

            dom.DateOk.show();  //显示确定按钮
            dom.DatePrev.unbind('click').click(function () {
                that._setMonth(new Date(year, month - 1, day, hour, minute));
            });
            dom.DateNext.unbind('click').click(function () {
                that._setMonth(new Date(year, month + 1, day, hour, minute));
            });            

            if (opt.time) {
                dom.DateHour.val(rwpDateTime.formathms(hour));
                dom.DateMinute.val(rwpDateTime.formathms(minute));                
                dom.DateTime.show();
            }
            dom.DateTitle.html("<a id=\"" + opt.id + "_setYear\" href=\"#" + year + "\">" + rwpDateTime.toYearMonth(year, month, opt.shortMonths) + "</a>");
            var body = that._getweek(),
                days = rwpDateTime.dayAm(year, month),  //获取本月最大天数
                prevDays = rwpDateTime.dayAm(year, month - 1),   //上月最大天数
                n = 42;

            body += '<div class="rwpDateRow rwpDateWeek">';
            for (var i = 0, num, idate, imonth, calss; i < n; i++) {
                if (i < week) {
                    num = prevDays - week + i + 1;   //上月 几日
                    imonth = month - 1;
                    calss = 'rwpDateOff';
                }
                else if (i >= week + days) {
                    num = i - days - week + 1;   //下月 几日
                    imonth = month + 1;
                    calss = 'rwpDateOff';
                }
                else {
                    num = i - week + 1;   //本月 几日
                    imonth = month;
                    calss = '';
                }
                idate = new Date(year, imonth, num);

                if (rwpDateTime.isOneDay(focusdate, idate)) {
                    calss = 'rwpDateFocus';
                }
                else if (rwpDateTime.isOneDay(currentdate, idate)) {
                    calss = 'rwpDateToday';
                }
                if (i % 7 === 0) {
                    if (i > 0) body += '</div><div class="rwpDateRow rwpDateWeek">';  //每7天作为一周
                }
                body += '<li><a id="' + opt.id + '_' + rwpDateTime.format(idate, 'yyyy-MM-dd') + '" href="javascript:void(0);"' + (calss != '' ? ' class="' + calss + '"' : '') + '>' + num + '</a></li>';
            }
            body += '</div>';

            dom.DateBody.html(body);

            $('#' + opt.id + '_setYear', dom.DateRoot).click(function (e) {
                e.preventDefault();
                that._setYear(year);
            });

            $('.rwpDateWeek>li>a', dom.DateRoot).click(function (e) {
                e.preventDefault();
                $('.rwpDateWeek>li>a', dom.DateRoot).removeClass('rwpDateFocus');  //移除其它a链接的选中样式
                $(this).addClass('rwpDateFocus');
            });

            return this;
        },        
//        _setHour: function (date) {  //展示小时
//            var that = this, opt = this.options, dom = this.dom,
//                focushour = date.getHours(),
//                idate = new Date(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes()),  //新建时间对象
//                body = '<div class="rwpDateRow rwpDateHour">';

//            dom.DateHead.hide();    //隐藏标题

//            for (var i = 0, calss; i < 24; i++) {
//                if (i % 4 === 0) {    //4个小时作为一行
//                    if (i > 0) body += '</div><div class="rwpDateRow rwpDateHour">';
//                }
//                idate.setHours(i);  //设置小时
        //                body += '<li><a id="' + opt.id + '_' + rwpDateTime.format(idate, 'yyyy-MM-dd HH:mm') + '" href="javascript:void(0);"';
//                if (focushour === i)
//                    body += ' class="rwpDateFocus"';
//                body += '>' + i + '</a></li>';
//            }
//            body += '</div>';
//            dom.DateBody.html(body);

//            $('.rwpDateHour>li>a', dom.DateRoot).click(function (e) {
//                e.preventDefault();
//                var date = $(this).attr('id').replace(opt.id + '_', '');
//                dom.DateHead.show();   //显示标题
//                that._setMonth(rwpDateTime.parseDate(date));
//            });

//            return this;
//        },        
//        _setMinute: function (date) {  //展示分钟
//            var that = this, opt = this.options, dom = this.dom,
//                focusminute = date.getMinutes(),
//                idate = new Date(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes()),  //新建时间对象
//                body = '<div class="rwpDateRow rwpDateMinute">';

//            dom.DateHead.hide();    //隐藏标题
//            
//            for (var i = 0, calss; i < 60; i++) {
//                if (i % 6 === 0) {    //6个分钟作为一行
//                    if (i > 0) body += '</div><div class="rwpDateRow rwpDateMinute">';
//                }
//                idate.setMinutes(i);  //设置分钟
        //                body += '<li><a id="' + opt.id + '_' + rwpDateTime.format(idate, 'yyyy-MM-dd HH:mm') + '" href="javascript:void(0);"';
//                if (focusminute === i)
//                    body += ' class="rwpDateFocus"';
//                body += '>' + i + '</a></li>';
//            }
//            body += '</div>';
//            dom.DateBody.html(body);

//            $('.rwpDateMinute>li>a', dom.DateRoot).click(function (e) {
//                e.preventDefault();
//                var date = $(this).attr('id').replace(opt.id + '_', '');
//                dom.DateHead.show();   //显示标题
//                that._setMonth(rwpDateTime.parseDate(date));
//            });

//            return this;
//        },
        _getweek: function () {   //获取星期字符串
            var str = '<div class="rwpDateDays">', opt = this.options,
                weekArr = opt.week.split(',');
            for (var i = 0, len = weekArr.length; i < len; i++) {
                str += '<li>' + weekArr[i] + '</li>';
            }
            str += '</div>';
            return str;
        },        
        _setYear: function (year) {  //展示年份
            var that = this, opt = this.options, dom  = this.dom,
                hour = 0, minute = 0,
                focusdate = rwpDateTime.parseDate(this.$element.val()) || new Date(),
                months = opt.months.split(','),
                body = '<div class="rwpDateRow rwpDateMonth">';
            
            dom.DateOk.hide();  //隐藏确定按钮 
            if (opt.time) {
                hour = parseInt(dom.DateHour.val(), 10);
                minute = parseInt(dom.DateMinute.val(), 10);
                dom.DateTime.hide();
            }
            dom.DateTitle.html("<a id=\"" + opt.id + "_setYears\" href=\"#\">" + year + "年</a>");             

            dom.DatePrev.unbind('click').click(function () {
                that._setYear(year - 1);
            });
            dom.DateNext.unbind('click').click(function () {
                that._setYear(year + 1);
            });
             
            for (var i = 0, idate, len = months.length; i < len; i++) {
                if (i % 4 === 0) {    //4个月份作为一行
                    if (i > 0) body += '</div><div class="rwpDateRow rwpDateMonth">';
                }
                idate = new Date(year, i, 1, hour, minute);
                body += '<li><a id="' + opt.id + '_' + rwpDateTime.format(idate, 'yyyy-MM-dd HH:mm') + '" href="javascript:void(0);"';
                if (focusdate.getFullYear() === year && focusdate.getMonth() === i) {
                    body += ' class="rwpDateFocus"';
                }
                body += '>' + months[i] + '</a></li>';
            }
            body += '</div>';
            dom.DateBody.html(body);

            $('#' + opt.id + '_setYears', dom.DateRoot).click(function (e) {
                e.preventDefault();
                that._setYears(year);
            });

            $('.rwpDateMonth>li>a', dom.DateRoot).click(function (e) {
                e.preventDefault();
                var date = rwpDateTime.parseDate($(this).attr('id').replace(opt.id + '_', ''));
                that._setMonth(date);
            });

            return this;
        },        
        _setYears: function (year) {  //10年
            var that = this, opt = this.options, dom = this.dom;
            var yearstr = year + '',
                q = parseInt(yearstr.substr(0, 1), 10),
                b = parseInt(yearstr.substr(1, 1), 10),
                s = parseInt(yearstr.substr(2, 1), 10),
                g = parseInt(yearstr.substr(3, 1), 10),
                currentyear = q * 1000 + b * 100 + s * 10,   //去除年份最后一位
                endyear = currentyear + 9, calss,
                focusdate = rwpDateTime.parseDate(this.$element.val()) || new Date(),
                focusyear = focusdate.getFullYear(),
                body = '<div class="rwpDateRow rwpDateYear">';

            dom.DateTitle.html("<a id=\"" + opt.id + "_setCentury\" href=\"#\">" + currentyear + '-' + endyear + "</a>");            

            dom.DatePrev.unbind('click').click(function () {
                that._setYears(year - 10);
            });
            dom.DateNext.unbind('click').click(function () {
                that._setYears(year + 10);
            });
           
            for (var i = 0, num; i < 12; i++) {
                if (i % 4 === 0) {    //4个年份作为一行
                    if (i > 0) body += '</div><div class="rwpDateRow rwpDateYear">';
                }
                num = currentyear + i - 1;
                calss = '';
                if (num < currentyear || num > endyear) {
                    calss = 'rwpDateOff';
                }
                if (num === focusyear) {
                    calss = 'rwpDateFocus';
                }
                body += '<li><a id="' + opt.id + '_' + num + '" href="javascript:void(0);"' + (calss == '' ? '' : ' class="' + calss + '"') + '>' + num + '</a></li>';
            }
            body += '</div>';
            dom.DateBody.html(body);

            $('#' + opt.id + '_setCentury', dom.DateRoot).click(function (e) {
                e.preventDefault();
                that._setCentury(year);
            });

            $('.rwpDateYear>li>a', dom.DateRoot).click(function (e) {
                e.preventDefault();
                var clickyear = parseInt($(this).attr('id').replace(opt.id + '_', ''), 10);
                that._setYear(clickyear);
            });

            return this;
        },        
        _setCentury: function (year) {  //世纪
            var that = this, opt = this.options, dom = this.dom;
            var yearstr = year + '',
                q = parseInt(yearstr.substr(0, 1), 10),
                b = parseInt(yearstr.substr(1, 1), 10),
                currentyear = q * 1000 + b * 100,  //取年份前二位
                yearend = currentyear + 99,
                focusdate = rwpDateTime.parseDate(this.$element.val()) || new Date(),
                focusyear = focusdate.getFullYear(),
                body = '<div class="rwpDateRow rwpDateYears">';
            var calss, startyear, endyaer;

            dom.DateTitle.html("<a id=\"" + opt.id + "_setCentury\" href=\"#\">" + currentyear + '-' + yearend + "</a>");

            dom.DatePrev.unbind('click').click(function () {
                that._setCentury(year - 100);
            });
            dom.DateNext.unbind('click').click(function () {
                that._setCentury(year + 100);
            });
            
            for (var i = 0; i < 10; i++) {
                if (i % 2 === 0) {
                    if (i > 0) body += '</div><div class="rwpDateRow rwpDateYears">';
                }
                calss = '';
                startyear = currentyear + (i * 10);
                endyaer = startyear + 9;
                if (focusyear >= startyear && focusyear <= endyaer) {
                    calss = 'rwpDateFocus';
                }
                body += '<li><a id="' + opt.id + '_' + startyear + '" href="javascript:void(0);"' + (calss == '' ? '' : ' class="' + calss + '"') + '>' + startyear + '-' + endyaer + '</a></li>';
            }
            body += '</div>';
            dom.DateBody.html(body);

            $('#' + opt.id + '_setCentury', dom.DateRoot).click(function (e) {
                e.preventDefault();
            });

            $('.rwpDateYears>li>a', dom.DateRoot).click(function (e) {
                e.preventDefault();
                var clickyear = parseInt($(this).attr('id').replace(opt.id + '_', ''), 10);
                that._setYears(clickyear);
            });

            return this;
        },
        getSelectDate: function () {   //获取选中的日期及时间
            var opt = this.options, dom = this.dom,
                $selectDate = $('.rwpDateWeek>li>a.rwpDateFocus', dom.DateRoot), date;
            if ($selectDate.length > 0) {
                date = $selectDate.eq(0).attr('id').replace(opt.id + '_', '');  //获取日期
                if (opt.time) {
                    date += ' ' + dom.DateHour.val() + ':' + dom.DateMinute.val();  //添加小时和分钟
                }
            }
            return date;
        }
    };

    var rwpDateTime = {
        //+---------------------------------------------------  
        //| 日期检查  
        //| 格式为：YYYY-MM-DD 
        //+---------------------------------------------------  
        isValidDate: function (val) {
            var r = $.isArray(val) ? val : this.regDate(val); //如果传入数组，则直接调用
            if (r == null) return false;
            if (r.length < 3) return false;
            var d = new Date(r[1], r[2], r[3]);
            if (d.getFullYear() != r[1]) return false;
            if (d.getMonth() != r[2]) return false;
            if (d.getDate() != r[3]) return false;
            return true;
        },
        //+---------------------------------------------------  
        //| 日期时间检查  
        //| 格式为：YYYY-MM-DD HH:MM 
        //+---------------------------------------------------  
        isValidDateTime: function (val) {
            var r = $.isArray(val) ? val : this.regDate(val); //如果传入数组，则直接调用
            if (r == null) return false;
            if (r.length < 5) return false;
            var d = new Date(r[1], r[2], r[3], r[4], r[5]);
            if (d.getFullYear() != r[1]) return false;
            if (d.getMonth() != r[2]) return false;
            if (d.getDate() != r[3]) return false;
            if (d.getHours() != r[4]) return false;
            if (d.getMinutes() != r[5]) return false;
            //if (d.getSeconds() != r[6]) return false;
            return true;
        },
        //+---------------------------------------------------  
        //| 正则判断日期时间  
        //| 返回数组  
        //+---------------------------------------------------
        regDate: function (Str) {
            var sDateTime = Str.replace(/(^\s+|\s+$)/g, ''); //去两边空格;
            var reg = /^(\d{4})\-(\d{1,2})\-(\d{1,2}) (\d{1,2}):(\d{1,2})$/;
            var r = sDateTime.match(reg);
            if (r == null) {
                reg = /^(\d{4})\-(\d{1,2})\-(\d{1,2})$/;
                r = sDateTime.match(reg);
            }
            if (r != null) r[2] = r[2] - 1;  //用于构造new Date时 月份减1
            return r;
        },
        //+---------------------------------------------------  
        //| 转换日期时间 
        //+---------------------------------------------------  
        parseDate: function (val) {
            if (!val) { return; }
            if (val.constructor == Date) { return val; }
            if (typeof val == 'string') {
                var r = this.regDate(val);
                if (this.isValidDateTime(r)) return new Date(r[1], r[2], r[3], r[4], r[5]);
                if (this.isValidDate(r)) return new Date(r[1], r[2], r[3]);
            }
            return;
        },
        //---------------------------------------------------  
        // 日期格式化  
        // 格式 YYYY/yyyy/YY/yy 表示年份  
        // MM/M 月份  
        // W/w 星期  
        // dd/DD/d/D 日期  
        // hh/HH/h/H 时间  
        // mm/m 分钟  
        // ss/SS/s/S 秒  
        //---------------------------------------------------
        format: function (date, formatStr) {
            var str = formatStr;
            //var Week = ['日', '一', '二', '三', '四', '五', '六']; 
            str = str.replace(/yyyy|YYYY/, date.getFullYear());
            str = str.replace(/yy|YY/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : '0' + (date.getYear() % 100));
            str = str.replace(/MM/, this.formathms(date.getMonth() + 1));
            str = str.replace(/M/g, date.getMonth() + 1);
            //str = str.replace(/w|W/g, Week[date.getDay()]); 
            str = str.replace(/dd|DD/, this.formathms(date.getDate()));
            str = str.replace(/d|D/g, date.getDate());
            str = str.replace(/hh|HH/, this.formathms(date.getHours()));
            str = str.replace(/h|H/g, date.getHours());
            str = str.replace(/mm/, this.formathms(date.getMinutes()));
            str = str.replace(/m/g, date.getMinutes());
            str = str.replace(/ss|SS/, this.formathms(date.getSeconds()));
            str = str.replace(/s|S/g, date.getSeconds());
            return str;
        },
        //+---------------------------------------------------  
        //| 输出年月 
        //+---------------------------------------------------  
        toYearMonth: function (year, month, months) {
            return year + '年' + months.split(',')[month];
        },
        //+---------------------------------------------------  
        //| 输出2位格式 
        //+---------------------------------------------------  
        formathms: function (hms) {
            return hms > 9 ? '' + hms : '0' + hms;
        },
        //+---------------------------------------------------  
        //| 取得月的最大天数  
        //+---------------------------------------------------  
        dayAm: function (year, month) {
            return 32 - new Date(year, month, 32).getDate();
        },
        //+---------------------------------------------------  
        //| 是否同一天  
        //+---------------------------------------------------  
        isOneDay: function (dtStart, dtEnd) {
            if (dtStart.getFullYear() != dtEnd.getFullYear()) return false;
            if (dtStart.getMonth() != dtEnd.getMonth()) return false;
            if (dtStart.getDate() != dtEnd.getDate()) return false;
            return true;
        },
        //+---------------------------------------------------  
        //| 比较日期差 dtEnd 格式为日期型或者 有效日期格式字符串  
        //+---------------------------------------------------  
        dateDiff: function (strInterval, dtStart, dtEnd) {
            switch (strInterval) {
                case 's': return parseInt(((dtEnd - dtStart) / 1000), 10);
                case 'n': return parseInt(((dtEnd - dtStart) / 60000), 10);
                case 'h': return parseInt(((dtEnd - dtStart) / 3600000), 10);
                case 'd': return parseInt(((dtEnd - dtStart) / 86400000), 10);
                case 'w': return parseInt(((dtEnd - dtStart) / (86400000 * 7)), 10);
                case 'm': return (dtEnd.getMonth() + 1) + ((dtEnd.getFullYear() - dtStart.getFullYear()) * 12) - (dtStart.getMonth() + 1);
                case 'y': return dtEnd.getFullYear() - dtStart.getFullYear();
            }
        }
    };

    $.rwpUI.DateTime = rwpDateTime;

    $.rwpUI.Dateinput = function (element, options) {
        (new rwpDateinput(element, options));
    };

    $.rwpUI.Dateinput.defaults = {    //默认参数
        id: 0, //设置ID
        time: false, //开启时间选择
        months: '一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月',
        shortMonths: '1月,2月,3月,4月,5月,6月,7月,8月,9月,10月,11月,12月',
        week: '日,一,二,三,四,五,六',
        onChangeDate: null    //日期更改事件
    };

    $.rwpUI.Dateinput._template = '<div class="rwpDateRoot">'
                                   + '<div class="rwpDateTop">'
                                      + '<a href="javascript:void(0);" class="rwpDateClean">清除</a>'
                                      + '<a href="javascript:void(0);" class="rwpDateOk">确定</a>'
                                      + '<a href="javascript:void(0);" class="rwpDateToday">今天</a>'
                                   + '</div>'
                                   + '<div class="rwpDateHead">'
                                      + '<a class="rwpDatePrev"></a>'
                                      + '<div class="rwpDateTitle"></div>'
                                      + '<a class="rwpDateNext"></a>'
                                   + '</div>'
                                   + '<div class="rwpDateBody"></div>'
                                   + '<div class="rwpDateTime" style="display: none;">'
                                      + '<input type="text" class="rwpDateHour"/>'
                                      + '<span class="ffl">:</span>'
                                      + '<input type="text" class="rwpDateMinute"/>'
                                   + '</div>'
                                + '</div>';

    $.rwpUI.Dateinput.fn = rwpDateinput;  //指向rwpDateinput

    $.fn[pluginName] = function (options) {
        if (this.length == 0) return;
        this.each(function () {
            var element = this;
            if ($.data(element, pluginName)) return;
            $.rwpUI.Dateinput(element, options);
        });
    };

})(jQuery, window, document);