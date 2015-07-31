package indi.tammy.qb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.tammy.qb.model.Question;

public interface QuestionDao {
	public void insert(Question question);
	
	public void formalInsert(int id);
	
	public void insertKnowQuestion(@Param(value="question_id")int question_id, @Param(value="know_id")int know_id);
	
	public void delete(int id);
	
	public void update(Question question);
	
	public void findByParam(@Param(value="know")int know, @Param(value="area")int area, @Param(value="standard")int standard, @Param(value="grade")int grade);

	public List<Question> findBySubject(@Param(value="subject") String subject, @Param(value="pStart") int pStart, @Param(value="pEnd") int pEnd);

	public int getTypeId(String type_name);
	
}
