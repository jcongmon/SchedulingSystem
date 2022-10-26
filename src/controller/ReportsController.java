package controller;

import helper.AppointmentsQuery;
import helper.ReportsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;

/**
 * Controller for the reports.fxml form.
 * Stores the entered information via the onActionSave event to the MySQL database.
 * Lambda Expressions - Lines 168, 182, 204, and 224 for event handling of the stateChoiceBox. When the countryChoiceBox has an input and the value changes, the attached listener signals to the second choice box what to populate the stateChoiceBox with. The lambda expression shortens the code.
 *
 * @author Jonathan Congmon
 */
public class ReportsController {
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
     * Current User Label
     */
    @FXML
    private Label currentUserLbl;
    /**
     * First Choice Box where the user selects type of sort
     */
    @FXML
    private ChoiceBox<String> firstChoiceBox;
    /**
     * Login ID Label
     */
    @FXML
    private Label loginIdLbl;
    /**
     * Appointments Table View Customer ID Column
     */
    @FXML
    private TableColumn<Appointments, Integer> appClientIdCol;
    /**
     * Appointments Table View Contact ID Column
     */
    @FXML
    private TableColumn<Appointments, Integer> appContactIdCol;
    /**
     * Count Label to show how many appointments based on sort
     */
    @FXML
    private Label countLbl;
    /**
     * Appointments Table View Description Column
     */
    @FXML
    private TableColumn<Appointments, String> appDescriptionCol;
    /**
     * Appointments Table View End Time Column
     */
    @FXML
    private TableColumn<Appointments, Timestamp> appEndCol;
    /**
     * Appointments Table View Appointment ID Column
     */
    @FXML
    private TableColumn<Appointments, Integer> appIdCol;
    /**
     * Appointments Table View Location Column
     */
    @FXML
    private TableColumn<Appointments, String> appLocationCol;
    /**
     * Appointments Table View Start Time Column
     */
    @FXML
    private TableColumn<Appointments, Timestamp> appStartCol;
    /**
     * Appointments Table View
     */
    @FXML
    private TableView<Appointments> appTableView;
    /**
     * Appointments Table View Title Column
     */
    @FXML
    private TableColumn<Appointments, String> appTitleCol;
    /**
     * Appointments Table View Type Column
     */
    @FXML
    private TableColumn<Appointments, String> appTypeCol;
    /**
     * Second Choice Box to specify the month/contactID/etc. based on the first choice box
     */
    @FXML
    private ChoiceBox<String> secondChoiceBox;
    /**
     * Zone ID Label
     */
    @FXML
    private Label zoneIdLbl;

    /**
     * Sends the username back to the menu.
     * Returns to the menu.
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
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

    /**
     * Populates the appointments table view, zone ID and username labels, as well as the choiceboxes.
     * Logic to determine which value from the first choice box is selected to populate the second choice box are if/else statements.
     */
    @FXML
    public void initialize() throws SQLException {
        appTableView.setItems(AppointmentsQuery.selectAllAppointments());
        countLbl.setText(AppointmentsQuery.selectAllAppointments().size() + " appointments");
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appClientIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        Locale.setDefault(locale);
        zoneIdLbl.setText("ZoneID: " + zoneId + ", " + locale);
        loginIdLbl.setText("Current User: ");

        //choiceboxes
        ObservableList<String> firstChoice = FXCollections.observableArrayList();
        firstChoice.add("View All");
        firstChoice.add("View By Month");
        firstChoice.add("View By Type");
        firstChoice.add("View By Contact");
        firstChoice.add("View Past Appointments");
        firstChoiceBox.setItems(firstChoice);
        firstChoiceBox.setValue(firstChoice.get(0));
        firstChoiceBox.getSelectionModel().selectedItemProperty().addListener((option, oldValue, newValue) -> {
            ObservableList<String> emptyList = FXCollections.observableArrayList();
            if (newValue.equals(firstChoice.get(0))) {
                try {
                    secondChoiceBox.setItems(emptyList);
                    ObservableList<Appointments> allAppts = AppointmentsQuery.selectAllAppointments();
                    appTableView.setItems(allAppts);
                    countLbl.setText(allAppts.size() + " appointments");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (newValue.equals(firstChoice.get(1))) {
                ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
                secondChoiceBox.setItems(months);
                secondChoiceBox.getSelectionModel().selectedItemProperty().addListener((option1, oldValue1, newValue1) -> {
                    try {
                        String choice = secondChoiceBox.getValue();
                        int month = -1;
                        for (int i = 0; i < months.size(); i++) {
                            if (choice.equals(months.get(i))) {
                                month = i + 1;
                                break;
                            }
                        }
                        ObservableList<Appointments> apptMonth = ReportsQuery.getAppByMonth(month);
                        appTableView.setItems(apptMonth);
                        countLbl.setText(apptMonth.size() + " appointments");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        // Gets rid of error message, does not change functionality
                    }
                });
            } else if (newValue.equals(firstChoice.get(2))) {
                ObservableList<String> typeList = AppointmentsQuery.getTypesList();
                secondChoiceBox.setItems(typeList);
                secondChoiceBox.getSelectionModel().selectedItemProperty().addListener((option1, oldValue1, newValue1) -> {
                    try {
                        String typeChoice = secondChoiceBox.getValue();
                        ObservableList<Appointments> apptSelected = ReportsQuery.getSelectedTypeChoice(typeChoice);
                        appTableView.setItems(apptSelected);
                        countLbl.setText(apptSelected.size() + " appointments");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        // Gets rid of error message, does not change functionality
                    }
                });
            } else if (newValue.equals(firstChoice.get(3))) {
                ObservableList<String> allContacts = null;
                try {
                    allContacts = ReportsQuery.selectAllContacts();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                secondChoiceBox.setItems(allContacts);
                secondChoiceBox.getSelectionModel().selectedItemProperty().addListener((option1, oldValue1, newValue1) -> {
                    try {
                        String contactChoice = secondChoiceBox.getValue();
                        int contactId = ReportsQuery.convertContactNameToId(contactChoice);
                        appTableView.setItems(ReportsQuery.getSelectedContact(contactId));
                        countLbl.setText(ReportsQuery.getSelectedContact(contactId).size() + " appointments");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        // Gets rid of error message, does not change functionality
                    }
                });
            } else if (newValue.equals(firstChoice.get(4))) {
                try {
                    secondChoiceBox.setItems(emptyList);
                    ObservableList<Appointments> pastList = ReportsQuery.getPastAppointments();
                    appTableView.setItems(pastList);
                    countLbl.setText(pastList.size() + " appointments");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    // Gets rid of error message, does not change functionality
                }
            }
        });
    }

    /**
     * Used by the menu form to send the username to the reports controller.
     */
    public void sendUser(String username) {
        currentUserLbl.setText(username);
    }
}
