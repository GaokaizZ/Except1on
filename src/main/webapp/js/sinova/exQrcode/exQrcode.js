/**
 * qrcode 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:39
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
	var url = ctx + "/exQrcode/delete";
	if(confirm("数据删除后将不能恢复,确定要删除选中的数据么?")){
		$.ajax({
    		url:url,
    		type:"post",
    		data:"records="+records,
    		dataType:"text",
    		success:function(msg){
    			if(msg==0){
    				alert("恭喜你！删除成功");
    				window.location.href = ctx +"/exQrcode/list";
    			}else{
    				alert("删除失败");
    				window.location.href = ctx +"/exQrcode/list";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
		
	}
}



//修改二维码名称
function commonUpdateForms() {
	var exName = jQuery("#exName").val();
	var id = jQuery("#id").val();
	if(exName==""||exName==null){
		alert("二维码名称不能为空");
		return;
	}
	if(confirm("确定修改数据吗?")){
		$.ajax({
			url:ctx + "/exQrcode/update",
			type:"post",
			data:"exName="+exName+"&id="+id,
			dataType:"text",
			success:function(msg){
				if(msg=="0"){
					alert("恭喜你！修改成功。");
					window.location.href = ctx +"/exQrcode/list";
				}else if(msg=="1"){
					alert("名称重复，请换个名称!");
					return;
				}else{
					alert("修改失败!");
					return;
				}
				
			},
			error:function(){
				alert("修改失败，请稍后再试！");
			}
		});
	}
	
}

//条件查询二维码
function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	$("#"+formId).submit();
}

//列表页重置
function cleanqrcodes(){
	jQuery("#exName_").val("");
	jQuery("#belongUser_").val("");
}
//编辑页重置
function resetValue(){
	jQuery("#exName").val("");
}
//编辑
function beforeUpdate(id){
	var url = ctx + "/exQrcode/beforeUpdate?id="+id;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}
//导出多条
function expMore(type){
	
	var url = "";
	if('PART' == type){
		var records="";
		$("input[name='check_li']:checkbox:checked").each(function(){ 
			records+=$(this).val()+",";
		}) 
		records=records.substring(0, records.length-1);
	    if (records=="") {
	        alert('未选中任何记录!');
	        return;
	    }
	    url = ctx + "/exQrcode/list/export?type=PART&records="+records;
	}else if('ALL' == type){
		if($("#totalCount").val() > 500){
	        alert('最多可以导出500条数据，请筛选后重新导出!');
	        return;
		}
		 url = ctx + "/exQrcode/list/export?type=ALL";
	}else{
        alert('请联系管理员!');
        return;
	}

	
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
	$("#searchForm").attr("action",ctx+"/exQrcode/list");

}