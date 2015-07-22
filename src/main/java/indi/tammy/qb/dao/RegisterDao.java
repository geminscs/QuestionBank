package indi.tammy.qb.dao;

import java.util.List;

import indi.tammy.qb.model.User;

public interface RegisterDao {

	public void insert(User user) ;

	public List<User> select();
}
