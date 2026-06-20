package com.example.fitpro.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.fitpro.model.ExerciseRecord;

public class GlobalDataStore {
    private static final ObservableList<ExerciseRecord> exerciseRecords = FXCollections.observableArrayList();

    private GlobalDataStore() {
        // 私有构造函数防止实例化
    }

    public static ObservableList<ExerciseRecord> getExerciseRecords() {
        return exerciseRecords;
    }
}