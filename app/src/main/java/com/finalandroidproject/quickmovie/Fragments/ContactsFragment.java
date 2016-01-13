package com.finalandroidproject.quickmovie.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.finalandroidproject.quickmovie.R;

import java.util.LinkedList;
import java.util.List;

public class ContactsFragment extends Fragment {
    List<String> contacts;
    private ContactsListAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ContactsListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        contacts = new LinkedList<String>();

        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            }
        }

        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class ContactsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contacts.size();
        }

        public Object getItem(int position) {
            return contacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.contact_row_layout, null);
            }

            TextView txtName = (TextView) convertView.findViewById(R.id.contactName);
            Button btnAddContact = (Button) convertView.findViewById(R.id.btnAddContact);

            String currContact = contacts.get(position);

            // TODO: set friend image
            txtName.setText(currContact);
            btnAddContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: send friend invitation to contact...
                }
            });
            return convertView;
        }
    }

}
