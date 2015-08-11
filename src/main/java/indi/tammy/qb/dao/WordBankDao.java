package indi.tammy.qb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.tammy.qb.model.WordBank;

public interface WordBankDao {
	public void insert(WordBank w);
	
	public List<WordBank> findByType(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="type")int type);
}
