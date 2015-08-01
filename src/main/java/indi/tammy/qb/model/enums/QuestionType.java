package indi.tammy.qb.model.enums;

public class QuestionType {
	int id;
	int type;
	String type_name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public QuestionType(int id, int type, String type_name) {
		super();
		this.id = id;
		this.type = type;
		this.type_name = type_name;
	}
	public QuestionType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
