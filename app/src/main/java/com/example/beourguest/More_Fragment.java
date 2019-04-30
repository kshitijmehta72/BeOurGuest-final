package com.example.beourguest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class More_Fragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.more_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        ImageView img1 = (ImageView)view.findViewById(R.id.user_img);
        TextView t1 = (TextView)view.findViewById(R.id.name);
        TextView t2 = (TextView)view.findViewById(R.id.email);
        Button logout = (Button)view.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        Button myac=(Button)view.findViewById(R.id.button3);
        myac.setOnClickListener(this);
        t1.setText(mAuth.getCurrentUser().getDisplayName());
        t2.setText(mAuth.getCurrentUser().getEmail());
        String imgurl = mAuth.getCurrentUser().getPhotoUrl().toString();
        Glide.with(this).load(imgurl).into(img1);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("496638047046-ob3sg92v33esi16hd143mo8hfbemqff1.apps.googleusercontent.com")
                        .requestEmail()
                        .requestProfile()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
                mGoogleSignInClient.signOut();
                mAuth.signOut();
                startActivity(new Intent(getContext(),Sign_In_Screen.class));
                Objects.requireNonNull(getActivity()).finish();
                break;
            case R.id.button3:
                Toast.makeText(getContext(),"Will be added soon",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
