package com.daniilryzhkov.albumreveal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for RecyclerView in AboutAlbumActivity
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private Context context;
    private List<Track> tracks;

    public TrackAdapter(Context context, List<Track> tracks) {
        this.context = context;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public TrackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_of_tracks, parent, false);
        return new TrackAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.trackNumber.setText(String.valueOf(track.getTrackNumber()));
        holder.trackName.setText(track.getTrackName());
        holder.trackArtist.setText(track.getArtistName());
        // Some pre-release albums may not have trackTimeMillis data
        if (track.getTrackTimeMillis() != null)
            holder.trackDuration.setText(formatTime(track.getTrackTimeMillis()));
        else {
            holder.trackDuration.setText(context.getResources().getString(R.string.no_data));
        }
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView trackNumber;
        private TextView trackName;
        private TextView trackArtist;
        private TextView trackDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.trackNumber = itemView.findViewById(R.id.track_number);
            this.trackName = itemView.findViewById(R.id.track_name);
            this.trackArtist = itemView.findViewById(R.id.track_artist);
            this.trackDuration = itemView.findViewById(R.id.track_duration);
        }
    }

    private String formatTime(int timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return sdf.format(timeInMillis);
    }
}
