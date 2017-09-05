/**
 * region 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:12:19
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
//汉字和字母
function checkCE(obj){
	var regStr = /^[A-Za-z\u4e00-\u9fa5]+$/;
	if(regStr.test(obj)){
		return true;
	}else{
		return false;
	}
}
//添加地市数据
function commonSaveForma(){
	var regionCode = jQuery("#regionCode_").val();
	var regionName = jQuery("#regionName_").val();
	var state = jQuery("#state_").val();
	if(regionName==""||regionName==null){
		alert("请把资料填写完整！");
		jQuery("#regionName_").focus();
		return;
	}
	if(!checkCE(regionName)){
		alert("地市名称请使用汉字或字母！");
		jQuery("#regionName_").focus();
		return;
	}
	$.ajax({
		url:ctx + "/region/save",
		type:"post",
		data:"regionCode="+regionCode+"&regionName="+regionName+"&state="+state,
		dataType:"text",
		success:function(msg){
			if(msg=="0"){
				alert("恭喜你！添加成功。");
				window.location.href = ctx + "/region/list";
			}
			if(msg=="1"){
				alert("请把资料填写完整!");
			}
			if(msg=="2"){
				alert("你填写的数据已存在!请核对后添加!");
				window.location.href = ctx + "/region/list";
			}
		},
		error:function(){
			alert("添加失败，请稍后再试！");
		}
	});
	
}

//修改地市信息
function commonUpdateForms() {
	var state = jQuery("#state_").val();
	var id = jQuery("#id_").val();
	var regionName = jQuery("#regionName_").val();
	if(confirm("确定修改数据吗?")){
		if(!checkCE(regionName)){
			alert("地市名称请使用汉字或字母！");
			jQuery("#regionName_").focus();
			return;
		}
		$.ajax({
			url:ctx + "/region/update",
			type:"post",
			data:"state="+state+"&id="+id+"&regionName="+regionName,
			dataType:"text",
			success:function(msg){
				if(msg=="0"){
					alert("恭喜你！修改成功。");
					window.location.href = ctx +"/region/list";
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
	var url = ctx + "/region/delete/more";
	if(confirm("数据删除后将不能恢复,确定要删除选中的数据么?")){
		$.ajax({
    		url:url,
    		type:"post",
    		data:"records="+records,
    		dataType:"text",
    		success:function(msg){
    			if(msg=="0"){
    				alert("恭喜你！删除成功");
    				window.location.href = ctx +"/region/list";
    			}
    			if(msg=="2"){
    				alert("地市下存在区县！");
    				window.location.href = ctx +"/region/list";
    			}
    			if(msg=="1"){
    				alert("删除失败");
    				window.location.href = ctx +"/region/list";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
		
	}
}

//条件查询地市
function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	$("#"+formId).submit();
}

//重置
function cleanqrcodes(){
	jQuery("#regionCode_").val("");
	jQuery("#regionName_").val("");
	jQuery("#state_").val("");
}

//编辑
function beforeUpdate(id){
	var url = ctx + "/region/beforeUpdate?id="+id;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}

//添加
function beforeSave(){
	var url = ctx + "/region/beforeSave";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}