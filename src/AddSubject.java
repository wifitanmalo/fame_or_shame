import javax.swing.*;
import java.awt.*;

public class AddSubject
{
    private final int id;
    private final String name;
    private final int credits;
    private final JPanel subject_panel;
    public static GradeMenu grade_details;

    // constructor
    public AddSubject(int id,
                      String name,
                      int credits,
                      JPanel subject_panel)
    {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.subject_panel = subject_panel;
        add_subject();
    }

    // method to add a subject
    public void add_subject()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 80));
        panel.setBackground(WindowComponent.default_button_background);
        panel.setLayout(null);

    // id of the subject
        JLabel subject_id = new JLabel();
        subject_id.setText(String.valueOf(id));

    // name of the subject
        JLabel subject_name = WindowComponent.set_text((name),
                                            10,
                                            10,
                                            260,
                                            18);

        WindowComponent.configure_container(subject_name,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        subject_name_size(subject_name));

    // value of the total score
        JLabel total_score = WindowComponent.set_text(("Total score: " + 0.0),
                                                10,
                                                WindowComponent.negative_y(subject_name, 2),
                                                350,
                                                16);
        WindowComponent.configure_container(total_score,
                                            Color.lightGray,
                                            2,
                                            WindowComponent.get_height(total_score));

    // percentage of the total evaluated
        JLabel total_evaluated = WindowComponent.set_text(("Total evaluated: " + 0.0 + "%"),
                                                            10,
                                                            WindowComponent.negative_y(total_score, 2),
                                                            350,
                                                            16);
        WindowComponent.configure_container(total_evaluated,
                                            Color.lightGray,
                                            2,
                                            WindowComponent.get_height(total_evaluated));

    // button to add a subject
        JButton delete_button = WindowComponent.set_button("x",
                                                            320,
                                                            (30) / 2,
                                                            50,
                                                            50,
                                                            WindowComponent.default_frame_background);
        WindowComponent.configure_container(delete_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);
        WindowComponent.button_event(delete_button,
                                    () -> new DeleteSubject(Integer.parseInt(subject_id.getText()),
                                                            panel,
                                                            subject_panel),
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

    // button to confirm the subject creation
        grade_details = new GradeMenu();
        grade_details.setVisible(false);
        WindowComponent.get_container().add(grade_details);

        JButton grade_button = WindowComponent.set_button("+",
                                                            (delete_button.getX()-60),
                                                            delete_button.getY(),
                                                            50,
                                                            50,
                                                            WindowComponent.default_frame_background);
        WindowComponent.configure_container(grade_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);
        WindowComponent.button_event(grade_button,
                                    () -> WindowComponent.switch_panel(SubjectMenu.main_panel, grade_details),
                                    grade_button.getBackground(),
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components on the panel
        panel.add(subject_name);
        panel.add(total_score);
        panel.add(total_evaluated);
        panel.add(delete_button);
        panel.add(grade_button);

        // add the panel to the subject panel
        subject_panel.add(panel);

        // reload the panel to show the changes
        WindowComponent.reload(subject_panel);
    }

    // method to set the subject name size based in their length
    public int subject_name_size(JLabel subject_name)
    {
        if((name.length()>24) && (name.length()<34))
        {
            return 12;
        }
        else if((name.length()>=34) && (name.length() <= 50))
        {
            return 8;
        }
        return WindowComponent.get_height(subject_name);
    }
}
