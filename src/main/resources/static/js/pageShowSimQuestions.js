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
	//分页
	$("#page").page({
		showInfo: true,
	    //showJump: true,
	    //showPageSizes: true,
	    infoFormat: '{start} ~ {end}条，共{total}条',
	    //total:1000
	    remote: {
	        url: '/admin/delSimQuestion/checkSimQuestions/getSimData',  //请求地址
	        params: { 
	        	id: $('#defaultQuestionId').val(),
	        	subject:$('input[name="currentSubjectName"]').val()
	        	},//用于测试       //自定义请求参数
	        beforeSend: function(XMLHttpRequest){
	            //...
	        },
	        success: function (result, pageIndex) {
	            //回调函数
	            //result 为 请求返回的数据，呈现数据
	        	alert('success');
	        	if(parseInt(result.total)==0){
	        		location.href='/admin/delSimQuestion';
	        	}else{
	        		 $("#test").empty().html($("#questionBoxTmpl").render(result.data));
	        	}
	        },
	        complete: function(XMLHttpRequest, textStatu){
	            //...
	        },
	        pageIndexName: 'pageIndex',     //请求参数，当前页数，索引从0开始
	        pageSizeName: 'pageSize',       //请求参数，每页数量
	        totalName: 'total'            //指定返回数据的总数据量的字段名
	    }
	});
	
//	$("#page").page({
//	showInfo: true,
//    //showJump: true,
//    //showPageSizes: true,
//    infoFormat: '{start} ~ {end}条，共{total}条',
//    //total:1000
//    remote: {
//        url: '/admin/delSimQuestion/getSimDataById',  //请求地址
//        params: { 
//        	id: $('#defaultQuestionId').val()
//        	},//用于测试       //自定义请求参数
//        beforeSend: function(XMLHttpRequest){
//            //...
//        },
//        success: function (result, pageIndex) {
//            //回调函数
//            //result 为 请求返回的数据，呈现数据
//        	alert('success');
//        	 $("#test").empty().html($("#questionBoxTmpl").render(result.data));
//        },
//        complete: function(XMLHttpRequest, textStatu){
//            //...
//        },
//        pageIndexName: 'pageIndex',     //请求参数，当前页数，索引从0开始
//        pageSizeName: 'pageSize',       //请求参数，每页数量
//        totalName: 'total'            //指定返回数据的总数据量的字段名
//    }
//});
	
	//是否展开完整信息
	$(document).on("click",'input[name="checkboxForFullInfo"]',function(){
		//alert('绑定成功');
		var div=$(this).parent().parent().parent();
		if($(this).prop('checked')){
			$(div).find('div[name="divFullInfo"]').css("display",""); 
		}else{
			$(div).find('div[name="divFullInfo"]').css("display","none"); 
		}
	});
	
	//单题忽略
	$(document).on("click",'button[name="btnIgnoreOne"]',function(){
		//alert('btnPassForOneQuestion绑定成功');
		//$(this).removeClass();
		$(this).parent().find('button').addClass('disabled');
		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
		var divparent = $(parents[parents.length-1]).parent();
		//alert(divparent);
		var id=$(divparent).find('input[name="simId"]').val();
		console.log('ignore sim question id:'+id);
		$.ajax({
			url:"/admin/delSimQuestion/checkSimQuestions/ignoreOne",
			data:{id:id},
			success:function(result){
				if(result==1||result=='1'){
					alert('成功');
					$("#page").page( 'remote');
				}else{
					alert('发生错误');
				}
			}
		});
	});
	
	
	
	
	//多题忽视
	$('button[name="btnIgnoreQuestions"]').click(function(){
		var idArr=[];
		$('input[name="checkBoxForSelect"]:checked').each(function(){
			var parents=$(this).parentsUntil('div[name="divQuestionBox"]');
			var divparent = $(parents[parents.length-1]).parent();
			//alert(divparent);
			var id=$(divparent).find('input[name="questionId"]').val();
			idArr.push(id);
		});
//		$.ajax({
//			url:"/admin/delSimQuestion/checkSimQuestions/ignoreOne",
//			data:{idArr:idArr},
//			success:function(result){
//				if(result==1||result=='1'){
//					alert('成功');
//					$("#page").page( 'remote'});
//				}else{
//					alert('发生错误');
//				}
//			}
//		});
		alert(JSON.stringify(idArr));
	});
	
	//删除单题
	$('button[name="btnDelOneQuestion"]').click(function(){
		$(this).parent().find('button').addClass('disabled');
		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
		var divparent = $(parents[parents.length-1]).parent();
		//alert(divparent);
		var id=$(divparent).find('input[name="questionId"]').val();
		$.ajax({
			url:"/admin/questionAdmin/deleteOne",
			data:{id:id},
			success:function(result){
				if(result==1||result=='1'){
					alert('成功');
					$("#page").page( 'remote');
				}else{
					alert('发生错误');
				}
			}
		});
		alert('delete sim question id:'+id);
	});
	
	//多题删除
	$('button[name="btnDelQuestions"]').click(function(){
		var idArr=[];
		$('input[name="checkBoxForSelect"]:checked').each(function(){
			var parents=$(this).parentsUntil('div[name="divQuestionBox"]');
			var divparent = $(parents[parents.length-1]).parent();
			//alert(divparent);
			var id=$(divparent).find('input[name="questionId"]').val();
			idArr.push(id);
		});
		$.ajax({
			url:"/admin/questionAdmin/deleteSome",
			data:{idArr:idArr},
			success:function(result){
				if(result==1||result=='1'){
					alert('成功');
					$("#page").page( 'remote');
				}else{
					alert('发生错误');
				}
			}
		});
		alert(JSON.stringify(idArr));
	});
	
	//忽略全部
	$('#btnIgnoreTheQuestion').click(function(){
		$(this).parent().find('button').addClass('disabled');
		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
		var divparent = $(parents[parents.length-1]).parent();
		//alert(divparent);
		var id=$(divparent).find('input[name="questionId"]').val();
		console.log('展示相似题目的页面：忽视全部');
		location.href='/admin/delSimQuestion';
//		$.ajax({
//		url:"/admin/delSimQuestion/ignoreOne",
//		data:{id:id},
//		success:function(result){
//			if(result==1||result=='1'){
//				alert('成功');
//				location.href='/admin/delSimQuestion';
//			}else{
//				alert('发生错误');
//			}
//		}
//		});
	});
	
	//删除原题
	$('#btnDelTheQuestion').click(function(){
		$(this).parent().find('button').addClass('disabled');
		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
		var divparent = $(parents[parents.length-1]).parent();
		//alert(divparent);
		var id=$(divparent).find('input[name="questionId"]').val();
		console.log('展示相似题目的页面：删除原题');
		location.href='/admin/delSimQuestion';
//		$.ajax({
//		url:"/admin/questionAdmin/deleteOne",
//		data:{id:id},
//		success:function(result){
//			if(result==1||result=='1'){
//				alert('成功');
//				location.href='/admin/delSimQuestion';
//			}else{
//				alert('发生错误');
//			}
//		}
//		});
	});
	
	//全选
	$('button[name="btnSelectAllQuestions"]').click(function(){
		$('input[name="checkBoxForSelect"]').each(function(){
			$(this).prop('checked',true);
		});
	});
	
});