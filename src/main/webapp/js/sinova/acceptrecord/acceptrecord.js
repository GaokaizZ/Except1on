/**
 * acceptrecord 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:17:04
 */

jQuery(document).ready(function(){
    //增加全选事件
   	//jQuery(":checkbox[name='check_all']").checkbox().toggle(":checkbox[name='check_li']");
	
	//validateRules('saveForm');
	
	$(".sub_left_menu tbody tr").click(function() {
		$(".sub_left_menu tbody tr.active").removeClass("active");
		$(this).attr("class", "active");		
		var _url = ctx + "/acceptrecord/look/json?id=" + $(this).attr("id");
	
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



// 删除多条
function deleteAcceptRecord(){
	var records="";
	$("input[name='check_li']:checkbox:checked").each(function(){ 
		records+=$(this).val()+",";
	}) 
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
    
	var url = ctx + "/acceptrecord/delete/more";
	jQuery.get(url, "records=" + records, function(data){
        alert("恭喜你，删除成功！");
        $("#searchForm").attr("action",ctx+"/acceptrecord/list");
        $("#searchForm").submit();
    });
}
//选时间
function selectTime(){
	var endTime=$("#endTime_").val();
	if(""!=endTime){
		WdatePicker({el:'beginTime_',maxDate:'#F{$dp.$D(\'endTime_\')||%y-%M-%d}'});
	}
}

//重置
function resetVal(){
	$("#mobile_").val("");
	$("#beginTime_").val("");
	$("#endTime_").val("");
	$("#account_").val("");
	$("#officeName_").val("");
	$("#regionName_").val("");
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
//查询办理量
function submitForm(formId,_target){
	//分页
	if (typeof(_target) == "undefined" || _target == "") { 
	   
		}else{
			$("#pageIndex").val("1");
		}
	$("#"+formId).submit();
}

function queryByRegion(regionCode){
	window.location.href = ctx +"/acceptrecord/listReportByRegion?regionCode="+regionCode;
}
function queryByOffice(officeCode,regionCode){
	window.location.href = ctx +"/acceptrecord/listReportByOffice?regionCode="+regionCode+"&officeCode="+officeCode;
}
//导出受理记录
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
	    url = ctx + "/acceptrecord/listexport?type=PART&records="+records;
	}else if('ALL' == type){
		if($("#totalCount").val() > 500){
	        alert('最多可以导出500条数据，请筛选后重新导出!');
	        return;
		}
		 url = ctx + "/acceptrecord/listexport?type=ALL";
	}else{
        alert('请联系管理员!');
        return;
	}

	
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
	$("#searchForm").attr("action",ctx+"/acceptrecord/list");

}

//导出地市办理量
function expRegion(){
	
	var url = ctx + "/acceptrecord/exportRegion";
	
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
	$("#searchForm").attr("action",ctx+"/acceptrecord/listReport");

}
//导出营业厅办理量
function expOffice(regionCode){

	var url = ctx + "/acceptrecord/exportOffice?regionCode="+regionCode;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
	$("#searchForm").attr("action",ctx +"/acceptrecord/listReportByRegion?regionCode="+regionCode);

}
//导出个人办理量
function expSales(officeCode,regionCode){

	var url = ctx + "/acceptrecord/exportSales?regionCode="+regionCode+"&officeCode="+officeCode;
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
	$("#searchForm").attr("action",ctx +"/acceptrecord/listReportByOffice?regionCode="+regionCode+"&officeCode="+officeCode);

}