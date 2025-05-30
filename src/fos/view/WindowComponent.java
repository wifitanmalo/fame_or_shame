package fos.view;

// awt imports
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

// swing imports
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
    public static Container currentContainer;

    // method to add a panel
    public static JPanel setPanel(Color color,
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

    // method to switch to another panel
    public static void switchPanel(Container previous_panel,
                                   Container next_panel)
    {
        previous_panel.setVisible(false);
        next_panel.setVisible(true);
    }

    // method to add a scroll bar to a panel
    public static JScrollPane setScrollBar(JPanel panel,
                                           int x,
                                           int y,
                                           int width,
                                           int height)
    {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BUTTON_BACKGROUND);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBounds(x, y, width, height);
        return scrollPanel;
    }

    // method to add a label
    public static JLabel setText(String text,
                                 int x,
                                 int y,
                                 int width,
                                 int height)
    {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        return label;
    }

    // method to configure the text settings of a container
    public static void configureText(Container container,
                                     Color color,
                                     int style,
                                     int size)
    {
        container.setForeground(color);
        container.setFont(new Font(WindowComponent.DEFAULT_FONT, style, size));
    }

    // method to add a text field
    public static JTextField setTextField(int x,
                                          int y,
                                          int width,
                                          int height)
    {
        JTextField textBox = new JTextField();
        textBox.setBounds(x, y, width, height);
        return textBox;
    }

    // method to clear the text boxes
    public static void clearBox(JTextField textBox)
    {
        textBox.setText("");
    }

    // method to add a button
    public static JButton setButton(String text,
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
    public static void buttonEvent(JButton button,
                                   Runnable function,
                                   Color idleColor,
                                   Color enterColor,
                                   Color pressedColor)
    {
        button.addActionListener(e -> function.run());

        button.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                button.setBackground(enterColor);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                button.setBackground(idleColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                button.setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                button.setBackground(idleColor);
            }
        });
    }

    // method to show a message box
    public static void messageBox(Container container,
                                  String message,
                                  String title,
                                  int option)
    {
        JOptionPane.showMessageDialog(container,
                                    message,
                                    title,
                                    option);
    }

    // method to reload the window components
    public static void reload(Container container)
    {
        container.revalidate();
        container.repaint();
    }

    // methods to move a container in x
    public static int xPositive(Container container, int distance)
    {
        return (container.getX()+container.getWidth()) + distance;
    }
    public static int xNegative(Container container, int distance)
    {
        return (container.getX()-container.getWidth()) - distance;
    }

    // methods to move a container in y
    public static int yPositive(Container container, int distance)
    {
        return (container.getY()-container.getHeight()) - distance;
    }
    public static int yNegative(Container container, int distance)
    {
        return (container.getY()+container.getHeight()) + distance;
    }

    // setters and getters
    public static void setContainer(Container container)
    {
        currentContainer = container;
    }
    public static Container getContainer()
    {
        return currentContainer;
    }

    // method to get the label height
    public static int getHeight(Container container)
    {
        return container.getHeight()-6;
    }
}