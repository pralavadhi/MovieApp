package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.databinding.ItemMovieBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onClick(Movie movie);
    }

    public MovieAdapter(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public void setMovieList(List<Movie> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Set basic info
        holder.binding.tvTitle.setText(movie.getTitle());
        holder.binding.tvYear.setText("Year: " + movie.getYear());
        holder.binding.tvStudio.setText("Studio: Loading...");
        holder.binding.tvRating.setText("Rating: Loading...");

        // Load image
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .into(holder.binding.ivPoster);

        // Fetch movie details using imdbID
        RetrofitClient.getInstance().getMovieDetails(movie.getImdbID(), "b619ef58")
                .enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            MovieDetails details = response.body();

                            String studio = details.getStudio() != null ? details.getStudio() : "N/A";
                            String rating = details.getRating() != null ? details.getRating() : "N/A";

                            holder.binding.tvStudio.setText("Studio: " + studio);
                            holder.binding.tvRating.setText("Rating: " + rating);
                        } else {
                            holder.binding.tvStudio.setText("Studio: N/A");
                            holder.binding.tvRating.setText("Rating: N/A");
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {
                        holder.binding.tvStudio.setText("Studio: N/A");
                        holder.binding.tvRating.setText("Rating: N/A");
                    }
                });

        // Handle click
        holder.itemView.setOnClickListener(v -> listener.onClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;

        public MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
