package indi.tammy.qb.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.tammy.qb.model.Question;
import indi.tammy.qb.model.User;
import indi.tammy.qb.service.QuestionService;
import indi.tammy.qb.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private QuestionService questionService;

	@RequestMapping(value={"/register"},method = RequestMethod.GET)
	public String registerGet(){
		List<User> users = registerService.getUsers();
		System.out.println(users.size());
		for(User x:users){
			System.out.println(x.getEmail()+"   "+x.getPassword());
		}
		User user = registerService.getUserById("123");
		System.out.println(user.getEmail());
		return "register";
	}
	
	@RequestMapping(value={"/admin/home"},method = RequestMethod.GET)
	public String adminHomeGet(){
		
		return "adminHome";
	}
	
	@RequestMapping(value={"/admin/manualImport"},method = RequestMethod.GET)
	public String adminManualImport(){
		
		return "pagesQuestionBank/pagesQuestionImport/pageManualImport";
	}
	
	@RequestMapping(value={"/test/qbankInsert"},method = RequestMethod.GET)
	@ResponseBody
	public String qbankDelete(){
		Question question = new Question();
		question.setContent("这是一道测试 问题");
		question.setAnswer("没有答案");
		question.setAnalysis("没有题目解析");
		question.setType(2);
		question.setFull(true);
		question.setHardness(0);
		question.setSubmit_time(System.currentTimeMillis());
		questionService.insert(question);
		return "This is a test page for qbank insert"+question.getId();
	}
	
	@RequestMapping(value={"/test/qbankDelete/{id}"},method = RequestMethod.GET)
	@ResponseBody
	public String qbankInsert(@PathVariable("id")int id){
		questionService.delete(id);
		return "This is a test page for qbank delete";
	}
	
	@RequestMapping(value={"/test/configPath"},method = RequestMethod.GET)
	@ResponseBody
	public String qbankInsert(HttpServletRequest request){
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		rootPath = rootPath.replace( "\\", "/" );
		System.out.println(rootPath);
		String contextPath = request.getContextPath();
		System.out.println(contextPath);
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		String originalPath = null;
		if ( contextPath.length() > 0 ) {
			originalPath = rootPath + uri.substring( contextPath.length() );
		} else {
			originalPath = rootPath + uri;
		}
		System.out.println(originalPath);

		return "1";
	}
}
