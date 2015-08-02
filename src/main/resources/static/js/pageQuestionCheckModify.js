$(function(){
    	 var ue = UE.getEditor('ueditor');
    	 
    	 //显示窗口
         $('#btnSelectKnow').click(function(){
         	$('#modalSelectKnow').modal('show');
         });
         var data1 = [
      	            {
      	                label: 'node1',
      	                id:1,
      	                children: [
      	                    { label: 'child1' ,id:2},
      	                    { label: 'child2',id:3 }
      	                ]
      	            },
      	            {
      	                label: 'node2',
      	                id:4,
      	                children: [
      	                    { label: 'child3',id:5 }
      	                ]
      	            }
      	        ];
      	$('#tree1').tree({
      	        data: data1
      	    });
     	 //节点的点击事件,多选事件
      	$('#tree1').bind(
      	        'tree.click',
      	        function(e) {
      	            // Disable single selection
      	            e.preventDefault();

      	            var selected_node = e.node;

      	            if (selected_node.id == undefined) {
      	                console.log('The multiple selection functions require that nodes have an id');
      	            }

      	            if ($('#tree1').tree('isNodeSelected', selected_node)) {
      	            	$('#tree1').tree('removeFromSelection', selected_node);
      	            }
      	            else {
      	            	$('#tree1').tree('addToSelection', selected_node);
      	            }
      	        }
      	    );
      	
      	//选择知识点的确认
      	$('#btnConfirmKnow').click(function(){
      		var nodes = $('#tree1').tree('getSelectedNodes');
      		var know='知识点：';
      		for(var i=0;i<nodes.length;i++){
      			console.log('name:'+nodes[i].name);
      			know=know+nodes[i].name+'；';
      		}
      		$('label[name="currentKnowName"]').text(know);
      		$('#modalSelectKnow').modal('hide');
      		console.log(nodes);
      	});
      	
      	//修改后保存
      	$('#btnSave').click(function(){
      		var subjectId=$('#selectForSubject').val();
      		var typeId=$('#selectForType').val();
      		var knowArr=[];
      		var content=ue.getContent();
      		var nodes = $('#tree1').tree('getSelectedNodes');
      		for(var i=0;i<nodes.length;i++){
      			knowArr.push(nodes[i].id);
      		}
      		var questionInfo={};
      		questionInfo.id=$('#questionId').val();
      		questionInfo.subjectId=subjectId;
      		questionInfo.typeId=typeId;
      		questionInfo.knowArr=knowArr;
      		questionInfo.content=content;
      		console.log('question info:'+JSON.stringify(questionInfo));
      		

//    		$.ajax({
//    			url:"/admin/questionCheck/questionModify/save",
//    			data:{questionInfoStr:JSON.stringify(questionInfo)},
//    			success:function(result){
//    				if(result==1||result=='1'){
//    					alert('保存成功');
//    					getPage();
//    				}else{
//    					alert('保存失败');
//    				}
//    			}
//    		});
    		
      	});
      	
      	function getTree() {
    	    // Some logic to retrieve, or generate tree structure
//    		var data = '[' +
//    	    '{' +
//    	      '"text": "Parent 1",' +
//    	      '"nodes": [' +
//    	        '{' +
//    	          '"text": "Child 1",' +
//    	          '"nodes": [' +
//    	            '{' +
//    	              '"text": "Grandchild 1"' +
//    	            '},' +
//    	            '{' +
//    	              '"text": "Grandchild 2"' +
//    	            '}' +
//    	          ']' +
//    	        '},' +
//    	        '{' +
//    	          '"text": "Child 2"' +
//    	        '}' +
//    	      ']' +
//    	    '},' +
//    	    '{' +
//    	      '"text": "Parent 2",' +
//    	      '"tags":["available"]'+
//    	    '},' +
//    	    '{' +
//    	      '"text": "Parent 3"' +
//    	    '},' +
//    	    '{' +
//    	      '"text": "Parent 4"' +
//    	    '},' +
//    	    '{' +
//    	      '"text": "Parent 5"' +
//    	    '}' +
//    	  ']';
    		
    		$.getJSON('/admin/questionAdmin/getKnow', {
    			subjectId:$('#selectForSubject').val(),
    			gradeId:-1,
    			fieldId:-1,
    			standardId:-1
    			},function (result, status) {
    				$('#tree1').tree('loadData', result);
    				$('input[name="currentKnowId"]').val(-1);
    				});
    		//var data2 = [{"children":[{"children":[{"children":[{"label":"函数的最大值和最小值"},{"label":"增函数、减函数的概念"},{"label":"函数的单调性、单调区间"}],"label":"单调性与最大（小）值"},{"children":[{"label":"奇函数、偶函数的概念"},{"label":"奇函数、偶函数的性质"}],"label":"奇偶性"}],"label":"函数的基本性质"},{"label":"集合的基本运算"},{"children":[{"children":[{"label":"函数的概念"},{"label":"函数符号y=f(x)"},{"label":"函数的定义域"},{"label":"函数的值域"},{"label":"函数的定义域"}],"label":"函数的概念"},{"children":[{"label":"函数的解析法表示"},{"label":"函数的图象法表示，描点法作图"},{"label":"函数的列表法表示"},{"label":"分段函数的意义与应用"},{"label":"映射的概念"}],"label":"函数的表示法"},{"label":"映射的概念"},{"label":"函数的基本性质"},{"label":"函数的基本性质"},{"label":"函数的基本性质"},{"children":[{"label":"单调性与最大（小）值"},{"label":"奇偶性"},{"children":[{"label":"函数的最大值和最小值"},{"label":"增函数、减函数的概念"},{"label":"函数的单调性、单调区间"}],"label":"单调性与最大（小）值"},{"children":[{"label":"奇函数、偶函数的概念"},{"label":"奇函数、偶函数的性质"}],"label":"奇偶性"}],"label":"函数的基本性质"}],"label":"函数的概念及表示"},{"children":[{"children":[{"label":"集合的含义"},{"label":"集合元素的特性"},{"label":"集合的相等"},{"label":"集合与元素关系"},{"label":"常用数集的记法"},{"label":"集合的表示法"}],"label":"集合的定义和表示"},{"children":[{"label":"子集、真子集的概念"},{"label":"空集的概念"}],"label":"集合间的基本关系"},{"label":"集合间的关系"},{"label":"集合的基本运算"},{"children":[{"label":"并集的含义"},{"label":"交集的含义"},{"label":"全集与补集"}],"label":"集合的基本运算"}],"label":"集合"}],"label":"集合与函数"},{"children":[{"children":[{"label":"任意角"},{"label":"弧度制"}],"label":"任意角的三角函数"},{"children":[{"label":"任意角的三角函数"},{"label":"同角三角函数的基本关系"}],"label":"任意角的三角函数"},{"children":[{"children":[{"label":"π+α与α的正弦、余弦、正切值的关系"},{"label":"-α与α的正弦、余弦、正切值的关系"},{"label":"π-α与的正弦、余弦、正切值的关系"},{"label":"与α的正弦、余弦值的关系"}],"label":"三角函数的诱导公式"}],"label":"三角函数的诱导公式"},{"children":[{"label":"正弦函数、余弦函数的图象"},{"label":"正弦函数、余弦函数的性质"},{"label":"正切函数的性质和图象"},{"label":"y=Asin(ωx+ψ)的图象"},{"label":"三角函数模型的简单应用"}],"label":"三角函数的图象和性质"}],"label":"三角函数"},{"children":[{"children":[{"label":"数列的定义"},{"label":"数列几种简单表示"}],"label":"数列的概念与简单表示法"},{"label":"等差数列"},{"children":[{"label":"向量的物理背景与概念"},{"label":"向量的几何表示"}],"label":"平面向量的实际背景及基本概念"},{"label":"平面向量的线性运算"},{"label":"平面向量的基本定理及坐标表示"},{"label":"平面向量的数量积"},{"label":"平面向量应用举例"}],"label":"平面向量"},{"children":[{"children":[{"children":[{"label":"根式的意义"},{"label":"分数指数幂的意义"},{"label":"无理数指数幂的意义"},{"label":"有理数指数幂的运算性质"}],"label":"指数与指数幂的运算"},{"children":[{"label":"指数函数的概念"},{"label":"指数函数的图象"},{"label":"指数函数的性质"}],"label":"指数函数及其性质"}],"label":"指数函数"},{"children":[{"children":[{"label":"对数的概念"},{"label":"常用对数与自然对数"},{"label":"对数的运算性质"},{"label":"对数的换底公式"}],"label":"对数与对数运算"},{"children":[{"label":"对数函数的概念"},{"label":"对数函数的图象"},{"label":"对数函数的性质"},{"label":"指数函数与对数函数的关系"}],"label":"对数函数及其性质"}],"label":"对数函数"},{"children":[{"children":[{"label":"幂函数的概念"},{"label":"幂函数的图象"},{"label":"幂函数的性质"}],"label":"幂函数"}],"label":"幂函数"}],"label":"基本初等函数"},{"children":[{"children":[{"label":"方程的根与函数的零点"},{"label":"用二分法求方程的近似解"}],"label":"函数与方程"},{"children":[{"label":"精确度与近似解"},{"label":"二分法求f(x)=0零点的基本方法"},{"label":"二分法求f(x)=0零点的基本步骤"}],"label":"函数模型及其应用"}],"label":"函数的应用"},{"children":[{"label":"两角和与差的正弦、余弦和正切公式"},{"label":"简单的三角恒等变换"}],"label":"三角恒等变换"},{"children":[{"label":"数列的概念与简单表示"},{"label":"等差数列"},{"label":"等差数列的前n项的和"},{"label":"等比数列"},{"label":"等比数列的前n项的和"},{"label":"数列的综合应用"}],"label":"数列"},{"children":[{"label":"不等关系与不等式"},{"label":"一元二次不等式及其解法"},{"label":"二元一次不等式（组）与简单线性规划问题"},{"label":"基本不等式"}],"label":"不等式"},{"children":[{"label":"空间几何体的结构"},{"label":"空间几何体的三视图和直观图"},{"label":"空间几何体的表面积与体积"}],"label":"空间几何体"},{"children":[{"label":"空间点、直线、平面之间的位置关系"},{"label":"直线、平面平行的判定及其性质"},{"label":"直线、平面垂直的判定及其性质"}],"label":"第二章点、直线、平面之间的位置关系"},{"children":[{"label":"直线的倾斜角与斜率"},{"label":"直线的方程"},{"label":"直线的交点坐标与距离公式"}],"label":"直线与方程"},{"children":[{"label":"圆的方程"},{"label":"直线、圆的位置关系"},{"label":"空间直角坐标系"}],"label":"圆的方程"},{"children":[{"label":"命题及其关系"},{"label":"充分条件与必要条件"},{"label":"简单的逻辑联结词"},{"label":"全称量词与存在量词"}],"label":"常用逻辑用语"},{"children":[{"label":"曲线与方程"},{"label":"椭圆"},{"label":"双曲线"},{"label":"抛物线"}],"label":"圆锥曲线与方程"},{"children":[{"label":"正弦定理和余弦定理"},{"label":"应用举例"}],"label":"解三角形"},{"children":[{"label":"空间向量及其运算"},{"label":"立体几何中的向量方法"}],"label":"空间向量与立体几何"}];

    	}
      	
      //获得题型,由科目决定，subjectId
   	 function getType(){
//   		 var data=[];
//   		 var temp2={};
//   		 temp2.id=1;
//   		 temp2.name='单选题';
//   		 data.push(temp2);
//   		 var temp3={};
//   		 temp3.id=2;
//   		 temp3.name='多选题';
//   		 data.push(temp3);
//   		 var temp4={};
//   		 temp4.id=3;
//   		 temp4.name='填空题';
//   		 data.push(temp4);
//   		 var temp5={};
//   		 temp5.id=4;
//   		 temp5.name='其他';
//   		 data.push(temp5);
//   		$("#selectForType").empty().html($("#optionTmpl").render(data));
   		$.getJSON('/admin/questionAdmin/getType', 
   				{subjectId:$('#selectForSubject').val()},
   				function (result, status) { 
   					//清空select
	   					
	   					$("#selectForType").empty().html($("#optionTmpl").render(result));
   					
   					});
   		//return data;
   	 }
      	//科目发生改变
      	$('#selectForSubject').change(function(){
      		getTree();
      		getType();
      	});
});