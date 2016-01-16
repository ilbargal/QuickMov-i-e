package com.finalandroidproject.quickmovie.Fragments;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
        // For testing
        if (currMovie == null) {
            currMovie = new Movie("משימה בלתי אפשרית 5","1\r\n2\n3\n4\n5\n6\n7\n8\n9\n10\n112" , 8.4, "פעולה", null, "https://upload.wikimedia.org/wikipedia/he/6/63/Mission_Impossible_-_Rogue_Nation.jpg");
        }

        TextView movieName = (TextView) currView.findViewById(R.id.lstMovies);
        TextView movieDescription = (TextView) currView.findViewById(R.id.selectionMovieDescription);
        ImageView movieImage = (ImageView) currView.findViewById(R.id.selectionMovieImage);
        TextView movieRating = (TextView) currView.findViewById(R.id.selectionMovieRating);

        movieName.setText(currMovie.getName());
        movieDescription.setMovementMethod(new ScrollingMovementMethod());
        movieDescription.setText(currMovie.getDescription());
        new DownloadImageTask(movieImage).execute(currMovie.getImagePath());

        movieRating.setText(String.valueOf(currMovie.getRating()) + " / 10");
        paintMovieByRating(movieRating, currMovie.getRating());

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

    private void paintMovieByRating(TextView textView, double rating) {
        if (rating >= 8)
            textView.setTextColor(Color.GREEN);
        else if (rating < 8 && rating > 4)
            textView.setTextColor(Color.YELLOW);
        else
            textView.setTextColor(Color.RED);
    }
}
