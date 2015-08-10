package indi.tammy.qb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WordBankController {
	
	
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
	
	/**
	 * 新词审阅界面
	 * @return
	 */
	@RequestMapping(value={"/admin/lexicon/wordsCheck"},method = RequestMethod.GET)
	public String pageWordsCheck(){
		return "pagesLexicon/pagesWordsCheck/pageWordsCheck";
	}
	
	@RequestMapping(value={"/admin/lexicon/wordImport/uploader"},method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> wordImportUploader(@RequestParam("file") MultipartFile upfile,String wordType,String grade){
		System.out.println("wordType:"+wordType+",grade:"+grade);
		return new ResponseEntity<String>("123", HttpStatus.BAD_REQUEST);
	}
	
	
}
