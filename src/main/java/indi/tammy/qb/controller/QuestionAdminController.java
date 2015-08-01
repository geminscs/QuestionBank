package indi.tammy.qb.controller;

import java.util.List;

import indi.tammy.qb.model.Question;
import indi.tammy.qb.service.QuestionService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionAdminController {
	@Autowired QuestionService questionService;
	
	@RequestMapping(value={"/admin/questionAdmin"},method = RequestMethod.GET)
	public String adminQuestion(){
		return "pagesQuestionBank/pagesQuestionAdmin/pageQuestionAdmin";
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getJsonData"},method = RequestMethod.GET)
	@ResponseBody
	public String getJsonData(){
		List<Question> l = questionService.formalFindByParam(1, 10, -1, -1, -1, -1, 101, -1, -1);
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
		return jsonMembers.toString();
	}
	
}
