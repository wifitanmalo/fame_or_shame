import java.awt.*;
import javax.swing.*;

public class AddSubject
{
    // subject object
    private final Subject subject;

    // subject panels
    public static JLabel total_score;
    private final JPanel subject_panel;
    public static GradeMenu grade_menu;

    // constructor
    public AddSubject(Subject subject,
                      JPanel subject_panel)
    {
        this.subject = subject;
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
        subject_id.setText(String.valueOf(subject.get_id()));

        // name of the subject
        JLabel subject_name = WindowComponent.set_text(subject.get_name(),
                                                        10,
                                                        10,
                                                        260,
                                                        18);

        WindowComponent.configure_text(subject_name,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        subject_name_size(subject_name));

        // text of the total score
        total_score = WindowComponent.set_text(("Total score: 0.0"),
                                                10,
                                                WindowComponent.negative_y(subject_name, 2),
                                                350,
                                                16);
        WindowComponent.configure_text(total_score,
                                            Color.lightGray,
                                            3,
                                            WindowComponent.get_height(total_score));

        // text of the total percentage evaluated
        JLabel total_evaluated = WindowComponent.set_text(("Total evaluated: 0.0%"),
                                                            10,
                                                            WindowComponent.negative_y(total_score, 2),
                                                            350,
                                                            16);
        WindowComponent.configure_text(total_evaluated,
                                            Color.lightGray,
                                            3,
                                            WindowComponent.get_height(total_evaluated));

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
                                    () -> new DeleteSubject(Integer.parseInt(subject_id.getText()),
                                                                            panel,
                                                                            subject_panel),
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // create the grade menu panel
        grade_menu = new GradeMenu(SubjectMenu.manager.get_index(subject.get_id()),
                                    total_score,
                                    total_evaluated);
        grade_menu.setVisible(false);
        WindowComponent.get_container().add(grade_menu);

        // button to enter on the grades menu
        JButton grade_button = WindowComponent.set_button("+",
                                                            (delete_button.getX()-60),
                                                            delete_button.getY(),
                                                            50,
                                                            50,
                                                            WindowComponent.default_frame_background);
        WindowComponent.configure_text(grade_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);
        WindowComponent.button_event(grade_button,
                                    () -> WindowComponent.switch_panel(Main.subject_menu, grade_menu),
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

    // method to set the subject name size based in its length
    public int subject_name_size(JLabel subject_name)
    {
        if((subject.get_name().length()>24) && (subject.get_name().length()<34))
        {
            return 12;
        }
        else if((subject.get_name().length()>=34) && (subject.get_name().length() <= 50))
        {
            return 8;
        }
        return WindowComponent.get_height(subject_name);
    }
}
