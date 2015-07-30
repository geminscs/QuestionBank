package indi.tammy.qb.wrapper;

public class QuestionWrapper {
	private String type;
	private String content;
	private String answer;
	private String know;
	private String analysis;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getKnow() {
		return know;
	}
	public void setKnow(String know) {
		this.know = know;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public QuestionWrapper(String type, String content, String answer,
			String know, String analysis) {
		super();
		this.type = type;
		this.content = content;
		this.answer = answer;
		this.know = know;
		this.analysis = analysis;
	}
	public QuestionWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "type=" + type + "<br/>content=" + content
				+ "<br/>answer=" + answer + "<br/>know=" + know + "<br/>analysis="
				+ analysis + "<br/><br/>";
	}
	
}
