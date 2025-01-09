import javax.swing.*;
import java.awt.*;

public class SubjectMenuView extends JFrame
{
    public static int panel_count = 0;
    private Management management = new Management();
    private SubjectDetails subject_details;

    private JPanel main_panel;

    // method to initialize the main panel
    public void initialize_panel(Container contentPane)
    {
        Component component = new Component();
        main_panel = new JPanel();
        main_panel.setLayout(null);
        main_panel.setBackground(Component.default_frame_background);
        main_panel.setBounds(0,
                0,
                Main.width,
                Main.height);

        JPanel subject_panel = new JPanel();
        JScrollPane scroll_bar = component.set_scroll_bar(subject_panel,
                155,
                30,
                400,
                300);
        main_panel.add(scroll_bar);

        subject_details = new SubjectDetails(management,
                                            component,
                                            main_panel,
                                            subject_panel,
                                            () -> component.switch_panel(subject_details, main_panel),
                                            () -> component.switch_panel(subject_details, main_panel));
        subject_details.setVisible(false);

        // button to add a subject
        JButton plus = component.set_button("+",
                50,
                (Main.height-50) / 2,
                50,
                50,
                Component.default_button_background);
        component.configure_container(plus,
                                    Component.default_font_foreground,
                                    1,
                                    18);
        component.button_event(plus,
                () -> component.switch_panel(main_panel, subject_details),
                Component.default_button_background,
                Color.decode("#00FF00"));

        main_panel.add(plus);
        contentPane.add(main_panel);
        contentPane.add(subject_details);
        component.reload(main_panel);
    }
}
