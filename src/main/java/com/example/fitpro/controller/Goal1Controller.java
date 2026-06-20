package com.example.fitpro.controller;


import com.example.fitpro.utils.NavigationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.example.fitpro.utils.NavigationUtils.getCurrentStage;

public class Goal1Controller {
    @FXML
    private TextArea recommendedPlanTextArea;

    @FXML
    private TextArea fitnessGoalsTextArea;


    @FXML
    private Label fitnessGoalsLabel;

    @FXML
    private Button getPlanButton;


    @FXML
    private void initialize() {

        loadGoals();
    }

    public void loadGoals() {

        String goalFilePath = "plans/user_goal.txt";
        StringBuilder goals = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(goalFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                goals.append(line).append("\n");
            }
        } catch (IOException e) {
            goals.append("No goals set yet.");
        }
        fitnessGoalsTextArea.setText(goals.toString());
    }

    @FXML
    private void getPlan() {
        String planFilePath = "plans/recommended_plan.txt";
        StringBuilder plan = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(planFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                plan.append(line).append("\n");
            }
        } catch (IOException e) {
            plan.append("Please set your goals first.");
        }
        recommendedPlanTextArea.setText(plan.toString());

    }
    @FXML
    private void fetchPlan(MouseEvent event) {

        File planFile = new File("plans/recommended_plan.txt"); // Path to your workout plan file
        if (!planFile.exists()) {
            recommendedPlanTextArea.setText("No plan set. Please configure your goal in 'Edit'.");
            return;
        }

        StringBuilder planContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(planFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                planContent.append(line).append("\n");
            }
        } catch (IOException e) {
            recommendedPlanTextArea.setText("Error loading the workout plan.");
            e.printStackTrace();
            return;
        }

        recommendedPlanTextArea.setText(planContent.toString());

    }
    @FXML
    private void navigateToGoal2(MouseEvent event) {
        NavigationUtils.navigateTo("/com/example/fitpro/goal2.fxml", getCurrentStage(event));
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