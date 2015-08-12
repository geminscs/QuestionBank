//细节的格式
function format ( d ) {
// `d` is the original data object for the row
	return '<div>'+
		   '<span><label>读音：</label>'+d.phonetic+'</span>&nbsp;&nbsp;'+
		   '<span><label>解释：</label>'+d.explain+'</span>'+
		   '</div>';
}

$(function(){
	//初始化dataTable
	var table=$('#datatableOfWords').DataTable({
    	"language": {
             "lengthMenu": "每页 _MENU_ 条记录",
             "zeroRecords": "没有找到记录",
             "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
             "infoEmpty": "无记录",
             "infoFiltered": "(从 _MAX_ 条记录过滤)",
             "paginate": {
                 "first": "首页",
                 "previous": "上页",
                 "next": "下页",
                 "last": "末页"
             },
             "search": "搜索 "
         },
         "ordering": false,
         "lengthChange": false,
         "searching": false,
//         "processing": true,
//         "serverSide": true,
//         "ajax":{
//        	 "url": "/admin/lexicon/wordsAdmin/getWordsData",
//             "type": "GET",
//             "dataType": "json",
//             "data": function ( d ) {
//                d.wordType=$('#selectForType').val();
//                d.grade=$('#selectForGrade').val();
//             }
//         },
         "ajax":"/datatableJson.txt",
         "columns": [
                     {
			        	 "className":      'details-control',
			             "orderable":      false,
			             "data":           null,
			             "defaultContent": ''
         			 },
         			 {
                         "data":null,
                         "defaultContent": '<input type="checkbox" name="checkBoxForSelect">'
                     },
                     { "data": "word" },
                     { "data": "type"},
                     { "data": "grade" },
                     {
                    	 "data":null,
                         "defaultContent": 
                        	 '<button class="btn btn-primary btn-xs" name="btnAddOneToTest">添加至组卷</button> '+
                        	 '<button class="btn btn-default btn-xs" name="btnDelOne">删除</button> '+
                        	 '<button class="btn btn-default btn-xs" name="btnModify">修改</button> '
                     }
                 ]
         
    });
	
	//展开详细信息
	$('#datatableOfWords tbody').on('click', 'td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = table.row( tr );
	 
	        if ( row.child.isShown() ) {
	            // This row is already open - close it
	            row.child.hide();
	            tr.removeClass('shown');
	        }
	        else {
	            // Open this row
	            row.child( format(row.data()) ).show();
	            tr.addClass('shown');
	        }
	});
	
	//添加一个单词至组卷
	$('#datatableOfWords tbody').on('click', 'button[name="btnAddOneToTest"]', function (){
		var tr=$(this).closest('tr');
		var row = table.row( tr );
		var data=row.data();
		var id=data.id;
		console.log('add one word id:'+id);
//		$.ajax({
//		url:'/admin/lexicon/wordsAdmin/AddOneToTest',
//		data:{
//			id:id
//		},
//		success:function(result){
//			if(result==1||result=='1'){
//				//alert('通过成功');
//				alert('删除成功');
//				table.ajax.reload();
//			}else{
//				alert('产生错误');
//			}
//		}
//	});
	});
	
	//添加多个选中的单词至组卷
	$('#btnAddSomeToTest').click(function(){
		var idArr=[];
		$('input[name="checkBoxForSelect"]:checked').each(function(){
			var tr=$(this).closest('tr');
			var row = table.row( tr );
			var data=row.data();
			var id=data.id;
			idArr.push(id);
		});
		console.log('add some words:'+JSON.stringify(idArr));
//		$.ajax({
//			url:'/admin/lexicon/wordCheck/passSome',
//			data:{
//				idArr:idArr
//			},
//			success:function(result){
//				if(result==1||result=='1'){
//					alert('成功');
//					table.ajax.reload();
//				}else{
//					alert('产生错误');
//				}
//			}
//		});
	});
	
	//删除一个单词
	$('#datatableOfWords tbody').on('click', 'button[name="btnDelOne"]', function () {
		//alert('btnPassOne');
		var tr=$(this).closest('tr');
		var row = table.row( tr );
		var data=row.data();
		var id=data.id;
		console.log('del word id:'+id);
		
		//table.ajax.reload();
//		$.ajax({
//			url:'/admin/lexicon/wordsAdmin/DelOne',
//			data:{
//				id:id
//			},
//			success:function(result){
//				if(result==1||result=='1'){
//					//alert('通过成功');
//					alert('删除成功');
//					table.ajax.reload();
//				}else{
//					alert('产生错误');
//				}
//			}
//		});
	});
	
	//删除多个选中的单词
	$('#btnDelSome').click(function(){
		var idArr=[];
		$('input[name="checkBoxForSelect"]:checked').each(function(){
			var tr=$(this).closest('tr');
			var row = table.row( tr );
			var data=row.data();
			var id=data.id;
			idArr.push(id);
		});
		console.log('del some words:'+JSON.stringify(idArr));
//		$.ajax({
//			url:'/admin/lexicon/wordAdmin/delSome',
//			data:{
//				idArr:idArr
//			},
//			success:function(result){
//				if(result==1||result=='1'){
//					alert('成功');
//					table.ajax.reload();
//				}else{
//					alert('产生错误');
//				}
//			}
//		});
	});
	
	//修改单词
	$('#datatableOfWords tbody').on('click', 'button[name="btnModify"]', function () {
		var tr=$(this).closest('tr');
		var row = table.row( tr );
		var data=row.data();
		var id=data.id;
		console.log('modify word id:'+id);
		location.href='/admin/lexicon/wordsCheck/modify?id='+id;

	});
	
	//选择的类型改变
	$('#selectForType').change(function(){
		console.log('type change:'+$('#selectForType').val());
	});
	
	//选择的年级改变
	$('#selectForGrade').change(function(){
		console.log('grade change:'+$('#selectForGrade').val());
	});
	
	//点击搜索
	$
});