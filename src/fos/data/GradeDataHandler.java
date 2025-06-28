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

// util imports
import java.util.Objects;

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


    // method to get a grade by the ID
    public Grade getGrade(int id, Container container)
    {
        String query = "SELECT * FROM Grade WHERE id = ?";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement getGrade = isConnected.prepareStatement(query))
        {
            getGrade.setInt(1, id);
            ResultSet grade = getGrade.executeQuery();

            if (grade.next())
            {
                // grade attributes
                int gradeId = grade.getInt("id");
                int idSubject = grade.getInt("id_subject");
                String name = grade.getString("name");
                double score = grade.getDouble("score");
                double percentage = grade.getDouble("percentage");
                int idSuperRaw = grade.getInt("id_super_grade");
                Integer idSuperGrade = grade.wasNull() ? null : idSuperRaw;

                // returns the grade with all the information
                return new Grade(gradeId, idSubject, name, score, percentage, idSuperGrade);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while getting the grade.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }

        // returns null in case of error/non-existence
        return null;
    }


    // method to load the grades from the database
    public void loadGrades(Subject subject, Integer idSuper, JPanel gradeBox)
    {
        if (idSuper == null)
        {
            // clear the grades box only at the top level
            gradeBox.removeAll();
        }

        String query;
        if (idSuper == null)
        {
            // query for normal/super grades
            query = "SELECT * FROM Grade WHERE id_subject = ? AND id_super_grade IS NULL";
        }
        else
        {
            // query for sub grades
            query = "SELECT * FROM Grade WHERE id_subject = ? AND id_super_grade = ?";
        }

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement state = isConnected.prepareStatement(query))
        {
            state.setInt(1, subject.getId());
            if (idSuper != null)
            {
                state.setInt(2, idSuper);
            }
            ResultSet currentGrade = state.executeQuery();

            while (currentGrade.next())
            {
                // grade attributes
                int id = currentGrade.getInt("id");
                int idSubject = currentGrade.getInt("id_subject");
                String name = currentGrade.getString("name");
                double score = currentGrade.getDouble("score");
                double percentage = currentGrade.getDouble("percentage");
                int superIdRaw = currentGrade.getInt("id_super_grade");
                Integer idSuperGrade = currentGrade.wasNull() ? null : superIdRaw;
                int amount = getSubgradesAmount(Objects.requireNonNullElse(idSuper, id), gradeBox);
                double gradeValue = getValue(id, gradeBox);

                // create a grade object with all the information
                Grade newGrade = new Grade(id, idSubject, name, score, percentage, idSuperGrade);

                // create the panel of the grade
                GradePanel newPanel = new GradePanel(subject, newGrade, gradeBox);
                newPanel.setScoreText(String.valueOf(score));
                newPanel.setPercentageText(String.valueOf(percentage));
                newPanel.setValueText(String.format("%.2f", gradeValue));

                if (idSuper != null)
                {
                    // apply the subgrade settings to the panel
                    newPanel.subgradeSettings();

                    if (amount > 0)
                    {
                        // set the grade value divided by the amount of subgrades in the value text
                        newPanel.setValueText(String.format("%.2f", gradeValue/amount));
                    }
                    else
                    {
                        // set the normal value of the grade in the value text
                        newPanel.setValueText(String.format("%.2f", gradeValue));
                    }
                }
                else
                {
                    if (amount > 0)
                    {
                        // remove the score text field of the panel
                        newPanel.remove(newPanel.getScoreField());

                        // recursive call to load the subgrades
                        loadGrades(subject, id, gradeBox);
                    }
                }
            }

            // reload the panel to show the changes
            WindowComponent.reload(gradeBox);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(gradeBox,
                                    "Error while reading the grades.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to create a new grade in the database
    public void createGrade(int idSubject,
                            String name,
                            double score,
                            double percentage,
                            Integer idSuperGrade,
                            Container container)
    {
        String query = "INSERT INTO Grade(id_subject, name, score, percentage, id_super_grade) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = ValidationUtils.connectDB();
             PreparedStatement create = conn.prepareStatement(query))
        {
            // grade attributes
            create.setInt(1, idSubject);
            create.setString(2, name);
            create.setDouble(3, score);
            create.setDouble(4, percentage);

            // verify if the grade is linked to another
            if (idSuperGrade != null)
            {
                create.setInt(5, idSuperGrade);
            }
            else
            {
                create.setNull(5, java.sql.Types.INTEGER);
            }

            // run the query
            create.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "Error while creating the grade.",
                                        "Database error",
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

            // run the query
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "Error while deleting grade.",
                                        "Database error",
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
                // return the amount od subgrades linked to the grade
                return amount.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while getting the amount of subgrades.",
                                    "Database error",
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

            // run the query
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while deleting sub grade.",
                                    "Database error",
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

            // run the query
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(SubjectMenu.subjectBox,
                                    "Error while deleting the grades.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to delete all sub grades of a grade in the database
    public void deleteAllSubgrades(int idSuper, Container container)
    {
        String query = "DELETE FROM Grade WHERE id_super_grade = ?";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toDelete = isConnected.prepareStatement(query))
        {
            toDelete.setInt(1, idSuper);

            // run the query
            toDelete.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while deleting the sub grades.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to update the score of a grade
    public void updateScore(Grade grade,
                            double newScore,
                            Container container)
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
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to update the percentage of a grade
    public void updatePercentage(Grade grade,
                                 double newPercentage,
                                 Container container)
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
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to update all the subgrades percentage of a grade
    public void updateSubPercentages(Grade superGrade,
                                     double newPercentage,
                                     Container container)
    {
        String query = "UPDATE Grade SET percentage = ? WHERE id_super_grade = ?";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement update = isConnected.prepareStatement(query))
        {
            update.setDouble(1, newPercentage);
            update.setInt(2, superGrade.getID());

            // run the query
            update.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while updating the sub percentages.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to get the value of a grade
    public double getValue(int gradeID, Container container)
    {
        String query = "SELECT (score * (percentage/100.0)) AS value FROM Grade WHERE id = ?";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement getGrade = isConnected.prepareStatement(query))
        {
            getGrade.setInt(1, gradeID);
            try (ResultSet grade = getGrade.executeQuery())
            {
                if (grade.next())
                {
                    return grade.getDouble("value");
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
        return 0.0;
    }


    // method to get the score of a super grade
    public double getSuperValue(Grade superGrade, Container gradeBox)
    {
        String query = "SELECT SUM(score) AS total_score FROM Grade WHERE id_super_grade = ?";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement getSuper = isConnected.prepareStatement(query))
        {
            getSuper.setInt(1, superGrade.getID());
            ResultSet grade = getSuper.executeQuery();

            if (grade.next())
            {
                // get the amount of sub grades linked to the grade
                int amount = getSubgradesAmount(superGrade.getID(), gradeBox);

                if (amount > 0) // super grade
                {
                    // return the sum of the subgrades divided by the amount
                    return grade.getDouble("total_score")/amount;
                }
                else // normal grade
                {
                    return getValue(superGrade.getID(), gradeBox);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(gradeBox,
                                    "Error whilte getting the value.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
        return 0.0;
    }
}