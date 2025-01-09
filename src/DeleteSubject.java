import javax.swing.*;

public class DeleteSubjectView
{
    private Management manager;
    private JPanel subject_panel;
    private Component component;

    // constructor
    public DeleteSubjectView(JPanel subject_panel,
                            Component component)
    {
        this.subject_panel = subject_panel;
        this.component = component;
    }

    // method to delete a subject
    public Runnable delete_subject(JPanel to_delete)
    {
        return () ->
        {
            int choice = JOptionPane.showConfirmDialog(subject_panel,
                                                        "Are you sure?",
                                                        "Delete subject",
                                                        JOptionPane.YES_NO_OPTION);
            System.out.print(to_delete.getComponents());
            if(choice == JOptionPane.YES_OPTION)
            {
                subject_panel.remove(to_delete);
                component.reload(subject_panel);
            }
        };
    }
}
