package indi.tammy.qb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.tammy.qb.model.Question;

public interface QuestionDao {
	//插入一个Question对象到qb_temp_qbank表
	public void insert(Question question);
	//从qb_temp_qbank复制一个Question对象到qb_formal_qbank表
	public long formalInsert(Question q);
	//插入一个Question对象到qb_formal_qbank表
	public void formalModifyQuestion(Question q);
	//插入知识点问题关系到qb_temp_knowQuestion表
	public void insertKnowQuestion(@Param(value="question_id")int question_id, @Param(value="know_id")int know_id);
	//插入错误问题关系到qb_wrong_question表
	public void insertWrongQuestion(@Param(value="question_id")int question_id, @Param(value="wrong_type")int wrong_type, @Param(value="wrong_message")String wrong_message);
	//从qb_temp_knowQuestion复制知识点问题关系到qb_formal_qbank
	public void formalInsertKnowQuestion(@Param(value="formal_id")int formal_id, @Param(value="temp_id")int temp_id);
	//插入知识点问题关系到qb_formal_knowQuestion表
	public void formalModifyKnowQuestion(@Param(value="question_id")int question_id, @Param(value="know_id")int know_id);
	//对qb_formal_knowQuestion进行表内复制
	public void formalCopyKnowQuestionByQId(@Param(value="old_id")int old_id, @Param(value="new_id")int new_id);
	
	public void delete(int id);
	
	public void deleteKnowQuestionByQId(int id);
	
	public void deleteSimQuestionByQId(int id);
	
	public void deleteSimQuestionById(int id);
	
	public void deleteWrongQuestionById(int id);
	
	public void formalDelete(int id);
	
	public void update(Question question);
	
	public List<Question> formalFindByParam(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="know_id")int know_id, @Param(value="area_id")int area_id, @Param(value="standard_id")int standard_id, @Param(value="grade_id")int grade_id, @Param(value="subject_id")int subject_id, @Param(value="type_id")int type_id, @Param(value="hardness")int hardness, @Param(value="key")String key);

	public List<Question> findBySubject(@Param(value="subject_id") int subject_id, @Param(value="pStart") int pStart, @Param(value="pEnd") int pEnd);

	public Question findById(int id);
	
	public Question formalFindById(int id);
	
	public List<Question> findHaveSimQuestionBySubjectId(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="subject_id")int subject_id);
	
	public List<Question> findSimQuestionByQId(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="question_id")int question_id);
	
	public List<Question> findWrongQuestionBySubjectId(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="subject_id")int subject_id);
	
	public int getTypeId(String type_name);
	
}
