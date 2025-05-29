package fos.data;

// awt import
import java.awt.Container;

// sql imports
import java.sql.*;

// swing imports
import javax.swing.JOptionPane;

// package imports
import fos.view.SubjectMenu;
import fos.view.SubjectPanel;
import fos.view.WindowComponent;
import fos.service.Subject;
import fos.service.ValidationUtils;

public class SubjectFileHandler
{
    // credit constants
    public static int SIGNED_CREDITS = 0;
    public static final int MAX_CREDITS = 21;

    // constructor
    public SubjectFileHandler()
    {

    }

    // method to get the total score of a subject
    public double getTotalScore(int subjectId, Container container)
    {
        double totalScore = 0.0;
        String query = "SELECT totalScore FROM Subject WHERE id = ?";

        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement get = conn.prepareStatement(query))
        {
            get.setInt(1, subjectId);
            try (ResultSet rs = get.executeQuery())
            {
                if (rs.next())
                {
                    totalScore = rs.getDouble("totalScore");
                }
            }

        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                    "Error while getting the score.",
                                    "Data error",
                                    JOptionPane.ERROR_MESSAGE);
        }
        return totalScore;
    }


    // method to load the subjects from the database
    public void loadSubjects(Container container)
    {
        String query = "SELECT * FROM Subject";

        try (Connection conn = ValidationUtils.connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query))
        {
            SubjectMenu.subjectBox.removeAll();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int credits = rs.getInt("credits");
                double totalScore = rs.getDouble("totalScore");
                double totalEvaluated = rs.getDouble("totalEvaluated");

                // create the subject with the information
                Subject subject = new Subject(id,name,credits);
                subject.setTotalScore(totalScore);
                subject.setTotalEvaluated(totalEvaluated);

                // create the subject panel of the subject
                SubjectPanel currentPanel = new SubjectPanel(subject);
                currentPanel.set_score_label(subject.getTotalScore());
                currentPanel.set_evaluated_label(subject.getTotalEvaluated());
            }
            // reload the panel to show the changes
            WindowComponent.reload(SubjectMenu.subjectBox);
        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                    "Error while reading the databaset",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to create a subject in the database
    public void createSubject(Subject subject, Container container)
    {
        String query = "INSERT INTO Subject(id, name, credits, totalScore, totalEvaluated) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = ValidationUtils.connectDB();
             java.sql.PreparedStatement create = conn.prepareStatement(query)) {

            create.setInt(1, subject.getId());
            create.setString(2, subject.getName());
            create.setInt(3, subject.getCredits());
            create.setDouble(4, subject.getTotalScore());
            create.setDouble(5, subject.getTotalEvaluated());

            create.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                        "Error while creating the subject.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to update a subject in the database
    public void updateSubject(int id, Container container)
    {
        String query = "UPDATE Subject " +
                "SET totalScore = (SELECT IFNULL(SUM(score * (percentage / 100.0)), 0) FROM Grade WHERE idSubject = ? AND idSuperGrade IS NULL), " +
                "    totalEvaluated = (SELECT IFNULL(SUM(percentage), 0) FROM Grade WHERE idSubject = ? AND idSuperGrade IS NULL) " +
                "WHERE id = ?;";

        try (Connection conn = ValidationUtils.connectDB();
             java.sql.PreparedStatement up = conn.prepareStatement(query))
        {
            up.setInt(1, id); // totalScore
            up.setInt(2, id); // totalEvaluated
            up.setInt(3, id); // subjectID

            up.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                    "Error while updating the subject.",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete a subject in the database
    public void deleteSubject(int id, Container container)
    {
        String query = "DELETE FROM Subject WHERE id = ?";

        try (Connection conn = ValidationUtils.connectDB();
             java.sql.PreparedStatement del = conn.prepareStatement(query))
        {
            del.setInt(1, id);
            del.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.message_box(container,
                                        "Error while deleting subject",
                                        "File error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }
}