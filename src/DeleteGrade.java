import javax.swing.*;

public class DeleteGrade
{
    private final JPanel to_delete;
    private final JPanel grade_panel;
    private final AddGrade grade;
    private final GradeMenu grade_menu;

    // constructor
    public DeleteGrade(AddGrade grade,
                       GradeMenu grade_menu,
                       JPanel to_delete,
                        JPanel grade_panel)
    {
        this.grade = grade;
        this.grade_menu = grade_menu;
        this.to_delete = to_delete;
        this.grade_panel = grade_panel;
        delete_grade();
    }

    // method to delete a subject
    public void delete_grade()
    {
        // remove the subject from the panel and the list
        grade_panel.remove(to_delete);

        // remove the grade from the list
        grade_menu.remove_grade(grade);

        // reload the panel to show the changes
        WindowComponent.reload(grade_panel);
    }
}
