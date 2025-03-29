package com.example.movieapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse {

    @SerializedName("Search")
    private List<Movie> search;

    @SerializedName("Response")
    private String response;

    public List<Movie> getSearch() {
        return search;
    }

    public String getResponse() {
        return response;
    }
}
