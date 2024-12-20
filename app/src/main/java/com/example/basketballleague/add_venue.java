package com.example.basketballleague;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class add_venue extends AppCompatActivity {

    EditText day_input, location_input;
    final Calendar calendar = Calendar.getInstance(); // To store selected date
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        day_input = findViewById(R.id.day_input);
        location_input = findViewById(R.id.location_input);
        add = findViewById(R.id.add);

        day_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show DatePickerDialog
                new DatePickerDialog(add_venue.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String dayOfWeekFormat = "EEEE"; // Day of the week (e.g., Monday)
                        SimpleDateFormat sdf = new SimpleDateFormat(dayOfWeekFormat, Locale.US);
                        day_input.setText(sdf.format(calendar.getTime()));
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = day_input.getText().toString().trim();
                String location = location_input.getText().toString().trim();

                if (day.isEmpty() || location.isEmpty()) {
                    if (day.isEmpty()) {
                        day_input.setError("Day is required");
                    }
                    if (location.isEmpty()) {
                        location_input.setError("Location is required");
                    }
                    return;
                }

                Mydatabase myDB = new Mydatabase(add_venue.this);
                long result = myDB.addVenue(day, location);

                if (result != -1) {
                    Toast.makeText(add_venue.this, "Venue added successfully", Toast.LENGTH_SHORT).show();

                    day_input.setText("");
                    location_input.setText("");

                    finish();
                } else {
                    // Handle failure (if the insertion failed for some reason)
                    Toast.makeText(add_venue.this, "Failed to add venue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}