package com.finalandroidproject.quickmovie.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalandroidproject.quickmovie.UsefulClasses.DownloadImageTask;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.R;

import java.util.ArrayList;
import java.util.Date;
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
        final List<Friend> friends = new ArrayList<Friend>();

        if (currMovie != null) {

            TextView movieName = (TextView) currView.findViewById(R.id.lstMovies);
            TextView movieDescription = (TextView) currView.findViewById(R.id.selectionMovieDescription);
            ImageView movieImage = (ImageView) currView.findViewById(R.id.selectionMovieImage);

            movieName.setText(currMovie.getName());
            movieDescription.setText(currMovie.getDescription());
            new DownloadImageTask(movieImage).execute(currMovie.getImagePath());
        }

        Button btnAddInvitation = (Button) currView.findViewById(R.id.btnCreateInvitation);
        btnAddInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save invitaion and get back
                MovieInvitation invitation;

                for (Friend currFriend : friends)
                {
                    invitation = new MovieInvitation(1,
                                                     new Friend("Bar gal", "123"),
                                                     currFriend,
                                                     currMovie,
                                                     "גלילות",
                                                     new Date());

                    // Save invitation
                }


                getActivity().onBackPressed();
            }
        });
    }
}
