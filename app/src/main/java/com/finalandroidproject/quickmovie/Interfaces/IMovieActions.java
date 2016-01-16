package com.finalandroidproject.quickmovie.Interfaces;

import com.finalandroidproject.quickmovie.Model.Movie;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public interface IMovieActions {
    List<Movie> getAllMovies(int startIndex,int MaxMovies,boolean Refresh);
    Movie getMovieByName(String movieName);
    List<Movie> getMoviesByParameters(HashMap<String, Object> parameters);
    void insertMovie(Movie newMovie);
    void updateMovie(Movie movie);
    void deleteMovie(Movie movie);
}
