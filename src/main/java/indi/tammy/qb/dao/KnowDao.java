package indi.tammy.qb.dao;

import indi.tammy.qb.model.Know;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface KnowDao {
	public List<Know> findBySubject(String subject);
	
	public List<Know> findByParam(@Param(value="subject_id")int subject_id, @Param(value="grade_id")int grade_id, @Param(value="area_id")int area_id, @Param(value="standard_id")int standard_id);
 
}
