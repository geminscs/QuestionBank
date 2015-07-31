package indi.tammy.qb.model;

public class Know {
	private long id;
	private int state;
	private String name;
	private int subjectid;
	private long parent;
	private int lev;
	private int kindex;
	private long kpath;
	private int isParent;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}
	public long getParent() {
		return parent;
	}
	public void setParent(long parent) {
		this.parent = parent;
	}
	public int getLev() {
		return lev;
	}
	public void setLev(int lev) {
		this.lev = lev;
	}
	public int getKindex() {
		return kindex;
	}
	public void setKindex(int kindex) {
		this.kindex = kindex;
	}
	public long getKpath() {
		return kpath;
	}
	public void setKpath(long kpath) {
		this.kpath = kpath;
	}
	public int getIsParent() {
		return isParent;
	}
	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}
	public Know() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Know(long id, int state, String name, int subjectid, long parent,
			int lev, int kindex, long kpath, int isParent) {
		super();
		this.id = id;
		this.state = state;
		this.name = name;
		this.subjectid = subjectid;
		this.parent = parent;
		this.lev = lev;
		this.kindex = kindex;
		this.kpath = kpath;
		this.isParent = isParent;
	}
	
	
}
