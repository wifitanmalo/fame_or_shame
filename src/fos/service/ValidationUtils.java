package fos.service;

// awt import
import java.awt.Container;

// swing imports
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// package imports
import fos.data.SubjectFileHandler;
import fos.view.Main;
import fos.view.SubjectMenu;
import fos.view.WindowComponent;

public class ValidationUtils
{
    // method to verify if a number is negative
    public static boolean isNegative(double number)
    {
        return number < 0;
    }

    // method to verify if a number exceeds the limit
    public static boolean exceedsLimit(double number, double limit)
    {
        return number > limit;
    }

    // method to verify if a number is equal to another
    public static boolean equals(double current_number, double number)
    {
        return current_number == number;
    }

    // method to verify if a text is empty
    public static String emptyText(String text)
    {
        if (text.isEmpty())
        {
            return "0";
        }
        else
        {
            return text;
        }
    }

    // method to verify if a subject already exists
    public static boolean subjectExists(int id)
    {
        for (Subject subject : SubjectMenu.fileHandler.getSubjectsList())
        {
            if (id == subject.getId())
            {
                return true;
            }
        }
        return false;
    }

    // method to verify if subject data is valid
    public static void addValidation(Container container,
                                JTextField idTextBox,
                                JTextField nameTextBox,
                                JTextField creditsTextBox)
    {
        try
        {
            // get subject values from text boxes
            int id = Integer.parseInt(idTextBox.getText().trim());
            String name = nameTextBox.getText().trim();
            int credits = Integer.parseInt(creditsTextBox.getText().trim());

            if(ValidationUtils.subjectExists(id))
            {
                WindowComponent.message_box(container,
                        "ID already exists.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.isNegative(id) || ValidationUtils.isNegative(credits))
            {
                WindowComponent.message_box(container,
                        "ID/Credits cannot be negative.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(name.isEmpty())
            {
                WindowComponent.message_box(container,
                        "Name cannot be empty.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceedsLimit(nameTextBox.getText().length(), 50))
            {
                WindowComponent.message_box(container,
                        "Name cannot be longer than 50 characters.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceedsLimit(SubjectFileHandler.SIGNED_CREDITS + credits,
                    SubjectFileHandler.MAX_CREDITS))
            {
                WindowComponent.message_box(container,
                        "Credits cannot be higher than " + SubjectFileHandler.MAX_CREDITS + ".",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // create the subject on the subject list/file
                Subject newSubject = new Subject(id, name, credits);
                SubjectMenu.fileHandler.createSubject(newSubject);
                SubjectMenu.fileHandler.getSubjectsList().add(newSubject);

                // refresh the subjects on the subject box
                Main.subjectMenu.refreshSubjects();

                // reload the panel to show the new subject
                WindowComponent.reload(Main.subjectMenu);

                // switch to the subject menu
                WindowComponent.switch_panel(container, Main.subjectMenu);
            }
        }
        catch (NumberFormatException e)
        {
            WindowComponent.message_box(container,
                    "ID/Credits values are not valid.",
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
