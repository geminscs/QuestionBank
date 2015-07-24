package indi.tammy.qb.service;

import indi.tammy.qb.dao.QuestionDao;
import indi.tammy.qb.model.Question;

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
}
