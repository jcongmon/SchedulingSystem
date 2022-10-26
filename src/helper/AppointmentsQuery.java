package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Helper abstract class contains functions relating to appointments queries between the Java program and the MySQL database.
 * Lambda Expression: Line 381 - Iterating over a list to reduce verbosity.
 *
 * @author Jonathan Congmon
 */
public abstract class AppointmentsQuery {
    //For AddAppointments Form

    /**
     * Selects all contacts from the MySQL database. Stores the results in an observable list and returns the observable list.
     *
     * @return ObservableList of the String type containing contact IDs and contact names.
     */
    public static ObservableList<String> selectAllContactsByName() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        while (rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            allContactsNames.add(contactId + " - " + contactName);
        }
        return allContactsNames;
    }

    /**
     * Populates an observable list with arbitrary types to be used in the appointments forms.
     *
     * @return ObservableList of the String type containing the types available.
     */
    public static ObservableList<String> getTypesList() {
        ObservableList<String> typesList = FXCollections.observableArrayList();
        typesList.add("Planning Session");
        typesList.add("De-Briefing");
        typesList.add("Team Building");
        typesList.add("Brainstorming Session");
        typesList.add("Project Meeting");
        typesList.add("One on One Meeting");
        typesList.add("Seminar");
        typesList.add("Other");
        return typesList;
    }

    /**
     * Creates an observable list containing numbers from 00 to 23 as strings.
     *
     * @return ObservableList of the String type containing hours from 00 to 23.
     */
    public static ObservableList<String> getHoursList() {
        ObservableList<String> hoursList = FXCollections.observableArrayList();

        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                hoursList.add(String.format("%02d", i));
            } else {
                hoursList.add(String.valueOf(i));
            }
        }
        return hoursList;
    }

    /**
     * Creates an observable list containing numbers from 00 to 59 as strings.
     *
     * @return ObservableList of the String type containing values from 00 to 59.
     */
    public static ObservableList<String> getMinutesOrSecondsList() {
        ObservableList<String> minutesOrSecondsList = FXCollections.observableArrayList();
        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                minutesOrSecondsList.add(String.format("%02d", i));
            } else {
                minutesOrSecondsList.add(String.valueOf(i));
            }
        }
        return minutesOrSecondsList;
    }

    /**
     * Returns the user ID from a username from the MySQL database.
     *
     * @param name is the username
     * @return user ID as an integer
     */
    public static int getUserIdFromName(String name) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USER_NAME=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        int userId = -1;
        while (rs.next()) {
            userId = rs.getInt("User_ID");
        }
        return userId;
    }

    /**
     * Inserts appointment data into the MySQL database.
     *
     * @param appointment is of the Appointment class
     */
    public static void insertAppointment(Appointments appointment) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, appointment.getStart());
        ps.setTimestamp(6, appointment.getEnd());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        ps.setTimestamp(7, currentTime);
        ps.setString(8, appointment.getCreatedBy());
        ps.setTimestamp(9, currentTime);
        ps.setString(10, appointment.getCreatedBy());
        ps.setInt(11, appointment.getCustomerId());
        ps.setInt(12, appointment.getUserId());
        ps.setInt(13, appointment.getContactId());
        ps.executeUpdate();
    }

    //For Menu form, populating appointments table

    /**
     * Selects all appointments from the MySQL database. Returns an observable list containing all appointments.
     *
     * @return observable list containing Appointments objects.
     */
    public static ObservableList<Appointments> selectAllAppointments() throws SQLException {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp startConverted = Timestamp.valueOf(Time.UTCtoLocal(start).toLocalDateTime());
            Timestamp end = rs.getTimestamp("End");
            Timestamp endConverted = Timestamp.valueOf(Time.UTCtoLocal(end).toLocalDateTime());
            Timestamp createDate = rs.getTimestamp("Create_Date");
            Timestamp createConverted = Timestamp.valueOf(Time.UTCtoLocal(createDate).toLocalDateTime());
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            Timestamp updateConverted = Timestamp.valueOf(Time.UTCtoLocal(lastUpdate).toLocalDateTime());
            String last_Updated_By = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appId, title, description, type, location, startConverted, endConverted, createConverted, createdBy, updateConverted, last_Updated_By, customerId, userId, contactId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /**
     * Selects all appointments from the MySQL database within the next week. Returns an observable list containing all appointments.
     *
     * @return observable list containing Appointments objects.
     */
    public static ObservableList<Appointments> selectWeekAppointments() throws SQLException {
        ObservableList<Appointments> weekAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE YEARWEEK(Start, 1) = YEARWEEK(CURDATE(), 1) AND Start > NOW()";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
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
            weekAppointments.add(appointment);
        }
        return weekAppointments;
    }

    /**
     * Selects all appointments from the MySQL database by the end of the month. Returns an observable list containing all appointments.
     *
     * @return observable list containing Appointments objects.
     */
    public static ObservableList<Appointments> selectMonthAppointments() throws SQLException {
        ObservableList<Appointments> monthAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE MONTH(Start) = MONTH(CURDATE()) AND Start > NOW()";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
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
            monthAppointments.add(appointment);
        }
        return monthAppointments;
    }

    /**
     * Gets the contact name from the contact ID.
     *
     * @return String containing the contact ID and contact name.
     */
    public static String getContact(int contactId) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        String contact = "";
        while (rs.next()) {
            contact = contactId + " - " + rs.getString("Contact_Name");
        }
        return contact;
    }

    /**
     * Parses a concatenated string for the state name.
     *
     * @return String containing the state name only
     */
    public static String getState(String concatLocation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < concatLocation.length(); i++) {
            char c = concatLocation.charAt(i);
            if (c != ',') {
                sb.append(c);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * Selects an appointment to update based on the appointment ID.
     *
     * @param appointment Appointment object that is to be updated
     */
    public static void updateAppointment(Appointments appointment) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET TITLE=?, DESCRIPTION=?, TYPE=?, START=?, END=?, LAST_UPDATE=?, LAST_UPDATED_BY=?, CONTACT_ID=?, LOCATION=?, CUSTOMER_ID=? WHERE APPOINTMENT_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getType());
        ps.setTimestamp(4, appointment.getStart());
        ps.setTimestamp(5, appointment.getEnd());
        ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        ps.setString(7, appointment.getLastUpdatedBy());
        ps.setInt(8, appointment.getContactId());
        ps.setString(9, appointment.getLocation());
        ps.setInt(10, appointment.getCustomerId());
        ps.setInt(11, appointment.getAppointmentId());
        ps.executeUpdate();
    }

    /**
     * Deletes selected appointment from the MySQL database based on the appointment ID.
     *
     * @param appointmentId Appointment ID
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

    /**
     * Selects all appointments from the MySQL database from a customer ID. Returns an observable list containing all appointments.
     *
     * @return observable list containing Appointments objects.
     */
    public static ObservableList<Appointments> getAppointmentsByCustomer(int custId) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE CUSTOMER_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
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
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }

    /**
     * Selects all appointments from the MySQL database within 15 minutes of system time. Returns an observable list containing all appointments.
     *
     * @return observable list containing Appointments objects.
     */
    public static ObservableList<Appointments> appointmentsWithinFifteenMin(LocalDateTime time) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE START > ? AND START < ? + INTERVAL 15 MINUTE";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
        Timestamp timeUTC = Time.localToUTC(time);
        ps.setTimestamp(1, timeUTC);
        ps.setTimestamp(2, timeUTC);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp startConvert = Timestamp.valueOf(Time.UTCtoLocal(start).toLocalDateTime());
            Timestamp end = rs.getTimestamp("End");
            Timestamp endConvert = Timestamp.valueOf(Time.UTCtoLocal(end).toLocalDateTime());
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String last_Updated_By = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appId, title, description, type, location, startConvert, endConvert, createDate, createdBy, lastUpdate, last_Updated_By, customerId, userId, contactId);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    /**
     * Generates a string for the login warning displaying the appointments in the next 15 minutes.
     *
     * @param appList Observable List containing appointment objects
     * @return String containing the appointment ID and start time
     */
    public static String displayUpcomingAppointments(ObservableList<Appointments> appList) {
        StringBuilder sb = new StringBuilder();
        appList.forEach(a -> sb.append("ID " + a.getAppointmentId() + ": " + a.getStart() + " "));
        return sb.toString();
    }
    /**
     * Checks if there is a client ID present in the database
     *
     * @param clientId Client ID
     * @return Boolean if a client ID is present.
     */
    public static boolean isClientIdPresent(int clientId) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, clientId);
        ResultSet rs = ps.executeQuery();
        boolean flag = false;
        while(rs.next()) {
            flag = true;
        }
        return flag;
    }
}
