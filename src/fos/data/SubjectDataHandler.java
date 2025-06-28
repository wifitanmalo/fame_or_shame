package fos.data;

// awt import
import java.awt.Container;

// sql imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// swing imports
import javax.swing.JOptionPane;

// package imports
import fos.view.SubjectMenu;
import fos.view.SubjectPanel;
import fos.view.WindowComponent;
import fos.service.Subject;
import fos.service.ValidationUtils;

public class SubjectDataHandler
{
    // credits constants
    public static int SIGNED_CREDITS = 0;


    // constructor
    public SubjectDataHandler()
    {

    }


    // method to get the sum of the total score
    public double getTotalScore(int subjectId, Container container)
    {
        double totalScore = 0.0;
        String query = "SELECT IFNULL(SUM(score * (percentage/100.0)), 0) FROM Grade WHERE id_subject = ? AND id_super_grade IS NULL";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement getSubject = isConnected.prepareStatement(query))
        {
            getSubject.setInt(1, subjectId);
            ResultSet subject = getSubject.executeQuery();
            if (subject.next())
            {
                return subject.getDouble(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while getting the score.",
                                    "Data error",
                                    JOptionPane.ERROR_MESSAGE);
        }
        return totalScore;
    }


    // method to get the sum of the total percentage
    public double getTotalPercentage(int id, Container container)
    {
        String query = "SELECT IFNULL(SUM(percentage), 0) FROM Grade WHERE id_subject = ? AND id_super_grade IS NULL;";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement getPercentage = isConnected.prepareStatement(query))
        {
            getPercentage.setInt(1, id);
            ResultSet percentage = getPercentage.executeQuery();
            if (percentage.next())
            {
                return percentage.getDouble(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                    "Error while getting the total percentage.",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return 0.0;
    }


    // method to load the subjects from the database
    public void loadSubjects()
    {
        String query = "SELECT * FROM Subject";
        try (Connection isConnected = ValidationUtils.connectDB();
             Statement state = isConnected.createStatement();
             ResultSet currentSubject = state.executeQuery(query))
        {
            // clear the subject box and credits constant
            SIGNED_CREDITS = 0;
            SubjectMenu.subjectBox.removeAll();
            while (currentSubject.next())
            {
                // current subject attributes
                int id = currentSubject.getInt("id");
                String name = currentSubject.getString("name");
                int credits = currentSubject.getInt("credits");
                double totalScore = currentSubject.getDouble("total_score");
                double totalEvaluated = currentSubject.getDouble("total_evaluated");

                // create the subject with the values
                Subject subject = new Subject(id,name,credits);
                subject.setTotalScore(totalScore);
                subject.setTotalEvaluated(totalEvaluated);

                // create the subject panel
                SubjectPanel currentPanel = new SubjectPanel(subject);
                currentPanel.setScoreLabel(subject.getTotalScore());
                currentPanel.setEvaluatedLabel(subject.getTotalEvaluated());

                // add the subject credits to the signed constant
                SIGNED_CREDITS += credits;

                // set the component colors of the panel
                currentPanel.performanceColor(totalScore, totalEvaluated);
            }
            // reload the panel to show the changes
            WindowComponent.reload(SubjectMenu.subjectBox);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(SubjectMenu.subjectBox,
                                    "Error while reading the database.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to create a subject in the database
    public void createSubject(Subject subject)
    {
        String query = "INSERT INTO Subject(id, name, credits, total_score, total_evaluated) VALUES(?, ?, ?, ?, ?)";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toCreate = isConnected.prepareStatement(query))
        {
            // subject attributes
            toCreate.setInt(1, subject.getId());
            toCreate.setString(2, subject.getName());
            toCreate.setInt(3, subject.getCredits());
            toCreate.setDouble(4, subject.getTotalScore());
            toCreate.setDouble(5, subject.getTotalEvaluated());

            // run the query
            toCreate.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(SubjectMenu.subjectBox,
                                        "Error while creating the subject.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to update the total score/evaluated of a subject in the database
    public void updateSubject(double score,
                              double percentage,
                              int id,
                              Container container)
    {
        String query = "UPDATE Subject SET total_score = ?, total_evaluated = ? WHERE id = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toUpdate = isConnected.prepareStatement(query))
        {
            // subject attributes
            toUpdate.setDouble(1, score);
            toUpdate.setDouble(2, percentage);
            toUpdate.setInt(3, id);

            // run the query
            toUpdate.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "Error while updating the subject.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to delete a subject in the database
    public void deleteSubject(int id)
    {
        String query = "DELETE FROM Subject WHERE id = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toDelete = isConnected.prepareStatement(query))
        {
            // id of the subject
            toDelete.setInt(1, id);
            // run the query
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(SubjectMenu.subjectBox,
                                        "Error while deleting subject.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }
}