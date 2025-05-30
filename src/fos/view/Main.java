package fos.view;

// swing imports
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame
{
    // window size values
    public static final int WINDOW_WIDTH=600, WINDOW_HEIGHT=400;

    // subject menu object
    public static SubjectMenu subjectMenu;

    // constructor
    public Main()
    {
        initializeWindow();
        WindowComponent.setContainer(this.getContentPane());
        subjectMenu = new SubjectMenu();
    }

    // method to run the main window
    public void initializeWindow()
    {
        setTitle("Fame or Shame");
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
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
