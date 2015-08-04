package indi.tammy.qb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.tammy.qb.model.Question;

public interface QuestionDao {
	public void insert(Question question);
	
	public long formalInsert(Question q);
	
	public void insertKnowQuestion(@Param(value="question_id")int question_id, @Param(value="know_id")int know_id);
	
	public void formalInsertKnowQuestion(@Param(value="formal_id")int formal_id, @Param(value="temp_id")int temp_id);
	
	public void delete(int id);
	
	public void deleteKnowQuestionByQId(int id);
	
	public void deleteSimQuestionByQId(int question_id);
	
	public void deleteWrongQuestionById(int id);
	
	public void formalDelete(int id);
	
	public void update(Question question);
	
	public List<Question> formalFindByParam(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="know_id")int know_id, @Param(value="area_id")int area_id, @Param(value="standard_id")int standard_id, @Param(value="grade_id")int grade_id, @Param(value="subject_id")int subject_id, @Param(value="type_id")int type_id, @Param(value="hardness")int hardness, @Param(value="key")String key);

	public List<Question> findBySubject(@Param(value="subject_id") int subject_id, @Param(value="pStart") int pStart, @Param(value="pEnd") int pEnd);

	public Question findById(int id);
	
	public List<Question> findHaveSimQuestionBySubjectId(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="subject_id")int subject_id);
	
	public List<Question> findSimQuestionByQId(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="question_id")int question_id);
	
	public List<Question> findWrongQuestionBySubjectId(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="subject_id")int subject_id);
	
	public int getTypeId(String type_name);
	
}
