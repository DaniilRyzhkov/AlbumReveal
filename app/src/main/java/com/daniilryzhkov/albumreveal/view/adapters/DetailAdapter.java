package com.daniilryzhkov.albumreveal.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniilryzhkov.albumreveal.R;
import com.daniilryzhkov.albumreveal.retrofit.ResultModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for RecyclerView in DetailActivity
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<ResultModel> results = new ArrayList<>();

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_tracks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultModel resultModel = results.get(position);
        holder.trackNumber.setText(String.valueOf(resultModel.getTrackNumber()));
        holder.trackName.setText(resultModel.getTrackName());
        holder.trackArtist.setText(resultModel.getArtistName());
        holder.trackDuration.setText(formatTime(resultModel.getTrackTimeMillis()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setList(List<ResultModel> list) {
        results = list;
        notifyDataSetChanged();
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
