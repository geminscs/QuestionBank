package indi.tammy.qb.model;

public class WordBank {
	int id;
	String word;
	int type;
	int grade;
	String phonetic;
	String explain;
	
	String type_name;
	int wrong_type;
	String wrong_type_name;
	String wrong_message;
	int total;
	
	

	public int getWrong_type() {
		return wrong_type;
	}

	public void setWrong_type(int wrong_type) {
		this.wrong_type = wrong_type;
	}

	public String getWrong_type_name() {
		return wrong_type_name;
	}

	public void setWrong_type_name(String wrong_type_name) {
		this.wrong_type_name = wrong_type_name;
	}

	public String getWrong_message() {
		return wrong_message;
	}

	public void setWrong_message(String wrong_message) {
		this.wrong_message = wrong_message;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getPhonetic() {
		return phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}


	public WordBank(int id, String word, int type, int grade, String phonetic,
			String explain, String type_name, int wrong_type,
			String wrong_type_name, String wrong_message, int total) {
		super();
		this.id = id;
		this.word = word;
		this.type = type;
		this.grade = grade;
		this.phonetic = phonetic;
		this.explain = explain;
		this.type_name = type_name;
		this.wrong_type = wrong_type;
		this.wrong_type_name = wrong_type_name;
		this.wrong_message = wrong_message;
		this.total = total;
	}

	public WordBank() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
