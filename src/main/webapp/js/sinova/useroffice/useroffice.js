/**
 * useroffice 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:16:10
 */

//添加用户到营业厅
function addUserOffice(){
	var records="";
	$("input[name='check_li']:checkbox:checked").each(function(){ 
		records+=$(this).val()+",";
	}) 
	
	records=records.substring(0, records.length-1);
    if (records=="") {
        alert('未选中任何记录!');
        return;
    }
    
    var regionCode = $("#regionCode").val();
	var countyCode = $("#countyCode").val();
	var officeCode = $("#officeCode").val();
	
	var url = ctx + "/useroffice/save";
	if(confirm("确定要添加选中的记录么?")){
    	$.ajax({
    		url:url,
    		type:"post",
    		data:"records="+records+"&regionCode="+regionCode+"&countyCode="+countyCode+"&officeCode="+officeCode,
    		success:function(msg){
    			if(msg.status=="success"){
    				alert("恭喜你！添加成功");
    				window.location.href = ctx  + "/useroffice/list?officeCode=" + officeCode+"&regionCode="+regionCode+"&countyCode="+countyCode;
    			}else{
    				alert("添加失败");
    				window.location.href = ctx  + "/useroffice/list?officeCode=" + officeCode+"&regionCode="+regionCode+"&countyCode="+countyCode;
    			}
    		},
    		error:function(){
    			alert("系统异常，请稍后再试！");
    		}
    	});
	}
	
}
