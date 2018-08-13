package com.example.ajaydeepaksrinivasan.movies.ui.Search;

import com.example.ajaydeepaksrinivasan.movies.Models.MovieResponse;

public interface SearchViewInterface {

    void showToast(String message);
    void displayResults(MovieResponse movieResponse);
    void displayError(String error);

}
