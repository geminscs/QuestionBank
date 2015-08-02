package indi.tammy.qb.model.enums;

public class Standard {
	int id;
	String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Standard(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Standard() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
