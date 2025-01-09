import javax.swing.*;
import java.awt.*;

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
            SubjectMenu.panel_count--;
            int index = SubjectMenu.management.get_index(subject_id);
            int credits = SubjectMenu.management.get_subjects_list().get(index).get_credits();
            Management.signed_credits-=credits;

            SubjectMenu.management.delete_subject(index);
            subject_panel.remove(to_delete);

            // Recarga el panel para reflejar los cambios
            WindowComponent.reload(subject_panel);
        }
    }
}
