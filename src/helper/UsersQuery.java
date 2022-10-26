package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper abstract class contains functions relating to user queries between the Java program and the MySQL database.
 *
 * @author Jonathan Congmon
 */
public abstract class UsersQuery {
    /**
     * Gets password stored in database from entered username and compares the database password to the entered password.
     *
     * @param user            Username
     * @param enteredPassword Password
     * @return Boolean if the username is in the database and the password matches that in the database.
     */
    public static boolean selectUser(String user, String enteredPassword) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, user);
        ResultSet rs = ps.executeQuery();
        String userName = "";
        String password = "";
        while (rs.next()) {
            userName = rs.getString("User_Name");
            password = rs.getString("Password");
        }
        return isValidUser(userName) && isValidPassword(password, enteredPassword);
    }

    /**
     * Checks if the username is blank
     *
     * @param user Username
     * @return Boolean if username is not blank
     */
    public static boolean isValidUser(String user) {
        if (user.isBlank()) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the entered password is the same as the stored password
     *
     * @param password        Password
     * @param enteredPassword Entered Password
     * @return Boolean if both passwords match
     */
    public static boolean isValidPassword(String password, String enteredPassword) {
        if (password.equals(enteredPassword))
            return true;
        else
            return false;
    }
}
