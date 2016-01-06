package com.finalandroidproject.quickmovie.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.R;

public class InvitationFragment extends ListFragment {

    private Movie currMovie;
    private OnFragmentInteractionListener mListener;

    public InvitationFragment() {
    }

    public InvitationFragment(Movie movie) {
        currMovie = movie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Change Adapter to display your content
        //setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //        android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
     //       mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_invitation, container, false);

        // Set the adapter
        //mListView = (AbsListView) view.findViewById(android.R.id.list);
        //((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        //mListView.setOnItemClickListener(this);

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
