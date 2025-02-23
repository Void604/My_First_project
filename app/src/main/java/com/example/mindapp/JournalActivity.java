package com.example.mindapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class JournalActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button saveButton;
    private MoodDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        saveButton = findViewById(R.id.saveButton);

        db = MoodDatabase.getInstance(this);

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String content = contentEditText.getText().toString();
            if (!title.isEmpty() && !content.isEmpty()) {
                saveJournalEntry(title, content);
                titleEditText.setText("");
                contentEditText.setText("");
            } else {
                Toast.makeText(JournalActivity.this, "Please enter a title and content!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveJournalEntry(String title, String content) {
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(title);
        journalEntry.setContent(content);
        journalEntry.setTimestamp(System.currentTimeMillis());

        new Thread(() -> db.journalDao().insert(journalEntry)).start();

        Toast.makeText(JournalActivity.this, "Journal entry saved!", Toast.LENGTH_SHORT).show();
    }
}
