package com.example.ajaydeepaksrinivasan.movies.ui.Main;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ajaydeepaksrinivasan.movies.Models.MovieResponse;
import com.example.ajaydeepaksrinivasan.movies.R;
import com.example.ajaydeepaksrinivasan.movies.adapters.MoviesAdapter;
import com.example.ajaydeepaksrinivasan.movies.ui.Search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainViewInterface {

    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String TAG = "MainActivity";
    RecyclerView.Adapter adapter;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpMVP();
        setUpViews();
        getMoviesList();
    }

    private void setUpViews() {

        setSupportActionBar(toolbar);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpMVP() {
        mainPresenter = new MainPresenter(this);
    }

    private void getMoviesList(){
        mainPresenter.getMovies();
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void displayMovies(MovieResponse movieResponse) {
        if(movieResponse != null){
            Log.d(TAG,movieResponse.getResults().get(1).getTitle());
            adapter = new MoviesAdapter(movieResponse.getResults(),MainActivity.this);
            rvMovies.setAdapter(adapter);
        } else {
            Log.d(TAG,"Moviereponse is null");
        }

    }

    @Override
    public void displayError(String message) {

        showToast(message);

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
         progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){

            showToast("Search clicked");
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
