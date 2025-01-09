import javax.swing.*;
import java.awt.*;

public class Components
{
    private JButton button;
    private JLabel label;

    // method to add a label
    public void set_label(String text,
                          int x,
                          int y,
                          int width,
                          int height)
    {
        label = new JLabel(text, SwingConstants.CENTER);
        label.setBounds(x,
                        y,
                        width,
                        height);
        label.setForeground(Color.darkGray);
        label.setFont(new Font("Verdana", 1, 14));
    }
    public JLabel get_label()
    {
        return label;
    }

    // method to add a button
    public void set_button(String text,
                                int x,
                                int y,
                                int width,
                                int height)
    {
        button = new JButton(text);
        button.setBounds(x,
                y,
                width,
                height);
        button.setFont(new Font("Verdana", 1, 16));
    }
    public JButton get_button()
    {
        return button;
    }

    public void set_image(String file_name,
                          int x,
                          int y,
                          int width,
                          int height)
    {
        label = new JLabel(new ImageIcon(file_name));
        label.setBounds(x,
                y,
                width,
                height);
    }
}
