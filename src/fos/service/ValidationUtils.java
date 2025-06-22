package fos.service;

// awt import
import java.awt.Container;

// sql imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// swing imports
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

// package imports
import fos.view.Main;
import fos.view.SettingsMenu;
import fos.view.SubjectMenu;
import fos.view.WindowComponent;
import fos.data.SubjectDataHandler;


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


    // method to verify if the database is connected
    public static Connection connectDB() throws SQLException
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection isConnected = DriverManager.getConnection("jdbc:sqlite:database/fos.db");
            setSchema(isConnected);
            return isConnected;
        }
        catch (ClassNotFoundException e)
        {
            throw new SQLException("----- driver not found -----", e);
        }
    }


    // method to insert the database squema if it does not exist
    private static void setSchema(Connection isConnected) throws SQLException
    {
        Statement squema = isConnected.createStatement();
        // insert the user table in the database
        squema.execute("""
            CREATE TABLE IF NOT EXISTS User (
                id INT PRIMARY KEY,
                passScore REAL NOT NULL,
                maxScore REAL NOT NULL,
                maxCredits INT NOT NULL
            );
        """);
        // insert the subject table in the database
        squema.execute("""
            CREATE TABLE IF NOT EXISTS Subject (
                id INTEGER PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                credits INTEGER NOT NULL,
                totalScore REAL DEFAULT 0,
                totalEvaluated REAL DEFAULT 0
            );
        """);
        // insert the grade table in the database
        squema.execute("""
            CREATE TABLE IF NOT EXISTS Grade (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                idSubject INTEGER NOT NULL,
                name VARCHAR(30),
                score REAL DEFAULT 0,
                percentage REAL DEFAULT 0,
                idSuperGrade INTEGER,
                FOREIGN KEY (idSubject) REFERENCES Subject(id),
                FOREIGN KEY (idSuperGrade) REFERENCES Grade(id)
            );
        """);
        squema.close();
    }


    // method to verify if a subject already exists
    public static boolean subjectExists(int id, Container container)
    {
        String query = "SELECT 1 FROM Subject WHERE id = ? LIMIT 1";
        try (Connection isConnected = connectDB();
             PreparedStatement exists = isConnected.prepareStatement(query))
        {
            // subject ID
            exists.setInt(1, id);
            // run the query
            ResultSet subject = exists.executeQuery();
            return subject.next();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "Error while searching the ID.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
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

            if(ValidationUtils.subjectExists(id, container))
            {
                WindowComponent.messageBox(container,
                                            "ID already exists.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.isNegative(id) || ValidationUtils.isNegative(credits))
            {
                WindowComponent.messageBox(container,
                                            "ID/Credits cannot be negative.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(name.isEmpty())
            {
                WindowComponent.messageBox(container,
                                            "Name cannot be empty.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceedsLimit(nameTextBox.getText().length(), 50))
            {
                WindowComponent.messageBox(container,
                                            "Name cannot be longer than 50 characters.",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else if(ValidationUtils.exceedsLimit(SubjectDataHandler.SIGNED_CREDITS + credits,
                    SettingsMenu.CURRENT_USER.getMaxCredits()))
            {
                WindowComponent.messageBox(container,
                                            "Credits cannot be higher than " + SettingsMenu.CURRENT_USER.getMaxCredits() + ".",
                                            "Input error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // create the subject and inserts it in the database
                Subject newSubject = new Subject(id, name, credits);
                SubjectMenu.dataHandler.createSubject(newSubject);
                // refresh the subjects on the subject box
                SubjectMenu.dataHandler.loadSubjects();
                // switch to the subject menu
                WindowComponent.switchPanel(container, Main.subjectMenu);
            }
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                        "invalid ID/Credits values.",
                                        "Input error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to get the remaining score to pass
    public static double getRemainingScore(double currentScore, double evaluatedPercentage)
    {
        // the subject has been fully evaluated
        if (evaluatedPercentage >= 100.0)
        {
            return (currentScore >= SettingsMenu.CURRENT_USER.getPassScore()) ? 0.0 : Double.POSITIVE_INFINITY;
        }
        // no more score are needed to pass
        if (currentScore >= SettingsMenu.CURRENT_USER.getPassScore())
        {
            return 0.0;
        }
        // remaining percentage to be evaluated
        double remainingPercentage = 100.0 - evaluatedPercentage;
        // remaining score to pass the subject
        double remainingGrade = (SettingsMenu.CURRENT_USER.getPassScore() - currentScore) * 100.0 / remainingPercentage;
        return Math.max(remainingGrade, 0.0);
    }


    // method for verify the risk threshold of a subject
    public static void riskThreshold(double currentScore,
                                    double evaluatedPercentage,
                                     JScrollPane gradeBox)
    {
        double remainingGrade = getRemainingScore(currentScore, evaluatedPercentage);
        if (currentScore >= SettingsMenu.CURRENT_USER.getPassScore()) // the subject has been passed
        {
            WindowComponent.messageBox(gradeBox,
                                        "You pass the subject!",
                                        "Now you can rest",
                                        JOptionPane.INFORMATION_MESSAGE
            );
        }
        else if (remainingGrade > SettingsMenu.CURRENT_USER.getMaxScore()) // the subject has been lost
        {

            WindowComponent.messageBox(gradeBox,
                                    "You lost the subject.",
                                    "End of the line",
                                    JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            String message = String.format("You need %.2f in the rest of the subject to pass.", remainingGrade);
            WindowComponent.messageBox(gradeBox,
                                        message,
                                        "You are close",
                                        JOptionPane.WARNING_MESSAGE
            );
        }
    }
}