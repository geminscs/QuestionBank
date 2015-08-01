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
	
	public List<Question> findBySubject(String subject, int pStart, int pEnd){
		return questionDao.findBySubject(subject, pStart, pEnd);
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

}
