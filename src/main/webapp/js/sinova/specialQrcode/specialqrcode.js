/**
 * qrcode 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:39
 */

jQuery(document).ready(function(){
	
	
});
var a=1;
function update(){
	if(a==1){
		a=0;
	//未选中的记录
	var records="";
	obj = $("#table1  .tb_content_con tbody .c-check").parents(".tb_content_con").find("input[name=items3]");
	intEnptyCounter = 0;
	for(i = 0 ; i < obj.length ;  i++){
		if(obj[i].checked == false){
			if(intEnptyCounter > 0){
            	records = records + ","  + obj[i].value;
        	}else{
            	records = obj[i].value;
            }  
        	intEnptyCounter = intEnptyCounter + 1;
		}
	}
	//选中的记录
	var checkss="";
	$("tbody input[name='items3']:checkbox:checked").each(function(){
			checkss+=$(this).val()+",";
		})
	checkss=checkss.substring(0, checkss.length-1);
	if(confirm("确定修改数据吗?")){
		$.ajax({
			url:ctx + "/specialqrcode/update",
			type:"post",
			data:"records="+records+"&checkss="+checkss,
			dataType:"json",
			success:function(msg){
				if(msg.status=="success"){
					alert("恭喜你！修改成功。");
					a=1;
					window.location.href = ctx +"/specialqrcode/list";
				}else{
					alert("修改失败！");
					a=1;
					window.location.href = ctx +"/specialqrcode/list";
				}
			},
			error:function(){
				a=1;
				alert("系统异常，请稍后再试！");
			}
		});
	}
 }else{
	 alert("不能重复提交");
 }
}

//条件查询
function mySubmitForm(formId,_target){
	if (typeof(_target) == "undefined" || _target == "") { 
		   
	}else{
		$("#pageIndex").val("1");
	}
	$("#"+formId).submit();
}

//重置
function resetVal(){
	jQuery("#busCode").val("");
	jQuery("#busName").val("");
	jQuery("#type").val("");
}
