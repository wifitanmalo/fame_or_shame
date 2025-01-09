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
        setBackground(Component.default_frame_background);
        setBounds(0, 0, Main.width, Main.height);

        JPanel input_panel = Component.set_panel(Component.default_button_background,
                                                    (Main.width-300)/2,
                                                    30,
                                                    300,
                                                    300);

    // back button settings
        JButton back_button = Component.set_button("Back",
                30,
                (Main.height - 84) / 2,
                84,
                84,
                Component.default_button_background);
        Component.configure_container(back_button,
                Component.default_font_foreground,
                1,
                18);
        Component.button_event(back_button,
                back_event,
                Component.default_button_background,
                Component.default_pressed_button_background);

    // add button settings
        JButton add_button = Component.set_button("Add",
                480,
                back_button.getY(),
                84,
                84,
                Component.default_button_background);
        Component.configure_container(add_button,
                Component.default_font_foreground,
                1,
                18);
        Component.button_event(add_button,
                this::add_validation,
                Component.default_button_background,
                Color.decode("#00FF00"));

    // id text settings
        JLabel id_text = Component.set_text("ID",
                                            (input_panel.getWidth()-260)/2,
                                            20,
                                            260,
                                            18);
        Component.configure_container(id_text,
                                        Component.default_font_foreground,
                                        1,
                                        Component.get_height(id_text));

    // id field settings
        id_box = Component.set_text_field(id_text.getX(),
                                            Component.distance_y(id_text,20),
                                            260,
                                            30);
        Component.configure_container(id_box,
                Component.default_button_background,
                1,
                18);

    // id text settings
        JLabel name_text = Component.set_text("Name",
                (input_panel.getWidth()-260)/2,
                Component.distance_y(id_box,20),
                260,
                18);
        Component.configure_container(name_text,
                                        Component.default_font_foreground,
                                        1,
                                        Component.get_height(name_text));

    // name field settings
        name_box = Component.set_text_field(id_text.getX(),
                                            Component.distance_y(name_text,20),
                                            260,
                                            30);
        Component.configure_container(name_box,
                                    Component.default_button_background,
                                    1,
                                    18);

    // credits text settings
        JLabel credits_text = Component.set_text("Credits",
                                                (input_panel.getWidth()-260)/2,
                                                Component.distance_y(name_box,20),
                                                260,
                                                18);
        Component.configure_container(credits_text,
                                        Component.default_font_foreground,
                                        1,
                                        Component.get_height(credits_text));

    // credits filed settings
        credits_box = Component.set_text_field(id_text.getX(),
                                                Component.distance_y(credits_text,20),
                                                260,
                                                30);
        Component.configure_container(credits_box,
                                    Component.default_button_background,
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

            if(!name.isEmpty())
            {
                manager.add_subject(id,
                                    name,
                                    credits);

                new AddSubject(id,
                                name,
                                credits,
                                subject_panel);
                Component.switch_panel(this, main_panel);
                Component.reload(main_panel);
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
