package com.example.fitpro.controller;

import com.example.fitpro.utils.NavigationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.fitpro.utils.NavigationUtils.getCurrentStage;


public class Goal2Controller {
    @FXML
    private SplitMenuButton goalTypeSplitMenuButton;
    @FXML
    private TextField targetWeightField;

    @FXML
    private TextField targetBodyFatField;

    @FXML
    private TextField exerciseFrequencyField;

    @FXML
    private TextArea customGoalsTextArea;

    @FXML
    private void setGoalTypeLoseWeight() {
        goalTypeSplitMenuButton.setText("Lose Weight");
    }

    @FXML
    private void setGoalTypeGainMuscle() {
        goalTypeSplitMenuButton.setText("Gain Muscle");
    }

    @FXML
    private void setGoalTypeMaintainWeight() {
        goalTypeSplitMenuButton.setText("Maintain Weight");
    }



    @FXML
    private void saveGoals(MouseEvent event) {
        String goalType = goalTypeSplitMenuButton.getText();
        String targetWeight = targetWeightField.getText();
        String targetBodyFat = targetBodyFatField.getText();
        String exerciseFrequency = exerciseFrequencyField.getText();
        String customGoals = customGoalsTextArea.getText();

        if ("TYPE".equals(goalType) || targetWeight.isEmpty() || targetBodyFat.isEmpty() || exerciseFrequency.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        String goalFilePath = "plans/user_goal.txt";
        File goalfile = new File(goalFilePath);
        File parentDir = goalfile.getParentFile();
        if (parentDir != null && !parentDir.exists()){
            parentDir.mkdirs();
        }
        String planFilePath = "plans/recommended_plan.txt";
        File planfile = new File(goalFilePath);
        File parentDir2 = planfile.getParentFile();
        if (parentDir2 != null && !parentDir2.exists()){
            parentDir2.mkdirs();
        }
        try (BufferedWriter goalWriter = new BufferedWriter(new FileWriter(goalFilePath));
             BufferedWriter planWriter = new BufferedWriter(new FileWriter(planFilePath))) {

            goalWriter.write("Goal Type: " + goalType + "\n");
            goalWriter.write("Target Weight: " + targetWeight + " KG\n");
            goalWriter.write("Target Body Fat: " + targetBodyFat + "%\n");
            goalWriter.write("Weekly Exercise Frequency: " + exerciseFrequency + "\n");
            goalWriter.write("Custom Goals: " + customGoals + "\n");

            if ("Lose Weight".equals(goalType)) {
                planWriter.write("1. Cardio 4-5 times per week (e.g., running, cycling).\n");
                planWriter.write("2. Strength training 2-3 times per week.\n");
                planWriter.write("3. Aim for a calorie deficit with a balanced diet.\n");
            } else if ("Gain Muscle".equals(goalType)) {
                planWriter.write("1. Strength training 4-5 times per week (focus on compound exercises).\n");
                planWriter.write("2. Increase protein intake and eat in a calorie surplus.\n");
                planWriter.write("3. Include rest days for muscle recovery.\n");
            } else if ("Maintain Weight".equals(goalType)) {
                planWriter.write("1. Mix of cardio and strength training 3-4 times per week.\n");
                planWriter.write("2. Maintain a balanced diet with stable calorie intake.\n");
                planWriter.write("3. Stay consistent with your routine.\n");
            }

            planWriter.write("\nTarget Weight: " + targetWeight + " KG\n");
            planWriter.write("Exercise Frequency: " + exerciseFrequency + " times per week.");


            resetFields(event);
            navigateToGoal(event);



            System.out.println("Goals saved successfully!");
            new Alert(Alert.AlertType.INFORMATION,"Goals saved successfully!").showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void resetFields(MouseEvent event) {
        goalTypeSplitMenuButton.setText("TYPE");
        targetWeightField.clear();
        targetBodyFatField.clear();
        exerciseFrequencyField.clear();
        customGoalsTextArea.clear();
    }
    @FXML
    private void navigateToGoal2(MouseEvent event) {
        try {
            NavigationUtils.navigateTo("/com/example/fitpro/goal1.fxml", getCurrentStage(event));
            System.out.println("goal1.fxml successfully loaded.");}
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to load");
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
