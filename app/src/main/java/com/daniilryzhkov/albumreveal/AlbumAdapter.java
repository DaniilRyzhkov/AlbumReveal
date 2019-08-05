package com.daniilryzhkov.albumreveal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for RecyclerView in MainActivity
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    // ClickListener realization for recyclerView
    interface AlbumOnClickListener {
        void onClick(int position, int collectionId);
    }

    private Context context;
    private List<Album> albums;
    private AlbumOnClickListener clickListener;

    public AlbumAdapter(Context context, List<Album> albums, AlbumOnClickListener clickListener) {
        this.context = context;
        this.albums = albums;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_of_albums, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albums.get(position);
        Picasso.with(context).load(album.getArtworkUrl100()).into(holder.albumLogo);
        holder.albumName.setText(album.getCollectionName());
        holder.artistName.setText(album.getArtistName());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView albumLogo;
        private TextView albumName;
        private TextView artistName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.albumLogo = itemView.findViewById(R.id.album_logo);
            this.albumName = itemView.findViewById(R.id.album_name);
            this.artistName = itemView.findViewById(R.id.artist_name);
            // ClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    clickListener.onClick(position, albums.get(position).getCollectionId());
                }
            });
        }
    }
}
