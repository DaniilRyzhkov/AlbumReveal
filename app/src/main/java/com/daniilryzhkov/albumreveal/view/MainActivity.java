package com.daniilryzhkov.albumreveal.view;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.daniilryzhkov.albumreveal.presenter.BasePresenter;
import com.daniilryzhkov.albumreveal.view.adapters.MainAdapter;
import com.daniilryzhkov.albumreveal.presenter.MainPresenter;
import com.daniilryzhkov.albumreveal.R;
import com.daniilryzhkov.albumreveal.retrofit.ResultModel;

import java.util.List;



/**
 * Launcher activity
 * Search and browse the results
 */
public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    ImageButton searchButton;
    EditText searchField;
    RecyclerView albumsList;
    ProgressBar progressBar;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.search_button);
        searchField = findViewById(R.id.search_field);
        albumsList = findViewById(R.id.albums_list);
        progressBar = findViewById(R.id.progress_bar);

        adapter = new MainAdapter();
        albumsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        albumsList.setHasFixedSize(true);
        albumsList.setAdapter(adapter);

        // Enter pressed (soft keyboard)
        searchField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                boolean b = keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER;
                if (b) {
                    mainPresenter.performSearch(searchField.getText().toString());
                }
                return b;
            }
        });

        // SearchButton pressed
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String term = searchField.getText().toString();
                mainPresenter.performSearch(term);
            }
        });

        mainPresenter.startToWork();
    }

    @Override
    public void requestFocus() {
        searchField.requestFocus();
    }

    @Override
    public void clearFocus() {
        searchField.clearFocus();
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
    public void hideKeyboard() {
        // toggle mode (alwaysVisible -> unchanged)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
        // hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchButton.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * RecyclerView to update new data
     *
     * @param list new data from iTunes Api
     */
    @Override
    public void updateList(List<ResultModel> list) {
        albumsList.setAdapter(adapter);
        adapter.setList(list);
    }
}
