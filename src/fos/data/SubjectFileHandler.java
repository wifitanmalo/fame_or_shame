package fos.data;

import fos.view.Main;
import fos.view.SubjectMenu;
import fos.view.WindowComponent;
import fos.service.Subject;
import fos.service.Management;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SubjectFileHandler
{
    private final Container container;
    private final Management manager;

    // constructor
    public SubjectFileHandler()
    {
        this.container = WindowComponent.get_container();;
        this.manager = SubjectMenu.manager;
    }

    // method to load the grades from the subjects file
    public void loadSubjects()
    {
        try
        {
            String line;
            BufferedReader read = new BufferedReader(new FileReader("subjects.txt"));
            while ((line = read.readLine()) != null)
            {
                String[] data = line.split(",");

                // subject values
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int credits = Integer.parseInt(data[2]);
                double score = Double.parseDouble(data[3]);
                double evaluated = Double.parseDouble(data[4]);

                // create the subject with the line values
                Subject subject = new Subject(id, name, credits);
                subject.setTotalScore(score);
                subject.setTotalEvaluated(evaluated);

                // create the subject in the subject panel/list
                manager.createSubject(subject);
            }
            read.close();
        }
        catch (IOException error)
        {
            WindowComponent.message_box(container,
                    "Error while reading the file",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to create a subject in the file
    public void createSubject(Subject subject)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("subjects.txt", true));
            String data = subject.getId()
                    + ","
                    + subject.getName()
                    + ","
                    + subject.getCredits()
                    + ",0.0,0.0";
            writer.write(data);
            writer.newLine();
            writer.close();
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                    "Error while writing the file",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to modify a subject in the file
    public void updateSubject(Subject subject,
                              double new_score,
                              double new_evaluated)
    {
        // file names
        File file = new File("subjects.txt");
        File temporal = new File("temporal.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temporal)))
        {
            String current_line;
            while ((current_line = reader.readLine()) != null)
            {
                // split the line in commas
                String[] data = current_line.split(",");
                int current_id = Integer.parseInt(data[0].trim());

                // get the score/evaluated of the line if the id matches
                if (current_id == subject.getId())
                {
                    data[3] = String.valueOf(new_score);
                    data[4] = String.valueOf(new_evaluated);
                    current_line = String.join(",", data);
                }
                writer.write(current_line);
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                    "Error while writing the file",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // replace the original file with the temporal one
        if (!file.delete() || !temporal.renameTo(file))
        {
            WindowComponent.message_box(container,
                    "Error while updating the file",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete a subject from the subjects file
    public void deleteSubject(Subject subject)
    {
        File file = new File("subjects.txt");
        File temporal = new File("temporal.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temporal)))
        {

            String line;
            String grade_data = subject.getId()
                    + "," + subject.getName()
                    + "," + subject.getCredits()
                    + "," + subject.getTotalScore()
                    + "," + subject.getTotalEvaluated();

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
            WindowComponent.message_box(Main.subjectMenu,
                    "Error while reading the file.",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // replace the original file with the temporal one
        if (!file.delete() || !temporal.renameTo(file))
        {
            WindowComponent.message_box(Main.subjectMenu,
                    "Error while rewriting file.",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
