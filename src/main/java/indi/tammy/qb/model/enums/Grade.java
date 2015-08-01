package indi.tammy.qb.model.enums;

public class Grade {
	int id;
	String grade;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Grade(int id, String grade) {
		super();
		this.id = id;
		this.grade = grade;
	}

	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
