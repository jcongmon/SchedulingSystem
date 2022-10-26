package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Helper abstract class contains functions relating to customer queries between the Java program and the MySQL database.
 *
 * @author Jonathan Congmon
 */
public abstract class ClientsQuery {
    /**
     * Selects all customers from the MySQL database. Stores the results in an observable list and returns the observable list.
     *
     * @return ObservableList containing customer objects
     */
    public static ObservableList<Customers> selectCustomers() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
            Customers customer = new Customers(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    /**
     * Inserts customer information into the MySQL database.
     */
    public static void insert(String customerName, String address, String postal, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setTimestamp(5, createDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionId);
        ps.executeUpdate();
    }

    /**
     * Gets the Division ID from the division name.
     *
     * @param division Division name
     * @return Division ID
     */
    public static int selectDivisonIDfromDivision(String division) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        int divId = -1;
        while (rs.next()) {
            divId = rs.getInt("Division_ID");
        }
        return divId;
    }

    /**
     * Gets the state name from division ID
     *
     * @param divisionId Division ID
     * @return Division Name
     */
    public static String selectStateFromDivisionId(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        String state = "";
        while (rs.next()) {
            state = rs.getString("Division");
        }
        return state;
    }

    /**
     * Returns an observable list containing all the first level divisions based on the country ID
     *
     * @param countryId Country ID
     * @return Observable list containing all the first level divisions based on country ID
     */
    public static ObservableList<String> selectStates(int countryId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> states = FXCollections.observableArrayList();
        while (rs.next()) {
            String division = rs.getString("Division");
            states.add(division);
        }
        return states;
    }

    /**
     * Selects all countries from the MySQL database.
     *
     * @return Observable list containing all countries as Strings.
     */
    public static ObservableList<String> selectCountries() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> countries = FXCollections.observableArrayList();
        while (rs.next()) {
            String country = rs.getString("Country");
            countries.add(country);
        }
        return countries;
    }

    /**
     * Gets the Country ID from the Country Name
     *
     * @param countryName Country Name
     * @return Country ID
     */
    public static int selectCountryIdFromName(String countryName) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE COUNTRY=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, countryName);
        ResultSet rs = ps.executeQuery();
        int countryId = -1;
        while (rs.next()) {
            countryId = rs.getInt("Country_ID");
        }
        return countryId;
    }

    /**
     * Gets the Country ID from the Division ID.
     *
     * @param divisionId Division ID
     * @return Country ID
     */
    public static int selectCountryIdFromDivisionId(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        int countryId = -1;
        while (rs.next()) {
            countryId = rs.getInt("Country_ID");
        }
        return countryId;
    }

    /**
     * Gets the Country Name from Country ID
     *
     * @param countryId Country ID
     * @return Country Name
     */
    public static String selectCountryNameFromCountryId(int countryId) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        String countryName = "";
        while (rs.next()) {
            countryName = rs.getString("Country");
        }
        return countryName;
    }

    /**
     * Gets customer from the customer ID
     *
     * @param queryCustomerId Customer ID
     * @return Customer Object
     */
    public static Customers selectCustomer(int queryCustomerId) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, queryCustomerId);
        ResultSet rs = ps.executeQuery();
        int customerId = -1;
        String customerName = "";
        String address = "";
        String postalCode = "";
        String phoneNumber = "";
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        String createdBy = "";
        Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
        String lastUpdatedBy = "";
        int divisionId = -1;

        while (rs.next()) {
            customerId = rs.getInt("Customer_ID");
            customerName = rs.getString("Customer_Name");
            address = rs.getString("Address");
            postalCode = rs.getString("Postal_Code");
            phoneNumber = rs.getString("Phone");
            createDate = rs.getTimestamp("Create_Date");
            createdBy = rs.getString("Created_By");
            lastUpdate = rs.getTimestamp("Last_Update");
            lastUpdatedBy = rs.getString("Last_Updated_By");
            divisionId = rs.getInt("Division_ID");
        }
        Customers customer = new Customers(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
        return customer;
    }

    /**
     * Updates the customer in the MySQL table based on Customer ID.
     */
    public static void updateCustomer(int customerId, String customerName, String address, String postal,
                                      String phone, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CUSTOMER_NAME=?, ADDRESS=?, POSTAL_CODE=?, PHONE=?, LAST_UPDATE=?, LAST_UPDATED_BY=?, DIVISION_ID=? WHERE CUSTOMER_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setTimestamp(5, lastUpdate);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, customerId);
        ps.executeUpdate();
    }

    /**
     * Gets the Customer ID from the customer name and address.
     *
     * @param customerName Customer Name
     * @param address      Address
     * @return Customer ID
     */
    public static int selectCustomerIdFromName(String customerName, String address) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_NAME= ? AND ADDRESS= ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ResultSet rs = ps.executeQuery();
        int customerId = -1;
        while (rs.next()) {
            customerId = rs.getInt("Customer_ID");
        }
        return customerId;
    }
    //Delete Client Functions

    /**
     * Checks if a customer has no appointments
     *
     * @param customerId Customer ID
     * @return boolean if the customer has no appointments
     */
    public static boolean isAppointmentsEmpty(int customerId) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE CUSTOMER_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        while (rs.next()) {
            count++;
        }
        return count == 0;
    }

    /**
     * Deletes a customer based on their customer ID
     *
     * @param customerId Customer ID
     */
    public static void deleteClient(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }
}