package com.example.beourguest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Ark extends AppCompatActivity implements ViewPager.OnPageChangeListener,  View.OnClickListener{
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private TextView stagcount,couplecount,coupletotal,stagtotal,totalbtax,scharge,totalatax,t1;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private FloatingActionButton map,expand,zomato;
    private boolean isFABOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ark_home);
        t1 = (TextView)findViewById(R.id.button2);
        myCalendar = Calendar.getInstance();
        String myFormat = "dd-MM-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        t1.setText(sdf.format(myCalendar.getTime()));
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog d1 = new DatePickerDialog (Ark.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                d1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                d1.show();
            }
        });

        isFABOpen=false;
        expand = findViewById(R.id.expandFAB);
        map = findViewById(R.id.mapFAB);
        zomato = findViewById(R.id.zomatoFAB);
        expand.setOnClickListener(this);
        map.setOnClickListener(this);
        zomato.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager1);
        viewPager.addOnPageChangeListener(this);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for(int y = 0; y < dotscount; y++){

            dots[y] = new ImageView(this);
            dots[y].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[y], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        stagcount = (TextView)findViewById(R.id.stag_count);
        couplecount = (TextView)findViewById(R.id.couple_count);
        stagtotal = (TextView)findViewById(R.id.stag_total);
        coupletotal = (TextView)findViewById(R.id.couple_total);
        totalbtax = (TextView)findViewById(R.id.total_amt);
        scharge = (TextView)findViewById(R.id.textView13);
        totalatax = (TextView)findViewById(R.id.grandtotal_amt);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        for(int z = 0; z< dotscount; z++){
            dots[z].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
        }

        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
    void updateLabel() {
        String myFormat = "dd-MM-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        t1.setText(sdf.format(myCalendar.getTime()));
    }

    public void onBookClick(View v) {
        Intent intent = new Intent(Ark.this,Book.class);
        intent.putExtra("callingclub","Ark");
        intent.putExtra("date",t1.getText().toString());
        intent.putExtra("stag",stagcount.getText().toString());
        intent.putExtra("couple",couplecount.getText().toString());
        intent.putExtra("total",totalatax.getText().toString());
        intent.putExtra("club_logo",Integer.toString(R.drawable.ark_logo));
        startActivity(intent);
        overridePendingTransition(R.anim.goup,R.anim.godown);
    }
    public void showFABMenu() {
        isFABOpen=true;
        map.show();
        zomato.show();
        map.animate().translationY(-200);
        zomato.animate().translationY(-350);
    }
    public void closeFABMenu() {
        isFABOpen=false;
        map.hide();
        zomato.hide();
        map.animate().translationY(0);
        zomato.animate().translationY(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.expandFAB) {
            if(!isFABOpen){
                showFABMenu();
            }else{
                closeFABMenu();
            }
        }
        else if(id==R.id.mapFAB) {
            Uri gmmIntentUri = Uri.parse("https://www.google.co.in/maps/place/Ark+2.0/@19.1141776,72.8618753,17z/data=!3m1!4b1!4m5!3m4!1s0x0:0x4ffaf30a62bfc880!8m2!3d19.1141776!4d72.864064");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        else if(id==R.id.zomatoFAB) {
            Intent zomatouri = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zomato.com/mumbai/ark-2-0-courtyard-by-marriott-chakala"));
            startActivity(zomatouri);
        }
        else if(id==R.id.button_add) {
            int x = Integer.parseInt(stagcount.getText().toString());
            if(x==5){
                //Do nothing, maximum is 5
            } else {
                stagcount.setText(Integer.toString(x+1));
                updateamounts();
            }
        }
        else if(id==R.id.button_add2) {
            int x = Integer.parseInt(couplecount.getText().toString());
            if(x==5) {
                //Do nothing, maximum is 5
            } else
            {
                couplecount.setText(Integer.toString(x+1));
                updateamounts();
            }
        }
        else if(id==R.id.button_sub) {
            int x = Integer.parseInt(stagcount.getText().toString());
            if(x==0) {
                //Do nothing, minimum is 0
            }else {
                stagcount.setText(Integer.toString(x-1));
                updateamounts();
            }
        }
        else if(id==R.id.button_sub2) {
            int x = Integer.parseInt(couplecount.getText().toString());
            if(x==0) {
                //Do nothing, minimum is 0
            }else {
                couplecount.setText(Integer.toString(x-1));
                updateamounts();
            }
        }
    }
    private void updateamounts() {
        int x = Integer.parseInt(stagcount.getText().toString());
        int y = Integer.parseInt(couplecount.getText().toString());
        stagtotal.setText(Integer.toString(x*1500));
        coupletotal.setText(Integer.toString(y*2500));
        int ntotalbtax = (x*1500+y*2500);
        int nscharge = (ntotalbtax*5)/100;
        totalbtax.setText(Integer.toString(ntotalbtax));
        scharge.setText(Integer.toString(nscharge));
        totalatax.setText(Integer.toString(ntotalbtax+nscharge));
    }


}
