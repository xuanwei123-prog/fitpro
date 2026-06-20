package com.example.fitpro.controller;

import com.example.fitpro.model.ExerciseRecord;
import com.example.fitpro.utils.NavigationUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;

public class ProgressController {

    @FXML
    private PieChart nutrientPieChart; // PieChart for nutrient intake ratio

    @FXML
    public void initialize() {
        // Create pie chart data
        PieChart.Data carbs = new PieChart.Data("Carbohydrates (40%)", 40);
        PieChart.Data protein = new PieChart.Data("Protein (35%)", 35);
        PieChart.Data fats = new PieChart.Data("Fats (25%)", 25);

        // Add data to pie chart
        nutrientPieChart.getData().addAll(carbs, protein, fats);

        // Set a title for the chart (optional)
        nutrientPieChart.setTitle("Nutrient Intake Ratio");
        sportAxis.setLabel("Sports");
        durationAxis.setLabel("Duration (minutes)");

        exerciseSummaryChart.setTitle("Exercise Summary");
        exerciseSummaryChart.getXAxis().setLabel("Sports");
        exerciseSummaryChart.getYAxis().setLabel("Duration (minutes)");
    }



    @FXML
    private BarChart<String, Number> exerciseSummaryChart;

    @FXML
    private CategoryAxis sportAxis;

    @FXML
    private NumberAxis durationAxis;

    private ObservableList<ExerciseRecord> exerciseRecords; // Pass this from LogController

    public void loadExerciseData(ObservableList<ExerciseRecord> records) {
        this.exerciseRecords = records;

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Exercise Duration");

        for (ExerciseRecord record : exerciseRecords) {
            series.getData().add(new XYChart.Data<>(record.getSport(), record.getDuration()));
        }

        exerciseSummaryChart.getData().clear();
        exerciseSummaryChart.getData().add(series);
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