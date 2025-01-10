import java.awt.*;
import javax.swing.*;

public class SubjectDetails extends JPanel
{
    // panels
    private final JPanel subject_panel;

    // text boxes
    private JTextField id_box;
    private JTextField name_box;
    private JTextField credits_box;

    // constructor
    public SubjectDetails(JPanel subject_panel)
    {
        this.subject_panel = subject_panel;
        initialize_panel();
    }

    // method to initialize
    private void initialize_panel()
    {
        setLayout(null);
        setBackground(WindowComponent.default_frame_background);
        setBounds(0,
                    0,
                    Main.width,
                    Main.height);

        JPanel input_panel = WindowComponent.set_panel(WindowComponent.default_button_background,
                                                    (Main.width-300)/2,
                                                    30,
                                                    300,
                                                    300);

    // back button settings
        JButton back_button = WindowComponent.set_button("Back",
                                                        34,
                                                        (Main.height - 84) / 2,
                                                        78,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_container(back_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            16);
        WindowComponent.button_event(back_button,
                                    () ->
                                    {
                                        WindowComponent.switch_panel(SubjectMenu.subject_details,
                                                                    SubjectMenu.main_panel);
                                        // clear the text boxes
                                        WindowComponent.clear_box(id_box);
                                        WindowComponent.clear_box(name_box);
                                        WindowComponent.clear_box(credits_box);
                                    },
                                    WindowComponent.default_button_background,
                                    WindowComponent.default_entered_button_background,
                                    WindowComponent.default_pressed_button_background);

    // add button settings
        JButton add_button = WindowComponent.set_button("Add",
                                                        480,
                                                        back_button.getY(),
                                                        78,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_container(add_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            16);
        WindowComponent.button_event(add_button,
                                    this::add_validation,
                                    WindowComponent.default_button_background,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

    // id text settings
        JLabel id_text = WindowComponent.set_text("ID",
                                            (input_panel.getWidth()-260)/2,
                                            20,
                                            260,
                                            22);
        WindowComponent.configure_container(id_text,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(id_text));

    // id field settings
        id_box = WindowComponent.set_text_field(id_text.getX(),
                                            WindowComponent.negative_y(id_text,20),
                                            260,
                                            30);
        WindowComponent.configure_container(id_box,
                WindowComponent.default_button_background,
                1,
                18);

    // id text settings
        JLabel name_text = WindowComponent.set_text("Name",
                (input_panel.getWidth()-260)/2,
                WindowComponent.negative_y(id_box,20),
                260,
                22);
        WindowComponent.configure_container(name_text,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(name_text));

    // name field settings
        name_box = WindowComponent.set_text_field(id_text.getX(),
                                            WindowComponent.negative_y(name_text,20),
                                            260,
                                            30);
        WindowComponent.configure_container(name_box,
                                    WindowComponent.default_button_background,
                                    1,
                                    18);

    // credits text settings
        JLabel credits_text = WindowComponent.set_text("Credits",
                                                (input_panel.getWidth()-260)/2,
                                                WindowComponent.negative_y(name_box,20),
                                                260,
                                                22);
        WindowComponent.configure_container(credits_text,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(credits_text));

    // credits filed settings
        credits_box = WindowComponent.set_text_field(id_text.getX(),
                                                WindowComponent.negative_y(credits_text,20),
                                                260,
                                                30);
        WindowComponent.configure_container(credits_box,
                                    WindowComponent.default_button_background,
                                    1,
                                    18);

        // add the components to the container
        add(input_panel);
        add(back_button);
        add(add_button);

        // add the components to the details panel
        input_panel.add(id_text);
        input_panel.add(id_box);
        input_panel.add(name_text);
        input_panel.add(name_box);
        input_panel.add(credits_text);
        input_panel.add(credits_box);
    }

    private void add_validation()
    {
        try
        {
            // subject values
            int id = Integer.parseInt(id_box.getText().trim());
            String name = name_box.getText().trim();
            int credits = Integer.parseInt(credits_box.getText().trim());

            // subject already exists
            if(SubjectMenu.management.subject_exists(id))
            {
                JOptionPane.showMessageDialog(this,
                                            "Subject already exists.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            // name box is empty
            else if(name.isEmpty())
            {
                JOptionPane.showMessageDialog(this,
                                            "Name cannot be EMPTY.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            // name length is higher than 50 characters
            else if(name_box.getText().length() > 50)
            {
                JOptionPane.showMessageDialog(this,
                        "Name cannot be HIGHER than 50.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            // signed credits are higher than the limit
            else if(Management.signed_credits+credits > Management.max_credits)
            {
                JOptionPane.showMessageDialog(this,
                                            "Credits cannot be HIGHER than " + Management.max_credits + ".",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                SubjectMenu.management.add_subject(id,
                                                    name,
                                                    credits);
                new AddSubject(id,
                                name,
                                credits,
                                subject_panel);
                WindowComponent.switch_panel(this, SubjectMenu.main_panel);
                WindowComponent.reload(SubjectMenu.main_panel);
            }
        }
        // error in the id or credits values
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this,
                                        "ID and Credits must be POSITIVE integers.",
                                        "Input error",
                                        JOptionPane.ERROR_MESSAGE);
        }

        // clear the text boxes
        WindowComponent.clear_box(id_box);
        WindowComponent.clear_box(name_box);
        WindowComponent.clear_box(credits_box);
    }
}
