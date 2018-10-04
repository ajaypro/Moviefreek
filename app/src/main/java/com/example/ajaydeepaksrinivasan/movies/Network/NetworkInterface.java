package com.example.ajaydeepaksrinivasan.movies.Network;

import com.example.ajaydeepaksrinivasan.movies.Models.MovieResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {

        @GET("discover/movie")
        Observable<MovieResponse> getMovies(@Query("api_key")String apiKey);

        @GET("search/movie")
        Observable<MovieResponse> getMoviesBasedOnQuery(@Query("api_key") String api_key, @Query("query") String q);
}
