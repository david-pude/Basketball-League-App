package com.example.basketballleague;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private Context cn;
    private ArrayList<String> schedule_id, teamA, teamB, date, time;

    public ScheduleAdapter(Context context, ArrayList<String> schedule_id, ArrayList<String> teamA, ArrayList<String> teamB, ArrayList<String> date, ArrayList<String> time) {
        this.cn = context;
        this.schedule_id = schedule_id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.time = time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cn);
        View view = inflater.inflate(R.layout.sched_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Set the data into the view elements
        holder.schedule.setText(schedule_id.get(position));
        holder.team_a.setText(teamA.get(position));
        holder.team_b.setText(teamB.get(position));
        holder.date_t.setText(date.get(position));
        holder.time_t.setText(time.get(position));
    }

    @Override
    public int getItemCount() {
        return schedule_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView schedule, team_a, team_b, date_t, time_t;
        ImageButton delete_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            schedule = itemView.findViewById(R.id.schedule_id);
            team_a = itemView.findViewById(R.id.TA);
            team_b = itemView.findViewById(R.id.TB);
            date_t = itemView.findViewById(R.id.date_row);
            time_t = itemView.findViewById(R.id.time_row);
            delete_btn = itemView.findViewById(R.id.delete_sched);

            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        String id = schedule_id.get(position);
                        ScheduleDatabase db = new ScheduleDatabase(cn);
                        db.deleteSchedule(id);

                        schedule_id.remove(position);
                        teamA.remove(position);
                        teamB.remove(position);
                        date.remove(position);
                        time.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
}

