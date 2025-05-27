package fos.data;

import java.awt.Container;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.swing.JOptionPane;
import java.util.ArrayList;

// input/output imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

// package imports
import fos.view.SubjectMenu;
import fos.view.WindowComponent;
import fos.service.Grade;
import fos.service.Subject;
import fos.service.ValidationUtils;

public class GradeFileHandler
{
    private final Container container;
    private final Path gradesFile;

    // constructor
    public GradeFileHandler()
    {
        this.container = WindowComponent.get_container();
        this.gradesFile = findFile();
        ValidationUtils.fileExists(gradesFile, container);
    }

    // method to find the path of the grades.txt file
    private Path findFile()
    {
        try
        {
            Path jarDir = Paths.get(
                    getClass()
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            ).getParent();
            return jarDir.resolve("grades.txt");
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException("Unable to resolve data file location", e);
        }
    }

    // method to load the grades from the grades.txt file
    public void loadGrades()
    {
        ValidationUtils.fileExists(gradesFile, container);
        ArrayList<String> allLines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(gradesFile))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isBlank()) allLines.add(line);
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                                        "Error while reading grades.txt",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Subject subject : SubjectMenu.fileHandler.getSubjectsList())
        {
            subject.setGradesList(new ArrayList<>());
            for (String gradeLine : allLines)
            {
                String[] data = gradeLine.split(",");
                int id = Integer.parseInt(data[0]);
                if (id == subject.getId())
                {
                    double score = Double.parseDouble(data[1]);
                    double percentage = Double.parseDouble(data[2]);
                    Grade grade = new Grade(id, score, percentage);
                    subject.createGrade(grade);
                }
            }
        }
    }

    // method to create a new grade in the grades.txt file
    public void createGrade(Subject subject)
    {
        ValidationUtils.fileExists(gradesFile, container);
        try (BufferedWriter writer = Files.newBufferedWriter(gradesFile, StandardOpenOption.APPEND))
        {
            for (Grade grade : subject.getGradesList())
            {
                String data = grade.getSubjectID() + "," + grade.getScore() + "," + grade.getPercentage();
                writer.write(data);
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                                        "Error while writing grades.txt",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete a grade in the grades.txt file
    public void deleteGrade(Subject subject)
    {
        ValidationUtils.fileExists(gradesFile, container);
        Path tempFile = gradesFile.resolveSibling("grades_temp.txt");
        try (BufferedReader reader = Files.newBufferedReader(gradesFile);
             BufferedWriter writer = Files.newBufferedWriter(tempFile))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                if (id != subject.getId()) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                                        "Error while deleting the file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        replaceFile(tempFile, gradesFile);
    }

    // method to update all the grades of a subject
    public void updateGrade(Subject subject)
    {
        deleteGrade(subject);
        createGrade(subject);
    }

    // method to replace the original file for the temporal one
    private void replaceFile(Path temporalFile, Path originalFile)
    {
        try
        {
            Files.delete(originalFile);
            Files.move(temporalFile, originalFile);
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                                        "Error replacing grade.txt",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }
}
