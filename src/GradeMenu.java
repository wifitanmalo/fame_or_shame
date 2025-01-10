import javax.swing.*;
import java.awt.*;

public class GradeMenu extends JPanel
{
    // constructor
    public GradeMenu()
    {
        initialize_panel();
    }

    // method to initialize the main panel
    public void initialize_panel()
    {
        setLayout(null);
        setBackground(WindowComponent.default_frame_background);
        setBounds(0,
                0,
                Main.width,
                Main.height);

        // create the grade panel
        JPanel grade_panel = new JPanel();
        JScrollPane scroll_grade = WindowComponent.set_scroll_bar(grade_panel,
                                                                    155,
                                                                    60,
                                                                    400,
                                                                    270);

        // subject name text settings
        JLabel name_text = WindowComponent.set_text("Grades",
                                                        scroll_grade.getX(),
                                                        scroll_grade.getY()-32,
                                                        scroll_grade.getWidth(),
                                                        26);
        WindowComponent.configure_container(name_text,
                                            WindowComponent.default_pressed_button_background,
                                            3,
                                            WindowComponent.get_height(name_text));

        // back button settings
        JButton back_button = WindowComponent.set_button("Back",
                                                        50,
                                                        WindowComponent.negative_y(scroll_grade, -50),
                                                        78,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_container(back_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            16);
        WindowComponent.button_event(back_button,
                                    () -> WindowComponent.switch_panel(this, SubjectMenu.main_panel),
                                    WindowComponent.default_button_background,
                                    WindowComponent.default_entered_button_background,
                                    WindowComponent.default_pressed_button_background);

        // button to add a subject
        JButton add_button = WindowComponent.set_button("+",
                                                        back_button.getX(),
                                                        WindowComponent.positive_y(back_button, 20),
                                                        50,
                                                        50,
                                                        WindowComponent.default_button_background);
        WindowComponent.configure_container(add_button,
                                            WindowComponent.default_font_foreground,
                                            1,
                                            18);
        WindowComponent.button_event(add_button,
                                    this::button_action,
                                    WindowComponent.default_button_background,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // id text settings
        JLabel score_text = WindowComponent.set_text("5.0",
                                                    back_button.getX(),
                                                    WindowComponent.positive_y(add_button, 20),
                                                    100,
                                                    26);
        WindowComponent.configure_container(score_text,
                                            WindowComponent.default_pressed_button_background,
                                            3,
                                            WindowComponent.get_height(name_text));

        // add the components to the main panel
        add(scroll_grade);
        add(name_text);
        add(back_button);
        add(add_button);
        add(score_text);
    }

    // method to set the add action based in the amount of signed credits
    public void button_action()
    {
        if(Management.signed_credits == Management.max_credits)
        {
            JOptionPane.showMessageDialog(this,
                    "You have already reached the credit limit.",
                    "Number limit",
                    JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            WindowComponent.switch_panel(this, SubjectMenu.main_panel);
        }

    }
}
