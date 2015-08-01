package indi.tammy.qb.controller;

import java.util.List;

import indi.tammy.qb.model.Know;
import indi.tammy.qb.model.Question;
import indi.tammy.qb.model.enums.Area;
import indi.tammy.qb.model.enums.Subject;
import indi.tammy.qb.service.EnumService;
import indi.tammy.qb.service.KnowService;
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
	@Autowired 
	private QuestionService questionService;
	
	@Autowired 
	private KnowService knowService;
	
	@Autowired
	private EnumService enumService;
	
	@RequestMapping(value={"/admin/questionAdmin"},method = RequestMethod.GET)
	public String adminQuestion(){
		return "pagesQuestionBank/pagesQuestionAdmin/pageQuestionAdmin";
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getQuestionData"},method = RequestMethod.GET)
	@ResponseBody
	public String getQuestionData(){
		List<Question> l = questionService.formalFindByParam(1, 10, -1, -1, -1, -1, 101, -1, -1, "%tbc%");
		JSONObject res = new JSONObject();
		JSONArray jsonMembers = new JSONArray();
		if(l.size() == 0){
			res.put("total", 0);
			return res.toString();
		}
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
		res.put("data", jsonMembers.toString());
		res.put("total", l.get(0).getTotal());
		return res.toString();
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getKnow"}, method=RequestMethod.GET)
	@ResponseBody
	public String testKnows(String subject, int area_id, int standard_id){
		List<Know> l = knowService.findByParam(subject, area_id, standard_id);		
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
	
	@RequestMapping(value={"/admin/questionAdmin/getArea"}, method=RequestMethod.GET)
	@ResponseBody
	public String getArea(int subjectId){
		List<Area> l = enumService.findAreaBySubjectId(subjectId);
		JSONArray jsonMembers = new JSONArray();
		for(Area x:l){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", x.getId());
			jsonObj.put("name", x.getName());
			jsonMembers.put(jsonObj);
		}
		return jsonMembers.toString();
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getSubject"}, method=RequestMethod.GET)
	@ResponseBody
	public String getSubject(int subjectId){
		List<Subject> l = enumService.findSubjectBySubjectId(subjectId);
		JSONArray jsonMembers = new JSONArray();
		for(Subject x:l){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", x.getId());
			jsonObj.put("name", x.getName());
			jsonMembers.put(jsonObj);
		}
		return jsonMembers.toString();
	}
	
}
