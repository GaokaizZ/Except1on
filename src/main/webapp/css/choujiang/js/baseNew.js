// JavaScript Document
/* 站点说明： 中国联通手机客户端
 * 开发时间： 2011-04-14
 * 开发者： 宁玉波   
 * 维护者： 胡建
 *
 * 样式版本： v3.0
 * 版本时间： 2014-03-14
 * 注意事项： 共用JS文件
 */

;jQuery(function( $ ){
	
	//Initialization
	//use this quick url
	url=function(url){
		window.location.href=url;
	};
	
	//you can try it go back
	var backBtn=$('.goback');
	var goBack=function(){
		window.history.back();
	};
	backBtn.bind('click',goBack);
	
	//弹出层调用
	function popupfn(popupBtn,popup,ensureBtn){
		$(popupBtn).click(function(){
			$(popup).show();
		});
		$(ensureBtn).click(function(){
			$(this).parents(popup).hide();
		});
	};
	popupfn('.popupBtn1','.bg_popup1','.ensureBtn');
	popupfn('.popupBtn2','.bg_popup2','.ensureBtn');
	popupfn('.popupBtn3','.bg_popup3','.ensureBtn');
	
	function autopopupfn(popup,ensureBtn){
		$(popup).show();
		$(ensureBtn).click(function(){
			$(this).parents(popup).hide();
		});
	};
	$('.popup_close').click(function(){
		$(this).parents('.bg_popup').hide();
	});
	
	//点击瞬间效果
	function setBtnColor(className,addClass){
		$(className).bind('mousedown touchstart',function(){
			 $(this).addClass(addClass);
		}).bind('mouseup touchend',function(){
			 $(this).removeClass(addClass);
		}).bind('mouseover touchmove',function(){
			 $(this).removeClass(addClass);
		});
	};
	setBtnColor('.tab_list','active');
	setBtnColor('.btn','active');
		
	
	//中奖查询导航切换效果
	$('.tab_award > li,.sub_tab li').click(function(){
		$(this).addClass('on').siblings('li').removeClass('on');
	});
	var subtabLiL = $('.sub_tab li').length;
	$('.sub_tab li').width(100/subtabLiL+'%');
	
	//中奖纪录查看更多
	$('.checkmore').bind('click',function(){
		$('.table_rank tr:gt(1)').clone(true).appendTo('.table_rank');
	});
	
	//大转盘
	(function(){
		var p = (function(zz) {
			
			// constants
			var REG = /rotate\((\d+)deg\)/i;
			var STATE_READY = 0, STATE_STARTING = 1, STATE_RUNNING = 2, STATE_STOPPING = 3;
			
			// configuration properties.
			var slide_interval = 30; // 动画间隔，毫秒
			var slide_min_deg = 1,
				slide_max_deg = 20;// 一次转动角度的最小值和最大值
			var slide_change_per_steps = 5;// 几次动画后可以增加或者减少转动角度大小。
			var cycles_on_stop = 1;// 到达停留角度前，以最小的的速度空转几圈。
			
			// internal fields 
			var tid = 0,//间隔id判读,1正常,0 非正常
				state = STATE_READY;
			var deg = 0;
			var slideDeg, slideCount;//旋转度数和次数
			var pointer = $(zz);
			
			// internal functions.
			function start(fn) {
				if (state != STATE_READY) badcall("You can only start it from READY state");
				if (tid) bug("An interval task id should not be found here.");
		
				// reset the slide variants.
				slideDeg = slide_min_deg;//旋转度数1
				slideCount = 0;
				function r() {
					slideCount++;
					if (slideDeg < slide_max_deg && slideCount % slide_change_per_steps == 0) {
						slideDeg++; // increase the slide degree 增加旋转度数 最大20
					}
					
		
					// move to next slide.
					deg = (deg + slideDeg) % 360;
					pointer.css({
						'-webkit-transform': inputDeg(pointer.css('transform'), deg),
						'-moz-transform': inputDeg(pointer.css('transform'), deg),
						'-o-transform': inputDeg(pointer.css('transform'), deg),
						'transform': inputDeg(pointer.css('transform'), deg)
					});
		
					// OK, has gone to the maximum slide degree. 
					//  also means running state.
					if (slideDeg >= slide_max_deg && state != STATE_RUNNING) {
						state = STATE_RUNNING;
						if (fn) fn();
					}
				}
		
				// start it now.
				tid = setInterval(r, slide_interval);
				state = STATE_STARTING;
			}
		
			function stop(stopDeg, fn) {
				if (state == STATE_STOPPING) badcall("We are trying to stop it now.");
				if (state == STATE_READY) badcall("In ready state, do not need to stop it.");
				if (!tid) bug("An interval task id should be found.");
		
				// stop the old task.
				clearInterval(tid);
				tid = 0;
		
				var cyclesBeforeStop = 0;
				function r() {
					slideCount++;
					if (slideDeg > slide_min_deg && slideCount % slide_change_per_steps == 0) {
						slideDeg--; // decrease the slide degree
					}
		
					deg = (deg + slideDeg) % 360;
					pointer.css({
						'-webkit-transform': inputDeg(pointer.css('transform'), deg),
						'-moz-transform': inputDeg(pointer.css('transform'), deg),
						'-o-transform': inputDeg(pointer.css('transform'), deg),
						'transform': inputDeg(pointer.css('transform'), deg)
					});
		
					if (
					// minimize speed, looks like we are going to the the stop point.
					(slideDeg <= slide_min_deg) &&
					// yes, we have arrived the stop point.
					degreeLike(stopDeg, deg, slideDeg) &&
					// 
					(++cyclesBeforeStop >= cycles_on_stop)) {
		
						// clear the task.
						clearInterval(tid);
						tid = 0;
						state = STATE_READY;
		
						if (fn) fn();
					}
		
				}
		
				tid = setInterval(r, slide_interval);
				state = STATE_STOPPING;
			}
			 
			// utility functions
			function inputDeg(s, deg) {
				var degExp = 'rotate(' + deg + 'deg)';
		
				if (!s) return degExp;
		
				if (!s.match(REG)) return degExp;
		
				return s.replace(degExp);
			}
		
			function degreeLike(deg1, deg2, margin) {
				return Math.abs(deg1 - deg2) < margin;
			}
		
			function bug(msg) {
				throw new Error('(BUG)' + msg);
			}
		
			function badcall(msg) {
				throw new Error('(BAD_CALL)' + msg);
			}
		
			return {
		
				// open apis.
				start: function(fn) {
					start(fn);
				},
		
				stop: function(degree, fn) {
					stop(degree, fn);
				},
		
				onReady: function() {
					return state == STATE_READY;
				}
			};
		})('.rotate');
		
		$(document).ready(function() {
			$('.start').click(function() {
				var _this = $('.start');
				if (!p.onReady()) {
					mockAjax(function(degree) {
						p.stop(degree,function() {
							alert("Congratulation! \n\n A big supprise!!! \n\n Try more?");
							_this.prop('disabled', false);
						});
					});
					_this.prop('disabled', true);
				} else {
					p.start();
				}
			});
		});
		
		function mockAjax(fn, timeout) {
			var deg = 270;
			setTimeout(function() {
				fn(deg);
			}, timeout || 1000);
		}
	})();
	
	
	//shake触发
	(function(){
		init();
		// 首先在页面上要监听运动传感事件
		function init(){
		　　if (window.DeviceMotionEvent) {
		　　　　window.addEventListener('devicemotion', deviceMotionHandler, false);// 移动浏览器支持运动传感事件
		　　} 
		}
		var SHAKE_THRESHOLD = 5000;// 首先，定义一个摇动的阀值
		var last_update = 0;// 定义一个变量保存上次更新的时间
		// 紧接着定义x、y、z记录三个轴的数据以及上一次出发的时间
		var x;
		var y;
		var z;
		var last_x;
		var last_y;
		var last_z;
		
		function deviceMotionHandler(eventData) {
		　　var acceleration = eventData.accelerationIncludingGravity;　// 获取含重力的加速度
		　　var curTime = new Date().getTime();　// 获取当前时间
		　　var diffTime = curTime -last_update;
		　　// 固定时间段
		　　if (diffTime > 80) {
		　　　　last_update = curTime;
		　　　　x = acceleration.x;
		　　　　y = acceleration.y;
		　　　　z = acceleration.z;
		　　　　var speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
		　　　　if (speed > SHAKE_THRESHOLD) {
				shake();
				function shake(){
					//定义摇一摇之后的js效果
					$('.start').trigger('click');
				} 
			}
		　　　　last_x = x;
		　　　　last_y = y;
		　　　　last_z = z;
		　　}
		};
	})();
	
	
	//输入框编辑
	$('.inputBox').find('input').keyup(function(){
		$(this).val() ? $(this).siblings('.del').show() : $(this).siblings('.del').hide();
	});
	$('.inputBox').find('.del').click(function(){
		$(this).hide().siblings('input').val('').focus();
	})
});