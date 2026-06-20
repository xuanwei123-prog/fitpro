package com.example.fitpro.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private SplitMenuButton genderMenu;

    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private TextField emailField;

    @FXML
    private TextField otpField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Button otpButton;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    private String generatedOtp;

    /**
     * Generates a 6-digit OTP and displays it in the console.
     */
    @FXML
    private void generateOtp(ActionEvent event) {
        generatedOtp = generateRandomOtp();
        System.out.println("Generated OTP: " + generatedOtp);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OTP Generated");
        alert.setHeaderText("Your OTP is:");
        alert.setContentText(generatedOtp);
        alert.showAndWait();
    }

    /**
     * Saves user information to a file and navigates back to the login page.
     */
    @FXML
    private void goToNext(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        saveUserData();
        navigateToPage("/com/example/fitpro/login.fxml");
    }

    /**
     * Navigates back to the login page without saving data.
     */
    @FXML
    private void goBack(ActionEvent event) {
        navigateToPage("/com/example/fitpro/login.fxml");
    }

    /**
     * Generates a random 6-digit OTP.
     *
     * @return The generated OTP.
     */
    private String generateRandomOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Validates the input fields and OTP.
     *
     * @return true if input is valid; false otherwise.
     */
    private boolean validateInput() {
        String username = usernameField.getText();
        String gender = genderMenu.getText();
        String birthday = birthdayPicker.getValue() != null ? birthdayPicker.getValue().toString() : "";
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String enteredOtp = otpField.getText();

        if (username.isEmpty() || gender.isEmpty() || birthday.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match.");
            return false;
        }

        if (generatedOtp == null || !generatedOtp.equals(enteredOtp)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid OTP.");
            return false;
        }

        return true;
    }

    /**
     * Saves user data to a .txt file.
     */
    private void saveUserData() {
        String username = usernameField.getText();
        String gender = genderMenu.getText();
        String birthday = birthdayPicker.getValue() != null ? birthdayPicker.getValue().toString() : "";
        String email = emailField.getText();
        String password = passwordField.getText();

        String data = String.join(";", username, gender, birthday, email, password);

        File file = new File("user_data.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.newLine();
            System.out.println("User data saved: " + data);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Failed to save user data.");
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the specified FXML page.
     */
    private void navigateToPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load the page.");
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private MenuItem maleOption;

    @FXML
    private MenuItem femaleOption;

    @FXML
    private void initialize() {
        // Initialize gender selection
        maleOption.setOnAction(event -> genderMenu.setText("Male"));
        femaleOption.setOnAction(event -> genderMenu.setText("Female"));
    }
}