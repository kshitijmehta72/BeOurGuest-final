package com.example.beourguest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Confirm extends AppCompatActivity {
    Bundle bundle;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Map<String,Object>  info;
    String date;
    TextView club_name,booking_id,stagcount,couplecount,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        bundle = getIntent().getBundleExtra("info");
        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        info =  new HashMap<>();
        date = bundle.getString("date");
        new UpdateDB().execute();
        club_name=findViewById(R.id.name);
        booking_id=findViewById(R.id.booking_id);
        stagcount=findViewById(R.id.stag_count);
        couplecount=findViewById(R.id.couple_count);
        total=findViewById(R.id.grandtotal_amt);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,Home_Screen.class));
        overridePendingTransition(R.anim.goup,R.anim.godown);
        finishAffinity();
    }


    private class UpdateDB extends AsyncTask<Void,Void,Void> {
        ProgressDialog progDailog;
        String qrkey;
        Bitmap bitmap;
        private final String dir = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/BoG_QRs/");
        @Override
        protected Void doInBackground(Void... voids) {
            info.put("ID", Objects.requireNonNull(mAuth.getUid()));
            info.put("club", Objects.requireNonNull(bundle.getString("callingclub")));
            info.put("date", Objects.requireNonNull(bundle.getString("date")));
            info.put("name",mAuth.getCurrentUser().getDisplayName());
            info.put("stag",bundle.getString("stag"));
            info.put("couple",bundle.get("couple"));
            info.put("PaymentMode",bundle.getString("paymentmode"));
            info.put("Total Amount",bundle.getString("total"));
            int totalstag = Integer.parseInt(bundle.getString("stag"));
            int totalcpl = Integer.parseInt(bundle.getString("couple"));
            int totalppl = totalstag + 2*totalcpl;
            info.put("Total Entries",totalppl);
            info.put("TransactionID",bundle.getString("TXNID"));
            info.put("BookingID",bundle.getString("OID"));
            info.put("Club Logo",bundle.getString("club_logo"));
            qrkey = bundle.getString("OID");
            info.put("qrkey",qrkey);
            Timestamp ts = Timestamp.now();
            info.put("Timestamp",ts);
            db.collection(Objects.requireNonNull(mAuth.getUid())).document(date)
                    .set(info)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error writing document", e);
                        }
                    });
            bitmap = QRCodeHelper
                    .newInstance(getApplicationContext())
                    .setContent(qrkey)
                    .setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
                    .setMargin(2)
                    .getQRCOde();
            File newdir = new File(dir);
            if(!newdir.exists()) {
                newdir.mkdirs();
            }
            File qrimage = new File(dir+date+".PNG");
            try {
                FileOutputStream out = new FileOutputStream(String.valueOf(qrimage));
                bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progDailog = new ProgressDialog(Confirm.this);
            progDailog.setMessage("Loading...!!!");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ImageView qr = (ImageView)findViewById(R.id.qr_code);
            club_name.setText(bundle.getString("callingclub"));
            booking_id.setText(bundle.getString("OID"));
            stagcount.setText(bundle.getString("stag"));
            couplecount.setText(bundle.getString("couple"));
            total.setText(bundle.getString("total"));
            qr.setImageBitmap(bitmap);
            progDailog.dismiss();
            Toast.makeText(Confirm.this,"Booking Confirmed",Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }
    public void onHomeClick(View v) {
        startActivity(new Intent(this,Home_Screen.class));
        overridePendingTransition(R.anim.goup,R.anim.godown);
        finishAffinity();
    }
    public void onNeedHelpClick(View v) {
        Snackbar.make(v,"Contact Us at 7228985777",Snackbar.LENGTH_SHORT).show();
    }
}
