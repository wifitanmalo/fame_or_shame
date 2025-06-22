package fos.view;

// awt imports
import java.awt.Color;
import java.awt.Dimension;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// package imports
import fos.service.Grade;
import fos.service.Subject;
import fos.service.ValidationUtils;

public class SubjectPanel extends JPanel
{
    // subject object
    private final Subject subject;

    // object of the grade menu
    private GradeMenu gradeMenu;

    // performance values texts
    private JLabel subjectName, totalScore, totalEvaluated;

    // buttons of the panel
    private JButton gradeButton, deleteButton;

    // constructor
    public SubjectPanel(Subject subject)
    {
        this.subject = subject;
        subjectPanel();
    }

    // method to add a subject
    public void subjectPanel()
    {
        setPreferredSize(new Dimension(400, 80));
        setBackground(WindowComponent.BUTTON_BACKGROUND);
        setLayout(null);

        // create the grade menu panel
        gradeMenu = new GradeMenu(subject);
        gradeMenu.setVisible(false);
        WindowComponent.getContainer().add(gradeMenu);

        // name of the subject
        subjectName = WindowComponent.setText(subject.getId() + " " + subject.getName(),
                                                        10,
                                                        10,
                                                        241,
                                                        18);

        WindowComponent.configureText(subjectName,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        nameSize(subjectName));

        // text of the total score
        totalScore = WindowComponent.setText(("Total score: 0.0"),
                                                10,
                                                WindowComponent.yNegative(subjectName, 2),
                                                350,
                                                16);
        WindowComponent.configureText(totalScore,
                                        Color.lightGray,
                                        3,
                                        WindowComponent.getHeight(totalScore));

        // text of the total percentage evaluated
        totalEvaluated = WindowComponent.setText(("Total evaluated: 0.0%"),
                                                    10,
                                                    WindowComponent.yNegative(totalScore, 2),
                                                    350,
                                                    16);
        WindowComponent.configureText(totalEvaluated,
                                        Color.lightGray,
                                        3,
                                        WindowComponent.getHeight(totalEvaluated));

        // create the button to delete the subject
        deleteButton = WindowComponent.setButton("x",
                                                            320,
                                                            15,
                                                            50,
                                                            50,
                                                            WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configureText(deleteButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);

        // button to enter on the grades menu
        gradeButton = WindowComponent.setButton("+",
                                                            (deleteButton.getX()-60),
                                                            deleteButton.getY(),
                                                            50,
                                                            50,
                                                            WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configureText(gradeButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);

        // add the components on the panel
        add(subjectName);
        add(totalScore);
        add(totalEvaluated);
        add(deleteButton);
        add(gradeButton);

        // add the subject on the subject box
        SubjectMenu.subjectBox.add(this);

        // reload the panel to show the changes
        WindowComponent.reload(SubjectMenu.subjectBox);
    }

    // method to set the subject name size based in its length
    public int nameSize(JLabel subject_name)
    {
        if((subject.getName().length()>24) && (subject.getName().length()<34))
        {
            return 12;
        }
        else if((subject.getName().length()>=34) && (subject.getName().length() <= 50))
        {
            return 8;
        }
        return WindowComponent.getHeight(subject_name);
    }

    // method to change the name/score/evaluated text colors
    public void panelColor(Color panelColor, Color nameColor, Color scoreEvaluatedColor, Color buttonColor)
    {
        // change the color of the panel
        this.setBackground(panelColor);
        // change the color of the subject name
        subjectName.setForeground(nameColor);
        // change the color of the total score/evaluated
        totalScore.setForeground(scoreEvaluatedColor);
        totalEvaluated.setForeground(scoreEvaluatedColor);
        // change the default color of the buttons
        buttonColor(buttonColor);
    }

    // method to change the color of the buttons to the same as the score/percentage text
    public void buttonColor(Color color)
    {
        // event to enter the grade menu
        gradeButton.setBackground(color);
        WindowComponent.buttonEvent(gradeButton,
                                    () ->
                                    {
                                        // load the panels of the grades in the grades box
                                        GradeMenu.dataHandler.loadGrades(subject, gradeMenu.getGradeBox());
                                        // update the subject score on the grade menu
                                        gradeMenu.setTextScore(subject.getTotalScore(), subject.getTotalEvaluated());
                                        // switch to the grade menu
                                        WindowComponent.switchPanel(Main.subjectMenu, gradeMenu);
                                    },
                                    color,
                                    Color.decode("#B6E036"),
                                    Color.decode("#9DD100"));

        // event to delete a subject
        deleteButton.setBackground(color);
        WindowComponent.buttonEvent(deleteButton,
                                    () ->
                                    {
                                        int choice = JOptionPane.showConfirmDialog(SubjectMenu.subjectBox,
                                                                                "You want to delete this subject?",
                                                                                "Delete subject",
                                                                                JOptionPane.YES_NO_OPTION);
                                        if(choice == JOptionPane.YES_OPTION)
                                        {
                                            // remove the subject from the database
                                            SubjectMenu.dataHandler.deleteSubject(subject.getId());
                                            // remove all the grades of the subject from the database
                                            GradeMenu.dataHandler.deleteAll(subject.getId());
                                            // reload the subjects to show the changes
                                            SubjectMenu.dataHandler.loadSubjects();
                                        }
                                    },
                                    color,
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));
    }

    // method for change the panel colors based in the risk threshold
    public void performanceColor(double currentScore,
                                 double evaluatedPercentage)
    {
        double remainingGrade = ValidationUtils.getRemainingScore(currentScore, evaluatedPercentage);
        if (currentScore >= SettingsMenu.CURRENT_USER.getPassScore()) // the subject has been passed
        {
            this.panelColor(Color.decode("#C5EF48"),
                            Color.decode("#64820A"),
                            Color.decode("#7D9E19"),
                            Color.decode("#7D9E19"));
        }
        else if (remainingGrade > SettingsMenu.CURRENT_USER.getMaxScore()) // the subject has been lost
        {
            this.panelColor(Color.decode("#FF746C"),
                            Color.decode("#B3352E"),
                            Color.decode("#BD433C"),
                            Color.decode("#BD433C"));
        }
        else
        {
            this.panelColor(WindowComponent.BUTTON_BACKGROUND,
                            Color.decode("#FFFFFF"),
                            Color.LIGHT_GRAY,
                            WindowComponent.FRAME_BACKGROUND);
        }
    }

    // setters and getters
    public void setScoreLabel(double score)
    {
        score = SubjectMenu.twoDecimals(score);
        totalScore.setText("Total score: " + score);
    }
    public JLabel getScoreLabel()
    {
        return totalScore;
    }

    public void setEvaluatedLabel(double percentage)
    {
        totalEvaluated.setText("Evaluated percentage: " + SubjectMenu.twoDecimals(percentage) + "%");
    }
    public JLabel getEvaluatedLabel()
    {
        return totalEvaluated;
    }

    public GradeMenu getGradeMenu()
    {
        return gradeMenu;
    }
}