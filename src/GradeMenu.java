import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;

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
                                        Main.subject_menu.refresh_subjects();
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
                                        update_grade(subject);
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

        // load the saved grades
        load_grades();
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

    // method to load the grades from the grades file
    public void load_grades()
    {
        try
        {
            String line;
            ArrayList<String> all_lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("grades.txt"));

            // add on the list all non-empty lines
            while ((line = reader.readLine()) != null)
            {
                all_lines.add(line);
            }
            reader.close();

            // loop to add each grade in its respective list
            for (Subject subject : SubjectMenu.manager.get_subjects_list())
            {
                subject.set_grades_list(new ArrayList<>());

                // loop to get the lines that match the subject id
                for (String grade : all_lines)
                {
                    String[] data = grade.split(",");
                    int id = Integer.parseInt(data[0]);

                    if (id == subject.get_id())
                    {
                        String score = data[1];
                        String percentage = data[2];

                        // create a new grade and set the score/percentage
                        GradePanel new_grade = new GradePanel(subject);
                        new_grade.set_score_text(score);
                        new_grade.set_percentage_text(percentage);  // Assuming you have this method

                        // add the new grade to the grades list
                        subject.create_grade(new_grade);
                    }
                }
            }
        }
        catch (IOException error)
        {
            WindowComponent.message_box(this,
                                        "Error while reading the file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to create a grade in the file
    public void create_grade(Subject subject)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("grades.txt", true));
            for(GradePanel grade : subject.get_grades_list())
            {
                String data = grade.get_subject_id()
                        + ","
                        + grade.get_score_text()
                        + ","
                        + grade.get_percentage_text();
                writer.write(data);
                writer.newLine();
            }
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

    // method to delete a grade in the file
    public void delete_grade(Subject subject)
    {
        // file names
        File input = new File("grades.txt");
        File temporal = new File("temporal.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temporal)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                // split the line in commas
                String[] data = line.split(",");
                int current_id = Integer.parseInt(data[0]);

                // ignore the lines with the same id of the subject
                if(current_id != subject.get_id())
                {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(this,
                                        "Error while reading the file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }

        // replace the original file with the temporal one
        if (!input.delete() || !temporal.renameTo(input))
        {
            WindowComponent.message_box(this,
                                        "Error while rewriting file.",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete the grades in the file
    public void update_grade(Subject subject)
    {
        delete_grade(subject);
        create_grade(subject);
    }

    // method to load the grades
    public void refresh_grades(Subject current_subject)
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

    // method to change the text and color of the total score
    public void set_text_score(double score)
    {
        score = SubjectMenu.two_decimals(score);
        score_text.setText(String.valueOf(score));
        if(score >= Subject.passing_score)
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