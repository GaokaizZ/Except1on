jQuery("document").ready(function() {
	ajaxmenu();
});

function mynavhref(_url, _id, _name) {
//	jQuery("a[id^='href_']").parent("li").removeClass("active");
//	var _li = jQuery("#href_" + _id).parent("li");
//	_li.addClass("active");

	myhref(_url);

//	jQuery("#johnNav").html('当前位置<i class="ace-icon fa fa-angle-double-right"></i> ' + _name);
}

/**
 * 获取所有导航资源
 */
function ajaxmenu() {
	var token = jQuery("#tokenId_").val();
	jQuery.ajax({
		url : ctx + "/system/menu/leftMenu",
		type : "post",
		cache : false,
		async : false,
		data : {'token' : token},
		scriptCharset : "utf-8",
		dataType : "json",
		success : function(_json) {
			if(_json.status=="success"){
				buildModule(_json.data);
			}
		}
	});
}

function buildModule(data) {
	if (data != null && typeof (data) != "undefined") {
		for ( var i = 0; i < data.length; i++) {
			var html = getParentModule(data[i]);
			jQuery("#module").append(html);
		}
		//console.info(jQuery("#module").html())
		//jQuery("#module").html("<li class='leftTree'><span name='null'> 基础管理 </span><ul class='undis'><li class='leftTree'><span name='/qrcodehall/null'> 区域管理 </ span > <ul class='dis'><li class='leftTree'><span name='bms/qrcodehall/wdgl/ggwgl.html'>广告位管理</span></li><li class='leftTree'><span name='bms/qrcodehall/wdgl/ywgl.html'>业务管理</span></li></ul></li><li class='leftTree'><span name='/qrcodehall/system/user/list'> 用户管理 </span></li></ul></li><li class='leftTree'><span name='null'> 字典管理 </span><ul class='undis'><li class='leftTree'><span name='/qrcodehall/system/menu/list'> 菜单管理 </span></li></ul></li><li class='leftTree'><span name='null'> 系统管理 </span><ul class='undis'><li class='leftTree'><span name='/qrcodehall/system/role/list'> 角色管理 </span></li></ul></li>");
	}
}

function getParentModule(json) {
	var _leaf = json["leaf"];
	var t = '<li class="leftTree" >';
	t = t + '<span name="null">' + json["name"] + '</span>';
	if (_leaf && _leaf.length > 0) {
		var m = "<ul class='undis'>";
		for ( var i = 0; i < _leaf.length; i++) {
			var n = getChindModule(_leaf[i], '');
			m = m + n;
		}
		t = t + m + "</ul>";
	} 
	t = t + "</li>";
	return t;
}

function getChindModule(json, html) {
	var _state = json["state"];
//	console.info("还有节点" + json["name"]);
	var _leaf = json["leaf"];
	var t = '<li class="leftTree"><span ';
	t = t + 'name="' + ctx + json["pageurl"] +'" >' + json["name"] + '</span>';
	if (_leaf && _leaf.length > 0) {
		var m = "<ul class='undis'>";
		for ( var i = 0; i < _leaf.length; i++) {
//			console.info(_leaf[i]);
			//var n = getChindModule(_leaf[i], '');
			var n = getNoteModule(_leaf[i]);
			m = m + n;
		}
		m = m + "</ul>";
		t = t+m;
	}
	t = t+ '</li>' 
	html = html + t;
	return html;
}

function getNoteModule(json) {
	var _leaf = json["leaf"];
	var t = '<li class="leftTree" >';
	var t = '<li class="leftTree"><span ';
	t = t + 'name="' + ctx + json["pageurl"] +'" >' + json["name"] + '</span>';
	if (_leaf && _leaf.length > 0) {
		var m = "<ul class='undis'>";
		for ( var i = 0; i < _leaf.length; i++) {
			var n = getChindModule(_leaf[i], '');
			m = m + n;
		}
		t = t + m + "</ul>";
	} 
	t = t + "</li>";
	return t;
}

function openUrl(_url) {
	if (_url == "####") {
		return;
	}
	window.location.href = _url;
}

function openUrlctx(_url) {
	if (_url == "####") {
		return;
	}
	window.location.href = ctx + _url;
}

