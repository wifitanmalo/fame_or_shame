import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class GradeMenu extends JPanel
{
    private final int index;
    private double current_score;
    private double current_percentage;
    public static JPanel grade_panel;

    // imported labels
    private JLabel score_text;
    private final JLabel subject_score;
    private final JLabel subject_percentage;

    private final ArrayList<AddGrade> grade_panel_list = new ArrayList<>();

    // constructor
    public GradeMenu(int index,
                     JLabel subject_score,
                     JLabel subject_percentage)
    {
        this.index = index;
        this.subject_score = subject_score;
        this.subject_percentage = subject_percentage;
        initialize_panel();
    }

    // method to initialize the main panel
    public void initialize_panel()
    {
        setLayout(null);
        setBackground(WindowComponent.default_frame_background);
        setBounds(0,
                0,
                Main.width,
                Main.height);

        // create the grade panel
        grade_panel = new JPanel();
        JScrollPane scroll_grade = WindowComponent.set_scroll_bar(grade_panel,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // back button settings
        JButton back_button = WindowComponent.set_button("Back",
                                                        50,
                                                        WindowComponent.negative_y(scroll_grade, -50),
                                                        78,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_text(back_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            16);
        WindowComponent.button_event(back_button,
                                    () -> WindowComponent.switch_panel(this, SubjectMenu.main_panel),
                                    WindowComponent.default_button_background,
                                    WindowComponent.default_entered_button_background,
                                    WindowComponent.default_pressed_button_background);

        // total button settings
        JButton total_button = WindowComponent.set_button("Total",
                                                            50,
                                                            WindowComponent.positive_y(back_button, 20),
                                                            78,
                                                            50,
                                                            WindowComponent.default_button_background);
        WindowComponent.configure_text(total_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            14);
        WindowComponent.button_event(total_button,
                                    () ->
                                    {
                                        if(grade_validation())
                                        {
                                            this.current_score = SubjectMenu.manager.get_subjects_list().get(index).get_total_score();
                                            this.current_percentage = SubjectMenu.manager.get_subjects_list().get(index).get_total_evaluated();
                                            WindowComponent.set_text_score(score_text, Math.round(this.current_score*100.0) / 100.0);
                                        }
                                        WindowComponent.set_text_score(subject_score, this.current_score);
                                        subject_score.setText("Total score: " + Math.round(this.current_score*100.0) / 100.0);
                                        subject_percentage.setText("Total evaluated: " + Math.round(this.current_percentage*100.0) / 100.0 + "%");
                                    },
                                    WindowComponent.default_button_background,
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // button to add a subject
        JButton add_button = WindowComponent.set_button("+",
                                                        back_button.getX(),
                                                        WindowComponent.positive_y(total_button, 20),
                                                        50,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_text(add_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);
        WindowComponent.button_event(add_button,
                                    () ->
                                    {
                                        AddGrade new_grade = new AddGrade("",
                                                                        "",
                                                                        this,
                                                                        grade_panel);
                                        grade_panel_list.add(new_grade);
                                    },
                                    WindowComponent.default_button_background,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // subject name text settings
        JLabel name_text = WindowComponent.set_text("Grades",
                                                    scroll_grade.getX(),
                                                    scroll_grade.getY()-32,
                                                    scroll_grade.getWidth(),
                                                    26);
        WindowComponent.configure_text(name_text,
                                            WindowComponent.default_pressed_button_background,
                                            3,
                                            WindowComponent.get_height(name_text));

        // score text settings
        score_text = WindowComponent.set_text(String.valueOf(this.current_score),
                                                    back_button.getX(),
                                                    WindowComponent.positive_y(add_button, 14),
                                                    80,
                                                    20);
        WindowComponent.configure_text(score_text,
                                            WindowComponent.default_pressed_button_background,
                                            3,
                                            WindowComponent.get_height(name_text));

        // add the components to the main panel
        add(scroll_grade);
        add(name_text);
        add(back_button);
        add(total_button);
        add(add_button);
        add(score_text);
    }

    // method to validate the grade
    public boolean grade_validation()
    {
        // reboot the grade values
        double total_score = 0;
        double total_percentage = 0;
        SubjectMenu.manager.get_subjects_list().get(index).set_total_score(0);
        SubjectMenu.manager.get_subjects_list().get(index).set_total_evaluated(0);

        for (AddGrade grade : grade_panel_list)
        {
            String scoreText = grade.get_score();
            String percentageText = grade.get_percentage();

            try
            {
                double score = Double.parseDouble(scoreText);
                double percentage = Double.parseDouble(percentageText);

                if(score<0 || percentage<=0)
                {
                    JOptionPane.showMessageDialog(this,
                                                "Score and percentage must be POSITIVE numbers.",
                                                "Input Error",
                                                JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if(ValidationUtils.exceeds_limit(score, Subject.max_score))
                {
                    JOptionPane.showMessageDialog(this,
                                                "Score cannot be higher than " + Subject.max_score + ".",
                                                "Score limit",
                                                JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                // get the current values
                total_score += score * (percentage/100);
                total_percentage += percentage;

                if(ValidationUtils.exceeds_limit(total_percentage, 100))
                {
                    JOptionPane.showMessageDialog(this,
                            "The total percentage cannot exceed 100%.",
                            "Percentage limit",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this,
                                            "Please enter valid numbers for score and percentage.",
                                            "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        SubjectMenu.manager.get_subjects_list().get(index).set_total_score(total_score);
        SubjectMenu.manager.get_subjects_list().get(index).set_total_evaluated(total_percentage);
        return true;
    }

    // method to delete a grade from the grades list
    public void remove_grade(AddGrade grade)
    {
        grade_panel_list.remove(grade);
        this.current_score = SubjectMenu.manager.get_subjects_list().get(index).get_total_score();
        WindowComponent.set_text_score(this.score_text, this.current_score);
    }
}