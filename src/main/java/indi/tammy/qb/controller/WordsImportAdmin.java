package indi.tammy.qb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WordsImportAdmin {
	
	/**
	 * 手动导入单词页面
	 * @return
	 */
	@RequestMapping(value={"/admin/lexicon/manualImport"},method = RequestMethod.GET)
	public String pageManualImport(){
		return "pagesLexicon/pagesWordsImport/pageManualImport";
	}
	
	/**
	 * word导入单词页面
	 * @return
	 */
	@RequestMapping(value={"/admin/lexicon/wordImport"},method = RequestMethod.GET)
	public String pageWordImport(){
		return "pagesLexicon/pagesWordsImport/pageWordImport";
	}
	
	
}
