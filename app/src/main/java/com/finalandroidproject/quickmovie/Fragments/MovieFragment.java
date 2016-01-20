package com.finalandroidproject.quickmovie.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.finalandroidproject.quickmovie.Activities.NavigationActivity;
import com.finalandroidproject.quickmovie.Model.Cache;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.R;
import com.finalandroidproject.quickmovie.UsefulClasses.DownloadImageTask;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;

public class MovieFragment extends Fragment implements AbsListView.OnItemClickListener {
    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    private MovieListAdapter mAdapter;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MovieListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(Cache.Movies.get(position).getName());
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String currMovie);
    }

    class MovieListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Cache.Movies.size();
        }

        @Override
        public Object getItem(int position) {
            return Cache.Movies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.movie_row_layout, null);
            }

            TextView txtMovieName = (TextView) convertView.findViewById(R.id.txtMovieName);
            ImageView imgMovieImage = (ImageView) convertView.findViewById(R.id.imgMovieImage);
            Button btnInviteFriendToMovie = (Button) convertView.findViewById(R.id.btnInviteFriends);

            // Movie name
            final Movie currMovie = Cache.Movies.get(position);
            txtMovieName.setText(currMovie.getName());

            // Async movie image
            if(currMovie.getImagePath() != null && currMovie.getImagePath() != "") {
                new DownloadImageTask(imgMovieImage, (ProgressBar) convertView.findViewById(R.id.movieProgressBar))
                        .execute(currMovie.getImagePath());
            }
            else {
                imgMovieImage.setImageResource(R.drawable.film);
            }

            // Invite to movies
            btnInviteFriendToMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent selectMovieNavigationIntent = new Intent(getActivity(), NavigationActivity.class);
                    IntentHelper.addObjectForKey("movie", currMovie);
                    startActivity(selectMovieNavigationIntent);
                }
            });
            return convertView;
        }
    }
}
