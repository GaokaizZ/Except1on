/**
 * acceptrecord 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:17:04
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
	

function showdata(result) {

	for (var s in result.data) {
		set_val(s, result.data[s]);
	}
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
	$("#workno_").val("");
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
	    url = ctx + "/acceptfail/listexport?type=PART&records="+records;
	}else if('ALL' == type){
		if($("#totalCount").val() > 500){
	        alert('最多可以导出500条数据，请筛选后重新导出!');
	        return;
		}
		 url = ctx + "/acceptfail/listexport?type=ALL";
	}else{
        alert('请联系管理员!');
        return;
	}

	
	$("#searchForm").attr("action",url);
	$("#searchForm").submit();
	$("#searchForm").attr("action",ctx+"/acceptfail/list");

}