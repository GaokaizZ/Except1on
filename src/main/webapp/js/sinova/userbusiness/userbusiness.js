/**
 * userbusiness 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:15:03
 */

jQuery(document).ready(function(){
    //增加全选事件
   	//jQuery(":checkbox[name='check_all']").checkbox().toggle(":checkbox[name='check_li']");
	
	//validateRules('saveForm');
	
	$(".sub_left_menu tbody tr").click(function() {
		$(".sub_left_menu tbody tr.active").removeClass("active");
		$(this).attr("class", "active");		
		var _url = ctx + "/system/user/look/json?id=" + $(this).attr("id");
	
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
	
});

function showdata(result) {

	for (var s in result.data) {
		set_val(s, result.data[s]);
	}
}

// 删除一条
function deleteUserBusiness() {
	var id = jQuery("#id").val();
	if (!id || id == "") {
		myalert("请选择你要删除的记录");
		return;
	} else {
		var _url = ctx + "/userbusiness/delete?id=" + id;
		var listurl = ctx + "/userbusiness/list";
		mydelete(_url, listurl);
	}
}

// 删除多条
function deleteMores(){
	var userId = jQuery("#userId").val();
	var records="";
	jQuery("input[name='check_li']:checkbox:checked").each(function(){ 
		records+=$(this).val()+",";
	}); 
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
	var url = ctx + "/userbusiness/deletebus";
    if(confirm("记录删除后将不能恢复,确定要删除选中的记录么?")){
    	$.ajax({
			url:url,
			type:"post",
			data:{"records":records},
			success:function(data){
				alert("删除成功！");
				$("#searchForm").attr("action",ctx+"/userbusiness/buslist");
				$("#searchForm").submit();
			},
			error:function(){
				alert("删除失败，请稍后再试！");
			}
		});
	}
}


//选择业务
function clickThisB(obj){
	if(obj.checked){
		busIds = addNum(obj.value);
	}else{
		busIds = removeNum(obj.value);
	}
	$("#busIds").val(busIds);
	console.info($("#busIds").val());
}
//加入字符串
function addNum(num){
	var val = $.trim($("#busIds").val());
	if(val ==''){
		var val = num;
	}else{
		var val = val +","+num;
	}
	return val;
}

//删除字符串
function removeNum(num){
	var val = $.trim($("#busIds").val());
	if("," == val.charAt(val.length-1)){
		val=val.substring(0,val.length-1) 
	}
	val = val.replace(num+",", "");
	val = val.replace(num, "");
	if("," == val.charAt(val.length-1)){
		val=val.substring(0,val.length-1) ;
	}
   	return val;
}

//跳转业务界面or返回
function selectBuessines(flag,userId,act,_target){
	if(flag == "go"){
		$("#account").val(act);
		var account = jQuery("#account").val();
		var busCode = jQuery("#busCode_").val();
		var busName = jQuery("#busName_").val();
		var id = jQuery("#id").val();
		if(!userId || userId == ""){
			alert("请选择相应用户!");
			return;
		}else{
			var url = ctx+'/userbusiness/buslist?userId='+userId+'&account='+account+'&busCode='+busCode+'&busName='+busName;
			window.location.href = url;
		}
	}
	if(flag == "back"){
			var url = ctx+'/userbusiness/list';
			window.location.href = url;
	}
	if(flag == "findBus"){
		var account = jQuery("#account").val();
		
		var id = jQuery("#userId").val();
		var busIds = jQuery("#busIds").val();
		
		var url = ctx + '/userbusiness/findBus?userId='+id+'&account='+account;
		window.location.href = url;
	}
	
}

function save(){
	
		var userId = jQuery("#userId").val();
		var records="";
		jQuery("input[name='check_li']:checkbox:checked").each(function(){ 
			records+=$(this).val()+",";
		}); 
		records=records.substring(0, records.length-1);
	    if (records=="") {
	        alert('未选中任何记录!');
	        return;
	    }else{
	    	var url = ctx + "/userbusiness/savebus";
	    	if(confirm("确定要添加选中的记录么?")){
	    		$.ajax({
	    			url:url,
	    			type:"post",
	    			data:{"records":records,"userId":userId},
	    			success:function(data){
	    				alert("添加成功！");
	    				$("#searchForm").attr("action",ctx+"/userbusiness/buslist");
	    				$("#searchForm").submit();
	    			},
	    			error:function(){
	    				alert("添加失败，请稍后再试！");
	    			}
	    		});
	    	}
	    	
	    }
		 
}
//根据条件查询查询用户
function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	var name = jQuery("#name_").val();
	var account = jQuery("#account_").val();
	
	var flag = $("#flag").val();
	if(flag=="buslist"){
		var userId = jQuery('#userId').val();
		var ac = jQuery('#account').val();
		var url = ctx + "/userbusiness/buslist";
		$("#"+formId).attr("action",url);
		$("#"+formId).submit();
	}else if(flag=="findBus"){
		var userId = $("#userId").val();
		var ac = $("#account_").val();
		var url = ctx + "/userbusiness/findBus";
		$("#"+formId).attr("action",url);
		$("#"+formId).submit();
	}else{
		var url = ctx + "/userbusiness/list?name="+name+"&account="+account;
		$("#"+formId).attr("action",url);
		$("#"+formId).submit();
	}
}
//查询重置
function resetVal(){
	$("#account_").val("");
	$("#name_").val("");
}
//业务绑定重置
function resetVal2(){
	$("#busCode_").val("");
	$("#busName_").val("");
	$("#busCode").val("");
	$("#busName").val("");
}
//返回buslist
function goBusList(){
	$("#searchForm").attr("action",ctx+"/userbusiness/buslist");
	$("#searchForm").submit();
}
