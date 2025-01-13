import java.awt.*;
import javax.swing.*;

public class GradePanel extends JPanel
{
    // panel where the grades are added
    private final JPanel grade_box;

    // text boxes
    private static JTextField score_box;
    private static JTextField percentage_box;

    // constructor
    public GradePanel()
    {
        this.grade_box = GradeMenu.get_grade_box();
        grade_panel();
    }

    // method to add a subject
    public void grade_panel()
    {
        setPreferredSize(new Dimension(400, 80));
        setBackground(WindowComponent.default_button_background);
        setLayout(null);

        // text field where you enter the score
        score_box = WindowComponent.set_text_field(94,
                                                    25,
                                                    80,
                                                    30);
        WindowComponent.configure_text(score_box,
                                        WindowComponent.default_button_background,
                                        1,
                                        18);

        // score text
        JLabel score_text = WindowComponent.set_text("Score:",
                                                        WindowComponent.negative_x(score_box, 0),
                                                        score_box.getY(),
                                                        64,
                                                        30);
        WindowComponent.configure_text(score_text,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);

        // text field where you enter the percentage
        percentage_box = WindowComponent.set_text_field(WindowComponent.positive_x(score_box, 16),
                                                        score_box.getY(),
                                                        score_box.getWidth(),
                                                        score_box.getHeight());
        WindowComponent.configure_text(percentage_box,
                                        WindowComponent.default_button_background,
                                        1,
                                        18);

        // percentage symbol
        JLabel percentage_symbol = WindowComponent.set_text("%",
                                                            WindowComponent.positive_x(percentage_box, 14),
                                                            score_text.getY(),
                                                            50,
                                                            30);
        WindowComponent.configure_text(percentage_symbol,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);

        // button to delete a grade
        JButton delete_button = WindowComponent.set_button("x",
                                                            320,
                                                            15,
                                                            50,
                                                            50,
                                                            WindowComponent.default_frame_background);
        WindowComponent.configure_text(delete_button,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        18);
        WindowComponent.button_event(delete_button,
                                    () -> new DeleteGrade(this, grade_box),
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // add the components on the panel
        add(score_text);
        add(score_box);
        add(percentage_box);
        add(percentage_symbol);
        add(delete_button);

        // add the panel to the subject panel
        grade_box.add(this);

        // reload the panel to show the changes
        WindowComponent.reload(grade_box);
    }

    // setters and getters
    public static void set_score_text(String score)
    {
        score_box.setText(score);
    }
    public String get_score_text()
    {
        return score_box.getText().trim();
    }

    public static void set_percentage_text(String percentage)
    {
        percentage_box.setText(percentage);
    }
    public String get_percentage_text()
    {
        return percentage_box.getText().trim();
    }
}
