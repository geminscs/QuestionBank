$(function(){
	$('#datatableOfWords').DataTable({
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
         "ajax": {
        	 "url": "scripts/post.php",
             "type": "POST",
             "data": function ( d ) {
                 d.opModule = $('#selectForOpModule').val();
                 d.userType=$('#selectForUserType').val();
                 d.dateRange=$('#inputRangeDate').val();
                 d.key=$('#keyForSearchOpLog').val();
                 // d.custom = $('#myInput').val();
                 // etc
             }
         }
    });
});