/**
 * goodsAct 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-06-29 14:13:24
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
//添加关联
function addRelation(busId){
	
	var goodsId = $("#goodsId").val();
	var groupId = $("#groupId").val();
    if (busId=="") {
        alert('未选中任何记录!');
        return;
    }
	if(confirm("确定添加关联吗?")){
		$.ajax({
			url:ctx + "/goodsAct/addRelation",
			type:"post",
			data:"goodsId="+goodsId+"&busId="+busId+"&groupId="+groupId,
			dataType:"json",
			success:function(msg){
				if(msg.status=="success"){
					alert("恭喜你！添加关联成功。");
					window.location.href = ctx + "/goodsAct/list?goodsId="+goodsId;
				}else{
					alert("添加关联失败！");
					return;
				}
			},
			error:function(){
				alert("系统异常，请稍后再试！");
			}
		});
	}
}

//批量添加关联
function plAddRelation(){
	var groupId = $("#groupId").val();
	var goodsId = $("#goodsId").val();
	var records="";
	$("input[name='check_li']:checkbox:checked").each(function(){ 
		records+=$(this).val()+",";
	}) 
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
    
	if(confirm("确定批量添加关联吗?")){
		$.ajax({
			url:ctx + "/goodsAct/plAddRelation",
			type:"post",
			data:"goodsId="+goodsId+"&records="+records+"&groupId="+groupId,
			dataType:"json",
			success:function(msg){
				if(msg.status=="success"){
					alert("恭喜你！添加关联成功。");
					window.location.href = ctx + "/goodsAct/list?goodsId="+goodsId;
				}else{
					alert("批量添加关联失败！");
					return;
				}
			},
			error:function(){
				alert("系统异常，请稍后再试！");
			}
		});
	}
}
//取消关联
function delRelation(busId){
	var goodsId = $("#goodsId").val();
    if (busId=="") {
        alert('未选中任何记录!');
        return;
    }
	if(confirm("确定取消关联吗?")){
		$.ajax({
			url:ctx + "/goodsAct/delRelation",
			type:"post",
			data:"goodsId="+goodsId+"&busId="+busId,
			dataType:"json",
			success:function(msg){
				if(msg.status=="success"){
					alert("恭喜你！取消关联成功。");
					window.location.href = ctx + "/goodsAct/list?goodsId="+goodsId;
				}else{
					alert("取消关联失败！");
					return;
				}
			},
			error:function(){
				alert("系统异常，请稍后再试！");
			}
		});
	}
}
//批量取消关联
function plDelRelation(){
	var goodsId = $("#goodsId").val();
	var records="";
	$("input[name='check_li']:checkbox:checked").each(function(){ 
		records+=$(this).val()+",";
	}) 
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
    
	if(confirm("确定批量取消关联吗?")){
		$.ajax({
			url:ctx + "/goodsAct/plDelRelation",
			type:"post",
			data:"goodsId="+goodsId+"&records="+records,
			dataType:"json",
			success:function(msg){
				if(msg.status=="success"){
					alert("恭喜你！批量取消关联成功。");
					window.location.href = ctx + "/goodsAct/list?goodsId="+goodsId;
				}else{
					alert("批量取消关联失败！");
					return;
				}
			},
			error:function(){
				alert("系统异常，请稍后再试！");
			}
		});
	}
}
//跳转到添加页面
function toRelation(goodsId,updown){
	
	if(goodsId==''){
		alert("商品ID为空，数据不符合条件！");
		return;
	}
	if(updown==''){
		alert("商品上下架状态不存在，数据不符合条件！");
		return;
	}
	if(updown =='0'){
		alert("请先将该商品上架，再进行关联！");
		return;
	}
	var url = ctx + "/goodsAct/list?goodsId="+goodsId;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}




//重置按钮(查询)
function resetVal(){
	$("#goodsName").val("");
	$("#feeCode").val("");
	$("#relation").val("");
	$("#busName").val("");
	$("#upDown_").val("");
	$("#busId_").val("");
	$("#busType").val("");
	$("#searchCity").val("choose");
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
function back(){
	window.location.href = ctx + "/goods/list";
}

