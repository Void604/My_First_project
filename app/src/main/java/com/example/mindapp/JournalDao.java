package com.example.mindapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JournalDao {
    @Insert
    void insert(JournalEntry journalEntry);

    @Query("SELECT * FROM journal_table ORDER BY timestamp DESC")
    List<JournalEntry> getAllEntries();
}
