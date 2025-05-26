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

    // method to initialize
    private void initializePanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FRAME_BACKGROUND);
        setBounds(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        // create the panel with the text boxes
        JPanel inputPanel = WindowComponent.set_panel(WindowComponent.BUTTON_BACKGROUND,
                                                        (this.getWidth()/2) - (300/2),
                                                        (this.getHeight()-340)/2,
                                                        300,
                                                        300);

        // create the back button
        JButton backButton = WindowComponent.set_button("Back",
                                                        inputPanel.getX()/4,
                                                        161,
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(backButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.button_event(backButton,
                                    () ->
                                    {
                                        Main.subjectMenu.refreshSubjects();
                                        WindowComponent.switch_panel(this, Main.subjectMenu);
                                        clearBoxes();
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // create the add button
        JButton addButton = WindowComponent.set_button("Add",
                                                        WindowComponent.positive_x(inputPanel, 29),
                                                        backButton.getY(),
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(addButton,
                                            WindowComponent.FONT_FOREGROUND,
                                            1,
                                            16);
        WindowComponent.button_event(addButton,
                                    () ->
                                    {
                                        ValidationUtils.addValidation(this, idTextBox, nameTextBox, creditsTextBox);
                                        clearBoxes();
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // id title
        JLabel idTitle = WindowComponent.set_text("ID",
                                                    (inputPanel.getWidth()-260)/2,
                                                    20,
                                                    260,
                                                    22);
        WindowComponent.configure_text(idTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.get_height(idTitle));

        // id text box
        idTextBox = WindowComponent.set_text_field(idTitle.getX(),
                                                    WindowComponent.negative_y(idTitle,20),
                                                    260,
                                                    30);
        WindowComponent.configure_text(idTextBox,
                WindowComponent.BUTTON_BACKGROUND,
                1,
                18);

        // name title
        JLabel nameTitle = WindowComponent.set_text("Name",
                                                    (inputPanel.getWidth()-260)/2,
                                                    WindowComponent.negative_y(idTextBox,20),
                                                    260,
                                                    22);
        WindowComponent.configure_text(nameTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.get_height(nameTitle));

        // name text box
        nameTextBox = WindowComponent.set_text_field(idTitle.getX(),
                                                        WindowComponent.negative_y(nameTitle,20),
                                                        260,
                                                        30);
        WindowComponent.configure_text(nameTextBox,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);

        // credits title
        JLabel creditsTitle = WindowComponent.set_text("Credits",
                                                (inputPanel.getWidth()-260)/2,
                                                WindowComponent.negative_y(nameTextBox,20),
                                                260,
                                                22);
        WindowComponent.configure_text(creditsTitle,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.get_height(creditsTitle));

        // credits text box
        creditsTextBox = WindowComponent.set_text_field(idTitle.getX(),
                                                        WindowComponent.negative_y(creditsTitle,20),
                                                        260,
                                                        30);
        WindowComponent.configure_text(creditsTextBox,
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
        WindowComponent.clear_box(idTextBox);
        WindowComponent.clear_box(nameTextBox);
        WindowComponent.clear_box(creditsTextBox);
    }
}
