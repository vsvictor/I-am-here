package com.home.iamhere;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.home.iamhere.data.ContactItem;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final List<ContactItem> mValues;
    private final ContactFragment.OnContactListener mListener;
    private Context context;
    public ContactAdapter(Context context, List<ContactItem> items, ContactFragment.OnContactListener listener) {
        this.context = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ContactItem cont = mValues.get(position);
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(String.valueOf(mValues.get(position).id));
        holder.mContentView.setText(cont.name);
        holder.mPhoneNumber.setText(cont.number);
        holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimarySuperLight));
        Picasso.with(context).load(cont.photo).into(holder.cvAvatar);
        if(cont.photo == null){
            holder.cvAvatar.setImageResource(R.drawable.ic_profile);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
                    mListener.onListContacts(cont);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mPhoneNumber;
        public final CircleImageView cvAvatar;
        public final CardView cardView;
        public ContactItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.tvID);
            mContentView = (TextView) view.findViewById(R.id.tvName);
            mPhoneNumber = (TextView) view.findViewById(R.id.tvPhoneNumber);
            cvAvatar = (CircleImageView) view.findViewById(R.id.cvProfile_image);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
/*
    public Bitmap openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
            }
        } finally {
            cursor.close();
        }
        return null;

    }
*/
}
