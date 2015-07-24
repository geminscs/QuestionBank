package indi.tammy.qb.model;

public class Question {
	private int id;
	private String content;
	private String analysis;
	private String answer;
	private int type;
	private boolean isFull;
	private int hardness;
	private long submit_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isFull() {
		return isFull;
	}
	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
	public int getHardness() {
		return hardness;
	}
	public void setHardness(int hardness) {
		this.hardness = hardness;
	}
	public long getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(long submit_time) {
		this.submit_time = submit_time;
	}
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Question(int id, String content, String analysis, String answer,
			int type, boolean isFull, int hardness, long submit_time) {
		super();
		this.id = id;
		this.content = content;
		this.analysis = analysis;
		this.answer = answer;
		this.type = type;
		this.isFull = isFull;
		this.hardness = hardness;
		this.submit_time = submit_time;
	}
	
	
}
