package fos.view;

// awt imports
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;

// swing imports
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// package imports
import fos.service.Subject;
import fos.data.GradeDataHandler;
import fos.service.ValidationUtils;

public class GradeMenu extends JPanel
{
    // object to use the grades data
    public static final GradeDataHandler dataHandler = new GradeDataHandler();

    // subject object
    private final Subject subject;

    // panel where the grades are shown
    private JPanel gradeBox;
    private JScrollPane scrollGrade;

    // text of the subject score
    private JLabel scoreText;

    // constructor
    public GradeMenu(Subject subject)
    {
        this.subject = subject;
        initializePanel();
    }

    // method to initialize the panel
    public void initializePanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FRAME_BACKGROUND);
        setBounds(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        // create the grade box
        gradeBox = new JPanel();
        scrollGrade = WindowComponent.setScrollBar(gradeBox,
                                                    155,
                                                    60,
                                                    400,
                                                    270);

        // button to back to the subject menu
        JButton backButton = WindowComponent.setButton("Back",
                                                        40,
                                                        WindowComponent.yNegative(scrollGrade, -50),
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(backButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        16);
        WindowComponent.buttonEvent(backButton,
                                    () ->
                                    {
                                        // refresh the subjects in the subject box
                                        SubjectMenu.dataHandler.loadSubjects();
                                        // switch to the subject menu
                                        WindowComponent.switchPanel(this, Main.subjectMenu);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    WindowComponent.ENTERED_BUTTON_BACKGROUND,
                                    WindowComponent.PRESSED_BUTTON_BACKGROUND);

        // button to calculate the total score/percentage
        JButton totalButton = WindowComponent.setButton("Total",
                                                        backButton.getX(),
                                                        WindowComponent.yPositive(backButton, 20),
                                                        78,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(totalButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        14);
        WindowComponent.buttonEvent(totalButton,
                                    () ->
                                    {
                                        // calculate the subject score/percentage in the database
                                        SubjectMenu.dataHandler.updateSubject(subject.getId(), gradeBox);
                                        // get the current values of the subject
                                        double score = SubjectMenu.dataHandler.getTotalScore(subject.getId(), gradeBox);
                                        double percentage = SubjectMenu.dataHandler.getTotalPercentage(subject.getId(), gradeBox);
                                        // update the score text of the menu
                                        setTextScore(score);
                                        // displays a message box with the remaining score to pass
                                        ValidationUtils.riskThreshold(score, percentage, scrollGrade);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // button to create a grade
        JButton addButton = WindowComponent.setButton("+",
                                                        backButton.getX() - 16,
                                                        WindowComponent.yPositive(totalButton, 20),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(addButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(addButton,
                                    () ->
                                    {
                                        // show the box to set the grade name
                                        nameDialogBox(scrollGrade, "Name: ", "Grade name");
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // button to create a grade
        JButton subButton = WindowComponent.setButton("|",
                                                        WindowComponent.xPositive(addButton, 8),
                                                        addButton.getY(),
                                                        50,
                                                        50,
                                                        WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.configureText(subButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(subButton,
                                    () ->
                                    {
                                       System.out.println("add a sub grade");
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // title of the name text box
        JLabel nameText = WindowComponent.setText("Grades",
                                                    scrollGrade.getX(),
                                                    scrollGrade.getY()-32,
                                                    scrollGrade.getWidth(),
                                                    26);
        WindowComponent.configureText(nameText,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.getHeight(nameText));

        // create the text box of the garde score
        scoreText = WindowComponent.setText(String.valueOf(subject.getTotalScore()),
                                            backButton.getX() + 20,
                                            WindowComponent.yPositive(addButton, 4),
                                            80,
                                            20);
        WindowComponent.configureText(scoreText,
                                        WindowComponent.PRESSED_BUTTON_BACKGROUND,
                                        3,
                                        WindowComponent.getHeight(nameText));

        // add the components to the main panel
        add(scrollGrade);
        add(nameText);
        add(backButton);
        add(totalButton);
        add(addButton);
        add(subButton);
        add(scoreText);

        // load the saved grades in the database
        dataHandler.loadGrades(subject, gradeBox);
    }

    // method to show the box where the grade name is set
    public void nameDialogBox(Component container,
                              String text,
                              String title)
    {
        Window window = SwingUtilities.getWindowAncestor(container);
        JDialog dialog = new JDialog(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(250, 100);
        dialog.setLayout(new FlowLayout());
        dialog.setLocationRelativeTo(container);

        JTextField nameField = new JTextField(10);
        WindowComponent.configureText(nameField, WindowComponent.BUTTON_BACKGROUND, 1, 14);
        JButton okButton = new JButton("OK");

        okButton.addActionListener(e ->
        {
            if(nameField.getText().length() <= 30)
            {
                // create a new grade in the database
                dataHandler.createGrade(this.subject.getId(), nameField.getText(), this);
                // load the saved grades in the database
                dataHandler.loadGrades(subject, gradeBox);
                // close the current box
                dialog.dispose();
            }
            else
            {
                // clear the name text field
                nameField.setText("");
                WindowComponent.messageBox(scrollGrade,
                                        "Name cannot be longer than 30 characters.",
                                        "Input error",
                                        JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.add(new JLabel(text));
        dialog.add(nameField);
        dialog.add(okButton);
        dialog.setVisible(true);
    }

    // method to change the color/value of the total score
    public void setTextScore(double score)
    {
        score = SubjectMenu.twoDecimals(score);
        scoreText.setText(String.valueOf(score));
        if(score >= Subject.PASSING_SCORE)
        {
            scoreText.setForeground(Color.decode("#C5EF48")); // green, you pass!
        }
        else
        {
            scoreText.setForeground(Color.decode("#FF6865")); // red, you lose...
        }
        // reload the panel to show the changes
        WindowComponent.reload(scoreText);
    }

    // method to get the subject box
    public JPanel getGradeBox()
    {
        return gradeBox;
    }
}