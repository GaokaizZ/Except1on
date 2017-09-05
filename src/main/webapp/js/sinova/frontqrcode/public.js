function show(cover,id){	
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;      
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;		
	
	var objCover=document.getElementById(cover);
	var objId=document.getElementById(id);
	var scrollW=document.documentElement.scrollWidth;
	var scrollH=document.documentElement.scrollHeight;
	if (Sys.safari) {
		var T=(document.documentElement.clientHeight-objId.clientHeight)/2+document.body.scrollTop;
	}else{
		var T=(document.documentElement.clientHeight-objId.clientHeight)/2+document.documentElement.scrollTop;	
	}
	var L=(document.documentElement.clientWidth-objId.clientWidth)/2+document.documentElement.scrollLeft;	
	
	objCover.style.width='100%';
	objCover.style.height=scrollH+"px";
	objCover.style.visibility="visible";	
	objId.style.top=T+"px";
	objId.style.left='5%';
	objId.style.visibility="visible";
	
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