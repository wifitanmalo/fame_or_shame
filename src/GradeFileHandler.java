import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GradeFileHandler
{
    private final Container container;

    // constructor
    public GradeFileHandler()
    {
        this.container = WindowComponent.get_container();;
    }

    // method to load the grades from the grades file
    public void loadGrades()
    {
        try
        {
            String line;
            ArrayList<String> all_lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("grades.txt"));

            // add on the list all non-empty lines
            while ((line = reader.readLine()) != null)
            {
                all_lines.add(line);
            }
            reader.close();

            // loop to add each grade in its respective list
            for (Subject subject : SubjectMenu.manager.getSubjectsList())
            {
                subject.setGradesList(new ArrayList<>());

                // loop to get the lines that match the subject id
                for (String grade : all_lines)
                {
                    String[] data = grade.split(",");
                    int id = Integer.parseInt(data[0]);

                    if (id == subject.getId())
                    {
                        String score = data[1];
                        String percentage = data[2];

                        // create a new grade and set the score/percentage
                        GradePanel new_grade = new GradePanel(subject);
                        new_grade.setScoreText(score);
                        new_grade.setPercentageText(percentage);  // Assuming you have this method

                        // add the new grade to the grades list
                        subject.createGrade(new_grade);
                    }
                }
            }
        }
        catch (IOException error)
        {
            WindowComponent.message_box(container,
                    "Error while reading the file",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to create a grade in the file
    public void createGrade(Subject subject)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("grades.txt", true));
            for(GradePanel grade : subject.getGradesList())
            {
                String data = grade.getSubjectId()
                                + ","
                                + grade.getScoreText()
                                + ","
                                + grade.getPercentageText();
                writer.write(data);
                writer.newLine();
            }
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

    // method to delete a grade in the file
    public void deleteGrade(Subject subject)
    {
        // file names
        File input = new File("grades.txt");
        File temporal = new File("temporal.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temporal)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                // split the line in commas
                String[] data = line.split(",");
                int current_id = Integer.parseInt(data[0]);

                // ignore the lines with the same id of the subject
                if(current_id != subject.getId())
                {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                    "Error while reading the file",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // replace the original file with the temporal one
        if (!input.delete() || !temporal.renameTo(input))
        {
            WindowComponent.message_box(container,
                    "Error while rewriting file.",
                    "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete the grades in the file
    public void updateGrade(Subject subject)
    {
        deleteGrade(subject);
        createGrade(subject);
    }
}
