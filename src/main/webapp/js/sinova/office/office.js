/**
 * office 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:01
 */

jQuery(document).ready(function(){
	
	jQuery(document).ready(function(){
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

//汉字和字母
function checkCE(obj){
	var regStr = /^[A-Za-z\u4e00-\u9fa5]+$/;
	if(regStr.test(obj)){
		return true;
	}else{
		return false;
	}
}
//添加时根据不同的地市显示不同的区县
function searchCounty(){
    $("#countyCode_").empty();
    $("#countyCode_").append("<option value='' selected>全部</option>");
   	var url = ctx + "/office/countylist";
   	var regionCode = $("#regionCode_ option:selected").val();
       	$.ajax({
       		url:url,
       		type:"post",
       		data:"regionCode="+regionCode,
       		dataType : "json",
       		success:function(_json){
       			if((_json.data)!='null' && (_json.data)!=''){
       				$.each((_json.data),function(i,data){
       					$("#countyCode_").append("<option value='"+data.countyCode+"'>"+data.countyName+"</option>");
       				});
       			}
       		},
       		error:function(){
       			alert("系统异常，请稍后再试！");
       		}
       	});
   }

//添加营业厅数据
function commonSaveForma(){
	var regionCode = jQuery("#regionCode_").val();
	var countyCode = jQuery("#countyCode_").val();
	var officeCode = jQuery("#officeCode_").val();
	var officeName = jQuery("#officeName_").val();
	var address = jQuery("#address_").val();
	var state = jQuery("#state_").val();
	if(officeName==""||officeName==null){
		alert("请把资料填写完整！");
		jQuery("#officeName_").focus();
		return;
	}
	if(!checkCE(officeName)){
		alert("营业厅名称请使用输入汉字或字母！");
		jQuery("#officeName_").focus();
		return;
	}
	$.ajax({
		url:ctx + "/office/save",
		type:"post",
		data:"regionCode="+regionCode+"&countyCode="+countyCode+"&officeCode="+officeCode+"&officeName="+officeName+"&address="+address+"&state="+state,
		dataType:"text",
		success:function(msg){
			if(msg=="0"){
				alert("恭喜你！添加成功。");
				window.location.href = ctx + "/office/list";
			}
			if(msg=="1"){
				alert("请把资料填写完整!");
			}
			if(msg=="2"){
				alert("你填写的数据已存在!请核对后添加!");
				window.location.href = ctx + "/office/list";
			}
		},
		error:function(){
			alert("添加失败，请稍后再试！");
		}
	});
	
}

//提交修改表单
function commonUpdateForms() {
	
	var state = jQuery("#state_").val();
	var id = jQuery("#id_").val();
	var officeName = jQuery("#officeName_").val();
	var address = jQuery("#address_").val();
	if(confirm("确定修改数据吗?")){
		if(!checkCE(officeName)){
			alert("营业厅名称请使用输入汉字或字母！");
			jQuery("#officeName_").focus();
			return;
		}
		$.ajax({
			url:ctx + "/office/update",
			type:"post",
			data:"state="+state+"&id="+id+"&officeName="+officeName+"&address="+address,
			dataType:"text",
			success:function(msg){
				if(msg=="0"){
					alert("恭喜你！修改成功。");
					window.location.href = ctx +"/office/list";
				}
				if(msg=="1"){
					alert("资料填写不完整！");
				}
			},
			error:function(){
				alert("修改失败，请稍后再试！");
			}
		});
	}
}


// 删除一条
function deleteOffice() {
	var id = jQuery("#id").val();
	if (!id || id == "") {
		myalert("请选择你要删除的记录");
		return;
	} else {
		var _url = ctx + "/office/delete?id=" + id;
		var listurl = ctx + "/office/list";
		mydelete(_url, listurl);
	}
}

// 批量删除营业厅
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
	var url = ctx + "/office/delete/more";
	if(confirm("数据删除后将不能恢复,确定要删除选中的数据么?")){
		$.ajax({
    		url:url,
    		type:"post",
    		data:"records="+records,
    		dataType:"text",
    		success:function(msg){
    			if(msg==0){
    				alert("恭喜你！删除成功");
    				window.location.href = ctx +"/office/list";
    			}else{
    				alert("删除失败");
    				window.location.href = ctx +"/office/list";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
		
	}
}

//条件查询营业厅
function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	$("#"+formId).submit();
}

//重置营业厅数据
function cleans(){
	jQuery("#regionCode_").val("");
	jQuery("#countyCode_").val("");
	jQuery("#officeCode_").val("");
	jQuery("#officeName_").val("");
	jQuery("#address_").val("");
	jQuery("#state_").val("");
}

//编辑营业厅跳转页面
function beforeUpdate(id){
	var url = ctx + "/office/beforeUpdate?id="+id;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}

//添加营业厅跳转页面
function beforeSave(){
	var url = ctx + "/office/beforeSave";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}


//查找营业厅与用户关系表(营业厅用户)
function addUser(officeCode,regionCode,countyCode){
	
		if (!officeCode || officeCode == "") {
			alert("请先选择营业厅！");
			return;
		}	
		var url = ctx + "/useroffice/list?officeCode="+officeCode+"&regionCode="+regionCode+"&countyCode="+countyCode;
		window.location.href = url;
}
//查询未分配营业厅的用户(营业厅用户)
function userNotInOffice(){
	
	var regionCode = jQuery("#regionCode").val();
	var countyCode = jQuery("#countyCode").val();
	var officeCode = jQuery("#officeCode").val();
	
	var url = ctx  + "/useroffice/findUser?officeCode="+officeCode+"&regionCode="+regionCode+"&countyCode="+countyCode;
	window.location.href = url;
}

//根据营业厅查询用户(营业厅用户)
function findUserByOfficeName(_target){
	//分页
	if (typeof(_target) == "undefined" || _target == "") { 
	   
		}else{
			$("#pageIndex").val("1");
		}
	
	var officeCode = jQuery("#officeCode").val();
	var account = jQuery("#account").val();
	var url = ctx + "/useroffice/list?officeCode="+officeCode+"&account="+account;
	window.location.href = url;
	
}
//重置按钮(营业厅用户)
function resetVal(){
	$("#account").val("");
}

//删除多条(营业厅用户)
function deleteMores(){
	var officeCode = jQuery("#officeCode").val();
	var regionCode = jQuery("#regionCode").val();
	var countyCode = jQuery("#countyCode").val();
	var records="";
	jQuery("input[name='check_li']:checkbox:checked").each(function(){ 
		records+=$(this).val()+",";
	}); 
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
	var url = ctx + "/useroffice/delete/more";
    	$.ajax({
    		url:url,
			type:"post",
			data:"records="+records,
			success:function(msg){
				if(msg.status=="success"){
					alert("删除成功");
					window.location.href = ctx  + "/useroffice/list?officeCode=" + officeCode+"&regionCode="+regionCode+"&countyCode="+countyCode;
				}else{
					alert("删除失败");
					window.location.href = ctx  + "/useroffice/list?officeCode=" + officeCode+"&regionCode="+regionCode+"&countyCode="+countyCode;
				}
			},
			error:function(){
				alert("系统异常，请稍后再试！");
			}
    	});
		
	
}
//设置岗位(营业厅用户)
function updatePost(){
	var officeCode = jQuery("#officeCode").val();
	var regionCode = jQuery("#regionCode").val();
	var countyCode = jQuery("#countyCode").val();
	
	var post = jQuery("input[name='check_lip']:checked").val()
	var records = jQuery("#records").val()
	if (!post || post == "") {
        alert('未选中任何记录!');
        return;
    }
	var url = ctx + "/useroffice/update/more";
		$.ajax({
			url:url,
			type:"post",
			data:"post="+post+"&records="+records,
			success:function(msg){
				if(msg.status=="success"){
					alert("设置成功");
					window.location.href = ctx  + "/useroffice/list?officeCode=" + officeCode+"&regionCode="+regionCode+"&countyCode="+countyCode;
				}else{
					alert("设置失败");
					window.location.href = ctx  + "/useroffice/list?officeCode=" + officeCode+"&regionCode="+regionCode+"&countyCode="+countyCode;
				}
			},
			error:function(){
				alert("系统异常，请稍后再试！");
			}
		});
	
}


//查找岗位(营业厅用户)
function listPost(id){
	var url = ctx + "/useroffice/postList";
	$.ajax({
		url:url,
		type:"post",
		data:"id="+id,
		success:function(msg){//设置岗位弹出层
			$("#postlist").html(msg);
		},
		error:function(){
			alert("系统异常，请稍后再试！");
		}
	});
   
}
