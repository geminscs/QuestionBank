package indi.tammy.qb.model.enums;

public class Subject {
	int id;
	int state;
	int flag;
	String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Subject(int id, int state, int flag, String name) {
		super();
		this.id = id;
		this.state = state;
		this.flag = flag;
		this.name = name;
	}
	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
