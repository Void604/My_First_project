package com.example.mindapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mood_table")
public class MoodEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mood;
    private long timestamp;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
