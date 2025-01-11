import javax.swing.*;

public class DeleteSubject
{
    private final int subject_id;
    private final JPanel to_delete;
    private final JPanel subject_panel;

    // constructor
    public DeleteSubject(int subject_id,
                         JPanel to_delete,
                         JPanel subject_panel)
    {
        this.subject_id = subject_id;
        this.to_delete = to_delete;
        this.subject_panel = subject_panel;
        delete_subject();
    }

    // method to delete a subject
    public void delete_subject()
    {
        int choice = JOptionPane.showConfirmDialog(subject_panel,
                                                    "Are you sure?",
                                                    "Delete subject",
                                                    JOptionPane.YES_NO_OPTION);
        if(choice == JOptionPane.YES_OPTION)
        {
            int index = SubjectMenu.manager.get_index(subject_id);

            // remove the subject from the panel and the list
            subject_panel.remove(to_delete);
            SubjectMenu.manager.delete_subject(index);

            // reload the panel to show the changes
            WindowComponent.reload(subject_panel);
        }
    }
}
