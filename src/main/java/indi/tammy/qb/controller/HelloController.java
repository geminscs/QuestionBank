package indi.tammy.qb.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.tammy.qb.model.Question;
import indi.tammy.qb.model.User;
import indi.tammy.qb.service.QuestionService;
import indi.tammy.qb.service.RegisterService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
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
	
	@RequestMapping(value={"/admin/questionAdmin"},method = RequestMethod.GET)
	public String adminQuestion(){
		
		return "pagesQuestionBank/pagesQuestionAdmin/pageQuestionAdmin";
	}
	
	@RequestMapping(value={"/admin/importNotice"},method = RequestMethod.GET)
	public String adminImportNotice(){
		
		return "pagesQuestionBank/pagesQuestionImport/pageImportNotice";
	}
	
	@RequestMapping(value={"/admin/questionCheck"},method = RequestMethod.GET)
	public String adminQuestionCheck(){
		
		return "pagesQuestionBank/pagesQuestionCheck/pageQuestionCheck";
	}
	
	@RequestMapping(value={"/admin/questionCheck/questionModify"},method = RequestMethod.GET)
	public String adminModifyForCheck(){
		
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
	
	@RequestMapping(value={"/getJsonData"},method = RequestMethod.GET)
	@ResponseBody
	public String getJsonData(String subject, int pageIndex, int pageSize){
		List<Question> l = questionService.findBySubject(subject, pageIndex * pageSize + 1, (pageIndex + 1) * pageSize);
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
		    JSONObject member1 = new JSONObject();  
		    member1.put("id", "1");  
		    member1.put("type", "作文题");  
		    member1.put("subject", "小学语文");  
		    member1.put("grade","三年级");  
		    member1.put("content", "test");  
		    member1.put("answer", "test");  
		    member1.put("analysis", "test");  
		    member1.put("know", "记叙文；说明文；议论文；全命题作文；半命题作文");
		    
		    jsonMembers.put(member1);  
		  
		    JSONObject member2 = new JSONObject();  
		    member2.put("id", "2");  
		    member2.put("type", "作文题");  
		    member2.put("subject", "小学语文");  
		    member2.put("grade","三年级");  
		    member2.put("content", "test");  
		    member2.put("answer", "test");  
		    member2.put("analysis", "test");  
		    member2.put("know", "记叙文；说明文；议论文；全命题作文；半命题作文");
		    jsonMembers.put(member2);  
		    json.put("data", jsonMembers); 
		    json.put("total", 800); 
		    
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
		questionService.formalInsert(id);
		questionService.delete(id);
		return;
	}
	
}
