$(function(){
	//$("#page").page({ total: 1000 });
	
//	$("#page").page({
//	    remote: {
//	        url: '/getJsonData',  //请求地址
//	        params: { query: "test" },       //自定义请求参数
//	        beforeSend: function(XMLHttpRequest){
//	            //...
//	        },
//	        success: function (result, pageIndex) {
//	            //回调函数
//	            //result 为 请求返回的数据，呈现数据
//	        	alert('success');
//	        	 $("#test").empty().html($("#questionBoxTmpl").render(result.data));
//	        },
//	        complete: function(XMLHttpRequest, textStatu){
//	            //...
//	        },
//	        showInfo: true,
//	        showPageSizes: true,
//	        pageIndexName: 'pageIndex',     //请求参数，当前页数，索引从0开始
//	        pageSizeName: 'pageSize',       //请求参数，每页数量
//	        totalName: 'total',              //指定返回数据的总数据量的字段名
//	        infoFormat: '{start} ~ {end}条，共{total}条'
//	    }
//	});
//	
	$("#page").page({
		showInfo: true,
	    //showJump: true,
	    //showPageSizes: true,
	    infoFormat: '{start} ~ {end}条，共{total}条',
	    //total:1000
	    remote: {
	        url: '/getJsonData',  //请求地址
	        params: { query: "test" },       //自定义请求参数
	        beforeSend: function(XMLHttpRequest){
	            //...
	        },
	        success: function (result, pageIndex) {
	            //回调函数
	            //result 为 请求返回的数据，呈现数据
	        	alert('success');
	        	 $("#test").empty().html($("#questionBoxTmpl").render(result.data));
	        },
	        complete: function(XMLHttpRequest, textStatu){
	            //...
	        },
	        pageIndexName: 'pageIndex',     //请求参数，当前页数，索引从0开始
	        pageSizeName: 'pageSize',       //请求参数，每页数量
	        totalName: 'total'            //指定返回数据的总数据量的字段名
	    }
	});
});