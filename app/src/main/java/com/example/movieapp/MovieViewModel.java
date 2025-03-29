package com.example.movieapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private MutableLiveData<MovieDetails> selectedMovie = new MutableLiveData<>();

    private final String API_KEY = "b619ef58"; // Your OMDB API key

    public LiveData<List<Movie>> getMovies() {
        return movieList;
    }

    public LiveData<MovieDetails> getMovieDetails() {
        return selectedMovie;
    }

    public void searchMovies(String query) {
        Log.d("DEBUG", "Search triggered for query: " + query);

        RetrofitClient.getInstance().searchMovies(query, API_KEY)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null) {
                            Log.d("DEBUG", "API Response: " + response.body().getResponse());

                            if ("True".equals(response.body().getResponse())
                                    && response.body().getSearch() != null
                                    && !response.body().getSearch().isEmpty()) {

                                Log.d("DEBUG", "Movies found: " + response.body().getSearch().size());
                                movieList.setValue(response.body().getSearch());

                            } else {
                                Log.e("DEBUG", "Empty results or response = False");
                                movieList.setValue(new ArrayList<>());
                            }

                        } else {
                            Log.e("DEBUG", "response.body() is null");
                            movieList.setValue(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.e("DEBUG", "API call failed: " + t.getMessage());
                        movieList.setValue(new ArrayList<>());
                    }
                });
    }





    // Fetch full details using imdbID
    public void loadMovieDetails(String imdbID) {
        RetrofitClient.getInstance().getMovieDetails(imdbID, API_KEY)
                .enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            selectedMovie.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {
                        selectedMovie.setValue(null);
                    }
                });
    }
}
