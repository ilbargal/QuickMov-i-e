package com.finalandroidproject.quickmovie.DAL;

import android.location.Location;
import android.util.Log;

import com.finalandroidproject.quickmovie.Interfaces.IMovieActions;
import com.finalandroidproject.quickmovie.Model.Cinema;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nivg1 on 13/01/2016.
 */
public class MovieDAL implements IMovieActions {
    public final static MovieDAL instance = new MovieDAL();

    private boolean _movieInLocalStore = false;

    @Override
    public List<Movie> getAllMovies(int startIndex, int MaxMovies, boolean Refresh) {
        try {
            // Parse get query for table Movies
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Movies");

            if (_movieInLocalStore && !Refresh) {
                query.fromLocalDatastore();
            }

            if(Refresh) {
                ParseObject.unpinAllInBackground("Movies");
            }

            query.setLimit(MaxMovies);
            query.setSkip(startIndex);

            List<ParseObject> data = query.find();

            List<Movie> arrMovies = new ArrayList<Movie>();
            for (ParseObject movieObject : data) {
                if (!_movieInLocalStore) {
                    movieObject.pinInBackground("Movies");
                }

                Movie mMovie = new Movie();
                String name = movieObject.getString("Name");
                String ganre = movieObject.getString("Genre");
                String description = movieObject.getString("Description");
                double rating = movieObject.getDouble("Rating");
                String image = movieObject.getParseFile("Image").getUrl();
                ParseRelation Cinema = movieObject.getRelation("Cinema");

                mMovie = new Movie();
                mMovie.setName(name);
                mMovie.setGanre(ganre);
                mMovie.setDescription(description);
                mMovie.setRating(rating);
                mMovie.setImagePath(image);
                mMovie.setCinemas(getCinemas(Cinema.getQuery(),Refresh));

                arrMovies.add(mMovie);

            }
            _movieInLocalStore = true;
            return arrMovies;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cinema> getCinemas(ParseQuery query, boolean Refresh){
        try {
            if (_movieInLocalStore && !Refresh) {
                query.fromLocalDatastore();
            }

            List<ParseObject> data =  query.find();

            List<Cinema> Cinames = new ArrayList<>();
            for (ParseObject cinemaObject : data) {
                if (!_movieInLocalStore) {
                    cinemaObject.pinInBackground("Cinemas");
                }

                Cinema newCinema = new Cinema();
                newCinema.setName(cinemaObject.getString("Name"));
                ParseGeoPoint Geopoint = cinemaObject.getParseGeoPoint("Location");
                Location loc = new Location("Parse");
                loc.setLatitude(Geopoint.getLatitude());
                loc.setLongitude(Geopoint.getLongitude());
                newCinema.setLocation(loc);

                Cinames.add(newCinema);
            }

            return  Cinames;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Movie getMovieByName(String movieName) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("Name",movieName);
        if(getMoviesByParameters(parameters).size() == 1){
            return getMoviesByParameters(parameters).get(0);
        } else {
            return null;
        }

    }

    @Override
    public List<Movie> getMoviesByParameters(HashMap<String, Object> parameters) {
        // Parse get query for table Users
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Movies");

        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            query.whereEqualTo(param.getKey(), param.getValue());
        }

        if (_movieInLocalStore) {
            query.fromLocalDatastore();
        }

        // Execute query (select)
        List<ParseObject> data = null;

        try {
            data = query.find();

            List<Movie> arrMovies = new ArrayList<Movie>();
            for (ParseObject movieObject : data) {
                Movie mMovie = new Movie();
                String name = movieObject.getString("Name");
                String ganre = movieObject.getString("Ganre");
                String description = movieObject.getString("Description");
                double rating = movieObject.getDouble("Rating");
                ParseFile image = movieObject.getParseFile("Image");

                mMovie = new Movie();
                mMovie.setName(name);
                mMovie.setGanre(ganre);
                mMovie.setDescription(description);
                mMovie.setRating(rating);
                mMovie.setImagePath(image.getUrl());

                arrMovies.add(mMovie);
            }

            return arrMovies;
        } catch (ParseException e) {
            return null;
        }
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
