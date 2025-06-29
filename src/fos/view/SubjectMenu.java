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


    // object of the creation subject/setting menu
    private final CreateSubject createSubject;
    private final SettingsMenu settingsMenu;


    // constructor
    public SubjectMenu()
    {
        // run the create subject menu object
        this.createSubject = new CreateSubject();
        createSubject.setVisible(false);
        // run the settings menu object
        this.settingsMenu = new SettingsMenu();
        settingsMenu.setVisible(false);
        // get the current container of the window
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
                                                        (scrollSubject.getX()/2) - 25,
                                                        scrollSubject.getY() + ((scrollSubject.getHeight()/2) - 25),
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
                                        if(ValidationUtils.equals(SubjectDataHandler.SIGNED_CREDITS, SettingsMenu.CURRENT_USER.getMaxCredits()))
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

        // create the button to enter the settings menu of the system
        JButton settingsButton = WindowComponent.setButton("@",
                                                        addButton.getX(),
                                                        WindowComponent.yNegative(addButton, 8),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(settingsButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.buttonEvent(settingsButton,
                                    () ->
                                    {
                                        // create the default user in the database if not exists
                                        if(!SettingsMenu.dataHandler.userExists(2704, scrollSubject))
                                        {
                                            SettingsMenu.dataHandler.createUser(2704,
                                                    3.0,
                                                    5,
                                                    21,
                                                    this);
                                        }

                                        // set the current values to the text fields
                                        SettingsMenu.CURRENT_USER = SettingsMenu.dataHandler.getUser(2704, this);
                                        settingsMenu.setUserValues(SettingsMenu.CURRENT_USER, WindowComponent.BUTTON_BACKGROUND);

                                        // switch to the settings menu
                                        WindowComponent.switchPanel(this, settingsMenu);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // add the components to the panel
        add(addButton);
        add(settingsButton);
        add(subjectsTitle);
        add(scrollSubject);

        // add the panels to the container
        container.add(this);
        container.add(createSubject);
        container.add(settingsMenu);

        // load the panels of the subjects in the subject box
        dataHandler.loadSubjects();

        // reload the panel to show the changes
        WindowComponent.reload(this);
    }
}