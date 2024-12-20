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

public class venue extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;
    Mydatabase myDB;
    ArrayList<String> venueId, venueDay, venueLocation;
    vadapter venueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);

        recyclerView = findViewById(R.id.recycler_view);
        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(venue.this, add_venue.class);
            startActivity(intent);
        });

        myDB = new Mydatabase(venue.this);
        venueId = new ArrayList<>();
        venueDay = new ArrayList<>();
        venueLocation = new ArrayList<>();

        venueAdapter = new vadapter(venue.this, venueId, venueLocation, venueDay);
        recyclerView.setAdapter(venueAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(venue.this));

        refreshData();
    }

    private void refreshData() {
        venueId.clear();
        venueDay.clear();
        venueLocation.clear();
        displayData();
        venueAdapter.notifyDataSetChanged();
    }

    private void displayData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                venueId.add(cursor.getString(0));
                venueDay.add(cursor.getString(1));
                venueLocation.add(cursor.getString(2));
            }
        }
    }
}