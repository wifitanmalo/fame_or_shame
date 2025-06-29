package fos.view;

// awt imports
import java.awt.Color;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

// package imports
import fos.service.Subject;
import fos.service.ValidationUtils;
import fos.data.GradeDataHandler;

public class GradeMenu extends JPanel
{
    // object to use the grades data
    public static final GradeDataHandler dataHandler = new GradeDataHandler();


    // subject/panel object
    private final Subject subject;


    // panel where the grades are shown
    private JPanel gradeBox;
    private JScrollPane scrollGrade;


    // text of the subject score
    private JLabel scoreText;


    // constructor
    public GradeMenu(Subject subject)
    {
        this.subject = subject;
        initializePanel();
    }


    // method to initialize the panel
    public void initializePanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FRAME_BACKGROUND);
        setBounds(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        // create the grade box
        gradeBox = new JPanel();
        scrollGrade = WindowComponent.setScrollBar(gradeBox,
                                                    155,
                                                    60,
                                                    400,
                                                    270);

        // button to back to the subject menu
        JButton backButton = WindowComponent.setButton("<",
                                                        (scrollGrade.getX()/2) - 25,
                                                        WindowComponent.yNegative(scrollGrade, -50),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(backButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.buttonEvent(backButton,
                                    () ->
                                    {
                                        // refresh the subjects in the subject box
                                        SubjectMenu.dataHandler.loadSubjects();

                                        // switch to the subject menu
                                        WindowComponent.switchPanel(this, Main.subjectMenu);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // button to calculate the total score/percentage
        JButton totalButton = WindowComponent.setButton("Total",
                                                        (scrollGrade.getX()/2) - 39,
                                                        WindowComponent.yPositive(backButton, 8),
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(totalButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        14);
        WindowComponent.buttonEvent(totalButton,
                                    () ->
                                    {
                                        // get the current subject score/percentage
                                        double score = SubjectMenu.dataHandler.getTotalScore(subject.getId(), scrollGrade);
                                        double percentage = SubjectMenu.dataHandler.getTotalPercentage(subject.getId(), scrollGrade);

                                        // verify if the evaluated percentage exceeds 100%
                                        if (ValidationUtils.exceedsLimit(percentage, 100))
                                        {
                                            WindowComponent.messageBox(scrollGrade,
                                                                    "The sum of the percentages cannot exceed 100.",
                                                                    "Limit error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                        else
                                        {
                                            // update the subject score/percentage in the database
                                            SubjectMenu.dataHandler.updateSubject(score,
                                                                                percentage,
                                                                                subject.getId(),
                                                                                scrollGrade);

                                            // update the score text of the menu
                                            setTextScore(score, percentage);

                                            // displays a message box with the remaining score to pass
                                            ValidationUtils.riskThreshold(score, percentage, scrollGrade);
                                        }
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // button to create a grade
        JButton addButton = WindowComponent.setButton("+",
                                                        backButton.getX(),
                                                        WindowComponent.yPositive(totalButton, 8),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(addButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(addButton,
                                    () ->
                                    {
                                        // show the box to set the grade name
                                        WindowComponent.gradeNameDialog("Name: ",
                                                                "Grade name",
                                                                this.subject,
                                                                0.0,
                                                                null,
                                                                scrollGrade);

                                        // load the saved grades in the database
                                        GradeMenu.dataHandler.loadGrades(subject, null, gradeBox);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // title of the name text box
        JLabel nameText = WindowComponent.setText("Grades",
                                                    scrollGrade.getX(),
                                                    scrollGrade.getY()-32,
                                                    scrollGrade.getWidth(),
                                                    26);
        WindowComponent.configureText(nameText,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.getHeight(nameText));

        // create the text box of the garde score
        scoreText = WindowComponent.setText(String.valueOf(subject.getTotalScore()),
                                            (scrollGrade.getX()/2) - 35,
                                            WindowComponent.yPositive(addButton, 6),
                                            70,
                                            20);
        WindowComponent.configureText(scoreText,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.getHeight(nameText));
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);

        // add the components to the main panel
        add(scrollGrade);
        add(nameText);
        add(backButton);
        add(totalButton);
        add(addButton);
        add(scoreText);

        // load the saved grades in the database
        dataHandler.loadGrades(subject, null, gradeBox);
    }


    // method to change the color/value of the total score
    public void setTextScore(double score, double percentage)
    {
        scoreText.setText(String.format("%.2f", score));
        if (score >= SettingsMenu.CURRENT_USER.getPassScore())
        {
            scoreText.setForeground(Color.decode("#C5EF48")); // green, you pass!
        }
        else if (ValidationUtils.getRemainingScore(score, percentage) > SettingsMenu.CURRENT_USER.getMaxScore())
        {
            scoreText.setForeground(Color.decode("#FF6865")); // red, you lose...
        }
        else
        {
            scoreText.setForeground(WindowComponent.PRESSED_BUTTON_BACKGROUND); // gray, you still have a chance
        }
        // reload the panel to show the changes
        WindowComponent.reload(scoreText);
    }


    // method to get the subject box
    public JPanel getGradeBox()
    {
        return gradeBox;
    }
}