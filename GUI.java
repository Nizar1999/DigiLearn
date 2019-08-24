import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;

public class GUI {
    Course[] courses;
    Account account;
    int chapterCounter = 0;
    int difficultyCounter = 0;
    public GUI(Course[] c) {
        courses = c;
    }
	public void menuGUI() {
		JFrame menuFrame = new JFrame("DigiLearn");
        JLabel title = new JLabel();
        JLabel quote = new JLabel();
        JButton[] buttons = new JButton[courses.length];
        JButton ccButton = new JButton("Create Course");
        JLabel accName = new JLabel(account.getUserName());
        JProgressBar coPB = new JProgressBar();
        //dynamically creates buttons based on number of courses
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setIcon(new ImageIcon("C:/DigiLearn/Resources/" + courses[i].getName() + ".png"));
            buttons[i].setText(courses[i].getName().toUpperCase());
            buttons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            buttons[i].setVerticalTextPosition(SwingConstants.BOTTOM);
            buttons[i].addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {
                for(int j = 0; j < courses.length; j++) {
                    if(((JButton)e.getSource()).getText().equals(courses[j].getName().toUpperCase())) {
                        infoGUI(j);
                        menuFrame.dispose();
                    }
                }
            }  
            });  
        }
        ccButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ccGUI();
                menuFrame.dispose();
            }
        });
        menuFrame.setDefaultCloseOperation(menuFrame.EXIT_ON_CLOSE);

        title.setFont(new Font("Times New Roman", 0, 36)); 
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setText("DigiLearn - Knoweldge For All");

        quote.setFont(new Font("Dialog", 2, 14));
        quote.setText("The expert at anything was once a beginner - Helen Hayes");

        accName.setHorizontalAlignment(SwingConstants.CENTER);
        coPB.setForeground(new Color(51, 204, 0));
        coPB.setMaximum(courses.length);
        //load progress into memory
        Reader r = new Reader();
        String progress = r.readFile("DigiLearn/Resources/Accounts/" + account.getUserName() +"/"+ account.getUserName() + "-Progress.txt");
        int[] prog = new int[courses.length];
        for(int i = 0; i < courses.length; i++){
            prog[i] = Character.getNumericValue(progress.charAt(i));
            System.out.println(Character.getNumericValue(progress.charAt(i)));
        }
        account.setCcDone(prog);
        //load progress bar data
        int totalco = 0;
        for(int i = 0; i < courses.length; i++) { //calculates nb of courses done
            if(account.getCcDone()[i] >= courses[i].getChapters().length) {
                totalco++;
            }
        }
        coPB.setValue(totalco);
        coPB.setToolTipText(totalco + "/" + courses.length + " Courses Completed");
        GroupLayout layout = new GroupLayout(menuFrame.getContentPane());
        menuFrame.getContentPane().setLayout(layout);
        GroupLayout.SequentialGroup sgroup = layout.createSequentialGroup(); //create a reference for later use
            layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(ccButton)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.LEADING, sgroup)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(quote, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE)
                            .addComponent(title, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(accName, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                            .addComponent(coPB, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        GroupLayout.ParallelGroup pgroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING); //creating a reference for later use
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(ccButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(accName, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(coPB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(title, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(quote, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(pgroup)
                .addContainerGap(297, Short.MAX_VALUE))
                
        );
        //dynamically adds new courses as buttons
        for(int i = 0; i < buttons.length; i++) {
            sgroup
                .addComponent(buttons[i], GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
            ;
            pgroup
                .addComponent(buttons[i], GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
            ;
            }

        menuFrame.pack();
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }

	public void infoGUI(int courseind) {
        JLabel difflabel = new JLabel();
        JRadioButton easy = new JRadioButton();
        JRadioButton medium = new JRadioButton();
        JRadioButton hard = new JRadioButton();
        JFrame infoFrame = new JFrame("Course Info");
		JLabel introHeader = new JLabel();
        JLabel introParagraph = new JLabel();
        JButton[] buttons = new JButton[courses[courseind].getChapters().length];
        JButton backButton = new JButton();
        ButtonGroup difficultysettings = new ButtonGroup();
        JProgressBar chPB = new JProgressBar();
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Chapter " + (i+1));
            buttons[i].addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {
                Reader r = new Reader();
                for(int j = 0; j < courses.length; j++) {
                    if(((JButton)e.getSource()).getText().equals("Chapter " + (j+1))) {
                        if(easy.isSelected()){
                            //Reader r = new Reader();
                            String question = r.readFile("DigiLearn/" + courses[courseind].getName() + "/Chapter " + (j+1) + "/QuestionDescriptionEasy.txt");
                            String[] possibleAnswers = r.readMLFile("DigiLearn/" + courses[courseind].getName() + "/Chapter " + (j+1) + "/PossibleAnswersEasy.txt").split("/");
                            String correctAns = r.readFile("DigiLearn/" + courses[courseind].getName() + "/Chapter " + (j+1) + "/CorrectAnswerEasy.txt");
                            courses[courseind].getChapters()[j].getQuestion().setDescription(question);
                            courses[courseind].getChapters()[j].getQuestion().setPA(possibleAnswers);
                            courses[courseind].getChapters()[j].getQuestion().setCorrectAns(correctAns);
                        }
                        if(medium.isSelected()){
                           // Reader r = new Reader();
                            String question = r.readFile("DigiLearn/" + courses[courseind].getName() + "/Chapter " + (j+1) + "/QuestionDescriptionMedium.txt");
                            String[] possibleAnswers = r.readMLFile("DigiLearn/" + courses[courseind].getName() + "/Chapter " + (j+1) + "/PossibleAnswersMedium.txt").split("/");
                            String correctAns = r.readFile("DigiLearn/" + courses[courseind].getName() + "/Chapter " + (j+1) + "/CorrectAnswerMedium.txt");
                            courses[courseind].getChapters()[j].getQuestion().setDescription(question);
                            courses[courseind].getChapters()[j].getQuestion().setPA(possibleAnswers);
                            courses[courseind].getChapters()[j].getQuestion().setCorrectAns(correctAns);
                        }
                        if(hard.isSelected()){
                            //Reader r = new Reader();
                            String question = r.readFile("DigiLearn/" + courses[courseind].getName() + "/Chapter " + (j+1) + "/QuestionDescriptionHard.txt");
                            String[] possibleAnswers = r.readMLFile("DigiLearn/" + courses[courseind].getName() + "/Chapter " + (j+1) + "/PossibleAnswersHard.txt").split("/");
                            String correctAns = r.readFile("DigiLearn/" +courses[courseind].getName() + "/Chapter " + (j+1) + "/CorrectAnswerHard.txt");
                            courses[courseind].getChapters()[j].getQuestion().setDescription(question);
                            courses[courseind].getChapters()[j].getQuestion().setPA(possibleAnswers);
                            courses[courseind].getChapters()[j].getQuestion().setCorrectAns(correctAns);
                        }


                        chapterGUI(courseind, j);
                        infoFrame.dispose();
                    }
                }
            }  
            });  
        }
        backButton.setText("Back");

        infoFrame.setDefaultCloseOperation(infoFrame.EXIT_ON_CLOSE);

        introHeader.setFont(new Font("Times New Roman", 0, 24));
        introHeader.setText(courses[courseind].getName() + " tutorial: Learn " + courses[courseind].getName() + " with examples");

        introParagraph.setFont(new Font("Times New Roman", 0, 14));
        //use html formatting in text for multiline paragraphs
        introParagraph.setText("<html> Thank you for using DigiLearn.\nOur goal is to help spread knowledge to people of the world\nPlease choose a chapter to begin learning. Make sure to take the quiz for each chapter and try to do well. Remember, failure is first step to success.");

        backButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                menuGUI();
                infoFrame.dispose();
            }  
        });
        difflabel.setHorizontalAlignment(SwingConstants.CENTER);
        difflabel.setText("Difficulty");
        easy.setText("Easy");
        medium.setText("Medium");
        hard.setText("Hard");
        easy.setSelected(true);
        difficultysettings.add(easy);
        difficultysettings.add(medium);
        difficultysettings.add(hard);

        chPB.setForeground(new Color(51, 204, 0));
        chPB.setMaximum(courses[courseind].getChapters().length);
        chPB.setValue(account.getCcDone()[courseind]);
        chPB.setToolTipText(chPB.getValue() + "/" + courses[courseind].getChapters().length + " Chapters Completed");

        GroupLayout layout = new GroupLayout(infoFrame.getContentPane());
        infoFrame.getContentPane().setLayout(layout);
        GroupLayout.ParallelGroup pgroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(backButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(pgroup
                            .addComponent(chPB, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(107, 107, 107)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(introHeader)
                            .addComponent(introParagraph, GroupLayout.PREFERRED_SIZE, 379, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(140, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(hard)
                    .addComponent(medium)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(easy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(difflabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        GroupLayout.SequentialGroup sgroup = layout.createSequentialGroup();
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(chPB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(sgroup)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(introHeader, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(introParagraph, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(difflabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(easy)
                .addGap(18, 18, 18)
                .addComponent(medium)
                .addGap(18, 18, 18)
                .addComponent(hard)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        for(int i = 0; i < buttons.length; i++) {
            pgroup.addComponent(buttons[i], GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE);
            sgroup.addComponent(buttons[i])
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }
        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    public void LoginGUI() {
        ArrayList<Account> Accounts = new ArrayList<Account>();
        JFrame loginFrame = new JFrame("DigiLearn - Login Screen");
        Label digiLearn = new Label();
        JLabel usernameLabel = new JLabel();
        JLabel passwordLabel = new JLabel();
        JButton loginButton = new JButton();
        JButton signupButton = new JButton();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        loginFrame.setDefaultCloseOperation(loginFrame.EXIT_ON_CLOSE);
        
        digiLearn.setFont(new Font("Arial Black", 1, 36));
        digiLearn.setText("DigiLearn");
        usernameLabel.setText("Username :");
        passwordLabel.setText("Password :");

        loginButton.setBackground(new Color(153, 255, 153));
        loginButton.setFont(new Font("Dialog", 1, 11));
        loginButton.setText("Login");
        loginButton.setToolTipText("logs you in to your account");
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(evt.getSource()==loginButton) {
                    try {
                    Reader accounts = new Reader();
                    String path = "DigiLearn\\Resources\\Accounts\\" + usernameField.getText() +"\\"+ usernameField.getText() + ".txt";
                    accounts.openFile(path);
                    int size=accounts.PassSize();
                    accounts.closeFile();
                    accounts.openFile(path);
                    Account temp = new Account(usernameField.getText(), accounts.readPass(size),courses.length);
                    accounts.closeFile();

                    if(temp.getPassword().Authenticate(passwordField.getPassword())) {
                        JOptionPane.showMessageDialog(null, "Welcome " + temp.getUserName()+ ", you have logged in");
                        account = temp;
                        menuGUI();
                        loginFrame.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Failed to login ,check your password");
                    }
                    catch(Exception e) {
                        JOptionPane.showMessageDialog(null, "Account does not exist");
                    }
                }
            }
        });

        signupButton.setBackground(new Color(204, 255, 51));
        signupButton.setFont(new Font("Dialog", 1, 11));
        signupButton.setText("Sign up");
        signupButton.setToolTipText("Creates an account with the above details");
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(evt.getSource() == signupButton)
                {
                    if(passwordField.getPassword().length > 6) {
                        Account newAcc = new Account(usernameField.getText(),passwordField.getPassword(),courses.length);
                        account = newAcc;
                        Account tmp = new Account(usernameField.getText(),passwordField.getPassword(),courses.length);
                        File x = new File("C:\\DigiLearn\\Resources\\Accounts\\" + account.getUserName());
                        x.mkdir();
                        File y = new File("C:\\DigiLearn\\Resources\\Accounts\\" + account.getUserName() +"\\"+ account.getUserName() +".txt");
                        if(!y.exists())
                        {
                            Writer Info = new Writer();
                            Info.openFile("C:\\DigiLearn\\Resources\\Accounts\\" + account.getUserName() + "\\" + account.getUserName() + ".txt");
                            Info.addRecords(account.getUserName(), account.getPassword().getPass());
                            Info.closeFile();
                            account = tmp;
                            Info.createFile("\\DigiLearn\\Resources\\Accounts\\" + account.getUserName() +"\\"+ account.getUserName() + "-Progress.txt");
                            String coursenb = "";
                            for(int i = 0; i < courses.length; i++) {
                                coursenb += "0";
                            }
                            Info.updateTextFiles("\\DigiLearn\\Resources\\Accounts\\" + account.getUserName() +"\\"+ account.getUserName() + "-Progress.txt", coursenb);
                            JOptionPane.showMessageDialog(null,usernameField.getText() + " you have successfully signed up");
                            loginFrame.dispose();
                            menuGUI();
                        }
                    else
                        JOptionPane.showMessageDialog(null, "Account already exists");
                    }      
                else 
                    JOptionPane.showMessageDialog(null, "Password too small. (should be greater than 6 characters)");
                }
            }});
        GroupLayout layout = new GroupLayout(loginFrame.getContentPane());
        loginFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameField)
                    .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
                .addGap(0, 439, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(481, 481, 481)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(signupButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loginButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(426, 426, 426)
                .addComponent(digiLearn, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(digiLearn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(loginButton)
                .addGap(28, 28, 28)
                .addComponent(signupButton)
                .addGap(262, 262, 262))
        );
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    public void chapterGUI(int courseind, int chapterind) {
        JFrame chapterFrame = new JFrame();
        JLabel chapter = new JLabel();
        JLabel info = new JLabel();
        JButton quiz = new JButton();
        JButton back = new JButton();

        chapterFrame.setDefaultCloseOperation(chapterFrame.EXIT_ON_CLOSE);

        chapter.setFont(new Font("Times New Roman", 0, 36));
        chapter.setText(courses[courseind].getChapters()[chapterind].getName());
        info.setFont(new Font("Times New Roman", 0, 14));
        info.setText(courses[courseind].getChapters()[chapterind].getInfo());
        info.setVerticalAlignment(SwingConstants.TOP);
        info.setVerticalTextPosition(SwingConstants.TOP);
        quiz.setText("Quiz");
        back.setText("Back");
        back.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                infoGUI(courseind);
                chapterFrame.dispose();
            }  
        });
        quiz.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                quizGUI(courseind,chapterind);
                chapterFrame.dispose();
            }  
        });   
        GroupLayout layout = new GroupLayout(chapterFrame.getContentPane());
        chapterFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(chapter, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addComponent(info, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(285, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(back)
                .addGap(18, 18, 18)
                .addComponent(quiz)
                .addGap(71, 71, 71))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(chapter, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(info, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(quiz)
                    .addComponent(back))
                .addGap(53, 53, 53))
        );
        chapterFrame.pack();
        chapterFrame.setLocationRelativeTo(null);
        chapterFrame.setVisible(true);
    }
    public void quizGUI(int courseind, int chapterind) {
        JFrame quizFrame = new JFrame();
        JLabel question = new JLabel();
        JRadioButton[] buttons = new JRadioButton[courses[courseind].getChapters()[chapterind].getQuestion().getPA().length];   
        JButton check = new JButton();
        ButtonGroup answers = new ButtonGroup();
        String correctAnswer = courses[courseind].getChapters()[chapterind].getQuestion().getCorrectAns();
        quizFrame.setDefaultCloseOperation(quizFrame.EXIT_ON_CLOSE);

        question.setFont(new Font("Times New Roman", 0, 18));
        question.setText(courses[courseind].getChapters()[chapterind].getQuestion().getDescription());
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JRadioButton(courses[courseind].getChapters()[chapterind].getQuestion().getPA()[i]);
            if(courses[courseind].getChapters()[chapterind].getQuestion() instanceof ImageMCQ) {
                buttons[i].setIcon(new ImageIcon("C:/DigiLearn/" + courses[courseind].getName() + "/Chapter " + (chapterind + 1)+ "/" + buttons[i].getText() + ".png"));
                //buttons[i].setForeground(new Color(255, 255, 255, 0)); to make text invis
            }
            answers.add(buttons[i]);
        }

        check.setText("Check Answer");
        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < buttons.length; i++)
                {
                    if(buttons[i].isSelected()) {
                        if(buttons[i].getText().equals(correctAnswer)) { //check if answer is correct
                            JOptionPane.showMessageDialog(null,"You have answered correctly!");
                        }
                        else
                                JOptionPane.showMessageDialog(null, "You have answered incorrectly.\nCorrect Answer: " + correctAnswer + "\nYou answered: " + buttons[i].getText());
                    }
                }
                account.ChapterDone(courseind);
                Writer save = new Writer();
                save.openFile("C:\\DigiLearn\\Resources\\Accounts\\" + account.getUserName() +"\\"+ account.getUserName() + "-Progress.txt");
                save.saveProgress(account.getCcDone());
                save.closeFile();
                if(chapterind < courses[courseind].getChapters().length - 1) { //if not final chapter yet go to next chapter
                    int chapterindex = chapterind + 1;
                    chapterGUI(courseind,chapterindex);
                    quizFrame.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "All chapters completed"); //if final chapter return to chapter info page
                    infoGUI(courseind);
                    quizFrame.dispose();
                }
            }
        });

        GroupLayout layout = new GroupLayout(quizFrame.getContentPane());
        quizFrame.getContentPane().setLayout(layout);
        GroupLayout.ParallelGroup pgroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(check)
                    .addGroup(pgroup
                        .addComponent(question, GroupLayout.PREFERRED_SIZE, 769, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        GroupLayout.SequentialGroup sgroup = layout.createSequentialGroup();
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sgroup
                .addGap(66, 66, 66)
                .addComponent(question, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        for(int i = 0;i < buttons.length; i++) {
            pgroup.addComponent(buttons[i], GroupLayout.PREFERRED_SIZE, 769, GroupLayout.PREFERRED_SIZE);
            sgroup.addComponent(buttons[i], GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }
        sgroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE) //add gap and check
              .addComponent(check)
              .addGap(39, 39, 39);
        quizFrame.pack();
        quizFrame.setLocationRelativeTo(null);
        quizFrame.setVisible(true);
    }
    public void ccGUI() {
        JFrame ccFrame = new JFrame();
        JPanel ccPanel = new JPanel();
        JLabel title = new JLabel();
        JLabel coNameLabel = new JLabel();
        JLabel chnblabel = new JLabel();
        JLabel copiclabel = new JLabel();
        JLabel chnamelabel = new JLabel();
        JLabel chlabel = new JLabel();
        JLabel qtypelabel = new JLabel();
        JLabel paLabel = new JLabel();
        JLabel qlabel = new JLabel();
        JLabel difficultylabel = new JLabel();
        JButton coImageBrowser = new JButton();
        JButton nextButton = new JButton();
        JButton backButton = new JButton();
        JTextField coNameTF = new JTextField();
        JTextField chNameTF = new JTextField();
        JTextField qTF = new JTextField();
        JRadioButton imageRB = new JRadioButton();
        JRadioButton textRB = new JRadioButton();
        ButtonGroup mcqType = new ButtonGroup();
        JSlider chnbslider = new JSlider();

        JButton[] browseButtons = new JButton[4];
        JCheckBox[] correctBoxes = new JCheckBox[4];
        JTextField[] paTF = new JTextField[4];
        JLabel infoLabel = new JLabel();
        JTextField infoTF = new JTextField();
        ccFrame.setDefaultCloseOperation(ccFrame.EXIT_ON_CLOSE);

        title.setFont(new Font("Yu Gothic", 0, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setText("COURSE CREATOR");

        coNameLabel.setFont(new Font("Verdana", 0, 14));
        coNameLabel.setText("Course Name: ");

        chnbslider.setMajorTickSpacing(1);
        chnbslider.setMaximum(10);
        chnbslider.setMinimum(1);
        chnbslider.setPaintLabels(true);
        chnbslider.setSnapToTicks(true);
        chnbslider.setValue(5);

        chnblabel.setFont(new Font("Verdana", 0, 14));
        chnblabel.setText("Number Of Chapters: ");

        copiclabel.setFont(new Font("Verdana", 0, 14));
        copiclabel.setText("Course Picture: "); 

        backButton.setText("Back");

        chnamelabel.setText("Chapter Name:");

        chlabel.setFont(new Font("Verdana", 0, 12));
        chlabel.setText("Chapter 1");

        imageRB.setText("Image");
        textRB.setText("Text");

        mcqType.add(imageRB);
        mcqType.add(textRB);

        qtypelabel.setText("MCQ Type:");

        nextButton.setText("Next");

        paLabel.setText("Possible Answers: ");

        qlabel.setText("Question:");
        infoLabel.setText("Information:");

        difficultylabel.setFont(new Font("Verdana", 0, 12));
        difficultylabel.setText("Easy");

        coImageBrowser.setText("Browse");
        textRB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i < browseButtons.length; i++) {
                            browseButtons[i].setEnabled(false);
                        }
            }
        });
        imageRB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i < browseButtons.length; i++) {
                            browseButtons[i].setEnabled(true);
                        }
            }
        });
        coImageBrowser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Choose a picture: ");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY); 
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isFile()) {
                        Writer w = new Writer();
                        try {
                            File destFile = new File("C:/DigiLearn/Resources/" + coNameTF.getText() + ".png");
                            w.copyFile(jfc.getSelectedFile(),destFile);
                        } catch (IOException ex) {
                        System.out.println("Error, could not copy images.");
                        }
                    }
                }
            }
        });
        for(int i = 0; i < browseButtons.length; i++) {//creates the buttons and check boxes
            browseButtons[i] = new JButton("Browse");
            correctBoxes[i] = new JCheckBox("Correct Answer");
            paTF[i] = new JTextField();
        }

        //BrowseButtons Action Listeners
        browseButtons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Choose a picture: ");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isFile()) {
                        Writer w = new Writer();
                        try {
                            File temp = new File("C:/DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1));
                            temp.mkdirs();//creating folder/directory
                            File destFile = new File(temp + "/" + paTF[0].getText() + ".png");//creating a file of extension png
                            w.copyFile(jfc.getSelectedFile(),destFile);//Coping source image to destination path
                        } catch (IOException ex) {
                        System.out.println("Error, could not copy images.");
                        }
                    }
                }
            }
        });
        browseButtons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Choose a picture: ");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isFile()) {
                        Writer w = new Writer();
                        try {
                            File temp = new File("C:/DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1));
                            temp.mkdirs();
                            File destFile = new File(temp + "/" + paTF[1].getText() + ".png");
                            w.copyFile(jfc.getSelectedFile(),destFile);
                        } catch (IOException ex) {
                        System.out.println("Error, could not copy images.");
                        }
                    }
                }
            }
        });
        browseButtons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Choose a picture: ");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isFile()) {
                        Writer w = new Writer();
                        try {
                            File temp = new File("C:/DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1));
                            temp.mkdirs();
                            File destFile = new File(temp + "/" + paTF[2].getText() + ".png");
                            w.copyFile(jfc.getSelectedFile(),destFile);
                        } catch (IOException ex) {
                        System.out.println("Error, could not copy images.");
                        }
                    }
                }
            }
        });
        browseButtons[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Choose a picture: ");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isFile()) {
                        Writer w = new Writer();
                        try {
                            File temp = new File("C:/DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1));
                            temp.mkdirs();
                            File destFile = new File(temp + "/" + paTF[3].getText() + ".png");
                            w.copyFile(jfc.getSelectedFile(),destFile);
                        } catch (IOException ex) {
                        System.out.println("Error, could not copy images.");
                        }
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuGUI();
                ccFrame.dispose();
                chapterCounter = 0;//clear the frame and reset the chaptercounter
            }
        });
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String diff = "";
                Writer w = new Writer();
                //first time pressing next execute code below
                if(chapterCounter == 0 && difficultyCounter == 0) {
                    w.updateTextFiles("DigiLearn/Metadata.txt", coNameTF.getText());
                    //creates chapter folders
                    for(int i = 0; i < chnbslider.getValue(); i++) {
                        File chFolders = new File("C:/DigiLearn/" + coNameTF.getText() + "/Chapter " + (i + 1));
                        chFolders.mkdirs();
                    }
                    //creates metadata file that holds the chapter names
                    w.createFile("DigiLearn/" + coNameTF.getText() + "/Metadata.txt");
                    w.writeTextFiles("DigiLearn/" + coNameTF.getText() + "/Metadata.txt", chNameTF.getText());
                    w.createFile("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/Information" +  ".txt");
                    w.writeTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/Information" + ".txt", "<html> "+infoTF.getText());
                    //disable some components from being edited
                    coNameTF.setEditable(false);
                    coImageBrowser.setEnabled(false);//disables 
                    chnbslider.setEnabled(false);
                    chNameTF.setEditable(false);
                    infoTF.setEditable(false);
                }

                else {
                    if(difficultyCounter == 0) {
                        w.updateTextFiles("DigiLearn/" + coNameTF.getText() + "/Metadata.txt", chNameTF.getText());
                        w.createFile("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/Information" +  ".txt");
                        w.writeTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/Information" + ".txt", "<html> "+infoTF.getText());
                        chNameTF.setEditable(false);
                        infoTF.setEditable(false);
                    }
                }

                switch(difficultyCounter) {
                    case 0:
                            diff = "Easy";
                            break;
                    case 1:
                            diff = "Medium";
                            break;
                    case 2:
                            diff = "Hard";
                            break;
                    default:
                }

                if(imageRB.isSelected()){
                    w.createFile("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/Type" + diff + ".txt");
                    w.writeTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/Type" + diff + ".txt", "Image");
                    imageRB.setSelected(false);
                }
                else {
                    w.createFile("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/Type" + diff + ".txt");
                    w.writeTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/Type" + diff + ".txt", "Text");
                    textRB.setSelected(false);
                }

                w.createFile("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/QuestionDescription" + diff + ".txt");
                w.writeTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/QuestionDescription" + diff + ".txt", qTF.getText());

                for(int i = 0; i < paTF.length; i++) {
                    int correct = 0;
                    if(i == 0) {
                        w.createFile("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/PossibleAnswers" + diff + ".txt");
                        w.writeTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/PossibleAnswers" + diff + ".txt", paTF[i].getText());
                    }
                    else {
                    w.updateTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/PossibleAnswers" + diff + ".txt", paTF[i].getText());
                    }
                    // First iteration we create the file for the first correct answer then if there is more than one correct answer we update the txt file by appending the new correct answer 
                    
                    if(correctBoxes[i].isSelected()){
                        if(correct == 0) {
                            w.createFile("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/CorrectAnswer" + diff + ".txt");
                            w.writeTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/CorrectAnswer" + diff + ".txt", paTF[i].getText());
                        }
                        else {
                            w.updateTextFiles("DigiLearn/" + coNameTF.getText() + "/Chapter " + (chapterCounter + 1) + "/CorrectAnswer" + diff + ".txt", paTF[i].getText());
                        }
                        correct++;
                    }
                    paTF[i].setText("");
                    correctBoxes[i].setSelected(false);
                }
                if(difficultyCounter == 2) {
                    chNameTF.setEditable(true);
                    infoTF.setEditable(true);
                    chapterCounter++;
                    chlabel.setText("Chapter " + (chapterCounter + 1));
                    //leave frame if entire course is done
                    if(chapterCounter == (chnbslider.getValue())) {
                    JOptionPane.showMessageDialog(null, "Course created successfully.");
                    menuGUI();
                    ccFrame.dispose();
                    }

                    difficultyCounter = 0;
                }
                else {
                    difficultyCounter++;
                }
                switch(difficultyCounter) {
                    case 0:
                            difficultylabel.setText("Easy");
                            break;
                    case 1:
                            difficultylabel.setText("Medium");
                            break;
                    case 2:
                            difficultylabel.setText("Hard");
                            break;
                    default:
                }
            }
        });

        GroupLayout ccPanelLayout = new GroupLayout(ccPanel);
        ccPanel.setLayout(ccPanelLayout);
        GroupLayout.ParallelGroup hpanelpgroup = ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.ParallelGroup hpanelpgroup2 = ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup hpanelsgroup = ccPanelLayout.createSequentialGroup();
        ccPanelLayout.setHorizontalGroup(
            ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(ccPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(ccPanelLayout.createSequentialGroup()
                        .addComponent(chlabel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(difficultylabel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ccPanelLayout.createSequentialGroup()
                        .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(chnamelabel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                            .addComponent(qtypelabel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                            .addComponent(paLabel))
                        .addGap(18, 18, 18)
                        .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(hpanelsgroup)
                            .addGroup(ccPanelLayout.createSequentialGroup()
                                .addComponent(imageRB)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textRB)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(ccPanelLayout.createSequentialGroup()
                                .addGroup(hpanelpgroup2)
                                .addGap(74, 74, 74)
                                .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(ccPanelLayout.createSequentialGroup()
                                        .addComponent(qlabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(qTF))
                                    .addGroup(ccPanelLayout.createSequentialGroup()
                                        .addGroup(hpanelpgroup)
                                        .addGap(0, 185, Short.MAX_VALUE)))))
                .addGap(19, 19, 19))
            .addGroup(ccPanelLayout.createSequentialGroup()
                        .addComponent(infoLabel)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        GroupLayout.SequentialGroup vpanelsgroup = ccPanelLayout.createSequentialGroup();
        ccPanelLayout.setVerticalGroup(
            ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(vpanelsgroup
                .addContainerGap()
                .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(chlabel)
                    .addComponent(difficultylabel))
                .addGap(9, 9, 9)
                .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(chnamelabel)
                    .addComponent(chNameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(qlabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addComponent(qTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(infoLabel)
                    .addComponent(infoTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(imageRB)
                    .addComponent(textRB)
                    .addComponent(qtypelabel)))
        );
        //adding GUI component arrays to the panel
        for(int i = 0; i < 4; i++){
            hpanelpgroup.addGroup(ccPanelLayout.createSequentialGroup()
                        .addComponent(browseButtons[i], GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(correctBoxes[i]));
            switch(i) {
                case 0:
                        hpanelpgroup2.addComponent(paTF[i], GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
                                     .addComponent(infoTF);
                        vpanelsgroup.addGap(16, 16, 16)
                                    .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(paLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(paTF[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(browseButtons[i])
                                        .addComponent(correctBoxes[i]));
                        break;
                case 1:
                        hpanelpgroup2.addComponent(paTF[i], GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE);
                        vpanelsgroup.addGap(16, 16, 16)
                                    .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(paTF[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(browseButtons[i])
                                        .addComponent(correctBoxes[i]));
                        break;
                case 2:             
                        hpanelpgroup2.addComponent(paTF[i], GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
                                     .addComponent(chNameTF, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE);
                        vpanelsgroup.addGap(16, 16, 16)
                                    .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(paTF[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(browseButtons[i])
                                        .addComponent(correctBoxes[i]));
                        break;
                case 3:
                        hpanelsgroup.addComponent(paTF[i], GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nextButton);
                        vpanelsgroup.addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(ccPanelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(ccPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(paTF[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)//
                                            .addComponent(browseButtons[i])//
                                            .addComponent(correctBoxes[i]))//
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(GroupLayout.Alignment.TRAILING, ccPanelLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nextButton)
                                    .addGap(35, 35, 35)));
                        break;
                default:
            }
        }

        GroupLayout layout = new GroupLayout(ccFrame.getContentPane());
        ccFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ccPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(backButton)
                                .addGap(158, 158, 158)
                                .addComponent(title, GroupLayout.PREFERRED_SIZE, 381, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 102, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(chnblabel)
                                    .addComponent(coNameLabel, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(chnbslider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(coNameTF, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(copiclabel, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addComponent(coImageBrowser, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(title, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(coNameLabel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                    .addComponent(coNameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(copiclabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addComponent(coImageBrowser))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(chnblabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(chnbslider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ccPanel, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        ccFrame.pack();
        ccFrame.setLocationRelativeTo(null);
        ccFrame.setVisible(true);
    }
}