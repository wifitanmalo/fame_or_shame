package fos.data;

// awt import
import java.awt.Container;

// sql imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// swing imports
import javax.swing.JPanel;
import javax.swing.JOptionPane;

// package imports
import fos.view.GradePanel;
import fos.view.WindowComponent;
import fos.service.Grade;
import fos.service.Subject;
import fos.service.ValidationUtils;

public class GradeDataHandler
{
    // constructor
    public GradeDataHandler()
    {

    }

    // method to load the grades from the database
    public void loadGrades(Subject subject, JPanel gradeBox, Container container)
    {
        String query = "SELECT * FROM Grade WHERE idSubject = ? AND idSuperGrade IS NULL";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement state = isConnected.prepareStatement(query))
        {
            state.setInt(1, subject.getId());
            ResultSet currentGrade = state.executeQuery();
            // clear the grades box
            gradeBox.removeAll();
            while (currentGrade.next())
            {
                int id = currentGrade.getInt("id");
                int idSubject = currentGrade.getInt("idSubject");
                String name = currentGrade.getString("name");
                double score = currentGrade.getDouble("score");
                double percentage = currentGrade.getDouble("percentage");

                // Construir objeto Grade con toda la información
                Grade newGrade = new Grade(id, idSubject, name, score, percentage);

                // Crear y agregar panel visual
                GradePanel newPanel = new GradePanel(subject, newGrade, gradeBox);
                newPanel.setScoreText(String.valueOf(score));
                newPanel.setPercentageText(String.valueOf(percentage));
            }
            // reload the panel to show the changes
            WindowComponent.reload(gradeBox);

        }
        catch (SQLException e)
        {
            WindowComponent.messageBox(container,
                                        "Error while reading the database.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to create a new grade in the grades.txt file
    public void createGrade(int idSubject, String name, Container container)
    {
        String query = "INSERT INTO Grade(idSubject, name) VALUES(?, ?)";
        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement create = conn.prepareStatement(query))
        {
            create.setInt(1, idSubject);
            create.setString(2, name);
            create.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.messageBox(container,
                                        "Error while creating the grade.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete a grade in the database
    public void deleteGrade(Grade grade, Container container)
    {
        String query = "DELETE FROM Grade WHERE id = ? AND idSubject = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toDelete = isConnected.prepareStatement(query))
        {
            toDelete.setInt(1, grade.getID());
            toDelete.setInt(2, grade.getSubjectID());
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.messageBox(container,
                                        "Error while deleting grade.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete all grades of a subject
    public void deleteAll(int idSubject, Container container)
    {
        String query = "DELETE FROM Grade WHERE idSubject = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toDelete = isConnected.prepareStatement(query))
        {
            // subject ID
            toDelete.setInt(1, idSubject);
            // run the query
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.messageBox(container,
                                        "Error while deleting the grades.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to update the score of a grade
    public void updateScore(Grade grade, double newScore, Container container)
    {
        String query = "UPDATE Grade SET score = ? WHERE id = ? AND idSubject = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toUpdate = isConnected.prepareStatement(query))
        {
            // grade attributes
            toUpdate.setDouble(1, newScore);
            toUpdate.setInt(2, grade.getID());
            toUpdate.setInt(3, grade.getSubjectID());
            // run the query
            toUpdate.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.messageBox(container,
                                        "Error while updating the grade score.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to update the percentage of a grade
    public void updatePercentage(Grade grade, double newPercentage, Container container)
    {
        String query = "UPDATE Grade SET percentage = ? WHERE id = ? AND idSubject = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toUpdate = isConnected.prepareStatement(query))
        {
            // grade attributes
            toUpdate.setDouble(1, newPercentage);
            toUpdate.setInt(2, grade.getID());
            toUpdate.setInt(3, grade.getSubjectID());
            // run the query
            toUpdate.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.messageBox(container,
                                        "Error while updating the grade percentage.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }
}