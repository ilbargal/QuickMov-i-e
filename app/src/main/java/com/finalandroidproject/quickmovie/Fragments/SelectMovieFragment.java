package com.finalandroidproject.quickmovie.Fragments;

import android.app.Fragment;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalandroidproject.quickmovie.DAL.MovieDAL;
import com.finalandroidproject.quickmovie.DownloadImageTask;
import com.finalandroidproject.quickmovie.IntentHelper;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.R;

import java.util.ArrayList;
import java.util.List;

public class SelectMovieFragment extends Fragment {

    public Movie currMovie;
    public List<Friend> friends;

    public SelectMovieFragment() {
        friends = new ArrayList<Friend>();
        friends.add((Friend) IntentHelper.getObjectForKey("friend"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_movie, container, false);
        initalize(view);
        return view;
    }

    public void initalize(View currView) {
        //Movie myMovie = new MovieDAL().getMovieByName("bla bla");
        Movie myMovie = new Movie("משימה בלתי אפשרית 5", "משימה בלתי אפשרית 5 הוא סרט ממש טוב עם טום קרוז. לכו לראות!", "5", "אימה", null, "https://upload.wikimedia.org/wikipedia/he/6/63/Mission_Impossible_-_Rogue_Nation.jpg");

        if (myMovie != null) {
            TextView movieDescription = (TextView) currView.findViewById(R.id.selectionMovieDescription);
            ImageView movieImage = (ImageView) currView.findViewById(R.id.selectionMovieImage);

            movieDescription.setText(myMovie.getDescription());
            new DownloadImageTask(movieImage).execute(myMovie.getImagePath());
        }
    }
}
