package com.finalandroidproject.quickmovie.Fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.finalandroidproject.quickmovie.DAL.FriendDAL;
import com.finalandroidproject.quickmovie.DAL.UserDAL;
import com.finalandroidproject.quickmovie.Model.Cache;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.User;
import com.finalandroidproject.quickmovie.R;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;

import java.util.LinkedList;
import java.util.List;

public class ContactsFragment extends ListFragment {
    private static List<Friend> contacts;
    private ContactsListAdapter mAdapter;
    private ListView list;

    private final String ADD_FRIEND_DIALOG_TITLE = "הוספת חבר";
    private final String USER_DOESNT_EXISTS = "משתמש לא קיים";

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
        try {
            if(Cache.Contacts.size() > 0) {
                contacts = Cache.Contacts;
            } else {
                contacts = new LinkedList<Friend>();

                ContentResolver cr = getActivity().getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {
                        String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

                        while (phones.moveToNext()) {
                            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

                            if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                                contacts.add(new Friend("", number, name));
                                break;
                            }
                        }
                    }
                }
            }
            list = (ListView) view.findViewById(android.R.id.list);
            list.setVisibility(View.VISIBLE);
            container.setVisibility(View.VISIBLE);
            mAdapter.contactsList = contacts;
            list.setAdapter(mAdapter);

            //setListAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    class ContactsListAdapter extends BaseAdapter {
        private TextView txtName;
        private Button btnAddContact;
        public List<Friend> contactsList;

        @Override
        public int getCount() {
            return contactsList.size();
        }

        public Object getItem(int position) {
            return contactsList.get(position);
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

            txtName = (TextView) convertView.findViewById(R.id.contactName);
            btnAddContact = (Button) convertView.findViewById(R.id.btnAddContact);

            String currContact = contactsList.get(position).getName();

            txtName.setText(currContact);
            btnAddContact.setTag(position);
            btnAddContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Friend newFriend = contactsList.get(Integer.parseInt(v.getTag().toString()));
                    String ObjectID = UserDAL.instance.CheckIfUserExsist(newFriend);
                    if (ObjectID != "") {
                        newFriend.setID(ObjectID);
                        FriendDAL.instance.addFriendtoUser((User) Cache.currentUser, newFriend);
                    } else {
                        AlertDialog.Builder alertDialgBuilder = new AlertDialog.Builder(getActivity());
                        alertDialgBuilder.setNegativeButton("אישור", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialgBuilder.setTitle(ADD_FRIEND_DIALOG_TITLE);
                        alertDialgBuilder.setMessage(USER_DOESNT_EXISTS);
                        alertDialgBuilder.create().show();
                    }

                }
            });
            return convertView;
        }
    }

}
