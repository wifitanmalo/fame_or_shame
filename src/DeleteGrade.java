import javax.swing.*;

public class DeleteGrade
{
    // grade panel object
    private final GradePanel grade;

    // panel where the grades are added
    private final JPanel grade_box;

    // constructor
    public DeleteGrade(GradePanel grade,
                       JPanel grade_panel)
    {
        this.grade = grade;
        this.grade_box = grade_panel;
        delete_grade();
    }

    // method to delete a grade
    public void delete_grade()
    {
        // remove the subject from the panel and the list
        grade_box.remove(grade);

        // reload the panel to show the changes
        WindowComponent.reload(grade_box);
    }
}
