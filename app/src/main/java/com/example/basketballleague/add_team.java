package com.example.basketballleague;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class add_team extends AppCompatActivity {

    EditText team, location;

    Button add_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_team);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        team = findViewById(R.id.team_name);
        location = findViewById(R.id.team_loc);
        add_team = findViewById(R.id.add_team);

        add_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team_name = team.getText().toString().trim();
                String team_loc = location.getText().toString().trim();

                if (team_name.isEmpty() || team_loc.isEmpty()) {
                    if (team_name.isEmpty()) {
                        team.setError("Team name is required");
                    }
                    if (team_loc.isEmpty()) {
                        location.setError("Location is required");
                    }
                    return;
                }

                team_database db = new team_database(com.example.basketballleague.add_team.this);
                db.addTeam(team_name, team_loc);

                Toast.makeText(add_team.this, "Team and Status added successfully", Toast.LENGTH_SHORT).show();

                team.setText("");
                location.setText("");

                finish();


            }
        });

    }
}