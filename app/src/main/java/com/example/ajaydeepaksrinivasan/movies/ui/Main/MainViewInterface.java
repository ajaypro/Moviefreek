package com.example.ajaydeepaksrinivasan.movies.ui.Main;

import com.example.ajaydeepaksrinivasan.movies.Models.MovieResponse;

public interface MainViewInterface {

    void showToast(String message);
    void displayMovies(MovieResponse movieResponse);
    void displayError(String message);
    void showProgressBar();
    void hideProgressBar();
}
