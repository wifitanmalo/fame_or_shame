import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class WindowComponent
{
    public static final String default_font = "Verdana";
    public static final Color default_font_foreground = Color.decode("#FFFFFF");
    public static final Color default_button_background = Color.decode("#3D3D3D");
    public static final Color default_pressed_button_background = Color.decode("#4D5156");
    public static final Color default_frame_background = Color.decode("#1F1F1F");

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
        return container.getHeight()-4;
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

    // method to set the event of the button
    public static void button_event(JButton button,
                                    Runnable function,
                                    Color idle_color,
                                    Color pressed_color)
    {
        button.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                function.run();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                button.setBackground(idle_color);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                button.setBackground(pressed_color);
            }
        });
    }

    // method to move a container in x
    public static int distance_x(Container container, int distance)
    {
        return (container.getX()+container.getWidth()) + distance;
    }

    // method to move a container in y
    public static int distance_y(Container container, int distance)
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