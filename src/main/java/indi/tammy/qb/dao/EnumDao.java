package indi.tammy.qb.dao;

import indi.tammy.qb.model.enums.Area;
import indi.tammy.qb.model.enums.Grade;
import indi.tammy.qb.model.enums.QuestionType;
import indi.tammy.qb.model.enums.Subject;

import java.util.List;

public interface EnumDao {
	public List<Area> findAreaBySubjectId(int subject_id);
	
	public List<Grade> findGradeBySubjectId(int subject_id);
	
	public List<QuestionType> findQuestionTypeBySubjectId(int subject_id);
	
	public List<Subject> findSubjectBySubjectId(int subject_id);

}
