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
public class QuestionAdminController {
	@Autowired 
	private QuestionService questionService;
	
	@Autowired 
	private KnowService knowService;
	
	@Autowired
	private EnumService enumService;
	
	public List<Grade> constuctGradeBySubjectId(int subject_id){
		List<Subject> l = enumService.findSubjectBySubjectId(subject_id);
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
					g.setGrade(grades[i - 1]);
					gradeList.add(g);
				}
			}
			else if(s.getFlag() == 3){
				for(int i = 10;i <= 12;i ++){
					Grade g = new Grade();
					g.setId(i);
					g.setGrade(grades[i - 1]);
					gradeList.add(g);
				}
			}
		}
		return gradeList;
	}
	
	@RequestMapping(value={"/admin/questionAdmin"},method = RequestMethod.GET)
	public String adminQuestion(ModelMap modelMap){
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
		modelMap.addAttribute("gradeList", constuctGradeBySubjectId(101));
		modelMap.addAttribute("areaList", enumService.findAreaBySubjectId(101));
		modelMap.addAttribute("standardList", enumService.findStandardBySubjectId(101));
		modelMap.addAttribute("questionTypeList", enumService.findQuestionTypeBySubjectId(101));
		return "pagesQuestionBank/pagesQuestionAdmin/pageQuestionAdmin";
	}
	
	@RequestMapping(value={"/admin/questionAdmin/questionModify"},method = RequestMethod.GET)
	public String questionModify(int id, ModelMap modelMap){
		Question q = questionService.formalFindById(id);
		List<QuestionType> l = enumService.findQuestionTypeBySubjectId(q.getSubject_id());
		q.setContent(StringEscapeUtils.unescapeHtml4(q.getContent()));
		List<Subject> l2 = enumService.findAllSubject();
		modelMap.addAttribute("question", q);
		modelMap.addAttribute("questionTypeList", l);
		modelMap.addAttribute("subjectList", l2);
		return "pagesQuestionBank/pagesQuestionAdmin/pageQuestionModify";
	}
	
	@RequestMapping(value={"/admin/questionAdmin/questionModify/save"}, method=RequestMethod.POST)
	@ResponseBody
	public int questionModifySave(String questionInfoStr){
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
		System.out.println(content);
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
		questionService.formalModifyQuestion(q);
		JSONArray knows = jsonObj.getJSONArray("knowArr");
		if(knows.length() > 0){
			for(int i = 0;i < knows.length();i ++){
				questionService.formalModifyKnowQuestion(q.getId(), knows.getInt(i));
			}
		}
		return 1;
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getQuestionData"},method = RequestMethod.GET)
	@ResponseBody
	public String getQuestionData(int subjectId, int gradeId, int fieldId, int standardId, int knowId, int typeId, String hardness, int pageIndex, int pageSize, String key){
		if(key == null || key.length() <= 0){
			key = null;
		}
		else{
			key = "%"+key+"%";
		}
		List<Question> l = questionService.formalFindByParam(pageIndex*pageSize+1, (pageIndex+1)*pageSize, knowId, fieldId, standardId, gradeId, subjectId, typeId, -1, key);
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
		System.out.println("questionData"+res.toString());
		return res.toString();
	}
	
	@RequestMapping(value={"/admin/questionAdmin/getKnow"}, method=RequestMethod.GET)
	@ResponseBody
	public String getKnows(int subjectId, int gradeId, int fieldId, int standardId){
		List<Know> l = knowService.findByParam(subjectId, gradeId, fieldId, standardId);		
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
		System.out.println(jsonMembers.toString());
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
		return "1";
	}
	
	@RequestMapping(value={"/admin/questionAdmin/deleteSome"},method = RequestMethod.GET)
	@ResponseBody
	public String deleteSome(String idArr){
		JSONArray ids = new JSONArray(idArr);
		for(int i = 0;i < ids.length();i ++){
			System.out.println(ids.getInt(i));
			questionService.formalDelete(ids.getInt(i));
		}
		return "1";
	}
	
	@RequestMapping(value={"/admin/questionAdmin/reportError"},method = RequestMethod.GET)
	@ResponseBody
	public int reportError(int id, int type, String content){
		questionService.insertWrongQuestion(id, type, content);
		return 1;
	}
	
	@RequestMapping(value={"/admin/questionAdmin/modify"},method = RequestMethod.GET)
	public String modify(int id){
		return "pagesQuestionBank/pagesQuestionAdmin/pageQuestionModify";
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
	    //htmlStr = htmlStr.replaceAll("<[div|DIV]>", "");
	    //htmlStr = htmlStr.replaceAll("</[div|DIV]>", "<br/>");
	    return htmlStr;
    }
	
}
