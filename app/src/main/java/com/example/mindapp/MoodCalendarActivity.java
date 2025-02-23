package com.example.mindapp;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.GregorianCalendar;

public class MoodCalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private MoodDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_calendar);

        calendarView = findViewById(R.id.calendarView);
        db = MoodDatabase.getInstance(this);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = new GregorianCalendar(year, month, dayOfMonth).getTimeInMillis();
            showMoodsForSelectedDate(selectedDate);
        });
    }

    private void showMoodsForSelectedDate(long date) {
        new Thread(() -> {
            List<MoodEntry> moodEntries = db.moodDao().getMoodsForDate(date);
            runOnUiThread(() -> {
                if (moodEntries.isEmpty()) {
                    Toast.makeText(MoodCalendarActivity.this, "No mood entries for this date", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder moods = new StringBuilder();
                    for (MoodEntry entry : moodEntries) {
                        moods.append(entry.getMood()).append("\n");
                    }
                    Toast.makeText(MoodCalendarActivity.this, moods.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }
}
