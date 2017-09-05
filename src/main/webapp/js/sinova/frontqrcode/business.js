//手机正则
var mobileReg =/^(13[0-9]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|4|6|7|8|9]|14[0-9]|17[8])\d{8}$/;
//验证码正则
var vCodeReg=/^\d{6}$/

function upback(){
	var param = $("#param").val();
	window.location.href = "/qrcodehall/qrcodedealing/forBusiness?param="+param;
}

function goLottery(){
	var flowNo = $("#param").val();
	var phone = $("#phone").val();
	var acceptId = $("#acceptId").val();
	var opId = $("#opId").val();
	window.location.href = "/qrcodehall/qrcodedealing/beforeLottery?flowNo="+flowNo+"&phone="+phone+"&acceptId="+acceptId+"&opId="+opId;
}

function upback2(){
	var param = $("#param").val();
	var busType = $("#busType").val();
	if (busType!="200") {
		busType = "4g"
	}
	window.location.href = "/qrcodehall/qrcodedealing/businessList?flowNo="+param+"&busType="+busType;
}

//空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
//业务办理详情
function businessDetail(goodsId,busCode,feeCode,opId,busType){
	var param = "goodsId:"+goodsId+",busCode:"+busCode+",feeCode:"+feeCode+",opId:"+opId+",busType:"+busType;
	var _url = "/qrcodehall/qrcodedealing/businessDetail?param="+stringToHex(param);
	window.location.href = _url;
//	$("#goodsId").val(goodsId);
//	$("#busCode").val(busCode);
//	$("#feeCode").val(feeCode);
//	$("#opId").val(opId);
//	$("#busType").val(busType);
//	$("#submitForm").submit();
}

function businessDetail2(busCode,opId,busType,flowNo){
	var param = "busCode:"+busCode+",opId:"+opId+",busType:"+busType+",flowNo:"+flowNo;
	var _url = "/qrcodehall/qrcodedealing/businessDetail?param="+stringToHex(param);
	window.location.href = _url;
//	$("#busCode").val(busCode);
//	$("#opId").val(opId);
//	$("#busType").val(busType);
//	$("#submitForm").submit();
}

/*
 * 跳转到活动列表页
 */
function toBusList(busType,flowNo,param,wapUrl) {
	if(busType=="wapgouji" || busType=="4ghuanka"){
		var paramMap = toMap(param);
		var urlMap = toMap(wapUrl);
		var href = urlMap[busType]+"="+paramMap[busType];
//		alert(href);
		window.location.href = href;
	}else{
		window.location.href = "/qrcodehall/qrcodedealing/businessList?busType="+busType+"&flowNo="+flowNo;
	}
}

function toMap(param){
	var map={};
	var a = param.split(",");
	for (var i=0;i<a.length;i++) {
		var b = a[i];
		var c = b.split("=");
		map[c[0]]=c[1];
	}
	return map;
}

//办理业务
function dealingBusiness(goodsId,busCode,feeCode,opId){
//	var _url = "/qrcodehall/qrcodedealing/dealingBusiness?goodsId="+goodsId+"&busCode="+busCode+"&feeCode="+feeCode+"&opId="+opId;
//	window.location.href = _url;
	$("#submitForm").submit();
}

//办理营销活动
function dealingYxBusiness(busCode,feeCode){
	var goodsBS = $("input[type='radio']:checked").val();
	if (goodsBS==null || goodsBS=='') {
		alert("请选择档次");
	}
	var arr = new Array();
	arr = goodsBS.split("+");
	$("#goodsId").val(arr[0]);
	$("#feeCode").val(arr[1]);
	$("#submitForm").submit();
}

//验证手机号
function valetPhone(){
	 $("#failmess").html();
	var phone = $("#mobilePhone").val();
	if(trim(phone)==""){
		 $("#failmess").html("请输入手机号码");
		return;
	}
	$("#phone").val(phone);
	
	if(!mobileReg.test(trim(phone))){
		$("#failmess").html("手机号码输入不合法");
		return;
	}
//	var _url = "/qrcodehall/qrcodedealing/valetPhone?type=0&phone="+phone+"&opId="+$("#opId").val()+"&goodsId="+$("#goodsId").val()+"&busCode="+$("#busCode").val()+"&feeCode="+$("#feeCode").val();
	 $.ajax({
			url:"/qrcodehall/qrcodedealing/checkPhone",
			type:"post",
			dataType : "json",
			data:"mobile="+trim(phone),
			success:function(date){
				
				if(date.status=="1"){
//					window.location.href = _url;
					$("#submitForm").submit();
				}else if(date.status=="0"){
					$("#failmess").html("请输入山西移动手机号");
				}else if(date.status=="-1"){
					$("#failmess").html("系统异常，请稍后再试！");
				}else{
					$("#failmess").html("系统异常，请稍后再试！");
				}
				
			},
			error:function(){
				$("#failmess").html("系统异常，请稍后再试！");
			}
		}); 
}

