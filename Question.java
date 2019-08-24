public abstract class Question {
	private String description;
	private String[] PA = new String[4];
	private String correctAns;
	
	public Question() {
		setDescription("");
		setCorrectAns("");
		setPA(new String[0]);
	}
	
	public Question(String d, String ca, String[] pa) {
		setDescription(d);
		setCorrectAns(ca);
		setPA(pa);
	}

	public void setDescription(String d) {
		description = d;
	}
	public void setCorrectAns(String ca) {
		correctAns = ca;
	}
	public void setPA(String[] pa) {
		 PA = pa;
	}

	public String getDescription() {
		return description;
	}
	public String getCorrectAns() {
		return correctAns;
	}
	public String[] getPA() {
		return PA;
	}
	public abstract void printQuestion();
}