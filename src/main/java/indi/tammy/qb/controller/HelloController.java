package indi.tammy.qb.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value={"/register"},method = RequestMethod.GET)
	public String registerGet(){
		List<User> users = registerService.getUsers();
		System.out.println(users.size());
		for(User x:users){
			System.out.println(x.getEmail()+"   "+x.getPassword());
		}
		User user = registerService.getUserById("123");
		System.out.println(user.getEmail());
		return "register";
	}
	
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
				primary.add(s);
			}
			else if(s.getFlag() == 2){
				middle.add(s);
			}
			else if(s.getFlag() == 3){
				high.add(s);
			}
		}
		modelMap.addAttribute("primary", primary);
		modelMap.addAttribute("middle", middle);
		modelMap.addAttribute("high", high);
		return "pagesQuestionBank/pagesQuestionCheck/pageQuestionCheck";
	}
	
	@RequestMapping(value={"/admin/questionCheck/questionModify"},method = RequestMethod.GET)
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

	
	@RequestMapping(value={"/test/qbankInsert"},method = RequestMethod.GET)
	@ResponseBody
	public String qbankDelete(){
		Question question = new Question();
		question.setContent("这是一道测试 问题");
		question.setAnswer("没有答案");
		question.setAnalysis("没有题目解析");
		question.setType(2);
		question.setFull(true);
		question.setHardness(0);
		question.setSubmit_time(System.currentTimeMillis());
		questionService.insert(question);
		return "This is a test page for qbank insert"+question.getId();
	}
	
	@RequestMapping(value={"/test/qbankDelete/{id}"},method = RequestMethod.GET)
	@ResponseBody
	public String qbankInsert(@PathVariable("id")int id){
		questionService.delete(id);
		return "This is a test page for qbank delete";
	}
	
	@RequestMapping(value={"/test/configPath"},method = RequestMethod.GET)
	@ResponseBody
	public String qbankInsert(HttpServletRequest request){
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		rootPath = rootPath.replace( "\\", "/" );
		System.out.println(rootPath);
		String contextPath = request.getContextPath();
		System.out.println(contextPath);
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		String originalPath = null;
		if ( contextPath.length() > 0 ) {
			originalPath = rootPath + uri.substring( contextPath.length() );
		} else {
			originalPath = rootPath + uri;
		}
		System.out.println(originalPath);
		
		Resource r = new ClassPathResource("ApplicationContextBase.xml");
		try {
			System.out.println(r.getFile().getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "1";
	}
	
	@RequestMapping(value={"/admin/questionCheck/getQuestionData"},method = RequestMethod.GET)
	@ResponseBody
	public String getJsonData(int subjectId, int pageIndex, int pageSize){
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
	
	@RequestMapping(value={"/admin/questionCheck/questionModify/save"}, method=RequestMethod.GET)
	@ResponseBody
	public String question(String questionInfoStr){
		JSONObject jsonObj = new JSONObject(questionInfoStr);
		Question q = new Question();
		q.setId(jsonObj.getInt("id"));
		q.setType(jsonObj.getInt("typeId"));
		q.setContent(jsonObj.getString("content"));
		questionService.update(q);
		JSONArray knows = new JSONArray(jsonObj.getString("knowArr"));
		if(knows.length() > 0){
			questionService.deleteKnowQuestionByQId(q.getId());
			for(int i = 0;i < knows.length();i ++){
				questionService.insertKnowQuestion(q.getId(), knows.getInt(i));
			}
		}
		return null;
	}
	
	
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
	
}
