package com.finalandroidproject.quickmovie.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
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

import com.finalandroidproject.quickmovie.DAL.InvitationDAL;
import com.finalandroidproject.quickmovie.Model.Cache;
import com.finalandroidproject.quickmovie.Model.Cinema;
import com.finalandroidproject.quickmovie.Model.User;
import com.finalandroidproject.quickmovie.UsefulClasses.DownloadImageTask;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectMovieFragment extends Fragment {

    public Movie currMovie;
    public static List<Friend> friends;
    private Cinema currCinema;

    private InvitationCreateListener listener;
    private AlertDialog movieDialog;
    private AlertDialog cinemasDialog;

    private final String CHOOSE_CINEMA_DIALOG_TITLE = "בחר אולם קולנוע";
    private final String CHOOSE_MOVIE_DIALOG_TITLE = "בחר סרט";
    private final int HIGH_RATING = 8;
    private final int MIDDLE_RATING = 4;


    public SelectMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_movie, container, false);
        friends = new ArrayList<Friend>();
        friends.add((Friend) IntentHelper.getObjectForKey("friend"));

        // Initalize all components
        initalize(view);

        // Creates dialogs
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
        builder.setTitle(CHOOSE_MOVIE_DIALOG_TITLE)
                .setItems(moviesNames.toArray(new CharSequence[moviesNames.size()]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        currMovie = Cache.Movies.get(which);

                        // Change chosen movie detials: picuture, description and name
                        setMovieDetails(currView, currMovie);
                    }
                });
        return builder.create();
    }

    public AlertDialog createCinemasDialog(final View currView, Movie currMovie) {
        ArrayList<String> cinemas = new ArrayList<String>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(CHOOSE_CINEMA_DIALOG_TITLE);
        if (currMovie.getCinemas() != null) {
            for (Cinema cinema : Cache.Cinemas) {
                cinemas.add(cinema.getName());
            }

            builder.setItems(cinemas.toArray(new CharSequence[cinemas.size()]), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    currCinema = Cache.Cinemas.get(which);
                }
            });
        }

        return builder.create();
    }

    public void initalize(View currView) {
        listener = new InvitationCreateListener() {
            @Override
            public void onInvitationsCreated(Movie currMovie) {
                Toast.makeText(getActivity(),
                            "   הזמנותיך עבור הסרט "  + currMovie.getName() + " נשלחו לחבריך",
                                Toast.LENGTH_LONG).show();
            }
        };

        final List<Friend> friends = new ArrayList<Friend>();
        if (currMovie == null && !Cache.Movies.isEmpty())
            currMovie = Cache.Movies.get(0);

        setMovieDetails(currView, currMovie);

        // Movie Dialog
        TextView chooseMovie = (TextView) currView.findViewById(R.id.lstMovies);
        chooseMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDialog.show();
            }
        });

        // Cinama dialgo
        TextView chooseCinema = (TextView) currView.findViewById(R.id.selectionMovieCinema);
        chooseCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cinemasDialog.show();
            }
        });

        // Friends list
        AbsListView mListView = (AbsListView) currView.findViewById(R.id.selectionMovieFriends);
        mListView.setAdapter(new FriendInvitationListAdapter(this));

        // Invite to movie button
        Button btnAddInvitation = (Button) currView.findViewById(R.id.btnCreateInvitation);
        btnAddInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SelectMovieFragment.friends.size() > 0) {
                    // Save invitaion and get back
                    MovieInvitation invitation;
                    for (Friend currFriend : SelectMovieFragment.friends) {
                        if(currFriend != null) {
                            invitation = new MovieInvitation(new Date().getTimezoneOffset(),
                                    new Friend(Cache.currentUser.getID(), Cache.currentUser.getPhone(), Cache.currentUser.getName()),
                                    currFriend,
                                    currMovie,
                                    currCinema,
                                    new Date());

                            new InvitationDAL().addNewInvitation(invitation);
                        }
                    }
                    getActivity().finish();
                    listener.onInvitationsCreated(currMovie);
                }
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
        movieImage.setVisibility(View.GONE);

        if(currMovie.getImagePath() != null && currMovie.getImagePath() != "") {
            new DownloadImageTask(movieImage, (ProgressBar) currView.findViewById(R.id.selectMovieProgressbar)).execute(currMovie.getImagePath());
        }
        else {
            movieImage.setImageResource(R.drawable.film);
        }


        movieRating.setText(String.valueOf(currMovie.getRating()) + " / 10");
        paintMovieByRating(movieRating, currMovie.getRating());
        movieSelectedCinema.setText("סינימה סיטי גלילות");
    }

    private void paintMovieByRating(TextView textView, double rating) {
        if (rating >= HIGH_RATING)
            textView.setTextColor(Color.GREEN);
        else if (rating < HIGH_RATING && rating > MIDDLE_RATING)
            textView.setTextColor(Color.YELLOW);
        else
            textView.setTextColor(Color.RED);
    }

    public interface InvitationCreateListener {
        void onInvitationsCreated(Movie currMovie);
    }

    class FriendInvitationListAdapter extends BaseAdapter {
        private SelectMovieFragment currFrag;

        public FriendInvitationListAdapter(SelectMovieFragment frag) {
            currFrag = frag;
        }

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


            final Friend currFriend = Cache.Friends.get(position);

            btnInviteToMovie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (!SelectMovieFragment.friends.contains(Cache.Friends.get(position))) {
                            SelectMovieFragment.friends.add(Cache.Friends.get(position));
                        }
                    } else {
                        if (SelectMovieFragment.friends.contains(Cache.Friends.get(position))) {
                            int pos = SelectMovieFragment.friends.indexOf(Cache.Friends.get(position));
                            SelectMovieFragment.friends.remove(pos);
                        }
                    }
                }
            });

            if (SelectMovieFragment.friends.contains(currFriend)) {
                btnInviteToMovie.setChecked(true);
            } else {
                btnInviteToMovie.setChecked(false);
            }

            if(imgFriendImage.getDrawable() == null) {
                if (currFriend.getProfilePic() != null && currFriend.getProfilePic() != "") {
                    new DownloadImageTask(imgFriendImage, (ProgressBar) convertView.findViewById(R.id.friendProgressbar)).execute(currFriend.getProfilePic());
                } else {
                    new DownloadImageTask(imgFriendImage, (ProgressBar) convertView.findViewById(R.id.friendProgressbar)).execute("http://kollabase.com/data/userpics/default.png");

                }
            }

            txtFriendName.setText(currFriend.getName());

            return convertView;
        }
    }
}
