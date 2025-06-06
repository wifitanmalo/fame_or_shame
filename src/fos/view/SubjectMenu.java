package fos.view;

// awt imports
import java.awt.Color;
import java.awt.Container;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// package imports
import fos.service.ValidationUtils;
import fos.data.SubjectDataHandler;

public class SubjectMenu extends JPanel
{
    // container object
    private final Container container;

    // object to use the subject data
    public static final SubjectDataHandler dataHandler = new SubjectDataHandler();

    // panel where the subjects are shown
    public static final JPanel subjectBox = new JPanel();

    // object of the create subject menu
    private final CreateSubject createSubject;

    // constructor
    public SubjectMenu()
    {
        this.createSubject = new CreateSubject();
        createSubject.setVisible(false);
        this.container = WindowComponent.getContainer();
        initializePanel();
    }

    // method to initialize the main panel
    public void initializePanel()
    {
        // create the main panel
        setLayout(null);
        setBackground(WindowComponent.FRAME_BACKGROUND);
        setBounds(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        // add a vertical scroll bar on the subject box
        JScrollPane scrollSubject = WindowComponent.setScrollBar(subjectBox,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // title of the subjects box
        JLabel subjectsTitle = WindowComponent.setText("Subjects",
                                                        scrollSubject.getX(),
                                                        scrollSubject.getY()-32,
                                                        260,
                                                        26);
        WindowComponent.configureText(subjectsTitle,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.getHeight(subjectsTitle));

        // create the button to create a new subject
        JButton addButton = WindowComponent.setButton("+",
                                                        50,
                                                        scrollSubject.getY() + ((scrollSubject.getHeight()-50)/2),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(addButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(addButton,
                                    () ->
                                    {
                                        if(ValidationUtils.equals(SubjectDataHandler.SIGNED_CREDITS, SubjectDataHandler.MAX_CREDITS))
                                        {
                                            WindowComponent.messageBox(subjectBox,
                                                                        "You have already reached the credit limit.",
                                                                        "Number limit",
                                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                        else
                                        {
                                            WindowComponent.switchPanel(this, createSubject);
                                        }
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components to the panel
        add(addButton);
        add(subjectsTitle);
        add(scrollSubject);

        // add the panels to the container
        container.add(this);
        container.add(createSubject);

        // load the panels of the subjects in the subject box
        dataHandler.loadSubjects();

        // reload the panel to show the changes
        WindowComponent.reload(this);
    }

    // method to cut the first two decimals of a number
    public static double twoDecimals(double number)
    {
        return (int)(number * 100) / 100.0;
    }
}
