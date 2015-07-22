package indi.tammy.qb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping(value={"/register"},method = RequestMethod.GET)
	public String registerGet(){	
		return "register";
	}
}
