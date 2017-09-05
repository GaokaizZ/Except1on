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

		var popTopH =$("#"+id).find(".popTOP").height() +10;
		var winHeight = $(window).height() - 242;
		$("#"+id).find(".popInfoBox").height(winHeight - popTopH);
		$("#"+id).find(".popInfoBox").css("padding-top",popTopH);

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

function showTB(cover,id){	
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
	objId.style.top=50+"px";
	objId.style.left=L+"px";
	objId.style.visibility="visible";

		var popTopH =$("#"+id).find(".popTOP").height() +10;
		var winHeight = $(window).height() - 242;
		$("#"+id).find(".popInfoBox").height(winHeight - popTopH);
		$("#"+id).find(".popInfoBox").css("padding-top",popTopH);

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
		objId.style.top=50+"px";
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
//空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
//验证码正则
var vCodeReg=/^\d{6}$/
function reloadValidateCode() {
    document.getElementById("captchaImg").src="${ctx}/getCaptcha?date =" + new Date().getTime();
    //$("#captchaImg").attr("src","${ctx}/getCaptcha?date = " + new Date() + Math.floor(Math.random()*24));
}

function stringToHex(str) {
　　　　var val="";
　　　　for(var i = 0; i < str.length; i++){

　　　　　　if(val == "")
　　　　　　　　val = str.charCodeAt(i).toString(16);
　　　　　　else
　　　　　　　　val += "" + str.charCodeAt(i).toString(16);
　　　　}
　　　　return val;
}


function validateUser(){
	$("#failmess").html("");
	var acc = $("#account").val();
	var pwd = $("#password").val();
	if(trim(acc) == ''){
		$("#failmess").html("请输入登录名");
		return;
	}
	if(trim(pwd) == ''){
		$("#failmess").html("请输入密码");
		return;
	}else{
		//对密码进行加密
		//pwd = base64encode(trim(pwd));
		pwd = stringToHex(trim(pwd));
	}
	$.ajax({
		url : "validateUser",
		type : "post",
		dataType : "json",
		data:"account="+trim(acc)+"&password="+trim(pwd),
		success:function(date){
			//console.info(date);
			if(date.statusCode == "200"){
				if(date.status == "1"){
					show('cover','validateMsg');
				}else if(date.status == "403"){
					$("#account").val('');
					$("#password").val('');
					$("#failmess").html("网络异常！");
				}else if(date.status == "500"){
					$("#account").val('');
					$("#password").val('');
					$("#failmess").html("用户存取错误！");
				}else{
					//$("#failmess").html(date.message);
					$("#account").val('');
					$("#password").val('');
					$("#failmess").html("用户名密码错误！");
				}
			}else{
				$("#account").val('');
				$("#password").val('');
				$("#failmess").html("用户名密码错误！");
			}
		},
		error:function(){
			$("#failmess").html("系统异常，请稍后再试！");
		}
	}); 
}
function validateCode(){
	$("#failmess").html("");
	var acc = $("#account").val();
	var pwd = $("#password").val();
	var phoneCode = $("#phoneCode").val();
	if(trim(acc) == ''){
		alert("请输入登陆名");
		return;
	}
	if(trim(pwd) == ''){
		alert("请输入密码");
		return;
	}
	var reg = new RegExp("^[0-9]*$");  
	if(trim(phoneCode) == '' || !reg.test(phoneCode)){   
        alert("请输入数字!");  
		return;
    }
	$.ajax({
		url : "validateUser",
		type : "post",
		dataType : "json",
		data:"account="+trim(acc)+"&password="+trim(pwd)+"&phoneCode="+trim(phoneCode),
		success:function(date){
			//console.info(date);
			if(date.status == "1"){
				formSubmit();
			}else{
				$("#phoneCode").val('');
				alert("验证码错误");
			}
		},
		error:function(){
			$("#failmess").html("系统异常，请稍后再试！");
		}
	}); 
}
	
function formSubmit(){
	//show('cover','delete');
	$("#failmess").html("");
	var acc = $("#account").val();
	var pwd = $("#password").val();
	if(trim(acc) == ''){
 		$("#failmess").html("请输入登陆名");
		return;
	}
	if(trim(pwd) == ''){
 		$("#failmess").html("请输入密码");
		return;
	}
	$('#loginForm').submit();
}
function sendVCode(){
	
	 $("#failmess").html("");
	var acc = $("#account").val();
	//验证验证码
	if(trim(acc) == ''){
		$("#failmess").html("请输入登陆名");
		return;
	}
	 $.ajax({
			url:"sendMess",
			type:"post",
			dataType : "json",
			data:"account="+trim(acc),
			success:function(date){
				
				if(date.status=="1"){
					document.getElementById("check_start1").disabled=true;
					controlTime1();
				}else if(date.status=="0"){
					$("#failmess").html("登陆名不存在！");
				}else{
					$("#failmess").html("系统异常，请稍后再试！");
				}
				
			},
			error:function(){
				$("#failmess").html("系统异常，请稍后再试！");
			}
		}); 
	
}
function cleanInput(){
	 $('#account').val("");
	 $('#password').val("");
	 $('#phone').val("");
	 $('#captcha').val("");
}
/**
 * JS定时器1
 * @param {Object} o	元素对象
 * @count:				基数值
 * @id:					定时器进程
 */
var count1 = "60";
function controlTime1(o){
	//1定时器内元素状态
	if(count1 >0){
		window.setTimeout(function(){
			count1--;
			document.getElementById("check_start1").value = count1+"秒后重发验证码";
			controlTime1(this);
		},1000); 
	}
	//2定时器解除后元素状态
	else {
		document.getElementById("check_start1").disabled=false;
		document.getElementById("check_start1").value = "点击发送验证码";
		count1 = "60";
	}
	
}