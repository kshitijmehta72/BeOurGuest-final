package com.example.beourguest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

public class Book extends AppCompatActivity {
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent callingintent = getIntent();
        bundle = callingintent.getExtras();
    }

    public void onCashClick(View v) {
        String oid= UUID.randomUUID().toString();
        bundle.putString("OID",oid);
        bundle.putString("paymentmode","cash");
        Intent confirmintent = new Intent(this,Confirm.class);
        confirmintent.putExtra("info",bundle);
        startActivity(confirmintent);
        overridePendingTransition(R.anim.goup,R.anim.godown);
    }
    public void onPayTmClick(View v) {
        bundle.putString("paymentmode","PayTm");
        Intent confirmintent = new Intent(this,PayTM.class);
        confirmintent.putExtra("info",bundle);
        startActivity(confirmintent);
        overridePendingTransition(R.anim.goup,R.anim.godown);
    }
    public void onCancelClick(View v) {
        startActivity(new Intent(this,Home_Screen.class));
        overridePendingTransition(R.anim.goup,R.anim.godown);
        finish();
    }
}
