package com.example.movieapp;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://www.omdbapi.com/";
    private static RetrofitClient instance;
    private final OMDBApiService apiService;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(OMDBApiService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Call<MovieResponse> searchMovies(String title, String apiKey) {
        return apiService.searchMovies(title, apiKey); // ✅ ACTUAL call
    }

    public Call<MovieDetails> getMovieDetails(String imdbID, String apiKey) {
        return apiService.getMovieDetails(imdbID, apiKey);
    }
}

