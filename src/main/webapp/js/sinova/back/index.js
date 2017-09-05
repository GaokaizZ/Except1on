// JavaScript Document
$('a').bind("focus", function(){    $(this).blur();})
//为右侧iframe宽高赋值 开始
	$(function(){
		if ($.browser.msie && ($.browser.version == '6.0') && !$.support.style) {//判断浏览器是否为IE6
			var winHeight = $(window).height() - 107;	
			var winWidth = $(window).width() - 220;
			$(".mainBoxWarp").height(winHeight);
			$("#iframeRight").width(winWidth);
			$("#iframeRight").height(winHeight);
			$(".pcs_tab_ul").width(winWidth);
			$(".myspan").width(winWidth /11 - 38);
			$("#treeBox").height(winHeight - 50);
			//$(".bmLeft").height(winHeight + 40);左侧树的高度
			$("#navBOX").width(winWidth);
			$("#nav_1").width(winWidth);
			$("#nav_1").find("span").width(winWidth /10);
			//var leng = $("#nav_1").children("span").length + 1;
			//$("#nav_1").find("span").width(winWidth /leng);
			//$("#gnqxNew").height(winHeight);
		}else{
			setInterval(function(){
				var winHeight = $(window).height() - 107;	
				var winWidth = $(window).width() - 220;
				$(".mainBoxWarp").height(winHeight);
				$("#iframeRight").width(winWidth);
				$("#iframeRight").height(winHeight);
				$(".pcs_tab_ul").width(winWidth);
				$(".myspan").width(winWidth /11 - 40);
				$("#treeBox").height(winHeight - 50);
				//$(".bmLeft").height(winHeight + 40);左侧树的高度
				$("#navBOX").width(winWidth);
				$("#nav_1").width(winWidth);
				$("#nav_1").find("span").width(winWidth /10);
				//var leng = $("#nav_1").children("span").length + 1;
				//$("#nav_1").find("span").width(winWidth /leng);
				//$("#gnqxNew").height(winHeight);
			},1)
		}
					 
	});
	//拖拽窗口 重新计算iframe宽高
	window.onresize = function (){
		var winHeight = $(window).height() - 107;	
			var winWidth = $(window).width() - 220;
			$(".mainBoxWarp").height(winHeight);
			$("#iframeRight").width(winWidth);
			$("#iframeRight").height(winHeight);
			$(".pcs_tab_ul").width(winWidth);
			$(".myspan").width(winWidth /11 - 38);
			$("#treeBox").height(winHeight - 50);
			//$(".bmLeft").height(winHeight + 40);左侧树的高度
			$("#navBOX").width(winWidth);
			$("#nav_1").width(winWidth);
			$("#nav_1").find("span").width(winWidth /10);
			//var leng = $("#nav_1").children("span").length + 1;
			//$("#nav_1").find("span").width(winWidth /leng);
			//$("#gnqxNew").height(winHeight);
	}
	//拖拽窗口 重新计算iframe宽高
//为右侧iframe宽高赋值 结束
