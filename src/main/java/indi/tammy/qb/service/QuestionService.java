package indi.tammy.qb.service;

import java.util.List;

import indi.tammy.qb.dao.QuestionDao;
import indi.tammy.qb.model.Question;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
	@Autowired
	private QuestionDao questionDao;
	
	public void insert(Question question){
		questionDao.insert(question);
	}
	
	public void delete(int id){
		questionDao.delete(id);
	}
	
	public List<Question> findBySubject(int subject_id, int pStart, int pEnd){
		return questionDao.findBySubject(subject_id, pStart, pEnd);
	}
	
	public long formalInsert(Question q){
		return questionDao.formalInsert(q);
	}
	
	public int getTypeId(String type_name){
		return questionDao.getTypeId(type_name);
	}
	
	public void insertKnowQuestion(int question_id, int know_id){
		questionDao.insertKnowQuestion(question_id, know_id);
	}
	
	public List<Question> formalFindByParam(int pStart, int pEnd, int know_id, int area_id, int standard_id, int grade_id, int subject_id, int type_id, int hardness, String key){
		return questionDao.formalFindByParam(pStart, pEnd, know_id, area_id, standard_id, grade_id, subject_id, type_id, hardness, key);
	}

	public void formalInsertKnowQuestion(int formal_id, int temp_id){
		questionDao.formalInsertKnowQuestion(formal_id, temp_id);
	}
	
	public void formalDelete(int id){
		questionDao.formalDelete(id);
	}
	
	public Question findById(int id){
		return questionDao.findById(id);
	}
	
	public void update(Question q){
		questionDao.update(q);
	}
	
	public void deleteKnowQuestionByQId(int id){
		questionDao.deleteKnowQuestionByQId(id);
	}
	
	public List<Question> findHaveSimQuestionBySubjectId(int pStart, int pEnd, int subject_id){
		return questionDao.findHaveSimQuestionBySubjectId(pStart, pEnd, subject_id);
	}
	
	public List<Question> findSimQuestionByQId(int pStart, int pEnd, int question_id){
		return questionDao.findSimQuestionByQId(pStart, pEnd, question_id);
	}
		
	public void deleteSimQuestionByQId(int question_id){
		questionDao.deleteSimQuestionByQId(question_id);
	}
	
	public List<Question> findWrongQuestionBySubjectId(int pStart, int pEnd, int subject_id){
		return questionDao.findWrongQuestionBySubjectId(pStart, pEnd, subject_id);
	}
	
	public void deleteWrongQuestionById(int id){
		questionDao.deleteWrongQuestionById(id);
	}

	public void insertWrongQuestion(int question_id, int wrong_type, String wrong_message){
		questionDao.insertWrongQuestion(question_id, wrong_type, wrong_message);
	}
	
	public Question formalFindById(int id){
		return questionDao.formalFindById(id);
	}
	
	public void formalModifyQuestion(Question q){
		questionDao.formalModifyQuestion(q);
	}
	
	public void formalModifyKnowQuestion(int question_id, int know_id){
		questionDao.formalModifyKnowQuestion(question_id, know_id);
	}
	
	public void formalCopyKnowQuestionByQId(int old_id, int new_id){
		questionDao.formalCopyKnowQuestionByQId(old_id, new_id);
	}
	
	public void deleteSimQuestionById(int id){
		questionDao.deleteSimQuestionById(id);
	}


}
