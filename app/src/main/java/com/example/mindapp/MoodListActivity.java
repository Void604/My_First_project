package com.example.mindapp;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MoodListActivity extends AppCompatActivity {

    private MoodDatabase db;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_list);

        listView = findViewById(R.id.listView);

        db = MoodDatabase.getInstance(this);

        new Thread(() -> {
            List<MoodEntry> moodEntries = db.moodDao().getAllMoods();
            runOnUiThread(() -> {
                MoodListAdapter adapter = new MoodListAdapter(MoodListActivity.this, moodEntries);
                listView.setAdapter(adapter);
            });
        }).start();
    }
}
