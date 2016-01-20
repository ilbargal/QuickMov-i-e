package com.finalandroidproject.quickmovie.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.finalandroidproject.quickmovie.Fragments.SelectMovieFragment;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.R;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;

import java.util.List;

public class NavigationActivity extends Activity {

    private SelectMovieFragment selectionMovieFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        LinearLayout layout = (LinearLayout) findViewById(R.id.frgNavigationContainer);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Movie currMovie = (Movie) IntentHelper.getObjectForKey("movie");
        selectionMovieFragment = createSelectMovieNavigationFragment(currMovie, null);
        fragmentTransaction.add(R.id.frgNavigationContainer, selectionMovieFragment);
        fragmentTransaction.commit();
    }

    private SelectMovieFragment createSelectMovieNavigationFragment(Movie currMovie, List<Friend> friends) {
        SelectMovieFragment fragment = new SelectMovieFragment();
        fragment.friends = friends;
        fragment.currMovie = currMovie;
        return fragment;
    }
}
