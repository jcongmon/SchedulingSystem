package controller;

import helper.AppointmentsQuery;
import helper.ClientsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;

/**
 * Controller for the menu.fxml form.
 * Main hub for all user actions. Allows the user to add/modify/delete customers and appointments. Offers a reports form to view all appointments.
 *
 * @author Jonathan Congmon
 */
public class MenuController {
    /**
     * Gets the system locale
     */
    Locale locale = Locale.getDefault();
    /**
     * Gets the system ZoneID
     */
    ZoneId zoneId = ZoneId.systemDefault();
    Stage stage;
    Parent scene;
    /**
     * Appointment Table View Client ID Column
     */
    @FXML
    private TableColumn<Appointments, Integer> appClientIdCol;
    /**
     * Appointment Table View Contact ID Column
     */
    @FXML
    private TableColumn<Appointments, String> appContactIdCol;
    /**
     * Appointment Table View Description Column
     */
    @FXML
    private TableColumn<Appointments, String> appDescriptionCol;
    /**
     * Appointment Table View End Time Column
     */
    @FXML
    private TableColumn<Appointments, Timestamp> appEndTimeCol;
    /**
     * Appointment Table View Appointment ID Column
     */
    @FXML
    private TableColumn<Appointments, Integer> appIdCol;
    /**
     * Appointment Table View Location Column
     */
    @FXML
    private TableColumn<Appointments, String> appLocationCol;
    /**
     * Appointment Table View Start Time Column
     */
    @FXML
    private TableColumn<Appointments, Timestamp> appStartTimeCol;
    /**
     * Appointment Table View Title Column
     */
    @FXML
    private TableColumn<Appointments, String> appTitleCol;
    /**
     * Appointment Table View Type Column
     */
    @FXML
    private TableColumn<Appointments, String> appTypeCol;
    /**
     * Appointment Table View
     */
    @FXML
    private TableView<Appointments> appointmentsTableView;
    /**
     * Customer Table View Address Column
     */
    @FXML
    private TableColumn<Customers, String> clientAddressCol;
    /**
     * Customer Table View Client ID Column
     */
    @FXML
    private TableColumn<Customers, Integer> clientIdCol;
    /**
     * Customer Table View Name Column
     */
    @FXML
    private TableColumn<Customers, String> clientNameCol;
    /**
     * Customer Table View Phone Number Column
     */
    @FXML
    private TableColumn<Customers, String> clientPhoneCol;
    /**
     * Customer Table View Postal Code Column
     */
    @FXML
    private TableColumn<Customers, String> clientPostalCol;
    /**
     * Customer Table View
     */
    @FXML
    private TableView<Customers> clientTableView;
    /**
     * Customer Table View Division ID Column
     */
    @FXML
    private TableColumn<Customers, Integer> divisionIdCol;
    /**
     * View All Radio Button
     */
    @FXML
    private RadioButton viewAllBtn;
    /**
     * Login ID Label
     */
    @FXML
    private Label loginIdLbl;
    /**
     * Zone ID Label
     */
    @FXML
    private Label zoneIdLbl;
    /**
     * Current User Label
     */
    @FXML
    private Label currentUserLbl;
    /**
     * Appointment Table View User ID Column
     */
    @FXML
    private TableColumn<Appointments, Integer> userIdCol;

