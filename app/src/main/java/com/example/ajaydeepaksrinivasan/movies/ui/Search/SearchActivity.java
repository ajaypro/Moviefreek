package com.example.ajaydeepaksrinivasan.movies.ui.Search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ajaydeepaksrinivasan.movies.Models.MovieResponse;
import com.example.ajaydeepaksrinivasan.movies.R;
import com.example.ajaydeepaksrinivasan.movies.adapters.MoviesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchViewInterface{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rvQueryResult)
    RecyclerView recyclerView;

    SearchPresenter searchPresenter;
    private String TAG = "SearchActivity";
    private SearchView searchView;

    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setUpViews();
        SetUpMVP();
    }

    private void SetUpMVP() {

        searchPresenter = new SearchPresenter(this);

    }

    private void setUpViews() {
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Enter movie name..");

        searchPresenter.getResultsOnQuerying(searchView);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void displayResults(MovieResponse movieResponse) {

        adapter = new MoviesAdapter(movieResponse.getResults(),SearchActivity.this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void displayError(String error) {
        showToast(error);

    }


}
