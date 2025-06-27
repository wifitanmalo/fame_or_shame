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
import fos.view.SubjectMenu;
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
    public void loadGrades(Subject subject, JPanel gradeBox)
    {
        String query = "SELECT * FROM Grade WHERE id_subject = ? AND id_super_grade IS NULL";

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
                int idSubject = currentGrade.getInt("id_subject");
                String name = currentGrade.getString("name");
                double score = currentGrade.getDouble("score");
                double percentage = currentGrade.getDouble("percentage");
                Integer idSuperGrade = currentGrade.getInt("id_super_grade");

                // create grade object with all the information
                Grade newGrade = new Grade(id, idSubject, name, score, percentage, idSuperGrade);

                // Crear y agregar panel visual
                GradePanel newPanel = new GradePanel(subject, newGrade, gradeBox);
                newPanel.setScoreText(String.valueOf(score));
                newPanel.setPercentageText(String.valueOf(percentage));
                newPanel.setValueText(String.format("%.2f", getValue(id, gradeBox)));
            }
            // reload the panel to show the changes
            WindowComponent.reload(gradeBox);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(gradeBox,
                                        "Error while reading the database.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to create a new grade in the grades.txt file
    public void createGrade(int idSubject,
                            String name,
                            Integer idSuperGrade,
                            Container container)
    {
        String query = "INSERT INTO Grade(id_subject, name, id_super_grade) VALUES(?, ?, ?)";
        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement create = conn.prepareStatement(query))
        {
            create.setInt(1, idSubject);
            create.setString(2, name);

            // verify if the value is not null
            if (idSuperGrade != null)
            {
                create.setInt(3, idSuperGrade);
            }
            else
            {
                create.setNull(3, java.sql.Types.INTEGER);
            }
            create.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "Error while creating the grade.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to delete a grade in the database
    public void deleteGrade(Grade grade, Container container)
    {
        String query = "DELETE FROM Grade WHERE id = ? AND id_subject = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toDelete = isConnected.prepareStatement(query))
        {
            toDelete.setInt(1, grade.getID());
            toDelete.setInt(2, grade.getSubjectID());
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "Error while deleting grade.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to get the total amount of subgrades
    public int getSubgradesAmount(int idSuperGrade, Container container)
    {
        String query = "SELECT Count(*) FROM Grade WHERE id_super_grade = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement state = isConnected.prepareStatement(query))
        {
            state.setInt(1, idSuperGrade);
            ResultSet amount = state.executeQuery();
            if (amount.next())
            {
                return amount.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while getting the amount of subgrades.",
                                    "Data error",
                                    JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }


    // method to delete a grade in the database
    public void deleteSubgrade(Grade grade, Container container)
    {
        String query = "DELETE FROM Grade WHERE id = ? AND id_super_grade = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toDelete = isConnected.prepareStatement(query))
        {
            toDelete.setInt(1, grade.getID());
            toDelete.setInt(2, grade.getSuperID());
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while deleting sub grade.",
                                    "Data error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to delete all grades of a subject in the database
    public void deleteAll(int idSubject)
    {
        String query = "DELETE FROM Grade WHERE id_subject = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toDelete = isConnected.prepareStatement(query))
        {
            toDelete.setInt(1, idSubject);
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(SubjectMenu.subjectBox,
                                    "Error while deleting the grades.",
                                    "Data error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to delete all sub grades of a grade in the database
    public void deleteAllSub(int idSuperGrade)
    {
        String query = "DELETE FROM Grade WHERE id_super_grade = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toDelete = isConnected.prepareStatement(query))
        {
            toDelete.setInt(1, idSuperGrade);
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(SubjectMenu.subjectBox,
                                    "Error while deleting the grades.",
                                    "Data error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to update the score of a grade
    public void updateScore(Grade grade, double newScore, Container container)
    {
        String query = "UPDATE Grade SET score = ? WHERE id = ? AND id_subject = ?";
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
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "Error while updating the grade score.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to update the percentage of a grade
    public void updatePercentage(Grade grade, double newPercentage, Container container)
    {
        String query = "UPDATE Grade SET percentage = ? WHERE id = ? AND id_subject = ?";
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
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "Error while updating the grade percentage.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to get the value of a grade
    public double getValue(int gradeID, Container container)
    {
        double totalScore = 0.0;
        String query = "SELECT (score * (percentage/100.0)) AS value FROM Grade WHERE id = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement getGrade = isConnected.prepareStatement(query))
        {
            getGrade.setInt(1, gradeID);
            try (ResultSet grade = getGrade.executeQuery())
            {
                if (grade.next())
                {
                    totalScore = grade.getDouble("value");
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while getting the grade value.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
        return totalScore;
    }
}