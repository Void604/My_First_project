package com.example.mindapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MoodListAdapter extends BaseAdapter {

    private final Context context;
    private final List<MoodEntry> moodEntries;
    private final LayoutInflater inflater;

    public MoodListAdapter(Context context, List<MoodEntry> moodEntries) {
        this.context = context;
        this.moodEntries = moodEntries;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return moodEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return moodEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mood_list_item, parent, false);
        }

        TextView moodTextView = convertView.findViewById(R.id.moodTextView);
        TextView timestampTextView = convertView.findViewById(R.id.timestampTextView);

        MoodEntry moodEntry = moodEntries.get(position);

        moodTextView.setText(moodEntry.getMood());
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(moodEntry.getTimestamp());
        timestampTextView.setText(formattedDate);

        return convertView;
    }
}
