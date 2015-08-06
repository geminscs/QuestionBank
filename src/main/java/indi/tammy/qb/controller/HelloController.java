package indi.tammy.qb.controller;


import java.util.ArrayList;
import java.util.List;



import java.util.regex.Pattern;

import indi.tammy.qb.annotation.SystemControllerLog;
import indi.tammy.qb.model.Know;
import indi.tammy.qb.model.Question;
import indi.tammy.qb.model.User;
import indi.tammy.qb.model.enums.QuestionType;
import indi.tammy.qb.model.enums.Subject;
import indi.tammy.qb.service.EnumService;
import indi.tammy.qb.service.KnowService;
import indi.tammy.qb.service.QuestionService;
import indi.tammy.qb.service.RegisterService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private KnowService knowService;
	
	@Autowired
	private EnumService enumService;

	@SystemControllerLog(description = "删除用户")
	@RequestMapping(value={"/admin/home"},method = RequestMethod.GET)
	public String adminHomeGet(){
		
		return "adminHome";
	}
	
	@RequestMapping(value={"/admin/manualImport"},method = RequestMethod.GET)
	public String adminManualImport(){
		
		return "pagesQuestionBank/pagesQuestionImport/pageManualImport";
	}
	
	@RequestMapping(value={"/admin/wordImport"},method = RequestMethod.GET)
	public String adminWordImport(){
		
		return "pagesQuestionBank/pagesQuestionImport/pageWordImport";
	}
	
	
	@RequestMapping(value={"/admin/importNotice"},method = RequestMethod.GET)
	public String adminImportNotice(){
		
		return "pagesQuestionBank/pagesQuestionImport/pageImportNotice";
	}
	
	@RequestMapping(value={"/admin/clip"},method = RequestMethod.GET)
	public String testClip(){
		
		return "test/test1";
	}
	
	@RequestMapping(value={"/admin/questionCheck"},method = RequestMethod.GET)
	public String adminQuestionCheck(ModelMap modelMap){
		List<Subject> l = enumService.findAllSubject();
		List<Subject> primary = new ArrayList<Subject>();
		List<Subject> middle = new ArrayList<Subject>();
		List<Subject> high = new ArrayList<Subject>();
		for(Subject s:l){
			if(s.getFlag() == 1){
				s.setName(s.getName().replaceFirst("小学", ""));
				primary.add(s);
			}
			else if(s.getFlag() == 2){
				s.setName(s.getName().replaceFirst("初中", ""));
				middle.add(s);
			}
			else if(s.getFlag() == 3){
				s.setName(s.getName().replaceFirst("高中", ""));
				high.add(s);
			}
		}
		modelMap.addAttribute("primary", primary);
		modelMap.addAttribute("middle", middle);
		modelMap.addAttribute("high", high);
		return "pagesQuestionBank/pagesQuestionCheck/pageQuestionCheck";
	}
	
	@RequestMapping(value={"/admin/questionCheck/questionModify"},method = RequestMethod.GET)
	@SystemControllerLog(description = "修改试题")
	public String adminModifyForCheck(int id, ModelMap modelMap){//根据试题id显示试卷信息
		Question q = questionService.findById(id);
		List<QuestionType> l = enumService.findQuestionTypeBySubjectId(q.getSubject_id());
		q.setContent(StringEscapeUtils.unescapeHtml4(q.getContent()));
		List<Subject> l2 = enumService.findAllSubject();
		modelMap.addAttribute("question", q);
		modelMap.addAttribute("questionTypeList", l);
		modelMap.addAttribute("subjectList", l2);
		return "pagesQuestionBank/pagesQuestionCheck/pageQuestionModify";
	}

	@RequestMapping(value={"/admin/questionCheck/getQuestionData"},method = RequestMethod.GET)
	@ResponseBody
	public String getQuestionData(int subjectId, int pageIndex, int pageSize){
		List<Question> l = questionService.findBySubject(subjectId, pageIndex * pageSize + 1, (pageIndex + 1) * pageSize);
		JSONObject json=new JSONObject();  
		JSONArray jsonMembers = new JSONArray();
		for(int i = 0;i < l.size();i ++){
			JSONObject member = new JSONObject();
			Question q = l.get(i);
			member.put("id", q.getId());  
			member.put("type", q.getType_name());  
			member.put("subject", "无此功能");  
			member.put("grade","无此功能");  
			member.put("content", q.getContent());  
			member.put("answer", q.getAnswer());  
			member.put("analysis", q.getAnalysis());  
			member.put("know", q.getKnow_name());
			jsonMembers.put(member); 
		}
		if(l.size() > 0){
			json.put("total", l.size());
		}
		else{
			json.put("total", 0);
		}
		json.put("data", jsonMembers.toString());    
		return json.toString();
	}
	
	@RequestMapping(value={"/admin/questionCheck/delete"}, method=RequestMethod.GET)
	@ResponseBody
	public void questionCheckDelete(int id){
		questionService.delete(id);
		return;
	}
	
	@RequestMapping(value={"/admin/questionCheck/pass"}, method=RequestMethod.GET)
	@ResponseBody
	public void questionCheckPass(int id){
		Question q = new Question();
		q.setId(id);
		questionService.formalInsert(q);
		questionService.formalInsertKnowQuestion(q.getId(), id);
		questionService.delete(id);
		return;
	}
	
	@RequestMapping(value={"/admin/questionCheck/passSome"}, method=RequestMethod.GET)
	@ResponseBody
	public int questionCheckPassSomw(String idArr){
		JSONArray ids = new JSONArray(idArr);
		for(int i = 0;i < ids.length();i ++){
			Question q = new Question();
			int id = ids.getInt(i);
			q.setId(id);
			questionService.formalInsert(q);
			questionService.formalInsertKnowQuestion(q.getId(), id);
			questionService.delete(id);
		}
		return 1;
	}
	
	@RequestMapping(value={"/admin/questionCheck/questionModify/save"}, method=RequestMethod.POST)
	@ResponseBody
	public int question(String questionInfoStr){
    	String contentName = "【题面】";
    	String answerName = "【答案】";
    	String analysisName = "【解析】";
		JSONObject jsonObj = new JSONObject(questionInfoStr);
		Question q = new Question();
		q.setId(jsonObj.getInt("id"));
		q.setType(jsonObj.getInt("typeId"));
		q.setSubject_id(jsonObj.getInt("subjectId"));
		String content = jsonObj.getString("content");
		content = clearFormat(content);
		Document doc = Jsoup.parse(content);
		Elements divs = doc.body().children();
		String nowType = null;
		for(int i = 0;i < divs.size();i ++){
			Element div = divs.get(i);
			if(div.html().startsWith(contentName)){
				nowType = contentName;
				q.setContent(div.html().substring(contentName.length()));
			}
			else if(div.html().startsWith(answerName)){
				nowType = answerName;
				q.setAnswer(div.html().substring(answerName.length()));
			}
			else if(div.html().startsWith(analysisName)){
				nowType = analysisName;
				q.setAnalysis(div.html().substring(analysisName.length()));
			}
			else{
				if(nowType == null){
					return 0;
				}
				if(nowType.equals(contentName)){
					q.setContent(q.getContent() + "<br/>" +div.html());
				}
				else if(nowType.equals(answerName)){
					q.setAnswer(q.getAnswer() + "<br/>" +div.html());
				}
				else if(nowType.equals(analysisName)){
					q.setAnalysis(q.getAnalysis() + "<br/>" +div.html());
				}
			}
		}
		if(q.getContent() == null || q.getAnalysis() == null || q.getAnswer() == null){
			return 0;
		}
		questionService.update(q);
		JSONArray knows = jsonObj.getJSONArray("knowArr");
		if(knows.length() > 0){
			questionService.deleteKnowQuestionByQId(q.getId());
			for(int i = 0;i < knows.length();i ++){
				questionService.insertKnowQuestion(q.getId(), knows.getInt(i));
			}
		}
		return 1;
	}
	
	@SystemControllerLog(description = "check里的getKnow")
	@RequestMapping(value={"/test/knows"}, method=RequestMethod.GET)
	@ResponseBody
	public String testKnows(int subjectId, int gradeId, int areaId, int standardId){
		List<Know> l = knowService.findByParam(subjectId, gradeId, areaId, standardId);		
		JSONArray res = constructTree(0, l);
		return res.toString();
	}
	
	public JSONArray constructTree(long parentIndex, List<Know> l){
		JSONArray jsonArray = new JSONArray();
		for(int i = 0;i < l.size();i ++){
			Know know = l.get(i);
			if(know.getParent() == parentIndex){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("label", know.getName());
				jsonObj.put("id", know.getId());
				JSONArray children = constructTree(know.getId(), l);
				if(children.length() > 0){
					jsonObj.put("children", children);
				}
				jsonArray.put(jsonObj);
			}
		}
		return jsonArray;
	}
	
	public String clearFormat(String htmlStr) {
	    htmlStr = htmlStr
	        .replaceAll(
	            "<[/]?(font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>",
	            "");
	    htmlStr = htmlStr
	        .replaceAll(
	            "<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>",
	            "<$1$2>");
	    htmlStr = htmlStr.replaceAll("<[P|p]>", "");
	    htmlStr = htmlStr.replaceAll("</[P|p]>", "<br/>");
	    return htmlStr;
    }
	
}
