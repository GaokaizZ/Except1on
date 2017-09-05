var tapReady = false;
// 兼容pc click事件
var touch = 'click';
if (window.orientation == undefined) {
  tapReady = true;
  $('.ty-phone').width(320);
} else {
  touch = 'tap';
}

// 屏幕滚动时设置tap状态，用于阻止屏幕滚动时误触发tap事件
$(document.body).on('touchstart', function(){
  tapReady = true;
});
$(document.body).on('touchmove', function(){
  tapReady = false;
});

// 设置浏览器区域
var phoneScreenObj = $('.ty-phone');
var phoneWrapObj = $('.ty-screenwrap');
var phoneScreen = {
  width: phoneScreenObj.width(),
  height: window.innerHeight
};
(function(){
  function setScreenSize(){
    var scrollHeight = $('.ty-screen.show').height();
    if (scrollHeight < phoneScreen['height']) {
      scrollHeight = phoneScreen['height'];
    }
    $('.ty-screen').width(phoneScreen['width']);
    $('.ty-phonenum').width(phoneScreen['width']);
    phoneScreenObj.height(scrollHeight);
  }
  setTimeout(setScreenSize, 200);
  window.onorientationchange = function(){
    var phoneLeft = phoneWrapObj.css('left');
    if (phoneLeft == 'auto') {
      phoneLeft = 0;
    } else {
      phoneLeft = parseInt(phoneLeft);
    }
    var phoneLeftIndex = phoneLeft / phoneScreen['width'];
    phoneScreen['width'] = phoneScreenObj.width();
    phoneScreen['height'] = window.innerHeight;
    setScreenSize();
    phoneWrapObj.width(phoneScreen['width'] * $('.ty-screen').length);
    phoneWrapObj.css('left', phoneScreen['width'] * phoneLeftIndex);
  };
})();

