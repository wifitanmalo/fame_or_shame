import java.awt.*;
import javax.swing.*;

public class SubjectMenu extends JPanel
{
    // manager object to use the subjects
    public static final Management manager = new Management();

    // subject panels
    private static final JPanel subject_box = new JPanel();
    private final CreateSubject create_subject;

    // container
    private final Container container;

    // constructor
    public SubjectMenu()
    {
        this.create_subject = new CreateSubject();
        create_subject.setVisible(false);
        this.container = WindowComponent.get_container();
        initialize_panel();
    }

    // method to initialize the main panel
    public void initialize_panel()
    {
        // create the main panel
        setLayout(null);
        setBackground(WindowComponent.default_frame_background);
        setBounds(0, 0, Main.width, Main.height);

        // add a vertical scroll bar on the subject box
        JScrollPane scroll_subject = WindowComponent.set_scroll_bar(subject_box,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // subject example
        Subject uwu = new Subject(1, "uwu", 3);
        SubjectMenu.manager.create_subject(uwu);

        // subjects title
        JLabel subjects_title = WindowComponent.set_text("Subjects",
                                                        scroll_subject.getX(),
                                                        scroll_subject.getY()-32,
                                                        260,
                                                        26);
        WindowComponent.configure_text(subjects_title,
                                        WindowComponent.default_pressed_button_background,
                                        3,
                                        WindowComponent.get_height(subjects_title));

        // button to create a subject
        JButton add_button = WindowComponent.set_button("+",
                                                        50,
                                                        scroll_subject.getY() + ((scroll_subject.getHeight()-50)/2),
                                                        50,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_text(add_button,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        18);
        WindowComponent.button_event(add_button,
                                    () ->
                                    {
                                        if(ValidationUtils.equals(Management.signed_credits, Management.max_credits))
                                        {
                                            WindowComponent.message_box(this,
                                                                        "You have already reached the credit limit.",
                                                                        "Number limit",
                                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                        else
                                        {
                                            WindowComponent.switch_panel(this, create_subject);
                                        }
                                    },
                                    WindowComponent.default_button_background,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components to the panel
        add(add_button);
        add(subjects_title);
        add(scroll_subject);

        // add the panels to the container
        container.add(this);
        container.add(create_subject);

        // reload the panel to show the changes
        WindowComponent.reload(this);
    }

    // method to get the subject box
    public static JPanel get_subject_box()
    {
        return subject_box;
    }
}
