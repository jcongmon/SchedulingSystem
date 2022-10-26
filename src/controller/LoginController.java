package controller;

import helper.AppointmentsQuery;
import helper.UsersQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the login.fxml form.
 * onActionLogin takes the entered username and password and checks if the two match in the database. A text file is made storing the logins with the username and time.
 * If the user is successful on login, a warning box appears if there are appointments in the next 15 minutes of the current system time.
 *
 * @author Jonathan Congmon
 */
public class LoginController {
    /**
     * Gets the system locale
     */
    Locale locale = Locale.getDefault();
    /**
     * Uses ResourceBundle for English/French language under /lang/
     */
    ResourceBundle messages = ResourceBundle.getBundle("lang.MessageBundle", locale);
    Stage stage;

    /**
     * Error Label
     */
    @FXML
    private Label errorLbl;
    /**
     * Login Label
     */
    @FXML
    private Label loginLbl;
    /**
     * Password Text Field
     */
    @FXML
    private TextField passwordTxt;
    /**
     * Username Text Field
     */
    @FXML
    private TextField userTxt;
    /**
     * ZoneID Label
     */
    @FXML
    private Label zoneIdLbl;
    /**
     * Header Log In Label
     */
    @FXML
    private Label headerLbl;
    /**
     * Login Button
     */
    @FXML
    private Button loginBtn;
    /**
     * Exit Button
     */
    @FXML
    private Button exitBtn;
    /**
     * Username Label
     */
    @FXML
    private Label userLbl;
    /**
     * Password Label
     */
    @FXML
    private Label passwordLbl;

    /**
     * When the user clicks the exit button, the program ends.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }
    /**
     * When the user clicks the login button, onActionLogin takes the input from userTxt and passwordTxt and compares to the database values.
     * If the user is successful on login, a warning box appears if there are appointments in the next 15 minutes of the current system time.
     * A file named login_activity.txt is made logging the login activity, if the authentication is successful or not, the username, date, and time of access.
     * Returns to the menu.
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        try {
            String enteredUser = userTxt.getText();
            String enteredPassword = passwordTxt.getText();
            File newFile = new File("login_activity.txt"); //IT KEEPS REWRITING LOGIN_ACTIVITY, HOW TO CHECK IF EXISTS

            FileWriter newWriter = new FileWriter("login_activity.txt", true);
            long currentTime = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            Date convTime = new Date(currentTime);

            if (UsersQuery.selectUser(enteredUser, enteredPassword)) {
                newWriter.write("LOGIN SUCCESS | USER: " + enteredUser + " | TIME OF ACCESS: " + sdf.format(convTime) + "\n");
                newWriter.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/menu.fxml"));
                loader.load();
                ObservableList<Appointments> appointmentList = AppointmentsQuery.appointmentsWithinFifteenMin(LocalDateTime.now());
                if (appointmentList.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(messages.getString("Appointments"));
                    alert.setContentText(messages.getString("ErrorMsg1"));
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(messages.getString("Appointments"));
                    alert.setContentText(appointmentList.size() + messages.getString("UpcomingAppts") + AppointmentsQuery.displayUpcomingAppointments(appointmentList));
                    alert.showAndWait();
                }
                MenuController MController = loader.getController();
                MController.sendUsername(enteredUser);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                newWriter.write("LOGIN FAILURE | USER: " + enteredUser + " | TIME OF ACCESS: " + sdf.format(convTime) + "\n");
                newWriter.close();
                errorLbl.setText(messages.getString("ErrorMessage"));
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(messages.getString("Error"));
                alert.setContentText(messages.getString("ErrorMessage"));
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    /**
     * Populates the labels with respective English or French text based on system language.
     */
    @FXML
    public void initialize() {
        Locale.setDefault(locale);
        ZoneId zoneId = ZoneId.systemDefault();
        zoneIdLbl.setText("ZoneID: " + zoneId + ", " + locale);
        loginLbl.setText(messages.getString("LoginStatus"));
        headerLbl.setText(messages.getString("Header"));
        loginBtn.setText(messages.getString("Login"));
        exitBtn.setText(messages.getString("Exit"));
        userLbl.setText(messages.getString("User"));
        passwordLbl.setText(messages.getString("Password"));
    }
}