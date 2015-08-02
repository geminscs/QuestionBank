package indi.tammy.qb.service;

import java.util.List;

import indi.tammy.qb.dao.KnowDao;
import indi.tammy.qb.model.Know;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowService {
	@Autowired
	private KnowDao knowDao;
	
	public List<Know> findBySubject(String subject){
		return knowDao.findBySubject(subject);
	}
	
	public List<Know> findByParam(int subject_id, int grade_id, int area_id, int standard_id){
		return knowDao.findByParam(subject_id, grade_id, area_id, standard_id);
	}

}
