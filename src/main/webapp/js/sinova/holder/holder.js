/**
 * holder 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-04-20
 */

jQuery(document).ready(function(){
    //增加全选事件
	$(".sub_left_menu tbody tr td label").click(function() {
		$(".sub_left_menu tbody tr.active").removeClass("active");
		$(this).parent().parent().attr("class", "active");		
		var _url = ctx + "/goods/look/json?id=" + $(this).parent().parent().attr("id");
		jQuery.ajax({
			url : _url,
			type : "post",
			dataType : "json",
			success : function(_json) {
				if(_json.status=="success"){
					showdata(_json);
				}
			}
		});
		return false;
	});
	
	$("#ids").click(function(){
		if($(this).prop("checked")){
			$("input[name='check_li']").each(function(){ 
				 $(this).prop("checked","true");
			}) 
		}else{
			$("input[name='check_li']").each(function(){ 
				$(this).removeAttr("checked");
			}) 
		}
	});
	
	$("input[name='check_li']").click(function(){
		var flag=$(this).prop("checked");
		if(flag){
			flag=checkCheckBox(flag);
			if(flag){
				$("#ids").prop("checked","true");
			}
		}else{
			$("#ids").removeAttr("checked");
		}
	});
});

function checkCheckBox(flag) {
	if(flag){
		$("input[name='check_li']").each(function(){
			if(!$(this).prop("checked")){
				return flag=false;
			}
		})
	}else{
		$("input[name='check_li']").each(function(){
			if($(this).prop("checked")){
				return flag=true;
			}
		})
	}
	return flag;
}

function showdata(result) {

	for (var s in result.data) {
		set_val(s, result.data[s]);
	}
}

// 删除一条
/*
function deleteHolder() {
	var id = jQuery("#id").val();
	if (!id || id == "") {
		myalert("请选择你要删除的记录");
		return;
	} else {
		var _url = ctx + "/holder/delete?id=" + id;
		var listurl = ctx + "/holder/list";
		mydelete(_url, listurl);
	}
}

// 删除多条
function deleteMore(){
	var records="";
	$("input[name='check_li']:checkbox:checked").each(function(){ 
		records+=$(this).val()+",";
	}) 
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
	var url = ctx + "/holder/delete/more";
	
		jQuery.get(url, "records=" + records, function(data){
            alert("恭喜你，删除成功！");
            $("#searchForm").attr("action",ctx+"/holder/list");
            $("#searchForm").submit();
        });
}
*/

//跳转到添加页面
function beforeAdd(){
	var url = ctx + "/holder/gotoAdd";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}
//跳转到编辑页面
function beforeUpdate(id){
	
	var url = ctx + "/holder/beforeUpdate?id="+id;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}

//汉字和字母
function checkCE(obj){
	var regStr = /^[A-Za-z\u4e00-\u9fa5]+$/;
	if(regStr.test(obj)){
		return true;
	}else{
		return false;
	}
}
//数字和字母
function checkME(obj){
	var regStr = /^[A-Za-z0-9]*$/;
	if(regStr.test(obj)){
		return true;
	}else{
		return false;
	}
}
//验证电话
function checkPhone(obj) {
     var regTelePhone = /^1[3|4|5|8][0-9]\d{8}$/;
     if (regTelePhone.test(obj)) {
          return true;
     } else {
          return false;
     }
}

