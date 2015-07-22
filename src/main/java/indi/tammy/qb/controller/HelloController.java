package indi.tammy.qb.controller;

import java.util.List;

import indi.tammy.qb.model.User;
import indi.tammy.qb.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
	@Autowired
	private RegisterService registerService;

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
}
