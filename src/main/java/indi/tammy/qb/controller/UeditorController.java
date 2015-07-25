package indi.tammy.qb.controller;

import indi.tammy.qb.ueditor.ActionEnter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



@Controller
public class UeditorController {
	@Autowired  
	private HttpServletRequest request;
	
	@Autowired  
	private HttpServletResponse response;

	@RequestMapping(value={"/admin/manualImport/ueditorControl"})
	@ResponseBody
	public String ueditorControl(){
		response.setContentType("application/json");               
		Resource r = new ClassPathResource("ApplicationContextBase.xml");
		String rootPath = null;
		try {
			rootPath = r.getFile().getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (new ActionEnter( request,rootPath).exec());
	}
	
	@RequestMapping(value={"/ueditorDemo"},method = RequestMethod.GET)
	public String ueditorDemo(){
		return "ueditorDemo";
	}
	
	@RequestMapping(value={"/admin/manualImport/imageUp"})
	@ResponseBody
	public String ueditorImageUp(@RequestParam("upfile") MultipartFile upfile){
		String fname = null;
		if(upfile != null){
			response.setContentType("application/json");               
			Resource r = new ClassPathResource("ApplicationContextBase.xml");
			String rootPath = null;
			try {
				rootPath = r.getFile().getParent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileOutputStream out = null; 
			rootPath = rootPath + "/static/upload";
	        // 存放路径，如果不存在则创建路径  
	        File file = new File(rootPath); 
	        //System.out.println(rootPath);
	        if(!file.exists()) file.mkdirs();  
	        fname = upfile.getOriginalFilename();  
	        try {
					out = new FileOutputStream(rootPath + "/" + fname);
					out.write(upfile.getBytes());
					out.close();
					return "{\"name\":\""+fname+"\", \"originalName\": \""+fname+"\", \"size\": 8192, \"state\": \"SUCCESS\", \"type\": \".jpg\", \"url\": \"/upload/"+fname+"\"}";
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		}
		return "FAIL";
	}
}
