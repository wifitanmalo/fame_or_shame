import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class WindowComponent
{
    public static final String default_font = "Verdana";
    public static final Color default_font_foreground = Color.decode("#FFFFFF");
    public static final Color default_button_background = Color.decode("#3D3D3D");
    public static final Color default_entered_button_background = Color.decode("#4D5156");
    public static final Color default_pressed_button_background = Color.decode("#AAAAAA");
    public static final Color default_frame_background = Color.decode("#1F1F1F");

    public static Container current_container;

    // method to set a container
    public static void set_container(Container container)
    {
        current_container = container;
    }

    // method to get a container
    public static Container get_container()
    {
        return current_container;
    }

    // method to add a panel
    public static JPanel set_panel(Color color,
                                    int x,
                                    int y,
                                    int width,
                                    int height)
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(color);
        panel.setBounds(x,
                        y,
                        width,
                        height);
        return panel;
    }

    // method to add a scroll panel
    public static JScrollPane set_scroll_bar(JPanel panel,
                                             int x,
                                             int y,
                                             int width,
                                             int height)
    {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(default_button_background);

        JScrollPane scroll_panel = new JScrollPane(panel);
        scroll_panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_panel.setBounds(x,
                                y,
                                width,
                                height);
        return scroll_panel;
    }

    // method to switch the panel
    public static void switch_panel(Container previous_panel,
                                    Container next_panel)
    {
        previous_panel.setVisible(false);
        next_panel.setVisible(true);
    }

    // method to add a label
    public static JLabel set_text(String text,
                                   int x,
                                   int y,
                                   int width,
                                   int height)
    {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        return label;
    }

    // method to get the label height
    public static int get_height(Container container)
    {
        return container.getHeight()-6;
    }

    // method to clear the text boxes
    public static void clear_box(JTextField text_box)
    {
        text_box.setText("");
    }

    // method to change the text and color of the total score
    public static void set_text_score(JLabel label,
                                      double current_score)
    {
        label.setText(String.valueOf(Math.round(current_score*100.0) / 100.0));
        if(current_score >= Subject.passing_score)
        {
            label.setForeground(Color.decode("#C5EF48"));
        }
        else
        {
            label.setForeground(Color.decode("#FF6865"));
        }
        WindowComponent.reload(label);
    }

    // method to configure a container
    public static void configure_container(Container container,
                                            Color color,
                                            int style,
                                            int size)
    {
        container.setForeground(color);
        container.setFont(new Font(WindowComponent.default_font,
                                    style,
                                    size));
    }

    // method to add a text field
    public static JTextField set_text_field(int x,
                                             int y,
                                             int width,
                                             int height)
    {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        return textField;
    }

    // method to add a button
    public static JButton set_button(String text,
                                      int x,
                                      int y,
                                      int width,
                                      int height,
                                      Color color)
    {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        button.setOpaque(true);
        button.setBackground(color);
        return button;
    }

    // method to set a button event
    public static void button_event(JButton button,
                                    Runnable function,
                                    Color idle_color,
                                    Color enter_color,
                                    Color pressed_color)
    {
        button.addActionListener(e -> function.run());

        button.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                button.setBackground(enter_color);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                button.setBackground(idle_color);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                button.setBackground(pressed_color);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                button.setBackground(idle_color);
            }
        });
    }

    // method to move a container in positive x
    public static int positive_x(Container container, int distance)
    {
        return (container.getX()+container.getWidth()) + distance;
    }
    // method to move a container in negative x
    public static int negative_x(Container container, int distance)
    {
        return (container.getX()-container.getWidth()) - distance;
    }

    // method to move a container in positive y
    public static int positive_y(Container container, int distance)
    {
        return (container.getY()-container.getHeight()) - distance;
    }
    // method to move a container in negative y
    public static int negative_y(Container container, int distance)
    {
        return (container.getY()+container.getHeight()) + distance;
    }

    // method to reload the window items
    public static void reload(Container container)
    {
        container.revalidate();
        container.repaint();
    }
}
