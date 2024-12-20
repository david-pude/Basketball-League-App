package com.example.basketballleague;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class standing extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton button_stand;

    stand_database db;
    ArrayList<String> stand_id, team, status;
    stand_adapter stand_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_standing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_standing);
        button_stand = findViewById(R.id.add_stand);

        button_stand.setOnClickListener(v -> {
            Intent intent = new Intent(standing.this, add_stand.class);
            startActivity(intent);
        });

        db = new stand_database(standing.this);
        stand_id = new ArrayList<>();
        team = new ArrayList<>();
        status = new ArrayList<>();

        displayData();
        stand_adapter = new stand_adapter(standing.this, stand_id, team, status);
        recyclerView.setAdapter(stand_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(standing.this));

        refreshData();
    }

    private void refreshData() {
        stand_id.clear();
        team.clear();
        status.clear();
        displayData();
        stand_adapter.notifyDataSetChanged();
    }
    void displayData() {
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                stand_id.add(cursor.getString(0));
                team.add(cursor.getString(1));
                status.add(cursor.getString(2));
            }
        }

    }
}