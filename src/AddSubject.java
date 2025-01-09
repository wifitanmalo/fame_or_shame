import javax.swing.*;
import java.awt.*;

public class AddSubject
{
    private final int id;
    private final String subject_name;
    private final int credits;
    private final JPanel subject_panel;

    // constructor
    public AddSubject(int id,
                      String subject_name,
                      int credits,
                      JPanel subject_panel)
    {
        this.id = id;
        this.subject_name = subject_name;
        this.credits = credits;
        this.subject_panel = subject_panel;
        add_subject();
    }

    // method to add a subject
    public void add_subject()
    {
        SubjectMenu.panel_count++;
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 80));
        panel.setBackground(Component.default_button_background);
        panel.setLayout(null);

    // name of the subject
        JLabel name = Component.set_text((subject_name + " (" + id + ")"),
                                            10,
                                            10,
                                            350,
                                            16);
        Component.configure_container(name,
                                        Component.default_font_foreground,
                                        1,
                                        Component.get_height(name));

    // value of the total score
        JLabel total_score = Component.set_text(("Total score: " + 0.0),
                                                10,
                                                Component.distance_y(name, 2),
                                                350,
                                                16);
        Component.configure_container(total_score,
                                    Color.lightGray,
                                    2,
                                    Component.get_height(total_score));

    // percentage of the total evaluated
        JLabel total_evaluated = Component.set_text(("Total evaluated: " + 0.0 + "%"),
                                                    10,
                                                    Component.distance_y(total_score, 2),
                                                    350,
                                                    16);
        Component.configure_container(total_evaluated,
                                        Color.lightGray,
                                        2,
                                        Component.get_height(total_evaluated));

    // button to add a subject
        JButton delete_button = Component.set_button("x",
                                                    320,
                                                    (30) / 2,
                                                    50,
                                                    50,
                                                    Component.default_frame_background);
        Component.configure_container(delete_button,
                                        Component.default_font_foreground,
                                        1,
                                        18);
        Component.button_event(delete_button,
                                () -> new DeleteSubject(panel, subject_panel),
                                delete_button.getBackground(),
                                Color.decode("#FF0000"));

    // button to confirm the subject creation
        JButton grade_button = Component.set_button("+",
                                                    (delete_button.getX()-60),
                                                    delete_button.getY(),
                                                    50,
                                                    50,
                                                    Component.default_frame_background);
        Component.configure_container(grade_button,
                                        Component.default_font_foreground,
                                        1,
                                        18);
        Component.button_event(grade_button,
                                () -> System.out.println("Nothing here yet"),
                                grade_button.getBackground(),
                                Color.decode("#00FF00"));

        // add the components on the panel
        panel.add(name);
        panel.add(total_score);
        panel.add(total_evaluated);
        panel.add(delete_button);
        panel.add(grade_button);

        // add the panel to the subject panel
        subject_panel.add(panel);

        // reload the panel to show the changes
        Component.reload(subject_panel);

    }
}
