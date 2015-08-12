package indi.tammy.qb.controller;

import java.util.List;

import indi.tammy.qb.model.WordBank;
import indi.tammy.qb.service.WordBankService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WordBankAdminController {
	@Autowired
	private WordBankService wordBankService;
	
	@RequestMapping(value={"/admin/lexicon/wordsAdmin/delOne"},method = RequestMethod.GET)
	@ResponseBody
	public int delOne(int id){
		wordBankService.formalDelete(id);
		return 1;
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsAdmin/delSome"},method = RequestMethod.GET)
	@ResponseBody
	public int delSome(String idArr){
		JSONArray ids = new JSONArray(idArr);
		for(int i = 0;i < ids.length();i ++){
			int id = ids.getInt(i);
			wordBankService.formalDelete(id);
		}
		return 1;
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsAdmin/getWordsData"},method = RequestMethod.GET)
	@ResponseBody
	public String getWordsData(int wordType, int grade, String key, int draw, int start, int length){
		if(key == null || key.length() <= 0){
			key = null;
		}
		else{
			key = "%"+key+"%";
		}
		List<WordBank> l = wordBankService.formalFindByParam(start+1, start+length, wordType, grade, key);
		JSONObject json=new JSONObject();  
		JSONArray jsonMembers = new JSONArray();
		for(int i = 0;i < l.size();i ++){
			JSONObject member = new JSONObject();
			WordBank w = l.get(i);
			member.put("id", w.getId());  
			member.put("type", w.getType_name());  
			member.put("grade",w.getGrade());  
			member.put("word", w.getWord());  
			member.put("phonetic", w.getPhonetic());  
			member.put("explain", w.getExplain());  
			jsonMembers.put(member); 
		}
		if(l.size() > 0){
			json.put("recordsTotal", l.get(0).getTotal());
			json.put("recordsFiltered", l.get(0).getTotal());
		}
		else{
			json.put("recordsTotal", 0);
			json.put("recordsFiltered", 0);
		}
		json.put("draw", draw);
		json.put("data", jsonMembers);    
		return json.toString();
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsAdmin/modify"},method = RequestMethod.GET)
	public String pageWordsAdminModify(int id, ModelMap modelMap){
		WordBank w = wordBankService.formalFindById(id);
		if(w == null){
			return "ErrorPage";
		}
		modelMap.addAttribute("wordBank", w);
		return "pagesLexicon/pagesWordsAdmin/pageWordsModify";
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsAdmin/modify/save"},method = RequestMethod.POST)
	@ResponseBody
	public int save(String wordInfo){
		if(wordInfo == null){
			return 0;
		}
		JSONObject jsonObj = new JSONObject(wordInfo);
		WordBank w = new WordBank();
		w.setId(jsonObj.getInt("id"));
		w.setWord(jsonObj.getString("word"));
		w.setPhonetic(jsonObj.getString("phonetic"));
		w.setExplain(jsonObj.getString("explain"));
		w.setType(jsonObj.getInt("type"));
		w.setGrade(jsonObj.getInt("grade"));
		wordBankService.formalUpdate(w);
		return 1;
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsAdmin/reportError"},method = RequestMethod.GET)
	@ResponseBody
	public int reportError(int id, int type, String content){
		WordBank w = new WordBank();
		w.setId(id);
		w.setWrong_type(type);
		w.setWrong_message(content);
		wordBankService.insertWrongWord(w);
		return 1;
	}
}
