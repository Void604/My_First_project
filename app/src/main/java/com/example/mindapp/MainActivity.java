package com.example.mindapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private EditText responseEditText;
    private Button submitButton;
    private Button viewEntriesButton;
    private Button viewAnalysisButton;
    private Button openJournalButton;
    private Button openCalendarButton;
    private Button logoutButton;

    private MoodDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        responseEditText = findViewById(R.id.responseEditText);
        submitButton = findViewById(R.id.submitButton);
        viewEntriesButton = findViewById(R.id.viewEntriesButton);
        viewAnalysisButton = findViewById(R.id.viewAnalysisButton);
        openJournalButton = findViewById(R.id.openJournalButton);
        openCalendarButton = findViewById(R.id.openCalendarButton);
        logoutButton = findViewById(R.id.logoutButton);

        db = MoodDatabase.getInstance(this);

        NotificationHelper.createNotificationChannel(this);
        scheduleDailyReminder();

        submitButton.setOnClickListener(v -> {
            String userResponse = responseEditText.getText().toString();
            if (!userResponse.isEmpty()) {
                saveMood(userResponse);
                responseEditText.setText(""); // Clear the input after submission
            } else {
                Toast.makeText(MainActivity.this, "Please enter your mood!", Toast.LENGTH_SHORT).show();
            }
        });

        viewEntriesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoodListActivity.class);
            startActivity(intent);
        });

        viewAnalysisButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoodAnalysisActivity.class);
            startActivity(intent);
        });

        openJournalButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JournalActivity.class);
            startActivity(intent);
        });

        openCalendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoodCalendarActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void saveMood(String mood) {
        MoodEntry moodEntry = new MoodEntry();
        moodEntry.setMood(mood);
        moodEntry.setTimestamp(System.currentTimeMillis());

        new Thread(() -> db.moodDao().insert(moodEntry)).start();

        Toast.makeText(MainActivity.this, "Response submitted!", Toast.LENGTH_SHORT).show();
    }

    private void scheduleDailyReminder() {
        Intent intent = new Intent(MainActivity.this, ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);  // Set the reminder time to 8 PM
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long startTime = calendar.getTimeInMillis();
        long intervalTime = AlarmManager.INTERVAL_DAY;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
    }
}
