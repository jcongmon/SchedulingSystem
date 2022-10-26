package com.congmonj;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main driver of the application.
 * Opens the login form and starts the connection between the MySQL database and program via JDBC.
 *
 * @author Jonathan Congmon
 */
public class Main extends Application {
    /**
     * Uses ResourceBundle for English/French language under /lang/
     * Loads the fxml file login.
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Locale locale = Locale.getDefault();
            ResourceBundle messages = ResourceBundle.getBundle("lang.MessageBundle", locale);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle(messages.getString("Title"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("error" + e.getCause());
        }
    }
    /**
     * Opens and closes the database connection via JDBC.
     * Starts the program.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
