/**
 * regionbusiness 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:14:14
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
function deleteMore(regionId){
	var regionName = jQuery("#regionName_").val();
	var records="";
	$("input[name='check_li']:checkbox:checked").each(function(){ 
		records+=$(this).val()+",";
	}) 
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
	var url = ctx + "/regionbusiness/deletemore";
	if(confirm("数据删除后将不能恢复,确定要删除选中的数据么?")){
		$.ajax({
    		url:url,
    		type:"post",
    		data:"records="+records,
    		dataType:"text",
    		success:function(msg){
    			if(msg==0){
    				alert("恭喜你！删除成功");
    				window.location.href = ctx +"/regionbusiness/buslist?regionId="+regionId+"&regionName="+encodeURIComponent(encodeURIComponent(regionName));
    			}else{
    				alert("删除失败");
    				window.location.href = ctx +"/regionbusiness/buslist";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
	}
	
}

//绑定业务操作
function selectBuessines(regionId,regionName,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	//条件查询已经绑定的业务
	var busCode = jQuery("#busCode_").val();
	var busName = jQuery("#busName_").val();
	var url =  ctx + "/regionbusiness/buslist?regionId="+regionId+"&regionName="+encodeURIComponent(encodeURIComponent(regionName))+"&busCode="+busCode+"&busName="+busName;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}

//查询这个地市未分配的业务
function addBuessines(regionId){
	var regionName = jQuery("#regionName_").val();
	window.location.href = ctx  + "/regionbusiness/businessPage?regionName="+encodeURIComponent(encodeURIComponent(regionName))+"&regionId="+regionId;
}

