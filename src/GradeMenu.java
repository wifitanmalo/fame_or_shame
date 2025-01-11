import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GradeMenu extends JPanel
{
    private final int index;
    private final ArrayList<AddGrade> gradeList = new ArrayList<>();

    // constructor
    public GradeMenu(int index)
    {
        this.index = index;
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
        JPanel grade_panel = new JPanel();
        JScrollPane scroll_grade = WindowComponent.set_scroll_bar(grade_panel,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // subject name text settings
        JLabel name_text = WindowComponent.set_text("Grades",
                                                        scroll_grade.getX(),
                                                        scroll_grade.getY()-32,
                                                        scroll_grade.getWidth(),
                                                        26);
        WindowComponent.configure_container(name_text,
                                            WindowComponent.default_pressed_button_background,
                                            3,
                                            WindowComponent.get_height(name_text));

        // back button settings
        JButton back_button = WindowComponent.set_button("Back",
                                                        50,
                                                        WindowComponent.negative_y(scroll_grade, -50),
                                                        78,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_container(back_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            16);
        WindowComponent.button_event(back_button,
                                    () -> WindowComponent.switch_panel(this, SubjectMenu.main_panel),
                                    WindowComponent.default_button_background,
                                    WindowComponent.default_entered_button_background,
                                    WindowComponent.default_pressed_button_background);

        // button to add a subject
        JButton add_button = WindowComponent.set_button("+",
                                                        back_button.getX(),
                                                        WindowComponent.positive_y(back_button, 20),
                                                        50,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_container(add_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);
        WindowComponent.button_event(add_button,
                                    () ->
                                    {
                                        if (validate_grades())
                                        {
                                            AddGrade new_grade = new AddGrade(grade_panel);
                                            gradeList.add(new_grade);
                                        }
                                    },
                                    WindowComponent.default_button_background,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // id text settings
        JLabel score_text = WindowComponent.set_text("5.0",
                                                    back_button.getX(),
                                                    WindowComponent.positive_y(add_button, 20),
                                                    100,
                                                    26);
        WindowComponent.configure_container(score_text,
                                            WindowComponent.default_pressed_button_background,
                                            3,
                                            WindowComponent.get_height(name_text));

        // add the components to the main panel
        add(scroll_grade);
        add(name_text);
        add(back_button);
        add(add_button);
        add(score_text);
    }

    private boolean validate_grades()
    {
        double totalGrade = 0;
        SubjectMenu.management.get_subjects_list().get(index).set_total_evaluated(0);

        for (AddGrade grade : gradeList)
        {
            String scoreText = grade.get_score();
            String percentageText = grade.get_percentage();

            try
            {
                double score = Double.parseDouble(scoreText);
                double percentage = Double.parseDouble(percentageText);
                totalGrade = SubjectMenu.management.get_subjects_list().get(index).get_total_evaluated();

                if(score<0 || percentage<=0)
                {
                    JOptionPane.showMessageDialog(this,
                                                "Score and percentage must be positive numbers.",
                                                "Input Error",
                                                JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if(totalGrade >= 100)
                {
                    JOptionPane.showMessageDialog(this,
                                                "You have already evaluated 100% of the subject.",
                                                "Number limit",
                                                JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                SubjectMenu.management.get_subjects_list().get(index).add_grade(score, percentage);

            } catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this,
                                            "Please enter valid numbers for score and percentage.",
                                            "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        System.out.println(totalGrade);
        update_score(totalGrade);
        return true;
    }

    private void update_score(double current_score)
    {
        JLabel scoreLabel = (JLabel) getComponentByName("score_text");
        if (scoreLabel != null)
        {
            scoreLabel.setText(String.format("%.2f", current_score));
        }
    }

    private Component getComponentByName(String name)
    {
        for (Component component : getComponents())
        {
            if (name.equals(component.getName()))
            {
                return component;
            }
        }
        return null;
    }

}
