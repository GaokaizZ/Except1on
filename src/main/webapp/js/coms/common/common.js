function myhref(_url) {
	mySubmitForm("springrain_sco_ajax_form", _url);
}

/**
 * 不告诉用户删除结果，直接刷新页面
 * 
 * @param _url
 */
function mydelete(_url) {
	myconfirm("确定删除数据?", function() {
		myhref(_url);
	});
}
/**
 * 列表页面删除，弹框提示用户删除结果，再刷新指定页面,一般为列表
 * 
 * @param _url
 * @param listage
 */
function mydelete(_url, listage, par) {
	myconfirm("确定删除数据?", function() {
		myhref2page(_url, listage, par);
	});
}

/**
 * 批量删除
 * 
 * @param _url
 * @param formId
 * @param listage
 * @returns {Boolean}
 */
function mydeletemore(_url, formId, listage) {
	var arr = new Array();
	var checks = jQuery(":checkbox[name='check_li']");
	checks.each(function(i, _obj) {
		if (_obj.checked) {
			arr.push(_obj.value);
		}

	});
	if (arr.length < 1) {
		myalert("请选择要删除的记录!");
		return false;
	}

	myconfirm("确定删除选中数据?", function() {
		myhref2page(_url, listage, "records=" + arr.join(","));
	});
}

function myexport(formId, _url) {
	var _form = document.getElementById(formId);
	var _action = _form.action;
	_form.action = _url;
	_form.submit();
	_form.action = _action;
}

// 提交FORM
function mySubmitForm(formId, _url, _target) {
	if (typeof(_target) == "undefined" || _target == "") { 
	   _target = "ajax_target";
	}
	
	var _type = jQuery('#' + formId).attr("method");
	if (!_type) {
		_type = "POST";
	}

	if (_url) {
		if (_url.indexOf("?") > 0) {
			_url += "&_=" + new Date().getTime();
		} else {
			_url += "?_=" + new Date().getTime();
		}
		jQuery('#' + formId).ajaxSubmit({
			url : _url,
			type : _type,
			target : "#" + _target
		});
	} else {
		jQuery('#' + formId).ajaxSubmit({
			type : _type,
			target : "#" + _target
		});
	}
}

// 提交修改表单
function commonUpdateForm(form, listurl) {
	myconfirm("确定修改数据吗?", function() {
		
		if (!form) {
			form = "updateForm";
		}
		jQuery.post($('#' + form).attr('action'), $('#' + form).serialize(),
				function(_json) {
			if (_json.status == "success") {
				myalert(_json.message, function() {
					myhref(listurl);
				});
			} else {
				myalert(_json.message);
			}
		});
	});
	
}

// 提交保存表单
function commonSaveForm(form, listurl, _id) {
	myconfirm("确定添加数据吗?", function() {
		
		if (!form) {
			form = "updateForm";
		}
		var id = "#id";
		if (_id) {
			id = "#" + _id;
		}
		
		jQuery(id, jQuery("#" + form)).val("");
		jQuery.post($('#' + form).attr('action'), $('#' + form).serialize(),
			function(_json) {
				if (_json.status == "success") {
					myalert(_json.message, function() {
						myhref(listurl);
					});
				} else {
					myalert(_json.message);
				}
			});
	});
}

// 提交保存表单
function myhref2page(_url, listurl, par) {
	if (!par) {
		par = null;
	}
	jQuery.post(_url, par, function(_json) {
		if (_json.status == "success") {
			myalert(_json.message, function() {
				myhref(listurl);
			});
		} else {
			myalert(_json.message);
		}
	});
}

/**
 * 根据节点ID,选中多个Ztree的节点的复选框
 * 
 * @param nodeIds
 * @param ztreeId
 */
function checkedZtreeNodes(nodeIds, ztreeId) {
	if (nodeIds == null) {
		return;
	}
	var array = nodeIds.split(",");

	if (!array || array.length < 1) {
		return;
	}
	var menuMultiSelectTree = $.fn.zTree.getZTreeObj(ztreeId);
	var tempNodes = menuMultiSelectTree.transformToArray(menuMultiSelectTree
			.getNodes());

	for (var i = 0; i < tempNodes.length; i++) {

		if (jQuery.inArray(tempNodes[i].id, array) >= 0) {
			menuMultiSelectTree.checkNode(tempNodes[i], true, false);
		}
	}
}
/**
 * 选中Ztree的一个节点
 * 
 * @param nodeId
 * @param ztreeId
 */
function selectZtreeOneNode(nodeId, ztreeId) {
	if (nodeId == null) {
		return;
	}

	var orgTree = $.fn.zTree.getZTreeObj(ztreeId);
	var tempNodes = orgTree.transformToArray(orgTree.getNodes());

	for (var i = 0; i < tempNodes.length; i++) {
		if (tempNodes[i].id == nodeId) {
			orgTree.selectNode(tempNodes[i]);
		}
	}
}

/* 赋值 */
function set_val(name, val) {
	if ($("#" + name + " option").length > 0) {
		$("#" + name).val(val);
		return;
	}

	if (($("#" + name).attr("type")) === "checkbox") {
		if (val == 1) {
			$("#" + name).attr("checked", true);
			return;
		}
	}
	if ($("." + name).length > 0) {
		if (($("." + name).first().attr("type")) === "checkbox") {
			var arr_val = val.split(",");
			for ( var s in arr_val) {
				$("input." + name + "[value=" + arr_val[s] + "]").attr("checked", true);
			}
		}
	}
	
	if (($("#" + name).attr("type")) === "datetime") {
		$("#" + name).val(datetimeStampFormat(val));
		return;
	}
	if (($("#" + name).attr("type")) === "text") {
		$("#" + name).val(val);
		return;
	}
	if (($("#" + name).attr("type")) === "hidden") {
		$("#" + name).val(val);
		return;
	}
	if (($("#" + name).attr("rows")) > 0) {
		$("#" + name).val(val);
		return;
	}
}


function add0(m){
	return m < 10 ? ('0' + m) : m
}

// 格式化时间戳，datetimeStamp是整数，否则要parseInt转换
function datetimeStampFormat(datetimeStamp) {
	var stamp = parseInt(datetimeStamp);
	if (isNaN(stamp)) {
		return datetimeStamp;
	}
	var time = new Date(stamp);
	var y = time.getFullYear();
	var m = time.getMonth() + 1;
	var d = time.getDate();
	var h = time.getHours();
	var mm = time.getMinutes();
	var s = time.getSeconds();
	return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
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

function change(id,str,va) {
    document.getElementById(id).value = str.replace(/\D/gi, "");
	var goN = document.getElementById("pageNoId").value;
	if(goN == ''){
		return;
	}
	if(parseInt(goN) > va || parseInt(goN) < 1){
		alert("页码不合法");
	}
}
