package com.example.beourguest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ReOpenBook extends AppCompatActivity {
    Bundle bundle;
    TextView club_name,booking_id,stagcount,couplecount,total;
    Bitmap bitmap;
    String qrkey;
    ImageView qr;
    ProgressDialog progDailog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        bundle = getIntent().getExtras();
        club_name=findViewById(R.id.name);
        booking_id=findViewById(R.id.booking_id);
        stagcount=findViewById(R.id.stag_count);
        couplecount=findViewById(R.id.couple_count);
        total=findViewById(R.id.grandtotal_amt);
        qr = findViewById(R.id.qr_code);
        qrkey = bundle.getString("qrkey");
        new updatepage().execute();
    }

    public class updatepage extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            progDailog = new ProgressDialog(ReOpenBook.this);
            progDailog.setMessage("Loading...!!!");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bitmap = QRCodeHelper
                    .newInstance(getApplicationContext())
                    .setContent(qrkey)
                    .setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
                    .setMargin(2)
                    .getQRCOde();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            club_name.setText(bundle.getString("club"));
            booking_id.setText(bundle.getString("bookid"));
            stagcount.setText(bundle.getString("stag"));
            couplecount.setText(bundle.getString("couple"));
            total.setText(bundle.getString("gtotal"));
            qr.setImageBitmap(bitmap);
            progDailog.dismiss();
            super.onPostExecute(aVoid);
        }
    }
    public void onHomeClick(View v) {
        startActivity(new Intent(this,Home_Screen.class));
        overridePendingTransition(R.anim.goup,R.anim.godown);
        finish();
    }
    public void onNeedHelpClick(View v) {
        Snackbar.make(v,"Contact Us at 7228985777",Snackbar.LENGTH_SHORT).show();
    }
}
