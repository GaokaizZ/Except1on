/**
 * adimage 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-05-05 10:37:53
 */

jQuery(document).ready(function(){
    //增加全选事件
   	//jQuery(":checkbox[name='check_all']").checkbox().toggle(":checkbox[name='check_li']");
	
	//validateRules('saveForm');
	
	$(".sub_left_menu tbody tr").click(function() {
		$(".sub_left_menu tbody tr.active").removeClass("active");
		$(this).attr("class", "active");		
		var _url = ctx + "/adimage/look/json?id=" + $(this).attr("id");
	
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
function deleteAdImage() {
	var id = jQuery("#id").val();
	if (!id || id == "") {
		alert("没有可删除的数据");
		return;
	} else {
		var _url = ctx + "/adimage/delete";
		if(confirm("数据删除后将不能恢复,确定要删除选中的数据吗?")){
	    	$.ajax({
	    		url:_url,
	    		type:"post",
	    		data:"id="+id,
	    		dataType:"text",
	    		success:function(msg){
	    			if(msg==0){
	    				alert("恭喜你！删除成功");
	    				window.location.href = ctx + "/adimage/manage";
	    			}else{
	    				alert("删除失败");
	    				window.location.href = ctx + "/adimage/manage";
	    			}
	    		},
	    		error:function(){
	    			alert("系统异常，请稍后再试！");
	    		}
	    	});
		}
	}
}

// 删除多条
function deleteMore(){
    var records = jQuery(":checkbox[name='check_li']").checkbox().val();
    if (records.length == "") {
        myalert('未选中任何记录!');
        return;
    }
	var url = ctx + "/adimage/delete/more";
    myconfirm("记录删除后将不能恢复,确定要删除选中的记录么?",function(){
		jQuery.get(url, "records=" + records, function(data){
            myalert(data.message);
            myreloadpage();
        });
	});
}

function checkTitle(obj){
	var regStr = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	if(regStr.test(obj)){
		return true;
	}else{
		return false;
	}
}

function checkUrl(obj){
	var regStr =new RegExp("(http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?");
	if(regStr.test(obj)){
		return true;
	}else{
		return false;
	}
}

function saveAdImafge(){
	var id=$.trim($("#id").val());
	var name = $("#name").val();
	var remark = $("#reMark").val();
	var image = $("#image").val();
	var _url = ctx + "/adimage/update";
	var obj = document.getElementsByName("uploadFile")[0];
	var str = obj.value;
	if(name==null || name==""){
		alert('请输入标题!');
		return;
	}
	if(remark == null || remark == ""){
		alert('请选择链接!');
		return;
	}
	if(!checkUrl(remark)){
		alert("请输入正确链接");
		$("#reMark").focus();
		return;
	}
	if((str == null || str == "")&&(image==null || image=="")){
		alert('请选择图片!');
		return;
	}
	if (str != null && str != "") {
		var imgname = str.substring(str.lastIndexOf(".")+1).toUpperCase();
		if(imgname != "JPG" && imgname != "GIF" && imgname != "PNG"){
			alert('图片必须是gif或jpg,png格式!');
			return;
		}
	}
	$('#adimageForm').ajaxSubmit({
		url:_url,
		type:"post",
		dataType:"json",
		data:"name="+name+"&remark="+remark,
		success	: function(_json){
	        if(_json.status == "success"){
	        	alert(_json.message);
	        	window.location.href = ctx + "/adimage/manage";
	        }else{
	        	alert(_json.message);
	        	return;
	        }
	    }
  });
}

function resetUpd(){
	$("#reMark").val("");
	$("#name").val("");
}

function lookDemo(){
	var id=$.trim($("#id").val());
	var _url = ctx + "/adimage/lookdemo?id="+id;
	window.open(_url,'预览首页','height=650,width=420,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
}

function setImagePreview(file) {  
//	var iframetmp = document.getElementById("iframe").document;
	var obj = document.getElementsByName("uploadFile")[0];
	var str = obj.value;
	if (str != null && str != "") {
		var imgname = str.substring(str.lastIndexOf(".")+1).toUpperCase();
		if(imgname != "JPG" && imgname != "GIF" && imgname != "PNG"){
			alert('图片必须是gif或jpg,png格式!');
			return false;
		}
	}
	var imgObjPreview=document.getElementById("iframe").contentWindow.document.getElementById("preciew");
//	imgObjPreview.src=file.value;
//    var imgObjPreview=iframetmp.getElementById("preciew");  
    if(file.files && file.files[0]){  
        //火狐下，直接设img属性  
        imgObjPreview.style.display = 'block';  
        imgObjPreview.style.width = '370px';  
        imgObjPreview.style.height = '140px';                      
        //imgObjPreview.src = docObj.files[0].getAsDataURL();  
          
        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式    
        imgObjPreview.src = window.URL.createObjectURL(file.files[0]);  
    }else{  
        //IE下，使用滤镜  
        file.select();  
//        var imgSrc = document.selection.createRange().text;  
        var imgSrc = file.value;
        var localImagId = document.getElementById("iframe").contentWindow.document.getElementById("localImag");  
        //必须设置初始大小  
        localImagId.style.width = "370px";  
        localImagId.style.height = "140px";  
        //图片异常的捕捉，防止用户修改后缀来伪造图片  
        try{  
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;  
        }catch(e){  
            alert("IE浏览器请在工具-Internet选项-安全-自定义中设置允许本地路径");  
            return false;  
        }  
        imgObjPreview.style.display = 'none';  
        document.selection.empty();  
    }  
    return true;  
}