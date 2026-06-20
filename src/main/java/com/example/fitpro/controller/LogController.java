package com.example.fitpro.controller;

import com.example.fitpro.model.ExerciseRecord;
import com.example.fitpro.utils.GlobalDataStore;
import com.example.fitpro.utils.NavigationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogController {


    @FXML
    private DatePicker datePicker;
    @FXML
    private final ObservableList<ExerciseRecord> exerciseRecords = GlobalDataStore.getExerciseRecords();

    @FXML
    private TextField durationField;

    @FXML
    private TextField sportField;

    @FXML
    private Slider intensitySlider;

    @FXML
    private Label calorieEstimateLabel;

    @FXML
    private TableView<ExerciseRecord> exerciseTable;

    @FXML
    private TableColumn<ExerciseRecord, String> dateColumn;

    @FXML
    private TableColumn<ExerciseRecord, String> sportColumn;

    @FXML
    private TableColumn<ExerciseRecord, Double> durationColumn;

    @FXML
    private TableColumn<ExerciseRecord, Integer> intensityColumn;

    @FXML
    private TableColumn<ExerciseRecord, String> caloriesColumn;







    @FXML
    public void initialize() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        sportColumn.setCellValueFactory(new PropertyValueFactory<>("sport"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        intensityColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));



        exerciseTable.setItems(exerciseRecords);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> updateCalorieEstimate());
        intensitySlider.valueProperty().addListener((observable, oldValue, newValue) -> updateCalorieEstimate());
    }
    private void updateCalorieEstimate() {
        String durationText = durationField.getText();
        try {
            double duration = Double.parseDouble(durationText);
            int intensity = (int) intensitySlider.getValue();
            double caloriesBurned = duration * intensity * 50;
            calorieEstimateLabel.setText(String.format("%.1f kcal", caloriesBurned));
        } catch (NumberFormatException e) {
            calorieEstimateLabel.setText("Invalid duration");
        }
    }
    public void addExerciseReport(){
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "N/A";
        String sports = sportField.getText().isEmpty() ? "N/A" : sportField.getText();
        String durationText = durationField.getText().isEmpty() ? "0" : durationField.getText();
        int intensity = (int)intensitySlider.getValue();

        try {
        double duration = Double.parseDouble(durationText);

        double caloriesBurned = duration * intensity * 50;



        // Add to TableView
        exerciseRecords.add(new ExerciseRecord(date, sports, duration, intensity,caloriesBurned));

     clearInputFields();
        } catch (NumberFormatException e) {
            showAlert("Error", "Duration must be a number.");
        }


}
    private void clearInputFields() {
        datePicker.setValue(null);
        sportField.clear();
        durationField.clear();
        intensitySlider.setValue(0);
        calorieEstimateLabel.setText("0.0 kcal");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
    @FXML
    private void navigateToProgress(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fitpro/progress.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());

            // 获取 ProgressController 并传递数据
            ProgressController progressController = loader.getController();
            progressController.loadExerciseData(exerciseRecords);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        NavigationUtils.navigateTo("/com/example/fitpro/progress.fxml", NavigationUtils.getCurrentStage(event));
    }
    private void loadExerciseData(ObservableList<ExerciseRecord> records) {
        this.exerciseRecords.setAll(records);
    }
}
