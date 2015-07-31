$(function(){
	//服务器端返回的json
	//alert("pageQuestionAdmin");
	//获得知识点
	function getTree() {
	    // Some logic to retrieve, or generate tree structure
//		var data = '[' +
//	    '{' +
//	      '"text": "Parent 1",' +
//	      '"nodes": [' +
//	        '{' +
//	          '"text": "Child 1",' +
//	          '"nodes": [' +
//	            '{' +
//	              '"text": "Grandchild 1"' +
//	            '},' +
//	            '{' +
//	              '"text": "Grandchild 2"' +
//	            '}' +
//	          ']' +
//	        '},' +
//	        '{' +
//	          '"text": "Child 2"' +
//	        '}' +
//	      ']' +
//	    '},' +
//	    '{' +
//	      '"text": "Parent 2",' +
//	      '"tags":["available"]'+
//	    '},' +
//	    '{' +
//	      '"text": "Parent 3"' +
//	    '},' +
//	    '{' +
//	      '"text": "Parent 4"' +
//	    '},' +
//	    '{' +
//	      '"text": "Parent 5"' +
//	    '}' +
//	  ']';
//		$.post('/getKnow', {
//			subjectId:$('input[name="currentSubjectId"]').val(),
//			gradeId:$('input[name="currentGradeId"]').val(),
//			fieldId:$('input[name="currentFieldId"]').val(),
//			standardId:$('input[name="currentStandardId"]').val()
//			},function (result, status) { data=result });
		var data=[];
		temp1={};
		temp1.text='parent1';
		var node1=[];
		var child={};
		child.text='child1';
		node1.push(child);
		temp1.nodes=node1;
		data.push(temp1);
		temp2={};
		temp2.text='parent2';
		data.push(temp2)
		temp3={};
		temp3.text='parent3';
		data.push(temp3)
	    return data;
	}
	
	//获得标准,标准由科目决定，subjectId
	function getStandard(){
		var data=[];
		var temp2={};
		temp2.id=1;
		temp2.name='课标';
		data.push(temp2);
		var temp3={};
		temp3.id=1;
		temp3.name='中考';
		data.push(temp3);
		var temp4={};
		temp4.id=1;
		temp4.name='高考';
		data.push(temp4);
		//alert(JSON.stringify(data));
		//$.post('/getGrade', {subjectId:$('input[name="currentSubjectId"]').val()},function (result, status) { data=result;});
		return data;
	}
	
	//获得年级,由科目决定，subjectId
	function getGrade(){
		var data=[];
		var temp2={};
		temp2.id=1;
		temp2.name='初一';
		data.push(temp2);
		var temp3={};
		temp3.id=1;
		temp3.name='初二';
		data.push(temp3);
		var temp4={};
		temp4.id=1;
		temp4.name='初三';
		data.push(temp4);
		//alert(JSON.stringify(data));
		//$.post('/getGrade', {subjectId:$('input[name="currentSubjectId"]').val()},function (result, status) { data=result;});
		return data;
	}
	
	//获得题型,由科目决定，subjectId
	 function getType(){
		 var data=[];
		 var temp2={};
		 temp2.id=1;
		 temp2.name='单选题';
		 data.push(temp2);
		 var temp3={};
		 temp3.id=2;
		 temp3.name='多选题';
		 data.push(temp3);
		 var temp4={};
		 temp4.id=3;
		 temp4.name='填空题';
		 data.push(temp4);
		 var temp5={};
		 temp5.id=4;
		 temp5.name='其他';
		 data.push(temp5);
		//$.post('/getType', {subjectId:$('input[name="currentSubjectId"]').val()},function (result, status) { data=result });
		return data;
	 }
	 
	//筛选知识点的树形结构
	$('#treeviewOfKPoint_pQuestionAdmin').treeview({
        color: "#428bca",
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        nodeIcon: 'glyphicon glyphicon-bookmark',
        data: "",
        showBorder:false,
        showTags:true
    });
	
	//选择科目
	$(document).on("click",'a[name="subjectSubName2"]',function(){
		$('div[name="divForSelectSubject"]').find('.box-body').toggle(200);
		$('div[name="divForSelectSubject"]').addClass('collapsed-box');
		$('div[name="divForSelectSubject"]').find('i').removeClass('fa-minus').addClass('fa-plus');
		var subjectName=$(this).parent().parent().find('h5[name=subjectSubName1]').text()+$(this).text();
		$('b[name="currentSubjectName"]').text(subjectName);
		$('input[name="currentSubjectId"]').val($(this).find('input').val());
		//alert($('input[name="currentSubjectId"]').val());
		$("#page").page( 'remote',0 ,$('b[name="currentSubjectName"]').text());
		
		//重新获得知识点
		$('#treeviewOfKPoint_pQuestionAdmin').treeview({data:getTree()});
		//重新获得年级
		$("#spanGradeList").empty().html($("#gradeTmpl").render(getGrade()));
		//重新获得题型
		$("#spanTypeList").empty().html($("#typeTmpl").render(getType()));
		//重新获得标准
		$("#spanStandardList").empty().html($("#standardTmpl").render(getStandard()));
	});
	
	//测试pagination插件
	//$("#page").page({ total: 1000 });
	$("#page").page({
		showInfo: true,
	    //showJump: true,
	    //showPageSizes: true,
	    infoFormat: '{start} ~ {end}条，共{total}条',
	    //total:1000
	    remote: {
	        url: '/getJsonData',  //请求地址
	        params: { 
	        	subjectId:$('input[name="currentSubjectId"]').val(),
				gradeId:$('input[name="currentGradeId"]').val(),
				fieldId:$('input[name="currentFieldId"]').val(),
				standardId:$('input[name="currentStandardId"]').val(),
				knowId:$('input[name="currentKnowId"]').val(),
				hardness:$('input[name="currentHardness"]').val()
	        	},       //自定义请求参数
	        beforeSend: function(XMLHttpRequest){
	            //...
	        },
	        success: function (result, pageIndex) {
	            //回调函数
	            //result 为 请求返回的数据，呈现数据
	        	alert('success');
	        	 $("#divQuestions").empty().html($("#questionBoxTmpl").render(result.data));
	        },
	        complete: function(XMLHttpRequest, textStatu){
	            //...
	        },
	        pageIndexName: 'pageIndex',     //请求参数，当前页数，索引从0开始
	        pageSizeName: 'pageSize',       //请求参数，每页数量
	        totalName: 'total'            //指定返回数据的总数据量的字段名
	    }
	});
	
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
	
	//添加单题至组卷
	$(document).on("click",'button[name="btnAddOneToPaper"]',function(){
		//alert('btnPassForOneQuestion绑定成功');
		//$(this).removeClass();
		$(this).parent().find('button').addClass('disabled');
		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
		var divparent = $(parents[parents.length-1]).parent();
		//alert(divparent);
		var id=$(divparent).find('input[name="questionId"]').val();
		alert('add one question to paper id:'+id);
	});
	
	//单题修改
	$(document).on("click",'button[name="btnModifyOneQuestion"]',function(){
		//alert('btnPassForOneQuestion绑定成功');
		//$(this).removeClass();
		$(this).parent().find('button').addClass('disabled');
		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
		var divparent = $(parents[parents.length-1]).parent();
		//alert(divparent);
		var id=$(divparent).find('input[name="questionId"]').val();
		alert('modify question id:'+id);
	});
	
	//单题删除
	$(document).on("click",'button[name="btnDelOneQuestion"]',function(){
		//alert('btnPassForOneQuestion绑定成功');
		//$(this).removeClass();
		$(this).parent().find('button').addClass('disabled');
		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
		var divparent = $(parents[parents.length-1]).parent();
		//alert(divparent);
		var id=$(divparent).find('input[name="questionId"]').val();
		alert('del question id:'+id);
	});
	
	//单题报错
	$(document).on("click",'button[name="btnReportOneError"]',function(){
		//alert('btnPassForOneQuestion绑定成功');
		//$(this).removeClass();
		$(this).parent().find('button').addClass('disabled');
		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
		var divparent = $(parents[parents.length-1]).parent();
		//alert(divparent);
		var id=$(divparent).find('input[name="questionId"]').val();
		alert('report one error question id:'+id);
	});
	
	//多题审核通过
	$('button[name="btnAddQuestionsToPaper"]').click(function(){
		var idArr=[];
		$('input[name="checkBoxForSelect"]:checked').each(function(){
			var parents=$(this).parentsUntil('div[name="divQuestionBox"]');
			var divparent = $(parents[parents.length-1]).parent();
			//alert(divparent);
			var id=$(divparent).find('input[name="questionId"]').val();
			idArr.push(id);
		});
		
		alert(JSON.stringify(idArr));
	});
	
	//全选
	$('button[name="btnSelectAllQuestions"]').click(function(){
		$('input[name="checkBoxForSelect"]').each(function(){
			$(this).prop('checked',true);
		});
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
		
		alert(JSON.stringify(idArr));
	});
});