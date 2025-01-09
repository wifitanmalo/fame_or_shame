import java.awt.*;
import javax.swing.*;

public class SubjectMenu extends JFrame
{
    public static int panel_count = 0;
    private final Management management = new Management();
    private final Container container;
    private JPanel main_panel;
    private SubjectDetails subject_details;

    // constructor
    public SubjectMenu(Container container)
    {
        this.container = container;
        initialize_panel();
    }

    // method to initialize the main panel
    public void initialize_panel()
    {
    // create the main panel
        main_panel = Component.set_panel(Component.default_frame_background,
                                        0,
                                        0,
                                        Main.width,
                                        Main.height);

    // create the subject panel
        JPanel subject_panel = new JPanel();
        JScrollPane scroll_subject = Component.set_scroll_bar(subject_panel,
                155,
                30,
                400,
                300);

        subject_details = new SubjectDetails(management,
                                            main_panel,
                                            subject_panel,
                                            () -> Component.switch_panel(subject_details, main_panel));
        subject_details.setVisible(false);

    // button to add a subject
        JButton add_button = Component.set_button("+",
                                                    50,
                                                    (Main.height-50) / 2,
                                                    50,
                                                    50,
                                                    Component.default_button_background);
        Component.configure_container(add_button,
                                        Component.default_font_foreground,
                                        1,
                                        18);
        Component.button_event(add_button,
                                () -> Component.switch_panel(main_panel, subject_details),
                                Component.default_button_background,
                                Color.decode("#00FF00"));

    // add the components to the main panel
        main_panel.add(scroll_subject);
        main_panel.add(add_button);

    // add the panels to the container
        container.add(main_panel);
        container.add(subject_details);

    // reload the panel to show the changes
        Component.reload(main_panel);
    }
}
