package com.example.mindapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoodDao {
    @Insert
    void insert(MoodEntry moodEntry);

    @Query("SELECT * FROM mood_table ORDER BY timestamp DESC")
    List<MoodEntry> getAllMoods();
    @Query("SELECT * FROM mood_table WHERE DATE(timestamp / 1000, 'unixepoch') = DATE(:date / 1000, 'unixepoch') ORDER BY timestamp DESC")
    List<MoodEntry> getMoodsForDate(long date);

}
