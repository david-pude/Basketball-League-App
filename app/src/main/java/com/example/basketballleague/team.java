package com.example.basketballleague;

import android.annotation.SuppressLint;
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

public class team extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_btn;
    team_database db;
    ArrayList<String> team_id, team_name, team_loc;
    adapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_team);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.team);
        add_btn = findViewById(R.id.add_btn);

        add_btn.setOnClickListener(v -> {
            Intent intent = new Intent(team.this, add_team.class);
            startActivity(intent);
        });

        db = new team_database(team.this);
        team_id = new ArrayList<>();
        team_name = new ArrayList<>();
        team_loc = new ArrayList<>();

        displayData();
        adapter = new adapter(team.this, team_id, team_name, team_loc);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(team.this));

        refreshData();
    }

    private void refreshData() {
        team_id.clear();
        team_name.clear();
        team_loc.clear();
        displayData();
        adapter.notifyDataSetChanged();
    }

    void displayData() {
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                team_id.add(cursor.getString(0));
                team_name.add(cursor.getString(1));
                team_loc.add(cursor.getString(2));
            }
        }

    }
}
