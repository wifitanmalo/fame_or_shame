import javax.swing.*;

public class DeleteSubject
{
    private Management manager;
    private final JPanel to_delete;
    private final JPanel subject_panel;

    // constructor
    public DeleteSubject(JPanel to_delete,
                         JPanel subject_panel)
    {
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
            subject_panel.remove(to_delete);
            Component.reload(subject_panel);
        }
    }
}
