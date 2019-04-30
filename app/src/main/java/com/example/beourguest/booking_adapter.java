package com.example.beourguest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class booking_adapter extends RecyclerView.Adapter<booking_adapter.bookingViewHolder> {

    private Context mCtx;

    private List<booking> bookingList;

    public booking_adapter(Context mCtx, List<booking> bookingList) {
        this.mCtx = mCtx;
        this.bookingList = bookingList;
    }

    @Override
    public bookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card_layout, null);
        return new bookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bookingViewHolder holder, int position) {

        booking booking = bookingList.get(position);
        holder.clubname.setText(booking.getClub_name());
        holder.bookingdate.setText(booking.getDate());
        holder.personcount.setText(String.valueOf(booking.getCount()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(booking.getImage()));

    }


    @Override
    public int getItemCount() {
        return bookingList.size();
    }



    class bookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView clubname, bookingdate, personcount;
        ImageView imageView;

        public bookingViewHolder(View itemView) {
            super(itemView);
            clubname = itemView.findViewById(R.id.club_name);
            bookingdate = itemView.findViewById(R.id.booking_date);
            personcount = itemView.findViewById(R.id.person_count);
            imageView = itemView.findViewById(R.id.club_photo);
            clubname.setOnClickListener(this);
            bookingdate.setOnClickListener(this);
            personcount.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mCtx,ReOpenBook.class);
            booking booking = bookingList.get(this.getLayoutPosition());
            intent.putExtra("club",booking.getClub_name());
            intent.putExtra("bookid",booking.getID());
            intent.putExtra("stag",booking.getstag());
            intent.putExtra("couple",booking.getcouple());
            intent.putExtra("gtotal",booking.getgtotal());
            intent.putExtra("qrkey",booking.getkey());
            mCtx.startActivity(intent);
            ((Activity)mCtx).overridePendingTransition(R.anim.goup,R.anim.godown);
        }
    }
}
