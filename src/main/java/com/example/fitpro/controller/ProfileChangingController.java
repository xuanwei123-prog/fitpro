package com.example.fitpro.controller;

import com.example.fitpro.utils.NavigationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProfileChangingController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField bodyFatField;

    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private SplitMenuButton genderDropdown;

    @FXML
    private Slider activityLevelSlider;

    @FXML
    private Slider digestiveEfficiencySlider;

    @FXML
    private Label bmrLabel;

    @FXML
    private Label tdeeLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    public static final String USER_DATA_FILE = "user_data.txt";

    private String originalUsername;
    @FXML
    private MenuItem maleOption;

    @FXML
    private MenuItem femaleOption;

    @FXML
    public void initialize() {
        saveButton.setOnMouseClicked(this::saveChanges);
        backButton.setOnMouseClicked(this::backToProfile);
        maleOption.setOnAction(event -> genderDropdown.setText("Male"));
        femaleOption.setOnAction(event -> genderDropdown.setText("Female"));
        maleOption.setOnAction(event -> {
            genderDropdown.setText("Male");
            updateBMRAndTDEE();
        });

        femaleOption.setOnAction(event -> {
            genderDropdown.setText("Female");
            updateBMRAndTDEE();
        });

        // Add listeners for other fields
        heightField.textProperty().addListener((observable, oldValue, newValue) -> updateBMRAndTDEE());
        weightField.textProperty().addListener((observable, oldValue, newValue) -> updateBMRAndTDEE());
        birthdayPicker.valueProperty().addListener((observable, oldValue, newValue) -> updateBMRAndTDEE());
        activityLevelSlider.valueProperty().addListener((observable, oldValue, newValue) -> updateBMRAndTDEE());


        saveButton.setOnMouseClicked(this::saveChanges);
        backButton.setOnMouseClicked(this::backToProfile);
    }

    public void loadUserData(String username, String gender, String birthday, String email, String height, String weight, String bodyFat) {
        originalUsername = username;
        usernameField.setText(username);
        genderDropdown.setText(gender);

        if (birthday != null && !birthday.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthdayPicker.setValue(LocalDate.parse(birthday, formatter));
        } else {
            birthdayPicker.setValue(null);
        }

        emailField.setText(email);
        heightField.setText(height);
        weightField.setText(weight);
        bodyFatField.setText(bodyFat);
    }

    @FXML
    private void saveChanges(MouseEvent event) {
        try {
            File file = new File(USER_DATA_FILE);
            if (!file.exists()) {
                System.err.println("Error: user data file not found.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder updatedData = new StringBuilder();
            String line;

            boolean userUpdated = false;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");

                if (userData.length < 12) {
                    for (int i = userData.length; i < 12; i++) {
                        userData = appendToArray(userData, "");
                    }
                }

                if (userData[0].equals(originalUsername)) {
                    userUpdated = true;

                    // 更新数据
                    userData[0] = usernameField.getText(); // 更新用户名
                    userData[1] = genderDropdown.getText();
                    userData[2] = birthdayPicker.getValue() != null ? birthdayPicker.getValue().toString() : "";
                    userData[3] = emailField.getText();
                    userData[5] = heightField.getText();
                    userData[6] = weightField.getText();
                    userData[7] = bodyFatField.getText();
                    userData[8] = String.valueOf(activityLevelSlider.getValue());
                    userData[9] = String.valueOf(digestiveEfficiencySlider.getValue());
                    userData[10] = bmrLabel.getText().replace(" kcal", "");
                    userData[11] = tdeeLabel.getText().replace(" kcal", "");

                    line = String.join(";", userData);
                }

                updatedData.append(line).append(System.lineSeparator());
            }

            reader.close();

            if (!userUpdated) {
                String[] newUserData = new String[12];
                newUserData[0] = usernameField.getText();
                newUserData[1] = genderDropdown.getText();
                newUserData[2] = birthdayPicker.getValue() != null ? birthdayPicker.getValue().toString() : "";
                newUserData[3] = emailField.getText();
                newUserData[4] = ""; // 保留空值
                newUserData[5] = heightField.getText();
                newUserData[6] = weightField.getText();
                newUserData[7] = bodyFatField.getText();
                newUserData[8] = String.valueOf(activityLevelSlider.getValue());
                newUserData[9] = String.valueOf(digestiveEfficiencySlider.getValue());
                newUserData[10] = bmrLabel.getText().replace(" kcal", "");
                newUserData[11] = tdeeLabel.getText().replace(" kcal", "");

                updatedData.append(String.join(";", newUserData)).append(System.lineSeparator());
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(updatedData.toString().trim());
            writer.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Changes saved successfully!");
        alert.showAndWait();

        backToProfile(event);
    }

    private String[] appendToArray(String[] array, String value) {
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    @FXML
    private void backToProfile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo10/profile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void updateBMRAndTDEE() {
        try {
            // Get selected gender
            String gender = genderDropdown.getText();
            if (gender.equals("gender")) {
                bmrLabel.setText("Please select gender");
                tdeeLabel.setText("Please select gender");
                return;
            }

            // Parse height, weight, and birthday
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            LocalDate birthday = birthdayPicker.getValue();
            if (birthday == null) {
                bmrLabel.setText("Please select birthday");
                tdeeLabel.setText("Please select birthday");
                return;
            }
            int age = LocalDate.now().getYear() - birthday.getYear();

            // Calculate BMR based on gender
            double bmr;
            if (gender.equals("Male")) {
                bmr = 66.5 + (13.8 * weight) + (5.0 * height) - (6.8 * age);
            } else if (gender.equals("Female")) {
                bmr = 665.1 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
            } else {
                bmrLabel.setText("Invalid gender");
                tdeeLabel.setText("Invalid gender");
                return;
            }

            // Calculate TDEE as BMR / 0.65
            double tdee = bmr / 0.65;

            // Display BMR and TDEE
            bmrLabel.setText(String.format("%.2f kcal", bmr));
            tdeeLabel.setText(String.format("%.2f kcal", tdee));
        } catch (NumberFormatException e) {
            // Handle invalid numeric input
            bmrLabel.setText("Invalid input");
            tdeeLabel.setText("Invalid input");
        }
    }
    @FXML
    private void navigateToHome(MouseEvent event) {
        NavigationUtils.navigateTo("/com/example/fitpro/homepage.fxml", NavigationUtils.getCurrentStage(event));
    }

    @FXML
    private void navigateToNutrition(MouseEvent event) {
        NavigationUtils.navigateTo("/com/example/fitpro/nutrition.fxml", NavigationUtils.getCurrentStage(event));
    }

    @FXML
    private void navigateToLog(MouseEvent event) {
        NavigationUtils.navigateTo("/com/example/fitpro/log.fxml", NavigationUtils.getCurrentStage(event));
    }

    @FXML
    private void navigateToProgress(MouseEvent event) {
        NavigationUtils.navigateTo("/com/example/fitpro/progress.fxml", NavigationUtils.getCurrentStage(event));
    }

    @FXML
    private void navigateToDevices(MouseEvent event) {
        NavigationUtils.navigateTo("/com/example/fitpro/device.fxml", NavigationUtils.getCurrentStage(event));
    }

    @FXML
    private void navigateToProfile(MouseEvent event) {
        NavigationUtils.navigateTo("/com/example/fitpro/profile.fxml", NavigationUtils.getCurrentStage(event));
    }

    @FXML
    private void navigateToGoal(MouseEvent event) {
        NavigationUtils.navigateTo("/com/example/fitpro/goal1.fxml", NavigationUtils.getCurrentStage(event));
    }

}


















