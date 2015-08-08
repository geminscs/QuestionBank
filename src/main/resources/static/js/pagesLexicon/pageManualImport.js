$(function(){
	//提交导入的单词
	$('#btnSubmit').click(function(){
		console.log('单词手动导入：提交')
		var str=$('#textareaForWords').val();
		var newString = str.replace(/\n/g, '<br/>');    
		$('#textareaForWords').val(newString);
		console.log('提交内容：'+str);
		var arr=new Array();
		arr=newString.split('<br/>');//注split可以用字符或字符串分割  
		for(var i=0;i<(arr.length);i++){
			console.log(i+':'+arr[i]);
		}
		
		var words={};
		words.type=$('#selectForType').val();
		words.grade=$('#selectForGrade').val();
		words.data=arr;
		console.log('words:'+JSON.stringify(words));
		$.ajax({
			type:'POST',
			url:'/admin/lexicon/manualImport/submit',
			data:{
				words:words
			}
		});
	});
	
	$('#btnClear').click(function(){
		console.log('单词手动导入：清空');
		$('#textareaForWords').val('');
	});
});