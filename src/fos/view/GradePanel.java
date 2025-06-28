package fos.view;

// awt imports
import java.awt.Color;
import java.awt.Component;
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

// util imports
import java.util.Objects;

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
                        throw new NumberFormatException("----- negative score -----");
                    }
                    else if (ValidationUtils.exceedsLimit(newScore, SettingsMenu.CURRENT_USER.getMaxScore()))
                    {
                        WindowComponent.messageBox(gradeBox,
                                                String.format("Score cannot exceed %.2f.",
                                                            SettingsMenu.CURRENT_USER.getMaxScore()),
                                                "Input error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- score exceeds 100 -----");
                    }

                    // keep/change the font color to black if hte value is valid
                    scoreField.setForeground(WindowComponent.BUTTON_BACKGROUND);

                    // set the new score to the grade
                    GradeMenu.dataHandler.updateScore(grade, newScore, gradeBox);

                    // verify if the grade is subgrade of another
                    if (grade.getSuperID() != null)
                    {
                        // get the super grade value/object
                        Grade superGrade = GradeMenu.dataHandler.getGrade(grade.getSuperID(), gradeBox);
                        double superValue = GradeMenu.dataHandler.getSuperValue(superGrade, gradeBox);

                        // set the new score to the super grade
                        GradeMenu.dataHandler.updateScore(superGrade, superValue, gradeBox);
                    }
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();

                    // change the font color to red if the value is invalid
                    scoreField.setForeground(Color.decode("#FF746C"));

                    // set the failed score to the default value
                    GradeMenu.dataHandler.updateScore(grade, 0.0, gradeBox);
                }

                // refresh the panels to show the changes
                refreshValue();
                refreshSuperValue();
                super.keyReleased(e);
            }
        });

        // name of the grade
        gradeName = WindowComponent.setText(grade.getName(),
                                                    scoreField.getX(),
                                                    scoreField.getY() - 26,
                                                    156,
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
                        throw new NumberFormatException("----- negative percentage -----");
                    }
                    else if (ValidationUtils.exceedsLimit(newPercentage,100.0))
                    {
                        WindowComponent.messageBox(gradeBox,
                                                "Percentage cannot exceeds 100%.",
                                                "Limit error",
                                                JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- percentage exceeds 100% -----");
                    }

                    // keep/change the font color to dark gray if the value is valid
                    percentageField.setForeground(WindowComponent.BUTTON_BACKGROUND);

                    // set the new percentage to the grade
                    GradeMenu.dataHandler.updatePercentage(grade, newPercentage, gradeBox);
                    GradeMenu.dataHandler.updateSubPercentages(grade, newPercentage, gradeBox);
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();

                    // change the font color to red if the value is invalid
                    percentageField.setForeground(Color.decode("#FF746C"));

                    // sets the failed percentage to the default value
                    GradeMenu.dataHandler.updatePercentage(grade, 0.0, gradeBox);
                    GradeMenu.dataHandler.updateSubPercentages(grade, 0.0, gradeBox);
                }

                // refresh the panel to show the changes
                refreshValue();
                refreshSubValues();
                refreshSuperValue();
                super.keyReleased(e);
            }
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
                                        // delete the subgrades linked to the grade in the database
                                        GradeMenu.dataHandler.deleteAllSubgrades(grade.getID(), this);

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
                                        // show the box to set the sub grade name
                                        WindowComponent.gradeNameDialog("Name: ",
                                                                    "Grade name",
                                                                    subject,
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


    // method to refresh the value of the grade
    public void refreshValue()
    {
        // get the value/super ID of the current grade
        double value = GradeMenu.dataHandler.getValue(grade.getID(), gradeBox);
        Integer idSuper = grade.getSuperID();

        // verify if the grade is subgrade of another
        if (idSuper != null)
        {
            // get the amount of subgrades linked to the super grade
            int amount = GradeMenu.dataHandler.getSubgradesAmount(idSuper, gradeBox);

            // set the grade value divided by the amount of subgrades in the value text
            setValueText(String.format("%.2f", amount > 0 ? value / amount : value));
        }
        else
        {
            // set the normal value of the grade in the value text
            setValueText(String.format("%.2f", value));
        }
    }


    // method to refresh the super grade value
    public void refreshSuperValue()
    {
        Integer idSuper = grade.getSuperID();
        if (idSuper != null)
        {
            for (Component panel : gradeBox.getComponents())
            {
                if (panel instanceof GradePanel superGrade)
                {
                    if (Objects.equals(superGrade.grade.getID(), idSuper))
                    {
                        // get the value of every super grade
                        superGrade.refreshValue();

                        // recursive call to refresh the super grade value
                        superGrade.refreshSuperValue();
                        break;
                    }
                }
            }
        }
    }


    // method to refresh the value of all subgrades linked to a grade
    public void refreshSubValues()
    {
        // cycle through all panels in the grade box
        for (Component panel : gradeBox.getComponents())
        {
            if (panel instanceof GradePanel subgrade)
            {
                if (Objects.equals(subgrade.grade.getSuperID(), this.grade.getID()))
                {
                    // get the value of every subgrade
                    subgrade.refreshValue();

                    // recursive call to refresh subgrades values
                    subgrade.refreshSubValues();
                }
            }
        }
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