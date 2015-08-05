$(function(){
	//服务器端返回的json
	//alert("pageQuestionAdmin");
	//测试用Data
	
	$.getJSON('/admin/questionAdmin/getKnow', {
		subjectId:$('input[name="currentSubjectId"]').val(),
		gradeId:$('input[name="currentGradeId"]').val(),
		fieldId:$('input[name="currentFieldId"]').val(),
		standardId:$('input[name="currentStandardId"]').val()
		},function (result, status) {
			$('#tree1').tree({data:result});
			$('input[name="currentKnowId"]').val(-1);
			});
	
	//节点的点击事件
	$('#tree1').bind(
		    'tree.click',
		    function(event) {
		        // The clicked node is 'event.node'
		        var node = event.node;
		        $('input[name="currentKnowId"]').val(node.id);
		        alert('当前科目id：'+$('input[name="currentSubjectId"]').val()+',年级id:'+$('input[name="currentGradeId"]').val()
						+',题型id：'+$('input[name="currentTypeId"]').val()+',标准id:'+$('input[name="currentStandardId"]').val()
						+',难度：'+$('input[name="currentHardness"]').val()+',地区id'+$('input[name="currentFieldId"]').val()
						+',节点id：'+ $('input[name="currentKnowId"]').val());
		        getPage();
		    }
		);
	
	//获得知识点,由科目、年级、标准、地区决定
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
		
		$('input[name="currentKnowId"]').val(-1)
		$.getJSON('/admin/questionAdmin/getKnow', {
			subjectId:$('input[name="currentSubjectId"]').val(),
			gradeId:$('input[name="currentGradeId"]').val(),
			fieldId:$('input[name="currentFieldId"]').val(),
			standardId:$('input[name="currentStandardId"]').val()
			},function (result, status) {
				
				$('#tree1').tree('loadData', result);
				
				});
		//var data2 = [{"children":[{"children":[{"children":[{"label":"函数的最大值和最小值"},{"label":"增函数、减函数的概念"},{"label":"函数的单调性、单调区间"}],"label":"单调性与最大（小）值"},{"children":[{"label":"奇函数、偶函数的概念"},{"label":"奇函数、偶函数的性质"}],"label":"奇偶性"}],"label":"函数的基本性质"},{"label":"集合的基本运算"},{"children":[{"children":[{"label":"函数的概念"},{"label":"函数符号y=f(x)"},{"label":"函数的定义域"},{"label":"函数的值域"},{"label":"函数的定义域"}],"label":"函数的概念"},{"children":[{"label":"函数的解析法表示"},{"label":"函数的图象法表示，描点法作图"},{"label":"函数的列表法表示"},{"label":"分段函数的意义与应用"},{"label":"映射的概念"}],"label":"函数的表示法"},{"label":"映射的概念"},{"label":"函数的基本性质"},{"label":"函数的基本性质"},{"label":"函数的基本性质"},{"children":[{"label":"单调性与最大（小）值"},{"label":"奇偶性"},{"children":[{"label":"函数的最大值和最小值"},{"label":"增函数、减函数的概念"},{"label":"函数的单调性、单调区间"}],"label":"单调性与最大（小）值"},{"children":[{"label":"奇函数、偶函数的概念"},{"label":"奇函数、偶函数的性质"}],"label":"奇偶性"}],"label":"函数的基本性质"}],"label":"函数的概念及表示"},{"children":[{"children":[{"label":"集合的含义"},{"label":"集合元素的特性"},{"label":"集合的相等"},{"label":"集合与元素关系"},{"label":"常用数集的记法"},{"label":"集合的表示法"}],"label":"集合的定义和表示"},{"children":[{"label":"子集、真子集的概念"},{"label":"空集的概念"}],"label":"集合间的基本关系"},{"label":"集合间的关系"},{"label":"集合的基本运算"},{"children":[{"label":"并集的含义"},{"label":"交集的含义"},{"label":"全集与补集"}],"label":"集合的基本运算"}],"label":"集合"}],"label":"集合与函数"},{"children":[{"children":[{"label":"任意角"},{"label":"弧度制"}],"label":"任意角的三角函数"},{"children":[{"label":"任意角的三角函数"},{"label":"同角三角函数的基本关系"}],"label":"任意角的三角函数"},{"children":[{"children":[{"label":"π+α与α的正弦、余弦、正切值的关系"},{"label":"-α与α的正弦、余弦、正切值的关系"},{"label":"π-α与的正弦、余弦、正切值的关系"},{"label":"与α的正弦、余弦值的关系"}],"label":"三角函数的诱导公式"}],"label":"三角函数的诱导公式"},{"children":[{"label":"正弦函数、余弦函数的图象"},{"label":"正弦函数、余弦函数的性质"},{"label":"正切函数的性质和图象"},{"label":"y=Asin(ωx+ψ)的图象"},{"label":"三角函数模型的简单应用"}],"label":"三角函数的图象和性质"}],"label":"三角函数"},{"children":[{"children":[{"label":"数列的定义"},{"label":"数列几种简单表示"}],"label":"数列的概念与简单表示法"},{"label":"等差数列"},{"children":[{"label":"向量的物理背景与概念"},{"label":"向量的几何表示"}],"label":"平面向量的实际背景及基本概念"},{"label":"平面向量的线性运算"},{"label":"平面向量的基本定理及坐标表示"},{"label":"平面向量的数量积"},{"label":"平面向量应用举例"}],"label":"平面向量"},{"children":[{"children":[{"children":[{"label":"根式的意义"},{"label":"分数指数幂的意义"},{"label":"无理数指数幂的意义"},{"label":"有理数指数幂的运算性质"}],"label":"指数与指数幂的运算"},{"children":[{"label":"指数函数的概念"},{"label":"指数函数的图象"},{"label":"指数函数的性质"}],"label":"指数函数及其性质"}],"label":"指数函数"},{"children":[{"children":[{"label":"对数的概念"},{"label":"常用对数与自然对数"},{"label":"对数的运算性质"},{"label":"对数的换底公式"}],"label":"对数与对数运算"},{"children":[{"label":"对数函数的概念"},{"label":"对数函数的图象"},{"label":"对数函数的性质"},{"label":"指数函数与对数函数的关系"}],"label":"对数函数及其性质"}],"label":"对数函数"},{"children":[{"children":[{"label":"幂函数的概念"},{"label":"幂函数的图象"},{"label":"幂函数的性质"}],"label":"幂函数"}],"label":"幂函数"}],"label":"基本初等函数"},{"children":[{"children":[{"label":"方程的根与函数的零点"},{"label":"用二分法求方程的近似解"}],"label":"函数与方程"},{"children":[{"label":"精确度与近似解"},{"label":"二分法求f(x)=0零点的基本方法"},{"label":"二分法求f(x)=0零点的基本步骤"}],"label":"函数模型及其应用"}],"label":"函数的应用"},{"children":[{"label":"两角和与差的正弦、余弦和正切公式"},{"label":"简单的三角恒等变换"}],"label":"三角恒等变换"},{"children":[{"label":"数列的概念与简单表示"},{"label":"等差数列"},{"label":"等差数列的前n项的和"},{"label":"等比数列"},{"label":"等比数列的前n项的和"},{"label":"数列的综合应用"}],"label":"数列"},{"children":[{"label":"不等关系与不等式"},{"label":"一元二次不等式及其解法"},{"label":"二元一次不等式（组）与简单线性规划问题"},{"label":"基本不等式"}],"label":"不等式"},{"children":[{"label":"空间几何体的结构"},{"label":"空间几何体的三视图和直观图"},{"label":"空间几何体的表面积与体积"}],"label":"空间几何体"},{"children":[{"label":"空间点、直线、平面之间的位置关系"},{"label":"直线、平面平行的判定及其性质"},{"label":"直线、平面垂直的判定及其性质"}],"label":"第二章点、直线、平面之间的位置关系"},{"children":[{"label":"直线的倾斜角与斜率"},{"label":"直线的方程"},{"label":"直线的交点坐标与距离公式"}],"label":"直线与方程"},{"children":[{"label":"圆的方程"},{"label":"直线、圆的位置关系"},{"label":"空间直角坐标系"}],"label":"圆的方程"},{"children":[{"label":"命题及其关系"},{"label":"充分条件与必要条件"},{"label":"简单的逻辑联结词"},{"label":"全称量词与存在量词"}],"label":"常用逻辑用语"},{"children":[{"label":"曲线与方程"},{"label":"椭圆"},{"label":"双曲线"},{"label":"抛物线"}],"label":"圆锥曲线与方程"},{"children":[{"label":"正弦定理和余弦定理"},{"label":"应用举例"}],"label":"解三角形"},{"children":[{"label":"空间向量及其运算"},{"label":"立体几何中的向量方法"}],"label":"空间向量与立体几何"}];

	}
	
	//获得标准,标准由科目决定，subjectId
	function getStandard(){
//		var temp2={};
//		temp2.id=1;
//		temp2.name='课标';
//		data.push(temp2);
//		var temp3={};
//		temp3.id=1;
//		temp3.name='中考';
//		data.push(temp3);
//		var temp4={};
//		temp4.id=1;
//		temp4.name='高考';
//		data.push(temp4);
		//alert(JSON.stringify(data));
		$('input[name="currentStandardId"]').val(-1);
		$.getJSON('/admin/questionAdmin/getStandard',
				{subjectId:$('input[name="currentSubjectId"]').val()},
				function (result, status) {
					$('a[name="aStandardName"]').removeClass();
					$('a[name="aStandardName"]').eq(0).addClass('current');
					
					alert(result);
					$("#spanStandardList").empty().html($("#standardTmpl").render(result));
					});
	}
	
	//获得年级,由科目决定，subjectId
	function getGrade(){
//		var temp2={};
//		temp2.id=1;
//		temp2.name='初一';
//		data.push(temp2);
//		var temp3={};
//		temp3.id=1;
//		temp3.name='初二';
//		data.push(temp3);
//		var temp4={};
//		temp4.id=1;
//		temp4.name='初三';
//		data.push(temp4);
		//alert(JSON.stringify(data));
		$('input[name="currentGradeId"]').val(-1);
		$.getJSON('/admin/questionAdmin/getGrade', 
				{subjectId:$('input[name="currentSubjectId"]').val()},
				function (result, status) { 
					$('a[name="aGradeName"]').removeClass();
					$('a[name="aGradeName"]').eq(0).addClass('current');
					$("#spanGradeList").empty().html($("#gradeTmpl").render(result));
					});
	}
	
	//获得题型,由科目决定，subjectId
	 function getType(){
//		 var data=[];
//		 var temp2={};
//		 temp2.id=1;
//		 temp2.name='单选题';
//		 data.push(temp2);
//		 var temp3={};
//		 temp3.id=2;
//		 temp3.name='多选题';
//		 data.push(temp3);
//		 var temp4={};
//		 temp4.id=3;
//		 temp4.name='填空题';
//		 data.push(temp4);
//		 var temp5={};
//		 temp5.id=4;
//		 temp5.name='其他';
//		 data.push(temp5);
		 $('input[name="currentTypeId"]').val(-1);
		$.getJSON('/admin/questionAdmin/getQuestionType', 
				{subjectId:$('input[name="currentSubjectId"]').val()},
				function (result, status) { 
					$('a[name="aTypeName"]').removeClass();
					$('a[name="aTypeName"]').eq(0).addClass('current');
					$("#spanTypeList").empty().html($("#typeTmpl").render(result));
					});
		//return data;
	 }
	 
	 //获得新的page
	 function getPage(){
		 $("#page").page('remote',0,{
			 	subjectId:$('input[name="currentSubjectId"]').val(),//科目id
				gradeId:$('input[name="currentGradeId"]').val(),//年级id
				fieldId:$('input[name="currentFieldId"]').val(),//地区id
				standardId:$('input[name="currentStandardId"]').val(),//标准id
				knowId:$('input[name="currentKnowId"]').val(),//知识点id
				typeId:$('input[name="currentTypeId"]').val(),//题型id
				hardness:$('input[name="currentHardness"]').val(),//难度
				key:$('input[name="keyForSearchQuestion"]').val()//关键字
				 });
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
	
	//分页插件
	//$("#page").page({ total: 1000 });
	$("#page").page({
		showInfo: true,
	    //showJump: true,
	    //showPageSizes: true,
	    infoFormat: '{start} ~ {end}条，共{total}条',
	    //total:1000
	    remote: {
	        url: '/admin/questionAdmin/getQuestionData',  //请求地址
	        params: { //根据这些参数返回
	        	subjectId:$('input[name="currentSubjectId"]').val(),//科目id
				gradeId:$('input[name="currentGradeId"]').val(),//年级id
				fieldId:$('input[name="currentFieldId"]').val(),//地区id
				standardId:$('input[name="currentStandardId"]').val(),//标准id
				knowId:$('input[name="currentKnowId"]').val(),//知识点id
				typeId:$('input[name="currentTypeId"]').val(),//题型id
				hardness:$('input[name="currentHardness"]').val(),//难度
				key:$('input[name="keyForSearchQuestion"]').val()//关键字
	        	},       //自定义请求参数
	        beforeSend: function(XMLHttpRequest){
	            //...
	        },
	        success: function (result, pageIndex) {
	            //回调函数
	            //result 为 请求返回的数据，呈现数据
	        	alert('success');
	        	 $("#divQuestions").empty().html($("#questionBoxTmpl").render(jQuery.parseJSON(result.data)));
	        },
	        complete: function(XMLHttpRequest, textStatu){
	            //...
	        },
	        pageIndexName: 'pageIndex',     //请求参数，当前页数，索引从0开始
	        pageSizeName: 'pageSize',       //请求参数，每页数量
	        totalName: 'total'            //指定返回数据的总数据量的字段名
	    }
	});
	
	//选择科目
	$(document).on("click",'a[name="subjectSubName2"]',function(){
		$('div[name="divForSelectSubject"]').find('.box-body').toggle(200);
		$('div[name="divForSelectSubject"]').addClass('collapsed-box');
		$('div[name="divForSelectSubject"]').find('i').removeClass('fa-minus').addClass('fa-plus');
		var originName=$('b[name="currentSubjectName"]').text();
		//$(this).parent().parent().find('h5[name=subjectSubName1]').text()+
		var subjectName=$(this).text();
		$('b[name="currentSubjectName"]').text(subjectName);
		$('input[name="currentSubjectId"]').val($(this).find('input').val());
		//alert($('input[name="currentSubjectId"]').val());
		
		
		//重新获得知识点
		getTree();
		//重新获得年级
		getGrade();
		//重新获得题型
		getType();
		//重新获得标准
		getStandard();
		
		//获得新的数据
		getPage();
		
	});
	
	//选择年级
	$(document).on("click",'a[name="aGradeName"]',function(){
		$('a[name="aGradeName"]').removeClass();
		$(this).addClass('current');
		$('input[name="currentGradeId"]').val($(this).find('input').val());
		
		//重新获得知识点
		getTree();
		
		
		//获得新的数据
		getPage();
		
	});
	
	//选择题型
	$(document).on("click",'a[name="aTypeName"]',function(){
		$('a[name="aTypeName"]').removeClass();
		$(this).addClass('current');
		$('input[name="currentTypeId"]').val($(this).find('input').val());
		
		//获得新的数据
		getPage();
		
	});
	
	//选择地区
	$(document).on("click",'a[name="aFieldName"]',function(){
		$('a[name="aFieldName"]').removeClass();
		$(this).addClass('current');
		$('input[name="currentFieldId"]').val($(this).find('input').val());
		//重新获得知识点
		getTree();
		
		//获得新的数据
		getPage();
		
	});
	
	//选择标准
	$(document).on("click",'a[name="aStandardName"]',function(){
		$('a[name="aStandardName"]').removeClass();
		$(this).addClass('current');
		$('input[name="currentStandardId"]').val($(this).find('input').val());
		//重新获得知识点
		getTree();
		
		//获得新的数据
		getPage();
	
	});
	
	//选择难度
	$(document).on("click",'a[name="aHardnessName"]',function(){
		$('a[name="aHardnessName"]').removeClass();
		$(this).addClass('current');
		$('input[name="currentHardness"]').val($(this).text());
		
		//获得新的数据
		getPage();
		
	});
	
	//选择知识点
	
	
	
	
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
		$.ajax({
			url:"/admin/questionAdmin/deleteOne",
			data:{id:id},
			success:function(result){
				if(result==1||result=='1'){
					alert('删除成功');
					getPage();
				}else{
					alert('删除发生错误');
				}
			}
		});
		alert('del question id:'+id);
	});
	
	//单题报错
	$(document).on("click",'button[name="btnReportOneError"]',function(){
		//alert('btnPassForOneQuestion绑定成功');
		//$(this).removeClass();
//		$(this).parent().find('button').addClass('disabled');
//		var parents = $(this).parentsUntil('div[name="divQuestionBox"]');
//		var divparent = $(parents[parents.length-1]).parent();
//		//alert(divparent);
//		var id=$(divparent).find('input[name="questionId"]').val();
//		$('input[name="currentQuestionError"]').val(id);
		$('#modalReportError').modal('show');
//		$.ajax({
//			url:"/admin/questionAdmin/reportError",
//			data:{id:id},
//			success:function(result){
//				if(result=="1"||result==1){
//					alert('报错成功');
//				}else{
//					alert('报错发生错误');
//				}
//			}
//		});
//		alert('report one error question id:'+id);
	});
	
	//举报错误
	$('#btnSubmitError').click(function(){
		var type=$('#selectErrorType').val();
		var content=$('#textErrorContent').val();
		var id=$('input[name="currentQuestionId"]').val();
		console.log('error type:'+type+',content:'+content+
				',question id:'+id);
//		$.ajax({
//			url:"/admin/questionAdmin/reportError",
//			data:{id:id,
//				type:type,
//				content:content},
//			success:function(result){
//				if(result=="1"||result==1){
//					alert('报错成功');
//				}else{
//					alert('报错发生错误');
//				}
//			}
//		});
		//清空输入框
		$('#textErrorContent').val('');
		$('#modalReportError').modal('hide');
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
		
		$.ajax({
			url:"/admin/questionAdmin/deleteQuestions",
			data:{idArr:JSON.stringify(idArr)},
			success:function(result){
				if(result==1||result=='1'){
					alert('删除成功');
					getPage();
				}else{
					alert('删除发生错误');
				}
			}
		});
		
		alert(JSON.stringify(idArr));
	});
	
	//搜索
	$('button[name="btnSearchQuestion"]').click(function(){
		var key=$('input[name="keyForSearchQuestion"]').val();
		getPage();
		//alert(key);
	});
});