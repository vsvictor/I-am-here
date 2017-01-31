package com.home.iamhere;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.home.iamhere.data.ContactItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = ContactFragment.class.getSimpleName();
    private List<ContactItem> list;
    private ContactAdapter adatpter;
    private OnContactListener listener;
    private double lantitude;
    private double longitude;

    public ContactFragment() {
    }

    @SuppressWarnings("unused")
    public static ContactFragment newInstance(double lan, double lon) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putDouble("lat", lan);
        args.putDouble("lon", lon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            lantitude = args.getDouble("lat", 0);
            longitude = args.getDouble("lon", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_item, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
            list = new ArrayList<ContactItem>();
            adatpter = new ContactAdapter(getContext(), list, listener);
            recyclerView.setAdapter(adatpter);
            //new Loader().execute();
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "(" + String.valueOf(lantitude) + ";" + String.valueOf(longitude) + ")", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnContactListener) {
            listener = (OnContactListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts.Data._ID,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Data.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Data.DISPLAY_NAME,
                    ContactsContract.Data.HAS_PHONE_NUMBER,
                    ContactsContract.Data.DATA1
            };

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + " = '1'",
                null,
                ContactsContract.Data.DISPLAY_NAME
        );
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        String contactNumber = null;
        if (data.moveToFirst()) {
            do {
                String contactID = data.getString(data.getColumnIndex("_id"));
                String name = data.getString(data.getColumnIndex("display_name"));
                String photo = data.getString(data.getColumnIndex("photo_uri"));
                Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                        new String[]{contactID},
                        null);

                if (cursorPhone.moveToFirst()) {
                    contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                } else contactNumber = "undefined";
                cursorPhone.close();
                list.add(new ContactItem(contactID, name, contactNumber, photo));
            } while (data.moveToNext());
            adatpter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adatpter.notifyDataSetChanged();
    }

    public interface OnContactListener {
        void onListContacts(ContactItem item);
    }
}
