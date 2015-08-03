package indi.tammy.qb.controller;

import java.util.ArrayList;
import java.util.List;

import indi.tammy.qb.model.Know;
import indi.tammy.qb.model.Question;
import indi.tammy.qb.model.enums.Area;
import indi.tammy.qb.model.enums.Grade;
import indi.tammy.qb.model.enums.QuestionType;
import indi.tammy.qb.model.enums.Standard;
import indi.tammy.qb.model.enums.Subject;
import indi.tammy.qb.service.EnumService;
import indi.tammy.qb.service.KnowService;
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
public class QuestionAdminController {
	@Autowired 
	private QuestionService questionService;
	
	@Autowired 
	private KnowService knowService;
	
	@Autowired
	private EnumService enumService;
	
	public List<Grade> constuctGradeBySubjectId(int subject_id){
		List<Subject> l = enumService.findSubjectBySubjectId(101);
		String[] grades = {"一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "初一", "初二", "初三", "高一", "高二", "高三"};
		List<Grade> gradeList = new ArrayList<Grade>();
		if(l.size() > 0){
			Subject s = l.get(0);
			if(s.getFlag() == 1){
				for(int i = 1;i <= 6;i ++){
					Grade g = new Grade();
					g.setId(i);
					g.setGrade(grades[i - 1]);
					gradeList.add(g);
				}
			}
			else if(s.getFlag() == 2){
				for(int i = 7;i <= 9;i ++){
					Grade g = new Grade();
					g.setId(i);
					g.setGrade(grades[i]);
					gradeList.add(g);
				}
			}
			else if(s.getFlag() == 3){
				for(int i = 10;i <= 12;i ++){
					Grade g = new Grade();
					g.setId(i);
					g.setGrade(grades[i]);
					gradeList.add(g);
				}
			}
		}
		return gradeList;
	}
	
	@RequestMapping(value={"/admin/questionAdmin"},method = RequestMethod.GET)
	public String adminQuestion(ModelMap modelMap){
		modelMap.addAttribute("gradeList", constuctGradeBySubjectId(101));
		modelMap.addAttribute("areaList", enumService.findAreaBySubjectId(101));
		modelMap.addAttribute("standardList", enumService.findStandardBySubjectId(101));
		modelMap.addAttribute("questionTypeList", enumService.findQuestionTypeBySubjectId(101));
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
		if(l.size() > 0){
			res.put("total", l.get(0).getTotal());
		}
		else{
			res.put("total", 0);
		}
		return res.toString();
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getKnow"}, method=RequestMethod.GET)
	@ResponseBody
	public String getKnows(int subjectId, int gradeId, int areaId, int standardId){
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
	
	@RequestMapping(value={"/admin/questionAdmin/getStandard"}, method=RequestMethod.GET)
	@ResponseBody
	public String getStandard(int subjectId){
		List<Standard> l = enumService.findStandardBySubjectId(subjectId);
		JSONArray jsonMembers = new JSONArray();
		for(Standard x:l){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", x.getId());
			jsonObj.put("name", x.getName());
			jsonMembers.put(jsonObj);
		}
		return jsonMembers.toString();
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getQuestionType"}, method=RequestMethod.GET)
	@ResponseBody
	public String getQuestionType(int subjectId){
		List<QuestionType> l = enumService.findQuestionTypeBySubjectId(subjectId);
		JSONArray jsonMembers = new JSONArray();
		for(QuestionType x:l){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", x.getType());
			jsonObj.put("name", x.getType_name());
			jsonMembers.put(jsonObj);
		}
		return jsonMembers.toString();
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getGrade"}, method=RequestMethod.GET)
	@ResponseBody
	public String getGrade(int subjectId){
		List<Grade> l = constuctGradeBySubjectId(subjectId);
		JSONArray jsonMembers = new JSONArray();
		for(Grade x:l){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", x.getId());
			jsonObj.put("name", x.getGrade());
			jsonMembers.put(jsonObj);
		}
		return jsonMembers.toString();
	}
	
	@RequestMapping(value={"/admin/questionAdmin/deleteOne"}, method=RequestMethod.GET)
	@ResponseBody
	public String deleteOne(int id){
		questionService.formalDelete(id);
		return null;
	}
	
	@RequestMapping(value={"/admin/questionAdmin/deleteSome"},method = RequestMethod.GET)
	@ResponseBody
	public String deleteSome(String idArr){
		JSONArray ids = new JSONArray(idArr);
		for(int i = 0;i < ids.length();i ++){
			questionService.formalDelete(ids.getInt(i));
		}
		return null;
	}
	
}
