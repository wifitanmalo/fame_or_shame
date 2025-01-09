import javax.swing.*;
import java.awt.*;

public class AddSubjectView
{
    int panel_count = SubjectMenu.panel_count;
    Component component = new Component();

    // constructor
    public AddSubjectView()
    {

    }

    // method to add a subject
    public void add_subject(JPanel subject_panel,
                                int id,
                                String subject_name,
                                int credits)
    {

            if(panel_count < 21)
            {
                panel_count++;
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(400, 80));
                panel.setBackground(Component.default_button_background);
                panel.setLayout(null);

            // name of the subject
                JLabel name = component.set_text((subject_name + " (" + id + ")"),
                                                    10,
                                                    10,
                                                    350,
                                                    16);
                component.configure_container(name,
                                        Component.default_font_foreground,
                                        1,
                                        component.get_height(name));

            // value of the total score
                JLabel total_score = component.set_text(("Total score: " + 0.0),
                                                        10,
                                                        component.distance_y(name, 2),
                                                        350,
                                                        16);
                component.configure_container(total_score,
                                        Color.lightGray,
                                        2,
                                        component.get_height(total_score));

            // percentage of the total evaluated
                JLabel total_evaluated = component.set_text(("Total evaluated: " + 0.0 + "%"),
                        10,
                        component.distance_y(total_score, 2),
                        350,
                        16);
                component.configure_container(total_evaluated,
                        Color.lightGray,
                        2,
                        component.get_height(total_evaluated));

                DeleteSubject delete_subject = new DeleteSubject(subject_panel, component);
            // button to add a subject
                JButton delete = component.set_button("x",
                        320,
                        (30) / 2,
                        50,
                        50,
                        Component.default_frame_background);
                component.configure_container(delete,
                        Component.default_font_foreground,
                        1,
                        18);
                component.button_event(delete,
                        delete_subject.delete_subject(panel),
                        delete.getBackground(),
                        Color.decode("#FF0000"));

            // button to add a subject
                JButton plus = component.set_button("+",
                        (delete.getX()-60),
                        delete.getY(),
                        50,
                        50,
                        Component.default_frame_background);
                component.configure_container(plus,
                        Component.default_font_foreground,
                        1,
                        18);
                component.button_event(plus,
                        () -> System.out.print("Nothing here yet"),
                        plus.getBackground(),
                        Color.decode("#00FF00"));

            // add the components on the panel
                panel.add(name);
                panel.add(total_score);
                panel.add(total_evaluated);
                panel.add(delete);
                panel.add(plus);
                subject_panel.add(panel);

                component.reload(subject_panel);
            }
    }
}
