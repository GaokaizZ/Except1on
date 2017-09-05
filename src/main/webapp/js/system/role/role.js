var menuModal;
function showdata(result) {
	$('#updateForm').data('bootstrapValidator').resetForm(true);
	$("#code").attr("readonly", "readonly");// 当编辑的时候设置编号只读

	for ( var s in result.data) {
		set_val(s, result.data[s]);
	}

	var menuNames = "";
	var menuIds = "";

	jQuery(result.data.menus).each(function(i, obj) {
		menuNames = menuNames + obj.name + ",";
		menuIds = menuIds + obj.id + ",";
	});
	jQuery("#menuNames").val(menuNames);
	jQuery("#menuIds").val(menuIds);

}

//删除角色
function deleteRole(roleId) {
	if(confirm("数据删除后将不能恢复,确定要删除选中的数据么?")){
		$.ajax({
    		url:ctx + "/system/role/delete",
    		type:"post",
    		data:"roleId="+roleId,
    		dataType:"text",
    		success:function(msg){
    			if(msg==0){
    				alert("恭喜你！删除成功");
    				window.location.href = ctx + "/system/role/list";
    			}else{
    				alert("删除失败");
    				window.location.href = ctx + "/system/role/list";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
	}
}

//条件查询角色
function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	$("#"+formId).submit();
}
//重置
function cleans(){
	jQuery("#code").val("");
	jQuery("#name").val("");
	jQuery("#state_").val("");
}
//添加
function beforeSave(){
	var url = ctx + "/system/role/beforeSave";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
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
//汉字和字母
function checkCE(obj){
	var regStr = /^[A-Za-z\u4e00-\u9fa5]+$/;
	if(regStr.test(obj)){
		return true;
	}else{
		return false;
	}
}
//添加角色信息
function saveRole() {
	var id = jQuery("#id").val();
	var code = jQuery("#code").val();
	var name = jQuery("#name").val();
	var menuIds = jQuery("#menuIds").val();
	var remark = jQuery("#remark").val();
	if((name==""||name == null)||(code==""||code==null)){
		alert("请把资料填写完整！！");
		return;
	}
	if(!checkME(code)){
		alert("角色编号请使用数字或字母");
		jQuery("#code").focus();
		return;
	}
	if(!checkCE(name)){
		alert("角色名称请使用汉字或字母");
		jQuery("#name").focus();
		return;
	}
	$.ajax({
		url:ctx + "/system/role/save",
		type:"post",
		data:"name="+name+"&id="+id+"&menuIds="+menuIds+"&code="+code+"&remark="+remark,
		dataType:"text",
		success:function(msg){
			if(msg=="0"){
				alert("恭喜你！添加成功。");
				window.location.href = ctx +"/system/role/list";
			}
			if(msg=="1"){
				alert("填写资料重复，请核对！");
			}
		},
		error:function(){
			alert("添加失败，请稍后再试！");
		}
	});
	
}

//修改跳页
function beforeUpdate(roleId){
	var url = ctx + "/system/role/beforeUpdate?roleId="+roleId;
	
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}

//修改角色信息
function updateRole() {
	var name = jQuery("#name").val();
	var id = jQuery("#id").val();
	var menuIds = jQuery("#menuIds").val();
	var remark = jQuery("#remark").val();
	if(confirm("确定修改数据吗?")){
		if(name==""||name == null){
			alert("请填写角色名称！！");
			return;
		}
		if(!checkCE(name)){
			alert("角色名称请使用汉字和字母");
			jQuery("#name").focus();
			return;
		}
		$.ajax({
			url:ctx + "/system/role/update",
			type:"post",
			data:"name="+name+"&id="+id+"&menuIds="+menuIds+"&remark="+remark,
			dataType:"text",
			success:function(msg){
				if(msg=="0"){
					alert("恭喜你！修改成功。");
					window.location.href = ctx +"/system/role/list";
				}
				if(msg=="1"){
					alert("角色名称重复，请核对！");
				}
			},
			error:function(){
				alert("修改失败，请稍后再试！");
			}
		});
	}
}

function resetRole() {
	$("#id").val("");
	$("#code").removeAttr("readonly");
	$('#updateForm').data('bootstrapValidator').resetForm(true);
	$('#updateForm')[0].reset();
}



function showMenuModal() {
		menuModal = $.scojs_modal({
		title : "选择菜单",
		target : "#modal",
		appendTo:"#menuListTree",
		cache : true,
		remote : ctx + "/system/menu/tree/pre",
		uuid : "menuMultiSelectTree",
		fun_confirm : "selectZtreeMenu"
	});
	menuModal.show();
	show('cover','tcccd');
}
function queding(){
	selectZtreeMenu('menuMultiSelectTree');
	hide('cover','tcccd');
}
function selectZtreeMenu(uuid) {
	var zTree = $.fn.zTree.getZTreeObj(uuid);

	var nodes = zTree.getCheckedNodes(true);

	var menuNames = "";
	var menuIds = "";

	jQuery(nodes).each(function(i, obj) {
		menuNames = menuNames + obj.name + ",";
		menuIds = menuIds + obj.id + ",";
	});
	jQuery("#menuNames").val(menuNames);
	jQuery("#menuIds").val(menuIds);

	menuModal.close();
}
