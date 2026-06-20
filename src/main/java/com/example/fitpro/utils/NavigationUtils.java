package com.example.fitpro.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import java.net.URL;


public class NavigationUtils {

    // Navigate to a specific FXML file
    public static void navigateTo(String fxmlFile, Stage currentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(NavigationUtils.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Attempting to load: " + fxmlFile);
        URL resource = NavigationUtils.class.getResource(fxmlFile);
        if (resource == null) {
            System.err.println("Resource not found: " + fxmlFile);
        } else {
            System.out.println("Resource found: " + resource);
        }
    }

    // Update profile image across all pages
    public static void updateProfileImageForAllPages(String newImagePath) {
        // You can implement logic here to propagate the new profile image path
        // For example, updating a shared user context or variable
        System.out.println("Profile image updated to: " + newImagePath);
    }

    // Show success message (e.g., after saving)
    public static void showSuccessMessage(String message) {
        System.out.println("SUCCESS: " + message);
        // Optionally, use a JavaFX Alert dialog for user feedback
    }

    // Show error message
    public static void showErrorMessage(String message) {
        System.err.println("ERROR: " + message);
        // Optionally, use a JavaFX Alert dialog for user feedback
    }
    public static Stage getCurrentStage(MouseEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }




}