package com.example.beourguest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class My_Events_Fragment extends Fragment {
    List<booking> bookingList;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    booking_adapter adapter;
    booking_adapter nadapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_events_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        bookingList = new ArrayList<>();
        new retrieve().execute();
        //Default value for RecyclerView
        bookingList.add(
                new booking(
                        "DEFAULT",
                        "DEFAULT",
                        0,0,
                        R.drawable.logo_brg,
                        "0000","ttl","qrkey"));



        adapter = new booking_adapter(getContext(),bookingList);
        recyclerView.setAdapter(adapter);
        recyclerView.setAlpha(0f);

    }

    private class retrieve extends AsyncTask<Void,Void,Void> {
        ProgressDialog progDailog;
        Object data;
        String club,date,bookid,gtotal,qrkey;
        Integer stag,couple,club_logo;
        @Override
        protected Void doInBackground(Void... voids) {
            db.collection(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            //Log.i("TAGGG", document.getId() + " => " + document.getData());
                            data = document.getData();
                            club = ((Map) data).get("club").toString();
                            date = ((Map) data).get("date").toString();
                            stag = Integer.parseInt(((Map) data).get("stag").toString());
                            couple = Integer.parseInt(((Map) data).get("couple").toString());
                            club_logo = Integer.parseInt(((Map) data).get("Club Logo").toString());
                            bookid = ((Map) data).get("BookingID").toString();
                            gtotal = ((Map) data).get("Total Amount").toString();
                            qrkey = Objects.requireNonNull(((Map) data).get("qrkey")).toString();
                            booking b1 = new booking(club,date,stag,couple,club_logo,bookid,gtotal,qrkey);
                            //Log.i("TAGG2",b1.getClub_name()+b1.getDate());
                            bookingList.add(
                                    new booking(club,date,stag,couple,club_logo,bookid,gtotal,qrkey)
                            );

                        }
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            nadapter = new booking_adapter(getContext(),bookingList);
            updateadapter();
            progDailog.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            progDailog = new ProgressDialog(getActivity());
            progDailog.setMessage("Loading...!!!");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
            super.onPreExecute();
        }
    }
    public void updateadapter() {
        //Timer function for delayed update on RecyclerView after getting data from DB
        Handler h1 = new Handler();
        final ProgressDialog progDailog1 = new ProgressDialog(getActivity());
        progDailog1.setMessage("Loading...!!!");
        progDailog1.setIndeterminate(false);
        progDailog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog1.setCancelable(true);
        progDailog1.show();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                bookingList.remove(0);
                recyclerView.setAdapter(nadapter);
                recyclerView.setAlpha(1f);
                progDailog1.dismiss();
            }
        },3000);
    }


}
