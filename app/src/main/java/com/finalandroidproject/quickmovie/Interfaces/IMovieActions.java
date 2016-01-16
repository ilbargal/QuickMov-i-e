package com.finalandroidproject.quickmovie.Interfaces;

import com.finalandroidproject.quickmovie.Model.Movie;

import java.util.Dictionary;
import java.util.List;

public interface IMovieActions {
    List<Movie> getAllMovies(int startIndex,int MaxMovies);
    Movie getMovieByName(String movieName);
    List<Movie> getMoviesByParameters(Dictionary<String, Object> parameters);
    void insertMovie(Movie newMovie);
    void updateMovie(Movie movie);
    void deleteMovie(Movie movie);
}
