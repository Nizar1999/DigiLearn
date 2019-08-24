public class Chapter {
	private String name;
	private String info;
	private Question question;
	
	public Chapter() {
		setName("");
		setInfo("");
		setQuestion(null);
	}
	
	public Chapter(String n, String i, Question q) {
		setName(n);
		setInfo(i);
		setQuestion(q);
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setInfo(String i) {
		info = i;
	}
	
	public void setQuestion(Question q) {
		question = q;
	}
	
	public String getName() {
		return name;
	}
	
	public String getInfo() {
		return info;
	}	
	
	public Question getQuestion() {
		return question;
	}
}	
	