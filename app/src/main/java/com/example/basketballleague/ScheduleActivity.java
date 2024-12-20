package com.example.basketballleague;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    RecyclerView rv;
    FloatingActionButton add;
    ScheduleDatabase db;
    ArrayList<String> schedule_id, teamA, teamB, date, time;
    ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        rv = findViewById(R.id.sched);
        add = findViewById(R.id.sched_add);

        add.setOnClickListener(v -> {
            Intent intent = new Intent(ScheduleActivity.this, AddSchedule.class);
            startActivity(intent);
        });

        db = new ScheduleDatabase(ScheduleActivity.this);

        schedule_id = new ArrayList<>();
        teamA = new ArrayList<>();
        teamB = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();

        scheduleAdapter = new ScheduleAdapter(ScheduleActivity.this, schedule_id, teamA, teamB, date, time);
        rv.setAdapter(scheduleAdapter);
        rv.setLayoutManager(new LinearLayoutManager(ScheduleActivity.this));

        refreshData();
    }
    private void refreshData() {
        schedule_id.clear();
        teamA.clear();
        teamB.clear();
        date.clear();
        time.clear();
        displayData();
        scheduleAdapter.notifyDataSetChanged();
    }
    private void displayData() {
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                schedule_id.add(cursor.getString(0));
                teamA.add(cursor.getString(1)); // ID
                teamB.add(cursor.getString(2)); // Day
                date.add(cursor.getString(3));
                time.add(cursor.getString(4));// Location
            }
        }
    }
}