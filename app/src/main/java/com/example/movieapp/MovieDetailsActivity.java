package com.example.movieapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.movieapp.databinding.ActivityMovieDetailsBinding;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        String imdbID = getIntent().getStringExtra("imdbID");

        if (imdbID != null) {
            viewModel.loadMovieDetails(imdbID);
        } else {
            Toast.makeText(this, "No movie ID provided", Toast.LENGTH_SHORT).show();
            finish();
        }

        viewModel.getMovieDetails().observe(this, movie -> {
            if (movie != null) {
                binding.textViewTitle.setText(movie.getTitle());
                binding.textViewYear.setText("Year: " + movie.getYear());
                binding.textViewGenre.setText("Genre: " + movie.getGenre());
                binding.textViewDirector.setText("Director: " + movie.getDirector());
                binding.textViewStudio.setText("Studio: " + (movie.getStudio() != null ? movie.getStudio() : "N/A"));
                binding.textViewRating.setText("Rating: " + (movie.getRating() != null ? movie.getRating() : "N/A"));
                binding.textViewPlot.setText("Plot: " + movie.getPlot());

                Glide.with(this)
                        .load(movie.getPoster())
                        .into(binding.imageViewPoster);
            } else {
                Toast.makeText(this, "Movie details could not be loaded.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonBack.setOnClickListener(v -> finish());
    }
}
