/**
 * business 页面使用javascript
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2015-01-09 09:13:13
 */

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


// 删除一条
function deleteBusiness() {
	var id = jQuery("#id").val();
	if (!id || id == "") {
		myalert("请选择你要删除的记录");
		return;
	} else {
		var _url = ctx + "/business/delete?id=" + id;
		var listurl = ctx + "/business/list";
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
	var url = ctx + "/business/delete/more";
    if(confirm("记录删除后将不能恢复,确定要删除选中的记录么?")){
    	$.ajax({
    		url:url,
    		type:"post",
    		data:"records="+records,
    		success:function(msg){
    			if(msg.status=="success"){
    				alert("恭喜你！删除成功");
    				window.location.href = ctx +"/business/list";
    			}else{
    				alert("删除失败");
    				window.location.href = ctx +"/business/list";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
	}
}


function upDownBusiness(id,type){
	var _url = ctx + "/business/upDown";
	var msg="";
	if("0"==type){
		msg="确定要下架吗?";
	}else if("1"==type){
		msg="确定要上架吗?";
	}
	$.ajax({
		url:_url,
		type:"post",
		data:"id="+id+"&upDown="+type,
		success:function(msg){
			if(msg.status=="success"){
				alert("操作成功！");
				window.location.href = ctx +"/business/list";
			}else{
				window.location.href = ctx +"/business/list";
			}
		},
		error:function(){
			alert("系统异常，请稍后再试！");
		}
	});

}

function upDownActivities(id,type,regionId){
	var _url = ctx + "/business/upDown";
	var msg="";
	if("0"==type){
		msg="确定要下架吗?";
	}else if("1"==type){
		msg="确定要上架吗?";
	}
	if(confirm(msg)){
		$.ajax({
    		url:_url,
    		type:"post",
    		data:"id="+id+"&upDown="+type,
    		success:function(msg){
    			if(msg.status=="success"){
    				alert("操作成功！");
    				window.location.href = ctx +"/business/list";
    			}else{
    				window.location.href = ctx +"/business/list";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
	}
}

function upDownBusinessBeath(type){
	var id="";
		$("input[name='check_li']:checkbox:checked").each(function(){
			id+=$(this).val()+",";
		})
		id=id.substring(0, id.length-1);
	    if (id=="") {
	        alert('未选中任何记录!');
	        return;
	    }
		var _url = ctx + "/business/upDown";

	    var msg="";
		if("0"==type){
			msg="确定要下架吗?";
		}else if("1"==type){
			msg="确定要上架吗?";
		}

		if(confirm(msg)){
			$.ajax({
	    		url:_url,
	    		type:"post",
	    		data:"id="+id+"&upDown="+type,
	    		success:function(msg){
	    			if(msg.status=="success"){
	    				alert("操作成功！");
	    				window.location.href = ctx +"/business/list";
	    			}else{
	    				window.location.href = ctx +"/business/list";
	    			}
	    		},
	    		error:function(){
	    			alert("系统异常，请稍后再试！");
	    		}
	    	});
		}
	
	
}

function checkForm(type){
	var id=$.trim($("#id").val());
	var idTemp=$.trim($("#idTemp").val());
	var busCode=$.trim($("#busCode").val());
	var busName=$.trim($("#busName").val());
	var defaultReward=$.trim($("#defaultReward").val());
	var busType=$.trim($("#busType").val());
	$("#idTemp").val(id);
	var reg = new RegExp("^[A-Za-z0-9]*$");
	var reg1 = new RegExp("^[0-9]+([.]{1}[0-9]){0,1}$");
	var reg2 = /^(\w|[\u4E00-\u9FA5])+$/;
	var url = ctx + "/business/update";
	var upDown = $.trim($("#upDown").val());
	var state = $.trim($("#state").val());
	var remark = $.trim($("#remark").val());
	if(type=='1'){
		if(""==id && ""==idTemp){
			alert("请选择要修改的业务！");
			return;
		}
		if(""==busCode){
			alert("业务编号不能为空！");
			return;
		}
		if(!reg.test(busCode)){
			alert("业务编号只能输入数字和字母!");
	        return;
	    }
		if(""==busName){
			alert("业务名称不能为空！");
			return;
		}
		if(!reg2.test(busName)){
			alert("业务名称包含非法字符!");
			return;
		}
		if(""==busType){
			alert("业务类型不能为空!");
	        return;
	    }
		if(""!=defaultReward){
			if(!reg1.test(defaultReward)){
				alert("默认酬金只能输入正整数或小数（保留一位）!");
		        return;
			}
		}
		if(""!=remark){
			if(remark.length>300){
				alert("备注内容不能超过300字！");
				return;
			}
		}
		if(""==id){
			$("#id").val(idTemp);
		}

		$.ajax({
			url:url,
			type:"post",
			data:{"id":id,"busCode":busCode,"busName":busName,"busType":busType,"defaultReward":defaultReward,"remark":remark,"upDown":upDown,"state":state,"type":type},
			dataType:"json",
			success:function(msg){
				if(msg.status=="success"){
    				alert("修改成功！");
    				window.location.href = ctx +"/business/list";
    			}else if(msg.status=="nameUsed"){
    				alert("业务名称已被使用,请重新输入!");
    			}else{
    				alert("修改失败！");
    			}
			},
			error:function(){
				alert("系统异常！请稍后再试！");
			}
		});
	}else if(type=='0'){
		$("#id").val("");
		if(""==busCode){
			alert("业务编号不能为空！");
			return;
		}
		if(!reg.test(busCode)){
			alert("业务编号只能输入数字和字母!");
	        return;
	    }
		if(""==busName){
			alert("业务名称不能为空！");
			return;
		}
		if(!reg2.test(busName)){
			alert("业务名称包含非法字符!");
			return;
		}
		if(""==busType){
			alert("业务类型不能为空!");
	        return;
	    }
		if(""!=defaultReward){
			if(!reg1.test(defaultReward)){
				alert("默认酬金只能输入正整数或小数（保留一位）!");
		        return;
			}
		}
		if(""!=remark){
			if(remark.length>300){
				alert("备注内容不能超过300字！");
				return;
			}
		}
		
		$.ajax({
			url:url,
			type:"post",
			data:{"id":id,"busCode":busCode,"busName":busName,"busType":busType,"defaultReward":defaultReward,"remark":remark,"upDown":upDown,"state":state},
			dataType:"json",
			success:function(msg){
				if(msg.status=="success"){
    				alert("添加成功！");
    				window.location.href = ctx +"/business/list";
    			}else if(msg.status=="codeUsed"){
    				alert("业务编号已被使用,请重新输入!");
    			}else if(msg.status=="nameUsed"){
    				alert("业务名称已被使用,请重新输入!");
    			}else{
    				alert("添加失败！");
    			}
			},
			error:function(){
				alert("系统异常！请稍后再试！");
			}
		});
	}
}

function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	$("#searchForm").submit();
}
function resetVal(){
	$("#busCode_").val("");
	$("#busName_").val("");
	$("#upDown_").val("");
	$("#busType_").val("");
}
function resetVal2(flag){
	if(flag=="add"){
		$("#busCode").val("");
		$("#busName").val("");
		$("#busType").val("");
		$("#defaultReward").val("");
		$("#remark").val("");
	}else if(flag=="edit"){
		$("#busName").val("");
		$("#busType").val("");
		$("#defaultReward").val("");
		$("#remark").val("");
	}
}
function beforeAdd(){
	var url = ctx + "/business/beforeAdd";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}
function beforeUpdate(id){
	$("#id").val(id);
	var url = ctx + "/business/beforeUpdate";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}