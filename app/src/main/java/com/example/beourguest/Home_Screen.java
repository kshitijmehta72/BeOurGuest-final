package com.example.beourguest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class Home_Screen extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.goup,R.anim.godown).replace(R.id.fragment,new Club_Home()).commit();
                    return true;
                case R.id.navigation_events:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.goup,R.anim.godown).replace(R.id.fragment,new My_Events_Fragment()).commit();
                    return true;
                case R.id.navigation_docs:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.goup,R.anim.godown).replace(R.id.fragment,new My_Docs_Fragment()).commit();
                    return true;
                case R.id.navigation_more:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.goup,R.anim.godown).replace(R.id.fragment,new More_Fragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home__screen);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Club_Home()).commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
