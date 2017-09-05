/**
 * goods 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:24
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
function deleteGoods() {
	var id = jQuery("#id").val();
	if (!id || id == "") {
		myalert("请选择你要删除的记录");
		return;
	} else {
		var _url = ctx + "/goods/delete?id=" + id;
		var listurl = ctx + "/goods/list";
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
	var url = ctx + "/goods/delete/more";
	
		jQuery.get(url, "records=" + records, function(data){
            alert("恭喜你，删除成功！");
            $("#searchForm").attr("action",ctx+"/goods/list");
            $("#searchForm").submit();
        });
}
//上下架操作
function upDownBusiness(id,type){
	var _url = ctx + "/goods/upDown";
	var msg="";
	if("0"==type){
		msg="确定要下架吗?";
	}else if("1"==type){
		msg="确定要上架吗?";
	}
	
	jQuery.post(_url, "id=" + id+"&upDown="+type, function(_json){
        alert(_json.message);
        window.location.href = ctx + "/goods/list";
       // submitForm("searchForm", ctx+"/goods/list");
    });
}
//批量上下架操作
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
    var _url = ctx + "/goods/upDown";
    
    var msg="";
	if("0"==type){
		msg="确定要下架吗?";
	}else if("1"==type){
		msg="确定要上架吗?";
	}
	
	jQuery.post(_url, "id=" + id+"&upDown="+type, function(_json){
        alert(_json.message);
        window.location.href = ctx + "/goods/list";
    });
	
}
//跳转到添加页面
function beforeAdd(){
	var url = ctx + "/goods/gotoAdd";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}
//跳转到编辑页面
function beforeUpdate(id){
	
	var url = ctx + "/goods/gotoUpdate?id="+id;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
}
//添加操作
function checkForm(type){
	var id=$.trim($("#id").val());
	var idTemp=$.trim($("#idTemp").val());
	var feeCode=$.trim($("#feeCode").val());
	var busId=$.trim($("#busId").val());
	var goodsName=$.trim($("#goodsName").val());
	var transactWay=$.trim($("#transactWay").val());
	var effectWay=$.trim($("#effectWay").val());
	var detailDesc=$.trim($("#detailDesc").val());
	var feeStandard=$.trim($("#feeStandard").val());
	var goodsDesc=$.trim($("#goodsDesc").val());
	var upDown=$.trim($("#upDown").val());
	var state=$.trim($("#state").val());
	var sort=$.trim($("#sort").val());
	$("#idTemp").val(id);
	var reg = new RegExp("^[A-Za-z0-9]*$");
	if(type=='1'){
		if(""==id && ""==idTemp){
			alert("请选择要修改的商品！");
			return;
		}
		if(""==effectWay){
			alert("生效方式不能为空！");
			return;
		}
		if(""==feeStandard){
			alert("资费标准不能为空！");
			return;
		}
		if(""==feeCode){
			alert("资费代码不能为空！");
			return;
		}
		if(!reg.test(feeCode)){
			alert("资费代码只能输入数字和字母!");
	        return;
	    }
		if(""==busId){
			alert("所属业务不能为空！");
			return;
		}
		if(""==id){
			$("#id").val(idTemp);
		}
		if(""==goodsDesc){
			alert("商品简介不能为空");
			return;
		}
		if(goodsDesc.length>100){
			alert("商品简介长度不能超过100！");
			return;
		}
		if(""==detailDesc){
			alert("商品详细描述不能为空");
			return;
		}
		if(detailDesc.length>600){
			alert("商品详细描述长度不能超过600！");
			return;
		}
		var _url = ctx + "/goods/update";
		$.ajax({
    		url:_url,
    		type:"post",
    		dataType:"json",
			data:"id="+id+"&upDown="+upDown+"&state="+state+"&goodsName=" + goodsName+"&feeCode="+feeCode+"&goodsDesc="+goodsDesc+"&feeStandard="+feeStandard+"&detailDesc="+detailDesc+"&effectWay="+effectWay+"&transactWay="+transactWay+"&busId="+busId+"&sort="+sort,
			success	: function(_json){
	        if(_json.status == "success"){
	        	alert(_json.message);
	        	window.location.href = ctx + "/goods/list";
	        }else{
	        	alert(_json.message);
	        	return;
	        }
	    }
	  });
	}else if(type=='0'){
		$("#id").val("");
		if(""==goodsName){
			alert("商品名称不能为空！");
			return;
		}
		if(""==effectWay){
			alert("生效方式不能为空！");
			return;
		}
		if(""==feeStandard){
			alert("资费标准不能为空！");
			return;
		}
		if(""==feeCode){
			alert("资费代码不能为空！");
			return;
		}
		if(!reg.test(feeCode)){
			alert("资费代码只能输入数字和字母!");
	        return;
	    }
		if(""==busId){
			alert("所属业务不能为空！");
			return;
		}
		if(""==goodsDesc){
			alert("商品简介不能为空");
			return;
		}
		if(goodsDesc.length>100){
			alert("商品简介长度不能超过100！");
			return;
		}
		if(""==detailDesc){
			alert("商品详细描述不能为空");
			return;
		}
		if(detailDesc.length>600){
			alert("商品详细描述长度不能超过600！");
			return;
		}
		var _url = ctx + "/goods/update";
		$.ajax({
    		url:_url,
    		type:"post",
    		dataType:"json",
			data:"goodsName=" + goodsName+"&feeCode="+feeCode+"&goodsDesc="+goodsDesc+"&feeStandard="+feeStandard+"&detailDesc="+detailDesc+"&effectWay="+effectWay+"&transactWay="+transactWay+"&busId="+busId+"&sort="+sort,
			success	: function(_json){
	        if(_json.status == "success"){
	        	alert(_json.message);
	        	window.location.href = ctx + "/goods/list";
	        }else{
	        	alert(_json.message);
	        	return;
	        }
	    }
	  });
		
	}
}
//重置按钮(添加)
function resetAdd(){
	$("#goodsName").val("");
	$("#feeCode").val("");
	$("#goodsDesc").val("");
	$("#feeStandard").val("");
	$("#detailDesc").val("");
	$("#effectWay").val("");
	$("#transactWay").val("");
	$("#busId").val("");
	$("#sort").val("");
}
//重置按钮(修改)
function resetUpdate(){
	$("#goodsName").val("");
	//$("#feeCode").val("");
	$("#goodsDesc").val("");
	$("#feeStandard").val("");
	$("#detailDesc").val("");
	$("#effectWay").val("");
	$("#transactWay").val("");
	$("#busId").val("");
	$("#sort").val("");
}
//重置按钮(查询)
function resetVal(){
	$("#goodsName_").val("");
	$("#feeCode_").val("");
	$("#upDown_").val("");
	$("#busId_").val("");
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


