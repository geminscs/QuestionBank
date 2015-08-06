$(function(){
	//$("#page").page({ total: 1000 });
	
	//分页
	$("#page").page({
		showInfo: true,
	    //showJump: true,
	    //showPageSizes: true,
	    infoFormat: '{start} ~ {end}条，共{total}条',
	    //total:1000
	    remote: {
	        url: '/admin/delSimQuestion/getSimData',  //请求地址
	        params: { 
	        	subjectId: $('input[name="currentSubjectId"]').val()
	        	},//用于测试       //自定义请求参数
	        beforeSend: function(XMLHttpRequest){
	            //...
	        },
	        success: function (result, pageIndex) {
	            //回调函数
	            //result 为 请求返回的数据，呈现数据
	        	alert('success');
	        	if(parseInt(result.total)==0){
	        		$("#test").empty();
	        		$('#divNoResult').css('display','');
	        	}else{
	        		$('#divNoResult').css('display','none');
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
//		showInfo: true,
//	    //showJump: true,
//	    //showPageSizes: true,
//	    infoFormat: '{start} ~ {end}条，共{total}条',
//	    //total:1000
//	    remote: {
//	        url: '/admin/delSimQuestion/getSimData',  //请求地址
//	        params: { 
//	        	subjectId: $('input[name="currentSubjectId"]').val(),
//	        	},//用于测试       //自定义请求参数
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
//	        pageIndexName: 'pageIndex',     //请求参数，当前页数，索引从0开始
//	        pageSizeName: 'pageSize',       //请求参数，每页数量
//	        totalName: 'total'            //指定返回数据的总数据量的字段名
//	    }
//	});
	
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
		var id=$(divparent).find('input[name="questionId"]').val();
//		$.ajax({
//			url:"/admin/delSimQuestion/ignoreOne",
//			data:{id:id},
//			success:function(result){
//				if(result==1||result=='1'){
//					alert('成功');
//					$("#page").page( 'remote',0 ,{subjectId:$('input[name="currentSubjectId"]').val()});
//				}else{
//					alert('发生错误');
//				}
//			}
//		});
		alert('ignore sim question id:'+id);
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
//			url:"/admin/delSimQuestion/ignoreSome",
//			data:{idArr:idArr},
//			success:function(result){
//				if(result==1||result=='1'){
//					alert('成功');
//					$("#page").page( 'remote',0 ,{subjectId:$('input[name="currentSubjectId"]').val()});
//				}else{
//					alert('发生错误');
//				}
//			}
//		});
		alert(JSON.stringify(idArr));
	});
	
	//全选
	$('button[name="btnSelectAllQuestions"]').click(function(){
		$('input[name="checkBoxForSelect"]').each(function(){
			$(this).prop('checked',true);
		});
	});
	
	
	
	//选择科目
	$(document).on("click",'a[name="subjectSubName2"]',function(){
		$('div[name="divForSelectSubject"]').find('.box-body').toggle(200);
		$('div[name="divForSelectSubject"]').addClass('collapsed-box');
		$('div[name="divForSelectSubject"]').find('i').removeClass('fa-minus').addClass('fa-plus');
		var subjectName=$(this).parent().parent().find('h5[name=subjectSubName1]').text()+$(this).text();
		$('b[name="currentSubjectName"]').text(subjectName);
		alert($('b[name="currentSubjectName"]').text());
		$("#page").page( 'remote',0 ,{subjectId:$('input[name="currentSubjectId"]').val()});
		
	});
	
});