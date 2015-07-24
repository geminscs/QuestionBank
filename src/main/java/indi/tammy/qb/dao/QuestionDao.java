package indi.tammy.qb.dao;

import org.apache.ibatis.annotations.Param;

import indi.tammy.qb.model.Question;

public interface QuestionDao {
	public void insert(Question question);
	
	public void delete(int id);
	
	public void update(Question question);
	
	public void findByParam(@Param(value="know")int know, @Param(value="area")int area, @Param(value="standard")int standard, @Param(value="grade")int grade);
}
