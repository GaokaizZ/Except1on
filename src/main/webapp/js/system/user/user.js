jQuery(document).ready(function() {
	$("#ids").click(function() {
		if ($(this).prop("checked")) {
			$("input[name='check_li']").each(function() {
				$(this).prop("checked", "true");
			})
		} else {
			$("input[name='check_li']").each(function() {
				$(this).removeAttr("checked");
			})
		}
	});

	$("input[name='check_li']").click(function() {
		var flag = $(this).prop("checked");
		if (flag) {
			flag = checkCheckBox(flag);
			if (flag) {
				$("#ids").prop("checked", "true");
			}
		} else {
			$("#ids").removeAttr("checked");
		}
	});
});
function checkCheckBox(flag) {
	if (flag) {
		$("input[name='check_li']").each(function() {
			if (!$(this).prop("checked")) {
				return flag = false;
			}
		})
	} else {
		$("input[name='check_li']").each(function() {
			if ($(this).prop("checked")) {
				return flag = true;
			}
		})
	}
	return flag;
}

var user_org_modal=null;
function showOrgModal(){
	if(user_org_modal != null){
		user_org_modal.show();
		return;
	}
	
	user_org_modal=$.scojs_modal({
		  title: '选择组织',
		  remote: "${ctx}/system/org/tree/pre",
		  uuid:"orgCheckBoxTree",
		  fun_confirm:"selectOrgVal"
		  
		});
	user_org_modal.show();
}


function selectOrgVal(uuid){

	var orgCheckBoxTree = $.fn.zTree.getZTreeObj(uuid);
	var nodes = orgCheckBoxTree.getCheckedNodes(true);
	if(nodes.length < 1){
		myalert("请选择一个组织!");
		return;
	}
	var orgNames="";
	var orgIds="";
	jQuery(nodes).each(function(i,_obj){
		orgNames+=_obj.name+",";
		orgIds+=_obj.id+",";
	});
	
	jQuery("#orgNames").val(orgNames);
	jQuery("#orgIds").val(orgIds);
	user_org_modal.close();
}

function deleteUser(){
	var id = jQuery("#id").val();
	if (!id || id == "") {
		myalert("请选择你要删除的记录");
		return;
	} else {
		var _url = "${ctx}/system/user/delete?id="+id;
		var listurl = "${ctx}/system/user/list";
		mydelete(_url, listurl);
	}
}

function showdata(result) {
	$("#account").attr("readonly",true);
	$("#workno").attr("readonly",true);
	for (var s in result.data) {
		set_val(s, result.data[s]);
	}
	var orgNames = "";
	var orgIds = "";
	jQuery(result.data["userOrgs"]).each(function(i, _obj) {
		orgNames += _obj.name + ",";
		orgIds += _obj.id + ",";
	});
	jQuery("#orgNames").val(orgNames);
	jQuery("#orgIds").val(orgIds);
	
	jQuery("#password").val("");
	
	// 处理角色表单字段
	$("#roleIds").find("option:selected").each(function(){
		$(this).prop("selected", false);
	});
	jQuery(result.data["userRoles"]).each(function(i, _obj) {
		jQuery("#roleIds option[value='" + _obj.id + "']").prop("selected", true);
	});
	jQuery("#roleIds").trigger("chosen:updated");  
}

//添加用户
function commonSaveUser(){
	var account = jQuery("#account").val();
	var name = jQuery("#name").val();
	var password = jQuery("#password").val();
	var roleIds = jQuery("#roleIds").val();
	var mobile = jQuery("#mobile").val();
	var state = jQuery("#state").val();
	$.ajax({
		url:ctx + "/system/user/save",
		type:"post",
		data:"account="+account+"&name="+name+"&password="+password+"&roleIds="+roleIds+"&state="+state+"&mobile="+mobile,
		success:function(msg){
			if(msg=="0"){
				myalert("恭喜你！添加成功。");
				myhref(ctx + "/system/user/list");
			}
			if(msg=="1"){
			     
				myalert("请把资料填写完整!");
				myhref(ctx + "/system/user/list");
			}
			if(msg=="2"){
				myalert("你填写的数据已存在!请核对后添加!");
				myhref(ctx + "/system/user/list");
			}
		},
		error:function(){
			myalert("添加失败，请稍后再试！");
		}
	});
	
}

//提交修改表单
function commonUpdateForms(form, listurl) {
	myconfirm("确定修改数据吗?", function() {
		if (!form) {
			form = "updateForm";
		}
		jQuery.post($('#' + form).attr('action'), $('#' + form).serialize(),
			function(_json) {
				if (_json == "1") {
					myalert("用户姓名或移动号码为空！", function() {
						myhref(listurl);
					});
				}
				if(_json == "0"){
					myalert("修改成功！", function() {
						myhref(listurl);
					});
				}
			}
		);
	});
	
}

