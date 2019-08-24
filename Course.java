public class Course {
	private Chapter[] chapters;
	private String name;
	
	public Course() {
		setChapters(new Chapter[0]);
		setName("");
	}
	
	public Course(int x, String n) {
		setChapters(new Chapter[x]);
		setName(n);
	}

	public void setChapters(Chapter[] c) {
		chapters = c;
	}

	public void setName(String n) {
		name = n;
	}

	public Chapter[] getChapters() {
		return chapters;
	}
	
	public String getName() {
		return name;
	}
}
	