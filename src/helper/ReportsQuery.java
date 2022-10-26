package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Helper abstract class contains functions relating to report queries between the Java program and the MySQL database.
 *
 * @author Jonathan Congmon
 */
public abstract class ReportsQuery {
    /**
     * Selects all appointments between system time and the end of the current month.
     *
     * @param month Month
     * @return ObservableList of Appointments objects
     */
    public static ObservableList<Appointments> getAppByMonth(int month) throws SQLException {
        ObservableList<Appointments> allAppByMonth = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE MONTH(Start) = ? AND Start > NOW()";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, month);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String last_Updated_By = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appId, title, description, type, location, start, end, createDate, createdBy, lastUpdate, last_Updated_By, customerId, userId, contactId);
            allAppByMonth.add(appointment);
        }
        return allAppByMonth;
    }

    /**
     * Selects all appointments from the MySQL database of selectedType.
     *
     * @param selectedType Selected Type
     * @return ObservableList of Appointments objects
     */
    public static ObservableList<Appointments> getSelectedTypeChoice(String selectedType) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE TYPE=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, selectedType);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointments> allSelectedTypes = FXCollections.observableArrayList();
        while (rs.next()) {
            int appId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String last_Updated_By = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appId, title, description, type, location, start, end, createDate, createdBy, lastUpdate, last_Updated_By, customerId, userId, contactId);
            allSelectedTypes.add(appointment);
        }
        return allSelectedTypes;
    }

    /**
     * Selects all past appointments from the MySQL database before system time.
     *
     * @return ObservableList of Appointments objects
     */
    public static ObservableList<Appointments> getPastAppointments() throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE End < NOW()";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointments> pastAppointments = FXCollections.observableArrayList();
        while (rs.next()) {
            int appId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String last_Updated_By = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appId, title, description, type, location, start, end, createDate, createdBy, lastUpdate, last_Updated_By, customerId, userId, contactId);
            pastAppointments.add(appointment);
        }
        return pastAppointments;
    }

    /**
     * Selects all contacts from the MySQL database.
     *
     * @return ObservableList of Strings containing contact names
     */
    public static ObservableList<String> selectAllContacts() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        while (rs.next()) {
            String contactName = rs.getString("Contact_Name");
            allContactsNames.add(contactName);
        }
        return allContactsNames;
    }

    /**
     * Gets contact ID from contact name
     *
     * @param name Contact Name
     * @return Contact ID
     */
    public static int convertContactNameToId(String name) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_NAME=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        int contactId = -1;
        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }

    /**
     * Selects all appointments from the MySQL database that have the contact ID contactId.
     *
     * @param contactId Contact ID
     * @return ObservableList of Appointments objects
     */
    public static ObservableList<Appointments> getSelectedContact(int contactId) throws SQLException {

        String sql = "SELECT * FROM APPOINTMENTS WHERE CONTACT_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointments> selectedContact = FXCollections.observableArrayList();
        while (rs.next()) {
            int appId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String last_Updated_By = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contId = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appId, title, description, type, location, start, end, createDate, createdBy, lastUpdate, last_Updated_By, customerId, userId, contId);
            selectedContact.add(appointment);
        }
        return selectedContact;
    }
}
