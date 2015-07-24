package indi.tammy.qb.controller;

import indi.tammy.qb.ueditor.ActionEnter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class UeditorController {

	@RequestMapping(value={"/admin/manualImport/ueditorControl"},method = RequestMethod.GET)
	@ResponseBody
	public String ueditorControl(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("application/json");               
		Resource r = new ClassPathResource("ApplicationContextBase.xml");
		String rootPath = null;
		try {
			rootPath = r.getFile().getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (new ActionEnter( request,rootPath).exec()+"<br/>"
				+request.getContextPath()+"<br/>"+request.getRequestURI());
	}
}
