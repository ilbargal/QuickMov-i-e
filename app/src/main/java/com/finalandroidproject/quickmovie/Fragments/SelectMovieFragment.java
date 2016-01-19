package com.finalandroidproject.quickmovie.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.finalandroidproject.quickmovie.Model.Cache;
import com.finalandroidproject.quickmovie.Model.Cinema;
import com.finalandroidproject.quickmovie.UsefulClasses.DownloadImageTask;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectMovieFragment extends Fragment {

    public Movie currMovie;
    public List<Friend> friends;
    public InvitationCreateListener listener;
    public AlertDialog movieDialog;
    public AlertDialog cinemasDialog;

    public SelectMovieFragment() {
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
        friends = new ArrayList<Friend>();
        friends.add((Friend) IntentHelper.getObjectForKey("friend"));
        initalize(view);
        movieDialog = createMoviesDialog(view);
        cinemasDialog = createCinemasDialog(view, currMovie);
        return view;
    }

    public AlertDialog createMoviesDialog(final View currView) {
        ArrayList<String> moviesNames = new ArrayList<String>();
        for (Movie movie : Cache.Movies) {
            moviesNames.add(movie.getName());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("בחר סרט")
                .setItems(moviesNames.toArray(new CharSequence[moviesNames.size()]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        currMovie = Cache.Movies.get(which);
                        setMovieDetails(currView, currMovie);
                        cinemasDialog = createCinemasDialog(currView, currMovie);
                    }
                });
        return builder.create();
    }

    public AlertDialog createCinemasDialog(final View currView, Movie currMovie) {
        ArrayList<String> cinemas = new ArrayList<String>();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("בחר אולם קולנוע");
        if (currMovie.getCinemas() != null) {
            for (Cinema cinema : currMovie.getCinemas()) {
                cinemas.add(cinema.getName());
            }

            builder.setItems(cinemas.toArray(new CharSequence[cinemas.size()]), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }

        return builder.create();
    }

    public void initalize(View currView) {
        //Movie myMovie = new MovieDAL().getMovieByName("bla bla");
        listener = new InvitationCreateListener() {
            @Override
            public void onInvitationsCreated(Movie currMovie) {
                Toast.makeText(getActivity(), "הזמנותיך עבור הסרט "  + currMovie.getName() + " נשלחו לחבריך", Toast.LENGTH_LONG).show();
            }
        };
        final List<Friend> friends = new ArrayList<Friend>();
        // For testing
        if (currMovie == null && !Cache.Movies.isEmpty()) {
            currMovie = Cache.Movies.get(0);
        }

        setMovieDetails(currView, currMovie);
        TextView chooseMovie = (TextView) currView.findViewById(R.id.lstMovies);
        chooseMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDialog.show();
            }
        });

        TextView chooseCinema = (TextView) currView.findViewById(R.id.selectionMovieCinema);
        chooseCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cinemasDialog.show();
            }
        });

        AbsListView mListView = (AbsListView) currView.findViewById(R.id.selectionMovieFriends);
        mListView.setAdapter(new FriendInvitationListAdapter());

        Button btnAddInvitation = (Button) currView.findViewById(R.id.btnCreateInvitation);
        btnAddInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save invitaion and get back
                MovieInvitation invitation;

                for (Friend currFriend : friends)
                {
                    invitation = new MovieInvitation(1,
                                                     new Friend("Bar gal", "123",""),
                                                     currFriend,
                                                     currMovie,
                                                     "גלילות",
                                                     new Date());

                    // Save invitation
                }
                getActivity().finish();
                listener.onInvitationsCreated(currMovie);
            }
        });
    }

    private void setMovieDetails(View currView, Movie currMovie) {
        TextView movieName = (TextView) currView.findViewById(R.id.lstMovies);
        TextView movieDescription = (TextView) currView.findViewById(R.id.selectionMovieDescription);
        ImageView movieImage = (ImageView) currView.findViewById(R.id.selectionMovieImage);
        TextView movieRating = (TextView) currView.findViewById(R.id.selectionMovieRating);

        TextView movieSelectedCinema = (TextView) currView.findViewById(R.id.selectionMovieCinema);

        movieName.setText(currMovie.getName());
        movieDescription.setMovementMethod(new ScrollingMovementMethod());
        movieDescription.setText(currMovie.getDescription());
        new DownloadImageTask(movieImage, (ProgressBar) currView.findViewById(R.id.selectMovieProgressbar)).execute(currMovie.getImagePath());

        movieRating.setText(String.valueOf(currMovie.getRating()) + " / 10");
        paintMovieByRating(movieRating, currMovie.getRating());
        //movieSelectedCinema.setText(currMovie.getCinemas().get(1));
        movieSelectedCinema.setText("סינימה סיטי גלילות");
    }

    private void paintMovieByRating(TextView textView, double rating) {
        if (rating >= 8)
            textView.setTextColor(Color.GREEN);
        else if (rating < 8 && rating > 4)
            textView.setTextColor(Color.YELLOW);
        else
            textView.setTextColor(Color.RED);
    }

    public interface InvitationCreateListener {
        void onInvitationsCreated(Movie currMovie);
    }

    class FriendInvitationListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Cache.Friends.size();
        }

        @Override
        public Object getItem(int position) {
            return Cache.Friends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.friend_movie_invitation_row_layout, null);
            }

            ImageView imgFriendImage = (ImageView) convertView.findViewById(R.id.imgFriend);
            TextView txtFriendName = (TextView) convertView.findViewById(R.id.friendName);
            CheckBox btnInviteToMovie = (CheckBox) convertView.findViewById(R.id.isFriendInvited);

            btnInviteToMovie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (!friends.contains(Cache.Friends.get(position))) {
                            friends.add(Cache.Friends.get(position));
                        }
                    }
                    else {
                        if (friends.contains(Cache.Friends.get(position))) {
                            friends.remove(position);
                        }
                    }
                }
            });
            btnInviteToMovie.setChecked(false);
            final Friend currFriend = Cache.Friends.get(position);

            if (friends.contains(currFriend))
                btnInviteToMovie.setChecked(true);
            new DownloadImageTask(imgFriendImage, (ProgressBar) convertView.findViewById(R.id.friendProgressbar)).execute(currFriend.getProfilePic());
            txtFriendName.setText(currFriend.getName());

            return convertView;
        }
    }
}
