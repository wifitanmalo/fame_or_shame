package fos.view;

// awt imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// package imports
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


    // score/percentage text boxes
    private JTextField scoreField, percentageField;


    // delete/subgrade buttons
    private JButton deleteButton, subButton;


    // text of the grade value
    private JLabel gradeName, gradeValue;


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

        // create the text field of the grade score
        scoreField = WindowComponent.setTextField(260/3,
                                                25,
                                                70,
                                                30);
        WindowComponent.configureText(scoreField,
                                        WindowComponent.BUTTON_BACKGROUND,
                                        1,
                                        14);
        scoreField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    double newScore = Double.parseDouble(scoreField.getText().trim());
                    if (ValidationUtils.isNegative(newScore))
                    {
                        WindowComponent.messageBox(gradeBox,
                                                "Score cannot be negative.",
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- negative number -----");
                    }
                    else if (ValidationUtils.exceedsLimit(newScore, SettingsMenu.CURRENT_USER.getMaxScore()))
                    {
                        WindowComponent.messageBox(gradeBox,
                                                "Score cannot be higher than " + SettingsMenu.CURRENT_USER.getMaxScore() + ".",
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
                    ex.printStackTrace();

                    // set the failed score to the default value
                    GradeMenu.dataHandler.updateScore(grade, 0.0, gradeBox);

                    // reload the grades to show the changes
                    GradeMenu.dataHandler.loadGrades(subject, null, gradeBox);
                }

                // verify if the grade is subgrade of another
                if (grade.getSuperID() != null)
                {
                    // get the super grade value/object
                    Grade superGrade = GradeMenu.dataHandler.getGrade(grade.getSuperID(), gradeBox);
                    double value = GradeMenu.dataHandler.getSuperValue(superGrade, gradeBox);

                    // get the amount of subgrades linked to the super grade
                    int amount = GradeMenu.dataHandler.getSubgradesAmount(grade.getSuperID(), gradeBox);

                    // set the new score to the super grade
                    GradeMenu.dataHandler.updateScore(superGrade, value, gradeBox);

                    if (amount > 0)
                    {
                        // set the grade value divided by the amount of subgrades in the value text
                        setValueText(String.format("%.2f", GradeMenu.dataHandler.getValue(grade.getID(), gradeBox)/amount));
                    }
                    else
                    {
                        // set the normal value of the grade in the value text
                        setValueText(String.format("%.2f", GradeMenu.dataHandler.getValue(grade.getID(), gradeBox)));
                    }
                }
                else
                {
                    // set the value of the grade in the value text
                    setValueText(String.format("%.2f", GradeMenu.dataHandler.getValue(grade.getID(), gradeBox)));
                }
                super.keyReleased(e);
            }
        });

        // name of the grade
        gradeName = WindowComponent.setText(grade.getName(),
                                                    scoreField.getX(),
                                                    scoreField.getY() - 26,
                                                    176,
                                                    30);
        WindowComponent.configureText(gradeName,
                                        Color.lightGray,
                                        1,
                                        12);

        // value that adds the grade to the total score
        gradeValue = WindowComponent.setText("0.0",
                                        (scoreField.getX()-64)/ 2,
                                            scoreField.getY(),
                                            64,
                                            30);
        WindowComponent.configureText(gradeValue,
                                    Color.LIGHT_GRAY,
                                    1,
                                    12);
        gradeValue.setHorizontalAlignment(SwingConstants.CENTER);

        // create the text field of the grade percentage
        percentageField = WindowComponent.setTextField(WindowComponent.xPositive(scoreField, 16),
                                                        scoreField.getY(),
                                                        scoreField.getWidth(),
                                                        scoreField.getHeight());
        WindowComponent.configureText(percentageField,
                                        WindowComponent.BUTTON_BACKGROUND,
                                        1,
                                        14);
        percentageField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    double newPercentage = Double.parseDouble(percentageField.getText().trim());
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
                        GradeMenu.dataHandler.updateSubPercentages(grade, newPercentage, gradeBox);
                    }
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();

                    // sets the failed percentage to the default value
                    GradeMenu.dataHandler.updatePercentage(grade, 0.0, gradeBox);
                    GradeMenu.dataHandler.updateSubPercentages(grade, 0.0, gradeBox);

                    // reload the subjects to show the changes
                    GradeMenu.dataHandler.loadGrades(subject, null, gradeBox);
                }
                setValueText(String.format("%.2f", GradeMenu.dataHandler.getValue(grade.getID(), gradeBox)));
                super.keyReleased(e);
            }
        });
        percentageField.addKeyListener(new KeyAdapter() {
        });

        // create the button to delete a grade
        deleteButton = WindowComponent.setButton("x",
                                                        320,
                                                        15,
                                                        50,
                                                        50,
                                                        WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configureText(deleteButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(deleteButton,
                                    () ->
                                    {
                                        // delete the current grade in the database
                                        GradeMenu.dataHandler.deleteGrade(grade, this);

                                        // load the saved grades in the database
                                        GradeMenu.dataHandler.loadGrades(subject, null, gradeBox);
                                    },
                                    WindowComponent.FRAME_BACKGROUND,
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // button to create a grade
        subButton = WindowComponent.setButton("+",
                                                WindowComponent.xNegative(deleteButton, 10),
                                                deleteButton.getY(),
                                                50,
                                                50,
                                                WindowComponent.FRAME_BACKGROUND);
        WindowComponent.configureText(subButton,
                                        WindowComponent.FONT_FOREGROUND,
                                        1,
                                        18);
        WindowComponent.buttonEvent(subButton,
                                    () ->
                                    {
                                        // create a sub grade in the database
                                        GradeMenu.dataHandler.createGrade(grade.getSubjectID(),
                                                                        "",
                                                                        0.0,
                                                                        grade.getPercentage(),
                                                                        grade.getID(),
                                                                        this);

                                        // load the saved grades in the database
                                        GradeMenu.dataHandler.loadGrades(subject, null, gradeBox);
                                    },
                                    WindowComponent.FRAME_BACKGROUND,
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components to the panel
        add(gradeName);
        add(gradeValue);
        add(scoreField);
        add(percentageField);
        add(deleteButton);
        add(subButton);

        // add the panel to the grade box
        gradeBox.add(this);

        // reload the panel to show the changes
        WindowComponent.reload(gradeBox);
    }


    // method to set the super grade configuration
    public void subgradeSettings()
    {
        // changes the panel color to contrast
        this.setBackground(WindowComponent.FRAME_BACKGROUND);

        // name of the grade
        JLabel threadSymbol = WindowComponent.setText("•",
                                                    gradeValue.getX() + 20,
                                                    gradeValue.getY(),
                                                    20,
                                                    30);
        WindowComponent.configureText(threadSymbol,
                                    Color.lightGray,
                                    1,
                                    18);
        threadSymbol.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(threadSymbol);

        // hide the percentage field and subgrade button
        this.remove(percentageField);
        this.remove(subButton);

        // move the components to the right
        scoreField.setBounds(percentageField.getX() - 35,
                            scoreField.getY(),
                            scoreField.getWidth(),
                            scoreField.getHeight());
        gradeName.setBounds(scoreField.getX(),
                            gradeName.getY(),
                            gradeName.getWidth(),
                            gradeName.getHeight());
        gradeValue.setBounds(WindowComponent.xPositive(threadSymbol, 4),
                            scoreField.getY(),
                            64,
                            30);

        // set a new event to the delete button
        deleteButton.setBackground(WindowComponent.BUTTON_BACKGROUND);
        WindowComponent.buttonEvent(deleteButton,
                                    () ->
                                    {
                                        // delete the current sub grade in the database
                                        GradeMenu.dataHandler.deleteSubgrade(grade, this);

                                        // load the saved grades in the database
                                        GradeMenu.dataHandler.loadGrades(subject, null, gradeBox);
                                    },
                                    WindowComponent.BUTTON_BACKGROUND,
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));
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
        scoreField.setText(score);
    }
    public String getScoreText()
    {
        return scoreField.getText().trim();
    }

    public void setScoreField(JTextField scoreField)
    {
        this.scoreField = scoreField;
    }
    public JTextField getScoreField()
    {
        return scoreField;
    }

    public void setPercentageText(String percentage)
    {
        percentageField.setText(percentage);
    }
    public String getPercentageText()
    {
        return percentageField.getText().trim();
    }

    public void setValueText(String value)
    {
        gradeValue.setText(value);
    }
    public JLabel getValueText()
    {
        return gradeValue;
    }
}