import javax.swing.*;
import java.awt.*;

public class AddGrade
{
    private final JPanel grade_panel;
    private final GradeMenu grade_menu;

    // grade values
    private String score;
    private String percentage;

    // text boxes
    private JTextField score_box;
    private JTextField percentage_box;

    // constructor
    public AddGrade(String score,
                    String percentage,
                    GradeMenu grade_menu,
                    JPanel grade_panel)
    {
        this.score = score;
        this.percentage = percentage;
        this.grade_menu = grade_menu;
        this.grade_panel = grade_panel;
        add_grade();
    }

    // method to add a subject
    public void add_grade()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 80));
        panel.setBackground(WindowComponent.default_button_background);
        panel.setLayout(null);

        // score field settings
        score_box = WindowComponent.set_text_field(score,
                                                    94,
                                                    25,
                                                    80,
                                                    30);
        WindowComponent.configure_text(score_box,
                                            WindowComponent.default_button_background,
                                            1,
                                            18);
        score_box.addActionListener(e -> grade_menu.grade_validation());

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

        // score field settings
        percentage_box = WindowComponent.set_text_field(percentage,
                                                        WindowComponent.positive_x(score_box, 16),
                                                        score_box.getY(),
                                                        score_box.getWidth(),
                                                        score_box.getHeight());
        WindowComponent.configure_text(percentage_box,
                                            WindowComponent.default_button_background,
                                            1,
                                            18);
        percentage_box.addActionListener(e -> grade_menu.grade_validation());

        // percentage text
        JLabel percentage_symbol = WindowComponent.set_text("%",
                                                            WindowComponent.positive_x(percentage_box, 14),
                                                            score_text.getY(),
                                                            50,
                                                            30);
        WindowComponent.configure_text(percentage_symbol,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);

        // button to delete a subject
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
                                    () ->
                                    {
                                        new DeleteGrade(this,
                                                        grade_menu,
                                                        panel,
                                                        grade_panel);
                                    },
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));


        // add the components on the panel
        panel.add(score_text);
        panel.add(score_box);
        panel.add(percentage_box);
        panel.add(percentage_symbol);
        panel.add(delete_button);

        // add the panel to the subject panel
        grade_panel.add(panel);

        // reload the panel to show the changes
        WindowComponent.reload(grade_panel);
    }

    // get the score value of the score box
    public String get_score()
    {
        return score_box.getText().trim();
    }

    // get the percentage value of the percentage box
    public String get_percentage()
    {
        return percentage_box.getText().trim();
    }

}
