package com.example.fitpro.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;
    @FXML
    private Hyperlink forgetPasswordLink;
    @FXML
    private Hyperlink registerLink;

    private static final String FILE_PATH = "user_data.txt";

    @FXML
    public void initialize() {
        signInButton.setOnAction(event -> handleLogin());
        forgetPasswordLink.setOnAction(event -> handleForgetPassword());
        registerLink.setOnAction(event -> handleRegister());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }

        boolean loginSuccessful = isLoginSuccessful(username, password);

        if (loginSuccessful) {
            showAlert("Success", "Login successful!");

            // 跳转到主页
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fitpro/homepage.fxml"));
                Stage stage = (Stage) signInButton.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load the homepage.");
            }
        } else {
            showAlert("Error", "Invalid username or password!");
        }
    }

    private boolean isLoginSuccessful(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(username) && parts[4].equals(password)) { // 密码是第5个字段
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void handleForgetPassword() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Reset Password");

        Label label = new Label("Enter your new password:");
        PasswordField newPasswordField = new PasswordField();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(event -> {
            String username = usernameField.getText();
            String newPassword = newPasswordField.getText();

            if (username.isEmpty() || newPassword.isEmpty()) {
                showAlert("Error", "Please fill in all fields!");
            } else {
                if (updatePassword(username, newPassword)) {
                    showAlert("Success", "Password updated successfully!");
                    dialog.close();
                } else {
                    showAlert("Error", "User not found!");
                }
            }
        });

        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(300, 150);
        label.setLayoutX(30);
        label.setLayoutY(20);
        newPasswordField.setLayoutX(30);
        newPasswordField.setLayoutY(50);
        submitButton.setLayoutX(110);
        submitButton.setLayoutY(90);

        pane.getChildren().addAll(label, newPasswordField, submitButton);

        Scene scene = new Scene(pane);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private boolean updatePassword(String username, String newPassword) {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            boolean userFound = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(username)) {
                    lines.add(username + ";" + newPassword);
                    userFound = true;
                } else {
                    lines.add(line);
                }
            }
            reader.close();

            if (userFound) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
                writer.close();
            }
            return userFound;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fitpro/register.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) registerLink.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



