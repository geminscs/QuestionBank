package indi.tammy.qb.controller;

import indi.tammy.qb.model.Question;
import indi.tammy.qb.model.WordBank;
import indi.tammy.qb.service.WordBankService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;












import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
	@Autowired
	private WordBankService wordBankService;
	
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
	 * 上传单词文件处理
	 * @param upfile
	 * @param wordType
	 * @param grade
	 * @return
	 */
	
	
	@SuppressWarnings("resource")
	@RequestMapping(value={"/admin/lexicon/wordImport/uploader"},method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> wordImportUploader(@RequestParam("file") MultipartFile upfile,int wordType,int grade){
		try {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(upfile.getInputStream());
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			System.out.println(xssfSheet.getLastRowNum());
			System.out.println(xssfSheet.getFirstRowNum());
			for(int i = xssfSheet.getFirstRowNum()+1;i <= xssfSheet.getLastRowNum();i ++){
				XSSFRow row = xssfSheet.getRow(i);
				if(row == null){
					continue;
				}
				WordBank w = new WordBank();
				w.setWord(getValue(row.getCell(0)));
				w.setPhonetic(getValue(row.getCell(1)));
				w.setExplain(getValue(row.getCell(2)));
				w.setType(wordType);
				w.setGrade(grade);
				wordBankService.insert(w);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>("123", HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("static-access")
	private String getValue(XSSFCell xssfRow) {
		if(xssfRow == null){
			return " ";
		}
		         if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
		             return String.valueOf(xssfRow.getBooleanCellValue());
		         } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
		             return String.valueOf(xssfRow.getNumericCellValue());
		         } else {
		             return String.valueOf(xssfRow.getStringCellValue());
		         }
		     }
}
