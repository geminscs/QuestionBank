package indi.tammy.qb.model.enums;

public class Area {
	int id;
	String name;
	int parent;
	int lev;
	
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

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getLev() {
		return lev;
	}

	public void setLev(int lev) {
		this.lev = lev;
	}

	public Area(int id, String name, int parent, int lev) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.lev = lev;
	}

	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
