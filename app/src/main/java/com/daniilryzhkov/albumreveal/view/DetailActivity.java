package com.daniilryzhkov.albumreveal.view;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.daniilryzhkov.albumreveal.view.adapters.DetailAdapter;
import com.daniilryzhkov.albumreveal.view.adapters.MainAdapter;
import com.daniilryzhkov.albumreveal.presenter.DetailPresenter;
import com.daniilryzhkov.albumreveal.R;
import com.daniilryzhkov.albumreveal.retrofit.ResultModel;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Activity shows detailed information for the selected album
 */
public class DetailActivity extends MvpAppCompatActivity implements DetailView {

    @InjectPresenter
    DetailPresenter detailPresenter;

    RecyclerView trackList;
    ImageView albumLogo;
    TextView albumName;
    TextView albumArtist;
    TextView albumGenre;
    TextView albumDate;
    TextView albumTrackCount;
    TextView albumCopyright;
    ProgressBar progressBar;

    private DetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        trackList = findViewById(R.id.track_list);
        albumLogo = findViewById(R.id.album_logo);
        albumName = findViewById(R.id.album_name);
        albumArtist = findViewById(R.id.album_artist);
        albumGenre = findViewById(R.id.album_genre);
        albumDate = findViewById(R.id.album_date);
        albumTrackCount = findViewById(R.id.album_track_count);
        albumCopyright = findViewById(R.id.album_copyright);
        progressBar = findViewById(R.id.progress_bar);

        adapter = new DetailAdapter();
        trackList.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        trackList.setHasFixedSize(true);
        trackList.setAdapter(adapter);

        detailPresenter.startToWork();
    }

    @Override
    public void performSingleSearch() {
        int collection_id = getIntent().getIntExtra(MainAdapter.COLLECTION_ID, 0);
        detailPresenter.performSearch(collection_id);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDetailInfo(ResultModel info) {
        Picasso.get().load(info.getArtworkUrl100()).into(albumLogo);
        albumName.setText(info.getCollectionName());
        albumArtist.setText(info.getArtistName());
        albumGenre.setText(info.getPrimaryGenreName());
        albumDate.setText(info.getReleaseDate().split(getResources().getString(R.string.dash))[0]);
        String trackCount = info.getTrackCount() + " " + getResources().getString(R.string.tracks);
        albumTrackCount.setText(trackCount);
        albumCopyright.setText(info.getCopyright());
    }

    /**
     * RecyclerView to update new data
     *
     * @param list new data from iTunes Api
     */
    @Override
    public void updateList(List<ResultModel> list) {
        adapter.setList(list);
    }

    public void closeActivity() {
        finish();
    }
}
