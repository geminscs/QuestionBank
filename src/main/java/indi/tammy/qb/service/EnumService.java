package indi.tammy.qb.service;

import java.util.List;

import indi.tammy.qb.dao.EnumDao;
import indi.tammy.qb.model.enums.Area;
import indi.tammy.qb.model.enums.Grade;
import indi.tammy.qb.model.enums.QuestionType;
import indi.tammy.qb.model.enums.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnumService {
	@Autowired
	private EnumDao enumDao;
	
	public List<Area> findAreaBySubjectId(int subject_id){
		return enumDao.findAreaBySubjectId(subject_id);
	}
	
	public List<Grade> findGradeBySubjectId(int subject_id){
		return enumDao.findGradeBySubjectId(subject_id);
	}
	
	public List<QuestionType> findQuestionTypeBySubjectId(int subject_id){
		return enumDao.findQuestionTypeBySubjectId(subject_id);
	}
	
	public List<Subject> findSubjectBySubjectId(int subject_id){
		return enumDao.findSubjectBySubjectId(subject_id);
	}
}