//填写营业员工号
function fillCode(phone){
	var code = $("#traderCode").val();
	var _url = "/qrcodehall/qrcodedealing/fillCode?code="+code+"&phone="+phone;
	window.location.href = _url;
}

//验证手机号办理业务
function submitBusiness(phone){
	 $("#failmess").html();
	var vCode = $("#vCode").val();
	//验证验证码
	if(!vCodeReg.test(trim(vCode))){
		$("#failmess").html("验证码输入不合法");
		return;
	}
//	var _url = "/qrcodehall/qrcodedealing/beforeSubmitBusiness?phone="+phone+"&opId="+$("#opId").val()+"&goodsId="+$("#goodsId").val()+"&busCode="+$("#busCode").val()+"&feeCode="+$("#feeCode").val();
	 $.ajax({
			url:"/qrcodehall/qrcodedealing/checkVCode",
			type:"post",
			dataType : "json",
			data:"vCode="+trim(vCode),
			success:function(date){
				
				if(date.status=="1"){
//					window.location.href = _url;
					$("#submitForm").attr("action","/qrcodehall/qrcodedealing/beforeSubmitBusiness");
					$("#submitForm").submit();
				}else if(date.status=="0"){
					$("#failmess").html("验证码输入错误！");
				}else if(date.status=="-1"){
					$("#failmess").html("系统超时，请重新获取验证码！");
				}else{
					$("#failmess").html("系统异常，请稍后再试！");
				}
				
			},
			error:function(){
				$("#failmess").html("系统异常，请稍后再试！");
			}
		}); 
}

//验证手机号
function checkMobile(mobile){
	if(!mobileReg.test(trim(mobile))){
		alert("手机号码输入不合法");
		return;
	}
}

//数字判断
function IsNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
    } else {
        if (window.event) {
            window.event.returnValue = false;
        }
        else {
            e.preventDefault(); //for firefox 
        }
    }
} 

function change(id,str) {
    document.getElementById(id).value = str.replace(/\D/gi, "");
}

//发送手机验证码
function reSend(phone) {
//	var _url = "/qrcodehall/qrcodedealing/valetPhone?type=1&phone="+phone+"&opId="+$("#opId").val()+"&goodsId="+$("#goodsId").val()+"&busCode="+$("#busCode").val()+"&feeCode="+$("#feeCode").val();
//	window.location.href = _url;
	$("#submitForm").attr("action","/qrcodehall/qrcodedealing/valetPhone");
	$("#submitForm").submit();
}

//确认办理业务
function goBusiness(){
//	var _url = "/qrcodehall/qrcodedealing/submitBusiness?phone="+$("#phone").val()+"&opId="+$("#opId").val()+"&goodsId="+$("#goodsId").val()+"&busCode="+$("#busCode").val()+"&feeCode="+$("#feeCode").val();
	hide('cover','tcc');
//	window.location.href = _url;
	$("#submitForm").submit();
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
//一键办理
function goRelation(flag){
	var records="";
	$("input[name='check_li']:checked").each(function(){ 
		records+=$(this).val()+",";
	})
	
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
	$("#submitForm").attr("action","/qrcodehall/qrcodedealing/valetPhone?goodsId="+records+"&flag="+flag);
	$("#submitForm").submit();
}
//验证手机号办理业务
function submitRelation(phone){
	 $("#failmess").html();
	var vCode = $("#vCode").val();
	//验证验证码
	if(!vCodeReg.test(trim(vCode))){
		$("#failmess").html("验证码输入不合法");
		return;
	}
	 $.ajax({
			url:"/qrcodehall/qrcodedealing/checkVCode",
			type:"post",
			dataType : "json",
			data:"vCode="+trim(vCode),
			success:function(date){
				
				if(date.status=="1"){
					$("#submitForm").attr("action","/qrcodehall/qrcodedealing/submitBusiness");
					$("#submitForm").submit();
				}else if(date.status=="0"){
					$("#failmess").html("验证码输入错误！");
				}else if(date.status=="-1"){
					$("#failmess").html("系统超时，请重新获取验证码！");
				}else{
					$("#failmess").html("系统异常，请稍后再试！");
				}
				
			},
			error:function(){
				$("#failmess").html("系统异常，请稍后再试！");
			}
		}); 
}
