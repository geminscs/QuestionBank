package indi.tammy.qb.service;

import indi.tammy.qb.dao.WordBankDao;
import indi.tammy.qb.model.WordBank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordBankService {
	@Autowired
	private WordBankDao wordBankDao;
	
	public void insert(WordBank w){
		wordBankDao.insert(w);
	}
	
	public List<WordBank> findByType(int pStart, int pEnd, int type){
		return wordBankDao.findByType(pStart, pEnd, type);
	}
	
	public void copyTempToFormal(int id){
		wordBankDao.copyTempToFormal(id);
	}
	
	public void delete(int id){
		wordBankDao.delete(id);
	}
}
