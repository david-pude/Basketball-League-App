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

public class vadapter extends RecyclerView.Adapter<vadapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> venue_id, venue_location, venue_day;

    public vadapter(Context context, ArrayList<String> venue_id, ArrayList<String> venue_location, ArrayList<String> venue_day) {
        this.context = context;
        this.venue_id = venue_id;
        this.venue_location = venue_location;
        this.venue_day = venue_day;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.venue_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.venue.setText(venue_id.get(position));
        holder.location.setText(venue_location.get(position));
        holder.day.setText(venue_day.get(position));
    }

    @Override
    public int getItemCount() {
        return venue_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView venue, location, day;
        ImageButton deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            venue = itemView.findViewById(R.id.venue);
            location = itemView.findViewById(R.id.location);
            day = itemView.findViewById(R.id.day);
            deleteButton = itemView.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        String id = venue_id.get(position);
                        Mydatabase myDB = new Mydatabase(context);
                        myDB.deleteVenue(id);

                        venue_id.remove(position);
                        venue_day.remove(position);
                        venue_location.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
}