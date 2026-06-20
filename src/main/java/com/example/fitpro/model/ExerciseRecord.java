package com.example.fitpro.model;

import java.io.Serializable;

public class ExerciseRecord implements Serializable {

    private static final long serialVersionUID = 1L; // For serialization compatibility
    private String date; // The date of the exercise session (e.g., "2024-12-22").
    private String sport; // The type of exercise (e.g., "Running").
    private double duration; // Duration of the exercise in minutes.
    private int intensityLevel; // Intensity level (1-5).
    private double caloriesBurned; // Estimated calories burned during the session.

    /**
     * Default constructor for ExerciseRecord.
     */
    public ExerciseRecord() {
        this.date = "";
        this.sport = "";
        this.duration = 0;
        this.intensityLevel = 1;
        this.caloriesBurned = 0.0;
    }

    /**
     * Parameterized constructor for ExerciseRecord.
     *
     * @param date           The date of the exercise session.
     * @param sport          The type of exercise performed.
     * @param duration       The duration of the exercise in minutes.
     * @param intensityLevel The intensity level of the exercise (1-5).
     * @param caloriesBurned The estimated calories burned during the session.
     */
    public ExerciseRecord(String date, String sport, double duration, int intensityLevel, double caloriesBurned) {
        this.date = date;
        this.sport = sport;
        this.duration = duration;
        this.intensityLevel = intensityLevel;
        this.caloriesBurned = caloriesBurned;
    }

    // Getters and setters for all fields.

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIntensityLevel() {
        return intensityLevel;
    }

    public void setIntensityLevel(int intensityLevel) {
        this.intensityLevel = intensityLevel;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    /**
     * Calculate the estimated calories burned based on intensity and duration.
     * Adjust the formula as needed for more accurate results.
     *
     * @param weight The user's weight in kilograms (optional parameter for customization).
     */
    public void calculateCaloriesBurned(double weight) {
        // Example formula: (duration in minutes) * (MET value based on intensity) * (weight in kg) / 200
        double metValue;
        switch (intensityLevel) {
            case 1:
                metValue = 3.0; // Light intensity
                break;
            case 2:
                metValue = 5.0; // Moderate intensity
                break;
            case 3:
                metValue = 8.0; // Intense activity
                break;
            case 4:
                metValue = 12.0; // Very intense activity
                break;
            case 5:
                metValue = 15.0; // Extreme intensity
                break;
            default:
                metValue = 5.0; // Default to moderate
        }
        this.caloriesBurned = duration * metValue ;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d min, Intensity: %d, %.1f kcal", sport, date, duration, intensityLevel, caloriesBurned);
    }
    public int getIntensity() {
        return intensityLevel; // 返回 intensityLevel 的值
    }

    public double getCalories() {
        return caloriesBurned; // 返回 caloriesBurned 的值
    }
}