package controller;

import helper.AppointmentsQuery;
import helper.ClientsQuery;
import helper.JDBC;
import helper.Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;

/**
 * Controller for the updateAppointment.fxml form.
 * Stores the entered information via the onActionSave event to the MySQL database.
 * Lambda Expression - Line 350 for event handling of the stateChoiceBox. When the countryChoiceBox has an input and the value changes, the attached listener signals to the second choice box what to populate the stateChoiceBox with. The lambda expression shortens the code.
 *
 * @author Jonathan Congmon
 */
public class UpdateAppointmentController {
    /**
     * Gets the system locale
     */
    Locale locale = Locale.getDefault();
    /**
     * Gets the system ZoneID
     */
    ZoneId zoneId = ZoneId.systemDefault();
    Stage stage;

    /**
     * Appointment ID Text Field
     */
    @FXML
    private TextField appointmentIdTxt;
    /**
     * Customer ID Text Field
     */
    @FXML
    private TextField clientIdTxt;
    /**
     * Contact ID Choice Box
     */
    @FXML
    private ChoiceBox<String> contactIdChoiceBox;
    /**
     * Country Name Choice Box
     */
    @FXML
    private ChoiceBox<String> countryChoiceBox;
    /**
     * Description Text Field
     */
    @FXML
    private TextField descriptionTxt;
    /**
     * End Hours Choice Box
     */
    @FXML
    private ChoiceBox<String> endHoursChoiceBox;
    /**
     * End Minutes Choice Box
     */
    @FXML
    private ChoiceBox<String> endMinutesChoiceBox;
    /**
     * End Seconds Choice Box
     */
    @FXML
    private ChoiceBox<String> endSecondsChoiceBox;
    /**
     * End Time Date Picker
     */
    @FXML
    private DatePicker endTimeDatePicker;
    /**
     * Login ID Label
     */
    @FXML
    private Label loginIdLbl;
    /**
     * Start Hours Choice Box
     */
    @FXML
    private ChoiceBox<String> startHoursChoiceBox;
    /**
     * Start Minutes Choice Box
     */
    @FXML
    private ChoiceBox<String> startMinutesChoiceBox;
    /**
     * Start Seconds Choice Box
     */
    @FXML
    private ChoiceBox<String> startSecondsChoiceBox;
    /**
     * Start Date Time Picker
     */
    @FXML
    private DatePicker startTimeDatePicker;
    /**
     * State Name Choice Box
     */
    @FXML
    private ChoiceBox<String> stateChoiceBox;
    /**
     * Title Text Field
     */
    @FXML
    private TextField titleTxt;
    /**
     * Type Choice Box
     */
    @FXML
    private ChoiceBox<String> typeChoiceBox;
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
     * Cancels any input on the form and does not save the information to the database.
     * Alert notification to ensure user is willing to not save values.
     * Returns to the menu.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Values will not be saved. Continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/menu.fxml"));
            loader.load();
            MenuController MController = loader.getController();
            MController.sendUsername(currentUserLbl.getText());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Saves information to the database via JDBC.
     * Parses the information in a try/catch block to ensure no incorrect type information.
     * Converts the time from the choice boxes to LocalDateTime in local time and Timestamps in UTC for storage.
     * Checks if there are any scheduling issues i.e. if the appointment is during business hours or there are overlapping appointments.
     * If there are no scheduling issues, data is saved as an Appointments object and passed to the database.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            //Time conversions
            int startHour = Integer.parseInt(startHoursChoiceBox.getValue());
            int startMinute = Integer.parseInt(startMinutesChoiceBox.getValue());
            int startSecond = Integer.parseInt(startSecondsChoiceBox.getValue());
            int endHour = Integer.parseInt(endHoursChoiceBox.getValue());
            int endMinute = Integer.parseInt(endMinutesChoiceBox.getValue());
            int endSecond = Integer.parseInt(endSecondsChoiceBox.getValue());

            LocalDateTime startLDT = startTimeDatePicker.getValue().atTime(startHour, startMinute, startSecond, 0);
            LocalDateTime endLDT = endTimeDatePicker.getValue().atTime(endHour, endMinute, endSecond, 0);

