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
	var url = ctx + "/qrcode/delete/more";
	if(confirm("数据删除后将不能恢复,确定要删除选中的数据么?")){
		$.ajax({
    		url:url,
    		type:"post",
    		data:"records="+records,
    		dataType:"text",
    		success:function(msg){
    			if(msg==0){
    				alert("恭喜你！删除成功");
    				window.location.href = ctx +"/qrcode/list";
    			}else{
    				alert("删除失败");
    				window.location.href = ctx +"/qrcode/list";
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
		
	}
}
var a=1;

//根据营业员展示对应的业务以及商品（tree）
function saveQrcode(){
	if(a==1){
		a=0;
		var userId=$("#salesAssistant").find("option:selected").val();//选中的文本
		var codeName =jQuery("#qrcodeName_").val();
		if(userId==null||userId==""){
			alert('请选中对应的营业员!');
			a=1;
	        return;
		}
		if(codeName==null||codeName==""){
			alert('二维码名称不能为空!');
			a=1;
	        return;
		}
		 var treeObj = $.fn.zTree.getZTreeObj("menuMultiSelectTree");
		   var nodes = treeObj.getCheckedNodes(true);
		   var msg = "";
		   for (var i = 0; i < nodes.length; i++) {
		       msg += nodes[i].goodsCode+":"+nodes[i].businessCode+",";
		   }
		if(msg==null||msg==""){
			alert('请选择对应的业务或产品!');
			a=1;
	        return;
		}
		jQuery.post(ctx + "/qrcode/save",{userId:userId,business:msg,qrcodeName:codeName},
				function(_json) {
					if (_json.status == "success") {
						a=1;
						var path =_json.data;
						jQuery("#qrcodePic").attr("src",ctx+path);
					} else {
						a=1;
						alert(_json.message);
					}
		});
	}else{
		alert("不能重复提交");
	}
	
	
}

//全业务二维码
function saveAllQrcode(){
	if(a==1){
		a=0;
		var codeName =jQuery("#qrcodeName_").val();
		if(codeName==null||codeName==""){
			alert('二维码名称不能为空!');
			a=1;
	        return;
		}
		jQuery.post(ctx + "/qrcode/saveAllQrcode",{qrcodeName:codeName},
				function(_json) {
					if (_json.status == "success") {
						a=1;
						var path =_json.data;
						jQuery("#qrcodePic").attr("src",ctx+path);
					} else {
						a=1;
						alert(_json.message);
					}
		});
		
	}else{
		alert("不能重复提交");
	}
}

//修改二维码是否有效
function commonUpdateForms() {
	var state = jQuery("#state_").val();
	var id = jQuery("#id_").val();
	if(confirm("确定修改数据吗?")){
		$.ajax({
			url:ctx + "/qrcode/update",
			type:"post",
			data:"state="+state+"&id="+id,
			dataType:"text",
			success:function(msg){
				if(msg=="0"){
					alert("恭喜你！修改成功。");
					window.location.href = ctx +"/qrcode/list";
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

//条件查询二维码
function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	$("#"+formId).submit();
}

//重置
function cleanqrcodes(){
	jQuery("#qrcodeName_").val("");
	jQuery("#belongUser_").val("");
	jQuery("#createDate_").val("");
	jQuery("#state_").val("");
	jQuery("#type_").val("");
}
//编辑
function beforeUpdate(id){
	var url = ctx + "/qrcode/beforeUpdate?id="+id;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}
//导出多条
function expMore(types){
	
	var url = "";
	if('PART' == types){
		var records="";
		$("input[name='check_li']:checkbox:checked").each(function(){ 
			records+=$(this).val()+",";
		}) 
		records=records.substring(0, records.length-1);
	    if (records=="") {
	        alert('未选中任何记录!');
	        return;
	    }
	    url = ctx + "/qrcode/list/export?types=PART&records="+records;
	}else if('ALL' == types){
		if($("#totalCount").val() > 500){
	        alert('最多可以导出500条数据，请筛选后重新导出!');
	        return;
		}
		 url = ctx + "/qrcode/list/export?types=ALL";
	}else{
        alert('请联系管理员!');
        return;
	}

	
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
	$("#searchForm").attr("action",ctx+"/qrcode/list");

}