package com.example.basketballleague;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class stand_adapter extends RecyclerView.Adapter<stand_adapter.ViewHolder> {

    private Context context;
    private ArrayList<String> stand_id, team_id, status_id;

    public stand_adapter(Context context, ArrayList<String> stand_id, ArrayList<String> team_id, ArrayList<String> status_id) {
        this.context = context;
        this.stand_id = stand_id;
        this.team_id = team_id;
        this.status_id = status_id;
    }

    @NonNull
    @Override
    public stand_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.standing_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.stand.setText(stand_id.get(position));
        holder.team.setText(team_id.get(position));
        holder.status.setText(status_id.get(position));
    }

    @Override
    public int getItemCount() {
        return stand_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stand, team, status;
        ImageButton delete_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stand = itemView.findViewById(R.id.standing);
            team = itemView.findViewById(R.id.team_standing);
            status = itemView.findViewById(R.id.team_stats);
            delete_btn = itemView.findViewById(R.id.delete_btn);

            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String id = stand_id.get(position);

                        stand_database myDB = new stand_database(context);
                        myDB.deleteStand(id);

                        stand_id.remove(position);
                        team_id.remove(position);
                        status_id.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
}