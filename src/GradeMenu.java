import java.awt.*;
import javax.swing.*;

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
        JScrollPane scroll_grade = WindowComponent.set_scroll_bar(gradeBox,
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
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(back_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.button_event(back_button,
                                    () ->
                                    {
                                        Main.subjectMenu.refreshSubjects();
                                        WindowComponent.switch_panel(this, Main.subjectMenu);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // button to calculate the total score/percentage
        JButton total_button = WindowComponent.set_button("Total",
                                                            50,
                                                            WindowComponent.positive_y(back_button, 20),
                                                            78,
                                                            50,
                                                            WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(total_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        14);
        WindowComponent.button_event(total_button,
                                    () ->
                                    {
                                        if(gradeValidation())
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
        JButton add_button = WindowComponent.set_button("+",
                                                        back_button.getX(),
                                                        WindowComponent.positive_y(total_button, 20),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(add_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.button_event(add_button,
                                    () ->
                                    {
                                        GradePanel new_grade = new GradePanel(subject);
                                        subject.createGrade(new_grade);
                                        fileHandler.updateGrade(subject);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // subject name text settings
        JLabel name_text = WindowComponent.set_text("Grades",
                                                    scroll_grade.getX(),
                                                    scroll_grade.getY()-32,
                                                    scroll_grade.getWidth(),
                                                    26);
        WindowComponent.configure_text(name_text,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.get_height(name_text));

        // text where the total score is shown
        scoreText = WindowComponent.set_text(String.valueOf(subject.getTotalScore()),
                                                back_button.getX(),
                                                WindowComponent.positive_y(add_button, 14),
                                                80,
                                                20);
        WindowComponent.configure_text(scoreText,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.get_height(name_text));

        // add the components to the main panel
        add(scroll_grade);
        add(name_text);
        add(back_button);
        add(total_button);
        add(add_button);
        add(scoreText);

        // load the saved grades
        fileHandler.loadGrades();
    }

    // method to validate the grade
    public boolean gradeValidation()
    {
        // reboot the grade values
        double total_score = 0;
        double total_percentage = 0;
        subject.setTotalScore(0);
        subject.setTotalEvaluated(0);

        for (GradePanel grade : subject.getGradesList())
        {
            String scoreText = grade.getScoreText();
            String percentageText = grade.getPercentageText();

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
                if(ValidationUtils.exceedsLimit(score, Subject.MAX_SCORE))
                {
                    JOptionPane.showMessageDialog(this,
                                                "Score cannot be higher than " + Subject.MAX_SCORE + ".",
                                                "Score limit",
                                                JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                // get the current values
                total_score += score * (percentage/100);
                total_percentage += percentage;

                if(ValidationUtils.exceedsLimit(total_percentage, 100))
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

        subject.setTotalScore(total_score);
        subject.setTotalEvaluated(total_percentage);
        return true;
    }

    // method to load the grades
    public void refreshGrades(Subject current_subject)
    {
        gradeBox.removeAll();
        this.subject = current_subject;
        for(GradePanel grade : subject.getGradesList())
        {
            gradeBox.add(grade);

            // set the grade values in the text fields
            grade.setScoreText(grade.getScoreText());
            grade.setPercentageText(grade.getPercentageText());

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