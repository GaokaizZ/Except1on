/**
 * brand 页面使用javascript
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 08:57:50
 */

jQuery(document).ready(function(){
    //增加全选事件
   	//jQuery(":checkbox[name='check_all']").checkbox().toggle(":checkbox[name='check_li']");
	
	//validateRules('saveForm');
	
	$(".sub_left_menu tbody tr").click(function() {
		$(".sub_left_menu tbody tr.active").removeClass("active");
		$(this).attr("class", "active");		
		var _url = ctx + "/brand/look/json?id=" + $(this).attr("id");
	
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
function deleteBrand() {
	var id = jQuery("#id").val();
	if (!id || id == "") {
		myalert("请选择你要删除的记录");
		return;
	} else {
		var _url = ctx + "/brand/delete?id=" + id;
		var listurl = ctx + "/brand/list";
		mydelete(_url, listurl);
	}
}

// 删除多条
function deleteMore(){
    var records = jQuery(":checkbox[name='check_li']").checkbox().val();
    if (records.length == "") {
        myalert('未选中任何记录!');
        return;
    }
	var url = ctx + "/brand/delete/more";
    myconfirm("记录删除后将不能恢复,确定要删除选中的记录么?",function(){
		jQuery.get(url, "records=" + records, function(data){
            myalert(data.message);
            myreloadpage();
        });
	});
}


