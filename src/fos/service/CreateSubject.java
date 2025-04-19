package fos.service;

import java.awt.*;
import javax.swing.*;

import fos.view.Main;
import fos.view.SubjectMenu;
import fos.view.WindowComponent;

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
        JPanel input_panel = WindowComponent.set_panel(WindowComponent.BUTTON_BACKGROUND,
                                                        (this.getWidth()/2) - (300/2),
                                                        (this.getHeight()-340)/2,
                                                        300,
                                                        300);

        // create the back button
        JButton back_button = WindowComponent.set_button("Back",
                                                        input_panel.getX()/4,
                                                        161,
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(back_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.button_event(back_button,
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
        JButton add_button = WindowComponent.set_button("Add",
                                                        WindowComponent.positive_x(input_panel, 29),
                                                        back_button.getY(),
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configure_text(add_button,
                                            WindowComponent.FONT_FOREGROUND,
                                            1,
                                            16);
        WindowComponent.button_event(add_button,
                                    this::add_validation,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // id title
        JLabel id_title = WindowComponent.set_text("ID",
                                                    (input_panel.getWidth()-260)/2,
                                                    20,
                                                    260,
                                                    22);
        WindowComponent.configure_text(id_title,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.get_height(id_title));

        // id text box
        idTextBox = WindowComponent.set_text_field(id_title.getX(),
                                                    WindowComponent.negative_y(id_title,20),
                                                    260,
                                                    30);
        WindowComponent.configure_text(idTextBox,
                WindowComponent.BUTTON_BACKGROUND,
                1,
                18);

        // name title
        JLabel name_title = WindowComponent.set_text("Name",
                                                    (input_panel.getWidth()-260)/2,
                                                    WindowComponent.negative_y(idTextBox,20),
                                                    260,
                                                    22);
        WindowComponent.configure_text(name_title,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.get_height(name_title));

        // name text box
        nameTextBox = WindowComponent.set_text_field(id_title.getX(),
                                                        WindowComponent.negative_y(name_title,20),
                                                        260,
                                                        30);
        WindowComponent.configure_text(nameTextBox,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);

        // credits title
        JLabel credits_title = WindowComponent.set_text("Credits",
                                                (input_panel.getWidth()-260)/2,
                                                WindowComponent.negative_y(nameTextBox,20),
                                                260,
                                                22);
        WindowComponent.configure_text(credits_title,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        WindowComponent.get_height(credits_title));

        // credits text box
        creditsTextBox = WindowComponent.set_text_field(id_title.getX(),
                                                        WindowComponent.negative_y(credits_title,20),
                                                        260,
                                                        30);
        WindowComponent.configure_text(creditsTextBox,
                                    WindowComponent.BUTTON_BACKGROUND,
                                    1,
                                    18);

        // add the components to the container
        add(input_panel);
        add(back_button);
        add(add_button);

        // add the components to the panel
        input_panel.add(id_title);
        input_panel.add(idTextBox);
        input_panel.add(name_title);
        input_panel.add(nameTextBox);
        input_panel.add(credits_title);
        input_panel.add(creditsTextBox);
    }

    // method to verify if subject data is valid
    private void add_validation()
    {
        try
        {
            // get subject values from text boxes
            int id = Integer.parseInt(idTextBox.getText().trim());
            String name = nameTextBox.getText().trim();
            int credits = Integer.parseInt(creditsTextBox.getText().trim());

            if(SubjectMenu.manager.subjectExists(id))
            {
                WindowComponent.message_box(this,
                                            "ID already exists.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.isNegative(id) || ValidationUtils.isNegative(credits))
            {
                WindowComponent.message_box(this,
                                            "ID/Credits cannot be negative.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(name.isEmpty())
            {
                WindowComponent.message_box(this,
                                            "Name cannot be empty.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceedsLimit(nameTextBox.getText().length(), 50))
            {
                WindowComponent.message_box(this,
                                            "Name cannot be longer than 50 characters.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceedsLimit(Management.signedCredits + credits,
                                                Management.MAX_CREDITS))
            {
                WindowComponent.message_box(this,
                                            "Credits cannot be higher than " + Management.MAX_CREDITS + ".",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // create the subject on the subject list/file
                Subject new_subject = new Subject(id, name, credits);
                SubjectMenu.manager.createSubject(new_subject);
                SubjectMenu.fileHandler.createSubject(new_subject);

                // refresh the subjects on the subject box
                Main.subjectMenu.refreshSubjects();

                // reload the panel to show the new subject
                WindowComponent.reload(Main.subjectMenu);

                // switch to the subject menu
                WindowComponent.switch_panel(this, Main.subjectMenu);
            }
        }
        catch (NumberFormatException e)
        {
            WindowComponent.message_box(this,
                                        "ID/Credits values are not valid.",
                                        "Input error",
                                        JOptionPane.ERROR_MESSAGE);
        }
        clearBoxes();
    }

    // method to clear all the text boxes
    public void clearBoxes()
    {
        WindowComponent.clear_box(idTextBox);
        WindowComponent.clear_box(nameTextBox);
        WindowComponent.clear_box(creditsTextBox);
    }
}
