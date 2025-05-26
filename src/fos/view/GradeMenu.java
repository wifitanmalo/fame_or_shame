package fos.view;

// awt imports
import java.awt.Color;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// package imports
import fos.service.Grade;
import fos.service.Subject;
import fos.service.ValidationUtils;
import fos.data.GradeFileHandler;

public class GradeMenu extends JPanel
{
    // object to use the grades file
    public static final GradeFileHandler fileHandler = new GradeFileHandler();

    // subject object
    private Subject subject;

    // panel where the grades are added
    private static JPanel gradeBox;

    // imported label
    private static JLabel scoreText;

    // constructor
    public GradeMenu(Subject subject)
    {
        this.subject = subject;
        initializePanel();
    }

    // method to initialize the main panel
    public void initializePanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FRAME_BACKGROUND);
        setBounds(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        // create the grade box
        gradeBox = new JPanel();
        JScrollPane scrollGrade = WindowComponent.set_scroll_bar(gradeBox,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // button to back to the subject menu
        JButton backButton = WindowComponent.set_button("Back",
                                                        50,
                                                        WindowComponent.negative_y(scrollGrade, -50),
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(backButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.button_event(backButton,
                                    () ->
                                    {
                                        Main.subjectMenu.refreshSubjects();
                                        WindowComponent.switch_panel(this, Main.subjectMenu);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // button to calculate the total score/percentage
        JButton totalButton = WindowComponent.set_button("Total",
                                                            50,
                                                            WindowComponent.positive_y(backButton, 20),
                                                            78,
                                                            50,
                                                            WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(totalButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        14);
        WindowComponent.button_event(totalButton,
                                    () ->
                                    {
                                        if(ValidationUtils.gradeValidation(subject, this))
                                        {
                                            setTextScore(subject.getTotalScore());
                                            SubjectMenu.fileHandler.updateSubject(subject,
                                                                            subject.getTotalScore(),
                                                                            subject.getTotalEvaluated());
                                            WindowComponent.reload(Main.subjectMenu);
                                        }
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // button to create a grade
        JButton addButton = WindowComponent.set_button("+",
                                                        backButton.getX(),
                                                        WindowComponent.positive_y(totalButton, 20),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(addButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.button_event(addButton,
                                    () ->
                                    {
                                        Grade newGrade = new Grade(subject.getId(), 0,0);
                                        subject.createGrade(newGrade);
                                        fileHandler.updateGrade(subject);

                                        SubjectPanel.gradeMenu.refreshGrades(this.subject);
                                        WindowComponent.reload(gradeBox);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // subject name text settings
        JLabel nameText = WindowComponent.set_text("Grades",
                                                    scrollGrade.getX(),
                                                    scrollGrade.getY()-32,
                                                    scrollGrade.getWidth(),
                                                    26);
        WindowComponent.configure_text(nameText,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.get_height(nameText));

        // text where the total score is shown
        scoreText = WindowComponent.set_text(String.valueOf(subject.getTotalScore()),
                                                backButton.getX(),
                                                WindowComponent.positive_y(addButton, 14),
                                                80,
                                                20);
        WindowComponent.configure_text(scoreText,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.get_height(nameText));

        // add the components to the main panel
        add(scrollGrade);
        add(nameText);
        add(backButton);
        add(totalButton);
        add(addButton);
        add(scoreText);

        // load the saved grades
        fileHandler.loadGrades();
    }

    // method to load the grades
    public void refreshGrades(Subject currentSubject)
    {
        gradeBox.removeAll();
        this.subject = currentSubject;
        for(Grade grade : subject.getGradesList())
        {
            // set the grade values in the text fields
            GradePanel currentPanel = new GradePanel(subject, grade);
            currentPanel.setScoreText(String.valueOf(grade.getScore()));
            currentPanel.setPercentageText(String.valueOf(grade.getPercentage()));

            // reload the panel to show the changes
            WindowComponent.reload(gradeBox);
        }
    }

    // method to change the text and color of the total score
    public void setTextScore(double score)
    {
        score = SubjectMenu.twoDecimals(score);
        scoreText.setText(String.valueOf(score));
        if(score >= Subject.PASSING_SCORE)
        {
            scoreText.setForeground(Color.decode("#C5EF48"));
        }
        else
        {
            scoreText.setForeground(Color.decode("#FF6865"));
        }
        WindowComponent.reload(scoreText);
    }

    // method to get the subject box
    public static JPanel getGradeBox()
    {
        return gradeBox;
    }
}