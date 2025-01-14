import javax.swing.*;

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
        delete_grade();
    }

    public void delete_grade()
    {
        if (subject.get_grades_list().contains(grade))
        {
            // delete the grade from the grades panel/list
            grade_box.remove(grade);
            subject.delete_grade(grade);

            // reload the panel to show the changes
            WindowComponent.reload(grade_box);
        }
    }

}
