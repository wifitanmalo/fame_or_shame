import java.awt.*;
import javax.swing.*;

public class SubjectPanel extends JPanel
{
    // subject object
    private final Subject subject;

    // subject panels
    private final JPanel subject_box;
    public static GradeMenu grade_menu;

    // performance labels
    private JLabel total_score;
    private JLabel total_evaluated;

    // constructor
    public SubjectPanel(Subject subject)
    {
        this.subject = subject;
        this.subject_box = SubjectMenu.getSubjectBox();
        subject_panel();
    }

    // method to add a subject
    public void subject_panel()
    {
        setPreferredSize(new Dimension(400, 80));
        setBackground(WindowComponent.BUTTON_BACKGROUND);
        setLayout(null);

        // create the grade menu panel
        grade_menu = new GradeMenu(subject);
        grade_menu.setVisible(false);
        WindowComponent.get_container().add(grade_menu);

        // name of the subject
        JLabel subject_name = WindowComponent.set_text(subject.getName(),
                                                        10,
                                                        10,
                                                        260,
                                                        18);

        WindowComponent.configure_text(subject_name,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        subject_name_size(subject_name));

        // text of the total score
        total_score = WindowComponent.set_text(("Total score: 0.0"),
                                                10,
                                                WindowComponent.negative_y(subject_name, 2),
                                                350,
                                                16);
        WindowComponent.configure_text(total_score,
                                            Color.lightGray,
                                            3,
                                            WindowComponent.get_height(total_score));

        // text of the total percentage evaluated
        total_evaluated = WindowComponent.set_text(("Total evaluated: 0.0%"),
                                                    10,
                                                    WindowComponent.negative_y(total_score, 2),
                                                    350,
                                                    16);
        WindowComponent.configure_text(total_evaluated,
                                        Color.lightGray,
                                        3,
                                        WindowComponent.get_height(total_evaluated));

        // button to delete a subject
        JButton delete_button = WindowComponent.set_button("x",
                                                            320,
                                                            15,
                                                            50,
                                                            50,
                                                            WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configure_text(delete_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.button_event(delete_button,
                                    () ->
                                    {
                                        new DeleteSubject(this.subject, this);
                                        GradeMenu.fileHandler.deleteGrade(this.subject);
                                    },
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // button to enter on the grades menu
        JButton grade_button = WindowComponent.set_button("+",
                                                            (delete_button.getX()-60),
                                                            delete_button.getY(),
                                                            50,
                                                            50,
                                                            WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configure_text(grade_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.button_event(grade_button,
                                    () ->
                                    {
                                        grade_menu.refreshGrades(this.subject);
                                        grade_menu.setTextScore(subject.getTotalScore());
                                        WindowComponent.switch_panel(Main.subjectMenu, grade_menu);
                                    },
                                    grade_button.getBackground(),
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components on the panel
        add(subject_name);
        add(total_score);
        add(total_evaluated);
        add(delete_button);
        add(grade_button);

        // add the subject on the subject box
        subject_box.add(this);

        // reload the panel to show the changes
        WindowComponent.reload(subject_box);
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
        return WindowComponent.get_height(subject_name);
    }

    // setters and getters
    public void set_score_label(double score)
    {
        score = SubjectMenu.twoDecimals(score);
        total_score.setText("Total score: " + score);
        if(score >= Subject.PASSING_SCORE)
        {
            total_score.setForeground(Color.decode("#C5EF48"));
        }
        else
        {
            total_score.setForeground(Color.decode("#FF6865"));
        }
    }
    public JLabel get_score_label()
    {
        return total_score;
    }

    public void set_evaluated_label(double percentage)
    {
        total_evaluated.setText("Evaluated percentage: " + SubjectMenu.twoDecimals(percentage) + "%");
    }
    public JLabel get_evaluated_label()
    {
        return total_evaluated;
    }
}
