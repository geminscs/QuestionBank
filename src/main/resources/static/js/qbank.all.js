$(document).ready(function(){
	//手动导入页面的的试题提交
	//console.log("手动导入试题页面：提交试题");
	$("#btnSubmit_pageManualImport").click(function(){
		console.log("手动导入试题页面：提交试题");
		var editor= UE.getEditor('ueditor');
		console.log("编辑器内容："+editor.getContent());
	});
	
	//手动导入页面的的试题清空
	$("#btnClear_pageManualImport").click(function(){
		console.log("手动导入试题页面：清空试题");
		var editor=UE.getEditor('ueditor');
		editor.setContent('');
	});
});