package fos.data;

// awt imports
import java.awt.Container;

// swing imports
import javax.swing.JOptionPane;

// sql imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// package imports
import fos.view.WindowComponent;
import fos.service.User;
import fos.service.ValidationUtils;


public class UserDataHandler
{
    // constructor
    public UserDataHandler()
    {

    }


    // method to create a user in the database
    public void createUser(int userID,
                           double passScore,
                           double maxScore,
                           int maxCredits,
                           Container container)
    {
        String query = """
            INSERT INTO User (id, pass_score, max_score, max_credits)
            SELECT ?, ?, ?, ?
            WHERE NOT EXISTS (
                SELECT 1 FROM User WHERE id = ?
            );
        """;

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement user = isConnected.prepareStatement(query))
        {
            // user attributes
            user.setInt(1, userID);
            user.setDouble(2, passScore);
            user.setDouble(3, maxScore);
            user.setInt(4, maxCredits);
            user.setInt(5, userID);

            // run the query
            user.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while creating the user.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }



    // method to get a user from the database
    public User getUser(int userID, Container container)
    {
        String query = "SELECT * FROM User WHERE id = ?";
        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement state = isConnected.prepareStatement(query))
        {
            state.setInt(1, userID);
            ResultSet user = state.executeQuery();

            if (user.next())
            {
                // user attributes
                int id = user.getInt("id");
                double passScore = user.getDouble("pass_score");
                double maxScore = user.getDouble("max_score");
                int maxCredits = user.getInt("max_credits");

                // return the user object with the information
                return new User(id, passScore, maxScore, maxCredits);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while reading the user.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }

        // returns the default user in case of error/nonexistence
        return new User(2704, 3.0, 5.0, 21);
    }


    // method to update the passing score in the database
    public void updatePassScore(int userID, double passScore, Container container)
    {
        String query = "UPDATE User SET pass_score = ? WHERE id = ?";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toUpdate = isConnected.prepareStatement(query))
        {
            toUpdate.setDouble(1, passScore);
            toUpdate.setInt(2, userID);

            // run the query
            toUpdate.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while updating the passing score.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to update the max score in the database
    public void updateMaxScore(int userID, double maxScore, Container container)
    {
        String query = "UPDATE User SET max_score = ? WHERE id = ?";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toUpdate = isConnected.prepareStatement(query))
        {
            toUpdate.setDouble(1, maxScore);
            toUpdate.setInt(2, userID);

            // run the query
            toUpdate.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while updating the max score.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // method to update the max credits in the database
    public void updateMaxCredits(int userID, int credits, Container container)
    {
        String query = "UPDATE User SET max_credits = ? WHERE id = ?";

        try (Connection isConnected = ValidationUtils.connectDB();
             PreparedStatement toUpdate = isConnected.prepareStatement(query))
        {
            toUpdate.setInt(1, credits);
            toUpdate.setInt(2, userID);

            // run the query
            toUpdate.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.messageBox(container,
                                    "Error while updating the credits.",
                                    "Database error",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }
}