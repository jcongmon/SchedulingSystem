package controller;

import helper.ClientsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the addClient.fxml form.
 * Stores the entered information via the onActionSave event to the MySQL database.
 * Lambda Expression - Line 190 for event handling of the stateChoiceBox. When the countryChoiceBox has an input and the value changes, the attached listener signals to the second choice box what to populate the stateChoiceBox with. The lambda expression shortens the code. *
 * @author Jonathan Congmon
 */
public class AddClientController implements Initializable {
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
     * Address Text Field
     */
    @FXML
    private TextField addressTxt;
    /**
     * Country Name Choice Box
     */
    @FXML
    private ChoiceBox<String> countryChoiceBox;
    /**
     * Login Label
     */
    @FXML
    private Label loginIdLbl;
    /**
     * Name Text Field
     */
    @FXML
    private TextField nameTxt;
    /**
     * Phone Number Text Field
     */
    @FXML
    private TextField phoneTxt;
    /**
     * Postal Code Text Field
     */
    @FXML
    private TextField postalTxt;
    /**
     * State Name Choice Box
     */
    @FXML
    private ChoiceBox<String> stateChoiceBox;
    /**
     * ZoneID Label
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
     * If there are issues, data is saved as a Customers object and passed to the database.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            if (nameTxt.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid name.");
                alert.showAndWait();
            } else if (addressTxt.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid address.");
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
            } else if (postalTxt.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a postal code.");
                alert.showAndWait();
            } else if (phoneTxt.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid phone number.");
                alert.showAndWait();
            } else {
                String name = nameTxt.getText();
                String address = addressTxt.getText();
                String state = stateChoiceBox.getValue();
                String postal = postalTxt.getText();
                String phone = phoneTxt.getText();
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                String createdBy = currentUserLbl.getText();
                Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
                String lastUpdatedBy = currentUserLbl.getText();

                int divId = ClientsQuery.selectDivisonIDfromDivision(state);
                ClientsQuery.insert(name, address, postal, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divId);

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
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the choice boxes on the form and sets the ZoneID and current username to their respective labels.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //Choice boxes
        try {
            countryChoiceBox.setItems(ClientsQuery.selectCountries());
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

        Locale.setDefault(locale);
        zoneIdLbl.setText("ZoneID: " + zoneId + ", " + locale);
        loginIdLbl.setText("Current User: ");
    }

    /**
     * Used in the MenuController to bring the username to the addClient form.
     */
    public void sendUser(String username) {
        currentUserLbl.setText(username);
    }
}
