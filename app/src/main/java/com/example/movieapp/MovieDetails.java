package com.example.movieapp;

import com.google.gson.annotations.SerializedName;

public class MovieDetails {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Genre")
    private String genre;

    @SerializedName("Director")
    private String director;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Poster")
    private String poster;

    @SerializedName("Production")
    private String studio;

    @SerializedName("imdbRating")
    private String rating;

    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getGenre() { return genre; }
    public String getDirector() { return director; }
    public String getPlot() { return plot; }
    public String getPoster() { return poster; }
    public String getStudio() { return studio; }
    public String getRating() { return rating; }
}
