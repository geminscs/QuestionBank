$(function(){
	
	//datatableOfLog初始化
    $('#datatableOfLog').DataTable({
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
    
    //Date range picker with time picker
    $('#inputRangeDate').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});
    
    //操作模块改变
    $('#selectForOpModule').change(function(){
    	console.log('OpModuleChange:'+$(this).val());
    });
    
    //用户类型改变
    $('#selectForUserType').change(function(){
    	console.log('UserTypeChange:'+$(this).val());
    });
    
    //时间区间改变
    $("#inputRangeDate").change(function(){
    	console.log('date time range change:'+$(this).val());

    });
});