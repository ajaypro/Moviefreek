package com.example.ajaydeepaksrinivasan.movies.ui.Main;

import android.util.Log;

import com.example.ajaydeepaksrinivasan.movies.Models.MovieResponse;
import com.example.ajaydeepaksrinivasan.movies.Network.NetworkInterface;
import com.example.ajaydeepaksrinivasan.movies.Network.Networkclient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;



public class MainPresenter implements MainPresenterInterface {

    MainViewInterface mainViewInterface;
    public String TAG = "MainPresenter";

    public MainPresenter(MainViewInterface mvi){
        this.mainViewInterface = mvi;

    }


    @Override
    public void getMovies() {
        getObservable().subscribeWith(getObserver());

    }

    private Observable<MovieResponse> getObservable() {
        return Networkclient.getRetrofit().create(NetworkInterface.class)
                .getMovies("a1f804d4c0332fe85b07f47b9331b0bd")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieResponse> getObserver() {
        return new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(@NonNull MovieResponse movieResponse) {
                Log.d(TAG,"OnNext"+movieResponse.getTotalResults());
                mainViewInterface.displayMovies(movieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG,"Error..."+e);
                e.printStackTrace();
                mainViewInterface.showToast("Not able to get movies data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                mainViewInterface.hideProgressBar();

            }
        };
    }




}