// 切换操作类
var screenSlide = function(){
  this.seed = 300;
};
screenSlide.prototype = {
  // 设置浏览器区域高度
  setScreenHeight: function(height){
    var scrollHeight = height;
    if (scrollHeight < window.innerHeight) {
      scrollHeight = window.innerHeight;
    }
    phoneScreenObj.height(scrollHeight);
  },
  // 弹出框
  alert: function(callScreen, msg){
    var alertObj = callScreen.find('.ty-alert');
    if (alertObj.length) {
      var scrollY = window.scrollY || document.scrollTop || 0;
      alertObj.css('paddingTop', scrollY + 40);
      alertObj.removeClass('fn-hide');
      alertObj.width(phoneScreen['width']);
      alertObj.find('.msg').html(msg);
      alertObj.css('opacity', 1);
      setTimeout(function(){
        alertObj.animate({
          opacity: 0
        }, 300, 'ease-out', function(){
          alertObj.addClass('fn-hide');
        });
      }, 1200);
    } else {
      alert(msg);
    }
  },
  // 弹出结果层
  resultAlert: function(callScreen, msg){
    var alertObj = callScreen.find('.ty-resultalert');
    if (alertObj.length) {
      alertObj.removeClass('fn-hide');
      alertObj.width(phoneScreen['width']);
      var alertTop = alertObj.offset().top;
      var alertHeight = callScreen.height();
      if (alertHeight < window.innerHeight) {
        alertHeight = window.innerHeight;
      }
      alertHeight -= alertTop;
      alertObj.height(alertHeight);
      alertObj.find('.msg').html(msg);
      alertObj.css('opacity', 1);
      var screenHref = callScreen.data('href');
      screenHref += '[result]';
      callScreen.data('href', screenHref);
      if (window.scrollY > 0) {
        window.scroll(0, 0);
      }
    } else {
      alert(msg);
    }
  },
  // 确认对话框
  confirm: function(callScreen, msg, callFn, msgtxt) {
    var confirmObj = callScreen.find('.ty-confirm');
    if (confirmObj.length) {
      confirmObj.find('.content').html('');
    } else {
      confirmObj = $('<div class="ty-confirm"><div class="content"></div></div>');
      callScreen.prepend(confirmObj);
    }
    confirmObj.removeClass('fn-hide');
    confirmObj.width(callScreen.width());
    var confirmHeight = callScreen.height();
    if (confirmHeight < window.innerHeight) {
      confirmHeight = window.innerHeight;
    }
    confirmObj.height(confirmHeight);
    var scrollY = window.scrollY || document.scrollTop || 0;
    confirmObj.find('.content').append('<div class="msg">' + msgtxt + '<p class="protitle">' + msg + '</p></div>');
    confirmObj.find('.content').append('<div class="btn_wrap"><span class="btn_cancel"><em class="ty-comicon"></em>取消操作</span><span class="btn_submit"><em class="ty-comicon"></em>确定操作</span></div>');
    confirmObj.css('paddingTop', (window.innerHeight - confirmObj.find('.content').height()) / 2 + scrollY);
    // 确定操作
    confirmObj.find('.btn_submit').on(touch, function(){
      //callFn();
      confirmObj.addClass('fn-hide');
    });
    // 取消操作
    confirmObj.find('.btn_cancel').on(touch, function(){
      confirmObj.addClass('fn-hide');
    });
  },
  // 绑定操作按钮
  call: function(callScreen){
    var _this = this;
    // 设置背景
    callScreen.find('.ty-screen-content').addClass('nobg');
    // 输出手机号
    if (callScreen.find('.ty-phonenum-place').length) {
      if (callScreen.find('.ty-phonenum').length == 0) {
       // callScreen.append('<div class="ty-phonenum"><span>13309876789</span><em class="ty-comicon"></em></div>');
        callScreen.find('.ty-phonenum').width(phoneScreen['width']);
      }
    }
    // 绑定上一页
    callScreen.find('.j-call-prev').on(touch, function(){
      if (tapReady){
        var thisScreen = $(this).parents('.ty-screen');
        var href = $(this).data('href');
        if (href) {
          screenFn.toPrev(thisScreen, href);
        }
      }
    });
    // 绑定下一页
    callScreen.find('.j-call-next').on(touch, function(){
      if (tapReady){
        var thisScreen = $(this).parents('.ty-screen');
        var href = $(this).data('href');
        if (href) {
          screenFn.toNext(thisScreen, href);
        }
      }
    });
    // 跳转链接
    callScreen.find('.j-call-url').on(touch, function(){
      if (tapReady){
        var href = $(this).data('href');
        if (href) {
          location.href = href;
        }
      }
    });
    // 绑定触发选中
    callScreen.find('.j-call-current').on(touch, function(){
      if (tapReady){
        var thisScreen = $(this).parents('.ty-screen');
        var target = $(this).parent();
        if ($(this).data('rmcurrent')) {
          if (target.hasClass('current')) {
            target.removeClass('current');
          } else {
            thisScreen.find($(this).data('rmcurrent')).removeClass('current');
            target.addClass('current');
          }        
        } else {
          target.toggleClass('current');
        }
        _this.setScreenHeight(thisScreen.find('.ty-screen-content').height());
      }
    });
    // 绑定单选
    callScreen.find('.j-call-radio span').on(touch, function(){
      if (tapReady){
        var target = $(this).parent();
        target.find('span').removeClass('current');
        $(this).addClass('current');
        var val = $(this).data('value');
        target.find('input').val(val);
        if (target.data('show')) {
          $(target.data('show')).html($(this).data('show'));
        }
        // 定制
        if ($('.j-call-money').length) {
          $('.j-call-money').val('');
        }
      }
    });
    // 绑定清空
    callScreen.find('.j-call-clear').on(touch, function(){
      if (tapReady){
        var clear = $(this).data('clear');
        if ($(clear)[0].tagName.toLowerCase() == 'input') {
          $(clear).val('');
        } else {
          $(clear).html('');
        }        
      }
    });
    // 验证码
    callScreen.find('.j-call-yzm').on(touch, function(){
      var target = $(this);
      if (tapReady && !target.hasClass('disable')){
        target.addClass('disable');
        $.post('yzm.html', function(response){
          // if (response.state) {
            // 验证码发送成功
            var djsTime = 60;
            function djs(){
              djsTime --;
              target.find('span').html(djsTime + '秒');
              if (djsTime > 0) {
                setTimeout(djs, 1000);
              } else {
                target.removeClass('disable');
                target.find('span').html('重新获取');
              }
            }
            djs();
          /*/} else {
            _this.alert(callScreen, '验证码获取失败，可能由服务器繁忙导致，请稍后再试！');
            target.removeClass('disable');
            target.find('span').html('重新获取');
          }*/
        });
      }
    });
    // 定制
    callScreen.find('.j-call-money').focus(function(){
      $('.ty-radio span.current').removeClass('current');
      $('.j-input-money').val('0');
    });
	callScreen.find('.j-zxtc-con ul li ').tap(function(){
														   $('.j-zxtc-con ul li').removeClass("active");
 															$(this).addClass("active");})
	
   
  },
  // 绘制屏幕
  drawScreen: function(newScreen, screenHref){
    var _this = this;
    newScreen.html('<div class="ty-screen-content"></div>');
    newScreen.data('href', screenHref);
    $.get(screenHref, function(response){
      var screenHtml = $(response);
      var state = true;
      screenHtml.each(function(){
        if (state && $(this).hasClass('ty-phone')) {
          var getScreen = $(this).find('.ty-screen.show');
          if (getScreen.length) {
            state = false;
            newScreen.find('.ty-screen-content').html(getScreen.find('.ty-screen-content').eq(0).html());
            _this.setScreenHeight(newScreen.find('.ty-screen-content').height());
            _this.call(newScreen);
          }
        }
      });
      if (state) {
        newScreen.find('.ty-screen-content').html('404');
      }
    })
    return newScreen;
  },
  // 切换到前页
  toPrev: function(thisScreen, screenHref){
    var _this = this;
    // 获取上一屏
    var prevScreen = thisScreen.prev();
    if (prevScreen.length < 1) {
      // 创建上一屏
      prevScreen = $('<div class="ty-screen"></div>');
      thisScreen.before(prevScreen);
      prevScreen.width(phoneScreen['width']);
      phoneWrapObj.width(phoneScreen['width'] * $('.ty-screen').length);
      phoneWrapObj.css('left', -phoneScreen['width']);
      this.drawScreen(prevScreen, screenHref);
    } else {
      screenHref = prevScreen.data('href');
    }
    // 滑动前的准备：“还原现场”
    var prevTop = prevScreen.css('paddingTop');
    if (prevTop == null || prevTop == 'auto') {
      prevTop = 0;
    } else {
      prevTop = parseInt(prevTop);
    }
    var phoneTop = phoneWrapObj.css('top');
    if (phoneTop == 'auto') {
      phoneTop = 0;
    } else {
      phoneTop = parseInt(phoneTop);
    }
    var scrollY = - phoneTop - prevTop;
    phoneScreenObj.height(window.innerHeight + scrollY);
    phoneWrapObj.css('top', -prevTop);
    window.scroll(0, scrollY);
    // 预备上一屏状态
    prevScreen.addClass('show');
    prevScreen.find('.ty-screen-content').css('minHeight', window.innerHeight);
    // 开始滚动
    if (history.replaceState) {
      history.replaceState(null, '中国移动', screenHref);
    }
    var phoneLeft = phoneWrapObj.css('left');
    if (phoneLeft == 'auto') {
      phoneLeft = 0;
    } else {
      phoneLeft = parseInt(phoneLeft);
    }
    phoneLeft += phoneScreen['width'];
    phoneWrapObj.animate({
      left: phoneLeft
    }, _this.seed, 'ease-out', function(){
      // 设置滚动后状态
      thisScreen.removeClass('show');
      _this.setScreenHeight(prevScreen.find('.ty-screen-content').height());
    });
  },
  // 切换到下页
  toNext: function(thisScreen, screenHref){
    var _this = this;
    // 获取下一屏
    var nextScreen = thisScreen.next();
    if (nextScreen.length < 1) {
      // 创建下一屏
      nextScreen = $('<div class="ty-screen"></div>');
      thisScreen.after(nextScreen);
      nextScreen.width(phoneScreen['width']);
      phoneWrapObj.width(phoneScreen['width'] * $('.ty-screen').length);
      this.drawScreen(nextScreen, screenHref);
    } else {
      // 清除下一屏内容
      if (nextScreen.data('href') != screenHref) {
        nextScreen.html('');
        this.drawScreen(nextScreen, screenHref);
      }
    }
    // 滑动前的准备：创建“类似截屏”
    var scrollY = window.scrollY || document.scrollTop || 0;
    var phoneTop = phoneWrapObj.css('top');
    if (phoneTop == 'auto') {
      phoneTop = 0;
    } else {
      phoneTop = parseInt(phoneTop);
    }
    phoneTop -= scrollY;
    phoneWrapObj.css('top', phoneTop);
    phoneScreenObj.height(window.innerHeight);
    // 设置下屏到预备位置
    nextScreen.addClass('show');
    nextScreen.css('paddingTop', -phoneTop);
    nextScreen.find('.ty-screen-content').css('minHeight', window.innerHeight);
    // 开始滚动
    if (history.replaceState) {
      history.replaceState(null, '中国移动', screenHref);
    }
    var phoneLeft = phoneWrapObj.css('left');
    if (phoneLeft == 'auto') {
      phoneLeft = 0;
    } else {
      phoneLeft = parseInt(phoneLeft);
    }
    phoneLeft -= phoneScreen['width'];
    phoneWrapObj.animate({
      left: phoneLeft
    }, _this.seed, 'ease-out', function(){
      // 设置滚动后状态
      if (window.orientation != undefined) {
        thisScreen.removeClass('show');
        _this.setScreenHeight(nextScreen.find('.ty-screen-content').height());
      }
    });
  },
  // 刷新当前页
  toNow: function(thisScreen, screenHref){
    this.drawScreen(thisScreen, screenHref);
    thisScreen.removeClass('show');
    this.setScreenHeight(thisScreen.find('.ty-screen-content').height());
  }
};
var screenFn = new screenSlide();
screenFn.call($('.ty-screen'));

