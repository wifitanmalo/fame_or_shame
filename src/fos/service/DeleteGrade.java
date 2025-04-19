package fos.service;

import javax.swing.*;

import fos.view.GradePanel;
import fos.view.WindowComponent;

public class DeleteGrade
{
    // subject object
    private final Subject subject;

    // grade panel object
    private final GradePanel grade;

    // panel where the grades are added
    private final JPanel grade_box;

    // constructor
    public DeleteGrade(Subject subject,
                       GradePanel grade,
                       JPanel grade_panel)
    {
        this.subject = subject;
        this.grade = grade;
        this.grade_box = grade_panel;
        deleteGrade();
    }

    public void deleteGrade()
    {
        if (subject.getGradesList().contains(grade))
        {
            // delete the grade from the grades panel/list
            grade_box.remove(grade);
            subject.deleteGrade(grade);

            // reload the panel to show the changes
            WindowComponent.reload(grade_box);
        }
    }
}
