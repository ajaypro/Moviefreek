package com.example.ajaydeepaksrinivasan.movies.ui.Search;

import android.util.Log;
import android.widget.SearchView;


import com.example.ajaydeepaksrinivasan.movies.Models.MovieResponse;
import com.example.ajaydeepaksrinivasan.movies.Network.NetworkInterface;
import com.example.ajaydeepaksrinivasan.movies.Network.Networkclient;


import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchPresenter implements SearchPresenterInterface {

    private String TAG = "SearchPresenter";
    private SearchViewInterface searchViewInterface;

    public SearchPresenter(SearchViewInterface searchViewInterface) {

        this.searchViewInterface = searchViewInterface;

    }


    @Override
    public void getResultsOnQuerying(SearchView searchView) {

        getObservableQuery(searchView)
                .filter(new Predicate<String>() {
                    /**
                     * Test the given input value and return a boolean.
                     *
                     * @param s the value
                     * @return the boolean result
                     * @throws Exception on error
                     */
                    @Override
                    public boolean test(String s) throws Exception {
                        if (s.equals(""))
                            return false;
                        else {
                            return true;
                        }

                    }
                })
                .debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<MovieResponse>>() {
                    @Override
                    public ObservableSource<MovieResponse> apply(String s) throws Exception {

                        return Networkclient.getRetrofit().create(NetworkInterface.class)
                                .getMovies("a1f804d4c0332fe85b07f47b9331b0bd");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());

    }

    private Observable<String> getObservableQuery(SearchView searchView) {

        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
    }

    private DisposableObserver<MovieResponse> getObserver() {
        return new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse movieResponse) {
                Log.d(TAG, "OnNext" + movieResponse.getTotalResults());
                searchViewInterface.displayResults(movieResponse);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error" + e);
                e.printStackTrace();
                searchViewInterface.displayError("Error fetching movie data");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");

            }
        };

    }
}
