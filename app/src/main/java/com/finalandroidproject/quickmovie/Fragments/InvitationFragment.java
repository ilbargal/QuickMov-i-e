package com.finalandroidproject.quickmovie.Fragments;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.finalandroidproject.quickmovie.DAL.InvitationDAL;
import com.finalandroidproject.quickmovie.Model.Cache;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.R;

public class InvitationFragment extends ListFragment {
    private AbsListView mListView;
    private InvitationListAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public InvitationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new InvitationListAdapter();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    class InvitationListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Cache.Invitations.size();
        }

        @Override
        public Object getItem(int position) {
            return Cache.Invitations.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.invitation_row_layout, null);
            }

            ImageView imgMovieImage = (ImageView) convertView.findViewById(R.id.imgMoviePic);
            TextView txtMovieDetails = (TextView) convertView.findViewById(R.id.txtMovieDetails);
            CheckBox checkIsAccepted = (CheckBox) convertView.findViewById(R.id.checkIsAccepted);

            final MovieInvitation currInvitation = Cache.Invitations.get(position);

            // TODO: set friend image

            String day = "";
            String month = "";
            String hour = "";
            String minuts = "";

            if (currInvitation.getInvitationDate().getDay() < 10){
                day = "0" + currInvitation.getInvitationDate().getDay();
            }
            else {
                day = String.valueOf(currInvitation.getInvitationDate().getDay());
            }

            if (currInvitation.getInvitationDate().getMonth() < 10){
                month = "0" + currInvitation.getInvitationDate().getMonth();
            }
            else {
                month = String.valueOf(currInvitation.getInvitationDate().getMonth());
            }

            if (currInvitation.getInvitationDate().getHours() < 10){
                hour = "0" + currInvitation.getInvitationDate().getHours();
            }
            else {
                hour = String.valueOf(currInvitation.getInvitationDate().getHours());
            }

            if (currInvitation.getInvitationDate().getMinutes() < 10){
                minuts = "0" + currInvitation.getInvitationDate().getMinutes();
            }
            else {
                minuts = String.valueOf(currInvitation.getInvitationDate().getMinutes());
            }

            String movieDate = day + "/" + month + "/" + currInvitation.getInvitationDate().getYear();
            String movieTime = hour + ":" + minuts;

            txtMovieDetails.setText(currInvitation.getFromFriend().getName() + " הזמין אותך לסרט: " +
                    currInvitation.getMovie().getName() + "\n" + " בקולנוע: " + currInvitation.getCinema() +
                    "\n בתאריך: " + movieDate + " בשעה: " + movieTime);
            checkIsAccepted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        currInvitation.setIsAccepted(true);
                        new InvitationDAL().updateInvitation(currInvitation);
                    }
                }
            });
            return convertView;
        }
    }

}
