package com.example.mindapp;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MoodAnalysisActivity extends AppCompatActivity {

    private ListView analysisListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_analysis);

        analysisListView = findViewById(R.id.analysisListView);

        // Fetch mood data and display analysis
        MoodDatabase db = MoodDatabase.getInstance(this);
        new Thread(() -> {
            List<MoodEntry> moodEntries = db.moodDao().getAllMoods();
            runOnUiThread(() -> {
                MoodAnalysisAdapter adapter = new MoodAnalysisAdapter(this, moodEntries);
                analysisListView.setAdapter(adapter);
            });
        }).start();
    }
}
