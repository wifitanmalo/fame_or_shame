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
import fos.service.Subject;

public class SubjectPanel extends JPanel
{
    // subject object
    private final Subject subject;

    // object of the grade menu
    private GradeMenu gradeMenu;

    // performance values texts
    private JLabel totalScore, totalEvaluated;

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
        JLabel subject_name = WindowComponent.setText(subject.getId() + " " + subject.getName(),
                                                        10,
                                                        10,
                                                        241,
                                                        18);

        WindowComponent.configureText(subject_name,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        subject_name_size(subject_name));

        // text of the total score
        totalScore = WindowComponent.setText(("Total score: 0.0"),
                                                10,
                                                WindowComponent.yNegative(subject_name, 2),
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
        JButton delete_button = WindowComponent.setButton("x",
                                                            320,
                                                            15,
                                                            50,
                                                            50,
                                                            WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configureText(delete_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(delete_button,
                                    () ->
                                    {
                                        int choice = JOptionPane.showConfirmDialog(this,
                                                                                "You want to delete this subject?",
                                                                                "Delete subject",
                                                                                JOptionPane.YES_NO_OPTION);
                                        if(choice == JOptionPane.YES_OPTION)
                                        {
                                            // remove the subject from the database
                                            SubjectMenu.dataHandler.deleteSubject(subject.getId(), this);
                                            // reload the subjects to show the changes
                                            SubjectMenu.dataHandler.loadSubjects(SubjectMenu.subjectBox);
                                        }
                                        GradeMenu.dataHandler.deleteAll(subject.getId(), this);
                                    },
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // button to enter on the grades menu
        JButton grade_button = WindowComponent.setButton("+",
                                                            (delete_button.getX()-60),
                                                            delete_button.getY(),
                                                            50,
                                                            50,
                                                            WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configureText(grade_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(grade_button,
                                    () ->
                                    {
                                        // load the panels of the grades in the grades box
                                        GradeMenu.dataHandler.loadGrades(subject, gradeMenu.getGradeBox(), this);
                                        // update the subject score on the grade menu
                                        gradeMenu.setTextScore(subject.getTotalScore());
                                        // switch to the grade menu
                                        WindowComponent.switchPanel(Main.subjectMenu, gradeMenu);
                                    },
                                    grade_button.getBackground(),
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components on the panel
        add(subject_name);
        add(totalScore);
        add(totalEvaluated);
        add(delete_button);
        add(grade_button);

        // add the subject on the subject box
        SubjectMenu.subjectBox.add(this);

        // reload the panel to show the changes
        WindowComponent.reload(SubjectMenu.subjectBox);
    }

    // method to set the subject name size based in its length
    public int subject_name_size(JLabel subject_name)
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

    // setters and getters
    public void set_score_label(double score)
    {
        score = SubjectMenu.twoDecimals(score);
        totalScore.setText("Total score: " + score);
        if(score >= Subject.PASSING_SCORE)
        {
            totalScore.setForeground(Color.decode("#C5EF48"));
        }
        else
        {
            totalScore.setForeground(Color.decode("#FF6865"));
        }
    }
    public JLabel get_score_label()
    {
        return totalScore;
    }

    public void set_evaluated_label(double percentage)
    {
        totalEvaluated.setText("Evaluated percentage: " + SubjectMenu.twoDecimals(percentage) + "%");
    }
    public JLabel get_evaluated_label()
    {
        return totalEvaluated;
    }
}
