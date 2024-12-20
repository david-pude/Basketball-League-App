package com.example.basketballleague;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class AddSchedule extends AppCompatActivity {

    EditText teamA_input, teamB_input, date_input, time_input;
    final Calendar calendar = Calendar.getInstance(); // To store selected date
    Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sched);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        teamA_input = findViewById(R.id.teamA_input);
        teamB_input = findViewById(R.id.teamB_input);
        date_input = findViewById(R.id.date);
        time_input = findViewById(R.id.time);
        add_btn = findViewById(R.id.add_sched);

        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String dateFormat = "MM/dd/yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                        date_input.setText(sdf.format(calendar.getTime()));
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddSchedule.this, (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    String timeFormat = "hh:mm a";
                    SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
                    time_input.setText(sdf.format(calendar.getTime()));
                },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false).show();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get inputs
                String teamA = teamA_input.getText().toString().trim();
                String teamB = teamB_input.getText().toString().trim();
                String date = date_input.getText().toString().trim();
                String time = time_input.getText().toString().trim();

                if (teamA.isEmpty() || teamB.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    if (teamA.isEmpty()) {
                        teamA_input.setError("Team A is required");
                    }
                    if (teamB.isEmpty()) {
                        teamB_input.setError("Team B is required");
                    }
                    if (date.isEmpty()) {
                        date_input.setError("Date is required");
                    }
                    if (time.isEmpty()) {
                        time_input.setError("Location is required");
                    }
                    return;
                }

                ScheduleDatabase db = new ScheduleDatabase(AddSchedule.this);
                long result = db.addSchedule(teamA, teamB, date, time);

                if (result != -1) {
                    Toast.makeText(AddSchedule.this, "Schedule added successfully", Toast.LENGTH_SHORT).show();

                    teamA_input.setText("");
                    teamB_input.setText("");
                    date_input.setText("");
                    time_input.setText("");

                    finish();
                } else {
                    Toast.makeText(AddSchedule.this, "Failed to add schedule", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
