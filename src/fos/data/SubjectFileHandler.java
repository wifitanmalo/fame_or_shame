package fos.data;

import java.awt.Container;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// input/output imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

// package imports
import fos.view.WindowComponent;
import fos.service.Subject;
import fos.service.ValidationUtils;

public class SubjectFileHandler
{
    private final Container container;
    public static int SIGNED_CREDITS = 0;
    public static final int MAX_CREDITS = 21;
    private ArrayList<Subject> subjectsList;
    private final Path subjectsFile;

    public SubjectFileHandler()
    {
        this.container = WindowComponent.get_container();
        this.subjectsList = new ArrayList<>();
        this.subjectsFile = findFile();
        ValidationUtils.fileExists(subjectsFile, container);
    }

    // method to find the path of the subjects.txt file
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
            return jarDir.resolve("subjects.txt");
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException("Unable to resolve data file location", e);
        }
    }

    // method to load the subjects from the subjects.txt file
    public void loadSubjects()
    {
        ValidationUtils.fileExists(subjectsFile, container);
        subjectsList.clear();
        SIGNED_CREDITS = 0;

        try (BufferedReader reader = Files.newBufferedReader(subjectsFile))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int credits = Integer.parseInt(data[2]);
                double score = Double.parseDouble(data[3]);
                double evaluated = Double.parseDouble(data[4]);

                Subject subject = new Subject(id, name, credits);
                subject.setTotalScore(score);
                subject.setTotalEvaluated(evaluated);

                SIGNED_CREDITS += credits;
                subjectsList.add(subject);
            }
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                                        "Error while reading subjects.txt",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to create a subject in the subjects.txt file
    public void createSubject(Subject subject)
    {
        ValidationUtils.fileExists(subjectsFile, container);
        try (BufferedWriter writer = Files.newBufferedWriter(subjectsFile, StandardOpenOption.APPEND))
        {
            String data = String.join(",",
                                    String.valueOf(subject.getId()),
                                    subject.getName(),
                                    String.valueOf(subject.getCredits()),
                                    "0.0",
                                    "0.0");
            writer.write(data);
            writer.newLine();
        }
        catch (IOException e)
        {
            WindowComponent.message_box(container,
                                        "Error while writing subjects.txt",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to update a subject in the subjects.txt file
    public void updateSubject(Subject subject, double newScore, double newEvaluated)
    {
        ValidationUtils.fileExists(subjectsFile, container);
        Path tempFile = subjectsFile.resolveSibling("subjects_temp.txt");
        try (BufferedReader reader = Files.newBufferedReader(subjectsFile);
             BufferedWriter writer = Files.newBufferedWriter(tempFile))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (line.isBlank()) continue;
                String[] data = line.split(",");
                int currentId = Integer.parseInt(data[0].trim());
                if (currentId == subject.getId())
                {
                    data[3] = String.valueOf(newScore);
                    data[4] = String.valueOf(newEvaluated);
                    line = String.join(",", data);
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e)
        {
            WindowComponent.message_box(container,
                                        "Error while updating subject",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        replaceFile(tempFile, subjectsFile);
    }

    // method to delete a subject in the subjects.txt file
    public void deleteSubject(Subject subject)
    {
        ValidationUtils.fileExists(subjectsFile, container);
        Path tempFile = subjectsFile.resolveSibling("subjects_temp.txt");
        try (BufferedReader reader = Files.newBufferedReader(subjectsFile);
             BufferedWriter writer = Files.newBufferedWriter(tempFile))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
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
                                        "Error while deleting subject",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        replaceFile(tempFile, subjectsFile);
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
                                        "Error replacing subjects file",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setSubjectsList(ArrayList<Subject> subjectsList) {
        this.subjectsList = subjectsList;
    }
    public ArrayList<Subject> getSubjectsList() {
        return subjectsList;
    }
}
