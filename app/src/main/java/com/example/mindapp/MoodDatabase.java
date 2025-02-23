package com.example.mindapp;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MoodEntry.class, JournalEntry.class, User.class}, version = 3)  // Incremented the version number
public abstract class MoodDatabase extends RoomDatabase {
    private static MoodDatabase instance;

    public abstract MoodDao moodDao();
    public abstract JournalDao journalDao();
    public abstract UserDao userDao();

    public static synchronized MoodDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MoodDatabase.class, "mood_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
