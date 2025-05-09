package fos.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class WindowComponent
{
    // default system font
    public static final String DEFAULT_FONT = "Verdana";

    // default color values
    public static final Color FONT_FOREGROUND = Color.decode("#FFFFFF");
    public static final Color BUTTON_BACKGROUND = Color.decode("#3D3D3D");
    public static final Color ENTERED_BUTTON_BACKGROUND = Color.decode("#4D5156");
    public static final Color PRESSED_BUTTON_BACKGROUND = Color.decode("#AAAAAA");
    public static final Color FRAME_BACKGROUND = Color.decode("#1F1F1F");

    // container object
    public static Container current_container;

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
        panel.setBounds(x, y, width, height);
        return panel;
    }

    // method to switch the panel
    public static void switch_panel(Container previous_panel,
                                    Container next_panel)
    {
        previous_panel.setVisible(false);
        next_panel.setVisible(true);
    }

    // method to add a scroll panel
    public static JScrollPane set_scroll_bar(JPanel panel,
                                             int x,
                                             int y,
                                             int width,
                                             int height)
    {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BUTTON_BACKGROUND);

        JScrollPane scroll_panel = new JScrollPane(panel);
        scroll_panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_panel.setBounds(x, y, width, height);
        return scroll_panel;
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

    // method to configure a container
    public static void configure_text(Container container,
                                      Color color,
                                      int style,
                                      int size)
    {
        container.setForeground(color);
        container.setFont(new Font(WindowComponent.DEFAULT_FONT, style, size));
    }

    // method to add a text field
    public static JTextField set_text_field(int x,
                                            int y,
                                            int width,
                                            int height)
    {
        JTextField text_box = new JTextField();
        text_box.setBounds(x, y, width, height);
        return text_box;
    }

    // method to clear the text boxes
    public static void clear_box(JTextField text_box)
    {
        text_box.setText("");
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
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(color);
        button.setBounds(x, y, width, height);
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

    // method to show a message
    public static void message_box(Container container,
                                    String message,
                                    String title,
                                    int option)
    {
        JOptionPane.showMessageDialog(container,
                                    message,
                                    title,
                                    option);
    }

    // method to reload the window items
    public static void reload(Container container)
    {
        container.revalidate();
        container.repaint();
    }

    // methods to move a container in x
    public static int positive_x(Container container, int distance)
    {
        return (container.getX()+container.getWidth()) + distance;
    }
    public static int negative_x(Container container, int distance)
    {
        return (container.getX()-container.getWidth()) - distance;
    }

    // methods to move a container in y
    public static int positive_y(Container container, int distance)
    {
        return (container.getY()-container.getHeight()) - distance;
    }
    public static int negative_y(Container container, int distance)
    {
        return (container.getY()+container.getHeight()) + distance;
    }

    // setters and getters
    public static void set_container(Container container)
    {
        current_container = container;
    }
    public static Container get_container()
    {
        return current_container;
    }

    // method to get the label height
    public static int get_height(Container container)
    {
        return container.getHeight()-6;
    }
}
