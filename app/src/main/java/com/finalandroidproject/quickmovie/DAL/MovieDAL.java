package com.finalandroidproject.quickmovie.DAL;

import com.finalandroidproject.quickmovie.Interfaces.IMovieActions;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by nivg1 on 13/01/2016.
 */
public class MovieDAL implements IMovieActions {
    public final static MovieDAL instance = new MovieDAL();

    @Override
    public List<Movie> getAllMovies(int startIndex, int MaxMovies) {
        // Parse get query for table Movies
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Movies");

        try {
            List<ParseObject> data = query.find();

            for (ParseObject movieObject : data) {

            }
        } catch (ParseException e) {

        }
        return null;
    }

    @Override
    public Movie getMovieByName(String movieName) {
        return null;
    }

    @Override
    public List<Movie> getMoviesByParameters(Dictionary<String, Object> parameters) {
        return null;
    }

    @Override
    public void insertMovie(Movie newMovie) {

    }

    @Override
    public void updateMovie(Movie movie) {

    }

    @Override
    public void deleteMovie(Movie movie) {

    }
}
