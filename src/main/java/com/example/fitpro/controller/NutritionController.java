package com.example.fitpro.controller;

import com.example.fitpro.utils.NavigationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class NutritionController {

    @FXML
    private TextField kjInput; // Input field for kilojoules

    @FXML
    private TextField calInput; // Input field for calories

    @FXML
    public void initialize() {
        // Add listeners to clear the other field when one field is being edited
        kjInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                calInput.clear(); // Clear calories input if kilojoules input is modified
            }
        });

        calInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                kjInput.clear(); // Clear kilojoules input if calories input is modified
            }
        });
        try {
            foodData = loadFoodData();
        } catch (IOException e) {
            showAlert("File Error", "Failed to load food data. Please check the file path.");
        }
    }

    /**
     * Handles the conversion when the convert button is clicked.
     *
     * @param event Mouse event triggered by the button click
     */
    @FXML
    private void handleConversion(MouseEvent event) {
        try {
            if (!kjInput.getText().isEmpty()) {
                // Convert kilojoules to calories
                double kilojoules = Double.parseDouble(kjInput.getText());
                double calories = kilojoules / 4.184; // Conversion formula
                calInput.setText(String.format("%.2f", calories));
            } else if (!calInput.getText().isEmpty()) {
                // Convert calories to kilojoules
                double calories = Double.parseDouble(calInput.getText());
                double kilojoules = calories * 4.184; // Conversion formula
                kjInput.setText(String.format("%.2f", kilojoules));
            } else {
                showWarning("Please enter a value in one of the fields."); // Warning if both fields are empty
            }
        } catch (NumberFormatException e) {
            showWarning("Invalid input. Please enter a valid number."); // Warning for invalid input
        }
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
    //below is food library.
    @FXML
    private TextField searchFoodInput; // Input field for searching food name
    @FXML
    private Button searchButton; // Button to initiate food search
    @FXML
    private TextArea foodResultArea; // Text area to display the search result

    // Map to store food data loaded from file
    private Map<String, String[]> foodData;



    /**
     * Handles the food search functionality.
     */
    @FXML
    private void handleSearchFood() {
        String foodName = searchFoodInput.getText().trim().toLowerCase(); // Get user input and convert to lowercase
        if (foodName.isEmpty()) {
            showAlert("Input Error", "Please enter a food name."); // Show alert if input is empty
            return;
        }

        // Check if the food exists in the data
        if (foodData.containsKey(foodName)) {
            String[] nutritionData = foodData.get(foodName);

            // Parse nutrition data
            double carbs = Double.parseDouble(nutritionData[0]);
            double protein = Double.parseDouble(nutritionData[1]);
            double fats = Double.parseDouble(nutritionData[2]);
            double totalCalories = Double.parseDouble(nutritionData[3]);

            // Calculate calories for each macronutrient
            double carbsKcal = carbs * 4;
            double proteinKcal = protein * 4;
            double fatsKcal = fats * 9;

            // Format the result string
            String result = String.format(
                    "Results:\n" +
                            "Food Name: %s\n" +
                            "Caloric content per 100g:\n" +
                            "- Carbs: %.1fg     %.0f kcal\n" +
                            "- Protein: %.1fg   %.0f kcal\n" +
                            "- Fats: %.1fg      %.0f kcal\n" +
                            "- Total Calories: %.0f kcal",
                    capitalize(foodName), carbs, carbsKcal, protein, proteinKcal, fats, fatsKcal, totalCalories
            );
            foodResultArea.setText(result); // Display the result in the text area
        } else {
            foodResultArea.setText("Food not found. Please try another item."); // Show message if food is not found
        }
    }

    /**
     * Loads food data from a file into a Map.
     *
     * @return Map containing food data
     * @throws IOException If the file cannot be read
     */
    private Map<String, String[]> loadFoodData() throws IOException {
        Map<String, String[]> foodMap = new HashMap<>();
        InputStream inputStream = getClass().getResourceAsStream("/food_library.txt");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: /food_library.txt");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":"); // Split line into food name and nutrition data
                String foodName = parts[0].trim().toLowerCase(); // Convert food name to lowercase for case-insensitive search
                String[] nutritionData = parts[1].trim().split(","); // Split nutrition data into components
                foodMap.put(foodName, nutritionData); // Add to the map
            }
        }
        return foodMap;
    }

    /**
     * Displays an alert dialog with a message.
     *
     * @param title The title of the alert
     * @param message The message to display
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Capitalizes the first letter of a string.
     *
     * @param text The input string
     * @return The capitalized string
     */
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text; // Return original text if null or empty
        return text.substring(0, 1).toUpperCase() + text.substring(1); // Capitalize the first letter
    }
    //below is Daily intake calculator
    @FXML
    private TextField calorieGoalInput; // Input for daily calorie goal

    @FXML
    private TextArea resultTextArea; // Text area to display calculation results

    @FXML
    private Button calculateButton; // Button to trigger calculation

    /**
     * Handles the calculation of macronutrient distribution.
     */
    @FXML
    private void calculateIntake() {
        try {
            // Get the daily calorie goal from user input
            double calorieGoal = Double.parseDouble(calorieGoalInput.getText());

            // Macronutrient percentages
            double carbPercentage = 0.40; // 40%
            double fatPercentage = 0.25;  // 25%
            double proteinPercentage = 0.35; // 35%

            // Calculate macronutrient calories
            double carbCalories = calorieGoal * carbPercentage;
            double fatCalories = calorieGoal * fatPercentage;
            double proteinCalories = calorieGoal * proteinPercentage;

            // Calculate macronutrient grams
            double carbGrams = carbCalories / 4; // 1g carbs = 4 kcal
            double fatGrams = fatCalories / 9;  // 1g fat = 9 kcal
            double proteinGrams = proteinCalories / 4; // 1g protein = 4 kcal

            // Format results and display them in the text area
            String result = String.format(
                    "Your daily intake based on %d kcal:\n" +
                            "- Carbs: %.1fg (%.0f kcal)\n" +
                            "- Fats: %.1fg (%.0f kcal)\n" +
                            "- Proteins: %.1fg (%.0f kcal)",
                    (int) calorieGoal, carbGrams, carbCalories, fatGrams, fatCalories, proteinGrams, proteinCalories
            );

            resultTextArea.setText(result);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for your calorie goal.");
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