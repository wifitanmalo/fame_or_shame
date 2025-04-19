package fos.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import fos.service.DeleteGrade;

public class GradePanel extends JPanel
{
    // panel where the grades are added
    private final JPanel gradeBox;

    // subject object
    private final fos.service.Subject subject;

    // subject id
    private int subjectId;

    // text boxes
    private JTextField scoreBox;
    private JTextField percentageBox;

    // constructor
    public GradePanel(fos.service.Subject subject)
    {
        this.subject = subject;
        this.subjectId = subject.getId();
        this.gradeBox = GradeMenu.getGradeBox();
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
                subject.updateGrade(GradePanel.this);
                GradeMenu.fileHandler.updateGrade(subject);
                super.keyReleased(e);
            }
        });

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
                subject.updateGrade(GradePanel.this);
                GradeMenu.fileHandler.updateGrade(subject);
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
                                        new DeleteGrade(subject, this, gradeBox);
                                        subject.getGradesList().remove(this);
                                        GradeMenu.fileHandler.updateGrade(subject);
                                    },
                                    delete_button.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // add the components on the panel
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
