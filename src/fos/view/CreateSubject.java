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
    private JTextField idTextBox;
    private JTextField nameTextBox;
    private JTextField creditsTextBox;

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
                                                        (this.getWidth()/2) - (300/2),
                                                        (this.getHeight()-340)/2,
                                                        300,
                                                        300);

        // create the back button
        JButton backButton = WindowComponent.setButton("Back",
                                                        inputPanel.getX()/4,
                                                        161,
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(backButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.buttonEvent(backButton,
                                    () ->
                                    {
                                        SubjectMenu.dataHandler.loadSubjects(this);
                                        WindowComponent.switchPanel(this, Main.subjectMenu);
                                        clearBoxes();
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // create the add button
        JButton addButton = WindowComponent.setButton("Add",
                                                        WindowComponent.xPositive(inputPanel, 29),
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
                                        ValidationUtils.subjectValidation(this, idTextBox, nameTextBox, creditsTextBox);
                                        clearBoxes();
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // title of the id text box
        JLabel idTitle = WindowComponent.setText("ID",
                                                    (inputPanel.getWidth()-260)/2,
                                                    20,
                                                    260,
                                                    22);
        WindowComponent.configureText(idTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(idTitle));

        // create the text box of the subject ID
        idTextBox = WindowComponent.setTextField(idTitle.getX(),
                                                    WindowComponent.yNegative(idTitle,20),
                                                    260,
                                                    30);
        WindowComponent.configureText(idTextBox,
                WindowComponent.BUTTON_BACKGROUND,
                1,
                18);

        // title of the name text box
        JLabel nameTitle = WindowComponent.setText("Name",
                                                    (inputPanel.getWidth()-260)/2,
                                                    WindowComponent.yNegative(idTextBox,20),
                                                    260,
                                                    22);
        WindowComponent.configureText(nameTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(nameTitle));

        // create the text box of the subject name
        nameTextBox = WindowComponent.setTextField(idTitle.getX(),
                                                        WindowComponent.yNegative(nameTitle,20),
                                                        260,
                                                        30);
        WindowComponent.configureText(nameTextBox,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);

        // title of the credits text box
        JLabel creditsTitle = WindowComponent.setText("Credits",
                                                (inputPanel.getWidth()-260)/2,
                                                WindowComponent.yNegative(nameTextBox,20),
                                                260,
                                                22);
        WindowComponent.configureText(creditsTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.getHeight(creditsTitle));

        // create the text box of the subject credits
        creditsTextBox = WindowComponent.setTextField(idTitle.getX(),
                                                        WindowComponent.yNegative(creditsTitle,20),
                                                        260,
                                                        30);
        WindowComponent.configureText(creditsTextBox,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);

        // add the components to the container
        add(inputPanel);
        add(backButton);
        add(addButton);

        // add the components to the panel
        inputPanel.add(idTitle);
        inputPanel.add(idTextBox);
        inputPanel.add(nameTitle);
        inputPanel.add(nameTextBox);
        inputPanel.add(creditsTitle);
        inputPanel.add(creditsTextBox);
    }

    // method to clear all the text boxes
    public void clearBoxes()
    {
        WindowComponent.clearBox(idTextBox);
        WindowComponent.clearBox(nameTextBox);
        WindowComponent.clearBox(creditsTextBox);
    }
}
