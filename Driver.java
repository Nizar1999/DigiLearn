public class Driver {
	public static void main(String[] args) {
		Reader r = new Reader();
		Course[] courses = new Course[r.countLines("DigiLearn/Metadata.txt")]; //creating array of courses
		loadData(courses);
		// data has been loaded into classes and must now be loaded into GUI
		GUI g = new GUI(courses);
		g.LoginGUI();
	}
	//a method to load all the data from the files into the classes
	public static void loadData(Course[] courses) {
		Reader r = new Reader();
		String[] courseNames = r.readMLFile("DigiLearn/Metadata.txt").split("/"); //course names in an array
		for(int i = 0; i < courseNames.length; i++) {
			String[] chapterNames = r.readMLFile("DigiLearn/" + courseNames[i] + "/Metadata.txt").split("/"); //chapter names in an array
			courses[i] = new Course(chapterNames.length,courseNames[i]); //filling array of courses
			for(int j = 0; j < chapterNames.length; j++) { //intitializing the chapters of this course
				String info = r.readFile("DigiLearn/" + courseNames[i] + "/Chapter " + (j+1) + "/Information.txt");
				String question = r.readFile("DigiLearn/" + courseNames[i] + "/Chapter " + (j+1) + "/QuestionDescription.txt");
				String[] possibleAnswers = r.readMLFile("DigiLearn/" + courseNames[i] + "/Chapter " + (j+1) + "/PossibleAnswers.txt").split("/");
				String correctAns = r.readFile("DigiLearn/" + courseNames[i] + "/Chapter " + (j+1) + "/CorrectAnswer.txt");
				String type = r.readFile("DigiLearn/" + courseNames[i] + "/Chapter " + (j+1) + "/type.txt");
				if(type.equals("Text"))
					courses[i].getChapters()[j] = new Chapter(chapterNames[j],info,new TextMCQ(question, correctAns, possibleAnswers)); //filling the questions for a chapter of a course
				else
					courses[i].getChapters()[j] = new Chapter(chapterNames[j],info,new ImageMCQ(question, correctAns, possibleAnswers));
			}
		}
	}
}