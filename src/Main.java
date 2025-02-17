import javax.swing.*;

public class Main extends JFrame
{
    // window size values
    public static final int width = 600, height = 400;

    // SubjectMenu object
    public static SubjectMenu subject_menu;

    // constructor
    public Main()
    {
        initialize_window();
        WindowComponent.set_container(this.getContentPane());
        subject_menu = new SubjectMenu();
    }

    // method to run the main window
    public void initialize_window()
    {
        setTitle("Fame or Shame");
        setBounds(0, 0, width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // here is where the magic starts
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Main::new);
    }
}
