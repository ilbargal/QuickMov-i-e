package com.finalandroidproject.quickmovie.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.finalandroidproject.quickmovie.Fragments.SelectMovieFragment;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.R;

import java.util.List;

public class NavigationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currFragment = createSelectMovieNavigationFragment(null, null);
        fragmentTransaction.add(R.id.frgNavigationContainer, currFragment);
        fragmentTransaction.commit();
    }

    private SelectMovieFragment createSelectMovieNavigationFragment(Movie currMovie, List<Friend> friends) {
        SelectMovieFragment fragment = new SelectMovieFragment();
        fragment.friends = friends;
        fragment.currMovie = currMovie;
        return fragment;
    }
}