function saveHolder(){
	var sourceNo = $("#sourceNo").val();
	var name = $("#name").val();
	var token = $("#token").val();
	var finder = $.trim($("#finder").val());
	var finderPhone = $("#finderPhone").val();
	var state = $("#state").val();
	if(""==sourceNo||sourceNo==null){
		alert("厂商编码不能为空！");
		$("#name").focus();
		return;
	}
	if(!checkME(sourceNo)){
		alert("厂商编码请使用数字和字母");
		$("#sourceNo").focus();
		return;
	}
	if(""==name||name==null){
		alert("厂商名称不能为空！");
		$("#name").focus();
		return;
	}
	if(!checkCE(name)){
		alert("厂商名称请使用汉字和字母");
		$("#name").focus();
		return;
	}
	if(""==token||token==null){
		alert("key不能为空！");
		$("#token").focus();
		return;
	}
	if(!checkME(token)){
		alert("key请使用数字和字母");
		$("#token").focus();
		return;
	}
	if(""==finder||finder==null){
		alert("联系人不能为空！");
		$("#finder").focus();
		return;
	}
	if(!checkCE(finder)){
		alert("联系人请使用汉字和字母");
		$("#finder").focus();
		return;
	}
	if(""==finderPhone||finderPhone==null){
		alert("联系人电话不能为空！");
		$("#finderPhone").focus();
		return;
	}
	if(!checkPhone(finderPhone)){
		alert("联系电话输入有误！请重新输入！");
		$("#finderPhone").focus();
		return;
	}
	if(""==state||state==null){
		alert("请选择状态！");
		$("#state").focus();
		return;
	}
	var _url = ctx + "/holder/save";
	$.ajax({
		url:_url,
		type:"post",
		dataType:"json",
		data:"sourceNo="+sourceNo+"&state="+state+"&name=" + name+"&token="+token+"&finder="+finder+"&finderPhone="+finderPhone,
		success	: function(_json){
	        if(_json.status == "success"){
	        	alert(_json.message);
	        	window.location.href = ctx + "/holder/list";
	        }else{
	        	alert(_json.message);
	        	return;
	        }
	    }
  });
}

function updateHolder(){
	var id=$.trim($("#id").val());
	var createDate=$.trim($("#createDate").val());
	var sourceNo = $("#sourceNo").val();
	var name = $("#name").val();
	var token = $("#token").val();
	var finder = $.trim($("#finder").val());
	var finderPhone = $("#finderPhone").val();
	var state = $("#state").val();
	
	if(""==sourceNo||sourceNo==null){
		alert("厂商编码不能为空！");
		$("#name").focus();
		return;
	}
	if(!checkME(sourceNo)){
		alert("厂商编码请使用数字和字母");
		$("#sourceNo").focus();
		return;
	}
	if(""==name||name==null){
		alert("厂商名称不能为空！");
		$("#name").focus();
		return;
	}
	if(!checkCE(name)){
		alert("厂商名称请使用汉字和字母");
		$("#name").focus();
		return;
	}
	if (""!=token||token!=null) {
		if(!checkME(token)){
			alert("key请使用数字和字母");
			$("#token").focus();
			return;
		}
	}
	if(""==finder||finder==null){
		alert("联系人不能为空！");
		$("#finder").focus();
		return;
	}
	if(!checkCE(finder)){
		alert("联系人请使用汉字和字母");
		$("#finder").focus();
		return;
	}
	if(""==finderPhone||finderPhone==null){
		alert("联系人电话不能为空！");
		$("#finderPhone").focus();
		return;
	}
	if(!checkPhone(finderPhone)){
		alert("联系电话输入有误！请重新输入！");
		$("#finderPhone").focus();
		return;
	}
	if(""==state||state==null){
		alert("请选择状态！");
		$("#state").focus();
		return;
	}
	var _url = ctx + "/holder/update";
	$.ajax({
		url:_url,
		type:"post",
		dataType:"json",
		data:"id="+id+"&sourceNo="+sourceNo+"&state="+state+"&name=" + name+"&token="+token+"&finder="+finder+"&finderPhone="+finderPhone+"&createDate="+createDate,
		success	: function(_json){
	        if(_json.status == "success"){
	        	alert(_json.message);
	        	window.location.href = ctx + "/holder/list";
	        }else{
	        	alert(_json.message);
	        	return;
	        }
	    }
  });
}

//重置按钮(添加)
function resetAdd(){
	$("#sourceNo").val("");
	$("#name").val("");
	$("#token").val("");
	$("#finder").val("");
	$("#finderPhone").val("");
	$("#state").val("");
}
//重置按钮(修改)
function resetUpdate(){
	$("#name").val("");
	$("#token").val("");
	$("#finder").val("");
	$("#finderPhone").val("");
	$("#state").val("");
}
//重置按钮(查询)
function resetVal(){
	$("#name_").val("");
	$("#sourceNo_").val("");
	$("#state_").val("");
}
//查询
function mySubmitForm(formId,_target){
	//分页
	if (typeof(_target) == "undefined" || _target == "") { 
	   
		}else{
			$("#pageIndex").val("1");
		}
	$("#"+formId).submit();
}


