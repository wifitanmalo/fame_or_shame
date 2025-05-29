package fos.service;

// awt import
import java.awt.Container;

// sql imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

// swing imports
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// package imports
import fos.data.SubjectFileHandler;
import fos.view.Main;
import fos.view.SubjectMenu;
import fos.view.WindowComponent;

public class ValidationUtils
{
    // method to verify if a number is negative
    public static boolean isNegative(double number)
    {
        return number < 0;
    }

    // method to verify if a number exceeds the limit
    public static boolean exceedsLimit(double number, double limit)
    {
        return number > limit;
    }

    // method to verify if a number is equal to another
    public static boolean equals(double current_number, double number)
    {
        return current_number == number;
    }

    /* method to verify if the database is connected
    public static Connection connectDB()
    {
        Connection conn = null;
        try
        {
            String url = "jdbc:sqlite:database/fos.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión establecida con SQLite");
        }
        catch (SQLException e)
        {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conn;
    }

     */
    public static Connection connectDB() throws SQLException
    {
        String DB_URL = "jdbc:sqlite:database/fos.db";
        try {
            Class.forName("org.sqlite.JDBC"); // Carga el driver
            return DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("El driver SQLite JDBC no se ha encontrado", e);
        }
    }

    // method to verify if a subject already exists
    public static boolean subjectExists(int id)
    {
        String query = "SELECT 1 FROM Subject WHERE id = ? LIMIT 1";

        try (Connection conn = connectDB();
             java.sql.PreparedStatement exists = conn.prepareStatement(query)) {

            exists.setInt(1, id);
            ResultSet rs = exists.executeQuery();
            return rs.next();

        }
        catch (SQLException e)
        {
            System.out.println("Error al verificar existencia del ID: " + e.getMessage());
            return false;
        }
    }

    // method to verify if subject data is valid
    public static void subjectValidation(Container container,
                                         JTextField idTextBox,
                                         JTextField nameTextBox,
                                         JTextField creditsTextBox)
    {
        try
        {
            // get subject values from text boxes
            int id = Integer.parseInt(idTextBox.getText().trim());
            String name = nameTextBox.getText().trim();
            int credits = Integer.parseInt(creditsTextBox.getText().trim());

            if(ValidationUtils.subjectExists(id))
            {
                WindowComponent.message_box(container,
                        "ID already exists.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.isNegative(id) || ValidationUtils.isNegative(credits))
            {
                WindowComponent.message_box(container,
                        "ID/Credits cannot be negative.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(name.isEmpty())
            {
                WindowComponent.message_box(container,
                        "Name cannot be empty.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceedsLimit(nameTextBox.getText().length(), 50))
            {
                WindowComponent.message_box(container,
                        "Name cannot be longer than 50 characters.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceedsLimit(SubjectFileHandler.SIGNED_CREDITS + credits,
                    SubjectFileHandler.MAX_CREDITS))
            {
                WindowComponent.message_box(container,
                        "Credits cannot be higher than " + SubjectFileHandler.MAX_CREDITS + ".",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // insert the subject on the database
                Subject newSubject = new Subject(id, name, credits);
                SubjectMenu.fileHandler.createSubject(newSubject, container);

                // refresh the subjects on the subject box
                SubjectMenu.fileHandler.loadSubjects(SubjectMenu.subjectBox);

                // switch to the subject menu
                WindowComponent.switch_panel(container, Main.subjectMenu);
            }
        }
        catch (NumberFormatException e)
        {
            WindowComponent.message_box(container,
                    "ID/Credits values are not valid.",
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to validate the grade
    public static boolean gradeValidation(Subject subject,
                                          Container container)
    {
        // reboot the grade values
        double totalScore = 0;
        double totalPercentage = 0;
        subject.setTotalScore(0);
        subject.setTotalEvaluated(0);

        for (Grade grade : subject.getGradesList())
        {
            try
            {
                double score = grade.getScore();
                double percentage = grade.getPercentage();

                if(score<0 || percentage<=0)
                {
                    JOptionPane.showMessageDialog(container,
                            "Score and percentage must be POSITIVE numbers.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if(ValidationUtils.exceedsLimit(score, Subject.MAX_SCORE))
                {
                    JOptionPane.showMessageDialog(container,
                            "Score cannot be higher than " + Subject.MAX_SCORE + ".",
                            "Score limit",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                // get the current values
                totalScore += score * (percentage/100);
                totalPercentage += percentage;

                if(ValidationUtils.exceedsLimit(totalPercentage, 100))
                {
                    JOptionPane.showMessageDialog(container,
                            "The total percentage cannot exceed 100%.",
                            "Percentage limit",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(container,
                        "Please enter valid numbers for score and percentage.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        subject.setTotalScore(totalScore);
        subject.setTotalEvaluated(totalPercentage);
        return true;
    }
}
