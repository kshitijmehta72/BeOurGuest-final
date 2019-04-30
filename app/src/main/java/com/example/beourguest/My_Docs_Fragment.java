package com.example.beourguest;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class My_Docs_Fragment extends Fragment implements View.OnClickListener {
    private ImageView user_image;
    private File imgID;
    FloatingActionButton upload,delete,expand;
    boolean isFABOpen=false;
    private final String dir = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/BoG_IDs/");
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_docs_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        upload = (FloatingActionButton) view.findViewById(R.id.DocAdd);
        upload.setOnClickListener(this);
        delete = (FloatingActionButton) view.findViewById(R.id.DocDelete);
        delete.setOnClickListener(this);
        expand = (FloatingActionButton) view.findViewById(R.id.DocExpand);
        expand.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
        user_image = (ImageView)view.findViewById(R.id.user_image);
        imgID  = new File(dir+"ID.jpeg");
        if(imgID.exists()) {
            Glide.with(getContext()).load(imgID).asBitmap().signature(new StringSignature(imgID.length() + "@" + imgID.lastModified())).into(user_image);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DocAdd:
                if(imgID.exists()) {
                    Toast.makeText(getContext(),"Please delete current ID first",Toast.LENGTH_SHORT).show();
                } else {
                    new openCameraIntent().execute();
                }
                break;
            case R.id.DocDelete:
                if(imgID.exists()) {
                    imgID.delete();
                    user_image.setImageResource(R.drawable.logo_black);
                } else {
                    Toast.makeText(getContext(),"No ID uploaded to delete",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.DocExpand:
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
                break;
        }

    }
    public void showFABMenu() {
        isFABOpen=true;
        upload.show();
        delete.show();
        upload.animate().translationY(-150);
        delete.animate().translationY(-300);
    }
    public void closeFABMenu() {
        isFABOpen=false;
        upload.hide();
        delete.hide();
        upload.animate().translationY(0);
        delete.animate().translationY(0);
    }

    protected class openCameraIntent extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            File newdir = new File(dir);
            newdir.mkdirs();
            File newfile = new File(dir+"ID.jpeg");
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri outputFileUri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),
                    BuildConfig.APPLICATION_ID + ".provider",
                    newfile);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(cameraIntent, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getContext()).load(imgID).asBitmap().signature(new StringSignature(imgID.length() + "@" + imgID.lastModified())).into(user_image);
                    handler.removeCallbacksAndMessages(null);
                }
            },4000);

            super.onPostExecute(aVoid);
        }
    }
}

