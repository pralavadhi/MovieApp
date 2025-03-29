package com.example.movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDBApiService {

    // Searches for movies by title
    @GET("/")
    Call<MovieResponse> searchMovies(
            @Query("s") String query,
            @Query("apikey") String apiKey
    );

    // Gets full movie details by imdbID
    @GET("/")
    Call<MovieDetails> getMovieDetails(
            @Query("i") String imdbID,
            @Query("apikey") String apiKey
    );
}
