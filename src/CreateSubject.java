import java.awt.*;
import javax.swing.*;

public class CreateSubject extends JPanel
{
    // text boxes
    private JTextField id_text_box;
    private JTextField name_text_box;
    private JTextField credits_text_box;

    // constructor
    public CreateSubject()
    {
        initialize_panel();
    }

    // method to initialize
    private void initialize_panel()
    {
        setLayout(null);
        setBackground(WindowComponent.default_frame_background);
        setBounds(0, 0, Main.width, Main.height);

        // create the panel with the text boxes
        JPanel input_panel = WindowComponent.set_panel(WindowComponent.default_button_background,
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
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_text(back_button,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        16);
        WindowComponent.button_event(back_button,
                                    () ->
                                    {
                                        Main.subject_menu.refresh_subjects();
                                        WindowComponent.switch_panel(this, Main.subject_menu);
                                        clear_boxes();
                                    },
                                    WindowComponent.default_button_background,
                                    WindowComponent.default_entered_button_background,
                                    WindowComponent.default_pressed_button_background);

        // create the add button
        JButton add_button = WindowComponent.set_button("Add",
                                                        WindowComponent.positive_x(input_panel, 29),
                                                        back_button.getY(),
                                                        78,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_text(add_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            16);
        WindowComponent.button_event(add_button,
                                    this::add_validation,
                                    WindowComponent.default_button_background,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // id title
        JLabel id_title = WindowComponent.set_text("ID",
                                                    (input_panel.getWidth()-260)/2,
                                                    20,
                                                    260,
                                                    22);
        WindowComponent.configure_text(id_title,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(id_title));

        // id text box
        id_text_box = WindowComponent.set_text_field(id_title.getX(),
                                                    WindowComponent.negative_y(id_title,20),
                                                    260,
                                                    30);
        WindowComponent.configure_text(id_text_box,
                WindowComponent.default_button_background,
                1,
                18);

        // name title
        JLabel name_title = WindowComponent.set_text("Name",
                                                    (input_panel.getWidth()-260)/2,
                                                    WindowComponent.negative_y(id_text_box,20),
                                                    260,
                                                    22);
        WindowComponent.configure_text(name_title,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(name_title));

        // name text box
        name_text_box = WindowComponent.set_text_field(id_title.getX(),
                                                        WindowComponent.negative_y(name_title,20),
                                                        260,
                                                        30);
        WindowComponent.configure_text(name_text_box,
                                    WindowComponent.default_button_background,
                                    1,
                                    18);

        // credits title
        JLabel credits_title = WindowComponent.set_text("Credits",
                                                (input_panel.getWidth()-260)/2,
                                                WindowComponent.negative_y(name_text_box,20),
                                                260,
                                                22);
        WindowComponent.configure_text(credits_title,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(credits_title));

        // credits text box
        credits_text_box = WindowComponent.set_text_field(id_title.getX(),
                                                        WindowComponent.negative_y(credits_title,20),
                                                        260,
                                                        30);
        WindowComponent.configure_text(credits_text_box,
                                    WindowComponent.default_button_background,
                                    1,
                                    18);

        // add the components to the container
        add(input_panel);
        add(back_button);
        add(add_button);

        // add the components to the panel
        input_panel.add(id_title);
        input_panel.add(id_text_box);
        input_panel.add(name_title);
        input_panel.add(name_text_box);
        input_panel.add(credits_title);
        input_panel.add(credits_text_box);
    }

    // method to verify if subject data is valid
    private void add_validation()
    {
        try
        {
            // get subject values from text boxes
            int id = Integer.parseInt(id_text_box.getText().trim());
            String name = name_text_box.getText().trim();
            int credits = Integer.parseInt(credits_text_box.getText().trim());

            if(SubjectMenu.manager.subject_exists(id))
            {
                WindowComponent.message_box(this,
                                            "ID already exists.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.is_negative(id) || ValidationUtils.is_negative(credits))
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
            else if(ValidationUtils.exceeds_limit(name_text_box.getText().length(), 50))
            {
                WindowComponent.message_box(this,
                                            "Name cannot be longer than 50 characters.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceeds_limit(Management.signed_credits + credits,
                                                Management.max_credits))
            {
                WindowComponent.message_box(this,
                                            "Credits cannot be higher than " + Management.max_credits + ".",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // create the subject on the subject list/file
                Subject new_subject = new Subject(id, name, credits);
                SubjectMenu.manager.create_subject(new_subject);
                Main.subject_menu.create_subject(new_subject);

                // refresh the subjects on the subject box
                Main.subject_menu.refresh_subjects();

                // reload the panel to show the new subject
                WindowComponent.reload(Main.subject_menu);

                // switch to the subject menu
                WindowComponent.switch_panel(this, Main.subject_menu);
            }
        }
        catch (NumberFormatException e)
        {
            WindowComponent.message_box(this,
                                        "ID/Credits values are not valid.",
                                        "Input error",
                                        JOptionPane.ERROR_MESSAGE);
        }
        clear_boxes();
    }

    // method to clear all the text boxes
    public void clear_boxes()
    {
        WindowComponent.clear_box(id_text_box);
        WindowComponent.clear_box(name_text_box);
        WindowComponent.clear_box(credits_text_box);
    }
}
