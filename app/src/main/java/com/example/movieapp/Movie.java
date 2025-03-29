package com.example.movieapp;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("imdbID")
    private String imdbID;

    @SerializedName("Poster")
    private String poster;

    // Constructor
    public Movie(String title, String year, String imdbID, String poster) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.poster = poster;
    }

    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getImdbID() { return imdbID; }
    public String getPoster() { return poster; }
}
