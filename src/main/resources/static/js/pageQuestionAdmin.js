$(function(){
	//服务器端返回的json
	alert("pageQuestionAdmin");
	var json = '[' +
    '{' +
      '"text": "Parent 1",' +
      '"nodes": [' +
        '{' +
          '"text": "Child 1",' +
          '"nodes": [' +
            '{' +
              '"text": "Grandchild 1"' +
            '},' +
            '{' +
              '"text": "Grandchild 2"' +
            '}' +
          ']' +
        '},' +
        '{' +
          '"text": "Child 2"' +
        '}' +
      ']' +
    '},' +
    '{' +
      '"text": "Parent 2",' +
      '"tags":["available"]'+
    '},' +
    '{' +
      '"text": "Parent 3"' +
    '},' +
    '{' +
      '"text": "Parent 4"' +
    '},' +
    '{' +
      '"text": "Parent 5"' +
    '}' +
  ']';
	//筛选知识点的树形结构
	$('#treeviewOfKPoint_pQuestionAdmin').treeview({
        color: "#428bca",
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        nodeIcon: 'glyphicon glyphicon-bookmark',
        data: json,
        showBorder:false,
        showTags:true
    });
});