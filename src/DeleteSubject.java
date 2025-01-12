import javax.swing.*;

public class DeleteSubject
{
    private final Subject subject;
    private final JPanel to_delete;
    private final JPanel subject_box;

    // constructor
    public DeleteSubject(Subject subject,
                        JPanel to_delete)
    {
        this.subject = subject;
        this.to_delete = to_delete;
        this.subject_box = SubjectMenu.get_subject_box();
        delete_subject();
    }

    // method to delete a subject
    public void delete_subject()
    {
        int choice = JOptionPane.showConfirmDialog(subject_box,
                                                    "You want to delete this subject?",
                                                    "Delete subject",
                                                    JOptionPane.YES_NO_OPTION);
        if(choice == JOptionPane.YES_OPTION)
        {
            // remove the subject from the panel/list
            subject_box.remove(to_delete);
            SubjectMenu.manager.delete_subject(subject);

            // reload the subject box to show the changes
            WindowComponent.reload(subject_box);
        }
    }
}
