package fos.view;

import java.awt.*;
import javax.swing.*;

import fos.service.CreateSubject;
import fos.service.Management;
import fos.service.ValidationUtils;
import fos.data.SubjectFileHandler;

public class SubjectMenu extends JPanel
{
    // container object
    private final Container container;

    // static objects
    public static final Management manager = new Management();
    public static final SubjectFileHandler fileHandler = new SubjectFileHandler();

    // subject panels
    private static final JPanel subjectBox = new JPanel();
    private final CreateSubject createSubject;

    // constructor
    public SubjectMenu()
    {
        this.createSubject = new CreateSubject();
        createSubject.setVisible(false);
        this.container = WindowComponent.get_container();
        initialize_panel();
    }

    // method to initialize the main panel
    public void initialize_panel()
    {
        // create the main panel
        setLayout(null);
        setBackground(WindowComponent.FRAME_BACKGROUND);
        setBounds(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        // add a vertical scroll bar on the subject box
        JScrollPane scroll_subject = WindowComponent.set_scroll_bar(subjectBox,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // subjects title
        JLabel subjects_title = WindowComponent.set_text("Subjects",
                                                        scroll_subject.getX(),
                                                        scroll_subject.getY()-32,
                                                        260,
                                                        26);
        WindowComponent.configure_text(subjects_title,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.get_height(subjects_title));

        // button to create a subject
        JButton add_button = WindowComponent.set_button("+",
                                                        50,
                                                        scroll_subject.getY() + ((scroll_subject.getHeight()-50)/2),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(add_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.button_event(add_button,
                                    () ->
                                    {
                                        if(ValidationUtils.equals(Management.signedCredits, Management.MAX_CREDITS))
                                        {
                                            WindowComponent.message_box(this,
                                                                        "You have already reached the credit limit.",
                                                                        "Number limit",
                                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                        else
                                        {
                                            WindowComponent.switch_panel(this, createSubject);
                                        }
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components to the panel
        add(add_button);
        add(subjects_title);
        add(scroll_subject);

        // add the panels to the container
        container.add(this);
        container.add(createSubject);

        // load the saved subjects
        fileHandler.loadSubjects();

        // create the panels of the subjects in the subject box
        refreshSubjects();

        // reload the panel to show the changes
        WindowComponent.reload(this);
    }

    // method to refresh the grades on the subject box
    public void refreshSubjects()
    {
        subjectBox.removeAll();
        for(fos.service.Subject subject : manager.getSubjectsList())
        {
            // create the panel in the subject box
            SubjectPanel current_panel = new SubjectPanel(subject);
            current_panel.set_score_label(subject.getTotalScore());
            current_panel.set_evaluated_label(subject.getTotalEvaluated());
        }
        // reload the panel to show the changes
        WindowComponent.reload(subjectBox);
    }

    // method to cut the first two decimals of a number
    public static double twoDecimals(double number)
    {
        return (int)(number * 100) / 100.0;
    }

    // method to get the subject box
    public static JPanel getSubjectBox()
    {
        return subjectBox;
    }
}
