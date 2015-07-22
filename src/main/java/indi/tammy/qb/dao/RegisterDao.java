package indi.tammy.qb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.tammy.qb.model.User;

public interface RegisterDao {

	public void insert(User user) ;

	public List<User> select();
	
	public User selectId(@Param(value = "email")String email);
}
