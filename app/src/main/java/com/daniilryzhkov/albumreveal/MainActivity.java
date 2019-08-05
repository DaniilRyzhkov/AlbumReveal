package com.daniilryzhkov.albumreveal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Launcher activity
 * Search and browse the albums
 */
public class MainActivity extends AppCompatActivity implements AlbumAdapter.AlbumOnClickListener {

    RecyclerView albumsList;
    EditText searchField;
    ImageButton searchButton;

    private List<Album> albums;
    private String term; // the title of the searched album
    public final static String COLLECTION_ID = "collection_id";
    public final static String LOG_TAG = "album_reveal_log";
    public final static String KEY_SAVE = "term";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albumsList = findViewById(R.id.albums_list);
        searchField = findViewById(R.id.search_field);
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                term = searchField.getText().toString();
                if (!term.isEmpty()) {
                    if (isOnline()) {
                        searchAlbums(term);
                        hideKeyboard();
                    } else {
                        Toast.makeText(MainActivity.this, MainActivity.this.getResources()
                                .getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, MainActivity.this.getResources()
                            .getString(R.string.enter_albums_title), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * The method uses the Retrofit library to query iTunes and get a list of albums
     * @param term the album name in the query
     */
    private void searchAlbums(String term) {
        ApiClient.getApi().getAlbumsResponse(term).enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if (response.code() == 200) {
                    AlbumResponse albumResponse = response.body();
                    if (albumResponse.getAlbums() != null) {
                        albums = albumResponse.getAlbums();
                        sortByName(albums);
                        setAdapter(albums);
                    }
                } else {
                    // Code 403 could mean that the number of requests per minute can reach the limit(20)
                    if (response.code() == 403)
                        // "No server response\nPlease try again later"
                        Toast.makeText(MainActivity.this, MainActivity.this.getResources()
                                .getString(R.string.error_403), Toast.LENGTH_LONG).show();
                    Log.e(LOG_TAG, "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });

    }

    /**
     * The method checks internet connection
     * @return true - online, false - offline
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

    /**
     * Sort the list by alphabetical type
     * @param albums target list
     */
    private void sortByName(List<Album> albums) {
        Collections.sort(albums, new Comparator<Album>() {
            @Override
            public int compare(Album album1, Album album2) {
                String albumName1 = album1.getCollectionName();
                String albumName2 = album2.getCollectionName();
                return albumName1.compareTo(albumName2);
            }
        });
    }

    /**
     * Create and Set adapter for RecyclerView
     * @param albums data List from iTunes
     */
    private void setAdapter(List<Album> albums) {
        AlbumAdapter adapter = new AlbumAdapter(MainActivity.this, albums, this);
        albumsList.setAdapter(adapter);
        albumsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        albumsList.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    /**
     * The method hides soft keyboard when a query is entered
     */
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(searchButton.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Method overrides from AlbumAdapter.AlbumOnClickListener
     * OnClickListener for RecyclerView
     * @param position     from getAdapterPosition() for ViewHolder's itemView
     * @param collectionId selected album id
     */
    @Override
    public void onClick(int position, int collectionId) {
        Intent intent = new Intent(this, AboutAlbumActivity.class);
        intent.putExtra(COLLECTION_ID, collectionId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (term != null && !term.isEmpty()) {  // Restore the list when screen rotates
            if (isOnline()) {
                searchAlbums(term);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_SAVE, term); // save search field data
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState); // restore search field data
        term = savedInstanceState.getString(KEY_SAVE);
    }
}
