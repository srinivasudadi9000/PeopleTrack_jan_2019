package com.srinivas.peopletracke.TrackLog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srinivas.peopletracke.R;

import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackHolder> {
    int id_layout;
    Context context;
    ArrayList<Track> tracks;

    public TrackAdapter(ArrayList<Track> tracks, int tracksingle, Context applicationContext) {
        this.tracks = tracks;
        this.id_layout = tracksingle;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public TrackHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(id_layout, viewGroup, false);
        return new TrackHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackHolder trackHolder, int i) {
        trackHolder.latitude_tv.setText("latitude "+tracks.get(i).getLat());
        trackHolder.longitude_tv.setText("longitude " +tracks.get(i).getLongitude());
        trackHolder.current_dt.setText(tracks.get(i).getCdt());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class TrackHolder extends RecyclerView.ViewHolder {
        TextView latitude_tv, longitude_tv,current_dt;

        public TrackHolder(@NonNull View itemView) {
            super(itemView);
            latitude_tv = itemView.findViewById(R.id.latitude_tv);
            longitude_tv = itemView.findViewById(R.id.longitude_tv);
            current_dt = itemView.findViewById(R.id.current_dt);
        }
    }
}
