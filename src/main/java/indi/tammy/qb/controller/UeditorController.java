package indi.tammy.qb.controller;

import indi.tammy.qb.ueditor.ActionEnter;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String rootPath = request.getSession().getServletContext().getRealPath("/");
		
		return (new ActionEnter( request,rootPath).exec()+"<br/>"
				+request.getContextPath()+"<br/>"+request.getRequestURI());
		
	
	}
}
