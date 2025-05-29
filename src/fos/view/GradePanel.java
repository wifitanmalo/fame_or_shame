package fos.view;

// awt imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// package imports
import fos.service.Grade;
import fos.service.Subject;

public class GradePanel extends JPanel
{
    // panel where the grades are added
    private final JPanel gradeBox;

    // subject and grade objects
    private final Subject subject;
    private final Grade grade;

    // subject id
    private int subjectId;

    // text boxes
    private JTextField scoreBox;
    private JTextField percentageBox;

    // constructor
    public GradePanel(Subject subject, Grade grade, JPanel gradeBox)
    {
        this.subject = subject;
        this.grade = grade;
        this.subjectId = subject.getId();
        this.gradeBox = gradeBox;
        gradePanel();
    }

    // method to create a grade
    public void gradePanel()
    {
        setPreferredSize(new Dimension(400, 80));
        setBackground(WindowComponent.BUTTON_BACKGROUND);
        setLayout(null);

        // text field where you enter the score
        scoreBox = WindowComponent.set_text_field(94,
                                                    25,
                                                    80,
                                                    30);
        WindowComponent.configure_text(scoreBox,
                                        WindowComponent.BUTTON_BACKGROUND,
                                        1,
                                        18);
        scoreBox.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    double newScore = Double.parseDouble(scoreBox.getText().trim());
                    GradeMenu.fileHandler.updateScore(grade, newScore, gradeBox);
                }
                catch (NumberFormatException ex)
                {
                    GradeMenu.fileHandler.updateScore(grade, 0.0, gradeBox);
                }
                super.keyReleased(e);
            }
        });

        // name of the grade
        JLabel gradeName = WindowComponent.set_text(grade.getName(),
                                                    scoreBox.getX(),
                                                    scoreBox.getY() - 26,
                                                    176,
                                                    30);
        WindowComponent.configure_text(gradeName,
                                        Color.lightGray,
                                        1,
                                        12);

        // score text
        JLabel score_text = WindowComponent.set_text("Score:",
                                                    WindowComponent.negative_x(scoreBox, 0),
                                                    scoreBox.getY(),
                                                    64,
                                                    30);
        WindowComponent.configure_text(score_text,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);

        // text field where you enter the percentage
        percentageBox = WindowComponent.set_text_field(WindowComponent.positive_x(scoreBox, 16),
                                                        scoreBox.getY(),
                                                        scoreBox.getWidth(),
                                                        scoreBox.getHeight());
        WindowComponent.configure_text(percentageBox,
                                        WindowComponent.BUTTON_BACKGROUND,
                                        1,
                                        18);
        percentageBox.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                // Update grade object
                try
                {
                    double newPercentage = Double.parseDouble(percentageBox.getText().trim());
                    GradeMenu.fileHandler.updatePercentage(grade, newPercentage, gradeBox);
                }
                catch (NumberFormatException ex)
                {
                    GradeMenu.fileHandler.updatePercentage(grade, 0.0, gradeBox);
                }
                super.keyReleased(e);
            }
        });

        // percentage symbol
        JLabel percentage_symbol = WindowComponent.set_text("%",
                                                            WindowComponent.positive_x(percentageBox, 14),
                                                            score_text.getY(),
                                                            50,
                                                            30);
        WindowComponent.configure_text(percentage_symbol,
                                            WindowComponent.FONT_FOREGROUND,
                                            1,
                                            18);

        // button to delete a grade
        JButton delete_button = WindowComponent.set_button("x",
                                                            320,
                                                            15,
                                                            50,
                                                            50,
                                                            WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configure_text(delete_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.button_event(delete_button,
                                    () ->
                                    {
                                        // delete the current grade in the database
                                        GradeMenu.fileHandler.deleteGrade(grade, this);
                                        // load the saved grades in the database
                                        GradeMenu.fileHandler.loadGrades(subject, gradeBox, this);
                                    },
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // add the components on the panel
        add(gradeName);
        add(score_text);
        add(scoreBox);
        add(percentageBox);
        add(percentage_symbol);
        add(delete_button);

        // add the panel to the grade box
        gradeBox.add(this);

        // reload the panel to show the changes
        WindowComponent.reload(gradeBox);
    }

    // setters and getters
    public void setSubjectId(int id)
    {
        this.subjectId = subject.getId();
    }
    public int getSubjectId()
    {
        return subjectId;
    }

    public void setScoreText(String score)
    {
        scoreBox.setText(score);
    }
    public String getScoreText()
    {
        return scoreBox.getText().trim();
    }

    public void setPercentageText(String percentage)
    {
        percentageBox.setText(percentage);
    }
    public String getPercentageText()
    {
        return percentageBox.getText().trim();
    }
}
