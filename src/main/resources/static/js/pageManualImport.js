$(function(){
	//获得ueditor
	var ue = UE.getEditor('ueditor');
	
	//获得根目录
	function getContextPath() {
	    var pathName = document.location.pathname;
	    var index = pathName.substr(1).indexOf("/");
	    var result = pathName.substr(0,index+1);
	    return result;
	}
	
	/**
	 * http://localhost:8083/proj
	 */
	function getRootPath(){
	    //获取当前网址，如： http://localhost:8083/proj/meun.jsp
	    var curWwwPath = window.document.location.href;
	    //获取主机地址之后的目录，如： proj/meun.jsp
	    var pathName = window.document.location.pathname;
	    var pos = curWwwPath.indexOf(pathName);
	    //获取主机地址，如： http://localhost:8083
	    var localhostPath = curWwwPath.substring(0, pos);
	    //获取带"/"的项目名，如：/proj
	    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);
	    return(localhostPath + projectName);
	}
	
	//初始化根目录
	var contextPath=getRootPath();
	
	//提交多道试题
	$('#btnSubmitQuestions').click(function(){
		console.log('手动导入试题页面：提交试题');
		console.log('试题内容:'+ue.getContent());
		$.ajax({
			type:'POST',
			url:'/admin/manualImport/submitQuestions',
			data:{
				questionsStr:ue.getContent()
			},
			success:function(result){
				if(result==1||result=='1'){
					alert('提交成功');
				}else{
					alert('提交失败');
				}
			}
		});
		
	}); 
	
	//清空ueditor
	$('#btnClearEditor').click(function(){
		console.log("手动导入试题页面：清空试题");
		ue.setContent('');
	});
});