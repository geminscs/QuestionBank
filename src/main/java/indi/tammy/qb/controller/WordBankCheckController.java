package indi.tammy.qb.controller;

import indi.tammy.qb.model.WordBank;
import indi.tammy.qb.service.WordBankService;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WordBankCheckController {
	@Autowired
	private WordBankService wordBankService;
	
	@RequestMapping(value={"/admin/lexicon/wordsCheck/getWordsData"},method = RequestMethod.GET)
	@ResponseBody
	public String getWordsData(int draw, int start, int length, int wordType){
		List<WordBank> l = wordBankService.findByType(start+1, start+length, wordType);
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
		json.put("data", jsonMembers.toString());    
		return json.toString();
	}
	
	/**
	 * 新词审阅界面
	 * @return
	 */
	@RequestMapping(value={"/admin/lexicon/wordsCheck"},method = RequestMethod.GET)
	public String pageWordsCheck(){
		return "pagesLexicon/pagesWordsCheck/pageWordsCheck";
	}
	
	/**
	 * 新词审阅的单词修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value={"/admin/lexicon/wordsCheck/modify"},method = RequestMethod.GET)
	public String pageWordsCheckModify(String id){
		return "pagesLexicon/pagesWordsCheck/pageWordsModify";
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsCheck/passOne"},method = RequestMethod.GET)
	public int passOne(int id){
		wordBankService.copyTempToFormal(id);
		wordBankService.delete(id);
		return 1;
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsCheck/passSome"},method = RequestMethod.GET)
	public int passSome(String idArr){
		JSONArray ids = new JSONArray(idArr);
		for(int i = 0;i < ids.length();i ++){
			int id = ids.getInt(i);
			wordBankService.copyTempToFormal(id);
			wordBankService.delete(id);
		}
		return 1;
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsCheck/delOne"},method = RequestMethod.GET)
	public int delOne(int id){
		wordBankService.delete(id);
		return 1;
	}
	
	@RequestMapping(value={"/admin/lexicon/wordsCheck/delSome"},method = RequestMethod.GET)
	public int delSome(String idArr){
		JSONArray ids = new JSONArray(idArr);
		for(int i = 0;i < ids.length();i ++){
			int id = ids.getInt(i);
			wordBankService.delete(id);
		}
		return 1;
	}
}
