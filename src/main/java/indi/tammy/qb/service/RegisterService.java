package indi.tammy.qb.service;

import indi.tammy.qb.dao.RegisterDao;
import indi.tammy.qb.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

	@Autowired
	private RegisterDao registerDao;
	
	public void add(User user) {
		registerDao.insert(user);
		
	}

	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return registerDao.select();
	}

	public User getUserById(String email){
		return registerDao.selectId(email);
	}
}
