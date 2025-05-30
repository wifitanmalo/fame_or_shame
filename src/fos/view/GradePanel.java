package fos.view;

// awt imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// swing imports
import javax.swing.*;

// package imports
import fos.data.SubjectDataHandler;
import fos.service.Grade;
import fos.service.Subject;
import fos.service.ValidationUtils;

public class GradePanel extends JPanel
{
    // panel where the grades are shown
    private final JPanel gradeBox;

    // subject/grade objects
    private final Subject subject;
    private final Grade grade;

    // subject id
    private int subjectId;

    // scoer/percentage text boxes
    private JTextField scoreBox, percentageBox;

    // constructor
    public GradePanel(Subject subject, Grade grade, JPanel gradeBox)
    {
        this.subject = subject;
        this.grade = grade;
        this.subjectId = subject.getId();
        this.gradeBox = gradeBox;
        gradePanel();
    }

    // method to initialize a grade panel
    public void gradePanel()
    {
        setPreferredSize(new Dimension(400, 80));
        setBackground(WindowComponent.BUTTON_BACKGROUND);
        setLayout(null);

        // create the text box of the grade score
        scoreBox = WindowComponent.setTextField(94,
                                                    25,
                                                    80,
                                                    30);
        WindowComponent.configureText(scoreBox,
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
                    if (ValidationUtils.isNegative(newScore))
                    {
                        WindowComponent.messageBox(gradeBox,
                                                "Score cannot be negative.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- negative number -----");
                    }
                    else if (ValidationUtils.exceedsLimit(newScore, Subject.MAX_SCORE))
                    {
                        WindowComponent.messageBox(gradeBox,
                                                "Score cannot be higher than " + Subject.MAX_SCORE+ ".",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- limit error -----");
                    }
                    else
                    {
                        // set the new score to the grade
                        GradeMenu.dataHandler.updateScore(grade, newScore, gradeBox);
                    }
                }
                catch (NumberFormatException ex)
                {
                    // set the failed score to the default value
                    GradeMenu.dataHandler.updateScore(grade, 0.0, gradeBox);
                    // reload the grades to show the changes
                    GradeMenu.dataHandler.loadGrades(subject, gradeBox, gradeBox);
                }
                super.keyReleased(e);
            }
        });

        // name of the grade
        JLabel gradeName = WindowComponent.setText(grade.getName(),
                                                    scoreBox.getX(),
                                                    scoreBox.getY() - 26,
                                                    176,
                                                    30);
        WindowComponent.configureText(gradeName,
                                        Color.lightGray,
                                        1,
                                        12);

        // title of the score text box
        JLabel score_text = WindowComponent.setText("Score:",
                                                    WindowComponent.xNegative(scoreBox, 0),
                                                    scoreBox.getY(),
                                                    64,
                                                    30);
        WindowComponent.configureText(score_text,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);

        // create the text box of the grade percentage
        percentageBox = WindowComponent.setTextField(WindowComponent.xPositive(scoreBox, 16),
                                                        scoreBox.getY(),
                                                        scoreBox.getWidth(),
                                                        scoreBox.getHeight());
        WindowComponent.configureText(percentageBox,
                                        WindowComponent.BUTTON_BACKGROUND,
                                        1,
                                        18);
        percentageBox.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    double newPercentage = Double.parseDouble(percentageBox.getText().trim());
                    if (ValidationUtils.isNegative(newPercentage))
                    {
                        WindowComponent.messageBox(gradeBox,
                                                "Percentage cannot be negative.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- negative number -----");
                    }
                    else if (ValidationUtils.exceedsLimit(newPercentage,100.0))
                    {
                        WindowComponent.messageBox(gradeBox,
                                                "Percentage cannot be higher than 100%.",
                                                "Limit error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- limit error -----");
                    }
                    else
                    {
                        // set the new percentage to the grade
                        GradeMenu.dataHandler.updatePercentage(grade, newPercentage, gradeBox);
                    }
                }
                catch (NumberFormatException ex)
                {
                    // sets the failed percentage to the default value
                    GradeMenu.dataHandler.updatePercentage(grade, 0.0, gradeBox);
                    // reload the subjects to show the changes
                    GradeMenu.dataHandler.loadGrades(subject, gradeBox, gradeBox);
                }
                super.keyReleased(e);
            }
        });
        percentageBox.addKeyListener(new KeyAdapter() {
        });

        // percentage symbol of the percentage text box
        JLabel percentage_symbol = WindowComponent.setText("%",
                                                            WindowComponent.xPositive(percentageBox, 14),
                                                            score_text.getY(),
                                                            50,
                                                            30);
        WindowComponent.configureText(percentage_symbol,
                                            WindowComponent.FONT_FOREGROUND,
                                            1,
                                            18);

        // create the button to delete a grade
        JButton delete_button = WindowComponent.setButton("x",
                                                            320,
                                                            15,
                                                            50,
                                                            50,
                                                            WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configureText(delete_button,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(delete_button,
                                    () ->
                                    {
                                        // delete the current grade in the database
                                        GradeMenu.dataHandler.deleteGrade(grade, this);
                                        // load the saved grades in the database
                                        GradeMenu.dataHandler.loadGrades(subject, gradeBox, this);
                                    },
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // add the components to the panel
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
