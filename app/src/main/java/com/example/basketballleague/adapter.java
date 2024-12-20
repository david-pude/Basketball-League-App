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

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    private Context context;

    private ArrayList<String> team_id, team_name_id, team_loc_id;

    public adapter(Context context, ArrayList<String> team_id, ArrayList<String> team_name_id, ArrayList<String> team_loc_id) {

        this.context = context;
        this.team_id = team_id;
        this.team_name_id = team_name_id;
        this.team_loc_id = team_loc_id;

    }


    @NonNull
    @Override
    public adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.team_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.ViewHolder holder, int position) {

        holder.team_layout.setText(team_id.get(position));
        holder.team_nam.setText(team_name_id.get(position));
        holder.team_lo.setText(team_loc_id.get(position));

    }

    @Override
    public int getItemCount() {
        return team_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView team_layout, team_nam, team_lo;
        ImageButton delete_team;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            team_layout = itemView.findViewById(R.id.team_layout);
            team_nam = itemView.findViewById(R.id.team_nam);
            team_lo = itemView.findViewById(R.id.team_lo);
            delete_team = itemView.findViewById(R.id.delete_team);

            delete_team.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        String id = team_id.get(position);
                        team_database myDB = new team_database(context);
                        myDB.deleteTeam(id);

                        team_id.remove(position);
                        team_name_id.remove(position);
                        team_loc_id.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

        }
    }
}