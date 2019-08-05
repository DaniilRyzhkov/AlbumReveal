package com.daniilryzhkov.albumreveal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity shows detailed information for the selected album
 */
public class AboutAlbumActivity extends AppCompatActivity {

    RecyclerView trackList;
    ImageView albumLogo;
    TextView albumName;
    TextView albumArtist;
    TextView albumGenre;
    TextView albumDate;
    TextView albumTrackCount;
    TextView albumPrice;
    TextView albumCopyright;

    private List<Track> tracks;
    private Track info; // For collection's info data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_album);

        // Show the home button on the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        trackList = findViewById(R.id.track_list);
        albumLogo = findViewById(R.id.album_logo);
        albumName = findViewById(R.id.album_name);
        albumArtist = findViewById(R.id.album_artist);
        albumGenre = findViewById(R.id.album_genre);
        albumDate = findViewById(R.id.album_date);
        albumTrackCount = findViewById(R.id.album_track_count);
        albumPrice = findViewById(R.id.album_price);
        albumCopyright = findViewById(R.id.album_copyright);

        // Get collection's id from extras
        int collection_id = getIntent().getIntExtra(MainActivity.COLLECTION_ID, 0);
        // Send a request to iTunes and get a info list about the selected album including a list of tracks
        ApiClient.getApi().getTracksResponse(collection_id).enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                if (response.code() == 200) {
                    TrackResponse trackResponse = response.body();
                    if (trackResponse.getTracks() != null) {
                        tracks = trackResponse.getTracks();
                        info = tracks.get(0); // Get collection's info
                        tracks.remove(0); // Remove collection's info, retaining track list only
                        setAdapter(tracks);
                        setAlbumInfo();
                    }
                } else {
                    Log.e(MainActivity.LOG_TAG, "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                Log.e(MainActivity.LOG_TAG, t.toString());
            }
        });
    }

    /**
     * Create and Set adapter for RecyclerView
     * @param tracks data List from iTunes
     */
    private void setAdapter(List<Track> tracks) {
        TrackAdapter adapter = new TrackAdapter(AboutAlbumActivity.this, tracks);
        trackList.setAdapter(adapter);
        trackList.setLayoutManager(new LinearLayoutManager(AboutAlbumActivity.this));
        trackList.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    /**
     * Set info into views for selected album
     */
    private void setAlbumInfo() {
        Picasso.with(this).load(info.getArtworkUrl100()).into(albumLogo);
        albumName.setText(info.getCollectionName());
        albumArtist.setText(info.getArtistName());
        albumGenre.setText(info.getPrimaryGenreName());
        albumDate.setText(info.getReleaseDate().split(getResources().getString(R.string.dash))[0]);
        // Concatenation the track count and "tracks"
        String trackCount = info.getTrackCount() + " " + getResources().getString(R.string.tracks);
        albumTrackCount.setText(trackCount);
        // Concatenation the price and the currency
        String price = info.getTrackCount() + " " + info.getCurrency();
        albumPrice.setText(price);
        albumCopyright.setText(info.getCopyright());
    }

    /**
     * Finish AboutAlbumActivity to back the MainActivity
     * @param item button's item
     * @return super class
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
