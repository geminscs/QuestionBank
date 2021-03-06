package indi.tammy.qb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.tammy.qb.model.WordBank;

public interface WordBankDao {
	public void insert(WordBank w);
	
	public void insertWrongWord(WordBank w);
	
	public void copyTempToFormal(int id);
	
	public void delete(int id);
	
	public void formalDelete(int id);
	
	public void update(WordBank w);
	
	public void formalUpdate(WordBank w);
	
	public List<WordBank> findByType(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="type")int type);

	public WordBank findById(int id);
	
	public WordBank formalFindById(int id);
	
	public WordBank formalFindByWord(String word);
	
	public List<WordBank> formalFindByParam(@Param(value="pStart")int pStart, @Param(value="pEnd")int pEnd, @Param(value="type")int type, @Param(value="grade")int grade, @Param(value="key")String key);
}
