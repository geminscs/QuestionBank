//细节的格式
function format ( d ) {
// `d` is the original data object for the row
	return '<div>'+
		   '<span><label>读音：</label>'+d.phonetic+'</span>&nbsp;&nbsp;'+
		   '<span><label>解释：</label>'+d.explain+'</span>'+
		   '</div>';
}
	

$(function(){
//	var table=$('#datatableOfWords').DataTable({
//    	"language": {
//             "lengthMenu": "每页 _MENU_ 条记录",
//             "zeroRecords": "没有找到记录",
//             "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
//             "infoEmpty": "无记录",
//             "infoFiltered": "(从 _MAX_ 条记录过滤)",
//             "paginate": {
//                 "first": "首页",
//                 "previous": "上页",
//                 "next": "下页",
//                 "last": "末页"
//             },
//             "search": "搜索 "
//         },
//         "ordering": false,
//         "lengthChange": false,
//         "searching": false,
//         "processing": true,
//         "serverSide": true,
//         "ajax": {
//        	 "url": "scripts/post.php",
//             "type": "POST",
//             "data": function ( d ) {
//                 d.opModule = $('#selectForOpModule').val();
//                 d.userType=$('#selectForUserType').val();
//                 d.dateRange=$('#inputRangeDate').val();
//                 d.key=$('#keyForSearchOpLog').val();
//                 // d.custom = $('#myInput').val();
//                 // etc
//             }
//         }
//    });
//	
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
         "processing": true,
         "serverSide": true,
         "ajax":{
        	 "url": "/admin/lexicon/wordsCheck/getWordsData",
             "type": "GET",
             "dataType": "json",
             "data": function ( d ) {
                d.wordType=1;
             }
         },
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
                        	 '<button class="btn btn-primary btn-xs" name="btnPassOne">通过</button> '+
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
	} );
	
	//通过一个单词
	$('#datatableOfWords tbody').on('click', 'button[name="btnPassOne"]', function () {
		//alert('btnPassOne');
		var tr=$(this).closest('tr');
		var row = table.row( tr );
		var data=row.data();
		var id=data.id;
		console.log('pass word id:'+id);
		
		//table.ajax.reload();
		$.ajax({
			url:'/admin/lexicon/wordsCheck/passOne',
			data:{
				id:id
			},
			success:function(result){
				if(result==1||result=='1'){
					//alert('通过成功');
					alert('通过成功');
					table.ajax.reload();
				}else{
					alert('产生错误');
				}
			}
		});
	});
	
	//通过多个选中的单词
	$('#btnPassSome').click(function(){
		var idArr=[];
		$('input[name="checkBoxForSelect"]:checked').each(function(){
			var tr=$(this).closest('tr');
			var row = table.row( tr );
			var data=row.data();
			var id=data.id;
			idArr.push(id);
		});
		console.log('pass some words:'+JSON.stringify(idArr));
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
	
	//通过一个单词
	$('#datatableOfWords tbody').on('click', 'button[name="btnDelOne"]', function () {
		var tr=$(this).closest('tr');
		var row = table.row( tr );
		var data=row.data();
		var id=data.id;
		console.log('del word id:'+id);
//		table.ajax.reload();
//		$.ajax({
//			url:'/admin/lexicon/wordsCheck/delOne';
//			data:{
//				id:id
//			},
//			success:function(result){
//				if(result==1||result=='1'){
//					alert('通过成功');
//					table.ajax.reload();
//				}else{
//					alert('产生错误');
//				}
//			}
//		});
	});
	
	//通过多个选中的单词
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
	
	//修改单词
	$('#datatableOfWords tbody').on('click', 'button[name="btnModify"]', function () {
		var tr=$(this).closest('tr');
		var row = table.row( tr );
		var data=row.data();
		var id=data.id;
		console.log('del word id:'+id);
		location.href='/admin/lexicon/wordsCheck/modify?id='+id;
//		table.ajax.reload();
//		$.ajax({
//			url:'/admin/lexicon/wordsCheck/delOne';
//			data:{
//				id:id
//			},
//			success:function(result){
//				if(result==1||result=='1'){
//					alert('通过成功');
//					table.ajax.reload();
//				}else{
//					alert('产生错误');
//				}
//			}
//		});
	});
	
});