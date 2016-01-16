package com.finalandroidproject.quickmovie.Fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.finalandroidproject.quickmovie.Activities.NavigationActivity;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;
import com.finalandroidproject.quickmovie.Model.Cache;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.R;

public class FriendFragment extends ListFragment {
    Button btnAddFriend;

    private AbsListView mListView;
    private FriendListAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public FriendFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FriendListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        btnAddFriend = (Button) view.findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: get all users contacts and open new fragment or change the current fragment...
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ContactsFragment fragment = new ContactsFragment();
                fragmentTransaction.replace(R.id.pager, fragment);
                fragmentTransaction.addToBackStack("friendsTAG");
                fragmentTransaction.commit();
            }
        });

        // Set OnItemClickListener so we can be notified on item clicks
        //mListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    class FriendListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Cache.Friends.size();
        }

        public Object getItem(int position) {
            return Cache.Friends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.friend_row_layout, null);
            }

            ImageView imgFriendImage = (ImageView) convertView.findViewById(R.id.imgFriendImage);
            TextView txtFriendName = (TextView) convertView.findViewById(R.id.txtFriendName);
            Button btnInviteToMovie = (Button) convertView.findViewById(R.id.btnInviteToMovie);

            final Friend currFriend = Cache.Friends.get(position);

            // TODO: set friend image
            txtFriendName.setText(currFriend.getName());
            btnInviteToMovie.setText("הזמן את "  + currFriend.getName() + "לסרט");
            btnInviteToMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent selectMovieNavigationIntent = new Intent(getActivity(), NavigationActivity.class);
                    IntentHelper.addObjectForKey("friend", currFriend);
                    startActivity(selectMovieNavigationIntent);
                }
            });
            return convertView;
        }
    }

}
