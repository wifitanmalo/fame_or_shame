import java.awt.*;
import javax.swing.*;

public class SubjectMenu extends JFrame
{
    public static final Management management = new Management();
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
        main_panel = WindowComponent.set_panel(WindowComponent.default_frame_background,
                                        0,
                                        0,
                                        Main.width,
                                        Main.height);

    // create the subject panel
        JPanel subject_panel = new JPanel();
        JScrollPane scroll_subject = WindowComponent.set_scroll_bar(subject_panel,
                155,
                30,
                400,
                300);

        subject_details = new SubjectDetails(main_panel,
                                            subject_panel,
                                            () -> WindowComponent.switch_panel(subject_details, main_panel));
        subject_details.setVisible(false);

    // button to add a subject
        JButton add_button = WindowComponent.set_button("+",
                                                    50,
                                                    (Main.height-50) / 2,
                                                    50,
                                                    50,
                                                    WindowComponent.default_button_background);
        WindowComponent.configure_container(add_button,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        18);
        WindowComponent.button_event(add_button,
                                    this::button_action,
                                    WindowComponent.default_button_background,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

    // add the components to the main panel
        main_panel.add(scroll_subject);
        main_panel.add(add_button);

    // add the panels to the container
        container.add(main_panel);
        container.add(subject_details);

    // reload the panel to show the changes
        WindowComponent.reload(main_panel);
    }

    // method to set the add action based in the amount of signed credits
    public void button_action()
    {
        if(Management.signed_credits == Management.max_credits)
        {
            JOptionPane.showMessageDialog(this,
                                                    "You have already reached the credit limit.",
                                                    "Number limit",
                                                    JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            WindowComponent.switch_panel(main_panel, subject_details);
        }

    }
}