//删除多条
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
	var url = ctx + "/system/user/delete/more";
	if(confirm("数据删除后将不能恢复,确定要删除选中的数据么?")){
    	$.ajax({
    		url:url,
    		type:"post",
    		data:"records="+records,
    		dataType:"text",
    		success:function(msg){
    			if(msg==0){
    				alert("恭喜你！删除成功");
    				window.location.href = ctx +"/system/user/list";
    			}else{
    				alert("删除失败");
    				window.location.href = ctx +"/system/user/list";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
	}
}

function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
		}else{
			$("#pageIndex").val("1");
		}
	$("#"+formId).submit();
}
function resetVal(){
	$('#searchForm input,select').val('');
}

function beforeAdd(){
	var url = "${ctx}/system/user/gotoAdd";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}

function beforeUpdate(id){
	$("#id").val(id);
	var url = "${ctx}/system/user/beforeUpdate";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}
function showCountry(){
	var regionCode=$("#bound_regionCode_").val();
	var url = ctx+"/system/user/userRoleList/searchCounty";  
  	$.ajax({
		url:url,   
       	type: 'POST',
       	contentType: "application/x-www-form-urlencoded; charset=utf-8", 
       	data:"regionCode="+regionCode,
      	dataType: 'html',					       
       	error: function(){                     
       		alert('请求失败');
       	},
       	success: function(data){		
       		var $sel = $("<select name='countyCode' id='bound_countyCode_' onchange='showOffice();'>");
       		$("#span_countrySelect").html($sel);
       		var data=eval("("+data+")");//转换为json对象
       		
       		var $opt = $("<option value=''>请选择</option>");
       		$("#bound_countyCode_").append($opt);
       		jQuery.each(data, function(i,item){  
       			var $opt = $("<option value='"+item.countyCode+"'>"+item.countyName+"</option>");
       			$("#bound_countyCode_").append($opt);
       			
			});  
	   	}
	});   
}
function showOffice(){
	var regionCode=$("#bound_regionCode_").val();
	var countyCode=$("#bound_countyCode_").val();
	var url = ctx+"/system/user/userRoleList/searchOffice";
	$.ajax({
		url:url,   
       	type: 'POST',
       	contentType: "application/x-www-form-urlencoded; charset=utf-8", 
       	data:"regionCode="+regionCode+"&countyCode="+countyCode,
      	dataType: 'html',					       
       	error: function(){                     
       		alert('请求失败');
       	},
       	success: function(data){		
       		var $sel = $("<select name='officeCode' id='bound_officeCode_' >");
       		$("#span_officeSelect").html($sel);
       		var data=eval("("+data+")");//转换为json对象
       		
       		var $opt = $("<option value=''>请选择</option>");
       		$("#bound_officeCode_").append($opt);
       		jQuery.each(data, function(i,item){  
       			var $opt = $("<option value='"+item.officeCode+"'>"+item.officeName+"</option>");
       			$("#bound_officeCode_").append($opt);
       			
			});  
	   	}
	});   
}

function boundUser(){
	//var dicRoleCode = "${(dicRoleCode)!''}";
	var regionCode = $('#bound_regionCode_').val();
	var countyCode = $('#bound_countyCode_').val();
	var officeCode = $('#bound_officeCode_').val();
	if(dicRoleCode!= null && dicRoleCode != undefined){
		var url = ctx  + "/system/user/userRoleList/befBoundUser?dicRoleCode="+dicRoleCode;
		if(dicRoleCode == "ROLE_SUPERMAN"){
			if(regionCode == undefined || regionCode == ""){
				alert("请选择需要添加的地市");return;
			}
			url = url  + "&regionCode="+regionCode;
		}
		if(dicRoleCode == "ROLE_REGIONER"){
			if(regionCode == undefined || regionCode == ""){
				alert("请选择需要添加的地市");return;
			}
			if(countyCode == undefined || countyCode == ""){
				alert("请选择需要添加的区县");return;
			}
			url = url  + "&regionCode="+regionCode+"&countyCode="+countyCode+"&officeCode="+officeCode;
		}
		if(dicRoleCode == "ROLE_OFFICE_MANAGE"){
			if(regionCode == undefined || regionCode == ""){
				alert("请选择需要添加的地市");return;
			}
			if(countyCode == undefined || countyCode == ""){
				alert("请选择需要添加的区县");return;
			}
			url = url  + "&regionCode="+regionCode+"&countyCode="+countyCode+"&officeCode="+officeCode;
		}
		//console.info(url);
		window.location.href = url;
	}
}