    /**
     * Brings the user to the addAppointment form.
     * Sends the current username and customerId to the addAppointment form.
     * Uses a try/catch block in case the user does not select a customer.
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/addAppointment.fxml"));
            loader.load();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            int customerId = clientTableView.getSelectionModel().getSelectedItem().getCustomerId();
            AddAppointmentController AAPController = loader.getController();
            AAPController.sendUser(currentUserLbl.getText());
            AAPController.sendClient(customerId);
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a client to add an appointment.");
            alert.showAndWait();
        }
    }

    /**
     * Brings the user to the addClient form.
     * Sends the current username to the addAppointment form.
     */
    @FXML
    void onActionAddClient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/addClient.fxml"));
        loader.load();
        AddClientController ACController = loader.getController();
        ACController.sendUser(currentUserLbl.getText());
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * After selecting an appointment (try/catch block), a warning alert shows the appointment ID with type.
     * Confirmation deletes the appointment from the database.
     * initialize() is called to reset the appointmentsTableView without the deleted appointment.
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        try {
            int appointmentId = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentId();
            String type = appointmentsTableView.getSelectionModel().getSelectedItem().getType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment " + appointmentId + ": " + type + " will be deleted. Continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentsQuery.deleteAppointment(appointmentId);
                initialize();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * After selecting a customer (try/catch block), a warning alert notifies of the deletion. If the client has an existing appointment, a warning notifies that the appointment must be deleted before client deletion.
     * Confirmation deletes the appointment from the customer from the database based on the customer ID.
     * initialize() is called to reset the clientTableView without the deleted appointment.
     */
    @FXML
    void onActionDeleteClient(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Client will be deleted. Continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int customerId = clientTableView.getSelectionModel().getSelectedItem().getCustomerId();
                if (ClientsQuery.isAppointmentsEmpty(customerId)) {
                    ClientsQuery.deleteClient(customerId);
                    initialize();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setContentText("This client still has associated appointments.");
                    alert1.showAndWait();
                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a client to delete.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Brings the user back to the login form.
     */
    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Gets the customer ID from the table and sends the customer information to the modifyClient controller. Username is also sent.
     * A try/catch block is used in case a customer is not selected from the table view.
     * Sends the user to the modifyClient form.
     */
    @FXML
    void onActionModifyClient(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyClient.fxml"));
            loader.load();
            ModifyClientController MCController = loader.getController();
            int customerId = clientTableView.getSelectionModel().getSelectedItem().getCustomerId();
            MCController.sendClient(customerId);
            MCController.sendUser(currentUserLbl.getText());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a client to modify.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the username to the ReportsController.
     * Sends the user to the Reports form.
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/reports.fxml"));
        loader.load();
        ReportsController RController = loader.getController();
        RController.sendUser(currentUserLbl.getText());
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When the View All radio button is selected, all the appointments are populated from the database to the table view.
     */
    @FXML
    void onActionViewAll(ActionEvent event) throws SQLException {
        appointmentsTableView.setItems(AppointmentsQuery.selectAllAppointments());
    }

    /**
     * When the View by Week radio button is selected, the appointments between the system date/time and the end of the week are populated from the database to the table view.
     */
    @FXML
    void onActionSortWeek(ActionEvent event) throws SQLException {
        appointmentsTableView.setItems(AppointmentsQuery.selectWeekAppointments());
    }

    /**
     * When the View by Month radio button is selected, all the appointments between the system/date time and the end of the month are populated from the database to the table view.
     */
    @FXML
    void onActionSortMonth(ActionEvent event) throws SQLException {
        appointmentsTableView.setItems(AppointmentsQuery.selectMonthAppointments());
    }

    /**
     * Gets the appointment ID from the table and sends the appointment information to the Update Aponitment Controller. Username is also sent.
     * A try/catch block is used in case an appointment is not selected from the table view.
     * Sends the user to the updateAppointment form.
     */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateAppointment.fxml"));
            loader.load();
            UpdateAppointmentController UAController = loader.getController();
            int appointmentId = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentId();
            UAController.sendAppointment(appointmentId);
            UAController.sendUser(currentUserLbl.getText());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an appointment to update.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates both the clientsTableView and appointmentsTableView with data from the database.
     */
    @FXML
    public void initialize() throws SQLException {
        //Client table
        clientTableView.setItems(ClientsQuery.selectCustomers());
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clientAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        clientPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        //Appointments table
        viewAllBtn.setSelected(true);
        appointmentsTableView.setItems(AppointmentsQuery.selectAllAppointments());

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appClientIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        zoneIdLbl.setText("ZoneID: " + zoneId + ", " + locale);
        loginIdLbl.setText("Current User: ");
    }

    /**
     * Brings the username to the menu form.
     */
    public void sendUsername(String username) {
        currentUserLbl.setText(username);
    }
}