//弹出层
function show(cover,id){	
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;      
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 
	(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;		
	//如果是ie6，隐藏页面select
	if(Sys.ie=="6.0"){
		var n=document.getElementsByTagName("select").length;
		var m=document.getElementById(id).getElementsByTagName("select").length;
		for(var i=0;i<n;i++){
			document.getElementsByTagName("select")[i].style.display= 'none';}
		for(var j=0;j<m;j++){		
			document.getElementById(id).getElementsByTagName("select")[j].style.display= '';}
	}	
	var objCover=document.getElementById(cover);
	var objId=document.getElementById(id);
	var scrollW=document.documentElement.scrollWidth;
	var scrollH=document.documentElement.scrollHeight;
	if (Sys.safari || Sys.chrome){
		var scrollH=document.body.scrollHeight;
		var T=(document.documentElement.clientHeight-objId.clientHeight)/2+document.body.scrollTop;
	}else{
		var T=(document.documentElement.clientHeight-objId.clientHeight)/2+document.documentElement.scrollTop;}
	var L=(document.documentElement.clientWidth-objId.clientWidth)/2+document.documentElement.scrollLeft;	
	objCover.style.width=scrollW+"px";
	objCover.style.height=scrollH+"px";
	objCover.style.visibility="visible";	
	objId.style.top=T+"px";
	objId.style.left=L+"px";
	objId.style.visibility="visible";
		
	window.onresize=function (){	
		var objCover=document.getElementById(cover);
		var objId=document.getElementById(id);
		var scrollW=document.documentElement.scrollWidth;
		if(document.documentElement.clientHeight >= document.documentElement.scrollHeight){
			var scrollH=document.documentElement.clientHeight;	
		}else{
			var scrollH=document.documentElement.scrollHeight;}
		if (Sys.safari || Sys.chrome) {
			var T=(document.documentElement.clientHeight-objId.clientHeight)/2+document.body.scrollTop;
		}else{
			var T=(document.documentElement.clientHeight-objId.clientHeight)/2+document.documentElement.scrollTop;}
		var L=(document.documentElement.clientWidth-objId.clientWidth)/2+document.documentElement.scrollLeft;		
		objCover.style.width=scrollW+"px";
		objCover.style.height=scrollH+"px";		
		objId.style.top=T+"px";
		objId.style.left=L+"px";
	}
}
function hide(cover,id){
	//将页面全部select换件设为可用状态
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;    
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;	
	if(Sys.ie=="6.0"){
		var n=document.getElementsByTagName("select").length;
		for(var i=0;i<n;i++){
			document.getElementsByTagName("select")[i].style.display= '';
		}
	}
	var objCover=document.getElementById(cover);
	var objId=document.getElementById(id);
	objCover.style.visibility="hidden";
	objId.style.visibility="hidden";
}





