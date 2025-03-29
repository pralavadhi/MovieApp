package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movieapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MovieViewModel viewModel;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Setup RecyclerView
        adapter = new MovieAdapter(this::onMovieClick); // pass click listener
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // Button click: search
        binding.btnSearch.setOnClickListener(v -> {
            String query = binding.etSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                viewModel.searchMovies(query);
            } else {
                Toast.makeText(this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe LiveData
        viewModel.getMovies().observe(this, this::updateUI);
    }

    // When mvie is clicked
    private void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("imdbID", movie.getImdbID());
        startActivity(intent);
    }

    private void updateUI(List<Movie> movies) {
        try {
            if (movies != null && !movies.isEmpty()) {
                Log.d("DEBUG", "UI updating: " + movies.size() + " movies");
                adapter.setMovieList(movies);
                binding.recyclerView.setVisibility(View.VISIBLE);
            } else {
                Log.e("DEBUG", "No movies to show");
                adapter.setMovieList(new ArrayList<>());
                binding.recyclerView.setVisibility(View.GONE);
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("DEBUG", "updateUI crash: " + e.getMessage());
            Toast.makeText(this, "UI crashed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}