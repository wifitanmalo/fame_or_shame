package fos.service;

import javax.swing.*;

import fos.view.SubjectMenu;
import fos.view.WindowComponent;

public class DeleteSubject
{
    // subject object
    private final Subject subject;

    // subject panels
    private final JPanel toDelete;
    private final JPanel subjectBox;

    // constructor
    public DeleteSubject(Subject subject,
                         JPanel toDelete)
    {
        this.subject = subject;
        this.toDelete = toDelete;
        this.subjectBox = SubjectMenu.getSubjectBox();
        deleteSubject();
    }

    // method to delete a subject
    public void deleteSubject()
    {
        int choice = JOptionPane.showConfirmDialog(subjectBox,
                                                    "You want to delete this subject?",
                                                    "Delete subject",
                                                    JOptionPane.YES_NO_OPTION);
        if(choice == JOptionPane.YES_OPTION && SubjectMenu.manager.getSubjectsList().contains(subject))
        {
            // remove the subject from the box/list/file
            subjectBox.remove(toDelete);
            SubjectMenu.manager.deleteSubject(subject);
            SubjectMenu.fileHandler.deleteSubject(subject);

            // reload the subject box to show the changes
            WindowComponent.reload(subjectBox);
        }
    }
}
