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


function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	$("#searchForm").submit();
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

//按照地市来进行业务量的查询有地市条件
function queryByRegion(regionCode){
	window.location.href = ctx +"/businesstongji/listByCity?regionCode="+regionCode;
}

function queryByOffice(officeCode,regionCode){
	window.location.href = ctx +"/acceptrecord/listReportByOffice?regionCode="+regionCode+"&officeCode="+officeCode;
}

//导出地市办理量
function expRegion(){
	var url = ctx + "/businesstongji/exportRegion";
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
	$("#searchForm").attr("action",ctx+"/businesstongji/businesstongjiList");

}
