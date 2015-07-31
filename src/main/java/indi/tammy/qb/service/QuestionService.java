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
	
	public void formalInsert(int id){
		questionDao.formalInsert(id);
	}
	
	public int getTypeId(String type_name){
		return questionDao.getTypeId(type_name);
	}
	
	public void insertKnowQuestion(int question_id, int know_id){
		questionDao.insertKnowQuestion(question_id, know_id);
	}

}
