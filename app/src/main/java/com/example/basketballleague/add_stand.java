package com.example.basketballleague;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class add_stand extends AppCompatActivity {

    EditText teamInput, statusInput;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stand);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        teamInput = findViewById(R.id.team);
        statusInput = findViewById(R.id.status);
        add = findViewById(R.id.add_stand);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String team = teamInput.getText().toString().trim();
                String status = statusInput.getText().toString().trim();

                if (team.isEmpty() || status.isEmpty()) {
                    if (team.isEmpty()) {
                        teamInput.setError("Team name is required");
                    }
                    if (status.isEmpty()) {
                        statusInput.setError("Status is required");
                    }
                    return;
                }

                stand_database db = new stand_database(add_stand.this);
                db.addStand(team, status);

                Toast.makeText(add_stand.this, "Team and Status added successfully", Toast.LENGTH_SHORT).show();

                teamInput.setText("");
                statusInput.setText("");

                finish();
            }
        });
    }
}