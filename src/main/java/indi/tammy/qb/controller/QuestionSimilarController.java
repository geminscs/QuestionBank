package indi.tammy.qb.controller;

import java.util.ArrayList;
import java.util.List;

import indi.tammy.qb.model.Question;
import indi.tammy.qb.model.enums.Subject;
import indi.tammy.qb.service.EnumService;
import indi.tammy.qb.service.QuestionService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionSimilarController {
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private EnumService enumService;
	
	@RequestMapping(value={"/admin/delSimQuestion"},method = RequestMethod.GET)
	public String adminDelSimQuestion(ModelMap modelMap){//根据试题id显示试卷信息
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
		return "pagesQuestionBank/pagesDelSimQuestion/pageDelSimQuestion";
	}
	
	
	@RequestMapping(value={"/admin/delSimQuestion/getSimData"},method = RequestMethod.GET)
	@ResponseBody
	public String getSimData(int subjectId){//根据试题id显示试卷信息
		List<Question> l = questionService.findHaveSimQuestionBySubjectId(1, 10, subjectId);
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
			
			jsonMembers.put(member);
		}
		
		if(l.size() > 0){
			res.put("total", l.get(0).getTotal());
		}else{
			res.put("total", 0);
		}
		res.put("data", jsonMembers);
		return res.toString();
	}
	
	@RequestMapping(value={"/admin/delSimQuestion/checkSimQuestions"},method = RequestMethod.GET)
	public String checkSimQuestions(int id){//根据试题id显示试卷信息
		List<Question> l = questionService.findSimQuestionByQId(1, 10, id);
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
			
			jsonMembers.put(member);
		}
		
		if(l.size() > 0){
			res.put("total", l.get(0).getTotal());
		}
		else{
			res.put("total", 0);
		}
		res.put("data", jsonMembers);
		System.out.println(res.toString());
		return "pagesQuestionBank/pagesDelSimQuestion/pageShowSimQuestion";
	}
	
	@RequestMapping(value={"/admin/delSimQuestion/ignoreOne"},method = RequestMethod.GET)
	@ResponseBody
	public String ignoreOne(int id){
		questionService.deleteSimQuestionByQId(id);
		return null;
	}
	
	@RequestMapping(value={"/admin/delSimQuestion/ignoreSome"},method = RequestMethod.GET)
	@ResponseBody
	public String ignoreSome(String idArr){
		JSONArray ids = new JSONArray(idArr);
		for(int i = 0;i < ids.length();i ++){
			questionService.deleteSimQuestionByQId(ids.getInt(i));
		}
		return null;
	}
	
}
