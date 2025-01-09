import java.awt.*;
import javax.swing.*;

public class SubjectDetails extends JPanel
{
    // panels
    private final JPanel main_panel;
    private final JPanel subject_panel;

    // text boxes
    private JTextField id_box;
    private JTextField name_box;
    private JTextField credits_box;

    // back event
    private final Runnable back_event;

    private final Management manager;

    // constructor
    public SubjectDetails(Management manager,
                            JPanel main_panel,
                            JPanel subject_panel,
                            Runnable back_event)
    {
        this.manager = manager;
        this.main_panel = main_panel;
        this.subject_panel = subject_panel;
        this.back_event = back_event;
        initialize_panel();
    }

    // method to initialize
    private void initialize_panel()
    {
        setLayout(null);
        setBackground(WindowComponent.default_frame_background);
        setBounds(0, 0, Main.width, Main.height);

        JPanel input_panel = WindowComponent.set_panel(WindowComponent.default_button_background,
                                                    (Main.width-300)/2,
                                                    30,
                                                    300,
                                                    300);

    // back button settings
        JButton back_button = WindowComponent.set_button("Back",
                30,
                (Main.height - 84) / 2,
                84,
                84,
                WindowComponent.default_button_background);
        WindowComponent.configure_container(back_button,
                WindowComponent.default_font_foreground,
                1,
                18);
        WindowComponent.button_event(back_button,
                back_event,
                WindowComponent.default_button_background,
                WindowComponent.default_pressed_button_background);

    // add button settings
        JButton add_button = WindowComponent.set_button("Add",
                480,
                back_button.getY(),
                84,
                84,
                WindowComponent.default_button_background);
        WindowComponent.configure_container(add_button,
                WindowComponent.default_font_foreground,
                1,
                18);
        WindowComponent.button_event(add_button,
                this::add_validation,
                WindowComponent.default_button_background,
                Color.decode("#00FF00"));

    // id text settings
        JLabel id_text = WindowComponent.set_text("ID",
                                            (input_panel.getWidth()-260)/2,
                                            20,
                                            260,
                                            18);
        WindowComponent.configure_container(id_text,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(id_text));

    // id field settings
        id_box = WindowComponent.set_text_field(id_text.getX(),
                                            WindowComponent.distance_y(id_text,20),
                                            260,
                                            30);
        WindowComponent.configure_container(id_box,
                WindowComponent.default_button_background,
                1,
                18);

    // id text settings
        JLabel name_text = WindowComponent.set_text("Name",
                (input_panel.getWidth()-260)/2,
                WindowComponent.distance_y(id_box,20),
                260,
                18);
        WindowComponent.configure_container(name_text,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(name_text));

    // name field settings
        name_box = WindowComponent.set_text_field(id_text.getX(),
                                            WindowComponent.distance_y(name_text,20),
                                            260,
                                            30);
        WindowComponent.configure_container(name_box,
                                    WindowComponent.default_button_background,
                                    1,
                                    18);

    // credits text settings
        JLabel credits_text = WindowComponent.set_text("Credits",
                                                (input_panel.getWidth()-260)/2,
                                                WindowComponent.distance_y(name_box,20),
                                                260,
                                                18);
        WindowComponent.configure_container(credits_text,
                                        WindowComponent.default_font_foreground,
                                        1,
                                        WindowComponent.get_height(credits_text));

    // credits filed settings
        credits_box = WindowComponent.set_text_field(id_text.getX(),
                                                WindowComponent.distance_y(credits_text,20),
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
            int id = Integer.parseInt(id_box.getText().trim());
            String name = name_box.getText().trim();
            int credits = Integer.parseInt(credits_box.getText().trim());

            if(name_box.getText().length() > 50)
            {
                JOptionPane.showMessageDialog(this,
                        "Name cannot be HIGHER than 50",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(name_box.getText().length() > 50)
            {
                JOptionPane.showMessageDialog(this,
                        "Name cannot be HIGHER than 50",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(Management.signed_credits+credits > Management.max_credits)
            {
                JOptionPane.showMessageDialog(this,
                                            "Signed credits cannot be HIGHER than " + Management.max_credits,
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(!name.isEmpty())
            {
                manager.add_subject(id,
                                    name,
                                    credits);

                new AddSubject(id,
                                name,
                                credits,
                                subject_panel);
                WindowComponent.switch_panel(this, main_panel);
                WindowComponent.reload(main_panel);
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                                            "Name cannot be EMPTY.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this,
                                        "ID and Credits must be POSITIVE integers.",
                                        "Input error",
                                        JOptionPane.ERROR_MESSAGE);
        }

        // clear the text boxes
        id_box.setText("");
        name_box.setText("");
        credits_box.setText("");
    }
}
