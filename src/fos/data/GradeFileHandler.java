package fos.data;

// awt import
import java.awt.Container;

// sql imports
import java.sql.*;

// swing imports
import javax.swing.JPanel;
import javax.swing.JOptionPane;

// package imports
import fos.view.GradePanel;
import fos.view.WindowComponent;
import fos.service.Grade;
import fos.service.Subject;
import fos.service.ValidationUtils;

public class GradeFileHandler
{
    // constructor
    public GradeFileHandler()
    {

    }

    // method to load the grades from the database
    public void loadGrades(Subject subject, JPanel gradeBox, Container container) {
        String query = "SELECT * FROM Grade WHERE idSubject = ? AND idSuperGrade IS NULL";

        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, subject.getId());

            ResultSet rs = stmt.executeQuery();
            gradeBox.removeAll();

            while (rs.next())
            {
                int id = rs.getInt("id");
                int idSubject = rs.getInt("idSubject");
                String name = rs.getString("name");
                double score = rs.getDouble("score");
                double percentage = rs.getDouble("percentage");

                // Construir objeto Grade con toda la información
                Grade newGrade = new Grade(id, idSubject, name, score, percentage);

                // Crear y agregar panel visual
                GradePanel newPanel = new GradePanel(subject, newGrade, gradeBox);
                newPanel.setScoreText(String.valueOf(score));
                newPanel.setPercentageText(String.valueOf(percentage));
            }

            WindowComponent.reload(gradeBox);

        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                        "Error while reading the database.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to create a new grade in the grades.txt file
    public void createGrade(int idSubject, String name, Container container) {
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
            WindowComponent.message_box(container,
                                        "Error while creating the grade.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete a grade in the database
    public void deleteGrade(Grade grade, Container container)
    {
        String query = "DELETE FROM Grade WHERE id = ? AND idSubject = ?";

        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement del = conn.prepareStatement(query))
        {
            del.setInt(1, grade.getID());
            del.setInt(2, grade.getSubjectID());
            del.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                    "Error while deleting grade",
                                    "File error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete all grades of a subject
    public void deleteAll(int idSubject, Container container)
    {
        String query = "DELETE FROM Grade WHERE idSubject = ?";

        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement del = conn.prepareStatement(query))
        {
            del.setInt(1, idSubject);
            del.executeUpdate();

        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                        "Error while deleting the grades.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to update the score of a grade
    public void updateScore(Grade grade, double newScore, Container container) {
        String query = "UPDATE Grade SET score = ? WHERE id = ? AND idSubject = ?";

        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, newScore);
            stmt.setInt(2, grade.getID());
            stmt.setInt(3, grade.getSubjectID());

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                    "Error while updating the grade score.",
                                    "Data error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to update the percentage of a grade
    public void updatePercentage(Grade grade, double newPercentage, Container container)
    {
        String query = "UPDATE Grade SET percentage = ? WHERE id = ? AND idSubject = ?";

        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, newPercentage);
            stmt.setInt(2, grade.getID());
            stmt.setInt(3, grade.getSubjectID());

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                        "Error while updating the grade percentage.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

}