            // Logic checks
            if (titleTxt.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid title.");
                alert.showAndWait();
            } else if (descriptionTxt.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid description.");
                alert.showAndWait();
            } else if (countryChoiceBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please select a valid country.");
                alert.showAndWait();
            } else if (stateChoiceBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please select a valid state.");
                alert.showAndWait();
            } else if (contactIdChoiceBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid contact.");
                alert.showAndWait();
            } else if (typeChoiceBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid type.");
                alert.showAndWait();
            } else if (startLDT.isAfter(endLDT)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid start and end date with valid times.");
                alert.showAndWait();
            } else {
                int appointmentId = Integer.parseInt(appointmentIdTxt.getText());
                int clientId = Integer.parseInt(clientIdTxt.getText());
                String title = titleTxt.getText();
                String description = descriptionTxt.getText();
                String country = countryChoiceBox.getValue();
                String state = stateChoiceBox.getValue();
                String contact = contactIdChoiceBox.getValue();
                int contactId = contact.charAt(0) - '0';
                String type = typeChoiceBox.getValue();
                Timestamp startTimestamp = Time.localToUTC(startLDT);
                Timestamp endTimestamp = Time.localToUTC(endLDT);
                int userId = AppointmentsQuery.getUserIdFromName(currentUserLbl.getText());
                String lastUpdatedBy = currentUserLbl.getText();
                String location = state + ", " + country;


                if (!AppointmentsQuery.isClientIdPresent(clientId)) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Client ID");
                    alert1.setContentText(clientId + " is an invalid client ID. Select another.");
                    alert1.showAndWait();
                }
                else if ((Time.isNotDuringBusinessHours(startTimestamp))) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Business Hours");
                    alert1.setContentText("Starting time is not within business hours (08:00:00 – 22:00:00 EST, Monday to Friday)");
                    alert1.showAndWait();
                } else if (Time.isOverlappingAppointment(startTimestamp, AppointmentsQuery.getAppointmentsByCustomer(clientId))) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Overlapping Appointment");
                    alert2.setContentText("There is an overlapping appointment. Please change the start time.");
                    alert2.showAndWait();
                } else {
                    Appointments appointment = new Appointments(appointmentId, title, description, type, location, startTimestamp, endTimestamp, new Timestamp(System.currentTimeMillis()), "", new Timestamp(System.currentTimeMillis()), lastUpdatedBy, clientId, userId, contactId);
                    AppointmentsQuery.updateAppointment(appointment);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/menu.fxml"));
                    loader.load();
                    MenuController MController = loader.getController();
                    MController.sendUsername(currentUserLbl.getText());
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Parent scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please choose a valid value for each field.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used in the Menu Controller to bring the appointment information from the database to the updateAppointment form.
     */
    public void sendAppointment(int appointmentId) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            appointmentIdTxt.setText(String.valueOf(rs.getInt("Appointment_ID")));
            titleTxt.setText(rs.getString("Title"));
            clientIdTxt.setText(String.valueOf(rs.getInt("Customer_ID")));
            int contact = rs.getInt("Contact_ID");
            contactIdChoiceBox.setValue(AppointmentsQuery.getContact(contact));
            descriptionTxt.setText(rs.getString("Description"));
            String state = AppointmentsQuery.getState(rs.getString("Location"));
            stateChoiceBox.setValue(state);
            int divId = ClientsQuery.selectDivisonIDfromDivision(state);
            int countryId = ClientsQuery.selectCountryIdFromDivisionId(divId);
            String country = ClientsQuery.selectCountryNameFromCountryId(countryId);
            countryChoiceBox.setValue(country);
            typeChoiceBox.setValue(rs.getString("Type"));

            //TIME
            Timestamp startDateTimestamp = rs.getTimestamp("Start");
            Timestamp endDateTimestamp = rs.getTimestamp("End");

            ZonedDateTime startConvertedZDT = Time.UTCtoLocal(startDateTimestamp);
            ZonedDateTime endConvertedZDT = Time.UTCtoLocal(endDateTimestamp);

            LocalDate startDate = startConvertedZDT.toLocalDate();
            LocalDate endDate = endConvertedZDT.toLocalDate();
            startTimeDatePicker.setValue(startDate);
            endTimeDatePicker.setValue(endDate);

            startHoursChoiceBox.setValue(String.valueOf(startConvertedZDT.getHour()));
            startMinutesChoiceBox.setValue(String.valueOf(startConvertedZDT.getMinute()));
            startSecondsChoiceBox.setValue(String.valueOf(startConvertedZDT.getSecond()));
            endHoursChoiceBox.setValue(String.valueOf(endConvertedZDT.getHour()));
            endMinutesChoiceBox.setValue(String.valueOf(endConvertedZDT.getMinute()));
            endSecondsChoiceBox.setValue(String.valueOf(endConvertedZDT.getSecond()));
        }
    }

    /**
     * Used in the MenuController to bring the username to the addAppointment form.
     */
    public void sendUser(String username) {
        currentUserLbl.setText(username);
    }

    /**
     * Populates the 8 choice boxes on the form and sets the ZoneID and current username to their respective labels.
     */
    @FXML
    public void initialize() {
        //Choice boxes
        try {
            countryChoiceBox.setItems(ClientsQuery.selectCountries());
            contactIdChoiceBox.setItems(AppointmentsQuery.selectAllContactsByName());
            typeChoiceBox.setItems(AppointmentsQuery.getTypesList());
            startHoursChoiceBox.setItems(AppointmentsQuery.getHoursList());
            startMinutesChoiceBox.setItems(AppointmentsQuery.getMinutesOrSecondsList());
            startSecondsChoiceBox.setItems(AppointmentsQuery.getMinutesOrSecondsList());
            endHoursChoiceBox.setItems(AppointmentsQuery.getHoursList());
            endMinutesChoiceBox.setItems(AppointmentsQuery.getMinutesOrSecondsList());
            endSecondsChoiceBox.setItems(AppointmentsQuery.getMinutesOrSecondsList());
            Locale.setDefault(locale);
            zoneIdLbl.setText("ZoneID: " + zoneId + ", " + locale);
            loginIdLbl.setText("Current User: ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        countryChoiceBox.getSelectionModel().selectedItemProperty().addListener((option, oldValue, newValue) ->
        {
            try {
                int countryId = ClientsQuery.selectCountryIdFromName(newValue);
                stateChoiceBox.setItems(ClientsQuery.selectStates(countryId));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
