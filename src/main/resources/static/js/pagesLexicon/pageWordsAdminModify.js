$(function(){
	//保存单词信息
	$('#btnSave').click(function(){
		var wordInfo={};
		wordInfo.id=$('#wordId').val();
		wordInfo.word=$('#inputWord').val();
		wordInfo.type=$('#selectForType').val();
		wordInfo.grade=$('#selectForGrade').val();
		wordInfo.phonetic=$('#inputPhonetic').val();
		wordInfo.explain=$('#inputExplain').val();
		console.log('word save:'+JSON.stringify(wordInfo));
		$.ajax({
			url:'/admin/lexicon/wordsAdmin/modify/save',
			data:{
				wordInfo:JSON.stringify(wordInfo)
			},
			type:'POST',
			success:function(result){
				if(result==1||result=='1'){
					alert('保存成功');
				}else{
					alert('发生错误');
				}
			}
		});
	});
	
	//返回单词审阅的界面
	$('#btnBack').click(function(){
		console.log('back to wordsCheck');
		location.href="/admin/lexicon/wordsCheck";
	});
});