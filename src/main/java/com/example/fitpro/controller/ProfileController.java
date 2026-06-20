package com.example.fitpro.controller;

import com.example.fitpro.utils.NavigationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ProfileController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label birthdayLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label bfpLabel;

    @FXML
    private Label bmrLabel;

    @FXML
    private Label tdeeLabel;

    @FXML
    private Slider activityLevelSlider;

    @FXML
    private Slider digestiveEfficiencySlider;

    @FXML
    private Button editButton;

    private static final String USER_DATA_FILE = "user_data.txt";

    @FXML
    public void initialize() {
        loadUserData();
        calculateBMRAndTDEE();
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            if ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");

                usernameLabel.setText(userData.length > 0 ? userData[0] : "");
                genderLabel.setText(userData.length > 1 ? userData[1] : "");
                birthdayLabel.setText(userData.length > 2 ? userData[2] : "");
                emailLabel.setText(userData.length > 3 ? userData[3] : "");
                heightLabel.setText(userData.length > 5 ? userData[5] + " cm" : "");
                weightLabel.setText(userData.length > 6 ? userData[6] + " kg" : "");
                bfpLabel.setText(userData.length > 7 ? userData[7] + "%" : "");

                if (userData.length > 8) {
                    activityLevelSlider.setValue(Double.parseDouble(userData[8]));
                } else {
                    activityLevelSlider.setValue(0);
                }

                if (userData.length > 9) {
                    digestiveEfficiencySlider.setValue(Double.parseDouble(userData[9]));
                } else {
                    digestiveEfficiencySlider.setValue(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateBMRAndTDEE() {
        try {
            // 获取用户性别、生日、身高、体重
            String gender = genderLabel.getText();
            String birthday = birthdayLabel.getText();
            String heightText = heightLabel.getText().replace(" cm", "");
            String weightText = weightLabel.getText().replace(" kg", "");

            if (gender.isEmpty() || birthday.isEmpty() || heightText.isEmpty() || weightText.isEmpty()) {
                bmrLabel.setText("N/A");
                tdeeLabel.setText("N/A");
                return;
            }

            int height = Integer.parseInt(heightText);
            int weight = Integer.parseInt(weightText);

            // 计算年龄
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);
            int age = Period.between(birthDate, LocalDate.now()).getYears();

            // 计算 BMR
            double bmr;
            if (gender.equalsIgnoreCase("male")) {
                bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
            } else if (gender.equalsIgnoreCase("female")) {
                bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
            } else {
                bmrLabel.setText("Invalid Gender");
                tdeeLabel.setText("N/A");
                return;
            }

            double activityFactor = activityLevelSlider.getValue();
            double tdee = bmr * (1.2 + (activityFactor * 0.2)); // 活动因子范围为 1.2 到 2.2

            bmrLabel.setText(String.format("%.2f kcal", bmr));
            tdeeLabel.setText(String.format("%.2f kcal", tdee));
        } catch (Exception e) {
            e.printStackTrace();
            bmrLabel.setText("Error");
            tdeeLabel.setText("Error");
        }
    }

    public void navigateToProfileChanging(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fitpro/profileChanging.fxml"));
            Parent root = loader.load();

            ProfileChangingController profileChangingController = loader.getController();

            profileChangingController.loadUserData(
                    usernameLabel.getText(),
                    genderLabel.getText(),
                    birthdayLabel.getText(),
                    emailLabel.getText(),
                    heightLabel.getText().replace(" cm", ""),
                    weightLabel.getText().replace(" kg", ""),
                    bfpLabel.getText().replace("%", "")
            );

            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
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








