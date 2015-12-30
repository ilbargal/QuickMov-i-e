package com.finalandroidproject.quickmovie;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Button;

import com.finalandroidproject.quickmovie.Fragments.FriendFragment;
import com.finalandroidproject.quickmovie.Fragments.MovieFragment;

import java.util.LinkedList;
import java.util.List;

public class MoviesActivity extends Activity {

    // Buttons
    Button movieButton;
    Button friendsButton;
    Button invitationsButton;

    // Fragments
    MovieFragment movieFragment;
    FriendFragment friendFragment;

    // Data Members
    List<Fragment> fragmentsList = new LinkedList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        Initalize();

        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(R.id.frgContainer, movieFragment);
        trans.add(R.id.frgContainer, friendFragment);
        trans.hide(movieFragment);
        trans.show(friendFragment);
        // TODO : after this we need to add all fragments
        trans.commit();
    }

    private void Initalize() {
        movieButton = (Button) findViewById(R.id.btnMoviesTab);
        friendsButton = (Button) findViewById(R.id.btnFriendsTab);
        invitationsButton = (Button) findViewById(R.id.btnInvitationsTab);

        movieFragment = new MovieFragment();
        friendFragment = new FriendFragment();
        fragmentsList.add(movieFragment);
        fragmentsList.add(friendFragment);
    }
}
