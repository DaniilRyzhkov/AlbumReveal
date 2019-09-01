package com.daniilryzhkov.albumreveal.view.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniilryzhkov.albumreveal.R;
import com.daniilryzhkov.albumreveal.retrofit.ResultModel;
import com.daniilryzhkov.albumreveal.view.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for RecyclerView in MainActivity
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    public final static String COLLECTION_ID = "collection_id";
    private List<ResultModel> results = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_albums, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(parent.getContext(), DetailActivity.class);
                intent.putExtra(COLLECTION_ID, results.get(position).getCollectionId());
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultModel album = results.get(position);
        Picasso.get().load(album.getArtworkUrl100()).into(holder.albumLogo);
        holder.albumName.setText(album.getCollectionName());
        holder.artistName.setText(album.getArtistName());
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

        private ImageView albumLogo;
        private TextView albumName;
        private TextView artistName;
        private LinearLayout rootLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.albumLogo = itemView.findViewById(R.id.album_logo);
            this.albumName = itemView.findViewById(R.id.album_name);
            this.artistName = itemView.findViewById(R.id.artist_name);
            this.rootLayout = itemView.findViewById(R.id.item_root_layout);
        }
    }
}
