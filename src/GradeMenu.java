import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class GradeMenu extends JPanel
{
    // subject object
    private Subject subject;

    // panel where the grades are added
    private static JPanel grade_box;

    // imported labels
    private static JLabel score_text;

    // constructor
    public GradeMenu(Subject subject)
    {
        this.subject = subject;
        initialize_panel();
    }

    // method to initialize the main panel
    public void initialize_panel()
    {
        setLayout(null);
        setBackground(WindowComponent.default_frame_background);
        setBounds(0, 0, Main.width, Main.height);

        // create the grade box
        grade_box = new JPanel();
        JScrollPane scroll_grade = WindowComponent.set_scroll_bar(grade_box,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // button to back to the subject menu
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
                                    () ->
                                    {
                                        WindowComponent.switch_panel(this, Main.subject_menu);
                                    },
                                    WindowComponent.default_button_background,
                                    WindowComponent.default_entered_button_background,
                                    WindowComponent.default_pressed_button_background);

        // button to calculate the total score/percentage
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
                                            set_text_score(subject.get_total_score());
                                            Main.subject_menu.update_subject(subject,
                                                                            subject.get_total_score(),
                                                                            subject.get_total_evaluated());
                                            WindowComponent.reload(Main.subject_menu);
                                        }
                                    },
                                    WindowComponent.default_button_background,
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // button to create a grade
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
                                        GradePanel new_grade = new GradePanel(subject);
                                        subject.create_grade(new_grade);
                                        add_grade(new_grade);
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

        // text where the total score is shown
        score_text = WindowComponent.set_text(String.valueOf(subject.get_total_score()),
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
        subject.set_total_score(0);
        subject.set_total_evaluated(0);

        for (GradePanel grade : subject.get_grades_list())
        {
            String scoreText = grade.get_score_text();
            String percentageText = grade.get_percentage_text();

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

        subject.set_total_score(total_score);
        subject.set_total_evaluated(total_percentage);
        return true;
    }

    // method to load the grades
    public void load_grades(Subject current_subject)
    {
        grade_box.removeAll();
        this.subject = current_subject;
        for(GradePanel grade : subject.get_grades_list())
        {
            grade_box.add(grade);

            // set the grade values in the text fields
            grade.set_score_text(grade.get_score_text());
            grade.set_percentage_text(grade.get_percentage_text());

            // reload the panel to show the changes
            WindowComponent.reload(grade_box);
        }
    }

    // method to create a grade in the file
    public void add_grade(GradePanel grade)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("grades.txt", true));
            String data = grade.get_subject_id()
                            + ","
                            + grade.get_score_text()
                            + ","
                            + grade.get_percentage_text();
            writer.write(data);
            writer.newLine();
            writer.close();
        }
        catch (IOException e)
        {
            WindowComponent.message_box(this,
                                        "Error while writing the file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to change the text and color of the total score
    public void set_text_score(double score)
    {
        score_text.setText(String.valueOf(Math.round(score*100.0) / 100.0));
        if(subject.get_total_score() >= Subject.passing_score)
        {
            score_text.setForeground(Color.decode("#C5EF48"));
        }
        else
        {
            score_text.setForeground(Color.decode("#FF6865"));
        }
        WindowComponent.reload(score_text);
    }

    // method to get the subject box
    public static JPanel get_grade_box()
    {
        return grade_box;
    }
}