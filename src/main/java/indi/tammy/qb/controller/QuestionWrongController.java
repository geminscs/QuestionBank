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
public class QuestionWrongController {
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(value={"/admin/delWrongQuestion"},method = RequestMethod.GET)
	public String adminDelWrongQuestion(){//根据试题id显示试卷信息
		return "pagesQuestionBank/pageDelWrongQuestion";
	}
	
	@RequestMapping(value={"/admin/delWrongQuestion/getWrongQuestionData"},method = RequestMethod.GET)
	@ResponseBody
	public String getWrongQuestionData(int pageIndex, int pageSize, int subjectId){//根据试题id显示试卷信息
		List<Question> l = questionService.findWrongQuestionBySubjectId(pageIndex*pageSize+1, (pageIndex+1)*pageSize, subjectId);
		JSONArray jsonMembers = new JSONArray();
		JSONObject res = new JSONObject();
		for(Question q:l){
			JSONObject member = new JSONObject();
			member.put("id", q.getId());  
			member.put("type", q.getType_name());   
			member.put("content", q.getContent());  
			member.put("answer", q.getAnswer());  
			member.put("analysis", q.getAnalysis());  
			member.put("know", q.getKnow_name());
			member.put("errorType", q.getWrong_type_name());
			member.put("errorMsg", q.getWrong_message());
			member.put("errorId", q.getWrong_id());
			
			jsonMembers.put(member);
		}
		
		if(l.size() > 0){
			res.put("total", l.get(0).getTotal());
		}
		else{
			res.put("total", 0);
		}
		res.put("data", jsonMembers);
		return res.toString();
	}
	
	@RequestMapping(value={"/admin/delWrongQuestion/ignoreOne"},method = RequestMethod.GET)
	@ResponseBody
	public String ignoreOne(int id){//根据试题id显示试卷信息
		questionService.deleteWrongQuestionById(id);
		return null;
	}
	
	@RequestMapping(value={"/admin/delWrongQuestion/ignoreSome"},method = RequestMethod.GET)
	@ResponseBody
	public String ignoreSome(String idArr){
		JSONArray ids = new JSONArray(idArr);
		for(int i = 0;i < ids.length();i ++){
			questionService.deleteWrongQuestionById(ids.getInt(i));
		}
		return null;
	}
	
	
}	
