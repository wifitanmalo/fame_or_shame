package fos.view;

// awt imports
import java.awt.Color;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// package imports
import fos.service.ValidationUtils;


public class CreateSubject extends JPanel
{
    // text boxes
    private JTextField idField, nameField, creditsField;


    // constructor
    public CreateSubject()
    {
        initializePanel();
    }


    // method to initialize the subject panel
    private void initializePanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FRAME_BACKGROUND);
        setBounds(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        // create the panel with the text boxes
        JPanel inputPanel = WindowComponent.setPanel(WindowComponent.BUTTON_BACKGROUND,
                                                    (this.getWidth()/2) - (216/2),
                                                    (this.getHeight()-270)/2,
                                                    200,
                                                    240);

        // create the back button
        JButton backButton = WindowComponent.setButton("<",
                                                        (inputPanel.getX()-50)/2,
                                                        160,
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(backButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.buttonEvent(backButton,
                                    () ->
                                    {
                                        // refresh the subjects on the subject box
                                        SubjectMenu.dataHandler.loadSubjects();

                                        // switch to the subject menu
                                        WindowComponent.switchPanel(this, Main.subjectMenu);

                                        // clear all the text fields
                                        clearBoxes();
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // create the add button(inputPanel.getX()-50)/2
        JButton addButton = WindowComponent.setButton("Add",
                                                        392 + ((inputPanel.getX()-78)/2),
                                                        backButton.getY(),
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(addButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.buttonEvent(addButton,
                                    () ->
                                    {
                                        // verify if the subject has valid information
                                        ValidationUtils.subjectValidation(this, idField, nameField, creditsField);

                                        // clear all the text fields
                                        clearBoxes();
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // title of the id text box
        JLabel idTitle = WindowComponent.setText("ID",
                                                    (inputPanel.getWidth()-130)/2,
                                                    20,
                                                    260,
                                                    22);
        WindowComponent.configureText(idTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(idTitle));

        // create the text box of the subject ID
        idField = WindowComponent.setTextField(idTitle.getX(),
                                                WindowComponent.yNegative(idTitle,4),
                                                130,
                                                30);
        WindowComponent.configureText(idField,
                                        WindowComponent.BUTTON_BACKGROUND,
                                        1,
                                        20);

        // title of the name text box
        JLabel nameTitle = WindowComponent.setText("Name",
                                                    idTitle.getX(),
                                                    WindowComponent.yNegative(idField,10),
                                                    130,
                                                    22);
        WindowComponent.configureText(nameTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(nameTitle));

        // create the text box of the subject name
        nameField = WindowComponent.setTextField(nameTitle.getX(),
                                                WindowComponent.yNegative(nameTitle,4),
                                                130,
                                                30);
        WindowComponent.configureText(nameField,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    14);

        // title of the credits text box
        JLabel creditsTitle = WindowComponent.setText("Credits",
                                                    nameTitle.getX(),
                                                    WindowComponent.yNegative(nameField,10),
                                                    130,
                                                    22);
        WindowComponent.configureText(creditsTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(creditsTitle));

        // create the text box of the subject credits
        creditsField = WindowComponent.setTextField(creditsTitle.getX(),
                                                    WindowComponent.yNegative(creditsTitle,4),
                                                    130,
                                                    30);
        WindowComponent.configureText(creditsField,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    20);

        // add the components to the container
        add(inputPanel);
        add(backButton);
        add(addButton);

        // add the components to the panel
        inputPanel.add(idTitle);
        inputPanel.add(idField);
        inputPanel.add(nameTitle);
        inputPanel.add(nameField);
        inputPanel.add(creditsTitle);
        inputPanel.add(creditsField);
    }


    // method to clear all the text boxes
    public void clearBoxes()
    {
        WindowComponent.clearBox(idField);
        WindowComponent.clearBox(nameField);
        WindowComponent.clearBox(creditsField);
    }
}