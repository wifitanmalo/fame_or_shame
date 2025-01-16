import java.io.*;
import javax.swing.*;

public class DeleteSubject
{
    // subject object
    private final Subject subject;

    // subject panels
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
        if(choice == JOptionPane.YES_OPTION && SubjectMenu.manager.get_subjects_list().contains(subject))
        {
            // remove the subject from the box/list/file
            subject_box.remove(to_delete);
            SubjectMenu.manager.delete_subject(subject);
            delete_from_file(subject);

            // reload the subject box to show the changes
            WindowComponent.reload(subject_box);
        }
    }

    // method to delete a subject from the subjects file
    public void delete_from_file(Subject subject)
    {
        File file = new File("subjects.txt");
        File temporal = new File("temporal.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temporal)))
        {

            String line;
            String grade_data = subject.get_id()
                                + "," + subject.get_name()
                                + "," + subject.get_credits()
                                + "," + subject.get_total_score()
                                + "," + subject.get_total_evaluated();

            while ((line = reader.readLine()) != null)
            {
                if (!line.trim().equals(grade_data))
                {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(Main.subject_menu,
                                        "Error while reading the file.",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }

        // replace the original file with the temporal one
        if (!file.delete() || !temporal.renameTo(file))
        {
            WindowComponent.message_box(Main.subject_menu,
                                        "Error while rewriting file.",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

}
